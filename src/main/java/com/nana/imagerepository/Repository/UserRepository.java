package com.nana.imagerepository.Repository;

import com.nana.imagerepository.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {
    Optional<User> findByUsername (String username);
    User findUserByUsername(String username);
}
