package br.ufpb.ecosystem.repositories;

import br.ufpb.ecosystem.entities.BibliographicSource;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BibliographicSourceRepository extends JpaRepository<BibliographicSource, String> {
}
