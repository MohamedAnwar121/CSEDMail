package com.example.mailbe.Repository;

import com.example.mailbe.Model.Mail;
import com.example.mailbe.Model.MailDetails;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MailDetailsRepo extends JpaRepository<MailDetails, String> {

    MailDetails findMailDetailsByMailDetailsId(String mailDetailsId);
}
