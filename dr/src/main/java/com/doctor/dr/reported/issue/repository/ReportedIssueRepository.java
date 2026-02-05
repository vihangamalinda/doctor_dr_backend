package com.doctor.dr.reported.issue.repository;

import com.doctor.dr.reported.issue.entity.ReportedIssue;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReportedIssueRepository extends JpaRepository<ReportedIssue, Long> {
    List<ReportedIssue> findAllByIsActiveTrue();
}
