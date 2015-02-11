/*
 * This class contains for individuals registery
 * Serves as managed bean for formCadParticipante.xhtml
 * Integrate facebook information loading method.(09.17.2014)
 * Credential input and validate method added to this bean.(09.25.2014)
 * Last revision Sept, 25, 2014 by Sun 
 */
package Beans;

import DAO.DaoImpl;
import DAO.EventDaoImpl;
import Entity.City;
import Entity.Cnae;
import Entity.CompanySize;
import Entity.Contact;
import Entity.Country;
import Entity.District;
import Entity.Event;
import Entity.Event_Registered;
import Entity.FriendInvited;
import Entity.FriendInvitedId;
import Entity.Individual;
import Entity.Login;
import Entity.NumEventsAttended;
import Entity.NumEventsCreated;
import Entity.Person;
import Entity.Registered;
import Entity.States;
import Enums.Gender;
import Enums.ParticipationType;
import Util.CNP;
import Util.Contexto;
import static Util.EmailService.sendEmail;
import Util.MessageUtility;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.mail.EmailException;
import org.hibernate.criterion.Restrictions;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.GrantedAuthorityImpl;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 *
 * @author EBI
 */
@ManagedBean(name = "cadPerson")
@ViewScoped
public class CadPersonService implements Serializable {

    private DaoImpl dao;

    private String fbName;
    private String fbGender;
    private String fbEmail;
    private String fbId;

    private String invitorId;

    private String credential;
    private Event event;

    private Person person;
    private Person invitor;

    private NumEventsCreated numEventsCreated;
    private NumEventsAttended numEventsAttended;

    private Contact contact;

    private List<States> states;
    private List<City> cities;
    private List<District> districts;

    //private List<Sector> sectors = new ArrayList<>();
    private List<CompanySize> comSizes = new ArrayList<>();
    private List<Cnae> cnaes = new ArrayList<>();

    private boolean disableState = true;
    private boolean disableCity = true;
    private boolean disableDistrict = true;

    private boolean reenvio = false;
    private boolean validPerson = false;

    private boolean phone = true;
    private boolean cellphone = true;
    private boolean disableFields = true;

    public CadPersonService() {
        this.person = new Person();
        this.invitor = new Person();
        this.person.setIndividual(new Individual());
        //this.event = new Event();
        this.contact = new Contact();
        this.dao = new EventDaoImpl();
        this.fbName = new String();
        this.fbGender = new String();
        this.fbEmail = new String();
        this.credential = new String();
        this.invitorId = new String();
        HttpServletRequest request = (HttpServletRequest) FacesContext
                .getCurrentInstance().getExternalContext().getRequest();
        this.setInvitorId(request.getParameter("uid"));
        //this.company = new Company();
    }

    @PostConstruct
    public void initMyBean() {
        /**
         * This map contains all the params you submitted from the html form
         */
        Map<String, String> requestParams = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        if (requestParams != null) {
        }
    }

    public String getInvitorId() {
        return invitorId;
    }

    public void setInvitorId(String invitorId) {
        this.invitorId = invitorId;
    }

    public String getFbId() {
        return fbId;
    }

    public void setFbId(String fbId) {
        this.fbId = fbId;
    }

    public String getCredential() {
        return credential;
    }

    public void setCredential(String credential) {
        this.credential = credential;
    }

    public String getFbName() {
        return fbName;
    }

    public void setFbName(String fbName) {
        this.fbName = fbName;
    }

    public String getFbGender() {
        return fbGender;
    }

    public void setFbGender(String fbGender) {
        this.fbGender = fbGender;
    }

    public String getFbEmail() {
        return fbEmail;
    }

    public void setFbEmail(String fbEmail) {
        this.fbEmail = fbEmail;
    }

    public boolean isDisableFields() {
        return disableFields;
    }

