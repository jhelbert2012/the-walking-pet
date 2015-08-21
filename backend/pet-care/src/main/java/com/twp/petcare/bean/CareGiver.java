package com.twp.petcare.bean;

import java.util.Date;

public class CareGiver extends User{

    private boolean hasCar;
    private int experienceYears;

    public CareGiver(int documentType, String documentNumber, String username, String password, char gender, String name, String surname, String address, String phoneNumber, String email, Date birthday) {
        super(documentType, documentNumber, username, password, gender, name, surname, address, phoneNumber, email, birthday);
    }

    /**
     * @return the hasCar
     */
    public boolean isHasCar() {
        return hasCar;
    }

    /**
     * @param hasCar the hasCar to set
     */
    public void setHasCar(boolean hasCar) {
        this.hasCar = hasCar;
    }

    /**
     * @return the experienceYears
     */
    public int getExperienceYears() {
        return experienceYears;
    }

    /**
     * @param experienceYears the experienceYears to set
     */
    public void setExperienceYears(int experienceYears) {
        this.experienceYears = experienceYears;
    }
    
}
