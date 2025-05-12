const express = require('express');
const Sale = require('../models/Sale');
const router = express.Router();

// Record Sale
router.post('/', async (req, res) => {
  const sale = new Sale(req.body);
  await sale.save();
  res.status(201).json(sale);
});

// Read all
router.get('/', async (_, res) => {
  const sales = await Sale.find().populate('itemsSold.itemId', 'itemName');
  res.json(sales);
});



// Summary endpoint
router.get('/summary', async (req, res) => {
  try {
    const now = new Date();
    const startOfDay = new Date(now.setHours(0,0,0,0));
    const startOfWeek = new Date();
    startOfWeek.setDate(now.getDate() - now.getDay());
    startOfWeek.setHours(0,0,0,0);
    const startOfMonth = new Date(now.getFullYear(), now.getMonth(), 1);

    // Helper to compute revenue and trend
    const computePeriod = async (startDate) => {
      const total = await Sale.aggregate([
        { $match: { date: { $gte: startDate } } },
        { $group: { _id: null, sum: { $sum: "$total" }, avg: { $avg: "$total" } } }
      ]);
      return total[0] || { sum: 0, avg: 0 };
    };

    const [dayData, weekData, monthData] = await Promise.all([
      computePeriod(startOfDay),
      computePeriod(startOfWeek),
      computePeriod(startOfMonth),
    ]);

    // For trends, compare with previous period
    const computeTrend = async (startDate, lengthInMs) => {
      const prevStart = new Date(startDate.getTime() - lengthInMs);
      const prevEnd = startDate;
      const prevTotal = await Sale.aggregate([
        { $match: { date: { $gte: prevStart, $lt: prevEnd } } },
        { $group: { _id: null, sum: { $sum: "$total" } } }
      ]);
      const prevSum = (prevTotal[0] && prevTotal[0].sum) || 0;
      const currSum = (await Sale.aggregate([
        { $match: { date: { $gte: startDate } } },
        { $group: { _id: null, sum: { $sum: "$total" } } }
      ]))[0]?.sum || 0;
      return prevSum === 0 ? 0 : (currSum - prevSum) / prevSum;
    };

    const dailyTrend = await computeTrend(startOfDay, 24*60*60*1000);
    const weeklyTrend = await computeTrend(startOfWeek, 7*24*60*60*1000);
    const monthlyTrend = await computeTrend(startOfMonth, 30*24*60*60*1000);

    // Top selling items over the month
    const topItems = await Sale.aggregate([
      { $match: { date: { $gte: startOfMonth } } },
      { $unwind: "$itemsSold" },
      {
        $group: {
          _id: "$itemsSold.itemId",
          unitsSold: { $sum: "$itemsSold.quantity" },
          revenue: { $sum: { $multiply: ["$itemsSold.quantity", "$itemsSold.price"] } }
        }
      },
      { $sort: { unitsSold: -1 } },
      { $limit: 5 },
      {
        $lookup: {
          from: "inventory",
          localField: "_id",
          foreignField: "_id",
          as: "item"
        }
      },
      { $unwind: "$item" },
      {
        $project: {
          productName: "$item.itemName",
          unitsSold: 1,
          revenue: 1
        }
      }
    ]);

    // For each top item, compute trend vs. previous month
    const topSellingItems = await Promise.all(topItems.map(async it => {
      const prevMonthStart = new Date(startOfMonth.getTime() - 30*24*60*60*1000);
      const prevData = await Sale.aggregate([
        { $match: { date: { $gte: prevMonthStart, $lt: startOfMonth } } },
        { $unwind: "$itemsSold" },
        { $match: { "itemsSold.itemId": it._id } },
        {
          $group: {
            _id: "$itemsSold.itemId",
            prevUnits: { $sum: "$itemsSold.quantity" },
            prevRevenue: { $sum: { $multiply: ["$itemsSold.quantity", "$itemsSold.price"] } }
          }
        }
      ]);
      const prev = prevData[0] || { prevUnits: 0, prevRevenue: 0 };
      const trend = prev.prevRevenue === 0
        ? 0
        : (it.revenue - prev.prevRevenue) / prev.prevRevenue;
      return {
        productName: it.productName,
        unitsSold: it.unitsSold,
        revenue: it.revenue,
        trend
      };
    }));

    res.json({
      dailyRevenue: dayData.sum,
      dailyRevenueTrend: dailyTrend,
      weeklyRevenue: weekData.sum,
      weeklyRevenueTrend: weeklyTrend,
      monthlyRevenue: monthData.sum,
      monthlyRevenueTrend: monthlyTrend,
      averageOrderValue: monthData.avg,
      averageOrderValueTrend: (await computeTrend(startOfMonth, 30*24*60*60*1000)) /* reuse if desired */,
      topSellingItems
    });
  } catch (err) {
    console.error(err);
    res.status(500).json({ error: 'Server error' });
  }
});




module.exports = router;