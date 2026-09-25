package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * The persistent class for the DIV_SOLICITUDCONCLUIDAS database table.
 * 
 */
@Entity
@Table(name="DIV_SOLICITUDCONCLUIDAS")
public class DivSolicitudConcluida implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="CVE_ID_SOLICITUD")
	private BigDecimal cveIdSolicitud;
	
	@Column(name="CVE_ID_ANALISIS")
	private BigDecimal cveIdAnalisis;

	@Column(name="REG_PATRON")
	private String regPatron;

	@Column(name="NUM_MODALIDAD")
	private String numModalidad;

	@Column(name="DIG_VER")
	private String digVer;
	
	@Column(name="REG_PATRON_COMPLETO")
	private String regPatronCompleto;

	@Column(name="DES_NOMBRE_COMERCIAL")
	private String nombreComercial;

	@Column(name="DES_RAZON_SOCIAL")
	private String razonSocial;
	
	@Column(name="NOM_NOMBRE")
	private String nombre;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_PRESENTACION")
	private Date fecPresentacion;

	@Column(name="DES_CAUSAS_ANALISIS")
	private String desCausasAnalisis;

	@Column(name="CVE_ID_ESTATUS_ANALISIS")
	private BigDecimal cveIdEstatusAnalisis;

	@Column(name="CVE_ID_TIPO_CAUSA")
	private BigDecimal cveIdTipoCausa;

	@Column(name="CVE_ID_ESTADO_TRAMITE")
	private BigDecimal cveIdEstadoTramite;

	@Column(name="DES_ESTADO_TRAMITE")
	private String desEstadoTramite;

	@Column(name="CVE_ID_TIPO_TRAMITE")
	private BigDecimal cveIdTipoTramite;

	@Column(name="DES_TIPO_TRAMITE")
	private String desTipoTramite;
	
	@Column(name="CVE_ID_DELEGACION")
	private BigDecimal cveIdDelegacion;
	
	@Column(name="DES_DELEG")
	private String desDeleg;
	
	@Column(name="CVE_ID_SUBDELEGACION")
	private BigDecimal cveIdSubdelegacion;
    
	@Column(name="DES_SUBDELEGACION")
	private String desSubdelegacion;
	
	@Column(name="CVE_ID_TIPO_PERSONA")
	private BigDecimal cveIdTipoPersona;

//	@Column(name="CVE_ID_USUARIO")
//	private BigDecimal cveIdUsuario;

	@Column(name="CVE_USUARIO_SSO")
	private String cveUsuarioSso;

	@Column(name="IND_PRESTA_SERVICIO_PERSONAL")
	private BigDecimal indPrestaServicioPersonal;

	@Column(name="IND_REG_PAT_CLASE")
	private BigDecimal indRegPatClase;

	@Column(name="CVE_ID_GRUPO_ANALISIS_CE")
	private BigDecimal cveIdGrupoAnalisisCe;

//	@Column(name="NOM_USUARIO_SISTEMA")
//	private String nomUsuarioSistema;


    public DivSolicitudConcluida() {
    }

	public BigDecimal getCveIdAnalisis() {
		return this.cveIdAnalisis;
	}

	public void setCveIdAnalisis(BigDecimal cveIdAnalisis) {
		this.cveIdAnalisis = cveIdAnalisis;
	}

	public BigDecimal getCveIdDelegacion() {
		return this.cveIdDelegacion;
	}

	public void setCveIdDelegacion(BigDecimal cveIdDelegacion) {
		this.cveIdDelegacion = cveIdDelegacion;
	}

	public BigDecimal getCveIdEstadoTramite() {
		return this.cveIdEstadoTramite;
	}

	public void setCveIdEstadoTramite(BigDecimal cveIdEstadoTramite) {
		this.cveIdEstadoTramite = cveIdEstadoTramite;
	}

	public BigDecimal getCveIdEstatusAnalisis() {
		return this.cveIdEstatusAnalisis;
	}

	public void setCveIdEstatusAnalisis(BigDecimal cveIdEstatusAnalisis) {
		this.cveIdEstatusAnalisis = cveIdEstatusAnalisis;
	}

	public BigDecimal getCveIdSolicitud() {
		return this.cveIdSolicitud;
	}

	public void setCveIdSolicitud(BigDecimal cveIdSolicitud) {
		this.cveIdSolicitud = cveIdSolicitud;
	}

	public BigDecimal getCveIdSubdelegacion() {
		return this.cveIdSubdelegacion;
	}

	public void setCveIdSubdelegacion(BigDecimal cveIdSubdelegacion) {
		this.cveIdSubdelegacion = cveIdSubdelegacion;
	}

	public BigDecimal getCveIdTipoCausa() {
		return this.cveIdTipoCausa;
	}

	public void setCveIdTipoCausa(BigDecimal cveIdTipoCausa) {
		this.cveIdTipoCausa = cveIdTipoCausa;
	}

	public BigDecimal getCveIdTipoPersona() {
		return this.cveIdTipoPersona;
	}

	public void setCveIdTipoPersona(BigDecimal cveIdTipoPersona) {
		this.cveIdTipoPersona = cveIdTipoPersona;
	}

	public BigDecimal getCveIdTipoTramite() {
		return this.cveIdTipoTramite;
	}

	public void setCveIdTipoTramite(BigDecimal cveIdTipoTramite) {
		this.cveIdTipoTramite = cveIdTipoTramite;
	}

