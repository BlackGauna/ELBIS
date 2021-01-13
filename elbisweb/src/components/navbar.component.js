import React, {Component} from 'react';
import {Link} from 'react-router-dom';

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
                    </ul>
                </div>
                <Link to="/logout"><button variant="outline-info">Logout</button></Link>
            </nav>
        );
    }
}
