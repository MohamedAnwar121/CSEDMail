package com.example.mailbe.Service.Command.FileCommand;

import com.example.mailbe.Model.Attachment;
import com.example.mailbe.Model.MailDetails;
import com.example.mailbe.Repository.MailDetailsRepo;
import com.example.mailbe.Service.Command.AbstractCommand;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Transactional
public class SaveAttachmentsToMailCommand extends AbstractCommand<Void> {

    private final List<Attachment> attachments;
    private final String mailDetailsId;
    private final MailDetailsRepo mailDetailsRepo;

    public SaveAttachmentsToMailCommand(List<Attachment> attachments, String mailDetailsId, MailDetailsRepo mailDetailsRepo) {
        this.attachments = attachments;
        this.mailDetailsId = mailDetailsId;
        this.mailDetailsRepo = mailDetailsRepo;
    }

    @Override
    public Void execute() {
        MailDetails mailDetails = mailDetailsRepo.findMailDetailsByMailDetailsId(mailDetailsId);
        mailDetails.setAttachments(attachments);
        return null;
    }
}
