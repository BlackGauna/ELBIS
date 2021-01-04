package model;

import org.springframework.security.crypto.bcrypt.BCrypt;

public class BcryptTest {

    private String password = "asd";
    private String hashedpassword = BCrypt.hashpw(password, BCrypt.gensalt(10));

    public void checkPassword(String password){
        if(BCrypt.checkpw(password,hashedpassword)){
            System.out.println("Success");
        }else{
            System.out.println("Failed");
        }
    }
}

