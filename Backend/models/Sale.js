const { Schema, model, Types } = require('mongoose');
const saleSchema = new Schema({
  date: { type: Date, default: Date.now },
  itemsSold: [
    {
      itemId: { type: Types.ObjectId, ref: 'Inventory' },
      quantity: Number,
      price: Number,
    }
  ],
  total: Number,
});
module.exports = model('Sale', saleSchema);