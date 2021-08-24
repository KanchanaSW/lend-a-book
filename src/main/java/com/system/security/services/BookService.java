package com.system.security.services;

import com.system.models.Book;
import com.system.repository.CrudBookRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class BookService implements ImplBookService{

    @Autowired
    private CrudBookRepo crudBookRepo;

   @Override
    public List<Book> getALl() {
        List<Book> list=new ArrayList<>();
        crudBookRepo.findAll().forEach(e->list.add(e));
        return list;
    }
   @Override
    public Boolean existsBookISBN(long isbn) {
        if(crudBookRepo.existsById(isbn)){
            return true;
        }else{
            return false;
        }
    }
    @Override
    public void addBook(Book book) {
        crudBookRepo.save(book);
    }

    @Override
    public Optional<Book> findByIsbn(long isbn) {
        Optional<Book> dd=crudBookRepo.findByIsbn(isbn);
        return dd;
    }

    @Override
    public List<Book> findByTitle(String title) {
        List<Book> dd=crudBookRepo.findByTitle(title);
        return dd;
    }

    @Override
    public Boolean existsBookTitle(String title) {
        if(crudBookRepo.existsByTitle(title)){
            return true;
        }else{
            return false;
        }
    }

    @Override
    public void updateBook(Book book) {
        crudBookRepo.save(book);
    }

    @Override
    public void deleteBook(long isbn) {
        crudBookRepo.deleteById(isbn);
    }

    @Override
    public long countAll() {
        return crudBookRepo.count();
    }
}
