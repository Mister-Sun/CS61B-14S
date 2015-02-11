/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Entity;

import Util.BaseEntity;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

/**
 *
 * @author thiagovulcao
 */
@Entity
public class Address implements Serializable, BaseEntity {

    //private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "cep", length = 8, nullable = false)
    private String cep;
    @Column(name = "street", length = 50, nullable = false)
    private String street;
    @Column(name = "complement", length = 50)
    private String complement;
    @Column(name = "number", length = 8)
    private String number;
    
    @JoinColumn(name = "district", updatable = true)
    @OneToOne
    private District district;
    
    @JoinColumn(name = "city", updatable = true)
    @OneToOne
    private City city;
    
    @JoinColumn(name = "state", updatable = true)
    @OneToOne
    private States state;
    
    @JoinColumn(name = "country", updatable = true)
    @OneToOne
    private Country country;
    
    @Override
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        String s = cep.replace("-", "");
        this.cep = s;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getComplement() {
        return complement;
    }

    public void setComplement(String complement) {
        this.complement = complement;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public District getDistrict() {
        return district;
    }

    public void setDistrict(District district) {
        this.district = district;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public States getState() {
        return state;
    }

    public void setState(States state) {
        this.state = state;
    }

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
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
        if (!(object instanceof Address)) {
            return false;
        }
        Address other = (Address) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.ebi.fomentapa.endereco.modelo.Endereco[ id=" + id + " ]";
    }
}
