/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Util;

import java.io.StringReader;
 
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
 
import org.w3c.dom.Document;
import org.xml.sax.InputSource;

/**
 *
 * @author EBI
 */
public class CieloUtil {
    private Document doc;
    
    public CieloUtil(){
    }
    
    public CieloUtil(String xml){
        this.convertStringToDocument(xml);
    }

    public Document getDoc() {
        return doc;
    }

    public void setDoc(Document doc) {
        this.doc = doc;
    }
    
    public String getTID(){
        return this.doc.getElementsByTagName("tid").item(0).getTextContent();
    }
    
    public void convertStringToDocument(String xmlStr) {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();  
        DocumentBuilder builder;  
        try 
        {  
            builder = factory.newDocumentBuilder();  
            this.doc = builder.parse( new InputSource( new StringReader( xmlStr ) ) ); 
            //System.out.println(doc.getElementsByTagName("tid").item(0).getTextContent()+"--------------");
            
        } catch (Exception e) {  
            e.printStackTrace();  
        } 
        
    }
    
    
}
