package com.TicketIT.Utils;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.security.SecureRandom;
import java.util.Random;

public class EncryptUtils {
    private static final Random RANDOM = new SecureRandom();
    private static final String ALPHABET = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final int SALT_LENGTH = 64;

    /**
     * Get a new salt.
     *
     * @return salt.
     */
    public static String getSalt() {
        StringBuilder result = new StringBuilder(SALT_LENGTH);

        for (int i = 0; i < SALT_LENGTH; i++) {
            result.append(ALPHABET.charAt(RANDOM.nextInt(ALPHABET.length())));
        }

        return new String(result);
    }

    /**
     * Encrypt the given string.
     *
     * @param text string to encrypt.
     * @param salt salt.
     * @return encrypted string.
     * @throws Exception exception during encryption.
     */
    public static String encrypt(String text, String salt) throws Exception{
        String strData;

        try {
            SecretKeySpec secretKeySpec = new SecretKeySpec(salt.getBytes(),"Blowfish");
            Cipher cipher = Cipher.getInstance("Blowfish");
            cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec);
            byte[] encrypted = cipher.doFinal(text.getBytes());
            strData = new String(encrypted);
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception(e);
        }

        return strData;
    }

    /**
     * Decrypt the given string.
     *
     * @param encryptedText string to decrypt.
     * @param salt key.
     * @return decrypted string.
     * @throws Exception exception during decryption.
     */
    public static String decrypt(String encryptedText,String salt) throws Exception{
        String strData;

        try {
            SecretKeySpec secretKeySpec = new SecretKeySpec(salt.getBytes(),"Blowfish");
            Cipher cipher = Cipher.getInstance("Blowfish");
            cipher.init(Cipher.DECRYPT_MODE, secretKeySpec);
            byte[] decrypted = cipher.doFinal(encryptedText.getBytes());
            strData = new String(decrypted);
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception(e);
        }

        return strData;
    }

}
