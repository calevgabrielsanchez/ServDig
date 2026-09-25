package mx.gob.imss.ctirss.correccion.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import mx.gob.imss.ctirss.correccion.framework.base.model.AbstractModel;

@Entity
@Table(name="CRT_SOLICITUDOFICIOS")
public class CrtSolicitudOficios extends AbstractModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	


	@Id
	@SequenceGenerator(name="CVE_REVOFICIOS", sequenceName="SEQ_CVE_SOLICITUDOFICIOS")
	@GeneratedValue(generator="CVE_REVOFICIOS")
	@Column(name="CVE_SOLOFICIOS")
	private Long cveSolOficios;
	@Column(name="CVE_SOLICITUDCORR")
	private Integer cveSolicitudCorr;
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
	
	
	public Long getCveSolOficios() {
		return cveSolOficios;
	}
	public void setCveSolOficios(Long cveSolOficios) {
		this.cveSolOficios = cveSolOficios;
	}
	public Integer getCveSolicitudCorr() {
		return cveSolicitudCorr;
	}
	public void setCveSolicitudCorr(Integer cveSolicitudCorr) {
		this.cveSolicitudCorr = cveSolicitudCorr;
	}
	public Long getId_TipoOficio() {
		return id_TipoOficio;
	}
	public void setId_TipoOficio(Long id_TipoOficio) {
		this.id_TipoOficio = id_TipoOficio;
	}
	public String getNumFolioOficio() {
		return numFolioOficio;
	}
	public void setNumFolioOficio(String numFolioOficio) {
		this.numFolioOficio = numFolioOficio;
	}
	public Date getFecFechaEmiOf() {
		return fecFechaEmiOf;
	}
	public void setFecFechaEmiOf(Date fecFechaEmiOf) {
		this.fecFechaEmiOf = fecFechaEmiOf;
	}
	public Date getFecFechaNotOf() {
		return fecFechaNotOf;
	}
	public void setFecFechaNotOf(Date fecFechaNotOf) {
		this.fecFechaNotOf = fecFechaNotOf;
	}
	public Date getFecFechaAtencionOf() {
		return fecFechaAtencionOf;
	}
	public void setFecFechaAtencionOf(Date fecFechaAtencionOf) {
		this.fecFechaAtencionOf = fecFechaAtencionOf;
	}
	public String getTxObservaciones() {
		return txObservaciones;
	}
	public void setTxObservaciones(String txObservaciones) {
		this.txObservaciones = txObservaciones;
	}
	public Date getFecFechaReg() {
		return fecFechaReg;
	}
	public void setFecFechaReg(Date fecFechaReg) {
		this.fecFechaReg = fecFechaReg;
	}
	public String getCveUsuario() {
		return cveUsuario;
	}
	public void setCveUsuario(String cveUsuario) {
		this.cveUsuario = cveUsuario;
	}
	public Long getIdMotivoCancelacion() {
		return idMotivoCancelacion;
	}
	public void setIdMotivoCancelacion(Long idMotivoCancelacion) {
		this.idMotivoCancelacion = idMotivoCancelacion;
	}
	public Long getEjercicio() {
		return ejercicio;
	}
	public void setEjercicio(Long ejercicio) {
		this.ejercicio = ejercicio;
	}
	
	
	
	
	
	

}
