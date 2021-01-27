import React from 'react';
import {BrowserRouter as Router, Redirect, Route} from "react-router-dom";
import {observer} from 'mobx-react';
import "bootstrap/dist/css/bootstrap.min.css";
import loggedUser from './session/loggedUser';
import './ELBISWeb.css';
import logo from './resources/ELBIS_logo/ELBIS_Ausgeschrieben.png';
import NavBar from "./components/ELBIS_navbar.component";
import loginView from "./components/loginView.component";
import SessionDataService from "./services/session.service";


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
            if (loggedUser.isLoggedIn) {
                return (<div className="app">
                    <Router>
                        <Route path="/" component={NavBar}/>
                    </Router>
                </div>);
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