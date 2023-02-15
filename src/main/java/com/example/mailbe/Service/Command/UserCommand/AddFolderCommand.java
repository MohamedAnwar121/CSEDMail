package com.example.mailbe.Service.Command.UserCommand;

import com.example.mailbe.Model.User;
import com.example.mailbe.Repository.UserRepo;
import com.example.mailbe.Service.Command.AbstractCommand;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;


@Transactional
public class AddFolderCommand extends AbstractCommand<Void> {

    private final String userEmail;
    private final String folderName;
    private final UserRepo userRepo;

    public AddFolderCommand(String userEmail, String folderName, UserRepo userRepo) {
        this.userEmail = userEmail;
        this.folderName = folderName;
        this.userRepo = userRepo;
    }

    @Override
    public Void execute(){

        User user = userRepo.findUserByEmail(userEmail);

        if (user.getCustomFolders().contains(folderName)){
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT, "name is repeated"
            );
        }

        user.getCustomFolders().remove("");
        user.getCustomFolders().add(folderName);

        return null;
    }
}
