class Solution {
    public String lexGreaterPermutation(String s, String target) {
        int n = s.length();
        int[] freq = new int[26];

        for (char c : s.toCharArray()) {
            freq[c - 'a']++;
        }

        // Try every possible position from right to left
        for (int i = n - 1; i >= 0; i--) {

            int[] cnt = freq.clone();

            // Match target's prefix [0 ... i-1]
            boolean possible = true;

            for (int j = 0; j < i; j++) {
                int x = target.charAt(j) - 'a';

                if (cnt[x] == 0) {
                    possible = false;
                    break;
                }

                cnt[x]--;
            }

            if (!possible) {
                continue;
            }

            // Find the smallest character greater than target[i]
            int x = target.charAt(i) - 'a';

            for (int c = x + 1; c < 26; c++) {

                if (cnt[c] > 0) {

                    StringBuilder ans = new StringBuilder();

                    // Keep prefix equal to target
                    for (int j = 0; j < i; j++) {
                        ans.append(target.charAt(j));
                    }

                    // Make the string greater here
                    ans.append((char) ('a' + c));
                    cnt[c]--;

                    // Put remaining characters in sorted order
                    for (int k = 0; k < 26; k++) {
                        while (cnt[k] > 0) {
                            ans.append((char) ('a' + k));
                            cnt[k]--;
                        }
                    }

                    return ans.toString();
                }
            }
        }

        return "";
    }
}