package model;

public class Topic {
    // Atrrib_______________________________________________________________________________________________________
    private int id;
    private String name;
    private int parentTopic;

    private Topic parent;



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

    public Topic getParent()
    {
        return parent;
    }

    public void setParent(Topic parent)
    {
        this.parent = parent;
    }



    // Methods_________________________________________________________________________________________________________

    // for ChoiceBox in save prompt of editor
    @Override
    public String toString()
    {
        return name;
    }
}
