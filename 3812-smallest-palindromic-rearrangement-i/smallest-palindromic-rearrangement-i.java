class Solution {
    public String smallestPalindrome(String s) {
        int[] freq = new int[26];

        // Count frequency
        for (char ch : s.toCharArray()) {
            freq[ch - 'a']++;
        }

        StringBuilder firstHalf = new StringBuilder();
        String middle = "";

        // Build first half and find middle character
        for (int i = 0; i < 26; i++) {
            for (int j = 0; j < freq[i] / 2; j++) {
                firstHalf.append((char) ('a' + i));
            }

            if (freq[i] % 2 == 1) {
                middle = String.valueOf((char) ('a' + i));
            }
        }

        StringBuilder secondHalf = new StringBuilder(firstHalf).reverse();

        return firstHalf.toString() + middle + secondHalf.toString();
    }
}