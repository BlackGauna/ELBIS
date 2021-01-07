package model;

import javafx.collections.ObservableList;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class User {
    protected String role = "User";
    // Atrrib_______________________________________________________________________________________________________
    protected int id;
    protected String email;
    protected String name;
    protected String password;
    protected String address;
    //private Date dateOfBirth;
    protected String dateOfBirth;
    protected Gender gender;
    protected ObservableList<Topic> topics;

    // Ctor_______________________________________________________________________________________________________
    public User() {
        this.id = 0;
        this.email = "Kein";
        this.name = "";
        this.password = "default";
        this.address = "default";
    }

    public User(int id, String email, String name, String gender, String role, String address, String dob) {
        this.id = id;
        this.email = email;
        this.name = name;
        setGender(gender);
        this.role = role;
        this.address = address;
        setDateOfBirth(dob);
    }

    public User(int id, String email, String name, String address, String password, String dateOfBirth, int gender) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.address = address;
        this.password = password;
        setDateOfBirth(dateOfBirth);
        setGender(gender);
    }

    // Getters,Setters_______________________________________________________________________________________________________
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        String passreturn = "";
        for (int i = 0; i < password.length(); i++) {
            passreturn += "*";
        }
        return passreturn;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPasswordClear() {
        return password;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dob) {
        this.dateOfBirth = dob;
    }


    public Gender getGender() {
        return gender;
    }

    public String getGenderAsString() {
        return gender.toString();
    }

    public int getGenderAsInt() {
        int g;
        if (this.gender == Gender.Maennlich) {
            g = 1;
        } else if (this.gender == Gender.Weiblich) {
            g = 2;
        } else if (this.gender == Gender.Divers) {
            g = 3;
        } else {
            g = 0;
            System.out.println("Couldn't map gender");
        }
        return g;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public void setGender(int gender) {
        if (gender == 1) {
            this.gender = Gender.Maennlich;
        }
        if (gender == 2) {
            this.gender = Gender.Weiblich;
        }
        if (gender == 3) {
            this.gender = Gender.Divers;
        }

    }

    public boolean setGender(String gender) {
        if (gender != null) {
            if (gender.equals(Gender.Maennlich.toString())) {
                setGender(Gender.Maennlich);
                return true;
            } else if (gender.equals(Gender.Weiblich.toString())) {
                setGender(Gender.Weiblich);
                return true;
            }
            if (gender.equals(Gender.Divers.toString())) {
                setGender(Gender.Divers);
                return true;
            } else {
                System.out.println("Couldnt load Gender");
                setGender(Gender.NONE);
                return false;
            }
        } else {
            System.out.println("Couldnt load Gender");
            setGender(Gender.NONE);
            return false;
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ObservableList<Topic> getTopics() {
        return topics;
    }

    public void setTopics(ObservableList<Topic> topics) {
        this.topics = topics;
    }

    public String getRole() {
        return role;
    }

    public int getRoleAsInt() {
        if(getRole().equals("User")){
            return 1;
        } else if ( getRole().equals("Moderator")){
            return 2;
        } else if ( getRole().equals("Administrator")){
            return 3;
        } else {
            System.out.println("Couldnt get Role from "+ getEmail());
            return 0;
        }
    }

    @Override
    public String toString() {
        return (name + " (" + email + ")");
    }
}