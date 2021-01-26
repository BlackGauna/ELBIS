import React, {Component} from 'react';
import {BrowserRouter as Router, Link, Route} from 'react-router-dom';
import {Redirect} from 'react-router-dom';
import "bootstrap/dist/css/bootstrap.min.css";
import loggedUser from '../session/loggedUser';
import ELBISweb from "./index.component";
import logo from '../resources/ELBIS_logo/ELBIS_Ausgeschrieben.png';
import {Form, FormGroup, Button} from "react-bootstrap";
import UserDataService from "../services/user.service";

//##########Component imports##########
import ELBIS_loginSubmitButton from "./ELBIS_loginSubmitButton";
import ELBIS_loginInputfield from "./ELBIS_loginInputfield.component";

export default class loginViewComponent extends Component {

    constructor(props) {
        super(props);
        this.state = {
            email: '',
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
            email: '',
            password: '',
            buttonDisabled: false
        })
    }

    //##########logging methods##########
    async doLogin() {
        if (!this.state.email) {
            return;
        }
        if (!this.state.password) {
            return;
        }
        //make sure the user can't double click the submit button
        this.setState({
            buttonDisabled: true
        })

        //authenticate a user
        UserDataService.authenticate(this.state.email, this.state.password).then(res => {
            const statemail = this.state.email;

            //login successfull
            if (res.data.success) {
                loggedUser.loading = false;
                loggedUser.isLoggedIn = true;
                loggedUser.email = res.data.email;
                loggedUser.password = res.data.password;
                loggedUser.role = res.data.role;
                window.location = '/login';
                sessionStorage.setItem("user", statemail)
                //login failed
            } else if (res.data.success === false) {
                loggedUser.loading = false;
                loggedUser.isLoggedIn = false;
                this.resetForm();
            }
        })
            .catch((error) => {
                loggedUser.loading = false;
                loggedUser.isLoggedIn = false;
                this.resetForm();
                console.log(error);
            })
    }


//##########Render##########
render()
{
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
                                placeholder='passwort'
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
