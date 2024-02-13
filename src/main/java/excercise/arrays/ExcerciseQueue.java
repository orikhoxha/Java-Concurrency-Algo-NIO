package excercise.arrays;

import chapter7.beyondclasses.nestedclasses.A;

import java.util.*;
import java.util.function.IntConsumer;

public class ExcerciseQueue {

    static class MyQueue {
        private final List<Integer>  data;
        private int pStart;

        public MyQueue() {
            data = new ArrayList<>();
            pStart = 0;
        }

        public boolean enqueue(int x) {
            data.add(x);
            return true;
        }

        public boolean dequeue() {
            if (isEmpty()) {
                return false;
            }
            pStart++;
            return true;
        }

        public int front() {
            return data.get(pStart);
        }
        public boolean isEmpty() {
            return pStart >= data.size();
        }

    }


    static class MyCircularQueue {
        final int[] a;
        int front;
        int rear = -1;
        int len = 0;

        public MyCircularQueue(int k) { a = new int[k];}

        public boolean enqueue(int val) {
            if (!isFull()) {
                rear = (rear + 1) % a.length;
                a[rear] = val;
                len++;
                return true;
            } else return false;
        }

         public boolean deQueue() {
            if (!isEmpty()) {
                front = (front + 1) % a.length;
                len--;
                return true;
            } else return false;
         }

        public int Rear() {
            return isEmpty() ? -1 : a[rear];
        }

        public int Front() { return  isEmpty() ? -1 : a[front];}

        public boolean isEmpty() {
            return len == 0;
        }
        public boolean isFull() {
            return len == a.length;
        }
    }

    static class MyCircularQueueTwo {
        private int[] data;
        private int head;
        private int tail;
        private int size;

        public MyCircularQueueTwo(int k) {
            data = new int[k];
            head = -1;
            tail = -1;
            size = k;
        }

        public boolean enqueue(int value) {
            if (isFull()) return false;
            if (isEmpty()) {
                head = 0;
                return true;
            }

            tail = (tail + 1) % size;
            data[tail] = value;
            return true;
        }

        public boolean dequeue() {
            if (isEmpty()) return false;
            if (head == tail) {
                head = -1;
                tail = -1;
                return true;
            }

            head = (head + 1) % size;
            return true;
        }

        public int Front() {
            if (isEmpty()) {
                return -1;
            }
            return data[head];
        }

        public int Rear() {
            if (isEmpty()) {
                return -1;
            }
            return data[tail];
        }

        public boolean isEmpty() {
            return head == -1;
        }
        public boolean isFull() {
            return ((tail + 1) % size) == head;
        }

    }

