package RSA;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.HashSet;
import java.util.Set;

public class RabinMillar {

    public static boolean probablyPrime(BigInteger p, int n) {
        if (p.equals(BigInteger.ZERO) || p.equals(BigInteger.ONE) ||
                (p.remainder(BigInteger.valueOf(2)).
                        equals(BigInteger.ZERO) && p.compareTo(BigInteger.valueOf(2)) > 0)
                || p.compareTo(BigInteger.ZERO) < 0) {
            return false;
        }

        BigInteger k = BigInteger.ZERO;
        BigInteger mod = new BigInteger(p.subtract(BigInteger.ONE).toString());
        BigInteger q = mod;

        while (q.remainder(BigInteger.valueOf(2)).equals(BigInteger.ZERO)) {
            q = q.divide(BigInteger.valueOf(2));
            k = k.add(BigInteger.ONE);
        }

        Set<BigInteger> witnesses = new HashSet<>();
        SecureRandom random = new SecureRandom();
        while(witnesses.size() < n) {
            BigInteger a;
            do {
                a = new BigInteger(p.bitLength(), random);
            } while (a.compareTo(BigInteger.ZERO) <= 0 || a.compareTo(p) >= 0);
            witnesses.add(a);
        }

        for (BigInteger a : witnesses) {
            // a^q mod p
            BigInteger x = modExp(a, q, p);

            if (x.equals(BigInteger.ONE) || x.equals(mod)) {
                continue;
            }

            boolean passed = false;
            for (BigInteger j = BigInteger.ONE; j.compareTo(k) < 0; j = j.add(BigInteger.ONE)) {
                BigInteger exponent = bigIntegerPow(BigInteger.TWO,j).multiply(q);
                x = modExp(a, exponent, p);
                if (x.equals(mod)) {
                    passed = true;
                    break;
                }
            }

            if (!passed) {
                return false;
            }
        }

        return true;
    }

    public static BigInteger modExp(BigInteger base, BigInteger exponent, BigInteger modulus) {
        BigInteger result = BigInteger.ONE;
        base = base.mod(modulus); // base < modulus

        while (exponent.compareTo(BigInteger.ZERO) > 0) {
            if (exponent.testBit(0)) {
                result = result.multiply(base).mod(modulus);
            }
            base = base.multiply(base).mod(modulus); // base = base^2 mod m
            exponent = exponent.shiftRight(1); // exponent = exponent / 2
        }

        return result;
    }

    public static BigInteger bigIntegerPow(BigInteger base, BigInteger exponent) {
        BigInteger result = BigInteger.ONE;
        while (exponent.signum() > 0) {
            if (exponent.testBit(0)) {
                result = result.multiply(base);
            }
            base = base.multiply(base);
            exponent = exponent.shiftRight(1);
        }
        return result;
    }

    public static BigInteger[] extendedGCD(BigInteger a, BigInteger b) {
        // Returns array: [gcd, x, y] such that a*x + b*y = gcd, byb2a inverse law gcd = 1;
        //consider a^-1 mod b law da 7sl.
        BigInteger x0 = BigInteger.ONE;
        BigInteger y0 = BigInteger.ZERO;
        BigInteger x1 = BigInteger.ZERO;
        BigInteger y1 = BigInteger.ONE;

        BigInteger originalB = b; // keep a copy for final normalization

        while (!b.equals(BigInteger.ZERO)) {
            BigInteger[] divMod = a.divideAndRemainder(b);
            BigInteger q = divMod[0];
            BigInteger r = divMod[1];

            a = b;
            b = r;

            BigInteger tempX = x1;
            x1 = x0.subtract(q.multiply(x1));
            x0 = tempX;

            BigInteger tempY = y1;
            y1 = y0.subtract(q.multiply(y1));
            y0 = tempY;
        }

        // Normalize x0 to be positive de el inverse mo4 3yzeno negative.
        if (x0.compareTo(BigInteger.ZERO) < 0) {
            x0 = x0.mod(originalB);
        }

        return new BigInteger[]{a, x0, y0};
    }
}
