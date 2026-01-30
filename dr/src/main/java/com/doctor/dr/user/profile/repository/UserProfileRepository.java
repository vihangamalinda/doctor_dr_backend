package com.doctor.dr.user.profile.repository;

import com.doctor.dr.user.profile.entity.UserProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserProfileRepository extends JpaRepository<UserProfile,Long> {
    List<UserProfile> findAllByIsActiveTrue();
}
