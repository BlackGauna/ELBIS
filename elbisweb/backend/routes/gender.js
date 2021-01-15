const router = require('express').Router();
let Gender = require('../models/gender.model');

// Get  all
router.route('/').get((req, res) => {
    Gender.find()
        .then(gender => res.json(gender))
        .catch(err => res.status(400).json('Error: ' + err));
});

// Get per ID
router.route('/:id').get((req, res) => {
    Gender.findById(req.params.id)
        .then(user => res.json(gender))
        .catch(err => res.status(400).json('Error: ' + err));
});


module.exports = router;