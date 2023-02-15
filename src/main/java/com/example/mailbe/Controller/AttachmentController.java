package com.example.mailbe.Controller;

import com.example.mailbe.Model.Attachment;
import com.example.mailbe.Response.ResponseFile;
import com.example.mailbe.Service.FileStorageService;
import jdk.security.jarsigner.JarSigner;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@CrossOrigin()
@RequestMapping("/api/file")
@RestController
public class AttachmentController {

    private final FileStorageService fileStorageService;

    @Autowired
    public AttachmentController(FileStorageService fileStorageService) {
        this.fileStorageService = fileStorageService;
    }

    @PostMapping("/upload")
    public ResponseEntity<String> uploadFile(@RequestParam("files") MultipartFile[] files
            , @RequestParam("mail_details_id") String mailDetailsId) {
        try {
            List<Attachment> attachments = fileStorageService.store(files);
            fileStorageService.saveAttachmentsToMailDetails(attachments,mailDetailsId);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body("Could not upload the file");
        }

        JSONObject jsonObject = new JSONObject().put("response","Uploaded the file successfully");
        return ResponseEntity.ok(jsonObject.toString());
    }

    @PutMapping("/getFiles")
    public ResponseEntity<List<ResponseFile>> getFiles(@RequestBody Map<String,Object> map) {
        String mailId = (String) map.get("mail_id");

        List<ResponseFile> files = fileStorageService.getAllFiles(mailId).map(dbFile -> {
            String fileDownloadUri = ServletUriComponentsBuilder
                    .fromCurrentContextPath()
                    .path("api/file/getFiles/")
                    .path(dbFile.getAttachmentId())
                    .toUriString();

            return new ResponseFile(
                    dbFile.getName(),
                    fileDownloadUri,
                    dbFile.getType(),
                    dbFile.getData().length);
        }).collect(Collectors.toList());

        return ResponseEntity.status(HttpStatus.OK).body(files);
    }

    @GetMapping("/getFiles/{id}")
    public ResponseEntity<byte[]> getFile(@PathVariable String id) {
        Attachment attachment = fileStorageService.getAttachmentByAttachmentId(id);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=\"" + attachment.getName() + "\"")
                .body(attachment.getData());
    }
}

