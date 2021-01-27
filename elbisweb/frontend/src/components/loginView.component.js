import React, {Component} from 'react';
import "bootstrap/dist/css/bootstrap.min.css";
import loggedUser from '../session/loggedUser';
import logo from '../resources/ELBIS_logo/ELBIS_Ausgeschrieben.png';
import {Form, FormGroup, Button} from "react-bootstrap";
import UserDataService from "../services/user.service";
import SessionDataService from "../services/session.service";
import ELBIS_loginSubmitButton from "./ELBIS_loginSubmitButton";
import ELBIS_loginInputfield from "./ELBIS_loginInputfield.component";

export default class loginViewComponent extends Component {

    constructor(props) {
        super(props);
        this.state = {
            email: '',
            password: '',
            buttonDisabled: false,
            loginstate: "geben sie ihre Anmeldedaten ein."
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

    resetForm(msg) {
        this.setState({
            email: '',
            password: '',
            buttonDisabled: false,
            loginstate: msg
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
        const authEmail = this.state.email;
        const authPassword = this.state.password;


        //authenticate a user
        UserDataService.authenticate(authEmail, authPassword).then(res => {
            //authenticate successfull
            if (res.data.success) {
                //Delete old sessions
                try{
                    SessionDataService.delete(authEmail)
                        .then(res => console.log(res.data));
                } catch(e){
                    console.log("No old session deleted")
                }
                /*
                    * create a session
                 */
                //frontend session
                //TODO maybe replace token generation with time+date hashing!!
                const token = Math.random().toString(36).substr(2);
                //const token = bcrypt.hash(((Math.random().toString(36).substr(2)) + (Date.now.toString)), 10);
                const sessUserID = res.data.data._id.toString();
                const sessEmail = res.data.data.email;
                const sessRole = res.data.data.role;
                sessionStorage.setItem("sessionToken", token);
                sessionStorage.setItem("sessionUserID", sessUserID);
                sessionStorage.setItem("sessionEmail", sessEmail);
                sessionStorage.setItem("sessionRole", sessRole);
                //backend session
                const session = {
                    token: token,
                    userid: sessUserID,
                    email: sessEmail,
                    role: sessRole,

                }
                SessionDataService.create(session)
                    .then(res => {
                        this.setState({
                            token: res.data.token,
                            userid: res.data.userid,
                            email: res.data.email,
                            role: res.data.role,
                            // TODO: expire
                            submitted: true
                        });
                        console.log(res.data);
                    })
                    .catch(e => {
                        console.log(e);
                    });
                //pageskip variables
                loggedUser.loading = false;
                loggedUser.isLoggedIn = true;
                window.location = '/login';
                //authenticate failed
            } else if (res.data.success === false) {
                loggedUser.loading = false;
                loggedUser.isLoggedIn = false;
                this.resetForm("Das angegebene Passwort war falsch.");
            }
        })
            .catch((error) => {
                loggedUser.loading = false;
                loggedUser.isLoggedIn = false;
                this.resetForm("Keine gültigen Logindaten.");
                console.log(error);
            })
    }


//##########Render##########
    render() {
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
                    <label>{this.state.loginstate}</label>

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
