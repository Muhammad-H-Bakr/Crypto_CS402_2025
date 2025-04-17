package DES;

public class Structure {


    public static String padding(String s, int blockSize) {
        int padding = s.length() % blockSize;
        if (padding == 0) {
            return s;
        } else {
            return s + "*".repeat(blockSize - padding);
        }
    }

    public static String stringToBinary(String s, int blockSize) {
        StringBuilder list = new StringBuilder();
        for (char c : s.toCharArray()) {
            String b = Integer.toBinaryString(c);
            if (b.length() < blockSize) {
                b = "0".repeat(blockSize - b.length()) + b;
            }
            list.append(b);
        }
        return list.toString();
    }

    public static String ASCII2String(String s) {
        StringBuilder list = new StringBuilder();
        for (int i = 0; i < s.length(); i += 8) {
            String c = s.substring(i, i + 8);
            list.append((char) Integer.parseInt(c, 2));
        }
        return list.toString();
    }
}
