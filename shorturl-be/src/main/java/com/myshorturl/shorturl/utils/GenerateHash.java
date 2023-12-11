package com.myshorturl.shorturl.utils;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import lombok.NonNull;
import org.springframework.stereotype.Component;

@Component
public class GenerateHash {
    private GenerateHash(){}

    // TODO: Make a factory for this, so that we can change hash algorithm when there is a need
    // Make Hash Generation small in size.
    private  String getHash(@NonNull final String input)
    {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-1");

            byte[] messageDigest = md.digest(input.getBytes());

            BigInteger no = new BigInteger(1, messageDigest);

            String hashText = no.toString(16);

            while (hashText.length() < 32) {
                hashText = "0" + hashText;
            }

            return hashText;
        }

        // For specifying wrong message digest algorithms
        catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    public  String getUrlHash(@NonNull final String longUrl){
        return getHash(longUrl);
    }
}
