class Solution {
    public boolean uniformArray(int[] nums1) {
        int minOdd = Integer.MAX_VALUE;

        for (int x : nums1) {
            if ((x & 1) == 1) {
                minOdd = Math.min(minOdd, x);
            }
        }

        // Make all elements odd.
        // Every even element needs a smaller odd element.
        boolean canOdd = true;

        for (int x : nums1) {
            if ((x & 1) == 0 && minOdd >= x) {
                canOdd = false;
                break;
            }
        }

        if (canOdd) {
            return true;
        }

        // Make all elements even.
        // Every odd element needs another smaller odd element.
        boolean canEven = true;

        for (int x : nums1) {
            if ((x & 1) == 1 && minOdd >= x) {
                canEven = false;
                break;
            }
        }

        return canEven;
    }
}