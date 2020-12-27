package model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class User {
    // Atrrib_______________________________________________________________________________________________________
    private int id;
    private String email;
    private String name;
    private String password;
    private String address;
    private Date dateOfBirth;
    private Gender gender;

    // Ctor_______________________________________________________________________________________________________
    public User() {
        this.id = 0;
        this.email = "default@default.net";
        this.name = "default";
        this.password = "default";
        this.address = "default";
    }

    public User(int id, String email, String name, String address) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.address = address;
    }

    public User(int id, String email, String name, String address, String password,String dateOfBirth,int gender) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.address = address;
        this.password = password;
       setDateOfBirth(dateOfBirth);
       setGender(gender);
    }



    // Methods_______________________________________________________________________________________________________
    public void editArticle(int articleId) {
        //TODO Implement EditArticle - Only if the user is also the creator
    }

    public void deleteArticle(int articleId) {
        //TODO Implement deleteArticle - Only if the user is also the creator
    }


    // Getters,Setters_______________________________________________________________________________________________________
    public int getId() {
        return id;
    }

    public void setId(int id) {
        System.out.println("Changing the User ID is not allowed");
        //this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        String passreturn= "";
        for (int i = 0; i < password.length(); i++){
            passreturn += "*";
        }
        return passreturn;
    }

    public String getPasswordClear() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        try {
            Date date = new SimpleDateFormat("dd.MM.yyyy").parse(dateOfBirth);
            this.dateOfBirth = date;
        } catch (ParseException e) {
            e.printStackTrace();
        }

    }

    public Gender getGenderAsGender() {
        return gender;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


}
