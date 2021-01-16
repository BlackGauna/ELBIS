import React, {Component} from 'react';
import {BrowserRouter as Router, Link, Route} from 'react-router-dom';
import "bootstrap/dist/css/bootstrap.min.css";

export default class loginViewComponent extends Component {



//##########Render##########
    render() {
        return (
            <Router>
                <br/>
                <div className="container">
                </div>
            <br/>
                <div className="container">
                    ELBIS login goes here
                    <br/><br/>
                    <Link to="/login/hauptseite"><button className="btn btn-primary" onClick="reload">Login</button></Link>
                </div>
            </Router>
        )
    }
}