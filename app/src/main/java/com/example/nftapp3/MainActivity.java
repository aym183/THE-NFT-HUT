package com.example.nftapp3;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;


public class MainActivity extends AppCompatActivity {


    private NotificationManagerCompat notificationManager;
    private String mode;
    private String id;
    int correctVal = 0;
    MyBroadcastReciever airplaneModeChangeReceiver = new MyBroadcastReciever();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        IntentFilter filter = new IntentFilter(Intent.ACTION_AIRPLANE_MODE_CHANGED);
        registerReceiver(airplaneModeChangeReceiver, filter);
        

    }

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

    public void loginEvent(View view) {

        EditText username = findViewById(R.id.usernameText);
        EditText password = findViewById(R.id.passwordText);


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

//        dataBaseHelper.addOne(userDetails);
            for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
                String userVal = c.getString(c.getColumnIndexOrThrow(DataBaseHelper.username_column));
                String passVal = c.getString(c.getColumnIndexOrThrow(DataBaseHelper.password_column));

                System.out.println(passVal);
                if (usernameInput.equals(userVal) && passwordInput.equals(passVal)) {

                    correctVal += 1;
                }

            }


            if (correctVal == 1) {
                System.out.println("RedirectNow");

                FirebaseData newdets = new FirebaseData();
//            newdets.NodeDetails(usernameInput);

                notificationManager = NotificationManagerCompat.from(this);
                Notification notification = new NotificationCompat.Builder(this, App.channel1_ID)
                        .setSmallIcon(R.drawable.nfthutlogo)
                        .setContentTitle("THE NFT HUT")
                        .setContentText("You have logged in!")
                        .setPriority(NotificationCompat.PRIORITY_HIGH)
                        .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                        .build();
                notificationManager.notify(1, notification);


                Intent login_intent = new Intent(MainActivity.this, HomeActivity.class);
                startActivity(login_intent);


            } else {

                Toast.makeText(MainActivity.this, "Incorrect Details!", Toast.LENGTH_LONG).show();


            }
        }
//        Log.d("success", loginCredentials.toString());
//        Toast.makeText(MainActivity.this, "Success = " + loginCredentials.size(), Toast.LENGTH_LONG).show();
    }

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

    }
    public void signupEvent(View view){

        EditText first_name = findViewById(R.id.firstNameText);
        EditText last_name = findViewById(R.id.lastNameText);
        EditText newUsername = findViewById(R.id.newusernameText);
        EditText newPassword = findViewById(R.id.newpasswordText);

        if(TextUtils.isEmpty(newUsername.getText()) || TextUtils.isEmpty(newPassword.getText()) || TextUtils.isEmpty(first_name.getText()) ||
                TextUtils.isEmpty(last_name.getText())) {

            Toast.makeText(getBaseContext(), "Enter all fields!", Toast.LENGTH_SHORT).show();

        }
        else if((!TextUtils.isEmpty(newUsername.getText()) || !TextUtils.isEmpty(newPassword.getText()) || !TextUtils.isEmpty(first_name.getText()) ||
                !TextUtils.isEmpty(last_name.getText())) && newPassword.length() < 8){

            System.out.println( newPassword.length());
            Toast.makeText(getBaseContext(), "Password should be greater than 8 characters!", Toast.LENGTH_LONG).show();
        }
        else{


            String firstnameText = first_name.getText().toString().trim();
            String lastnameText = last_name.getText().toString().trim();
            String newUser = newUsername.getText().toString().trim();
            String newPw = newPassword.getText().toString().trim();
            first_name.setText("");
            last_name.setText("");
            newUsername.setText("");
            newPassword.setText("");

            SharedPreferences sp = getSharedPreferences("Username", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sp.edit();
            editor.putString("username_value", String.valueOf(newUser));
            editor.commit();

            // Synced sqlite and firebase
            UserDetails userDetails;
            userDetails = new UserDetails(getRandomNumber(1000, 9999), newUser, newPw);
            ContentValues contentValues = new ContentValues();
            contentValues.put(DataBaseHelper.username_column, newUser);
            contentValues.put(DataBaseHelper.password_column, newPw);
            Uri uri = getContentResolver().insert(MyContentProvider.CONTENT_URI, contentValues);
            Toast.makeText(getBaseContext(), uri.toString(), Toast.LENGTH_LONG).show();

//        DataBaseHelper dataBaseHelper = new DataBaseHelper(MainActivity.this);
//        dataBaseHelper.addOne(userDetails);

            FirebaseData newdets = new FirebaseData();
            newdets.userDetails(newUser, firstnameText, lastnameText, newPw);


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

    public int getRandomNumber(int min, int max) {
        return (int) ((Math.random() * (max - min)) + min);
    }





}