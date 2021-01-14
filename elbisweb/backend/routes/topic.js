const router = require('express').Router();
let Topic = require('../models/topic.model');

router.route('/').get((req, res) => {
    Topic.find()
        .then(topic => res.json(topic))
        .catch(err => res.status(400).json('Error: ' + err));
});

//TODO Other Methods
//TODO Should we also get the id ?

//Create new
router.route('/add').post((req, res) => {
    const name = req.body.name;
    //TODO Make sure topic works
    const parentTopic = req.body.parentTopic;

    const newTopic = new Topic({name, parentTopic});

    newTopic.save()
        .then(() => res.json('Topic added!'))
        .catch(err => res.status(400).json('Error: ' + err));
});

// Get per ID
router.route('/:id').get((req, res) => {
    Topic.findById(req.params.id)
        .then(topic => res.json(topic))
        .catch(err => res.status(400).json('Error: ' + err));
});

// Delete by ID
router.route('/:id').delete((req, res) => {
    Topic.findByIdAndDelete(req.params.id)
        .then(() => res.json('Topic deleted.'))
        .catch(err => res.status(400).json('Error: ' + err));
});

// Update
router.route('/update/:id').post((req, res) => {
    Topic.findById(req.params.id)
        .then(topic => {

            topic.name = req.body.name;
            //TODO Make sure topic works
            topic.parentTopic = req.body.parentTopic;

            topic.save()
                .then(() => res.json('Topic updated!'))
                .catch(err => res.status(400).json('Error: ' + err));
        })
        .catch(err => res.status(400).json('Error: ' + err));
});

module.exports = router;