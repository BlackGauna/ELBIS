const router = require('express').Router();
let User = require('../models/user.model');

//TODO Other methods
//TODO Should we also get the ID ?

// Get all
router.route('/').get((req, res) => {
    User.find()
        .then(user => res.json(user))
        .catch(err => res.status(400).json('Error: ' + err));
});

// Add
router.route('/add').post((req, res) => {
    const email = req.body.email;
    const password = req.body.password;
    const role = req.body.role;
    const name = req.body.name;
    const address = req.body.address;
    const gender = req.body.gender;
    const dateOfBirth = req.body.dateOfBirth;

    const newUser = new User({email, password, role, name, address, gender, dateOfBirth});

    newUser.save()
        .then(() => res.json('User added!'))
        .catch(err => res.status(400).json('Error: ' + err));
});

// Get per ID
router.route('/:id').get((req, res) => {
    User.findById(req.params.id)
        .then(user => res.json(user))
        .catch(err => res.status(400).json('Error: ' + err));
});

// Delete
router.route('/:id').delete((req, res) => {
    User.findByIdAndDelete(req.params.id)
        .then(() => res.json('User deleted.'))
        .catch(err => res.status(400).json('Error: ' + err));
});

// Update
router.route('/update/:id').post((req, res) => {
    User.findById(req.params.id)
        .then(user => {
            user.email = req.body.email;
            user.password = req.body.password;
            user.role = req.body.role;
            user.name = req.body.name;
            user.address = req.body.address;
            user.gender = req.body.gender;
            user.dateOfBirth = req.body.dateOfBirth;

            user.save()
                .then(() => res.json('User updated!'))
                .catch(err => res.status(400).json('Error: ' + err));
        })
        .catch(err => res.status(400).json('Error: ' + err));
});

module.exports = router;