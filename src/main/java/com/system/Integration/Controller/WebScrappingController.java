package com.system.Integration.Controller;

import com.system.Integration.Models.WebScrapping;
import com.system.Integration.Services.WebScrapingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("api/scrape/")
public class WebScrappingController {
    private WebScrapingService webScrapingService;

    @Autowired
    public WebScrappingController(WebScrapingService webScrapingService) {
        this.webScrapingService = webScrapingService;
    }

    @GetMapping("data")
    public List<WebScrapping> getWebscrapeData() throws IOException {
        return webScrapingService.getWebscrapeData();
    }
}
