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

import android.widget.Button;
import android.widget.EditText;
import android.util.Log;
import android.view.View;
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

    public void loginEvent(View view) {

        EditText username = findViewById(R.id.usernameText);
        EditText password = findViewById(R.id.passwordText);
        String usernameInput = username.getText().toString();
        String passwordInput = password.getText().toString();

        UserDetails userDetails;

        userDetails = new UserDetails(getRandomNumber(1000,9999), usernameInput, passwordInput);
        Toast.makeText(MainActivity.this, userDetails.toString(), Toast.LENGTH_SHORT).show();
//        Log.d("success", usernameInput);
//        Log.d("success", passwordInput);
        username.setText("");
        password.setText("");
        String URL = "content://com.example.nftapp3.MyContentProvider";
        Uri users = Uri.parse(URL);
        Cursor c = getContentResolver().query(users, null, null, null, null);
        String myUsers = null;


        //Toast.makeText(this, myUsers, Toast.LENGTH_SHORT);


        //DataBaseHelper dataBaseHelper = new DataBaseHelper(MainActivity.this);
       // List<UserDetails> loginCredentials = dataBaseHelper.getEveryone(usernameInput, passwordInput);

        // user1, password1
//        dataBaseHelper.addOne(userDetails);
        for(c.moveToFirst(); !c.isAfterLast(); c.moveToNext()){
            String userVal = c.getString(c.getColumnIndexOrThrow(DataBaseHelper.username_column));
            String passVal = c.getString(c.getColumnIndexOrThrow(DataBaseHelper.password_column));
            System.out.println(userVal);

            System.out.println(passVal);
            if(usernameInput.equals(userVal) && passwordInput.equals(passVal)){
                System.out.println("I AM HERE");
                System.out.println(userVal);
                System.out.println(passVal);
                correctVal += 1;
            }

        }


        if(correctVal == 1){
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


        }
        else{

            System.out.println("Don't Redirect");


        }

//        Log.d("success", loginCredentials.toString());
//        Toast.makeText(MainActivity.this, "Success = " + loginCredentials.size(), Toast.LENGTH_LONG).show();
    }

    public void registerEvent(View v){

        TextView firstN = findViewById(R.id.firstNameText);
        TextView lastN = findViewById(R.id.lastNameText);
        TextView usern = findViewById(R.id.newusernameText);
        TextView passw = findViewById(R.id.newpasswordText);
        Button signup = findViewById(R.id.SignupButton);

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
        String firstnameText = first_name.getText().toString();
        String lastnameText = last_name.getText().toString();
        String newUser = newUsername.getText().toString();
        String newPw = newPassword.getText().toString();
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
        userDetails = new UserDetails(getRandomNumber(1000,9999), newUser, newPw);
        ContentValues contentValues = new ContentValues();
        contentValues.put(DataBaseHelper.username_column, newUser);
        contentValues.put(DataBaseHelper.password_column, newPw);
        Uri uri = getContentResolver(). insert(MyContentProvider.CONTENT_URI, contentValues);
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

        TextView firstN = findViewById(R.id.firstNameText);
        TextView lastN = findViewById(R.id.lastNameText);
        TextView usern = findViewById(R.id.newusernameText);
        TextView passw = findViewById(R.id.newpasswordText);
        Button signup = findViewById(R.id.SignupButton);

        firstN.setVisibility(View.INVISIBLE);
        lastN.setVisibility(View.INVISIBLE);
        usern.setVisibility(View.INVISIBLE);
        passw.setVisibility(View.INVISIBLE);
        signup.setVisibility(View.INVISIBLE);


    }

    public int getRandomNumber(int min, int max) {
        return (int) ((Math.random() * (max - min)) + min);
    }





}