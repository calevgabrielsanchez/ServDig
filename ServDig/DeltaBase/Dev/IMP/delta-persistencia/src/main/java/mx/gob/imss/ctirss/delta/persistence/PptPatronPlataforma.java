package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * The persistent class for the PPT_PATRON_PLATAFORMA database table.
 * 
 */
@Entity
@Table(name="PPT_PATRON_PLATAFORMA")
@NamedQuery(name="PptPatronPlataforma.findAll", query="SELECT p FROM PptPatronPlataforma p")
public class PptPatronPlataforma implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * IDentificador del seguro
	 */
	//@Id
    //@Column(name="CVE_ID_BIT_CORREOS_SIVRO", nullable=false, precision=22)
	//private long cveIdBitCorreosSivro;

	@Id
	@Column(name="CVE_REG_PATRON", precision=22)
	private String cveRegPatron;
	
	@Column(name="CVE_MODAL", precision=3)
	private Integer cveModalidad;

	@Column(name="NUM_DIG_VER", precision=1)
	private Integer numDigVer;
	
	@Temporal(TemporalType.DATE)
	@Column(name="FEC_ALTA")
	private Date fecAlta;

	@Temporal(TemporalType.DATE)
	@Column(name="FEC_BAJA")
	private Date fecBaja;

	@Temporal(TemporalType.DATE)
	@Column(name="FEC_REGISTRO_ALTA")
	private Date fecRegistroAlta;

	public String getCveRegPatron() {
		return cveRegPatron;
	}

	public void setCveRegPatron(String cveRegPatron) {
		this.cveRegPatron = cveRegPatron;
	}

	public Integer getCveModalidad() {
		return cveModalidad;
	}

	public void setCveModalidad(Integer cveModalidad) {
		this.cveModalidad = cveModalidad;
	}

	public Integer getNumDigVer() {
		return numDigVer;
	}

	public void setNumDigVer(Integer numDigVer) {
		this.numDigVer = numDigVer;
	}

	public Date getFecAlta() {
		return fecAlta;
	}

	public void setFecAlta(Date fecAlta) {
		this.fecAlta = fecAlta;
	}

	public Date getFecBaja() {
		return fecBaja;
	}

	public void setFecBaja(Date fecBaja) {
		this.fecBaja = fecBaja;
	}

	public Date getFecRegistroAlta() {
		return fecRegistroAlta;
	}

	public void setFecRegistroAlta(Date fecRegistroAlta) {
		this.fecRegistroAlta = fecRegistroAlta;
	}

}