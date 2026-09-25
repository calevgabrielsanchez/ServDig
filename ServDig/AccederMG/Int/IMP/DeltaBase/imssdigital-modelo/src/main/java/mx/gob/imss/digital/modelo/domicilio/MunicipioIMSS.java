/**
 * 
 */
package mx.gob.imss.digital.modelo.domicilio;

import java.io.Serializable;
import java.util.Date;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * @author User
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "municipioIMSS", namespace = "http://mx.gob.imss.digital.modelo.domicilio")
@XmlRootElement(name = "municipioIMSS", namespace = "http://mx.gob.imss.digital.modelo.domicilio")
public class MunicipioIMSS implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4565934635650786304L;
	
	private String idMunicipio;
	private String cvecMunicipioSINDO;
	private String descMunicipio;
	private Subdelegacion subdelegacion;
	private TipoAmbito tipoAmbito;
	private Date fechaInicioOperacionesServiciosUrbanos;
	private Date fechaInicioOperacionesServiciosCampo;
	private Integer identificadorConvenio;
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
	
	
	
	
}
