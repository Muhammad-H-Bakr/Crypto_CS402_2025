package PlayFair;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class PlayCipher {

    private String formWord(String plainText) {
        plainText = plainText.toLowerCase();
        StringBuilder helper = new StringBuilder(plainText);
        Map<Integer, Character> ignored = new HashMap<>();
        for (int i = 0; i < helper.length(); i++) {
            if (!Character.isLetter(helper.charAt(i))) {
                ignored.put(i, helper.charAt(i));
            }
        }
        ignored.forEach((_, v) ->
                helper.deleteCharAt(helper.indexOf(v + "")));
        return helper.toString();
    }

    private Map<List<Character>, List<Integer>> constructMatrix(String key) {
        Map<List<Character>, List<Integer>> matrix = new HashMap<>();
        StringBuilder noRepeat = new StringBuilder();
        StringBuilder alphabet = new StringBuilder("abcdefghijklmnopqrstuvwxyz");
        for (int i = 0; i < key.length(); i++) {
            if (noRepeat.indexOf(Character.toLowerCase(key.charAt(i)) + "") == -1) {
                noRepeat.append(Character.toLowerCase(key.charAt(i)));
            }
        }
        int x = 0, y = 0;
        for (int i = 0; i < noRepeat.length(); i++) {
            if (noRepeat.charAt(i) == 'i' || noRepeat.charAt(i) == 'j') {
                alphabet.deleteCharAt(alphabet.indexOf("i"));
                alphabet.deleteCharAt(alphabet.indexOf("j"));
                if (!matrix.containsKey(List.of('i', 'j'))) {
                    matrix.put(List.of('i', 'j'), List.of(x, y));
                    x++;
                    if (x == 5) {
                        x = 0;
                        y++;
                    }
                }
            } else {
                matrix.put(List.of(noRepeat.charAt(i)), List.of(x, y));
                alphabet.deleteCharAt(alphabet.indexOf(noRepeat.charAt(i) + ""));
                x++;
                if (x == 5) {
                    x = 0;
                    y++;
                }
            }
        }
        for (int z = 0; z < alphabet.length(); z++) {
            if (alphabet.charAt(z) == 'i' || alphabet.charAt(z) == 'j') {
                if (!matrix.containsKey(List.of('i', 'j'))) {
                    matrix.put(List.of('i', 'j'), List.of(x, y));
                    x++;
                    if (x == 5) {
                        x = 0;
                        y++;
                    }
                }
            } else {
                matrix.put(List.of(alphabet.charAt(z)), List.of(x, y));
                x++;
                if (x == 5) {
                    x = 0;
                    y++;
                }
            }
        }
        return matrix;
    }

    private void Mov(StringBuilder encrypted, Map<List<Character>,
            List<Integer>> matrix, int x1, int x2, int y1, int y2) {
        matrix.forEach((C, L) -> {
            if (L.equals(List.of(x2, y1))) {
                if (C.equals(List.of('i', 'j'))) {
                    encrypted.append("i");
                } else {
                    encrypted.append(C.getFirst());
                }
            }
        });

        matrix.forEach((C, L) -> {
            if (L.equals(List.of(x1, y2))) {
                if (C.equals(List.of('i', 'j'))) {
                    encrypted.append("i");
                } else {
                    encrypted.append(C.getFirst());
                }
            }
        });
    }

    public String encrypt(String plainText, String key) {
        key = formWord(key);
        StringBuilder helper = new StringBuilder(formWord(plainText));
        StringBuilder encrypted = new StringBuilder();
        Map<List<Character>, List<Integer>> matrix = constructMatrix(key);
        int i = 0, j = 1;
        while (j < helper.length()) {
            if (helper.charAt(i) == helper.charAt(j)) {
                helper.insert(j, "x");
            }
            i += 2;
            j += 2;
        }
        if (helper.length() % 2 != 0) {
            helper.append("x");
        }
        for (int k = 0; k + 1 < helper.length(); k += 2) {
            int x1, x2, y1, y2;
            List<Integer> letter1;
            List<Integer> letter2;
            if (helper.charAt(k) == 'i' || helper.charAt(k) == 'j') {
                letter1 = matrix.get(List.of('i', 'j'));
            } else {
                letter1 = matrix.get(List.of(helper.charAt(k)));
            }
            if (helper.charAt(k + 1) == 'i' || helper.charAt(k + 1) == 'j') {
                letter2 = matrix.get(List.of('i', 'j'));
            } else {
                letter2 = matrix.get(List.of(helper.charAt(k + 1)));
            }
            x1 = letter1.get(0);
            y1 = letter1.get(1);
            x2 = letter2.get(0);
            y2 = letter2.get(1);
            if (x1 != x2 && y1 != y2) {
                Mov(encrypted, matrix, x1, x2, y1, y2);
            } else if (x1 == x2) {
                y1 = (y1 + 1) % 5;
                y2 = (y2 + 1) % 5;
                Mov(encrypted, matrix, x2, x1, y1, y2);
            } else {
                x1 = (x1 + 1) % 5;
                x2 = (x2 + 1) % 5;
                Mov(encrypted, matrix, x2, x1, y1, y2);
            }
        }
        return encrypted.toString().toUpperCase();
    }


    public String decrypt(String plainText, String key) {
        key = formWord(key);
        StringBuilder helper = new StringBuilder(formWord(plainText));
        StringBuilder decrypted = new StringBuilder();
        Map<List<Character>, List<Integer>> matrix = constructMatrix(key);
        int i = 0, j = 1;
        while (j < helper.length()) {
            if (helper.charAt(i) == helper.charAt(j)) {
                helper.insert(j, "x");
            }
            i += 2;
            j += 2;
        }
        if (helper.length() % 2 != 0) {
            helper.append("x");
        }
        for (int k = 0; k + 1 < helper.length(); k += 2) {
            int x1, x2, y1, y2;
            List<Integer> letter1;
            List<Integer> letter2;
            if (helper.charAt(k) == 'i' || helper.charAt(k) == 'j') {
                letter1 = matrix.get(List.of('i', 'j'));
            } else {
                letter1 = matrix.get(List.of(helper.charAt(k)));
            }
            if (helper.charAt(k + 1) == 'i' || helper.charAt(k + 1) == 'j') {
                letter2 = matrix.get(List.of('i', 'j'));
            } else {
                letter2 = matrix.get(List.of(helper.charAt(k + 1)));
            }
            x1 = letter1.get(0);
            y1 = letter1.get(1);
            x2 = letter2.get(0);
            y2 = letter2.get(1);
            if (x1 != x2 && y1 != y2) {
                Mov(decrypted, matrix, x1, x2, y1, y2);
            } else if (x1 == x2) {
                y1--;
                if (y1 < 0) {
                    y1 += 5;
                }
                y2--;
                if (y2 < 0) {
                    y2 += 5;
                }
                Mov(decrypted, matrix, x2, x1, y1, y2);
            } else {
                x1--;
                if (x1 < 0) {
                    x1 += 5;
                }
                x2--;
                if (x2 < 0) {
                    x2 += 5;
                }
                Mov(decrypted, matrix, x2, x1, y1, y2);
            }
        }
        return decrypted.toString().toLowerCase();
    }
}
