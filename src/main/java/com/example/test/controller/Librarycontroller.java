package com.example.test.controller;

import com.example.test.exception.IdNotFoundException;
import com.example.test.model.Book;
import com.example.test.model.User;
import com.example.test.repository.BookRepository;
import com.example.test.repository.UserRepository;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.swing.text.html.Option;
import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequestMapping
public class Librarycontroller {
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private UserRepository userRepository;
    @PostMapping("/addbook")
    public ResponseEntity<Book> create(@RequestBody @Valid Book book){
        Book book1=new Book(0L,book.getName());
        bookRepository.save(book);
        return ResponseEntity.ok().body(book1);
    }
    @PostMapping("/adduser")
    public ResponseEntity<User> createUser(@RequestBody User user){
        User user1=new User(0L,user.getName());
        userRepository.save(user);
        return ResponseEntity.ok().body(user1);
    }
    @GetMapping("/book/{id}")
    public ResponseEntity<Book> getBook(@PathVariable Long id) throws IdNotFoundException{
        Book book =bookRepository.findById(id).orElse(null);
        if(book==null){
            //return ResponseEntity.noContent().build();
            throw new IdNotFoundException("NO Id");
        }
        return ResponseEntity.ok().body(book);


    }
    @GetMapping("/user/{id}")
    public ResponseEntity<User> getUser(@PathVariable Long id){
        User user = userRepository.findById(id).orElse(null);
        if(user==null){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok().body(user);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Book> updateDetails(@RequestBody @Valid Book book, @PathVariable long id) throws IdNotFoundException{
        Book buk = null;
        Optional<Book> updatebuk = bookRepository.findById(id);
        if (updatebuk.isPresent()) {
            buk = updatebuk.get();
            buk.setId(id);
            buk.setName(buk.getName());
            bookRepository.save(buk);
            return ResponseEntity.ok().body(buk);
        } else {
            throw new IdNotFoundException("NO Id");
            //return ResponseEntity.noContent().build();
        }
    }

        @DeleteMapping("/delete/{id}")
        public ResponseEntity<Boolean> deleteBook(@PathVariable long id){
        Optional<Book>buk = bookRepository.findById(id);
        if(buk.isEmpty()){
            return ResponseEntity.ok(false);
        }else{
            bookRepository.deleteById(id);
            return ResponseEntity.ok(true);
        }


    }}

