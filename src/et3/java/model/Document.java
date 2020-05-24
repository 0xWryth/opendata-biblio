package et3.java.model;

import java.util.*;

/**
 * Document is the abstract base class for all types of document
 * used in the modelization of the OpenData library.
 * A Document object encapsulates a title, an EAN, a date...
 */
public abstract class Document {
    private String title;
    private String EAN;
    private String date;
    private String publisher;
    private Author author;

    public Document(String title, String EAN, String date, String publisher) {
        this.title = title;
        this.EAN = EAN;
        this.date = date;
        this.publisher = publisher;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 17 * hash + Objects.hashCode(this.EAN);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        
        final Document other = (Document) obj;
        
        if (!this.title.equals(other.title)) {
            return false;
        }
        if (!this.EAN.equals(other.EAN)) {
            return false;
        }
        if (!this.date.equals(other.date)) {
            return false;
        }
        if (!this.publisher.equals(other.publisher)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + " : \"" + title
                + "\", author=" + author + ", EAN=" + EAN
                + ", published in " + date + " by " + publisher;
    }

    public void setAuthor(Author docAuthor) {
        this.author = docAuthor;
    }

    public String getEAN() {
        return EAN;
    }

    public String getTitle() {
        return title;
    }

    public String getDate() {
        return date;
    }

    public String getPublisher() {
        return publisher;
    }

    public Author getAuthor() {
        return author;
    }
}