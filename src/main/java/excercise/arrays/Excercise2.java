package excercise.arrays;

import java.util.*;

public class Excercise2 {

    static int arrayPairSum(int[] nums) {

        int[] arr = new int[20001];
        for (int i = 0; i < nums.length; i++) {
            arr[10000 + nums[i]]++;
        }

        int sum = 0;
        boolean odd = true;
        for (int i = 0; i < arr.length; i++) {
            while (arr[i] > 0) {
                if (odd) {
                    sum += i - 10000;
                }
                odd = !odd;
                arr[i]--;
            }
        }
        return sum;
    }

    static boolean isMonotonic(int[] nums) {

        if (nums.length == 1) return true;
        boolean increase = false;
        boolean decrease = false;

        for (int i = 1; i < nums.length;i++) {
            if (nums[i] == nums[i - 1]) continue;

            if (nums[i] > nums[i - 1]) {
                increase = true;
            } else if (nums[i] < nums[i - 1]) {
                decrease = true;
            }

            if (increase && decrease) return false;
        }

        return true;
    }

    static boolean isMonotonicV2(int[] nums) {

        if (nums.length == 1) return true;
        boolean up = nums[nums.length - 1] - nums[0] > 0;
        for (int i = 0; i < nums.length - 1; i++) {
            if (nums[i+1] != nums[i] && (nums[i+1] - nums[i] > 0 != up)) return false;
        }
        return true;
    }

    static boolean isMonotonicV3(int[] nums) {
        if (nums.length < 2) return true;

        int direction = 0;
        for (int i = 1; i < nums.length; i++) {
            if (nums[i] > nums[i-1]) {
                if (direction == 0) {
                    direction = 1;
                } else if(direction == -1) {
                    return false;
                }
            } else if (nums[i] < nums[i - 1]) {
                if (direction == 0) {
                    direction = -1;
                } else if (direction == 1) {
                    return false;
                }
            }
        }
        return true;
    }


    static void mergeSortedArray(int[] nums1, int m, int[] nums2, int n) {
        //  nums1 = [1,2,3,4,5,6], m = 3, nums2 = [1,2,3], n = 3
        // check max(m,n)
        //  (3,1) 3
        //  (3,3) 3
        //  (2,2) 3
        //
        m -= 1;
        n -= 1;
        int index = nums1.length - 1;
        while (index >= 0) {
            if (n < 0) break;
            nums1[index--] = (m > 0 && nums1[m] > nums2[n]) ? nums1[m--] : nums1[n--];
        }
        Arrays.stream(nums1).forEach(System.out::println);
    }

    static boolean validMountArray(int[] arr) {

        boolean isAscending = false;
        boolean isDescending = false;

        for (int i = 0; i < arr.length - 1; i++) {
            if (arr[i] < arr[i + 1] && !isDescending) isAscending = true;
            else if (isAscending && arr[i] > arr[i+1]) isDescending = true;
            else return false;
        }

        return isAscending && isDescending;
    }

    static boolean validMountArrayV2(int[] arr) {
        if (arr.length <= 2) {
            return false;
        }

        boolean isAscending = false;
        boolean isDescending = false;
        int i = 0;
        while (i < arr.length - 1 && arr[i] < arr[i+1]) {
            i++;
            isAscending = true;
        }

        while (i < arr.length - 1) {
            if (arr[i] <= arr[i+1]) return false;
            i++;
            isDescending = true;
        }

        return isAscending && isDescending;
    }

    static boolean validMountV3(int[] arr) {
        if (arr.length < 3) return false;


        int start = 0;
        int end = arr.length - 1;


        // 2,3,1,3,2,4,6,7,9,2,19
        // 2,1,4,3,9,6

        //
        while (start < end) {
            if (arr[start + 1] > arr[start]) start++;
            else if  (arr[end - 1] > arr[end]) end--;
            else break;
        }

        return start > 0 && end < arr.length - 1 && start == end;
    }

    static int[] relativeSortArray(int[] arr1, int[] arr2) {

        int currIndex =0;
         for (int i = 0; i < arr2.length; i++) {
             for (int j = currIndex; j < arr1.length; j++) {
                 if (arr1[j] == arr2[i]) {
                     int temp = arr1[currIndex];
                     arr1[currIndex++] = arr1[j];
                     arr1[j] = temp;
                 }
             }
         }

         Arrays.sort(arr1,currIndex,arr1.length);

         return arr1;
    }

