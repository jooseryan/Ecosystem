package br.com.ecosystem.repositories;

import br.com.ecosystem.models.Biblioteca;
import org.springframework.data.jpa.repository.JpaRepository;


public interface BibliotecaRepository extends JpaRepository<Biblioteca, String> {
}
