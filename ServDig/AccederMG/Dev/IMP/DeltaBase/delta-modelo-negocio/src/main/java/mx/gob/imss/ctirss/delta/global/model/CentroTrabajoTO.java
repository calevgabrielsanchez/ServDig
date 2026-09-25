package mx.gob.imss.ctirss.delta.global.model;

import java.util.ArrayList;
import java.util.List;

public class CentroTrabajoTO extends DomicilioTO {
	
	/**
	 * Serial version
	 */
	private static final long serialVersionUID = 1906591660934646453L;
	private List<MedioContactoTO> mediosContacto= new ArrayList<MedioContactoTO>();
	private Long idRegistroPatronal;
	
	
	public List<MedioContactoTO> getMediosContacto() {
		return mediosContacto;
	}
	public void setMediosContacto(List<MedioContactoTO> mediosContacto) {
		this.mediosContacto = mediosContacto;
	}
	public Long getIdRegistroPatronal() {
		return idRegistroPatronal;
	}
	public void setIdRegistroPatronal(Long idRegistroPatronal) {
		this.idRegistroPatronal = idRegistroPatronal;
	}
	
	
	
}
