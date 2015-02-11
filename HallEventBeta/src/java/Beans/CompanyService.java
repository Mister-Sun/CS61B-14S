/*
 * This file contains all the methods for company information update.
 * Serves as managed bean for xhtml page formCompany.xhtml and formAtualizarComp.xhtml.
 * Last revision Sep, 12, 2014 by Sun
 */
package Beans;

import DAO.DaoImpl;
import DAO.EventDaoImpl;
import Entity.City;
import Entity.Cnae;
import Entity.Company;
import Entity.CompanySize;
import Entity.Contact;
import Entity.Country;
import Entity.District;
import Entity.Person;
import Entity.Sector;
import Entity.States;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import org.hibernate.criterion.Restrictions;
import Util.MessageUtility;
import java.io.Serializable;
import org.hibernate.criterion.MatchMode;

/**
 *
 * @author sun
 */
@ManagedBean(name = "crudCompany")
@SessionScoped
public class CompanyService implements Serializable {

    private DaoImpl dao;
    private Person person;
    private Contact contact;
    private Contact contactEdit;

    private Company company;
    private List<Company> companyList = new ArrayList<>();
    private List<Person> personList = new ArrayList<>();

    private String search;
    private String searchType;

    private List<CompanySize> comSizes;
    private List<Cnae> cnaes;

    private List<Country> countries;
    private List<States> states;
    private List<City> cities;
    private List<District> districts;

    public CompanyService() {
        this.dao = new EventDaoImpl();
        this.company = new Company();
        this.person = new Person();
        this.contact = new Contact();
        this.contactEdit = new Contact();
    }

    public List<Person> getPersonList() {
        return personList;
    }

    public void setPersonList(List<Person> personList) {
        this.personList = personList;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public Contact getContact() {
        return contact;
    }

    public void setContact(Contact contact) {
        this.contact = contact;
    }

    public Contact getContactEdit() {
        return contactEdit;
    }

    public void setContactEdit(Contact contactEdit) {
        this.contactEdit = contactEdit;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public List<Company> getCompanyList() {
        return companyList;
    }

    public void setCompanyList(List<Company> companyList) {
        this.companyList = companyList;
    }

    public String getSearch() {
        return search;
    }

    public void setSearch(String search) {
        this.search = search;
    }

    public String getSearchType() {
        return searchType;
    }

    public void setSearchType(String searchType) {
        this.searchType = searchType;
    }

    public List<Sector> getSectors() {
        List<Sector> sectors = dao.getSession().createCriteria(Sector.class).list();
        return sectors;
    }

    public List<Country> getCountries() {
        List<Country> countries = dao.getSession().createCriteria(Country.class).list();
        return countries;
    }

    public List<CompanySize> getComSizes() {
        return comSizes;
    }

    public void setComSizes(List<CompanySize> comSizes) {
        this.comSizes = comSizes;
    }

    public List<Cnae> getCnaes() {
        return cnaes;
    }

    public void setCnaes(List<Cnae> cnaes) {
        this.cnaes = cnaes;
    }

    public List<States> getStates() {
        return states;
    }

    public void setStates(List<States> states) {
        this.states = states;
    }

    public List<City> getCities() {
        return cities;
    }

    public void setCities(List<City> cities) {
        this.cities = cities;
    }

    public List<District> getDistricts() {
        return districts;
    }

    public void setDistricts(List<District> districts) {
        this.districts = districts;
    }

    public void loadCompanySize() {
        this.comSizes = dao.getSession().createCriteria(CompanySize.class)
                .add(Restrictions.eq("sector", this.person.getCompany().getSector())).list();
    }

    public void loadStates() {
        this.states = dao.getSession().createCriteria(States.class)
                .add(Restrictions.eq("country", this.person.getAddress().getCountry())).list();
        this.cities = new ArrayList<>();
    }

    public void loadCities() {
        this.cities = dao.getSession().createCriteria(City.class)
                .add(Restrictions.eq("state", this.person.getAddress().getState())).list();
        this.districts = new ArrayList<>();
    }

    public void loadDistricts() {
        this.districts = dao.getSession().createCriteria(District.class)
                .add(Restrictions.eq("city", this.person.getAddress().getCity())).list();
    }

    public void loadCnaes() {
        this.cnaes = dao.getSession().createCriteria(Cnae.class)
                .add(Restrictions.eq("sector", this.person.getCompany().getSector())).list();
    }

    public void searchCompanies() {
        this.companyList = new ArrayList<>();
        if (search.isEmpty()) {
            this.personList = dao.getSession().createCriteria(Person.class).add(Restrictions.eq("active", false)).createCriteria("company").list();
        } else {
            this.personList = dao.getSession().createCriteria(Person.class).add(Restrictions.eq("active", false)).createCriteria("company").add(Restrictions.like(searchType, search, MatchMode.ANYWHERE)).list();
        }
        for (Person p : this.personList) {
            this.companyList.add(p.getCompany());
        }
    }

    public String loadCompany(Company c) {
        this.person = (Person) dao.getSession().createCriteria(Person.class).add(Restrictions.eq("company", c)).uniqueResult();
        this.company = c;
        loadCompanySize();
        loadCnaes();
        loadStates();
        loadCities();
        loadDistricts();
        return "/formAtualizarComp.htm";
    }

    public void deleteCompany(Company com) {
        Person p = (Person) dao.getSession().createCriteria(Person.class).add(Restrictions.eq("company", com)).uniqueResult();

        p.setActive(true);

        try {
            this.dao.update(p);
            MessageUtility.callInfo("sucess");

            this.searchCompanies();
        } catch (Exception e) {
            MessageUtility.callError("error");
        }
    }

    public void deleteContact(Contact con) {
        person.getContacts().remove(con);
        dao.update(person);
        dao.destroy(con);
    }

    public void addCompanyContact() {
        this.contact.setPerson(this.person);
        this.person.getContacts().add(this.contact);
        dao.update(person);
        this.contact = new Contact();
    }

    public void loadCompanyContact(Contact con) {
        this.contactEdit = con;
    }

    public void updateCompanyContact() {
        dao.update(contactEdit);
        this.contactEdit = new Contact();
    }

    public String updateCompany() {

        dao.update(person);
        System.out.println("Company Size: " + company.getCompanySize().getDescription());

        return "/formCompany.htm";
    }

}
