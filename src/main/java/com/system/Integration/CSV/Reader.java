package com.system.Integration.CSV;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.springframework.stereotype.Service;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;

@Service
public class Reader {

    public static void main(String args[]) throws Exception {

       try {
            //C:\Users\KanchanaSW\IdeaProjects\L-MS\src\main\java\com\system\Integration\CSV\Books.csv
            BufferedReader reader = Files.newBufferedReader(Paths.get("C:\\Users\\KanchanaSW\\IdeaProjects\\L-MS\\src\\main\\java\\com\\system\\Integration\\CSV\\Books.csv"));

            // read csv file
            Iterable<CSVRecord> records = CSVFormat.DEFAULT.withHeader("Id","Author","CoverPage","ISBN","Publisher","Status","Summary","Title","NoOfCopies").parse(reader);
            for (CSVRecord record : records) {
                System.out.println("Record #: " + record.getRecordNumber());
                System.out.println("ID: " + record.get("Id"));
                System.out.println("Author: " + record.get("Author"));
                System.out.println("CoverPage: " + record.get("CoverPage"));
                System.out.println("ISBN: " + record.get("ISBN"));
                System.out.println("Publisher: " + record.get("Publisher"));
                System.out.println("Status: " + record.get("Status"));
                System.out.println("Summary: " + record.get("Summary"));
                System.out.println("Title: " + record.get("Title"));
                System.out.println("NoOfCopies: " + record.get("NoOfCopies"));
            }

            // close the reader
            reader.close();

        } catch (IOException ex) {
            ex.printStackTrace();
        }


    }
}
