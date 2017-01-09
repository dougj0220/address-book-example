package io.doug.service;

import io.doug.entity.Contact;
import io.doug.exception.BusinessException;
import io.doug.repository.ContactRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

/**
 * Created by djeremias on 12/28/16.
 */
@Service
@Transactional
public class ContactService {

    private final ContactRepository contactRepository;

    @Autowired
    public ContactService(ContactRepository contactRepository) {
        this.contactRepository = contactRepository;
    }


    public Contact getById(Long id) {
        if (id == null) {
            throw new BusinessException("");
        }

        return contactRepository.findOne(id);
    }

    public Contact create(Contact contact) {
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
}
