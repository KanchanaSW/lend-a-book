package com.system.Integration.Models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.net.URL;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WebScrapingMovie {
        String coverImage;
        String movieTitle;
        String urlLink;
        String rentPrice;

}
