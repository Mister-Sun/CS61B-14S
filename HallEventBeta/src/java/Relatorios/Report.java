/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Relatorios;

import Entity.Event;
import Entity.Registered;
import Entity.Programa;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import javax.faces.context.FacesContext;
import javax.swing.JOptionPane;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperPrintManager;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.view.JasperViewer;

/**
 *
 * @author dbjaime
 */
public class Report {
    
    private String name;
    private String eventName;
    private String program;
    private String beginTime;
    private String endTime;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    

    public String getProgram() {
        return program;
    }

    public void setProgram(String program) {
        this.program = program;
    }

    public String getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(String beginTime) {
        this.beginTime = beginTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    
    
    public static void printCertification(Registered registered, Event event, Programa program) {
        System.out.println("priting");
        List<Report> reports = new ArrayList<Report>();
        Report report = new Report();
        report.setName(registered.getPerson().getIndividual().getName());
//        if(registered.getTipoPresenca() == PresencaTipo.PARTICIPANTE){
//            report.setTipo("Pessoa Fisica");
//        }
//        if(registered.getParticipante().getTpart() == TipoParticipante.ESTUDANTE){
//            report.setTipo(credencial.getInscrito().getParticipante().getEmpresa().getNomeFantasia());
//        }
        report.setEventName(event.getName());
        report.setProgram("programaisissisi");
//        report.setDataIn(evento.getDataEventoFormatada());
//        report.setDataFim(evento.getHoraFimFormatada());
        reports.add(report);

        JRDataSource jrds = new JRBeanCollectionDataSource(reports);


        try {
            String caminho = FacesContext.getCurrentInstance().getExternalContext().getRealPath("") + File.separator + "report";
            JasperReport jasperReport = JasperCompileManager.compileReport(caminho + File.separator +"certificado.jrxml");
//            JasperReport jasperReport = JasperCompileManager.compileReport("report/credencialqr.jrxml");
            JasperPrint print = JasperFillManager.fillReport(jasperReport, null, jrds);
            //JasperViewer.viewReport(print, true);
            JasperPrintManager.printReport(print, false);

        } catch (JRException ex) {
            JOptionPane.showMessageDialog(null, "erro" + ex);
        }

    }
    
    
    
}
