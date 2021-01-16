import React, {Component} from 'react';
import {BrowserRouter as Router, Link, Route} from 'react-router-dom';
import "bootstrap/dist/css/bootstrap.min.css";

//##########Component imports##########
import administration_topicListComponent from "./administration_topicList.component";

export default class userView extends Component {
//##########Render##########
    render() {
        return (
            <Router>
                <div className="container">
                    <Route path="/" component={administration_topicListComponent} />
                </div>
            </Router>
        )
    }
}