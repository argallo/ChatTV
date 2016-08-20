package com.gallo.chattv;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class MainActivity extends AppCompatActivity {

    private final static String URL = "http://tvschedule.zap2it.com/tvlistings/ZCGrid.do?aid=tvschedule";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        new Listings().execute();

    }
    private class Listings extends AsyncTask<Void, Void, Void> {


        @Override
        protected Void doInBackground(Void... params) {
            try {
                Document document = Jsoup.connect(URL).get();
                Elements elements = document.getElementsByClass("zc-pg-t");
                for(Element element:elements) {
                    Log.i("CHAT",element.outerHtml());
                }
            } catch(Exception e){

            }
            return null;
        }
    }



}
