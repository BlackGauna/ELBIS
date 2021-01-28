import React, {Component} from 'react';
import DatePicker from 'react-datepicker';
import "react-datepicker/dist/react-datepicker.css";
import "bootstrap/dist/css/bootstrap.min.css";
import UserDataService from "../../services/user.service";
import RoleDataService from "../../services/role.service";
import GenderDataService from "../../services/gender.service";
import Select from 'react-select';
import {Redirect} from "react-router-dom";
import {Button, FormLabel} from "react-bootstrap";

// TODO: Geburtsdatum (DatePicker?!)

//##########Component imports##########

export default class moderation_createUser extends Component {
    //##########constructor##########
    constructor(props) {
        super(props);
        //prepare all fields and make sure the functions are bound to this object
        this.onChange_email = this.onChange_email.bind(this);
        this.onChange_password = this.onChange_password.bind(this);
        this.onChange_passwordCheck = this.onChange_passwordCheck.bind(this);
        this.onChange_foreName = this.onChange_foreName.bind(this);
        this.onChange_surName = this.onChange_surName.bind(this);
        this.onChange_street = this.onChange_street.bind(this);
        this.onChange_houseNumber = this.onChange_houseNumber.bind(this);
        this.onChange_plz = this.onChange_plz.bind(this);
        this.onChange_place = this.onChange_place.bind(this);
        this.onChange_gender = this.onChange_gender.bind(this);
        this.onChange_role = this.onChange_role.bind(this);

        this.onSubmit = this.onSubmit.bind(this);

        this.state = {
            email: '',
            password: '',
            passwordCheck: '',
            foreName: '',
            surName: '',
            street: '',
            houseNumber: '',
            plz: '',
            place: '',
            gender: [],
            choosenGender: '',
            role: [],
            choosenRole: '',
            dateOfBirth: new Date(),

            stateText: '',
            redirect: false,
            submitted: false
        }
    }

    redirect = () => {
        this.setState({redirect: true})
    }

    // Get gender options for dropdown
    async getGenderOptions() {
        const res = await GenderDataService.getAll()

        let options = res.data.map(d => ({
            "label": d.name
        }))

        this.setState({gender: options})
    }

    // Get role options for dropdown
    async getRoleOptions() {
        const res = await RoleDataService.getAll()
        const data = res.data

        const options = data.map(d => ({
            "label": d.name
        }))

        this.setState({role: options})
    }

    //##########Mount method (equals initialize!)##########
    componentDidMount() {
        this.getGenderOptions()
        this.getRoleOptions()
    }

