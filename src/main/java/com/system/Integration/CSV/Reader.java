package com.system.Integration.CSV;

import com.system.models.Book;
import com.system.security.services.BookService;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Service
public class Reader {

    @Autowired
    private BookService bookService;

    public List<Book> csvReadNew(){
        List<Book> books=new ArrayList<>();
        try {
            BufferedReader reader = Files.newBufferedReader(Paths.get("C:\\Users\\KanchanaSW\\IdeaProjects\\L-MS\\src\\main\\java\\com\\system\\Integration\\CSV\\Books.csv"));

            // read csv file
            Iterable<CSVRecord> records = CSVFormat.DEFAULT.withHeader("Id","Author","CoverPage","ISBN","Publisher","Status","Summary","Title","NoOfCopies").parse(reader);
            for (CSVRecord record : records) {
                if (bookService.existsTitle(record.get("Title"))){
                    System.out.println(record.get("Title"));
                }else {
                    Book book = new Book();
                    book.setId(Integer.parseInt(record.get("Id")));
                    book.setAuthor(record.get("Author"));
                    book.setCoverPage(record.get("CoverPage"));
                    book.setIsbn(Long.parseLong(record.get("ISBN")));
                    book.setPublisher(record.get("Publisher"));
                    book.setStatus(record.get("Status"));
                    book.setSummary(record.get("Summary"));
                    book.setTitle(record.get("Title"));
                    book.setNoOfCopies(Integer.parseInt(record.get("NoOfCopies")));
                    books.add(book);
                }
            }

            // close the reader
            reader.close();

        } catch (IOException ex) {
            ex.printStackTrace();
        }
        System.out.println(books.size());
        return books;
    }

    public List<Book> csvReadExists(){
        List<Book> books=new ArrayList<>();
        try {
            BufferedReader reader = Files.newBufferedReader(Paths.get("C:\\Users\\KanchanaSW\\IdeaProjects\\L-MS\\src\\main\\java\\com\\system\\Integration\\CSV\\Books.csv"));

            // read csv file
            Iterable<CSVRecord> records = CSVFormat.DEFAULT.withHeader("Id","Author","CoverPage","ISBN","Publisher","Status","Summary","Title","NoOfCopies").parse(reader);
            for (CSVRecord record : records) {
                if (bookService.existsTitle(record.get("Title"))){
                    Book book=new Book();
                    book.setId(Integer.parseInt(record.get("Id")));
                    book.setAuthor(record.get("Author"));
                    book.setCoverPage(record.get("CoverPage"));
                    book.setIsbn(Long.parseLong(record.get("ISBN")));
                    book.setPublisher(record.get("Publisher"));
                    book.setStatus(record.get("Status"));
                    book.setSummary(record.get("Summary"));
                    book.setTitle(record.get("Title"));
                    book.setNoOfCopies(Integer.parseInt(record.get("NoOfCopies")));
                    books.add(book);
                }
                System.out.println(record.get("Title"));
            }

            // close the reader
            reader.close();

        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return books;
    }

    public Book csvReadById(int bookId){
        Book book = new Book();
        try {
            BufferedReader reader = Files.newBufferedReader(Paths.get("C:\\Users\\KanchanaSW\\IdeaProjects\\L-MS\\src\\main\\java\\com\\system\\Integration\\CSV\\Books.csv"));

            // read csv file
            Iterable<CSVRecord> records = CSVFormat.DEFAULT.withHeader("Id","Author","CoverPage","ISBN","Publisher","Status","Summary","Title","NoOfCopies").parse(reader);
            for (CSVRecord record : records) {
                int id = Integer.parseInt(record.get("Id"));
                if (bookId == id){
                    book.setId(id);
                    book.setAuthor(record.get("Author"));
                    book.setCoverPage(record.get("CoverPage"));
                    book.setIsbn(Long.parseLong(record.get("ISBN")));
                    book.setPublisher(record.get("Publisher"));
                    book.setStatus(record.get("Status"));
                    book.setSummary(record.get("Summary"));
                    book.setTitle(record.get("Title"));
                    book.setNoOfCopies(Integer.parseInt(record.get("NoOfCopies")));
                }

            }

            // close the reader
            reader.close();

        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return book;
    }

    public void removeLine(String lineContent) throws IOException
    {
        File file = new File("C:\\Users\\KanchanaSW\\IdeaProjects\\L-MS\\src\\main\\java\\com\\system\\Integration\\CSV\\Books.csv");
        File temp = new File("C:\\Users\\KanchanaSW\\IdeaProjects\\L-MS\\src\\main\\java\\com\\system\\Integration\\CSV\\tempBooks.csv");
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
