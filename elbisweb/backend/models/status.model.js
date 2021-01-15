const mongoose = require('mongoose');
const Schema = mongoose.Schema;

const statusSchema = new Schema({
    name: {type: String, required:  true},
}, {
    timestamps: true,
});

Status = mongoose.model('Status', statusSchema);

module.exports = Status;