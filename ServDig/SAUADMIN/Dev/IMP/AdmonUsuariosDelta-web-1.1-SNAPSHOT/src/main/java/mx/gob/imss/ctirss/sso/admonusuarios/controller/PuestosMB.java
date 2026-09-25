package mx.gob.imss.ctirss.sso.admonusuarios.controller;

import java.util.List;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;

import mx.gob.imss.ctirss.sso.admonusuarios.dto.PuestosDTO;
import mx.gob.imss.ctirss.sso.admonusuarios.service.CatalogoPuestosServiceLocale;


@ManagedBean(name="puestosMB")
public class PuestosMB {
	
	@EJB
	private CatalogoPuestosServiceLocale catalogoPuestosServiceLocale;
	
	@ManagedProperty(value="#{consultaGenerica}")	 
	private ConsultaGenericaController filtrosConsulta;

	
	private PuestosDTO[] puestos;

	private String cvePuesto;
	private String descripcion;
	private String descripcionCrear;
	
	public String getDescripcionCrear() {
		return descripcionCrear;
	}

	public void setDescripcionCrear(String descripcionCrear) {
		this.descripcionCrear = descripcionCrear;
	}

	public String getCvePuesto() {
		return cvePuesto;
	}

	public void setCvePuesto(String cvePuesto) {
		this.cvePuesto = cvePuesto;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	
	public PuestosDTO[] getPuestos() throws Exception {
		List<PuestosDTO> listPuestos = catalogoPuestosServiceLocale.findAll(filtrosConsulta.getClaveDepartamento());
		
		if (listPuestos != null && listPuestos.size() > 0) {
			puestos = new PuestosDTO[listPuestos.size()];

			for (int i = 0; i < listPuestos.size(); i++) {
				PuestosDTO dato = new PuestosDTO();
				dato.setCveSsopuesto(listPuestos.get(i).getCveSsopuesto());
				dato.setDesPuesto(listPuestos.get(i).getDesPuesto());
				puestos[i] = dato;
			}
		}

		return puestos;  
	}   

	public void setPuestos(PuestosDTO[] puestos) {
		this.puestos = puestos;
	}
	
	public String delete() {
		System.out.println("Borrando dato");
		PuestosDTO dato = new PuestosDTO();
		dato.setCveSsopuesto(getCveDepto());
		try {
			catalogoPuestosServiceLocale.delete(dato, filtrosConsulta.getClaveDepartamento());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return "catalogoPuestos";
	}
	
	public String add() {
		System.out.println("Agregando -");
		PuestosDTO dato = new PuestosDTO();
		dato.setDesPuesto(descripcionCrear);
		
		try {
			catalogoPuestosServiceLocale.create(dato, filtrosConsulta.getClaveDepartamento());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "catalogoPuestos";
	}
	
	public String update() {
		PuestosDTO dato = new PuestosDTO();
		dato.setDesPuesto(descripcion);
		dato.setCveSsopuesto(getCveDepto());
		try {
			catalogoPuestosServiceLocale.update(dato, filtrosConsulta.getClaveDepartamento());
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("Modificando");
		return "catalogoPuestos";
	}
	
	private long getCveDepto() {
		return new Long(cvePuesto);
	}

	public ConsultaGenericaController getFiltrosConsulta() {
		return filtrosConsulta;
	}

	public void setFiltrosConsulta(ConsultaGenericaController filtrosConsulta) {
		this.filtrosConsulta = filtrosConsulta;
	}
	
	
}

