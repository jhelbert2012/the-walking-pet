package com.twp.petcare.bean;

import org.springframework.data.annotation.Id;

public class Pet {

    @Id
    private String id;
    private String name;
    private int age;
    private char genre;
    private int specie;
    private int breed;
    private int height;
    private int weight;
    private String colour;

    //TODO How is the relationship between pets and vaccines? a list?

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    /**
     * @return the breed
     */
    public int getBreed() {
        return breed;
    }

    /**
     * @param breed the breed to set
     */
    public void setBreed(int breed) {
        this.breed = breed;
    }

    /**
     * @return the specie
     */
    public int getSpecie() {
        return specie;
    }

    /**
     * @param specie the specie to set
     */
    public void setSpecie(int specie) {
        this.specie = specie;
    }

    /**
     * @return the height
     */
    public int getHeight() {
        return height;
    }

    /**
     * @param height the height to set
     */
    public void setHeight(int height) {
        this.height = height;
    }

    /**
     * @return the weight
     */
    public int getWeight() {
        return weight;
    }

    /**
     * @param weight the weight to set
     */
    public void setWeight(int weight) {
        this.weight = weight;
    }

    /**
     * @return the genre
     */
    public int getGenre() {
        return genre;
    }

    /**
     * @return the colour
     */
    public String getColour() {
        return colour;
    }

    /**
     * @param colour the colour to set
     */
    public void setColour(String colour) {
        this.colour = colour;
    }
}
