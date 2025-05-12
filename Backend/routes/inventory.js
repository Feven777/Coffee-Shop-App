const express = require('express');
const Inventory = require('../models/Inventory');
const router = express.Router();

// Create
router.post('/', async (req, res) => {
  const item = new Inventory(req.body);
  await item.save();
  res.status(201).json(item);
});

// Read all
router.get('/', async (_, res) => {
  const items = await Inventory.find();
  res.json(items);
});

// Update
router.put('/:id', async (req, res) => {
  const updated = await Inventory.findByIdAndUpdate(req.params.id, req.body, { new: true });
  res.json(updated);
});

// Delete
router.delete('/:id', async (req, res) => {
  await Inventory.findByIdAndDelete(req.params.id);
  res.sendStatus(204);
});

module.exports = router;