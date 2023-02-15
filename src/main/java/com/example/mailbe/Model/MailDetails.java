package com.example.mailbe.Model;

import com.example.mailbe.Model.TypeConverter.StringSetConverter;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.CascadeType;

import javax.persistence.*;
import javax.validation.constraints.Email;
import java.util.HashSet;
import java.util.List;

import static com.example.mailbe.Model.TypeConverter.StringSetConverter.SPLIT_CHAR;

@JsonPropertyOrder({"maildetails_id","subject", "message", "sended_at", "priority"})
@Entity
@Table(name = "mail_details", schema = "public")
public class MailDetails {

    @Id
    @JsonProperty("maildetails_id")
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    @Column(name = "maildetails_id", nullable = false)
    private String mailDetailsId;

    @JsonProperty("subject")
    @Column(name = "subject", nullable = true)
    private String subject;

    @JsonProperty("message")
    @Column(name = "message", nullable = true)
    private String message;


    @JsonProperty("sended_at")
    @Column(name = "sended_at",nullable = true)
    private String sendedAt;

    @JsonProperty("sended_at_seconds")
    @Column(name = "sended_at_seconds",nullable = true)
    private String sendedAtInSeconds;

    @JsonProperty("priority")
    @Column(name = "priority", nullable = false)
    private String priority;

    @JsonProperty("recievers")
    @Column(name = "recievers", nullable = true)
    @Convert(converter = StringSetConverter.class)
    private HashSet<@Email String> recievers;

    @JsonProperty("recievers_as_string")
    @Column(name = "recievers_as_string", nullable = true)
    private String recieversAsString;

    @OneToMany(fetch = FetchType.EAGER)
    @Cascade(CascadeType.ALL)
    @JoinColumn(name="maildetails_id")
    @JsonManagedReference
    private List<Attachment> attachments;

    @Cascade(CascadeType.ALL)
    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name="maildetails_id")
    @JsonManagedReference
    private List<Mail> mails;

    public MailDetails() {}

    public MailDetails(String mailDetailsId, String subject, String message, String sendedAt
            , String sendedAtInSeconds, String priority, HashSet<@Email String> recievers
            , String recieversAsString, List<Attachment> attachments, List<Mail> mails) {
        this.mailDetailsId = mailDetailsId;
        this.subject = subject;
        this.message = message;
        this.sendedAt = sendedAt;
        this.sendedAtInSeconds = sendedAtInSeconds;
        this.priority = priority;
        this.recievers = recievers;
        this.recieversAsString = recieversAsString;
        this.attachments = attachments;
        this.mails = mails;
    }

    public String getMailDetailsId() {
        return mailDetailsId;
    }

    public void setMailDetailsId(String id) {
        this.mailDetailsId = id;
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


    public List<Mail> getMails() {
        return mails;
    }

    public void setMails(List<Mail> mails) {
        this.mails = mails;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public HashSet<String> getRecievers() {
        return recievers;
    }

    public void setRecievers(HashSet<String> recievers) {
        this.recievers = recievers;
    }

    public String getRecieversAsString() {
        return recieversAsString;
    }

    public void setRecieversAsString(String recieversAsString) {
        this.recieversAsString = recieversAsString;
    }

    public void setRecieversAsString(HashSet<String> recievers) {
        this.recieversAsString = recievers != null ? String.join(SPLIT_CHAR, recievers) : "";
    }

    public List<Attachment> getAttachments() {
        return attachments;
    }

    public void setAttachments(List<Attachment> attachments) {
        this.attachments = attachments;
    }

    public String getSendedAt() {
        return sendedAt;
    }

    public void setSendedAt(String sendedAt) {
        this.sendedAt = sendedAt;
    }

    public String getSendedAtInSeconds() {
        return sendedAtInSeconds;
    }

    public void setSendedAtInSeconds(String sendedAtInSeconds) {
        this.sendedAtInSeconds = sendedAtInSeconds;
    }

}
