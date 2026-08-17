class Solution {
    public int stoneGameV(int[] stoneValue) {
        int n = stoneValue.length;

        // Prefix sum
        int[] prefix = new int[n + 1];

        for (int i = 0; i < n; i++) {
            prefix[i + 1] = prefix[i] + stoneValue[i];
        }

        Integer[][] dp = new Integer[n][n];

        return solve(0, n - 1, stoneValue, prefix, dp);
    }

    private int solve(int l, int r, int[] arr, int[] prefix, Integer[][] dp) {

        // Only one stone
        if (l == r) {
            return 0;
        }

        if (dp[l][r] != null) {
            return dp[l][r];
        }

        int ans = 0;

        // Try every possible split
        for (int k = l; k < r; k++) {

            int leftSum = prefix[k + 1] - prefix[l];
            int rightSum = prefix[r + 1] - prefix[k + 1];

            if (leftSum < rightSum) {
                // Right side is thrown away
                ans = Math.max(
                    ans,
                    leftSum + solve(l, k, arr, prefix, dp)
                );
            }
            else if (leftSum > rightSum) {
                // Left side is thrown away
                ans = Math.max(
                    ans,
                    rightSum + solve(k + 1, r, arr, prefix, dp)
                );
            }
            else {
                // Equal -> Alice can choose either side
                ans = Math.max(
                    ans,
                    Math.max(
                        leftSum + solve(l, k, arr, prefix, dp),
                        rightSum + solve(k + 1, r, arr, prefix, dp)
                    )
                );
            }
        }

        return dp[l][r] = ans;
    }
}