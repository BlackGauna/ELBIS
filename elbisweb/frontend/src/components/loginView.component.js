import React, {Component} from 'react';
import {BrowserRouter as Router, Link, Route} from 'react-router-dom';
import {Redirect} from 'react-router-dom';
import "bootstrap/dist/css/bootstrap.min.css";
import loggedUser from '../session/loggedUser';
import ELBISweb from "./ELBISweb.component";
import DatePicker from "react-datepicker";

export default class loginViewComponent extends Component {

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

    //##########logout method##########
    async doLogout() {
        try {
            let res = await fetch('/isLoggedOut', {
                method: 'post',
                headers: {
                    'Accept': 'application/json',
                    'Content-type': 'application/json'
                }
            });
            let result = await res.json();

            if (result && result.success) {
                loggedUser.isLoggedIn = false;
                loggedUser.eMail = '';
                loggedUser.role = '';
            }
        } catch (e) {
            console.log("Logout Error! " + e);
        }
    }
//##########submit method##########
    onSubmit(){
        //TODO login logic goes here
        console.log("LOGGING")
        window.location = '/login/hauptseite';
    }
//##########Render##########
    render() {
        /*
        if (loggedUser.loading) {
            return (
                <Router>
                    <div className="container">
                        Login is building here
                    </div>
                    <Redirect to="/login/hauptseite"/>
                </Router>
            )
        } */
        if (loggedUser.isLoggedIn) {
            return (
                <Router>
                    <Redirect to="/login"/>
                </Router>
            )
        } else {
            //TODO email and password form need to give some values
            //TODO API implementation of login methods
            return (
                <div className="container">
                    <h3>Anmeldung</h3>
                    <form onSubmit={this.onSubmit}>
                        <div className="form-group">
                            <label>E-Mail: </label>
                            <br/>
                            <input type = "email" className="form-control" />
                        </div>

                        <div className="form-group">
                            <label>passwort: </label>
                            <br/>
                            <input type = "password" className="form-control" />
                        </div>

                        <div className="form-group">
                            <input type="submit" value="Anmelden" className="btn btn-primary" disabled='true'/>
                        </div>
                    </form>
                    <div className="container">
                        I AM NOT WORKING YET <br/>
                        <Link to="/login/hauptseite"><button className="btn btn-primary">provisorischer 'weiter zu elbis' button</button></Link>
                    </div>
                </div>
            )
        }
    }
}
