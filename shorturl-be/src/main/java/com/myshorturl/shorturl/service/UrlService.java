package com.myshorturl.shorturl.service;

import com.myshorturl.shorturl.model.CreateUrlDto;
import com.myshorturl.shorturl.model.UrlModel;
import com.myshorturl.shorturl.repository.UrlRepository;
import com.myshorturl.shorturl.utils.GenerateHash;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
public class UrlService {
    // TODO: add controller advice Exception handler.
    private final UrlRepository urlRepository;
    private final GenerateHash generateHash;
    public ResponseEntity<UrlModel> createShortUrl(CreateUrlDto createUrlDto){
        String shortUrl = generateHash.getUrlHash(createUrlDto.longUrl());
        UrlModel urlModel = new UrlModel(shortUrl,shortUrl, createUrlDto.longUrl(), 0);
        urlRepository.save(urlModel);
        return ResponseEntity.status(HttpStatus.CREATED).body(urlModel);
    }

    public ResponseEntity<UrlModel> getUrlModel(String shortUrl){
        UrlModel urlModel = urlRepository.findByShortUrl(shortUrl);
        return ResponseEntity.status(HttpStatus.OK).body(urlModel);
    }

    public ResponseEntity<String> getUrlByShortUrl(String shortUrl){
        UrlModel urlModel =  urlRepository.findByShortUrl(shortUrl);
        urlModel.setUrlHit(urlModel.getUrlHit()+1);
        saveUrlModel(urlModel);
        return ResponseEntity.status(HttpStatus.TEMPORARY_REDIRECT)
            .header("location", urlModel.getLongUrl()).body("redirected");
    }

    private void saveUrlModel(UrlModel urlModel){
        urlRepository.save(urlModel);
    }

}
