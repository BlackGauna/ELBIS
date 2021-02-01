import React, {Component} from "react";
import TopicDataService from "../../services/topic.service";
import "bootstrap/dist/css/bootstrap.min.css";
import Select from "react-select";
import {Redirect} from "react-router-dom";

// TODO: implement modal (or something else) to edit a topic
// TODO: parentTopic isn't updating yet (see onChange method)

export default class administration_editTopic extends Component {

    constructor(props){
        super(props);
        this.state = {
            currentTopic:{
                name: '',
                choosenParentTopic: ''
            },
            parentTopic: []
        }
    }

    componentDidMount() {
        this.getTopic(this.props.match.params.id);
        this.getParentTopicOptions()
    }

    async getParentTopicOptions() {
        const res = await TopicDataService.getAll()
        const data = res.data

        const options = data.map(d => ({
            "label": d.name
        }))

        this.setState({parentTopic: options})
    }

    // ####### get selected Topic

    getTopic = (id) =>{
        TopicDataService.get(id)
            .then(res => {
                this.setState({
                    currentTopic: {
                        name: res.data.name,
                        choosenParentTopic: res.data.parentTopic
                    }
                });
                console.log(res.data);
            })
            .catch (e => {
                console.log(e);
            });
    }

    // ########### parentTopic isn't updating yet (because of the onChange method)

    updateTopic = () => {
        TopicDataService.update(
            this.props.match.params.id,
            // this.state.currentTopic.id,
            this.state.currentTopic
        )
            .then(res => {
                console.log(res.data);
                this.setState({
                    message: "The topic was updated successfully!"
                });
            })
            .catch(e => {
                console.log(e);
            });

    }

    // ########## Render ###########

    render() {
        if (this.state.redirect) {
            return <Redirect to="/login/admin/manageTopics"/>
        } else {
            const {currentTopic} = this.state;
            return (
                <div>
                    {currentTopic ? (
                        <div className="container">


                            <h3>Bereich bearbeiten</h3>
                            <hr/>

                            <div className="form-group">
                                <label>Name: </label>
                                <br/>
                                <input
                                    type="name"
                                    className="form-control"
                                    defaultValue={currentTopic.name}
                                    onChange={this.onChange_name}/>
                            </div>


                            <div className="form-group">
                                <label>Elternbereich: </label>
                                <Select
                                    placeholder={"Elternbereich auswählen..."}
                                    options={this.state.parentTopic}
                                    onChange={this.onChange_parentTopic}
                                    />
                            </div>



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

    // onChange methods

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

    // TODO: parentTopic isn't updating yet
    onChange_parentTopic = (e) => {
        this.setState({
            choosenParentTopic: e.label
        })
    }

}