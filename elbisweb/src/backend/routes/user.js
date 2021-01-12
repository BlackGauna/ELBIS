const router = require('express').Router();
let User = require('../models/user.model');

router.route('/').get((req, res) => {
    User.find()
        .then(user => res.json(user))
        .catch(err => res.status(400).json('Error: ' + err));
});

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

module.exports = router;