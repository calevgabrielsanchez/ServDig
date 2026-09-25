package mx.gob.imss.ctirss.correccion.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
/**
 * Modelo para la tabla CRT_AUDITOR_ASIGNADO
 * @author Enrique Duran Jimenez
 * @since 29/05/2012
 */
import mx.gob.imss.ctirss.correccion.framework.base.model.AbstractModel;

@SuppressWarnings("serial")
@Entity
@Table(name="CRT_AUDITOR_ASIGNADO")
@JsonIgnoreProperties(ignoreUnknown = true)
public class CrtAuditorAsignado extends AbstractModel{
	
	@Id
	@SequenceGenerator(name="CVE_AUDITOR_ASIGNADO_GENERATOR", sequenceName="CRS_AUDITOR_ASIGNADO")
	@GeneratedValue(generator="CVE_AUDITOR_ASIGNADO_GENERATOR")
	@Column(name = "CVE_AUDITOR_ASIGNADO")
	private Long cveAuditorAsignado;
	
	@Column(name = "CVE_AUDITOR")
	private Integer cveAuditor;
	
	@Column(name = "CVE_INVITACION")
	private Long cveInvitacion;
	
	@Column(name = "CVE_PROMOCION")
	private Long cvePromocion;
	
	@Column(name = "CVE_SOLICITUDCORR")
	private Long cveSolicitudCorr;
	
    @Temporal( TemporalType.DATE)
	@Column(name="FEC_FECHASIGNACIONINI")
	private Date fecFechaAsignacionIni;
    
    @Temporal( TemporalType.DATE)
    @Column(name = "FEC_FECHASIGNACIONFIN")
	private Date fecFechaAsignacionFin;
    
//	@Column(name = "CVE_USUARIO")
//	private Long cveUsuario;
    
    @Column(name = "CVE_USUARIO")
	private String cveUsuario;
	
	@Temporal( TemporalType.TIMESTAMP)
	@Column(name="FEC_FECHAREG")
	private Date fecFechaReg;
	
	@Column(name = "CVE_USUARIO_ASIGNADO")
	private String cveAuditorUsuarioAsignado;
	
	@Transient
	private String nombreCompleto;
	
	@Transient
	private String total;
	
	@Transient
	private Long sDelegOrig;
	
	@Transient
	private String folio;
	
	@Transient
	private String regPatronal;
	
	@Transient
	private String razonSocial;
	
	@Transient
	private String fechaIni;
	
	@Transient
	private String fechaFin;
	
	@Transient
	private String fechaAsignacion;
	
	@Transient
	private String cveUsuarioAsignado;
	

	/**
	 * @return the cveAuditorAsignado
	 */
	public Long getCveAuditorAsignado() {
		return cveAuditorAsignado;
	}

	/**
	 * @param cveAuditorAsignado the cveAuditorAsignado to set
	 */
	public void setCveAuditorAsignado(Long cveAuditorAsignado) {
		this.cveAuditorAsignado = cveAuditorAsignado;
	}

	/**
	 * @return the cveAuditor
	 */
	public Integer getCveAuditor() {
		return cveAuditor;
	}

	/**
	 * @param cveAuditor the cveAuditor to set
	 */
	public void setCveAuditor(Integer cveAuditor) {
		this.cveAuditor = cveAuditor;
	}

	/**
	 * @return the cveInvitacion
	 */
	public Long getCveInvitacion() {
		return cveInvitacion;
	}

	/**
	 * @param cveInvitacion the cveInvitacion to set
	 */
	public void setCveInvitacion(Long cveInvitacion) {
		this.cveInvitacion = cveInvitacion;
	}

	/**
	 * @return the cvePromocion
	 */
	public Long getCvePromocion() {
		return cvePromocion;
	}

	/**
	 * @param cvePromocion the cvePromocion to set
	 */
	public void setCvePromocion(Long cvePromocion) {
		this.cvePromocion = cvePromocion;
	}

	/**
	 * @return the cveSolicitudCorr
	 */
	public Long getCveSolicitudCorr() {
		return cveSolicitudCorr;
	}

	/**
	 * @param cveSolicitudCorr the cveSolicitudCorr to set
	 */
	public void setCveSolicitudCorr(Long cveSolicitudCorr) {
		this.cveSolicitudCorr = cveSolicitudCorr;
	}

	/**
	 * @return the fecFechaAsignacionIni
	 */
	public Date getFecFechaAsignacionIni() {
		return fecFechaAsignacionIni;
	}

	/**
	 * @param fecFechaAsignacionIni the fecFechaAsignacionIni to set
	 */
	public void setFecFechaAsignacionIni(Date fecFechaAsignacionIni) {
		this.fecFechaAsignacionIni = fecFechaAsignacionIni;
	}

