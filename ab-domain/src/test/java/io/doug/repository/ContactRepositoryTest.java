package io.doug.repository;

import io.doug.ContactRepository;
import io.doug.entity.Contact;
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
public class ContactRepositoryTest {

    @Autowired
    private ContactRepository contactRepository;
    private Contact contact;

    @Test
    public void testCreateContact() throws Exception {
        contact = new Contact("doug", "testing", "email@test.com", "412-555-1234");
        contactRepository.save(contact);
        Assert.isTrue(contact.getId() != null);
    }
}
