import React, {Component} from "react";
import TopicDataService from "../../services/topic.service";
import "bootstrap/dist/css/bootstrap.min.css";
import Select from "react-select";
import {Button} from "react-bootstrap";

// TODO: implement modal (or something else) to edit a topic
// TODO: parentTopic isn't updating yet (see onChange method)

export default class administration_editTopic extends Component {
    /********
     *
     * Constructor
     *
     ********/
    constructor(props) {
        super(props);
        this.state = {
            //this topic was given by the topictable (only used to load the topic from the db by id)
            givenTopicByTable: this.props.topic,
            //in form: please use the loaded topic(currentTopic) down below due to its loaded directly from the db and might store newer information
            currentTopic: {
                id:'',
                name: '',
                choosenParentTopic: ''
            },
            parentTopic: [],
            toggleParent: false,
            submitted: false
        }
        console.log("Opened EDITTOPIC with: ")
        console.log(this.props.topic)
    }

    /********
     *
     * Mounting
     *
     ********/
    componentDidMount() {
        //this.getTopic(this.props.match.params.id);
        this.getTopic(this.props.topic._id);
        this.getParentTopicOptions()
    }

    /********
     *
     * Load topic and parents from DB
     *
     ********/
    getTopic = (id) => {
        TopicDataService.get(id)
            .then(res => {
                this.setState({
                    currentTopic: {
                        id: res.data._id,
                        name: res.data.name,
                        choosenParentTopic: res.data.parentTopic
                    }

                });
                console.log("Loaded Topic from DB:");
                console.log(res.data);
            })
            .catch(e => {
                console.log(e);
            });
    }

    async getParentTopicOptions() {
        const res = await TopicDataService.getAll()
        const data = res.data

        const options = data.map(d => ({
            "label": d.name
        }))

        this.setState({parentTopic: options})
    }

    /********
     *
     * update topic in DB
     *
     ********/
        //TODO
    updateTopic = () => {
        const {currentTopic} = this.state
        console.log(currentTopic)
        TopicDataService.update(
            currentTopic.id, {
                name: currentTopic.name,
                parentTopic: currentTopic.choosenParentTopic
            }
        )
            .then(res => {
                console.log(res.data);
                this.setState({
                    message: "The topic was updated successfully!",
                    submitted: true
                });
            })
            .catch(e => {
                console.log(e);
            });

    }

    /********
     *
     * Render
     *
     ********/
    render() {
        if(this.state.submitted){
            //TODO test if works for everyone
            window.location.reload();
        }
        else {
            const {currentTopic} = this.state;
            const {parentTopic} = this.state;
            const {toggleParent} = this.state;
            let nameField =
                <div className="form-group">
                    <label>Name: </label>
                    <br/>
                    <input
                        type="name"
                        className="form-control"
                        defaultValue={currentTopic.name}
                        onChange={this.onChange_name}/>
                </div>
            let chooseParent
            if (toggleParent) {
                chooseParent =
                    <div className="form-group">
                        <label>Elternbereich: </label>
                        <Select
                            placeholder={"Elternbereich auswählen..."}
                            options={parentTopic}
                            onChange={this.onChange_parentTopic}
                        />
                    </div>
            } else {
                chooseParent =
                    <div className="form-group">
                        <label>Elternbereich: </label> <br/>
                        <div className={"container"}>
                            <label style={{
                                fontWeight: "700",
                                fontSize: "20px",
                                background: "none",
                                textAlign: "left",
                                marginLeft: "-14px"
                            }}> {currentTopic.choosenParentTopic} </label>
                        </div>
                        <Button variant="primary"
                                size='sm'
                                onClick={this.toggle_parentTopic}>Elternbereich ändern</Button>

                    </div>
            }

            return (
                <div>
                    {currentTopic ? (
                        <div className="container">
                            {nameField}
                            {chooseParent}
                            <br/>
                            <div className="form-group">
                                <input
                                    type="submit"
                                    value="Bestätigen"
                                    className="btn btn-primary"
                                    onClick={this.updateTopic}/>
                            </div>
                        </div>
                    ) : (
                        <div>
                            <p>Test...</p>
                        </div>
                    )}
                </div>
            );
        }
    }

    /********
     *
     * Changefunctions
     *
     ********/
    onChange_name = (e) => {
        const name = e.target.value;
        this.setState(function (prevState) {
            return {
                currentTopic: {
                    ...prevState.currentTopic,
                    name: name
                }
            };
        });
    }

    onChange_parentTopic = (e) => {
        const choosen = e.label;
        this.setState(function (prevState) {
            return {
                currentTopic: {
                    ...prevState.currentTopic,
                    choosenParentTopic: choosen
                }
            };
        });
    }
    toggle_parentTopic = () => {
        let {toggleParent} = this.state;
        toggleParent = !toggleParent;
        this.setState({toggleParent: toggleParent})
        //this.getUser(this.state.currentUser.id)
    }

}