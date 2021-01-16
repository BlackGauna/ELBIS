const mongoose = require('mongoose');
const Schema = mongoose.Schema;

const topicSchema = new Schema({
    //TODO Limits to variables (Trim,minlength etc)
    name: {type: String, required:  true},
    //TODO make sure the topicSchema import works
    parentTopic: {type: mongoose.Schema.Types.ObjectId, ref: 'Topic', required:  false},
}, {
    timestamps: true,
});

Topic = mongoose.model('Topic', topicSchema);

module.exports = Topic;