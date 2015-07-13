package com.twp.petcare.bean;

public class CareGiver extends User{

    private boolean hasCar;
    private int experienceYears;

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
