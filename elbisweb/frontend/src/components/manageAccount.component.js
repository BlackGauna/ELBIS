import React, {Component} from 'react';
import {BrowserRouter as Router, Link, Route} from 'react-router-dom';
import "bootstrap/dist/css/bootstrap.min.css";

export default class EmanageAccount extends Component {
//##########Render##########
    render() {
        return (
                <div className="container">
                    <h3>Hallo {sessionStorage.getItem("sessionEmail")} </h3>
                   Hier kannst du bald einen Account verwwalten!
                </div>
        )
    }
}