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
import Entity.Individual;
import Entity.Person;
import Entity.PositionInCompany;
import Entity.Sector;
import Entity.States;
import Util.CNP;
import static Util.EmailService.sendEmail;
import Util.MessageUtility;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import org.apache.commons.mail.EmailException;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author EBI
 */
@ManagedBean(name = "crudPerson")
@SessionScoped
public class PersonService {

    private DaoImpl dao;
    private Person person;
    private Person PersonComp;
    private Company company;
    private List<Individual> individualList = new ArrayList<>();
    private List<Person> personList = new ArrayList<>();

    private String search;
    private String searchType;

    private Contact contact;
    private Contact contactEdit;
    private List<Contact> contacts = new ArrayList<>();

    private List<States> states;
    private List<City> cities;
    private List<District> districts;

    //private List<Sector> sectors = new ArrayList<>();
    private List<CompanySize> comSizes = new ArrayList<>();
    private List<Cnae> cnaes = new ArrayList<>();

    private boolean disableStateComp = true;
    private boolean disableCityComp = true;
    private boolean disableDistrictComp = true;
    private List<States> statesComp;
    private List<City> citiesComp;
    private List<District> districtsComp;

    private boolean disableState = false;
    private boolean disableCity = false;
    private boolean disableDistrict = false;

    boolean validCompany = true;

    private boolean reenvio = false;
    private boolean addCompany = false;

    public PersonService() {
        this.person = new Person();
        this.PersonComp = new Person();
        this.company = new Company();
        this.contact = new Contact();
        this.dao = new EventDaoImpl();
        //this.company = new Company();
    }

    public List<Person> getPersonList() {
        return personList;
    }

    public void setPersonList(List<Person> personList) {
        this.personList = personList;
    }

    public Person getPersonComp() {
        return PersonComp;
    }

    public void setPersonComp(Person PersonComp) {
        this.PersonComp = PersonComp;
    }

    public boolean isAddCompany() {
        return addCompany;
    }

