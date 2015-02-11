/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HW4;

/**
 *
 * @author sun
 */
public class LockDList extends DList {

//
//    @Override
    protected DListNode newNode(Object item, DListNode prev, DListNode next) {
        DListNode node = new LockDListNode(item, prev, next);
        return node;
    }

    public void lockNode(DListNode node) {
        ((LockDListNode) node).locked = true;
    }

    public void remove(DListNode node) {
        if (((LockDListNode) node).locked) {
            return;
        }
        super.remove(node);
    }

    @Override
    public LockDListNode back() {
        return (LockDListNode)super.back(); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public LockDListNode front() {
        return (LockDListNode)super.front(); //To change body of generated methods, choose Tools | Templates.
    }

}
