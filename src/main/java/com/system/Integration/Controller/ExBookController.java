package com.system.Integration.Controller;

import com.system.Integration.Models.ExBook;
import com.system.Integration.Models.ExMovie;
import com.system.Integration.Services.ExService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("api/external/")
public class ExBookController {

    @Autowired
    private ExService exService;

    @GetMapping(value = "/books")
    public List<ExBook> exBooks(){return exService.all();}

    @GetMapping(value = "books/{title}")
    public List<ExBook> searchByTitle(@PathVariable(name = "title")String title){
        return exService.seachBook(title);
    }

    @GetMapping(value = "/movies")
    public List<ExMovie> exMovies(){return exService.allMovie();}

    @GetMapping(value = "movies/{title}")
    public List<ExMovie> seByTitleMovie(@PathVariable(name = "title")String title){
        return exService.searchMovie(title);
    }
}
