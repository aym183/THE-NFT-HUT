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

    FirebaseDatabase rootNode;
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://the-nft-hut-d2a38-default-rtdb.firebaseio.com/");
    String[] values = new String[3];

//    public void NodeDetails(String user){
//        rootNode = FirebaseDatabase.getInstance();
//        reference = rootNode.getReference("Users");
//
//        reference.child("username").push().setValue(user);
//    }

    public void userDetails(String username, String firstName, String lastName, String password){

        databaseReference.child("Users").child(username).child("First Name").setValue(firstName);
        databaseReference.child("Users").child(username).child("Last Name").setValue(lastName);
        databaseReference.child("Users").child(username).child("Password").setValue(password);


    }

//    public String[] readData(){
//
//        String[] data = {"username", "First Name", "Last Name"};
//
//        for(int i = 0; i<data.length; i++) {
//
//            int position = i;
//            rootNode = FirebaseDatabase.getInstance();
//            reference = rootNode.getReference("Users").child(data[i]);
//
//            reference.addValueEventListener(new ValueEventListener() {
//                @Override
//                public void onDataChange(@NonNull DataSnapshot snapshot) {
//                    if (snapshot.exists()) {
//
////                     reference.child("username");
////                     reference.child("First Name");
////                     reference.child("Last Name");
//                        String data = snapshot.getValue().toString();
//                        values[position] = data;
//                        Log.d("VALUE FROM FIRE", data);
//
//
//                    }
//                }
//
//                @Override
//                public void onCancelled(@NonNull DatabaseError error) {
//
//                }
//            });
//
//
//        }
//        return values;
//    }


}
