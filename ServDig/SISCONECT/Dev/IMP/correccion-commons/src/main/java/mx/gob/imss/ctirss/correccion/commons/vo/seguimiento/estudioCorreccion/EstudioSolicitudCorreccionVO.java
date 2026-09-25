/**
 * 
 */
package mx.gob.imss.ctirss.correccion.commons.vo.seguimiento.estudioCorreccion;

import java.io.Serializable;
import java.util.Date;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import mx.gob.imss.ctirss.correccion.framework.base.model.AbstractModel;
import mx.gob.imss.ctirss.correccion.utils.Functions;

/**
 * 
 * VO para encapsular la informacion de las solicitudes de correccion que se muestran 
 * en el estudio de correccion.
 * @author CesarAgustin
 * @version 1.0.0
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class EstudioSolicitudCorreccionVO extends AbstractModel implements
		Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7849700487953005180L;
	
	//Propiedades para filtrar la consulta
	private String regPatronal;
	private String folioCorr;
	private String fechaPresentaIni;
	private String fechaPresentaFin;
	private Integer cveEstatus;
	private Long idSubDelegacion;
	private Integer ejercicioAnio;
	private Integer idEstadoSel;
	
	//Propiedades para mostrar en la tabla
	private Integer idSolicitud;
	private String razonSocial;
	private String fechaPresenta;
	
	private String descripcionEstatus;
	private String fechaEstatusTxt;
	private Date fecFechaEstatus;
	private Long diasTranscurridos;
	
	
	public EstudioSolicitudCorreccionVO(){}
	
	public EstudioSolicitudCorreccionVO(Object[] obj){
		int i=0;
		
		setIdSolicitud(Integer.parseInt(String.valueOf(obj[i++])));
		setFolioCorr(String.valueOf(obj[i++]));
		//setFechaPresenta(Functions.dateToString2((Date) obj[i++]));
		setFecFechaEstatus((Date) obj[i++]);
		setFechaEstatusTxt(Functions.dateToString2(getFecFechaEstatus()));
		setCveEstatus(Integer.parseInt(String.valueOf(obj[i++])));
		setDescripcionEstatus(String.valueOf(obj[i++]));
		setRazonSocial(String.valueOf(obj[i++]));
		setRegPatronal(String.valueOf(obj[i++]));
		
	}
	
	
	public String getRegPatronal() {
		return regPatronal;
	}
	public void setRegPatronal(String regPatronal) {
		this.regPatronal = regPatronal;
	}
	
	public String getFolioCorr() {
		return folioCorr;
	}
	public void setFolioCorr(String folioCorr) {
		this.folioCorr = folioCorr;
	}
	
	public String getFechaPresentaIni() {
		return fechaPresentaIni;
	}
	public void setFechaPresentaIni(String fechaPresentaIni) {
		this.fechaPresentaIni = fechaPresentaIni;
	}
	
	public String getFechaPresentaFin() {
		return fechaPresentaFin;
	}
	public void setFechaPresentaFin(String fechaPresentaFin) {
		this.fechaPresentaFin = fechaPresentaFin;
	}
	
	public Integer getIdSolicitud() {
		return idSolicitud;
	}
	public void setIdSolicitud(Integer idSolicitud) {
		this.idSolicitud = idSolicitud;
	}
	
	public String getRazonSocial() {
		return razonSocial;
	}
	public void setRazonSocial(String razonSocial) {
		this.razonSocial = razonSocial;
	}
	
	public String getFechaPresenta() {
		return fechaPresenta;
	}
	public void setFechaPresenta(String fechaPresenta) {
		this.fechaPresenta = fechaPresenta;
	}
	
	public Integer getCveEstatus() {
		return cveEstatus;
	}
	public void setCveEstatus(Integer cveEstatus) {
		this.cveEstatus = cveEstatus;
	}

	public Long getIdSubDelegacion() {
		return idSubDelegacion;
	}
	public void setIdSubDelegacion(Long idSubDelegacion) {
		this.idSubDelegacion = idSubDelegacion;
	}

	/**
	 * Retorna el valor descripcionEstatus
	 * @return  descripcionEstatus
	 */
	public String getDescripcionEstatus() {
		return descripcionEstatus;
	}

	/**
	 * Asigna el valor del descripcionEstatus al atributo descripcionEstatus
	 * @param descripcionEstatus 
	 */
	public void setDescripcionEstatus(String descripcionEstatus) {
		this.descripcionEstatus = descripcionEstatus;
	}

	

	/**
	 * Retorna el valor fechaEstatusTxt
	 * @return  fechaEstatusTxt
	 */
	public String getFechaEstatusTxt() {
		return fechaEstatusTxt;
	}

	/**
	 * Asigna el valor del fechaEstatusTxt al atributo fechaEstatusTxt
	 * @param fechaEstatusTxt 
	 */
	public void setFechaEstatusTxt(String fechaEstatusTxt) {
		this.fechaEstatusTxt = fechaEstatusTxt;
	}

	/**
	 * Retorna el valor diasTranscurridos
	 * @return  diasTranscurridos
	 */
	public Long getDiasTranscurridos() {
		return diasTranscurridos;
	}

	/**
	 * Asigna el valor del diasTranscurridos al atributo diasTranscurridos
	 * @param diasTranscurridos 
	 */
	public void setDiasTranscurridos(Long diasTranscurridos) {
		this.diasTranscurridos = diasTranscurridos;
	}

	/**
	 * Retorna el valor fecFechaEstatus
	 * @return  fecFechaEstatus
	 */
	public Date getFecFechaEstatus() {
		return fecFechaEstatus;
	}

	/**
	 * Asigna el valor del fecFechaEstatus al atributo fecFechaEstatus
	 * @param fecFechaEstatus 
	 */
	public void setFecFechaEstatus(Date fecFechaEstatus) {
		this.fecFechaEstatus = fecFechaEstatus;
	}

	/**
	 * Retorna el valor ejercicioAnio
	 * @return  ejercicioAnio
	 */
	public Integer getEjercicioAnio() {
		return ejercicioAnio;
	}

	/**
	 * Asigna el valor del ejercicioAnio al atributo ejercicioAnio
	 * @param ejercicioAnio 
	 */
	public void setEjercicioAnio(Integer ejercicioAnio) {
		this.ejercicioAnio = ejercicioAnio;
	}

	/**
	 * Retorna el valor idEstadoSel
	 * @return  idEstadoSel
	 */
	public Integer getIdEstadoSel() {
		return idEstadoSel;
	}

	/**
	 * Asigna el valor del idEstadoSel al atributo idEstadoSel
	 * @param idEstadoSel 
	 */
	public void setIdEstadoSel(Integer idEstadoSel) {
		this.idEstadoSel = idEstadoSel;
	}
	
	
}
