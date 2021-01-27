import React, {Component} from 'react';
import {BrowserRouter as Router, Link, Route} from 'react-router-dom';
import "bootstrap/dist/css/bootstrap.min.css";
import { Redirect } from 'react-router-dom';

export default class resetPassword extends Component {
    constructor(props) {
        super(props);
        this.state = {
           redirect: false
        }
    }

    redirect = () => {
        console.log("redirect")
        this.setState({redirect:true})
    }

//##########Render##########
    render() {
        if (this.state.redirect) {
            return <Redirect to="/" />
        } else {
        return (

            <Router>

                <div className="container"> <br/><hr/><br/>
                    <h4> Passwort vergessen? Bald gibts hier die Lösung! </h4>
                    <Link onClick={this.redirect}> Abbrechen </Link>
                </div>
            </Router>
        ) }
    }
}