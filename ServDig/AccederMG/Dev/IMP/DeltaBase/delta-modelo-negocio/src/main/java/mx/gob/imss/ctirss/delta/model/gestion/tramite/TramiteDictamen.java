package mx.gob.imss.ctirss.delta.model.gestion.tramite;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class TramiteDictamen extends TramiteSujetoObligado {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1190542395492075929L;
	private Long idEjercicioFiscal;
	private Long idPatronDictamen;
	
	public Long getIdEjercicioFiscal() {
		return idEjercicioFiscal;
	}
	
	public void setIdEjercicioFiscal(Long idEjercicioFiscal) {
		this.idEjercicioFiscal = idEjercicioFiscal;
	}
	
	public Long getIdPatronDictamen() {
		return idPatronDictamen;
	}
	
	public void setIdPatronDictamen(Long idPatronDictamen) {
		this.idPatronDictamen = idPatronDictamen;
	}

}