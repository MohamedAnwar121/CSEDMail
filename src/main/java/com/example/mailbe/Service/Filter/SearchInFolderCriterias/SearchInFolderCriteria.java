package com.example.mailbe.Service.Filter.SearchInFolderCriterias;

import com.example.mailbe.Response.MailSchema;

import java.util.List;

public interface SearchInFolderCriteria {
    List<MailSchema> meetCriteria(String userEmail, String folder, int index, String col, String key);
}
