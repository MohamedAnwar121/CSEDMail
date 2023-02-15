package com.example.mailbe.Service.Command.MailCommand;

import com.example.mailbe.Repository.MailRepo;
import com.example.mailbe.Service.Command.AbstractCommand;
import com.example.mailbe.Service.Filter.FolderCriterias.SentOrDraftCriteria;
import com.example.mailbe.Service.Filter.FolderCriterias.TrashOrCustomFolderCriteria;

public class GetFolderSizeCommand extends AbstractCommand<Long> {

    private final String userEmail;
    private final String folderName;
    private final MailRepo mailRepo;

    public GetFolderSizeCommand(String userEmail, String folderName, MailRepo mailRepo) {
        this.userEmail = userEmail;
        this.folderName = folderName;
        this.mailRepo = mailRepo;
    }


    @Override
    public Long execute() {
        return switch (folderName){
            case "Inbox" -> mailRepo.countAllByRecieverAndFolder(userEmail,folderName);
            case "Draft", "Sent" -> mailRepo.countAllBySenderAndFolder(userEmail,folderName);
            default -> mailRepo.countAllByFolderAndSenderOrFolderAndReciever
                    (folderName,userEmail,folderName,userEmail);
        };
    }
}
