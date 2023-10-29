package com.example.comminicator.repository;

import com.example.comminicator.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    public Optional<User> findByEmail(String email);

    @Query("SELECT u FROM User u WHERE u.fullName LIKE %:name%")
    List<User> searchUsers(@Param("name") String name);
}
