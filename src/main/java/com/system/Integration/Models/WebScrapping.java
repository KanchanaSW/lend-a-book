package com.system.Integration.Models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WebScrapping {
    String coverImage;
    String bookTitle;
    String bookAuthor;
    String bookLink;
    String price;
}
