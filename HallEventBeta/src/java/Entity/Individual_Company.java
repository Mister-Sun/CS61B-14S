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
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import org.primefaces.validate.bean.ClientConstraint;

/**
 *
 * @author sun
 */
@Entity(name = "individual_company")
@AssociationOverrides({
    @AssociationOverride(name = "ic.individual", joinColumns
            = @JoinColumn(name = "individual_id")),
    @AssociationOverride(name = "ic.company", joinColumns
            = @JoinColumn(name = "company_id"))
})
public class Individual_Company implements Serializable {

    @EmbeddedId
    private Individual_CompanyID ic;

    @Column(name = "type")
    private String type;

    public Individual_Company() {
        this.ic = new Individual_CompanyID();
    }

    public Individual_CompanyID getIc() {
        return ic;
    }

    public void setIc(Individual_CompanyID ic) {
        this.ic = ic;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Individual getIndividual() {
        return this.getIc().getIndividual();
    }

    public void setIndividual(Individual i) {
        this.getIc().setIndividual(i);
    }

    public Company getCompany() {
        return this.getIc().getCompany();
    }

    public void setCompany(Company c) {
        this.getIc().setCompany(c);
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 97 * hash + Objects.hashCode(this.ic);
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
        final Individual_Company other = (Individual_Company) obj;
        if (!Objects.equals(this.ic, other.ic)) {
            return false;
        }
        return true;
    }

}
