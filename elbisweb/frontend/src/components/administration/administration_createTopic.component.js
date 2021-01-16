import React, {Component} from 'react';
import axios from 'axios';
import "bootstrap/dist/css/bootstrap.min.css";

//##########Component imports##########

// TODO: parentTopic


export default class administration_createTopic extends Component {
    //##########Render##########
    render() {
        return (
            <div className="container">
                <h3>Bereich erstellen</h3>
                <form onSubmit={this.onSubmit}>
                    <div className="form-group">
                        <label>Name: </label>
                        <br/>
                        <input type = "text" className="form-control" value={this.state.name} onChange={this.onChange_name}/>
                    </div>
                    <div className="form-group">
                        <input type="submit" value="Bereich erstellen" className="btn btn-primary" />
                    </div>
                </form>
            </div>
        )
    }

//##########Mount method (equals initialize!)##########
    componentDidMount() {
        this.setState({
        })
    }

//##########constructor##########
    constructor(props) {
        super(props);
        //prepare all fields and make sure the functions are bound to this object
        this.onChange_name = this.onChange_name.bind(this);
        this.onSubmit = this.onSubmit.bind(this);

        this.state = {
            name: '',
            //TODO: parentTopic
        }
    }

//##########submit method##########
    onSubmit(e) {
        //don't let any other submit run
        e.preventDefault();
        //create the object
        const topic = {
            name: this.state.name,
        }

        console.log(topic)

        axios.post('http://localhost:5000/topic/add', topic)
            .then(res => console.log(res.data));
    }

//##########change methods##########
    onChange_name(e) {
        this.setState({
            name: e.target.value
        })
    }

    //TODO: parentTopic

}