package excercise.arrays;

import java.util.*;

public class ExcerciseArrayList {



    static class ListNode {
        int val;
        ListNode next;
        ListNode(int val) { this.val = val;}
    }
    static class MyLinkedList {
        int size;
        ListNode head;

        public MyLinkedList() {
            size = 0;
            head = new ListNode(0);
        }

        public int get(int index) {
            if (index < 0 || index >= size) return -1;
            ListNode curr = head;
            for (int i = 0; i <= index; i++) curr = curr.next;
            return curr.val;
        }

        public void addAtHead(int val) {
            addAtIndex(0, val);
        }

        public void addAtTail(int val) {
            addAtIndex(size, val);
        }

        public void addAtIndex(int index, int val) {
            if (index > size) return;

            if (index < 0) index = 0;

            ++size;

            ListNode pred = head;
            for (int i = 0; i < index; i++) pred = pred.next;

            ListNode toAdd = new ListNode(val);
            toAdd.next = pred.next;
            pred.next = toAdd;
        }

        public void deleteAtIndex(int index) {
            if (index >= size || index < 0) return;
            size--;
            ListNode curr = head;
            for (int i = 0; i < index; i++) curr = curr.next;
            curr.next = curr.next.next;
        }

    }

    static class MyLinkedListTwo {
        int size;
        ListNode head;

        public MyLinkedListTwo() {
            size = 0;
            head = new ListNode(0);
        }

        public int get(int index) {
            if (index < 0 || index >= size ) return -1;
            ListNode curr = head;
            for (int i = 0; i <= index; i++) curr = curr.next;
            return curr.val;
        }

        public void addAtHead(int val) {
            addAtIndex(0, val);
        }

        public void addAtTail(int val) {
            addAtIndex(size, val);
        }

        public void addAtIndex(int index, int val) {
            if (index > size) return;

            if (index < size) index = 0;

            ++size;

            ListNode pred = head;
            for (int i = 0; i < index; i++) pred = pred.next;

            ListNode toAdd = new ListNode(val);
            toAdd.next = pred.next;
            pred.next = toAdd;
        }

        public void deleteAtIndex(int index) {
            if (index >= size || index < 0) return;

            size--;
            ListNode currentNode = head;
            for (int i = 0; i < index; i++) currentNode = currentNode.next;
            currentNode.next = currentNode.next.next;
        }
    }

    static class DoublyLinkedList {
        int size;
        DoublyListNode head;

        public DoublyLinkedList() {
            this.size = 0;
            this.head = new DoublyListNode(0);
        }

        public int get(int index) {
            if (index < 0 || index >= size) return -1;
            DoublyListNode curr = head;
            for (int i = 0; i <= index; ++i) curr = curr.next;
            return curr.val;
        }

        public void addAtHead(int val) {
            addAtIndex(0, val);
        }

        public void addAtTail(int val) {
            addAtIndex(size, val);
        }

        public void deleteAtIndex(int index) {
            if (index > size || index < 0) return;
            size--;

            DoublyListNode prev = head;
            for (int i = 0; i < index; i++) prev = prev.next;
            DoublyListNode curr = prev.next;

            prev.next = curr.next;

            if (curr.next != null)  curr.next.prev = prev;
            --size;
        }

        public void addAtIndex(int index, int val) {
            if (index > size) return;
            if (index < size) index = 0;

            DoublyListNode prev = head;
            for (int i = 0; i < index; i++) prev = prev.next;
            DoublyListNode next = prev.next;
            DoublyListNode newNode = new DoublyListNode(val);
            newNode.prev = prev;
            newNode.next = next;

            prev.next = newNode;

            if (next != null) next.prev = newNode;

            ++size;
        }


    }

    static boolean hasCycle(ListNode listNode) {

        ListNode slowPointer = listNode;
        ListNode fastPointer = listNode;

        do {
            slowPointer = slowPointer.next;
            fastPointer = fastPointer.next;

            if (fastPointer != null) {
                fastPointer = fastPointer.next;
            }

            if (slowPointer == fastPointer) return true;

        } while (fastPointer != null);

        return false;
    }



