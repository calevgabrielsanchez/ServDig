package mx.gob.imss.ctirss.correccion.base.model;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.SequenceGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import mx.gob.imss.ctirss.correccion.framework.base.model.AbstractDocumentoElectronicoModel;

/**
 * The persistent class for the CRT_SOLICITUDCORR database table.
 * 
 * 
 */
@MappedSuperclass
public class AbstractCrtSolicitudcorr extends AbstractDocumentoElectronicoModel {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "CVE_PATRON_GENERATOR", sequenceName = "CRS_CVE_SOLICITUDCORR")
	@GeneratedValue(generator = "CVE_PATRON_GENERATOR")
	@Column(name = "CVE_SOLICITUDCORR")
	private Integer cveSolicitudCorr;

	@Column(name = "NU_FOLIO")
	private String nuFolio;

	@Column(name = "CVE_FK_SUBDELEGACION")
	private Long cveSubdelegacion;

	@Column(name = "CVE_TIPOCORR")
	private Integer cveTipoCorreccion;

	@Column(name = "ID_FORMAPRESENTA")
	private Integer idFormaPresenta;	
	
	
	@Temporal(TemporalType.DATE)
	@Column(name = "FEC_RECEPOFICIOINV")
	private Date fecFechaRecepcionOficio;

	@Temporal(TemporalType.DATE)
	@Column(name = "FEC_PERIODOINI")
	private Date fecFechaPeriodoIni;

	@Temporal(TemporalType.DATE)
	@Column(name = "FEC_PERIODOFIN")
	private Date fecFechaPeriodoFin;

	@Column(name = "NU_NUMTRAB")
	private Integer numTrabajadores;

	@Temporal(TemporalType.DATE)
	@Column(name = "FEC_ELABORASOLCOR")
	private Date fecFechaElacoracionCorreccion;

	@Temporal(TemporalType.DATE)
	@Column(name = "FEC_FECHAUTORIZASOL")
	private Date fecFechaAutorizacionCorreccion;

	@Column(name = "TX_REP_LEGAL_ELAB")
	private String txRepresentanteLegal;

	@Column(name = "REF_LUGAR")
	private String lugar;

	@Temporal(TemporalType.DATE)
	@Column(name = "FEC_FECHALIM")
	private Date fecFechaLimite;

	@Column(name = "CVE_STATUS")
	private Integer cveStatus;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "FEC_FECHAREG")
	private Date fecFechaRegistro;

	@Column(name = "CVE_USUARIO")
	private String cveUsuario;

	@Column(name = "CVE_FK_PATRON")
	private Long cvePatron;

	@Column(name = "ID_MOTIVORECHAZO")
	private Integer cveMotivoRechazo;

	@Column(name = "CVE_NROREGOBRA")
	private BigDecimal cveNumeroRegObra;

	@Column(name = "TX_OBSERVARECHAZO")
	private String observacionesRechazo;

