/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
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
import Entity.Individual_Company;
import Entity.Login;
import Entity.Person;
import Entity.PositionInCompany;
import Entity.Sector;
import Entity.States;
import Util.CNP;
import Util.Contexto;
import static Util.EmailService.sendEmail;
import Util.MessageUtility;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import org.apache.commons.mail.EmailException;
import org.hibernate.criterion.Restrictions;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;

/**
 *
 * @author Sun
 */
@ManagedBean(name = "cadClientCompany")
@SessionScoped
public class CadClientCompanyService implements Serializable {

    private DaoImpl dao;
    private Person person;
    private Person personCom;
    private Contact contact;
    private Company company;
    private Login login;
    private Individual_Company individualCompany;

    private Contact contactEdit;

    private List<States> states;
    private List<City> cities;
    private List<District> districts;

    private List<CompanySize> comSizes = new ArrayList<>();
    private List<Cnae> cnaes = new ArrayList<>();

    private boolean disableStateComp = true;
    private boolean disableCityComp = true;
    private boolean disableDistrictComp = true;
    private List<States> statesComp;
    private List<City> citiesComp;
    private List<District> districtsComp;

    private boolean reenvio = false;
    private boolean disableFields = true;
    private boolean validCompany = true;

    private boolean phone = true;
    private boolean cellphone = true;

    public CadClientCompanyService() {
        this.dao = new EventDaoImpl();
        this.login = this.loadLogin();
        this.person = login.getPerson();
        this.personCom = new Person();
        this.personCom.setCompany(new Company());
        this.contact = new Contact();
        this.company = new Company();
        this.individualCompany = new Individual_Company();
        phone = cellphone = true;
    }

    //Getters and Setters.
    public Login getLogin() {
        return login;
    }

    public void setLogin(Login login) {
        this.login = login;
    }

    public Individual_Company getIndividualCompany() {
        return individualCompany;
    }

    public void setIndividualCompany(Individual_Company individualCompany) {
        this.individualCompany = individualCompany;
    }

    public Contact getContactEdit() {
        return contactEdit;
    }

    public void setContactEdit(Contact contactEdit) {
        this.contactEdit = contactEdit;
    }

    public boolean isPhone() {
        return phone;
    }

    public void setPhone(boolean phone) {
        this.phone = phone;
    }

    public boolean isCellphone() {
        return cellphone;
    }

    public void setCellphone(boolean cellphone) {
        this.cellphone = cellphone;
    }

    public boolean isDisableFields() {
        return disableFields;
    }

    public void setDisableFields(boolean disableFields) {
        this.disableFields = disableFields;
    }

    public Person getPersonCom() {
        return personCom;
    }

    public void setPersonCom(Person personCom) {
        this.personCom = personCom;
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

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
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

    public boolean isDisableStateComp() {
        return disableStateComp;
    }

    public void setDisableStateComp(boolean disableStateComp) {
        this.disableStateComp = disableStateComp;
    }

    public boolean isDisableCityComp() {
        return disableCityComp;
    }

    public void setDisableCityComp(boolean disableCityComp) {
        this.disableCityComp = disableCityComp;
    }

    public boolean isDisableDistrictComp() {
        return disableDistrictComp;
    }

    public void setDisableDistrictComp(boolean disableDistrictComp) {
        this.disableDistrictComp = disableDistrictComp;
    }

    public List<States> getStatesComp() {
        return statesComp;
    }

    public void setStatesComp(List<States> statesComp) {
        this.statesComp = statesComp;
    }

    public List<City> getCitiesComp() {
        return citiesComp;
    }

    public void setCitiesComp(List<City> citiesComp) {
        this.citiesComp = citiesComp;
    }

    public List<District> getDistrictsComp() {
        return districtsComp;
    }

    public void setDistrictsComp(List<District> districtsComp) {
        this.districtsComp = districtsComp;
    }

    public boolean isReenvio() {
        return reenvio;
    }

    public void setReenvio(boolean reenvio) {
        this.reenvio = reenvio;
    }

    public boolean isValidCompany() {
        return validCompany;
    }

    public void setValidCompany(boolean validCompany) {
        this.validCompany = validCompany;
    }

    public void inputPhones(boolean b) {
        if (b) {
            this.phone = false;
            this.cellphone = false;
        }
    }

    //Add company's contact information
    public void addComContact() {

        this.contact.setPerson(this.personCom);
        this.personCom.getContacts().add(this.contact);
        this.contact = new Contact();
    }

    public void deleteContact(int i) {
        personCom.getContacts().remove(i);
    }

    public void savePersonCompany() {
        if (personCom.getContacts().isEmpty()) {
            MessageUtility.callError("nocontacts");
        } else {
            try {
                this.individualCompany.setIndividual(person.getIndividual());
                this.individualCompany.setCompany(personCom.getCompany());
                personCom.getCompany().getIndiCompanys().add(individualCompany);
                this.dao.setClasse(Person.class);
                this.dao.save(this.personCom);
                MessageUtility.callInfo("sucess");
                this.cleanPersonCadastre();
            } catch (Exception e) {
                MessageUtility.callError("error");
            }
        }
    }

    public void validateCompany() {
        String temp = this.personCom.getCompany().getCnpj().replaceAll("\\.", "");
        temp = temp.replaceAll("/", "");
        temp = temp.replaceAll("-", "");
//        this.company.setCnpj(temp);
        if (CNP.isValidCNPJ(temp)) {
            Person p = (Person) dao.getSession().createCriteria(Person.class)
                    .createAlias("company", "com")
                    .add(Restrictions.eq("com.cnpj", this.personCom.getCompany().getCnpj())).uniqueResult();

            if (p != null) {
                validCompany = false;
                disableFields = true;
                String message = "A empresa de CNPJ " + p.getCompany().getCnpj() + " já está cadastrada no sistema.";
                FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_WARN, message, message);
                FacesContext facesCtx = FacesContext.getCurrentInstance();
                facesCtx.addMessage(null, facesMsg);
            } else {
                validCompany = true;
                disableFields = false;
            }
        } else {
            this.company.setCnpj("");
            MessageUtility.callWarn("infalidcnpj");
            validCompany = true;
            disableFields = true;
        }
    }

