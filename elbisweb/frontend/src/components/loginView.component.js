import React, {Component} from 'react';
import {BrowserRouter as Router, Link, Route} from 'react-router-dom';
import {Redirect} from 'react-router-dom';
import "bootstrap/dist/css/bootstrap.min.css";
import loggedUser from '../session/loggedUser';
import ELBISweb from "./index.component";
import logo from '../resources/ELBIS_logo/ELBIS_Ausgeschrieben.png';
import {Form, FormGroup, Button} from "react-bootstrap";

//##########Component imports##########
import ELBIS_loginSubmitButton from "./ELBIS_loginSubmitButton";
import ELBIS_loginInputfield from "./ELBIS_loginInputfield.component";

export default class loginViewComponent extends Component {

    constructor(props) {
        super(props);
        this.state = {
            eMail: '',
            password: '',
            buttonDisabled: false
        }
    }

    setInputValue(property, val) {
        val = val.trim();
        if (val.length > 24) {
            return;
        }
        this.setState({
            [property]: val
        })
    }

    resetForm() {
        this.setState({
            eMail: '',
            password: '',
            buttonDisabled: false
        })
    }

    //##########logging methods##########
    async doLogin() {
        if (!this.state.eMail) {
            return;
        }
        if (!this.state.password) {
            return;
        }
        //make sure the user can't double click the submit button
        this.setState({
            buttonDisabled: true
        })
        try {
            let res = await fetch('isLoggedIn', {
                method: 'post',
                headers: {
                    'Accept': 'application/json',
                    'Content-Type': 'application/json'
                }, body: JSON.stringify({
                    email: this.state.email,
                    //TODO maybe hash passwords before
                    password: this.state.password
                })
            });

            let result = await res.json();
            if (result && result.success) {
                loggedUser.isLoggedIn = true;
                loggedUser.email = result.email;
                loggedUser.role = result.role;
            } else if (result && result.success === false) {
                this.resetForm();
                alert(result.msg);
            }

        } catch (e) {
            console.log(e);
            this.resetForm();
        }
    }


//##########submit method##########
    onSubmit = () => {
        //TODO login logic goes here
        console.log("LOGGING");
    }

//##########Render##########
    render() {

        //TODO email and password form need to give some values
        //TODO API implementation of login methods
        return (
            <div className="container">
                <br/><br/>
                <div className="container">
                    <p/><br/>
                    <img src={logo} alt="ELBIS"></img>
                    <br/>
                    <h4>Anmeldung</h4>
                    <hr/>
                    <p/>
                    <form onSubmit={this.onSubmit}>
                        <FormGroup role="form">
                            <div className="form-group">
                                <label>E-Mail: </label>
                                <br/>
                                <ELBIS_loginInputfield
                                    type="email"
                                    placeholder='email'
                                    value={this.state.email ? this.state.email : ''}
                                    onChange={(val) => this.setInputValue('email', val)}
                                />
                            </div>
                            <div className="form-group">
                                <label>Passwort: </label>
                                <br/>
                                <ELBIS_loginInputfield
                                    type="password"
                                    placeholder='Passwort'
                                    value={this.state.password ? this.state.password : ''}
                                    onChange={(val) => this.setInputValue('password', val)}
                                />
                            </div>

                            <div className="form-group">
                                <br/>
                                <ELBIS_loginSubmitButton
                                    text="Anmelden"
                                    disabled={this.state.buttonDisabled}
                                    onClick={() => this.doLogin()}
                                ></ELBIS_loginSubmitButton>
                            </div>
                        </FormGroup>
                    </form>
                </div>
            </div>
        )
    }
}
