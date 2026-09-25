package mx.imss.estrados.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name="NEE_CAT_DELEGACION")
public class NeeCatDelegacion implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7487021861123714783L;

	/**
	 * 
	 */
	

	public NeeCatDelegacion() {
	}
	
	@Id
	@Column(name="CVE_ID_DELEGACION")
	private Integer cveIdDelegacion;
	
	@Column(name="DES_DELEG")
	private String desDeleg;
	
	@Column(name="ANIO_INI_OPER")
	private String anioIniOper;
	
	@Column(name="CLAVE_DELEGACION")
	private String claveDelegacion;
	
	@Column(name="TIP_DELEGACION")
	private Integer tipDelegacion;
	
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
	
	@Column(name="CVE_CIZ")
	private Integer cveCiz;
	
	@Column(name="DES_RIMSSDELEGACION")
	private String desRIMSSDelegacion;

	public Integer getCveIdDelegacion() {
		return cveIdDelegacion;
	}

	public void setCveIdDelegacion(Integer cveIdDelegacion) {
		this.cveIdDelegacion = cveIdDelegacion;
	}

	public String getDesDeleg() {
		return desDeleg;
	}

	public void setDesDeleg(String desDeleg) {
		this.desDeleg = desDeleg;
	}

	public String getAnioIniOper() {
		return anioIniOper;
	}

	public void setAnioIniOper(String anioIniOper) {
		this.anioIniOper = anioIniOper;
	}

	public String getClaveDelegacion() {
		return claveDelegacion;
	}

	public void setClaveDelegacion(String claveDelegacion) {
		this.claveDelegacion = claveDelegacion;
	}

	public Integer getTipDelegacion() {
		return tipDelegacion;
	}

	public void setTipDelegacion(Integer tipDelegacion) {
		this.tipDelegacion = tipDelegacion;
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

	public Integer getCveCiz() {
		return cveCiz;
	}

	public void setCveCiz(Integer cveCiz) {
		this.cveCiz = cveCiz;
	}

	public String getDesRIMSSDelegacion() {
		return desRIMSSDelegacion;
	}

	public void setDesRIMSSDelegacion(String desRIMSSDelegacion) {
		this.desRIMSSDelegacion = desRIMSSDelegacion;
	}

	

}
