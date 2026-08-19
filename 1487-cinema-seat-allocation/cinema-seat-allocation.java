class Solution {
    public int maxNumberOfFamilies(int n, int[][] reservedSeats) {
        HashMap<Integer, Integer> map = new HashMap<>();

        // Store only relevant seats: 2 to 9
        for (int[] seat : reservedSeats) {
            int row = seat[0];
            int col = seat[1];

            if (col >= 2 && col <= 9) {
                map.put(row, map.getOrDefault(row, 0) | (1 << col));
            }
        }

        // Initially every row can fit 2 families
        int ans = (n - map.size()) * 2;

        for (int mask : map.values()) {
            boolean left = (mask & ((1 << 2) | (1 << 3) | 
                                   (1 << 4) | (1 << 5))) == 0;

            boolean middle = (mask & ((1 << 4) | (1 << 5) | 
                                      (1 << 6) | (1 << 7))) == 0;

            boolean right = (mask & ((1 << 6) | (1 << 7) | 
                                     (1 << 8) | (1 << 9))) == 0;

            if (left && right) {
                ans += 2;
            } else if (left || middle || right) {
                ans += 1;
            }
        }

        return ans;
    }
}