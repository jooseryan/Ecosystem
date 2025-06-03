package br.ufpb.ecosystem.services;

import br.ufpb.ecosystem.dtos.AuthorDTO;
import br.ufpb.ecosystem.dtos.BibliographicSourceDTO;
import br.ufpb.ecosystem.dtos.KeywordDTO;
import br.ufpb.ecosystem.entities.Author;
import br.ufpb.ecosystem.entities.BibliographicSource;
import br.ufpb.ecosystem.entities.Keyword;
import br.ufpb.ecosystem.repositories.AuthorRepository;
import br.ufpb.ecosystem.repositories.BibliographicSourceRepository;
import br.ufpb.ecosystem.repositories.KeywordRepository;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class BibliographicSourceService {

    private final BibliographicSourceRepository bibliographicSourceRepository;
    private final KeywordRepository keywordRepository;
    private final AuthorRepository authorRepository;

    public BibliographicSourceService(BibliographicSourceRepository bibliographicSourceRepository, KeywordRepository keywordRepository, AuthorRepository authorRepository) {
        this.bibliographicSourceRepository = bibliographicSourceRepository;
        this.keywordRepository = keywordRepository;
        this.authorRepository = authorRepository;
    }

    private List<Author> convertAuthorDtoToEntity(List<AuthorDTO> authorDtos) {
        if (authorDtos == null) return new ArrayList<>();

        return authorDtos.stream()
                .map(dto -> {
                    Optional<Author> existing = authorRepository.findByEmail(dto.getEmail());
                    return existing.orElseGet(() -> {
                        Author newAuthor = new Author();
                        newAuthor.setId(dto.getId());
                        newAuthor.setName(dto.getName());
                        newAuthor.setEmail(dto.getEmail());
                        newAuthor.setOrcid(dto.getOrcid());
                        newAuthor.setAffiliation(dto.getAffiliation());
                        return authorRepository.save(newAuthor);
                    });
                })
                .collect(Collectors.toList());
    }

    private List<Keyword> convertKeywordDtoToEntity(List<KeywordDTO> keywordDtos) {
        if (keywordDtos == null) return new ArrayList<>();

        return keywordDtos.stream()
                .map(dto -> {
                    Optional<Keyword> existing = keywordRepository.findByValue(dto.getValue());
                    return existing.orElseGet(() -> {
                        Keyword newKeyword = new Keyword();
                        newKeyword.setValue(dto.getValue());
                        return keywordRepository.save(newKeyword);
                    });
                })
                .collect(Collectors.toList());
    }

    private List<AuthorDTO> convertAuthorToDto(List<Author> authors) {
        if (authors == null) return new ArrayList<>();

        return authors.stream()
                .map(author -> new AuthorDTO(author.getId(), author.getName(), author.getEmail(),
                        author.getOrcid(), author.getAffiliation()))
                .collect(Collectors.toList());
    }

    private List<KeywordDTO> convertKeywordToDto(List<Keyword> keywords) {
        if (keywords == null) return new ArrayList<>();

        return keywords.stream()
                .map(keyword -> new KeywordDTO(keyword.getId(), keyword.getValue()))
                .collect(Collectors.toList());
    }

    public BibliographicSourceDTO insert(BibliographicSourceDTO dto) {
        if (isDuplicate(dto)) {
            throw new IllegalArgumentException("Este trabalho já foi registrado.");
        }
        List<Author> authors = convertAuthorDtoToEntity(dto.getAuthors());
        List<Keyword> keywords = convertKeywordDtoToEntity(dto.getKeywords());


        BibliographicSource source = new BibliographicSource(
                dto.getCode(), dto.getTitle(), authors, dto.getYear(),
                dto.getReference(), dto.getUrl(), dto.getType(), dto.getMedia(),
                dto.getDriveUrl(), dto.getImageUrl(), dto.getNotes(), keywords, dto.getAbstractText()
        );

        BibliographicSource saved = bibliographicSourceRepository.save(source);

        return new BibliographicSourceDTO(
                saved.getReviewerCode(), saved.getTitle(), convertAuthorToDto(saved.getAuthors()), saved.getYear(),
                saved.getReference(), saved.getUrl(), saved.getType(), saved.getMedia(),
                saved.getDriveUrl(), saved.getImageUrl(), saved.getNotes(), convertKeywordToDto(saved.getKeywords()), saved.getAbstractText()
        );
    }

    private List<BibliographicSourceDTO> saveAllToDatabase(List<BibliographicSourceDTO> dtos) {
        List<BibliographicSource> sources = dtos.stream()
                .map(dto -> new BibliographicSource(
                        dto.getCode(), dto.getTitle(), convertAuthorDtoToEntity(dto.getAuthors()), dto.getYear(),
                        dto.getReference(), dto.getUrl(), dto.getType(), dto.getMedia(),
                        dto.getDriveUrl(), dto.getImageUrl(), dto.getNotes(), convertKeywordDtoToEntity(dto.getKeywords()), dto.getAbstractText()
                ))
                .collect(Collectors.toList());

        bibliographicSourceRepository.saveAll(sources);
        return dtos;
    }

    public String formatAuthorNames(String authors) {
        String[] authorList = authors.split(";");
        List<String> formattedNames = new ArrayList<>();

        for (String author : authorList) {
            String[] parts = author.trim().split(",");
            if (parts.length == 2) {
                formattedNames.add(parts[1].trim() + " " + parts[0].trim());
            } else {
                formattedNames.add(author.trim());
            }
        }
        return String.join(", ", formattedNames);
    }

    public int parseIntSafe(String value) {
        if (value == null || value.trim().isEmpty()) {
            return 0;
        }
        return Integer.parseInt(value);
    }

    public List<BibliographicSourceDTO> findAll() {
        return bibliographicSourceRepository.findAll().stream()
                .map(b -> new BibliographicSourceDTO(
                        b.getReviewerCode(), b.getTitle(), convertAuthorToDto(b.getAuthors()), b.getYear(), b.getReference(),
                        b.getUrl(), b.getType(), b.getMedia(), b.getDriveUrl(), b.getImageUrl(), b.getNotes(),
                        convertKeywordToDto(b.getKeywords()), b.getAbstractText()
                ))
                .collect(Collectors.toList());
    }

    public Optional<BibliographicSourceDTO> findById(Long id) {
        return bibliographicSourceRepository.findById(id)
                .map(b -> new BibliographicSourceDTO(
                        b.getReviewerCode(), b.getTitle(), convertAuthorToDto(b.getAuthors()), b.getYear(), b.getReference(),
                        b.getUrl(), b.getType(), b.getMedia(), b.getDriveUrl(), b.getImageUrl(), b.getNotes(),
                        convertKeywordToDto(b.getKeywords()), b.getAbstractText()
                ));
    }

    public BibliographicSourceDTO update(Long id, BibliographicSourceDTO dto) {
        BibliographicSource source = bibliographicSourceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Record not found."));

        source.setTitle(dto.getTitle());
        source.setAuthors(convertAuthorDtoToEntity(dto.getAuthors()));
        source.setYear(dto.getYear());
        source.setReference(dto.getReference());
        source.setUrl(dto.getUrl());
        source.setType(dto.getType());
        source.setMedia(dto.getMedia());
        source.setDriveUrl(dto.getDriveUrl());
        source.setImageUrl(dto.getImageUrl());
        source.setNotes(dto.getNotes());
        source.setKeywords(convertKeywordDtoToEntity(dto.getKeywords()));
        source.setAbstractText(dto.getAbstractText());

        BibliographicSource updated = bibliographicSourceRepository.save(source);

        return new BibliographicSourceDTO(
                updated.getReviewerCode(), updated.getTitle(), convertAuthorToDto(updated.getAuthors()), updated.getYear(),
                updated.getReference(), updated.getUrl(), updated.getType(), updated.getMedia(),
                updated.getDriveUrl(), updated.getImageUrl(), updated.getNotes(), convertKeywordToDto(updated.getKeywords()),
                updated.getAbstractText()
        );
    }

    public BibliographicSourceDTO partialUpdate(Long id, BibliographicSourceDTO dto) {
        BibliographicSource source = bibliographicSourceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Record not found."));

        if (dto.getTitle() != null) source.setTitle(dto.getTitle());
        if (dto.getAuthors() != null) source.setAuthors(convertAuthorDtoToEntity(dto.getAuthors()));
        if (Objects.nonNull(dto.getYear())) source.setYear(dto.getYear());
        if (dto.getReference() != null) source.setReference(dto.getReference());
        if (dto.getUrl() != null) source.setUrl(dto.getUrl());
        if (dto.getType() != null) source.setType(dto.getType());
        if (dto.getMedia() != null) source.setMedia(dto.getMedia());
        if (dto.getDriveUrl() != null) source.setDriveUrl(dto.getDriveUrl());
        if (dto.getImageUrl() != null) source.setImageUrl(dto.getImageUrl());
        if (dto.getNotes() != null) source.setNotes(dto.getNotes());
        if (dto.getKeywords() != null) source.setKeywords(convertKeywordDtoToEntity(dto.getKeywords()));
        if (dto.getAbstractText() != null) source.setAbstractText(dto.getAbstractText());

        BibliographicSource updated = bibliographicSourceRepository.save(source);

        return new BibliographicSourceDTO(
                updated.getReviewerCode(), updated.getTitle(), convertAuthorToDto(updated.getAuthors()), updated.getYear(),
                updated.getReference(), updated.getUrl(), updated.getType(), updated.getMedia(),
                updated.getDriveUrl(), updated.getImageUrl(), updated.getNotes(), convertKeywordToDto(updated.getKeywords()), updated.getAbstractText()
        );
    }

    public void delete(Long id) {
        if (!bibliographicSourceRepository.existsById(id)) {
            throw new RuntimeException("Record not found.");
        }
        bibliographicSourceRepository.deleteById(id);
    }

    public boolean isDuplicate(BibliographicSourceDTO dto) {
        List<BibliographicSource> checkers = bibliographicSourceRepository
                .findByTitleAndYearAndAbstractText(
                        dto.getTitle().trim(),
                        dto.getYear(),
                        dto.getAbstractText().trim()
                );

        for (BibliographicSource checker : checkers) {
            if (sameAuthors(checker.getAuthors(), dto.getAuthors())) {
                return true;
            }
        }

        return false;
    }


    private boolean sameAuthors(List<Author> entityAuthors, List<AuthorDTO> dtoAuthors) {
        Set<String> existingAuthorSet = entityAuthors.stream()
                .map(a -> a.getName().trim().toLowerCase())
                .collect(Collectors.toCollection(TreeSet::new)); // ordena

        Set<String> dtoAuthorSet = dtoAuthors.stream()
                .map(a -> a.getName().trim().toLowerCase())
                .collect(Collectors.toCollection(TreeSet::new)); // ordena

        return existingAuthorSet.equals(dtoAuthorSet);
    }

}
