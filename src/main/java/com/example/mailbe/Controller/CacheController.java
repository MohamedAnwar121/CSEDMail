package com.example.mailbe.Controller;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import com.example.mailbe.Response.ResponseFile;
import com.example.mailbe.Service.CacheManagmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

@CrossOrigin()
@RequestMapping("/api/cache")
@RestController
public class CacheController {

    private final CacheManagmentService cacheManagmentService;

    @Autowired
    public CacheController(CacheManagmentService cacheManagmentService) {
        this.cacheManagmentService = cacheManagmentService;
    }

    @PostMapping("/generateUrls")
    public ResponseEntity<List<ResponseFile>> uploadFiles(@RequestParam("file") MultipartFile[] files) {
        String message = "";
        try {
            List<String> fileNames = new ArrayList<>();

            Arrays.asList(files).stream().forEach(file -> {
                cacheManagmentService.save(file);
                fileNames.add(file.getOriginalFilename());
            });

            message = "Uploaded the files successfully: " + fileNames;
            System.out.println(message);
        } catch (Exception e) {
            message = "Fail to upload files!";
            System.out.println(message);
        }
        List<ResponseFile> fileInfos = cacheManagmentService.loadAll().map(path -> {
            String filename = path.getFileName().toString();
            String url = MvcUriComponentsBuilder
                    .fromMethodName(CacheController.class,
                            "getFile", path.getFileName().toString()).build().toString();

            return new ResponseFile(filename, url,"",0);
        }).collect(Collectors.toList());

        return ResponseEntity.status(HttpStatus.OK).body(fileInfos);
    }


    @GetMapping("/files/{filename:.+}")
    public ResponseEntity<Resource> getFile(@PathVariable String filename) {
        Resource file = cacheManagmentService.load(filename);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"").body(file);
    }


    @GetMapping("/reset")
    public ResponseEntity<String> reset(){
        cacheManagmentService.deleteAll();
        cacheManagmentService.init();
        return ResponseEntity.ok().body(null);
    }

}
