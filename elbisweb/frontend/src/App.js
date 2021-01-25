import React from 'react';
import {BrowserRouter as Router, Redirect, Route} from "react-router-dom";
import {observer} from 'mobx-react';
import "bootstrap/dist/css/bootstrap.min.css";
import loggedUser from './session/loggedUser';
import './ELBISWeb.css';
import logo from './resources/ELBIS_logo/ELBIS_Ausgeschrieben.png';

//##########Component imports##########
import NavBar from "./components/ELBIS_navbar.component";
import ELBISweb from "./components/index.component";
import userView from "./components/user/userView.component";
import moderationView from "./components/moderation/moderationView.component";
import createUser from "./components/moderation/moderation_createUser.component";
import administrationView from "./components/administration/administrationView.component.js";
import loginView from "./components/loginView.component";
import CreateArticle from "./components/user/CreateArticle.component";
import createTopic from "./components/administration/administration_createTopic.component";
import allArticlesList from "./components/moderation/moderation_articleList.component";
import editUser from "./components/moderation/moderation_editUser.component";

//##########App start##########
//TODO check which type of user is logged in before redirecting to moderation or administration
class App extends React.Component {
    render() {
        if (loggedUser.loading) {
            return (
                <div className="app">
                    <div className="container">
                        <img src={logo} alt="ELBIS"></img> Is loading...
                    </div>
                </div>
            )
        } else {
            if (loggedUser.isLoggedIn||!loggedUser.isLoggedIn) {
                return (
                    <div className="app">
                        <Router>
                            <Route path="/" component={NavBar}/>
                            <br/>
                            <Route exact path="/login/hauptseite" component={ELBISweb}/>
                            <Route exact path="/login/artikelverwaltung" component={userView}/>
                            <Route exact path="/login/moderation" component={moderationView}/>
                            <Route exact path="/login/nutzerErstellen" component={createUser}/>
                            <Route exact path="/login/administration" component={administrationView}/>
                            <Route exact path="/login/edit" component={CreateArticle} />
                            <Route exact path="/login/edit/:id" component={CreateArticle} />
                            <Route exact path="/login/bereichErstellen" component={createTopic}/>
                            <Route exact path="/login/articles" component={allArticlesList}/>
                            <Route exact path="/login/editUser/:id" component={editUser}/>
                        </Router>
                    </div>
                )
            } else {
                return (
                    <div className="app">
                        <Router>
                            <Route path="/" component={loginView}/>
                        </Router>
                    </div>
                )
            }
        }
        /*
        return (
            <Router>
                <Route exact path="/" component={loginView}/>
                <Route path="/login" component={NavBar}/>
                <br/>
                <Route exact path="/login/hauptseite" component={ELBISweb}/>
                <Route exact path="/login/artikelverwaltung" component={userView}/>
                <Route exact path="/login/moderation" component={moderationView}/>
                <Route exact path="/login/nutzerErstellen" component={createUser}/>
                <Route exact path="/login/administration" component={administrationView}/>
                <Route exact path="/login/artikelErstellen" component={CreateArticle}/>
                <Route exact path="/login/bereichErstellen" component={createTopic}/>
                <Route exact path="/login/articles" component={allArticlesList}/>
                <Route exact path="/login/editUser/:id" component={editUser}/>
            </Router>
        ); */
    }

//##########Mount method with login##########
    async componentDidMount() {
        try {
            let res = await fetch('/isLoggedIn', {
                method: 'post',
                headers: {
                    'Accept': 'application/json',
                    'Content-Type': 'application/json'
                }
            });
            let result = await res.json();

            if (result && result.success) {
                loggedUser.loading = false;
                loggedUser.isLoggedIn = true;
                loggedUser.eMail = result.eMail;
                loggedUser.role = result.role;
            } else {
                loggedUser.loading = false;
                loggedUser.isLoggedIn = false;
            }
        } catch (e) {
            loggedUser.loading = false;
            loggedUser.isLoggedIn = false;
            console.log("Login Error!");
        }
    }
}

export default observer(App);