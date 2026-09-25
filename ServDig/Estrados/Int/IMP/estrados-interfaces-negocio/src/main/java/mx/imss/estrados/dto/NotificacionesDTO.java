package mx.imss.estrados.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class NotificacionesDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -652959734133220118L;

	/**
	 * 
	 */
	

	public NotificacionesDTO() {
		this.departamentoDTO = new DepartamentoDTO();
		this.areaRespNotifDTO = new AreaRespNotifDTO();
		this.delegacionDTO = new DelegacionDTO();
		this.subdelegacionDTO = new SubdelegacionDTO();
		this.sujetoANotificarDTO = new SujetoANotificarDTO();
		this.tipodocumentoDTO = new TipodocumentoDTO();
		this.areanormativaDTO = new AreanormativaDTO();
	}

	public NotificacionesDTO(long cveNotificaciones,
			DepartamentoDTO departamentoDTO, AreaRespNotifDTO areaRespNotifDTO,
			DelegacionDTO delegacionDTO, SubdelegacionDTO subdelegacionDTO,
			SujetoANotificarDTO sujetoANotificarDTO, String registroPatronal,
			String desNumRegCpa, String razonSocial, String desDomicilio,
			Integer idDomicilio, StatusDTO statusDTO, String desNumOficio,
			TipodocumentoDTO tipodocumentoDTO, Date fecPublicacion,
			Date fecInicioPublicacion, Date fecFinPublicacion,
			Date fecRetiroPublicacion, String cveUsuario, Date fecRegistro,
			String desRefAcuse, String desRefPublicacion, String desRefRetiro) {
		super();
		this.cveNotificaciones = cveNotificaciones;
		this.departamentoDTO = departamentoDTO;
		this.areaRespNotifDTO = areaRespNotifDTO;
		this.delegacionDTO = delegacionDTO;
		this.subdelegacionDTO = subdelegacionDTO;
		this.sujetoANotificarDTO = sujetoANotificarDTO;
		this.registroPatronal = registroPatronal;
		this.desNumRegCpa = desNumRegCpa;
		this.razonSocial = razonSocial;
		this.desDomicilio = desDomicilio;
		this.idDomicilio = idDomicilio;
		this.statusDTO = statusDTO;
		this.tipodocumentoDTO = tipodocumentoDTO;
		this.fecPublicacion = fecPublicacion;
		this.fecInicioPublicacion = fecInicioPublicacion;
		this.fecFinPublicacion = fecFinPublicacion;
		this.fecRetiroPublicacion = fecRetiroPublicacion;
		this.cveUsuario = cveUsuario;
		this.fecRegistro = fecRegistro;
		this.desRefAcuse = desRefAcuse;
		this.desRefPublicacion = desRefPublicacion;
		this.desRefRetiro = desRefRetiro;
	}

	private long cveNotificaciones;
	private DepartamentoDTO departamentoDTO;
	private AreaRespNotifDTO areaRespNotifDTO;
	private DelegacionDTO delegacionDTO;
	private SubdelegacionDTO subdelegacionDTO;
	private SujetoANotificarDTO sujetoANotificarDTO;
	private AreanormativaDTO areanormativaDTO;
	private String registroPatronal;
	private String desNumRegCpa;
	private String razonSocial;
	private String desDomicilio;
	private Integer idDomicilio;
	private StatusDTO statusDTO;
	private String desNumOficio;
	private TipodocumentoDTO tipodocumentoDTO;
	private Date fecPublicacion;
	private Date fecInicioPublicacion;
	private Date fecFinPublicacion;
	private Date fecRetiroPublicacion;

	private String fecPublicacionCadena;
	private String fecInicioPublicacionCadena;
	private String fecFinPublicacionCadena;
	private String fecRetiroPublicacionCadena;

	private String cveUsuario;
	private Date fecRegistro;
	private String desRefAcuse;
	private String desRefPublicacion;
	private String desRefRetiro;
	private List<DocumentosAdjuntosDTO> listDocumentosAdjuntosDTOs;
	private boolean autorizarNotificacion;
	private SsoVwUsuarioDTO ssoVwUsuarioDTO;
	private int estadoError;
	private boolean autorizacionFinalizada;

	public long getCveNotificaciones() {
		return cveNotificaciones;
	}

	public void setCveNotificaciones(long cveNotificaciones) {
		this.cveNotificaciones = cveNotificaciones;
	}

	public DepartamentoDTO getDepartamentoDTO() {
		return departamentoDTO;
	}

	public void setDepartamentoDTO(DepartamentoDTO departamentoDTO) {
		this.departamentoDTO = departamentoDTO;
	}

	public AreaRespNotifDTO getAreaRespNotifDTO() {
		return areaRespNotifDTO;
	}

	public void setAreaRespNotifDTO(AreaRespNotifDTO areaRespNotifDTO) {
		this.areaRespNotifDTO = areaRespNotifDTO;
	}

	public DelegacionDTO getDelegacionDTO() {
		return delegacionDTO;
	}

	public void setDelegacionDTO(DelegacionDTO delegacionDTO) {
		this.delegacionDTO = delegacionDTO;
	}

	public SubdelegacionDTO getSubdelegacionDTO() {
		return subdelegacionDTO;
	}

	public void setSubdelegacionDTO(SubdelegacionDTO subdelegacionDTO) {
		this.subdelegacionDTO = subdelegacionDTO;
	}

	public SujetoANotificarDTO getSujetoANotificarDTO() {
		return sujetoANotificarDTO;
	}

	public void setSujetoANotificarDTO(SujetoANotificarDTO sujetoANotificarDTO) {
		this.sujetoANotificarDTO = sujetoANotificarDTO;
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

	public StatusDTO getStatusDTO() {
		return statusDTO;
	}

	public void setStatusDTO(StatusDTO statusDTO) {
		this.statusDTO = statusDTO;
	}

	public String getDesNumOficio() {
		return desNumOficio;
	}

	public void setDesNumOficio(String desNumOficio) {
		this.desNumOficio = desNumOficio;
	}

	public TipodocumentoDTO getTipodocumentoDTO() {
		return tipodocumentoDTO;
	}

	public void setTipodocumentoDTO(TipodocumentoDTO tipodocumentoDTO) {
		this.tipodocumentoDTO = tipodocumentoDTO;
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

	public AreanormativaDTO getAreanormativaDTO() {
		return areanormativaDTO;
	}

	public void setAreanormativaDTO(AreanormativaDTO areanormativaDTO) {
		this.areanormativaDTO = areanormativaDTO;
	}

	public List<DocumentosAdjuntosDTO> getListDocumentosAdjuntosDTOs() {
		return listDocumentosAdjuntosDTOs;
	}

	public void setListDocumentosAdjuntosDTOs(
			List<DocumentosAdjuntosDTO> listDocumentosAdjuntosDTOs) {
		this.listDocumentosAdjuntosDTOs = listDocumentosAdjuntosDTOs;
	}

	public String getFecPublicacionCadena() {
		return fecPublicacionCadena;
	}

	public void setFecPublicacionCadena(String fecPublicacionCadena) {
		this.fecPublicacionCadena = fecPublicacionCadena;
	}

	public String getFecInicioPublicacionCadena() {
		return fecInicioPublicacionCadena;
	}

	public void setFecInicioPublicacionCadena(String fecInicioPublicacionCadena) {
		this.fecInicioPublicacionCadena = fecInicioPublicacionCadena;
	}

	public String getFecFinPublicacionCadena() {
		return fecFinPublicacionCadena;
	}

	public void setFecFinPublicacionCadena(String fecFinPublicacionCadena) {
		this.fecFinPublicacionCadena = fecFinPublicacionCadena;
	}

	public String getFecRetiroPublicacionCadena() {
		return fecRetiroPublicacionCadena;
	}

	public void setFecRetiroPublicacionCadena(String fecRetiroPublicacionCadena) {
		this.fecRetiroPublicacionCadena = fecRetiroPublicacionCadena;
	}

	public boolean isAutorizarNotificacion() {
		return autorizarNotificacion;
	}

	public void setAutorizarNotificacion(boolean autorizarNotificacion) {
		this.autorizarNotificacion = autorizarNotificacion;
	}

	public int getEstadoError() {
		return estadoError;
	}

	public void setEstadoError(int estadoError) {
		this.estadoError = estadoError;
	}

	public SsoVwUsuarioDTO getSsoVwUsuarioDTO() {
		return ssoVwUsuarioDTO;
	}

	public void setSsoVwUsuarioDTO(SsoVwUsuarioDTO ssoVwUsuarioDTO) {
		this.ssoVwUsuarioDTO = ssoVwUsuarioDTO;
	}

	public boolean isAutorizacionFinalizada() {
		return autorizacionFinalizada;
	}

	public void setAutorizacionFinalizada(boolean autorizacionFinalizada) {
		this.autorizacionFinalizada = autorizacionFinalizada;
	}

}
