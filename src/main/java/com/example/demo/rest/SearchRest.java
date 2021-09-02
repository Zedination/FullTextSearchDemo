package com.example.demo.rest;

import com.example.demo.dao.FullTextSearchDemo;
import com.example.demo.entities.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class SearchRest {

    @Autowired
    private FullTextSearchDemo demo;

    @PostMapping("/insert-book")
    public String insertBook(@RequestParam("id") Integer id, @RequestParam("title") String title,
                             @RequestParam("author") String author, @RequestParam("description") String description) {
        Book book = new Book();
        book.setBookId(id);
        book.setTitle(title);
        book.setAuthor(author);
        book.setDescription(description);
        demo.insertBook(book);
        return "success";
    }

    @GetMapping("/test")
    public List<Book> search_book(@RequestParam("q") String keyWord) {
        return demo.searchBook(keyWord);
    }

    @GetMapping("/indexed")
    public String indexedBook() throws Exception {
        demo.indexBooks();
        return "success";
    }
}
