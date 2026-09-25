package com.mkyong.common;
 

import javax.faces.bean.ManagedBean;
import javax.ejb.EJB;

import javax.faces.bean.SessionScoped;

import mx.gob.imss.ctirss.infraestructura.dashboardservicios.modelo.negocio.Sistema;
import mx.gob.imss.ctirss.infraestructura.dashboardservicios.servicios.business.interfaces.ConsultaBusinessRemote;

import java.io.Serializable;
import java.util.List;
 
@ManagedBean
@SessionScoped
public class HelloBean implements Serializable {
 
	private static final long serialVersionUID = 1L;
 
	private String name;
	
	@EJB(mappedName="ejb/ConsultaBusiness")
	ConsultaBusinessRemote consulta;
	
	
	public String getName() {
		
		//ConsultaBusinessRemote consulta = (ConsultaBusinessRemote)ctx.lookup("java:global/classes/ConsultaBusiness!mx.gob.imss.ctirss.infraestructura.dashboardservicios.servicios.business.interfaces.ConsultaBusinessRemote");
		 
		 System.out.println("+++++++++++++++++++ SISTEMAS++");
		 //consulta = EjbLocator.getEjbRemote();
		 List<Sistema> listaSistemas = consulta.getSistemas(null);
		 
		 for (Sistema sistema: listaSistemas){
			 System.out.println("Sistema: " + sistema.getCveSistema());
		 }
		 System.out.println("+++++++++++++++++++++++++++++++++++++");
		 System.out.println();
		
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
}