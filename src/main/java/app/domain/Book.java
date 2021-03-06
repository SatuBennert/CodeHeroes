package app.domain;

import javax.persistence.Entity;
import javax.persistence.DiscriminatorValue;
import javax.validation.constraints.Pattern;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * This class is for creating different Book objects.
 */
@Entity
@DiscriminatorValue(value = "Book")
public class Book extends Reference {

    @NotEmpty(message = "Field can not be empty!")
    private String publisher;

    /**
     * Optional variables:
     */
    private String address;
    
    private String edition;

    @Pattern(regexp = "^([1-9][0-9]{0,4})*$", message ="Field must contain number between 1 and 19999")
    private String volume;
     
    private String series;
    
    @Pattern(regexp = "^([1-9][0-2]{0,1})*$", message ="Field must contain number between 1 and 12")
    private String month;
    
    public String getVolume() {
        return volume;
    }

    public void setVolume(String vol) {
        this.volume = vol;
    }

    public String getSeries() {
        return series;
    }

    public void setSeries(String series) {
        this.series = series;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }
    
    
    
    
    public String getEdition() {
        return edition;
    }

    public String getPublisher() {
        return this.publisher;
    }

    public String getAddress() {
        return this.address;
    }

    public void setEdition(String edition) {
        this.edition = edition;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    

    /**
     * This method returns all the information of the book referenced. The
     * if-sentence allows the address field to be empty.
     *
     * @return The reference information of a book as a String.
     */
    @Override
    public String toString() {
        String output = super.authorsToString() + ". " + super.getTitle() + ". " + this.publisher + ", ";
        if (!this.edition.isEmpty()) {
            output = output + this.edition + ", ";
        }
        output = output + super.getYear() + ".";
        if (!this.address.isEmpty()) {
            output = output + " " + this.address + ".";
        }
        output = output + " Key{" + super.getKey() + "}";
        if (!super.getTags().isEmpty()) {
            output = output + " " + super.tagsToString() + ".";
        }
        return output;
    }

    /**
     * This method returns all the information of the book referenced as a
     * BibTex String.
     *
     * @return The BibTex-reference information of a book as a String.
     */
    @Override
    public String toBibTex() {
        String output = "@book{" + super.getKey() + ",\n";
        output = output + "author = {" + super.authorsToBibTex() + "},\n";
        output = output + "title = {" + super.getTitle() + "},\n";
        output = output + "year = {" + super.getYear() + "},\n";
        output = output + "publisher = {" + this.publisher + "},\n";

        if (!this.address.isEmpty()) {
            output = output + "address = {" + this.address + "},\n";
        }
        if (!this.edition.isEmpty()) {
            output = output + "edition = {" + this.edition + "},\n";
        }

        output = output + "}";

        return output;
    }

}
