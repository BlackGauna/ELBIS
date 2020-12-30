package model;

public class Moderator extends User {

    // Ctor_______________________________________________________________________________________________________

    public Moderator() {
        super();
    }

    public Moderator(int id, String email, String name, String gender, String role,  String address, String dob) {
        super(id, email, name, gender, role, address, dob);
    }

    public Moderator(int id, String email, String name, String address,String password,String dateOfBirth,int gender) {
        super(id, email, name, address, password, dateOfBirth, gender);
    }
}