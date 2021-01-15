const router = require('express').Router();
let Status = require('../models/status.model');

// Get  all
router.route('/').get((req, res) => {
    Status.find()
        .then(status => res.json(status))
        .catch(err => res.status(400).json('Error: ' + err));
});

// Get per ID
router.route('/:id').get((req, res) => {
    Status.findById(req.params.id)
        .then(user => res.json(status))
        .catch(err => res.status(400).json('Error: ' + err));
});

module.exports = router;