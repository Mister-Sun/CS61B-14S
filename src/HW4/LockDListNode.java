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
public class LockDListNode extends DListNode {
    
    public boolean locked;

    public LockDListNode(Object i, DListNode p, DListNode n) {
        super(i, p, n);
        locked = false;
    }

}