    static boolean hasCycleV2(ListNode head) {
        ListNode slowRunner = head;
        ListNode fastRunner = head.next;

        while (fastRunner != null) {
            fastRunner = fastRunner.next == null ? null : fastRunner.next.next;
            slowRunner = slowRunner.next;
            if (fastRunner == slowRunner) {
                return true;
            }
        }

        return false;
    }

    static ListNode detectCycle(ListNode head) {
        if (head == null || head.next == null) return null;

        ListNode intersection = getIntersection(head);
        if (intersection == null) return null;

        ListNode ptr1 = head;
        ListNode ptr2 = intersection;

        while (ptr1 != ptr2) {
            ptr1 = ptr1.next;
            ptr2 = ptr2.next;
        }

        return ptr1;
    }

    static ListNode getIntersection(ListNode head) {
        ListNode slow = head;
        ListNode fast = head;

        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
            if (slow == fast) return slow;
        }
        return null;
    }

    static ListNode getIntersectionNode(ListNode headA, ListNode headB) {

        if (headA == null || headB == null) return null;

        ListNode pA = headA;
        ListNode pB = headB;

        while (pA != pB) {
            pA = (pA != null) ? pA.next : headB;
            pB = (pB != null) ? pB.next : headA;
        }

        return pA;
    }

    static ListNode removeNthFromEnd(ListNode head, int n) {

        ListNode dummy = new ListNode(0);
        dummy.next = head;
        ListNode first = dummy;
        ListNode second = dummy;
        for (int i = 1; i <= n + 1; i++) {
            first = first.next;
        }

        while (first != null) {
            first = first.next;
            second = second.next;
        }
        second = second.next.next;
        return dummy.next;
    }

    static ListNode reverseLinkedList(ListNode head) {
        ListNode dummy = new ListNode(0);
        dummy.next = head;
        ListNode curr = head;
        ListNode oldHead = head;

        while (curr != null && curr.next != null) {
            ListNode newHead = curr.next;
            curr.next = curr.next.next;
            newHead.next = oldHead;
            dummy.next = newHead;
            oldHead = newHead;
        }

        return dummy.next;
    }

    static ListNode reverseLinkedListV2(ListNode head) {
        ListNode prev = null;
        ListNode forward = null;
        ListNode current = head;
        while (current != null) {
            forward = current.next;
            current.next = prev;
            prev = current;
            current = forward;
        }

        return prev;
    }

    static ListNode removeElements(ListNode head, int val) {
        ListNode dummy = new ListNode(0);
        dummy.next = head;

        ListNode current = head;
        ListNode prev = dummy;

        while (current != null) {
            if (current.val == val) {
                prev.next = current.next;
            } else {
                prev = current;
            }
            current = current.next;
        }

        return dummy.next;
    }

    // recursive
    static ListNode removeElementsV2(ListNode head, int val) {
        if (head == null) return null;
        head.next = removeElementsV2(head.next, val);

        if (head.val == val) {
            return head.next;
        }
        return head;
    }

    static ListNode oddEvenList(ListNode head) {

        if (head == null) return null;

        ListNode evenList = head.next;

        ListNode odd = head;
        ListNode even = evenList;

        while (odd.next != null && even.next != null) {
            odd.next = even.next;
            odd = even.next;

            even.next = odd.next != null ? odd.next : null;
            even = even.next;
        }

        odd.next = evenList;
        return head;

    }


    static boolean isPalindromeLinkedList(ListNode head) {
        //if (head == null || head.next == null) return false;

        List<Integer> vals = new ArrayList<>();
        ListNode current = head;
        while (current != null) {
            vals.add(current.val);
            current = current.next;;
        }

        for (int i = 0, j = vals.size() - 1; i < j; i++,j--) {
            if (!vals.get(i).equals(vals.get(j))) {
                return false;
            }
        }

        return true;

    }

    static boolean isPalindromeLinkedListV2(ListNode head) {
        ListNode prev = null;
        ListNode slow = head;
        ListNode fast = head;

        while (true) {
            if (fast == null) break; // even

            if (fast.next == null) { // odd
                slow = slow.next;
                break;
            }

            fast = fast.next.next;
            ListNode temp = slow.next;
            slow.next = prev;
            prev = slow;
            slow = temp;
        }

        while (slow != null) {
            if (prev.val != slow.val) return false;
            prev = prev.next;
            slow = slow.next;
        }

        return true;
    }

    static class DoublyListNode {
        int val;
        DoublyListNode next, prev;
        DoublyListNode(int x) { this.val = x;}
    }

    static ListNode mergeTwoLists(ListNode list1, ListNode list2) {

        if (list1 == null) return list2;
        if (list2 == null) return list1;

        ListNode node1 = list1;
        ListNode node2 = list2;

        ListNode dummy = new ListNode(200);
        ListNode firstNode = dummy;

        while (node1 != node2) {

            while (node1 != null && node1.val < node2.val) {
                firstNode.next = node1;
                node1 = node1.next;
                firstNode = firstNode.next;
            }

            node1 = node1 != null ? node1 : dummy;

            if (node2 == dummy) break;

            while (node2 != null && node2.val <= node1.val) {
                firstNode.next = node2;
                node2 = node2.next;
                firstNode = firstNode.next;
            }

            node2 = node2 != null ? node2 : dummy;
        }

        return dummy.next;
    }

    static ListNode mergeTwoListsV2(ListNode list1, ListNode list2) {
        ListNode mergedListHead = new ListNode(0);
        ListNode ptr1 = list1, ptr2 = list2;
        ListNode ptr = mergedListHead;

        while (ptr1 != null && ptr2 != null) {

            if (ptr1.val <= ptr2.val) {
                ptr.next = ptr1;
                ptr = ptr.next;
                ptr1 = ptr1.next;
            } else {
                ptr.next = ptr2;
                ptr = ptr.next;
                ptr2 = ptr2.next;
            }
        }

        if (ptr1 != null) {
            ptr.next = ptr1;
        }

        if (ptr2 != null) {
            ptr.next = ptr2;
        }

        return mergedListHead.next;
    }

    static ListNode addTwoNumbers(ListNode l1, ListNode l2) {

        int carry = 0;
        ListNode listSum = new ListNode(0);
        ListNode lSumNode = listSum;

        ListNode n1 = l1;
        ListNode n2 = l2;

        ListNode zeroNode = new ListNode(0);

        while (n1 != null || n2 != null) {

            ListNode n1N = n1 == null ? zeroNode : n1;
            ListNode n2N = n2 == null ? zeroNode : n2;

            int currSum = n1N.val + n2N.val + carry;
            ListNode sum = new ListNode(currSum % 10);
            carry = currSum >= 10 ? 1 : 0;

            lSumNode.next = sum;
            lSumNode = lSumNode.next;

            if (n1 != null) n1 = n1.next;
            if (n2 != null) n2 = n2.next;
        }

        if (carry > 0) {
            lSumNode.next = new ListNode(1);
        }

        return listSum.next;
    }


    static class DN {
        public int val;
        public DN prev;
        public DN next;
        public DN child;
    }


    static DN next(DN node) {
        if (node.next == null && node.child == null) return node;
        DN temp = node.next; // 4 8

        if (node.child != null) {
            node.next = node.child;
            DN child = next(node.child); // 7  11
            node.child = null;
            child.next = temp;
            temp.prev = child;
        }

        if (temp != null) {
            next(node.next);
        }

        return node;
    }


    public static DN flatten(DN head) {
        if (head == null) return head;

        DN p = head;

        while (p != null) {
            if (p.child == null) {
                p = p.next;
            } else {
                DN temp = p.child;
                while (temp.next != null) {
                    temp = temp.next;
                }

                temp.next = p.next;
                if (p.next != null) p.next.prev = temp;
                p.next = p.child;
                p.child.prev = p;
                p.child = null;
            }
        }

        return head;
    }

    static DN flattenRec(DN head) {
        DN p = head;
        while (p != null) {
            if (p.child != null) {
                DN right = p.next;

                p.next = flattenRec(p.child);
                p.next.prev = p;
                p.child = null;

                while (p.next != null) {
                    p = p.next;
                }

                if (right != null){
                    p.next = right;
                    p.next.prev = p;
                }
            }
            p = p.next;
        }
        return head;
    }

    static class NR {
        int val;
        NR next;
        NR random;

        public NR(int val) {
            this.val = val;
            this.next = null;
            this.random = null;
        }
    }

    static NR copyRandomList(NR head) {
        if (head == null) return head;

        Map<NR, NR> map = new HashMap<>();

        NR node = head;
        while (node != null) {
            map.put(node, new NR(node.val));
            node = node.next;
        }

        node = head;

        while (node != null) {
            map.get(node).next = map.get(node.next);
            map.get(node).random = map.get(node.random);
            node = node.next;
        }
        return map.get(head);
    }

    static NR copyRandomListV2(NR head) {
        NR iter = head, next;

        while (iter != null) {
            next = iter.next;
            NR copy = new NR(iter.val);
            iter.next = copy;
            copy.next = next;
            iter = next;
        }

        iter = head;
        while (iter != null) {
            if (iter.random != null) {
                iter.next.random = iter.random.next;
            }
            iter = iter.next.next;
        }

        iter = head;
        NR pseudoHead = new NR(0);
        NR copy, copyIter = pseudoHead;

        while (iter != null) {
            next = iter.next.next;

            copy = iter.next;
            copyIter.next = copy;
            copyIter = copy;

            iter.next = next;
            iter = next;
        }

        return pseudoHead.next;
    }

    static ListNode rotateRight(ListNode head, int k) {

        ListNode curr = head;

        int len = 0;

        ListNode tail = head;
        while (curr != null) {
            curr = curr.next;
            tail = curr != null ? curr : tail;
            len++;

        }

        if (len == 0) return head;

        int iter = len - ((k + len) % len);

        if (iter == len) return head;

        curr = head;

        ListNode prev = new ListNode(0);
        prev.next = head;

        for (int i = 0; i < iter; i++) {
            curr = curr.next;
            prev = prev.next;
        }

        if (curr != null) {
            tail.next = head;
            prev.next = null;
            head = curr;
        }

        return head;
    }

    static ListNode reverseBetween(ListNode head, int left, int right) {
        ListNode fakeHead = new ListNode(0);
        fakeHead.next = head;
        ListNode prev = fakeHead;
        ListNode curr = fakeHead.next;

        int i = 1;
        while (i < left) {
            prev = curr;
            curr = curr.next;
            i++;
        }

        ListNode node = prev;
        while (i <= right) {
            ListNode tmp = curr.next;
            curr.next = prev;
            prev = curr;
            curr = tmp;
            i++;
        }

        node.next.next = curr;
        node.next = prev;
        return fakeHead.next;
    }

    static ListNode deleteDuplicates(ListNode head) {
        ListNode dummy = new ListNode(-200);
        dummy.next = head;
        ListNode curr = dummy.next;
        ListNode prev = dummy;

        // 0 - > 1 -> 2 -> 3 -> 3 -> 4 -> 4 -> 5
       while (curr != null) {
           while (curr.next != null && curr.val == curr.next.val) {
               curr = curr.next;
           }

           if (prev.next == curr) {
               prev = prev.next;
           } else {
               prev.next = curr.next;
           }
           curr = curr.next;
       }
        return dummy.next;
    }


    static class LRUCache {
        private LinkedHashMap<Integer, Integer> map;
        private final int CAPACITY;
        public LRUCache(int capacity) {
            CAPACITY =capacity;
            map = new LinkedHashMap<Integer, Integer>(capacity, 0.75f, true) {
                @Override
                protected boolean removeEldestEntry(Map.Entry<Integer, Integer> eldest) {
                    return size() > CAPACITY;
                }
            };
        }

        public int get(int key) {
            return map.getOrDefault(key, -1);
        }

        public void set(int key, int value) {
            map.put(key, value);
        }
    }

    static class LRuCache {
        private class Node {
            int key, val;
            Node prev, next;
            Node(int k, int v) {
                this.key = k;
                this.val = v;
            }
            Node() {
                this(0,0);
            }
        }

        private int capacity, count;
        private Map<Integer, Node> map;
        private Node head, tail;

        public LRuCache(int capacity) {
            this.capacity = capacity;
            this.count = 0;
            map = new HashMap<>();
            head = new Node();
            tail = new Node();
            head.next = tail;
            tail.prev = head;
        }

        public int get(int key) {
            Node n = map.get(key);
            if (null == n)  return -1;
            update(n);
            return n.val;
        }

        public void set(int key, int val) {
            Node n = map.get(key);
            if (null == n) {
                n = new Node(key, val);
                map.put(key, n);
                add(n);
                ++count;
            } else {
                n.val = val;
                update(n);
            }

            if (count > capacity) {
                Node toDel = tail.prev;
                remove(toDel);
                map.remove(toDel.key);
                --count;
            }
        }

        private void update(Node node) {
            remove(node);
            add(node);
        }
        private void add(Node node) {
            Node after = head.next;
            head.next = node;
            node.prev = head;
            node.next = after;
            after.prev = node;
        }
        private void remove(Node node) {
            Node before = node.prev;
            Node after = node.next;
            before.next = after;
            after.prev=  before;
        }
    }

    public static void main(String[] args) {
        /*MyLinkedList linkedList = new MyLinkedList();

        linkedList.addAtHead(2);
        linkedList.addAtHead(1);
        linkedList.addAtIndex(2, 3);
        linkedList.addAtIndex(1,4);
        linkedList.addAtTail(5);

        linkedList.get(1);

        linkedList.deleteAtIndex(2);
        linkedList.deleteAtIndex(2);*/

        /*ListNode listNode = new ListNode(1);
        ListNode listNode1 = new ListNode(2);
        ListNode listNode2 = new ListNode(0);
        ListNode listNode3 = new ListNode(-4);

        listNode.next = listNode1;
        listNode1.next = listNode2;
        listNode2.next = listNode3;
        listNode3.next = listNode1;*/

        //hasCycle(listNode);
        //detectCycle(listNode);
        // 1->4->2->3->5->null


        /*ListNode headOne = new ListNode(1);
        ListNode listNode1 = new ListNode(9);
        ListNode listNode2 = new ListNode(1);

        headOne.next = listNode1;
        listNode1.next = listNode2;

        ListNode headTwo = new ListNode(3);

        ListNode intersectOne = new ListNode(2);
        ListNode intersectTwo = new ListNode(4);

        listNode2.next = intersectOne;
        headTwo.next = intersectOne;

        intersectOne.next = intersectTwo;*/


        //getIntersectionNode(headOne, headTwo);

        /*ListNode l1 = new ListNode(2);
        ListNode l2 = new ListNode(3);
        ListNode l3 = new ListNode(1);
        ListNode l4 = new ListNode(4);
        ListNode l5 = new ListNode(6);
        ListNode l6 = new ListNode(5);
        ListNode l7 = new ListNode(1);
        ListNode l8 = new ListNode(8);
        ListNode l9 = new ListNode(9);

        l1.next = l2;
        l2.next = l3;
        l3.next = l4;
        l4.next = l5;
        l5.next = l6;
        l6.next = l7;
        l7.next = l8;
        l8.next = l9;

       // removeNthFromEnd(l1, 6);

        ListNode l10 = new ListNode(1);*/
        //removeNthFromEnd(l10, 1);



        /*ListNode head = new ListNode(23);
        ListNode l2 = new ListNode(6);
        ListNode l3 = new ListNode(15);

        head.next = l2;
        l2.next = l3;
        //reverseLinkedList(head);
        reverseLinkedListV2(head);*/

        /*ListNode head = new ListNode(1);
        ListNode n1 = new ListNode(2);
        ListNode n2 = new ListNode(6);
        ListNode n3 = new ListNode(3);
        ListNode n4 = new ListNode(4);
        ListNode n5 = new ListNode(5);
        ListNode n6 = new ListNode(6);

        head.next = n1;
        n1.next = n2;
        n2.next = n3;
        n3.next = n4;
        n4.next = n5;
        n5.next = n6;

        removeElements(head, 6);*/


       /* ListNode head = new ListNode(1);
        ListNode n1 = new ListNode(2);
        ListNode n2 = new ListNode(3);
        ListNode n3 = new ListNode(4);
        ListNode n4 = new ListNode(5);

        head.next = n1;
        n1.next = n2;
        n2.next = n3;
        n3.next = n4;

        oddEvenList(head);*/


       /* DoublyLinkedList list = new DoublyLinkedList();

        list.addAtHead(3);
        list.addAtHead(4);

        list.get(1);
        list.get(0);

        list.deleteAtIndex(1);
        list.deleteAtIndex(0);*/

       /* ListNode head1 = new ListNode(1);
        ListNode n1 = new ListNode(2);
        ListNode n2 = new ListNode(4);

        head1.next = n1;
        n1.next = n2;

        ListNode head2 = new ListNode(1);
        ListNode n3 = new ListNode(3);
        ListNode n4 = new ListNode(4);

        head2.next = n3;
        n3.next = n4;*/

        //mergeTwoLists(head1, head2);

        /*ListNode head1 = new ListNode(2);
        ListNode n1 = new ListNode(4);
        ListNode n2 = new ListNode(3);

        head1.next = n1;
        n1.next = n2;

        ListNode head2 = new ListNode(5);
        ListNode n3 = new ListNode(6);
        ListNode n4 = new ListNode(4);

        head2.next = n3;
        n3.next = n4;

        addTwoNumbers(head1, head2);*/


        /*DN n1 = new DN();
        n1.val = 1;

        DN n2 = new DN();
        n2.val = 2;

        DN n3 = new DN();
        n3.val = 3;

        DN n4 = new DN();
        n4.val = 4;

        DN n5 = new DN();
        n5.val = 5;

        DN n6 = new DN();
        n6.val = 6;

        n1.next = n2;

        n2.prev = n1;
        n2.next = n3;

        n3.prev = n2;
        n3.next = n4;

        n4.prev = n3;
        n4.next = n5;

        n5.prev = n4;
        n5.next = n6;


        DN n7 = new DN();
        n7.val = 7;

        DN n8 = new DN();
        n8.val = 8;

        DN n9 = new DN();
        n9.val = 9;

        DN n10 = new DN();
        n10.val = 10;

        n7.next = n8;

        n8.prev = n7;
        n8.next = n9;

        n9.prev = n8;
        n9.next = n10;

        n10.prev = n9;


        DN n11 = new DN();
        n11.val = 11;

        DN n12 = new DN();
        n12.val = 12;

        n11.next = n12;
        n12.prev = n11;


        n3.child = n7;
        n8.child = n11;

        flatten(n1);*/

        /*NR head = new NR(7);

        NR n2 = new NR(13);
        NR n3 = new NR(11);
        NR n4 = new NR(10);
        NR n5 = new NR(1);

        head.next = n2;
        n2.next = n3;
        n3.next = n4;
        n4.next = n5;

        head.random = null;
        n2.random = head;
        n3.random = n5;
        n4.random = n3;
        n5.random = head;

        copyRandomList(head);*/

        /*ListNode head = new ListNode(0);
        ListNode n2 = new ListNode(1);
        ListNode n3 = new ListNode(2);
        *//*ListNode n3 = new ListNode(3);
        ListNode n4 = new ListNode(4);
        ListNode n5 = new ListNode(5);
        *//*

        head.next = n2;
        n2.next = n3;

        *//*
        n3.next = n4;
        n4.next = n5;*//*
       *//* ListNode n4 = new ListNode(4);
        ListNode n5 = new ListNode(5);*//*

       // head.next = n2;

        rotateRight(head, 4);*/


        ListNode head = new ListNode(0);
        ListNode n2   = new ListNode(1);
       /* ListNode n4   = new ListNode( 4);
        ListNode n5   = new ListNode(5);*/

        head.next = n2;
        /*n3.next = n4;
        n4.next = n5;*/

        /*ListNode head = new ListNode(3);
        head.next = new ListNode(5);*/

        reverseBetween(head,1,2);


    }
}
