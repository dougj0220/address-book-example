package io.doug.controller;

import io.doug.entity.Contact;
import io.doug.service.ContactService;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.util.Assert;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by djeremias on 1/11/17.
 */
@RunWith(SpringRunner.class)
@WebMvcTest(value = ContactController.class)
public class NewContactControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private ContactService contactService;

    private Contact contact;
    private Contact retContact;

    @Before
    public void setup() {
        contact = new Contact("new", "contact", "new@contact.com", "412-555-1234");
        contact.setId(999999L);
        retContact = new Contact();
    }

    @WithMockUser(username="admin@doug.com")
    @Test
    public void testGetById() throws Exception {
        given(contactService.getById(999999L)).willReturn(contact);

        String url = "/contact/" + 999999L;


       String result =  mvc.perform(MockMvcRequestBuilders.get(url))
               .andExpect(status().isOk())
               .andReturn()
               .getResponse().getContentAsString();

        JSONObject json = new JSONObject(result).getJSONObject("payload");
        Assert.isTrue(json != null);
        Assert.isTrue(json.getString("firstName").equals("new"));
    }
}
