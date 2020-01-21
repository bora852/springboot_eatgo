package kr.co.bora.eatgo.application;

public class UserExistedException extends  RuntimeException{

    EmailExistedException(String email) {
        super("Email is already registered" + email);
    }
}
