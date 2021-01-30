/*
//TODOs

-------General-------

TODO MAYBE deploy the project to a free hosting server at the end ?
TODO MAYBE learn how to export it as an app to phones because its really cool

TODO Moderators and Admins can use All topics

-------DB/Backend-------

TODO check on starting routine if a file to corrosponding articles in the DB exists - if not delete the DB entry

Article
TODO make an article updateable
TODO The Html files created should have the name as topic_title_id
TODO Load by ID only works half because of the html files
TODO add expire date to for articles and then automatically archive the expired articles.
TODO Insert examples and tests to collections
TODO maybe - automatically delete empty articles?
User
TODO Birthdate in correct format

-------Main/Frontend-------

Tables
TODO Make tables sortable
TODO Show creationDate, lastEdit (access via mongoDB?) and expireDate (?)
TODO 'edit' links of artiocles do not lead to the correct path yet (should be a full path or redirect instead of a relative path)
    - User- MyArticles
    - Moderator - manageArticles
TODO 'edit' links of topics do not lead to the correct path yet (should be a full path or redirect instead of a relative path)
TODO 'manageUsers' implement accesslevels
    - moderators should just see/edit/delete users and admins should see all accounts
    - an admin can add and delete an allowed topic of users
TODO Buttons should show a modal before performing an action
    - Delete: Check if ur sure to ddelete
    - Submit: Check if ur sure to submit

SidePages
TODO finalize 'createUser'
    - Choose allowedTopics and write them correcly to the DB
TODO finalize 'editUser'
    - create static initial labels with values loaded from db
    - add "edit" button to edit any attribute with a modal
TODO finalize 'manageAccount'
    - copy "editUser" and just load the logged in User to be edited
TODO create a 'submit' component for the moderator to publish submitted articles
TODO show all articles/onepagers in state 'public' on the home site

-------Editor-------










 */