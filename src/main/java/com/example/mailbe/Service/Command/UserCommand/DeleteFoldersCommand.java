package com.example.mailbe.Service.Command.UserCommand;

import com.example.mailbe.Model.User;
import com.example.mailbe.Repository.UserRepo;
import com.example.mailbe.Service.Command.AbstractCommand;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;


@Transactional
public class DeleteFoldersCommand extends AbstractCommand<Void> {

    private final String userEmail;
    private final List<String> folderNames;
    private final UserRepo userRepo;

    public DeleteFoldersCommand(String userEmail, List<String> folderNames, UserRepo userRepo) {
        this.userEmail = userEmail;
        this.folderNames = folderNames;
        this.userRepo = userRepo;
    }

    @Override
    public Void execute() {
        for (var folderName : folderNames){
            User user = userRepo.findUserByEmail(userEmail);
            HashSet<String> customFolders = user.getCustomFolders();
            customFolders.remove(folderName);
        }
        return null;
    }
}
