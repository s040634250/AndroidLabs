package com.example.androidlabs;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

public class EmptyActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_empty);
        DetailsFragment frag = new DetailsFragment();
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, frag);
        ft.commit();
    }
}
