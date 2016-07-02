package com.dclover.gpsutilities.Model;

/**
 * Created by MY PC on 05/06/2016.
 */
public class Message {
    private String mText;
    private String mSender;
    private String mDate;

    public Message() {
    }

    public Message(String mText, String mSender) {
        this.mText = mText;
        this.mSender = mSender;
    }

    public Message(String mText, String mSender, String mDate) {
        this.mText = mText;
        this.mSender = mSender;
        this.mDate = mDate;
    }

    public String getmDate() {
        return mDate;
    }

    public void setmDate(String mDate) {
        this.mDate = mDate;
    }

    public String getText() {
        return mText;
    }

    public void setText(String text) {
        mText = text;
    }

    public String getSender() {
        return mSender;
    }

    public void setSender(String sender) {
        mSender = sender;
    }
}
