package mx.gob.imss.ctirss.delta.model.gestion.medio.contacto;

import mx.gob.imss.ctirss.delta.model.gestion.patronal.ViewModelItem;

public class PersonaContacto extends ViewModelItem {
	
	private static final long serialVersionUID = 1506078680426095665L;
	
	private MedioContacto medioContacto;
	private long cveIdPersonafContacto;
	private long cveIdPersonamContacto;
	
	public MedioContacto getMedioContacto() {
		return medioContacto;
	}

	public void setMedioContacto(MedioContacto medioContacto) {
		this.medioContacto = medioContacto;
	}

	public long getCveIdPersonafContacto() {
		return cveIdPersonafContacto;
	}
	
	public void setCveIdPersonafContacto(long cveIdPersonafContacto) {
		this.cveIdPersonafContacto = cveIdPersonafContacto;
	}
	
	public long getCveIdPersonamContacto() {
		return cveIdPersonamContacto;
	}
	
	public void setCveIdPersonamContacto(long cveIdPersonamContacto) {
		this.cveIdPersonamContacto = cveIdPersonamContacto;
	}
	
}
