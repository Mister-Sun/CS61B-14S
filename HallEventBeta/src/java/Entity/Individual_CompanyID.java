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
public class Individual_CompanyID implements Serializable{
    @ManyToOne(fetch = FetchType.LAZY)
    private Individual individual;
    @ManyToOne(fetch = FetchType.LAZY)
    private Company company;

    public Individual_CompanyID() {
        this.individual = new Individual();
        this.company = new Company();
    }

    public Individual getIndividual() {
        return individual;
    }

    public void setIndividual(Individual individual) {
        this.individual = individual;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }
    
}
