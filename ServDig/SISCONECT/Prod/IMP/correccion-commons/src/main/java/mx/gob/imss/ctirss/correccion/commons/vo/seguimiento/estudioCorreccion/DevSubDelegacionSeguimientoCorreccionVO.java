package mx.gob.imss.ctirss.correccion.commons.vo.seguimiento.estudioCorreccion;

import java.io.Serializable;
import java.util.Date;

import mx.gob.imss.ctirss.correccion.framework.utils.ControlTabs;
import mx.gob.imss.ctirss.domiciliosInegi.model.DgDomicilioGeografico;

public class DevSubDelegacionSeguimientoCorreccionVO extends ControlTabs implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String fechaDerivacion;
	private long cveNuevaDelegacion;
	private long idNuevaSubdelegacion;
	private long idAnteriorSubdelegacion;
	private String folioDerivacion;
	private Integer cveSolicitud;
	
	public String getFechaDerivacion() {
		return fechaDerivacion;
	}
	public void setFechaDerivacion(String fechaDerivacion) {
		this.fechaDerivacion = fechaDerivacion;
	}
	public long getCveNuevaDelegacion() {
		return cveNuevaDelegacion;
	}
	public void setCveNuevaDelegacion(long cveNuevaDelegacion) {
		this.cveNuevaDelegacion = cveNuevaDelegacion;
	}
	public long getIdNuevaSubdelegacion() {
		return idNuevaSubdelegacion;
	}
	public void setIdNuevaSubdelegacion(long idNuevaSubdelegacion) {
		this.idNuevaSubdelegacion = idNuevaSubdelegacion;
	}
	public long getIdAnteriorSubdelegacion() {
		return idAnteriorSubdelegacion;
	}
	public void setIdAnteriorSubdelegacion(long idAnteriorSubdelegacion) {
		this.idAnteriorSubdelegacion = idAnteriorSubdelegacion;
	}
	public String getFolioDerivacion() {
		return folioDerivacion;
	}
	public void setFolioDerivacion(String folioDerivacion) {
		this.folioDerivacion = folioDerivacion;
	}
	public Integer getCveSolicitud() {
		return cveSolicitud;
	}
	public void setCveSolicitud(Integer cveSolicitud) {
		this.cveSolicitud = cveSolicitud;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}



}
