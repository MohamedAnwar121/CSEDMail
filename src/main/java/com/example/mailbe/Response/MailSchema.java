package com.example.mailbe.Response;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Collection;
import java.util.HashSet;
import java.util.Map;

public class MailSchema {

    @JsonProperty("mail_id")
    private String mail_id;
    private String sended_at;

    @JsonProperty("subject")
    private String subject;

    @JsonProperty("message")
    private String message;

    @JsonProperty("priority")
    private String priority;

    private String sender;
    private HashSet<String> recievers;
    private String folder;
    private String deleted_at;

    public MailSchema() {}

    @SuppressWarnings("unchecked")
    @JsonProperty("mail")
    private void unPackNested(Map<String,Object> mail) {
        this.sender = (String) mail.get("sender");
        this.recievers = new HashSet<>((Collection<? extends String>) mail.get("reciever"));
    }

    public MailSchema(String mail_id, String sended_at, String subject, String message
            , String priority, String sender, HashSet<String> recievers, String folder, String deleted_at) {
        this.mail_id = mail_id;
        this.sended_at = sended_at;
        this.subject = subject;
        this.message = message;
        this.priority = priority;
        this.sender = sender;
        this.recievers = recievers;
        this.folder = folder;
        this.deleted_at = deleted_at;
    }


    public String getMail_id() {
        return mail_id;
    }

    public void setMail_id(String mail_id) {
        this.mail_id = mail_id;
    }

    public String getSended_at() {
        return sended_at;
    }

    public void setSended_at(String sended_at) {
        this.sended_at = sended_at;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public HashSet<String> getRecievers() {
        return recievers;
    }

    public void setRecievers(HashSet<String> recievers) {
        this.recievers = recievers;
    }

    public String getFolder() {
        return folder;
    }

    public void setFolder(String folder) {
        this.folder = folder;
    }

    public String getDeleted_at() {
        return deleted_at;
    }

    public void setDeleted_at(String deleted_at) {
        this.deleted_at = deleted_at;
    }
}
