package br.ufpb.ecosystem.entities;

import jakarta.persistence.*;
import java.util.List;
import br.ufpb.ecosystem.enums.BibliographicSourceEnum.Type;
import br.ufpb.ecosystem.enums.BibliographicSourceEnum.Media;

@Entity
@Table(name = "bibliographic_source")
public class BibliographicSource {

    private static final int DEFAULT_TEXT_LENGTH = 1000;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "reviewer_code")
    private String reviewerCode;

    @Column(length = DEFAULT_TEXT_LENGTH)
    private String title;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "bibliographic_source_author",
            joinColumns = @JoinColumn(name = "source_id"),
            inverseJoinColumns = @JoinColumn(name = "author_id")
    )
    private List<Author> authors;

    private int year;

    @Column(length = DEFAULT_TEXT_LENGTH)
    private String reference;

    @Column(length = DEFAULT_TEXT_LENGTH)
    private String url;

    private Type type;

    private Media media;

    @Column(name = "drive_url", length = DEFAULT_TEXT_LENGTH)
    private String driveUrl;

    @Column(name = "image_url", length = DEFAULT_TEXT_LENGTH)
    private String imageUrl;

    @Column(length = DEFAULT_TEXT_LENGTH)
    private String notes;

    @ManyToMany
    @JoinTable(
            name = "bibliographic_source_keyword",
            joinColumns = @JoinColumn(name = "source_id"),
            inverseJoinColumns = @JoinColumn(name = "keyword_id")
    )
    private List<Keyword> keywords;


    @Column(name = "abstract_text", length = DEFAULT_TEXT_LENGTH)
    private String abstractText;

    public BibliographicSource() {}

    public BibliographicSource(String reviewerCode, String title, List<Author> authors, int year, String reference, String url,
                               Type type, Media media, String driveUrl, String imageUrl, String notes,
                               List<Keyword> keywords, String abstractText) {
        this.reviewerCode = reviewerCode;
        this.title = title;
        this.authors = authors;
        this.year = year;
        this.reference = reference;
        this.url = url;
        this.type = type;
        this.media = media;
        this.driveUrl = driveUrl;
        this.imageUrl = imageUrl;
        this.notes = notes;
        this.keywords = keywords;
        this.abstractText = abstractText;
    }

    // Getters and Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getReviewerCode() {
        return reviewerCode;
    }

    public void setReviewerCode(String reviewerCode) {
        this.reviewerCode = reviewerCode;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<Author> getAuthors() {
        return authors;
    }

    public void setAuthors(List<Author> authors) {
        this.authors = authors;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public Media getMedia() {
        return media;
    }

    public void setMedia(Media media) {
        this.media = media;
    }

    public String getDriveUrl() {
        return driveUrl;
    }

    public void setDriveUrl(String driveUrl) {
        this.driveUrl = driveUrl;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public List<Keyword> getKeywords() {
        return keywords;
    }

    public void setKeywords(List<Keyword> keywords) {
        this.keywords = keywords;
    }

    public String getAbstractText() {
        return abstractText;
    }

    public void setAbstractText(String abstractText) {
        this.abstractText = abstractText;
    }
}
