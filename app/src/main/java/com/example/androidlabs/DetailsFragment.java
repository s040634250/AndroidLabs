package com.example.androidlabs;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 */
public class DetailsFragment extends Fragment {
    TextView message;
    TextView id;
    CheckBox sent;
    Button hideButton;
    private Bundle muhData;

    public DetailsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        muhData = getArguments();

        String messageString = muhData.getString("MESSAGE");
        long messageId = muhData.getLong("ID");
        boolean isSent = muhData.getBoolean("SENT");
        boolean tablet = muhData.getBoolean("TABLET");

        // Inflate the layout for this fragment
        View thisMessage = inflater.inflate(R.layout.fragment_details, container, false);
        message = thisMessage.findViewById(R.id.messageString);
        id = thisMessage.findViewById(R.id.id);
        sent = thisMessage.findViewById(R.id.sentBool);
        hideButton = thisMessage.findViewById(R.id.button6);

        message.setText(messageString);
        id.setText(String.valueOf(messageId));
        sent.setChecked(isSent);
        hideButton.setOnClickListener(v -> {

            if (tablet) {
                ChatRoomActivity parent = (ChatRoomActivity) getActivity();
                parent.getSupportFragmentManager().beginTransaction().remove(this).commit();
            } else {
                EmptyActivity parent = (EmptyActivity) getActivity();
                parent.finish(); //go back
            }
        });
        return thisMessage;
    }


}
