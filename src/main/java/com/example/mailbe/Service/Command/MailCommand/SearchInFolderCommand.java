package com.example.mailbe.Service.Command.MailCommand;

import com.example.mailbe.Response.MailSchema;
import com.example.mailbe.Service.Command.AbstractCommand;
import com.example.mailbe.Service.Factory.CriteriaFactoryProducer;
import com.example.mailbe.Service.Factory.SearchInFolderCriteriaFactory;
import com.example.mailbe.Service.Filter.SearchInFolderCriterias.SearchInFolderCriteria;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Transactional
public class SearchInFolderCommand extends AbstractCommand<List<MailSchema>> {

    private final String userEmail;
    private final int index;
    private final String col;
    private final String key;
    private final String folder;
    private final CriteriaFactoryProducer criteriaFactoryProducer;

    public SearchInFolderCommand(String userEmail, int index, String col, String key
            , String folder, CriteriaFactoryProducer criteriaFactoryProducer) {
        this.userEmail = userEmail;
        this.index = index;
        this.col = col;
        this.key = key;
        this.folder = folder;
        this.criteriaFactoryProducer = criteriaFactoryProducer;
    }

    public List<MailSchema> execute(){

        SearchInFolderCriteriaFactory searchInFolderCriteriaFactory =
                (SearchInFolderCriteriaFactory) criteriaFactoryProducer
                        .getCriteriaFactory("SearchInFolderCriteriaFactory");

        SearchInFolderCriteria searchInFolderCriteria = searchInFolderCriteriaFactory.getCriteria(folder);
        return searchInFolderCriteria.meetCriteria(userEmail,folder,index,col,key);
    }
}
