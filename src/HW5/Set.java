package HW5;

/* Set.java */
import HW5.list.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * A Set is a collection of Comparable elements stored in sorted order.
 * Duplicate elements are not permitted in a Set.
 *
 */
public class Set {
    /* Fill in the data fields here. */

    private DList listSet;

    /**
     * Set ADT invariants: 1) The Set's elements must be precisely the elements
     * of the List. 2) The List must always contain Comparable elements, and
     * those elements must always be sorted in ascending order. 3) No two
     * elements in the List may be equal according to compareTo().
     *
     */
    /**
     * Constructs an empty Set.
     *
     * Performance: runs in O(1) time.
     *
     */
    public Set() {
        // Your solution here.
        listSet = new DList();
    }

    /**
     * cardinality() returns the number of elements in this Set.
     *
     * Performance: runs in O(1) time.
     *
     */
    public int cardinality() {
        // Replace the following line with your solution.
        return listSet.length();
    }

    /**
     * insert() inserts a Comparable element into this Set.
     *
     * Sets are maintained in sorted order. The ordering is specified by the
     * compareTo() method of the java.lang.Comparable interface.
     *
     * Performance: runs in O(this.cardinality()) time.
     *
     */
    public void insert(Comparable c) {
        // Your solution here.
        ListNode curr = listSet.front();

        try {
            while (curr.isValidNode()) {
                if (c.compareTo(curr.item()) == 0) {
                    return;
                }
                if (c.compareTo(curr.item()) < 0) {
                    curr.insertBefore(c);
                    return;
                }
                curr = curr.next();
            }
        } catch (InvalidNodeException e) {
            System.out.print(e);
        }
        listSet.insertBack(c);
    }

    /**
     * union() modifies this Set so that it contains all the elements it started
     * with, plus all the elements of s. The Set s is NOT modified. Make sure
     * that duplicate elements are not created.
     *
     * Performance: Must run in O(this.cardinality() + s.cardinality()) time.
     *
     * Your implementation should NOT copy elements of s or "this", though it
     * will copy _references_ to the elements of s. Your implementation will
     * create new _nodes_ for the elements of s that are added to "this", but
     * you should reuse the nodes that are already part of "this".
     *
     * DO NOT MODIFY THE SET s. DO NOT ATTEMPT TO COPY ELEMENTS; just copy
     * _references_ to them.
     *
     */
    public void union(Set s) {
        // Your solution here.  
        ListNode n1 = listSet.front();
        ListNode n2 = s.listSet.front();
        try {
//            while(n2.isValidNode()){
//                int compare = ((Comparable) n1.item()).compareTo((Comparable) n2.item());
//                if(compare > 0){
//                    ListNode temp = n2.next();
//                    n1.insertBefore(n2.item());
//                    n2 = temp;
//
//                }else if (compare == 0){
//                    n2 = n2.next();
//                }else{
//                    break;
//                }
//            }
            while (n2.isValidNode() && n1.isValidNode()) {
                int compare = ((Comparable) n1.item()).compareTo((Comparable) n2.item());
                if (compare == 0) {
                    n2 = n2.next();
                } else if (compare > 0) {
                    // n1 > n2
                    n1.insertBefore(n2.item());
                    n2 = n2.next();

                } else {
                    // n1 < n2
                    n1 = n1.next();
                }
            }
            while (n2.isValidNode()) {
                listSet.insertBack(n2.item());
                n2 = n2.next();
            }
        } catch (InvalidNodeException e) {
            System.out.print(e);
        }
    }

    /**
     * intersect() modifies this Set so that it contains the intersection of its
     * own elements and the elements of s. The Set s is NOT modified.
     *
     * Performance: Must run in O(this.cardinality() + s.cardinality()) time.
     *
     * Do not construct any new ListNodes during the execution of intersect.
     * Reuse the nodes of "this" that will be in the intersection.
     *
     * DO NOT MODIFY THE SET s. DO NOT CONSTRUCT ANY NEW NODES. DO NOT ATTEMPT
     * TO COPY ELEMENTS.
     *
     */
    public void intersect(Set s) {
        // Your solution here.
        ListNode n1 = this.listSet.front();
        ListNode n2 = s.listSet.front();
        try {
            while (n2.isValidNode() && n1.isValidNode()) {
                int compare = ((Comparable) n1.item()).compareTo((Comparable) n2.item());
                if (compare > 0) {
                    n2 = n2.next();

                } else if (compare == 0) {
                    n2 = n2.next();
                    n1 = n1.next();
                } else {
                    ListNode temp = n1.next();
                    n1.remove();
                    n1 = temp;
                }
            }
            while (n1.isValidNode()) {
                ListNode temp = n1.next();
                n1.remove();
                n1 = temp;
            }
        } catch (InvalidNodeException e) {
            System.out.print(e);
        }
    }

    /**
     * toString() returns a String representation of this Set. The String must
     * have the following format: { } for an empty Set. No spaces before "{" or
     * after "}"; two spaces between them. { 1 2 3 } for a Set of three Integer
     * elements. No spaces before "{" or after "}"; two spaces before and after
     * each element. Elements are printed with their own toString method,
     * whatever that may be. The elements must appear in sorted order, from
     * lowest to highest according to the compareTo() method.
     *
     * WARNING: THE AUTOGRADER EXPECTS YOU TO PRINT SETS IN _EXACTLY_ THIS
     * FORMAT, RIGHT UP TO THE TWO SPACES BETWEEN ELEMENTS. ANY DEVIATIONS WILL
     * LOSE POINTS.
     *
     */
    public String toString() {
        // Replace the following line with your solution.
        String result = "{ ";
        if (!listSet.isEmpty()) {
            ListNode curr = listSet.front();
            while (curr.isValidNode()) {
                try {
                    result = result.concat(curr.item().toString().concat(" "));
                    curr = curr.next();
                } catch (InvalidNodeException ex) {
                    Logger.getLogger(Set.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
        }
        result = result.concat("}");
        return result;
    }

    public static void main(String[] argv) {

        System.out.println("------------- Test insert Comparable -------------");
        Set s = new Set();
        s.insert(new Integer(3));
        s.insert(new Integer(4));
        s.insert(new Integer(8));
        System.out.println("Set s = " + s);

        Set s2 = new Set();
        s2.insert(new Integer(4));
        s2.insert(new Integer(5));
        s2.insert(new Integer(5));
        System.out.println("Set s2 = " + s2);

        Set s3 = new Set();
        s3.insert(new Integer(5));
        s3.insert(new Integer(3));
        s3.insert(new Integer(8));
        System.out.println("Set s3 = " + s3);

        Set s4 = new Set();
        s4.insert(new Integer(1));
        s4.insert(new Integer(2));
        s4.insert(new Integer(3));
        s4.insert(new Integer(6));
        s4.insert(new Integer(7));
        s4.insert(new Integer(9));
        System.out.println("Set s4 = " + s4);

        System.out.println("------------- Test union -------------");
        s.union(s2);
        System.out.println("After s.union(s2), s = " + s);
        s.union(s4);
        System.out.println("After s.union(s4), s = " + s);
        s3.union(s2);
        System.out.println("After s3.union(s2), s = " + s3);

        System.out.println("------------- Test intersect -------------");
        s3.intersect(s);
        System.out.println("After s3.intersect(s), s = " + s3);

        System.out.println("------------- Test cardinality -------------");
        System.out.println("s.cardinality() = " + s.cardinality());
        // You may want to add more (ungraded) test code here.
    }
}
