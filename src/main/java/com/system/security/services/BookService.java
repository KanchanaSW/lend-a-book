package com.system.security.services;

import com.system.DTO.BookDTO;
import com.system.models.Book;
import com.system.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;

    public List<Book> get(List<Long> ids){
        return bookRepository.findAllById(ids);
    }

    public List<Book> getALlBooks() {
        List<Book> list=new ArrayList<>();
        bookRepository.findAll().forEach(e->list.add(e));
        return list;
    }

    public boolean existsIsbn(Long isbn){
        return bookRepository.existsById(isbn);
    }
    public boolean existsTitle(String title){
        return bookRepository.existsByTitle(title);
    }
    public boolean existsAuthor(String author){
        return bookRepository.existsByAuthor(author);
    }

    public Book addBook(BookDTO bookDTO){
        try {
            Book book = new Book();
            book.setIsbn(bookDTO.getIsbn());
            book.setTitle(bookDTO.getTitle());
            book.setAuthor(bookDTO.getAuthor());
            book.setPublisher(bookDTO.getPublisher());
            book.setCopiesAvi(bookDTO.getCopiesAvi());
            book.setCoverPage(bookDTO.getCoverPage());

            bookRepository.save(book);
            return book;

        }catch (Exception e){
            return null;
        }
    }
    public long countAllBooks(){
       return bookRepository.count();
    }
    public Book findBook(long isbn){
        Optional<Book> book=bookRepository.findById(isbn);
        Book b=null;
        if (book.isPresent()){
            b=book.get();
        }
        return b;
    }
    public Book findBookTitle(String title){
        Book book=bookRepository.findByTitle(title);
       return book;
    }
    public Book updateBook(BookDTO bookDTO){
        try{
            Book book = bookRepository.getById(bookDTO.getIsbn());
            book.setTitle(bookDTO.getTitle());
            book.setAuthor(bookDTO.getAuthor());
            book.setPublisher(bookDTO.getPublisher());
            book.setCopiesAvi(bookDTO.getCopiesAvi());
            book.setCoverPage(bookDTO.getCoverPage());

            bookRepository.save(book);
            return book;
        }catch (Exception e){
            return null;
        }
    }
    public void deleteBook(long isbn){
        bookRepository.deleteById(isbn);
    }

}



















