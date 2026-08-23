class Solution {
    public boolean sumGame(String num) {
        int n = num.length();
        int half = n / 2;

        int diff = 0;
        int q1 = 0;
        int q2 = 0;

        for (int i = 0; i < half; i++) {
            char c = num.charAt(i);

            if (c == '?') {
                q1++;
            } else {
                diff += c - '0';
            }
        }

        for (int i = half; i < n; i++) {
            char c = num.charAt(i);

            if (c == '?') {
                q2++;
            } else {
                diff -= c - '0';
            }
        }

        // Odd number of '?' -> Alice wins
        if ((q1 + q2) % 2 == 1) {
            return true;
        }

        // Bob can force equality only in this exact case
        return diff != 9 * (q2 - q1) / 2;
    }
}