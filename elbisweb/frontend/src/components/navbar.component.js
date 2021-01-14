import React, {Component} from 'react';
import {Link} from 'react-router-dom';
import "bootstrap/dist/css/bootstrap.min.css";

export default class Navbar extends Component{

    render() {
        return (
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
        );
    }
}