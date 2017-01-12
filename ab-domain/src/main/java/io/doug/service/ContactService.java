package io.doug.service;

import io.doug.exception.BusinessException;
import io.doug.repository.ContactRepository;
import io.doug.entity.Contact;
import io.doug.entity.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.transaction.Transactional;

/**
 * Created by djeremias on 12/28/16.
 */
@Service
@Transactional
public class ContactService {

    private static final Logger LOG = LoggerFactory.getLogger(ContactService.class);

    private final ContactRepository contactRepository;

    @Autowired
    public ContactService(ContactRepository contactRepository) {
        this.contactRepository = contactRepository;
    }

    public Page<Contact> getByFirstNameAndLastNameAndUser(String firstName, String lastName, User user,Pageable pageable) {
        if (user == null) {
            throw new BusinessException("invalid user data passed into service method");
        }
        // wildcard if nulls
        if (StringUtils.isEmpty(firstName)) {
            firstName = "%";
        }

        if (StringUtils.isEmpty(lastName)) {
            lastName = "%";
        }

        return contactRepository.findByFirstNameAndLastName(firstName, lastName, user, pageable);
    }

    public Page<Contact> getByUser(User user, Pageable pageable) {
        if (user == null) {
            throw new BusinessException("invalid user data passed into service method");
        }

        return contactRepository.findByUser(user, pageable);
    }

    public Contact getById(Long id) {
        if (id == null) {
            throw new BusinessException("invalid contact id data passed into service method");
        }

        return contactRepository.findOne(id);
    }

    public Contact create(Contact contact, User user) {
        if (user == null || user.getId() == null) {
            throw  new BusinessException("invalid user passed into create()");
        }

        // establish the relationship and user is parent
        user.addContact(contact);

        if (!contact.validate()) {
            throw new BusinessException("Required fields are missing from contact");
        }

        return contactRepository.save(contact);
    }

    public Contact update(Contact contact) {
        if (contact.getId() == null) {
            throw new BusinessException("contact not valid");
        }

        if (getById(contact.getId()) == null) {
            throw new BusinessException("no valid contact found with id: " + contact.getId());
        }

        return contactRepository.save(contact);
    }

    public Boolean delete(Long id) {
        if (id == null) {
            throw new BusinessException("contact id not passed");
        }
        Contact deleteMe = this.getById(id);

        if (deleteMe == null) {
            LOG.error("unable to find a valid contact with contactId: {}", id);
            throw new BusinessException("could not find a valid contact with id: " + id);
        }

        contactRepository.delete(deleteMe);

        return Boolean.TRUE;
    }
}
