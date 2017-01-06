package io.doug.controller;

import io.doug.entity.User;
import io.doug.util.ApiResponse;
import io.doug.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

/**
 * Created by djeremias on 1/3/17.
 */
@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<ApiResponse<User>> getUser(
            @AuthenticationPrincipal User authenticatedUser,
            @RequestHeader("x-client-id") Long clientId,
            @PathVariable Long id) {
        ApiResponse<User> apiResponse = new ApiResponse<>(userService.getById(id));

        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }
}
