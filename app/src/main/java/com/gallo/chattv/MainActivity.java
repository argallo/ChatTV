package com.gallo.chattv;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
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
    public Map<String, String> listing;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listing = new HashMap<>();
        new Listings().execute();

    }

    private class StableArrayAdapter extends ArrayAdapter<String> {

        HashMap<String, Integer> mIdMap = new HashMap<String, Integer>();

        public StableArrayAdapter(Context context, int textViewResourceId,
                                  List<String> objects) {
            super(context, textViewResourceId, objects);
            for (int i = 0; i < objects.size(); ++i) {
                mIdMap.put(objects.get(i), i);
            }
        }

        @Override
        public long getItemId(int position) {
            String item = getItem(position);
            return mIdMap.get(item);
        }

        @Override
        public boolean hasStableIds() {
            return true;
        }

    }
    private class Listings extends AsyncTask<Void, Void, Void> {


        @Override
        protected Void doInBackground(Void... params) {
            try {
                Document document = Jsoup.connect(URL).get();
                int x = 0;
                for (Element table : document.select("table")) {
                    for (Element row : table.select("tr")) {
                        if(x == 0) {
                            Elements tds = row.select("td");
                            listing.put(tds.get(0).text(), tds.get(1).text());
                        }
                    }
                }
            } catch(Exception e){

            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            for (Map.Entry<String, String> entry : listing.entrySet()) {
                System.out.println(entry.getKey()+" : "+entry.getValue());

            }

            final ListView listview = (ListView) findViewById(R.id.listview);
            ChannelAdapter adapter = new ChannelAdapter(MainActivity.this,
                    listing.keySet().toArray(new String[listing.size()]),
                    listing.values().toArray(new String[listing.size()]));
            listview.setAdapter(adapter);

            listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> parent, final View view,
                                        int position, long id) {
                    final String item = (String) parent.getItemAtPosition(position);

                }

            });

        }
    }



}