    static int[] relativeSortArrayV2(int[] arr1, int[] arr2) {
        int[] cnt = new int[1001];
        for (int n : arr1) cnt[n]++;
        int i = 0;

        for (int n : arr2) {
            while (cnt[n]-- > 0) {
                arr1[i++] = n;
            }
        }

        for (int n = 0; n < cnt.length; n++){
            while (cnt[n]-- > 0) {
                arr1[i++] = n;
            }
        }
        return arr1;
    }

    // TreeMap
    static int[] relativeSortArrayV3(int[] arr1, int[] arr2) {
        TreeMap<Integer, Integer> map = new TreeMap<>();
        for (int n : arr1) map.put(n, map.getOrDefault(n,0) + 1);
        int i = 0;
        for (int n : arr2) {
            for (int j = 0; j < map.get(n); j++) {
                arr1[i++] = n;
            }
            map.remove(n);
        }

        for (int n : map.keySet()) {
            for (int j = 0; j < map.get(n); j++) {
                arr1[i++] = n;
            }
        }
        return arr1;
    }

    static int[] shuffle(int[] nums, int n) {

        if (nums.length == 2) return nums;

        // 1,2,3,4,5,|6,7,8,9,10  n = 5
        // 1,6,2,7,3,|8,7,5,9,10
        //              i
        //                  j

        //       i    j     n = 5

       //  i = 0
        // j = 0


        // arr[i] = arr[i+1]
        // arr[i+1] = arr[n+i]



        // 2,3,4,5,1,7
        //     i j
        int i = 1;
        int j = nums.length - 2;


        while (i < j) {
            int temp = nums[n - 1];
            nums[n - 1] = nums[j];
            nums[j--] = temp;

            if (i < j) {
                temp = nums[n];
                nums[n] = nums[i];
                nums[i++] = temp;
            }
        }
        // 1,5,2,6,3,7,4,8
        //       i j    (swap m-1 with j)  (swap m+1 with i)   i++ j--;
        //


        // from left take the > mid left elements and put rihgt
        // from right take the

        return nums;
    }

    static int[] shuffleV2(int[] nums, int n) {
        int res[] = new int[n * 2];
        for (int i = 0; i < n; i++) {
            res[2 * i] = nums[i];
            res[2 * i + 1] = nums[n + i];
        }
        return res;
    }

    static int[] shuffleV4(int[] nums, int n) {
        // [1,2,3,4,5,6]
        // [1+50000]
        //

        for (int i = 0; i < n; i++){
            nums[i] += nums[n+i] * 10000;
        }

        for (int i = n - 1; i >= 0; i--) {
            nums[2 * i + 1] = nums[i] / 10000;
            nums[2 * i] = nums[i] % 10000;
        }
        return nums;
    }

    static int[] shuffleV3(int[] nums, int n) {

        int i = nums.length - 1;
        int j = n - 1;

        // 1,2,3,4,5,6
        // j     i
        while (i >= n) {
            nums[i] <<=10;
            nums[i] |= nums[j]; // or += 10

            i--;
            j--;

        }

        i = 0;
        j = n;
        while (j < nums.length) {
            int num1 = nums[j] & 1023;
            int num2 = nums[j] >> 10;
            nums[i] = num1;
            nums[i + 1] = num2;
            i += 2;
            j++;
        }

        return nums;
    }

    static int xorOperation(int n, int start) {

        int xor = 0;
        for (int i = 0; i < n; i++) {
            xor ^= start + (2 * i);  // 0
            System.out.println(xor);
        }

        return xor;
    }

    // 010
    // 011
    //  10
    static int XORforN(int val) {
        return switch (val & 3) {
            case 0 -> val;
            case 1 -> 1;
            case 2 -> val + 1;
            default -> 0;
        };
    }

    static int xorOperationV2(int n, int start) {
        int val1 = start & 1 & n;
        int val2 = XORforN((start >> 1) + n - 1);
        int val3 = XORforN((start >> 1) - 1);
        int val4 = (val2 ^ val3);
        return val1 | val4;
    }

