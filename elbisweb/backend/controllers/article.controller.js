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
    const filepath='articles/'+req.body.topic+"_"+req.body.title+'.html';
    fs.writeFile(filepath, req.body.content, (err)=>{
        if (err) throw err;

        // success
        console.log("File saved!");
    });

    // Create a Article
    const article = new Article({
        title: req.body.title,
        path: filepath, // save .html file path
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
                const filename=data.path;

                const article=data;

                const content=fs.readFileSync(filename, 'utf8');

                res.json({article, content});
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

    // get old title by parsing path of req
    const oldtitle = req.body.path.substring(req.body.path.indexOf('_')+1, req.body.path.lastIndexOf('.'));
    console.log("old: "+ oldtitle);


    // overwrite path field to new path
    if(oldtitle!==req.body.title)
    {
        fs.unlink(req.body.path, function (err) {
            if (err) throw err;

            //success
            console.log("Old file deleted");
        })

        const newpath=req.body.path.substring(0,req.body.path.indexOf('_')+1)
            +req.body.title
            +'.html';
        console.log('new path: '+ newpath);

        req.body.path=newpath;
    }


    // delete fields not wanted in db
    delete req.body["content"];
    // console.log("deleted");

    Article.findByIdAndUpdate(id, req.body, {useFindAndModify: false, new:true})
        .then(data => {
            if (!data){
                res.status(404).send({
                    message: "Cannot update Article with id = " + id + ". Maybe Article was not found!"
                });
            } else {
                console.log("path: "+data.path);
                // update local html file with article's current content
                fs.writeFile(data.path, html, (err)=>{
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

    //TODO: also delete html file of article
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