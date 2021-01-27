import React, {Component} from 'react';
import "bootstrap/dist/css/bootstrap.min.css";
import {Nav, Navbar, Button, NavDropdown, FormLabel} from "react-bootstrap";
import loggedUser from "../session/loggedUser";
import SessionDataService from "../services/session.service";
import FormCheckLabel from "react-bootstrap/FormCheckLabel";
import FormFileLabel from "react-bootstrap/FormFileLabel";

export default class NavBar extends Component {
//reverted changes//
    render() {

        if (sessionStorage.getItem("sessionRole") === "Administrator") {
            //TODO resolve redundancies
            /*************
             *   Admin Area
             * *************/
            return (
                <Navbar
                    bg="dark"
                    variant={"dark"}
                    className={"border-bottom border-light mb-3"}
                >
                    <Navbar.Brand href={"/login/home"}>ELBIS</Navbar.Brand>
                    <Navbar.Toggle aria-controls={"basic-navbar-nav"}/>
                    <Navbar.Collapse id={"basic-navbar-nav"}>
                        <Nav className={"mr-auto"}>
                            <NavDropdown title="Mein Bereich" id="userDropdown">
                                <NavDropdown.Item href={"/login/manageAccount"}>Account verwalten</NavDropdown.Item>
                            </NavDropdown>
                            <NavDropdown title="Artikelverwaltung" id="userDropdown">
                                <NavDropdown.Item href={"/login/edit"}>Artikel erstellen</NavDropdown.Item>
                                <NavDropdown.Item href={"/login/user/myArticles"}>Meine Artikel</NavDropdown.Item>
                            </NavDropdown>
                            <NavDropdown title="Moderation" id="moderationDropdown">
                                <NavDropdown.Item href={"/login/mod/manageSubmissions"}>Neue Veröffentlichungen</NavDropdown.Item>
                                <NavDropdown.Item href={"/login/mod/manageArticles"}>Artikelverwaltung</NavDropdown.Item>
                                <NavDropdown.Item href={"/login/mod/manageUsers"}>Benutzerverwaltung</NavDropdown.Item>
                            </NavDropdown>
                            <NavDropdown title="Administration" id="administrationDropdown">
                                <NavDropdown.Item
                                    href={"/login/admin/manageTopics"}>Bereichsverwaltung</NavDropdown.Item>
                                <NavDropdown.Item href={"/login/admin/manageRoles"}>Rollenverwaltung</NavDropdown.Item>
                            </NavDropdown>
                        </Nav>
                        <span className="navbar-text m-1">
                            {sessionStorage.getItem("sessionEmail")}
                        </span>
                        <Button href={"/"} onClick={() => this.doLogout()} variant={"outline-primary"}>Logout</Button>
                    </Navbar.Collapse>
                </Navbar>

            );
        } else if (sessionStorage.getItem("sessionRole") === "Moderator") {
            /*************
             *   Mod Area
             * *************/
            return (
                <Navbar
                    bg="dark"
                    variant={"dark"}
                    className={"border-bottom border-light mb-3"}
                >
                    <Navbar.Brand href={"/login/home"}>ELBIS</Navbar.Brand>
                    <Navbar.Toggle aria-controls={"basic-navbar-nav"}/>
                    <Navbar.Collapse id={"basic-navbar-nav"}>
                        <Nav className={"mr-auto"}>
                            <NavDropdown title="Mein Bereich" id="userDropdown">
                                <NavDropdown.Item href={"/login/manageAccount"}>Account verwalten</NavDropdown.Item>
                            </NavDropdown>
                            <NavDropdown title="Artikelverwaltung" id="userDropdown">
                                <NavDropdown.Item href={"/login/user/myArticles"}>Meine Artikel</NavDropdown.Item>
                                <NavDropdown.Item href={"/login/edit"}>Artikel erstellen</NavDropdown.Item>
                            </NavDropdown>
                            <NavDropdown title="Moderation" id="moderationDropdown">
                                <NavDropdown.Item href={"/login/mod/manageSubmissions"}>Neue Veröffentlichungen</NavDropdown.Item>
                                <NavDropdown.Item href={"/login/mod/manageUsers"}>Benutzerverwaltung</NavDropdown.Item>
                                <NavDropdown.Item href={"/login/mod/manageArticles"}>Artikelverwaltung</NavDropdown.Item>
                            </NavDropdown>
                        </Nav>
                        <span className="navbar-text m-1">
                            {sessionStorage.getItem("sessionEmail")}
                        </span>
                        <Button href={"/"} onClick={() => this.doLogout()} variant={"outline-primary"}>Logout</Button>
                    </Navbar.Collapse>
                </Navbar>

            );
        } else if (sessionStorage.getItem("sessionRole") === "User") {
            /*************
             *   User Area
             * *************/
            return (
                <Navbar
                    bg="dark"
                    variant={"dark"}
                    className={"border-bottom border-light mb-3"}
                >
                    <Navbar.Brand href={"/login/home"}>ELBIS</Navbar.Brand>
                    <Navbar.Toggle aria-controls={"basic-navbar-nav"}/>
                    <Navbar.Collapse id={"basic-navbar-nav"}>
                        <Nav className={"mr-auto"}>
                            <NavDropdown title="Mein Bereich" id="userDropdown">
                                <NavDropdown.Item href={"/login/manageAccount"}>Account verwalten</NavDropdown.Item>
                            </NavDropdown>
                            <NavDropdown title="Artikelverwaltung" id="userDropdown">
                                <NavDropdown.Item href={"/login/edit"}>Artikel erstellen</NavDropdown.Item>
                                <NavDropdown.Item href={"/login/user/myArticles"}>Meine Artikel</NavDropdown.Item>
                            </NavDropdown>
                        </Nav>
                        <span className="navbar-text m-1">
                            {sessionStorage.getItem("sessionEmail")}
                        </span>
                        <Button href={"/"} onClick={() => this.doLogout()} variant={"outline-primary"}>Logout</Button>
                    </Navbar.Collapse>
                </Navbar>

            );
        } else {
        }
    }

    async doLogout() {
        try {
            SessionDataService.delete(sessionStorage.getItem("sessionEmail"))
                .then(res => console.log(res.data));
            loggedUser.isLoggedIn = false;
            loggedUser.loading = true;
        } catch (e) {
            console.log("Couldn't log out " + e);
        }
    }
}

