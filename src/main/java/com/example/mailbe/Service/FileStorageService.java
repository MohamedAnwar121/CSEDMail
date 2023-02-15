package com.example.mailbe.Service;

import com.example.mailbe.Model.Attachment;
import com.example.mailbe.Repository.AttachmentRepo;
import com.example.mailbe.Repository.MailDetailsRepo;
import com.example.mailbe.Repository.MailRepo;
import com.example.mailbe.Service.Command.AbstractCommand;
import com.example.mailbe.Service.Command.FileCommand.SaveAttachmentsToMailCommand;
import com.example.mailbe.Service.Command.FileCommand.StoreAttachmentCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

@Service
@Transactional
public class FileStorageService {

    private final AttachmentRepo attachmentRepo;
    private final MailRepo mailRepo;
    private final MailDetailsRepo mailDetailsRepo;

    @Autowired
    public FileStorageService(AttachmentRepo attachmentRepo, MailRepo mailRepo, MailDetailsRepo mailDetailsRepo) {
        this.attachmentRepo = attachmentRepo;
        this.mailRepo = mailRepo;
        this.mailDetailsRepo = mailDetailsRepo;
    }

    public List<Attachment> store(MultipartFile[] files) throws IOException {
        AbstractCommand<List<Attachment>> storeAttachmentCommand =
                new StoreAttachmentCommand(files);
        return storeAttachmentCommand.execute();
    }

    public void saveAttachmentsToMailDetails(List<Attachment> attachments, String mailDetailsId){
        AbstractCommand<Void> saveAttachmentsToMailCommand =
                new SaveAttachmentsToMailCommand(attachments,mailDetailsId,mailDetailsRepo);
        saveAttachmentsToMailCommand.execute();
    }

    public Stream<Attachment> getAllFiles(String mailId) {
        return mailRepo.findMailByMailId(mailId).getMailDetails().getAttachments().stream();
    }

    public Attachment getAttachmentByAttachmentId(String attachmentId){
        return attachmentRepo.findAttachmentByAttachmentId(attachmentId);
    }
}
