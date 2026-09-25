package mx.gob.imss.ctirss.delta.model.domicilio;

import java.util.Date;

import mx.gob.imss.ctirss.delta.framework.base.model.AbstractModel;

public class MunicipioIMSS extends  AbstractModel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1368978087745351930L;
	
	private String idMunicipio;
	private String cvecMunicipioSINDO;
	private String descMunicipio;
	private Subdelegacion subdelegacion;
	private TipoAmbito tipoAmbito;
	private Date fechaInicioOperacionesServiciosUrbanos;
	private Date fechaInicioOperacionesServiciosCampo;
	private Integer identificadorConvenio;
	private String rp;
	private String descEntidad;
	
	public String getIdMunicipio() {
		return idMunicipio;
	}
	public void setIdMunicipio(String idMunicipio) {
		this.idMunicipio = idMunicipio;
	}
	public String getCvecMunicipioSINDO() {
		return cvecMunicipioSINDO;
	}
	public void setCvecMunicipioSINDO(String cvecMunicipioSINDO) {
		this.cvecMunicipioSINDO = cvecMunicipioSINDO;
	}
	public String getDescMunicipio() {
		return descMunicipio;
	}
	public void setDescMunicipio(String descMunicipio) {
		this.descMunicipio = descMunicipio;
	}
	public Subdelegacion getSubdelegacion() {
		return subdelegacion;
	}
	public void setSubdelegacion(Subdelegacion subdelegacion) {
		this.subdelegacion = subdelegacion;
	}
	public TipoAmbito getTipoAmbito() {
		return tipoAmbito;
	}
	public void setTipoAmbito(TipoAmbito tipoAmbito) {
		this.tipoAmbito = tipoAmbito;
	}
	public Date getFechaInicioOperacionesServiciosUrbanos() {
		return fechaInicioOperacionesServiciosUrbanos;
	}
	public void setFechaInicioOperacionesServiciosUrbanos(
			Date fechaInicioOperacionesServiciosUrbanos) {
		this.fechaInicioOperacionesServiciosUrbanos = fechaInicioOperacionesServiciosUrbanos;
	}
	public Date getFechaInicioOperacionesServiciosCampo() {
		return fechaInicioOperacionesServiciosCampo;
	}
	public void setFechaInicioOperacionesServiciosCampo(
			Date fechaInicioOperacionesServiciosCampo) {
		this.fechaInicioOperacionesServiciosCampo = fechaInicioOperacionesServiciosCampo;
	}
	public Integer getIdentificadorConvenio() {
		return identificadorConvenio;
	}
	public void setIdentificadorConvenio(Integer identificadorConvenio) {
		this.identificadorConvenio = identificadorConvenio;
	}
	public String getRp() {
		return rp;
	}
	public void setRp(String rp) {
		this.rp = rp;
	}
	public String getDescEntidad() {
		return descEntidad;
	}
	public void setDescEntidad(String descEntidad) {
		this.descEntidad = descEntidad;
	}

}
