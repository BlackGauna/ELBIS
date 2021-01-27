const sessions = require('../controllers/session.controller');
const router = require('express').Router();

// Create a new Session
router.post("/", sessions.create);

// check if session for email exists
router.get("/check/:token/:email/:role", sessions.checkSession);

// Delete a Session with email
router.delete("/:email", sessions.delete);

// Retrieve all sessions
router.get("/", sessions.findAll);


module.exports = router;