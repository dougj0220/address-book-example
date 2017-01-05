package io.doug.service;

import io.doug.entity.Contact;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import javax.transaction.Transactional;

/**
 * Created by djeremias on 1/4/17.
 */
@Transactional
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class ContactServiceTest {

    @Autowired
    private ContactService contactService;
    private Contact contact;

    @Before
    public void setup() throws Exception {
        contact = new Contact("Doug", "testing", "me@test.com", "412-555-1234");
        contactService.create(contact);
    }

    @Test
    public void testGetById() throws Exception {
        Contact retVal = contactService.getById(contact.getId());
        Assert.isTrue(retVal != null);
        Assert.isTrue(retVal.getFirstName().equals(contact.getFirstName()));
    }

    @Test
    public void testCreate() throws Exception {
        Contact newContact = new Contact("newContact", "testing", "me@test.com", "412-555-1234");
        contactService.create(newContact);
        Assert.isTrue(newContact.getId() != null);
    }
}
