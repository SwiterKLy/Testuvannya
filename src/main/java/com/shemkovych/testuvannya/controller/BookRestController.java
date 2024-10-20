package com.shemkovych.testuvannya.controller;

import com.shemkovych.testuvannya.model.Book;
import com.shemkovych.testuvannya.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
@RestController
@RequestMapping("api/v1/books")
@RequiredArgsConstructor
public class BookRestController {
    private final BookService bookService;

    @RequestMapping("/hello")
    public String hello() {
        return "<h1> !!!!!!! hello   !!!!!!!!!!!!!!!! <h1>" ;
    }

    @RequestMapping("/")
    public List<Book> showAll() {
        return bookService.getBooks();
    }

    @RequestMapping("/id")
    public Book showOne() {
        return null;
    }

    }