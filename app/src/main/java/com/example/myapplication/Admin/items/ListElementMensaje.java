package com.example.myapplication.Admin.items;


public class ListElementMensaje {
    private String text;
    private String senderId;
    private String receiverId;
    private long timestamp;


    public ListElementMensaje() {
    }

    // Constructor, getters y setters
    public ListElementMensaje(String text, String senderId, String receiverId, long timestamp) {
        this.text = text;
        this.senderId = senderId;
        this.receiverId = receiverId;
        this.timestamp = timestamp;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    public String getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(String receiverId) {
        this.receiverId = receiverId;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
}
