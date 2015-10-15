/**
 *  Compilation:  javac MyHash.java
 *  Execution:    java MyHash accounts.java
 *  Dependencies: In.java StdOut.java
 * 
 */

import java.util.NoSuchElementException;
/**
 * Implement a hash table for to archive account and passward.
 * This implementation doesn't handle collision and will return null
 * if duplicated key is insert. Value can be duplicated. This hashtable
 * does not accept key and value as null. When the key is already taken,
 * the hashtable will not override the existing value in order to protect
 * the data. The only way to change the value is to delete the entry and
 * create a new one. This hash table doesn't handle the collision.
 * 
 * Feng Liu
 * 8/23/2015
 */
public class MyHash<Key, Value>
{
    private final int INITIAL_SIZE = 4;    // initial size of hashtable when construction
    private int M;              // the size of hashtable
    private int size;           // current number of element in hashtable
    private Value[] value;         // array of values
    private Key[] key;              // array of keys

    public MyHash()
    {
        size = 0;
        M = INITIAL_SIZE;
        value = (Value[]) new Object[INITIAL_SIZE];
        key = (Key[]) new Object[INITIAL_SIZE];
    }

    public int sizeM() {
        return key.length;
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size() == 0;
    }

    public int hash(Key key) {
        if (key == null) throw new NullPointerException("The key is null");
        return (key.hashCode() & 0x7fffffff) % M;
    }

    /**
     * in 1st implementation, keyword this is missing in put and get and 
     * other methods and then compile error like "array required, but object found"
     */
    public void put(Key key, Value value) {
        if (key == null) throw new NullPointerException("The key is null");
        if (value == null) throw new NoSuchElementException("The value is null");

        int hash = hash(key);
        if (this.key[hash] == null) {
            this.key[hash] = key;
            this.value[hash] = value;
            size += 1;
        }
        //else, the key is already taken and do nothing

        if (size >= (M/2)) resize();
    }

    public Value get(Key key) {
        if (key == null) throw new NullPointerException("The key is null");

        int hash = hash(key);
        if (this.key[hash] != null) return value[hash];

        return null;
    }

    public boolean contains(Key key) {
        if (key == null) throw new NullPointerException("The key is null");

        int hash = hash(key);
        if (this.key[hash] != null) return true;

        return false;
    }

    public void delete(Key key) {
        if (key == null) throw new NullPointerException("The key is null");

        int hash = hash(key);
        this.key[hash] = null;
        this.value[hash] = null;
    }

    /**
     * in the first implementation, forgot to reassign the field "this.key = tempKey"
     */
    private void resize() {
        //update M
        M = M*2;
        Key[] tempKey = (Key[]) new Object[M];
        Value[] tempValue = (Value[]) new Object[M];

        //rehash every entry
        for (int i = 0; i < key.length; i++) {
            if (this.key[i] != null && this.value[i] != null) {
                int reHash = hash(this.key[i]); // get key and rehash with new size
                tempKey[reHash] = this.key[i];  // put the key to the new index
                tempValue[reHash] = this.value[i]; // put the value to the new index
            }
        }

        this.key = tempKey;
        this.value = tempValue;
    }

    /**
     * hashtable test case
     */
    public static void main(String args[]) {
        //and other data type?
        MyHash<String, String> hashTb = new MyHash<String, String>();
        String filename = args[0];
        In in = new In(filename);

        //null key and null value
        try {
            //code that may throw exception
            hashTb.put(null, "23228958");
        }
        catch(Exception ex) {
            System.out.println(ex);
        }

        try {
            //code that may throw exception
            hashTb.put("Newman", null);
        }
        catch(Exception ex) {
            System.out.println(ex+"\n");
        }

        //isEmpty
        StdOut.println("isEmpty = " + hashTb.isEmpty()); 
        // very stupid mistake that calling isEmpty without "hashTb."

        //put, get, contains, size, sizeM and resize and hashing
        for (int i = 0; !in.isEmpty(); i++) {
            String key = in.readString();
            String value = in.readString();

            StdOut.println("input key: "+key+", value: "+value);
            // put entry, test unique key and override key along the way
            if (!hashTb.contains(key)) hashTb.put(key, value);
            else {
                //hashing correctly?
                hashTb.put(key, value);
                StdOut.println("key is existing and not to override value");
                StdOut.println("the existing key: "+key+", value: "+hashTb.get(key));
            }

            StdOut.println("hash of the key is " + hashTb.hash(key));
            //contains
            if (hashTb.contains(key)) StdOut.println("yes, key exists in table");
            else StdOut.println("no, somthing is wrong");

            //size, sizeM and resize
            StdOut.println("current element: "+hashTb.size()
                +" current array size: "+hashTb.sizeM()+"\n");
        }

        //isEmpth
        StdOut.println("isEmpty = " + hashTb.isEmpty());

        //delete
        String st1 = "edward"; //exist
        StdOut.println("before, value is " + hashTb.get(st1));
        StdOut.println("before, key existed? = "+ hashTb.contains(st1));
        hashTb.delete(st1);
        StdOut.println("after, value is " + hashTb.get(st1));
        StdOut.println("after, key exist? = "+ hashTb.contains(st1));

        String st2 = "edward123"; //not exist
        StdOut.println("before, value is " + hashTb.get(st2));
        StdOut.println("before, key existed? = "+ hashTb.contains(st2));
        hashTb.delete(st2);
        StdOut.println("after, value is " + hashTb.get(st2));
        StdOut.println("after, key exist? = "+ hashTb.contains(st2));
    }
}
