/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app;

import cucumber.api.java.After;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import java.io.File;
import java.util.List;

import static org.junit.Assert.assertTrue;
import org.openqa.selenium.Alert;

import org.openqa.selenium.By;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

public class Stepdefs {

    WebDriver driver;
    String baseUrl;
    int amount1, amount2;

    public Stepdefs() {

        baseUrl = "http://localhost:8080";
        File file;

        if (System.getProperty("os.name").matches("Mac OS X")) {
            file = new File("lib/macgeckodriver");
        } else if (System.getProperty("os.name").matches("Linux")) {
            file = new File("lib/geckodriver");
        } else {
            file = new File("lib/wingeckodriver.exe");
        }

        String path = file.getAbsolutePath();
        System.setProperty("webdriver.gecko.driver", path);
        this.driver = new FirefoxDriver();

    }

    @Given("^form book is selected$")
    public void form_book_is_selected() throws Throwable {
        driver.get(baseUrl + "/books");
        pageHasContent("Add book reference");
    }

    @Given("^form inproceedings is selected$")
    public void form_inproceedings_is_selected() throws Throwable {
        driver.get(baseUrl + "/inproceedings");
        pageHasContent("Add inproceedings reference");
    }

    @Given("^form article is selected$")
    public void form_article_is_selected() throws Throwable {
        driver.get(baseUrl + "/article");
        pageHasContent("Add article reference");
    }

    @Given("^Front page is opened$")
    public void front_page_is_opened() throws Throwable {
        driver.get(baseUrl);
        pageHasContent("Welcome to Reference manager");

    }

    @Given("^Edit is pressed for key \"([^\"]*)\"$")
    public void edit_is_pressed_for_key(String key) throws Throwable {
        // let's get all the <td> -elements from HTML
        List<WebElement> lista = driver.findElements(By.tagName("td"));
        int indeksi = 0;
        for (int i = 0; i < lista.size(); i++) {
            WebElement element = lista.get(i);    // there are four columns: checkbox, reference, tags, edit 
            if (element.getText().contains(key)) {
                indeksi = i + 2; // element + 2 -> get the edit -element 
                break;
            }
        }
        if (indeksi > 0) {            // let's press the founded EDIT button (submit doesn't work)
            lista.get(indeksi).click();
        }
        Thread.sleep(2000);
    }

    @Given("^a book reference with key \"([^\"]*)\" author \"([^\"]*)\" title \"([^\"]*)\" year \"([^\"]*)\" publisher \"([^\"]*)\" is created$")
    public void a_book_reference_with_key_author_title_year_publisher_is_created(String key, String author, String title, String year, String publisher) throws Throwable {
        form_book_is_selected();
        String address = "", series = "", edition = "", month = "", volume = "", tags = "";
        updateBook(key, author, title, year, publisher, address, series, edition, month, volume, tags);
        system_will_respond_with("Reference added successfully!");
    }

    // searchingReference.feature uses:
    @Given("^a book reference with key \"([^\"]*)\" author \"([^\"]*)\" title \"([^\"]*)\" year \"([^\"]*)\" publisher \"([^\"]*)\" is created succesfully$")
    public void a_book_reference_with_key_author_title_year_publisher_is_created_succesfully(String key, String author, String title, String year, String publisher) throws Throwable {
        form_book_is_selected();
        String address = "", series = "", edition = "", month = "", volume = "", tags = "";
        updateBook(key, author, title, year, publisher, address, series, edition, month, volume, tags);
        system_will_respond_with("Reference added successfully!");
    }

    @Given("^searchdata \"([^\"]*)\" is given$")
    public void searchdata_is_given(String searchData) throws Throwable {
        driver.findElement(By.name("search")).sendKeys(searchData);
    }

    @Given("^filename \"([^\"]*)\" is added$")
    public void filename_is_added(String fileName) throws Throwable {
        front_page_is_opened();
        driver.findElement(By.name("fileName")).sendKeys(fileName);
    }

    @Given("^number of displayed references is calculated$")
    public void number_of_displayed_references_is_calculated() throws Throwable {
        amount1 = driver.findElements(By.tagName("td")).size();
    }

    // tag-test needs this
    @Given("^book reference with key \"([^\"]*)\" author \"([^\"]*)\" title \"([^\"]*)\" year \"([^\"]*)\" publisher \"([^\"]*)\" tags \"([^\"]*)\"is created successfully$")
    public void book_reference_with_key_author_title_year_publisher_tags_is_created_successfully(String key, String author, String title, String year, String publisher, String tags) throws Throwable {
        form_book_is_selected();
        String address = "", series = "", edition = "", month = "", volume = "";
        updateBook(key, author, title, year, publisher, address, series, edition, month, volume, tags);
        system_will_respond_with("Reference added successfully!");
    }

    @When("^printFile button is pressed$")
    public void printfile_button_is_pressed() throws Throwable {
        driver.findElement(By.name("fileName")).click();
    }

    @When("^Search button is pressed$")
    public void search_button_is_pressed() throws Throwable {
        driver.findElement(By.name("search")).submit();
    }

