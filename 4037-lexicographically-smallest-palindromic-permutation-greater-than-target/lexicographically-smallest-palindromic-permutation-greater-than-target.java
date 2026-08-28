class Solution {
    public String lexPalindromicPermutation(String s, String target) {
        int n = s.length();
        int[] freq = new int[26];

        for (char ch : s.toCharArray()) {
            freq[ch - 'a']++;
        }

        // Check whether a palindrome is possible
        int odd = 0;
        char middle = 0;

        for (int i = 0; i < 26; i++) {
            if ((freq[i] & 1) == 1) {
                odd++;
                middle = (char) ('a' + i);
            }
        }

        if (odd > 1) {
            return "";
        }

        // Frequencies for the left half
        int[] cnt = new int[26];
        for (int i = 0; i < 26; i++) {
            cnt[i] = freq[i] / 2;
        }

        int half = n / 2;
        char[] left = new char[half];

        // Build the smallest left half which gives palindrome > target
        for (int i = 0; i < half; i++) {
            int t = target.charAt(i) - 'a';

            // First try to keep the same character
            if (cnt[t] > 0) {
                left[i] = target.charAt(i);
                cnt[t]--;
            } else {
                // Can't stay equal.
                // Try the smallest character greater than target[i].
                for (int c = t + 1; c < 26; c++) {
                    if (cnt[c] > 0) {
                        left[i] = (char) ('a' + c);
                        cnt[c]--;

                        fillSmallest(left, i + 1, cnt);

                        return makePalindrome(left, middle, n);
                    }
                }

                // No greater character here.
                // We must backtrack.
                for (int j = i - 1; j >= 0; j--) {
                    int old = left[j] - 'a';
                    cnt[old]++;

                    int wanted = target.charAt(j) - 'a';

                    for (int c = wanted + 1; c < 26; c++) {
                        if (cnt[c] > 0) {
                            left[j] = (char) ('a' + c);
                            cnt[c]--;

                            fillSmallest(left, j + 1, cnt);

                            return makePalindrome(left, middle, n);
                        }
                    }
                }

                return "";
            }
        }

        // The first half is equal to target's first half.
        // The palindrome may still be greater because of the second half.
        String palindrome = makePalindrome(left, middle, n);

        if (palindrome.compareTo(target) > 0) {
            return palindrome;
        }

        // Need the next larger left half.
        for (int i = half - 1; i >= 0; i--) {
            int old = left[i] - 'a';
            cnt[old]++;

            int wanted = target.charAt(i) - 'a';

            for (int c = wanted + 1; c < 26; c++) {
                if (cnt[c] > 0) {
                    left[i] = (char) ('a' + c);
                    cnt[c]--;

                    fillSmallest(left, i + 1, cnt);

                    return makePalindrome(left, middle, n);
                }
            }
        }

        return "";
    }

    private void fillSmallest(char[] left, int start, int[] cnt) {
        int p = start;

        for (int c = 0; c < 26; c++) {
            while (cnt[c] > 0) {
                left[p++] = (char) ('a' + c);
                cnt[c]--;
            }
        }
    }

    private String makePalindrome(char[] left, char middle, int n) {
        char[] ans = new char[n];
        int half = n / 2;

        for (int i = 0; i < half; i++) {
            ans[i] = left[i];
            ans[n - 1 - i] = left[i];
        }

        if (n % 2 == 1) {
            ans[half] = middle;
        }

        return new String(ans);
    }
}