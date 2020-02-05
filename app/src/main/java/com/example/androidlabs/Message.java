package com.example.androidlabs;

public class Message {

    private String messageText;
    private boolean isSent;

    public Message(String m, boolean b){
        setMessageText(m);
        setSent(b);
    }

    public Message(){
        this("Default",true);
    }

    public String getMessageText(){
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
}
