import React, {Component} from 'react';
import {Link} from 'react-router-dom';
import "bootstrap/dist/css/bootstrap.min.css";
import {Nav, Navbar, Button, NavDropdown} from "react-bootstrap";

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
                            <NavDropdown.Item href={"/login/artikelErstellen"}>Artikel erstellen</NavDropdown.Item>
                            <NavDropdown.Item href={"/pfadzumeinenartikeln"}>Meine Artikel</NavDropdown.Item>
                        </NavDropdown>
                        <NavDropdown title="Moderation" id="moderationDropdown">
                            <NavDropdown.Item href={"/login/moderation"}>Benutzerverwaltung</NavDropdown.Item>
                            <NavDropdown.Item href={"/login/articles"}>Artikelverwaltung</NavDropdown.Item>
                        </NavDropdown>
                        <NavDropdown title="Administration" id="administrationDropdown">
                            <NavDropdown.Item href={"/login/administration"}>Bereichsverwaltung</NavDropdown.Item>
                            <NavDropdown.Item href={"/pfadzurrollenverwaltung"}>Rollenverwaltung</NavDropdown.Item>
                        </NavDropdown>
                    </Nav>
                    <Nav className={"ml-auto"}>
                        <Nav.Link href={"/eigeneskontobearbeiten"}>Mein Bereich</Nav.Link>
                    </Nav>
                    <Button href={"/"} variant={"outline-primary"}>Logout</Button>
                </Navbar.Collapse>
            </Navbar>

        );
    }
}


// old return
/*
<nav className="navbar navbar-dark bg-dark navbar-expand-lg">
                <Link to="/login/hauptseite" className="navbar-brand">ELBIS web</Link>
                <div className="collapse navbar-collapse">
                    <ul className="navbar-nav mr-auto">
                        <li className="navbar-item">
                            <Link to="/login/artikelverwaltung" className="nav-link">Artikelverwaltung</Link>
                        </li>
                        <li className="navbar-item">
                            <Link to="/login/moderation" className="nav-link">Moderation</Link>
                        </li>
                        <li className="navbar-item">
                            <Link to="/login/administration" className="nav-link">Administration</Link>
                        </li>
                        <li className="navbar-item">
                            <Link to="/login/nutzerErstellen" className="nav-link">TEST</Link>
                        </li>
                    </ul>
                </div>
                <Link to="/"><button class="btn btn-primary">Logout</button></Link>
            </nav>
 */