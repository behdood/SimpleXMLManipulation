package com.me.model.dto;


import java.util.Objects;

public class Name {
//    private String firstName;
//    private String lastName;
//    private char middleInitial;
    private String value;

//    public Name(String firstName, String lastName, char middleInitial) {
//        this.firstName = firstName;
//        this.lastName = lastName;
//        this.middleInitial = middleInitial;
//    }

    public Name(String value) {
        this.value = value;
    }

//    public String getFirstName() {
//        return firstName;
//    }
//
//    public void setFirstName(String firstName) {
//        this.firstName = firstName;
//    }
//
//    public String getLastName() {
//        return lastName;
//    }
//
//    public void setLastName(String lastName) {
//        this.lastName = lastName;
//    }
//
//    public char getMiddleInitial() {
//        return middleInitial;
//    }
//
//    public void setMiddleInitial(char middleInitial) {
//        this.middleInitial = middleInitial;
//    }


    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Name name = (Name) o;
//        return Objects.equals(middleInitial, name.middleInitial) &&
//                Objects.equals(firstName, name.firstName) &&
//                Objects.equals(lastName, name.lastName);
        return Objects.equals(value, name.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

//    public int hashCode() {
//        return Objects.hash(firstName, lastName, middleInitial);
//    }


    @Override
    public String toString() {
        return value;
//        if (Objects.equals(middleInitial, '\0'))
//            return firstName + ' ' + lastName;
//        return firstName + ' ' + middleInitial + ". " + lastName;
    }

    public boolean isValid() {
        return (value != null && !value.equals(""));
    }
}
