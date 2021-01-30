import React, {Component} from "react";
import UserDataService from "../../services/user.service";
import "bootstrap/dist/css/bootstrap.min.css";
import Select from 'react-select';
import {GENDER} from "../../session/gender.ice";
import {ROLE} from "../../session/userRoles.ice";
import {Redirect} from "react-router-dom";
import {LabelImportant} from "@material-ui/icons";

export default class moderation_editUser extends Component {

    constructor(props) {
        super(props);

        this.state = {
            currentUser: {
                id: null,
                email: '',
                password: '',
                passwordCheck: '',
                foreName: '',
                surName: '',
                street: '',
                houseNumber: '',
                plz: '',
                place: '',
                choosenGender: '',
                choosenRole: '',
                dateOfBirth: new Date(),
                allowedTopics: [],
                choosenTopics: [],
            },
            role: [],
            gender: [],
            stateText: '',
            redirect: false,

        };
    }

    redirect = () => {
        this.setState({redirect: true})
    }

    componentDidMount() {
        this.getUser(this.props.match.params.id);
        this.getGenderOptions()
        this.getRoleOptions()
    }

    // Get gender options for dropdown
    async getGenderOptions() {
        const {currentUser} = this.state;
        const data = GENDER.getAll();
        const options = data.map(d => ({
            "label": d.name
        }))
        this.setState({gender: options})
    }

    // Get role options for dropdown
    async getRoleOptions() {
        let data
        const {currentUser} = this.state;
        if (sessionStorage.getItem("sessionRole") === ROLE.ADMINISTRATOR) {
            data = ROLE.getAll()
        } else {
            data = ROLE.getModeratorOptions()
        }
        const options = data.map(d => ({
            "label": d.name
        }))
        this.setState({role: options})
    }

    getUser = (id) => {
        UserDataService.get(id)
            .then(response => {
                this.setState({
                    currentUser: {
                        email: response.data.email,
                        //TODO the hash will be loaded - dont change the password / !only write a new PW to DB if fiel changed
                        //password: response.data.password,
                        //passwordCheck: response.data.password,
                        //TODO add splitted DB fields
                        foreName: response.data.name,
                        surName: response.data.name,
                        street: response.data.address,
                        houseNumber: response.data.address,
                        plz: response.data.address,
                        place: response.data.address,
                        choosenGender: response.data.gender,
                        choosenRole: response.data.role,
                        //TODO convert to lacal date?
                        //dateOfBirth:  response.data.dateOfBirth,
                    }
                });
                console.log(response.data);
            })
            .catch(e => {
                console.log(e);
            });
    }

    //##########update in DB method########## //TODO not working yet
    updateUser = () => {
        UserDataService.update(
            this.state.currentUser.id,
            this.state.currentUser
        )
            .then(response => {
                console.log(response.data);
                this.setState({
                    message: "The user was updated successfully!"
                });
            })
            .catch(e => {
                console.log(e);
            });
    }

