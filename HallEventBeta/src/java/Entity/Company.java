/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

/**
 *
 * @author EBI
 */
@Entity
public class Company implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "cnpj", length = 18, nullable = false, unique = true)
    private String cnpj;
    @Temporal(TemporalType.DATE)
    @Column(name = "opening_date", nullable = false)
    private Date openingDate;
    @Column(name = "trade_name", nullable = false, length = 75)
    private String tradeName;
    @Column(name = "company_name", nullable = false, length = 75)
    private String companyName;
    @Column(name = "employees", nullable = false)
    private Integer employees;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "company")
    private List<Individual> individuals = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "ic.company")
    @Cascade(CascadeType.ALL)
    private List<Individual_Company> companys = new ArrayList<>();

    @JoinColumn(name = "sector")
    @OneToOne
    private Sector sector;

    @JoinColumn(name = "company_size")
    @OneToOne
    private CompanySize companySize;

    @JoinColumn(name = "cnae")
    @OneToOne
    private Cnae cnae;

    @OneToMany
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<Registered> registereds = new ArrayList<>();

    @Transient
    private Address address = new Address();

    @Transient
    private List<Contact> contacts = new ArrayList<>();

    public Company() {
    }

    public List<Individual_Company> getIndiCompanys() {
        return companys;
    }

    public void setIndiCompanys(List<Individual_Company> companys) {
        this.companys = companys;
    }

    public List<Registered> getRegistereds() {
        return registereds;
    }

    public void setRegistereds(List<Registered> registereds) {
        this.registereds = registereds;
    }

    public Address getAddres() {
        return address;
    }

    public void setAddres(Address address) {
        this.address = address;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public Date getOpeningDate() {
        return openingDate;
    }

    public void setOpeningDate(Date openingDate) {
        this.openingDate = openingDate;
    }

    public String getTradeName() {
        return tradeName;
    }

    public void setTradeName(String tradeName) {
        this.tradeName = tradeName;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public Integer getEmployees() {
        return employees;
    }

    public void setEmployees(Integer employees) {
        this.employees = employees;
    }

    public List<Individual> getIndividuals() {
        return individuals;
    }

    public void setIndividuals(List<Individual> individuals) {
        this.individuals = individuals;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public List<Contact> getContacts() {
        return contacts;
    }

    public void setContacts(List<Contact> contacts) {
        this.contacts = contacts;
    }

    public Sector getSector() {
        return sector;
    }

    public void setSector(Sector sector) {
        this.sector = sector;
    }

    public CompanySize getCompanySize() {
        return companySize;
    }

    public void setCompanySize(CompanySize companySize) {
        this.companySize = companySize;
    }

    public Cnae getCnae() {
        return cnae;
    }

    public void setCnae(Cnae cnae) {
        this.cnae = cnae;
    }

}
