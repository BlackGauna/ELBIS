let Article = require('../models/article.model');
const fs= require('fs');

// Create and save a new Article
exports.create = (req, res) => {
    // Validate request
    if (!req.body.title) {
        res.status(400).send({message: "Content can not be empty!"});
        return;
    }

    // save html content to file
    const filename='articles/'+req.body.topic+"-"+req.body.title+'.html';
    fs.writeFile(filename, req.body.content, (err)=>{
        if (err) throw err;

        // success
        console.log("File saved!");
    });

    // Create a Article
    const article = new Article({
        title: req.body.title,
        content: filename, // save .html file path
        status: req.body.status,
        topic: req.body.topic,
        author: req.body.author,
        publisher: req.body.publisher,
        publisherComment: req.body.publisherComment
    });

    // Save Article in database
    article
        .save()
        .then(article => {
            res.json(article);
        })
        .catch(err => {
            console.log("Article create Error");
            res.status(500).send({

                message:
                    err.message || "Some error occured while creating the Article."
            });
        });
};

// Retrieve all Articles from the database
exports.findAll = (req, res) => {
    Article.find()
        .then(data => {
            res.send(data)
        })
        .catch(err => {
            res.status(500).send({
                message:
                    err.message || "Some error occured while retrieving Articles."
            });
        });
};

// Find a single Article with an ID
exports.findOne = (req, res) => {
    const id = req.params.id;

    console.log(id);
    Article.findById(id)
        .then(data => {
            if (!data){
                res.status(404).send({message: "Not found Article with id " + id});
            }
            else {

                // get real content from saved html file
                const filename=data.content;
                const article=data;
                article.content=fs.readFileSync(filename);

                res.send(article);
            }
        })
        .catch(err => {
            res
                .status(500)
                .send({message: "Error retrieving Article with id " + id});
        });
};

// Update a Article by the id
exports.update = (req, res) => {
    if (!req.body){
        return res.status(400).send({
            message: "Data to update can not be empty!"
        });
    }

    const id = req.params.id;
    const html= req.body.content;

    delete req.body["content"];
    console.log("deleted");
    console.log(req.body);

    Article.findByIdAndUpdate(id, req.body, {useFindAndModify: false, new:true})
        .then(data => {
            if (!data){
                console.log("fial");
                res.status(404).send({
                    message: "Cannot update Article with id = " + id + ". Maybe Article was not found!"
                });
            } else {
                console.log("oath: "+data.content);
                fs.writeFile(data.content, html, (err)=>{
                    if (err) throw err;

                    // success
                    console.log("File updated!");
                });

                console.log(data);
                res.send(data);
            }
        })
        .catch(err => {
            res.status(500).send({
                message: "Error updating Article with id " + id
            });
        });
};

// Delete Article with ID
exports.delete = (req, res) => {
    const id = req.params.id;

    Article.findByIdAndRemove(id, {useFindAndModify: false})
        .then(data => {
            if (!data) {
                res.status(404).send({
                    message: "Cannot delete Article with id " + id + ". Maybe Article was not found"
                });
            } else {
                res.send({
                    message: "Article was deleted successfully!"
                });
            }
        })
        .catch(err => {
            res.status(500).send({
                message: "Could not delete Article with id " + id
            });
        });
}