const router = require('express').Router();
let Role = require('../models/role.model');

// Get  all
router.route('/').get((req, res) => {
    Role.find()
        .then(role => res.json(role))
        .catch(err => res.status(400).json('Error: ' + err));
});

// Get per ID
router.route('/:id').get((req, res) => {
    Role.findById(req.params.id)
        .then(user => res.json(role))
        .catch(err => res.status(400).json('Error: ' + err));
});

module.exports = router;