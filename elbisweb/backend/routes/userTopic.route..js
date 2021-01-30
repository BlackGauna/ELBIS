const userTopic = require('../controllers/userTopic.controller');
const router = require('express').Router();

// Create a new userTopic
router.post("/", userTopic.create);

// Retrieve all userTopics
router.get("/", userTopic.findAll);

// delete a userTopic
router.delete("/:email/:topic", userTopic.delete);

//find userTopics by a specific email
router.get("/:email", userTopic.findByEmail);

module.exports = router;