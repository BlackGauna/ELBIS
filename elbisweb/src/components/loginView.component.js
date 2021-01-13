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
                    Login does not work yet, klick the link and refresh the site manually to get into the app again
                </div>
            <br/>
                <div className="container">
                    <Link to="/login/hauptseite">Login</Link>
                </div>
            </Router>
        )
    }
}