package br.ufpb.ecosystem.repositories;

import br.ufpb.ecosystem.entities.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository; // Optional but recommended
import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<UserRole, Long> {

    Optional<UserRole> findByRole(String role);
}
