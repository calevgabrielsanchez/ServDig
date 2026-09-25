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
 * The persistent class for the DIT_SOLICITUD database table.
 * 
 */
@Entity
@Table(name="DIT_SOLICITUD_FIRMA_DIGITAL")
public class DitSolicitudFirmaDigital implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@SequenceGenerator(name="SEQ_DITSOLICITUDFIRMADIGITAL", sequenceName="SEQ_DITSOLICITUDFIRMADIGITAL")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SEQ_DITSOLICITUDFIRMADIGITAL")
	@Column(name = "CVE_ID_FIRMA_DIGITAL")
	private Long cveIdFirmaDigital;
	
	@Column(name="CVE_ID_SOLICITUD")
	private long cveIdSolicitud;

	@Temporal( TemporalType.DATE)       
	@Column(name="FEC_REGISTRO_ALTA")
	private Date fecRegAlta;
	
    @Temporal( TemporalType.DATE)       
	@Column(name="FEC_REGISTRO_BAJA")
	private Date fecRegBaja;
	
    @Temporal( TemporalType.DATE)       
	@Column(name="FEC_REGISTRO_ACTUALIZADO")
	private Date fecRegActualizado;
	
	//CVE_ID_SOLICITUD
				
	@Column(name="NUM_SEQ_NOTARIA", nullable=false, length=255)
	private String numSecNotaria;
	@Column(name="REF_SELLO_DIGITAL", nullable=true, length=255)
	private String numSelloDigital;
	@Column(name="REF_CADENA_ORIGINAL", nullable=true, length=255)
	private String numCadenaOriginal;		

	@Column(name="REF_URL_ACUSE_FIRMA", nullable=true, length=150)
	private String refUrlAcuseFirma;

	@Temporal( TemporalType.DATE)
	@Column(name="FEC_INICIO_VIGENCIA_CERT")
	private Date fecInicioVigenciaCert;

	@Temporal( TemporalType.DATE)
	@Column(name="FEC_FIN_VIGENCIA_CERT")
	private Date fecFinVigenciaCert;

	@Column(name="REF_NUM_SERIE_CERTIFICADO", nullable=true, length=150)
	private String refNumSerieCertificado;

	//bi-directional many-to-one association to DitSolicitud
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="CVE_ID_SOLICITUD", insertable = false, updatable = false)
	private DitSolicitud ditSolicitud;

	public Long getCveIdFirmaDigital() {
		return cveIdFirmaDigital;
	}

	public void setCveIdFirmaDigital(Long cveIdFirmaDigital) {
		this.cveIdFirmaDigital = cveIdFirmaDigital;
	}
		
	public Date getFecRegAlta() {
		return fecRegAlta;
	}

	public void setFecRegAlta(Date fecRegAlta) {
		this.fecRegAlta = fecRegAlta;
	}

	public Date getFecRegBaja() {
		return fecRegBaja;
	}

	public void setFecRegBaja(Date fecRegBaja) {
		this.fecRegBaja = fecRegBaja;
	}

	public Date getFecRegActualizado() {
		return fecRegActualizado;
	}

	public void setFecRegActualizado(Date fecRegActualizado) {
		this.fecRegActualizado = fecRegActualizado;
	}

	public String getNumSecNotaria() {
		return numSecNotaria;
	}

	public void setNumSecNotaria(String numSecNotaria) {
		this.numSecNotaria = numSecNotaria;
	}

	public String getNumSelloDigital() {
		return numSelloDigital;
	}

	public void setNumSelloDigital(String numSelloDigital) {
		this.numSelloDigital = numSelloDigital;
	}

	public String getNumCadenaOriginal() {
		return numCadenaOriginal;
	}

	public void setNumCadenaOriginal(String numCadenaOriginal) {
		this.numCadenaOriginal = numCadenaOriginal;
	}

	public DitSolicitud getDitSolicitud() {
		return ditSolicitud;
	}

	public void setDitSolicitud(DitSolicitud ditSolicitud) {
		this.ditSolicitud = ditSolicitud;
	}

	public long getCveIdSolicitud() {
		return cveIdSolicitud;
	}

	public void setCveIdSolicitud(long cveIdSolicitud) {
		this.cveIdSolicitud = cveIdSolicitud;
	}

	public String getRefUrlAcuseFirma() {
		return refUrlAcuseFirma;
	}

	public void setRefUrlAcuseFirma(String refUrlAcuseFirma) {
		this.refUrlAcuseFirma = refUrlAcuseFirma;
	}

	public Date getFecInicioVigenciaCert() {
		return fecInicioVigenciaCert;
	}

	public void setFecInicioVigenciaCert(Date fecInicioVigenciaCert) {
		this.fecInicioVigenciaCert = fecInicioVigenciaCert;
	}

	public Date getFecFinVigenciaCert() {
		return fecFinVigenciaCert;
	}

	public void setFecFinVigenciaCert(Date fecFinVigenciaCert) {
		this.fecFinVigenciaCert = fecFinVigenciaCert;
	}

	public String getRefNumSerieCertificado() {
		return refNumSerieCertificado;
	}

	public void setRefNumSerieCertificado(String refNumSerieCertificado) {
		this.refNumSerieCertificado = refNumSerieCertificado;
	}
		
  }