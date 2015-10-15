import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Binary search tree
 * 
 * @author Feng Liu
 * @version 8/16/2015
 */
public class MyBST<Key extends Comparable<Key>, Value>
{
    Node root;
    int size;

    public MyBST() {
        root = null;
        size = 0;
    }

    private class Node{
        Node left, right;
        Key key;
        Value value;

        public Node(Key key, Value value) {
            this.key = key;
            this.value = value;
            left = null;
            right = null;
        }
    }

    /**
     * get the quality of existing entries in the tree
     */
    public int size() {
        return size;
    }

    /**
     * check the tree if it is empty
     */
    public boolean isEmpty() {
        return size() == 0;
    }

    /**
     * clear the entire tree
     */
    public void clear() {
        root = null;
    }

    /**
     * put the entry in our tree. update the vlaue with new vlaue if the entry
     * is alread existed in our tree
     */
    public void put(Key key, Value value) {
        root = put(root, key, value);
    }

    // it should return the new created node, and then assign to the either left or right child
    private Node put(Node node, Key key, Value value) {
        if (node == null) {
            size += 1;
            return new Node(key, value);
        }

        int cmd = node.key.compareTo(key);
        //StdOut.println("put cmd is " + cmd);
        if      (cmd > 0) node.left = put(node.left, key, value);
        else if (cmd < 0) node.right = put(node.right, key, value);
        else node.value = value; // update the value if same key found

        return node;
    }

    /**
     * get the value with given key, return null if the key is not existed
     * in the tree
     */
    public Value get(Key key) {
        return get(root, key);
    }

    private Value get(Node node, Key key) {
        if (node == null) return null;

        int cmd = node.key.compareTo(key);
        //StdOut.println("get cmd is " + cmd);
        if      (cmd > 0) return get(node.left, key);
        else if (cmd < 0) return get(node.right, key);

        return node.value; // else node.key == key and return the value
    }

    /**
     * check the tree with give key and return true if the key is existed. 
     * Otherwise, return false
     */
    public boolean containsKey(Key key) {
        return containsKey(root, key);
    }

    private boolean containsKey(Node node, Key key) {
        if (node == null) return false; // not found until last

        int cmd = node.key.compareTo(key);
        if      (cmd > 0) return containsKey(node.left, key);
        else if (cmd < 0) return containsKey(node.right, key);
        else              return true; // else node.key == key and return true
    }

    /**
     * check the tree with given value in linear time and return true if the
     * value is in the tree. Otherwise, return false
     */
    public boolean containsValue(Value value) {
        return containsValue(root, value);
    }

    private boolean containsValue(Node node, Value value) {
        if (node == null) return false;

        // equals() ok here?
        if (!node.value.equals(value)) 
            return containsValue(node.left, value) || containsValue(node.right, value);
        return true;
    }

    /**
     * return the least key greater than or equal to the given key
     */
    public Key ceiling(Key key) {
        return ceiling(root, key);
    }

    private Key ceiling(Node node, Key key) {
        if (node == null) return null;

        int cmd = node.key.compareTo(key);
        if      (cmd == 0) return node.key;
        else if (cmd < 0) return ceiling(node.right, key); // node < key
        //node > key
        Key temp = ceiling(node.left, key);
        if (temp == null) return node.key;
        else return temp;
    }

    /**
     * return the greatest key less than or equal to the given key
     */
    public Key floor(Key key) {
        return floor(root, key);
    }

    private Key floor(Node node, Key key) {
        if (node == null) return null;

        int cmd = node.key.compareTo(key);
        if (cmd == 0) return node.key;
        else if (cmd > 0) return floor(node.left, key);// node > key
        //node < key
        Key temp = floor(node.right, key);
        if (temp == null) return node.key;
        else return temp;
    }

    public Iterable<Key> levelOrder() {
        Queue<Node> nodes = new Queue<Node>();
        Queue<Key> keys = new Queue<Key>();

        nodes.enqueue(root);
        while (!nodes.isEmpty()) {
            Node node = nodes.dequeue();
            if (node == null) continue;
            nodes.enqueue(node.left); // may add null to queue
            nodes.enqueue(node.right);
            keys.enqueue(node.key);
        }

        return keys;
    }

    public Queue<Key> preOrder() {
        Queue<Key> keys = new Queue<Key>();
        return preOrder(root, keys);
    }

    private Queue<Key> preOrder(Node node, Queue<Key> keys) {
        if (node == null) return keys;

        keys.enqueue(node.key);
        keys = preOrder(node.left, keys);
        keys = preOrder(node.right, keys);

        return keys;
    }

    public static void main(String args[]) {
        MyBST<Integer, Integer> tree = new MyBST<Integer, Integer>();
        StdOut.println("test put and isEmpty method");
        StdOut.println("is the tree empty? " + tree.isEmpty());
        tree.put(1, 11); 
        tree.put(2, 12);
        tree.put(3, 13);

        StdOut.println("test isEmpty and size method");
        StdOut.println("is the tree empty? " + tree.isEmpty());
        System.out.println("the size of tree is " + tree.size());

        StdOut.println("test get method");
        System.out.println(tree.get(1));
        System.out.println(tree.get(2));
        System.out.println(tree.get(3));
        System.out.println(tree.get(0));

        StdOut.println("test containsKey method");
        System.out.println(tree.containsKey(1));
        System.out.println(tree.containsKey(2));
        System.out.println(tree.containsKey(3));
        System.out.println(tree.containsKey(0));

        StdOut.println("test containsValue method");
        StdOut.println(tree.containsValue(11));
        StdOut.println(tree.containsValue(12));
        StdOut.println(tree.containsValue(13));
        StdOut.println(tree.containsValue(14));

        StdOut.println("test floor and ceiling method");
        StdOut.println(tree.floor(10));
        StdOut.println(tree.ceiling(2));

        tree.clear();
        tree.put(4,4);
        tree.put(2,2);
        tree.put(3,3);
        tree.put(9,9);
        tree.put(8,8);
        tree.put(7,7);
        tree.put(1,1);
        tree.put(10,10);
        
        StdOut.println("pre order");
        //preOrder should print 4 2 1 3 9 8 7 10
        for (int i : tree.preOrder()) {
            StdOut.println(i);
        }

        //levelOrder should print 4 2 9 1 3 8 10 7
        StdOut.println("levelOrder");
        for (int i : tree.levelOrder()) {
            StdOut.println(i);
        }
    }
}
