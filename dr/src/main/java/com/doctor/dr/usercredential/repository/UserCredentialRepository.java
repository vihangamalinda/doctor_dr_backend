package com.doctor.dr.usercredential.repository;

import com.doctor.dr.usercredential.entity.UserCredential;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserCredentialRepository extends JpaRepository<UserCredential,Long> {
    @Query("""
            SELECT u
            FROM UserCredential u
            LEFT JOIN FETCH u.roleAssignments ra
            LEFT JOIN FETCH ra.userRole
            WHERE u.userName = :username
            """)
    Optional<UserCredential> findByUserName(String username);
}
