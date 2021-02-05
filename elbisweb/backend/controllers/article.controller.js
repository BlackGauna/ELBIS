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
    const filepath='articles/'+req.body.topic+"_"+req.body.title+'_'+Date.now() +'.html';
    fs.writeFile(filepath, req.body.content, (err)=>{
        if (err) throw err;

        // success
        console.log("File saved!");
    });

    // Create a Article
    const article = new Article({
        title: req.body.title,
        path: filepath, // save .html file path
        expireDate: req.body.expireDate,
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
            console.log(err);
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
            // convert data array to editable JSON array
            let mod= JSON.parse(JSON.stringify(data));

            // find content of articles and append into content field
            mod.forEach(article=>{

                try {
                    const content=fs.readFileSync(article.path, 'utf8');
                    article.content=content;
                }catch {
                    article.content="Article not found.";
                }

            })
            //console.log(mod);

            res.send(mod)
        })
        .catch(err => {
            res.status(500).send({
                message:
                    err.message || "Some error occured while retrieving Articles."
            });
        });
};

// Retrieve all Articles with by email
exports.findByStatus = (req, res) => {
    const status = req.params.status;

    console.log("DB searching for Entries with Status: "+status);
    Article.find({status: status})
        .then(data => {
            if((!data) || (!data.length)){
                res.status(404).send({message: "Not found Article with status " + status});
            } else{
                res.send(data)
            }

        })
        .catch(err => {
            res
                .status(500)
                .send({message: "Error retrieving Article with status " + status});
        });
};

// Retrieve all Articles by a specific email
exports.findByEmail = (req, res) => {
    const email = req.params.email;

    Article.find({author: email})
        .then(data => {
            if((!data) || (!data.length)){
                res.status(404).send({message: "Not found Article with email " + email});
            } else{
                res.send(data)
            }

        })
        .catch(err => {
                res
                .status(500)
                    .send({message: "Error retrieving Article with email " + email});
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
//TODO Cannot read property 'substring' of undefined error.
exports.update = (req, res) => {
    if (!req.body){
        return res.status(400).send({
            message: "Data to update can not be empty!"
        });
    }

    const id = req.params.id;

    let html;
    if(req.body.content){
        html= req.body.content;
    }


    // get old title by parsing path of req
    const oldtitle = req.body.path.substring(req.body.path.indexOf('_')+1, req.body.path.lastIndexOf('_'));
    console.log("old title: "+ oldtitle);
    console.log("new title: "+req.body.title)

    // get old topic
    const oldTopic = req.body.path.substring(req.body.path.indexOf('/')+1, req.body.path.indexOf('_'));
    console.log("old topic: "+ oldTopic);
    console.log("new topic: "+ req.body.topic);
    console.log(oldtitle!==req.body.title ||oldTopic!==req.body.topic);


    // delete old file if title changed
    if(oldtitle!==req.body.title || oldTopic!==req.body.topic)
    {
        fs.unlink(req.body.path, function (err) {
            if (err) throw err;

            //success
            console.log("Old file deleted");
        })

        const newPath=req.body.path.substring(0,req.body.path.indexOf('/')+1)
            + req.body.topic+ '_'
            +req.body.title + '_' + req.body.id
            +'.html';

        console.log('new path: '+ newPath);

        // overwrite old path with new
        req.body.path=newPath;
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
                if(html){
                    fs.writeFile(data.path, html, (err)=>{
                        if (err) throw err;

                        // success
                        console.log("File updated!");
                    });
                }


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
                fs.unlink(data.path, (err) => {
                    if (err) {
                        console.log("failed to delete local file of the article:"+err);
                    } else {
                        console.log('successfully deleted local file of the article');                                
                    }
            });
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