package com.example.nftapp3;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Arrays;
import java.util.HashMap;

public class AccountActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    int[] textViews = {R.id.username, R.id.firstName, R.id.lastName};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_account);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setSelectedItemId(R.id.account);

        FirebaseDatabase rootNode;
        DatabaseReference reference;
        String[] values = new String[3];

        rootNode = FirebaseDatabase.getInstance();
        reference = rootNode.getReference("Users");

        String[] text_data = {"username", "First Name", "Last Name"};

        for(int i = 0; i<text_data.length; i++) {

            int position = i;
            rootNode = FirebaseDatabase.getInstance();
            reference = rootNode.getReference("Users").child(text_data[i]);

            reference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.exists()) {
                        String data = snapshot.getValue().toString();
                        values[position] = data;
                        TextView textView = findViewById(textViews[position]);
                        if(text_data[position] != "username"){
                            textView.setText(text_data[position] + ":        " + data);
                        }
                        else{
                            textView.setText(data);
                        }


                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Log.d("VALUE FROM FIRE", "you dont work");
                }
            });


        }



        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment fragment = null;

                switch(item.getItemId()){

                    case R.id.home:

                        startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                        overridePendingTransition(0, 0);
                        return true;


                    case R.id.explore:

                        startActivity(new Intent(getApplicationContext(), ExploreActivity.class));
                        overridePendingTransition(0, 0);
                        return true;


                    case R.id.chart:

                        startActivity(new Intent(getApplicationContext(), AnalyticsActivity.class));
                        overridePendingTransition(0, 0);
                        return true;


                    case R.id.account:

                        return true;
                }

                return false;
            }
        });
    }


    public void LogoutEvent(View v){

        startActivity(new Intent(getApplicationContext(), MainActivity.class));
        overridePendingTransition(0, 0);

    }


}
