import React, {Component} from 'react';
import {Link} from 'react-router-dom';

export default class Navbar extends Component{

    render() {
        return (
            <nav className="navbar navbar-dark bg-dark navbar-expand-lg">
                <Link to="/" className="navbar-brand">ELBIS</Link>
                <div className="collapse navbar-collapse">
                    <ul className="navbar-nav mr-auto">
                        <li className="navbar-item">
                            <Link to="/user" className="nav-link">Users</Link>
                        </li>
                    </ul>
                </div>
            </nav>
        );
    }



}
