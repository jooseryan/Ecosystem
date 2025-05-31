package br.ufpb.ecosystem.repositories;

import br.ufpb.ecosystem.entities.BibliographicSource;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BibliographicSourceRepository extends JpaRepository<BibliographicSource, Long> {

    List<BibliographicSource> findByTitleAndYearAndAbstractText(String title, Integer year, String abstractText);

}