    public void cleanPersonCadastre() {
        this.personCom = new Person();
        this.personCom.setCompany(new Company());
        this.contact = new Contact();
        this.disableFields = true;
        this.disableCityComp = true;
        this.disableDistrictComp = true;
        this.disableStateComp = true;
    }

    public List<Country> getCountries() {
        List<Country> countries = dao.getSession().createCriteria(Country.class).list();
        return countries;
    }

    public List<Sector> getSectors() {
        List<Sector> sectors = dao.getSession().createCriteria(Sector.class).list();
        return sectors;
    }

    public List<PositionInCompany> getPositions() {
        List<PositionInCompany> position = dao.getSession().createCriteria(PositionInCompany.class).list();
        return position;
    }

    //load the address information.
    public void loadStates() {
        this.disableStateComp = false;
        this.disableCityComp = true;
        this.disableDistrictComp = true;
        this.statesComp = dao.getSession().createCriteria(States.class)
                .add(Restrictions.eq("country", this.personCom.getAddress().getCountry())).list();
        this.citiesComp = new ArrayList<>();
    }

    public void loadCities() {
        this.disableCityComp = false;
        this.disableDistrictComp = true;
        this.citiesComp = dao.getSession().createCriteria(City.class)
                .add(Restrictions.eq("state", this.personCom.getAddress().getState())).list();
        this.districtsComp = new ArrayList<>();
    }

    public void loadDistricts() {
        this.disableDistrictComp = false;
        this.districtsComp = dao.getSession().createCriteria(District.class)
                .add(Restrictions.eq("city", this.personCom.getAddress().getCity())).list();
    }

    //carrega portes
    public void loadCompanySize() {
        this.comSizes = dao.getSession().createCriteria(CompanySize.class)
                .add(Restrictions.eq("sector", this.personCom.getCompany().getSector())).list();
    }

    public void loadCnaes() {
        this.cnaes = dao.getSession().createCriteria(Cnae.class)
                .add(Restrictions.eq("sector", this.personCom.getCompany().getSector())).list();
    }

    // load user's login information
    public Login loadLogin() {
        String t = "";
        SecurityContext context = SecurityContextHolder.getContext();
        if (context instanceof SecurityContext) {
            Authentication authentication = context.getAuthentication();
            if (authentication instanceof Authentication) {
                try {
                    t = ((Login) Contexto.getSessao().getAttribute("search")).getUsername();
                } catch (NullPointerException e) {
                    t = ((User) authentication.getPrincipal()).getUsername();
                }
            }
        }

        Login l = (Login) this.dao.getSession().createCriteria(Login.class).add(Restrictions.eq("username", t)).uniqueResult();

        return l;
    }

}
