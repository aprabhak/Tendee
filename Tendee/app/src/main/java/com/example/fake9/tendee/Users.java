package com.example.fake9.tendee;

/**
 * Created by ChaoLun on 2/23/18.
 */

public class Users {
    public String name;
    public String description;
    public String address;
    public String email;
    public int id;



    public Users(String name, String description, String address, String email, int id) {
        this.name = name;
        this.description = description;
        this.address = address;
        this.email = email;
        this.id = id;
    }

    public Users(){

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
