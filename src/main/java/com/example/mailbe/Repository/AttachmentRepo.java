package com.example.mailbe.Repository;

import com.example.mailbe.Model.Attachment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AttachmentRepo extends JpaRepository<Attachment,String> {

    Attachment findAttachmentByAttachmentId(String id);
}
