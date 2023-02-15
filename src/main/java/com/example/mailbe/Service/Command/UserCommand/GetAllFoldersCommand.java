package com.example.mailbe.Service.Command.UserCommand;

import com.example.mailbe.Model.User;
import com.example.mailbe.Repository.UserRepo;
import com.example.mailbe.Service.Command.AbstractCommand;

import java.util.HashSet;

public class GetAllFoldersCommand extends AbstractCommand<HashSet<String>> {

    private final String userEmail;
    private final UserRepo userRepo;

    public GetAllFoldersCommand(String userEmail, UserRepo userRepo) {
        this.userEmail = userEmail;
        this.userRepo = userRepo;
    }

    @Override
    public HashSet<String> execute() {
        User user = userRepo.findUserByEmail(userEmail);
        HashSet<String> folders = user.getCustomFolders();
        folders.remove("");
        return folders;
    }
}
