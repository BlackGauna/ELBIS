const users = require('../controllers/user.controller.js');
const router = require('express').Router();

// Create a new User
router.post("/", users.create);

// Retrieve all Users
router.get("/", users.findAll);

// Retrieve a single User with id
router.get("/:id", users.findOne);

//Authenticate a User by email and password
router.get("/authenticate/:email/:password", users.authOne);

// Update a User with id
router.put("/:id", users.update);

// Delete a User with id
router.delete("/:id", users.delete);

module.exports = router;