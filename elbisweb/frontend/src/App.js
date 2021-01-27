import React from 'react';
import {BrowserRouter as Router, Redirect, Route} from "react-router-dom";
import {observer} from 'mobx-react';
import "bootstrap/dist/css/bootstrap.min.css";
import loggedUser from './session/loggedUser';
import './ELBISWeb.css';
import logo from './resources/ELBIS_logo/ELBIS_Ausgeschrieben.png';
import NavBar from "./components/ELBIS_navbar.component";
import ELBISweb from "./components/index.component";
import manageAccount from "./components/manageAccount.component";
import userView from "./components/user/user_myArticles.Component";
import createUser from "./components/moderation/moderation_createUser.component";
import loginView from "./components/loginView.component";
import CreateArticle from "./components/user/CreateArticle.component";
import createTopic from "./components/administration/administration_createTopic.component";
import manageRoles from "./components/administration/administration_userList.component";
import allArticlesList from "./components/moderation/moderation_articleList.component";
import manageSubmissions from "./components/moderation/moderation_submissionList.component";
import editUser from "./components/moderation/moderation_editUser.component";
import SessionDataService from "./services/session.service";
import moderation_userList from "./components/moderation/moderation_userList.component";
import administration_topicList from "./components/administration/administration_topicList.component";

//##########App start##########
/*
************************************************************************************
*   Frontend Session information will be stored as followed:
*       sessionStorage.getItem("sessionToken"); //to get the session token
*       sessionStorage.getItem("sessionUserID"); //to get the logged userID
*       sessionStorage.getItem("sessionEmail"); //to get the logged Email
*       sessionStorage.getItem("sessionRole");  //to get the logged Role
************************************************************************************
*   The App will check if the session is still alive on Backend-Side on every Reload/Remount of the page (no matter if manual or by the app itself)
 */
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
            //TODO resolve redundancies
            if (loggedUser.isLoggedIn) {
                if (sessionStorage.getItem("sessionRole") === "Administrator") {
                    /*************
                     *   Admin Area
                     * *************/
                    return (
                        <div className="app">
                            <Router>
                                <Route path="/" component={NavBar}/>
                                <br/>
                                <Route exact path="/login/home" component={ELBISweb}/>
                                <Route exact path="/login/manageAccount" component={manageAccount}/>
                                <Route exact path="/login/edit" component={CreateArticle}/>
                                <Route exact path="/login/edit/:id" component={CreateArticle}/>
                                <Route exact path="/login/user/myArticles" component={userView}/>
                                <Route exact path="/login/mod/manageSubmissions" component={manageSubmissions}/>
                                <Route exact path="/login/mod/manageArticles" component={allArticlesList}/>
                                <Route exact path="/login/mod/manageUsers" component={moderation_userList}/>
                                <Route exact path="/login/mod/createUser" component={createUser}/>
                                <Route exact path="/login/mod/editUser/:id" component={editUser}/>
                                <Route exact path="/login/admin/manageTopics" component={administration_topicList}/>
                                <Route exact path="/login/admin/createTopic" component={createTopic}/>
                                <Route exact path="/login/admin/manageRoles" component={manageRoles}/>
                            </Router>
                        </div>
                    )
                } else if (sessionStorage.getItem("sessionRole") === "Moderator") {
                    /*************
                     *   Mod Area
                     * *************/
                    return (<div className="app">
                        <Router>
                            <Route path="/" component={NavBar}/>
                            <br/>
                            <Route exact path="/login/home" component={ELBISweb}/>
                            <Route exact path="/login/edit" component={CreateArticle}/>
                            <Route exact path="/login/edit/:id" component={CreateArticle}/>
                            <Route exact path="/login/user/myArticles" component={userView}/>
                            <Route exact path="/login/mod/manageSubmissions" component={manageSubmissions}/>
                            <Route exact path="/login/mod/manageArticles" component={allArticlesList}/>
                            <Route exact path="/login/mod/manageUsers" component={moderation_userList}/>
                            <Route exact path="/login/mod/createUser" component={createUser}/>
                            <Route exact path="/login/mod/editUser/:id" component={editUser}/>
                        </Router>
                    </div>);
                } else if (sessionStorage.getItem("sessionRole") === "User") {
                    /*************
                     *   User Area
                     * *************/
                    return (<div className="app">
                        <Router>
                            <Route path="/" component={NavBar}/>
                            <br/>
                            <Route exact path="/login/home" component={ELBISweb}/>
                            <Route exact path="/login/edit" component={CreateArticle}/>
                            <Route exact path="/login/edit/:id" component={CreateArticle}/>
                            <Route exact path="/login/user/myArticles" component={userView}/>
                        </Router>
                    </div>);
                } else {
                }
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

//##########Mount method with sessioncheck##########
    async componentDidMount() {
        //wait for session check
        console.log("FRONTEND SESSION STATE (t/ID/e/r): " + sessionStorage.getItem("sessionToken") + " / " + sessionStorage.getItem("sessionUserID") + " / " + sessionStorage.getItem("sessionEmail") + " / " + sessionStorage.getItem("sessionRole"));
        if (loggedUser.loading) {
            //check if session exists
            SessionDataService.check(sessionStorage.getItem("sessionToken"), sessionStorage.getItem("sessionEmail")).then(res => {
                console.log(res);
                //session existing
                if (res.data.existing) {
                    loggedUser.loading = false;
                    loggedUser.isLoggedIn = true;
                    //no session found
                } else if (res.data.existing === false) {
                    loggedUser.loading = false;
                    loggedUser.isLoggedIn = false;
                }
            })
                .catch((error) => {
                    loggedUser.loading = false;
                    loggedUser.isLoggedIn = false;
                    console.log(error);
                })
        } else {
            loggedUser.loading = false;
            loggedUser.isLoggedIn = false;
        }
    }
}

export default observer(App);