    static int numIslands(char[][] grid) {
        int count = 0;
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                if (grid[i][j] == '1') {
                    bfsFill(grid, i, j);
                    count++;
                }
            }
        }
        return count;
    }

    private static void bfsFill(char[][] grid, int x, int y) {
        grid[x][y] = '0';
        int n = grid.length;
        int m = grid[0].length;
        LinkedList<Integer> queue = new LinkedList<>();
        int code = x * m + y;
        queue.offer(code);
        while (!queue.isEmpty()) {
            code = queue.poll();
            int i = code / m;
            int j = code % m;
            if (i > 0 && grid[i - 1][j] == '1') {
                queue.offer((i - 1) * m + j);
                grid[i - 1][j] = '0';
            }
            if (i < n - 1 && grid[i + 1][j] == '1') {
                queue.offer((i + 1) * m + j);
                grid[i + 1][j] = '0';
            }

            if (j > 0 && grid[i][j - 1] == '1') {
                queue.offer(i * m + j - 1);
                grid[i][j - 1] = '0';
            }

            if (j < m - 1 && grid[i][j + 1] == '1') {
                queue.offer(i * m + j + 1);
                grid[i][j + 1] = '0';
            }
        }
    }

    static class Pair {
        int first, second;
        public Pair(int first, int second) {
            this.first = first;
            this.second = second;
        }
    }

    public static int openLock(String[] deadends, String target) {
        Set<String> visited = new HashSet<>(Arrays.asList(deadends));
        int depth = -1;
        Queue<String> q = new LinkedList<>(List.of("0000"));
        while (!q.isEmpty()) {
            depth++;
            int size = q.size();
            for (int i = 0; i < size; i++) {
                String node = q.poll();
                if (node.equals(target)) return depth;
                if (visited.contains(node)) continue;
                visited.add(node);
                q.addAll(getSuccessors(node));
            }
        }

        return -1;
    }

    private static List<String> getSuccessors(String str) {
        List<String> res = new LinkedList<>();
        for (int i = 0; i < str.length(); i++) {
            res.add(str.substring(0, i) + (str.charAt(i) == '0' ? 9 : str.charAt(i) - '0' - 1) + str.substring(i + 1));
            res.add(str.substring(0, i) + (str.charAt(i) == '9' ? 0 : str.charAt(i) - '0' + 1) + str.substring(i + 1));
        }
        return res;
    }

    static class MyStack {
        private List<Integer> data;

        public MyStack() {
            data = new ArrayList<>();
        }

        public void push(int x) {
            data.add(x);
        }

        public boolean isEmpty() {
            return data.isEmpty();
        }

        public int top() {
            return data.get(data.size() - 1);
        }

        public boolean pop() {
            if (isEmpty()) return false;

            data.remove(data.size() - 1);
            return true;
        }


    }

    static class MinStack {

        private Node head;

        public void push(int x) {
            if (head == null) {
                head = new Node(x, x, null);
            } else {
                head = new Node(x, Math.min(x, head.min), head);
            }
        }

        public void pop() {
            head = head.next;
        }

        public int top() {
            return head.val;
        }

        public int getMin() {
            return head.min;
        }

        private static class Node {
            int val;
            int min;
            Node next;

            private Node(int val, int min, Node next) {
                this.val = val;
                this.min = min;
                this.next = next;
            }
        }
    }


    // "{[{]]}"
    //  }]}
    static boolean isValid(String s) {
        Stack<Character> stack = new Stack<>();
        for (char c : s.toCharArray()) {
            if (c == '(') stack.push(')');
            else if (c == '{') stack.push('}');
            else if (c == '[') stack.push(']');
            else if (stack.isEmpty() || stack.pop() != c) return false;
        }
        return stack.isEmpty();
    }

    static boolean isValidV2(String s) {
        char[] chars = new char[s.length()];
        int pointer = -1;
        for (char c : s.toCharArray()) {

            // "{[{]]}" 2
            //  {,[,{,
            if (c == '[' || c == '{' || c == '(') {
                pointer++;
                chars[pointer] = c;
            } else {
                if (pointer < 0) return false;
                else {
                    if ((chars[pointer] == '[' && c == ']') || (chars[pointer] == '(' && c == ')') || (chars[pointer] == '{' && c == '}')) {
                        pointer--;
                    } else {
                        return false;
                    }
                }
            }
        }

        return pointer < 0;
    }

    static int[] dailyTemperatures(int[] temperatures) {
        Stack<Integer> stack = new Stack<>();

        int[] ret = new int[temperatures.length];
        for (int i = 0; i < temperatures.length; i++) {
            while (!stack.isEmpty() && temperatures[i] > temperatures[stack.peek()]) {
                int idx = stack.pop();
                ret[idx] = i - idx;
            }
            stack.push(i);
        }
        return ret;
    }

    static int[] dailyTemperaturesV2(int[] temperatures) {
        int n = temperatures.length;
        int hottest = 0;
        int[] answer = new int[n];

        for (int currDay = n - 1; currDay >= 0; currDay--) {
            int currentTemp = temperatures[currDay];
            if (currentTemp >= hottest) {
                hottest = currentTemp;
                continue;
            }

            int days = 1;
            while (temperatures[currDay + days] <= currentTemp) {
                days += answer[currDay + days];
            }
            answer[currDay] = days;
        }
        return answer;
    }

    static int evalRPN(String[] tokens) {
        int a;
        int b;

        Stack<Integer> S = new Stack<>();

        for(String s : tokens) {
            switch (s) {
                case "+" -> S.add(S.pop() + S.pop());
                case "/" -> {
                    b = S.pop();
                    a = S.pop();
                    S.add(a / b);
                }
                case "*" -> S.add(S.pop() * S.pop());
                case "-" -> {
                    b = S.pop();
                    a = S.pop();
                    S.add(a - b);
                }
                default -> S.add(Integer.parseInt(s));
            }
        }

        return S.pop();
    }

    static String decodeString(String s) {
        String res = "";
        Stack<Integer> countStack = new Stack<>();
        Stack<String> resStack = new Stack<>();
        int idx = 0;
        while (idx < s.length()) {
            if (Character.isDigit(s.charAt(idx))) {
                int count = 0;
                while (Character.isDigit(s.charAt(idx))) {
                    count = 10 * count + (s.charAt(idx) - '0');
                    idx++;
                }
                countStack.push(count);
            } else if (s.charAt(idx) == '[') {
                resStack.push(res);
                res = "";
                idx++;
            } else if (s.charAt(idx) == ']') {
                StringBuilder temp = new StringBuilder(resStack.pop());
                int repeatTimes = countStack.pop();
                for (int i = 0; i < repeatTimes; i++) {
                    temp.append(res);
                }
                res = temp.toString();
                idx++;
            } else {
                res += s.charAt(idx++);
            }
        }
        return res;
    }

    static int[][] floodFill(int[][] image, int sr, int sc, int newColor) {
        int[] DIRS = {0, 1, 0, -1, 0};

        if (image[sr][sc] == newColor) return image;

        int m = image.length;
        int n = image[0].length;

        Queue<int[]> q = new LinkedList<>();
        q.offer(new int[]{sr, sc});
        int oldColor = image[sr][sc];
        image[sr][sc] = newColor;
        while (!q.isEmpty()) {
            int[] top = q.poll();
            for (int i = 0; i < 4; i++) {

            }
        }

        return image;
    }

    static String simplifyPath(String path) {
        Stack<String> s = new Stack<>();
        StringBuilder res = new StringBuilder();
        String[] p = path.split("/");
        for (String value : p) {
            if (!s.isEmpty() && value.equals("..")) s.pop();
            else if (!value.equals("") && !value.equals(".") && !value.equals("..")) s.push(value);
        }

        if (s.isEmpty()) return "/";
        while (!s.isEmpty()) {
            res.insert(0, s.pop()).insert(0,"/");
        }
        return res.toString();
    }

    static int calculate(String s) {
        Stack<Integer> stack = new Stack<>();

        int result = 0;
        int number = 0;
        int sign = 1;

        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (Character.isDigit(c)) {
                number = 10 * number + (c - '0');
            } else if (c == '+') {
                result += sign * number;
                number = 0;
                sign = 1;
            } else if (c == '-') {
                result += sign * number;
                number = 0;
                sign = -1;
            } else if (c == '(') {
                stack.push(result);
                stack.push(sign);
                sign = 1;
                result = 0;
            } else if (c == ')') {
                result += sign * number;
                number = 0;
                result *= stack.pop();
                result += stack.pop();
            }
        }

        if (number != 0) result += sign * number;
        return result;
    }

    static class FreqStack {
        Map<Integer, Integer> freq = new HashMap<>();
        Map<Integer, Stack<Integer>> m = new HashMap<>();
        int maxFreq = 0;

        public void push(int x) {
            int f = freq.getOrDefault(x, 0) + 1;
            freq.put(x, f);
            maxFreq = Math.max(maxFreq, f);
            if (!m.containsKey(f)) m.put(f, new Stack<>());
            m.get(f).add(x);
        }

        public int pop() {
            int x = m.get(maxFreq).pop();
            freq.put(x, maxFreq - 1);
            if (m.get(maxFreq).size() == 0) maxFreq--;
            return x;
        }
    }

    static class CustomStack {
        int[] stack;
        int size;
        int index;

        public CustomStack(int maxSize) {
            stack = new int[maxSize];
            size = maxSize;
            index = 0;
        }

        public void push(int x) {
            if (index < size) {
                stack[index++] = x;
            }
        }

        public int pop() {
            if (index == 0) return -1;
            return stack[--index];
        }

        public void increment(int k, int val) {
            if (stack.length == 0) return;
            for (int i = 0; i < k; i++) {
                stack[i] += val;
            }
        }
    }

    static class FizzBuzz {
        private final int n;
        private int currentNumber = 1;

        public FizzBuzz(int n) {
            this.n = n;
        }

        public synchronized void fizz(Runnable printFizz) throws InterruptedException {
            while (currentNumber <= n) {
                if (currentNumber % 3 != 0 || currentNumber % 5 == 0) {
                    wait();
                    continue;
                }
                printFizz.run();
                currentNumber += 1;
                notifyAll();
            }
        }

        public synchronized void buzz(Runnable printBuzz) throws InterruptedException {
            while (currentNumber <= n) {
                if (currentNumber % 5 != 0 || currentNumber % 3 == 0) {
                    wait();
                    continue;
                }
                printBuzz.run();
                currentNumber += 1;
                notifyAll();
            }
        }

        public synchronized void fizzbuzz(Runnable printFizzBuzz) throws InterruptedException {
            while (currentNumber <= n) {
                if (currentNumber % 15 != 0) {
                    wait();
                    continue;
                }
                printFizzBuzz.run();
                currentNumber += 1;
                notifyAll();
            }
        }

        public synchronized void number(IntConsumer printNumber) throws InterruptedException {
            while (currentNumber <= n) {
                if (currentNumber % 3 == 0 || currentNumber % 5 == 0) {
                    wait();
                    continue;
                }
                printNumber.accept(currentNumber);
                currentNumber += 1;
                notifyAll();
            }
        }
    }

    static class FooBar {
        private final int n;
        private int i = 1;

        public FooBar(int n) {
            this.n = n;
        }

        public void foo(Runnable printFoo) throws InterruptedException {

            while (i <= n * 2) {
                synchronized (this) {
                    if (i % 2 == 0) {
                        wait();
                    } else {
                        // printFoo.run() outputs "foo". Do not change or remove this line.
                        printFoo.run();
                        i++;
                        notify();
                    }
                }
            }
        }

        public void bar(Runnable printBar) throws InterruptedException {
            while (i <= n * 2) {
                synchronized (this) {
                    if (i % 2 != 0) {
                        wait();
                    } else {
                        // printFoo.run() outputs "foo". Do not change or remove this line.
                        printBar.run();
                        i++;
                        notify();
                    }
                }
            }
        }
    }

    static class ZeroEvenOdd {
        private final int n;
        private int currNumber;
        private int incBy = 1;

        public ZeroEvenOdd(int n) {
            this.n = n;
        }

        // 010
        public void zero(IntConsumer printNumber) throws InterruptedException {
            while (incBy <= n) {
                synchronized (this) {
                    if (currNumber != 0)  {
                        wait();
                    } else {
                        printNumber.accept(0);
                        currNumber = 1;
                        notifyAll();
                    }
                }
            }
        }

        // 1
        public void odd(IntConsumer printNumber) throws InterruptedException {
            while (incBy  <= n) {
                synchronized (this) {
                    if (currNumber == 0 || incBy % 2 != 1) {
                        wait();
                    } else {
                        printNumber.accept(incBy);
                        currNumber = 0;
                        incBy++;
                        notifyAll();
                    }
                }
            }
        }

        public void even(IntConsumer printNumber) throws InterruptedException {
            while (incBy <= n) {
                synchronized (this) {
                    if (currNumber == 0|| incBy % 2 != 0) {
                        wait();
                    } else {
                        printNumber.accept(incBy);
                        currNumber = 0;
                        incBy++;
                        notifyAll();
                    }
                }
            }
        }
    }

    static int search(int[] nums, int target) {
        int left = 0;
        int right = nums.length - 1;
        while (left <= right) {
            int mid = left + (right - left) / 2;

            if (nums[mid] == target) return mid;
            else if (nums[mid] < target) left = mid + 1;
            else right = mid - 1;
        }

        return -1;
    }

    static int searchUpperBound(int[] nums, int target) {
        int left = 0;
        int right = nums.length - 1;
        while (left < right) {
            int mid = left + (right - left) / 2;
            if (nums[mid] <= target) left = mid + 1;
            else right = mid;
        }

        if (left > 0 && nums[left - 1] == target) {
            return left - 1;
        } else {
            return -1;
        }
    }

    static int sqrt(int x) {
        if (x == 0) return 0;
        int left = 1, right = Integer.MAX_VALUE;
        while (true) {
            int mid = left + (right - left) / 2;
            if (mid > x / mid) right = mid - 1;
            else {
                if (mid + 1 > x / (mid + 1)) return mid;
                left = mid + 1;
            }
        }
    }



    /*static int guessNumber(int n) {
        int lo = 1;
        int hi = n;
        while (lo <= hi) {
            int mid = lo + (hi - lo) / 2;
            //int guess = guess(mid);
            if (guess > 0) {
                lo = mid + 1;
            } else if (guess < 0) {
                hi = mid - 1;
            } else {
                return mid;
            }
        }
        return -1;
    }*/

    //       e
    //     m
    //   5,6,0,1,2,3,4
    //

    static int searchRotatedArr(int[] nums, int target) {
        int start = 0;
        int end = nums.length - 1;
        while (start <= end) {
            int mid = start + (end - start) / 2;
            if (nums[mid] == target) {
                return mid;
            }

            if (nums[start] <= nums[mid]) {
                if (target < nums[mid] && target >= nums[start]){
                    end = mid - 1;
                } else {
                    start = mid + 1;
                }
            }

            if (nums[mid] <= nums[end]) {
                if (target > nums[mid] && target <= nums[end]) {
                    start = mid + 1;
                } else {
                    end = mid - 1;
                }
            }
        }
        return -1;
    }

    //       s e
    // 1,2,3,4,5
    /*static int firstBadVersion(int n) {
        int start = 1, end = n;
        while (start < end) {
            int mid = start + (end - start) / 2;
            if (!isBadVersion(mid)) start = mid + 1;
            else end = mid;
        }

        return start;
    }*/

    static int findMin(int[] num) {
        if (num == null || num.length == 0) return 0;
        if (num.length == 1) return num[0];

        int start = 0;
        int end = num.length - 1;
        while (start < end) {
            int mid = start + (end - start) / 2;
            if (mid > 0 && num[mid] < num[mid - 1]) return num[mid];

            if (num[start] <= num[mid] && num[mid] > num[end]) {
                start = mid + 1;
            } else {
                end = mid - 1;
            }
        }
        return num[start];
    }

    static int[] searchRange(int[] A, int target) {
        int start = firstGreaterEqual(A, target);
        if (start == A.length || A[start] != target) {
            return new int[]{-1, -1};
        }
        return new int[]{start, firstGreaterEqual(A, target + 1) - 1};
    }

    static int firstGreaterEqual(int[] A, int target) {
        int lo = 0;
        int hi = A.length - 1;
        while (lo < hi) {
            int mid = lo + ((hi - lo) >> 1);
            if (A[mid] < target) {
                lo = mid + 1;
            } else {
                hi = mid;
            }
        }
        return lo;
    }


    // 1,2,1,3,5,6,4
    //
    static int findPeakElement(int[] nums) {
        for (int i = 1; i < nums.length; i++) {
            if (nums[i] < nums[i - 1]) {
                return i - 1;
            }
        }
        return nums.length - 1;
    }


    //         l   h
    //           m m1
    // 1,2,1,3,5,6,7
    //
    static int findPeakElementV2(int[] nums) {
        int lo = 0;
        int hi = nums.length - 1;
        while (lo < hi) {
            int mid1 = lo + (hi - lo) / 2;
            int mid2 = mid1 + 1;
            if (nums[mid1] < nums[mid2]) lo = mid2;
            else hi = mid1;
        }
        return lo;
    }

    public static void main(String[] args) {
        /*ueue<Integer> q = new LinkedList<>();

        q.peek();
        q.offer(5);
        q.offer(13);
        q.offer(8);
        q.offer(6);
        q.poll();
*/

        /*char[][] grid = {
                {'1','1','0','0','0'},
                {'1','1','0','0','0'},
                {'0','0','1','0','0'},
                {'0','0','0','1','1'}
        };

        numIslands(grid);*/

        //openLock(new String[]{"0201","0101","0102","1212","2002"}, "0202");

        /*MinStack ms = new MinStack();
        ms.push(3);
        ms.push(4);
        ms.push(2);
        ms.push(1);

        ms.pop();
        ms.pop();
        ms.push(-2);*/


        //dailyTemperaturesV2(new int[]{73,74,75,71,69,72,76,73});

        //decodeString("3[a2[c]]");

        //simplifyPath("/home//foo/");
        //calculate("(1+(4+5+2)-3)+(6+8)");


        /*FreqStack fs = new FreqStack();
        fs.push(5);
        fs.push(7);
        fs.push(5);
        fs.push(7);
        fs.push(4);
        fs.push(5);
        fs.pop();
        fs.pop();
        fs.pop();
        fs.pop();*/
        //sqrt(8);
       // searchRotatedArr(new int []{4,5,6,7,0,1,2}, 0);
        //searchRange(new int[]{7,7,7,8,8,10}, 7);

        findPeakElementV2(new int[]{1,2,1,3,5,6,7});

    }
}
