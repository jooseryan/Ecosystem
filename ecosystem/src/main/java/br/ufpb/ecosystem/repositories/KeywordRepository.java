package br.ufpb.ecosystem.repositories;

import br.ufpb.ecosystem.entities.Keyword;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface KeywordRepository extends JpaRepository<Keyword, Long> {

    Optional<Keyword> findByValue(String value);
}
