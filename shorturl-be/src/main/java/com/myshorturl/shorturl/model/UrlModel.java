package com.myshorturl.shorturl.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "shortUrl")
@AllArgsConstructor
@Getter
public class UrlModel {
    @Id
    private final String id;
    private final String shortUrl;
    private final String longUrl;
    private int urlHit;
    public void setUrlHit(int urlHit) {
        this.urlHit = urlHit;
    }
}
