package com.example.mailbe.Controller;

import com.example.mailbe.Model.Contact;
import com.example.mailbe.Model.User;
import com.example.mailbe.Service.UserService;
import com.example.mailbe.Service.ValidationService;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Map;

@CrossOrigin()
@RequestMapping("/api/user")
@RestController
public class UserController {

    private final UserService userService;
    private final ValidationService validatorService;

    @Autowired
    public UserController(UserService userService, ValidationService validationService) {
        this.userService = userService;
        this.validatorService = validationService;
    }

    @PutMapping ("/getUser")
    public ResponseEntity<User> getUser(@RequestBody String json){
        User user = userService.getUserByEmail((String) new JSONObject(json).getString("user_email"));
        return ResponseEntity.ok(user);
    }

    @PutMapping("/getUserName")
    public ResponseEntity<String> getUserNameByEmail(@RequestBody String json){
        JSONObject jsonObject = new JSONObject(json);
        /*JSONObject response = new JSONObject();
        response.put("user_name",userService.getUserNameByEmail(jsonObject.getString("user_email")));*/
        return ResponseEntity.ok(userService.getUserNameByEmail(jsonObject.getString("user_email")));
    }

    @PostMapping("/addContact")
    public void addContact(@RequestBody Contact contact) {
        validatorService.checkEmailsInDB(contact.getEmails());
        userService.addContact(contact);
    }

    @PostMapping("/addEmailToContact")
    public void addEmailToContact(@RequestBody String json) {
        JSONObject jsonObject = new JSONObject(json);
        String email = jsonObject.getString("email");
        String contactId = jsonObject.getString("contact_id");

        validatorService.checkEmailsInDB(new HashSet<String>(List.of(email)));
        userService.addEmailToContact(email, contactId);
    }


    @SuppressWarnings("unchecked")
    @PutMapping("/deleteEmailsFromContact")
    public void deleteEmailsFromContact(@RequestBody Map<String,Object> map) {
        List<String> emails = (List<String>) map.get("emails");
        String contactId = (String) map.get("contact_id");
        userService.deleteEmailsFromContact(emails, contactId);
    }


    @SuppressWarnings("unchecked")
    @PutMapping("/deleteContacts")
    public void deleteContacts(@RequestBody Map<String,Object> map) {
        List<String> contactIds = (List<String>) map.get("contact_ids");
        String userId = (String) map.get("user_id");
        userService.deleteContacts(userId,contactIds);
    }


    @PutMapping("/getAllContacts")
    public ResponseEntity<List<Contact>> getAllContactsSortedBy(@RequestBody String json) {
        JSONObject jsonObject = new JSONObject(json);
        List<Contact> contacts = userService.getAllContacts(
                jsonObject.getString("user_id"),
                jsonObject.has("column") ? jsonObject.getString("column") : "");
        return ResponseEntity.ok(contacts);
    }


    @PutMapping("/updateContactName")
    public void updateNameInContact(@RequestBody String json) {
        JSONObject jsonObject = new JSONObject(json);
        String contactId = jsonObject.getString("contact_id");
        String newName = jsonObject.getString("new_name");

        validatorService.checkContactNameUnique(contactId, newName);
        userService.updateNameInContact(contactId, newName);
    }


    @PutMapping("/updateEmailInContact")
    public void updateEmailInContact(@RequestBody String json) {
        JSONObject jsonObject = new JSONObject(json);
        String contactId = jsonObject.getString("contact_id");
        String oldEmail = jsonObject.getString("old_email");
        String newEmail = jsonObject.getString("new_email");

        validatorService.checkEmailsInDB(new HashSet<String>(List.of(newEmail)));
        userService.updateEmailInContact(contactId, oldEmail, newEmail);
    }

    @PutMapping("/searchInContacts")
    public ResponseEntity<List<Contact>> searchInContactsBy(@RequestBody String json) {
        JSONObject jsonObject = new JSONObject(json);
        List<Contact> contacts = userService.searchInContactsBy(jsonObject.getString("user_id"),
                jsonObject.getString("col")
                , jsonObject.getString("key"));
        return ResponseEntity.ok(contacts);
    }


    @PutMapping("/getAllFolders")
    public ResponseEntity<HashSet<String>> getAllFolders(@RequestBody Map<String,Object> map){
        HashSet<String> folders = userService.getAllFolders((String) map.get("user_email"));
        return ResponseEntity.ok(folders);
    }

    @PostMapping("/addFolder")
    public void addFolder(@RequestBody String json) {
        JSONObject jsonObject = new JSONObject(json);
        userService.addFolder(jsonObject.getString("user_email"), jsonObject.getString("folder_name"));
    }

    @PutMapping("/renameFolder")
    public void renameFolder(@RequestBody String json) {
        JSONObject jsonObject = new JSONObject(json);
        userService.renameFolder(jsonObject.getString("user_email"),
                jsonObject.getString("oldfolder_name"),
                jsonObject.getString("newfolder_name"));
    }

    @SuppressWarnings("unchecked")
    @PutMapping("/deleteFolders")
    public void deleteFolders(@RequestBody Map<String,Object> map) {
        userService.deleteFolders((String) map.get("user_email"), (List<String>) map.get("folders_names"));
    }
}
