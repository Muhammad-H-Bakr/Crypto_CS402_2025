package MonoAlpha;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class MonoCipher {
    List<String[]> mapCuts;

/*
    public static List<String> shuffler() { //1-time use.
        Map<Character, Character> map = new HashMap<>();
        List<Character> chars = new ArrayList<>();
        for (int i = 0; i < 26; i++) {
            chars.add((char) ('a' + i));
        }
        Collections.shuffle(chars);
        List<Character> shuffledChars = new ArrayList<>(chars);
        for (int i = 0; i < 26; i++) {
            map.put((char) ('a' + i), shuffledChars.get(i));
        }
        List<String> answers = new ArrayList<>();
        map.forEach((k, v) -> answers.add(k + "=" + v));
        return answers;
    }
*/

    public MonoCipher() {
        List<String> map = new ArrayList<>();
        try {
            File f1 = new File("D:\\CryptoCS402\\Crypto_CS402_2025" +
                    "\\src\\main\\java\\MonoAlpha\\Mapping");
            Scanner dataReader = new Scanner(f1);
            while (dataReader.hasNextLine()) {
                map.add(dataReader.nextLine());
            }
            dataReader.close();
        } catch (FileNotFoundException exception) {
            throw new RuntimeException(exception);
        }
        mapCuts = new ArrayList<>();
        for (String s : map) {
            mapCuts.add(s.split("="));
        }
    }

    public String encrypt(String input) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < input.length(); i++) {
            if (Character.isAlphabetic(input.charAt(i))) {
                if (Character.isUpperCase(input.charAt(i))) {
                    for (String[] mapCut : mapCuts) {
                        if (mapCut[0].equals
                                (String.valueOf(input.charAt(i)).toLowerCase())) {
                            result.append(mapCut[1].toUpperCase());
                            break;
                        }
                    }
                } else {
                    for (String[] mapCut : mapCuts) {
                        if (mapCut[0].equals(String.valueOf(input.charAt(i)))) {
                            result.append(mapCut[1]);
                            break;
                        }
                    }
                }
            } else {
                result.append(input.charAt(i));
            }
        }
        return result.toString();
    }

    public String decrypt(String input) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < input.length(); i++) {
            if (Character.isAlphabetic(input.charAt(i))) {
                if (Character.isLowerCase(input.charAt(i))) {
                    for (String[] mapCut : mapCuts) {
                        if (mapCut[1].equals(String.valueOf(input.charAt(i)))) {
                            result.append(mapCut[0]);
                            break;
                        }
                    }
                } else {
                    for (String[] mapCut : mapCuts) {
                        if (mapCut[1].equals
                                (String.valueOf(input.charAt(i)).toLowerCase())) {
                            result.append(mapCut[0].toUpperCase());
                            break;
                        }
                    }
                }
            } else {
                result.append(input.charAt(i));
            }
        }
        return result.toString();
    }
}