package mx.gob.imss.ctirss.delta.model.gestion.solicitud.certificacion.retiro;

import java.math.BigDecimal;

import mx.gob.imss.ctirss.delta.framework.base.model.AbstractModel;

public class FolioCertificacion extends AbstractModel {

	private static final long serialVersionUID = 1L;

	private Long idFolioCertificacion;
	private BigDecimal idFolioCertificacionAux;
	private String numDelegacion;
	private Integer anioRegistro;
	private BigDecimal anioRegistroAux;
	private String secuencia;

	public Long getIdFolioCertificacion() {
		return idFolioCertificacion;
	}

	public void setIdFolioCertificacion(Long idFolioCertificacion) {
		this.idFolioCertificacion = idFolioCertificacion;
	}

	public BigDecimal getIdFolioCertificacionAux() {
		return idFolioCertificacionAux;
	}

	public void setIdFolioCertificacionAux(BigDecimal idFolioCertificacionAux) {
		this.idFolioCertificacionAux = idFolioCertificacionAux;
		
		if (idFolioCertificacionAux != null) {
			this.idFolioCertificacion = idFolioCertificacionAux.longValue();
		}
	}

	public String getNumDelegacion() {
		return numDelegacion;
	}

	public void setNumDelegacion(String numDelegacion) {
		this.numDelegacion = numDelegacion;
	}

	public Integer getAnioRegistro() {
		return anioRegistro;
	}

	public void setAnioRegistro(Integer anioRegistro) {
		this.anioRegistro = anioRegistro;
	}

	public BigDecimal getAnioRegistroAux() {
		return anioRegistroAux;
	}

	public void setAnioRegistroAux(BigDecimal anioRegistroAux) {
		this.anioRegistroAux = anioRegistroAux;
		
		if (anioRegistroAux != null) {
			this.anioRegistro = anioRegistroAux.intValueExact();
		}
	}
	
	public String getSecuencia() {
		return secuencia;
	}

	public void setSecuencia(String secuencia) {
		this.secuencia = secuencia;
	}

}
