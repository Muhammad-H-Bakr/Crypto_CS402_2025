package Vigenere;

import CeaserCipher.Ceaser;

public class VigCipher {
    Ceaser cs = new Ceaser();

    public int encode(char x) {
        x = Character.toLowerCase(x);
        if (Character.isLetter(x)) {
            return x - 'a';
        } else {
            return 0;
        }
    }

    public String encrypt(String plainText, String key) {
        int len = key.length();
        StringBuilder res = new StringBuilder();
        for (int i = 0; i < plainText.length(); i++) {
            res.append(cs.encrypt(plainText.charAt(i) + "",
                    encode(key.charAt(i % len))));
        }
        return res.toString();
    }

    public String decrypt(String cipherText, String key) {
        int len = key.length();
        StringBuilder res = new StringBuilder();
        for (int i = 0; i < cipherText.length(); i++) {
            res.append(cs.decrypt(cipherText.charAt(i) + "",
                    encode(key.charAt(i % len))));
        }
        return res.toString();
    }
}
