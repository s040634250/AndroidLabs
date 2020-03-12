package com.example.androidlabs;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

public class EmptyActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_empty);

        Bundle dataToSend = getIntent().getExtras();
        DetailsFragment frag = new DetailsFragment();
        frag.setArguments(dataToSend);
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, frag);
        ft.commit();
    }
}
