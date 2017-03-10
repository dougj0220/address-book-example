package io.doug.controller;

import io.doug.entity.User;
import io.doug.service.UserService;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.util.Base64Utils;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.transaction.Transactional;

import static org.hamcrest.Matchers.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Created by djeremias on 1/10/17.
 */
@Transactional
@RunWith(SpringRunner.class)
//@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
@WebMvcTest(value = {ContactController.class, UserController.class, OAuthController.class})
public abstract class AbstractControllerTest {

    static final String ADMIN_PASSWORD = "password";

    @Autowired
    UserService userService;

    @Autowired
    MockMvc mockMvc;

    User adminUser;
    String accessToken;

    @Value("${oauth2.client.id}")
    private String getClientId;

    @Value("${oauth2.client.secret}")
    private String getClientSecret;

    @Before
    public void setUp() throws Exception {
       //MockitoAnnotations.initMocks(this);

       /*mockMvc = MockMvcBuilders.webAppContextSetup(context)
               //.addFilter(springSecurityFilterChain)
               .apply(springSecurity())
               .build();*/

        adminUser = userService.getByLogin("admin@doug.com");

        accessToken = getAccessToken(adminUser.getLogin(), ADMIN_PASSWORD);

        before();
    }

    abstract public void before() throws Exception;

    MockHttpServletRequestBuilder buildRequest(RequestMethod method, String uri, boolean withAuthHeader) {
        return buildRequest(method, uri, withAuthHeader, true, null);
    }

    MockHttpServletRequestBuilder buildRequest(RequestMethod method, String uri, boolean withAuthHeader, boolean withAdminUser, User notAdminUser) {

        MockHttpServletRequestBuilder builder;

        switch (method) {
            case DELETE:
                builder = MockMvcRequestBuilders
                        .delete(uri);
                break;
            case POST:
                builder = MockMvcRequestBuilders
                        .post(uri);
                break;
            case PUT:
                builder = MockMvcRequestBuilders
                        .put(uri);
                break;
            default:// GET falls into here
                builder = MockMvcRequestBuilders
                        .get(uri);
        }

        SecurityMockMvcRequestPostProcessors.UserRequestPostProcessor urpp = (withAdminUser ? user(adminUser.getUsername()) : user(notAdminUser.getUsername()));

        MockHttpServletRequestBuilder requestBuilder = builder
                .with(urpp);

        if (withAuthHeader) {
            builder.header("Authorization", "Bearer " + accessToken);
        }

        return requestBuilder;
    }


    String getAccessToken(String username, String password) throws Exception {
        String authorization = "Basic " + new String(Base64Utils.encode((getClientId + ":" + getClientSecret).getBytes()));
        String contentType = MediaType.APPLICATION_JSON + ";charset=UTF-8";

        // @formatter:off
        String content = mockMvc
                .perform(
                        post("/oauth/token")
                                .header("Authorization", authorization)
                                .contentType(
                                        MediaType.APPLICATION_FORM_URLENCODED)
                                .param("username", username)
                                .param("password", password)
                                .param("grant_type", "password")
                                .param("scope", "read write")
                                .param("client_id", getClientId)
                                .param("client_secret", getClientSecret))
                .andExpect(status().isOk())
                .andExpect(content().contentType(contentType))
                .andExpect(jsonPath("$.access_token", is(notNullValue())))
                .andExpect(jsonPath("$.token_type", is(equalTo("bearer"))))
                .andExpect(jsonPath("$.refresh_token", is(notNullValue())))
                .andExpect(jsonPath("$.expires_in", is(greaterThan(4000))))
                .andExpect(jsonPath("$.scope", is(equalTo("read write"))))
                //.andExpect(authenticated().withRoles("ROLE_SUPER_ADMIN","ROLE_ADMIN"))
                .andReturn().getResponse().getContentAsString();

        // @formatter:on
        return content.substring(17, 53);
    }

}
