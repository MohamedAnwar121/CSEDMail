package com.example.mailbe.Util;

public enum DefaultMailFolder {

    Inbox("Inbox"),
    Sent("Sent"),
    Draft("Draft"),
    Trash("Trash");

    private final String name;

    DefaultMailFolder(String name) {
        this.name = name;
    }

}
