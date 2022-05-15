package com.example.nftapp3;

import android.app.Notification;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


/**
 * The MainActivity class handles the Registration and Login events
 */
public class MainActivity extends AppCompatActivity {

    private NotificationManagerCompat notificationManager;
    private String mode;
    private String id;
    String data;
    int correctVal = 0;
    MyBroadcastReciever airplaneModeChangeReceiver = new MyBroadcastReciever();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        IntentFilter filter = new IntentFilter(Intent.ACTION_AIRPLANE_MODE_CHANGED);
        registerReceiver(airplaneModeChangeReceiver, filter);
    }

    /**
     * This method is used to handle the click of cancel button during registration
     * @param view The image view that registers the click event
     */
    public void cancelEvent(View view){

        ImageView cancelBtn = findViewById(R.id.cancel_Btn);
        TextView firstN = findViewById(R.id.firstNameText);
        TextView lastN = findViewById(R.id.lastNameText);
        TextView usern = findViewById(R.id.newusernameText);
        TextView passw = findViewById(R.id.newpasswordText);
        Button signup = findViewById(R.id.SignupButton);

        cancelBtn.setVisibility(View.INVISIBLE);
        firstN.setVisibility(View.INVISIBLE);
        lastN.setVisibility(View.INVISIBLE);
        usern.setVisibility(View.INVISIBLE);
        passw.setVisibility(View.INVISIBLE);
        signup.setVisibility(View.INVISIBLE);

    }

    /**
     * This method handles the login event by taking username and password.
     * Error handling also done
     * Use of Notifications and Toast Messages to display messages
     * @param view The view that registers the click event
     */
    public void loginEvent(View view) {

        EditText username = findViewById(R.id.usernameText);
        EditText password = findViewById(R.id.passwordText);

        /* Error checking for empty input fields */
        if( TextUtils.isEmpty(username.getText()) || TextUtils.isEmpty(password.getText())) {
            Toast.makeText(getBaseContext(), "Enter all fields!", Toast.LENGTH_SHORT).show();
        }

        else {
            String usernameInput = username.getText().toString().trim();
            String passwordInput = password.getText().toString().trim();

            UserDetails userDetails;
            userDetails = new UserDetails(getRandomNumber(1000, 9999), usernameInput, passwordInput);

            username.setText("");
            password.setText("");
            String URL = "content://com.example.nftapp3.MyContentProvider";
            Uri users = Uri.parse(URL);
            Cursor c = getContentResolver().query(users, null, null, null, null);
            String myUsers = null;

            DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://the-nft-hut-d2a38-default-rtdb.firebaseio.com/");
            databaseReference.child("Users").child(usernameInput).child("Password").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {

                    /* On successful login, user is notified and redirected to the home page */
                    if (snapshot.exists()) {
                        data = snapshot.getValue().toString();
                        FirebaseData newdets = new FirebaseData();
                        notificationManager = NotificationManagerCompat.from(MainActivity.this);
                        Notification notification = new NotificationCompat.Builder(MainActivity.this, App.channel1_ID)
                                .setSmallIcon(R.drawable.nfthutlogo)
                                .setContentTitle("THE NFT HUT")
                                .setContentText("You have logged in!")
                                .setPriority(NotificationCompat.PRIORITY_HIGH)
                                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                                .build();
                        notificationManager.notify(1, notification);

                        SharedPreferences sp = getSharedPreferences("Username", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sp.edit();
                        editor.putString("username_value", usernameInput);
                        editor.commit();

                        Intent login_intent = new Intent(MainActivity.this, HomeActivity.class);
                        startActivity(login_intent);
                    }
                    else{
                        Toast.makeText(MainActivity.this, "Incorrect Details!", Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Toast.makeText(MainActivity.this, "Incorrect Details!", Toast.LENGTH_LONG).show();
                }
            });
        }
        }

    /**
     * This method handles the registration button click event
     * by displaying the registration fields
     * @param v The view that registers the click event
    */
    public void registerEvent(View v){

        ImageView cancelBtn = findViewById(R.id.cancel_Btn);
        TextView firstN = findViewById(R.id.firstNameText);
        TextView lastN = findViewById(R.id.lastNameText);
        TextView usern = findViewById(R.id.newusernameText);
        TextView passw = findViewById(R.id.newpasswordText);
        Button signup = findViewById(R.id.SignupButton);

        cancelBtn.setVisibility(View.VISIBLE);
        firstN.setVisibility(View.VISIBLE);
        lastN.setVisibility(View.VISIBLE);
        usern.setVisibility(View.VISIBLE);
        passw.setVisibility(View.VISIBLE);
        signup.setVisibility(View.VISIBLE);

        /* Use of service to execute background actions after successful registration */
        startService(new Intent(this, AndroidService.class));

    }

    /**
     * This method handles the signup event
     * the fields displayed are First Name, Last Name, username and password
     * Error handling also done
     * Use of Notifications and Toast Messages to display messages
     * @param view The view that registers the click event
     */
    public void signupEvent(View view){

        EditText first_name = findViewById(R.id.firstNameText);
        EditText last_name = findViewById(R.id.lastNameText);
        EditText newUsername = findViewById(R.id.newusernameText);
        EditText newPassword = findViewById(R.id.newpasswordText);

        /* Error handling to check whether all fields has an input */
        if(TextUtils.isEmpty(newUsername.getText()) || TextUtils.isEmpty(newPassword.getText()) || TextUtils.isEmpty(first_name.getText()) ||
                TextUtils.isEmpty(last_name.getText())) {

            Toast.makeText(getBaseContext(), "Enter all fields!", Toast.LENGTH_SHORT).show();

        }

        /* Error handling to check is password length less than 8 */
        else if((!TextUtils.isEmpty(newUsername.getText()) || !TextUtils.isEmpty(newPassword.getText()) || !TextUtils.isEmpty(first_name.getText()) ||
                !TextUtils.isEmpty(last_name.getText())) && newPassword.length() < 8){
            Toast.makeText(getBaseContext(), "Password should be greater than 8 characters!", Toast.LENGTH_LONG).show();
        }
        else {

            String firstnameText = first_name.getText().toString().trim();
            String lastnameText = last_name.getText().toString().trim();
            String newUser = newUsername.getText().toString().trim();
            String newPw = newPassword.getText().toString().trim();
            first_name.setText("");
            last_name.setText("");
            newUsername.setText("");
            newPassword.setText("");

            /* Adding of username to SharedPreferences to be used later */
            SharedPreferences sp = getSharedPreferences("Username", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sp.edit();
            editor.putString("username_value", String.valueOf(newUser));
            editor.commit();

            /* Syncing sqllite and Fiebase insert operationsfor new users which can only be
             * done once internet connection is available */
            UserDetails userDetails;
            userDetails = new UserDetails(getRandomNumber(1000, 9999), newUser, newPw);
            ContentValues contentValues = new ContentValues();
            contentValues.put(DataBaseHelper.username_column, newUser);
            contentValues.put(DataBaseHelper.password_column, newPw);
            Uri uri = getContentResolver().insert(MyContentProvider.CONTENT_URI, contentValues);
            Toast.makeText(getBaseContext(), uri.toString(), Toast.LENGTH_LONG).show();
            FirebaseData newdets = new FirebaseData();
            newdets.userDetails(newUser, firstnameText, lastnameText, newPw);

            /* USE OF NOTIFICATIONS TO DISPLAY SUCCESSFUL REGISTRATION */
            notificationManager = NotificationManagerCompat.from(this);
            Notification notification = new NotificationCompat.Builder(this, App.channel1_ID)
                    .setSmallIcon(R.drawable.nfthutlogo)
                    .setContentTitle("THE NFT HUT")
                    .setContentText("Account Created!")
                    .setPriority(NotificationCompat.PRIORITY_HIGH)
                    .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                    .build();
            notificationManager.notify(1, notification);

            ImageView cancelBtn = findViewById(R.id.cancel_Btn);
            TextView firstN = findViewById(R.id.firstNameText);
            TextView lastN = findViewById(R.id.lastNameText);
            TextView usern = findViewById(R.id.newusernameText);
            TextView passw = findViewById(R.id.newpasswordText);
            Button signup = findViewById(R.id.SignupButton);

            cancelBtn.setVisibility(View.INVISIBLE);
            firstN.setVisibility(View.INVISIBLE);
            lastN.setVisibility(View.INVISIBLE);
            usern.setVisibility(View.INVISIBLE);
            passw.setVisibility(View.INVISIBLE);
            signup.setVisibility(View.INVISIBLE);
        }
    }

    public void UserGuide(View view){
        Intent guide_intent = new Intent(getApplicationContext(), User_Guide.class);
        startActivity(guide_intent);

    }
    /**
     * This method is used to get a random integer
     * @param min The min value limit
     * @param min The max value limit
     * @return int The random number
     */
    public int getRandomNumber(int min, int max) {
        return (int) ((Math.random() * (max - min)) + min);
    }
}