package com.gallo.chattv;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.ExtractedText;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by Gallo-Desktop on 8/20/2016.
 */
public class ChatActivity extends AppCompatActivity {

    private String channelName;
    private String showName;
    private final static String GetChat = "http://chattv.x10host.com/chat.php?id=";
    private final static String SendMesage = "http://chattv.x10host.com/addmessage.php?id=";
    private final static String messageGet = "&message=";



    private ListView chatListView;
    private ChatAdapter chatAdapter;
    private EditText editText;
    private Button send;

    public String currentMessage = "";
    public ArrayList<String> allmessages;
    public String[] newMessages;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle extras = getIntent().getExtras();
        channelName = extras.getString("SELECTED_CHANNEL");
        showName = extras.getString("CURRENT_SHOW");
        setContentView(R.layout.activity_chat);
        this.setTitle(channelName+": "+showName);
        chatListView = (ListView)findViewById(R.id.listView);
        allmessages = new ArrayList<String>();
        chatAdapter = new ChatAdapter(this, allmessages);
        chatListView.setAdapter(chatAdapter);
        editText = (EditText)findViewById(R.id.editText);
        send = (Button)findViewById(R.id.button);
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendMessage();
            }
        });
        editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEND) {
                    sendMessage();
                    return true;
                }
                return false;
            }
        });

        new Messaging().execute("do");

        new Thread(new Runnable() {
            @Override
            public void run() {
                while(true) {
                    try {
                        Thread.sleep(5000);
                        new Messaging().execute("reload");
                    } catch (Exception e) {

                    }
                }
            }
        }).start();
    }

    public void sendMessage(){
        if(!editText.getText().toString().equals("")) {
            currentMessage = editText.getText().toString();

            allmessages.add(currentMessage);
            chatAdapter.add(currentMessage);
            currentMessage = currentMessage.replaceAll(" ", "%20");
            chatAdapter.notifyDataSetChanged();
            chatListView.setSelection(chatAdapter.getCount()-1);
            editText.setText("");
            editText.clearFocus();
            new Send().execute("send");
        }
    }

    private class Send extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            try {
                Document document = Jsoup.connect(SendMesage+channelName+messageGet+currentMessage).get();
                currentMessage = "";
            } catch (Exception e) {

            }
            return "Executed";
        }
    }

    private class Messaging extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            try {
                Document document = Jsoup.connect(GetChat+channelName).get();
                String messages = document.body().text();
                messages = messages.replaceAll("%20", " ");
                Log.i("tag", messages);
                newMessages = messages.split("~~");
                Log.i("first", newMessages[0]);
            } catch (Exception e) {

            }
            return "Executed";
        }

        @Override
        protected void onPostExecute(String aVoid) {
            super.onPostExecute(aVoid);
            if(newMessages.length > allmessages.size()){
                chatAdapter.clear();
                chatAdapter.addAll(newMessages);
                allmessages = new ArrayList<>(Arrays.asList(newMessages));
                chatAdapter.notifyDataSetChanged();
                chatListView.setSelection(chatAdapter.getCount()-1);
            }
        }
    }



}