    public void setDisableFields(boolean disableFields) {
        this.disableFields = disableFields;
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

    public boolean isValidPerson() {
        return validPerson;
    }

    public void setValidPerson(boolean validPerson) {
        this.validPerson = validPerson;
    }

    public boolean isReenvio() {
        return reenvio;
    }

    public void setReenvio(boolean reenvio) {
        this.reenvio = reenvio;
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

    public void inputPhones(boolean b) {
        if (b) {
            this.phone = false;
            this.cellphone = false;
        }
    }

    public void deleteContact(int i) {
        person.getContacts().remove(i);
    }

    public void addContact() {
        this.contact.setPerson(this.person);
        this.person.getContacts().add(this.contact);
        this.contact = new Contact();

    }

    public boolean searchUser() {
        this.dao.setClasse(Login.class);
        Login login = (Login) this.dao.getSession().createCriteria(Login.class).add(Restrictions.eq("username", this.person.getLogin().getUsername())).uniqueResult();
        if (login != null) {
            return true;
        }
        return false;
    }

    public String savePerson() {
        if (this.searchUser()) {
            MessageUtility.callError("invaliduser");
            return null;
        } else {
            this.person.getLogin().setPerson(this.person);
            this.numEventsCreated = new NumEventsCreated();
            this.numEventsAttended = new NumEventsAttended();
            this.numEventsAttended.setNumOfAttendance(0);
            this.numEventsCreated.setNumOfCreations(0);
            this.numEventsCreated.setPerson(this.person);
            this.numEventsAttended.setPerson(this.person);
            this.person.setNumEventsAttended(numEventsAttended);
            this.person.setNumEventCreated(numEventsCreated);
            
            setInvitor();
            
            try {

                if ((!credential.isEmpty())) {
                    if (!StringUtils.isNumeric(credential)) {
                        MessageUtility.callError("invalidcode");
                        return null;
                    }
                    Long longCredential = Long.parseLong(credential);
                    this.event = (Event) dao.getSession().createCriteria(Event.class).add(Restrictions.eq("code", longCredential)).uniqueResult();
                    if (event != null && event.isIsPrivate()) {
                        this.dao.setClasse(Person.class);
                        Long id = this.dao.save(this.person);
                        validateCredential();
                        Contexto.getSessao().setAttribute("search", this.person.getLogin());
                        login(Contexto.getRequest(), person.getLogin().getUsername(), person.getLogin().getPassword());
                        this.person.setId(id);
                        sendEmail("Confirmação de Inscrição", this.person, this.person.getLogin().getUsername(), "ParametroNãoUtilizado");
                        MessageUtility.callInfo("sucess");
                        this.cleanPersonCadastre();
                        return "/signUpSuccess.htm";
                    } else {
                        MessageUtility.callError("invalidcode");
                    }
                } else {
                    this.dao.setClasse(Person.class);
                    Long id = this.dao.save(this.person);
                    Contexto.getSessao().setAttribute("search", this.person.getLogin());
                    login(Contexto.getRequest(), person.getLogin().getUsername(), person.getLogin().getPassword());
                    this.person.setId(id);
                    sendEmail("Confirmação de Inscrição", this.person, this.person.getLogin().getUsername(), "ParametroNãoUtilizado");
                    MessageUtility.callInfo("sucess");
                    this.cleanPersonCadastre();
                    return "/signUpSuccess.htm";
                }
                return null;
            } catch (IOException | EmailException e) {
                MessageUtility.callError("error");
                return null;
            }
        }
    }

    public void setInvitor() {
        if (invitorId != null) {
            Long Id = Long.parseLong(invitorId);
            this.invitor = (Person) dao.getSession().createCriteria(Person.class).add(Restrictions.eq("id", Id)).uniqueResult();
            FriendInvited fi = new FriendInvited();
            fi.setPersonInvitor(invitor);
            fi.setPersonInvited(person);
            this.person.getInvitors().add(fi);
            this.invitor.getInviteds().add(fi);
            //this.dao.update(invitor);
        }
    }

    //This method is to validate the credential input, and sign the user to the designated event if the credential is valid.
    public void validateCredential() {
        Registered registered = new Registered();
        registered.setPerson(this.person);
        registered.setRegisteredType(this.person.getRegisteredType());
        List<Event_Registered> erl = new ArrayList<>();
        Event_Registered er = new Event_Registered();
        er.setEvent(event);
        er.setParticipationType(ParticipationType.PARTICIPANTE);
        er.setRegistered(registered);
        er.setIsPresent(false);
        er.setPayment(null);
        erl.add(er);
        registered.setEventRegisteredList(erl);
        this.dao.setClasse(Registered.class);
        this.dao.save(registered);
    }

    public void login(HttpServletRequest request, String userName, String password) {

        UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(userName, password);

        // Authenticate the user
        AuthenticationManager am = new AuthenticationManager() {

            @Override
            public Authentication authenticate(Authentication authentication) throws AuthenticationException {
                String name = authentication.getName();
                String password = authentication.getCredentials().toString();

                List<GrantedAuthority> grantedAuths = new ArrayList<>();
                grantedAuths.add(new GrantedAuthorityImpl("TESTE"));
                Authentication auth = new UsernamePasswordAuthenticationToken(name, password, grantedAuths);
                return auth;

            }

        };

        Authentication authentication = am.authenticate(authRequest);

        //.authenticate(authRequest)
        SecurityContext securityContext = SecurityContextHolder.getContext();
        securityContext.setAuthentication(authentication);

        // Create a new session and add the security context.
        HttpSession session = request.getSession(true);
        session.setAttribute("SPRING_SECURITY_CONTEXT", securityContext);
    }

    public void deletePerson(Individual ind) {
        Person p = (Person) dao.getSession().createCriteria(Person.class).add(Restrictions.eq("individual", ind)).uniqueResult();

        p.setActive(false);

        try {
            this.dao.update(p);
            this.cleanPersonCadastre();
            MessageUtility.callInfo("sucess");
        } catch (Exception e) {
            MessageUtility.callError("error");
        }
    }

    public void cleanPersonCadastre() {

        this.person = new Person();
        this.person.setIndividual(new Individual());
        this.reenvio = false;
        this.disableFields = true;
    }

    public String loadPart(Person p) {
        this.person = p;
        //this.person = (Person) dao.getSession().createCriteria(Person.class).add(Restrictions.eq("individual", i)).uniqueResult();

        return "/formAtualizarPart.htm";
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

    public void validatePerson() {
        Person p;

        if (CNP.isValidCPF(this.person.getIndividual().getCpf())) {
            p = (Person) dao.getSession().createCriteria(Person.class)
                    .createAlias("individual", "i")
                    .add(Restrictions.eq("i.cpf", this.person.getIndividual().getCpf())).uniqueResult();

            if (p != null) {
                MessageUtility.callWarn("already");
                disableFields = true;
                this.person = p;
                validPerson = false;
                reenvio = true;
                this.person = new Person();
                this.person.setIndividual(new Individual());
                this.person.getIndividual().setName(fbName);
                this.person.getLogin().setUsername(fbEmail);
                switch (fbGender) {
                    case "male":
                        this.person.getIndividual().setGender(Gender.MASCULINO);
                        break;
                    case "female":
                        this.person.getIndividual().setGender(Gender.FEMININO);
                        break;
                }
            } else {

                this.disableFields = false;
                validPerson = false;
                reenvio = false;

            }
        } else {

            this.person.getIndividual().setCpf("");
            MessageUtility.callWarn("invalidcpf");
            validPerson = true;
            reenvio = false;
            disableFields = true;
        }
    }

    public List<Country> getCountries() {
        List<Country> countries = dao.getSession().createCriteria(Country.class).list();
        return countries;
    }

    public void loadStates() {

        this.disableState = false;
        this.disableCity = true;
        this.disableDistrict = true;
        this.states = dao.getSession().createCriteria(States.class)
                .add(Restrictions.eq("country", this.person.getAddress().getCountry())).list();
        this.cities = new ArrayList<>();

    }

    public void loadCities() {

        this.disableCity = false;
        this.disableDistrict = true;
        this.cities = dao.getSession().createCriteria(City.class)
                .add(Restrictions.eq("state", this.person.getAddress().getState())).list();
        this.districts = new ArrayList<>();

    }

    public void loadDistricts() {

        this.disableDistrict = false;
        this.districts = dao.getSession().createCriteria(District.class)
                .add(Restrictions.eq("city", this.person.getAddress().getCity())).list();

    }

    public void loadFbInfo() {
        this.fbName = fbName.toUpperCase();
        this.fbEmail = fbEmail.toUpperCase();
        this.person.getIndividual().setName(fbName);
        this.person.getLogin().setUsername(fbEmail);
        this.person.setFbId(fbId);
        switch (fbGender) {
            case "male":
                this.person.getIndividual().setGender(Gender.MASCULINO);
                break;
            case "female":
                this.person.getIndividual().setGender(Gender.FEMININO);
                break;
        }
    }

}
