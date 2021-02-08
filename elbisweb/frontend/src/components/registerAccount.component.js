import React, {Component} from 'react';
import "bootstrap/dist/css/bootstrap.min.css";
import logo from '../resources/ELBIS_logo/ELBIS_Ausgeschrieben.svg';
import DatePicker from "react-datepicker";
import {Button, Col, Container, FormLabel, Row} from "react-bootstrap";
import Select from "react-select";
import {GENDER} from "../session/gender.ice";
import UserDataService from "../services/user.service";
import Form from "react-bootstrap/Form";

export default class registerAccount extends Component {

    constructor(props) {
        super(props);

        this.state = {
            email: '',
            password: '',
            passwordCheck: '',
            fName: '',
            lName: '',
            street: '',
            hNumber: '',
            plz: '',
            city: '',
            gender: [],
            choosenGender: '',
            dateOfBirth: new Date().toLocaleDateString(),

            stateText: '',

            passwordLength: false,
            containsNumbers: false,
            isUpperCase: false,
            containsSymbols: false,

            submitted: false
        }
    }

    componentDidMount() {
        this.getGenderOptions()
    }


    // Get gender options for dropdown
    async getGenderOptions() {
        const data = GENDER.getAll();
        const options = data.map(d => ({
            "label": d.name
        }))
        this.setState({gender: options})
    }


    // check to see if there is any number in the PW
    checkForNumbers(string){
        const matches = string.match(/\d+/g);
        this.setState({
            containsNumbers: matches != null
        });
    }

    // check PW for upper case
    checkForUpperCase(string){
        const matches = string.match(/[A-Z]/);
        this.setState({
            isUpperCase: matches != null
        });
    }

    // check PW for symbols
    checkForSymbols(string){
        const symbols = new RegExp(/[^A-Z a-z0-9]/);
        this.setState({
            containsSymbols: symbols.test(string)
        });
    }


    /********
     *
     * Render
     *
     ********/
    render() {
        let {
            password,
            passwordLength,
            containsNumbers,
            isUpperCase,
            containsSymbols
        } = this.state
        let btnStatus = !(passwordLength && containsNumbers && isUpperCase && containsSymbols);
        return (
            <div className="Register">
                <Container>
                    <Form onSubmit={this.onSubmit}> <br/><br/>
                        <img src={logo} height={150} style={{marginBlock: "-4%", marginLeft: "35%"}} alt="ELBIS"/>
                        <hr/>
                        <h2>Account erstellen</h2>
                        <br/>
                        <Form.Group size="lg" controlId="email">
                            <Form.Label>E-Mail</Form.Label>
                            <input
                                type="email"
                                className="form-control"
                                placeholder="E-Mail"
                                value={this.state.email}
                                onChange={this.onChange_email}/>
                        </Form.Group>
                        <Row>
                            <Col>
                                <Form.Group size="lg" controlId="password">
                                    <Form.Label>Passwort</Form.Label>
                                    <input
                                        type="password"
                                        className="form-control"
                                        placeholder="Passwort"
                                        value={password}
                                        onChange={this.onChange_password('password')}/>
                                    <div>
                                        <div className={passwordLength ? 'green' : null}>- Mehr als 8 Zeichen</div>
                                        <div className={containsNumbers ? 'green' : null}>- Zahlen</div>
                                        <div className={isUpperCase ? 'green' : null}>- Großbuchstaben</div>
                                        <div className={containsSymbols ? 'green' : null}>- Sonderzeichen</div>
                                    </div>
                                </Form.Group>
                            </Col>
                            <Col>
                                <Form.Group size="lg" controlId="password">
                                    <Form.Label>Passwort bestätigen</Form.Label>
                                    <input
                                        type="password"
                                        className="form-control"
                                        placeholder="Passwort"
                                        value={this.state.passwordCheck}
                                        onChange={this.onChange_passwordCheck}/>
                                </Form.Group>
                            </Col>
                        </Row>
                        <Row>
                            <Col>
                                <Form.Group size="lg" controlId="fName">
                                    <Form.Label>Vorname</Form.Label>
                                    <input
                                        type="name"
                                        className="form-control"
                                        placeholder="Vorname"
                                        value={this.state.fName}
                                        onChange={this.onChange_fName}/>
                                </Form.Group>
                            </Col>
                            <Col>
                                <Form.Group size="lg" controlId="lName">
                                    <Form.Label>Nachname</Form.Label>
                                    <input
                                        type="name"
                                        className="form-control"
                                        placeholder="Nachname"
                                        value={this.state.lName}
                                        onChange={this.onChange_lName}/>
                                </Form.Group>
                            </Col>
                        </Row>
                        <Row>
                            <Col xs={10}>
                                <Form.Group size="lg" controlId="street">
                                    <Form.Label>Straße</Form.Label>
                                    <input
                                        type="street"
                                        className="form-control"
                                        placeholder="Straße"
                                        value={this.state.street}
                                        onChange={this.onChange_street}/>
                                </Form.Group>
                            </Col>
                            <Col>
                                <Form.Group size="lg" controlId="hNumber">
                                    <Form.Label>Hausnummer</Form.Label>
                                    <input
                                        type="houseNumber"
                                        className="form-control"
                                        placeholder="Hausnummer"
                                        value={this.state.hNumber}
                                        onChange={this.onChange_hNumber}/>
                                </Form.Group>
                            </Col>
                        </Row>
                        <Row>
                            <Col>
                                <Form.Group size="lg" controlId="plz">
                                    <Form.Label>PLZ</Form.Label>
                                    <input
                                        type="plz"
                                        className="form-control"
                                        placeholder="PLZ"
                                        value={this.state.plz}
                                        onChange={this.onChange_plz}/>
                                </Form.Group>
                            </Col>
                            <Col xs={10}>
                                <Form.Group size="lg" controlId="city">
                                    <Form.Label>Ort</Form.Label>
                                    <input
                                        type="ort"
                                        className="form-control"
                                        placeholder="Ort"
                                        value={this.state.city}
                                        onChange={this.onChange_city}/>
                                </Form.Group>
                            </Col>
                        </Row>
                        <Row>
                            <Col>
                                <Form.Group size="lg" controlId="bday">
                                    <Form.Label>Geburtsdatum</Form.Label>
                                    <br/>
                                    <DatePicker
                                        selected={new Date()}
                                        onChange={this.onChange_dateOfBirth}
                                        peekNextMonth
                                        showMonthDropdown
                                        showYearDropdown
                                        dropdownMode="select"
                                        customInput={<Button variant="outline-secondary" size="lg"
                                                             block>{this.state.dateOfBirth}</Button>}
                                    />
                                </Form.Group>
                            </Col>
                            <Col>
                                <Form.Group size="lg" controlId="gender">
                                    <Form.Label>Geschlecht</Form.Label>
                                    <Select
                                        type="geschlecht"
                                        placeholder="Geschlecht auswählen..."
                                        options={this.state.gender}
                                        onChange={this.onChange_gender}/>
                                </Form.Group>
                            </Col>
                        </Row>

                        <br/>
                        <input type="submit" disabled={btnStatus} value="Account erstellen" className="btn btn-primary"/>

                        <br/>
                        <br/>
                        <br/>

                    </Form>

                </Container>


            </div>
        )
    }

