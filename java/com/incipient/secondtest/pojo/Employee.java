package com.incipient.secondtest.pojo;

public class Employee {
    private int id;
    private String first;
    private String last;
    private String dob;
    private String email;
    private String gender;
    private String profile;
    private boolean isSelected = false;

    /*public Employee(String first, String last, String email, String dob, String gender, String profile) {
    }*/


    public void setSelected(boolean selected) {
        isSelected = selected;
    }


    public boolean isSelected() {
        return isSelected;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirst() {
        return first;
    }

    public void setFirst(String first) {
        this.first = first;
    }

    public String getLast() {
        return last;
    }

    public void setLast(String last) {
        this.last = last;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }





    /*@Override
    public boolean equals(Object obj) {

        if(obj == null)
            return false;

        Employee itemCompare = (Employee) obj;
        if(itemCompare.getFirst().equals(this.getLast()))
            return true;
        return false;
    }*/
}