package com.system.controllers;

import com.system.DTO.BookDTO;
import com.system.models.Book;
import com.system.payload.response.MessageResponse;
import com.system.security.services.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "*",maxAge = 3600)
@RestController
@RequestMapping("/api/books")
public class BookController {

    @Autowired
    private BookService bookService;

    @RequestMapping(value = "/addBook")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> addBook(@RequestBody BookDTO newBook){
        try {
            if (bookService.existsIsbn(newBook.getIsbn())) {
                return ResponseEntity.badRequest().body(
                        new MessageResponse("Error : Book already exists with the same ISBN"));
            } else if (bookService.existsTitle(newBook.getTitle())) {
                return ResponseEntity.badRequest().body(
                        new MessageResponse("Error : Book already exists with the same Title"));
            } else {
                bookService.addBook(newBook);
                return ResponseEntity.ok(new MessageResponse("Success : Book added!!!"));
            }
        }catch (Exception e){
            return ResponseEntity.badRequest().body(new MessageResponse("error occcured"+e));
        }
    }

    @RequestMapping(method = RequestMethod.GET,value = "/books")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> getAllBookList(){
        try {
            List<Book> list = bookService.getALlBooks();
            return ResponseEntity.ok().body(list);
        }catch (Exception e){
            return ResponseEntity.badRequest().body(new MessageResponse("error occcured get all books"+e));
        }
    }

  @RequestMapping(method = RequestMethod.GET,value = "/books/count")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> countAllbooks(){
        long x=bookService.countAllBooks();
        return ResponseEntity.status(HttpStatus.ACCEPTED).body("Count is : "+x);
    }

   //get single book details
    @RequestMapping(method = RequestMethod.GET,value = "/book/{isbn}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> getBookDetails(@PathVariable long isbn){
        if (bookService.existsIsbn(isbn)){
           Book book= bookService.findBook(isbn);
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(book);
        }else {
            return ResponseEntity.badRequest().body(
                    new MessageResponse("Error : book don't exists with the ISBN : "+isbn)
            );
        }
    }
    //get book by title.
    @RequestMapping(method = RequestMethod.GET,value = "/bookTitle/{title}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> searchBookByTitle(@PathVariable String title){
        if (bookService.existsTitle(title)){
          Book book= bookService.findBookTitle(title);
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(book);
        }else {
            return ResponseEntity.badRequest().body(
                    new MessageResponse("Error : book don't exists with the title : "+title)
            );
        }
    }
    //update book details here.
    @RequestMapping(method = RequestMethod.PUT,value = "/updateBook")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> updateBook(@Valid @RequestBody BookDTO book){
       try {
           if (bookService.existsIsbn(book.getIsbn())) {
               bookService.updateBook(book);
               return ResponseEntity.status(HttpStatus.ACCEPTED).body(book);
           } else {
               return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                       new MessageResponse("Error : book dont exists with isbn :" + book.getIsbn())
               );
           }
       }catch (Exception e){
           return ResponseEntity.badRequest().body(new MessageResponse("error occcured"+e));
       }
    }

    //delete book
    @RequestMapping(method = RequestMethod.DELETE,value = "/deleteBook/{isbn}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deleteBook(@PathVariable long isbn){
        if (bookService.existsIsbn(isbn)){
            bookService.deleteBook(isbn);
            return ResponseEntity.ok(new MessageResponse("Success: Book deleted!!"));
        }else {
            return ResponseEntity.badRequest().body(new MessageResponse("Error : no book with this isbn : "+isbn));
        }
    }


}
