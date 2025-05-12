require('dotenv').config();
require('./db');

const express = require('express');
const app = express();
app.use(express.json());


const { router: usersRouter, authMiddleware } = require('./routes/users');

// Set up routes
app.use('/api/users', usersRouter); 
app.use('/api/inventory', authMiddleware(['Admin']), require('./routes/inventory'));
app.use('/api/shifts', authMiddleware(['Admin']), require('./routes/shifts'));
app.use('/api/sales', authMiddleware(['Admin', 'Employee']), require('./routes/sales'));

const PORT = process.env.PORT || 5000;
app.listen(PORT,"0.0.0.0", () => console.log(`🚀 Server running on port ${PORT}`));
