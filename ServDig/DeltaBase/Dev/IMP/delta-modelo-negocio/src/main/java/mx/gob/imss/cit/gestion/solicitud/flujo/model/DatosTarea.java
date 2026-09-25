package mx.gob.imss.cit.gestion.solicitud.flujo.model;

import java.io.Serializable;

public class DatosTarea implements Serializable {

	/**
	 * Bean para los datos de tareas
	 * 
	 * @author softtek
	 * 
	 */
	private static final long serialVersionUID = 2502561256978042348L;

	private String folio;

	private String idSolicitud;

	private Long idTareaUsuario;

	private Long idInstancia;

	private Integer idTramite;

	private Integer idEstadoTramite;

	private Integer idEstadoTarea;

        private String estadoTarea;

	private Long idTarea;

	private String usuario;

	private MensajeTarea mensajeTarea;

	private InicioTramite inicioTramite;
	
	private String fecRegistroActualizado;

	private String fecRegistroAlta;

	private String fecIniTarea;

	private String fecFinTarea;

	private String fecSolicitud;

	private Long idSubdelegacion;
	
	private String descDelegacion;
	
	private String descSubdelegacion;
	
	private String nss;
	
	private String curp;
	
	private String descTipoSolicitud;
	

	public String getFolio() {
		return folio;
	}

	public void setFolio(String folio) {
		this.folio = folio;
	}

	public String getIdSolicitud() {
		return idSolicitud;
	}

	public void setIdSolicitud(String idSolicitud) {
		this.idSolicitud = idSolicitud;
	}

	public Long getIdTareaUsuario() {
		return idTareaUsuario;
	}

	public void setIdTareaUsuario(Long idTareaUsuario) {
		this.idTareaUsuario = idTareaUsuario;
	}

	public Long getIdInstancia() {
		return idInstancia;
	}

	public void setIdInstancia(Long idInstancia) {
		this.idInstancia = idInstancia;
	}

	public Integer getIdTramite() {
		return idTramite;
	}

	public void setIdTramite(Integer idTramite) {
		this.idTramite = idTramite;
	}

	public Integer getIdEstadoTramite() {
		return idEstadoTramite;
	}

	public void setIdEstadoTramite(Integer idEstadoTramite) {
		this.idEstadoTramite = idEstadoTramite;
	}

	public Integer getIdEstadoTarea() {
		return idEstadoTarea;
	}

	public void setIdEstadoTarea(Integer idEstadoTarea) {
		this.idEstadoTarea = idEstadoTarea;
	}

        public String getEstadoTarea() {
                return estadoTarea;
        }

        public void setEstadoTarea(String estadoTarea) {
                this.estadoTarea = estadoTarea;
        }

	public Long getIdTarea() {
		return idTarea;
	}

	public void setIdTarea(Long idTarea) {
		this.idTarea = idTarea;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public MensajeTarea getMensajeTarea() {
		return mensajeTarea;
	}

	public void setMensajeTarea(MensajeTarea mensajeTarea) {
		this.mensajeTarea = mensajeTarea;
	}

	public InicioTramite getInicioTramite() {
		return inicioTramite;
	}

	public void setInicioTramite(InicioTramite inicioTramite) {
		this.inicioTramite = inicioTramite;
	}
	
	public String getFecRegistroActualizado() {
		return fecRegistroActualizado;
	}

	public void setFecRegistroActualizado(String fecRegistroActualizado) {
		this.fecRegistroActualizado = fecRegistroActualizado;
	}

	public String getFecRegistroAlta() {
		return fecRegistroAlta;
	}

	public void setFecRegistroAlta(String fecRegistroAlta) {
		this.fecRegistroAlta = fecRegistroAlta;
	}

	public String getFecIniTarea() {
		return fecIniTarea;
	}

	public void setFecIniTarea(String fecIniTarea) {
		this.fecIniTarea = fecIniTarea;
	}

	public String getFecFinTarea() {
		return fecFinTarea;
	}

	public void setFecFinTarea(String fecFinTarea) {
		this.fecFinTarea = fecFinTarea;
	}

	public String getFecSolicitud() {
		return fecSolicitud;
	}

	public void setFecSolicitud(String fecSolicitud) {
		this.fecSolicitud = fecSolicitud;
	}

	public Long getIdSubdelegacion() {
		return idSubdelegacion;
	}

	public void setIdSubdelegacion(Long idSubdelegacion) {
		this.idSubdelegacion = idSubdelegacion;
	}
	
	public String getDescDelegacion() {
        return descDelegacion;
    }

    public void setDescDelegacion(String descDelegacion) {
        this.descDelegacion = descDelegacion;
    }

    public String getDescSubdelegacion() {
        return descSubdelegacion;
    }

    public void setDescSubdelegacion(String descSubdelegacion) {
        this.descSubdelegacion = descSubdelegacion;
    }

    public String getNss() {
        return nss;
    }

    public void setNss(String nss) {
        this.nss = nss;
    }

    public String getCurp() {
        return curp;
    }

    public void setCurp(String curp) {
        this.curp = curp;
    }

    public String getDescTipoSolicitud() {
        return descTipoSolicitud;
    }
    
    public void setDescTipoSolicitud(String descTipoSolicitud) {
        this.descTipoSolicitud = descTipoSolicitud;
    }


}