    //##########Render##########
    //TODO maybe open an edit modal with those links?
    //TODO better intuitive design
    //TODO buttons for special tools
    render() {
        if (this.state.redirect) {
            return <Redirect to="/login/mod/manageUsers"/>
        } else {
            const {currentUser} = this.state;
            return (
                <div>
                    {currentUser ? (
                        <div className="container">
                            <h3>Nutzer bearbeiten</h3>
                            <hr/>
                            <div className="form-row">
                                <div className="form-group col-md-6">
                                    <label>Vorname</label>
                                    <h5>{currentUser.foreName}</h5> <a href=''>bearbeiten</a>

                                </div>
                                <div className="form-group col-md-6">
                                    <label>Nachname</label>
                                    <h5>{currentUser.surName}</h5> <a href=''>bearbeiten</a>
                                </div>
                            </div>
                            <div className="form-row">
                            <div className="form-group col-md-6">
                                <label>E-Mail</label>
                                <h5>{currentUser.email}</h5> <a href=''>bearbeiten</a>
                            </div>
                            </div>
                            <br/>
                            <div className="form-row">
                            <div className="form-group col-md-3">
                                <label>Passwort bearbeiten: </label> <br/>
                                <button
                                    className="btn btn-secondary"
                                >
                                    Neues Passwort setzen
                                </button>
                            </div>

                            <div className="form-group col-md-5">
                                <h6>{currentUser.choosenRole}</h6>
                                <button
                                    className="btn btn-secondary"
                                >
                                    Ändern
                                </button>
                            </div>
                            </div>
                            <hr/>
                            <div className="form-row">
                                <div className="form-group col-md-3">
                                    <label>Straße</label>
                                    <h6>{currentUser.street}</h6> <a href=''>bearbeiten</a>
                                </div>
                                <div className="form-group col-md-3">
                                    <label>Hausnummer</label>
                                    <h6>{currentUser.houseNumber}</h6> <a href=''>bearbeiten</a>
                                </div>
                            <div className="form-group col-md-3">
                                <label>PLZ</label>
                                <h6>{currentUser.plz}</h6> <a href=''>bearbeiten</a>
                            </div>
                            <div className="form-group col-md-3">
                                <label>Ort</label>
                                <h6>{currentUser.place}</h6> <a href=''>bearbeiten</a>
                            </div>
                            </div>
                            <div className="form-row">
                                <div className="form-group col-md-3">
                                    <label>Geschlecht</label>
                                    <h6>{currentUser.choosenGender}</h6>
                                    <button
                                        className="btn btn-secondary"
                                    >
                                        Ändern
                                    </button>
                                </div>
                            </div>

                            <button
                                className="btn btn-primary"
                                onClick={this.redirect}
                            >
                                Zurück
                            </button>
                        </div>
                    ) : (
                        <div>
                            <p>Test...</p>
                        </div>
                    )}
                </div>
            );
        }
    }

    //##########OLD Render##########
    /*
    render() {
        const {currentUser} = this.state;
        return (
            <div>
                {currentUser ? (
                    <div className="container">
                        <h3>Nutzer bearbeiten</h3>
                        <form>
                            <div className="form-row">
                                <div className="form-group col-md-6">
                                    <label>Vorname</label>
                                    <input
                                        type="name"
                                        className="form-control"
                                        placeholder="Vorname"
                                        value={currentUser.foreName}
                                        onChange={this.onChange_foreName}/>
                                </div>
                                <div className="form-group col-md-6">
                                    <label>Nachname</label>
                                    <input
                                        type="name"
                                        className="form-control"
                                        placeholder="Nachname"
                                        value={currentUser.surName}
                                        onChange={this.onChange_surName}/>
                                </div>
                            </div>

                            <div className="form-group">
                                <label>E-Mail</label>
                                <input
                                    type="email"
                                    className="form-control"
                                    placeholder="E-Mail"
                                    value={currentUser.email}
                                    onChange={this.onChange_email}/>
                            </div>
                            <div className="form-row">
                                <div className="form-group col-md-6">
                                    <label>Passwort</label>
                                    <input
                                        type="password"
                                        className="form-control"
                                        placeholder="Passwort"
                                        value={currentUser.password}
                                        onChange={this.onChange_password}/>
                                </div>
                                <div className="form-group col-md-6">
                                    <label>Passwort bestätigen</label>
                                    <input
                                        type="password"
                                        className="form-control"
                                        placeholder="Passwort"
                                        value={currentUser.passwordCheck}
                                        onChange={this.onChange_passwordCheck}/>
                                </div>
                            </div>
                            <div className="form-row">
                                <div className="form-group col-md-10">
                                    <label>Straße</label>
                                    <input
                                        type="street"
                                        className="form-control"
                                        placeholder="Straße"
                                        value={currentUser.street}
                                        onChange={this.onChange_street}/>
                                </div>
                                <div className="form-group col-md-2">
                                    <label>Hausnummer</label>
                                    <input
                                        type="houseNumber"
                                        className="form-control"
                                        placeholder="Hausnummer"
                                        value={currentUser.houseNumber}
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
                                        value={currentUser.plz}
                                        onChange={this.onChange_plz}/>
                                </div>
                                <div className="form-group col-md-9">
                                    <label>Ort</label>
                                    <input
                                        type="ort"
                                        className="form-control"
                                        placeholder="Ort"
                                        value={currentUser.place}
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
                                        value={currentUser.choosenGender}
                                        onChange={this.onChange_gender}/>
                                </div>
                                <div className="form-group col-md-6">
                                    <label>Rolle</label>
                                    <Select
                                        type="role"
                                        placeholder="Rolle"
                                        options={this.state.role}
                                        value={currentUser.choosenRole}
                                        onChange={this.onChange_role}/>
                                </div>
                            </div>

                        </form>

                        <button
                            type="submit"
                            className="btn btn-primary"
                            onClick={this.updateUser}
                        >
                            Bestätigen
                        </button>
                    </div>
                ) : (
                    <div>
                        <p>Test...</p>
                    </div>
                )}
            </div>

        );
    } */

//##########change methods##########
    onChange_email = (e) => {
        const email = e.target.value;

        this.setState(function (prevState) {
            return {
                currentUser: {
                    ...prevState.currentUser,
                    email: email
                }
            };
        });
    }

