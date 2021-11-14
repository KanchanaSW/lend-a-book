package com.system.security.services;

import com.system.DTO.BookDTO;
import com.system.models.Book;
import com.system.models.Quantity;
import com.system.repository.BookRepository;
import com.system.repository.QuantityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private QuantityRepository quantityRepository;

    public List<Book> get(List<Integer> ids){
        return bookRepository.findAllById(ids);
    }

    public List<Book> getALlBooks() {
        List<Book> list=new ArrayList<>();
        bookRepository.findAll().forEach(e->list.add(e));
        return list;
    }

    public boolean existsIsbn(Long isbn){
        return bookRepository.existsByIsbn(isbn);
    }
    public boolean existsId(Integer id){
        return bookRepository.existsById(id);
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
            book.setStatus(bookDTO.getStatus());
            book.setCoverPage(bookDTO.getCoverPage());
            book.setSummary(bookDTO.getSummary());
            bookRepository.save(book);
            //add the quantity here
            Book book1=bookRepository.findByIsbn(bookDTO.getIsbn());
            Quantity quantity =new Quantity();
            quantity.setNoOfCopies(bookDTO.getNoOfCopies());
            quantity.setBook(book1);
            //quantity.setMovie();
            quantityRepository.save(quantity);
            return book;

        }catch (Exception e){
            return null;
        }finally {

        }
    }
    public long countAllBooks(){
       return bookRepository.count();
    }
    public Book findBook(Integer id){
        Optional<Book> book=bookRepository.findById(id);
        Book b=null;
        if (book.isPresent()){
            b=book.get();
        }
        return b;
    }
    public Book findBookByIsbn(Long isbn){
        return bookRepository.findByIsbn(isbn);
    }
    public Book findBookTitle(String title){
        Book book=bookRepository.findByTitle(title);
       return book;
    }
    public Book updateBook(BookDTO bookDTO){
        try{
            Book book = bookRepository.findByIsbn(bookDTO.getIsbn());
            //book.setId(book.getId());
            book.setIsbn(bookDTO.getIsbn());
            book.setTitle(bookDTO.getTitle());
            book.setAuthor(bookDTO.getAuthor());
            book.setPublisher(bookDTO.getPublisher());
            book.setStatus(bookDTO.getStatus());
            book.setCoverPage(bookDTO.getCoverPage());
            book.setSummary(bookDTO.getSummary());

            bookRepository.save(book);
            Quantity quantity =quantityRepository.findByBookId(book.getId());
            quantity.setNoOfCopies(bookDTO.getNoOfCopies());
            quantity.setBook(book);
            //quantity.setMovie();
            quantityRepository.save(quantity);
            return book;
        }catch (Exception e){
            return null;
        }
    }
    public void deleteBook(Integer id){
        Quantity quantity =quantityRepository.findByBookId(id);
        quantityRepository.delete(quantity);
        bookRepository.deleteById(id);
    }

}



















