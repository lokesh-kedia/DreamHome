package com.LBS.DreamHome;

public class ChatMessage {

    private String message;
    private String sender;
    private String time;
    private String name;


    public ChatMessage() {
    }


    public ChatMessage(String message, String sender, String time, String name) {
        this.message = message;
        this.sender = sender;
        this.time = time;
        this.name = name;

    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


}