/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Beans;

import Cielo.Xml;
import DAO.DaoImpl;
import DAO.EventDaoImpl;
import Entity.Address;
import Entity.Event;
import Entity.Event_Event;
import Entity.Event_Registered;
import Entity.Login;
import Entity.PartiType;
import Entity.Payment;
import Entity.Person;
import Entity.Registered;
import Enums.ParticipationType;
import Util.Contexto;
import Util.MessageUtility;
import br.com.uol.pagseguro.domain.PaymentRequest;
import br.com.uol.pagseguro.enums.Currency;
import br.com.uol.pagseguro.enums.DocumentType;
import br.com.uol.pagseguro.enums.ShippingType;
import br.com.uol.pagseguro.exception.PagSeguroServiceException;
import br.com.uol.pagseguro.properties.PagSeguroConfig;
import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.MultiThreadedHttpConnectionManager;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.lang.time.StopWatch;
import org.hibernate.criterion.Restrictions;
import org.jdom.Attribute;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.output.XMLOutputter;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;

/**
 *
 * @author EBI
 */
@ManagedBean(name = "regEvents")
@SessionScoped
public class RegEventsService implements Serializable {

    private Person personCom;
    private Person person;
    //private List<Event> events;
    private List<Event> selectedEvents;
    private Registered registered;
    private long partiTypeId;
    
    private PartiType partiType;

    private String redirectURL;

    private Payment payment;

    private TreeNode root;

    private DaoImpl dao;

    private Xml xml;
    private String respostaXml = "";

    private String cnpj = "";

    private static final int _1_SEGUNDO = 1000;

    private static final int _CONNECTION_TIMEOUT = 10 * _1_SEGUNDO;

    private static final int _READ_TIMEOUT = 40 * _1_SEGUNDO;

    public RegEventsService() {
        this.root = new DefaultTreeNode("Root", null);
        this.personCom = new Person();
        this.person = new Person();
        this.registered = new Registered();
        this.selectedEvents = new ArrayList<>();
        this.dao = new EventDaoImpl();
        this.xml = new Xml();
        this.payment = new Payment();
        Login login = this.loadLogin();
        this.person = login.getPerson();
        this.partiType = new PartiType();
        this.setUpTree();
    }

    public long getPartiTypeId() {
        return partiTypeId;
    }

    public void setPartiTypeId(long partiTypeId) {
        this.partiTypeId = partiTypeId;
    }

    public PartiType getPartiType() {
        return partiType;
    }

    public void setPartiType(PartiType partiType) {
        this.partiType = partiType;
    }

    public Registered getRegistered() {
        return registered;
    }