    static int[] decodeXOR(int[] encoded, int first) {
        int[] arr = new int[encoded.length + 1];


        arr[0] = first ^ encoded[0];
        for (int i = 1; i < arr.length; i++) {
            arr[i] = encoded[i - 1] ^ arr[i-1];
        }
        return arr;

    }

    public int[] getConcatenation(int[] nums) {
        int[] ans = new int[nums.length * 2];

        // 123
        // 12312
        for (int i = 0; i < nums.length; i++) {
            ans[i + nums.length] = ans[i] = nums[i];
        }
        return ans;
    }

    // [2,3,4,5,5,11,10,4]
    // [11]
    // [
    static int findShortestSubArray(int[] nums) {
        int[] freq = new int[50001];

        int maxFreq = Integer.MIN_VALUE;
        for (int num : nums) {
            freq[num]++;
            maxFreq = Math.max(maxFreq, freq[num]);
        }

        int minSubArray = Integer.MAX_VALUE;

        for (int i = 0; i < nums.length; i++) {
            if (freq[nums[i]] == maxFreq) {

                int end = i + 1;
                freq[nums[i]]--;

                while (freq[nums[i]] > 0 && end < nums.length) {
                    if (nums[end] == nums[i]) {
                        freq[nums[i]]--;
                    }
                    end++;
                }

                if (end - i == maxFreq) return end - i; // if two numbers have same degree and same subarray size, take first.

                minSubArray = Math.min(minSubArray, end - i);   // 5
            }
        }

        return minSubArray;
    }

    static int findShortestSubArrayV2(int[] nums) {
        int max = -1;
        for (int num : nums) {
            if (num > max) max = num;
        }

        MinMax[] minmax = new MinMax[max + 1];
        int maxFreq = 0;
        for (int i = 0; i < nums.length; i++) {
            if (minmax[nums[i]] == null) minmax[nums[i]] = new MinMax(i);
            minmax[nums[i]].max = i;
            minmax[nums[i]].count++;

            if  (minmax[nums[i]].count > maxFreq) maxFreq = minmax[nums[i]].count;
        }

        int minArray = Integer.MAX_VALUE;
        for (int i = 0; i < minmax.length; i++) {
            if (minmax[i] != null && minmax[i].count == maxFreq) {
                minArray = Math.min(minArray, minmax[i].max - minmax[i].min + 1);
                break;
            }
        }
        return minArray;

    }

    private static class MinMax {
        int count = 0;
        int min = -1;
        int max = -1;
        public MinMax(int i) {
            this.min = i;
            this.max = i;
        }
    }

    public static int[] getConcatenationV2(int[] nums) {
        int n = nums.length;
        int[] ans = new int[2 * n];

        System.arraycopy(nums, 0, ans, 0, n);
        System.arraycopy(ans, 0, ans, n, n);

        System.gc();
        return ans;
    }

    static int[] buildArray(int[] nums) {

       for (int i = 0; i < nums.length; i++) {
            nums[i] = nums[i] | ((nums[nums[i]] & 1023) << 10);
        }

        for (int i = 0; i < nums.length; i++) {
            nums[i] >>= 10;
        }

        return nums;
    }

    public static int[] buildArrayV2(int[] nums) {
        int n = nums.length;

        for(int i=0; i<n; i++){
            // this is done to keep both old and new value together.
            // going by the example of [5,0,1,2,3,4]
            // after this nums[0] will be 5 + 6*(4%6) = 5 + 24 = 29;
            // now for next index calulation we might need the original value of num[0] which can be obtain by num[0]%6 = 29%6 = 5;
            // if we want to get just he new value of num[0], we can get it by num[0]/6 = 29/6 = 4
            nums[i] = nums[i] + n*(nums[nums[i]] % n);
        }

        for(int i=0; i<n; i++){
            nums[i] = nums[i]/n;
        }

        return nums;
    }


    public static int[] buildArrayV4(int[] nums) {
        nums=apermutation(nums,0);
        return nums;
    }
    static int[] apermutation(int[] nums, int start){
        if(start < nums.length){
            int result=nums[nums[start]];

            // [2,1,3,0]
            // [3,1,0,2]
            apermutation(nums,start+1);
            nums[start]=result;
        }
        return nums;
        // [       0]
    }

