let UserTopic = require('../models/userTopic.model');

exports.delete = (req, res) => {
    const email = req.params.email;
    const topic = req.params.topic;

    UserTopic.remove({email: email, topic: topic})
        .then(data => {
            if (!data) {
                res.status(404).send({
                    message: "No delete for userTopic with email " + email + " and topic "+ topic
                });
            } else {
                res.send({
                    message: "userTopic was deleted successfully!"
                });
            }
        })
        .catch(err => {
            res.status(500).send({
                message: "Error on delete userTopic with email " + email + " and topic "+ topic
            });
        });
}

// Create and save a new userTopic
exports.create = (req, res) => {
    // Create a topic
    const userTopic = new UserTopic({
        email: req.body.email,
        topic: req.body.topic
    });

    // Save topic in database
    userTopic
        .save(userTopic)
        .then(data => {
            res.send(data);
        })
        .catch(err => {
            res.status(500).send({
                message:
                    err.message || "Some error occured while creating the userTopic."
            });
        });
};

// Retrieve all userTopics from the database
exports.findAll = (req, res) => {
    UserTopic.find()
        .then(data => {
            res.send(data)
        })
        .catch(err => {
            res.status(500).send({
                message:
                    err.message || "Some error occured while retrieving userTopics."
            });
        });
};

// Retrieve all Usertopics by a specific email
// TODO on wrong email the DB just answers [] instead of an Error
exports.findByEmail = (req, res) => {
    const email = req.params.email;

    UserTopic.find({email: email})
        .then(data => {
            if(!data){
                res.status(404).send({message: "Not found UserTopic with email " + email});
            } else{
                res.send(data)
            }

        })
        .catch(err => {
            res
                .status(500)
                .send({message: "Error retrieving UserTopics with email " + email});
        });
};