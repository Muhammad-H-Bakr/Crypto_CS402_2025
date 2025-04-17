package DES;

import java.util.ArrayList;
import java.util.List;

public class Rounds {
    public static List<String> roundKey(String key) {

        List<String> list = new ArrayList<>();
        StringBuilder keyPC1 = new StringBuilder();

        for (int j = 0; j < Constants.PC1.length; j++) {
            keyPC1.append(key.charAt(Constants.PC1[j] - 1)); //length 56.
        }

        for (int i = 0; i < 16; i++) {
            String left = keyPC1.substring(0, 28);
            String right = keyPC1.substring(28);
            String shiftL = left.substring(Constants.leftShifts[i]) +
                    left.substring(0, Constants.leftShifts[i]);
            String shiftR = right.substring(Constants.leftShifts[i]) +
                    right.substring(0, Constants.leftShifts[i]);
            keyPC1 = new StringBuilder(shiftL + shiftR);
            StringBuilder keyPC2 = new StringBuilder();
            for (int j = 0; j < Constants.PC2.length; j++) {
                keyPC2.append(keyPC1.charAt(Constants.PC2[j] - 1)); //length 48.
            }
            list.add(keyPC2.toString());
        }
        return list;
    }

    public static String feistelFunction(String text, String key) {
        String left = text.substring(0, 32);
        String right = text.substring(32);
        String newRight;
        StringBuilder rightExp = new StringBuilder();
        for (int i = 0; i < Constants.expansionPerm.length; i++) {
            rightExp.append(right.charAt(Constants.expansionPerm[i] - 1));
        }
        StringBuilder xorExp = new StringBuilder();
        for (int i = 0; i < rightExp.length(); i++) {
            int x = Integer.parseInt(rightExp.substring(i, i + 1)) ^
                    Integer.parseInt(key.substring(i, i + 1));
            xorExp.append(x);
        }
        StringBuilder S_boxes = new StringBuilder();
        for (int i = 0; i < xorExp.length(); i += 6) {
            String s = xorExp.substring(i, i + 6);
            int x = Integer.parseInt(s.charAt(0) + "" + s.charAt(5), 2);
            int y = Integer.parseInt(s.substring(1, 5), 2);
            int ans;
            switch (i) {
                case 0:
                    ans = Constants.S1[x][y];
                    S_boxes.append(Structure.stringToBinary(String.valueOf(ans), 4));
                    break;
                case 6:
                    ans = Constants.S2[x][y];
                    S_boxes.append(Structure.stringToBinary(String.valueOf(ans), 4));
                    break;
                case 12:
                    ans = Constants.S3[x][y];
                    S_boxes.append(Structure.stringToBinary(String.valueOf(ans), 4));
                    break;
                case 18:
                    ans = Constants.S4[x][y];
                    S_boxes.append(Structure.stringToBinary(String.valueOf(ans), 4));
                    break;
                case 24:
                    ans = Constants.S5[x][y];
                    S_boxes.append(Structure.stringToBinary(String.valueOf(ans), 4));
                    break;
                case 30:
                    ans = Constants.S6[x][y];
                    S_boxes.append(Structure.stringToBinary(String.valueOf(ans), 4));
                    break;
                case 36:
                    ans = Constants.S7[x][y];
                    S_boxes.append(Structure.stringToBinary(String.valueOf(ans), 4));
                    break;
                case 42:
                    ans = Constants.S8[x][y];
                    S_boxes.append(Structure.stringToBinary(String.valueOf(ans), 4));
                    break;
            }
        }
        StringBuilder perm = new StringBuilder();
        for (int i = 0; i < Constants.permFunction.length; i++) {
            perm.append(S_boxes.charAt(Constants.permFunction[i] - 1));
        }
        StringBuilder permXor = new StringBuilder();
        for (int i = 0; i < perm.length(); i++) {
            int x = Integer.parseInt(perm.substring(i, i + 1)) ^
                    Integer.parseInt(left.substring(i, i + 1));
            permXor.append(x);
        }
        newRight = permXor.toString();
        return right + newRight;
    }

    public static String encrypt(String text, String key) {
        List<String> listKeys = roundKey(key);
        StringBuilder cipherText = new StringBuilder();
        for (int i = 0; i < text.length(); i += 64) {
            String subText = text.substring(i, i + 64);
            StringBuilder tmp = new StringBuilder();
            for (int j = 0; j < Constants.IP.length; j++) {
                tmp.append(subText.charAt(Constants.IP[j] - 1));
            }
            subText = tmp.toString();
            for (int j = 0; j < 16; j++) {
                subText = feistelFunction(subText, listKeys.get(j));
            }
            String left = subText.substring(0, 32);
            String right = subText.substring(32);
            subText = right + left;
            tmp = new StringBuilder();
            for (int j = 0; j < Constants.inverseIP.length; j++) {
                tmp.append(subText.charAt(Constants.inverseIP[j] - 1));
            }
            subText = tmp.toString();
            cipherText.append(subText);
        }
        return cipherText.toString();
    }

    public static String decrypt(String text, String key) {
        List<String> listKeys = roundKey(key);
        StringBuilder cipherText = new StringBuilder();
        for (int i = 0; i < text.length(); i += 64) {
            String subText = text.substring(i, i + 64);
            StringBuilder tmp = new StringBuilder();
            for (int j = 0; j < Constants.IP.length; j++) {
                tmp.append(subText.charAt(Constants.IP[j] - 1));
            }
            subText = tmp.toString();
            for (int j = 0; j < 16; j++) {
                subText = feistelFunction(subText, listKeys.get(15 - j));
            }
            String left = subText.substring(0, 32);
            String right = subText.substring(32);
            subText = right + left;
            tmp = new StringBuilder();
            for (int j = 0; j < Constants.inverseIP.length; j++) {
                tmp.append(subText.charAt(Constants.inverseIP[j] - 1));
            }
            subText = tmp.toString();
            cipherText.append(subText);
        }
        return cipherText.toString();

    }
}
