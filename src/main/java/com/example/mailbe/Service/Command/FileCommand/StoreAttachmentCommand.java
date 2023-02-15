package com.example.mailbe.Service.Command.FileCommand;

import com.example.mailbe.Model.Attachment;
import com.example.mailbe.Service.Command.AbstractCommand;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

public class StoreAttachmentCommand extends AbstractCommand<List<Attachment>> {

    private final MultipartFile[] files;

    public StoreAttachmentCommand(MultipartFile[] files) {
        this.files = files;
    }

    @Override
    public List<Attachment> execute() {
        try {
            List<Attachment> attachments = new ArrayList<>();
            for (var file : files){
                String fileName = StringUtils.cleanPath(file.getOriginalFilename());
                Attachment attachment = new Attachment(fileName, file.getContentType(), file.getBytes());
                attachments.add(attachment);
            }
            return attachments;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
