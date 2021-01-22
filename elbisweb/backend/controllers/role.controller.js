let Role = require('../models/role.model');

// Create and save a new role
exports.create = (req, res) => {
    // Create a role
    const role = new Role({
        name: req.body.name,
    });

    // Save Role in database
    role
        .save(role)
        .then(data => {
            res.send(data);
        })
        .catch(err => {
            res.status(500).send({
                message:
                    err.message || "Some error occured while creating the role."
            });
        });
};

// Retrieve all Roles from the database
exports.findAll = (req, res) => {
    Role.find()
        .then(data => {
            res.send(data)
        })
        .catch(err => {
            res.status(500).send({
                message:
                    err.message || "Some error occured while retrieving roles."
            });
        });
};

// Find a single Role with an ID
exports.findOne = (req, res) => {
    const id = req.params.id;

    Role.findById(id)
        .then(data => {
            if (!data)
                res.status(404).send({message: "Not found role with id " + id});
            else res.send(data);
        })
        .catch(err => {
            res
                .status(500)
                .send({message: "Error retrieving role with id " + id});
        });
};