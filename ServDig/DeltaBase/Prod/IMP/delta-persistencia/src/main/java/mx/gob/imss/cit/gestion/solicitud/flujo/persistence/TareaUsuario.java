package mx.gob.imss.cit.gestion.solicitud.flujo.persistence;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * Bean para las tareas de ususario
 * 
 * @author softtek
 *
 */
@Entity
@Table(name = "DIT_TAREA_USUARIO")
public class TareaUsuario implements Serializable {

	/**
	 * Numero de version
	 */
	private static final long serialVersionUID = -7229544397144783838L;

	/**
	 * Identificador de la tarea de usuario
	 */
	@Id
	@SequenceGenerator(name = "TAREA_USUARIO_GENERATOR", sequenceName = "SEQ_DITTAREAUSUARIO", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "TAREA_USUARIO_GENERATOR")
	@Column(name = "CVE_ID_TAREA_USUARIO")
	private Long idTareaUsuario;
	
	@Embedded
	private DatosAuditoria datosAuditoria;


	/**
	 * Identificador de la tarea
	 */
	@ManyToOne(fetch = FetchType.LAZY, optional = true)
	@JoinColumn(name = "CVE_ID_TAREA", updatable = false, insertable = true)
	private Tarea tarea;

	/**
	 * Identificador de la instancia
	 */
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "CVE_ID_INSTANCIA", updatable = false, insertable = true)
	private Instancia instancia;

	/**
	 * Identificador de usuario
	 */
	@Column(name = "CVE_USUARIO")
	private String usuario;

	/**
	 * Identificador del estado de la tarea
	 */
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "CVE_ID_EDO_TAREA", updatable = true, insertable = true)
	private EstadoTarea estadoTarea;

	/**
	 * Fecha de inicio de la tarea
	 */
	@Column(name = "FEC_INI_TAREA")
	// @Temporal(javax.persistence.TemporalType.TIMESTAMP)
	private Date fechaInicioTarea;

	/**
	 * Fecha de finalizacion de la tarea
	 */
	@Column(name = "FEC_FIN_TAREA")
	// @Temporal(javax.persistence.TemporalType.TIMESTAMP)
	private Date fechaFinTarea;

	/**
	 * Tiempo limite
	 */
	@Column(name = "NUM_TIMEOUT")
	private Long timeout;

	/**
	 * Fecha de asignacion
	 */
	@Column(name = "FEC_ASIGNACION")
	// @Temporal(javax.persistence.TemporalType.TIMESTAMP)
	private Date fechaAsignacion;

	/**
	 * Informacion del tramite
	 */
	@Column(name = "DES_BDOC_TAREA")
	private String bDoc;
	
	/**
	 * Observacion
	 */
	@Column(name = "DES_OBSERVACION")
	private String observacion;
	
	public TareaUsuario() {
		super();
		this.datosAuditoria = new DatosAuditoria();
	}

	/**
	 * 
	 * @return idTareaUsuario
	 */
	public Long getIdTareaUsuario() {
		return idTareaUsuario;
	}

	/**
	 * 
	 * @param idTareaUsuario
	 *            a fijar
	 */
	public void setIdTareaUsuario(Long idTareaUsuario) {
		this.idTareaUsuario = idTareaUsuario;
	}

	/**
	 * 
	 * @return tarea a fijar
	 */
	public Tarea getTarea() {
		return tarea;
	}

	/**
	 * 
	 * @param tarea
	 *            a fijar
	 */
	public void setTarea(Tarea tarea) {
		this.tarea = tarea;
	}

	/**
	 * 
	 * @return instancia
	 */
	public Instancia getInstancia() {
		return instancia;
	}

	/**
	 * 
	 * @param instancia
	 *            a fijar
	 */
	public void setInstancia(Instancia instancia) {
		this.instancia = instancia;
	}

	/**
	 * 
	 * @return usuario
	 */
	public String getUsuario() {
		return usuario;
	}

	/**
	 * 
	 * @param usuario
	 *            a fijar
	 */
	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	/**
	 * 
	 * @return estadoTareaUsuario
	 */
	public EstadoTarea getEstadoTarea() {
		return estadoTarea;
	}

	/**
	 * 
	 * @param estadoTareaUsuario
	 *            a fijar
	 */
	public void setEstadoTarea(EstadoTarea estadoTarea) {
		this.estadoTarea = estadoTarea;
	}

	/**
	 * 
	 * @return fechaInicioTarea
	 */
	public Date getFechaInicioTarea() {
		Date fecha = fechaInicioTarea;
		return fecha;
	}

	/**
	 * 
	 * @param fechaInicioTarea
	 *            a fijar
	 */
	public void setFechaInicioTarea(Date fechaInicioTarea) {
		if (fechaInicioTarea != null) {
			Calendar cal = new GregorianCalendar();
			cal.setTime(fechaInicioTarea);
			this.fechaInicioTarea = cal.getTime();
		} else {
			this.fechaInicioTarea = null;
		}
	}

	/**
	 * 
	 * @return fechaFinTarea
	 */
	public Date getFechaFinTarea() {
		Date fecha = fechaFinTarea;
		return fecha;
	}

	/**
	 * 
	 * @param fechaFinTarea
	 *            a fijar
	 */
	public void setFechaFinTarea(Date fechaFinTarea) {
		if (fechaFinTarea != null) {
			Calendar cal = new GregorianCalendar();
			cal.setTime(fechaFinTarea);
			this.fechaFinTarea = cal.getTime();
		} else {
			this.fechaFinTarea = null;
		}
	}

	/**
	 * 
	 * @return timeout
	 */
	public Long getTimeout() {
		return timeout;
	}

	/**
	 * 
	 * @param timeout
	 *            a fijar
	 */
	public void setTimeout(Long timeout) {
		this.timeout = timeout;
	}

	/**
	 * 
	 * @return fechaAsignacion
	 */
	public Date getFechaAsignacion() {
		Date fecha = fechaAsignacion;
		return fecha;
	}

	/**
	 * 
	 * @param fechaAsignacion
	 *            a fijar
	 */
	public void setFechaAsignacion(Date fechaAsignacion) {
		if (fechaAsignacion != null) {
			Calendar cal = new GregorianCalendar();
			cal.setTime(fechaAsignacion);
			this.fechaAsignacion = cal.getTime();
		} else {
			this.fechaAsignacion = null;
		}
	}

	/**
	 * 
	 * @return bDoc
	 */
	public String getbDoc() {
		return bDoc;
	}

	/**
	 * 
	 * @param bDoc
	 *            a fijar
	 */
	public void setbDoc(String bDoc) {
		this.bDoc = bDoc;
	}
	
	public DatosAuditoria getDatosAuditoria() {
		return datosAuditoria;
	}

	public void setDatosAuditoria(DatosAuditoria datosAuditoria) {
		this.datosAuditoria = datosAuditoria;
	}

	public String getObservacion() {
		return observacion;
	}

	public void setObservacion(String observacion) {
		this.observacion = observacion;
	}

}
