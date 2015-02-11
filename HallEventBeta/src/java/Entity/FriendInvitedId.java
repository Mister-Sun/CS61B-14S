/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entity;

import java.io.Serializable;
import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;

/**
 *
 * @author sun
 */
@Embeddable
public class FriendInvitedId implements Serializable {

    @ManyToOne(fetch = FetchType.LAZY)
    private Person personInvitor;
    @ManyToOne(fetch = FetchType.LAZY)
    private Person personInvited;

    public FriendInvitedId() {
        this.personInvitor = new Person();
        this.personInvited = new Person();
    }

    public Person getPersonInvitor() {
        return personInvitor;
    }

    public void setPersonInvitor(Person personInvitor) {
        this.personInvitor = personInvitor;
    }

    public Person getPersonInvited() {
        return personInvited;
    }

    public void setPersonInvited(Person personInvited) {
        this.personInvited = personInvited;
    }

}
