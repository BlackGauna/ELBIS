package model;

public class Topic {
    private int id;
    private String name;
    private int parentTopic;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getParentTopic() {
        return parentTopic;
    }

    public void setParentTopic(int parentTopic) {
        this.parentTopic = parentTopic;
    }
}
