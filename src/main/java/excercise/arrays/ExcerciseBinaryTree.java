package excercise.arrays;

import chapter7.beyondclasses.nestedclasses.A;

import java.util.*;

public class ExcerciseBinaryTree {

    static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode() {}
        TreeNode(int val) { this.val = val; }
        TreeNode(int val, TreeNode left, TreeNode right) {
            this.val = val;
            this.left = left;
            this.right = right;
        }
    }
    

    static List<Integer> preorderTraversal(TreeNode root) {
        var pre = new LinkedList<Integer>();
        preHelper(root, pre);
        return pre;
    }


    // 1, null,
    static void preHelper(TreeNode root, List<Integer> pre) {
        if (root == null) return;
        pre.add(root.val);
        preHelper(root.left, pre);
        preHelper(root.right, pre);
    }

    static List<Integer> preorderIt(TreeNode root) {
        List<Integer> pre = new LinkedList<>();
        if (root == null) return pre;
        Stack<TreeNode> tovisit = new Stack<>();
        tovisit.push(root);
        while (!tovisit.empty()) {
            TreeNode visiting = tovisit.pop();
            pre.add(visiting.val);
            if (visiting.right != null) tovisit.push(visiting.right);
            if (visiting.left != null) tovisit.push(visiting.left);
        }
        return pre;
    }

    static List<Integer> inorderTraversal(TreeNode root) {
        List<Integer> res = new ArrayList<>();
        helper(root, res);
        return res;
    }

    static void helper(TreeNode root, List<Integer> res) {
        if (root != null) {
            helper(root.left, res);
            res.add(root.val);
            helper(root.right, res);
        }
    }

    static List<Integer> inorderTraversalIter(TreeNode root) {
        List<Integer> res = new ArrayList<>();
        Stack<TreeNode> stack = new Stack<>();
        TreeNode curr = root;
        while (curr != null || !stack.isEmpty()) {
            while (curr != null) {
                stack.push(curr);
                curr = curr.left;
            }
            curr = stack.pop();
            res.add(curr.val);
            curr = curr.right;
        }
        return res;
    }

    static List<Integer> postorderTraversal(TreeNode root) {
        LinkedList<Integer> ans = new LinkedList<>();
        Stack<TreeNode> stack = new Stack<>();
        if (root == null) return ans;

        stack.push(root);
        while (!stack.isEmpty()) {
            TreeNode curr = stack.pop();
            ans.addFirst(curr.val);
            if (curr.left != null) {
                stack.push(curr.left);
            }

            if (curr.right != null) {
                stack.push(curr.right);
            }

        }

        return ans;
    }

    static List<Integer> postorderTraversalRec(TreeNode root) {
        List<Integer> res = new LinkedList<>();
        helperPostOrder(res, root);
       return res;
    }

    static void helperPostOrder(List<Integer> res, TreeNode root) {
        if (root == null) return;
        helperPostOrder(res, root.left);
        helperPostOrder(res, root.right);
        res.add(root.val);
    }

    static List<List<Integer>> levelOrderDFS(TreeNode root) {
        Queue<TreeNode> queue = new LinkedList<>();
        List<List<Integer>> wrapList = new LinkedList<>();

        if (root == null) return wrapList;
        queue.offer(root);
        while (!queue.isEmpty()) {
            int levelNum = queue.size();
            List<Integer> szubList = new LinkedList<>();
            for (int i = 0; i < levelNum; ++i) {
                if (queue.peek().left != null) queue.offer(queue.peek().left);
                if (queue.peek().right != null) queue.offer(queue.peek().right);
                szubList.add(queue.poll().val);
            }
            wrapList.add(szubList);
        }
        return wrapList;
    }


    // [3],
    static List<List<Integer>> levelOrderBFS(TreeNode root) {
        List<List<Integer>> res = new ArrayList<>();
        levelHelperBFS(res, root, 0);
        return res;
    }

    static void levelHelperBFS(List<List<Integer>> res, TreeNode root, int height) {
        if (root == null) return;
        if (height >= res.size()) res.add(new LinkedList<>());
        res.get(height).add(root.val);
        levelHelperBFS(res, root.left, height + 1);
        levelHelperBFS(res, root.right, height + 1);
    }

    public static void main(String[] args) {



        TreeNode n7 = new TreeNode(2);

        TreeNode n6 = new TreeNode(5);
        TreeNode n5 = new TreeNode(3);
        TreeNode n4 = new TreeNode(4, n7, null);
        TreeNode n3 = new TreeNode(1);

        TreeNode n2 = new TreeNode(4, n5, n6);
        TreeNode n1 = new TreeNode(2, n3, n4);

        TreeNode root = new TreeNode(1, n1, n2);

        //preorderTraversal(root);

        //inorderTraversal(root);
       // postorderTraversal(root);

        levelOrderBFS(root);
    }
}
