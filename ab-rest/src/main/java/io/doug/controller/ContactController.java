package io.doug.controller;

import io.doug.entity.Contact;
import io.doug.entity.User;
import io.doug.service.ContactService;
import io.doug.util.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

/**
 * Created by djeremias on 1/3/17.
 */
@RestController
@RequestMapping("/contact")
public class ContactController {

    private final ContactService contactService;

    @Autowired
    public ContactController(ContactService contactService) {
        this.contactService = contactService;
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<ApiResponse<Page<Contact>>> searchContacts(@RequestParam(required = false) String firstName,
                                                                     @RequestParam(required = false) String lastName,
                                                                     @AuthenticationPrincipal User user,
                                                                     Pageable pageable) {
        ApiResponse<Page<Contact>> apiResponse = new ApiResponse<>("Search results",
                contactService.getByFirstNameAndLastNameAndUser(firstName, lastName, user, pageable));

        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @RequestMapping(value = "/user-list", method = RequestMethod.GET)
    public ResponseEntity<ApiResponse<Page<Contact>>> getContactForUser(@AuthenticationPrincipal User user, Pageable pageable) {
        ApiResponse<Page<Contact>> apiResponse = new ApiResponse<>("Contact results for user",
                contactService.getByUser(user, pageable));

        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<ApiResponse<Contact>> getContactById(@PathVariable Long id) {
        ApiResponse<Contact> apiResponse = new ApiResponse<>(contactService.getById(id));

        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<ApiResponse<Contact>> create(@RequestBody Contact contact,
                                                       @AuthenticationPrincipal User user) {

        ApiResponse<Contact> apiResponse = new ApiResponse<>("Contact successfully created.",
                contactService.create(contact, user));

        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.PUT)
    public ResponseEntity<ApiResponse<Contact>> update(@RequestBody Contact contact) {

        ApiResponse<Contact> apiResponse = new ApiResponse<>("Contact successfully updated.",
                contactService.update(contact));

        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<ApiResponse<Boolean>> delete(@PathVariable Long id) {

        ApiResponse<Boolean> apiResponse = new ApiResponse<>("Contact successfully deleted.",
                contactService.delete(id));

        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }
}
