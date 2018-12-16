package com.TicketIT;

import com.TicketIT.Utils.EncryptUtils;
import org.jasypt.exceptions.EncryptionOperationNotPossibleException;
import org.junit.*;

/**
 * Tests the encryption utils.
 */
public class EncryptionTests {

    @Test
    public void TestCanEncryptStrings(){
        String stringToEncrypt = "testString";
        String salt = EncryptUtils.getSalt();
        String encryptedString = EncryptUtils.encrypt(stringToEncrypt, salt);
        assert(!encryptedString.equals(stringToEncrypt));
    }

    @Test
    public void TestCanDecryptStrings(){
        String stringToEncrypt = "testString";
        String salt = EncryptUtils.getSalt();
        String encryptedString = EncryptUtils.encrypt(stringToEncrypt, salt);
        assert(!encryptedString.equals(stringToEncrypt));
        String decryptedString = EncryptUtils.decrypt(encryptedString, salt);
        assert(stringToEncrypt.equals(decryptedString));
    }

    @Test
    public void TestCantDecryptStringsIncorrectSalt(){
        String stringToEncrypt = "testString";
        String salt = EncryptUtils.getSalt();
        String encryptedString = EncryptUtils.encrypt(stringToEncrypt, salt);
        assert(!encryptedString.equals(stringToEncrypt));
        try {
            String decryptedString = EncryptUtils.decrypt(encryptedString, EncryptUtils.getSalt());
        } catch(EncryptionOperationNotPossibleException ex){
            assert(true); // Decryption wasn't possible.
            return;
        }
        assert(false); // Decryption was possible with incorrect salt.
    }
}
