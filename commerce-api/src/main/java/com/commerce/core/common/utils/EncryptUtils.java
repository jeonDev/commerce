package com.commerce.core.common.utils;

import com.commerce.core.common.exception.CommerceException;
import lombok.extern.slf4j.Slf4j;

import java.security.MessageDigest;
import java.util.Base64;

@Slf4j
public class EncryptUtils {

    /**
     * SHA-256 Encrypt
     */
    public static String encryptSHA256(String value) {
        return encrypt(value, "SHA256");
    }

    /**
     * Encrypt
     */
    private static String encrypt(String value, String algorithms) {
        MessageDigest md = null;

        try {
            md = MessageDigest.getInstance(algorithms);
            md.update(value.getBytes());
        } catch (Exception e) {
            throw new CommerceException(e);
        }
        return Base64.getEncoder().encodeToString(md.digest());
    }

    /**
     * Decrypt
     */
    private static String decrypt(String value) {
        return value;
    }
}
