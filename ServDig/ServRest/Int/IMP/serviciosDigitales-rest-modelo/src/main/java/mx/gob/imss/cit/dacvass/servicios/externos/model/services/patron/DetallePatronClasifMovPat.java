package mx.gob.imss.cit.dacvass.servicios.externos.model.services.patron;

import java.io.Serializable;
import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class DetallePatronClasifMovPat extends DatosGeneralesPatron implements Serializable {

	private static final long serialVersionUID = -8018215670131893295L;
	
	private String desDivision;
	private String desGrupo;
	private String desFraccion;
	private String nomEntidadFederativa;
	private String cveDelegacion;
	private String cveSubDelegacion;
	private String desTipoMovimiento;
	private Date fecMovimiento;
	private String nomDelegacion;
	private String nomSubDelegacion;
	
	
	
	
	
	public String getNomDelegacion() {
		return nomDelegacion;
	}
	public void setNomDelegacion(String nomDelegacion) {
		this.nomDelegacion = nomDelegacion;
	}
	public String getNomSubDelegacion() {
		return nomSubDelegacion;
	}
	public void setNomSubDelegacion(String nomSubDelegacion) {
		this.nomSubDelegacion = nomSubDelegacion;
	}
	public String getDesDivision() {
		return desDivision;
	}
	public void setDesDivision(String desDivision) {
		this.desDivision = desDivision;
	}
	public String getDesGrupo() {
		return desGrupo;
	}
	public void setDesGrupo(String desGrupo) {
		this.desGrupo = desGrupo;
	}
	public String getDesFraccion() {
		return desFraccion;
	}
	public void setDesFraccion(String desFraccion) {
		this.desFraccion = desFraccion;
	}
	public String getNomEntidadFederativa() {
		return nomEntidadFederativa;
	}
	public void setNomEntidadFederativa(String nomEntidadFederativa) {
		this.nomEntidadFederativa = nomEntidadFederativa;
	}
	public String getCveDelegacion() {
		return cveDelegacion;
	}
	public void setCveDelegacion(String cveDelegacion) {
		this.cveDelegacion = cveDelegacion;
	}
	public String getCveSubDelegacion() {
		return cveSubDelegacion;
	}
	public void setCveSubDelegacion(String cveSubDelegacion) {
		this.cveSubDelegacion = cveSubDelegacion;
	}
	public String getDesTipoMovimiento() {
		return desTipoMovimiento;
	}
	public void setDesTipoMovimiento(String desTipoMovimiento) {
		this.desTipoMovimiento = desTipoMovimiento;
	}
	public Date getFecMovimiento() {
		return fecMovimiento;
	}
	public void setFecMovimiento(Date fecMovimiento) {
		this.fecMovimiento = fecMovimiento;
	}
	
	
	
	

}
