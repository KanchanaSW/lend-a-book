package com.system.Integration.Services;

import com.system.Integration.Models.WebScrapingMovie;
import com.system.Integration.Models.WebScrapping;

import org.springframework.stereotype.Service;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class WebScrapingService {

    public List<WebScrapping> getWebScrapeBook() throws IOException {
        List<WebScrapping> webScrappingList = new ArrayList<>();

        Document document = Jsoup.
                connect("https://www.valorebooks.com/top-textbook-rentals").get();

        Elements body=document.select("div#main_rental_results");
        //System.out.println(body.select("div.result_listing").size());
        for (Element e : body.select("div.result_listing")) {

            String url=e.select("div.info > div.product_thumb a").attr("href");
            String bookLink = "https://www.valorebooks.com"+url;

            String coverImage = e.select("div.info > div.product_thumb > a img").attr("src");
            String bookTitle = e.select("div.info > div.product_thumb > a img").attr("alt");

            String bookAuthor = e.select("div.info > div.desc span.by").text();
            String price=e.select("div.price > a.rent_it").text();

            WebScrapping webScrapping = new WebScrapping
                    (coverImage, bookTitle, bookAuthor, bookLink,price);
            webScrappingList.add(webScrapping);

        }
        System.out.println(webScrappingList);
        return webScrappingList;
    }

    public List<WebScrapingMovie> getScrapedMovies() throws IOException {
        List<WebScrapingMovie> webScrappingList = new ArrayList<>();
        Document document = Jsoup.connect("https://vimeo.com/ondemand/discover/staffpicks").get();

        Elements links = document.select("ul.js-infinite_scroll_container "); // a with href
        for (Element e : links.select("li")) {
            String movieTitle = e.select("h3.vod_card_title").text();
            String tt = e.select("div.vod_card.js-vod_card a").attr("href");
            String movieLink = "https://vimeo.com/" + tt;
            String coverImage = e.select("div.img img").attr("src");
            String rentPrice = e.select("div.img > div.vod_card_poster_cover > div.vod_poster_cover_prices > div.vod_poster_cover_price i").attr("title");

            WebScrapingMovie webScrapping = new WebScrapingMovie
                    (coverImage, movieTitle, movieLink, rentPrice);
            if (!Objects.equals(webScrapping.getMovieTitle(), "")){
                webScrappingList.add(webScrapping);
            }
        }
        System.out.println("page 1");
        System.out.println(webScrappingList.size());
        // webScrappingList.clear();

        // Move to the next page
        Element page = document.select("p.txt_align_center a").first();
        Document pageDoc = Jsoup.connect(page.attr("abs:href")).get();
        Elements links2 = pageDoc.select("ul.js-infinite_scroll_container "); // a with href
        for (Element e : links2.select("li")) {
            String movieTitle = e.select("h3.vod_card_title").text();
            String tt = e.select("div.vod_card.js-vod_card a").attr("href");
            String movieLink = "https://vimeo.com/" + tt;
            String coverImage = e.select("div.img img").attr("src");
            String rentPrice = e.select("div.img > div.vod_card_poster_cover > div.vod_poster_cover_prices > div.vod_poster_cover_price i").attr("title");

            WebScrapingMovie webScrapping = new WebScrapingMovie
                    (coverImage, movieTitle, movieLink, rentPrice);
            if (!Objects.equals(webScrapping.getMovieTitle(), "")){
                webScrappingList.add(webScrapping);
            }
        }
        System.out.println("page 2");
        System.out.println(webScrappingList.size());
        return webScrappingList;
    }



/*    //movies
    //https://archive.org/details/movie_trailers?and[]=year%3A%222021%22
    public List<WebScrapingMovie> getScrapedMovies() throws IOException {
        List<WebScrapingMovie> webScrappingList = new ArrayList<>();
        //String url="https://archive.org/details/movie_trailers";
        String url="https://archive.org/details/televisionmovies";
        Document document = Jsoup.connect(url).get();

        for (Element e : document.select("div.results div.C234")) {
            String movieTitle = e.select("div.item-ttl.C.C2 a div.ttl").text();
            if (!movieTitle.contentEquals("")) {
                String link = e.select("div.item-ttl.C.C2 a").attr("href");
                String movieLink = "https://archive.org/" + link;//link
                String[] list = link.split("/");
                String coverImage = "https://archive.org/services/img/" + list[2];

                WebScrapingMovie webScrapping = new WebScrapingMovie
                        (coverImage, movieTitle, movieLink);
                webScrappingList.add(webScrapping);
            }

        }
        System.out.println(webScrappingList);
        return webScrappingList;

    }

    public List<WebScrapingMovie> getScrapedMovies2() throws IOException {
        List<WebScrapingMovie> webScrappingList = new ArrayList<>();
        Document document = Jsoup.connect("https://www.azmovies.net/popular-movies").get();
        String title = document.title(); //Get title
        System.out.println("  Title: " + title); //Print title.
        Elements links = document.select("ul.page-itemlist"); // a with href

        Elements imgs=document.select("img[src]");
        System.out.println(imgs.select("img[src]").attr("src"));

        for (Element e : links.select("li")) {
            String movieTitle=e.select("div.gridinfo h2 a").text();
            String movieLink = e.select("div.pagethumb a").attr("href");
            String coverImage = e.select("a img").attr("src");
            WebScrapingMovie webScrapping = new WebScrapingMovie
                    (coverImage, movieTitle, movieLink);
            webScrappingList.add(webScrapping);
        }
        System.out.println(webScrappingList);
        return webScrappingList;
    }*/
/*    public List<WebScrapping> getWebScrapeData() throws IOException {
        List<WebScrapping> webScrappingList = new ArrayList<>();
        Document document = Jsoup.
                connect("https://www.overdrive.com/subjects/young-adult-fiction").get();
        //https://www.overdrive.com/subjects/juvenile-fiction
        //https://www.overdrive.com/subjects/fantasy
        //https://www.overdrive.com/subjects/mystery
        //https://www.overdrive.com/subjects/romance
        //https://www.overdrive.com/subjects/young-adult-fiction
        //https://www.overdrive.com/subjects/thriller
        for (Element e : document.select("div.results div.title-result-row")) {

            Element img = e.select("div.title-result-row__cover-cell div.title-result-row__cover a > img").first();
            String coverImage = img.attr("src");//image

            String bookTitle = e.select("div.title-result-row__details h3.title-result-row__title a").text();
            String bookAuthor = e.select("div.title-result-row__details h3.title-result-row__creator").text();
            Element url = e.select("div.title-result-row__details h3.title-result-row__title > a").first();
            String link = url.attr("href");
            String bookLink = "https://www.overdrive.com/" + link;//link

            WebScrapping webScrapping = new WebScrapping
                    (coverImage, bookTitle, bookAuthor, bookLink);
            webScrappingList.add(webScrapping);
        }
        System.out.println(webScrappingList);
        return webScrappingList;
    }

    //beta
    public List<WebScrapping> getWebScrapeData2() throws IOException {
        List<WebScrapping> webScrappingList = new ArrayList<>();
        Document document = Jsoup.
                connect("https://openlibrary.org/search?subject_facet=Accessible+book&q=language%3Aeng&sort=editions").get();

        for (Element e : document.select("li.searchResultItem")) {

            Element img = e.select("span.bookcover a > img").first();
            String img1 = img.attr("src");
            String coverImage = "https:/" + img1;

            String bookTitle = e.select("div.details div.resultTitle h3.bookTitle a").text();
            String bookAuthor = e.select("div.details span.bookauthor").text();
            Element url = e.select("span.bookcover > a").first();
            String link = url.attr("href");
            String bookLink = "https://openlibrary.org/" + link;//link

            WebScrapping webScrapping = new WebScrapping
                    (coverImage, bookTitle, bookAuthor, bookLink);
            webScrappingList.add(webScrapping);
        }
        System.out.println(webScrappingList);
        return webScrappingList;
    }

    //beta
    public List<WebScrapping> getWebScrapeData3() throws IOException {
        List<WebScrapping> webScrappingList = new ArrayList<>();

        Document document = Jsoup.
                connect("https://archive.org/details/books").get();
        for (Element e : document.select("div.results div.C234")) {

            String bookTitle = e.select("div.item-ttl.C.C2 a div.ttl").text();
            if (!bookTitle.contentEquals("")) {

                String bookAuthor = e.select("div.by.C.C4 span.byv").text();
                String link = e.select("div.item-ttl.C.C2 a").attr("href");
                String bookLink = "https://archive.org/" + link;//link

                String[] list = link.split("/");
                String coverImage = "https://archive.org/services/img/" + list[2];

                WebScrapping webScrapping = new WebScrapping
                        (coverImage, bookTitle, bookAuthor, bookLink);
                webScrappingList.add(webScrapping);
            }

        }
        System.out.println(webScrappingList);
        return webScrappingList;
    }*/
}
