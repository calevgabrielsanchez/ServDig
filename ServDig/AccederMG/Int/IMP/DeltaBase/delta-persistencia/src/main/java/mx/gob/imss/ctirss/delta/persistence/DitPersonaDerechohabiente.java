package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


/**
 * The persistent class for the DIT_DERECHOHABIENTE database table.
 * 
 */
@Entity
@Table(name="DIT_PERSONA_DERECHOHABIENTE")
public class DitPersonaDerechohabiente implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "DIT_PERSONA_DERECHOHABIENTE_GENERATOR", sequenceName = "SEQ_DITPERSONADERECHOHABIENTE", allocationSize = 1)    
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "DIT_PERSONA_DERECHOHABIENTE_GENERATOR")
	@Column(name="CVE_ID_PER_DERECHOHAB", nullable=false, precision=22)
	private long cveIdPerDerechohabiente;

	@Temporal( TemporalType.TIMESTAMP)
	@Column(name="FEC_REGISTRO_ACTUALIZADO")
	private Date fecRegistroActualizado;

	@Temporal( TemporalType.TIMESTAMP)
	@Column(name="FEC_REGISTRO_ALTA")
	private Date fecRegistroAlta;

	@Temporal( TemporalType.TIMESTAMP)
	@Column(name="FEC_REGISTRO_BAJA")
	private Date fecRegistroBaja;
	
	//bi-directional one-to-one association to DitPersona
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="CVE_ID_PERSONA", nullable=false, updatable=false)
	private DitPersona ditPersona;
	
	@Column(name="CVE_EXPEDIENTE_ELECTRONICO", length=20)
	private String cveExpedienteElectronico;
	
	@Column(name="IND_REG_ACTIVO")
	private Integer indRegActivo;

	public long getCveIdPerDerechohabiente() {
		return cveIdPerDerechohabiente;
	}

	public void setCveIdPerDerechohabiente(long cveIdPerDerechohabiente) {
		this.cveIdPerDerechohabiente = cveIdPerDerechohabiente;
	}

	public Date getFecRegistroActualizado() {
		return this.fecRegistroActualizado;
	}

	public void setFecRegistroActualizado(Date fecRegistroActualizado) {
		this.fecRegistroActualizado = fecRegistroActualizado;
	}

	public Date getFecRegistroAlta() {
		return this.fecRegistroAlta;
	}

	public void setFecRegistroAlta(Date fecRegistroAlta) {
		this.fecRegistroAlta = fecRegistroAlta;
	}

	public Date getFecRegistroBaja() {
		return this.fecRegistroBaja;
	}

	public void setFecRegistroBaja(Date fecRegistroBaja) {
		this.fecRegistroBaja = fecRegistroBaja;
	}

	public DitPersona getDitPersona() {
		return this.ditPersona;
	}

	public void setDitPersona(DitPersona ditPersona) {
		this.ditPersona = ditPersona;
	}
	
	/**
	 * @return the cveExpedienteElectronico
	 */
	public String getCveExpedienteElectronico() {
		return cveExpedienteElectronico;
	}

	/**
	 * @param cveExpedienteElectronico the cveExpedienteElectronico to set
	 */
	public void setCveExpedienteElectronico(String cveExpedienteElectronico) {
		this.cveExpedienteElectronico = cveExpedienteElectronico;
	}

	public Integer getIndRegActivo() {
		return indRegActivo;
	}

	public void setIndRegActivo(Integer indRegActivo) {
		this.indRegActivo = indRegActivo;
	}
	
}