	/**
	 * @return the fecFechaAsignacionFin
	 */
	public Date getFecFechaAsignacionFin() {
		return fecFechaAsignacionFin;
	}

	/**
	 * @param fecFechaAsignacionFin the fecFechaAsignacionFin to set
	 */
	public void setFecFechaAsignacionFin(Date fecFechaAsignacionFin) {
		this.fecFechaAsignacionFin = fecFechaAsignacionFin;
	}

//	/**
//	 * @return the cveUsuario
//	 */
//	public Long getCveUsuario() {
//		return cveUsuario;
//	}
//
//	/**
//	 * @param cveUsuario the cveUsuario to set
//	 */
//	public void setCveUsuario(Long cveUsuario) {
//		this.cveUsuario = cveUsuario;
//	}
	
	/**
	 * @return the cveUsuario
	 */
	public String getCveUsuario() {
		return cveUsuario;
	}

	/**
	 * @param cveUsuario the cveUsuario to set
	 */
	public void setCveUsuario(String cveUsuario) {
		this.cveUsuario = cveUsuario;
	}

	/**
	 * @return the fecFechaReg
	 */
	public Date getFecFechaReg() {
		return fecFechaReg;
	}

	/**
	 * @param fecFechaReg the fecFechaReg to set
	 */
	public void setFecFechaReg(Date fecFechaReg) {
		this.fecFechaReg = fecFechaReg;
	}

	/**
	 * @return the nombreCompleto
	 */
	public String getNombreCompleto() {
		return nombreCompleto;
	}

	/**
	 * @param nombreCompleto the nombreCompleto to set
	 */
	public void setNombreCompleto(String nombreCompleto) {
		this.nombreCompleto = nombreCompleto;
	}

	/**
	 * @return the total
	 */
	public String getTotal() {
		return total;
	}

	/**
	 * @param total the total to set
	 */
	public void setTotal(String total) {
		this.total = total;
	}

	/**
	 * @return the sDelegOrig
	 */
	public Long getsDelegOrig() {
		return sDelegOrig;
	}

	/**
	 * @param sDelegOrig the sDelegOrig to set
	 */
	public void setsDelegOrig(Long sDelegOrig) {
		this.sDelegOrig = sDelegOrig;
	}

	/**
	 * @return the folio
	 */
	public String getFolio() {
		return folio;
	}

	/**
	 * @param folio the folio to set
	 */
	public void setFolio(String folio) {
		this.folio = folio;
	}

	/**
	 * @return the regPatronal
	 */
	public String getRegPatronal() {
		return regPatronal;
	}

	/**
	 * @param regPatronal the regPatronal to set
	 */
	public void setRegPatronal(String regPatronal) {
		this.regPatronal = regPatronal;
	}

	/**
	 * @return the razonSocial
	 */
	public String getRazonSocial() {
		return razonSocial;
	}

	/**
	 * @param razonSocial the razonSocial to set
	 */
	public void setRazonSocial(String razonSocial) {
		this.razonSocial = razonSocial;
	}

	/**
	 * @return the fechaIni
	 */
	public String getFechaIni() {
		return fechaIni;
	}

	/**
	 * @param fechaIni the fechaIni to set
	 */
	public void setFechaIni(String fechaIni) {
		this.fechaIni = fechaIni;
	}

	/**
	 * @return the fechaFin
	 */
	public String getFechaFin() {
		return fechaFin;
	}

	/**
	 * @param fechaFin the fechaFin to set
	 */
	public void setFechaFin(String fechaFin) {
		this.fechaFin = fechaFin;
	}

	/**
	 * @return the fechaAsignacion
	 */
	public String getFechaAsignacion() {
		return fechaAsignacion;
	}

	/**
	 * @param fechaAsignacion the fechaAsignacion to set
	 */
	public void setFechaAsignacion(String fechaAsignacion) {
		this.fechaAsignacion = fechaAsignacion;
	}

	/**
	 * @return the cveUsuarioAsignado
	 */
	public String getCveUsuarioAsignado() {
		return cveUsuarioAsignado;
	}

	/**
	 * @param cveUsuarioAsignado the cveUsuarioAsignado to set
	 */
	public void setCveUsuarioAsignado(String cveUsuarioAsignado) {
		this.cveUsuarioAsignado = cveUsuarioAsignado;
	}

	public String getCveAuditorUsuarioAsignado() {
		return cveAuditorUsuarioAsignado;
	}

	public void setCveAuditorUsuarioAsignado(String cveAuditorUsuarioAsignado) {
		this.cveAuditorUsuarioAsignado = cveAuditorUsuarioAsignado;
	}

}
