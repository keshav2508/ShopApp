package com.example.shop;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class User {
    String name ;
    String mobile ;
    String location ;
    String userEmail ;
    String userPassword ;
    String shopStatus;

    public User(){

    }

    public User(String name , String mobile , String location , String userEmail , String userPassword, String shopStatus ) {
        this.name=name;
        this.mobile=mobile;
        this.location=location;
        this.userEmail=userEmail;
        this.userPassword=userPassword;
        this.shopStatus=shopStatus;
    }
    public String getName() {
        return name;
    }

    public String getMobile() {
        return mobile;
    }

    public String getLocation() {
        return location;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public String getShopStatus() {
        return shopStatus;
    }


}
