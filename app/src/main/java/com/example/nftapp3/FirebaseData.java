package com.example.nftapp3;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class FirebaseData  {

    FirebaseDatabase rootNode;
    DatabaseReference reference;

    public void NodeDetails(){
        rootNode = FirebaseDatabase.getInstance();
        reference = rootNode.getReference("users");

        reference.setValue("First Data Storage");
    }
}
