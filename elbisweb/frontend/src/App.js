import React from 'react';
import {BrowserRouter as Router, Route} from "react-router-dom";
import "bootstrap/dist/css/bootstrap.min.css";
import'./ELBISWeb.css';

//##########Component imports##########
import NavBar from "./components/navbar.component";
import ELBISweb from "./components/ELBISweb.component";
import userView from "./components/user/userView.component";
import moderationView from "./components/moderation/moderationView.component";
import createUser from "./components/moderation/moderation_createUser.component";
import administrationView from "./components/administration/administrationView.component.js";
import loginView from "./components/loginView.component";
import CreateArticle from "./components/user/CreateArticle.component";
import createTopic from "./components/administration/administration_createTopic.component";

//##########App start##########
//TODO check which type of user is logged in before redirecting to moderation or administration
function App(){
  return (
      <Router>
          <Route exact path="/" component={loginView} />
          <Route path="/login" component={NavBar} />
          <br/>
          <Route exact path="/login/hauptseite" component={ELBISweb} />
          <Route exact path="/login/artikelverwaltung" component={userView} />
          <Route exact path="/login/moderation" component={moderationView} />
          <Route exact path="/login/nutzerErstellen" component={createUser} />
          <Route exact path="/login/administration" component={administrationView} />
          <Route exact path="/login/createArticle" component={CreateArticle} />
          <Route exact path="/login/createTopic" component={createTopic}/>

      </Router>
  );
}
export default App;