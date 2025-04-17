package CeaserCipher;

import java.util.ArrayList;
import java.util.List;

public class Ceaser {

    public String encrypt(String plainText, int key) {
        key %= 26;
        if (key < 0) {
            key += 26;
        }
        StringBuilder word = new StringBuilder(plainText);
        for (int i = 0; i < word.length(); i++) {
            if (Character.isUpperCase(word.charAt(i))) {
                word.setCharAt(i, (char) (((word.charAt(i) - 'A' + key) % 26) + 'A'));
            } else if (Character.isLowerCase(word.charAt(i))) {
                word.setCharAt(i, (char) (((word.charAt(i) - 'a' + key) % 26) + 'a'));
            }
        }
        return word.toString();
    }

    public String decrypt(String plainText, int key) {
        key %= 26;
        if (key < 0) {
            key += 26;
        }
        StringBuilder word = new StringBuilder(plainText);
        for (int i = 0; i < word.length(); i++) {
            if (Character.isUpperCase(word.charAt(i))) {
                if (((word.charAt(i) - 'A' - key) % 26) < 0) {
                    word.setCharAt(i, (char) (word.charAt(i) + 26));
                }
                word.setCharAt(i, (char) (((word.charAt(i) - 'A' - key) % 26) + 'A'));
            } else if (Character.isLowerCase(word.charAt(i))) {
                if (((word.charAt(i) - 'a' - key) % 26) < 0) {
                    word.setCharAt(i, (char) (word.charAt(i) + 26));
                }
                word.setCharAt(i, (char) (((word.charAt(i) - 'a' - key) % 26) + 'a'));
            }
        }
        return word.toString();
    }

    public List<String> attacks(String cipherText) {
        List<String> deciphers = new ArrayList<>();
        for (int i = 1; i < 26; i++) {
            deciphers.add(decrypt(cipherText, i));
        }
        return deciphers;
    }

    public String knowPlainAttack(String cipher1, String plain1) {
        String key = "null";
        for (int i = 0; i < cipher1.length(); i++) {
            if(Character.isAlphabetic(cipher1.charAt(i))){
                key = String.valueOf(cipher1.charAt(i)-plain1.charAt(i));
            }
        }
        return key;
    }
}