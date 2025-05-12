const express = require('express');
const bcrypt = require('bcryptjs');
const jwt = require('jsonwebtoken');
const User = require('../models/User');
const router = express.Router();

// Register
router.post('/register', async (req, res) => {
    const { name, email, password } = req.body;  // ✅ Remove role from request

    if (!name || !email || !password) {
        return res.status(400).json({ error: "All fields are required." });
    }

    // ✅ Assign "Employee" role by default
    const hashedPassword = await bcrypt.hash(password, 10);
    const newUser = new User({ name, email, passwordHash: hashedPassword, role: "Employee" });

    await newUser.save();
    res.status(201).json({ message: "User registered successfully", role: newUser.role });
});

router.post('/login', async (req, res) => {
    const { email, password } = req.body;
    
    // Find user in MongoDB
    const user = await User.findOne({ email });
    if (!user) {
        return res.status(400).json({ error: 'Invalid credentials' });
    }

    // Validate password
    const valid = await bcrypt.compare(password, user.passwordHash);
    if (!valid) {
        return res.status(400).json({ error: 'Invalid credentials' });
    }

    // Generate JWT token
    const token = jwt.sign(
        { userId: user._id, role: user.role },  // ✅ Include role in token payload
        process.env.JWT_SECRET,
        { expiresIn: '8h' }
    );

    // ✅ Ensure role is included in response
    res.json({ token, userId: user._id, role: user.role });
    router.get('/test', (req, res) => {
    res.json({ message: "API is working!" });
});
});
// Auth middleware (for use in other routes too)
const authMiddleware = roles => (req, res, next) => {
  const header = req.headers.authorization;
  if (!header) return res.sendStatus(401);
  const token = header.split(' ')[1];
  try {
    const payload = jwt.verify(token, process.env.JWT_SECRET);
    if (!roles.includes(payload.role)) return res.sendStatus(403);
    req.user = payload;
    next();
  } catch {
    res.sendStatus(401);
  }
};

// Get current user
router.get('/me', authMiddleware(['Admin', 'Employee']), async (req, res) => {
  const user = await User.findById(req.user.userId).select('-passwordHash');
  res.json(user);
});


module.exports = {
  router,
  authMiddleware
};
