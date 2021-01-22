const roles = require('../controllers/role.controller');
const router = require('express').Router();

// Create a new Role
router.post("/", roles.create);

// Retrieve all Roles
router.get("/", roles.findAll);

// Retrieve a single Role with id
router.get("/:id", roles.findOne);

module.exports = router;