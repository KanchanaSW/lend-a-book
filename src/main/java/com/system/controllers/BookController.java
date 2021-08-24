package com.system.controllers;

import com.system.models.Book;
import com.system.payload.response.MessageResponse;
import com.system.security.services.ImplBookService;
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
@RequestMapping("/api/items")
public class BookController {

    @Autowired
   private ImplBookService bookService;

  @RequestMapping(method = RequestMethod.GET,value = "/books/count")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> countAllbooks(){
        long x=bookService.countAll();
        return ResponseEntity.status(HttpStatus.ACCEPTED).body("Count is : "+x);
    }

   //get single book details
    @RequestMapping(method = RequestMethod.GET,value = "/viewBook/{isbn}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> getBookDetails(@PathVariable long isbn){
        if (bookService.existsBookISBN(isbn)){
           Optional<Book> singleBook=bookService.findByIsbn(isbn);
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(singleBook);
        }else {
            return ResponseEntity.badRequest().body(
                    new MessageResponse("Error : book don't exists with the ISBN : "+isbn)
            );
        }
    }

    //search by title.
    @RequestMapping(method = RequestMethod.GET,value = "/search/{title}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> searchBookByTitle(@PathVariable String title){
        if (bookService.existsBookTitle(title)){
            //Optional<Book> dd=bookService.findByTitle(title);
            List<Book> dd=bookService.findByTitle(title);
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(dd);
        }else {
            return ResponseEntity.badRequest().body(
                    new MessageResponse("Error : book don't exists with the title : "+title)
            );
        }
    }

    @RequestMapping(method = RequestMethod.GET,value = "/books")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<Book>> getBookList(){
        List<Book> list=bookService.getALl();
        return new ResponseEntity<List<Book>>(list,HttpStatus.OK);
    }


    @RequestMapping(method = RequestMethod.POST,value = "/addBook")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> addBook(@RequestBody Book newBook){
        long id=newBook.isbn;
          if(bookService.existsBookISBN(id)){
            return ResponseEntity.badRequest().body(
                    new MessageResponse("Error : Book already exists with the same ISBN"));
        }
         bookService.addBook(newBook);
        return ResponseEntity.ok(new MessageResponse("Success : Book added!!!"));
    }

    //update drug details here.
    @RequestMapping(method = RequestMethod.PUT,value = "/updateBook")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> updateBook(@Valid @RequestBody Book book){
       if (bookService.existsBookISBN(book.getIsbn())) {
            bookService.updateBook(book);
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(book);
        }else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(book);
        }
    }

    //delete
    @RequestMapping(method = RequestMethod.DELETE,value = "/deleteBook/{isbn}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deleteBook(@PathVariable long isbn){
        if (bookService.existsBookISBN(isbn)){
            bookService.deleteBook(isbn);
            return ResponseEntity.ok(new MessageResponse("Success: Book deleted!!"));
        }else {
            return ResponseEntity.badRequest().body(new MessageResponse("Error : no book with this isbn : "+isbn));
        }
    }



}