    public void setAddCompany(boolean addCompany) {
        this.addCompany = addCompany;
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

    public boolean isValidCompany() {
        return validCompany;
    }

    public void setValidCompany(boolean validCompany) {
        this.validCompany = validCompany;
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

    public boolean isDisableState() {
        return disableState;
    }

    public void setDisableState(boolean disableState) {
        this.disableState = disableState;
    }

    public boolean isDisableCity() {
        return disableCity;
    }

    public void setDisableCity(boolean disableCity) {
        this.disableCity = disableCity;
    }

    public boolean isDisableDistrict() {
        return disableDistrict;
    }

    public void setDisableDistrict(boolean disableDistrict) {
        this.disableDistrict = disableDistrict;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public List<Individual> getIndividualList() {
        return individualList;
    }

    public void setIndividualList(List<Individual> individualList) {
        this.individualList = individualList;
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

    public List<Contact> getContacts() {
        return contacts;
    }

    public void setContacts(List<Contact> contacts) {
        this.contacts = contacts;
    }

    public boolean isReenvio() {
        return reenvio;
    }

    public void setReenvio(boolean reenvio) {
        this.reenvio = reenvio;
    }

    public void bindCompany() {
        System.out.println("opirdthjuóij´dop[óhj´pd[eoh[ry");
        this.addCompany = true;
    }

    public List<Sector> getSectors() {
        List<Sector> sectors = dao.getSession().createCriteria(Sector.class).list();
        return sectors;
    }

    public void reenvioEmail() throws EmailException, IOException {
        Person p = this.person;
        Contact main = new Contact();
        if (p.getContacts().size() > 1) {
            for (Contact c : p.getContacts()) {
                if (c.isMain()) {
                    main = c;
                }
            }
        } else {
            main = p.getContacts().get(0);
        }

        sendEmail("Reconfirmação de Inscrição", p, main.getEmail(), "");

        String message = "Email de reconfirmação enviado";
        FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_INFO, message, message);
        FacesContext facesCtx = FacesContext.getCurrentInstance();
        facesCtx.addMessage(null, facesMsg);

    }

    public void loadCompanySize() {
        this.comSizes = dao.getSession().createCriteria(CompanySize.class)
                .add(Restrictions.eq("setor", this.company.getSector())).list();
    }

    public void loadCnaes() {
        this.cnaes = dao.getSession().createCriteria(Cnae.class)
                .add(Restrictions.eq("setor", this.company.getSector())).list();
    }

    public void addComContact() {
        this.company.getContacts().add(this.contact);
        this.contact = new Contact();
    }

    public void addPersonContact() {
        this.contact.setPerson(this.person);
        this.person.getContacts().add(this.contact);        
        dao.update(person);
        this.contact = new Contact();
    }

    public void validateCompany() {
        String temp = this.company.getCnpj().replaceAll("\\.", "");
        temp = temp.replaceAll("/", "");
        temp = temp.replaceAll("-", "");
        this.company.setCnpj(temp);
        if (CNP.isValidCNPJ(this.company.getCnpj())) {
            Person p = (Person) dao.getSession().createCriteria(Person.class)
                    .createAlias("company", "com")
                    .add(Restrictions.eq("com.cnpj", this.company.getCnpj())).uniqueResult();

            if (p != null) {
                this.PersonComp = p;
                validCompany = false;
                String message = "A empresa de CNPJ " + p.getCompany().getCnpj() + " já possui representantes cadastrados , incluir abaixo os dados de um novo representante da mesma.";
                FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_WARN, message, message);
                FacesContext facesCtx = FacesContext.getCurrentInstance();
                facesCtx.addMessage(null, facesMsg);
            } else {
                validCompany = true;
            }
        } else {
            this.company.setCnpj("");
            MessageUtility.callWarn("infalidcnpj");
            validCompany = true;
        }
    }

    public void searchIndividuals() {
        this.individualList = new ArrayList<>(); 
        this.personList = dao.getSession().createCriteria(Person.class).add(Restrictions.eq("active", false)).createCriteria("individual").add(Restrictions.like(searchType, search, MatchMode.ANYWHERE)).list();
        for(Person p:this.personList){
            this.individualList.add(p.getIndividual());
        }
    }

    public void deletePerson(Individual ind) {
        Person p = (Person) dao.getSession().createCriteria(Person.class).add(Restrictions.eq("individual", ind)).uniqueResult();

        p.setActive(true);

        try {
            this.dao.update(p);
            this.searchIndividuals();
            MessageUtility.callInfo("sucess");
        } catch (Exception e) {
            MessageUtility.callError("error");
        }
    }

    public void deletePartInterno(Individual ind) {

//        Person p = (Person) dao.getSession().createCriteria(Person.class).add(Restrictions.eq("individual", ind)).uniqueResult();
//        
//        int size = p.getRegistered().getEventRegisteredList().size();
//        this.dao.setClasse(Event_Registered.class);
//        for(int i=0;i<size;i++){
//            Event_Registered f = p.getRegistered().getEventRegisteredList().get(0);
//            f.setPk(null);
//            p.getRegistered().getEventRegisteredList().remove(f);
//            this.dao.destroy(f);
//        }
//        
//        try {
//            this.dao.destroy(p);
//            //this.cleanPartCadastre();
//            MessageUtility.callInfo("sucess");
//        } catch (Exception e) {
//            MessageUtility.callError("error");
//        }
    }

    public String loadPerson(Individual i) {
        this.person = (Person) dao.getSession().createCriteria(Person.class).add(Restrictions.eq("individual", i)).uniqueResult();
        return "/formAtualizarPart.htm";
    }

    public String updatePerson() {
        try {
            this.dao.setClasse(Person.class);
            this.dao.update(this.person);
            //this.cleanPartCadastre();
            MessageUtility.callInfo("sucess");
        } catch (Exception e) {
            MessageUtility.callError("error");
        }
        return "/formParticipante.htm";
    }

    public List<PositionInCompany> getPositions() {
        List<PositionInCompany> position = dao.getSession().createCriteria(PositionInCompany.class).list();
        return position;
    }

    public List<Country> getCountries(Country c) {
        List<Country> countries = dao.getSession().createCriteria(Country.class).list();
        if (this.person.getAddress().getCountry() != null) {
            this.loadStates();
        }
        if (this.person.getAddress().getState() != null) {
            this.loadCities();
        }
        if (this.person.getAddress().getCity() != null) {
            this.loadDistricts();
        }
//        if(c != null){
//            countries.set(0, c);
//        }
        return countries;
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

    public void updateIndiContact() {
        dao.update(contactEdit);
        this.contactEdit = new Contact();
    }

    public void deleteContact(Contact con) {
        person.getContacts().remove(con);
        dao.update(person);
        dao.destroy(con);
    }

}
