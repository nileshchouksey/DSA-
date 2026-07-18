class Solution {
    public int findGCD(int[] nums) {

        int min = nums[0];
        int max = nums[0];

        for (int num : nums) {
            min = Math.min(min, num);
            max = Math.max(max, num);
        }

        while (min != 0) {
            int temp = min;
            min = max % min;
            max = temp;
        }

        return max;
    }
}