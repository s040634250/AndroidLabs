package com.example.androidlabs;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class ChatRoomActivity extends AppCompatActivity {
    ListView chat;
    Button receive;
    Button send;
    EditText messager;
    ArrayList<Message> messageList = new ArrayList<>();
    BaseAdapter a;
    Message message;
    int sentInitialized;
    int receivedInitialized;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_room);

        chat = findViewById(R.id.ListView);
        receive = findViewById(R.id.ReceiveButton);
        send = findViewById(R.id.SendButton);
        messager = findViewById(R.id.ChatMessage);

        chat.setAdapter(a = new myListAdapter());


        send.setOnClickListener(v->{
                message = new Message(messager.getText().toString(), true);
                messager.setText("");
                messageList.add(message);
                a.notifyDataSetChanged();
                });
        receive.setOnClickListener(v->{
            message = new Message(messager.getText().toString(), false);
            messager.setText("");
            messageList.add(message);
            a.notifyDataSetChanged();
        });
    }

    private class myListAdapter extends BaseAdapter {

            public int getCount() {
                return messageList.size();  } //This function tells how many objects to show

            public Message getItem(int position) {
                return messageList.get(position);  }  //This returns the string at position p

            public long getItemId(int p) {
                return p; } //This returns the database id of the item at position p

            public View getView(int p, View thisRow, ViewGroup parent)
            {
                    if(getItem(p).isSent()){
                            thisRow = getLayoutInflater().inflate(R.layout.table_row_layout, parent, false);
                            TextView sentText = thisRow.findViewById(R.id.sentField);
                            sentText.setText(getItem(p).getMessageText());
                            sentInitialized = 1;
                    }else{
                            thisRow = getLayoutInflater().inflate(R.layout.table_row_layout2, parent, false);
                            TextView receiveText = thisRow.findViewById(R.id.receivedField);
                            receiveText.setText(getItem(p).getMessageText());
                            receivedInitialized = 1;
                    }

                chat.setOnItemLongClickListener((parent1, view, position, id) -> {
                    String alert1 = getResources().getString(R.string.AlertDialog1);
                    String alert2 = getResources().getString(R.string.AlertDialog2);

                    AlertDialog.Builder builder = new AlertDialog.Builder(ChatRoomActivity.this);
                    builder.setMessage(alert1+position+"\n"+alert2+getItemId(position));
                    builder.setTitle(R.string.AlertDialogTitle);
                    builder.setNegativeButton(R.string.cancel, (dialog, which) -> dialog.dismiss());
                    builder.setPositiveButton(R.string.delete, (dialog, which) -> {
                        messageList.remove(getItem(position));a.notifyDataSetChanged();});


                    builder.create();
                    builder.show();

                    return true;
                });

                return thisRow;
            }
    }

    }