//	public BigDecimal getCveIdUsuario() {
//		return this.cveIdUsuario;
//	}
//
//	public void setCveIdUsuario(BigDecimal cveIdUsuario) {
//		this.cveIdUsuario = cveIdUsuario;
//	}

	public String getDesCausasAnalisis() {
		return this.desCausasAnalisis;
	}

	public void setDesCausasAnalisis(String desCausasAnalisis) {
		this.desCausasAnalisis = desCausasAnalisis;
	}

	public String getDesDeleg() {
		return this.desDeleg;
	}

	public void setDesDeleg(String desDeleg) {
		this.desDeleg = desDeleg;
	}

	public String getDesEstadoTramite() {
		return this.desEstadoTramite;
	}

	public void setDesEstadoTramite(String desEstadoTramite) {
		this.desEstadoTramite = desEstadoTramite;
	}

	public String getDesSubdelegacion() {
		return this.desSubdelegacion;
	}

	public void setDesSubdelegacion(String desSubdelegacion) {
		this.desSubdelegacion = desSubdelegacion;
	}

	public String getDesTipoTramite() {
		return this.desTipoTramite;
	}

	public void setDesTipoTramite(String desTipoTramite) {
		this.desTipoTramite = desTipoTramite;
	}

	public Date getFecPresentacion() {
		return this.fecPresentacion;
	}

	public void setFecPresentacion(Date fecPresentacion) {
		this.fecPresentacion = fecPresentacion;
	}

	public BigDecimal getIndPrestaServicioPersonal() {
		return this.indPrestaServicioPersonal;
	}

	public void setIndPrestaServicioPersonal(BigDecimal indPrestaServicioPersonal) {
		this.indPrestaServicioPersonal = indPrestaServicioPersonal;
	}

	public BigDecimal getIndRegPatClase() {
		return this.indRegPatClase;
	}

	public void setIndRegPatClase(BigDecimal indRegPatClase) {
		this.indRegPatClase = indRegPatClase;
	}

//	public String getNomUsuarioSistema() {
//		return this.nomUsuarioSistema;
//	}
//
//	public void setNomUsuarioSistema(String nomUsuarioSistema) {	
//		this.nomUsuarioSistema = nomUsuarioSistema;
//	}

	public String getNombreComercial() {
		return this.nombreComercial;
	}

	public void setNombreComercial(String nombreComercial) {
		this.nombreComercial = nombreComercial;
	}

	public String getRegPatron() {
		return this.regPatron;
	}

	public void setRegPatron(String regPatron) {
		this.regPatron = regPatron;
	}

	public String getRazonSocial() {
		return razonSocial;
	}

	public void setRazonSocial(String razonSocial) {
		this.razonSocial = razonSocial;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getDigVer() {
		return digVer;
	}

	public void setDigVer(String digVer) {
		this.digVer = digVer;
	}

	public BigDecimal getCveIdGrupoAnalisisCe() {
		return cveIdGrupoAnalisisCe;
	}

	public void setCveIdGrupoAnalisisCe(BigDecimal cveIdGrupoAnalisisCe) {
		this.cveIdGrupoAnalisisCe = cveIdGrupoAnalisisCe;
	}

	public String getRegPatronCompleto() {
		return regPatronCompleto;
	}

	public void setRegPatronCompleto(String regPatronCompleto) {
		this.regPatronCompleto = regPatronCompleto;
	}

	public String getNumModalidad() {
		return numModalidad;
	}

	public void setNumModalidad(String numModalidad) {
		this.numModalidad = numModalidad;
	}

	public String getCveUsuarioSso() {
		return cveUsuarioSso;
	}

	public void setCveUsuarioSso(String cveUsuarioSso) {
		this.cveUsuarioSso = cveUsuarioSso;
	}
		
}