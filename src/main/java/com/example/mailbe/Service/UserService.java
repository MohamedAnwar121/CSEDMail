package com.example.mailbe.Service;

import com.example.mailbe.Model.Contact;
import com.example.mailbe.Model.User;
import com.example.mailbe.Repository.ContactRepo;
import com.example.mailbe.Repository.UserRepo;
import com.example.mailbe.Security.UserDetailsImpl;
import com.example.mailbe.Service.Command.AbstractCommand;
import com.example.mailbe.Service.Command.UserCommand.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.validation.constraints.Email;
import java.util.HashSet;
import java.util.List;

@Service
@Transactional
public class UserService implements UserDetailsService {

    private final UserRepo userRepo;
    private final ContactRepo contactRepo;
    private final BCryptPasswordEncoder encoder;

    @Autowired
    public UserService(UserRepo userRepo,ContactRepo contactRepo, BCryptPasswordEncoder encoder) {
        this.userRepo = userRepo;
        this.encoder = encoder;
        this.contactRepo = contactRepo;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepo.findUserByEmail((email != null) ? email : "");
        if (user == null) throw new UsernameNotFoundException("can not find " + email);
        return new UserDetailsImpl(user);
    }

    public User getUserByEmail(String userEmail) {
        User user = userRepo.findUserByEmail(userEmail);
        user.getCustomFolders().remove("");
        return user;
    }

    public String getUserNameByEmail(String userEmail){
        return userRepo.findUserByEmail(userEmail).getName();
    }

    public User getUserById(String id){
        return userRepo.findUserByUserId(id);
    }

    public void saveUser(User user) {
        user.setPassword(encoder.encode(user.getPassword()));
        userRepo.saveAndFlush(user);
    }


    public void addContact(Contact contact){
        AbstractCommand<Void> addContactCommand =
                new AddContactCommand(contact,contactRepo);
        addContactCommand.execute();
    }

    public void deleteContacts(String userId, List<String> contactIds) {
        AbstractCommand<Void> deleteContactsCommand =
                new DeleteContactsCommand(userId,contactIds,contactRepo);
        deleteContactsCommand.execute();
    }

    public List<Contact> getAllContacts(String userId,String column){
        AbstractCommand<List<Contact>> getAllContactsCommand =
                new GetAllContactsCommand(userId,column,contactRepo,userRepo);
        return getAllContactsCommand.execute();
    }

    public void addEmailToContact(@Email String email, String contactId){
        AbstractCommand<Void> addEmailToContactCommand =
                new AddEmailToContactCommand(contactRepo,email,contactId);
        addEmailToContactCommand.execute();
    }


    public void deleteEmailsFromContact(List<String> emails, String contactId) {
        AbstractCommand<Void> deleteEmailsFromContact =
                new DeleteEmailsFromContactCommand(contactRepo,emails,contactId);
        deleteEmailsFromContact.execute();
    }

    public void updateNameInContact(String contactId, String newName) {
        AbstractCommand<Void> updateNameInContact =
                new UpdateNameInContactCommand(contactId,newName,contactRepo);
        updateNameInContact.execute();
    }


    public void updateEmailInContact(String contactId, String oldEmail, String newEmail) {
        AbstractCommand<Void> updateEmailInContact =
                new UpdateEmailInContactCommand(contactId,oldEmail,newEmail,contactRepo);
        updateEmailInContact.execute();
    }

    public List<Contact> searchInContactsBy(String userId, String col, String key) {
        AbstractCommand<List<Contact>> searchInContactsCommand =
                new SearchInContactsCommand(userId,col,key,contactRepo);
        return searchInContactsCommand.execute();
    }

    public void addFolder(String userEmail, String folderName) {
        AbstractCommand<Void> addFolderCommand = new AddFolderCommand(userEmail,folderName,userRepo);
        addFolderCommand.execute();
    }

    public void renameFolder(String userEmail, String oldFolderName, String newFolderName) {
        AbstractCommand<Void> renameFolderCommand =
                new RenameFolderCommand(userEmail,oldFolderName,newFolderName,userRepo);
        renameFolderCommand.execute();
    }

    public void deleteFolders(String userEmail, List<String> folderNames) {
        AbstractCommand<Void> deleteFoldersCommand =
                new DeleteFoldersCommand(userEmail,folderNames,userRepo);
        deleteFoldersCommand.execute();
    }

    public HashSet<String> getAllFolders(String userEmail) {
        AbstractCommand<HashSet<String>> getAllFoldersCommand = new GetAllFoldersCommand(userEmail,userRepo);
        return getAllFoldersCommand.execute();
    }
}