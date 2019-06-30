package com.ge.music.media;

import java.util.Map;

public class MessageEvent {

    private int event;
    private Map<String,Object> data;

    public MessageEvent() {
    }

    public MessageEvent(int event, Map<String, Object> data) {
        this.event = event;
        this.data = data;
    }

    public int getEvent() {
        return event;
    }

    public void setEvent(int event) {
        this.event = event;
    }

    public Map<String, Object> getData() {
        return data;
    }

    public void setData(Map<String, Object> data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "MessageEvent{" +
                "event=" + event +
                ", data=" + data +
                '}';
    }
}
