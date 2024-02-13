package excercise.arrays;

import java.math.BigInteger;
import java.util.*;
import java.util.stream.Stream;

public class Excercise1 {

    static int findMaxConsecuitiveOnes(int[] nums) {
        if (nums.length == 0) return 0;

        int maxConseq = 0;
        int current = 0;

        for (int i = 0; i < nums.length; i++) {
            if (nums[i] == 1) {
                current++;
                maxConseq = Math.max(current, maxConseq);
            } else {
                current = 0;
            }


        }

        return maxConseq;

    }


    /// 12 / 10 = 1.
    //  1 / 10 = 0.
    //  123 / 10 = 12
    //  12 /  10 = 1.2
    // 1.2/10 = 0. 3

    static int countEvenNumOfDigits(int[] arr) {

        if (arr.length == 0) {
            return 0;
        }

        int evenNumbers = 0;
        for (int i = 0; i < arr.length; i++) {
            int num = arr[i];

            int countNumsInPos = 0;

            while (num > 0) {
                num = num / 10;
                countNumsInPos++;
            }

            if (countNumsInPos % 2 == 0) {
                evenNumbers++;
            }
        }

        return evenNumbers;
    }

    static int[] sortedSquares(int[] nums) {

        int[] arr = new int[nums.length];




        int left = 0;
        int right = nums.length - 1;
        int current = right;

        while(left <= right) {
            arr[current--] = Math.abs(nums[right]) > Math.abs(nums[left]) ? (int) Math.pow(nums[right--], 2) : (int) Math.pow(nums[left++], 2);
        }
        return arr;
    }



    static int[] duplicateZeros(int[] arr) {
        int possibleDups = 0;
        int length = arr.length - 1;

        for (int left = 0; left <= length - possibleDups; left++) {

            if (arr[left] == 0) {
                if (left == length - possibleDups) {
                    arr[length] = 0;
                    length -= 1;
                    break;
                }
                possibleDups++;
            }
        }

        int last = length - possibleDups;

        for (int i = last; i >=0; i--) {
            if (arr[i] == 0) {
                arr[i + possibleDups] = 0;
                possibleDups--;
                arr[i + possibleDups] = 0;
            } else {
                arr[i + possibleDups] = arr[i];
            }
        }

        return arr;

    }



    static void merge(int[] nums1, int m, int[] nums2, int n) {

        if (n == 0 ) {
            return;
        }

       /* if (m == 0 && nums1.length > 0) {
            System.arraycopy(nums2,0,nums1,0,n);
        }*/

        m = m - 1;
        n = n - 1;


        int currentPos = nums1.length - 1;

        while (m >= 0 && n >= 0) {
            if (nums1[m] >= nums2[n]) {
                nums1[currentPos--] = nums1[m--];
            } else {
                nums1[currentPos--] = nums2[n--];
            }
        }

        while (n >= 0) {
            nums1[currentPos--] = nums2[n--];
        }
    }


    static int removeElement(int[] nums, int val) {


        //      i j
        // [3,2,2,3]
        // [2,2,2,3]
       /* int k = nums.length - 1;
        int p = 0;

        while (p < k) {
            if (nums[p] == val) {
               if (nums[p] == nums[k]) {
                   k--;
               } else {
                  nums[p] = nums[p] + nums[k];
                  nums[k] = nums[p] - nums[k];
                  nums[p] = nums[p] - nums[k];
                  p++;
                  k--;
               }
            } else {
                p++;
            }
        }
        return k+1;*/

        int i = 0;
        for (int j = 0; j < nums.length; j++) {
            if (nums[j] != val) {
                nums[i] = nums[j];
                i++;
            }
        }

        return i;
    }

    static int removeDuplicates(int[] nums) {
        int i = 0;

        for (int j = 1; j < nums.length;j++) {
            if (nums[i] != nums[j]) {
                i++;
                nums[i] = nums[j];
            }
        }
        return i + 1;
    }

    static boolean checkIfExists(int[] arr) {
        Map<Integer, Integer> map = new HashMap<>();

        for (int c : arr) {
            if (map.get(c * 2) != null || (map.get(c / 2) != null && c % 2 == 0)) {
                return true;
            } else {
                map.put(c, 1);
            }
        }
        return false;
    }



    // 1,2,5,4,5,4,3,5,1
    //
    static boolean validMountainArray(int[] arr) {

       if (arr.length <= 2) {
            return false;
        }

       boolean isAscending = false;
       boolean isDescending = false;

        int i = 0;
        while (arr[i] < arr[i+1]) {
            i++;
            isAscending = true;
        }


        while (i < arr.length - 1) {
            if (arr[i] <= arr[i+1]) {
                return false;
            }
            i++;
            isDescending = true;
        }
        return isAscending && isDescending;


    }


    static int[] replaceElements(int[] arr) {

        int max = Integer.MIN_VALUE;

        // 17,18,5,4,6,1
        //    ,6,6,6,1

        int nextElem = arr[arr.length - 1];

        for (int i = arr.length - 1; i > 0; i--) {
            max = Math.max(nextElem, max);
            nextElem = arr[i - 1];
            arr[i-1] = max;
        }

        arr[arr.length - 1] = -1;
        for(int num : arr) {
            System.out.println(num + ",");
        }
        return arr;
    }

    static void moveZerosToEnd(int[] arr) {

        // [0,1,0,3,12]
        // [1,0,0,3,12]
        // [1,0,0,3,12]
        // [1,3,0,0,12]
        // [1,3,12,0,0]

        int i = 0;
        for (int j = 1; j < arr.length; j++) {
            if (arr[j] != 0) {
                arr[i] = arr[i] + arr[j];
                arr[j] = arr[i] - arr[j];
                arr[i] = arr[i] - arr[j];
                i++;
            }
        }

        Arrays.stream(Arrays.copyOfRange(arr, 0, i)).forEach(System.out::println);
    }

    static int[] sortArrayByParity(int[] arr) {

       /* int i = 0, j = A.length - 1;



        while (i < j) {
            if (A[i] % 2 > A[j] % 2) {
                int tmp = A[i];
                A[i] = A[j];
                A[j] = tmp;
            }

            if (A[i] % 2 == 0) i++;
            if (A[j] % 2 == 1) j--;
        }

        return A;*/


        // 0,1,2
        // 1,2,
        // 0,1,2
        // 3,1,2,4
        // 2,1,3,4
        // 0,1,2
        // 0,1,2
        //
        if (arr.length == 1) {
            return arr;
        }
        int i = 0;

        boolean swapped;
        for (int j = 1; j < arr.length; j++) {
            swapped = false;
            if (arr[j] % 2 == 0) {
                arr[i] = arr[i] + arr[j];
                arr[j] = arr[i] - arr[j];
                arr[i] = arr[i] - arr[j];
                i++;
                swapped = true;
            }

            if (!swapped && arr[i] % 2 == 0) {
                i++;
            }
        }

        Arrays.stream(arr).forEach(System.out::println);
        return arr;
    }


    static int heightChecker(int[] heights) {
        final int[] buckets = createBuckets(heights);
        int outOfOrder = 0;
        int nextInBucket = 0;

        for (int i = 0; i < heights.length; i++) {
            nextInBucket = nextValidNumber(buckets, nextInBucket);

            if (nextInBucket != heights[i]) {
                outOfOrder++;
            }
            buckets[nextInBucket]--;
        }

        return outOfOrder;
    }

    /*
        Creates the buckets. As an example, if the range was 0 <= num <= 10, and the input was
        [1, 1, 4, 2, 1, 3], then the buckets array would be equal to
        [0, 3, 1, 1, 1, 0, 0, 0, 0, 0, 0], because there are three "1", one "2" and so on.
    */
    static int[] createBuckets(int[] heights) {
        int[] buckets = new int[101];
        for (int height : heights) {
            buckets[height]++;
        }

        return buckets;
    }

    /*
        Returns the next valid number. For example, if our bucket array is [0, 0, 7, 3, 0, 1], and
        our currentNumber is 0, then it returns 2, since it's the index of the first non-empty bucket.
    */
    static int nextValidNumber(int[] buckets, int currentNumber) {
        // [0,3,1,1,1]
        while (buckets[currentNumber] == 0) {
            currentNumber++;
        }
        return currentNumber;
    }


    // [2,5,3,4]
    // [2,2,2,3,7]
    //

    public int thirdMax(int[] nums) {

        int first = Integer.MIN_VALUE;
        int second = Integer.MIN_VALUE;
        int third = Integer.MIN_VALUE;

        for (int num : nums) {
            if (first == num || second == num || third == num) {
                continue;
            }

            if (first <= num) { // 2  3 7
                third = second;
                second = first;
                first = num;

            } else if(second <= num) {
                third = second; //
                second = num; // 2 3
            } else if (third <= num) {
                third = num; // 2
            }
        }

        if (third == Integer.MIN_VALUE) {
            return first;
        }

        return third;
    }

    static int thirdMaxSorted(int[] nums) {
        Arrays.sort(nums);


        for (int index = 0; index < nums.length / 2; ++index) {
            int temp = nums[index];
            nums[index] = nums[nums.length - 1 - index];
            nums[nums.length - 1 - index] = temp;
        }


        int countDist = 1;
        int currNum = nums[0];

        // 5,5,3,3,2,2
        for (int index = 1; index < nums.length; ++index) {
            if (currNum != nums[index]) {
                countDist++;
                currNum = nums[index];
            }

            if (countDist == 3) {
                return nums[index];
            }
        }

        return nums[0];
    }


    // 4,-3,-2,-7,8,2,-3,-1
    // 1,1,2,3,3,4,8,8
    // 8 - 1
    //
    static List<Integer> fin32dDissapearedNumbers(int[] nums) {
        for (int i = 0; i < nums.length; i++) {
            int newIndex = Math.abs(nums[i]) - 1;
            if (nums[newIndex] > 0) {
                nums[newIndex] *= -1;
            }
        }

        return null;
    }


    static void dissapearedNumbers(int[] nums) {
        int[] buckets = createBucketsNumber(nums);
        for (int i = 1; i < buckets.length; i++) {
            if (buckets[i] == 0) {
                System.out.println(i);
            }
        }

    }


    static int[] createBucketsNumber(int[] nums) {
        int[] buckets = new int[nums.length + 1];
        for (int num : nums) {
            buckets[num]++;
        }
        return buckets;
    }
    //     l   r
    // [-4,-1,0,3,10]
    // [      9,  16,  100 ]
    static int[] sortedSquare(int[] nums) {
        int left = 0;
        int right = nums.length - 1;

        int[] squareArr = new int[nums.length];

        int currentPos = right;
        while (left < right) {
            squareArr[currentPos--] = Math.abs(nums[left]) > Math.abs(nums[right]) ? (int) Math.pow(nums[left++],2) : (int) Math.pow(nums[right--], 2);
        }

        return squareArr;
    }


    static int maxConsecutiveOnes(int[] arr) {
        int res = 0, zero = 0, left = 0, k = 1;

        for (int right = 0; right < arr.length; right++) {
            if (arr[right] == 0) ++zero;
            while (zero > k) {
                if (arr[left++] == 0) --zero;
            }
            res = Math.max(res, right - left + 1);
        }

        return res;
    }

    static int findMaxConsecuitiveOnesQueue(int[] nums) {
        int res = 0, left = 0, k = 1;

        Queue<Integer> q = new LinkedList<>();

       //                 l   r
        // LinkedList 1,0,1,1,0
        // res = 1  2  3  4
        // 4

        for (int right = 0; right < nums.length; right++) {
            if (nums[right] == 0) q.offer(right);
            if (q.size() > k) {
                left = q.poll() + 1;
            }
            res = Math.max(res, right - left + 1);
        }
        return res;
    }



