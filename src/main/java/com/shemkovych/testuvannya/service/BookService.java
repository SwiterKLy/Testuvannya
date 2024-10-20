package com.shemkovych.testuvannya.service;

import com.shemkovych.testuvannya.model.Book;
import com.shemkovych.testuvannya.repository.BookRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BookService {

    private final BookRepository BookRepository;

    private List<Book> Books = new ArrayList<>();
    {
        Books.add(new Book("1", "name1", "000001","description1"));
        Books.add(new Book("2", "name2", "000002","description3"));
        Books.add(new Book("3", "name3", "000003","description3"));
    }

    //  @PostConstruct
    void init() {
        BookRepository.saveAll(Books);
    }

    public List<Book> getBooks() {
        return BookRepository.findAll();
    }
    //  CRUD   - create read update delete


}
