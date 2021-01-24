import React, {Component} from 'react';
import "bootstrap/dist/css/bootstrap.min.css";
import {Nav, Navbar, Button, NavDropdown} from "react-bootstrap";
import loggedUser from "../session/loggedUser";

export default class NavBar extends Component{

    render() {
        return (
            <Navbar
                bg="dark"
                variant={"dark"}
                className={"border-bottom border-light mb-3"}
            >
                <Navbar.Brand href={"/login/hauptseite"}>ELBIS</Navbar.Brand>
                <Navbar.Toggle aria-controls={"basic-navbar-nav"} />
                <Navbar.Collapse id={"basic-navbar-nav"}>
                    <Nav className={"mr-auto"}>
                        <NavDropdown title="Artikelverwaltung" id="moderationDropdown">
                            <NavDropdown.Item href={"/login/edit"}>Artikel erstellen</NavDropdown.Item>
                            <NavDropdown.Item href={"/login/pfadzumeinenartikeln"}>Meine Artikel</NavDropdown.Item>
                        </NavDropdown>
                        <NavDropdown title="Moderation" id="moderationDropdown">
                            <NavDropdown.Item href={"/login/moderation"}>Benutzerverwaltung</NavDropdown.Item>
                            <NavDropdown.Item href={"/login/articles"}>Artikelverwaltung</NavDropdown.Item>
                        </NavDropdown>
                        <NavDropdown title="Administration" id="administrationDropdown">
                            <NavDropdown.Item href={"/login/administration"}>Bereichsverwaltung</NavDropdown.Item>
                            <NavDropdown.Item href={"/login/pfadzurrollenverwaltung"}>Rollenverwaltung</NavDropdown.Item>
                        </NavDropdown>
                    </Nav>
                    <Nav className={"ml-auto"}>
                        <Nav.Link href={"/login/eigeneskontobearbeiten"}>Mein Bereich</Nav.Link>
                    </Nav>
                    <Button href={"/"} onClick={() =>this.doLogout} variant={"outline-primary"}>Logout</Button>
                </Navbar.Collapse>
            </Navbar>

        );
    }

    async doLogout() {
        try {
            let res = await fetch('/isLoggedOut', {
                method: 'post',
                headers: {
                    'Accept': 'application/json',
                    'Content-Type': 'application/json'
                }
            });
            let result = await res.json();

            if (result && result.success) {
                loggedUser.isLoggedIn = false;
                loggedUser.eMail = '';
                loggedUser.role = '';
            }
        } catch (e) {
            console.log("Logout Error! " + e);
        }
    }
}
