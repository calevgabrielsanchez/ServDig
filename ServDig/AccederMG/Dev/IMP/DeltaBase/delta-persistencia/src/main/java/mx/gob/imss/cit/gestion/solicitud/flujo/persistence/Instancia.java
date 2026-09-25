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
 * Bean de Instancia
 * 
 * @author softtek
 *
 */
@Entity
@Table(name = "DIT_INSTANCIA")
public class Instancia implements Serializable {

	/**
	 * Numero de version
	 */
	private static final long serialVersionUID = -2687959823914983076L;

	/**
	 * Identificador del BP
	 */
	@Id
	@SequenceGenerator(name = "INSTANCIA_GENERATOR", sequenceName = "SEQ_DITINSTANCIA", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "INSTANCIA_GENERATOR")
	@Column(name = "CVE_ID_INSTANCIA")
	private Long bpId;

	@Embedded
	private DatosAuditoria datosAuditoria;

	/**
	 * Identificador del proceso
	 */
	@ManyToOne(fetch = FetchType.LAZY, optional = true)
	@JoinColumn(name = "CVE_ID_PROCESO", updatable = false, insertable = true)
	private Proceso proceso;

	/**
	 * Identificador del estado instancia
	 */
	@ManyToOne(fetch = FetchType.EAGER, optional = false)
	@JoinColumn(name = "CVE_ID_EDO_INSTANCIA", updatable = true, insertable = true)
	private EstadoInstancia estadoInstancia;

	/**
	 * Informacion del tramite
	 */
	@Column(name = "DES_BDOC_INSTANCIA")
	private String bDoc;

	@Column(name = "CVE_ID_TRAMITE")
	private Integer idTramite;

	public Instancia() {
		super();
		this.datosAuditoria = new DatosAuditoria();
	}

	/**
	 * 
	 * @return bpId
	 */
	public Long getBpId() {
		return bpId;
	}

	/**
	 * 
	 * @param bpId
	 *            a fijar
	 */
	public void setBpId(Long bpId) {
		this.bpId = bpId;
	}

	/**
	 * 
	 * @return proceso
	 */
	public Proceso getProceso() {
		return proceso;
	}

	/***
	 * 
	 * @param proceso
	 *            a fijar
	 */
	public void setProceso(Proceso proceso) {
		this.proceso = proceso;
	}

	/**
	 * 
	 * @return estadoInstancia
	 */
	public EstadoInstancia getEstadoInstancia() {
		return estadoInstancia;
	}

	/**
	 * 
	 * @param estadoInstancia
	 *            a fijar
	 */
	public void setEstadoInstancia(EstadoInstancia estadoInstancia) {
		this.estadoInstancia = estadoInstancia;
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

	/**
	 * 
	 * @return idTramite
	 */
	public Integer getIdTramite() {
		return idTramite;
	}

	/**
	 * 
	 * @param idTramite
	 *            a fijar
	 */
	public void setIdTramite(Integer idTramite) {
		this.idTramite = idTramite;
	}

	public DatosAuditoria getDatosAuditoria() {
		return datosAuditoria;
	}

	public void setDatosAuditoria(DatosAuditoria datosAuditoria) {
		this.datosAuditoria = datosAuditoria;
	}

}
