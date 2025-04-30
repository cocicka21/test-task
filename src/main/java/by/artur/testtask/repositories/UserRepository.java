package by.artur.testtask.repositories;

import by.artur.testtask.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.Optional;

@RepositoryRestResource(exported = false)
public interface UserRepository extends JpaRepository<User, Long>, JpaSpecificationExecutor<User> {

    Optional<User> findByEmails_Email(String email);

    Optional<User> findByPhones_Phone(String phone);

    Optional<User> findByName(String name);

//    @Query("""
//            SELECT u FROM User u
//            JOIN FETCH u.account
//            LEFT JOIN FETCH u.emails
//            LEFT JOIN FETCH u.phones
//            WHERE (:name IS NULL OR u.name LIKE CONCAT(:name, '%'))
//              AND (:email IS NULL OR EXISTS (
//                  SELECT 1 FROM EmailData e WHERE e.user = u AND e.email = :email))
//              AND (:phone IS NULL OR EXISTS (
//                  SELECT 1 FROM PhoneData p WHERE p.user = u AND p.phone = :phone))
//              AND (:dateOfBirth IS NULL OR u.dateOfBirth > :dateOfBirth)
//            """)
//
//    Page<User> searchUsers(
//            @Param("name") String name,
//            @Param("email") String email,
//            @Param("phone") String phone,
//            @Param("dateOfBirth") LocalDate dateOfBirth,
//            Pageable pageable
//    );
}