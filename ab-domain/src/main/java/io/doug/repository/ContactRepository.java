package io.doug.repository;

import io.doug.entity.Contact;
import io.doug.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

/**
 * Created by djeremias on 12/28/16.
 */
public interface ContactRepository extends PagingAndSortingRepository<Contact, Long> {

    Page<Contact> findByUser(User user, Pageable pageable);

    @Query("SELECT c FROM Contact c "
            + "WHERE (UPPER(COALESCE(c.firstName,' ')) LIKE %:firstName%) "
            + "AND (UPPER(COALESCE(c.lastName,' ')) LIKE %:lastName%) "
            + "AND c.user = :user")
    Page<Contact> findByFirstNameAndLastName(@Param("firstName") String firstName,
                                             @Param("lastName") String lastName,
                                             @Param("user") User user, Pageable pageable);
}
