package com.example.mailbe.Repository;

import com.example.mailbe.Model.Contact;
import com.example.mailbe.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.validation.constraints.Email;
import java.util.List;

@Repository
public interface UserRepo extends JpaRepository<User, String> {

    User findUserByUserId(String id);
    User findUserByEmail(String email);
    boolean existsUserByEmail(@Email String email);

}
