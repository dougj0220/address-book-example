package io.doug.repository;

import io.doug.entity.Contact;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by djeremias on 12/28/16.
 */
public interface ContactRepository extends JpaRepository<Contact, Long> {


}
