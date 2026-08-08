class Solution {
    public int[] validSequence(String word1, String word2) {
        int n = word1.length(), m = word2.length();
        // suf[i] = longest suffix of word2 matchable as a subsequence of word1[i..n-1]
        int[] suf = new int[n + 1];
        int j = m - 1;
        for (int i = n - 1; i >= 0; i--) {
            if (j >= 0 && word1.charAt(i) == word2.charAt(j)) {
                j--;
            }
            suf[i] = m - 1 - j; // count matched so far from the end
        }

        int[] ans = new int[m];
        int p = 0;
        boolean mismatchUsed = false;

        for (int i = 0; i < n && p < m; i++) {
            if (word1.charAt(i) == word2.charAt(p)) {
                ans[p] = i;
                p++;
            } else if (!mismatchUsed) {
                int remaining = m - p - 1; // chars of word2 still needed after this one
                if (suf[i + 1] >= remaining) {
                    ans[p] = i;
                    p++;
                    mismatchUsed = true;
                }
            }
        }

        if (p != m) {
            return new int[0];
        }
        return ans;
    }
}