    public void setRegistered(Registered registered) {
        this.registered = registered;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public String getRedirectURL() {
        return redirectURL;
    }

    public void setRedirectURL(String redirectURL) {
        this.redirectURL = redirectURL;
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public Person getPersonCom() {
        return personCom;
    }

    public void setPersonCom(Person personCom) {
        this.personCom = personCom;
    }

    public Payment getPayment() {
        return payment;
    }

    public void setPayment(Payment payment) {
        this.payment = payment;
    }

    public String getRespostaXml() {
        return respostaXml;
    }

    public void setRespostaXml(String respostaXml) {
        this.respostaXml = respostaXml;
    }

    public Xml getXml() {
        return xml;
    }

    public void setXml(Xml xml) {
        this.xml = xml;
    }

    public String getValidityDate() {
        String[] array = this.xml.getOwner().getCardValidity().split("/");
        return array[1] + array[0];
    }

    

    public List<Event> getSelectedEvents() {
        return selectedEvents;
    }

    public void setSelectedEvents(List<Event> selectedEvents) {
        this.selectedEvents = selectedEvents;
    }

    public TreeNode getRoot() {
        return root;
    }

    public void setRoot(TreeNode root) {
        this.root = root;
    }

    public void loadCompany() {
        String temp = this.cnpj;
        temp = temp.replaceAll("\\.", "");
        temp = temp.replaceAll("/", "");
        temp = temp.replaceAll("-", "");
        this.personCom = (Person) this.dao.getSession().createCriteria(Person.class).createAlias("company", "c").add(Restrictions.eq("c.cnpj", this.cnpj)).uniqueResult();
        if(this.personCom != null){
            registered.setCompany(this.personCom.getCompany());
        }
    }

    public void cleanCadastre() {
        this.cnpj = "";
        this.personCom = new Person();
        this.selectedEvents = new ArrayList<>();
        this.root = new DefaultTreeNode("Root", null);
        this.setUpTree();
   }

    public void validateEvent(Event e) {
        if (e.isRegister()) {
            if (e.isFull()) {
                String message = "O evento selecionado está lotado";
                e.setRegister(false);
                FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_WARN, message, message);
                FacesContext facesCtx = FacesContext.getCurrentInstance();
                facesCtx.addMessage(null, facesMsg);
            } else {
                if (this.selectedEvents.size() > 0) {
                    boolean pass = false;
                    for (Event ev : this.selectedEvents) {
                        Date eaIn = ev.getDates().get(0).getBeginDate();
                        Date eaFim = ev.getDates().get(ev.getDates().size() - 1).getEndDate();
                        Date ebIn = e.getDates().get(0).getBeginDate();
                        Date ebFim = e.getDates().get(e.getDates().size() - 1).getEndDate();

                        if (!(eaFim.before(ebIn) || ebFim.before(eaIn))) {

                            if ((eaFim.getHours() <= ebIn.getHours()) || (ebFim.getHours() <= eaIn.getHours())) {

                                if ((eaFim.getMinutes() <= ebIn.getMinutes()) || (ebFim.getMinutes() <= eaIn.getMinutes())) {
                                    pass = true;
                                }
                            }
                        } else {
                            pass = true;
                        }
                        if (!pass) {
                            String message = "Já existe um evento adicionado nesse mesmo horário";
                            e.setRegister(false);
                            FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_WARN, message, message);
                            FacesContext facesCtx = FacesContext.getCurrentInstance();
                            facesCtx.addMessage(null, facesMsg);
                            break;
                        }
                    }
                    if (pass) {
                        this.selectedEvents.add(e);
                    }
                } else {
                    this.selectedEvents.add(e);
                }
            }
        } else {
            this.selectedEvents.remove(e);
        }
        //System.out.println("==="+this.selectedEvents.size());
    }
    
    

    public void readNode(List<Event_Event> lee, DefaultTreeNode father) {

        for (Event_Event ee : lee) {
            Event son = ee.getSonEvent();
            if (son.getEventRegisteredList().size() >= son.getMaxQuantity()) {
                son.setFull(true);
            }
            DefaultTreeNode node = new DefaultTreeNode(son);
            father.getChildren().add(node);
            if (son.getSonEvents().size() > 0) {
                this.readNode(son.getSonEvents(), node);
            }
        }
    }

    public void setUpTree() {
        List<Event> eventForTree = this.dao.getSession().createCriteria(Event.class).add(Restrictions.eq("main", true)).list();

        for (Event e : eventForTree) {
            if (e.getEventRegisteredList().size() >= e.getMaxQuantity()) {
                e.setFull(true);
            }
            DefaultTreeNode node = new DefaultTreeNode(e);
            root.getChildren().add(node);
            if (e.getSonEvents().size() > 0) {
                this.readNode(e.getSonEvents(), node);
            }
        }
    }

