package com.example.reservationsProject.keyGenerator;

import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.security.SecureRandom;
import java.util.Base64;


public class KeyGenerator {

    public void generateAndWriteKeyToFile() {
        String secretKey = generateSecretKey();
        writeKeyToFile(secretKey);
    }

    private String generateSecretKey() {
        SecureRandom random = new SecureRandom();
        byte[] key = new byte[64];
        random.nextBytes(key);
        return Base64.getEncoder().encodeToString(key);
    }

    private void writeKeyToFile(String secretKey) {
        try (FileWriter fileWriter = new FileWriter("env.properties", true);
             PrintWriter printWriter = new PrintWriter(fileWriter)) {

            printWriter.println("JWT_SECRET=" + secretKey);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}