import React, {Component} from 'react';
import "bootstrap/dist/css/bootstrap.min.css";
import TopicDataService from "../../services/topic.service";
import EditIcon from '@material-ui/icons/Edit';
import DeleteIcon from '@material-ui/icons/Delete';
import IconButton from '@material-ui/core/IconButton';
import Modal from "react-bootstrap/Modal";
import AddTopic from '../administration/administration_createTopic.component';

const Topic = props => (
    <tr>
        <td>{props.topic.name}</td>
        <td>{props.topic.parentTopic}</td>
        <td align="right">
            <IconButton aria-label="edit" href={"/login/admin/editTopic/" + props.topic._id}>
                <EditIcon/>
            </IconButton>
            <IconButton aria-label="delete" href='#' onClick={() => {
                props.deleteTopic(props.topic._id)
            }}>
                <DeleteIcon/>
            </IconButton>
        </td>
    </tr>
)

export default class administration_topicList extends Component {
    // Constructor
    constructor(props) {
        super(props);

        this.deleteTopic = this.deleteTopic.bind(this);
        this.state = {
            topic: [],
            showCreateTopic: false
        };
    }

    handleModal(){
        this.setState({showCreateTopic:!this.state.showCreateTopic});
    }

    // Mount method
    componentDidMount() {
        TopicDataService.getAll()
            .then(response => {
                this.setState({topic: response.data})
            })
            .catch((error) => {
                console.log(error);
            })
    }

    // Delete topic
    deleteTopic(id) {
        TopicDataService.delete(id)
            .then(res => console.log(res.data));
        this.setState({
            topic: this.state.topic.filter(el => el._id !== id)
        })
    }

    // get topicList
    topicList() {
        return this.state.topic.map(currentTopic => {
            return <Topic topic={currentTopic} deleteTopic={this.deleteTopic} key={currentTopic._id}/>;
        })
    }

    render() {
        return (
            <div className="container">
                <div className='ElbisTable'>
                    <h3>Bereichsverwaltung</h3>
                    <table className="topicTable table">
                        <thead className="thead-light">
                        <tr>
                            <th>Name</th>
                            <th>Elternbereich</th>

                            <th className={"text-right"}>
                                <button className="btn btn-primary btn-sm" onClick={()=>{this.handleModal()}}>+</button>
                            </th>

                            <Modal show={this.state.showCreateTopic} onHide={()=>this.handleModal()} size="lg">
                                <Modal.Body>
                                    <AddTopic/>
                                </Modal.Body>
                                <Modal.Footer>
                                    <button className="btn btn-primary btn-sm" onClick={
                                        ()=>{this.handleModal()
                                        }}>Close
                                    </button>
                                </Modal.Footer>
                            </Modal>

                        </tr>
                        </thead>
                        <tbody>
                        {this.topicList()}
                        </tbody>
                    </table>
                </div>
            </div>
        )
    }
}