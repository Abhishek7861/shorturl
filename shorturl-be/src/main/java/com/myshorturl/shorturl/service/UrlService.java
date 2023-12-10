package com.myshorturl.shorturl.service;

import com.myshorturl.shorturl.model.UrlModel;
import com.myshorturl.shorturl.repository.UrlRepository;
import java.util.Optional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UrlService {
    private final UrlRepository urlRepository;
    public void saveUrl(UrlModel urlModel){
        urlRepository.save(urlModel);
    }

    public UrlModel getUrlByShortUrl(String shortUrl){
        return urlRepository.findByShortUrl(shortUrl);
    }

    public Optional<UrlModel> getUrlById(String id){
        return urlRepository.findById(id);
    }
}
