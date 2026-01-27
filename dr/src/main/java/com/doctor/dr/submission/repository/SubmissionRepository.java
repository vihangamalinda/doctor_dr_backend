package com.doctor.dr.submission.repository;

import com.doctor.dr.submission.entity.Submission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubmissionRepository extends JpaRepository<Submission,Long> {
    List<Submission> findAllByIsActiveTrue();
    List<Submission> findByDiseaseStage_id(long id);

}
