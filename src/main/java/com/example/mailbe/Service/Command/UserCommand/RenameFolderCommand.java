package com.example.mailbe.Service.Command.UserCommand;

import com.example.mailbe.Model.User;
import com.example.mailbe.Repository.UserRepo;
import com.example.mailbe.Service.Command.AbstractCommand;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;


@Transactional
public class RenameFolderCommand extends AbstractCommand<Void> {

    private final String userEmail;
    private final String oldFolderName;
    private final String newFolderName;
    private final UserRepo userRepo;

    public RenameFolderCommand(String userEmail, String oldFolderName, String newFolderName, UserRepo userRepo) {
        this.userEmail = userEmail;
        this.oldFolderName = oldFolderName;
        this.newFolderName = newFolderName;
        this.userRepo = userRepo;
    }

    @Override
    public Void execute(){
        User user = userRepo.findUserByEmail(userEmail);
        HashSet<String> customFolders = user.getCustomFolders();
        customFolders.remove(oldFolderName);
        customFolders.add(newFolderName);
        return null;
    }
}
