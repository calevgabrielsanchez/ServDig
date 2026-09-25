package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * The persistent class for the DIT_CURP database table.
 * 
 */
@Entity
@Table(name="DIT_DATOS_CERTIFICADO_FIEL")
public class DitDatosCertificadoFiel implements Serializable{

	/**
	 * Serial version
	 */
	private static final long serialVersionUID = 1877104214434258462L;
	
	@Id
	@SequenceGenerator(name = "DIT_DATOS_CERTIFICADO_FIEL_GENERATOR", sequenceName = "SEQ_DITDATOSCERTIFICADOFIEL", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "DIT_DATOS_CERTIFICADO_FIEL_GENERATOR")
	@Column(name="CVE_ID_DATOS_CERT_FIEL", unique=true, nullable=false, precision=22)
	private long cveIdDatosCertFiel;

	@Column(name="FEC_INI_VIGENCIA", nullable=false)
	private Date fecIniVigencia;
	
	@Column(name="FEC_FIN_VIGENCIA", length=18)
	private Date fecFinVigencia;

	@Column(name="NUM_SERIAL", nullable=false, length=18)
	private String numSerial;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_ALTA", nullable=true)
	private Date fecRegistroAlta;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_BAJA", nullable=true)
	private Date fecRegistroBaja;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_ACTUALIZADO", nullable=true)
	private Date fecRegistroActualizado;
    
    @OneToOne
	@JoinColumn(name="CVE_ID_PERSONA_FISICA")
	private DitPersonaFisica ditPersonaFisica;
    
    @OneToOne
	@JoinColumn(name="CVE_ID_PERSONA_MORAL")
	private DitPersonaMoral ditPersonaMoral;

	public long getCveIdDatosCertFiel() {
		return cveIdDatosCertFiel;
	}

	public void setCveIdDatosCertFiel(long cveIdDatosCertFiel) {
		this.cveIdDatosCertFiel = cveIdDatosCertFiel;
	}

	public Date getFecIniVigencia() {
		return fecIniVigencia;
	}

	public void setFecIniVigencia(Date fecIniVigencia) {
		this.fecIniVigencia = fecIniVigencia;
	}

	public Date getFecFinVigencia() {
		return fecFinVigencia;
	}

	public void setFecFinVigencia(Date fecFinVigencia) {
		this.fecFinVigencia = fecFinVigencia;
	}

	public String getNumSerial() {
		return numSerial;
	}

	public void setNumSerial(String numSerial) {
		this.numSerial = numSerial;
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

	public DitPersonaFisica getDitPersonaFisica() {
		return ditPersonaFisica;
	}

	public void setDitPersonaFisica(DitPersonaFisica ditPersonaFisica) {
		this.ditPersonaFisica = ditPersonaFisica;
	}

	public DitPersonaMoral getDitPersonaMoral() {
		return ditPersonaMoral;
	}

	public void setDitPersonaMoral(DitPersonaMoral ditPersonaMoral) {
		this.ditPersonaMoral = ditPersonaMoral;
	}
    
}
