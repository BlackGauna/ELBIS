let User = require('../models/user.model');
const bcrypt = require('bcryptjs');
// Create and save a new User
exports.create = async (req, res) => {
    // Validate request
    if (!req.body.email) {
        res.status(400).send({message: "Content can not be empty!"});
        return;
    }

    // Create a user
    const user = new User({
        email: req.body.email,
        password: req.body.password,
        role: req.body.role,
        fName: req.body.fName,
        lName: req.body.lName,
        street: req.body.street,
        hNumber: req.body.hNumber,
        plz: req.body.plz,
        city: req.body.city,
        gender: req.body.gender,
        dateOfBirth: req.body.dateOfBirth,
        // allowedTopics: req.body.allowedTopics
    });

    // Save user in database
    user
        .save(user)
        .then(data => {
            res.send(data);
        })
        .catch(err => {
            res.status(500).send({
                message:
                    err.message || "Some error occured while creating the user."
            });
        });
};

// Retrieve all Users from the database
exports.findAll = (req, res) => {
    User.find()
        .then(data => {
            res.send(data)
        })
        .catch(err => {
            res.status(500).send({
                message:
                err.message || "Some error occured while retrieving users."
            });
        });
};

// Find a single User with an ID
exports.findOne = (req, res) => {
    const id = req.params.id;

    User.findById(id)
        .then(data => {
            if (!data)
                res.status(404).send({message: "Not found user with id " + id});
            else res.send(data);
        })
        .catch(err => {
            res
                .status(500)
                .send({message: "Error retrieving user with id " + id});
        });
};

//Authenticate an User by email and password
exports.authOne = (req, res) => {
    const postemail = req.params.email;
    const postpassword = req.params.password;

    User.findOne({email: postemail})
        .then(data => {
            if (!data) {
                res.status(404).send({success: false,message: "Not found user with email " + postemail});
            }
            else {
                if (bcrypt.compareSync(postpassword, data.password)) {
                    res.send({success: true,data});
                } else {
                    res.status(404).send({success: false,message: "No password match found for " + postemail});
                }
            }
        })
        .catch(err => {
            res
                .status(500)
                .send({success: false,message: "Error retrieving user with email " + postemail});
        });
};

// Update a user by the id
exports.update = async (req, res) => {
    if (!req.body){
        return res.status(400).send({
            message: "Data to update can not be empty!"
        });
    }

    const id = req.params.id;
    if(!req.body.password){
        delete req.body["password"];
    }else{
        req.body.password = await bcrypt.hash(req.body.password, 10);
    }
    
    User.findByIdAndUpdate(id, req.body, {useFindAndModify: false})
        .then(data => {
            if (!data){
                res.status(404).send({
                    message: "Cannot update User with id = " + id + ". Maybe User was not found!"
                });
            } else
                 res.send({message: "User was updated successfully."});
        })
        .catch(err => {
            res.status(500).send({
                message: "Error updating User with id " + id
            });
        });
};

// Delete User with ID
exports.delete = (req, res) => {
    const id = req.params.id;

    User.findByIdAndRemove(id, {useFindAndModify: false})
        .then(data => {
            if (!data) {
                res.status(404).send({
                    message: "Cannot delete User with id " + id + ". Maybe User was not found"
                });
            } else {
                res.send({
                    message: "User was deleted successfully!"
                });
            }
        })
        .catch(err => {
            res.status(500).send({
                message: "Could not delete User with id " + id
            });
        });
}