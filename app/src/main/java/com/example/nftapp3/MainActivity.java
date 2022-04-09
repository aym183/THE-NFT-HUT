package com.example.nftapp3;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    public void loginEvent(View view) {

//        View myView = findViewById(R.id.loginButton);
//        myView.setEnabled(false);



        EditText username = findViewById(R.id.usernameText);
        EditText password = findViewById(R.id.passwordText);
        String usernameInput = username.getText().toString();
        String passwordInput = password.getText().toString();

        UserDetails userDetails;

        userDetails = new UserDetails(usernameInput, passwordInput);
        Toast.makeText(MainActivity.this, userDetails.toString(), Toast.LENGTH_SHORT).show();
        Log.d("success", usernameInput);
        Log.d("success", passwordInput);
        username.setText("");
        password.setText("");
        DataBaseHelper dataBaseHelper = new DataBaseHelper(MainActivity.this);
        List<UserDetails> loginCredentials = dataBaseHelper.getEveryone(usernameInput, passwordInput);

        // boolean success = dataBaseHelper.addOne(userDetails);
        if(loginCredentials.size() >= 1){
            System.out.println("RedirectNow");
            Intent intent = new Intent(MainActivity.this, NavbarRedirect.class);
            startActivity(intent);
        }
        else{
            System.out.println("Don't Redirect");
        }

        Log.d("success", loginCredentials.toString());
        Toast.makeText(MainActivity.this, "Success = " + loginCredentials.size(), Toast.LENGTH_LONG).show();
    }

    public int getRandomNumber(int min, int max) {
        return (int) ((Math.random() * (max - min)) + min);
    }



}