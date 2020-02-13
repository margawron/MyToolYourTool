package pl.polsl.inzoprog.myToolYourTool.services;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

/**
 * @author Marcel Gawron
 * @version 1.0
 */
@Service
public class CryptoService {

    public CryptoService() {

    }

    public String digestPassAndSalt(String password, String salt) {
        String toBeDigested = password + salt;

        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("SHA-512");
        } catch (NoSuchAlgorithmException e) {
            return null;
        }
        byte[] digested = md.digest(toBeDigested.getBytes());

        return new String(digested, StandardCharsets.UTF_8);
    }

    public String generateSalt() {
        byte[] rawSalt = new byte[14];
        new Random().nextBytes(rawSalt);
        return new String(rawSalt, StandardCharsets.UTF_8);
    }

    public String generateRandomAlphanumericString(int length) {
        return RandomStringUtils.randomAlphanumeric(length);
    }
}
