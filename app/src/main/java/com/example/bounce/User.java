package com.example.bounce;

public class User {
    public String password;
    public String name;
    public String lastNameOrAddress;
    public boolean isBar;

    public User(String password, String name, String lastNameOrAddress, boolean isBar) {
        this.password = password;
        this.name = name;
        this.lastNameOrAddress = lastNameOrAddress;
        this.isBar = isBar;
    }
}
