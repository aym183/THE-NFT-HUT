package com.example.nftapp3;

/**
 * The UserDetails class creates the user details passed and converts to a format
 * that works in db
 */
public class UserDetails {

    private int user_id;
    private String username;
    private String password;

    public UserDetails(int user_id, String username, String password){
        this.user_id = user_id;
        this.username = username;
        this.password = password;
    }

    /**
     * This method converts string to a format that's acceptable in the db
     */
    @Override
    public String toString() {
        return "UserDetails{" +
                "user_id='" + user_id + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

    /**
     * Getters and Setters for each of the attributes
     */

    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

}
