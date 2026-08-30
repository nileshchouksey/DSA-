class Solution {
    public int minimumDeletions(int[] nums) {
        int n = nums.length;

        int minIdx = 0;
        int maxIdx = 0;

        for (int i = 1; i < n; i++) {
            if (nums[i] < nums[minIdx]) {
                minIdx = i;
            }
            if (nums[i] > nums[maxIdx]) {
                maxIdx = i;
            }
        }

        int left = Math.min(minIdx, maxIdx);
        int right = Math.max(minIdx, maxIdx);

        // Case 1: remove both from front
        int fromFront = right + 1;

        // Case 2: remove both from back
        int fromBack = n - left;

        // Case 3: one from front, one from back
        int fromBothSides = (left + 1) + (n - right);

        return Math.min(fromFront,
                Math.min(fromBack, fromBothSides));
    }
}