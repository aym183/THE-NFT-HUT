package com.example.nftapp3;

import android.app.Notification;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.fragment.app.Fragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * The AccountActivity class handles all the operations for the account page
 */
public class AccountActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    int[] textViews = {R.id.firstName, R.id.lastName};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_account);
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setSelectedItemId(R.id.account);
        FirebaseDatabase rootNode;
        DatabaseReference reference;
        String[] values = new String[3];

        /* The username that is stored in Shared Preferences is retrieved
        * and fetched to retrieve user details*/

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://the-nft-hut-d2a38-default-rtdb.firebaseio.com/");
        SharedPreferences sp = getApplicationContext().getSharedPreferences("Username", Context.MODE_PRIVATE);
        String value = sp.getString("username_value", "");
        TextView username = findViewById(R.id.username);
        username.setText(value);

        String[] text_data = {"First Name", "Last Name"};

        for(int i = 0; i<textViews.length; i++) {

            int position = i;

            databaseReference.child("Users").child(value).child(text_data[i]).addValueEventListener(new ValueEventListener() {
                /**
                 * This method checks whether user has valid details and is the success case
                 * @param snapshot The value/object containing the user details
                 */
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {

                    // Once snapshot exits, user details are displayed on the account page
                    if (snapshot.exists()) {
                        String data = snapshot.getValue().toString();
                        values[position] = data;
                        TextView textView = findViewById(textViews[position]);
                        textView.setText(text_data[position] + ":        " + data);
                    }
                }

                /**
                 * This method handles failure case when checking for user details
                 * @param error This displays the error
                 */
                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });


        }

        /**
         * This method handles click on the bottom navbar and where to redirect the user
         * @param item This is the item in the navbar that the user clicks
         * @return 'true' is returned once user clicks a particular item and is redirected correctly
         */
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


    /**
     * This method handles LOGOUT event
     * @param v This arguement represents the View that received the click event
     */
    public void LogoutEvent(View v){

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(AccountActivity.this);
        Notification notification = new NotificationCompat.Builder(AccountActivity.this, App.channel1_ID)
                .setSmallIcon(R.drawable.nfthutlogo)
                .setContentTitle("THE NFT HUT")
                .setContentText("You have logged in!")
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                .build();
        notificationManager.notify(1, notification);
        startActivity(new Intent(getApplicationContext(), MainActivity.class));
        overridePendingTransition(0, 0);

    }

    /**
     * This method handles LEARN MORE event
     * @param v This arguement represents the View that received the click event
     */
    public void learnMoreEvent(View v){
        Uri webpage = Uri.parse("https://thenfthut.live");
        Intent webIntent = new Intent(Intent.ACTION_VIEW, webpage);
        startActivity(webIntent);
    }


}
