import React, {Component} from 'react';
import {Link} from 'react-router-dom';
import axios from 'axios';
import "bootstrap/dist/css/bootstrap.min.css";

const Topic = props => (
    <tr>
        <td>{props.topic.name}</td>
        <td>{props.topic.parentTopic}</td>
        <td align="right">
            <Link to={"/edit/"+props.topic._id}>bearbeiten</Link> | <a href='#' onClick={() => {props.deleteTopic(props.topic._id)}}>löschen</a>
        </td>
    </tr>
)

export default class administration_topicListComponent extends Component {
    constructor(props) {
        super(props);

        this.deleteTopic = this.deleteTopic.bind(this);
        this.state = {topic: []};
    }

    componentDidMount() {
        axios.get('http://localhost:5000/topic/')
            .then(response => {
                this.setState({topic: response.data})
            })
            .catch((error) => {
                console.log(error);
            })
    }

    deleteTopic(id){
        axios.delete('http://localhost:5000/topic/'+id)
            .then(res => console.log(res.data));
        this.setState({
            topic: this.state.topic.filter(el => el._id !== id)
        })
    }

    topicList(){
        return this.state.topic.map(currentTopic => {
            return <Topic topic={currentTopic} deleteTopic={this.deleteTopic} key={currentTopic._id}/>;
        })
    }

    render(){
        return (
            <div className='ElbisTable'>
                <h3>Bereichsverwaltung</h3>
                <table className="topicTable table">
                    <thead className="thead-light">
                    <tr>
                        <th>Name</th>
                        <th>Elternbereich</th>
                        <th className={"text-right"}><Link to="/login/bereichErstellen"><button className="btn btn-primary btn-sm" onClick="reload">+</button></Link></th>
                    </tr>
                    </thead>
                    <tbody>
                    {this.topicList()}
                    </tbody>
                </table>
            </div>
        )
    }
}