    /********
     *
     * Send user to DB
     *
     ********/
    onSubmit= (e)=> {
        //don't let any other submit run
        e.preventDefault();
        //create the object
        if (this.state.email) {
            if (this.state.password) {
                if (this.state.password === this.state.passwordCheck) {
                    const user = {
                        email: this.state.email,
                        password: this.state.password,
                        fName: this.state.fName,
                        lName: this.state.lName,
                        street: this.state.street,
                        hNumber: this.state.hNumber,
                        plz: this.state.plz,
                        city: this.state.city,
                        gender: this.state.choosenGender,
                        dateOfBirth: this.state.dateOfBirth,
                    }

                    UserDataService.create(user)
                        .then(res => {
                            this.setState({
                                email: res.data.email,
                                password: res.data.password,
                                fName: res.data.fName,
                                lName: res.data.lName,
                                street: res.data.street,
                                hNumber: res.data.hNumber,
                                plz: res.data.plz,
                                city: res.data.city,
                                gender: res.data.gender,
                                dateOfBirth: res.data.dateOfBirth,
                                role: "Nutzer",

                                submitted: true
                            });
                            console.log(res.data);
                        })
                        .catch(e => {
                            console.log(e);
                        });
                } else {
                    this.setState({
                        passwordCheck: '',
                        stateText: 'Die Passwörter stimmen nicht überein.'
                    })
                }
            } else {
                this.setState({
                    stateText: 'Geben Sie ein Passwort ein'
                })
            }
        } else {
            this.setState({
                stateText: 'Geben Sie eine E-Mail-Addresse ein'
            })
        }
    }


    /********
     *
     * Change methods
     *
     ********/
    onChange_email=(e)=> {
        this.setState({
            email: e.target.value
        })
    }

    onChange_password= input => e => {
        let targetValue = e.target.value.replace(/\s/g, '');
        this.checkForNumbers(targetValue);
        this.checkForUpperCase(targetValue);
        this.checkForSymbols(targetValue);
        this.setState({
            [input]: targetValue,
            passwordLength: targetValue.length > 7
        });
    }

    onChange_passwordCheck=(e)=> {
        this.setState({
            passwordCheck: e.target.value
        })
    }

    onChange_fName=(e)=> {
        this.setState({
            fName: e.target.value
        })
    }

    onChange_lName=(e)=> {
        this.setState({
            lName: e.target.value
        })
    }

    onChange_street=(e)=> {
        this.setState({
            street: e.target.value
        })
    }

    onChange_hNumber=(e)=> {
        this.setState({
            hNumber: e.target.value
        })
    }

    onChange_plz=(e)=> {
        this.setState({
            plz: e.target.value
        })
    }

    onChange_city=(e)=> {
        this.setState({
            city: e.target.value
        })
    }

    onChange_gender=(e)=> {
        this.setState({
            choosenGender: e.label
        })
    }

    onChange_dateOfBirth = (date) => {
        const myDate = date.toLocaleDateString()
        this.setState({
            dateOfBirth: myDate
        })
    }

}