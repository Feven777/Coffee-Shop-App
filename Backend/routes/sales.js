const express = require('express');
const Sale = require('../models/Sale');
const router = express.Router();

// Summary endpoint
router.get('/summary', async (req, res) => {
  try {
    const now = new Date();
    const startOfDay = new Date(now.setHours(0, 0, 0, 0));
    const startOfWeek = new Date();
    startOfWeek.setDate(now.getDate() - now.getDay());
    startOfWeek.setHours(0, 0, 0, 0);
    const startOfMonth = new Date(now.getFullYear(), now.getMonth(), 1);

    // Helper to compute revenue and trends
    const computePeriod = async (startDate, endDate) => {
      const total = await Sale.aggregate([
        { $match: { date: { $gte: startDate, $lt: endDate } } }, // ✅ Ensure proper filtering
        { $group: { _id: null, sum: { $sum: "$total" }, avg: { $avg: "$total" } } }
      ]);
      return total.length > 0 ? total[0] : { sum: 0, avg: 0 };
    };

    const [dayData, weekData, monthData] = await Promise.all([
      computePeriod(startOfDay, now), // ✅ Fix daily revenue issue
      computePeriod(startOfWeek, now),
      computePeriod(startOfMonth, now),
    ]);

    // Compute revenue trends compared to previous periods
    const computeTrend = async (startDate, lengthInMs) => {
      const prevStart = new Date(startDate.getTime() - lengthInMs);
      const prevEnd = startDate;
      const prevTotal = await Sale.aggregate([
        { $match: { date: { $gte: prevStart, $lt: prevEnd } } },
        { $group: { _id: null, sum: { $sum: "$total" } } }
      ]);
      const prevSum = prevTotal.length > 0 ? prevTotal[0].sum : 0;
      const currSum = await Sale.aggregate([
        { $match: { date: { $gte: startDate } } },
        { $group: { _id: null, sum: { $sum: "$total" } } }
      ]);
      const currSumVal = currSum.length > 0 ? currSum[0].sum : 0;
      return prevSum === 0 ? 0 : ((currSumVal - prevSum) / prevSum); // ✅ Fix trend logic
    };

    const dailyTrend = await computeTrend(startOfDay, 24 * 60 * 60 * 1000);
    const weeklyTrend = await computeTrend(startOfWeek, 7 * 24 * 60 * 60 * 1000);
    const monthlyTrend = await computeTrend(startOfMonth, 30 * 24 * 60 * 60 * 1000);

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
          from: "inventory",  // ✅ Fix reference
          localField: "_id",
          foreignField: "_id",  
          as: "itemDetails"
        }
      },
      { $unwind: "$itemDetails" },
      {
        $project: {
          productName: "$itemDetails.itemName",  // ✅ Fix missing `productName`
          unitsSold: 1,
          revenue: 1
        }
      }
    ]);

    res.json({
      dailyRevenue: dayData.sum || 0,
      dailyRevenueTrend: dailyTrend,
      weeklyRevenue: weekData.sum || 0,
      weeklyRevenueTrend: weeklyTrend,
      monthlyRevenue: monthData.sum || 0,
      monthlyRevenueTrend: monthlyTrend,
      averageOrderValue: monthData.avg ?? 0,  // ✅ Prevents null values
      averageOrderValueTrend: await computeTrend(startOfMonth, 30 * 24 * 60 * 60 * 1000),
      topSellingItems: topItems.length > 0 ? topItems : [] // ✅ Ensures list isn't empty
    });

  } catch (err) {
    console.error(err);
    res.status(500).json({ error: 'Server error' });
  }
});

module.exports = router;