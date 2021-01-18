import { Component } from "react";
import { Editor } from "@tinymce/tinymce-react";
import axios from "axios";

export default class CreateArticle extends Component {
  handleEditorChange = (content, editor) => {
    console.log("Content was updated:", content);
  };
  render() {
    return (
      <Editor
          apiKey="0pg6bjj3shae8ys7qwuzkwo6jba2p7i7bs6onheyzqlhswen"
        initialValue="<p>This is the initial content of the editor</p>"
        init={{
            //skin: "oxide-dark",
            //content_css:"dark",
          height: 500,
          plugins: [
            "advlist autolink lists link image charmap print preview anchor",
            "searchreplace visualblocks code fullscreen",
            "insertdatetime media table paste code help wordcount",
          ],
          toolbar:
            "undo redo | formatselect | bold italic backcolor | \
                  alignleft aligncenter alignright alignjustify | \
                  bullist numlist outdent indent | removeformat | help",
            toolbar_mode:'floating',
            file_picker_types:"image",
        }}
        onEditorChange={this.handleEditorChange}
      />
    );
  }
}
