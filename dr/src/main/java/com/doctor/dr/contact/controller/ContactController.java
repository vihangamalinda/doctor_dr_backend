package com.doctor.dr.contact.controller;

import com.doctor.dr.contact.dto.request.ContactRequestDTO;
import com.doctor.dr.contact.dto.response.ContactResponseDTO;
import com.doctor.dr.contact.service.ContactService;
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

    @GetMapping("/all")
    public ResponseEntity<List<ContactResponseDTO>> getAll(){
        return ResponseEntity.ok(this.contactService.getAll());
    }

    @PostMapping("/create")
    public ResponseEntity<String> create(@RequestBody ContactRequestDTO contactRequestDTO){
        this.contactService.create(contactRequestDTO);
        return ResponseEntity.ok("created");
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<ContactResponseDTO> getById(@PathVariable("id") Long id){
        ContactResponseDTO contactResponseDTO =this.contactService.getById(id);
        return ResponseEntity.ok(contactResponseDTO);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteById(@PathVariable("id") Long id){
        this.contactService.deleteById(id);
        return ResponseEntity.ok("deleted");
    }
}
