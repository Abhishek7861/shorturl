package com.myshorturl.shorturl.controller;

import com.myshorturl.shorturl.model.CreateUrlDto;
import com.myshorturl.shorturl.model.UrlModel;
import com.myshorturl.shorturl.service.UrlService;
import lombok.AllArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class ShortUrlController {

    private final UrlService urlService;

    @PostMapping
    public ResponseEntity<UrlModel> createShortUrl(@RequestBody CreateUrlDto createUrlDto)
        throws BadRequestException {
        return urlService.createShortUrl(createUrlDto);
    }


    @GetMapping("/{shortUrl}")
    public ResponseEntity<String> getLongUrl(@PathVariable String shortUrl) {
        return urlService.getUrlByShortUrl(shortUrl);
    }

    @GetMapping("/{shortUrl}/info")
    public ResponseEntity<UrlModel> getUrlHitCount(@PathVariable String shortUrl) {
        return urlService.getUrlModel(shortUrl);
    }
}
