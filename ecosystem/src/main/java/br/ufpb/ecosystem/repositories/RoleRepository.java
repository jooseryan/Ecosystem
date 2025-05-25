package br.ufpb.ecosystem.repositories;

import br.ufpb.ecosystem.entities.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface RoleRepository extends JpaRepository<UserRole, Long> {
    Optional<UserRole> findByRole(String role);
}
