package com.doctor.dr.contact.service.information;

import com.doctor.dr.contact.entity.Contact;
import com.doctor.dr.contact.repository.ContactRepository;
import org.springframework.stereotype.Service;

@Service
public class ContactInformationImpl implements ContactInformation{
    private final ContactRepository contactRepository;

    public ContactInformationImpl(ContactRepository contactRepository) {
        this.contactRepository = contactRepository;
    }

    @Override
    public Contact getContactById(Long id) {
        return this.contactRepository.findById(id).orElseThrow(() -> new RuntimeException("Contact Id was not found."));
    }
}
