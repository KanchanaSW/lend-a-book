package com.system.Integration.Controller;

import com.system.Integration.CSV.Reader;
import com.system.models.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("api/csv/")
public class CSVController {

    @Autowired
    private Reader reader;

    @RequestMapping(method = RequestMethod.GET, value = "/csvBooks")
    public List<Book> bookListNew(){
        return reader.csvReadNew();
    }
    @RequestMapping(method = RequestMethod.GET, value = "/csvBooksExists")
    public List<Book> bookListExits(){
        return reader.csvReadExists();
    }
    @RequestMapping(method = RequestMethod.GET, value = "/csvBook/{bookId}")
    public Book csvBook(@PathVariable Integer bookId){
        return reader.csvReadById(bookId);
    }
}
