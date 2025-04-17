package AES;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static AES.GF256.multiply;
import static AES.GF256.transpose;


public class Rounds {


    public static String[][] from32ToMatrixState(String s) {
        String[][] mat = new String[4][4];
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                mat[i][j] = s.substring((i * 8) + (j * 2), (i * 8) + (j * 2) + 2);
            }
        }
        return transpose(mat); //filled by column instead of row.
    }

    public static String outputString(String[][] mat) {
        StringBuilder s = new StringBuilder();
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                s.append(mat[j][i]);
            }
        }
        return s.toString();
    }

    public static String xorHexa(String s1, String s2) {
        String result = Integer.toHexString(Integer.parseInt(s1, 16)
                ^ Integer.parseInt(s2, 16));
        return (result.length() == 1) ? "0" + result : result;
    }

    public static String s_BoxResult(String s, int mode) {
        int row = Integer.parseInt(s.substring(0, 1), 16);
        int column = Integer.parseInt(s.substring(1, 2), 16);
        String result = "";
        if (mode == 0) {
            result = Integer.toHexString((Constants.sbox[row][column]));
        } else if (mode == 1) {
            result = Integer.toHexString((Constants.inverseSbox[row][column]));
        }
        return (result.length() == 1) ? "0" + result : result;
    }

    public static String[][] shiftRow(String[][] matrix, int mode) {
        int rows = matrix.length;
        if (rows == 0) return matrix;
        int cols = matrix[0].length;

        String[][] result = new String[rows][cols];

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (mode == 0) {
                    result[i][j] = matrix[i][(j + i) % cols];
                } else if (mode == 1) {
                    result[i][j] = matrix[i][(j - i + cols) % cols];
                } else {
                    return null;
                }
            }
        }
        return result;
    }

    public static String[][] mixColumn(String[][] s, int mode) {
        String[][] ans = new String[s.length][s.length];
        int[][] B = new int[4][4];
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                B[i][j] = Integer.parseInt(s[i][j], 16);
            }
        }
        int[][] result = new int[4][4];
        int[][] A = new int[0][];
        if (mode == 0) {
            A = Constants.mixColumn;
        } else if (mode == 1) {
            A = Constants.inverseMixColumn;
        }
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                int sum = 0;
                for (int k = 0; k < 4; k++) {
                    sum ^= multiply(A[i][k], B[k][j]);
                }
                result[i][j] = sum;
            }
        }
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                String represent = Integer.toHexString(result[i][j]);
                ans[i][j] = (represent.length() == 1) ?
                        "0" + represent : represent;
            }
        }
        return ans;
    }

    public static String[][] listWordToMatrixState(List<List<String>> ls) {
        //words in column orientation not row.
        String[][] roundWordsMatrix = new String[4][4];
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                roundWordsMatrix[i][j] = ls.get(i).get(j);
            }
        }
        return transpose(roundWordsMatrix);
    }

    public static String[][] addRoundKey(String[][] text, String[][] roundKeyMatrix) {
        String[][] result = new String[4][4];
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                result[i][j] = xorHexa(text[i][j], roundKeyMatrix[i][j]);
            }
        }
        return result;
    }

    public static String[][] subBytes(String[][] text, int mode) {
        String[][] res = new String[4][4];
        if (mode == 0) {
            for (int i = 0; i < 4; i++) {
                for (int j = 0; j < 4; j++) {
                    res[i][j] = s_BoxResult(text[i][j], 0);
                }
            }
            return res;
        } else if (mode == 1) {
            for (int i = 0; i < 4; i++) {
                for (int j = 0; j < 4; j++) {
                    res[i][j] = s_BoxResult(text[i][j], 1);
                }
            }
            return res;
        } else return null;
    }

    public static List<List<List<String>>> expandKey(String keyHex) {

        String[][] key = from32ToMatrixState(keyHex);
        List<List<List<String>>> ans = new ArrayList<>();
        List<List<String>> words = new ArrayList<>();

        for (int j = 0; j < 4; j++) { //w0, w1, w2, w3
            List<String> word = new ArrayList<>();
            for (int i = 0; i < 4; i++) {
                word.add(key[i][j]);
            }
            words.add(word);
        }

        ans.add(words); //first 4 words.

        for (int i = 0; i < 10; i++) {

            List<List<String>> prevWords = new ArrayList<>(ans.get(i)); //last 4 words.
            List<List<String>> newWords = new ArrayList<>();

            for (int j = 0; j < 4; j++) {
                List<String> word = new ArrayList<>(); //wanted word.
                List<String> prevBy4 = new ArrayList<>(prevWords.get(j));
                List<String> temp;

                if (newWords.isEmpty()) {
                    temp = new ArrayList<>(prevWords.get(3));
                } else {
                    temp = new ArrayList<>(newWords.getLast()); //prevWord by 1.
                }
                if (j == 0) { //divisible by 4.
                    //Shift:
                    String shifted = temp.getFirst();
                    temp = temp.subList(1, temp.size());
                    temp.add(shifted);

                    //S-Box:
                    for (int l = 0; l < 4; l++) {
                        String s = s_BoxResult(temp.get(l), 0);
                        temp.set(l, s);
                    }

                    //Xor-RCon.
                    String first = temp.getFirst();
                    int RCon = Constants.RCon[i];
                    String result = Integer.toHexString(Integer.parseInt(first, 16)
                            ^ RCon);
                    String s = (result.length() == 1) ? "0" + result : result;
                    temp.set(0, s);
                }

                //Xor with prev by 4 eitherway.
                for (int l = 0; l < 4; l++) {
                    String s = xorHexa(temp.get(l), prevBy4.get(l));
                    word.add(s);
                }
                newWords.add(word);
            }
            ans.add(newWords);
        }

        return ans;
    }

    public static String encrypt(String plainText, String key) {

        List<List<List<String>>> words = expandKey(key);
        String[][] text = from32ToMatrixState(plainText);
        String[][] roundWordsMatrix = listWordToMatrixState(words.getFirst());

        text = addRoundKey(text, roundWordsMatrix);


        for (int i = 1; i < 11; i++) {
            roundWordsMatrix = listWordToMatrixState(words.get(i));


            text = subBytes(text, 0);


            text = shiftRow(Objects.requireNonNull(text), 0);

            if (i != 10) {
                text = mixColumn(Objects.requireNonNull(text), 0);
            }
            text = addRoundKey(text, roundWordsMatrix);
        }

        return outputString(text);
    }

    public static String decrypt(String cipherText, String key) {

        List<List<List<String>>> words = expandKey(key);
        String[][] text = from32ToMatrixState(cipherText);
        String[][] roundWordsMatrix = listWordToMatrixState(words.getLast());

        text = addRoundKey(text, roundWordsMatrix);


        for (int i = 9; i >= 0; i--) {
            roundWordsMatrix = listWordToMatrixState(words.get(i));


            text = shiftRow(Objects.requireNonNull(text), 1);

            text = subBytes(text, 1);

            text = addRoundKey(text, roundWordsMatrix);

            if (i != 0) {
                text = mixColumn(Objects.requireNonNull(text), 1);
            }
        }

        return outputString(text);
    }
}