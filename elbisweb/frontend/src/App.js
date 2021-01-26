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
import UserDataService from "./services/user.service";

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
            if (loggedUser.isLoggedIn || !loggedUser.isLoggedIn) {
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
                            <Route exact path="/login/edit" component={CreateArticle}/>
                            <Route exact path="/login/edit/:id" component={CreateArticle}/>
                            <Route exact path="/login/bereichErstellen" component={createTopic}/>
                            <Route exact path="/login/articles" component={allArticlesList}/>
                            <Route exact path="/login/editUser/:id" component={editUser}/>
                        </Router>
                    </div>
                )
            } else if (!loggedUser.isLoggedIn) {
                return (
                    <div className="app">
                        <Router>
                            <Route path="/" component={loginView}/>
                        </Router>
                    </div>
                )
            }
        }
    }

//##########Mount method with login##########
    async componentDidMount() {
        if (loggedUser.loading) {
            UserDataService.authenticate(loggedUser.email, loggedUser.password).then(res => {
                //auth successfull
                if (res.data.success) {
                    loggedUser.loading = false;
                    loggedUser.isLoggedIn = true;
                    //auth failed
                } else if (res.data.success === false) {
                    loggedUser.loading = false;
                    loggedUser.isLoggedIn = false;
                }
            })
                .catch((error) => {
                    loggedUser.loading = false;
                    loggedUser.isLoggedIn = false;
                    console.log(error);
                })
        }
    }
}

export default observer(App);