import React, {Component} from 'react';
import {BrowserRouter as Router, Link, Route} from 'react-router-dom';
import "bootstrap/dist/css/bootstrap.min.css";

//##########Component imports##########


export default class moderation_createUser extends Component {
    //##########Render##########
    render() {
        return (
            <div className="container">
                <h3>Nutzer erstellen</h3>
                <form onSubmit={this.onSubmit}>
                    <div className="form-group">
                        <label>E-Mail: </label>
                        <br/>
                        <input type = "text" className="form-control" value={this.state.email} onChange={this.onChange_eMail}/>
                    </div>

                    <div className="form-group">
                        <label>passwort: </label>
                        <br/>
                        <input type = "text" className="form-control" value={this.state.password} onChange={this.onChange_password}/>
                    </div>
                    <div className="form-group">
                        <label>passwort bestätigen: </label>
                        <br/>
                        <input type = "text" className="form-control" value={this.state.passwordCheck} onChange={this.onChange_passwordCheck}/>
                    </div>
                    <div className="form-group">
                        <label>Vorname: </label>
                        <br/>
                        <input type = "text" className="form-control" value={this.state.foreName} onChange={this.onChange_foreName}/>
                    </div>
                    <div className="form-group">
                        <label>Nachname: </label>
                        <br/>
                        <input type = "text" className="form-control" value={this.state.surName} onChange={this.onChange_surName}/>
                    </div>
                    <div className="form-group">
                        <label>Addresse: </label>
                        <br/>
                        <input type = "text" className="form-control" value={this.state.address} onChange={this.onChange_address}/>
                    </div>
                    <div className="form-group">
                        <label>Geschlecht:  </label>
                        <br/>
                        <select ref="userInput"
                                required
                                className="ElbisSelect"
                                value={this.state.gender}
                                onChange={this.onChange_gender}>
                            {
                                this.state.gender.map(function (gender) {
                                    return <option key={gender} value={gender}>{gender}</option>;
                                })
                            }
                        </select>
                    </div>
                    <div className="form-group">
                        <label>Rolle: </label>
                        <br/>
                        <select ref="userInput"
                                required
                                className="ElbisSelect"
                                value={this.state.role}
                                onChange={this.onChange_role}>
                            {
                                this.state.role.map(function (role) {
                                    return <option key={role} value={role}>{role}</option>;
                                })
                            }
                        </select>
                    </div>
                    <div className="form-group">
                        <label>Geburtsdatum: </label>
                        <br/>
                        <div>
                            A datePicker is missed here
                        </div>
                    </div>
                </form>
            </div>
        )
    }

//##########Mount method (equals initialize!)##########
    comonentDidMount() {
        this.setState({
            //fill dropdowns
            gender: ['Maennlich', 'Weiblich', 'Divers'],
            //TODO make role dynamic (if a mod is logged in - just allow to create users!)
            role: ['Nutzer', 'Moderator', 'Administrator']
        })
    }

//##########constructor##########
    constructor(props) {
        super(props);
        //prepare all fields and make sure the functions are bound to this object
        this.onChange_eMail = this.onChange_eMail.bind(this);
        this.onChange_password = this.onChange_password.bind(this);
        this.onChange_passwordCheck = this.onChange_passwordCheck.bind(this);
        this.onChange_foreName = this.onChange_foreName.bind(this);
        this.onChange_surName = this.onChange_surName.bind(this);
        this.onChange_address = this.onChange_address.bind(this);
        this.onChange_gender = this.onChange_gender.bind(this);
        this.onChange_role = this.onChange_role.bind(this);
        this.onChange_bday = this.onChange_bday.bind(this);
        this.onSubmit = this.onSubmit.bind(this);

        this.state = {
            eMail: '',
            password: '',
            passwordCheck: '',
            foreName: '',
            surName: '',
            address: '',
            gender: [],
            role: [],
            bDay: new Date(),
        }
    }

//##########submit method##########
    onSubmit(e) {
        //don't let any other submit run
        e.preventDefault();
        //create the object
        const user = {
            eMail: this.state.eMail,
            //TODO make password check
            password: this.state.password,
            name: this.state.foreName + " " + this.state.surName,
            address: this.state.add,
            //TODO submit gender and role in the right way
            gender: this.state.gender,
            role: this.state.role,
            bDay: this.state.bday
        }
        //TODO replace and do an actual submit to the db
        console.log(user)
        //go back to the moderationView
        window.location = '/login/moderation';
    }

//##########change methods##########
    onChange_eMail(e) {
        this.setState({
            eMail: e.target.value //target equals a textBox target, value its value; Changes just the given state value
        })
    }

    onChange_password(e) {
        this.setState({
            password: e.target.value
        })
    }

    onChange_passwordCheck(e) {
        this.setState({
            passwordCheck: e.target.value
        })
    }

    onChange_foreName(e) {
        this.setState({
            foreName: e.target.value
        })
    }

    onChange_surName(e) {
        this.setState({
            surName: e.target.value
        })
    }

    onChange_address(e) {
        this.setState({
            address: e.target.value
        })
    }

    onChange_gender(e) {
        this.setState({
            gender: e.target.value
        })
    }

    onChange_role(e) {
        this.setState({
            role: e.target.value
        })
    }

    onChange_bday(date) {
        this.setState({
            bday: date
        })
    }
}