    static int[] buildArrayV5(int[] nums) {
        int[] res = new int[nums.length];
        for (int i = 0; i < nums.length; i++) {
            res[i] = nums[nums[i]];
        }
        return res;
    }

    static int[] arrayRankTransformation(int[] arr) {

        if (arr.length < 1) return arr;
        if (arr.length < 2) return new int[]{1};

        int[] copyArr = Arrays.copyOf(arr, arr.length);
        Arrays.sort(copyArr);

        HashMap<Integer, Integer> map = new HashMap<>();
        for (int x : copyArr) {
            map.putIfAbsent(x,map.size() + 1);
        }

        for (int i = 0; i < arr.length; i++) {
            copyArr[i] = map.get(arr[i]);
        }

        return copyArr;
    }

    static int[] arrayRankTransformationV2(int[] arr) {
        if (arr.length == 0) return new int[0];
        if (arr.length == 1) return new int[]{1};
        Map<Integer, Integer> map = new HashMap<>();
        int[] sorted = arr.clone();
        Arrays.sort(sorted);
        int rank = 1;
        map.put(sorted[0], rank++);
        for (int i=1; i<sorted.length; i++) {
            if (sorted[i] != sorted[i-1]) {
                map.put(sorted[i], rank++);
            }
        }
        for (int i=0; i<arr.length; i++) {
            sorted[i] = map.get(arr[i]);
        }
        return sorted;
    }


    static List<String> stringMatching(String[] words) {
        int n = words.length;
        List<String> ans = new ArrayList<>();
        for(int i = 0; i < n; i++){
            String s = words[i];
            if(check(words , s , i)) {
                ans.add(s);
            }
        }
        return ans;
    }
    static boolean check(String[] words , String x , int i){
        int n = words.length;
        for(int j = 0; j < n; j++){
            String s = words[j];
            if(i != j && s.contains(x)) {
                return true;
            }
        }
        return false;
    }

    static int[] frequencySort(int[] nums) {
        Map<Integer, Integer> map = new HashMap<>();
        for (int num : nums) map.put(num, map.getOrDefault(num, 0) + 1);

        List<Map.Entry<Integer, Integer>> list = new ArrayList<>(map.entrySet());
        list.sort((a, b) -> a.getValue() == b.getValue() ? b.getKey() - a.getKey() : a.getValue() - b.getValue());
        int index = 0;
        for (Map.Entry<Integer, Integer> entry : list) {
            int count = entry.getValue();
            int key = entry.getKey();

            for (int i = 0; i < count; i++) {
                nums[index++] = key;
            }
        }

        return nums;
    }

    static int[] frequencySortV2(int[] nums) {
        int[] count = new int[201];
        for (int num : nums) count[num + 100]++;

        int max = 0;
        for (int j : count) {
            if (j > max) max = j;
        }

        int k = 0;
        for (int i = 1; i <= max; i++) {
            for (int l = count.length - 1; l >= 0; l--) {
                if (count[l] == i) {
                    for (int j = 0; j < i; j++) {
                        nums[k++] = l - 100;
                    }
                }
            }
        }
        return nums;
    }

    static int getMaximumGenerated(int n) {
        int[] res = new int[n + 1];
        res[0] = 0;
        res[1] = 1;

        // nums]2 * i] = nums[i] when 2 <= 2 * i <= n
        return -1;
    }

