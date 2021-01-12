const mongoose = require('mongoose');

const Schema = mongoose.Schema;

const userSchema = new Schema({
    email: {type: String, required:  true},
    password: {type: String, required:  true},
    role: {type: String, required:  true},
    name: {type: String, required:  true},
    address: {type: String, required:  true},
    gender: {type: String, required:  true},
    dateOfBirth: {type: Date, required:  true},
}, {
    timestamps: true,
});

const User = mongoose.model('User', userSchema);

module.exports = User;