package easy;

import java.util.Arrays;

public class TwoSum {
    public static void main(String[] args) {
        int arr[] = {2,7,11,15};
        System.out.println(Arrays.toString(twoSum(arr, 9)));
    }

    public static int[] twoSum(int[] nums, int target) {
        Arrays.sort(nums);
        int length = nums.length;
        int answer[] = null;

        for(int i= 0; i< length; i++) {
            if(nums[i] < target) {
                int index = Arrays.binarySearch(nums, i+1, length, target-nums[i]);
                if (index >=0 ) {
                    answer = new int[]{i, index};
                    return answer;
                }
            }
        }
        return answer;
    }
}

