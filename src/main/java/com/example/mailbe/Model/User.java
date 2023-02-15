package com.example.mailbe.Model;

import com.example.mailbe.Model.TypeConverter.StringSetConverter;
import com.example.mailbe.Validation.CSEDEmail;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.CascadeType;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.HashSet;
import java.util.List;


@JsonPropertyOrder(
        {"user_id", "name", "email", "password", "role", "custom_folders"})
@Entity
@Table(name = "user", schema = "public")
public class User {

    @Id
    @JsonProperty("user_id")
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    @Column(name = "user_id", nullable = false)
    private String userId;

    @NotBlank
    @JsonProperty("name")
    @Column(name = "name", nullable = false)
    private String name;

    @CSEDEmail
    @JsonProperty("email")
    @Column(name = "email", nullable = false)
    private String email;

    @NotBlank
    @JsonProperty("password")
    @Column(name = "password", nullable = false)
    private String password;

    @JsonProperty("role")
    @Column(name = "role", nullable = false)
    private String role;

    @JsonProperty("custom_folders")
    @Column(name = "custom_folders")
    @Convert(converter = StringSetConverter.class)
    private HashSet<String> customFolders;

    @Cascade(CascadeType.ALL)
    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @JsonManagedReference
    private List<Contact> contacts;

    public User() {}

    public User(String userId, String name, String email, String password, String role, HashSet<String> customFolders, List<Contact> contacts) {
        this.userId = userId;
        this.name = name;
        this.email = email;
        this.password = password;
        this.role = role;
        this.customFolders = customFolders;
        this.contacts = contacts;
    }

    public HashSet<String> getCustomFolders() {
        return customFolders;
    }

    public void setCustomFolders(HashSet<String> customFolders) {
        this.customFolders = customFolders;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String id) {
        this.userId = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public List<Contact> getContacts() {
        return contacts;
    }

    public void setContacts(List<Contact> contacts) {
        this.contacts = contacts;
    }

    @PrePersist
    void preInsert() {
        if (this.role == null)
            this.role = "user";
    }
}
