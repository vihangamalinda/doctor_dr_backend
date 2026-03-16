package com.doctor.dr.requested.help.mapper;

import com.doctor.dr.contact.entity.Contact;
import com.doctor.dr.requested.help.dto.response.ContactDetailResponseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Named;

@Mapper(componentModel = "spring", implementationName = "RequestedHelpContactDetailMapperImpl")
public interface ContactDetailMapper {
    @Named("toContactDetailResponseDTO")
    ContactDetailResponseDTO toContactDetailResponseDTO(Contact contact);
}