    @When("^key \"([^\"]*)\" author \"([^\"]*)\" title \"([^\"]*)\" year \"([^\"]*)\" publisher \"([^\"]*)\" are inserted$")
    public void key_author_title_year_publisher_are_inserted(String key, String author, String title, String year, String publisher) throws Throwable {
        String address = "", series = "", edition = "", month = "", volume = "", tags = "";
        updateBook(key, author, title, year, publisher, address, series, edition, month, volume, tags);
    }

    @When("^key \"([^\"]*)\" author \"([^\"]*)\" title \"([^\"]*)\" year \"([^\"]*)\" journal \"([^\"]*)\" publisher \"([^\"]*)\" volume \"([^\"]*)\" number \"([^\"]*)\" startingPage \"([^\"]*)\" endingPage \"([^\"]*)\" month \"([^\"]*)\" address \"([^\"]*)\" are inserted$")
    public void key_author_title_year_journal_publisher_volume_number_startingPage_endingPage_month_address_are_inserted(String key, String authors, String title, String year, String journal, String publisher, String volume, String number, String startingPage, String endingPage, String month, String address) throws Throwable {
        String tags = "";
        updateArticle(key, authors, title, year, journal, publisher, volume, number, startingPage, endingPage, month, address, tags);

    }

    @When("^key \"([^\"]*)\" author \"([^\"]*)\" title \"([^\"]*)\" year \"([^\"]*)\" pubisher \"([^\"]*)\" editor \"([^\"]*)\" booktitle \"([^\"]*)\" address \"([^\"]*)\" series \"([^\"]*)\" startingPage \"([^\"]*)\" endingPage \"([^\"]*)\" month \"([^\"]*)\" organization \"([^\"]*)\" are inserted$")
    public void key_author_title_year_pubisher_editor_booktitle_address_series_startingPage_endingPage_month_organization_are_inserted(
            String key, String author, String title, String year, String publisher, String editor, String booktitle, String address, String series, String startingPage, String endingPage, String month, String organization) throws Throwable {
        String tags = "";
        updateInproceedings(key, author, title, year, publisher, editor, booktitle, address, series, startingPage, endingPage, month, organization, tags);
    }

    @When("^Delete is pressed$")
    public void delete_() throws InterruptedException {
        // this will make the popup visible
        driver.findElement(By.xpath("/html/body/div/div/div/form[2]/input[2]")).click();
        Thread.sleep(2000); // If you wanna see the popup for 2 seconds
    }

    @When("^popup is accepted$")
    public void popup_is_accepted() throws Throwable {
        boolean yes = true;
        popup(yes);
    }

    @When("^popup is not accepted$")
    public void popup_is_not_accepted() throws Throwable {
        boolean no = false;
        popup(no);
    }

    @When("^a book reference with key \"([^\"]*)\" author \"([^\"]*)\" title \"([^\"]*)\" year \"([^\"]*)\" publisher \"([^\"]*)\" is updated$")
    public void a_book_reference_with_key_author_title_year_publisher_is_updated(String key, String author, String title, String year, String publisher) throws Throwable {
        //   System.out.println("Driverin page source onko true? " +driver.getPageSource().contains("Edit a book reference"));
        assertTrue(driver.getPageSource().contains("Edit a book reference"));
        WebElement element = driver.findElement(By.name("key"));
        element.sendKeys(key);
        element = driver.findElement(By.name("authors"));
        element.sendKeys(author);

        driver.findElement(By.name("save")).submit();

    }

    @When("^ListAll button is pressed$")
    public void listall_button_is_pressed() throws Throwable {
        driver.findElement(By.name("listAll")).click();
    }

    @When("^number of displayed references is calculated again$")
    public void number_of_displayed_references_is_calculated_again() throws Throwable {
        amount2 = driver.findElements(By.tagName("td")).size();
    }

    @When("^link tag by name \"([^\"]*)\" is pressed$")
    public void link_tag_by_name_is_pressed(String tag) throws Throwable {
        List<WebElement> lista = driver.findElements(By.tagName("span"));
        for (WebElement element : lista) {
            if (element.getText().contains(tag)) {
                element.click();
            }
        }
        Thread.sleep(2000);
    }

    @Then("^the number is same$")
    public void the_number_is_same() throws Throwable {
        assertTrue(this.amount1 == this.amount2);
    }
    
// tag-test
    @Then("^the number difference is at least \"([^\"]*)\" less than earlier$")
    public void the_number_difference_is_at_least_less_than_earlier(String ero) throws Throwable {
        assertTrue((this.amount1 - this.amount2) >= Integer.parseInt(ero));
    }

    @Then("^file is created by name \"([^\"]*)\"$")
    public void file_is_created_by_name(String fileName) throws Throwable {
        assertTrue(driver.getPageSource().contains("fileName"));

    }

    @Then("^system will not respond with \"([^\"]*)\"$")
    public void system_will_not_respond_with(String content) throws Throwable {
        pageHasNotContent(content);
    }

    @Then("^system will respond with \"([^\"]*)\"$")
    public void system_will_respond_with(String anyString) throws Throwable {
        Thread.sleep(4000);
        pageHasContent(anyString);
    }

