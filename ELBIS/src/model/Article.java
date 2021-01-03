package model;

public class Article {
    // Atrrib_______________________________________________________________________________________________________
    private int id = 0; // if id==0 then Article is regarded as not in db yet
    private String title;
    private String content;
    private String creationDate;
    private String expireDate;
    private String lastEdit;
    private Status status;
    private Topic topic;
    private User author;
    private User publisher;
    private String publisherComment;

    // Ctor_______________________________________________________________________________________________________

    // for ArticleTable
    public Article(int id, String title, String creationDate, String expireDate, String lastEdit, int topic_int, String publisherComment) {
        this.id = id;
        this.title = title;
        this.creationDate = creationDate;
        this.expireDate = expireDate;
        this.lastEdit = lastEdit;
        setTopicID(topic_int);
        this.publisherComment = publisherComment;
    }

    public Article() {
    }

    // for ArticleTable - LoadOwnArticles
    public Article(int id, String title, String creationDate, String expireDate, String lastEdit, String statusString,
                   Topic topic, User author, User publisher, String publisherComment) {
        this.id = id;
        this.title = title;
        this.creationDate = creationDate;
        this.expireDate = expireDate;
        this.lastEdit = lastEdit;
        setStatusByString(statusString);
        this.topic = topic;
        this.author = author;
        this.publisher = publisher;
        this.publisherComment = publisherComment;
    }

    // Methods_______________________________________________________________________________________________________

    // Getters,Setters________________________________________________________________________________________________

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getExpireDate() {
        return expireDate;
    }

    public void setExpireDate(String expireDate) {
        this.expireDate = expireDate;
    }

    public String getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
    }

    public String getPublisherComment() {
        return publisherComment;
    }

    public void setPublisherComment(String publisherComment) {
        this.publisherComment = publisherComment;
    }

    public int getTopicID() {
        return topic.getId();
    }

    public void setTopicID(int topic_int) {
        this.topic.setId(topic_int);
    }

    public Topic getTopic() {
        return topic;
    }

    public void setTopic(Topic topic) {
        this.topic = topic;
    }

    public String getLastEdit() {
        return lastEdit;
    }

    public void setLastEdit(String lastEdit) {
        this.lastEdit = lastEdit;
    }

    public int getStatusID() {
        return status.getStatusCode();
    }

    public void setStatusByID(int statusID) {
        switch (statusID) {
            case 1:
                this.setStatus(Status.Default);
                break;
            case 2:
                this.setStatus(Status.Submitted);
                break;
            case 3:
                this.setStatus(Status.Declined);
                break;
            case 4:
                this.setStatus(Status.Authorized);
                break;
            case 5:
                this.setStatus(Status.Released);
                break;
            case 6:
                this.setStatus(Status.Archived);
                break;
            default:
                break;
        }
    }
    public void setStatus(Status status) {
        this.status = status;
    }

    public boolean setStatusByString(String status){
        if(status.equals(Status.Default.toString())){
            setStatus(Status.Default);
            return true;
        } else if(status.equals(Status.Submitted.toString())){
            setStatus(Status.Submitted);
            return true;
        }
        else if(status.equals(Status.Declined.toString())){
            setStatus(Status.Declined);
            return true;
        }
        else if(status.equals(Status.Authorized.toString())){
            setStatus(Status.Authorized);
            return true;
        }
        else if(status.equals(Status.Released.toString())){
            setStatus(Status.Released);
            return true;
        }
        else if(status.equals(Status.Archived.toString())){
            setStatus(Status.Archived);
            return true;
        }
        else{
            return false;
        }
    }

    public Status getStatus() {
        return status;
    }

    public int getAuthorID() {
        return author.getId();
    }

    public int getPublisherID() {
        return publisher.getId();
    }

    public String getAuthorName() {
        return author.getName();
    }

    public User getAuthor(){
        return author;
    }

    public void setAuthor(User author){
        this.author = author;
    }

    public String getTopicName() {
        return topic.getName();
    }

    public String getStatusAsString() {
        return status.toString();
    }

    public String getPublisherName() {
        return publisher.getName();
    }
    public User getPublisher(){
        return publisher;
    }

    public void setPublisher(User publisher){
        this.publisher = publisher;
    }
}