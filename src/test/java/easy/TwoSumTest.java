package easy;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

class TwoSumTest {

    @ParameterizedTest
    @MethodSource("provideTestCases")
    void twoSum_ShouldFindCorrectIndices(int[] nums, int target, int[] expected) {
        int[] result = TwoSum.twoSum(nums, target);
        
        if (expected == null) {
            Assertions.assertNull(result);
        } else {
            Assertions.assertNotNull(result);
            Assertions.assertEquals(2, result.length);
            // Since we're sorting the array, we can only check if the values at these indices sum up to target
            Assertions.assertEquals(target, nums[result[0]] + nums[result[1]]);
        }
    }

    private static Stream<Arguments> provideTestCases() {
        return Stream.of(
            // Basic test case
            Arguments.of(new int[]{2, 7, 11, 15}, 9, new int[]{0, 1}),
            
            // Test case with negative numbers
            Arguments.of(new int[]{-3, 4, 3, 90}, 0, new int[]{0, 2}),
            
            // Test case with duplicate numbers
            Arguments.of(new int[]{3, 3}, 6, new int[]{0, 1}),
            
            // Test case with no solution
            Arguments.of(new int[]{2, 7, 11, 15}, 100, null),
            
            // Test case with larger numbers
            Arguments.of(new int[]{1000, 2000, 3000}, 5000, new int[]{1, 2}),
            
            // Test case with minimum array size
            Arguments.of(new int[]{1, 2}, 3, new int[]{0, 1}),
            
            // Test case with zero
            Arguments.of(new int[]{0, 4, 3, 0}, 0, new int[]{0, 3})
        );
    }

    @Test
    void twoSum_WithEmptyArray_ShouldReturnNull() {
        int[] result = TwoSum.twoSum(new int[]{}, 1);
        Assertions.assertNull(result);
    }

    @Test
    void twoSum_WithSingleElement_ShouldReturnNull() {
        int[] result = TwoSum.twoSum(new int[]{1}, 1);
        Assertions.assertNull(result);
    }

    @Test
    void twoSum_WithAllNegativeNumbers_ShouldWork() {
        int[] nums = {-5, -2, -3, -1};
        int target = -4;
        int[] result = TwoSum.twoSum(nums, target);
        Assertions.assertNotNull(result);
        Assertions.assertEquals(target, nums[result[0]] + nums[result[1]]);
    }
}