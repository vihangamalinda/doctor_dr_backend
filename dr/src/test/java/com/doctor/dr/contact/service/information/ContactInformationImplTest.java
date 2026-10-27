package com.doctor.dr.contact.service.information;

import com.doctor.dr.contact.entity.Contact;
import com.doctor.dr.contact.repository.ContactRepository;
import com.doctor.dr.exception.ResourceNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class ContactInformationImplTest {
    @Mock
    private ContactRepository contactRepository;
    @InjectMocks
    private ContactInformationImpl contactInformation;

    @Test
    void getContactById_shouldThrowResourceNotFoundException_whenInvalidIdIsGiven(){
        when(this.contactRepository.findById(anyLong())).thenReturn(Optional.empty());

        Exception exception= assertThrows(ResourceNotFoundException.class,()->this.contactInformation.getContactById(55L));

        assertEquals("Contact Id was not found.",exception.getMessage());
        verify(this.contactRepository,times(1)).findById(any(Long.class));
    }

    @Test
    void getContactById_shouldReturnContact_whenValidIdIsGiven(){
        Contact contact = mock(Contact.class);
        when(this.contactRepository.findById(anyLong())).thenReturn(Optional.of(contact));

        Contact result= this.contactInformation.getContactById(55L);
        assertNotNull(contact);
        verify(this.contactRepository,times(1)).findById(any(Long.class));
    }
}