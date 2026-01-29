package com.doctor.dr.status.repository;

import com.doctor.dr.status.entity.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StatusRepository extends JpaRepository<Status, Long> {
    List<Status> findAllByIsActiveTrue();

}
