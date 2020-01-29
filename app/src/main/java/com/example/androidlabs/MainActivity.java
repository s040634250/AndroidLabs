package com.example.androidlabs;

import androidx.appcompat.app.AppCompatActivity;

import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;


public class MainActivity extends AppCompatActivity {
    public void toastMsg(String msg) {

        Toast toast = Toast.makeText(this, msg, Toast.LENGTH_LONG);
        toast.show();
    }

    public void displayToastMsg(View v) {
        toastMsg(v.getResources().getString(R.string.toast_message));
    }

    public void switchSnackbar(View v){
        Switch switch1 = findViewById( R.id.switch1 );
        switch1.setOnCheckedChangeListener((buttonView, isChecked) -> {
            String position;
            if ( !isChecked )
            {
                position = "off";
            }else position = "on";
            Snackbar.make(buttonView, "The switch is now " + position, Snackbar.LENGTH_SHORT).setAction( "Undo", click -> buttonView.setChecked(!isChecked)).show();
        });
        }


    public void checkBoxSnackbar(View v){
    CheckBox checkBox = findViewById( R.id.checkBox );
    checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
        String position;
        if ( !isChecked )
        {
            position = "off";
    }else position = "on";
        Snackbar.make(buttonView, "The switch is now " + position, Snackbar.LENGTH_SHORT).setAction( "Undo", click -> buttonView.setChecked(!isChecked)).show();
});
    }
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_linear);
    }
}