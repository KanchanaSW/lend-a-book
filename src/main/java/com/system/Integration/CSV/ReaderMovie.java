package com.system.Integration.CSV;

import com.system.models.Book;
import com.system.models.Movie;
import com.system.security.services.MovieService;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Service
public class ReaderMovie {
    @Autowired
    private MovieService movieService;

    public List<Movie> csvReadMovieNew(){
        List<Movie> movies=new ArrayList<>();
        try {
            BufferedReader reader = Files.newBufferedReader(Paths.get("C:\\Users\\KanchanaSW\\IdeaProjects\\L-MS\\src\\main\\java\\com\\system\\Integration\\CSV\\Movies.csv"));

            // read csv file
            Iterable<CSVRecord> records = CSVFormat.DEFAULT.withHeader("movieId","description","image","length","noOfCopies","r18","status","title").parse(reader);
            for (CSVRecord record : records) {
                Movie m=new Movie();
                if (movieService.existsByTitle(record.get("title"))) {
                    System.out.println(record.get("title"));
                }else {
                    m.setMovieId(Integer.valueOf(record.get("movieId")));
                    m.setDescription(record.get("description"));
                    m.setImage(record.get("image"));
                    m.setLength(record.get("length"));
                    m.setNoOfCopies(Integer.valueOf(record.get("noOfCopies")));
                    m.setR18(Boolean.parseBoolean(record.get("r18").toLowerCase(Locale.ROOT)));
                    m.setStatus(record.get("status"));
                    m.setTitle(record.get("title"));
                    movies.add(m);
                }
            }

            // close the reader
            reader.close();

        } catch (IOException ex) {
            ex.printStackTrace();
        }
        System.out.println(movies.size());
        return movies;
    }

    public List<Movie> csvReadMovieExists(){
        List<Movie> movies=new ArrayList<>();
        try {
            BufferedReader reader = Files.newBufferedReader(Paths.get("C:\\Users\\KanchanaSW\\IdeaProjects\\L-MS\\src\\main\\java\\com\\system\\Integration\\CSV\\Movies.csv"));

            // read csv file
            Iterable<CSVRecord> records = CSVFormat.DEFAULT.withHeader("movieId","description","image","length","noOfCopies","r18","status","title").parse(reader);
            for (CSVRecord record : records) {
                Movie m=new Movie();
                if (movieService.existsByTitle(record.get("title"))){
                    m.setMovieId(Integer.valueOf(record.get("movieId")));
                    m.setDescription(record.get("description"));
                    m.setImage(record.get("image"));
                    m.setLength(record.get("length"));
                    m.setNoOfCopies(Integer.valueOf(record.get("noOfCopies")));
                    m.setR18(Boolean.parseBoolean(record.get("r18").toLowerCase(Locale.ROOT)));
                    m.setStatus(record.get("status"));
                    m.setTitle(record.get("title"));
                    movies.add(m);
                }

            }

            // close the reader
            reader.close();

        } catch (IOException ex) {
            ex.printStackTrace();
        }
        System.out.println(movies.size());
        return movies;
    }

    public Movie csvReadMovieFindById(int id){
        Movie m=new Movie();
        try {
            BufferedReader reader = Files.newBufferedReader(Paths.get("C:\\Users\\KanchanaSW\\IdeaProjects\\L-MS\\src\\main\\java\\com\\system\\Integration\\CSV\\Movies.csv"));

            // read csv file
            Iterable<CSVRecord> records = CSVFormat.DEFAULT.withHeader("movieId","description","image","length","noOfCopies","r18","status","title").parse(reader);
            for (CSVRecord record : records) {
               int movieId=Integer.parseInt(record.get("movieId"));

                if (movieId == id){
                    m.setMovieId(Integer.valueOf(record.get("movieId")));
                    m.setDescription(record.get("description"));
                    m.setImage(record.get("image"));
                    m.setLength(record.get("length"));
                    m.setNoOfCopies(Integer.valueOf(record.get("noOfCopies")));
                    m.setR18(Boolean.parseBoolean(record.get("r18").toLowerCase(Locale.ROOT)));
                    m.setStatus(record.get("status"));
                    m.setTitle(record.get("title"));
                }

            }

            // close the reader
            reader.close();

        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return m;
    }
    public void removeLine(String lineContent) throws IOException
    {
        File file = new File("C:\\Users\\KanchanaSW\\IdeaProjects\\L-MS\\src\\main\\java\\com\\system\\Integration\\CSV\\Movies.csv");
        File temp = new File("C:\\Users\\KanchanaSW\\IdeaProjects\\L-MS\\src\\main\\java\\com\\system\\Integration\\CSV\\tempMovies.csv");
        PrintWriter out = new PrintWriter(new FileWriter(temp));
        Files.lines(file.toPath())
                .filter(line -> !line.contains(lineContent))
                .forEach(out::println);
        out.flush();
        out.close();
        //Delete the original file
        if (!file.delete()) {
            System.out.println("Could not delete file");
            return;
        }
        if (!temp.renameTo(file))
            System.out.println("Could not rename file");
        System.out.println("done");
    }
}
