package com.example.nftapp3;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


/**
 * The FirebaseData class handles Firebase operations
 */
public class FirebaseData{

    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://the-nft-hut-d2a38-default-rtdb.firebaseio.com/");

    /**
     * This method adds user details to Firebase
     * @param username This is the username the user inputs during registration
     * @param firstName This is the first name the user inputs during registration
     * @param lastName  This is the last name the user inputs during registration
     * @param password  This is the password the user inputs during registration
     */
    public void userDetails(String username, String firstName, String lastName, String password){

        databaseReference.child("Users").child(username).child("First Name").setValue(firstName);
        databaseReference.child("Users").child(username).child("Last Name").setValue(lastName);
        databaseReference.child("Users").child(username).child("Password").setValue(password);

    }
}
