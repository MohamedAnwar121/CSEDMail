package com.example.mailbe.Model;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;

@JsonPropertyOrder({})
@Entity
@Table(name = "attachment", schema = "public")
public class Attachment {

    @Id
    @JsonProperty("attachment_id")
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    @Column(name = "attachment_id", nullable = false)
    private String attachmentId;


    @JsonProperty("name")
    @Column(name = "name", nullable = false)
    private String name;

    @JsonProperty("type")
    @Column(name = "type", nullable = false)
    private String type;

    @Lob
    @JsonProperty("data")
    @Column(name = "data", nullable = false)
    private byte[] data;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "maildetails_id")
    @JsonBackReference
    private MailDetails mailDetails;

    public Attachment(String name, String type, byte[] data) {
        this.name = name;
        this.type = type;
        this.data = data;
    }

    public Attachment() {}

    public String getAttachmentId() {
        return attachmentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }

}
