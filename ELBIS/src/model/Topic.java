package model;

public class Topic {
    // Atrrib_______________________________________________________________________________________________________
    private int id;
    private String name;
    private int parentTopic;

    // Ctor_______________________________________________________________________________________________________
    public Topic(int id, String name, int parentTopic) {
        this.id = id;
        this.name = name;
        this.parentTopic = parentTopic;
    }

    public Topic(){

    }

    // Getters,Setters________________________________________________________________________________________________
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
