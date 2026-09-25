package mx.gob.imss.cit.gestion.solicitud.flujo.model;

import java.io.Serializable;
import java.util.Date;

/**
 * Bean para la bandeja de tareas
 * 
 * @author softtek
 * 
 */
public class TareaBandeja implements Serializable {

	/**
	 * Numero de version
	 */
	private static final long serialVersionUID = 4890138433806384302L;

	private Long idTareaUsuario;

	private Long idTarea;

	private String nombreTarea;

	private Long idInstancia;

	private Long idProceso;

	private String nombreProceso;

	private Integer idTramite;

	private InicioTramite inicioTramite;

	private MensajeTarea mensajeTarea;
	
	private Date fechaInicioTarea;

	private String usuario;

	public Long getIdTareaUsuario() {
		return idTareaUsuario;
	}

	public void setIdTareaUsuario(Long idTareaUsuario) {
		this.idTareaUsuario = idTareaUsuario;
	}

	public Long getIdTarea() {
		return idTarea;
	}

	public void setIdTarea(Long idTarea) {
		this.idTarea = idTarea;
	}

	public String getNombreTarea() {
		return nombreTarea;
	}

	public void setNombreTarea(String nombreTarea) {
		this.nombreTarea = nombreTarea;
	}

	public Long getIdInstancia() {
		return idInstancia;
	}

	public void setIdInstancia(Long idInstancia) {
		this.idInstancia = idInstancia;
	}

	public Long getIdProceso() {
		return idProceso;
	}

	public void setIdProceso(Long idProceso) {
		this.idProceso = idProceso;
	}

	public String getNombreProceso() {
		return nombreProceso;
	}

	public void setNombreProceso(String nombreProceso) {
		this.nombreProceso = nombreProceso;
	}

	public Integer getIdTramite() {
		return idTramite;
	}

	public void setIdTramite(Integer idTramite) {
		this.idTramite = idTramite;
	}

	public InicioTramite getInicioTramite() {
		return inicioTramite;
	}

	public void setInicioTramite(InicioTramite inicioTramite) {
		this.inicioTramite = inicioTramite;
	}

	public MensajeTarea getMensajeTarea() {
		return mensajeTarea;
	}

	public void setMensajeTarea(MensajeTarea mensajeTarea) {
		this.mensajeTarea = mensajeTarea;
	}
	
	public Date getFechaInicioTarea() {
		Date fecha = fechaInicioTarea;
		return fecha;
	}

	public void setFechaInicioTarea(Date fechaInicioTarea) {
			this.fechaInicioTarea = fechaInicioTarea;

	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}
}
