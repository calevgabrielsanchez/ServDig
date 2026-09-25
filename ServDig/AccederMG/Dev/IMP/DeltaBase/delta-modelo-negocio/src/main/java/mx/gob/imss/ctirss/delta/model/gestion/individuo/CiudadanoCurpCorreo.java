package mx.gob.imss.ctirss.delta.model.gestion.individuo;

import java.io.Serializable;
import java.util.Date;

public class CiudadanoCurpCorreo implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private long cveIdCiudadanoCurpCorreo;
	private String fecRegistroActualizado;
	private Date fecRegistroAlta;
	private Date fecRegistroBaja;
	private Boolean indAceptoTerminosCondicione;
	private String refCorreoElectronico;
	private String refCurp;
	public long getCveIdCiudadanoCurpCorreo() {
		return cveIdCiudadanoCurpCorreo;
	}
	public void setCveIdCiudadanoCurpCorreo(long cveIdCiudadanoCurpCorreo) {
		this.cveIdCiudadanoCurpCorreo = cveIdCiudadanoCurpCorreo;
	}
	public String getFecRegistroActualizado() {
		return fecRegistroActualizado;
	}
	public void setFecRegistroActualizado(String fecRegistroActualizado) {
		this.fecRegistroActualizado = fecRegistroActualizado;
	}
	public Date getFecRegistroAlta() {
		return fecRegistroAlta;
	}
	public void setFecRegistroAlta(Date fecRegistroAlta) {
		this.fecRegistroAlta = fecRegistroAlta;
	}
	public Date getFecRegistroBaja() {
		return fecRegistroBaja;
	}
	public void setFecRegistroBaja(Date fecRegistroBaja) {
		this.fecRegistroBaja = fecRegistroBaja;
	}
	public Boolean getIndAceptoTerminosCondicione() {
		return indAceptoTerminosCondicione;
	}
	public void setIndAceptoTerminosCondicione(
			Boolean indAceptoTerminosCondicione) {
		this.indAceptoTerminosCondicione = indAceptoTerminosCondicione;
	}
	public String getRefCorreoElectronico() {
		return refCorreoElectronico;
	}
	public void setRefCorreoElectronico(String refCorreoElectronico) {
		this.refCorreoElectronico = refCorreoElectronico;
	}
	public String getRefCurp() {
		return refCurp;
	}
	public void setRefCurp(String refCurp) {
		this.refCurp = refCurp;
	}
	
	
	
	
}
