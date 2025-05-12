const { Schema, model } = require('mongoose');
const inventorySchema = new Schema({
  itemName: { type: String, required: true },
  category: String,
  quantity: { type: Number, default: 0 },
  unit: String,
  lowStockThreshold: { type: Number, default: 5 },
});
module.exports = model('Inventory', inventorySchema);