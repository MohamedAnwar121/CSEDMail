package com.example.mailbe.Service.Filter.FolderCriterias;

import com.example.mailbe.Response.MailSchema;

import java.util.List;

public interface FolderCriteria {
    List<MailSchema> meetCriteria(String userEmail,String folder, int index,String sortCol);
}
