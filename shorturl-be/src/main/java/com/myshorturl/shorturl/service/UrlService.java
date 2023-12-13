package com.myshorturl.shorturl.service;

import com.myshorturl.shorturl.model.CreateUrlDto;
import com.myshorturl.shorturl.model.UrlModel;
import com.myshorturl.shorturl.repository.UrlRepository;
import com.myshorturl.shorturl.utils.GenerateHash;
import java.util.Objects;
import lombok.AllArgsConstructor;
import lombok.NonNull;
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
    public ResponseEntity<UrlModel> createShortUrl(@NonNull final CreateUrlDto createUrlDto){
        String shortUrl = getUrlUniqueHash(createUrlDto);
        UrlModel urlModel = new UrlModel(shortUrl,shortUrl, createUrlDto.longUrl(), 0);
        urlRepository.save(urlModel);
        return ResponseEntity.status(HttpStatus.CREATED).body(urlModel);
    }

    public ResponseEntity<UrlModel> getUrlModel(String shortUrl){
        UrlModel urlModel = urlRepository.findByShortUrl(shortUrl);
        if(Objects.isNull(urlModel)){
            log.info("Long Url NOT EXISTS for Short URL: {}",shortUrl);
            return ResponseEntity.badRequest().body(null);
        }
        return ResponseEntity.status(HttpStatus.OK).body(urlModel);
    }

    public ResponseEntity<String> getUrlByShortUrl(String shortUrl){
        UrlModel urlModel =  urlRepository.findByShortUrl(shortUrl);
        if(Objects.isNull(urlModel)){
            log.info("Long Url NOT EXISTS for Short URL: {}",shortUrl);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error");
        }
        urlModel.setUrlHit(urlModel.getUrlHit()+1);
        saveUrlModel(urlModel);
        return ResponseEntity.status(HttpStatus.TEMPORARY_REDIRECT)
            .header("location", urlModel.getLongUrl()).body("redirected");
    }

    private void saveUrlModel(UrlModel urlModel){
        urlRepository.save(urlModel);
    }

    private String getUrlUniqueHash(@NonNull final CreateUrlDto createUrlDto){
        String possibleShortUrl;
        StringBuilder url = new StringBuilder(createUrlDto.longUrl());
        while (true){
            possibleShortUrl = generateHash.getUrlHash(url.toString());
            UrlModel urlModel = urlRepository.findByShortUrl(possibleShortUrl);
            if(Objects.isNull(urlModel)){ return possibleShortUrl; }
            if(urlModel.getLongUrl().equals(createUrlDto.longUrl())){ return possibleShortUrl; }
            url.append("com");
        }
    }


}
