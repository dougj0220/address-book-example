package io.doug.service;

import io.doug.exception.BusinessException;
import io.doug.repository.UserRepository;
import io.doug.entity.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

/**
 * Created by djeremias on 12/28/16.
 */
@Service
@Transactional
public class UserService implements UserDetailsService {

    private final static Logger LOG = LoggerFactory.getLogger(UserService.class);

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // used for OAuth authentication
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = getByLogin(username);
        if (user == null) {
            throw new UsernameNotFoundException(String.format("User %s does not exist!", username));
        }

        return  new User(user);
    }

    public User getByLogin(String login) {
        return userRepository.findByLogin(login);
    }

    public User getById(Long id) {
        if (id == null) {
            throw new BusinessException("No valid id was passed to getById()");
        }

        User foundUser = userRepository.findOne(id);

        if (foundUser == null) {
            throw new BusinessException("Could not find a user with id: " + id);
        }

        return foundUser;
    }

}
