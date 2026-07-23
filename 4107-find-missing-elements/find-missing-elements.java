import java.util.*;

class Solution {
    public List<Integer> findMissingElements(int[] arr) {
        
        List<Integer> result = new ArrayList<>();

        // Sort the array
        Arrays.sort(arr);

        int min = arr[0];
        int max = arr[arr.length - 1];

        int i = 0;

        while (min <= max) {

            // If min exists in array
            if (i < arr.length && arr[i] == min) {

                // Skip duplicates
                while (i < arr.length && arr[i] == min) {
                    i++;
                }

                min++;
            } 
            else {
                // min is not present, so it is missing
                result.add(min);
                min++;
            }
        }

        return result;
    }
}