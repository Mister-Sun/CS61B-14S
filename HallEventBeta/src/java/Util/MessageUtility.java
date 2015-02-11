/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Util;

import java.io.Serializable;
import java.util.ResourceBundle;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

/**
 *
 * @author thiagovulcao
 */
public class MessageUtility implements Serializable {

    private static ResourceBundle bundle = ResourceBundle.getBundle("messages",
            FacesContext.getCurrentInstance().getViewRoot().getLocale());

    public static void callError(String msg) {
        String message = bundle.getString(msg);
        FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, message, message);
        FacesContext facesCtx = FacesContext.getCurrentInstance();
        facesCtx.addMessage(null, facesMsg);
    }

    public static void callInfo(String msg) {
        String message = bundle.getString(msg);
        FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_INFO, message, message);
        FacesContext facesCtx = FacesContext.getCurrentInstance();
        facesCtx.addMessage(null, facesMsg);
    }

    public static void callWarn(String msg) {
        String message = bundle.getString(msg);
        FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_WARN, message, message);
        FacesContext facesCtx = FacesContext.getCurrentInstance();
        facesCtx.addMessage(null, facesMsg);
    }

    public static void callSystemError(String msg) {
        String message = bundle.getString(msg);
        FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, message, message);
        FacesContext facesCtx = FacesContext.getCurrentInstance();
        facesCtx.addMessage(null, facesMsg);
    }
}
