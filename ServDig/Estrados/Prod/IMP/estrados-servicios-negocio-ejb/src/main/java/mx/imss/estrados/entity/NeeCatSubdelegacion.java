package mx.imss.estrados.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name="NEE_CAT_SUBDELEGACION")
public class NeeCatSubdelegacion implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7092758292543545895L;

	/**
	 * 
	 */
	

	public NeeCatSubdelegacion() {
	}
	
	@Id
	@Column(name="CVE_ID_SUBDELEGACION")
	private Integer cveIdSubdelegacion;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="CVE_ID_DELEGACION")
	private NeeCatDelegacion neeCatDelegacion;
	
	@Column(name="DES_SUBDELEGACION")
	private String desSubdelegacion;
	
	@Column(name="ANIO_INI_OPER")
	private String anioIniOper;
	
	@Column(name="CLAVE_SUBDELEGACION")
	private String claveSubdelegacion;
	
	@Temporal(TemporalType.DATE)
	@Column(name="FEC_REGISTRO_ALTA")
	private Date fecRegistroAlta;
	
	@Temporal(TemporalType.DATE)
	@Column(name="FEC_REGISTRO_BAJA")
	private Date fecRegistroBaja;
	
	@Temporal(TemporalType.DATE)
	@Column(name="FEC_REGISTRO_ACTUALIZADO")
	private Date fecRegistroActualizado;
	
	@Column(name="DOMICILIO_ID")
	private String domicilioId;
	
	@Column(name="DES_RIMSSSUBDELEGACION")
	private String desRIMSSSubDelegacion;

	public Integer getCveIdSubdelegacion() {
		return cveIdSubdelegacion;
	}

	public void setCveIdSubdelegacion(Integer cveIdSubdelegacion) {
		this.cveIdSubdelegacion = cveIdSubdelegacion;
	}

	public NeeCatDelegacion getNeeCatDelegacion() {
		return neeCatDelegacion;
	}

	public void setNeeCatDelegacion(NeeCatDelegacion neeCatDelegacion) {
		this.neeCatDelegacion = neeCatDelegacion;
	}

	public String getDesSubdelegacion() {
		return desSubdelegacion;
	}

	public void setDesSubdelegacion(String desSubdelegacion) {
		this.desSubdelegacion = desSubdelegacion;
	}

	public String getAnioIniOper() {
		return anioIniOper;
	}

	public void setAnioIniOper(String anioIniOper) {
		this.anioIniOper = anioIniOper;
	}

	public String getClaveSubdelegacion() {
		return claveSubdelegacion;
	}

	public void setClaveSubdelegacion(String claveSubdelegacion) {
		this.claveSubdelegacion = claveSubdelegacion;
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

	public Date getFecRegistroActualizado() {
		return fecRegistroActualizado;
	}

	public void setFecRegistroActualizado(Date fecRegistroActualizado) {
		this.fecRegistroActualizado = fecRegistroActualizado;
	}

	public String getDomicilioId() {
		return domicilioId;
	}

	public void setDomicilioId(String domicilioId) {
		this.domicilioId = domicilioId;
	}

	public String getDesRIMSSSubDelegacion() {
		return desRIMSSSubDelegacion;
	}

	public void setDesRIMSSSubDelegacion(String desRIMSSSubDelegacion) {
		this.desRIMSSSubDelegacion = desRIMSSSubDelegacion;
	}

	

}
