package com.gallo.chattv;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Gallo-Desktop on 8/20/2016.
 */
public class ChannelAdapter extends ArrayAdapter<String> {

    private final Context context;
    private final String[] channels;
    private final String[] shows;

    public ChannelAdapter(Context context, String[] channels, String[] shows) {
        super(context, R.layout.listcell, channels);
        this.context = context;
        this.channels = channels;
        this.shows = shows;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.listcell, parent, false);
        TextView channel = (TextView) rowView.findViewById(R.id.channel);
        channel.setText(channels[position]);

        TextView show = (TextView) rowView.findViewById(R.id.firstLine);
        show.setText(shows[position]);

        TextView description = (TextView) rowView.findViewById(R.id.secondLine);
        description.setText("");
        return rowView;
    }

}
