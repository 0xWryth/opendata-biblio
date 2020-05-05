package et3.java.model;

import java.util.*;

public abstract class Document {
    private String title;
    private String EAN;
    private String date;
    private String publisher;

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
    
    
}