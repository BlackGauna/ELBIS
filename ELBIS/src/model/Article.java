package model;

public class Article {
    // Atrrib_______________________________________________________________________________________________________
    private int id=0; // if id==0 then Article is regarded as not in db yet
    private String title;
    private String content;
    private String creationDate;
    private String expireDate;
    private String lastEdit;
    private Status status;
    private int topic_int;
    private Topic topic;
    private String statusString;
    private String author;
    private String publisher;
    private int author_Id;
    private int publisher_Id;
    private String publisherComment;
    private int status_int;

    // Ctor_______________________________________________________________________________________________________

    // for ArticleTable
    public Article(int id, String title, String creationDate, String expireDate, String lastEdit, int topic_int, String publisherComment) {
        this.id = id;
        this.title = title;
        this.creationDate = creationDate;
        this.expireDate = expireDate;
        this.lastEdit = lastEdit;
        this.topic_int = topic_int;
        this.publisherComment = publisherComment;
    }

    public Article() {
    }

    // for ArticleTable - LoadOwnArticles
    public Article(int id, String title, String creationDate, String expireDate, String lastEdit, String status,
                   Topic topic, String author, String publisher, String publisherComment) {
        this.id = id;
        this.title = title;
        this.creationDate = creationDate;
        this.expireDate = expireDate;
        this.lastEdit = lastEdit;
        this.statusString = status;
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
    public int getTopic_int() {
        return topic_int;
    }
    public void setTopic_int(int topic_int) {
        this.topic_int = topic_int;
    }
    public Topic getTopic()
    {
        return topic;
    }
    public void setTopic(Topic topic)
    {
        this.topic = topic;
    }
    public String getLastEdit() {
        return lastEdit;
    }
    public void setLastEdit(String lastEdit) {
        this.lastEdit = lastEdit;
    }
    public int getStatus_int() {
        return status_int;
    }
    public void setStatus_int(int status_int) {
        this.status_int = status_int;
    }
    public Status getStatus() {
        return status;
    }
    public int getAuthor_Id() {
        return author_Id;
    }
    public void setAuthor_Id(int author_Id) {
        this.author_Id = author_Id;
    }
    public int getPublisher_Id() {
        return publisher_Id;
    }
    public void setPublisher_Id(int publisher_Id) {
        this.publisher_Id = publisher_Id;
    }
    public void setStatus(Status status) {
        this.status = status;
    }
    public String getAuthor() {
        return author;
    }
    public String getTopicName(){
        return topic.getName();
    }
    public String getStatusString() {
        return statusString;
    }
    public String getPublisher() {
        return publisher;
    }
}