package com.doctor.dr.contact.service;

import com.doctor.dr.contact.dto.request.ContactRequestDTO;
import com.doctor.dr.contact.dto.response.ContactResponseDTO;
import com.doctor.dr.contact.entity.Contact;
import com.doctor.dr.contact.mapper.ContactMapper;
import com.doctor.dr.contact.repository.ContactRepository;
import com.doctor.dr.exception.ResourceNotFoundException;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ContactServiceImplTest {

    @Mock
    private ContactRepository contactRepository;
    @Mock
    private ContactMapper contactMapper;

    @InjectMocks
    private ContactServiceImpl contactService;


    @Test
    void getAll_shouldReturnAllContacts_whenContactsExist() {
        Contact firstContact = this.createContact(2L,
                "Vihanga",
                "Malinda",
                "071455673",
                true);
        Contact secondContact = this.createContact(5L,
                "Test",
                "Malinda",
                "0714745673",
                true);
        List<Contact> contactList = List.of(firstContact,
                secondContact);

        when(contactRepository.findAllByIsActiveTrue()).thenReturn(contactList);
        when(contactMapper.toContactResponseDTO(firstContact)).thenReturn(this.contactToContactResponseDTO(firstContact)); // Mock the mapping to ContactResponseDTO
        when(contactMapper.toContactResponseDTO(secondContact)).thenReturn(this.contactToContactResponseDTO(secondContact)); // Mock the mapping to ContactResponseDTO


        List<ContactResponseDTO> result = this.contactService.getAll();

        assertEquals(2, result.size());

        assertAll("First contact",
                () -> assertEquals(firstContact.getId(), result.get(0).getId()),
                () -> assertEquals(firstContact.getFirstName(), result.get(0).getFirstName()),
                () -> assertEquals(firstContact.getLastName(), result.get(0).getLastName()),
                () -> assertEquals(firstContact.getContactNumber(), result.get(0).getContactNumber()),
                () -> assertEquals(firstContact.getIsActive(), result.get(0).isActive())
        );

        assertAll("Second contact",
                () -> assertEquals(secondContact.getId(), result.get(1).getId()),
                () -> assertEquals(secondContact.getFirstName(), result.get(1).getFirstName()),
                () -> assertEquals(secondContact.getLastName(), result.get(1).getLastName()),
                () -> assertEquals(secondContact.getContactNumber(), result.get(1).getContactNumber()),
                () -> assertEquals(secondContact.getIsActive(), result.get(1).isActive())
        );
        verify(contactMapper, times(1)).toContactResponseDTO(firstContact);
        verify(contactMapper, times(1)).toContactResponseDTO(secondContact);

        verify(contactRepository).findAllByIsActiveTrue();
    }

    @Test
    void getAll_shouldReturnEmptyList_whenNoContactsExist() {
        when(contactRepository.findAllByIsActiveTrue()).thenReturn(Collections.emptyList());

        List<ContactResponseDTO> result = contactService.getAll();
        verify(contactMapper, times(0)).toContactResponseDTO(null);

        verify(contactRepository).findAllByIsActiveTrue();
        assertEquals(0, result.size());
    }


    @Test
    void create_shouldCreateContact_whenValidContactRequestDTOIsGiven() {
        ContactRequestDTO contactRequestDTO = this.createContactRequestDTO("Vihanga", "Malinda", "0743991512");

        Contact contact = contactRequestDTOToContact(contactRequestDTO);
        when(contactMapper.toContact(contactRequestDTO)).thenReturn(contact);
        when(contactRepository.save(contact)).thenReturn(contact);

        this.contactService.create(contactRequestDTO);
        verify(contactMapper, times(1)).toContact(contactRequestDTO);
        verify(contactRepository, times(1)).save(contact);
    }

    @Test
    void create_shouldThrowException_whenEmptyFirstNameIsGiven() {
        ContactRequestDTO contactRequestDTO = this.createContactRequestDTO("", "Malinda", "0743991512");

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> this.contactService.create(contactRequestDTO)
        );
        assertEquals(
                "First name , last name and contact number can not be empty",
                exception.getMessage()
        );

    }

    @Test
    void create_shouldThrowException_whenEmptyLastNameIsGiven() {
        ContactRequestDTO contactRequestDTO = this.createContactRequestDTO("Vihanga", "", "0743991512");

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> this.contactService.create(contactRequestDTO)
        );
        assertEquals(
                "First name , last name and contact number can not be empty",
                exception.getMessage()
        );
    }

    @Test
    void create_shouldThrowException_whenEmptyContactNumberIsGiven() {
        ContactRequestDTO contactRequestDTO = this.createContactRequestDTO("Vihanga", "Malinda", "");

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> this.contactService.create(contactRequestDTO)
        );
        assertEquals(
                "First name , last name and contact number can not be empty",
                exception.getMessage()
        );
    }

    @Test
    void create_shouldThrowException_whenBlankFirstNameIsGiven() {
        ContactRequestDTO contactRequestDTO = this.createContactRequestDTO("   ", "Malinda", "0743991512");

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> this.contactService.create(contactRequestDTO)
        );
        assertEquals(
                "First name , last name and contact number can not be empty",
                exception.getMessage()
        );

    }

    @Test
    void create_shouldThrowException_whenBlankLastNameIsGiven() {
        ContactRequestDTO contactRequestDTO = this.createContactRequestDTO("Vihanga", "   ", "0743991512");

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> this.contactService.create(contactRequestDTO)
        );
        assertEquals(
                "First name , last name and contact number can not be empty",
                exception.getMessage()
        );
    }

    @Test
    void create_shouldThrowException_whenBlankContactNumberIsGiven() {
        ContactRequestDTO contactRequestDTO = this.createContactRequestDTO("Vihanga", "Malinda", "     ");

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> this.contactService.create(contactRequestDTO)
        );
        assertEquals(
                "First name , last name and contact number can not be empty",
                exception.getMessage()
        );
    }


    @Test
    void getById_shouldReturnContact_whenValidIdIsGiven() {
        Contact contact = this.createContact(5L, "Test guest", "Guest 2", "775472", true);

        ContactResponseDTO contactResponseDTO = this.contactToContactResponseDTO(contact);

        when(this.contactRepository.findById(5L)).thenReturn(Optional.of(contact));
        when(this.contactMapper.toContactResponseDTO(contact)).thenReturn(contactResponseDTO);

        ContactResponseDTO result = this.contactService.getById(5L);

        assertEquals(5L, result.getId());
        verify(this.contactRepository, times(1)).findById(5L);
        verify(this.contactMapper, times(1)).toContactResponseDTO(contact);
    }

    @Test
    void getById_shouldThrowException_whenInvalidValidIdIsGiven() {
        when(this.contactRepository.findById(6L)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(ResourceNotFoundException.class, () -> this.contactService.getById(6L));
        assertEquals(
                "Contact Id was not found.",
                exception.getMessage()
        );

    }

    @Test
    void deleteById_shouldThrowException_whenInvalidIdIsGiven() {
        when(this.contactRepository.findById(6L)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(ResourceNotFoundException.class, () -> this.contactService.getById(6L));
        assertEquals(
                "Contact Id was not found.",
                exception.getMessage()
        );
    }

    @Test
    void deleteById_shouldDeactivateContact_whenActiveValidIdIsGiven() {

        Contact contact = this.createContact(1L, "Vihanga", "Malinda", "075445782", true);
        when(this.contactRepository.findById(1L)).thenReturn(Optional.of(contact));

        this.contactService.deleteById(1L);

        verify(this.contactRepository, times(1)).save(contact);
    }

    @Test
    void deleteById_shouldNotDeactivateContact_whenDeactivatedValidContactIdIsGiven() {
        Contact contact = this.createContact(1L, "Vihanga", "Malinda", "075445782", false);
        when(this.contactRepository.findById(1L)).thenReturn(Optional.of(contact));

        this.contactService.deleteById(1L);

        verify(this.contactRepository, times(0)).save(contact);
    }


    private Contact createContact(Long id, String firstName, String lastName, String phone, Boolean isActive) {
        Contact contact = new Contact();
        contact.setId(id);
        contact.setFirstName(firstName);
        contact.setLastName(lastName);

        contact.setContactNumber(phone);
        contact.setIsActive(isActive);
        return contact;
    }

    private ContactResponseDTO contactToContactResponseDTO(Contact contact) {
        return new ContactResponseDTO(contact.getId(),
                contact.getFirstName(),
                contact.getLastName(),
                contact.getContactNumber(),
                contact.getIsActive());
    }


    private ContactRequestDTO createContactRequestDTO(String firstName, String lastName, String contactNumber) {
        return new ContactRequestDTO(firstName, lastName, contactNumber);
    }

    private Contact contactRequestDTOToContact(ContactRequestDTO dto) {
        // is Active should be applied on service layer when creating
        return new Contact(0L, dto.getFirstName(), dto.getLastName(), dto.getContactNumber(), false);
    }
}