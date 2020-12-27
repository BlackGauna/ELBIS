package model;

public class Administrator extends Moderator {

    // Ctor_______________________________________________________________________________________________________

    public Administrator() {
        super();
    }

    public Administrator(int id, String email, String name, String address) {
        super(id, email, name, address);
    }

    public Administrator(int id, String email, String name, String address,String password,String dateOfBirth,int gender) {
        super(id, email, name, address, password, dateOfBirth, gender);
    }




}
