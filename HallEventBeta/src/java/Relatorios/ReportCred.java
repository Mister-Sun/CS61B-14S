/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Relatorios;


import Entity.Credential;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletResponse;
import javax.swing.JOptionPane;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperPrintManager;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.view.JasperViewer;

/**
 *
 * @author ebi
 */
public class ReportCred {
    String name = "";
    long code;
    String type = "";
    long resgisteredId;

    public String getTypo() {
        return type;
    }
    
    public void setTypo(String type) {
        this.type = type;
    }

    public long getRegisteredId() {
        return resgisteredId;
    }

    public void setRegisteredId(long resgisteredId) {
        this.resgisteredId = resgisteredId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getCode() {
        return code;
    }

    public void setCode(long code) {
        this.code = code;
    }

   
    
    public static List<ReportCred>  getDadosCertificado() {
        List<ReportCred> reports = new ArrayList<ReportCred>();
        ReportCred report = new ReportCred();
        report.setName("Evento");
        report.setCode(new Long("112345678912"));
        reports.add(report);
        return reports;
    }

    public static void imprimeEtiqueta(Credential credencial) {

        List<ReportCred> reports = new ArrayList<ReportCred>();
        ReportCred report = new ReportCred();
        report.setName(credencial.getRegistered().getPerson().getIndividual().getName());
        report.setCode(new Long(credencial.getCode()));
        report.setRegisteredId(credencial.getRegistered().getId());
        reports.add(report);

        JRDataSource jrds = new JRBeanCollectionDataSource(reports);


        try {
//           
            JasperReport jasperReport = JasperCompileManager.compileReport("report/qrCodeMOdif.jrxml");
//            JasperReport jasperReport = JasperCompileManager.compileReport("report/credencialqr.jrxml");
            JasperPrint print = JasperFillManager.fillReport(jasperReport, null, jrds);
            //JasperViewer.viewReport(print, false);
            JasperPrintManager.printReport(print, false);

        } catch (JRException ex) {
            JOptionPane.showMessageDialog(null, "erro" + ex);
        }

    }
    
    public static void printCredential(Credential credential) {  
        try {  
            //System.out.println("entrou no visualizar relatorio");  
            
            //---------- gera o relatorio ----------  
            List<ReportCred> reports = new ArrayList<ReportCred>();
            ReportCred report = new ReportCred();
            report.setName(credential.getRegistered().getPerson().getIndividual().getName());
            report.setCode(new Long(credential.getCode()));
            report.setRegisteredId(credential.getRegistered().getId());
            reports.add(report);

            JRDataSource jrds = new JRBeanCollectionDataSource(reports);
        
            String caminho = FacesContext.getCurrentInstance().getExternalContext().getRealPath("") + File.separator + "report";
            JasperReport jasperReport = JasperCompileManager.compileReport(caminho + File.separator +"qrCodeMOdif.jrxml");
            JasperPrint print = JasperFillManager.fillReport(jasperReport, null, jrds);
            byte[] b = JasperExportManager.exportReportToPdf(print);   
  
            HttpServletResponse res = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();  
            res.setContentType("application/pdf");  
            //Código abaixo gerar o relatório e disponibiliza diretamente na página   
            res.setHeader("Content-disposition", "inline;filename=arquivo.pdf");  
            //Código abaixo gerar o relatório e disponibiliza para o cliente baixar ou salvar   
            //res.setHeader("Content-disposition", "attachment;filename=arquivo.pdf");  
            res.getOutputStream().write(b);  
            res.getCharacterEncoding();  
            FacesContext.getCurrentInstance().responseComplete();  
            System.out.println("saiu do visualizar relatorio");  
        } catch (NumberFormatException ex) {  
            ex.printStackTrace();  
        } catch (JRException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }  
    } 
}
