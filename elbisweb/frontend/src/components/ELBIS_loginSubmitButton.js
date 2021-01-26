import React from 'react';
import {Button} from "react-bootstrap";

class ELBIS_loginSubmitButton extends React.Component {

    render() {
        return (
            <div className="submitButton">
                <Button
                    className="mb-1"
                    disabled={this.props.disabled}
                    onClick={() => this.props.onClick()}
                >
                    {this.props.text}
                </Button>
            </div>
        );
    }
}

export default ELBIS_loginSubmitButton;