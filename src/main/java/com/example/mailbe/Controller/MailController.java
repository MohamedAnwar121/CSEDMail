package com.example.mailbe.Controller;

import com.example.mailbe.Response.MailSchema;
import com.example.mailbe.Service.MailService;
import com.example.mailbe.Util.DefaultMailFolder;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@CrossOrigin()
@RequestMapping("/api/mail")
@RestController
public class MailController {
    private final MailService mailService;

    @Autowired
    public MailController(MailService mailService) {
        this.mailService = mailService;
    }

    @PostMapping("/compose")
    public ResponseEntity<String> compose(@RequestBody MailSchema mailSchema) {
        String mailDetailsId = mailService.compose(mailSchema);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("mail_details_id",mailDetailsId);
        return ResponseEntity.ok(jsonObject.toString());
    }

    @PostMapping("/saveToDraft")
    public ResponseEntity<String> saveToDraft(@RequestBody MailSchema mailSchema) {
        String mailDetailsId = mailService.saveToDraft(mailSchema);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("mail_details_id",mailDetailsId);
        return ResponseEntity.ok(jsonObject.toString());
    }

    /*@PutMapping("/updateDraft")
    public ResponseEntity<String> updateDraft(@RequestBody MailSchema mailSchema) {
        String mailDetailsId = mailService.updateDraft(mailSchema);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("mail_details_id",mailDetailsId);
        return ResponseEntity.ok(jsonObject.toString());
    }*/


    @PostMapping("/inbox")
    public ResponseEntity<List<MailSchema>> getInbox(@RequestBody String json) {
        JSONObject jsonObject = new JSONObject(json);
        List<MailSchema> mails = mailService.getFolder(DefaultMailFolder.Inbox.name(),
                jsonObject.getString("user_email")
                , jsonObject.getInt("page_index")
                , jsonObject.has("sort_column") ? jsonObject.getString("sort_column") : "sended_at");
        return ResponseEntity.ok(mails);
    }


    @PostMapping("/draft")
    public ResponseEntity<List<MailSchema>> getDraft(@RequestBody String json) {
        JSONObject jsonObject = new JSONObject(json);
        List<MailSchema> mails = mailService.getFolder(DefaultMailFolder.Draft.name()
                , jsonObject.getString("user_email")
                , jsonObject.getInt("page_index")
                , jsonObject.has("sort_column") ? jsonObject.getString("sort_column") : "sended_at");
        return ResponseEntity.ok(mails);
    }

    @PostMapping("/sent")
    public ResponseEntity<List<MailSchema>> getSent(@RequestBody String json) {
        JSONObject jsonObject = new JSONObject(json);
        List<MailSchema> mails = mailService.getFolder(DefaultMailFolder.Sent.name()
                , jsonObject.getString("user_email")
                , jsonObject.getInt("page_index"),
                jsonObject.has("sort_column") ? jsonObject.getString("sort_column") : "sended_at");
        return ResponseEntity.ok(mails);
    }

    @PostMapping("/trash")
    public ResponseEntity<List<MailSchema>> getTrash(@RequestBody String json) {
        JSONObject jsonObject = new JSONObject(json);
        List<MailSchema> mails = mailService.getFolder(DefaultMailFolder.Trash.name()
                , jsonObject.getString("user_email")
                , jsonObject.getInt("page_index"),
                jsonObject.has("sort_column") ? jsonObject.getString("sort_column") : "sended_at");
        return ResponseEntity.ok(mails);
    }

    @PostMapping("/loadCustomFolder")
    public ResponseEntity<List<MailSchema>> getCustomFolder(@RequestBody String json) {
        JSONObject jsonObject = new JSONObject(json);
        List<MailSchema> mails = mailService.getFolder(jsonObject.getString("folder")
                , jsonObject.getString("user_email")
                , jsonObject.getInt("page_index"),
                jsonObject.has("sort_column") ? jsonObject.getString("sort_column") : "sended_at");
        return ResponseEntity.ok(mails);
    }

