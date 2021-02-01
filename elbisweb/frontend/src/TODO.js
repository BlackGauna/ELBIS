/*
//TODOs

-------General-------

TODO MAYBE deploy the project to a free hosting server at the end ?
TODO MAYBE learn how to export it as an app to phones because its really cool

TODO Moderators and Admins can use All topics (but they can still hav entries in userTopics - for the case they get degraded to a user)
TODO on TopicChange or TopicDelete - check userTopics and change

-------DB/Backend-------

TODO check on starting routine if a file to corrosponding articles in the DB exists - if not delete the DB entry

Article
TODO make an article updateable
TODO Load by ID only works half because of the html files
TODO add expire date to for articles and then automatically archive the expired articles.
TODO Insert examples and tests to collections
TODO maybe - automatically delete empty articles? -> delete Files and DB entry
User
TODO Birthdate in correct format

-------Main/Frontend-------

Tables
TODO Make tables sortable
TODO Show creationDate, lastEdit (access via mongoDB?) and expireDate in each table (?)
TODO 'edit' links of topics do not lead to the correct path yet (should be a full path or redirect instead of a relative path)
TODO 'manageUsers' implement accesslevels
    - an admin can add and delete an allowed topic of users

TODO Buttons should show a modal before performing an action
    - Delete: Check if ur sure to delete
    - Submit: Check if ur sure to submit

SidePages
TODO finalize 'createUser'
    - Choose allowedTopics and write them correcly to the (userTopic)DB
TODO finalize 'editUser'
    - create static initial labels with values loaded from db
    - add "edit" button to edit any attribute with a modal
     - Choose allowedTopics and write them correcly to the (userTopic)DB
TODO finalize 'manageAccount'
    - copy "editUser" and just load the logged in User to be edited
TODO create a 'submit' component for the moderator to publish submitted articles
TODO show all articles/onepagers in state 'public' on the home site

-------Editor-------










 */