package com.example.mailbe.Service.Factory;

import com.example.mailbe.Response.MailSchema;
import com.example.mailbe.Service.Builder.AbstractMailBuilder;
import com.example.mailbe.Service.Builder.ComposeBuilder;
import com.example.mailbe.Service.Builder.DraftBuilder;

public class MailBuilderFactory {

    protected final MailSchema mailResponseSchema;

    public MailBuilderFactory(MailSchema mailResponseSchema) {
        this.mailResponseSchema = mailResponseSchema;
    }

    public AbstractMailBuilder getBuilder(String builder){
        return switch (builder){
            case "Compose" -> new ComposeBuilder(mailResponseSchema);
            case "Draft" -> new DraftBuilder(mailResponseSchema);
            default -> null;
        };
    }
}
