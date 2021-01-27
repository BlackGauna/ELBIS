let Session = require('../models/session.model');

// Create and save a new Session
exports.create = (req, res) => {
    // Create a session
    const session = new Session({
        token: req.body.token,
        userid: req.body.userid,
        email: req.body.email,
        role: req.body.role,
    });

    // Save session in database
    session
        .save(session)
        .then(data => {
            res.send(data);
        })
        .catch(err => {
            res.status(500).send({
                message:
                    err.message || "Couldn't save session."
            });
        });
};

// Delete Session with Email
exports.delete = (req, res) => {
    const delemail = req.params.email;

    Session.remove({email: delemail})
        .then(data => {
            if (!data) {
                res.status(404).send({
                    message: "Cannot delete Session with email " + email + ". Maybe Session was not found"
                });
            } else {
                res.send({
                    message: "Session was deleted successfully!"
                });
            }
        })
        .catch(err => {
            res.status(500).send({
                message: "Could not delete Session with email " + email
            });
        });
}

exports.checkSession = (req, res) => {
    const posttoken = req.params.token;
    const postemail = req.params.email;
    const postrole = req.params.role;

    Session.findOne({token: posttoken, email: postemail, role: postrole})
        .then(data => {
            if (!data) {
                res.status(404).send({existing: false,message: "Not session found with Email " + postemail});
            }
            else {
                if (posttoken === data.token) {
                    res.send({existing: true,data});
                } else {
                    res.status(404).send({existing: false,message: "Bad Token for " + postemail});
                }
            }
        })
        .catch(err => {
            res
                .status(500)
                .send({existing: false,message: "Error checking token for " + postemail});
        });
};
// Retrieve all Sessions from the database
exports.findAll = (req, res) => {
    Session.find()
        .then(data => {
            res.send(data)
        })
        .catch(err => {
            res.status(500).send({
                message:
                    err.message || "Some error occured while retrieving sessions."
            });
        });
};