    onChange_password = (e) => {
        const password = e.target.value;

        this.setState(function (prevState) {
            return {
                currentUser: {
                    ...prevState.currentUser,
                    password: password
                }
            };
        });
    }

    onChange_passwordCheck = (e) => {
        const passwordCheck = e.target.value;

        this.setState(function (prevState) {
            return {
                currentUser: {
                    ...prevState.currentUser,
                    passwordCheck: passwordCheck
                }
            };
        });
    }

    onChange_foreName = (e) => {
        const foreName = e.target.value;

        this.setState(function (prevState) {
            return {
                currentUser: {
                    ...prevState.currentUser,
                    foreName: foreName
                }
            };
        });
    }

    onChange_surName = (e) => {
        const surName = e.target.value;

        this.setState(function (prevState) {
            return {
                currentUser: {
                    ...prevState.currentUser,
                    surName: surName
                }
            };
        });
    }

    onChange_street = (e) => {
        const street = e.target.value;

        this.setState(function (prevState) {
            return {
                currentUser: {
                    ...prevState.currentUser,
                    street: street
                }
            };
        });
    }

    onChange_houseNumber = (e) => {
        const houseNumber = e.target.value;

        this.setState(function (prevState) {
            return {
                currentUser: {
                    ...prevState.currentUser,
                    houseNumber: houseNumber
                }
            };
        });
    }

    onChange_plz = (e) => {
        const plz = e.target.value;

        this.setState(function (prevState) {
            return {
                currentUser: {
                    ...prevState.currentUser,
                    plz: plz
                }
            };
        });
    }

    onChange_place = (e) => {
        const place = e.target.value;

        this.setState(function (prevState) {
            return {
                currentUser: {
                    ...prevState.currentUser,
                    place: place
                }
            };
        });
    }

    onChange_gender = (e) => {
        const gender = e.target.value;

        this.setState(function (prevState) {
            return {
                currentUser: {
                    ...prevState.currentUser,
                    choosenGender: gender
                }
            };
        });
    }

    onChange_role = (e) => {
        const role = e.target.value;
        this.setState(function (prevState) {
            return {
                currentUser: {
                    ...prevState.currentUser,
                    choosenRole: role
                }
            };
        });
    }

    onChange_dateOfBirth = (date) => {
        this.setState({
            dateOfBirth: date
        })
    }
}