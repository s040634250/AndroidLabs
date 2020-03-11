package com.example.androidlabs;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

public class ProfileActivity extends AppCompatActivity {
    public static final String ACTIVITY_NAME = "PROFILE_ACTIVITY";
    static final int REQUEST_IMAGE_CAPTURE = 1;
    EditText email;
    ImageButton mImageButton;
    Button chatButton;
    Button weather;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        mImageButton = findViewById(R.id.imageButton2);
        mImageButton.setOnClickListener(v -> {

            Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
            }
        });

        Intent fromMain = getIntent();
        //fromMain.getStringExtra("EMAIL");

        email = findViewById(R.id.editText5);
        email.setText(fromMain.getStringExtra("EMAIL"));

        chatButton = findViewById(R.id.button3);
        chatButton.setOnClickListener(v -> {
            Intent goToChat = new Intent(ProfileActivity.this, ChatRoomActivity.class);
            startActivityForResult(goToChat, 345);
        });
        weather = findViewById(R.id.button4);
        weather.setOnClickListener(v -> {
            Intent goToWeather = new Intent(ProfileActivity.this, WeatherForcast.class);
            startActivityForResult(goToWeather, 345);
        });
        Log.e(ACTIVITY_NAME, "in function: onCreate()");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            mImageButton.setImageBitmap(imageBitmap);
        }

        Log.e(ACTIVITY_NAME, "in function: onActivityResult()");


    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.e(ACTIVITY_NAME, "in function: onStart()");

    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.e(ACTIVITY_NAME, "in function: onResume()");


    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.e(ACTIVITY_NAME, "in function: onPause()");

    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.e(ACTIVITY_NAME, "in function: onStop()");

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e(ACTIVITY_NAME, "in function: onDestroy()");

    }

}
