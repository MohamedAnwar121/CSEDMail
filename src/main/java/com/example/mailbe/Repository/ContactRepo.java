package com.example.mailbe.Repository;

import com.example.mailbe.Model.Contact;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


import java.util.List;

@Repository
public interface ContactRepo extends JpaRepository<Contact, String> , PagingAndSortingRepository<Contact,String> {

    void deleteContactByContactId(String id);
    Contact findContactByContactId(String id);
    void deleteAllByUser_UserId(String userId);


    List<Contact> findAllByUser_UserId(String userId);

    List<Contact> findAllByUser_UserIdOrderByName(String userId);
    List<Contact> findAllByUser_UserIdOrderByEmails(String userId);
    List<Contact> findAllByUser_UserIdAndNameContainingIgnoreCase(String userId,String key);


    //List<Contact> findAllByUser_UserIdAndEmailsContaining(String userEmail,String key);


    //List<Contact> findAllByUser_EmailAndNameContainingIgnoreCase(String userEmail,String key);

    /*    List<Contact> findAllByUser_EmailOrderByName(String userEmail);
    List<Contact> findAllByUser_EmailOrderByEmails(String userEmail);*/


    /*@Query(value = "SELECT c FROM Contact c WHERE c.user.email = ?1 AND c.emails LIKE %?2%")
    List<Contact> searchInContactsBy(@Param("user_email") String userEmail,@Param("key") String key);*/

    //List<Contact> findAllByUser_EmailAndEmailsContaining(String userEmail,String key);
    //List<Contact> findAllByUser_UserIdAndEmailsContaining(String userEmail,String key);
}
