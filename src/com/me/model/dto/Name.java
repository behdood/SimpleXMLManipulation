package com.me.model.dto;


public class Name {
    private String firstName;
    private String lastName;
    private char middleInitial;

    public Name(String firstName, String lastName, char middleInitial) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.middleInitial = middleInitial;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public char getMiddleInitial() {
        return middleInitial;
    }

    public void setMiddleInitial(char middleInitial) {
        this.middleInitial = middleInitial;
    }

    @Override
    public String toString() {
        return firstName + ' ' + middleInitial + ". " + lastName;
    }
}
