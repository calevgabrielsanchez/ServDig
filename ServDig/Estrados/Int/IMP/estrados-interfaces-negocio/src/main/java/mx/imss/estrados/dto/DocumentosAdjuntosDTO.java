package mx.imss.estrados.dto;

import java.io.Serializable;

public class DocumentosAdjuntosDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4528450550055266840L;

	/**
	 * 
	 */
	

	public DocumentosAdjuntosDTO() {
		this.notificacionesDTO = new NotificacionesDTO();
		this.tipoAdjuntoDTO = new TipoAdjuntoDTO();
	}

	public DocumentosAdjuntosDTO(long cveDoctoAdjunto,
			NotificacionesDTO notificacionesDTO, String desNumOficio,
			String desNombreArchivo, String desRefFilesystem,
			TipoAdjuntoDTO tipoAdjuntoDTO) {
		super();
		this.cveDoctoAdjunto = cveDoctoAdjunto;
		this.notificacionesDTO = notificacionesDTO;
		this.desNumOficio = desNumOficio;
		this.desNombreArchivo = desNombreArchivo;
		this.desRefFilesystem = desRefFilesystem;
		this.tipoAdjuntoDTO = tipoAdjuntoDTO;
		this.notificacionesDTO = new NotificacionesDTO();
		this.tipoAdjuntoDTO = new TipoAdjuntoDTO();
	}

	private long cveDoctoAdjunto;
	private NotificacionesDTO notificacionesDTO;
	private String desNumOficio;
	private String desNombreArchivo;
	private String desRefFilesystem;
	private TipoAdjuntoDTO tipoAdjuntoDTO;
	private byte[] archivo;
	private SsoVwUsuarioDTO dtSsoVwUsuarioDTO;

	public long getCveDoctoAdjunto() {
		return cveDoctoAdjunto;
	}

	public void setCveDoctoAdjunto(long cveDoctoAdjunto) {
		this.cveDoctoAdjunto = cveDoctoAdjunto;
	}

	public NotificacionesDTO getNotificacionesDTO() {
		return notificacionesDTO;
	}

	public void setNotificacionesDTO(NotificacionesDTO notificacionesDTO) {
		this.notificacionesDTO = notificacionesDTO;
	}

	public String getDesNumOficio() {
		return desNumOficio;
	}

	public void setDesNumOficio(String desNumOficio) {
		this.desNumOficio = desNumOficio;
	}

	public String getDesNombreArchivo() {
		return desNombreArchivo;
	}

	public void setDesNombreArchivo(String desNombreArchivo) {
		this.desNombreArchivo = desNombreArchivo;
	}

	public String getDesRefFilesystem() {
		return desRefFilesystem;
	}

	public void setDesRefFilesystem(String desRefFilesystem) {
		this.desRefFilesystem = desRefFilesystem;
	}

	public TipoAdjuntoDTO getTipoAdjuntoDTO() {
		return tipoAdjuntoDTO;
	}

	public void setTipoAdjuntoDTO(TipoAdjuntoDTO tipoAdjuntoDTO) {
		this.tipoAdjuntoDTO = tipoAdjuntoDTO;
	}

	public byte[] getArchivo() {
		return archivo;
	}

	public void setArchivo(byte[] archivo) {
		this.archivo = archivo;
	}

	public SsoVwUsuarioDTO getDtSsoVwUsuarioDTO() {
		return dtSsoVwUsuarioDTO;
	}

	public void setDtSsoVwUsuarioDTO(SsoVwUsuarioDTO dtSsoVwUsuarioDTO) {
		this.dtSsoVwUsuarioDTO = dtSsoVwUsuarioDTO;
	}

}
