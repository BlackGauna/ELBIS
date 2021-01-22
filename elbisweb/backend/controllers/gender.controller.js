let Gender = require('../models/gender.model');

// Create and save a new Gender
exports.create = (req, res) => {
    // Create a gender
    const gender = new Gender({
        name: req.body.name,
    });

    // Save gender in database
    gender
        .save(gender)
        .then(data => {
            res.send(data);
        })
        .catch(err => {
            res.status(500).send({
                message:
                    err.message || "Some error occured while creating the gender."
            });
        });
};

// Retrieve all genders from the database
exports.findAll = (req, res) => {
    Gender.find()
        .then(data => {
            res.send(data)
        })
        .catch(err => {
            res.status(500).send({
                message:
                    err.message || "Some error occured while retrieving genders."
            });
        });
};

// Find a single Gender with an ID
exports.findOne = (req, res) => {
    const id = req.params.id;

    Gender.findById(id)
        .then(data => {
            if (!data)
                res.status(404).send({message: "Not found gender with id " + id});
            else res.send(data);
        })
        .catch(err => {
            res
                .status(500)
                .send({message: "Error retrieving gender with id " + id});
        });
};