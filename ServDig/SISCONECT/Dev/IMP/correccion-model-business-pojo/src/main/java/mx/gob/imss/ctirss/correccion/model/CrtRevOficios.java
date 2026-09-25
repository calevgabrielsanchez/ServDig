package mx.gob.imss.ctirss.correccion.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import mx.gob.imss.ctirss.correccion.framework.base.model.AbstractModel;
/**
 * Model para la tabla CRT_REVOFICIOS
 *
 * @since 07/08/2012
 */

@Entity
@Table(name="CRT_REVOFICIOS")
public class CrtRevOficios extends AbstractModel{

	
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="CVE_REVOFICIOS", sequenceName="SEQ_CVE_REVOFICIOS")
	@GeneratedValue(generator="CVE_REVOFICIOS")
	@Column(name="CVE_REVOFICIOS")
	private Long cveRevOficios;
	@Column(name="CVE_PRESENTACORR")
	private Integer cvePresentaCorr;
	@Column(name="ID_TIPOOFICIO")
	private Long id_TipoOficio;
	@Column(name="NUM_FOLIO_OFICIO")
	private String numFolioOficio;
	@Temporal( TemporalType.DATE)
	@Column(name="FEC_FECHAEMIOF")
	private Date fecFechaEmiOf;
	@Temporal( TemporalType.DATE)
	@Column(name="FEC_FECHANOTOF")
	private Date fecFechaNotOf;
	@Temporal( TemporalType.DATE)
	@Column(name="FEC_FECHAATENCIONOF")
	private Date fecFechaAtencionOf;
	@Column(name="TX_OBSERVACIONES")
	private String txObservaciones;
	@Temporal( TemporalType.TIMESTAMP)
	@Column(name="FEC_FECHAREG")
	private Date fecFechaReg;
	@Column(name="CVE_USUARIO")
	private String cveUsuario;
	@Column(name="ID_MOTIVOCANCELACION")
	private Long idMotivoCancelacion;
	@Column(name="EJERCICIO")
	private Long ejercicio;
	
	
	
	@Transient
	private Long cveSolCorr;
	@Transient
	private String fechaEmision;
	@Transient
	private String fechaNotificacion;
	@Transient
	private String fechaAtencion;
	@Transient
	private String fechaAutProrroga;
	
	
	/**
	 * @return the cveRevOficios
	 */
	public Long getCveRevOficios() {
		return cveRevOficios;
	}
	/**
	 * @param cveRevOficios the cveRevOficios to set
	 */
	public void setCveRevOficios(Long cveRevOficios) {
		this.cveRevOficios = cveRevOficios;
	}
	/**
	 * @return the cvePresentaCorr
	 */
	public Integer getCvePresentaCorr() {
		return cvePresentaCorr;
	}
	/**
	 * @param cvePresentaCorr the cvePresentaCorr to set
	 */
	public void setCvePresentaCorr(Integer cvePresentaCorr) {
		this.cvePresentaCorr = cvePresentaCorr;
	}
	/**
	 * @return the id_TipoOficio
	 */
	public Long getId_TipoOficio() {
		return id_TipoOficio;
	}
	/**
	 * @param id_TipoOficio the id_TipoOficio to set
	 */
	public void setId_TipoOficio(Long id_TipoOficio) {
		this.id_TipoOficio = id_TipoOficio;
	}
	/**
	 * @return the numFolioOficio
	 */
	public String getNumFolioOficio() {
		return numFolioOficio;
	}
	/**
	 * @param numFolioOficio the numFolioOficio to set
	 */
	public void setNumFolioOficio(String numFolioOficio) {
		this.numFolioOficio = numFolioOficio;
	}
	/**
	 * @return the fecFechaEmiOf
	 */
	public Date getFecFechaEmiOf() {
		return fecFechaEmiOf;
	}
	/**
	 * @param fecFechaEmiOf the fecFechaEmiOf to set
	 */
	public void setFecFechaEmiOf(Date fecFechaEmiOf) {
		this.fecFechaEmiOf = fecFechaEmiOf;
	}
	/**
	 * @return the fecFechaNotOf
	 */
	public Date getFecFechaNotOf() {
		return fecFechaNotOf;
	}
	/**
	 * @param fecFechaNotOf the fecFechaNotOf to set
	 */
	public void setFecFechaNotOf(Date fecFechaNotOf) {
		this.fecFechaNotOf = fecFechaNotOf;
	}
	/**
	 * @return the fecFechaAtencionOf
	 */
	public Date getFecFechaAtencionOf() {
		return fecFechaAtencionOf;
	}
	/**
	 * @param fecFechaAtencionOf the fecFechaAtencionOf to set
	 */
	public void setFecFechaAtencionOf(Date fecFechaAtencionOf) {
		this.fecFechaAtencionOf = fecFechaAtencionOf;
	}
	/**
	 * @return the txObservaciones
	 */
	public String getTxObservaciones() {
		return txObservaciones;
	}
	/**
	 * @param txObservaciones the txObservaciones to set
	 */
	public void setTxObservaciones(String txObservaciones) {
		this.txObservaciones = txObservaciones;
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
	 * @return the cveSolCorr
	 */
	@Transient
	public Long getCveSolCorr() {
		return cveSolCorr;
	}
	/**
	 * @param cveSolCorr the cveSolCorr to set
	 */
	@Transient
	public void setCveSolCorr(Long cveSolCorr) {
		this.cveSolCorr = cveSolCorr;
	}
	/**
	 * @return the fechaEmision
	 */
	public String getFechaEmision() {
		return fechaEmision;
	}
	/**
	 * @param fechaEmision the fechaEmision to set
	 */
	public void setFechaEmision(String fechaEmision) {
		this.fechaEmision = fechaEmision;
	}
	/**
	 * @return the fechaNotificacion
	 */
	public String getFechaNotificacion() {
		return fechaNotificacion;
	}
	/**
	 * @param fechaNotificacion the fechaNotificacion to set
	 */
	public void setFechaNotificacion(String fechaNotificacion) {
		this.fechaNotificacion = fechaNotificacion;
	}
	/**
	 * @return the fechaAtencion
	 */
	public String getFechaAtencion() {
		return fechaAtencion;
	}
	/**
	 * @param fechaAtencion the fechaAtencion to set
	 */
	public void setFechaAtencion(String fechaAtencion) {
		this.fechaAtencion = fechaAtencion;
	}
	/**
	 * @return the idMotivoCancelacion
	 */
	public Long getIdMotivoCancelacion() {
		return idMotivoCancelacion;
	}
	/**
	 * @param idMotivoCancelacion the idMotivoCancelacion to set
	 */
	public void setIdMotivoCancelacion(Long idMotivoCancelacion) {
		this.idMotivoCancelacion = idMotivoCancelacion;
	}
	/**
	 * @return the ejercicio
	 */
	public Long getEjercicio() {
		return ejercicio;
	}
	/**
	 * @param ejercicio the ejercicio to set
	 */
	public void setEjercicio(Long ejercicio) {
		this.ejercicio = ejercicio;
	}
	/**
	 * @return the fechaAutProrroga
	 */
	public String getFechaAutProrroga() {
		return fechaAutProrroga;
	}
	/**
	 * @param fechaAutProrroga the fechaAutProrroga to set
	 */
	public void setFechaAutProrroga(String fechaAutProrroga) {
		this.fechaAutProrroga = fechaAutProrroga;
	}
	
	
}
