package mx.imss.estrados.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import mx.imss.estrados.dto.NotificacionesDTO;

@Entity
@Table(name="NEE_NOTIFICACIONES")
public class NeeNotificaciones implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2421344814693738258L;

	/**
	 * 
	 */
	

	public NeeNotificaciones() {
	}
	
	@Id
	
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="NEE_NOTIFICACIONES_CVENOTIFICACIONES_GENERATOR" )
	@SequenceGenerator(name="NEE_NOTIFICACIONES_CVENOTIFICACIONES_GENERATOR", sequenceName="SEQ_NEE_CVE_NOTIFICACIONES", allocationSize=1 )
	@Column(name="CVE_NOTIFICACIONES")
	private long cveNotificaciones;
	
	
	@Column(name="CVE_DEPTO")
	private Integer cveDepto;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="CVE_AREA_RESP_NOTIF")
	private NeeCatAreaRespNotif neeCatAreaRespNotif;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="CVE_ID_DELEGACION")
	private NeeCatDelegacion neeCatDelegacion;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="CVE_ID_SUBDELEGACION")
	private NeeCatSubdelegacion neeCatSubdelegacion;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="CVE_SUJETO_A_NOTIFICAR")
	private NeeCatSujetoANotificar neeCatSujetoANotificar;
	
	@Column(name="REGISTRO_PATRONAL")
	private String registroPatronal;
	
	@Column(name="DES_NUM_REG_CPA")
	private String desNumRegCpa;
	
	@Column(name="RAZON_SOCIAL")
	private String razonSocial;
	
	@Column(name="DES_DOMICILIO")
	private String desDomicilio;
	
	@Column(name="ID_DOMICILIO")
	private Integer idDomicilio;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="CVE_STATUS")
	private NeeCatStatus neeCatStatus;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="CVE_TIPODOCTO")
	private NeeCatTipodocumento neeCatTipodocumento;
	
	@Temporal(TemporalType.DATE)
	@Column(name="FEC_PUBLICACION")
	private Date fecPublicacion;
	
	@Temporal(TemporalType.DATE)
	@Column(name="FEC_INICIO_PUBLICACION")
	private Date fecInicioPublicacion;
	
	@Temporal(TemporalType.DATE)
	@Column(name="FEC_FIN_PUBLICACION")
	private Date fecFinPublicacion;
	
	@Temporal(TemporalType.DATE)
	@Column(name="FEC_RETIRO_PUBLICACION")
	private Date fecRetiroPublicacion;
	
	@Column(name="CVE_USUARIO")
	private String cveUsuario;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="FEC_REGISTRO")
	private Date fecRegistro;
	
	@Column(name="DES_REF_ACUSE")
	private String desRefAcuse;
	
	@Column(name="DES_REF_PUBLICACION")
	private String desRefPublicacion;
	
	@Column(name="DES_REF_RETIRO")
	private String desRefRetiro;
	
	@Column(name="CORREO_USUARIO")
	private String correoUsuario;
	
	@OneToMany(mappedBy = "neeNotificaciones")
	private List<NeeDocumentosAdjuntos> listNeeDocumentosAdjuntos;

	@Transient
	private NotificacionesDTO notificacionesDTO;

	public NotificacionesDTO getNotificacionesDTO() {
		return notificacionesDTO;
	}

	public void setNotificacionesDTO(NotificacionesDTO notificacionesDTO) {
		this.notificacionesDTO = notificacionesDTO;
	}
	
	public long getCveNotificaciones() {
		return cveNotificaciones;
	}

	public void setCveNotificaciones(long cveNotificaciones) {
		this.cveNotificaciones = cveNotificaciones;
	}

	public Integer getCveDepto() {
		return cveDepto;
	}

	public void setCveDepto(Integer cveDepto) {
		this.cveDepto = cveDepto;
	}

	public NeeCatAreaRespNotif getNeeCatAreaRespNotif() {
		return neeCatAreaRespNotif;
	}

	public void setNeeCatAreaRespNotif(NeeCatAreaRespNotif neeCatAreaRespNotif) {
		this.neeCatAreaRespNotif = neeCatAreaRespNotif;
	}

	public NeeCatDelegacion getNeeCatDelegacion() {
		return neeCatDelegacion;
	}

	public void setNeeCatDelegacion(NeeCatDelegacion neeCatDelegacion) {
		this.neeCatDelegacion = neeCatDelegacion;
	}

	public NeeCatSubdelegacion getNeeCatSubdelegacion() {
		return neeCatSubdelegacion;
	}

	public void setNeeCatSubdelegacion(NeeCatSubdelegacion neeCatSubdelegacion) {
		this.neeCatSubdelegacion = neeCatSubdelegacion;
	}

	public NeeCatSujetoANotificar getNeeCatSujetoANotificar() {
		return neeCatSujetoANotificar;
	}

	public void setNeeCatSujetoANotificar(
			NeeCatSujetoANotificar neeCatSujetoANotificar) {
		this.neeCatSujetoANotificar = neeCatSujetoANotificar;
	}

	public String getRegistroPatronal() {
		return registroPatronal;
	}

	public void setRegistroPatronal(String registroPatronal) {
		this.registroPatronal = registroPatronal;
	}

	public String getDesNumRegCpa() {
		return desNumRegCpa;
	}

	public void setDesNumRegCpa(String desNumRegCpa) {
		this.desNumRegCpa = desNumRegCpa;
	}

	public String getRazonSocial() {
		return razonSocial;
	}

	public void setRazonSocial(String razonSocial) {
		this.razonSocial = razonSocial;
	}

	public String getDesDomicilio() {
		return desDomicilio;
	}

	public void setDesDomicilio(String desDomicilio) {
		this.desDomicilio = desDomicilio;
	}

	public Integer getIdDomicilio() {
		return idDomicilio;
	}

	public void setIdDomicilio(Integer idDomicilio) {
		this.idDomicilio = idDomicilio;
	}

	public NeeCatStatus getNeeCatStatus() {
		return neeCatStatus;
	}

	public void setNeeCatStatus(NeeCatStatus neeCatStatus) {
		this.neeCatStatus = neeCatStatus;
	}

	public NeeCatTipodocumento getNeeCatTipodocumento() {
		return neeCatTipodocumento;
	}

	public void setNeeCatTipodocumento(NeeCatTipodocumento neeCatTipodocumento) {
		this.neeCatTipodocumento = neeCatTipodocumento;
	}

	public Date getFecPublicacion() {
		return fecPublicacion;
	}

	public void setFecPublicacion(Date fecPublicacion) {
		this.fecPublicacion = fecPublicacion;
	}

	public Date getFecInicioPublicacion() {
		return fecInicioPublicacion;
	}

	public void setFecInicioPublicacion(Date fecInicioPublicacion) {
		this.fecInicioPublicacion = fecInicioPublicacion;
	}

	public Date getFecFinPublicacion() {
		return fecFinPublicacion;
	}

	public void setFecFinPublicacion(Date fecFinPublicacion) {
		this.fecFinPublicacion = fecFinPublicacion;
	}

	public Date getFecRetiroPublicacion() {
		return fecRetiroPublicacion;
	}

	public void setFecRetiroPublicacion(Date fecRetiroPublicacion) {
		this.fecRetiroPublicacion = fecRetiroPublicacion;
	}

	public String getCveUsuario() {
		return cveUsuario;
	}

	public void setCveUsuario(String cveUsuario) {
		this.cveUsuario = cveUsuario;
	}


	public Date getFecRegistro() {
		return fecRegistro;
	}

	public void setFecRegistro(Date fecRegistro) {
		this.fecRegistro = fecRegistro;
	}

	public String getDesRefAcuse() {
		return desRefAcuse;
	}

	public void setDesRefAcuse(String desRefAcuse) {
		this.desRefAcuse = desRefAcuse;
	}

	public String getDesRefPublicacion() {
		return desRefPublicacion;
	}

	public void setDesRefPublicacion(String desRefPublicacion) {
		this.desRefPublicacion = desRefPublicacion;
	}

	public String getDesRefRetiro() {
		return desRefRetiro;
	}

	public void setDesRefRetiro(String desRefRetiro) {
		this.desRefRetiro = desRefRetiro;
	}

	public List<NeeDocumentosAdjuntos> getListNeeDocumentosAdjuntos() {
		return listNeeDocumentosAdjuntos;
	}

	public void setListNeeDocumentosAdjuntos(
			List<NeeDocumentosAdjuntos> listNeeDocumentosAdjuntos) {
		this.listNeeDocumentosAdjuntos = listNeeDocumentosAdjuntos;
	}

	public String getCorreoUsuario() {
		return correoUsuario;
	}

	public void setCorreoUsuario(String correoUsuario) {
		this.correoUsuario = correoUsuario;
	}

}
