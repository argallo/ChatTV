package com.gallo.chattv;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private final static String URL = "http://tvschedule.zap2it.com/tvlistings/ZCGrid.do?aid=tvschedule";
    public ArrayList<Channel> listing;
    public ListView listView;
    public ChannelAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listing = new ArrayList<Channel>();
        new Listings().execute("");

    }

    private class Listings extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            try {
            Document document = Jsoup.connect(URL).get();
            int x = 0;
            for (Element table : document.select("table")) {
                for (Element row : table.select("tr")) {
                    if(x == 0) {
                        Elements tds = row.select("td");
                        listing.add(new Channel(tds.get(0).text(), tds.get(1).text()));
                    }
                }
            }
        } catch(Exception e){

        }
        return "Executed";
    }

        @Override
        protected void onPostExecute(String aVoid) {
            super.onPostExecute(aVoid);
            if(listView != null){
                adapter.clear();
                adapter.addAll(listing);
                adapter.notifyDataSetChanged();
                listView.invalidateViews();
                listView.refreshDrawableState();
            } else {
                listView = (ListView) findViewById(R.id.listview);
                adapter = new ChannelAdapter(MainActivity.this, listing);
                listView.setAdapter(adapter);

                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                    @Override
                    public void onItemClick(AdapterView<?> parent, final View view,
                                            int position, long id) {
                        final Channel selectedChannel = (Channel) parent.getItemAtPosition(position);
                        Intent intent = new Intent(getBaseContext(), ChatActivity.class);
                        intent.putExtra("SELECTED_CHANNEL", selectedChannel.getChannelName());
                        intent.putExtra("CURRENT_SHOW", selectedChannel.getCurrentShow());
                        startActivity(intent);
                    }

                });
            }

        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.refresh, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.refresh:
                listing = new ArrayList<Channel>();
                new Listings().execute();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
