import React, {Component} from 'react';
import {BrowserRouter as Router, Link, Route} from 'react-router-dom';
import "bootstrap/dist/css/bootstrap.min.css";

//##########Component imports##########
import moderation_userList from "./moderation_userList.component";

export default class moderationView extends Component {
//##########Render##########
    render() {
        return (
            <Router>
                <div className="container">
                    <Route path="/login/moderation" component={moderation_userList} />
                </div>
            </Router>
        )
    }
}