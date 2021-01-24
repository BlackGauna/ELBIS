import React from 'react';
import {Form} from "react-bootstrap";

class ELBIS_loginInputfield extends React.Component {

    render() {
        return (
            <Form.Control
                type={this.props.type}
                size='sm'
                placeholder={this.props.placeholder}
                value={this.props.value}
                onChange={(e) => this.props.onChange(e.target.value)}
            />
        );
    }
}

export default ELBIS_loginInputfield;