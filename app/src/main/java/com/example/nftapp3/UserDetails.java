package com.example.nftapp3;

public class UserDetails {

    private int user_id;
    private String username;
    private String password;

    public UserDetails(String username, String password){

        this.username = username;
        this.password = password;

    }

    @Override
    public String toString() {
        return "UserDetails{" +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
