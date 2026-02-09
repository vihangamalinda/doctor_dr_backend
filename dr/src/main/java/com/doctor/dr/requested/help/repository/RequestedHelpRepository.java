package com.doctor.dr.requested.help.repository;

import com.doctor.dr.requested.help.entity.RequestedHelp;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RequestedHelpRepository extends JpaRepository<RequestedHelp, Long> {
    List<RequestedHelp> findAllByIsActiveTrue();

    List<RequestedHelp> findByCreatedByUserProfile_id(long id);

    List<RequestedHelp> findByReviewedByUserProfile_id(long id);
}
