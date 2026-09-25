package mx.gob.imss.ctirss.delta.model.derechohabientes.reportes;

import java.io.Serializable;

public class SpRespuestaCommon implements Serializable {
	
	/**
	 * SerialVersionUID.
	 */
	private static final long serialVersionUID = -7389177603494444774L;

	private Integer codProceso;
	
	private String desProceso;
	
	public SpRespuestaCommon() {}
	
	public SpRespuestaCommon(Integer codProceso, String desProceso) {
		super();
		this.codProceso = codProceso;
		this.desProceso = desProceso;
	}

	public Integer getCodProceso() {
		return codProceso;
	}

	public void setCodProceso(Integer codProceso) {
		this.codProceso = codProceso;
	}

	public String getDesProceso() {
		return desProceso;
	}

	public void setDesProceso(String desProceso) {
		this.desProceso = desProceso;
	}
	
}
