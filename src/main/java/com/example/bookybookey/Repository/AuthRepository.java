package com.example.bookybookey.Repository;

import com.example.bookybookey.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthRepository extends JpaRepository<User, Integer> {
    User findUserByUsername(String username);
    User findUserByUserId(Integer userId);
}
