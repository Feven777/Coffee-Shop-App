const { Schema, model, Types } = require('mongoose');
const shiftSchema = new Schema({
  employeeId: { type: Types.ObjectId, ref: 'User', required: true },
  date: { type: Date, required: true },
  shiftType: { type: String, enum: ['Morning','Evening'], required: true },
  notes: String,
});
module.exports = model('Shift', shiftSchema);