    @PutMapping("/getFolderSize")
    public ResponseEntity<String> getFolderSize(@RequestBody Map<String,Object> map){
        long folderSize = mailService.
                getFolderSize((String) map.get("user_email"), (String) map.get("folder_name"));
        return ResponseEntity.ok(String.valueOf(folderSize));
    }

    @PostMapping("/searchInInbox")
    public ResponseEntity<List<MailSchema>> searchInInboxWithCol(@RequestBody String json) {
        JSONObject jsonObject = new JSONObject(json);
        List<MailSchema> mails = mailService.searchInFolderWithCol(DefaultMailFolder.Inbox.name(),
                jsonObject.getString("user_email"),
                jsonObject.getInt("page_index"),
                jsonObject.getString("column"),
                jsonObject.getString("key"));
        return ResponseEntity.ok(mails);
    }

    @PostMapping("/searchInFolder")
    public ResponseEntity<List<MailSchema>> searchInFolderWithCol(@RequestBody String json) {
        JSONObject jsonObject = new JSONObject(json);
        List<MailSchema> mails = mailService.searchInFolderWithCol(jsonObject.getString("user_email"),
                jsonObject.getString("folder"),
                jsonObject.getInt("page_index"),
                jsonObject.getString("column"),
                jsonObject.getString("key"));
        return ResponseEntity.ok(mails);
    }

    @PostMapping("/searchInSentOrDraft")
    public ResponseEntity<List<MailSchema>> searchInSentOrDraftWithCol(@RequestBody String json) {
        JSONObject jsonObject = new JSONObject(json);
        List<MailSchema> mails = mailService.searchInFolderWithCol(
                jsonObject.getString("folder"),
                jsonObject.getString("user_email"),
                jsonObject.getInt("page_index"),
                jsonObject.getString("column"),
                jsonObject.getString("key"));
        return ResponseEntity.ok(mails);
    }

    @PostMapping("/searchInTrash")
    public ResponseEntity<List<MailSchema>> searchInTrashWithCol(@RequestBody String json) {
        JSONObject jsonObject = new JSONObject(json);
        List<MailSchema> mails = mailService.searchInFolderWithCol(DefaultMailFolder.Trash.name(),
                jsonObject.getString("user_email"),
                jsonObject.getInt("page_index"),
                jsonObject.getString("column"),
                jsonObject.getString("key"));
        return ResponseEntity.ok(mails);
    }


    @SuppressWarnings("unchecked")
    @PutMapping("/moveMailTo")
    public ResponseEntity<String> moveMailTo(@RequestBody Map<String,Object> map) {
        mailService.moveMailsTo((List<String>) map.get("mail_ids"), (String) map.get("newfolder_name"));
        JSONObject response = new JSONObject().put("response","Mail Moved Successfully");
        return ResponseEntity.ok(response.toString());
    }

    @SuppressWarnings("unchecked")
    @PutMapping("/deleteMails")
    public ResponseEntity<String> deleteMails(@RequestBody Map<String,Object> map) {
        mailService.moveMailsToTrash((List<String>)map.get("mail_ids"));

        JSONObject response = new JSONObject().put("response","Mail Deleted Successfully");
        return ResponseEntity.ok(response.toString());
    }

    @SuppressWarnings("unchecked")
    @PutMapping("/restoreMails")
    public ResponseEntity<String> restoreMails(@RequestBody Map<String,Object> map){
        mailService.restoreMails((List<String>)map.get("mail_ids"));
        JSONObject response = new JSONObject().put("response","Mail Restored Successfully");
        return ResponseEntity.ok(response.toString());
    }

    @SuppressWarnings("unchecked")
    @PutMapping("/eraseMails")
    public ResponseEntity<String> eraseMails(@RequestBody Map<String,Object> map) {
        mailService.eraseMails((List<String>) map.get("mail_ids"));

        JSONObject response = new JSONObject().put("response","Mail Erased Successfully");
        return ResponseEntity.ok(response.toString());
    }

}
