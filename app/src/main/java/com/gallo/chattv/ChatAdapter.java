package com.gallo.chattv;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Gallo-Desktop on 8/20/2016.
 */
public class ChatAdapter extends ArrayAdapter<String> {

    private final Context context;
    private final ArrayList<String> messages;


    public ChatAdapter(Context context, ArrayList<String> messages) {
        super(context,R.layout.messagecell, messages);
        this.context = context;
        this.messages = messages;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.messagecell, parent, false);
        TextView channel = (TextView) rowView.findViewById(R.id.message);
        channel.setText(messages.get(position));

        return rowView;
    }

}
