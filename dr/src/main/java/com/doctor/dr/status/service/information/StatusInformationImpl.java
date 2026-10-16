package com.doctor.dr.status.service.information;

import com.doctor.dr.exception.ResourceNotFoundException;
import com.doctor.dr.status.entity.Status;
import com.doctor.dr.status.repository.StatusRepository;
import org.springframework.stereotype.Service;

@Service
public class StatusInformationImpl implements StatusInformation{

    private final StatusRepository statusRepository;

    public StatusInformationImpl(StatusRepository statusRepository) {
        this.statusRepository = statusRepository;
    }

    @Override
    public Status getStatusById(long id) {
        return this.statusRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Id not found."));
    }
}
