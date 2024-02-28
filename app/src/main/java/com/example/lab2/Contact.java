package com.example.lab2;

import java.io.Serializable;

public class Contact implements Serializable {
    public int Id ;
    public String name;
    public String PhoneNumber;
    public String image;
    public boolean Status;

    public void setId(int id) {
        Id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPhoneNumber(String phoneNumber) {
        PhoneNumber = phoneNumber;
    }

    public void setStatus(boolean status) {
        Status = status;
    }

    public int getId() {
        return Id;
    }

    public String getName() {
        return name;
    }

    public String getPhoneNumber() {
        return PhoneNumber;
    }

    public boolean isStatus() {
        return Status;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getImage() {
        return image;
    }

    public Contact(int id, String name, String phoneNumber, String image, boolean status) {
        Id = id;
        this.name = name;
        PhoneNumber = phoneNumber;
        Status = status;
        this.image = image;
    }
}
