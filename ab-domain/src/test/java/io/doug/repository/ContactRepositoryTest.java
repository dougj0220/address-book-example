package io.doug.repository;

import io.doug.entity.Contact;
import io.doug.entity.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.Assert;

import javax.transaction.Transactional;

/**
 * Created by djeremias on 1/4/17.
 */
@Transactional
@RunWith(SpringRunner.class)
//@SpringBootTest
@DataJpaTest
@AutoConfigureTestDatabase(replace=AutoConfigureTestDatabase.Replace.NONE)
public class ContactRepositoryTest {

    @Autowired
    private ContactRepository contactRepository;

    @Autowired
    private UserRepository userRepository;

    private Contact contact;
    private User user;

    @Before
    public void setup() throws Exception {
        user = new User("admin@admin.com","password");
        userRepository.save(user);
    }

    @Test
    public void testCreateContact() throws Exception {
        contact = new Contact("doug", "testing", "email@test.com", "412-555-1234");
        contact.setUser(user);
        contactRepository.save(contact);
        Assert.isTrue(contact.getId() != null);
    }
}
