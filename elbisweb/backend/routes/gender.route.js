const genders = require('../controllers/gender.controller');
const router = require('express').Router();

// Create a new Gender
router.post("/", genders.create);

// Retrieve all Genders
router.get("/", genders.findAll);

// Retrieve a single Gender with id
router.get("/:id", genders.findOne);

module.exports = router;