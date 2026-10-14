package com.doctor.dr.contact.controller;

import com.doctor.dr.contact.dto.request.ContactRequestDTO;
import com.doctor.dr.contact.dto.response.ContactResponseDTO;
import com.doctor.dr.contact.service.ContactService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/contact")
public class ContactController {

    private final ContactService contactService;


    public ContactController(ContactService contactService) {
        this.contactService = contactService;
    }

    @Operation(summary = "Get all contacts",
            description = "This endpoint retrieves a list of all contacts in the system.",
            tags = {"Contact Management"})
    @GetMapping("/all")
    public ResponseEntity<List<ContactResponseDTO>> getAll(){
        return ResponseEntity.ok(this.contactService.getAll());
    }

    @Operation(summary = "Create a new contact",
            description = "This endpoint allows the creation of a new contact in the system.",
            tags = {"Contact Management"})
    @PostMapping("/create")
    public ResponseEntity<String> create(@RequestBody ContactRequestDTO contactRequestDTO){
        this.contactService.create(contactRequestDTO);
        return ResponseEntity.ok("created");
    }

    @Operation(summary = "Get contact by ID",
            description = "This endpoint retrieves a contact by its unique ID.",
            tags = {"Contact Management"})
    @GetMapping("/id/{id}")
    public ResponseEntity<ContactResponseDTO> getById(@PathVariable("id") Long id){
        ContactResponseDTO contactResponseDTO =this.contactService.getById(id);
        return ResponseEntity.ok(contactResponseDTO);
    }

    @Operation(summary = "Inactivate contact by ID",
            description = "This endpoint inactivates an existing contact identified by its unique ID.",
            tags = {"Contact Management"})
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteById(@PathVariable("id") Long id){
        this.contactService.deleteById(id);
        return ResponseEntity.ok("deleted");
    }
}
