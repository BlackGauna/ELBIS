const mongoose = require('mongoose');

const Schema = mongoose.Schema;

const userSchema = new Schema({
    email: {type: String, required:  true},
    password: {type: String, required:  true},
    role: {type: String, required:  true},
    name: {type: String, required:  true},
    address: {type: String, required:  false},
    gender: {type: String, required:  false},
    dateOfBirth: {type: Date, required:  false},
}, {
    timestamps: true,
});

const User = mongoose.model('User', userSchema);

module.exports = User;