package com.gallo.chattv;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.HashMap;
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
    private class Listings extends AsyncTask<Void, Void, Void> {


        @Override
        protected Void doInBackground(Void... params) {
            try {
               /* Document document = Jsoup.connect(URL).get();
                Elements channels = document.getElementsByClass("zc-st-c"); // gets all tv channel names
                Elements shows = document.getElementsByClass("zc-pg-t"); // gets all tv channel names

                for(Element channel:channels) {
                    Log.i("CHAT", channel.text());
                }
                */
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

        }
    }



}
