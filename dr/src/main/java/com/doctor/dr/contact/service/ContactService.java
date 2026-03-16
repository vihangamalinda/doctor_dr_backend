package com.doctor.dr.contact.service;

import com.doctor.dr.contact.dto.request.ContactRequestDTO;
import com.doctor.dr.contact.dto.response.ContactResponseDTO;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ContactService {
    List<ContactResponseDTO> getAll();

    void create(ContactRequestDTO contactRequestDTO);

    ContactResponseDTO getById(Long id);

    void deleteById(Long id);
}
