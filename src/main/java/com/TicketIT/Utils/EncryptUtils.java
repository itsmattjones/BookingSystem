package com.TicketIT.Utils;

import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import java.security.SecureRandom;
import java.util.*;

public class EncryptUtils {
    private static final Random RANDOM = new SecureRandom();
    private static final String ALPHABET = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String MASTER_SALT = "02As3a6DSa3su138r89F24D43FSdfs3asddDSF8dASD3fF4gaSd234sdifj983asd";
    private static final int SALT_LENGTH = 64;

    /**
     * Encrypts the given string using the JASYPT library.
     *
     * @param string The string to encrypt.
     * @param salt The salt/password for the encryption.
     * @return The encrypted string.
     */
    public static String encrypt(String string, String salt){
        StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();
        encryptor.setPassword(decryptSalt(salt));
        return encryptor.encrypt(string);
    }

    /**
     * Decrypts the given string using the JASYPT library.
     *
     * @param encryptedString The encrypted string.
     * @param salt The salt/password for the encryption.
     * @return The decrypted string.
     */
    public static String decrypt(String encryptedString, String salt){
        StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();
        String decryptedSalt = decryptSalt(salt);
        encryptor.setPassword(decryptedSalt);
        return encryptor.decrypt(encryptedString);
    }

    /**
     * Generates a random salt for use with encryption.
     *
     * @return the generated salt.
     */
    public static String getSalt() {
        StringBuilder result = new StringBuilder(SALT_LENGTH);

        for (int i = 0; i < SALT_LENGTH; i++)
            result.append(ALPHABET.charAt(RANDOM.nextInt(ALPHABET.length())));

        return encryptSalt(new String(result));
    }

    /**
     * Encrypts the given salt using the master salt.
     *
     * @param salt the salt to encrypt.
     * @return the decrypted salt.
     */
    private static String encryptSalt(String salt){
        StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();
        encryptor.setPassword(MASTER_SALT);
        return encryptor.encrypt(salt);
    }

    /**
     * Decrypts the given salt using the master salt.
     *
     * @param encryptedSalt the salt to decrypt.
     * @return the decrypted salt.
     */
    private static String decryptSalt(String encryptedSalt){
        StandardPBEStringEncryptor saltEncryptor = new StandardPBEStringEncryptor();
        saltEncryptor.setPassword(MASTER_SALT);
        return saltEncryptor.decrypt(encryptedSalt);
    }
}