    public String mensagem() {

        Calendar c = new GregorianCalendar();
        c.setTime(new Date());
        SimpleDateFormat out = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat out2 = new SimpleDateFormat("HH:mm:ss");
        String time = out.format(c.getTime()) + "T" + out2.format(c.getTime());

        Document doc = new Document();

        Element rootE = new Element("requisicao-transacao");
        rootE.setAttribute(new Attribute("id", "a97ab62a-7956-41ea-b03f-c2e9f612c293"));
        rootE.setAttribute(new Attribute("versao", "1.2.1"));

        Element dadosec = new Element("dados-ec");
        dadosec.addContent(new Element("numero").setText("1006993069")); // numero do cliente
        dadosec.addContent(new Element("chave").setText("25fbb99741c739dd84d7b06ec78c9bac718838630f30b112d033ce2e621b34f3")); //chave cliente

        Element dpo = new Element("dados-portador");
        dpo.addContent(new Element("numero").setText("4012001037141112"));
        //dpo.addContent(new Element("numero").setText(this.xml.getOwner().getCardNumber()));
        dpo.addContent(new Element("validade").setText("201805"));
        //dpo.addContent(new Element("validade").setText(this.getValidityDate()));
        dpo.addContent(new Element("indicador").setText("1"));
        dpo.addContent(new Element("codigo-seguranca").setText("123"));
        //dpo.addContent(new Element("token").setText(""));

        Element dpe = new Element("dados-pedido");
        dpe.addContent(new Element("numero").setText("178148599"));
        dpe.addContent(new Element("valor").setText("1500"));
        dpe.addContent(new Element("moeda").setText("986"));
        //dpe.addContent(new Element("data-hora").setText("2011-12-07T11:43:37"));
        dpe.addContent(new Element("data-hora").setText(time));
        dpe.addContent(new Element("descricao").setText("[origem:10.50.54.156]"));
        dpe.addContent(new Element("idioma").setText("PT"));
        dpe.addContent(new Element("soft-descriptor").setText("sdhbfksjhfs"));
        //dpe.addContent(new Element("taxa-embarque").setText(""));

        Element fp = new Element("forma-pagamento");
        fp.addContent(new Element("bandeira").setText("visa"));
        //fp.addContent(new Element("bandeira").setText(this.xml.getCardFlag().toLowerCase()));
        fp.addContent(new Element("produto").setText("1"));
        fp.addContent(new Element("parcelas").setText("1"));

        Element ur = new Element("url-retorno").setText("http://localhost:8080/Eventos_2.0/");
        Element aut = new Element("autorizar").setText("3");
        Element cap = new Element("capturar").setText("true");
        Element cl = new Element("campo-livre").setText("Informações extras");
        //Element bin = new Element("bin").setText("401200");
        //Element gt = new Element("gerar-token").setText("false");

        rootE.addContent(dadosec);
        rootE.addContent(dpo);
        rootE.addContent(dpe);
        rootE.addContent(fp);
        rootE.addContent(ur);
        rootE.addContent(aut);
        rootE.addContent(cap);
        //root.addContent(cl);
        //root.addContent(bin);
        //root.addContent(gt);

        doc.setRootElement(rootE);

        XMLOutputter xout = new XMLOutputter();
        return xout.outputString(doc);
//        OutputStream out = new FileOutputStream( new File("C:\\Users\\EBI\\Documents\\Fax\\teste.xml"));
//        xout.output(doc , out);
    }

    public void sendXml() {
        String mensagemXml = this.mensagem();
        HttpClient httpClient = new HttpClient();
        httpClient.setHttpConnectionManager(new MultiThreadedHttpConnectionManager());
        httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(_CONNECTION_TIMEOUT);
        httpClient.getHttpConnectionManager().getParams().setSoTimeout(_READ_TIMEOUT);
        httpClient.getHttpConnectionManager().closeIdleConnections(_1_SEGUNDO);
        PostMethod httpMethod = new PostMethod("https://qasecommerce.cielo.com.br/servicos/ecommwsec.do");
        httpMethod.addParameter("mensagem", mensagemXml);

//        if (logger.isDebugEnabled()) {
//                logger.debug("Destino: '" + destino.getUrl() + "'\nMensagem: \n" + mensagemXml);
//        }
        try {
            StopWatch stopWatch = new StopWatch();
            stopWatch.start();

            httpClient.executeMethod(httpMethod);

            this.respostaXml = httpMethod.getResponseBodyAsString();

            stopWatch.stop();
            System.out.println(this.respostaXml);

//                RegistroTempoProcessamento.registrar(stopWatch);
//
//                if (logger.isDebugEnabled()) {
//                        logger.debug("Retorno [em " + stopWatch + ", id='" + mensagem.getId() + "']: \n" + respostaXml);
//                }
//                Resposta resposta = RespostaFactory.getInstance().criar(respostaXml);
//
//                if (resposta.getId() != null && ! mensagem.getId().equals(resposta.getId())) {
//                        throw new IllegalArgumentException("Resposta inválida: idRecebido='" + resposta.getId()
//                                        + "', idEnviado='" + mensagem.getId() + "'.");
//                }
//
//                if (resposta instanceof Erro) {
//                        Erro erro = (Erro) resposta;
//                        throw new RequisicaoInvalidaException(erro);
//                }
//
//                Transacao transacao = (Transacao) resposta;
//
//                return transacao;
        } catch (HttpException e) {
//                logger.error(e, e);
//                throw new FalhaComunicaoException(e.getMessage());
        } catch (IOException e) {
//                logger.error(e, e);
//                throw new FalhaComunicaoException(e.getMessage());
        } finally {
            httpMethod.releaseConnection();
        }
    }

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

