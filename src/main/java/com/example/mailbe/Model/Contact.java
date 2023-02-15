package com.example.mailbe.Model;

import com.example.mailbe.Model.TypeConverter.StringSetConverter;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.Email;
import java.util.HashSet;

@JsonPropertyOrder({"contact_id", "name", "emails", "user"})
@Entity
@Table(name = "contact", schema = "public")
public class Contact {

    @Id
    @JsonProperty("contact_id")
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    @Column(name = "contact_id", nullable = false)
    private String contactId;

    @JsonProperty("name")
    @Column(name = "name", nullable = false)
    private String name;

    @JsonProperty("emails")
    @Column(name = "emails", nullable = false)
    @Convert(converter = StringSetConverter.class)
    private HashSet<@Email String> emails;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @JsonBackReference
    private User user;


    public Contact() {}

    public Contact(String contactId, String name, HashSet<@Email String> emails, User user) {
        this.contactId = contactId;
        this.name = name;
        this.emails = emails;
        this.user = user;
    }


    public String getContactId() {
        return contactId;
    }

    public void setContactId(String contactId) {
        this.contactId = contactId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public HashSet<String> getEmails() {
        return emails;
    }

    public void setEmails(HashSet<String> emails) {
        this.emails = emails;
    }
}
