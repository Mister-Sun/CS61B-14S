/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Beans;

import Cielo.Xml;
import Util.CieloUtil;
import java.io.IOException;
import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.MultiThreadedHttpConnectionManager;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.lang.time.StopWatch;
import org.jdom.Attribute;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.output.XMLOutputter;

/**
 *
 * @author EBI
 */

@ManagedBean(name = "cielo")
@ViewScoped
public class CieloService implements Serializable{
    
    private Xml xml;
    
    private static final int _1_SEGUNDO = 1000;
	
    private static final int _CONNECTION_TIMEOUT = 10 * _1_SEGUNDO;

    private static final int _READ_TIMEOUT = 40 * _1_SEGUNDO;
    
    public CieloService(){
        this.xml = new Xml();
    }

    public Xml getXml() {
        return xml;
    }

    public void setXml(Xml xml) {
        this.xml = xml;
    }
    
    public String getValidityDate(){
        String[] array = this.xml.getOwner().getCardValidity().split("/");
        return array[1]+array[0];
    }
    
    
    
    public void teste(){
        this.send2();
    }
    
    public String mensagem(){
        Document doc = new Document();
        
        Element root = new Element("requisicao-transacao");
        root.setAttribute(new Attribute("id","a97ab62a-7956-41ea-b03f-c2e9f612c293"));
        root.setAttribute(new Attribute("versao","1.2.1"));
        
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
        dpe.addContent(new Element("data-hora").setText("2011-12-07T11:43:37"));
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

        root.addContent(dadosec);
        root.addContent(dpo);
        root.addContent(dpe);
        root.addContent(fp);
        root.addContent(ur);
        root.addContent(aut);
        root.addContent(cap);
        //root.addContent(cl);
        //root.addContent(bin);
        //root.addContent(gt);

        doc.setRootElement(root);
         
        XMLOutputter xout = new XMLOutputter();
        return xout.outputString(doc);
//        OutputStream out = new FileOutputStream( new File("C:\\Users\\EBI\\Documents\\Fax\\teste.xml"));
//        xout.output(doc , out);
    }
    
    public void send2(){
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

                String respostaXml = httpMethod.getResponseBodyAsString();
                CieloUtil cielo = new CieloUtil();
                
                
                stopWatch.stop();
                System.out.println(respostaXml);
                

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
                
        } 
        catch (HttpException e) {
//                logger.error(e, e);
//                throw new FalhaComunicaoException(e.getMessage());
        } 
        catch (IOException e) {
//                logger.error(e, e);
//                throw new FalhaComunicaoException(e.getMessage());
        }
        finally {
                httpMethod.releaseConnection();
        }
    }
}
