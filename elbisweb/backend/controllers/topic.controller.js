let Topic = require('../models/topic.model');
const Article = require("../models/article.model");
const UserTopic = require("../models/userTopic.model");

// Create and save a new Topic
exports.create = (req, res) => {
    // Validate request
    if (!req.body.name) {
        res.status(400).send({message: "Content can not be empty!"});
        return;
    }

    // Create a topic
    const topic = new Topic({
        name: req.body.name,
        parentTopic: req.body.parentTopic
    });

    // Save topic in database
    topic
        .save(topic)
        .then(data => {
            res.send(data);
        })
        .catch(err => {
            res.status(500).send({
                message:
                    err.message || "Some error occured while creating the topic."
            });
        });
};

// Retrieve all topics from the database
exports.findAll = (req, res) => {
    Topic.find()
        .then(data => {
            res.send(data)
        })
        .catch(err => {
            res.status(500).send({
                message:
                    err.message || "Some error occured while retrieving topics."
            });
        });
};

// Find a single topic with an ID
exports.findOne = (req, res) => {
    const id = req.params.id;

    Topic.findById(id)
        .then(data => {
            if (!data)
                res.status(404).send({message: "Not found topic with id " + id});
            else res.send(data);
        })
        .catch(err => {
            res
                .status(500)
                .send({message: "Error retrieving topic with id " + id});
        });
};

// Update a topic by the id
exports.update = (req, res) => {
    if (!req.body){
        return res.status(400).send({
            message: "Data to update can not be empty!"
        });
    }
    const id = req.params.id;

    Topic.findByIdAndUpdate(id, req.body, {useFindAndModify: false})
        .then(data => {
            if (!data){
                res.status(404).send({
                    message: "Cannot update topic with id = " + id + ". Maybe topic was not found!"
                });
            } else {
                if(req.body.name){
                    Article.updateMany({"topic":data.name},{"$set":{"topic":req.body.name}},{"multi":true},(err, writeResult) => {});
                    UserTopic.updateMany({"topic":data.name},{"$set":{"topic":req.body.name}},{"multi":true},(err, writeResult) => {});
                }
                res.send({message: "Topic was updated successfully."});
            }
        })
        .catch(err => {
            res.status(500).send({
                message: "Error updating Topic with id " + id
            });
        });
};

// Delete topic with ID
exports.delete = (req, res) => {
    const id = req.params.id;

    Topic.findByIdAndRemove(id, {useFindAndModify: false})
        .then(data => {
            if (!data) {
                res.status(404).send({
                    message: "Cannot delete Topic with id " + id + ". Maybe Topic was not found"
                });
            } else {
                Article.updateMany({"topic":data.name},{"$set":{"topic":data.parentTopic}},{"multi":true},(err, writeResult) => {});
                UserTopic.deleteMany({"topic":data.name},{"multi":true},(err, writeResult) => {});
                res.send({
                    message: "Topic was deleted successfully!"
                });
            }
        })
        .catch(err => {
            res.status(500).send({
                message: "Could not delete Topic with id " + id
            });
        });
}