/*
//TODOs

-------General-------

TODO MAYBE deploy the project to a free hosting server at the end ?
TODO MAYBE learn how to export it as an app to phones because its really cool

TODO Moderators and Admins can use All topics (but they can still hav entries in userTopics - for the case they get degraded to a user)

-------DB/Backend-------
TODO on Topic-Name-Change (topicUpdate) or TopicDelete - check userTopics and change/delete related topicnames
TODO on User-Email-Change (userUpdate) or UserDelete - check UserTopics and change/delete related useremails

TODO check on starting routine if a file to articles in the DB exists - if not delete the DB entry

Article
TODO make an article updateable
TODO add expire date to for articles and then automatically archive the expired articles.
TODO maybe - automatically delete empty articles? -> delete Files and DB entry
User




IMPORTANT

TODO update topic name updates every single article topic
TODO By delete pdates every article and user topic that the email is related to author and publisher
TODO When a topic is deleted, every article that belongs to it should revert the topic to Default





-------Main/Frontend-------

Tables
TODO 'edit' links of topics do not lead to the correct path yet (should be a full path or redirect instead of a relative path)
TODO 'manageUsers' implement accesslevels
    - an admin can add and delete an allowed topic of users

TODO Buttons should show a modal before performing an action
    - Delete: Check if ur sure to delete
    - Submit: Check if ur sure to submit

TODO Handle Text overflow on cells

TODO Make a public/submitted article revertable (unpublish) and the comment editable

SidePages
TODO finalize 'createUser'
    - Choose allowedTopics and write them correcly to the (userTopic)DB
TODO finalize 'editUser'
     - passwordchange, DateOfBirth
     - make UserTopics editable
TODO create a 'submit' component for the moderator to publish submitted articles
TODO show all articles/onepagers in state 'public' on the home site
TODO make a public homepage for non-logged Users

-------Editor-------
TODO implement OnePagers
TODO show OnePagers on Homepage

 */