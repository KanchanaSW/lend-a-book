package com.system.security.services;

import com.system.models.Book;
import com.system.models.IssuedBook;
import com.system.repository.IssuedBookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IssuedBookService {

    private IssuedBookRepository issuedBookRepository;

    @Autowired
    public IssuedBookService(IssuedBookRepository issuedBookRepository) {
        this.issuedBookRepository = issuedBookRepository;
    }

    public List<IssuedBook> getAll() {
        return issuedBookRepository.findAll();
    }

    public IssuedBook get(Long id) {
        return issuedBookRepository.findById(id).get();
    }

    public Long getCountByBook(Book book) {
        return issuedBookRepository.countByBookAndReturned(book, 0);
    }

    public IssuedBook save(IssuedBook issuedBook) {
        return issuedBookRepository.save(issuedBook);
    }

    public IssuedBook addNew(IssuedBook issuedBook) {
        issuedBook.setReturned( 0 );
        return issuedBookRepository.save(issuedBook);
    }
}