    public static int[][] construct2DArray(int[] original, int m, int n) {
        if (original.length != m * n) return new int[][]{};
        int[][] arr = new int[m][n];

        int index = 0;
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                arr[i][j] = original[index++];
            }
        }
        return arr;
    }

    static int[][] construct2DArrayV2(int[] original, int m, int n) {
        if (original.length != m * n) return new int[][]{};

        int[][] res = new int[m][n];
        for (int i = 0; i < m; i++) {
            System.arraycopy(original, i * n, res[i], 0, n);
        }
        System.gc();
        return res;
    }



    static boolean divideArray(int[] nums) {
        int[] freq = new int[501];
        for(int num : nums) freq[num]++;

        for (int num : freq) {
            if (num % 2 != 0) return false;
        }

        return true;
    }

    static int[] applyOperations(int[] nums) {
        int firstZero = -1;

        for (int i = 0; i < nums.length - 1; i++) {
            if (firstZero == -1 && (nums[i] == 0)) firstZero = i;
            if (nums[i] == nums[i + 1]) {
                nums[i] *= 2;
                nums[i + 1] = 0;
                i++;
            }
        }

        if (firstZero != -1) {
            for (int i = firstZero; i < nums.length - 1; i++) {
                if (nums[i + 1] != 0) {
                    nums[firstZero] = nums[i + 1];
                    nums[i + 1] = 0;
                    firstZero++;
                }
            }
        }
        return nums;
    }

    static long findTheArrayConcVal(int[] nums) {
        long res = 0;
        int l = 0, r = nums.length - 1;

        while (l <= r) {
            long b = nums[r];

            long hundredsCount = 0;

            if (l != r) {
                while (b > 0) {
                    b /= 10;
                    hundredsCount++;
                }
            }

            res += (nums[l] * (hundredsCount == 0 ? 0 : Math.pow(10,hundredsCount)) + nums[r]);

            l++;
            r--;
        }
        return res;
    }

    static long findTheArrayConcValV2(int[] nums) {
        long total = 0;
        int i = 0;
        int j = nums.length - 1;

        while (i <= j) {
            total += (i == j) ? nums[i] : concatenate(nums[i++], nums[j--]);
        }

        return -1;
    }

    static long concatenate(int a, int b) {
        int temp = b;
        while (temp > 0) {
            a *= 10;
            temp /= 10;
        }
        return a + b;
    }

    static int[][] mergeArrays(int[][] nums1, int[][] nums2) {
        Map<Integer, Integer> tree = new TreeMap<>();

        for (int[] ints : nums1) {
            tree.put(ints[0], ints[1]);
        }
        for (int[] ints : nums2) {
            if (tree.containsKey(ints[0])) tree.put(ints[0], tree.get(ints[0]) + ints[1]);
            else tree.put(ints[0], ints[1]);
        }

        int[][] res = new int[tree.size()][2];

        int i = 0;
        for (Map.Entry<Integer, Integer> val : tree.entrySet()) {
            res[i++] = new int[]{val.getKey(), val.getValue()};
        }
        return res;
    }

    static int[][] mergeArraysV2(int[][] nums1, int[][] nums2) {
        int i = 0, j = 0;

        List<int[]> res = new ArrayList<>();

        while (i < nums1.length || j < nums2.length) {
            if (i < nums1.length && (j == nums2.length || nums1[i][0] < nums2[j][0])) {
                res.add(nums1[i]);
                i++;
            } else if (j < nums2.length && (i == nums1.length || nums1[i][0] > nums2[j][0])) {
                res.add(nums2[j]);
                j++;
            } else {
                res.add(new int[]{nums1[i][0], nums1[i][1] + nums2[j][1]});
                i++;
                j++;
            }
        }
        return res.toArray(new int[res.size()][]);
    }

    //1
    //2 3
    //4 5 6
    //7 8 9 10
    //11 12 13 14'

    // 9 * (9 + 1) / 2
    // 45

    // n = 45
    // 1 + 2 + 3 + (m - 1) + m = m * (m - 1) / 2
    //
    // 2 * (2 + 1) / 2 -> 3


    // 1
    // 2 3
    // 4 5 6
    // 7 8 9 10
    // 11 12 13 14 15
    // 16 17 18 19 20 21
    // 22 23 24 25 26 27 28
    // 29 30 31 32 33 34 35 36
    // 37 38 39 40 41 42 43 44 45

    // 20
    // 1 + 2 + 3 + 4 + 5 + 6 + 7 +

    // x * (x + 1) / 2 <= 44
    //

    // 1
    // 1 1
    static int arrangeCoins(int n) {
        int count = 1;
        while (n > 0) {
            count++;
            n -= count;
        }
        return count - 1;

    }


    // 1 + 2 + 3 + 4 => m (m + 1) / 2 <= n

    // n = 5
    // m = 1
    // 1 * (1 + 1 ) / 2 -> 2 <= n
    // 2 * (2 + 1) / 2 ->  3 <= n
    // 3 * (3 + 1) / 2 ->  6

    static int arrangeCoinsV2(int n) {
        if (n <= 1) return n;
        if (n <= 3) return n == 3 ? 2 : 1;

        long start = 2;
        long end = n / 2;

        while (start <= end) {
            long mid = start + (end - start) / 2;
            long coinsFilled = mid * (mid + 1) / 2;
            if (coinsFilled == n) return (int) mid;

            if (coinsFilled < n) { start = mid + 1; }
            else { end = mid - 1;}
        }

        return (int) end;
    }

    static int arrangeCoinsV3(int n) {

        int mask = 1 << 15;

        long result = 0;

        //  1000000000000000
        while (mask > 0) {
            long temp = result | mask;
            long coinsFilled = temp * (temp + 1) / 2;
            if (coinsFilled == n) return (int) temp;

            if (coinsFilled < n) {
                result = temp;
            }
            mask >>= 1;
        }
        return -1;
    }

    static List<Integer> intersection(int[][] nums) {

        List<Integer> numsIntersectList = new ArrayList<>();
        int[] countNr = new int[1001];

        int maxNum = -1;
        for (int[] numsArr : nums) {
            for (int i : numsArr) {
                countNr[i]++;
                if (countNr[i] == nums.length) numsIntersectList.add(i);
            }
        }

        Collections.sort(numsIntersectList);
        return numsIntersectList;
    }

    static int countHillValley(int[] nums) {
        if (nums.length <= 2) return 0;

        int count = 0;
        for (int i = 1, j = 0; i < nums.length - 1; i++) {
            if ((nums[j] < nums[i] && nums[i] > nums[i + 1]) ||
                    (nums[j] > nums[i] && nums[i] < nums[i + 1])) {
                count++;
                j = i;
            }
        }
        return count;
    }

    static int minimumOperations(int[] nums) {
        int[] numsFreq = new int[101];
        int max = -1;
        for (int num : nums) {
            numsFreq[num]++;
            if (num > max) max = num;
        }

        numsFreq[0] = 0;

        int count = 0;
        for (int i = 0; i <= max; i++) {
            if (numsFreq[i] > 0) count++;
        }
        return count;
    }

    static int minimumRightShifts(List<Integer> nums) {

        if (nums.size() < 2 ) return 0;
        int pivot = nums.size() - 1;

        int countTimes = 0;
        int prevIndex = nums.size() - 1;
        for (int i = 0; i < nums.size(); i++) {
            if (nums.get(i) < nums.get(prevIndex)) {
                if (++countTimes > 1) return -1;
            }
            pivot = nums.get(i) > nums.get(pivot) ? pivot : i;
            prevIndex = i;
        }

        return (nums.size() - pivot) % nums.size();

    }

    static boolean isPrefixString(String s, String[] words) {
        StringBuilder sb = new StringBuilder();
        for (String word: words) {
            sb.append(word);
            if (sb.length() == s.length() && s.equals(sb.toString())) return true;
            if (!s.contains(sb.toString())) return false;
        }
        return false;
    }

    static boolean isPrefixStringV2(String s, String[] words) {
        int index = 0;
        for (String word: words) {
            if (index + word.length() > s.length()) {
                return false;
            }
            String substring = s.substring(index, index + word.length());
            if (substring.equals(word)) {
                index += word.length();
                if (index == s.length()) {
                    return true;
                }
            } else {
                return false;
            }
        }
        return false;
    }

    static int findKthLargest(int[] nums, int k) {
        return quickSelect(nums, 0, nums.length - 1, k);
    }

    static int quickSelect(int[] nums, int low, int high, int k) {
        int pivot = low;



        // 5 3 4 6 2 1
        // 2 4 3 3 5 6
        //
        // all
        for (int j = low; j < high; j++) {
            if (nums[j] <= nums[high]) {
                swap(nums,pivot++, j);
            }
        }

        swap(nums, pivot, high);

        int count = high - pivot + 1;
        if (count == k) return nums[pivot];
        if (count > k) return quickSelect(nums, pivot + 1, high, k);
        return quickSelect(nums, low, pivot - 1, k - count);

    }

    static void swap(int[] arr, int a, int b) {
        arr[a] = arr[a] + arr[b];
        arr[b] = arr[a] - arr[b];
        arr[a] = arr[a] - arr[b];
    }

    public static void main(String[] args) {

        //System.out.println(isMonotonic(new int[]{1,3,2}));
        //validMountArray(new int[]{1,2,3,2,3});
        //System.out.println(validMountV3(new int[]{1,2,3,2,1}));
        //Arrays.stream(relativeSortArray(new int[]{26,21,11,20,50,34,1,18}, new int[]{21,11,26,20})).forEach(System.out::println);

        //Arrays.stream(shuffle(new int[]{7,5,9,7,5,8,10,4,3,3,2,5,9,10}, 7)).forEach(System.out::println);


        int a = 1000;
        a <<= 10;

        a |= 10;

        int b = 6;
        b <<= 10;
        //System.out.println(b);

        //System.out.println(a);

        //Arrays.stream(shuffleV4(new int[]{1, 2, 3, 4, 5, 6}, 3)).forEach(System.out::println);

        //       0000000000001111101000 0000000000
        //       0000000000001111101000 0000001010
        //                              1111111111
        //                              0000001010 -> 10




        //
        //xorOperation(5,0);

       //Arrays.stream(decodeXOR(new int[]{0,0,5,3}, 7)).forEach(System.out::println);



        // n = 4  start = 3
        // 3/2 + 4 - 1 -> 4
        // 3 / 2
        // 1,2,3,4  3/1 = 1  4/2
        // 0,1,2,3
        // 4 xor 0 =
        //   100
        //     0
        //

        //System.out.println(xorOperationV2(4, 6));

        // 18,20, 22, 24

        // 9,10,11,12
        // start + 2 * i (end range)
        // start + 2 * (n - 1) (end range) 18 + 2 * 3 = 24
        // start = 18 to 24 is same as 9 to 12 9 10 11 12
        //    1001
        //    1001

        //Arrays.stream(getConcatenationV2(new int[]{1, 2, 3})).forEach(System.out::println);

        //System.out.println(findShortestSubArray(new int[]{1,2,2,3,1,4,2}));


        int[] c = new int[6];
        c[0] = 0;
        c[1] = 2;
        c[2] = 1;
        c[3] = 5;
        c[4] = 3;
        c[5] = 4;

        c[1] = c[c[1] & 1023] << 10 | c[1];

        // System.out.println(c[1] & 1023);
        // System.out.println(c[1] >> 10);

        //                                   0000000000
        //                                  10000000000

        //Arrays.stream(buildArray(new int[]{0, 2, 1, 5, 3, 4})).forEach(System.out::println);

        //buildArrayV2(new int[]{5,2,1,4,3,0});

        //buildArrayV4(new int[]{2,1,3,0});

        //Arrays.stream(arrayRankTransformation(new int[]{40, 30, 20, 10})).forEach(System.out::println);


       // System.out.println("abcdef".compareTo("hello1"));

        //Arrays.stream(frequencySort(new int[]{1,1,2,2,2,3})).sorted().forEach(System.out::println);

        //frequencySort(new int[]{1,1,2,2,2,3});

        //construct2DArray(new int[]{1,2,3}, 1,3);

        //construct2DArrayV2(new int[]{1,2,3,4,5,6}, 2,3);

        //applyOperations(new int[]{1,2,2,1,1,0});

        //findTheArrayConcVal(new int[]{12,13,54});

        //System.out.println((10 << 3) | (10 << 2));

        // 0001010
        //      2 * 16 + 8 * 16 = 32 + 128 = 80
        //

        //mergeArrays(new int[][]{{1,2},{2,3},{4,5}}, new int[][]{{1,4},{3,2},{4,1}});

        //System.out.println(arrangeCoinsV2(5));

        //System.out.println(1 << 15);

        //arrangeCoinsV3(45);

        //System.out.println(Integer.compare(2,3));

        //System.out.println(countHillValley(new int[]{21,21,21,2,2,2,2,21,21,45}));

        //minimumOperations(new int[]{1,5,0,3,5});

       //minimumRightShifts(List.of(1,44,55,15,56,36,21,47,85,68));
        isPrefixString("a", new String[]{"aa","aaaa","banana"});

        isPrefixStringV2("iloveyou", new String[]{});

    }




}
