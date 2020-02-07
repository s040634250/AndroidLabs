package com.example.androidlabs;

public class Message {

    private String messageText;
    private boolean isSent;
    private long id;

    public Message(String m, boolean b, long i) {
        setMessageText(m);
        setSent(b);
        setId(i);
    }

    public Message() {
        this("Default", true, 69);
    }

    public String getMessageText() {
        return messageText;
    }

    public void setMessageText(String messageText) {
        this.messageText = messageText;
    }

    public boolean isSent() {
        return isSent;
    }

    public void setSent(boolean sent) {
        isSent = sent;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
