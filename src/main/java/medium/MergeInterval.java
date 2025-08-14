package medium;

import java.util.Arrays;

class MergeInterval {
    public int[][] insert(int[][] intervals, int[] newInterval) {
        if (intervals.length == 0) {
            return new int[][]{newInterval};
        }
        boolean newIntervalUsed = false;
        int[][] answer = new int[intervals.length + 1][2];
        int index = 0;
        if (newInterval[1] < intervals[0][0]) {
            answer[index++] = newInterval;
            System.arraycopy(intervals, 0, answer, 1, intervals.length);
            return Arrays.copyOf(answer, intervals.length + 1);
        } else if (intervals[0][1] >= newInterval[0]) {
            answer[index++] = new int[]{Math.min(intervals[0][0], newInterval[0]), Math.max(intervals[0][1], newInterval[1])};
            newIntervalUsed = true;
        }
        if (index == 0) {
            answer[index++] = intervals[0];
        }
        for (int i = index; i < intervals.length; i++) {
            int[] lastElement = answer[index - 1];
            if (lastElement[1] >= intervals[i][0]) {
                answer[index - 1] = new int[]{Math.min(lastElement[0], intervals[i][0]), Math.max(lastElement[1], intervals[i][1])};
            } else if (((lastElement[1] >= newInterval[0] && lastElement[0] < newInterval[0]) || (lastElement[0] >= newInterval[0] && lastElement[0] < newInterval[1])) && !newIntervalUsed) {
                answer[index - 1] = new int[]{Math.min(lastElement[0], newInterval[0]), Math.max(lastElement[1], newInterval[1])};
                newIntervalUsed = true;
                i-=1;
            } else {
                answer[index] = intervals[i];
                index++;
            }
        }
        if (!newIntervalUsed) {
            int[] lastElement = answer[index - 1];
            if(((lastElement[1] >= newInterval[0] && lastElement[0] < newInterval[0]) || (lastElement[0] >= newInterval[0] && lastElement[0] < newInterval[1])) && !newIntervalUsed) {
                answer[index - 1] = new int[]{Math.min(lastElement[0], newInterval[0]), Math.max(lastElement[1], newInterval[1])};
            } else {
                //    answer[index++] = newInterval;
                answer = MergeInterval.modify(answer, newInterval, index);
                index++;
            }

        }
        return Arrays.copyOf(answer, index);  // This line is crucial
    }

    private static int[][] modify(int[][] arr, int[] interval, int elementCount){
        int[][] answer = new int[arr.length][2];
        int position = getInsertionPosition(arr, interval, elementCount);
        System.arraycopy(arr, 0, answer, 0, position);
        answer[position] = interval;
        System.arraycopy(arr, position, answer, position+1, elementCount-position);
        return answer;
    }

    private static int getInsertionPosition(int[][] arr, int[] interval, int count) {
        for (int i =0; i< count; i++) {
            if (arr[i][0] >= interval[0]) {
                return i;
            }
        }
        return count;
    }

    public static void main(String[] args) {
        int[][] intervals = {{0,5},{9,12}};
        int[] newInterval = {7,16};
        MergeInterval solution = new MergeInterval();
        int[][] result = solution.insert(intervals, newInterval);
        System.out.println(Arrays.deepToString(result));
    }
}