//	/**
//	 * Auditor asignado en la solicitud de la correccian en caso de que no
//	 * exista poner 0
//	 */
//	@Column(name = "CVE_AUDITOR_ASIGNADO")
//	private Long cveAuditorAsignado;
	
	/**
	 * Auditor asignado en la solicitud de la correccian en caso de que no
	 * exista poner 0
	 */
	@Column(name = "CVE_AUDITOR_ASIGNADO")
	private String cveAuditorAsignado;

	/**
	 * 0.- Diferente a construccian 1.- Construccian
	 */
	@Column(name = "ID_TIPO_SOLICITUD")
	private Integer idTipoSolicitud;

	/**
	 * En caso de rechazar la solicitud se debra indicar la referencia de
	 * rechazo
	 */
	@Column(name = "TX_REF_RECHAZO")
	private String txRefRechazo;
	
	
	
	@Column(name = "CVE_ID_SOLICITUD_BDTU")
	private Integer cveIdSolicitudBDTU;
	
	

	public AbstractCrtSolicitudcorr(Integer cveSolicitudCorr, String nuFolio,
			Long cvePatron, Date fecFechaPeriodoIni, Date fecFechaPeriodoFin,
			Date fecFechaElacoracionCorreccion) {
		this.cveSolicitudCorr = cveSolicitudCorr;
		this.nuFolio = nuFolio;
		this.cvePatron = cvePatron;
		this.fecFechaPeriodoIni = fecFechaPeriodoIni;
		this.fecFechaPeriodoFin = fecFechaPeriodoFin;
		this.fecFechaElacoracionCorreccion = fecFechaElacoracionCorreccion;
	}

	public AbstractCrtSolicitudcorr() {
	}

	public String getObservacionesRechazo() {
		return observacionesRechazo;
	}

	public void setObservacionesRechazo(String observacionesRechazo) {
		this.observacionesRechazo = observacionesRechazo;
	}

	public BigDecimal getCveNumeroRegObra() {
		return cveNumeroRegObra;
	}

	public void setCveNumeroRegObra(BigDecimal cveNumeroRegObra) {
		this.cveNumeroRegObra = cveNumeroRegObra;
	}

	public Integer getCveSolicitudCorr() {
		return cveSolicitudCorr;
	}

	public void setCveSolicitudCorr(Integer cveSolicitudCorr) {
		this.cveSolicitudCorr = cveSolicitudCorr;
	}

	public String getNuFolio() {
		return nuFolio;
	}

	public void setNuFolio(String nuFolio) {
		this.nuFolio = nuFolio;
	}

	public Long getCveSubdelegacion() {
		return cveSubdelegacion;
	}

	public void setCveSubdelegacion(Long cveSubdelegacion) {
		this.cveSubdelegacion = cveSubdelegacion;
	}

	public Integer getCveTipoCorreccion() {
		return cveTipoCorreccion;
	}

	public void setCveTipoCorreccion(Integer cveTipoCorreccion) {
		this.cveTipoCorreccion = cveTipoCorreccion;
	}

	public Date getFecFechaRecepcionOficio() {
		return fecFechaRecepcionOficio;
	}

	public void setFecFechaRecepcionOficio(Date fecFechaRecepcionOficio) {
		this.fecFechaRecepcionOficio = fecFechaRecepcionOficio;
	}

	public Date getFecFechaPeriodoIni() {
		return fecFechaPeriodoIni;
	}

	public void setFecFechaPeriodoIni(Date fecFechaPeriodoIni) {
		this.fecFechaPeriodoIni = fecFechaPeriodoIni;
	}

	public Date getFecFechaPeriodoFin() {
		return fecFechaPeriodoFin;
	}

	public void setFecFechaPeriodoFin(Date fecFechaPeriodoFin) {
		this.fecFechaPeriodoFin = fecFechaPeriodoFin;
	}

	public Integer getNumTrabajadores() {
		return numTrabajadores;
	}

	public void setNumTrabajadores(Integer numTrabajadores) {
		this.numTrabajadores = numTrabajadores;
	}

	public Date getFecFechaElacoracionCorreccion() {
		return fecFechaElacoracionCorreccion;
	}

	public void setFecFechaElacoracionCorreccion(
			Date fecFechaElacoracionCorreccion) {
		this.fecFechaElacoracionCorreccion = fecFechaElacoracionCorreccion;
	}

	public String getTxRepresentanteLegal() {
		return txRepresentanteLegal;
	}

	public void setTxRepresentanteLegal(String txRepresentanteLegal) {
		this.txRepresentanteLegal = txRepresentanteLegal;
	}

	public String getLugar() {
		return lugar;
	}

	public void setLugar(String lugar) {
		this.lugar = lugar;
	}

	public Date getFecFechaLimite() {
		return fecFechaLimite;
	}

	public void setFecFechaLimite(Date fecFechaLimite) {
		this.fecFechaLimite = fecFechaLimite;
	}

	public Integer getCveStatus() {
		return cveStatus;
	}

	public void setCveStatus(Integer cveStatus) {
		this.cveStatus = cveStatus;
	}

	public Date getFecFechaRegistro() {
		return fecFechaRegistro;
	}

	public void setFecFechaRegistro(Date fecFechaRegistro) {
		this.fecFechaRegistro = fecFechaRegistro;
	}

	public String getCveUsuario() {
		return cveUsuario;
	}

	public void setCveUsuario(String cveUsuario) {
		this.cveUsuario = cveUsuario;
	}

	public Long getCvePatron() {
		return cvePatron;
	}

	public void setCvePatron(Long cvePatron) {
		this.cvePatron = cvePatron;
	}

	public Integer getCveMotivoRechazo() {
		return cveMotivoRechazo;
	}

	public void setCveMotivoRechazo(Integer cveMotivoRechazo) {
		this.cveMotivoRechazo = cveMotivoRechazo;
	}

	/**
	 * Indica si la solicitud de la correccian es de obra o diferente a ella
	 * 
	 * @return 0 diferente de obra, 1 construccian/obra
	 */
	public Integer getIdTipoSolicitud() {
		return idTipoSolicitud;
	}

	/**
	 * Permite ingresar un identificador que marca a la solicutud de la
	 * correccian como obra o deferente de ella. 0.- Diferente de obra 1.- Obra
	 * 
	 * @param idTipoSolicitud
	 */
	public void setIdTipoSolicitud(Integer idTipoSolicitud) {
		this.idTipoSolicitud = idTipoSolicitud;
	}

