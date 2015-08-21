package com.twp.petcare.bean;

import java.util.Date;
import java.util.List;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.rest.core.annotation.RestResource;

public class Owner extends User{

    @DBRef
    @RestResource(exported = false)
    private List<Pet> pets;
    
    public Owner(int documentType, String documentNumber, String username, String password, char gender, String name, String surname, String address, String phoneNumber, String email, Date birthday) {
        super(documentType, documentNumber, username, password, gender, name, surname, address, phoneNumber, email, birthday);
    }

    public Owner(String id) {
        super(id);
    }

    public Owner() {
    }

    /**
     * @return the pets
     */
    public List<Pet> getPets() {
        return pets;
    }

    /**
     * @param pets the pets to set
     */
    public void setPets(List<Pet> pets) {
        this.pets = pets;
    }

}
