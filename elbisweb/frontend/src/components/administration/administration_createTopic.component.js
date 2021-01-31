import React, {Component} from 'react';
import "bootstrap/dist/css/bootstrap.min.css";
import TopicDataService from "../../services/topic.service";
import Select from 'react-select';

//##########Component imports##########

export default class administration_createTopic extends Component {
    //##########constructor##########
    constructor(props) {
        super(props);
        //prepare all fields and make sure the functions are bound to this object
        this.onChange_name = this.onChange_name.bind(this);
        this.onChange_parentTopic = this.onChange_parentTopic.bind(this);
        this.onSubmit = this.onSubmit.bind(this);

        this.state = {
            name: '',
            parentTopic: [],
            choosenParentTopic: '',

            submitted: false
        }
    }

    // Get topic options for dropdown
    async getParentTopicOptions() {
        const res = await TopicDataService.getAll()
        // const res = await TopicDataService.get("6002e1e5b319cf32d4e0c504");
        const data = res.data


        const options = data.map(d => ({
            "label": d.name
        }))

        this.setState({parentTopic: options})
    }

    //##########Mount method (equals initialize!)##########
    componentDidMount() {
        this.getParentTopicOptions()
    }

    //##########Render##########
    render() {
        return (
            <div className="container">
                <h3>Bereich erstellen</h3>
                <br/>
                <form onSubmit={this.onSubmit}>
                    <div className="form-group">
                        <label>Name: </label>
                        <br/>
                        <input type="name" className="form-control" value={this.state.name}
                               onChange={this.onChange_name}/>
                    </div>
                    <div className="form-group">
                        <label>Elternbereich: </label>
                        <Select
                            placeholder="Elternbereich auswählen..."
                            options={this.state.parentTopic}
                            onChange={this.onChange_parentTopic}/>
                    </div>
                    <div className="form-group">
                        <input type="submit" value="Bereich erstellen" className="btn btn-primary"/>
                    </div>
                </form>
            </div>
        )
    }

    //##########submit method##########
    onSubmit(e) {
        //don't let any other submit run
        e.preventDefault();
        //create the object
        const topic = {
            name: this.state.name,
            parentTopic: this.state.choosenGender
        }

        TopicDataService.create(topic)
            .then(res => {
                this.setState({
                    name: res.data.name,
                    parentTopic: res.data.parentTopic,

                    submitted: true
                });
                console.log(res.data);
            })
            .catch(e => {
                console.log(e);
            });
    }

    //##########change methods##########
    onChange_name(e) {
        this.setState({
            name: e.target.value
        })
    }

    onChange_parentTopic(e) {
        this.setState({
            choosenParentTopic: e.label
        })
    }

}