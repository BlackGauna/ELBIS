import React, {Component} from 'react';
import {BrowserRouter as Router, Link, Route} from 'react-router-dom';
import "bootstrap/dist/css/bootstrap.min.css";

//##########Component imports##########

export default class userView extends Component {
//##########Render##########
    render() {
        return (
            <Router>
                <div className="container">
                    administration content goes here as routes
                </div>
            </Router>
        )
    }
}