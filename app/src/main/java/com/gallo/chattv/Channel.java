package com.gallo.chattv;

/**
 * Created by Gallo-Desktop on 8/20/2016.
 */
public class Channel {

    private String channelName;
    private String currentShow;


    public Channel(String channelName, String currentShow){
        this.channelName = channelName;
        this.currentShow = currentShow;
    }

    public String getChannelName() {
        return channelName;
    }

    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }

    public String getCurrentShow() {
        return currentShow;
    }

    public void setCurrentShow(String currentShow) {
        this.currentShow = currentShow;
    }
}
