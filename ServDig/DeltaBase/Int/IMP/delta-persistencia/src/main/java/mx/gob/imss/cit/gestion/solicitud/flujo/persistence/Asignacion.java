package mx.gob.imss.cit.gestion.solicitud.flujo.persistence;

import java.io.Serializable;

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
 * Bean para la asignacion
 * 
 * @author softtek
 *
 */
@Entity
@Table(name = "DIT_INST_PARTICIP")
public class Asignacion implements Serializable {

	/**
	 * Numero de version
	 */
	private static final long serialVersionUID = -8317476078192735372L;

	/**
	 * Identificador de la tarea de usuario
	 */
	@Id
	@SequenceGenerator(name = "INST_PARTICIP_GENERATOR", sequenceName = "SEQ_DITINSTPARTICIP", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "INST_PARTICIP_GENERATOR")
	@Column(name = "CVE_ID_INST_PARTICIP")
	private Long idAsignacion;

	/**
	 * Identificador del participante
	 */
	@ManyToOne(fetch = FetchType.EAGER, optional = true)
	@JoinColumn(name = "CVE_ID_PARTICIPANTE", updatable = false, insertable = true)
	private Participante participante;

	/**
	 * Identificador de la instancia
	 */
	@ManyToOne(fetch = FetchType.EAGER, optional = false)
	@JoinColumn(name = "CVE_ID_INSTANCIA", updatable = false, insertable = true)
	private Instancia instancia;

	/**
	 * Identificador de usuario
	 */
	@Column(name = "CVE_USUARIO")
	private String usuario;
	
	@Embedded
	private DatosAuditoria datosAuditoria;

	/**
	 * Constructor de la clase
	 */
	public Asignacion() {
		super();
		this.datosAuditoria = new DatosAuditoria();
	}

	/**
	 * 
	 * @return idAsignacion
	 */
	public Long getIdAsignacion() {
		return idAsignacion;
	}

	/**
	 * 
	 * @param idAsignacion
	 *            a fijar
	 */
	public void setIdAsignacion(Long idAsignacion) {
		this.idAsignacion = idAsignacion;
	}

	/**
	 * 
	 * @return participante
	 */
	public Participante getParticipante() {
		return participante;
	}

	/**
	 * 
	 * @param participante
	 *            a fijar
	 */
	public void setParticipante(Participante participante) {
		this.participante = participante;
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
	
	public DatosAuditoria getDatosAuditoria() {
		return datosAuditoria;
	}

	public void setDatosAuditoria(DatosAuditoria datosAuditoria) {
		this.datosAuditoria = datosAuditoria;
	}

}
