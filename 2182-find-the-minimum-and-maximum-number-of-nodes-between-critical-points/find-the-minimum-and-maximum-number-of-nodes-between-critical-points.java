class Solution {
    public int[] nodesBetweenCriticalPoints(ListNode head) {
        int first = -1;
        int prevCritical = -1;
        int minDistance = Integer.MAX_VALUE;
        int maxDistance = -1;

        ListNode prev = head;
        ListNode curr = head.next;

        int index = 1;

        while (curr != null && curr.next != null) {
            ListNode next = curr.next;

            boolean isCritical =
                (curr.val > prev.val && curr.val > next.val) ||
                (curr.val < prev.val && curr.val < next.val);

            if (isCritical) {
                if (first == -1) {
                    first = index;
                } else {
                    minDistance = Math.min(
                        minDistance,
                        index - prevCritical
                    );

                    maxDistance = index - first;
                }

                prevCritical = index;
            }

            prev = curr;
            curr = next;
            index++;
        }

        if (maxDistance == -1) {
            return new int[]{-1, -1};
        }

        return new int[]{minDistance, maxDistance};
    }
}