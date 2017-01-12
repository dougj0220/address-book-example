package io.doug.controller;

import io.doug.entity.Contact;
import io.doug.entity.User;
import io.doug.repository.UserRepository;
import io.doug.service.ContactService;
import org.json.JSONObject;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMethod;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by djeremias on 1/10/17.
 */
public class ContactControllerTest extends AbstractControllerTest {

    @Autowired
    private ContactService contactService;

    @Autowired
    private UserRepository userRepository;

    private Contact contact;
    //private User user;

    @Override
    public void before() {
        User user = new User("admin@admin.com","password");
        userRepository.save(user);
        contact = new Contact("Doug", "testing", "me@test.com", "412-555-1234");
        contact.setUser(user);
        contactService.create(contact, user);
    }


    @Test
    public void testGetById() throws Exception {
        String url = "/contact/" + contact.getId();

        MockHttpServletRequestBuilder getRequest = buildRequest(RequestMethod.GET, url, true);

        String getResult = mockMvc.perform(getRequest).andExpect(status().isOk()).andReturn().getResponse()
                .getContentAsString();

        JSONObject contactJson = new JSONObject(getResult).getJSONObject("payload");
        Assert.isTrue(new Long(contactJson.getLong("id")).equals(contact.getId()));
    }
}
