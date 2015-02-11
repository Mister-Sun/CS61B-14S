/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Cielo;

import java.io.IOException;
import org.jdom.Attribute;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.output.XMLOutputter;



/**
 *
 * @author EBI
 */
public class CieloManager {
    
    public void send(){
        
    }
    
    public void directTransaction(Xml xml) throws IOException{
        
        
        Document doc = new Document();
        
        Element root = new Element("requisicao-transacao");
        root.setAttribute(new Attribute("id","a97ab62a-7956-41ea-b03f-c2e9f612c293"));
        root.setAttribute(new Attribute("versao","1.2.1"));
        
        Element dadosec = new Element("dados-ec");
        dadosec.addContent(new Element("numero").setText("1006993069"));
        dadosec.addContent(new Element("chave").setText("25fbb99741c739dd84d7b06ec78c9bac718838630f30b112d033ce2e621b34f3"));

        Element dpo = new Element("dados-portador");
        dpo.addContent(new Element("numero").setText(xml.getOwner().getCardNumber()));
        dpo.addContent(new Element("validade").setText(xml.getOwner().getCardValidity()));
        dpo.addContent(new Element("indicador").setText(xml.getOwner().getIndicator()));
        dpo.addContent(new Element("codigo-seguranca").setText(xml.getOwner().getSecurityCode()));
        //dpo.addContent(new Element("token").setText(""));
        
        Element dpe = new Element("dados-pedido");
        dpe.addContent(new Element("numero").setText(xml.getRequest().getNumber()));
        dpe.addContent(new Element("valor").setText(xml.getRequest().getValue()));
        dpe.addContent(new Element("moeda").setText(xml.getRequest().getCurrency()));
        dpe.addContent(new Element("data-hora").setText(xml.getRequest().getDateTime()));
        dpe.addContent(new Element("descricao").setText(xml.getRequest().getDescription()));
        dpe.addContent(new Element("indioma").setText(xml.getRequest().getLanguage()));
        dpe.addContent(new Element("soft-descriptor").setText(xml.getRequest().getSoftDescriptor()));
        //dpe.addContent(new Element("taxa-embarque").setText(""));
        
        Element fp = new Element("forma-pagamento");
        fp.addContent(new Element("bandeira").setText(xml.getCardFlag()));
        fp.addContent(new Element("produto").setText(xml.getProduct()));
        fp.addContent(new Element("parcelas").setText(xml.getPlots()));
        
        Element ur = new Element("url-retorno").setText(xml.getUrlRetorno());
        Element cap = new Element("capturar").setText(xml.getCapture());
        Element cl = new Element("campo-livre").setText(xml.getCampoLivre());
        //Element bin = new Element("bin").setText(xml.getBin());
        //Element gt = new Element("gerar-token").setText("false");

        root.addContent(dadosec);
        root.addContent(dpo);
        root.addContent(dpe);
        root.addContent(fp);
        root.addContent(ur);
        root.addContent(cap);
        root.addContent(cl);
        //root.addContent(bin);
        //root.addContent(gt);
        
        doc.setRootElement(root);
        System.out.println(root.getText()+"======");
        XMLOutputter xout = new XMLOutputter();
        xout.outputString(doc);
//        
//        OutputStream out = new FileOutputStream( new File("C:\\Users\\EBI\\Documents\\Fax\\teste.xml"));
//        xout.output(doc , out);
    }
}
