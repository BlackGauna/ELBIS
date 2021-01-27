const articles = require('../controllers/article.controller.js');
const router = require('express').Router();

// Create a new Article
router.post("/", articles.create);

// Retrieve all Articles
router.get("/", articles.findAll);

// Retrieve all Articles with by email
router.get("/:email", articles.findByEmail);

// Retrieve a single Article with id
router.get("/:id", articles.findOne);

// Update a Article with id
router.put("/:id", articles.update);

// Delete a Article with id
router.delete("/:id", articles.delete);

module.exports = router;