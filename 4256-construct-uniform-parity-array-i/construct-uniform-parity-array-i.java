class Solution {
    public boolean uniformArray(int[] nums1) {
        int n = nums1.length;

        if (n == 1) {
            return true;
        }

        boolean hasEven = false;
        boolean hasOdd = false;

        for (int x : nums1) {
            if (x % 2 == 0) {
                hasEven = true;
            } else {
                hasOdd = true;
            }
        }

        // If both parities exist, we can make every element odd
        // by subtracting an element of opposite parity.
        if (hasEven && hasOdd) {
            return true;
        }

        // If all elements have the same parity, we can make
        // every element even by subtracting another element.
        return true;
    }
}