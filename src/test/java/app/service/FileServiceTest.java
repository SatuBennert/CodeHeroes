package app.service;

import app.domain.Book;
import app.domain.FileObject;
import app.domain.Reference;
import java.util.ArrayList;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.runner.RunWith;
import static org.mockito.Mockito.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class FileServiceTest {

    @Autowired
    FileService service;
    @Autowired
    LetterChecker letterChecker;

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {

    }

    @Test
    public void referenceCallesToBibtex() {

        String book3 = "@book{" + "K2017" + ",\n";
        book3 = book3 + "author = {" + "Kääpä" + "},\n";
        book3 = book3 + "title = {" + "Yö" + "},\n";
        book3 = book3 + "year = {" + "2017" + "},\n";
        book3 = book3 + "publisher = {" + "Otava" + "},\n";
        book3 = book3 + "}";

        List<Reference> lista = new ArrayList<Reference>();
        Book mockBook = mock(Book.class);
        lista.add(mockBook);
        when(mockBook.toBibTex()).thenReturn(book3);

        String fileName = "nimi";

        service.createFile(lista, fileName);

        verify(mockBook).toBibTex();
    }

}