    static int findMiddleIndex(int[] nums) {

        int pivot = -1;

        for (int i = 0; i < nums.length; i++) {

            int leftSum = 0;
            int rightSum = 0;

            for (int j = i - 1 ; j >= 0; j--) {
                leftSum +=  nums[j];
            }

            for (int k = i + 1 ; k < nums.length; k++) {
                rightSum +=  nums[k];
            }

            if (leftSum == rightSum)  return i;

        }

        return pivot;
    }

    static int findPivotIndex(int[] nums) {
        int total = 0, sum = 0;

        // 2,1,-1  total = 2
        for (int num : nums) total += num;
        for (int i = 0; i < nums.length; sum += nums[i++]) {
            if (sum * 2 == total - nums[i]) return i;
        }
        return -1;
    }

    static int dominantIndex(int[] nums) {

        int max = nums[0];
        int secondMax = 0;
        int index = 0;

        for (int i = 1; i < nums.length; i++) {
            int n = nums[i];
            if (n > max) {
                secondMax = max;
                max = n;
                index = i;
            } else if (n > secondMax) {
                secondMax = n;
            }
        }

        return max >= secondMax * 2 ? index : -1;
/*
        int lastIndex = nums.length - 1;
        for (int i = nums.length - 2; i >= 0; i--) {
            if (nums[lastIndex] != nums[i] && nums[lastIndex] < nums[i] * 2) return -1;
        }

        return lastIndex;*/
    }

    static int[] plusOne(int[] digits) {

        int n = digits.length;
        for (int i = n - 1; i >= 0; i--) {
            if (digits[i] < 9) {
                digits[i]++;
                return digits;
            }
            digits[i] = 0;
        }

        int[] newNumber = new int[n+1];
        newNumber[0] = 1;
        return newNumber;
    }


    static int[] diagonalOrder(int[][] mat) {
        int row = 0;
        int col = 0;
        int m = mat.length;
        int n = mat[0].length;
        int[] arr = new int[m * n];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = mat[row][col];
            if ((row + col) % 2 == 0) { //
                if ( col == n - 1) { row++; }
                else if (row == 0) { col++; }
                else         {row--; col++; }
            } else {
                if (row == m - 1)  {col++; }
                else if (col == 0) {row++; }
                else               {row++; col--; }
            }
        }

