package io.doug;

import io.doug.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by djeremias on 12/28/16.
 */
public interface UserRepository extends JpaRepository<User, Long> {

    User findByLogin(String login);
}
