package com.doctor.dr.contact.service;

import com.doctor.dr.contact.dto.request.ContactRequestDTO;
import com.doctor.dr.contact.dto.response.ContactResponseDTO;
import com.doctor.dr.contact.entity.Contact;
import com.doctor.dr.contact.mapper.ContactMapper;
import com.doctor.dr.contact.repository.ContactRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ContactServiceImpl implements ContactService{
    private final ContactRepository contactRepository;
    private final ContactMapper contactMapper;

    public ContactServiceImpl(ContactRepository contactRepository, ContactMapper contactMapper) {
        this.contactRepository = contactRepository;
        this.contactMapper = contactMapper;
    }

    @Override
    public List<ContactResponseDTO> getAll() {
        List<Contact> contactList = this.contactRepository.findAllByIsActiveTrue();
        return contactList.stream().map(this.contactMapper::toContactResponseDTO).collect(Collectors.toList());
    }

    @Override
    public void create(ContactRequestDTO contactRequestDTO) {
        Contact contact = this.contactMapper.toContact(contactRequestDTO);
        contact.setIsActive(true);
        persistEntity(contact);
    }

    private void persistEntity(Contact contact) {
        this.contactRepository.save(contact);
    }

    @Override
    public ContactResponseDTO getById(Long id) {
        Contact contact = findById(id);
        return this.contactMapper.toContactResponseDTO(contact);
    }

    private Contact findById(Long id) {
        return this.contactRepository.findById(id).orElseThrow(() -> new RuntimeException("Contact Id was not found."));
    }

    @Override
    public void deleteById(Long id) {
        Contact contact =findById(id);
        if(contact.getIsActive()){
            contact.setIsActive(false);
            this.persistEntity(contact);
        }
    }
}
