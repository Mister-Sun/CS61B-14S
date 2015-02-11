/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Entity;


import Util.BaseEntity;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/**
 *
 * @author THIAGOVULCAO
 */
@Entity
public class Cnae implements Serializable, BaseEntity {

    private static final long serialVersionUID = 1L;
    @Id
    private Long id;
    @Column(name = "activity", nullable = false)
    private String activity;
    @Column(name = "description")
    private String description;
    @Column(name = "classification")
    private Integer classification;
    
    @ManyToOne( fetch = FetchType.LAZY)
    @JoinColumn(name = "sector_id")
    private Sector sector;

    @Override
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    
    public String getActivity() {
        String retorno="";
        int contaPalavras = 0;
        for (int i = 0; i < activity.length(); i++) {
            retorno += activity.charAt(i);
            if (activity.charAt(i) == ' ') {
                contaPalavras++;
                if (contaPalavras ==8){
                    retorno += "</br>";
                    contaPalavras = 0;
                }
            }   
        }
        return retorno;
    }

    public void setActivity(String atividade) {
        this.activity = atividade;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Sector getSector() {
        return sector;
    }

    public void setSector(Sector sector) {
        this.sector = sector;
    }

    public Integer getClassification() {
        return classification;
    }

    public void setClassification(Integer classification) {
        this.classification = classification;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Cnae)) {
            return false;
        }
        Cnae other = (Cnae) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.ebi.fomentapa.empresa.modelo.Cnae[ id=" + id + " ]";
    }
}
