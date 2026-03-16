package com.doctor.dr.contact.mapper;

import com.doctor.dr.contact.dto.request.ContactRequestDTO;
import com.doctor.dr.contact.dto.response.ContactResponseDTO;
import com.doctor.dr.contact.entity.Contact;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ContactMapper {

    ContactResponseDTO toContactResponseDTO(Contact contact);
    Contact toContact(ContactRequestDTO contactRequestDTO);
}
