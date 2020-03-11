package com.example.androidlabs;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Fragment;

import androidx.fragment.app.FragmentTransaction;

import java.util.ArrayList;

public class ChatRoomActivity extends AppCompatActivity {
    ListView chat;
    Button receive;
    Button send;
    EditText messager;
    ArrayList<Message> messageList = new ArrayList<>();
    BaseAdapter a;
    Message message;
    SQLiteDatabase db;
    FrameLayout fragment;
    private boolean tablet;

    public static final String MESSAGE = "MESSAGE";
    public static final String ID = "ID";
    public static final String SENT = "SENT";
    public static final String TABLET = "TABLET";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_room);

        chat = findViewById(R.id.ListView);
        receive = findViewById(R.id.ReceiveButton);
        send = findViewById(R.id.SendButton);
        messager = findViewById(R.id.ChatMessage);
        fragment = findViewById(R.id.frameLayout);
        if (fragment == null) {
            tablet = false;
        } else tablet = true;
        chat.setAdapter(a = new myListAdapter());

        DbOpenHelper dbOpener = new DbOpenHelper(this);
        db = dbOpener.getWritableDatabase();

        String[] columns = {DbOpenHelper.ID_COLUMN, DbOpenHelper.SENT_COLUMN, DbOpenHelper.MESSAGE_COLUMN};
        Cursor result = db.query(false, DbOpenHelper.TABLE_NAME, columns, null, null, null, null, null, null);

        int idIndex = result.getColumnIndex(DbOpenHelper.ID_COLUMN);
        int sentIndex = result.getColumnIndex(DbOpenHelper.SENT_COLUMN);
        int messageIndex = result.getColumnIndex(DbOpenHelper.MESSAGE_COLUMN);

        while (result.moveToNext()) {

            long id = result.getLong(idIndex);
            boolean isSent = false;
            if (result.getInt(sentIndex) == 1) isSent = true;
            String message = result.getString(messageIndex);

            messageList.add(new Message(message, isSent, id));
        }

        printCursor(result, db.getVersion());

        send.setOnClickListener(v -> {
            String sentMessage = messager.getText().toString();
            ContentValues dbInsert = new ContentValues();
            dbInsert.put(DbOpenHelper.SENT_COLUMN, 1);
            dbInsert.put(DbOpenHelper.MESSAGE_COLUMN, sentMessage);

            long newID = db.insert(DbOpenHelper.TABLE_NAME, null, dbInsert);

            message = new Message(messager.getText().toString(), true, newID);
            messager.setText("");
            messageList.add(message);

            a.notifyDataSetChanged();
        });
        receive.setOnClickListener(v -> {
            String receivedMessage = messager.getText().toString();
            ContentValues dbInsert = new ContentValues();
            dbInsert.put(DbOpenHelper.SENT_COLUMN, 0);
            dbInsert.put(DbOpenHelper.MESSAGE_COLUMN, receivedMessage);

            long newID = db.insert(DbOpenHelper.TABLE_NAME, null, dbInsert);

            message = new Message(messager.getText().toString(), false, newID);
            messager.setText("");
            messageList.add(message);

            a.notifyDataSetChanged();
        });
    }

    private class myListAdapter extends BaseAdapter {

        public int getCount() {
            return messageList.size();
        } //This function tells how many objects to show

        public Message getItem(int position) {
            return messageList.get(position);
        }  //This returns the string at position p

        public long getItemId(int p) {
            return getItem(p).getId();
        } //This returns the database id of the item at position p

        public View getView(int p, View thisRow, ViewGroup parent) {
            if (getItem(p).isSent()) {
                thisRow = getLayoutInflater().inflate(R.layout.table_row_layout, parent, false);
                TextView sentText = thisRow.findViewById(R.id.sentField);
                sentText.setText(getItem(p).getMessageText());
            } else {
                thisRow = getLayoutInflater().inflate(R.layout.table_row_layout2, parent, false);
                TextView receiveText = thisRow.findViewById(R.id.receivedField);
                receiveText.setText(getItem(p).getMessageText());
            }
            chat.setOnItemLongClickListener();
            chat.setOnItemClickListener((parent1, view, position, id) -> {

                Bundle muhData = new Bundle();
                muhData.putString(MESSAGE, getItem(p).getMessageText());
                muhData.putLong(ID, getItemId(p));
                muhData.putBoolean(SENT, getItem(p).isSent());
                muhData.putBoolean(TABLET, tablet);

                if (tablet) {
                    DetailsFragment frag = new DetailsFragment();
                    frag.setArguments(muhData);
                    FragmentTransaction ft = getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, frag);
                    ft.commit();

                } else {
                    Intent goToDetails = new Intent(ChatRoomActivity.this, EmptyActivity.class);
                    startActivityForResult(goToDetails, 345);
                }
            });

            return thisRow;
        }
    }

    public void printCursor(Cursor c, int v) {
        int columnCount = c.getColumnCount();
        String columnNames = TextUtils.join(", ", c.getColumnNames());
        int rowCount = c.getCount();


        int idColumn = c.getColumnIndex(DbOpenHelper.ID_COLUMN);
        int sentColumn = c.getColumnIndex(DbOpenHelper.SENT_COLUMN);
        int messageColumn = c.getColumnIndex(DbOpenHelper.MESSAGE_COLUMN);


        Log.i("dbVersion", "Verion Number: " + v);
        Log.i("columnCount", "Column Count: " + columnCount);
        Log.i("columnNames", "Column Names: " + columnNames);
        Log.i("rowCount", "Row Count: " + rowCount);

        while (c.moveToPrevious()) {
            boolean isSent = false;

            long id = c.getLong(idColumn);
            int sent = c.getInt(sentColumn);
            String message = c.getString(messageColumn);

            if (sent == 1) isSent = true;

            Log.i("rowData", id + "\t" + isSent + "\t" + message);
        }
    }
}

