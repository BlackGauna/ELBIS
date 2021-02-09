import React from 'react';
import {BrowserRouter as Router, Route, Switch, Redirect} from "react-router-dom";
import {observer} from 'mobx-react';
import {ROLE} from "./session/userRoles.ice";
import "bootstrap/dist/css/bootstrap.min.css";
import loggedUser from './session/loggedUser';
import './ELBISWeb.css';
import logo from './resources/ELBIS_logo/ELBIS_Ausgeschrieben.svg';
import NavBar from "./components/ELBIS_navbar.component";
import TerminalView from "./components/terminal.component";
import ManageAccount from "./components/manageAccount.component";
import MyArticles from "./components/user/user_myArticles.Component";
import LoginView from "./components/loginView.component";
import ResetPassword from "./components/resetPassword.component";
import CreateArticle from "./components/user/CreateArticle.component";
import CreateTopic from "./components/administration/administration_createTopic.component";
import AllArticlesList from "./components/moderation/moderation_articleList.component";
import ManageSubmissions from "./components/moderation/moderation_submissionList.component";
import EditUser from "./components/moderation/moderation_editUser.component";
import SessionDataService from "./services/session.service";
import Moderation_userList from "./components/moderation/moderation_userList.component";
import Administration_topicList from "./components/administration/administration_topicList.component";
import ArticleView from "./components/ArticleView.component";
import EditTopic from "./components/administration/administration_editTopic.component";
import RegisterAccount from "./components/registerAccount.component";
import ArticleList from "./components/articleList.component";

//##########App start##########
/*
************************************************************************************
*   Frontend Session information will be stored as followed:
*       sessionStorage.getItem("sessionToken"); //to get the session token
*       sessionStorage.getItem("sessionUserID"); //to get the logged userID
*       sessionStorage.getItem("sessionEmail"); //to get the logged Email
*       sessionStorage.getItem("sessionRole");  //to get the logged Role
************************************************************************************
*   The App will check if the session is still alive on Backend-Side on every Reload/Remount of the page (no matter if manual or by the app itself).
*   Checks token, email AND role of a session - if one is invalid, the user gets logged out (not manipulatable by backend check)
* ***********************************************************************************
*
*
 */

class App extends React.Component {
    render() {
        const NotFound=()=> <div>Not found <Redirect to='/public' /></div>
        let adminRoutes, moderationRoutes, userRoutes,publicRoutes;
        adminRoutes =
            <div>
                <Route exact path="/login/admin/manageTopics" component={Administration_topicList}/>
                <Route exact path="/login/admin/createTopic" component={CreateTopic}/>
                <Route exact path="/login/admin/editTopic/:id" component={EditTopic}/>
            </div>
        moderationRoutes =
            <div>
                <Route exact path="/login/mod/manageSubmissions" component={ManageSubmissions}/>
                <Route exact path="/login/mod/manageArticles" component={AllArticlesList}/>
                <Route exact path="/login/mod/manageUsers" component={Moderation_userList}/>
                <Route exact path="/login/mod/editUser/:id" component={EditUser}/>
            </div>
        userRoutes =
            <div>
                <Route path="/" component={NavBar}/>
                <Route exact path="/article/:id" component={ArticleView}/>
                <br/>
                <Route exact path="/" component={ArticleList}/>
                <Route exact path="/login/resetPassword" component={ResetPassword}/>
                <Route exact path="/login/terminal" component={TerminalView}/>
                <Route exact path="/login/manageAccount" component={ManageAccount}/>
                <Route exact path="/login/edit" component={CreateArticle}/>
                <Route exact path="/login/edit/:id" component={CreateArticle}/>
                <Route exact path="/login/user/myArticles" component={MyArticles}/>
                </div>
        publicRoutes =
            <div>
                <Route path="/" component={NavBar}/>
                <Route exact path="/terminal" exact component={TerminalView}/>
                {/*<Switch>*/}
                    <Route exact path="/login" exact component={LoginView}/>
                    <Route path="/public/register" component={RegisterAccount}/>
                    <Route exact path="/public/resetPassword" component={ResetPassword}/>
                    <Route exact path="/" component={ArticleList}/>
                    <Route exact path="/article/:id" component={ArticleView}/>
                    {/*<Route component={NotFound} />
                    </Switch>*/}
            </div>
        if (loggedUser.loading) {
            return (
                <div className="app">
                    <div className="container">
                        <img src={logo} style={{marginBlock: "10%", marginLeft: "10.5%", height: 150}} alt="ELBIS"/><h1> Is loading...</h1>
                    </div>
                </div>
            )
        } else {
           if (loggedUser.isLoggedIn) {
                if (sessionStorage.getItem("sessionRole") === ROLE.ADMINISTRATOR) {
                    /*************
                     *   Admin Area
                     * *************/
                    return (
                        <div className="app">
                            <Router>
                                {userRoutes}
                                {moderationRoutes}
                                {adminRoutes}
                            </Router>
                        </div>
                    )
                } else if (sessionStorage.getItem("sessionRole") === ROLE.MODERATOR) {
                    /*************
                     *   Mod Area
                     * *************/
                    return (<div className="app">
                        <Router>
                            {userRoutes}
                            {moderationRoutes}
                        </Router>
                    </div>);
                } else if (sessionStorage.getItem("sessionRole") === ROLE.USER) {
                    /*************
                     *   User Area
                     * *************/
                    return (<div className="app">
                        <Router>
                            {userRoutes}
                        </Router>
                    </div>);
                } else {
                    return (
                        <div className="app">
                            <Router>
                                <Route component={NotFound} />
                            </Router>
                        </div>
                    )
                }
            } else if (!loggedUser.isLoggedIn) {
                /*************
                 *   public Area
                 * *************/
                return (
                    <div className="app">
                        <Router>
                            {publicRoutes}
                        </Router>
                    </div>
                )
            }
        }
    }

    /*************
     *   Mount method with sessioncheck
     * *************/
    async componentDidMount() {
        //wait for session check
        console.log("FRONTEND SESSION STATE (t/ID/e/r): " + sessionStorage.getItem("sessionToken") + " / " + sessionStorage.getItem("sessionUserID") + " / " + sessionStorage.getItem("sessionEmail") + " / " + sessionStorage.getItem("sessionRole"));
        if (loggedUser.loading) {
            //check if session exists
            SessionDataService.check(sessionStorage.getItem("sessionToken"), sessionStorage.getItem("sessionEmail"), sessionStorage.getItem("sessionRole")).then(res => {
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