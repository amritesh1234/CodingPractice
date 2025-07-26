package easy;

import java.util.Arrays;

public class TwoSumWithIndices {
    // Inner class to represent the tuple of value and original index
    private static class Pair implements Comparable<Pair> {
        int value;
        int originalIndex;
        
        Pair(int value, int originalIndex) {
            this.value = value;
            this.originalIndex = originalIndex;
        }
        
        @Override
        public int compareTo(Pair other) {
            return Integer.compare(this.value, other.value);
        }
    }
    
    public static void main(String[] args) {
        int arr[] = {2,7,11,15};
        System.out.println(Arrays.toString(twoSum(arr, 9)));
    }

    public static int[] twoSum(int[] nums, int target) {
        int length = nums.length;
        Pair[] pairs = new Pair[length];
        
        // Create pairs with original indices
        for (int i = 0; i < length; i++) {
            pairs[i] = new Pair(nums[i], i);
        }
        
        // Sort the pairs array based on values
        Arrays.sort(pairs);
        
        for (int i = 0; i < length; i++) {
            if (pairs[i].value < target) {
                // Binary search for the complement value
                int complement = target - pairs[i].value;
                int left = i + 1;
                int right = length - 1;
                
                while (left <= right) {
                    int mid = left + (right - left) / 2;
                    if (pairs[mid].value == complement) {
                        // Return original indices stored in the pairs
                        return new int[]{pairs[i].originalIndex, pairs[mid].originalIndex};
                    }
                    if (pairs[mid].value < complement) {
                        left = mid + 1;
                    } else {
                        right = mid - 1;
                    }
                }
            }
        }
        return null;
    }
}
