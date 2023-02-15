package com.example.mailbe.Repository;

import com.example.mailbe.Model.Mail;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.time.Instant;
import java.util.List;

@Repository
public interface MailRepo extends JpaRepository<Mail, String> {

    Mail findMailByMailId(String mailId);
    void deleteMailByMailId(String mailId);
    void removeMailByMailId(String mailId);


    @Query(value = "select m from Mail m where m.mailId = ?1")
    Mail findByMailId(String mailId);

    List<Mail> getAllByMailId(String mailId);


    @Query(value = "select m from Mail m where m.deletedAt <= ?1")
    List<Mail> findAllOldMails(Instant deleted_at);


    List<Mail> findAllByRecieverAndFolder(String reciever, String folder, Pageable pageable);
    List<Mail> findAllBySenderAndFolder(String sender, String folder, Pageable pageable);
    List<Mail> findAllByFolderAndSenderOrFolderAndReciever(String folder, String sender
            ,String folder2,String reciever, Pageable pageable);

    long countAllByRecieverAndFolder(String reciever, String folder);
    long countAllBySenderAndFolder(String sender, String folder);
    long countAllByFolderAndSenderOrFolderAndReciever(String folder,String sender,String _folder,String reciever);

    //--------------------------------------------------------------------------------------------------------------------

    List<Mail> findAllByRecieverAndFolderOrderBySenderDesc(String reciever, String folder, Pageable pageable);
    List<Mail> findAllByRecieverAndFolderOrderByMailDetails_RecieversAsStringAsc(String reciever, String folder, Pageable pageable);
    List<Mail> findAllByRecieverAndFolderOrderByMailDetails_SubjectAsc(String reciever, String folder, Pageable pageable);
    List<Mail> findAllByRecieverAndFolderOrderByMailDetails_MessageAsc(String reciever, String folder, Pageable pageable);
    List<Mail> findAllByRecieverAndFolderOrderByMailDetails_PriorityDesc(String reciever, String folder, Pageable pageable);
    List<Mail> findAllByRecieverAndFolderOrderByMailDetails_SendedAtInSecondsDesc(String reciever, String folder, Pageable pageable);



    List<Mail> findAllBySenderAndFolderOrderBySenderDesc(String reciever, String folder, Pageable pageable);
    List<Mail> findAllBySenderAndFolderOrderByMailDetails_RecieversAsStringAsc(String reciever, String folder, Pageable pageable);
    List<Mail> findAllBySenderAndFolderOrderByMailDetails_SubjectAsc(String reciever, String folder, Pageable pageable);
    List<Mail> findAllBySenderAndFolderOrderByMailDetails_MessageAsc(String reciever, String folder, Pageable pageable);
    List<Mail> findAllBySenderAndFolderOrderByMailDetails_PriorityDesc(String reciever, String folder, Pageable pageable);
    List<Mail> findAllBySenderAndFolderOrderByMailDetails_SendedAtInSecondsDesc(String reciever, String folder, Pageable pageable);


    List<Mail> findAllByFolderAndSenderOrFolderAndRecieverOrderBySenderDesc
            (String folder,String sender,String folder2,String reciever, Pageable pageable);

    List<Mail> findAllByFolderAndSenderOrFolderAndRecieverOrderByMailDetails_RecieversAsStringAsc
            (String folder,String sender,String folder2,String reciever, Pageable pageable);

    List<Mail> findAllByFolderAndSenderOrFolderAndRecieverOrderByMailDetails_SubjectAsc
            (String folder,String sender,String folder2,String reciever, Pageable pageable);

    List<Mail> findAllByFolderAndSenderOrFolderAndRecieverOrderByMailDetails_MessageAsc
            (String folder,String sender,String folder2,String reciever, Pageable pageable);

    List<Mail> findAllByFolderAndSenderOrFolderAndRecieverOrderByMailDetails_PriorityDesc
            (String folder,String sender,String folder2,String reciever, Pageable pageable);

    List<Mail> findAllByFolderAndSenderOrFolderAndRecieverOrderByMailDetails_SendedAtInSecondsDesc
            (String folder,String sender,String folder2,String reciever, Pageable pageable);
    //--------------------------------------------------------------------------------------------------------------------

    List<Mail> findAllByRecieverAndFolderAndMailDetails_RecieversAsStringContaining(String userEmail, String folder, String key, Pageable pageable);
    List<Mail> findAllByRecieverAndFolderAndSenderContaining(String userEmail, String folder, String key, Pageable pageable);
    List<Mail> findAllByRecieverAndFolderAndMailDetails_MessageContaining(String userEmail, String folder, String key, Pageable pageable);
    List<Mail> findAllByRecieverAndFolderAndMailDetails_SubjectContaining(String userEmail, String folder, String key, Pageable pageable);
    List<Mail> findAllByRecieverAndFolderAndMailDetails_PriorityContaining(String userEmail, String folder, String key, Pageable pageable);
    List<Mail> findAllByRecieverAndFolderAndMailDetails_SendedAtContaining(String userEmail, String folder, String key, Pageable pageable);

    List<Mail> findAllBySenderAndFolderAndMailDetails_RecieversAsStringContaining(String userEmail, String folder, String key, Pageable pageable);
    List<Mail> findAllBySenderAndFolderAndSenderContaining(String userEmail, String folder, String key, Pageable pageable);
    List<Mail> findAllBySenderAndFolderAndMailDetails_MessageContaining(String userEmail, String folder, String key, Pageable pageable);
    List<Mail> findAllBySenderAndFolderAndMailDetails_SubjectContaining(String userEmail, String folder, String key, Pageable pageable);
    List<Mail> findAllBySenderAndFolderAndMailDetails_PriorityContaining(String userEmail, String folder,String key, Pageable pageable);
    List<Mail> findAllBySenderAndFolderAndMailDetails_SendedAtContaining(String userEmail, String folder, String key, Pageable pageable);


    List<Mail> findAllByFolderAndSenderOrFolderAndRecieverAndMailDetails_RecieversAsStringContaining
            (String folder,String sender,String folder2,String reciever, String key, Pageable pageable);

    List<Mail> findAllByFolderAndSenderOrFolderAndRecieverAndSenderContaining
            (String folder,String sender,String folder2,String reciever, String key, Pageable pageable);

    List<Mail> findAllByFolderAndSenderOrFolderAndRecieverAndMailDetails_MessageContaining
            (String folder,String sender,String folder2,String reciever, String key, Pageable pageable);

    List<Mail> findAllByFolderAndSenderOrFolderAndRecieverAndMailDetails_SubjectContaining
            (String folder,String sender,String folder2,String reciever, String key, Pageable pageable);

    List<Mail>  findAllByFolderAndSenderOrFolderAndRecieverAndMailDetails_PriorityContaining
            (String folder,String sender,String folder2,String reciever, String key, Pageable pageable);

    List<Mail> findAllByFolderAndSenderOrFolderAndRecieverAndMailDetails_SendedAtContaining
            (String folder,String sender,String folder2,String reciever, String key, Pageable pageable);


}
