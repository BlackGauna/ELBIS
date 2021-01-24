import React from 'react';
import {Button} from "react-bootstrap";

class ELBIS_loginSubmitButton extends React.Component {

    render() {
        return (
            <Button
                type="submit"
                className="mb-1"
                disabled={this.props.disabled}
                onClick={() => this.props.onClick()}
            >
                {this.props.text}
            </Button>
        );
    }
}

export default ELBIS_loginSubmitButton;