    //##########Render##########
    render() {
        if (this.state.redirect) {
            return <Redirect to="/login/mod/manageUsers"/>
        } else {
            return (
                <div className="container">
                    <h3>Nutzer erstellen</h3>
                    <form onSubmit={this.onSubmit}>

                        <div className="form-group">
                            <label>E-Mail</label>
                            <input
                                type="email"
                                className="form-control"
                                placeholder="E-Mail"
                                value={this.state.email}
                                onChange={this.onChange_email}/>
                        </div>
                        <div className="form-row">
                            <div className="form-group col-md-6">
                                <label>Passwort</label>
                                <input
                                    type="password"
                                    className="form-control"
                                    placeholder="Passwort"
                                    value={this.state.password}
                                    onChange={this.onChange_password}/>
                            </div>
                            <div className="form-group col-md-6">
                                <label>Passwort bestätigen</label>
                                <input
                                    type="password"
                                    className="form-control"
                                    placeholder="Passwort"
                                    value={this.state.passwordCheck}
                                    onChange={this.onChange_passwordCheck}/>
                            </div>
                        </div>
                        <div className="form-group">
                            <label>Geburtsdatum</label>
                            <div className="form-row">
                                <DatePicker
                                    selected={this.state.dateOfBirth}
                                    onChange={this.onChange_dateOfBirth}
                                    peekNextMonth
                                    showMonthDropdown
                                    showYearDropdown
                                    dropdownMode="select"
                                    customInput={<Button variant="outline-secondary" size="lg" block>{this.state.dateOfBirth.toLocaleDateString()}</Button>}
                                /> </div>
                        </div>
                        <div className="form-row">
                            <div className="form-group col-md-6">
                                <label>Vorname</label>
                                <input
                                    type="name"
                                    className="form-control"
                                    placeholder="Vorname"
                                    value={this.state.foreName}
                                    onChange={this.onChange_foreName}/>
                            </div>
                            <div className="form-group col-md-6">
                                <label>Nachname</label>
                                <input
                                    type="name"
                                    className="form-control"
                                    placeholder="Nachname"
                                    value={this.state.surName}
                                    onChange={this.onChange_surName}/>
                            </div>
                        </div>
                        <div className="form-row">
                            <div className="form-group col-md-10">
                                <label>Straße</label>
                                <input
                                    type="street"
                                    className="form-control"
                                    placeholder="Straße"
                                    value={this.state.street}
                                    onChange={this.onChange_street}/>
                            </div>
                            <div className="form-group col-md-2">
                                <label>Hausnummer</label>
                                <input
                                    type="houseNumber"
                                    className="form-control"
                                    placeholder="Hausnummer"
                                    value={this.state.houseNumber}
                                    onChange={this.onChange_houseNumber}/>
                            </div>
                        </div>
                        <div className="form-row">
                            <div className="form-group col-md-3">
                                <label>PLZ</label>
                                <input
                                    type="plz"
                                    className="form-control"
                                    placeholder="PLZ"
                                    value={this.state.plz}
                                    onChange={this.onChange_plz}/>
                            </div>
                            <div className="form-group col-md-9">
                                <label>Ort</label>
                                <input
                                    type="ort"
                                    className="form-control"
                                    placeholder="Ort"
                                    value={this.state.place}
                                    onChange={this.onChange_place}/>
                            </div>
                        </div>
                        <div className="form-row">
                            <div className="form-group col-md-6">
                                <label>Geschlecht</label>
                                <Select
                                    type="geschlecht"
                                    placeholder="Geschlecht"
                                    options={this.state.gender}
                                    onChange={this.onChange_gender}/>
                            </div>
                            <div className="form-group col-md-6">
                                <label>Rolle</label>
                                <Select
                                    type="role"
                                    placeholder="Rolle"
                                    options={this.state.role}
                                    onChange={this.onChange_role}/>
                            </div>
                        </div>
                        <div className="form-group">
                            <input type="submit" value="Nutzer erstellen" className="btn btn-primary"/>
                            <FormLabel className="m-3" style={{color: "red"}}>{this.state.stateText}</FormLabel>
                        </div>
                    </form>
                </div>
            )
        }
    }

    //##########submit method##########
    onSubmit(e) {
        //don't let any other submit run
        e.preventDefault();
        //create the object
        if (this.state.email) {
            if (this.state.password) {
                if (this.state.password === this.state.passwordCheck) {
                    const user = {
                        email: this.state.email,
                        password: this.state.password,
                        name: this.state.foreName + " " + this.state.surName,
                        address: this.state.street + " " + this.state.houseNumber + ", " + this.state.plz + " " + this.state.place,
                        gender: this.state.choosenGender,
                        role: this.state.choosenRole,
                        dateOfBirth: this.state.dateOfBirth
                    }

                    UserDataService.create(user)
                        .then(res => {
                            this.setState({
                                email: res.data.email,
                                password: res.data.password,
                                name: res.data.foreName + " " + res.data.surName,
                                address: res.data.street + " " + res.data.houseNumber + ", " + res.data.plz + " " + res.data.place,
                                gender: res.data.gender,
                                role: res.data.role,
                                dateOfBirth: res.data.dateOfBirth,

                                submitted: true
                            });
                            console.log(res.data);
                        })
                        .catch(e => {
                            console.log(e);
                        });

                    //go back to the moderationView
                    this.redirect();
                } else {
                    this.setState({
                        passwordCheck: '',
                        stateText: 'Die Passwörter stimmen nicht überein.'
                    })
                }
            } else {
                this.setState({
                    stateText: 'Geben sie ein Passwort ein'
                })
            }
        } else {
            this.setState({
                stateText: 'Geben sie eine Email-Addresse ein'
            })
        }
    }

    //##########change methods##########
    onChange_email(e) {
        this.setState({
            email: e.target.value //target equals a textBox target, value its value; Changes just the given state value
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

    onChange_street(e) {
        this.setState({
            street: e.target.value
        })
    }

    onChange_houseNumber(e) {
        this.setState({
            houseNumber: e.target.value
        })
    }

    onChange_plz(e) {
        this.setState({
            plz: e.target.value
        })
    }

    onChange_place(e) {
        this.setState({
            place: e.target.value
        })
    }

    onChange_gender(e) {
        this.setState({
            choosenGender: e.label
        })
    }

    onChange_role(e) {
        this.setState({
            choosenRole: e.label
        })
    }

    onChange_dateOfBirth = (date) => {
        this.setState({
            dateOfBirth: date
        })
    }
}