package mx.gob.imss.ctirss.delta.model.derechohabientes.reportes;

public class SpRespuestaReporte extends SpRespuestaCommon {
	
	private String estatus;
	
	private String folio;
	
	public SpRespuestaReporte() {}
	
	public SpRespuestaReporte(String estatus, String folio, Integer codProceso, String desProceso) {
		super(codProceso, desProceso);
		this.estatus = estatus;
		this.folio = folio;
	}

	public String getEstatus() {
		return estatus;
	}

	public void setEstatus(String estatus) {
		this.estatus = estatus;
	}

	public String getFolio() {
		return folio;
	}

	public void setFolio(String folio) {
		this.folio = folio;
	}
	
}
