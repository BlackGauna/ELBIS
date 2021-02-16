let Status = require('../models/status.model');

// Create and save a new status
exports.create = (req, res) => {
    // Validate request
    if (!req.body.name) {
        res.status(400).send({message: "Name can not be empty!"});
        return;
    }

    // Create a status
    const status = new Status({
        name: req.body.name,
    });

    // Save status in database
    status
        .save(status)
        .then(data => {
            res.send(data);
        })
        .catch(err => {
            res.status(500).send({
                message:
                    err.message || "Some error occured while creating the status."
            });
        });
};

// Retrieve all Users from the database
exports.findAll = (req, res) => {
    Status.find()
        .then(data => {
            res.send(data)
        })
        .catch(err => {
            res.status(500).send({
                message:
                    err.message || "Some error occured while retrieving status."
            });
        });
};

// Find a single User with an ID
exports.findOne = (req, res) => {
    const id = req.params.id;

    Status.findById(id)
        .then(data => {
            if (!data)
                res.status(404).send({message: "Not found status with id " + id});
            else res.send(data);
        })
        .catch(() => {
            res
                .status(500)
                .send({message: "Error retrieving status with id " + id});
        });
};

// Update a user by the id
exports.update = (req, res) => {
    if (!req.body){
        return res.status(400).send({
            message: "Data to update can not be empty!"
        });
    }

    const id = req.params.id;

    Status.findByIdAndUpdate(id, req.body, {useFindAndModify: false})
        .then(data => {
            if (!data){
                res.status(404).send({
                    message: "Cannot update status with id = " + id + ". Maybe status was not found!"
                });
            } else res.send({message: "status was updated successfully."});
        })
        .catch(() => {
            res.status(500).send({
                message: "Error updating status with id " + id
            });
        });
};

// Delete User with ID
exports.delete = (req, res) => {
    const id = req.params.id;

    Status.findByIdAndRemove(id, {useFindAndModify: false})
        .then(data => {
            if (!data) {
                res.status(404).send({
                    message: "Cannot delete status with id " + id + ". Maybe status was not found"
                });
            } else {
                res.send({
                    message: "status was deleted successfully!"
                });
            }
        })
        .catch(() => {
            res.status(500).send({
                message: "Could not delete status with id " + id
            });
        });
}