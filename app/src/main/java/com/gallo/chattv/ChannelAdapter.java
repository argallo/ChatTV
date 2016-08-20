package com.gallo.chattv;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Gallo-Desktop on 8/20/2016.
 */
public class ChannelAdapter extends ArrayAdapter<Channel> {

    private final Context context;
    private final ArrayList<Channel> channels;


    public ChannelAdapter(Context context, ArrayList<Channel> channels) {
        super(context, R.layout.listcell, channels);
        this.context = context;
        this.channels = channels;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.listcell, parent, false);
        TextView channel = (TextView) rowView.findViewById(R.id.channel);
        channel.setText(channels.get(position).getChannelName());

        TextView show = (TextView) rowView.findViewById(R.id.firstLine);
        show.setText(channels.get(position).getCurrentShow());


        return rowView;
    }

}
