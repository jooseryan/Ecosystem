package br.ufpb.ecosystem.specification;

import br.ufpb.ecosystem.entities.Author;
import br.ufpb.ecosystem.entities.BibliographicSource;
import br.ufpb.ecosystem.enums.BibliographicSourceEnum;
import org.springframework.data.jpa.domain.Specification;

import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;

public class BibliographicSourceSpecs {

    public static Specification<BibliographicSource> titleContains(String title) {
        return (root, query, cb) ->
                (title == null || title.isBlank()) ? null :
                        cb.like(cb.lower(root.get("title")), "%" + title.toLowerCase() + "%");
    }

    public static Specification<BibliographicSource> authorNameContains(String authorName) {
        return (root, query, cb) -> {
            if (authorName == null || authorName.isBlank()) return null;
            Join<BibliographicSource, Author> authors = root.join("authors", JoinType.LEFT);
            return cb.like(cb.lower(authors.get("name")), "%" + authorName.toLowerCase() + "%");
        };
    }

    public static Specification<BibliographicSource> yearEquals(Integer year) {
        return (root, query, cb) ->
                (year == null) ? null :
                        cb.equal(root.get("year"), year);
    }

    public static Specification<BibliographicSource> typeEquals(BibliographicSourceEnum.Type type) {
        return (root, query, cb) ->
                (type == null) ? null :
                        cb.equal(root.get("type"), type);
    }

    public static Specification<BibliographicSource> mediaEquals(BibliographicSourceEnum.Media media) {
        return (root, query, cb) ->
                (media == null) ? null :
                        cb.equal(root.get("media"), media);
    }
}
