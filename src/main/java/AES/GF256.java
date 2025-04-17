package AES;

import java.util.ArrayList;
import java.util.List;

public class GF256 {

    // Irreducible polynomial for AES (x^8 + x^4 + x^3 + x + 1)
    private static final int IRREDUCIBLE_POLY = 0x11B;

    // Multiply a by x in GF(2^8)
    private static int xtime(int a) {
        return ((a << 1) ^ ((a & 0x80) != 0 ? IRREDUCIBLE_POLY : 0)) & 0xFF; //0x11B to 0x1B.
    }

    // Multiply two bytes using the x*f(x) rule repeatedly.
    public static int multiply(int a, int b) {
        int result = 0;

        while (b != 0) {
            if ((b & 1) != 0) {
                result ^= a;
            }
            a = xtime(a);
            b >>= 1;
        }

        return result;
    }


    public static String binaryToHex(String s) {
        StringBuilder list = new StringBuilder();
        for(int i = 0; i < s.length(); i+=8) {
            String c = s.substring(i, i + 8);
            String left = c.substring(0, 4);
            String right = c.substring(4);
            list.append(Integer.toHexString(Integer.parseInt(left, 2))).
                    append(Integer.toHexString(Integer.parseInt(right, 2)));
        }
        return list.toString();
    }

    public static String hexToBinary(String hex) {
        StringBuilder binary = new StringBuilder();
        for (int i = 0; i < hex.length(); i++) {
            int val = Integer.parseInt(hex.substring(i, i + 1), 16);
            binary.append(String.format("%4s", Integer.toBinaryString(val)).
                    replace(' ', '0'));
        }
        return binary.toString();
    }


    public static String[][] transpose(String[][] matrix) {
        int rows = matrix.length;
        int cols = matrix[0].length;
        String[][] transposed = new String[cols][rows];

        for (int i = 0; i < rows; i++)
            for (int j = 0; j < cols; j++)
                transposed[j][i] = matrix[i][j];

        return transposed;
    }

    public static List<String> blocking(String text){
        List<String> ans = new ArrayList<>();
        for (int i = 0; i < text.length(); i+=32) {
            ans.add(text.substring(i, i + 32));
        }
        return ans;
    }
}