//	/**
//	 * Permite obtener el auditor asignado a la solicitud de la correccian.
//	 * 
//	 * @return id auditor , 0 en caso de que no exista
//	 */
//	public Long getCveAuditorAsignado() {
//		if (this.cveAuditorAsignado == null
//				|| this.cveAuditorAsignado.equals("")) {
//			this.cveAuditorAsignado = 0L;
//		}
//		return cveAuditorAsignado;
//	}
//
//	/**
//	 * Otorga al auditor asignado a la solicitud de la correccian en caso de que
//	 * este no exista sera cero (0)
//	 * 
//	 * @param cveAuditorAsignado
//	 */
//	public void setCveAuditorAsignado(Long cveAuditorAsignado) {
//		this.cveAuditorAsignado = cveAuditorAsignado;
//	}
	
	/**
	 * Permite obtener el auditor asignado a la solicitud de la correccian.
	 * 
	 * @return id auditor , 0 en caso de que no exista
	 */
	public String getCveAuditorAsignado() {
		if (this.cveAuditorAsignado == null
				|| this.cveAuditorAsignado.equals("")) {
			this.cveAuditorAsignado = "";
		}
		return cveAuditorAsignado;
	}

	/**
	 * Otorga al auditor asignado a la solicitud de la correccian en caso de que
	 * este no exista sera cero (0)
	 * 
	 * @param cveAuditorAsignado
	 */
	public void setCveAuditorAsignado(String cveAuditorAsignado) {
		this.cveAuditorAsignado = cveAuditorAsignado;
	}

	/**
	 * Otorga la referencia que indica el rechazo de la solicitud
	 * 
	 * @return
	 */
	public String getTxRefRechazo() {
		return txRefRechazo;
	}

	/**
	 * Permite ingresar una referencia de rechazo a la solicitud.
	 * 
	 * @param txRefRechazo
	 */
	public void setTxRefRechazo(String txRefRechazo) {
		this.txRefRechazo = txRefRechazo;
	}

	public Date getFecFechaAutorizacionCorreccion() {
		return fecFechaAutorizacionCorreccion;
	}

	public void setFecFechaAutorizacionCorreccion(
			Date fecFechaAutorizacionCorreccion) {
		this.fecFechaAutorizacionCorreccion = fecFechaAutorizacionCorreccion;
	}

	public Integer getIdFormaPresenta() {
		return idFormaPresenta;
	}

	public void setIdFormaPresenta(Integer idFormaPresenta) {
		this.idFormaPresenta = idFormaPresenta;
	}

	public Integer getCveIdSolicitudBDTU() {
		return cveIdSolicitudBDTU;
	}

	public void setCveIdSolicitudBDTU(Integer cveIdSolicitudBDTU) {
		this.cveIdSolicitudBDTU = cveIdSolicitudBDTU;
	}

	
	
	
}