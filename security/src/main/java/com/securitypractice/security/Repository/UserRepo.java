package com.securitypractice.security.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.securitypractice.security.Model.User;
import java.util.Optional;

public interface UserRepo extends JpaRepository<User, Integer> {
    Optional<User> findByUserName(String userName);

    User findByUserNameAndPassword(String userName, String password);

}
