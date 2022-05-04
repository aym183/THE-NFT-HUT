package com.example.nftapp3;

import android.os.UserHandle;
import android.util.Log;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.Locale;

public class FirebaseData{

    public boolean[] isTrue;
    FirebaseDatabase rootNode;
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://the-nft-hut-d2a38-default-rtdb.firebaseio.com/");
    String[] values = new String[3];


    public void userDetails(String username, String firstName, String lastName, String password){

        databaseReference.child("Users").child(username).child("First Name").setValue(firstName);
        databaseReference.child("Users").child(username).child("Last Name").setValue(lastName);
        databaseReference.child("Users").child(username).child("Password").setValue(password);


    }

}
