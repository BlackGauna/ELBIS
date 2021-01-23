const status = require('../controllers/status.controller');
const router = require('express').Router();

// Create a new Status
router.post("/", status.create);

// Retrieve all Status
router.get("/", status.findAll);

// Retrieve a single status with id
router.get("/:id", status.findOne);

// Update a Status with id
router.put("/:id", status.update);

// Delete a Status with id
router.delete("/:id", status.delete);

module.exports = router;