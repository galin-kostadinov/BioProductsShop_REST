package org.gkk.bioshopapp.data.repository;

import org.gkk.bioshopapp.data.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
    Optional<User> findByUsernameAndPassword(String username, String passwordHash);

    boolean existsByUsername(String username);

    Optional<User> findByUsername(String username);

    @Modifying
    @Transactional
    void deleteUserByUsername(String username);
}
