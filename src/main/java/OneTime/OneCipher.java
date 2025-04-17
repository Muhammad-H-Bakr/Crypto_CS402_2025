package OneTime;

import Vigenere.VigCipher;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class OneCipher {
    VigCipher cipher = new VigCipher();

    int generateRandomNumber(int s, int a, int b) {
        return (a * s + b) % 26;
    }

    public List<String> encrypt(String plainText) {
        int len = plainText.length();
        Random rnd = new Random();
        int randomizer = 5; // seed
        int a = 10,
                b = 15;
        StringBuilder key = new StringBuilder();
        List<String> result = new ArrayList<>();
        for (int i = 0; i < len; i++) {
            key.append((char) (randomizer + 'a'));
            randomizer = generateRandomNumber(randomizer, a, b);
        }
        String ans = cipher.encrypt(plainText, key.toString());
        result.add(ans);
        result.add(key.toString());
        return result;
    }

    public String decrypt(String cipherText, String key) {
        return cipher.decrypt(cipherText, key);
    }
}
