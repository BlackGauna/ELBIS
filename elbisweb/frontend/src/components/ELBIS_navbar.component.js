import React, {Component} from 'react';
import "bootstrap/dist/css/bootstrap.min.css";
import {Nav, Navbar, Button, NavDropdown, FormLabel} from "react-bootstrap";
import loggedUser from "../session/loggedUser";
import {ROLE} from "../session/userRoles.ice";
import SessionDataService from "../services/session.service";
import FormCheckLabel from "react-bootstrap/FormCheckLabel";
import FormFileLabel from "react-bootstrap/FormFileLabel";

export default class NavBar extends Component {
    render() {
        if (sessionStorage.getItem("sessionRole") === ROLE.ADMINISTRATOR) {
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
                    <Navbar.Brand href={"/login/"}>ELBIS</Navbar.Brand>
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
                            </NavDropdown>
                        </Nav>
                        <span className="navbar-text m-1">
                            {sessionStorage.getItem("sessionEmail")}
                        </span>
                        <Button href={"/"} onClick={() => this.doLogout()} variant={"outline-primary"}>Logout</Button>
                    </Navbar.Collapse>
                </Navbar>

            );
        } else if (sessionStorage.getItem("sessionRole") === ROLE.MODERATOR) {
            /*************
             *   Mod Area
             * *************/
            return (
                <Navbar
                    bg="dark"
                    variant={"dark"}
                    className={"border-bottom border-light mb-3"}
                >
                    <Navbar.Brand href={"/login/"}>ELBIS</Navbar.Brand>
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
        } else if (sessionStorage.getItem("sessionRole") === ROLE.USER) {
            /*************
             *   User Area
             * *************/
            return (
                <Navbar
                    bg="dark"
                    variant={"dark"}
                    className={"border-bottom border-light mb-3"}
                >
                    <Navbar.Brand href={"/login/"}>ELBIS</Navbar.Brand>
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
            return (<div>
                Please contact an admin to to assign a valid role to your account.
                <Button onClick={() => this.doLogout()} variant={"outline-primary"}>Logout</Button>
            </div>)
        }
    }

    async doLogout() {
        try {
            sessionStorage.setItem("sessionToken", "");
            SessionDataService.delete(sessionStorage.getItem("sessionEmail"))
                .then(res => console.log(res.data));
            loggedUser.isLoggedIn = false;
            loggedUser.loading = true;
            sessionStorage.setItem("sessionUserID",  "");
            sessionStorage.setItem("sessionEmail",  "");
            sessionStorage.setItem("sessionRole",  "");
        } catch (e) {
            console.log("Couldn't log out " + e);
        }
    }
}

