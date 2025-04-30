package by.artur.testtask.configuration;

import by.artur.testtask.entities.EmailData;
import by.artur.testtask.entities.User;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class UserSpecification {
    public static Specification<User> withFilters(String name, String phone, String email, LocalDate dateOfBirth) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (name != null && !name.isEmpty()) {
                predicates.add(cb.like(cb.lower(root.get("name")), name.toLowerCase() + "%"));
            }

            if (phone != null && !phone.isEmpty()) {
                predicates.add(cb.equal(root.get("phones"), phone));
            }

            if (email != null && !email.isEmpty()) {
                Join<User, EmailData> emailJoin = root.join("emails", JoinType.LEFT);
                predicates.add(cb.equal(emailJoin.get("email"), email));
            }

            if (dateOfBirth != null) {
                predicates.add(cb.greaterThan(root.get("dateOfBirth"), dateOfBirth));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}
