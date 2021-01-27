import React, {Component} from 'react';
import "bootstrap/dist/css/bootstrap.min.css";
import {Nav, Navbar, Button, NavDropdown, FormLabel} from "react-bootstrap";
import loggedUser from "../session/loggedUser";
import SessionDataService from "../services/session.service";
import FormCheckLabel from "react-bootstrap/FormCheckLabel";
import FormFileLabel from "react-bootstrap/FormFileLabel";
import {BrowserRouter as Router, Route} from "react-router-dom";
import ELBISweb from "./index.component";
import manageAccount from "./manageAccount.component";
import CreateArticle from "./user/CreateArticle.component";
import userView from "./user/user_myArticles.Component";
import manageSubmissions from "./moderation/moderation_submissionList.component";
import allArticlesList from "./moderation/moderation_articleList.component";
import moderation_userList from "./moderation/moderation_userList.component";
import createUser from "./moderation/moderation_createUser.component";
import editUser from "./moderation/moderation_editUser.component";
import administration_topicList from "./administration/administration_topicList.component";
import createTopic from "./administration/administration_createTopic.component";
import manageRoles from "./administration/administration_userList.component";

export default class NavBar extends Component {

    render() {
        if (sessionStorage.getItem("sessionRole") === "Administrator") {
            //TODO resolve redundancies
            /*************
             *   Admin Area
             * *************/
            return (
                <div className="app">
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
            <Router>
                <Route exact path="/login/home" component={ELBISweb}/>
                <Route exact path="/login/manageAccount" component={manageAccount}/>
                <Route exact path="/login/edit" component={CreateArticle}/>
                <Route exact path="/login/edit/:id" component={CreateArticle}/>
                <Route exact path="/login/user/myArticles" component={userView}/>
                <Route exact path="/login/mod/manageSubmissions" component={manageSubmissions}/>
                <Route exact path="/login/mod/manageArticles" component={allArticlesList}/>
                <Route exact path="/login/mod/manageUsers" component={moderation_userList}/>
                <Route exact path="/login/mod/createUser" component={createUser}/>
                <Route exact path="/login/mod/editUser/:id" component={editUser}/>
                <Route exact path="/login/admin/manageTopics" component={administration_topicList}/>
                <Route exact path="/login/admin/createTopic" component={createTopic}/>
                <Route exact path="/login/admin/manageRoles" component={manageRoles}/>
            </Router>
                </div>
            );
        } else if (sessionStorage.getItem("sessionRole") === "Moderator") {
            /*************
             *   Mod Area
             * *************/
            return (
                <div className="app">
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
                    <Router>
                        <Route exact path="/login/home" component={ELBISweb}/>
                        <Route exact path="/login/manageAccount" component={manageAccount}/>
                        <Route exact path="/login/edit" component={CreateArticle}/>
                        <Route exact path="/login/edit/:id" component={CreateArticle}/>
                        <Route exact path="/login/user/myArticles" component={userView}/>
                        <Route exact path="/login/mod/manageSubmissions" component={manageSubmissions}/>
                        <Route exact path="/login/mod/manageArticles" component={allArticlesList}/>
                        <Route exact path="/login/mod/manageUsers" component={moderation_userList}/>
                        <Route exact path="/login/mod/createUser" component={createUser}/>
                        <Route exact path="/login/mod/editUser/:id" component={editUser}/>
                    </Router>
                </div>
            );
        } else if (sessionStorage.getItem("sessionRole") === "User") {
            /*************
             *   User Area
             * *************/
            return (
                <div className="app">
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
                    <Router>
                        <br/>
                        <Route exact path="/login/home" component={ELBISweb}/>
                        <Route exact path="/login/manageAccount" component={manageAccount}/>
                        <Route exact path="/login/edit" component={CreateArticle}/>
                        <Route exact path="/login/edit/:id" component={CreateArticle}/>
                        <Route exact path="/login/user/myArticles" component={userView}/>
                    </Router>
                </div>
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

