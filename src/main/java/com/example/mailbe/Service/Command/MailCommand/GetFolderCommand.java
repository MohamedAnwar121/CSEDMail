package com.example.mailbe.Service.Command.MailCommand;

import com.example.mailbe.Response.MailSchema;
import com.example.mailbe.Service.Command.AbstractCommand;
import com.example.mailbe.Service.Factory.CriteriaFactoryProducer;
import com.example.mailbe.Service.Factory.FolderCriteriaFactory;
import com.example.mailbe.Service.Filter.FolderCriterias.FolderCriteria;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Transactional
public class GetFolderCommand extends AbstractCommand<List<MailSchema>> {


    private final String userEmail;
    private final String folder;
    private final int index;
    private final String sortCol;
    private final CriteriaFactoryProducer criteriaFactoryProducer;

    public GetFolderCommand(String userEmail, String folder, int index, String sortCol
            , CriteriaFactoryProducer criteriaFactoryProducer) {
        this.userEmail = userEmail;
        this.folder = folder;
        this.index = index;
        this.sortCol = sortCol;
        this.criteriaFactoryProducer = criteriaFactoryProducer;
    }


    @Override
    public List<MailSchema> execute() {
        FolderCriteriaFactory folderCriteriaFactory =
                (FolderCriteriaFactory) criteriaFactoryProducer.getCriteriaFactory("FolderCriteriaFactory");

        FolderCriteria folderCriteria = folderCriteriaFactory.getCriteria(folder);
        return folderCriteria.meetCriteria(userEmail, folder, index, sortCol);
    }

}
