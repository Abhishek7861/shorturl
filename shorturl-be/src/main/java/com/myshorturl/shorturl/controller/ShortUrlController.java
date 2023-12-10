package com.myshorturl.shorturl.controller;

import com.myshorturl.shorturl.model.CreateUrlDto;
import com.myshorturl.shorturl.model.UrlModel;
import com.myshorturl.shorturl.service.UrlService;
import com.myshorturl.shorturl.utils.GenerateHash;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@Slf4j
public class ShortUrlController {
    private final UrlService urlService;

    @PostMapping
    public ResponseEntity<UrlModel> createShortUrl(@RequestBody CreateUrlDto createUrlDto){
        String shortUrl = GenerateHash.encryptThisString(createUrlDto.longUrl());
        UrlModel urlModel = new UrlModel("1",shortUrl,createUrlDto.longUrl(),1);
        urlService.saveUrl(urlModel);
        return ResponseEntity.status(HttpStatus.CREATED).body(urlModel);
    }


    @GetMapping("/{shortUrl}")
    public ResponseEntity<String> getLongUrl(@PathVariable String shortUrl){
        UrlModel urlModel = urlService.getUrlByShortUrl(shortUrl);
        log.info("shortUrl : {} returns longUrl : {}",shortUrl,urlModel.getLongUrl());
        return ResponseEntity.status(HttpStatus.TEMPORARY_REDIRECT).header("location",urlModel.getLongUrl()).body("");
    }
}