    @Then("^book reference with data \"([^\"]*)\" is displayd in the list$")
    public void book_reference_with_data_is_displayd_in_the_list(String searchdata) throws Throwable {
        system_will_respond_with(searchdata);
    }

// this method is true, if page has not the given text as a parameter
    private void pageHasNotContent(String content) throws InterruptedException {
        Thread.sleep(4000);
        assertTrue(!driver.getPageSource().contains(content));
    }

// This method is true, if page has the given text as a parameter    
    private void pageHasContent(String content) throws InterruptedException {
        Thread.sleep(4000);
        assertTrue(driver.getPageSource().contains(content));
    }

    private void popup(Boolean accept) {
        long timeout = 5000;
        long waitForAlert = System.currentTimeMillis() + timeout;
        boolean boolFound = false;
        do {
            try {
                Alert alert = this.driver.switchTo().alert();
                if (alert != null) {
                    if (accept) {
                        alert.accept();
                    }// OK is accepted from the popup
                    else {
                        alert.dismiss();
                    } // Cancel is accepted from the popup
                    boolFound = true;
                }
            } catch (NoAlertPresentException ex) {
            }
        } while ((System.currentTimeMillis() < waitForAlert) && (!boolFound));

    }

    private void updateField(WebElement element, String data) {
        element.sendKeys("");
        element.sendKeys(data);
    }

    // this method fills the book form with mandatory fields and submits.
    private void updateBook(String key, String author, String title, String year, String publisher, String address, String series, String edition, String month, String volume, String tags) throws InterruptedException {
        WebElement element = driver.findElement(By.name("key"));
        updateField(element, key);
        element = driver.findElement(By.name("authors"));
        updateField(element, author);
        element = driver.findElement(By.name("title"));
        updateField(element, title);
        element = driver.findElement(By.name("year"));
        updateField(element, year);
        element = driver.findElement(By.name("publisher"));
        updateField(element, publisher);
        element = driver.findElement(By.name("address"));
        updateField(element, address);
        element = driver.findElement(By.name("series"));
        updateField(element, series);
        element = driver.findElement(By.name("edition"));
        updateField(element, edition);
        element = driver.findElement(By.name("month"));
        updateField(element, month);
        element = driver.findElement(By.name("volume"));
        updateField(element, volume);
        element = driver.findElement(By.name("tags"));
        updateField(element, tags);

        driver.findElement(By.name("save")).submit();

    }

    // all fields included - if missing, different message given
    public void updateArticle(String key, String author, String title, String year,
            String journal, String publisher, String volume, String number, String startingPage, String endingPage, String month, String address, String tags) {
        WebElement element = driver.findElement(By.name("key"));
        updateField(element, key);
        element = driver.findElement(By.name("authors"));
        updateField(element, author);
        element = driver.findElement(By.name("title"));
        updateField(element, title);
        element = driver.findElement(By.name("year"));
        updateField(element, year);
        element = driver.findElement(By.name("journal"));
        updateField(element, journal);
        element = driver.findElement(By.name("publisher"));
        updateField(element, publisher);
        element = driver.findElement(By.name("volume"));
        updateField(element, volume);
        element = driver.findElement(By.name("number"));
        updateField(element, number);
        element = driver.findElement(By.name("startingPage"));
        updateField(element, startingPage);
        element = driver.findElement(By.name("endingPage"));
        updateField(element, endingPage);
        element = driver.findElement(By.name("month"));
        updateField(element, month);
        element = driver.findElement(By.name("address"));
        updateField(element, address);
        element = driver.findElement(By.name("tags"));
        updateField(element, tags);

        driver.findElement(By.name("save")).submit();
    }

    public void updateInproceedings(String key, String author, String title, String year, String publisher, String editor, String booktitle, String address, String series, String startingPage, String endingPage, String month, String organization, String tags) {
        WebElement element = driver.findElement(By.name("key"));
        updateField(element, key);
        element = driver.findElement(By.name("authors"));
        updateField(element, author);
        element = driver.findElement(By.name("title"));
        updateField(element, title);
        element = driver.findElement(By.name("year"));
        updateField(element, year);
        element = driver.findElement(By.name("publisher"));
        updateField(element, publisher);
        element = driver.findElement(By.name("editor"));
        updateField(element, editor);
        element = driver.findElement(By.name("bookTitle"));
        updateField(element, booktitle);
        element = driver.findElement(By.name("address"));
        updateField(element, address);
        element = driver.findElement(By.name("series"));
        updateField(element, series);
        element = driver.findElement(By.name("startingPage"));
        updateField(element, startingPage);
        element = driver.findElement(By.name("endingPage"));
        updateField(element, endingPage);
        element = driver.findElement(By.name("month"));
        updateField(element, month);
        element = driver.findElement(By.name("organization"));
        updateField(element, organization);
        element = driver.findElement(By.name("tags"));
        updateField(element, tags);

        driver.findElement(By.name("save")).submit();
    }

    @After
    public void tearDown() {
        driver.quit();
    }

}
