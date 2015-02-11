/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entity;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.AssociationOverride;
import javax.persistence.AssociationOverrides;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;

/**
 *
 * @author sun
 */
@Entity(name = "friend_invited")
@AssociationOverrides({
    @AssociationOverride(name = "friendInvitedId.personInvitor", joinColumns
            = @JoinColumn(name = "person_invitor_id")),
    @AssociationOverride(name = "friendInvitedId.personInvited", joinColumns
            = @JoinColumn(name = "person_invited_id"))})
public class FriendInvited implements Serializable {

    @EmbeddedId
    private FriendInvitedId friendInvitedId = new FriendInvitedId();

    public FriendInvitedId getFriendInvitedId() {
        return friendInvitedId;
    }

    public void setFriendInvitedId(FriendInvitedId friendInvitedId) {
        this.friendInvitedId = friendInvitedId;
    }

    public Person getPersonInvitor() {
        return this.getFriendInvitedId().getPersonInvitor();
    }

    public void setPersonInvitor(Person p) {
        this.getFriendInvitedId().setPersonInvitor(p);
    }

    public Person getPersonInvited() {
        return this.getFriendInvitedId().getPersonInvited();
    }

    public void setPersonInvited(Person p) {
        this.getFriendInvitedId().setPersonInvited(p);
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 37 * hash + Objects.hashCode(this.friendInvitedId);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final FriendInvited other = (FriendInvited) obj;
        if (!Objects.equals(this.friendInvitedId, other.friendInvitedId)) {
            return false;
        }
        return true;
    }

}
