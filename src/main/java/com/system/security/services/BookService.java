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


    public List<Book> get(List<Integer> ids){
        return bookRepository.findAllById(ids);
    }

    public List<Book> getALlBooks() {
        List<Book> list=new ArrayList<>();
        bookRepository.findAll().forEach(e->list.add(e));
        return list;
    }
    //list all distint books
    public List<Book> getAllUniqueBooks(){
        List<Book> list=new ArrayList<>();
        bookRepository.findAll().forEach(e-> {
            List<Book> l=bookRepository.findAllByTitle(e.getTitle());
            list.add(l.get(0));
        });
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
            book.setNoOfCopies(bookDTO.getNoOfCopies());
            bookRepository.save(book);

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
            book.setNoOfCopies(bookDTO.getNoOfCopies());

           bookRepository.save(book);

            return book;
        }catch (Exception e){
            return null;
        }
    }
    public void deleteBook(Integer id){

        bookRepository.deleteById(id);
    }
    public Book save(Book book) {
        return bookRepository.save(book);
    }
}



















