package com.example.nftapp3;

import android.os.UserHandle;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class FirebaseData  {

    FirebaseDatabase rootNode;
    DatabaseReference reference;

    public void NodeDetails(String user){
        rootNode = FirebaseDatabase.getInstance();
        reference = rootNode.getReference("Users");

        reference.child("username").setValue(user);
    }

    public void userDetails(String username, String firstName, String lastName){
        rootNode = FirebaseDatabase.getInstance();
        reference = rootNode.getReference("Users");

        reference.child("username").setValue(username);
        reference.child("First Name").setValue(firstName);
        reference.child("Last Name").setValue(lastName);

    }


}
