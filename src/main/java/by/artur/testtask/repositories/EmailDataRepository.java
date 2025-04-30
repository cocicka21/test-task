package by.artur.testtask.repositories;

import by.artur.testtask.entities.EmailData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(exported = false)
public interface EmailDataRepository extends JpaRepository<EmailData, Long> {

    boolean existsByEmail(String email);
}