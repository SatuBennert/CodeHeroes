package app.domain;

import javax.persistence.Entity;
import javax.persistence.DiscriminatorValue;
import javax.validation.constraints.Pattern;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * This class is for creating different Article objects.
 */
@Entity
@DiscriminatorValue(value = "Article")
public class Article extends Reference {

    /**
     * Compulsory variables:
     */
    @NotEmpty(message = "Field can not be empty!")
    private String journal;

    private String publisher;
    private String address;

    @NotEmpty(message = "Field can not be empty!")
    @Pattern(regexp = "^([1-9][0-9]{0,4})*$", message = "Field must contain number between 1 and 19999")
    private String volume;

    @Pattern(regexp = "^([1-9][0-9]{0,4})*$", message = "Field must contain number between 1 and 19999")
    private String number;

    @Pattern(regexp = "^([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9]|[1-9][0-9][0-9][0-9][0-9])*$", message = "Field must contain number between 1 and 199999")
    private String startingPage;

    @Pattern(regexp = "^([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|[1-9][0-9][0-9][0-9]|[1-9][0-9][0-9][0-9][0-9])*$", message = "Field must contain number between 1 and 199999")
    private String endingPage;

    @Pattern(regexp = "^([1-9][0-2]{0,1})*$", message = "Field must contain number between 1 and 12")
    private String month;

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getPublisher() {
        return this.publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public void setJournal(String journal) {
        this.journal = journal;
    }

    public String getJournal() {
        return this.journal;
    }

    public void setVolume(String vol) {
        this.volume = vol;
    }

    public String getVolume() {
        return this.volume;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getNumber() {
        return this.number;
    }

    public void setEndingPage(String epage) {
        this.endingPage = epage;
    }

    public String getEndingPage() {
        return this.endingPage;
    }

    public void setStartingPage(String spage) {
        this.startingPage = spage;
    }

    public String getStartingPage() {
        return this.startingPage;
    }

    public String getAddress() {
        return this.address;
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
        String output = super.authorsToString() + ". " + super.getTitle() + ". " + this.journal + ", " + this.volume;
        if (!this.number.isEmpty()) {
            output = output + "(" + this.number + ")";
        }

        if (!this.startingPage.isEmpty() && !this.endingPage.isEmpty()) {
            output = output + ":" + this.startingPage + "-" + this.endingPage;
        }
        if (!this.publisher.isEmpty()) {
            output = output + ", " + this.publisher;
        }
        output = output + ", " + super.getYear() + ".";
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

        String output = "@article{" + super.getKey() + ",\n";
        output = output + "author = {" + super.authorsToBibTex() + "},\n";
        output = output + "title = {" + super.getTitle() + "},\n";
        output = output + "journal = {" + this.journal + "},\n";
        output = output + "vol = {" + this.volume + "},\n";
        if (!this.number.isEmpty()) {
            output = output + "number = {" + this.number + "},\n";
        }
        output = output + "year = {" + super.getYear() + "},\n";
        if (!this.startingPage.isEmpty() && !this.endingPage.isEmpty()) {
            output = output + "pages = {" + this.startingPage + "--" + this.endingPage + "},\n";
        }
        if (!this.publisher.isEmpty()) {
            output = output + "publisher = {" + this.publisher + "},\n";
        }
        if (!this.address.isEmpty()) {
            output = output + "address = {" + this.address + "},\n";
        }
        output = output + "}";
        return output;
    }

}
