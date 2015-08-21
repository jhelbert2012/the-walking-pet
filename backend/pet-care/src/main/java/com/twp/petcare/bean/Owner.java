package com.twp.petcare.bean;

import java.util.Date;

public class Owner extends User{

    public Owner(int documentType, String documentNumber, String username, String password, char gender, String name, String surname, String address, String phoneNumber, String email, Date birthday) {
        super(documentType, documentNumber, username, password, gender, name, surname, address, phoneNumber, email, birthday);
    }

    public Owner(String id) {
        super(id);
    }

    public Owner() {
    }
}
