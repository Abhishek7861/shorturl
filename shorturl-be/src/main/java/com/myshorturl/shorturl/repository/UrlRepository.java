package com.myshorturl.shorturl.repository;

import com.myshorturl.shorturl.model.UrlModel;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UrlRepository extends MongoRepository<UrlModel,String> {
    UrlModel findByShortUrl(String shortUrl);
}
