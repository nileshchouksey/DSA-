import java.util.*;

class Solution {

    long LIMIT;

    public String smallestPalindrome(String s, int k) {
        LIMIT = k;

        int[] freq = new int[26];

        for (char c : s.toCharArray())
            freq[c - 'a']++;

        StringBuilder mid = new StringBuilder();

        int halfLen = 0;

        for (int i = 0; i < 26; i++) {
            if ((freq[i] & 1) == 1)
                mid.append((char) ('a' + i));

            freq[i] /= 2;
            halfLen += freq[i];
        }

        if (countWays(freq) < k)
            return "";

        StringBuilder half = new StringBuilder();

        for (int pos = 0; pos < halfLen; pos++) {

            for (int c = 0; c < 26; c++) {

                if (freq[c] == 0)
                    continue;

                freq[c]--;

                long ways = countWays(freq);

                if (ways >= k) {
                    half.append((char) ('a' + c));
                    break;
                }

                k -= ways;
                freq[c]++;
            }
        }

        String left = half.toString();

        return left + mid.toString() + new StringBuilder(left).reverse();
    }

    private long countWays(int[] cnt) {

        int total = 0;
        for (int x : cnt)
            total += x;

        long ans = 1;
        int rem = total;

        for (int x : cnt) {

            if (x == 0)
                continue;

            ans = multiply(ans, nCr(rem, x));

            if (ans >= LIMIT)
                return LIMIT;

            rem -= x;
        }

        return ans;
    }

    private long nCr(int n, int r) {

        if (r > n)
            return 0;

        r = Math.min(r, n - r);

        long res = 1;

        for (int i = 1; i <= r; i++) {

            long num = n - r + i;
            long den = i;

            long g = gcd(num, den);
            num /= g;
            den /= g;

            g = gcd(res, den);
            res /= g;
            den /= g;

            if (res > LIMIT / num)
                return LIMIT;

            res *= num;

            res /= den;

            if (res >= LIMIT)
                return LIMIT;
        }

        return res;
    }

    private long multiply(long a, long b) {

        if (a == 0 || b == 0)
            return 0;

        if (a >= LIMIT || b >= LIMIT)
            return LIMIT;

        if (a > LIMIT / b)
            return LIMIT;

        long ans = a * b;

        return Math.min(ans, LIMIT);
    }

    private long gcd(long a, long b) {

        while (b != 0) {
            long t = a % b;
            a = b;
            b = t;
        }

        return a;
    }
}