package com.example.androidlabs;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;


public class MainActivity extends AppCompatActivity {

    EditText prevEmail;
    SharedPreferences prefs;
    Button login;
    String previous;
    //Context c = this.getConext();

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        prevEmail = findViewById(R.id.editText2);
        prefs = getSharedPreferences("name", MODE_PRIVATE);

        login  = findViewById(R.id.button2);
        login.setOnClickListener(v -> {
            Intent goToProfile = new Intent(MainActivity.this, ProfileActivity.class);

            goToProfile.putExtra("EMAIL", prevEmail.getText().toString());

            startActivityForResult(goToProfile, 345);
        });
    }

    protected void onPause() {
        super.onPause();

        //SharedPreferences prefs = getSharedPreferences("FileName", MODE_PRIVATE);

        SharedPreferences.Editor editor = prefs.edit();
        previous = prevEmail.getText().toString();
        editor.putString("",previous);
        editor.apply();
    }
}
