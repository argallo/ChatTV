package com.gallo.chattv;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.widget.ListView;

/**
 * Created by Gallo-Desktop on 8/20/2016.
 */
public class ChatActivity extends AppCompatActivity {

    private String channelName;
    private String showName;

    private ListView chatListView;
    private ChatAdapter chatAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle extras = getIntent().getExtras();
        channelName = extras.getString("SELECTED_CHANNEL");
        showName = extras.getString("CURRENT_SHOW");
        setContentView(R.layout.activity_chat);
        this.setTitle(channelName+": "+showName);

    }
}
