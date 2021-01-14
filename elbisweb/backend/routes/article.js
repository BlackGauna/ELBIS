const router = require('express').Router();
let Article = require('../models/article.model');

router.route('/').get((req, res) => {
    Article.find()
        .then(article => res.json(article))
        .catch(err => res.status(400).json('Error: ' + err));
});

//TODO Load methods, Views ?
//TODO Should we also get the ID ?

//Create new
router.route('/add').post((req, res) => {
    const title = req.body.title;
    const content = req.body.content;
    const status = req.body.status;
    //TODO Make sure topic works
    //TODO The other dates ?
    const topic = req.body.topic;
    const authorId = req.body.authorId;
    //TODO should publisherId and Comment be here ? Test purposes for now
    const publisherId = req.body.publisherId;
    const publisherComment = req.body.publisherComment;

    const newArticle = new Article({title, content, status, topic, authorId, publisherId, publisherComment});

    newArticle.save()
        .then(() => res.json('Article saved!'))
        .catch(err => res.status(400).json('Error: ' + err));
});

// Get per ID
router.route('/:id').get((req, res) => {
    Article.findById(req.params.id)
        .then(article => res.json(article))
        .catch(err => res.status(400).json('Error: ' + err));
});

// Delete by ID
router.route('/:id').delete((req, res) => {
    Article.findByIdAndDelete(req.params.id)
        .then(() => res.json('Article deleted.'))
        .catch(err => res.status(400).json('Error: ' + err));
});

// Update
router.route('/update/:id').post((req, res) => {
    Article.findById(req.params.id)
        .then(article => {

            article.title = req.body.title;
            article.content = req.body.content;
            article.status = req.body.status;
            //TODO Make sure topic works
            //TODO The other dates ?
            article.topic = req.body.topic;
            article.authorId = req.body.authorId;
            //TODO should publisherId and Comment be here ? Test purposes for now
            article.publisherId = req.body.publisherId;
            article.publisherComment = req.body.publisherComment;

            article.save()
                .then(() => res.json('Article updated!'))
                .catch(err => res.status(400).json('Error: ' + err));
        })
        .catch(err => res.status(400).json('Error: ' + err));
});

module.exports = router;