    public String registerPayment() {
//        this.sendXml();
//        CieloUtil cielo = new CieloUtil(this.respostaXml);
        
        //System.out.println("jhkj"+login.getPerson().getIndividual().getName());
        Registered registered = new Registered();
        registered.setPerson(this.person);
        registered.setRegisteredType(this.person.getRegisteredType());
//        p.setRegistered(registered);

        List<Event_Registered> erl = new ArrayList<>();
        
        for (Event e : this.selectedEvents) {
            Event_Registered er = new Event_Registered();
            er.setEvent(e);
            er.setParticipationType(ParticipationType.PARTICIPANTE);
            er.setRegistered(registered);
            er.setIsPresent(false);
            er.setPayment(null);
            PartiType pt = new PartiType();
            pt = (PartiType) dao.getSession().createCriteria(PartiType.class).add(Restrictions.eq("id", partiTypeId)).uniqueResult();
            pt.getEventRegistereds().add(er);
            er.setPartiType(pt);
            erl.add(er);
            System.out.println("Participation Type ID:  " + partiTypeId);
            System.out.println("Local var ID: " + pt.getId() + pt.getName());
        }
        registered.setEventRegisteredList(erl);
        
//     String redirectURL = "/signUpSuccess.htm";
        if (!erl.isEmpty()) {
            redirectURL = createPaymentPagSeguro(erl);
        }

//        this.payment.setRegistered(registered);
//        this.payment.setTid(cielo.getTID());
        try {
            this.dao.setClasse(Registered.class);
            this.dao.save(registered);
//           this.dao.setClaredsse(Payment.class);
//           this.dao.save(this.payment);

           //sendEmail("Confirmação de Inscrição", this.person
            //        , this.person.getContacts().get(0).getEmail(), "ParametroNãoUtilizado");
            MessageUtility.callInfo("sucess");
//           return "/signUpSuccess.htm";
            System.out.println("redirectURL-------------:" + redirectURL);
//           if(redirectURL!=null)
            //FacesContext.getCurrentInstance().getExternalContext().dispatch(redirectURL);
            //FacesContext.getCurrentInstance().getResponseWriter().append(redirectURL);
            //FacesContext.getCurrentInstance().getExternalContext()
            return "/signUpSuccess.htm";
        } catch (Exception e) {
            MessageUtility.callError("error");
            return null;
        }
    }

    public String createPaymentPagSeguro(List<Event_Registered> erl) {
//        if (erl.isEmpty()){ 
//            return ""; 
//        }
//        

        PaymentRequest paymentRequest = new PaymentRequest();
        Payment payment = new Payment();
        String paymentURL = new String();

        payment.setTid(String.valueOf(payment.hashCode()));

        for (Event_Registered registeredEvent : erl) {
            Event evt = registeredEvent.getEvent();

            paymentRequest.addItem(evt.getId().toString(), evt.getName(), 1, new BigDecimal(evt.getCost() + ".00"), 1L, new BigDecimal("1.00"));

        }

        Address address = erl.get(0).getRegistered().getPerson().getAddress();
        paymentRequest.setShippingAddress("BRA", //s
                address.getState().getAcronym(), //
                address.getCity().getName(), //
                address.getDistrict().getName(), //
                address.getCep(), //
                address.getStreet(), //
                address.getNumber(), //
                address.getComplement());

        paymentRequest.setShippingType(ShippingType.SEDEX);

        paymentRequest.setShippingCost(new BigDecimal("0.00"));

        paymentRequest.setSender("Vitor Alves", //
                "vitor.molotov@gmail.com", //
                "91", //
                "91641767", //
                DocumentType.CPF, //
                "948.825.922-15");

        paymentRequest.setCurrency(Currency.BRL);

        paymentRequest.setReference(payment.getTid());

        paymentRequest.setNotificationURL("http://www.ebi.com.br/");

        paymentRequest.setRedirectURL("http://www.ebi.com.br/");

        // Another way to set checkout parameters
        paymentRequest.addParameter("senderBornDate", //
                "01/11/1988");

        try {

            Boolean onlyCheckoutCode = false;

            // Set your account credentials on src/pagseguro-config.properties
            paymentURL = paymentRequest.register(PagSeguroConfig.getAccountCredentials(), onlyCheckoutCode);

            System.out.println(paymentURL);

        } catch (PagSeguroServiceException e) {
            System.err.println(e.getMessage());
        }
        return paymentURL;

    }
    
    public List<PartiType> loadPartiTypes(Event event){
        List<PartiType> partiTypes = new ArrayList<>();
        partiTypes = dao.getSession().createCriteria(PartiType.class).add(Restrictions.eq("event", event)).list();
        return partiTypes;
    }

}
