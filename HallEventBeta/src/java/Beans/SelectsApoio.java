/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Beans;


import Enums.Cards;
import Enums.Gender;
import Enums.ParticipationType;
import Enums.RegisteredType;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.model.SelectItem;

/**
 *
 * @author thiagovulcao
 */
@ManagedBean(name="apoio")
@RequestScoped
public class SelectsApoio implements Serializable {

    /**
     * Creates a new instance of SelectsApoio
     */
    public SelectsApoio() {
    }

    public List<SelectItem> getGenders() {
        List<SelectItem> genders = new ArrayList<SelectItem>();
        for (Gender g : Gender.values()) {
            genders.add(new SelectItem(g, g.toString()));
        }
        return genders;
    }
    
    public List<SelectItem> getTPart(){
        List<SelectItem> tpart = new ArrayList<SelectItem>();
        for (RegisteredType tp : RegisteredType.values()) {
            tpart.add(new SelectItem(tp, tp.toString()));
        }
        return tpart;
    }
    
    public List<SelectItem> getTPresenca(){
        List<SelectItem> presenca = new ArrayList<SelectItem>();
        for (ParticipationType pt : ParticipationType.values()) {
            presenca.add(new SelectItem(pt, pt.toString()));
        }
        return presenca;
    }
    
    public List<SelectItem> getCards(){
        List<SelectItem> cards = new ArrayList<>();
        for (Cards c : Cards.values()) {
            cards.add(new SelectItem(c, c.toString()));
        }
        return cards;
    }
}
