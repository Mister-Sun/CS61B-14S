/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Entity;


import Util.BaseEntity;
import java.io.Serializable;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

/**
 *
 * @author THIAGOVULCAO
 */
@Entity
public class Sector implements Serializable, BaseEntity {

    //private static final long serialVersionUID = 1L;
    @Id
    private Long id;
    @Column(name = "description", length = 20, nullable = false)
    private String description;
    @Column(name = "status", length = 1, nullable = false)
    private boolean status;
    @OneToMany(fetch = FetchType.LAZY)
    private List<CompanySize> comSize;
    @OneToMany(fetch = FetchType.LAZY)
    private List<Cnae> cnaes;

    @Override
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean getStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public List<CompanySize> getComSize() {
        return comSize;
    }

    public void setComSize(List<CompanySize> comSize) {
        this.comSize = comSize;
    }

    public List<Cnae> getCnaes() {
        return cnaes;
    }

    public void setCnaes(List<Cnae> cnaes) {
        this.cnaes = cnaes;
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
        if (!(object instanceof Sector)) {
            return false;
        }
        Sector other = (Sector) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.ebi.fomentapa.empresa.modelo.Setor[ id=" + id + " ]";
    }
}
