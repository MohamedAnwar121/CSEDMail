package com.example.mailbe.Model;

import com.example.mailbe.Model.TypeConverter.InstantTypeConverter;
import com.example.mailbe.Validation.CSEDEmail;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.CascadeType;

import javax.persistence.*;
import javax.validation.constraints.Email;
import java.time.Instant;

@JsonPropertyOrder({"mail_id", "maildetails_id","sender", "reciever", "folder", "deleted_at"})
@Entity
@Table(name = "mail", schema = "public")
public class Mail {

    @Id
    @JsonProperty("mail_id")
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    @Column(name = "mail_id", nullable = false)
    private String mailId;

    @CSEDEmail
    @JsonProperty("sender")
    @Column(name = "sender", nullable = false)
    private String sender;

    @CSEDEmail
    @JsonProperty("reciever")
    @Column(name = "reciever", nullable = true)
    private String reciever;


    @Column(name = "folder", nullable = true)
    private String folder;


    @Column(name = "deleted_at", nullable = true)
    @Convert(converter = InstantTypeConverter.class)
    private Instant deletedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "maildetails_id")
    @JsonBackReference
    private MailDetails mailDetails;

    public Mail() {}


    public Mail(String mailId, String sender, String reciever,
                String folder, Instant deletedAt, MailDetails mailDetails) {
        this.mailId = mailId;
        this.sender = sender;
        this.reciever = reciever;
        this.folder = folder;
        this.deletedAt = deletedAt;
        this.mailDetails = mailDetails;
    }

    public String getMailId() {
        return mailId;
    }

    public void setMailId(String mailId) {
        this.mailId = mailId;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getReciever() {
        return reciever;
    }

    public void setReciever(String reciever) {
        this.reciever = reciever;
    }

    public String getFolder() {
        return folder;
    }

    public void setFolder(String folder) {
        this.folder = folder;
    }

    public Instant getDeletedAt() {
        return deletedAt;
    }

    public void setDeletedAt(Instant deletedAt) {
        this.deletedAt = deletedAt;
    }

    public MailDetails getMailDetails() {
        return mailDetails;
    }

    public void setMailDetails(MailDetails mail_details) {
        this.mailDetails = mail_details;
    }
}
