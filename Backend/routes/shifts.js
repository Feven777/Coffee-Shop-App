const express = require('express');
const Shift = require('../models/Shift');
const router = express.Router();

// Create
router.post('/', async (req, res) => {
  const shift = new Shift(req.body);
  await shift.save();
  res.status(201).json(shift);
});

// Read all
router.get('/', async (_, res) => {
  const shifts = await Shift.find().populate('employeeId', 'name');
  res.json(shifts);
});

// Update
router.put('/:id', async (req, res) => {
  const updated = await Shift.findByIdAndUpdate(req.params.id, req.body, { new: true });
  res.json(updated);
});

// Delete
router.delete('/:id', async (req, res) => {
  await Shift.findByIdAndDelete(req.params.id);
  res.sendStatus(204);
});

module.exports = router;