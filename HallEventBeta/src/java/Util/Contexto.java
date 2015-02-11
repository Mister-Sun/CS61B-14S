/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Util;
import java.io.IOException;
import java.io.Serializable;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;



public class Contexto implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final String USUARIO = "session_user";


	public Contexto() {
	}
	
	public static String getUsuario() {
		return USUARIO;
	}


	public static HttpSession getSessao() {
		return (HttpSession) FacesContext.getCurrentInstance()
				.getExternalContext().getSession(true);
	}
	public static void invalidaSessao(String pagina){
		HttpServletRequest request = (HttpServletRequest) getExternalContext().getRequest();
		Contexto.getSessao().setAttribute("session_user", null);
		HttpSession sessao = (HttpSession) request.getSession();
		sessao.invalidate();
		try{
			getExternalContext().redirect(request.getContextPath()+"/"+pagina);
		}catch(IOException e){
			e.fillInStackTrace();
		}
	
	}
//	public static Usuario getUsuario_Sessao(){
//		Usuario userSession = ((Usuario) getSessao().getAttribute("session_user"));
//		return userSession;
//	}
	
	public static HttpServletRequest getRequest(){
		return (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
	}

	public static HttpServletResponse getResponse() {
		return (HttpServletResponse) FacesContext.getCurrentInstance()
				.getExternalContext().getResponse();
	}

	public static ExternalContext getExternalContext() {
		return FacesContext.getCurrentInstance().getExternalContext();
	}

	public static String getPath(String caminho) {
		ServletContext request = (ServletContext) FacesContext
				.getCurrentInstance().getExternalContext().getContext();
		return request.getRealPath(caminho);

	}
	
	public static String gerURL(String caminho){
		FacesContext aFacesContext = FacesContext.getCurrentInstance();
		ServletContext context = (ServletContext) aFacesContext.getExternalContext().getContext();
		String realPath = context.getRealPath("/");
		return realPath + caminho;
	}

}
