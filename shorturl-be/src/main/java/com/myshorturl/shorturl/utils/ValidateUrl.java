package com.myshorturl.shorturl.utils;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.BadRequestException;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class ValidateUrl {
    public void isValidURL(String url)
        throws BadRequestException {
        try {
            new URL(url).toURI();
        } catch (MalformedURLException | URISyntaxException e) {
            log.info("Invalid Url: {}",url);
            throw new BadRequestException("Invalid url Syntax");
        }
    }

}
