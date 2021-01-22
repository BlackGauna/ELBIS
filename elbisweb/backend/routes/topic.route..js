const topics = require('../controllers/topic.controller');
const router = require('express').Router();

// Create a new Topic
router.post("/", topics.create);

// Retrieve all Topics
router.get("/", topics.findAll);

// Retrieve a single Topic with id
router.get("/:id", topics.findOne);

// Update a Topic with id
router.put("/:id", topics.update);

// Delete a Topic with id
router.delete("/:id", topics.delete);

module.exports = router;