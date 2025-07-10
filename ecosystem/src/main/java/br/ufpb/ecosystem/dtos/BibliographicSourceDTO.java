package br.ufpb.ecosystem.dtos;

import br.ufpb.ecosystem.enums.BibliographicSourceEnum.Type;
import br.ufpb.ecosystem.enums.BibliographicSourceEnum.Media;

import java.util.List;

public class BibliographicSourceDTO {

    private Long id;
    private String reviewerCode;
    private String title;
    private List<AuthorDTO> authors;
    private int year;
    private String reference;
    private String url;
    private Type type;
    private Media media;
    private String driveUrl;
    private String imageUrl;
    private String notes;
    private List<KeywordDTO> keywords;
    private String abstractText;

    public BibliographicSourceDTO() {}

    public BibliographicSourceDTO(Long id, String reviewerCode, String title, List<AuthorDTO> authors, int year, String reference, String url,
                                  Type type, Media media, String driveUrl, String imageUrl, String notes,
                                  List<KeywordDTO> keywords, String abstractText) {
        this.id = id;
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

    public List<AuthorDTO> getAuthors() {
        return authors;
    }

    public void setAuthors(List<AuthorDTO> authors) {
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

    public List<KeywordDTO> getKeywords() {
        return keywords;
    }

    public void setKeywords(List<KeywordDTO> keywords) {
        this.keywords = keywords;
    }

    public String getAbstractText() {
        return abstractText;
    }

    public void setAbstractText(String abstractText) {
        this.abstractText = abstractText;
    }
}
