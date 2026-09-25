package mx.gob.imss.ctirss.delta.gestion.seguroVoluntario.vo;

public class DatosLinea {

	private String lineaCaptura;
	private String numFolioSua;
	private Double montoPagar;
	private String registroPatronal;
	private String nombreRegistroPatronal;
	private String numPeriodoAseguramiento;
	private Long idCotizacion;

	public DatosLinea() {}

	public String getLineaCaptura() {
		return lineaCaptura;
	}

	public void setLineaCaptura(String lineaCaptura) {
		this.lineaCaptura = lineaCaptura;
	}

	public String getNumFolioSua() {
		return numFolioSua;
	}

	public void setNumFolioSua(String numFolioSua) {
		this.numFolioSua = numFolioSua;
	}

	public Double getMontoPagar() {
		return montoPagar;
	}

	public void setMontoPagar(Double montoPagar) {
		this.montoPagar = montoPagar;
	}

	public String getRegistroPatronal() {
		return registroPatronal;
	}

	public void setRegistroPatronal(String registroPatronal) {
		this.registroPatronal = registroPatronal;
	}

	public String getNombreRegistroPatronal() {
		return nombreRegistroPatronal;
	}

	public void setNombreRegistroPatronal(String nombreRegistroPatronal) {
		this.nombreRegistroPatronal = nombreRegistroPatronal;
	}

	public String getNumPeriodoAseguramiento() {
		return numPeriodoAseguramiento;
	}

	public void setNumPeriodoAseguramiento(String numPeriodoAseguramiento) {
		this.numPeriodoAseguramiento = numPeriodoAseguramiento;
	}

	public Long getIdCotizacion() {
		return idCotizacion;
	}

	public void setIdCotizacion(Long idCotizacion) {
		this.idCotizacion = idCotizacion;
	}

	@Override
	public String toString() {
		return "DatosLinea [lineaCaptura=" + lineaCaptura + ", numFolioSua=" + numFolioSua + ", montoPagar="
				+ montoPagar + ", registroPatronal=" + registroPatronal + ", nombreRegistroPatronal="
				+ nombreRegistroPatronal + ", numPeriodoAseguramiento=" + numPeriodoAseguramiento + ", idCotizacion="
				+ idCotizacion + "]";
	}
	
}
