const { Schema, model } = require('mongoose');
const userSchema = new Schema({
  name: { type: String, required: true },
  email: { type: String, required: true, unique: true },
  passwordHash: { type: String, required: true },
  role: { type: String, enum: ['Admin','Employee'], default: 'Employee' },
});
module.exports = model('User', userSchema);