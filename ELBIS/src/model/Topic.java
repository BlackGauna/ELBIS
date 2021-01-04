package model;

public class Topic {
    // Atrrib_______________________________________________________________________________________________________
    private int id;
    private String name;
    private Topic parent;


    // Ctor_______________________________________________________________________________________________________

    public Topic(int id, String name, Topic parentTopic) {
        this.id = id;
        this.name = name;
        this.parent = parentTopic;
    }

    public Topic() {

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

    public int getParentID() {
        return parent.getId();
    }

    public void setParent(Topic parentTopic) {
        this.parent = parentTopic;
    }

    public Topic getParent() {
        return parent;
    }

    public String getParentName() {
        return parent.getName();
    }

    // Methods_________________________________________________________________________________________________________

    // for ChoiceBox in save prompt of editor
    @Override
    public String toString() {
        return name;
    }
}