        return arr;
    }

    static  List<Integer> spiralOrder(int[][] matrix) {
        int top = 0;
        int bottom = matrix.length - 1;
        int left = 0;
        int right = matrix[0].length - 1;
        List<Integer> arr = new ArrayList<>();

        int index = 0;
        while (true) {
            for (int i = left; i <= right; i++) {
                arr.add(matrix[top][i]);
            }
            top++;

            if (top > bottom) break;

            for (int i = top; i <= bottom; i++) {
                arr.add(matrix[i][right]);
            }
            right--;

            if(right < left) break;

            for (int i = right; i >= left; i--) {
                arr.add(matrix[bottom][i]);
            }
            bottom--;

            if (top > bottom) break;

            for (int i = bottom; i >= top; i--) {
                arr.add(matrix[i][left]);
            }
            left++;

            if (right < left) break;

        }
        return arr;
    }

    /*
                1
              1   1
             1  2  1
            1 3   3  1
           1  4 6  4  1
          1 5 10 10 5  1

     */

    static List<List<Integer>> generate(int numRows) {
        List<List<Integer>> result = new ArrayList<>();

        for (int i = 0; i < numRows; i++) {
            List<Integer> list = new ArrayList<>();
            for (int j = 0; j < i + 1; j++) {
                if (j == 0 || j == i) {
                    list.add(1);
                } else {
                    int a = result.get(i - 1).get(j - 1);
                    int b = result.get(i - 1).get(j);
                    list.add(a + b);
                }
            }
            result.add(list);
        }
        return result;
    }

    static List<List<Integer>> generatePascal(int numRows) {
        List<List<Integer>> result = new ArrayList<>();
        List<Integer> row = new ArrayList<>();


        /*
                1
              1   1
             1  2  1
            1 3   3  1
           1  4 6  4  1
          1 5 10 10 5  1


          1 2 1


     */
        for (int i = 0; i < numRows; i++) {

            row.add(0,1);

            for (int j = 1; j < row.size() - 1; j++) {
                row.set(j, row.get(j) + row.get(j + 1));
            }
            result.add(new ArrayList<>(row));
        }
        return result;
    }

    /*
     [2 5 8]
     [4 0 -1]

     */


    static String addBinary(String a, String b) {
        int carry = 0;

        StringBuilder sb = new StringBuilder();

        int i = a.length() - 1;
        int j = b.length() - 1;

        while (i >= 0 || j >= 0) {
            int sum = carry;
            if (i >= 0) sum += a.charAt(i--) - '0';
            if (j >= 0) sum += b.charAt(j--) - '0';
            sb.append(sum % 2);
            carry = sum / 2;

        }
        if (carry != 0) sb.append(carry);
        return sb.reverse().toString();

    }


    static int strStr(String haystack, String needle) {
        for (int i = 0; i < haystack.length(); i++) {
            if (haystack.startsWith(needle, i)) {
                return i;
            }
        }
        return -1;
    }


    // leetcode
    // leeto
    //
    static int strStr2(String haystack, String needle) {
        for (int i = 0;; i++) {
            for (int j = 0;;j++) {
                if (j == needle.length()) return i;
                if (i + j  == haystack.length()) return -1;
                if (needle.charAt(j) != haystack.charAt(i + j)) break;
            }
        }
    }


    // ["flower","flo","floght"]
    // [ f
    static String longestCommonPrefix(String[] strs) {
        StringBuilder str = new StringBuilder();

        String prefix = strs[0];
        for (int i = 0;i < strs[0].length();i++) {
            char c = strs[0].charAt(i);
            for (String s : strs) {
                if (i >= s.length() || s.charAt(i) != c) return str.toString();
            }
            str.append(c);
        }

        return str.toString();
    }

    static String longestCommonPrefixFaster(String[] strs) {
        String prefix = strs[0];

        for (int i = 1; i < strs.length; i++) {
            while (strs[i].indexOf(prefix) != 0) {
                prefix = prefix.substring(0, prefix.length() - 1);

                if (prefix.isEmpty()) {
                    return "";
                }
            }
        }

        return prefix;
    }

    static void reverseString(char[] s) {
        int left = 0;
        int right = s.length - 1;

        while (left < right) {
            char temp = s[left];
            s[left++] = s[right];
            s[right--] = temp;
        }

        System.out.println(s);
    }

    // 6,2,6,5,1,2
    // 2 + 5 + 2

    // 1,4,3,2
    // 1, 2
    static int arrayPairSum(int[] nums) {
        Arrays.sort(nums);
        int sum = 0;
        for (int i = 0; i < nums.length - 1; i += 2) {
            sum += Math.min(nums[i], nums[i+1]);
        }
        return sum;
    }
    //(1,2) (2,5),
    // [6,2,6,5,1,2]
    // [0 1     0 2]
    static int arrayPairSumFast(int[] nums) {
        int[] exist = new int[20001];
        for (int i = 0; i < nums.length; i++) {
            exist[nums[i] + 10000]++;
        }


        // 1 + 2 + 6
        int sum = 0;
        boolean odd = true; // f  t f t
        for (int i = 0; i < exist.length; i++) {
            while (exist[i] > 0) {
                if (odd) {
                    sum += i - 10000;
                }
                odd = !odd;
                exist[i]--;
            }
        }
        return sum;
    }

    static int[] twoSum(int[] numbers, int target) {
        Map<Integer, Integer> map = new HashMap<>();


        // [2,7,11,15]   9
        // if
        //   map.get(target - arr[i]) != null
        //     arr.add(i + 1, map.get(target - arr[i] + 1);


        int[] res = new int[2];
        for (int i = 0; i < numbers.length; i++) {
            if (map.get(target - numbers[i]) != null) {
                res[0] = Math.min(map.get(target - numbers[i]) + 1, i + 1);
                res[1] = Math.max(map.get(target - numbers[i]) + 1, i + 1);
                break;
            } else {
                map.put(numbers[i], i);
            }
        }

        return res;
    }

    static int[] twoSumFaster(int[] numbers, int target) {
        // [2,7,11,15]
        //  l r
        // if target < l + r
        //      r--
        // if target > l + r
        //      l++
        // if target == l + r
        // return l+1, r + 1
        int l = 0;
        int r = numbers.length - 1;
        while (r > 0) {
            if (target < numbers[l] + numbers[r]) { r--;}
            else if (target > numbers[l] + numbers[r]) {l++;}
            else {
                break;
            }
        }
        return new int[]{l + 1, r + 1};
    }

    static int[] twoSumFastest(int[] numbers, int target) {
        for (int i = 0; i < numbers.length; i++) {
            int low = i + 1;
            int high = numbers.length - 1;

            while (low <= high) {
                int mid = (high - low) / 2 + low;
                if (numbers[mid] == target - numbers[i]) {
                    return new int[]{i+1, mid + 1};
                } else if (numbers[mid] > target - numbers[i]) {
                    high = mid - 1;
                } else {
                    low = mid + 1;
                }
            }
        }
        return new int[]{-1,-1};
    }


    // 2,2,2,2,2,2 11
    //
    static int minSubArrayLen(int target, int[] nums) {
        if (nums == null || nums.length == 0) return 0;

        int i = 0;
        int j = 0;
        int sum = 0;
        int min = Integer.MAX_VALUE;

        while (j < nums.length) {
            sum += nums[j++];

            while (sum >= target) {
                min = Math.min(min, j - i);
                sum -= nums[i++];
            }
        }
        return min == Integer.MAX_VALUE ? 0 : min;
    }


    // 1 2 3
    // 3 1 2
    // 2 3 1
    // 1 2 3

    // 1 2 3 4 5
    // 4 5 1 2 3

    // 1 2 3 4 5
    // 5 1 2 3 4
    // 4 5 1 2 3

    //       i + k % arr.length;
    //       1

    static int[] rotateArray(int[] arr, int k) {
        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < arr.length; i++) {
            map.put((i + k) % arr.length, arr[i]);
        }

        for (int i = 0; i < arr.length; i++) {
            arr[i] = map.get(i);
        }

        return arr;
    }


    // 1 2 3 4 5 6 7
    // 5 6 7 1 2 3 4
    // 6 7 1 2 3 4 5

    // 1 2 3 4 5 6 7 1 2 3 4 5 6 7 1 2 3 4 5 6 7

    // 3 4 5 1 2

    // 1 2 3 4 5
    // 3 2 4 5
    //
    static int[] rotateArrayReverse(int[] nums, int k) {
        k %= nums.length;
        reverse(nums, 0, nums.length - 1);
        reverse(nums, 0, k - 1);
        reverse(nums, k, nums.length - 1);
        return nums;
    }

    static void reverse(int[] nums, int start, int end) {
        while (start < end) {
            int temp = nums[start];
            nums[start] = nums[end];
            nums[end] = temp;
            start++;
            end--;
        }
    }

    static List<Integer> getRow(int rowIndex) {
        List<Integer> row = new ArrayList<>();

        for (int i = 0 ; i <= rowIndex; i++) {
            row.add(0, 1);
            for (int j = 1; j < row.size() - 1; j++) {
                row.set(j, row.get(j) + row.get(j + 1));
            }
        }
        return row;
    }

    static List<Integer> getRowPascal(int rowIndex) {

        ArrayList<Integer> list=new ArrayList<>();
        long val=1;
        for(int j=0;j<=rowIndex;j++){
            list.add((int)val);
            val=val*(rowIndex-j)/(j+1);
        }
        return list;
    }

    static String reverseWords(String s) {
        String[] str = s.trim().split("\\s+");
        StringBuilder sb = new StringBuilder();

        for (int i = str.length - 1; i > 0; i--) {
            sb.append(str[i] + " ");
        }

        sb.append(str[0]);
        return sb.toString();
    }

    static String reverseWordsNoBuiltin(String s) {
        if (s == null) return null;

        char[] a = s.toCharArray();
        int n = a.length;

        // " orik  is "
        // " si  ikro "
        reverse(a, 0, n - 1);

        // " si  ikro "
        reverseWords(a, n);


        return cleanSpaces(a, n);

    }

    static void reverseWords(char[] a, int n) {
        int i = 0, j = 0;

        //     j
        //   i
        // " si  kiro "
        while (i < n) {
            while (i < j || i < n && a[i] == ' ') i++;
            while (j < i || j < n && a[j] != ' ') j++;
            reverse(a, i, j - 1);
        }
    }
    static void reverse(char[] a, int i, int j) {
        while (i < j) {
            char t = a[i];
            a[i++] = a[j];
            a[j--] = t;
        }
    }

    static String cleanSpaces(char[] a, int n) {
        int i = 0;
        int j = 0;

        while (j < n) {

            while (j < n && a[j] == ' ') j++; // skip spaces
            while (j < n && a[j] != ' ') a[i++] = a[j++]; // keep non spaces
            while (j < n && a[j] == ' ') j++;
            if (j < n) a[i++] = ' ';  // keep only one space
        }

        return new String(a).substring(0, i);
    }

    static String reverseWordFast(String s) {
        char[] charArr = s.toCharArray();
        char[] ans = new char[charArr.length];
        int i = charArr.length - 1;

        int ind = 0;
        while (i >= 0 && charArr[i] == ' ') i--;

        while (i >= 0) {
            int j = i;

            while (i >= 0 && charArr[i] != ' ') i--;

            if (ind > 0) ans[ind++] = ' ';
            for (int k = i + 1; k <= j; k++) ans[ind++] = charArr[k];

            while (i >= 0 && charArr[i] == ' ') i--;
        }
        return new String(ans,0, ind);
    }

    static String reverseWordsInString(String s) {
        char[] strChar = s.toCharArray();
        int n = strChar.length - 1;
        int i = s.length() - 1;

        while (i >= 0) {

            while (i >= 0 && strChar[i] == ' ') i--;
            int j = i;

            while (i >= 0 && strChar[i] != ' ') i--;
            reverse(strChar, i + 1, j);

        }
        return new String(strChar);
    }

    static int findMiddleIndexV3(int[] nums) {

        // 2,3,-1,8,4 = 16
        int total = 0;
        for (int num : nums) total += num;

        int sum = 0;
        for (int i = 0; i < nums.length; i++) {
            if (sum * 2 == total - nums[i]) return i;
            sum += nums[i];
        }
        return -1;
    }

    static boolean isGood(int[] nums) {
        if (nums.length < 2) return false;
        if (nums.length == 2 && nums[0] == 1) return nums[0] == nums[1];

        int[] numsFreq = new int[201];

        int maxNum = 0;
        for (int num : nums) {
            numsFreq[num]++;
            if (num > maxNum) maxNum = num;
            if (maxNum > nums.length - 1) return false;
        }

        for (int i = 1; i < maxNum; i++) {
            if (numsFreq[i] != 1) return false;
        }

        return numsFreq[maxNum] == 2;
    }


    /*

       1,3,3,2

       00
       01
       00
       01

       01
       11
       01
       11

       11
       11
       10
       10

       11
      111

     */
    static boolean isGoodV2(int[] a) {
        int n = a.length; int res = 0; int count = 0;
        for (int i = 0; i < n; i++) {
            res = res ^ a[i] ^ i;
            if (a[i] > n - 1) return false;
            if (a[i] == n - 1) count++;
        }
        return res == n - 1 && count == 2;
    }

    static List<Integer> addToArrayFrom(int[] num, int k) {

        // [2,1,5]
        //  8 0 6


        List<Integer> list = new ArrayList<>();
        int carry = 0;
        int sum = 0;

        int i = num.length - 1;
        while (k > 0 || i >= 0) {

            if (i >= 0) {
                sum = num[i] + (k % 10) + carry;
            } else {
                sum = (k % 10) + carry;
            }

            if (sum >= 10) {
                carry = 1;
                sum %= 10;
            } else {
                carry = 0;
            }
            k /= 10;
            list.add(0, sum);
            i--;
        }

        if (carry > 0) list.add(0,1);

        return list;
    }


    static List<Integer> addToArrayFormV2(int[] num, int k) {

        //  993     1002
        //    9
        //  1
        //

        for (int i = num.length -1; i >= 0; i--) {
            int sum = num[i] + k;
            if (k == 0) { break;}
            else {
                num[i] = sum % 10;
                k = sum / 10;
            }
        }

        List<Integer> al = new ArrayList<>();
        while (k > 0) {
            al.add(0, k % 10);
            k /= 10;
        }
        for (int i : num) {
            al.add(i);
        }
        return al;
    }


    static List<String> removeAnagramsV2(String[] words) {
        List<String> anagrams = new ArrayList<>();
        int i = 0;
        int k = i + 1;

        // a b c d
        //
        while (i < words.length) {
            while (k < words.length && isAnagram(words[k], words[k - 1])) k++;
            anagrams.add(words[i]);
            i = k;
            k++;
        }

        return anagrams;
    }
    static List<String> removeAnagrams(String[] words) {

        List<String> anagrams = new ArrayList<>();
        Map<String, Byte> stringAnagram = new HashMap<>();

        int wordsLength = words.length;

        for (int i = 0; i < wordsLength - 1; i++) {
            if (stringAnagram.get(words[i]) != null) continue;


            for (int j = i + 1; j < wordsLength; j++) {

                if (isAnagram(words[i], words[j])) {
                    stringAnagram.put(words[i], (byte) 1);
                    stringAnagram.put(words[j], (byte) 1);
                }
            }
            anagrams.add(words[i]);
        }

        //if (anagrams.size() == 0) return List.of(words);

        if (stringAnagram.get(words[wordsLength - 1]) == null) anagrams.add(words[wordsLength - 1]);

        return anagrams;
    }


    private static boolean isAnagram(String a, String b) {

        if (a.length() != b.length()) return false;

        if (a.equals(b)) return true;


        int[] freq1 = new int[26];

        int i = 0;
        int j = 0;

        while (i < a.length() || j < b.length()) {
            if (i < a.length()) freq1[a.charAt(i++) - 'a']++;
            if (j < b.length()) freq1[b.charAt(j++) - 'a']--;
        }

        for (int value : freq1) {
            if (value != 0) return false;
        }

        return true;
    }

    static int findMaxLength(int[] nums) {
        int sum = 0;
        int max = 0;

        Map<Integer, Integer> map = new HashMap<>();
        map.put(0, -1);

        // 1,0,0,1,0,1,1
        for (int i = 0; i < nums.length; i++) {
            sum += nums[i] == 1 ? 1 : -1;
            if (map.get(sum) != null) max = Math.max(max, i - map.get(sum));
            else map.put(sum, i);
        }
         return max;
    }


    static int findMaxLengthV2(int[] nums) {

        int n = nums.length;
        int[] m = new int[n*2+1];
        m[n] = 1;
        int balance = n;
        int result = 0;
        for(int i = 2; i<nums.length+2; i++) {
            balance += (nums[i-2]<<1)-1;
            if(m[balance]==0) {
                m[balance] =  i;
            } else {
                result = Math.max(result,i-m[balance]);
            }
        }
        return result;
    }

    static boolean isValidSudoku(char[][] board) {
        Set<String> seen = new HashSet<>();
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                char number = board[i][j];
                if (number != '.') {
                    if (!seen.add(number + " in row " + i)
                            || !seen.add(number + " in col " + j)
                            || !seen.add(number + " in block " + i / 3 + "-" + j / 3)) {
                        return false;
                    }
                }
            }
        }

        return true;
    }

    static boolean isValidSudokuV2(char[][] board) {
        for (int row = 0; row < board.length; row++) {
            for (int col = 0; col < board[row].length; col++) {
                if (board[row][col] != '.') {
                    if (!isSafe(board, row, col, board[row][col])) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    static boolean isSafe(char[][] board, int row, int col, char target) {

        // row check
        for (int c = 0; c < board[0].length; c++) {
            if (board[row][c] == target && col != c) return false;
        }

        // col check
        for (int r = 0; r < board.length; r++) {
            if (board[r][col] == target && row != r) return false;
        }

        int subgridStartRow = 3 * (row / 3);
        int subgridStartCol = 3 * (col / 3);


        for (int r = subgridStartRow; r < subgridStartRow + 3; r++) {
            for (int c = subgridStartCol; c < subgridStartCol + 3; c++) {
                if ((r != row || c != col) && board[r][c] == target) return false;
            }
        }

        return true;
    }


    /*static int searchRecCount(int[] nums, Map<Integer, Next> map,  int i) {
        if (nums[i] == i) {
            return 1;
        }

        if (map.get(nums[i]) != null) {
            return map.get(nums[i]).countDepth;
        } else {
            map.put(nums[i],)
        }
        return 1 + searchRecCount(nums, map, nums[i]);
    }*/
    static int arrayNesting(int[] nums) {
        Map<Integer, Integer> map = new HashMap<>();
        int max = 0;

        for (int i = 0; i < nums.length; i++) {
            if (map.get(i) != null) continue;

            int j = i;



            map.put(i, nums[i]); // 0 5 6 2 0
            int count = 1;

            while ((j = map.get(j)) != i) {
                count++;
                map.put(j, nums[j]);
            }

            max = Math.max(max, count);
        }

        // [0] -> 5 - > [6] -> 2 -> 0 -> 5

        // 15,

        return max;
    }

    static int arrayNestingV2(int[] nums) {
        int n = nums.length;
        int max = 0;

        for (int i = 0;  i < n; i++) {
            int index = i, count = 0;

            // 12,11,0,3,1,13,9
            while (nums[index] < n && nums[index] != i) {
                count++;
                nums[index] += n;
                index = nums[index]  - n;
            }
            if (++count > max) max = count;
            if (max >= (n+1) / 2) return max;
        }
        return max;
    }

    static int arrayNestingV3(int[] nums) {
        int res = 0;
        for (int i = 0; i < nums.length; i++) {

            if (nums[i] != Integer.MAX_VALUE) {

                int start = nums[i], count = 0;

                while (nums[start] != Integer.MAX_VALUE) {
                    int temp = start;
                    start = nums[start];
                    count++; // 1
                    nums[temp] = Integer.MAX_VALUE; // M
                }
                res = Math.max(res, count);
            }

        }
        return res;
    }

    static int removeDuplicatesTWOMedium(int[] nums) {
        int i = 0;
        for (int n : nums) {
            if (i < 2 || n > nums[i - 2]) nums[i++] = n;
        }
        return i;
    }

    static int canCompleteCircuit(int[] gas, int[] cost) {
        int n = gas.length;
        int totalSurplus = 0;
        int surplus = 0;
        int start = 0;

        for (int i = 0; i < n; i++) {
            totalSurplus += gas[i] - cost[i];
            surplus += gas[i] - cost[i];
            if (surplus < 0) {
                surplus = 0;
                start = i + 1;
            }
        }
        return (totalSurplus < 0) ? -1 : start;
    }

    static int canCompleteCircuitV2(int[] gas, int[] cost) {
        int sumCost = 0;
        int sumGas = 0;
        int fuel = 0;
        int startIndex = 0;
        for (int i = 0; i < gas.length; i++) {
            sumCost += cost[i];
            sumGas += gas[i];
        }
        // 1,2,3,4,5
        // 3,4,5,1,2

        if (sumGas < sumCost) {
            return -1;
        }

        for (int i = 0; i < gas.length; i++) {
            fuel = fuel + gas[i] - cost[i];
            if (fuel < 0) {
                startIndex = i + 1;
                fuel = 0;
            }
        }
        return startIndex;
    }

    static int[] productExceptSelf(int[] nums) {
        int product = 1;

        int countZero = 0;
        for (int num : nums) {
            if (num == 0) {
                countZero++;
                continue;
            }
            product *= num;
        }

        for (int i = 0; i < nums.length; i++) {
            if (countZero > 1) nums[i] = 0;
            else {
                //nums[i] = countZero > 0 ? nums[i] == 0 ? product : nums[i] / product : nums[i]/product;
                  nums[i] = nums[i] == 0 ? product : countZero > 0 ? 0 : product / nums[i];
            }
        }
        return nums;
    }

    static boolean canJump(int[] nums) {

        // 5,0,0,0,4
        //
        int last = nums.length - 1;
        for(int i = nums.length - 2; i >= 0; i--){
            if(i + nums[i] >= last) {
                last = i;
            }
        }
        return last <= 0;
    }

    // 4,1,0,1,4
    static boolean canJumpV2(int[] nums) {

        int last = nums.length - 1;
        for (int i = nums.length - 2; i >= 0; i--) {
            if (i + nums[i] >= last) last = i;
        }

        return last == 0;
    }

    static int canJump2(int[] nums) {

        int jumps = 0;
        int curEnd = 0;
        int curFarthest = 0;


        // 1,2,1,1,1
        for (int i = 0; i < nums.length - 1; i++) {
            curFarthest = Math.max(curFarthest, i + nums[i]); // 1 3
            if (i == curEnd) {
                jumps++;
                curEnd = curFarthest; //1 3
            }
        }
        return jumps;
    }

    static String convert(String s, int numRows) {
       if (numRows <= 1) return s;

       StringBuilder[] sbs = new StringBuilder[numRows];
       for (int i = 0; i < sbs.length; i++) sbs[i] = new StringBuilder();
       int idx = 0;
       int direction = -1;
       char[] chars = s.toCharArray();

       for (char c : chars) {
           sbs[idx].append(c);
           if (idx == 0 || idx == numRows - 1) direction = -direction;
           idx += direction;
       }
       StringBuilder sb = new StringBuilder();
       for (StringBuilder sbc : sbs)  sb.append(sbc);

       return sb.toString();
    }


    /*
    * "This    is    an",
      "example  of text",
      "justification.  "
    *
    *
    *
    * */
    static List<String> fullJustify(String[] words, int maxWidth) {
        List<String> res = new ArrayList<>();
        if (words == null || words.length == 0) return res;
        int index = 0;
        while (index < words.length) {
            int last = index + 1;
            int count = words[index].length();
            while (last < words.length) {
                if (words[last].length() + count + 1 > maxWidth) break;
                count += words[last].length() + 1;
                last++;
            }

            StringBuilder sb = new StringBuilder();
            sb.append(words[index]);

            int diff = last - index - 1;

            if (last == words.length || diff == 0) {
                for (int i = index + 1; i < last; i++) {
                    sb.append(" ");
                    sb.append(words[i]);
                }

                sb.append(" ".repeat(Math.max(0, maxWidth - sb.length())));
            } else {
                int space = (maxWidth - count) % diff;
                int r = (maxWidth - count) % diff;
                for (int i = index + 1; i < last; i++) {
                    sb.append(" ".repeat(Math.max(0, space)));
                    if (r > 0) {
                        sb.append(" ");
                        r--;
                    }
                    sb.append(" ");
                    sb.append(words[i]);
                }
            }
            res.add(sb.toString());
            index = last;
        }

        return res;
    }

    static List<String> fullJustifyV2(String[] words, int maxWidth) {
        List<String> result = new ArrayList<>();
        StringBuilder sb = new StringBuilder();

        int start = 0;
        int length = 0;


        /*
    * "This    is   an ",
      "example  of text",
      "justification.  "
    */
        for (int i = 0; i < words.length; i++) {
            if (words[i].length() <= (maxWidth - length)) {
                length += words[i].length() + 1;
            } else {

                int remainingSpaces = maxWidth - length + 1;
                int noOfWords = i - start - 1;
                int extraSpacesBetweenWords = 0;

                if (noOfWords > 0) {
                    extraSpacesBetweenWords = remainingSpaces / noOfWords;
                    remainingSpaces = remainingSpaces % noOfWords;
                }

                while (start < i - 1) {
                    sb.append(words[start++]).append(" ");
                    int k = 0;
                    while (k < extraSpacesBetweenWords) {
                        sb.append(" ");
                        k++;
                    }
                    if (remainingSpaces > 0) {
                        sb.append(" ");
                        remainingSpaces--;
                    }
                }
                sb.append(words[start]);

                while (remainingSpaces > 0) {
                    sb.append(" ");
                    remainingSpaces--;
                }
                result.add(sb.toString());
                start = i;
                length = words[i].length() + 1;
                sb.setLength(0);
            }
        }

        sb.setLength(0);
        while (start < words.length - 1) {
            sb.append(words[start++]).append(" ");
        }
        sb.append(words[start]);

        while (sb.length() < maxWidth) sb.append(" ");

        result.add(sb.toString());

        return result;

    }



    // 2 4 5 5 6 5 5 1
    // 1 2 3 3 4 2 2 1
    static int candy(int[] ratings) {

        int countCandies = 0;
        int[] lRating = new int[ratings.length];

        lRating[0] = 1;
        for (int i = 1; i < ratings.length; i++) {
            if (ratings[i] > ratings[i - 1]) {
                lRating[i] = lRating[i - 1] + 1;
            } else {
                lRating[i]++;
            }
        }

        for (int i = ratings.length - 2; i >= 0; i--) {
            if (ratings[i] > ratings[i + 1]) {
                if (lRating[i] <= lRating[i + 1]) {
                    lRating[i] = lRating[i + 1] + 1;
                }
            }
        }

        int sum = 0;
        for (int j : lRating) {
            sum += j;
        }

        return sum;
    }

    static int trapV2(int[] heights) {
        if (heights == null || heights.length == 0) return 0;

        int left = 0;
        int right = heights.length - 1;
        int maxLeft = 0;
        int maxRight = 0;

        int totalWater = 0;

        while (left < right) {
            if (heights[left] < heights[right]) {
                if (heights[left] > maxLeft) {
                    maxLeft = heights[left];
                } else {
                    totalWater += maxLeft - heights[left];
                }
                left++;
            } else {
                if (heights[right] > maxRight) {
                    maxRight = heights[right];
                } else {
                    totalWater += maxRight - heights[right];
                }
                right--;
            }
        }

        return totalWater;
    }

    static int maxArea(int[] height) {
        if (height == null || height.length == 0) return 0;
        int left = 0;
        int right = height.length - 1;

        int maxSquare = 0;
        while (left < right) {
            if (height[left] < height[right]) {
                maxSquare = Math.max(maxSquare, height[left] * (right - left)); // 8
                left++;
            } else {
                maxSquare = Math.max(maxSquare, height[right] * (right - left));
                right--;
            }
        }

        return maxSquare;
    }

    static int trap(int[] height) {

        int[] maxLeft = new int[height.length];
        int[] maxRight = new int[height.length];

        int l = -1;
        int r = -1;

        int count = 0;
        for (int i = 0, j = height.length - 1; i < height.length; i++, j--) {
            l = Math.max(l, height[i]); // max left
            maxLeft[i] = l;

            r = Math.max(r, height[j]); // max right
            maxRight[j] = r;
        }

        for (int i = 0; i < height.length; i++) {
            count += Math.min(maxLeft[i], maxRight[i]) - height[i];
        }

        return count;
    }


    // 1 2 5 7 3   t = 0   max = 7
    // 6 5 0
    //

    // -1,0,1,2,-1,-4  t = 0 max = 2
    // -1,

    // -1              [-1,0,1]  [-1,2,-1]
    //  0
    //  1

    //  -1 1
    //  0  1
    //
    static List<List<Integer>> threeSum(int[] nums) {

        if (nums.length < 3) return null;

        Arrays.sort(nums);
        List<List<Integer>> res = new ArrayList<>();
        for (int i = 0; i < nums.length - 2; i++) {
            int target = -nums[i];

            int j = i + 1;
            int k = nums.length - 1;

            if (i > 0 && nums[i] == nums[i - 1]) continue;
            while (j < k) {

                if (nums[j] + nums[k] == target) {
                    res.add(List.of(nums[i], nums[j], nums[k]));
                    j++;
                    k--;

                    while (j < k && nums[j] == nums[j - 1]) j++;
                    while (j < k && k < nums.length && nums[k] == nums[k + 1]) k--;

                } else if (nums[j] + nums[k] > target) {
                    k--;
                } else {
                    j++;
                }
            }
        }

        return res;
    }

    static List<List<Integer>> fourSum(int[] nums, int target) {

        Arrays.sort(nums);

        List<List<Integer>> res = new ArrayList<>();

        for (int i = 0; i < nums.length - 3; i++) {

            if (i > 0 && nums[i] == nums[i - 1]) continue;

            for (int j = i + 1; j < nums.length - 2; j++) {

                if (j > i + 1 && nums[j] == nums[j - 1]) continue;

                int l = j + 1;
                int r = nums.length - 1;
                long lr = nums[i] + nums[j];
                while (l < r) {
                    long sum = lr + nums[l] + nums[r];

                    if (sum > Integer.MAX_VALUE || sum < Integer.MIN_VALUE) {
                        l++;
                        r--;
                        continue;
                    }
                    if (sum == target) {
                        res.add(List.of(nums[i], nums[j], nums[l], nums[r]));
                        l++;
                        r--;
                        while (l < r && nums[l] == nums[l - 1]) l++;
                        while (l < r && r < nums.length && nums[r] == nums[r + 1]) r--;
                    } else if (lr + nums[l] + nums[r] > target) {
                        r--;
                    } else {
                        l++;
                    }
                }
            }
        }

        return res;
    }


    private List<List<Integer>> res = new ArrayList<>();
    public  List<List<Integer>> threeSum2(int[] nums) {
        int target = 0;

        return new AbstractList<List<Integer>>() {
            @Override
            public List<Integer> get(int index) {
                init();
                return res.get(index);
            }

            @Override
            public int size() {
                init();
                return res.size();
            }

            private void init() {
                if (res != null) return;
                Arrays.sort(nums);
                int l, r;
                int sum;
                Set<List<Integer>> tempRes = new HashSet<>();
                for (int i = 0; i < nums.length - 2; i++) {
                    l = i + 1;
                    r = nums.length - 1;

                    if (i > 0 && nums[i] == nums[i - 1]) continue;
                    while (l < r) {
                        sum = nums[i] + nums[l] + nums[r];
                        if (sum == target) {
                            List<Integer> t = new ArrayList<>();
                            t.add(nums[i]);
                            t.add(nums[l]);
                            t.add(nums[r]);
                            tempRes.add(t);
                        }
                        if (sum < target) ++l;
                        --r;
                    }
                }
                res = new ArrayList<>(tempRes);
            }
        };
    }

    static int lengthOfLongestSubstring(String s) {

        if (s.length() == 0) return 0;
        Map<Character, Integer> map = new HashMap<>();
        int max = 0;
        for (int i = 0, j = 0; i < s.length(); i++) {
            if (map.containsKey(s.charAt(i))) {
                j = Math.max(j, map.get(s.charAt(i)) + 1);
            }
            map.put(s.charAt(i), i);
            max = Math.max(max, i - j  + 1);
        }

        return max;
    }

    static int lengthOfLongestSubstringV2(String s) {
        int ans = 0;
        int[] count = new int[128];

        for (int l = 0, r = 0; r < s.length(); ++r) {
            ++count[s.charAt(r)];
            while (count[s.charAt(r)] > 1) --count[s.charAt(l++)];
            ans = Math.max(ans, r - l + 1);
        }
        return ans;
    }

    // doesnt work for input "ababaab" "ab","ba","ba"
    static List<Integer> findSubstring(String s, String[] words) {
        if (words.length * words[0].length() > s.length()) return new ArrayList<>();

        int maxLengthToSearch = words.length * words[0].length();

        int startSearchIndex = 0;

        int wordLength = words[0].length();

        List<Integer> res = new ArrayList<>();

        int currMaxIndex;
        int wordIndex;

        StringBuilder sb = new StringBuilder();

        while (startSearchIndex <= s.length() - maxLengthToSearch) {

            boolean allFound = true;
            int minIndex = Integer.MAX_VALUE;

            currMaxIndex = startSearchIndex + maxLengthToSearch - 1;


            sb.append(s, startSearchIndex, currMaxIndex + 1);
            for (String word : words) {

                int wordIndexSb = sb.indexOf(word);
                wordIndex = wordIndexSb >= 0 ? s.indexOf(word,startSearchIndex) : -1;
                if (wordIndex < 0) {
                    allFound = false;
                    break;
                } else {
                    minIndex = Math.min(minIndex, wordIndex);
                    sb.replace(wordIndexSb, wordIndexSb + word.length(), "");
                }
            }

            if (allFound) {
                res.add(minIndex);
            }

            startSearchIndex += 1;
            /*if (oneFound && minIndex != startSearchIndex) startSearchIndex = minIndex;
            else startSearchIndex += wordLength;*/

            sb.setLength(0);
        }

        return res;
    }

    static List<Integer> findSubstringV2(String s, String[] words) {
        final Map<String, Integer> counts = new HashMap<>();
        for (final String word : words) {
            counts.put(word, counts.getOrDefault(word,0) + 1);
        }

        final List<Integer> indexes = new ArrayList<>();
        int n = s.length();
        int num = words.length;
        int len = words[0].length();
        for (int i = 0; i < n - num * len + 1; i++) {
            final Map<String, Integer> seen = new HashMap<>();
            int j = 0;
            while (j < num) {
                String word = s.substring(i + j * len, i + (j + 1) * len);
                if (counts.containsKey(word)) {
                    seen.put(word, seen.getOrDefault(word, 0) + 1);
                    if (seen.get(word) > counts.getOrDefault(word, 0)) {
                        break;
                    }
                } else {
                    break;
                }
                j++;
            }
            if (j == num) indexes.add(i);
        }
        return indexes;
    }

    static List<Integer> findSubstringV3(String s, String[] words) {
        var result = new ArrayList<Integer>();

        var wordMap = new HashMap<String, Integer>();
        for (String word : words) wordMap.put(word, wordMap.getOrDefault(word, 0) + 1);

        var wordLength = words[0].length();

        for(int i = 0; i < s.length() - (words.length * wordLength) + 1; ++i) {
            var seen = new HashMap<String, Integer>();
            var totalSeen = 0;
            for (int j = 0; j < words.length; ++j) {
                var pos = i + j * wordLength;
                var w = s.substring(pos, pos + wordLength);

                var allowedCount = wordMap.getOrDefault(w, 0);
                var seenCount = seen.getOrDefault(w,0);
                if (seenCount >= allowedCount) break;
                seen.put(w, seenCount + 1);
                ++totalSeen;
            }
            if (totalSeen == words.length) result.add(i);
        }
        return result;
    }

    static String minWindow(String s, String t) {
        if (s.length() < t.length()) return "";

        Map<Character, Integer> countChars = new HashMap<>();


        int[] res = new int[]{1, Integer.MAX_VALUE};

        for (int i = 0; i < t.length(); i++) countChars.put(t.charAt(i), countChars.getOrDefault(t.charAt(i), 0) + 1);

        int i = 0;
        int j = 0;

        while (i < s.length() - t.length() + 1) {
            var seen = new HashMap<Character, Integer>();
            var totalSeen = 0;

            while (j < s.length() && totalSeen < t.length()) {
                var seenCount = seen.getOrDefault(s.charAt(j),0);
                var allowedCount = countChars.getOrDefault(s.charAt(j), 0);

                if (seenCount < allowedCount)  {
                    seen.put(s.charAt(j), seenCount + 1);
                    totalSeen++;
                }

                j++;
            }

            if (totalSeen == t.length()) {
                if (j - i < res[1] - res[0]) {
                    res[0] = i;
                    res[1] = j;
                }
            }

            i++;
            j = i;
        }

        return res[1] != Integer.MAX_VALUE ? s.substring(res[0], res[1]) : "";
    }


    // https://leetcode.com/problems/minimum-window-substring/solutions/26808/here-is-a-10-line-template-that-can-solve-most-substring-problems/?envType=study-plan-v2&envId=top-interview-150
    static String minWindowV2(String s, String t) {
        int[] arr = new int[128];
        for (int i = 0; i < t.length(); i++) arr[t.charAt(i)]++;
        int counter = t.length();
        int begin = 0;
        int end = 0;
        int d = Integer.MAX_VALUE;
        int head = 0;

        while (end < s.length()) {
            if (arr[s.charAt(end++)]-- > 0) counter--;
            while (counter == 0) {
                if (end - begin < d) d = end - (head = begin);
                if (arr[s.charAt(begin++)]++ == 0) counter++;
            }
        }

        return d == Integer.MAX_VALUE ? "" : s.substring(head, head + d);
    }


    // brute force
    static int[] maxSlidingWindow(int[] nums, int k) {

        if (nums.length == 1) return nums;

        int[] res = new int[nums.length - k + 1];
        for(int i = 0; i < nums.length - k + 1; i++) {
            var max = Integer.MIN_VALUE;
            int counter = 0;
            int j = i;
            while (j < nums.length && counter++ < k + 1) {
                max = Math.max(max, nums[j++]);
            }
            res[i] = max;
        }
        return res;
    }

    public static int[] maxSlidingWindowV2(int[] nums, int k) {
        // assume nums is not null
        if (nums.length == 0 || k == 0) {
            return new int[0];
        }
        int n = nums.length;
        int[] result = new int[n - k + 1]; // number of windows

        PriorityQueue<Integer> maxPQ = new PriorityQueue<>((o1, o2) -> (nums[o2] - nums[o1])); // stores values

        for (int i = 0; i < n; ++i) {
            int start = i - k;
            if (start >= 0) {
                maxPQ.remove(nums[start]); // remove the out-of-bound value
            }
            maxPQ.offer(nums[i]);
            if (maxPQ.size() == k) {
                result[i - k + 1] = maxPQ.peek();
            }
        }
        return result;
    }

    static int[] maxSlidingWindowV3(int[] nums, int k) {
        // assume nums is not null
        int n = nums.length;
        if (n == 0 || k == 0) {
            return new int[0];
        }
        int[] result = new int[n - k + 1]; // number of windows
        Deque<Integer> win = new ArrayDeque<>(); // stores indices

        for (int i = 0; i < n; ++i) {
            // remove indices that are out of bound
            while (win.size() > 0 && win.peekFirst() <= i - k) {
                win.pollFirst();
            }
            // remove indices whose corresponding values are less than nums[i]
            while (win.size() > 0 && nums[win.peekLast()] < nums[i]) {
                win.pollLast();
            }
            // add nums[i]
            win.offerLast(i);
            // add to result
            if (i >= k - 1) {
                result[i - k + 1] = nums[win.peekFirst()];
            }
        }
        return result;
    }

    static void rotate(int[][] matrix) {
        int m = matrix.length;
        int n = matrix[0].length;

        if (m != n) return;

        for (int i = 0; i < m - 1; i++) {

            int j = i + 1;
            while (j < m) {
                int temp = matrix[i][j];
                matrix[i][j] = matrix[j][i];
                matrix[j][i] = temp;
                j++;
            }
        }

        for (int i = 0; i < m; i++) {
            int j = 0;
            int k = m - 1;
            while (j < k) {
                int temp = matrix[i][j];
                matrix[i][j] = matrix[i][k];
                matrix[i][k] = temp;
                j++;
                k--;
            }
        }
    }

    static void setZeros(int[][] matrix) {

        int col0 = 1;
        int rows = matrix.length;
        int cols = matrix[0].length;

        for (int i = 0; i < rows; i++) {
            if (matrix[i][0] == 0) col0 = 0;
            for (int j = 1; j < cols; j++)
                if (matrix[i][j] == 0)
                    matrix[i][0] = matrix[0][j] = 0;
        }

        for (int i = rows - 1; i >= 0; i--) {
            for (int j = cols - 1; j >= 1; j--)
                if (matrix[i][0] == 0 || matrix[0][j] == 0)
                    matrix[i][j] = 0;
            if (col0 == 0) matrix[i][0] = 0;
        }
    }

    static void gameOfLife(int[][] board) {
        int rows = board.length;
        int cols = board[0].length;

        for (int i = 0; i < rows; i++) {

            int countLiveNeighbors = 0;
            int j = 0;
            int k = 1;
            while (j < cols) {
                if (j == cols - 1)  {
                    k = -1;
                }
                countLiveNeighbors += board[i][j + k];
                countLiveNeighbors += board[i + 1][j];
                countLiveNeighbors += board[i + 1][j + k];

                if (j > 0 && j < cols - 1) {
                    countLiveNeighbors +=  board[i][j - k];
                    countLiveNeighbors += board[i + 1][j - k];
                }

                /*if (i > 0 && j < rows - 1) {
                    countLiveNeighbors += board[i -1][
                }
*/
                j++;
            }
        }


    }

    static void gameOfLifeV2(int[][] board) {
        if (board == null || board.length == 0) return;
        int m = board.length, n = board[0].length;

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                int lives = liveNeighbors(board, m, n, i, j);

                // In the beginning, every 2nd bit is 0;
                // So we only need to care about when will the 2nd bit become 1.
                if (board[i][j] == 1 && lives >= 2 && lives <= 3) {
                    board[i][j] = 3; // Make the 2nd bit 1: 01 ---> 11
                }
                if (board[i][j] == 0 && lives == 3) {
                    board[i][j] = 2; // Make the 2nd bit 1: 00 ---> 10
                }
            }
        }

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                board[i][j] >>= 1;  // Get the 2nd state.
            }
        }
    }

    static int liveNeighbors(int[][] board, int m, int n, int i, int j) {
        int lives = 0;
        for (int x = Math.max(i - 1, 0); x <= Math.min(i + 1, m - 1); x++) {
            for (int y = Math.max(j - 1, 0); y <= Math.min(j + 1, n - 1); y++) {
                lives += board[x][y] & 1;
            }
        }
        lives -= board[i][j] & 1;
        return lives;
    }


    static class MyHashSet {
        List<Integer>[] container = null;
        int cap = 1000;
        double loadFactor = 0.75;
        int count = 0;
        public MyHashSet() {
            container = new LinkedList[cap];
        }

        public void add(int key) {
            if (contains(key)) return;

            if (loadFactor * cap == count) {
                cap *= 2;
                List<Integer>[] oldC = container;
                container = new LinkedList[cap];
                for (int i = 0; i < oldC.length; i++) {
                    List<Integer> list = oldC[i];
                    if (list != null) {
                        for (int entry : list) {
                            this.add(entry);
                        }
                    }
                }
            }

            int hash = key % cap;
            if (container[hash] == null) {
                container[hash] = new LinkedList<>();
            }
            container[hash].add(key);
            ++count;
        }

        public void remove(int key) {
            int hash = key % cap;
            List<Integer> list = container[hash];
            if (list != null) {
                Iterator<Integer> itr = list.iterator();
                while (itr.hasNext()) {
                    if (itr.next() == key) {
                        itr.remove();
                        --count;
                        break;
                    }
                }
            }
        }

        public boolean contains(int key) {
            int hash = key % cap;
            List<Integer> list = container[hash];
            if (list != null) {
                for (Integer integer : list) {
                    if (integer == key) {
                        return true;
                    }
                }
            }
            return false;
        }
    }


    static class MyHashSetTwo {
        int[] num;
        public MyHashSetTwo() {
            num = new int[31251];
        }

        public void add(int key) {
            num[getIndex(key)] |= getMask(key);
        }

        public void remove(int key) {
            num[getIndex(key)] &= (~getMask(key));
        }

        public boolean contains(int key) {
            return (num[getIndex(key)] & getMask(key)) != 0;
        }

        private int getIndex(int key) {
            return (key / 32);
        }

        private int getMask(int key) {
            key %= 32;
            return (1 << key);
        }
    }

    static class MyHashMap {
        final ListNode[] nodes = new ListNode[10_000];


        public void put(int key, int value) {
            int index = index(key);
            if (nodes[index] == null) {
                nodes[index] = new ListNode(-1, -1);
            }
            ListNode prev = find(nodes[index], key);
            if (prev.next == null) {
                prev.next = new ListNode(key, value);
            } else {
                prev.next.value = value;
            }
        }

        public int get(int key) {
            int index = index(key);
            if (nodes[index] == null) return -1;
            ListNode node = find(nodes[index], key);
            return node.next == null ? -1 : node.next.value;
        }
        public void remove(int key) {
            int i = index(key);
            if (nodes[i] != null) {
                ListNode prev = find(nodes[i], key);
                if (prev.next != null) {
                    prev.next = prev.next.next;
                }
            }
        }


        // [0] ->  [1,2] [2,3]  [3,4]
        //

        ListNode find(ListNode bucket, int key) {
            ListNode node = bucket, prev = null;
            for (; node != null && node.key != key; node = node.next)
                prev = node;
            return prev;
        }
        private int index(int key) { return Integer.hashCode(key) % nodes.length;}

        class ListNode {
            int key, value;
            ListNode next;

            ListNode(int key, int value) {
                this.key = key;
                this.value = value;
            }
        }
    }

    static boolean containsDuplciate(int[] nums) {
        for (int i = 0; i < nums.length; i++) {
            int current = nums[i];
            int j = i - 1;
            while (j >= 0 && current < nums[j]) {
                nums[j + 1] = nums[j];
                j--;
            }
            if (j >= 0 && nums[j] == current) return true;

            nums[j + 1] = current;
        }

        return false;
    }


    static int singleNumber(int[] nums) {
        for (int i = 0; i < nums.length; i++) {
            int current = nums[i];
            int j = i - 1;
            while (j >= 0 && current < nums[j]) {
                nums[j + 1] = nums[j];
                j--;
            }

            if (j >= 0 && nums[j] != current) return current;
            nums[j + 1] = current;

        }

        return -1;
    }

    static int singleNumberV2(int[] nums) {
        int ans = 0;
        for (int num : nums) {
            ans ^= num;
        }
        return ans;
    }

    static int[] intersection(int[] nums1, int[] nums2) {
        Set<Integer> set1 = new HashSet<>();

        for (int num : nums1) {
            set1.add(num);
        }

        Set<Integer> intersect = new HashSet<>();
        for (int j : nums2) {
            if (set1.contains(j)) {
                intersect.add(j);
            }
        }

        int[] res = new int[intersect.size()];
        int i = 0;
        for (Integer num : intersect) {
            res[i++] = num;
        }

        return res;
    }

    static int[] intersectionV2(int[] nums1, int[] nums2) {
        Arrays.sort(nums1);
        Arrays.sort(nums2);

        int i = 0;
        int j = 0;

        // 1 2 3
        // 1 1 1 1

        Set<Integer> set = new HashSet<>();
        while (i < nums1.length && j < nums2.length) {
            if (nums1[i] < nums2[j]) {
                i++;
            } else if (nums1[i] > nums2[j]) {
                j++;
            } else {
                set.add(nums1[i]);
                i++;
                j++;
            }
        }

        int[] res = new int[set.size()];
        int k = 0;
        for (Integer num : set) {
            res[i++] = num;
        }
        return res;
    }

    static int[] intersectionV3(int[] a, int[] b){
        Arrays.sort(b);
        Set<Integer> set = new HashSet<>();
        for (Integer num : a) {
            if (binarySearch(num, b)) {
                set.add(num);
            }
        }

        int i = 0;
        int[] res = new int[set.size()];
        for (Integer num : set) {
            res[i++] = num;
        }
        return res;
    }

    static boolean binarySearch(int num, int[] nums) {
        int lo = 0;
        int hi = nums.length - 1;
        while (lo <= hi) {
            int mid = lo + (hi - lo) / 2;
            if (nums[mid] == num) return true;
            else if (nums[mid] > num ) {
                hi = mid - 1;
            } else {
                lo = mid + 1;
            }
        }

        return false;
    }

    static int digit(int n) {
        int sum = 0, temp;
        while (n > 1) {
            temp = n % 10;
            sum += temp * temp;
            n /= 10;
        }
        return sum;
    }

    static boolean isHappy(int n) {
        int slow, fast;
        slow = fast = n;
        do {
            slow = digit(slow);
            fast = digit(fast);
            fast = digit(fast);

        } while (slow != fast);

        return slow == 1;
    }

    static boolean isHappyV2(int n) {
        Set<Integer> inLoop = new HashSet<>();

        while (inLoop.add(n)) {
            int squareSum = 0, remain = 0;
            while (n > 0) {
                remain = n % 10;
                squareSum += remain * remain;
                n /= 10;
            }

            if (squareSum == 1) return true;
            else n = squareSum;
        }

        return false;
    }

    static int[] twoSumV2(int[] nums, int target) {
        Map<Integer, Integer> val = new HashMap<>();
        int l = 0;
        int r = nums.length - 1;
        while (l <= r) {
            int diff = target - nums[l];
            if (val.containsKey(diff)) return new int[]{l, val.get(diff)};
            val.put(nums[l],l);

            diff = target - nums[r];
            if (val.containsKey(diff)) return new int[]{val.get(diff), r};
            val.put(nums[r],r);
            l++;r--;
        }

        return new int[]{};
    }

    public int numJewelsInStones(String jewels, String stones) {
        Map<Character, Integer> countJewels = new HashMap<>();

        int res = 0;
        for (int i = 0; i < stones.length(); i++) {
            countJewels.put(stones.charAt(i), countJewels.getOrDefault(stones.charAt(i), 0) + 1);
        }

        for (int i = 0; i < jewels.length(); ++i) {
            if (countJewels.containsKey(jewels.charAt(i))) {
                res += countJewels.get(jewels.charAt(i));
            }
        }

        return res;
    }

    public int numJewelsInStonesV2(String jewels, String stones) {
        int res = 0;
        for (int i = 0; i < stones.length(); ++i) {
            if (jewels.indexOf(stones.charAt(i)) != -1) res++;
        }

        return res;
    }

    static int lengthOfLongestSubstringV3(String s) {
        int max = Integer.MIN_VALUE;


        Map<Character, Integer> map = new HashMap<>();

        for (int l = 0,  r = 0; r < s.length(); r++) {
            map.put(s.charAt(r), map.getOrDefault(s.charAt(r), 0) + 1);
            while (map.get(s.charAt(r)) > 1) map.computeIfPresent(s.charAt(l++), (k, v) -> v - 1);
            max = Math.max(max, r - l + 1);
        }

        return max;
    }

    static int lengthOfLongestSubstringV4(String s) {
        int n = s.length();
        int max = 0;
        int[] charIndex = new int[128];

        for (int i = 0, j = 0; j < n; j++) {
            char c = s.charAt(j);
            i = Math.max(charIndex[c], 0);
            max = Math.max(max, j - i + 1);
            charIndex[c] = j + 1;
        }

        return max;
    }

    static int fourSomeCount(int[] nums1, int[] nums2, int[] nums3, int[] nums4) {
        var pairCountBySum = new HashMap<Integer, Integer>();
        int fourSumCount = 0;

        for (var num1 : nums1) {
            for (var num2 : nums2) {
                pairCountBySum.compute(num1 + num2, (k, sumCount) -> sumCount == null ? 1 : ++sumCount);
            }
        }

        for (var num3 : nums3) {
            for (var num4: nums4) {
                fourSumCount += pairCountBySum.getOrDefault(-(num3 + num4), 0);
            }
        }


        return fourSumCount;
    }

    static int[] topKFrequent(int[] nums, int k) {

        if (k == nums.length) return nums;

        var count = new HashMap<Integer, Integer>();
        for (int n : nums) {
            count.put(n, count.getOrDefault(n, 0) + 1);
        }

        var heap = new PriorityQueue<Integer>((n1, n2) -> count.get(n1) - count.get(n2));

        for (int n : count.keySet()) {
            heap.add(n);
            if (heap.size() > k) heap.poll();
        }

        var top = new int[k];
        for (int i = k - 1; i >= 0; --i) {
            top[i] = heap.poll();
        }

        return top;
    }

    static int[] topKFrequentV2(int[] nums, int k) {
        var count = new HashMap<Integer, Integer>();
        for (int num : nums) count.put(num, count.getOrDefault(num,0) + 1);

        int n = count.size();
        int[] unique = new int[n];
        int i = 0;
        for (int num : count.keySet()) {
            unique[i++] = num;
        }

        quickSelect(unique, 0, n - 1, n - k, count);

        return Arrays.copyOfRange(unique, n - k, n);
    }

    static int partition(int[] arr, int left, int right, int pivotIndex, Map<Integer, Integer> map) {
        int pivotFrequency = map.get(arr[pivotIndex]);
        swap(arr, pivotIndex, right); // move pivot to the right

        int storeIndex = left;


        // Move all less frequent elements to the left
        for (int i = left; i <= right; i++) {
            if (map.get(arr[i]) < pivotFrequency) {
                swap(arr, storeIndex, i);
                storeIndex++;
            }
        }

        swap(arr, storeIndex, right);

        return storeIndex;
    }

    static void swap(int[] arr, int a, int b) {
        if (a != b) {
            arr[a] = arr[a] + arr[b];
            arr[b] = arr[a] - arr[b];
            arr[a] = arr[a] - arr[b];
        }
    }

    static void quickSelect(int[] arr, int left, int right, int kSmallest, Map<Integer, Integer> map) {
        if (left == right) return;

        Random random = new Random();
        int pivotIndex = left + random.nextInt(right - left);
        pivotIndex = partition(arr, left, right, pivotIndex, map);

        if (kSmallest == pivotIndex) {
            return;
        } else if (kSmallest < pivotIndex) {
            quickSelect(arr, left, pivotIndex - 1, kSmallest, map);
        } else {
            quickSelect(arr,pivotIndex + 1, right, kSmallest, map);
        }
    }

    static class RandomizedSet {
        Map<Integer, Integer> valToInd;
        List<Integer> list;
        int ind = 0;

        public RandomizedSet() {
            valToInd = new HashMap<>();
            list = new ArrayList<>();
        }

        public boolean insert(int val) {
            if (valToInd.containsKey(val)) return false;
            list.add(val);
            valToInd.put(val, list.size() - 1);
            return true;
        }

        public boolean remove(int val) {
            int ind = valToInd.getOrDefault(val, -1);
            if (ind == -1) return false;
            Collections.swap(list, ind, list.size() - 1);
            int swappedWith = list.get(ind);
            valToInd.put(swappedWith, ind);
            list.remove(list.size() -1);
            valToInd.remove(val);
            return true;
        }

        public int getRandom() {
            int max = list.size();
            int min = 0;
            int ind = (int) (Math.random() * (max - min) + min);
            return list.get(ind);
        }
    }

    static class RandomizedSetTwo {
        private final Random random = new Random();
        private final Map<Integer, Integer> map = new HashMap<>();
        private int[] vals = new int[32];
        private int i = 0;

        public RandomizedSetTwo(){}

        public boolean insert(int val) {
            Integer added = map.putIfAbsent(val, i);
            if (added != null) return false;

            if (i >= vals.length) {
                vals = Arrays.copyOf(vals, vals.length * 2);
            }
            vals[i++] = val;
            return true;
        }

        public boolean remove(int val) {
            Integer removed = map.remove(val);
            if (removed == null) return false;

            if (removed < i - 1) {
                vals[removed] = vals[i - 1];
                map.put(vals[i - 1], removed);
            }
            i--;
            return true;
        }

        public int getRandom(){
            int index = random.nextInt(i);
            return vals[index];
        }
    }

    static List<List<String>> groupAnagrams(String[] strs) {

        var groupByMap = new HashMap<String, List<String>>();

        for (String s : strs) {
            char[] charStr = s.toCharArray();
            Arrays.sort(charStr);
            String sorted = new String(charStr);
            if (!groupByMap.containsKey(sorted)) {
                groupByMap.put(sorted, new ArrayList<>());
            }
            groupByMap.get(sorted).add(s);
        }

        return new ArrayList<>(groupByMap.values());
    }

    static List<List<String>> groupAnagramsV2(String[] strs) {
        List<List<String>> res = new AbstractList<>() {

            List<List<String>> result = null;

            @Override
            public List<String> get(int index) {
                if (result == null) {
                    init();
                }
                return result.get(index);
            }

            @Override
            public int size() {
                if (result == null) {
                    init();
                }
                return result.size();
            }

            private void init() {
                Map<String, List<String>> anaMap = new HashMap<>();

                for (String s : strs) {
                    char[] arr = new char[26];
                    char[] strArr = s.toCharArray();
                    for (int i = 0; i < arr.length; i++) {
                        arr[strArr[i] - 'a']++;
                    }
                    String key = String.valueOf(arr);
                    anaMap.computeIfAbsent(key, k -> new ArrayList<>());
                    anaMap.get(key).add(s);
                }
                result = new ArrayList<>(anaMap.values().size());
                for (Map.Entry<String, List<String>> anaToList : anaMap.entrySet()) {
                    result.add(anaToList.getValue());
                }
            }

        };

        return res;
    }

    static int longestConsecutive(int[] nums) {
        int res = 0;
        Map<Integer, Integer> map = new HashMap<>();

        for (int num : nums) {
            if (!map.containsKey(num)) {
                int left = map.getOrDefault(num - 1, 0);
                int right = map.getOrDefault(num + 1, 0);
                int sum = left + right + 1;
                map.put(num, sum);

                res = Math.max(res, sum);


                // extend the length to the boundary(s)
                // of the sequence
                map.put(num - left, sum);
                map.put(num + right, sum);
            }
        }
        return res;
    }

    static int longestConsecuitive(int[] nums) {
        if (nums.length == 0) return 0;

        int longest = 0;

        if (nums.length < 1000) {
            Arrays.sort(nums);
            int current = 0;
            for (int i = 1; i < nums.length; i++) {
                if (nums[i] == nums[i - 1]) continue;

                if (nums[i] == nums[i - 1] + 1) {
                    current += 1;
                } else {
                    if (current + 1 > longest) longest = current + 1;
                    current = 0;
                }
            }
            return Math.max(current + 1, longest);
        }

        int min = Integer.MAX_VALUE;
        int max = Integer.MIN_VALUE;

        for (int num: nums) {
            if (num > max) max = num;
            if (num < min) min = num;
        }

        boolean[] seen = new boolean[max - min + 1];
        for (int num : nums) {
            seen[num - min] = true;
        }

        int current = 0;

        for (boolean b : seen) {
            if (b) {
                current += 1;
            } else {
                if (current > longest) {
                    longest = current;
                }
                current = 0;
            }
        }

        if (current > longest) {
            longest = current;
        }

        return longest;
    }

    static void selectionSort(int[] arr) {
        int minIndex;

        for (int i = 0; i < arr.length; i++) {
            minIndex = i;
            for (int j = i + 1; j < arr.length; j++) {
                if (arr[j] < arr[minIndex]) {
                    minIndex = j;
                }
            }
            int temp = arr[minIndex];
            arr[minIndex] = arr[i];
            arr[i] = temp;
        }
    }

    static void sortColors(int[] colors) {
        int[] arr = new int[3];
        for (int num : colors) {
            arr[num]++;
        }


        int j = 0;
        for (int i = 0; i < arr.length; i++) {
            while (arr[i]-- >= 1) {
                colors[j++] = i;
            }
        }


    }

    static void bubbleSort(int[] arr) {
        boolean hasSwapped = true;

        while (hasSwapped) {
            hasSwapped = false;
            for (int i = 0; i < arr.length - 1; i++) {
                if (arr[i] > arr[i + 1]) {
                    int temp = arr[i];
                    arr[i] = arr[i + 1];
                    arr[i + 1] = temp;
                    hasSwapped = true;
                }
            }
        }
    }


    static void insertionSort(int[] arr) {
        for (int i = 1; i < arr.length; i++) {
            int currentIndex = i;
            while (currentIndex > 0 && arr[currentIndex - 1] > arr[currentIndex]) {
                int temp = arr[currentIndex];
                arr[currentIndex] = arr[currentIndex - 1];
                arr[currentIndex - 1] = temp;
                currentIndex -= 1;
            }
        }
    }

    static ListNode insertionSortList(ListNode head) {
        ListNode dummy = new ListNode();
        ListNode current = head;

        while (current != null) {
            ListNode prev = dummy;

            while (prev.next != null && prev.next.val <= current.val) {
                prev = prev.next;
            }

            ListNode next = current.next;
            current.next = prev.next;
            prev.next = current;

            current = next;
        }

        return dummy.next;
    }

    static class ListNode {
        int val;
        ListNode next;
        ListNode(){}
        ListNode(int val) {this.val = val;}
        ListNode(int val, ListNode next) {
            this.val = val;
            this.next = next;
        }
    }

    static void heapSort(int[] arr) {
        for (int i = arr.length / 2 - 1; i >= 0; i--) {
            maxHeapify(arr, arr.length, i);
        }
    }

    static void maxHeapify(int[] arr, int heapSize, int index) {
        int left = 2 * index + 1;
        int right = 2 * index + 2;
        int largest = index;
    }

    static int[] sortArray(int[] nums) {
        int max = 0;
        int min = 0;
        for (int i : nums) {
            max = Math.max(max, i);
            min = Math.min(min, i);
        }


        int z = max - min;
        int[] freq = new int[z + 1];
        for (int j : nums) {
            freq[j - min]++;
        }

        int index = 0;
        for (int k = 0; k <= z; k++) {
            while (freq[k]-- >= 1) {
                nums[index++] = k + min;
            }
        }
        return nums;
    }

    static int findKthLargest(int[] nums, int k) {
        return quickSelectV2(nums, 0, nums.length -1, k);
    }


    //  5 3 4 6 2 1
    //
    static int quickSelectV2(int[] nums, int low, int high, int k) {
        int pivot = low;

        for (int j = low; j < high; j++) {
            if (nums[j] <= nums[high]) {
                swap(nums, pivot++, j);
            }
        }

        swap(nums, pivot, high);

        int count = high - pivot + 1;
        if (count == k) return nums[pivot];
        if (count > k) return quickSelectV2(nums, pivot + 1, high, k);
        return quickSelectV2(nums, low, pivot - 1, k - count);
    }

    static List<List<Integer>> minimumAbsDifference(int[] arr) {
        int min = Integer.MAX_VALUE;
        int max = Integer.MIN_VALUE;

        for (int num : arr) {
            min = Math.min(min, num);
            max = Math.max(max, num);
        }

        int[] sorted = new int[max - min + 1];
        for (int i = 0; i < arr.length; i++) {
            sorted[arr[i] - min]++;
        }

        int minDifference = Integer.MAX_VALUE;
        int i = 0;


        List<List<Integer>> res = new ArrayList<>();
        while (i < sorted.length - 1) {
            int j = i + 1;
            int currDifference;
            while (sorted[j] == 0) j++;
            currDifference = Math.abs(j - i);
            if (currDifference < minDifference) {
                minDifference = currDifference;
                res.clear();
                res.add(List.of(i + min, j + min));
            } else if (currDifference == minDifference){
                res.add(List.of(i + min, j + min));
            }

            i = j;
        }

        return res;
    }




    private static final int NUM_DIGITS = 10;

    static void radixSort(int[] arr) {
        int maxElem = Integer.MIN_VALUE;
        for (int elem : arr) {
            if (elem > maxElem) maxElem = elem;
        }

        int placeVal = 1;
        while (maxElem / placeVal > 0) {
            countingSort(arr, placeVal);
            placeVal *= 10;
        }
    }

    static void countingSort(int[] arr, int placeVal) {
        int[] counts = new int[NUM_DIGITS];

        for (int elem : arr) {
            int current = elem / placeVal;
            counts[current % NUM_DIGITS]++;
        }

        int startingIndex = 0;
        for (int i = 0; i < counts.length; i++) {
            int count = counts[i];
            counts[i] = startingIndex;
            startingIndex += count;
        }

        int[] sortedArray = new int[arr.length];
        for (int elem : arr) {
            int current = elem / placeVal;
            sortedArray[counts[current % NUM_DIGITS]] = elem;
            counts[current % NUM_DIGITS] += 1;
        }

        System.arraycopy(sortedArray, 0, arr, 0, arr.length);
    }

    static String countSortingString(String s) {
        int min = Integer.MAX_VALUE;
        int max = Integer.MIN_VALUE;
        char[] strArr = s.toCharArray();
        for (char c : strArr) {
            min = Math.min(c, min);
            max = Math.max(c, max);
        }

        int[] counts = new int[max - min + 1];

        for (char c : strArr) {
            counts[c - min]++;
        }

        int startingIndex = 0;
        for (int i = 0; i < counts.length; i++) {
            int count = counts[i];
            counts[i] = startingIndex;
            startingIndex += count;
        }

        char[] sorted = new char[s.length()];
        for (char c : strArr) {
            sorted[counts[c - min]] = c;
            counts[c - min] += 1;
        }

        return Arrays.toString(sorted);

    }

    static void countingSortV2(int[] arr) {
        int shift = Arrays.stream(arr).min().getAsInt();
        int K = Arrays.stream(arr).max().getAsInt() - shift;
        int[] counts = new int[K + 1];
        for (int num : arr) {
            counts[num - shift]++;
        }

        int startingIndex = 0;
        for (int i = 0; i < counts.length; i++) {
            int count = counts[i];
            counts[i] = startingIndex;
            startingIndex += count;
        }

        int[] sortedArray = new int[arr.length];
        for (int elem : arr) {
            sortedArray[counts[elem - shift]] = elem;
            counts[elem - shift] += 1;
        }

        for (int i = 0; i < arr.length; i++) {
            arr[i] = sortedArray[i];
        }

    }

    static void bucketSort(int[] arr, int K) {
        List<List<Integer>> buckets = new ArrayList<>(K);
        int shift = Arrays.stream(arr).min().getAsInt();
        int maxValue = Arrays.stream(arr).max().getAsInt() - shift;
        // place elements into buckets
        double bucketSize = (double) maxValue / K;
        if (bucketSize < 1) {
            bucketSize = 1.0;
        }
        for (int elem : arr) {
            // same as K * arr[i] / max(lst)
            int index = (int) (elem - shift / bucketSize);
            if (index == K) {
                // put the max value in the last bucket
                buckets.get(K - 1).add(elem);
            } else {
                buckets.get(index).add(elem);
            }
        }

        // sort individual buckets
        for (List<Integer> bucket : buckets) {
            Collections.sort(bucket);
        }

        // convert sorted buckets into final output
        List<Integer> sortedList = new ArrayList<Integer>();
        for (List<Integer> bucket : buckets) {
            sortedList.addAll(bucket);
        }

        // perfectly fine to just return sortedList here
        // but common practice is to mutate original array with sorted elements
        for (int i = 0; i < arr.length; i++) {
            arr[i] = sortedList.get(i);
        }

    }

    public static void main(String[] args) {

        //System.out.println(findMaxConsecuitiveOnes(new int[]{1,1,1,0,0,0,1,1,1,1,1,0,0,1,1,1,1,1,1}));

        //System.out.println(countEvenNumOfDigits(new int[]{123,5423,4223,234567,75643}));

        //int[] arr = new int[]{-7,-3,2,3,11};

       // Arrays.stream(sortedSquares(arr)).forEach(System.out::println);


        //Arrays.stream(duplicateZeros(new int[]{8,4,5,0,0,0,0,7})).forEach(System.out::println);


       /* int[] a = new int[]{3,4,20,0,0,0,0};
        int[] b = new int[]{1,4,9,15};*/

        /*int[] a = new int[]{0,0,0,0};
        int[] b = new int[]{1,4,9,15};
        int m = 0;
        int n = 4;

        merge(a,m,b,n);
        Arrays.stream(a).forEach(System.out::println);
*/

       /* int[] nums = new int[]{3,2,2,3};
        int val = 3;

        int k = removeElement(nums,val);

        for(int num : nums) {
            System.out.print(num + ",");
        }

        System.out.println("k: " + k);*/


        /*int[] dupArray = new int[]{0,0,1,1,1,2,2,3,3,4};
        int k = removeDuplicates(dupArray);
        for (int num : dupArray) {
            System.out.print(num + ",");
        }

        System.out.println(k);*/

        //System.out.println(checkIfExists(new int[]{3,1,7,11}));



        //System.out.println(validMountainArray(new int[]{0,1,2,3,4,5,6,7,8,9}));


       // replaceElements(new int[]{17,18,5,4,6,1});

        //moveZerosToEnd(new int[]{0,1,0,3,12});

        //sortArrayByParity(new int[]{3,1,2,4});

        //System.out.println(heightChecker(new int[]{5,1,2,3,4}));

        //System.out.println(thirdMaxSorted(new int[] {2,2,3,3,5,5}));

        //dissapearedNumbers(new int[]{1,1});
        //dissapearedNums(new int[]{4,3,2,7,8,2,3,1});
        //findDissapearedNumbers(new int[]{4,3,2,7,8,2,3,1});

       // Arrays.stream(sortedSquares2(new int[]{-4, -1, 0, 3, 10})).forEach(System.out::println);

        //System.out.println(maxConsecutiveOnes(new int[]{1,0,0,0,1,1,0,1,1,0}));

        //System.out.println(findPivotIndex(new int[] {2,3,-1,8,4}));
       // System.out.println(dominantIndex(new int[]{3,6,1,0}));

        //Arrays.stream(plusOne(new int[]{7,2,8,5,0,9,1,2,9,5,3,6,6,7,3,2,8,4,3,7,9,5,7,7,4,7,4,9,4,7,0,1,1,1,7,4,0,0,6})).forEach(System.out::println);


       // spiralOrder(new int[][]{{2,5,8}, {4, 0, -1}}).stream().forEach(System.out::println);

        //generate(5);

        //System.out.println(addBinary("1010","1011"));

       // System.out.println(strStr2("leetcode", "leeto"));

        //System.out.println(longestCommonPrefixFaster(new String[]{"flo","car"}));
        //reverseString(new char[]{'o','r','i','k'});

        //Stream.of(arrayPairSum(new int[]{6,2,6,5,1,2})).forEach(System.out::println);

        //Arrays.stream(twoSum(new int[]{-1,0}, -1)).forEach(System.out::println);
        //Arrays.stream(twoSumFaster(new int[]{2,3,4}, 6)).forEach(System.out::println);
        //System.out.println(minSubArrayLen(15, new int[]{1,7,8,4,3,2,15}));

        //Arrays.stream(rotateArrayReverse(new int[]{1,2,3,4,5}, 3)).forEach(System.out::println);

        //System.out.println(getRow(3));

        //System.out.println(getRowPascal(3));

        //System.out.println(reverseWordFast(" this is orik"));

        //System.out.println(reverseWordsInString("Let's take LeetCode contest"));

        //findMiddleIndexV3(new int[]{2,3,-1,8,4});

        //System.out.println(isGood(new int[]{1,3,3,2}));
        //System.out.println(isGoodV2(new int[]{1,2,3,4,7,6,5,7}));
        //System.out.println(addToArrayFrom(new int[]{1,2,0,0}, 34));
        //System.out.println(addToArrayFormV2(new int[]{9,9,5}, 987));

        //System.out.println((int)'z');

        //System.out.println(isAnagram("abba","baba"));

        //System.out.println(removeAnagrams(new String[]{"a"}));

        //
        // "z","z","z","gsw","wsg","gsw","krptu"
        //System.out.println('z' - 97);

        //System.out.println(removeAnagramsV2(new String[]{"a","e","a"}));


        //System.out.println(isAnagram("orik", "kiro"));

        //System.out.println(findMaxLength(new int[]{0,1,0}));
        //findMaxLength(new int[]{1,0,0,1,0,1,1});
        //findMaxLengthV2(new int[]{1,1,0});

        //System.out.println(arrayNesting(new int[]{0,1,2}));

        //arrayNesting(new int[]{5,4,0,3,1,6,2});

        //System.out.println(removeDuplicatesTWOMedium(new int[]{1,1,1,2,2,3}));


        //System.out.println(canCompleteCircuitV2(new int[]{1,2,3,4,5}, new int[]{3,4,5,1,2}));

        //productExceptSelf(new int[]{-1,1,0,-3,3});
        //canJump(new int[]{2,0});

        //canJumpV2(new int[]{3,1,0,7,4});
        //canJump2(new int[]{1,2,4,1,1,1,1});

        //System.out.println(convert("PAYPALISHIRING", 3));

        //fullJustifyV2(new String[]{"What","must","be","acknowledgment","shall","be"}, 16);

        //candy(new int[]{1,0,2});
        //System.out.println(trap(new int[]{4,2,0,3,2,5}));
        //maxArea(new int[]{1,1});

        //threeSum(new int[]{-2,0,3,-1,4,0,3,4,1,1,1,-3,-5,4,0}).forEach(System.out::println);

        //fourSum(new int[]{1000000000,1000000000,1000000000,1000000000}, -294967296).forEach(System.out::println);

        //System.out.println(lengthOfLongestSubstring("dvdf"));



        String a = "foobarthebar";

        //System.out.println(a.indexOf("bar", 5));


       // System.out.println(findSubstring("ababaab", new String[]{"ab","ba","ba"}));


        //findSubstringV3("barfoofoobarthefoobarman", new String[]{"bar","foo","the"});
        //System.out.println(minWindow("ADOBECODEBANC", "ABC"));

       // minWindowV2("ADOBECODEBANC", "ABC");
       // System.out.println((int)'a' - 'A');

        //minWindowV2("ADOBECODEBANC", "ABC");

        //System.out.println(maxSlidingWindow(new int[]{1,3,-1,-3,5,3,6,7}, 3));


        //maxSlidingWindowV3(new int[]{1,3,-1,-3,5,3,6,7}, 3);


        //rotate(new int[][]{{5,1,9,11},{2,4,8,10},{13,3,6,7},{15,14,12,16}});

        //setZeros(new int[][]{{0,1,2,0}, {3, 4, 5, 2}, {1, 3, 1, 5}});

        //gameOfLifeV2(new int[][]{{1,1},{1,0}});

      /*  MyHashSet obj = new MyHashSet();

        for (int i = 1; i <= 1000000; i++) {
            obj.add(i);
        }


        List<Integer> list = obj.container[1];
        for (Integer n : list) {
            System.out.print(n + " ");
        }
        System.out.println(list.size());*/

       /* MyHashSetTwo t = new MyHashSetTwo();
        t.add(37);
        t.add(36);

        t.contains(37);
        t.remove(37);*/
        //System.out.println( 5<<1);

       // System.out.println(5>>1);


       // System.out.println(~(1<<5));


      //  System.out.println(-32&(~48));

        //containsDuplciate(new int[]{5,4,3,2,1,2,5});

        //singleNumber(new int[]{3,1,2,4,2,4});

        //isHappyV2(2);


        // [4,1,-1,2,-1,2,3]
        //2
        //topKFrequent(new int[]{4,1,-1,2,-1,2,3}, 2);

        //topKFrequentV2(new int[]{5,5,2,1,1,3,4,1,5,2,5,2,3}, 3);


       /* RandomizedSet rs = new RandomizedSet();

        rs.insert(3);
        rs.insert(4);
        rs.insert(2);
        rs.insert(5);
        rs.remove(4);*/


       // System.out.println(Math.random());

        //groupAnagramsV2(new String[]{"eat","tea","tan","ate","nat","bat"});

       /* String s = "nat";
        char[] arr = new char[26];


        char[] strArr = s.toCharArray();
        for (int i = 0; i < s.length(); i++) {
            arr[strArr[i] - 'a']++;
        }

        StringBuilder sb = new StringBuilder();*/

       /* for (int i = 0; i < arr.length; i++) {
            while (arr[i]-- > 0) {
                sb.append(arr[i] + 'a');
            }
        }
        //String key = String.valueOf(arr);
        System.out.println(sb);*/

        //longestConsecutive(new int[]{0,3,7,2,5,8,4,6,0,1});


       // sortColors(new int[]{2,0,2,1,1,0});

        //findKthLargest(new int[]{3,2,3,1,2,4,5,5,6}, 4);

        //System.out.println(minimumAbsDifference(new int[]{3,8,-10,23,19,-4,-14,27}));

        //radixSort(new int[]{256,336,736,443,831,907});

        countSortingString("orai");

    }
}
