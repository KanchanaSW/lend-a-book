package com.system.Integration.Services;

import com.system.Integration.Models.WebScrapping;
import org.springframework.stereotype.Service;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class WebScrapingService {
    public List<WebScrapping> getWebscrapeData() throws IOException {
        List<WebScrapping> webScrappingList = new ArrayList<>();
        Document document = Jsoup.
                connect("https://www.overdrive.com/collections/17532/overdrive-librarians-favorite-reads-of-2021").get();

        for (Element e : document.select("div.resultsContainer div.title-result-row")) {

            Element img=e.select("div.title-result-row__cover-cell div.title-result-row__cover a > img").first();
            String coverImage=img.attr("src");//image

            String bookTitle = e.select("div.title-result-row__details h3.title-result-row__title a").text();
            String bookAuthor = e.select("div.title-result-row__details h3.title-result-row__creator").text();
            Element url = e.select("div.title-result-row__details h3.title-result-row__title > a").first();
            String link=url.attr("href");
            String bookLink="https://www.overdrive.com/"+link;//link

            WebScrapping webScrapping = new WebScrapping
                    (coverImage,bookTitle,bookAuthor,bookLink);
            webScrappingList.add(webScrapping);
        }
        System.out.println(webScrappingList);
        return webScrappingList;
    }
}
