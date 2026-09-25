package mx.gob.imss.ctirss.delta.model.gestion.patronal;

import java.util.ArrayList;
import java.util.List;

import mx.gob.imss.ctirss.delta.model.domicilio.Domicilio;
import mx.gob.imss.ctirss.delta.model.gestion.medio.contacto.MedioContacto;

public class CentroTrabajo extends Domicilio {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2512572634331647397L;
	List<MedioContacto> mediosContacto= new ArrayList<MedioContacto>();
	private Long cveIdPatronSujetoObligado;
	
	/**
	 * 
	 * @author Hugo Martinez
	 * @Date 11/07/2012
	 * @return
	 */
	public List<MedioContacto> getMediosContacto() {
		return mediosContacto;
	}
	
	/**
	 * 
	 * @author Hugo Martinez
	 * @Date 11/07/2012
	 * @param mediosContacto
	 */
	public void setMediosContacto(List<MedioContacto> mediosContacto) {
		this.mediosContacto = mediosContacto;
	}

	public Long getCveIdPatronSujetoObligado() {
		return cveIdPatronSujetoObligado;
	}

	public void setCveIdPatronSujetoObligado(Long cveIdPatronSujetoObligado) {
		this.cveIdPatronSujetoObligado = cveIdPatronSujetoObligado;
	}
	
	
}
