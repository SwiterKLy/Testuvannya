package com.shemkovych.testuvannya;

import com.shemkovych.testuvannya.model.Book;
import com.shemkovych.testuvannya.repository.BookRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@DataMongoTest
public class RepositoryTest {

    @Autowired
    BookRepository underTest;

    @BeforeAll
    void beforeAll() {}

    @BeforeEach
    void setUp() {
        Book orwell = new Book("1", "1984", "George Orwell", "###test");
        Book tolkien = new Book("2", "The Hobbit", "J.R.R. Tolkien", "###test");
        Book rowling = new Book("3", "Harry Potter and the Philosopher's Stone", "J.K. Rowling", "###test");
        underTest.saveAll(List.of(orwell, tolkien, rowling));
    }

    @AfterEach
    void tearDown() {
        List<Book> booksToDelete = underTest.findAll().stream()
                .filter(book -> book.getDescription().contains("###test"))
                .toList();
        underTest.deleteAll(booksToDelete);
    }

    @AfterAll
    void afterAll() {}

    @Test
    void testSetShouldContains_3_Records_ToTest() {
        List<Book> booksToDelete = underTest.findAll().stream()
                .filter(book -> book.getDescription().contains("###test"))
                .toList();
        assertEquals(3, booksToDelete.size());
    }

    @Test
    void shouldGiveIdForNewRecord() {
        // given
        Book newBook = new Book("The Catcher in the Rye", "J.D. Salinger", "###test");
        // when
        underTest.save(newBook);
        Book bookFromDb = underTest.findAll().stream()
                .filter(book -> book.getName().equals("The Catcher in the Rye"))
                .findFirst().orElse(null);
        // then
        assertNotNull(bookFromDb);
        assertNotNull(bookFromDb.getId());
        assertFalse(bookFromDb.getId().isEmpty());
        assertEquals(23, bookFromDb.getId().length());
    }

    @Test
    void shouldDeleteBookById() {
        // given
        Book toDelete = underTest.findAll().stream()
                .filter(book -> book.getName().equals("1984"))
                .findFirst().orElse(null);
        // when
        assertNotNull(toDelete);
        underTest.deleteById(toDelete.getId());
        Book deletedBook = underTest.findById(toDelete.getId()).orElse(null);
        // then
        assertNull(deletedBook);
    }

    @Test
    void shouldFindBooksByName() {
        // given
        String name = "Harry Potter and the Philosopher's Stone";
        // when
        List<Book> booksByName = underTest.findAll().stream()
                .filter(book -> book.getName().equals(name))
                .toList();
        // then
        assertEquals(1, booksByName.size());
        assertEquals(name, booksByName.get(0).getName());
    }

    @Test
    void shouldUpdateBookDescription() {
        // given
        Book toUpdate = underTest.findAll().stream()
                .filter(book -> book.getName().equals("The Hobbit"))
                .findFirst().orElse(null);
        assertNotNull(toUpdate);
        toUpdate.setDescription("An epic fantasy adventure");
        // when
        underTest.save(toUpdate);
        Book updatedBook = underTest.findById(toUpdate.getId()).orElse(null);
        // then
        assertNotNull(updatedBook);
        assertEquals("An epic fantasy adventure", updatedBook.getDescription());
    }
}
