import java.util.*;

class Solution {

    int[] allowedPrimes = {2, 3, 5, 7};

    // contribution of each digit
    int[][] contrib = {
            {0,0,0,0},
            {0,0,0,0},
            {1,0,0,0},
            {0,1,0,0},
            {2,0,0,0},
            {0,0,1,0},
            {1,1,0,0},
            {0,0,0,1},
            {3,0,0,0},
            {0,2,0,0}
    };

    int maxE2, maxE3, maxE5, maxE7;
    int[][][][] dp;

    private void buildDP(int E2, int E3, int E5, int E7) {

        maxE2 = E2;
        maxE3 = E3;
        maxE5 = E5;
        maxE7 = E7;

        dp = new int[E2 + 1][E3 + 1][E5 + 1][E7 + 1];

        for (int a = 0; a <= E2; a++)
            for (int b = 0; b <= E3; b++)
                for (int c = 0; c <= E5; c++)
                    Arrays.fill(dp[a][b][c], Integer.MAX_VALUE);

        dp[0][0][0][0] = 0;

        for (int s = 1; s <= E2 + E3 + E5 + E7; s++) {

            for (int e2 = 0; e2 <= E2; e2++)
                for (int e3 = 0; e3 <= E3; e3++)
                    for (int e5 = 0; e5 <= E5; e5++)
                        for (int e7 = 0; e7 <= E7; e7++) {

                            if (e2 + e3 + e5 + e7 != s)
                                continue;

                            int best = Integer.MAX_VALUE;

                            for (int d = 2; d <= 9; d++) {

                                int ne2 = Math.max(0, e2 - contrib[d][0]);
                                int ne3 = Math.max(0, e3 - contrib[d][1]);
                                int ne5 = Math.max(0, e5 - contrib[d][2]);
                                int ne7 = Math.max(0, e7 - contrib[d][3]);

                                if (dp[ne2][ne3][ne5][ne7] != Integer.MAX_VALUE) {
                                    best = Math.min(best,
                                            dp[ne2][ne3][ne5][ne7] + 1);
                                }
                            }

                            dp[e2][e3][e5][e7] = best;
                        }
        }
    }

    private int minDigits(int e2, int e3, int e5, int e7) {
        return dp[Math.min(e2, maxE2)]
                 [Math.min(e3, maxE3)]
                 [Math.min(e5, maxE5)]
                 [Math.min(e7, maxE7)];
    }

    private void applyDigit(int[] freq, int d) {
        freq[2] = Math.max(0, freq[2] - contrib[d][0]);
        freq[3] = Math.max(0, freq[3] - contrib[d][1]);
        freq[5] = Math.max(0, freq[5] - contrib[d][2]);
        freq[7] = Math.max(0, freq[7] - contrib[d][3]);
    }

    private boolean isReqMet(int[] freq) {
        for (int p : allowedPrimes) {
            if (freq[p] > 0)
                return false;
        }
        return true;
    }

    private String greedyFill(int[] freq, int L) {

        StringBuilder sb = new StringBuilder();

        for (int pos = 0; pos < L; pos++) {

            int slotsAfter = L - pos - 1;

            for (int d = 1; d <= 9; d++) {

                int[] nf = freq.clone();

                applyDigit(nf, d);

                if (minDigits(nf[2], nf[3], nf[5], nf[7]) <= slotsAfter) {

                    freq = nf;
                    sb.append((char) ('0' + d));
                    break;
                }
            }
        }

        return sb.toString();
    }

    public String smallestNumber(String num, long t) {

        int[] freqFull = new int[10];

        for (int p : allowedPrimes) {
            while (t % p == 0) {
                freqFull[p]++;
                t /= p;
            }
        }

        if (t > 1)
            return "-1";

        buildDP(freqFull[2], freqFull[3], freqFull[5], freqFull[7]);

        int len = num.length();

        boolean hasZero = false;

        for (int i = 0; i < len; i++) {
            if (num.charAt(i) == '0') {
                hasZero = true;
                break;
            }
        }

        if (!hasZero) {

            int[] freq = freqFull.clone();

            for (int i = 0; i < len; i++)
                applyDigit(freq, num.charAt(i) - '0');

            if (isReqMet(freq))
                return num;
        }

        int[][] prefixFreq = new int[len + 1][10];

        System.arraycopy(freqFull, 0, prefixFreq[0], 0, 10);

        for (int i = 0; i < len; i++) {

            prefixFreq[i + 1] = prefixFreq[i].clone();

            if (num.charAt(i) != '0')
                applyDigit(prefixFreq[i + 1], num.charAt(i) - '0');
        }

        int limit = hasZero ? num.indexOf('0') : len - 1;

        String answer = "";

        for (int pos = limit; pos >= 0 && answer.isEmpty(); pos--) {

            int[] freqBefore = prefixFreq[pos];

            int origDigit = num.charAt(pos) - '0';

            for (int d = origDigit + 1; d <= 9; d++) {

                int[] nf = freqBefore.clone();

                applyDigit(nf, d);

                int slotsAfter = len - pos - 1;

                if (minDigits(nf[2], nf[3], nf[5], nf[7]) <= slotsAfter) {

                    answer = num.substring(0, pos)
                            + (char) ('0' + d)
                            + greedyFill(nf, slotsAfter);

                    break;
                }
            }
        }

        if (!answer.isEmpty())
            return answer;

        int totalNeeded = minDigits(freqFull[2], freqFull[3], freqFull[5], freqFull[7]);

        int L = Math.max(len + 1, totalNeeded);

        return greedyFill(freqFull, L);
    }
}