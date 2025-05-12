package RSA;

import java.io.ByteArrayOutputStream;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.util.*;

public class RSA {
    BigInteger p, q, n, phi, e, d;
    int minBits = 23, maxBits = 75;

    public RSA() {
        p = generateRandomBigInteger(minBits, maxBits);
        q = generateRandomBigInteger(minBits, maxBits);
        while (p.compareTo(q) == 0) {
            p = generateRandomBigInteger(minBits, maxBits); //8yr wa7d fehom.
        }
        while (!(RabinMillar.probablyPrime(p, 10))) {
            p = generateRandomBigInteger(minBits, maxBits);
        }
        while (!(RabinMillar.probablyPrime(q, 10))) {
            q = generateRandomBigInteger(minBits, maxBits);
        }

        n = p.multiply(q);
        phi = p.subtract(BigInteger.ONE).multiply(q.subtract(BigInteger.ONE));

        // Generate random BigInteger a in range [1, phi - 1] with gcd = 1;
        do {
            SecureRandom random = new SecureRandom();
            do {
                e = new BigInteger(p.bitLength(), random);
            } while (e.compareTo(BigInteger.ZERO) <= 0 || e.compareTo(phi) >= 0);
        } while (e.gcd(phi).compareTo(BigInteger.ONE) != 0);

        d = RabinMillar.extendedGCD(e, phi)[1];

        System.out.println("p: " + p);
        System.out.println("q: " + q);
        System.out.println("n: " + n);
        System.out.println("phi: " + phi);
        System.out.println("e: " + e);
        System.out.println("d: " + d);
    }


    public static BigInteger generateRandomBigInteger(int minBits, int maxBits) {

        Random random = new Random();
        int bitLength = minBits + random.nextInt(maxBits - minBits + 1);

        BigInteger result;
        do {
            result = new BigInteger(bitLength, random);
        } while (result.bitLength() < minBits);

        return result;
    }

    public String encrypt(String plainText) {
        int blockSize = (n.bitLength() - 1) / 8;
        byte[] inputBytes = plainText.getBytes(StandardCharsets.UTF_8);

        List<String> encryptedBlocks = new ArrayList<>();
        int base64Length = Base64.getEncoder().encodeToString(n.toByteArray()).length();
        // safe block length

        for (int i = 0; i < inputBytes.length; i += blockSize) {
            int end = Math.min(i + blockSize, inputBytes.length);
            byte[] block = Arrays.copyOfRange(inputBytes, i, end);
            BigInteger blockInt = new BigInteger(block);
            BigInteger encrypted = RabinMillar.modExp(blockInt, e, n);
            StringBuilder base64Block = new StringBuilder(Base64.getEncoder().
                    encodeToString(encrypted.toByteArray()));

            // Pad block to fixed length
            while (base64Block.length() < base64Length) {
                base64Block.insert(0, "0");
            }

            encryptedBlocks.add(base64Block.toString());
        }

        return String.join("", encryptedBlocks);
    }


    public String decrypt(String cipherText) {
        int base64Length = Base64.getEncoder().encodeToString(n.toByteArray()).length();

        List<String> base64Blocks = new ArrayList<>();
        for (int i = 0; i < cipherText.length(); i += base64Length) {
            base64Blocks.add(cipherText.substring(i, i + base64Length));
        }

        ByteArrayOutputStream output = new ByteArrayOutputStream();

        for (String block : base64Blocks) {
            String trimmedBlock = block.replaceFirst("^0+(?!$)",
                    ""); // remove leading 0s

            byte[] encryptedBytes = Base64.getDecoder().decode(trimmedBlock);
            BigInteger encryptedInt = new BigInteger(encryptedBytes);
            BigInteger decryptedInt = RabinMillar.modExp(encryptedInt, d, n);

            byte[] blockBytes = decryptedInt.toByteArray();
            if (blockBytes.length > 1 && blockBytes[0] == 0) {
                blockBytes = Arrays.copyOfRange(blockBytes, 1, blockBytes.length);
            }

            output.write(blockBytes, 0, blockBytes.length);
        }

        return output.toString(StandardCharsets.UTF_8);
    }
}