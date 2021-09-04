package com.test.model;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.internet.MimeBodyPart;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class EmailMessage {

    private SimpleStringProperty sender;
    private SimpleStringProperty recepient;
    private SimpleStringProperty subject;
    private SimpleObjectProperty<SizeInteger> size;
    private SimpleObjectProperty<Date> date;
    private Message message;
    private boolean isRead;

    private List<MimeBodyPart> attachmentList = new ArrayList<MimeBodyPart>();

    private boolean hasAttachments = false;

    public EmailMessage(String subject, String sender, String recepient, int size, Date date, boolean isRead, Message message) {

        this.sender = new SimpleStringProperty(sender);
        this.recepient = new SimpleStringProperty(recepient);
        this.subject = new SimpleStringProperty(subject);
        this.size = new SimpleObjectProperty<SizeInteger>(new SizeInteger(size));
        this.date = new SimpleObjectProperty<Date>(date);
        this.isRead = isRead;
        this.message = message;
    }

    public String getSubject() {
        return this.subject.get();
    }

    public List<MimeBodyPart> getAttachmentList() {
        return attachmentList;
    }

    public boolean hasAttachments() {

        return hasAttachments;
    }

    public String getSender() {
        return this.sender.get();
    }

    public String getRecepient() {
        return this.recepient.get();
    }

    public SizeInteger getSize() {
        return this.size.get();
    }

    public Date getDate() {
        return this.date.get();
    }

    public boolean isRead() {
        return isRead;
    }

    public void setRead(boolean read) {
        this.isRead = read;
    }

    public Message getMessage() {
        return this.message;
    }

    public void addAttachment(MimeBodyPart mbp) {
        hasAttachments = true;
        try {
            this.attachmentList.add(mbp);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}