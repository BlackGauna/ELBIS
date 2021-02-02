const mongoose = require('mongoose');
const bcrypt = require('bcryptjs');
const Schema = mongoose.Schema;

const userSchema = new Schema({
    email: {type: String, required: true, unique: true, trim: true, minlength: 5},
    password: {type: String, required: true},
    role: {type: String, required: true},
    fName: {type: String, required: true},
    lName: {type: String, required: true},
    street: {type: String, required: true},
    hNumber: {type: String, required: true},
    plz: {type: String, required: true},
    city: {type: String, required: true},
    gender: {type: String, required: true},
    dateOfBirth: {type: Date, required: true},
}, {
    timestamps: true,
});

userSchema.pre("save", async function (next) {
    const user = this;

    if (user.isModified("password")) {
        user.password = await bcrypt.hash(user.password, 10);
    }
});

const User = mongoose.model('User', userSchema);

module.exports = User;