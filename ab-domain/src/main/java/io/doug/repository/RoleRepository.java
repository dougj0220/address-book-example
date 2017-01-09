package io.doug.repository;

import io.doug.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;

/**
 * Created by djeremias on 1/5/17.
 */
public interface RoleRepository extends JpaRepository<Role, LocalDate> {
    Role findByName(String roleName);
}
