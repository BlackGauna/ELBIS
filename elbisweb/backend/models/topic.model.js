const mongoose = require('mongoose');
const Schema = mongoose.Schema;

const topicSchema = new Schema({
    name: {type: String, required: true, unique: true},
    parentTopic: {type: String, required: false},
}, {
    timestamps: true,
});

Topic = mongoose.model('Topic', topicSchema);

module.exports = Topic;