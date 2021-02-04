import React, {Component} from "react";
import UserDataService from "../../services/user.service";
import {Form} from "react-bootstrap";
import Select from 'react-select';
import {TextareaAutosize} from "@material-ui/core";
import {ARTICLESTATUS} from "../../session/articleStatus.ice";
import ArticleDataService from "../../services/article.service";

export default class handleSubmission extends Component {
    //TODO update comment, state and publisher at submit
    /********
     *
     * Constructor
     *
     ********/
    constructor(props) {
        super(props);
        this.state = {
            myArticle: this.props.article,
            submissionOptions: [],
        }
        console.log("HandleSubmission opened with: ")
        console.log(this.state.myArticle)
    }

    /********
     *
     * Mounting
     *
     ********/
    componentDidMount() {
        this.getSubmissionOptions()
        //update the publisher with the current logged user mail
        const newPublisher = sessionStorage.getItem("sessionEmail");
        this.setState(
            function (prevState) {
                return {
                    myArticle: {
                        ...prevState.myArticle,
                        publisher: newPublisher
                    }
                };
            }
        )
    }

    async getSubmissionOptions() {
        const data = ARTICLESTATUS.getSubmissionOptions();
        const options = data.map(d => ({
            "label": d.name,
        }))
        this.setState({submissionOptions: options})
    }

    /********
     *
     * other
     *
     ********/
    onSubmit = (e) => {
        //TODO close modal somehow
        if (this.state.myArticle.comment === '') {
            window.alert("Bitte geben sie einen Kommentar ein.")
        } else {
            if (this.state.myArticle.status === 'Eingereicht') {
                window.alert("Bitte wählen sie einen neuen Status.")
            } else {
                if (window.confirm('Der Status dieses Artikels wird nun auf "' + this.state.myArticle.status + '" gesetzt. Fortfahren?')) {
                    console.log("confirmed and sending article " + this.state.myArticle._id)
                    console.log(this.state.myArticle)
                    e.preventDefault();
                    //ARTICLE UPDATE
                    //TODO error on update article function (other payload-model used in controller function???)
                    ArticleDataService.update(
                        this.state.myArticle._id, this.state.myArticle)
                        .then(response => {
                            console.log(response.data);
                            this.setState({
                                message: "The article was updated successfully!"
                            });
                        })
                        .catch(e => {
                            console.log(e);
                        });


                } else {
                    console.log("back")
                }
            }
        }

    }

    /********
     *
     * Render
     *
     ********/
//TODO add comment field and two buttons (decline/authorize)
    render() {
        return (
            <div>
                <form onSubmit={this.onSubmit}>
                    <h6>Kommentar verfassen</h6>
                    {/*Kommentarrow*/}
                    <div className="form-row">
                        <div className="form-group col-md-12">
                    <textarea
                        className="form-control"
                        id="comment"
                        rows="3"
                        onChange={this.onChange_comment}/>

                        </div>
                        <br/>
                    </div>
                    {/*Submissionoptionrow*/}
                    <div className="form-row">
                        <div className="form-group col-md-5">
                            <Select
                                type="geschlecht"
                                placeholder={this.state.myArticle.status}
                                options={this.state.submissionOptions}
                                onChange={this.onChange_submissionOption}/>
                        </div>
                        <br/>
                    </div>

                    {/*Bestätigen*/}
                    <div className="form-row">
                        <div className="form-group col-md-5">
                            <input type="submit" value="Absenden" className="btn btn-primary"/>
                        </div>
                    </div>
                </form>
            </div>
        )
    }

    /********
     *
     * Change functions
     *
     ********/
    onChange_submissionOption = (e) => {
        const newStatus = e.label;
        this.setState(
            function (prevState) {
                return {
                    myArticle: {
                        ...prevState.myArticle,
                        status: newStatus
                    }
                };
            }
        )
    }
    onChange_comment = (e) => {
        const newComment = e.target.value;
        this.setState(
            function (prevState) {
                return {
                    myArticle: {
                        ...prevState.myArticle,
                        publisherComment: newComment
                    }
                };
            }
        )
    }
}