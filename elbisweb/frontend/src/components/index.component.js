import React, {Component} from 'react';
import {BrowserRouter as Router, Link, Route} from 'react-router-dom';
import "bootstrap/dist/css/bootstrap.min.css";

export default class ELBISweb extends Component {
//##########Render##########
    render() {
        return (
            <Router>
                <div className="container">
                    Maybe show all public article one-pagers here
                </div>
            </Router>
        )
    }
}