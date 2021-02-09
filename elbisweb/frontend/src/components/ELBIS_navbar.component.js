import React, {Component} from 'react';
import "bootstrap/dist/css/bootstrap.min.css";
import {Nav, Navbar, Button, NavDropdown} from "react-bootstrap";
import loggedUser from "../session/loggedUser";
import {ROLE} from "../session/userRoles.ice";
import SessionDataService from "../services/session.service";

export default class NavBar extends Component {
    render() {
        let navPublicStart, navPublicEnd, navLoginStart, navLoginEnd, navUser, navModerator, navAdmin;

        navAdmin =
            <div>
                <NavDropdown title="Administration" id="administrationDropdown">
                    <NavDropdown.Item
                        href={"/login/admin/manageTopics"}>Bereichsverwaltung</NavDropdown.Item>
                </NavDropdown>
            </div>
        navModerator =
            <div>
                <NavDropdown title="Moderation" id="moderationDropdown">
                    <NavDropdown.Item href={"/login/mod/manageSubmissions"}>Neue Veröffentlichungen</NavDropdown.Item>
                    <NavDropdown.Item href={"/login/mod/manageArticles"}>Artikelverwaltung</NavDropdown.Item>
                    <NavDropdown.Item href={"/login/mod/manageUsers"}>Benutzerverwaltung</NavDropdown.Item>
                </NavDropdown>
            </div>
        navUser =
            <div>
                <NavDropdown title="Mein Bereich" id="userDropdown">
                    <NavDropdown.Item href={"/login/manageAccount"}>Account verwalten</NavDropdown.Item>
                    <NavDropdown.Item href={"/login/edit"}>Artikel erstellen</NavDropdown.Item>
                    <NavDropdown.Item href={"/login/user/myArticles"}>Meine Artikel</NavDropdown.Item>
                </NavDropdown>
            </div>
        navLoginStart =
            <div>
                <Navbar.Brand href={"/login/"}>ELBIS</Navbar.Brand>
                <Navbar.Toggle aria-controls={"basic-navbar-nav"}/>
            </div>
        navLoginEnd =
            <div className="row">
                <span className="navbar-text">
                            {sessionStorage.getItem("sessionEmail")}
                        </span>
                <Nav.Link href={"/"} onClick={() => this.doLogout()}>Abmelden</Nav.Link>
                {/*<Button href={"/"} onClick={() => this.doLogout()} variant={"outline-primary"}>Logout</Button>*/}
            </div>
        navPublicStart =
            <div>
                <div>
                    <Navbar.Brand href={"/"}>ELBIS</Navbar.Brand>
                    <Navbar.Toggle aria-controls={"basic-navbar-nav"}/>
                </div>
            </div>
        navPublicEnd =
            <div div className="row">
                <Nav.Link href={"/terminal"}>Terminal aufrufen</Nav.Link>
                <Nav.Link href={"/login"} >Anmelden</Nav.Link>
            </div>

        if (sessionStorage.getItem("sessionRole") === ROLE.ADMINISTRATOR) {
            /*************
             *   Admin Area
             * *************/
            return (
                <Navbar
                    bg="dark"
                    variant={"dark"}
                    className={"border-bottom border-light mb-3"}>
                    <Navbar.Collapse id={"basic-navbar-nav"}>
                        <Nav className={"mr-auto"}>
                            {navLoginStart}
                            {navUser}
                            {navModerator}
                            {navAdmin}
                        </Nav>
                        {navLoginEnd}
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
                    className={"border-bottom border-light mb-3"}>
                    <Navbar.Collapse id={"basic-navbar-nav"}>
                        <Nav className={"mr-auto"}>
                            {navLoginStart}
                            {navUser}
                            {navModerator}
                        </Nav>
                        {navLoginEnd}
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
                    className={"border-bottom border-light mb-3"}>
                    <Navbar.Collapse id={"basic-navbar-nav"}>
                        <Nav className={"mr-auto"}>
                            {navLoginStart}
                            {navUser}
                        </Nav>
                        {navLoginEnd}
                    </Navbar.Collapse>
                </Navbar>
            );
        } else{
            return(
                <Navbar
                    bg="dark"
                    variant={"dark"}
                    className={"border-bottom border-light mb-3"}>
                    <Navbar.Collapse id={"basic-navbar-nav"}>
                        <Nav className={"mr-auto"}>
                            {navPublicStart}
                        </Nav>
                        {navPublicEnd}
                    </Navbar.Collapse>
                </Navbar>
            )
        }
        /*else {
            return (<div>
                Please contact an admin to to assign a valid role to your account.
                <Button onClick={() => this.doLogout()} variant={"outline-primary"}>Logout</Button>
            </div>)
        }*/
    }

    async doLogout() {
        try {
            sessionStorage.setItem("sessionToken", "");
            SessionDataService.delete(sessionStorage.getItem("sessionEmail"))
                .then(res => console.log(res.data));
            loggedUser.isLoggedIn = false;
            loggedUser.loading = true;
            sessionStorage.setItem("sessionUserID", "");
            sessionStorage.setItem("sessionEmail", "");
            sessionStorage.setItem("sessionRole", "");
        } catch (e) {
            console.log("Couldn't log out " + e);
        }
    }
}

