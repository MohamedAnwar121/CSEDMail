package com.example.mailbe.Service;

import com.example.mailbe.Response.MailSchema;
import com.example.mailbe.Repository.MailDetailsRepo;
import com.example.mailbe.Repository.MailRepo;
import com.example.mailbe.Service.Command.AbstractCommand;
import com.example.mailbe.Service.Command.MailCommand.*;
import com.example.mailbe.Service.Factory.CriteriaFactoryProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class MailService {

    private final MailRepo mailRepo;
    private final MailDetailsRepo mailDetailsRepo;
    private final ValidationService validationService;
    private final CriteriaFactoryProducer factoryProducer;


    @Autowired
    public MailService(MailRepo mailRepo, MailDetailsRepo mailDetailsRepo, ValidationService validationService, CriteriaFactoryProducer factoryProducer) {
        this.mailRepo = mailRepo;
        this.mailDetailsRepo = mailDetailsRepo;
        this.validationService = validationService;
        this.factoryProducer = factoryProducer;
    }

    public String compose(MailSchema mailSchema) {
        AbstractCommand<String> composeCommand =
                new ComposeCommand(validationService,mailSchema,mailDetailsRepo,mailRepo);
        return composeCommand.execute();
    }


    public String saveToDraft(MailSchema mailSchema) {
        AbstractCommand<String> saveToDraftCommand =
                new SaveToDraftCommand(mailSchema,mailDetailsRepo, mailRepo);
        return saveToDraftCommand.execute();
    }


    public List<MailSchema> getFolder(String folder, String userEmail, int index, String sortCol) {
        AbstractCommand<List<MailSchema>> getFolderCommand =
                new GetFolderCommand(userEmail, folder, index, sortCol, factoryProducer);
        return getFolderCommand.execute();
    }

    public long getFolderSize(String userEmail, String folderName) {
        AbstractCommand<Long> getFolderSizeCommand = new GetFolderSizeCommand(userEmail, folderName, mailRepo);
        return getFolderSizeCommand.execute();
    }


    public List<MailSchema> searchInFolderWithCol
            (String folder, String userEmail, int index, String col, String key) {
        AbstractCommand<List<MailSchema>> searchInFolderCommand =
                new SearchInFolderCommand(userEmail,index,col,key,folder, factoryProducer);
        return searchInFolderCommand.execute();
    }

    public void moveMailsTo(List<String> mailIds, String newFolderName) {
        AbstractCommand<Void> moveMailsToCommand = new MoveMailsToCommand(mailIds,newFolderName,mailRepo);
        moveMailsToCommand.execute();
    }

    public void moveMailsToTrash(List<String> mailIds) {
        AbstractCommand<Void> moveMailsToTrash = new MoveMailsToTrashCommand(mailIds,mailRepo);
        moveMailsToTrash.execute();
    }

    public void eraseMails(List<String> mailIds) {
        AbstractCommand<Void> eraseMailsCommand = new EraseMailsCommand(mailIds,mailRepo,mailDetailsRepo);
        eraseMailsCommand.execute();
    }

    public String updateDraft(MailSchema mailSchema) {
        AbstractCommand<String> updateDraftCommand = new UpdateDraftCommand(mailRepo,mailDetailsRepo,mailSchema);
        return updateDraftCommand.execute();
    }

    public void deleteOldMails() {
        AbstractCommand<Void> deleteOldMails = new DeleteOldMailsCommand(mailRepo,mailDetailsRepo);
        deleteOldMails.execute();
    }

    public void restoreMails(List<String> mailIds) {
        AbstractCommand<Void> restoreMailsCommand = new RestoreMailCommand(mailIds,mailRepo);
        restoreMailsCommand.execute();
    }
}
