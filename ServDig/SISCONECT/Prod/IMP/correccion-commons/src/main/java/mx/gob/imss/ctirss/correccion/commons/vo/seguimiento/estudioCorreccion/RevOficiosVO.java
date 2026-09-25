package mx.gob.imss.ctirss.correccion.commons.vo.seguimiento.estudioCorreccion;

import java.io.Serializable;
import java.util.List;


import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import mx.gob.imss.ctirss.correccion.framework.utils.ControlTabs;
import mx.gob.imss.ctirss.correccion.model.CgcCatMotivoCancelacion;

@SuppressWarnings("rawtypes")
@JsonIgnoreProperties(ignoreUnknown = true)
public class RevOficiosVO extends ControlTabs implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2091020397143171191L;
	private Long cveRevOficios;
	private Integer cvePresentaCorr;	
	private Long idTipoOficio;
	private String numFolioOficio;
	private String fecFechaEmiOf;
	private String fecFechaNotOf;
	private String fecFechaAtencionOf;
	private String fecElaboraPresentacion;
	private String txObservaciones;
	private String fecFechaReg;
	private String cveUsuario;
	private Long cveSolCorr;
	private Long idMotivoCancelacion;
	private Long ejercicio;
	private List<CgcCatMotivoCancelacion> motivosCancelacion;
	
	private String funcionarioRegistra;
	private String funcionarioAutoriza;
	
	public Long getCveRevOficios() {
		return cveRevOficios;
	}
	public void setCveRevOficios(Long cveRevOficios) {
		this.cveRevOficios = cveRevOficios;
	}
	public Integer getCvePresentaCorr() {
		return cvePresentaCorr;
	}
	public void setCvePresentaCorr(Integer cvePresentaCorr) {
		this.cvePresentaCorr = cvePresentaCorr;
	}
	
	public String getNumFolioOficio() {
		return numFolioOficio;
	}
	public void setNumFolioOficio(String numFolioOficio) {
		this.numFolioOficio = numFolioOficio;
	}
	public String getFecFechaEmiOf() {
		return fecFechaEmiOf;
	}
	public void setFecFechaEmiOf(String fecFechaEmiOf) {
		this.fecFechaEmiOf = fecFechaEmiOf;
	}
	public String getFecFechaNotOf() {
		return fecFechaNotOf;
	}
	public void setFecFechaNotOf(String fecFechaNotOf) {
		this.fecFechaNotOf = fecFechaNotOf;
	}
	public String getFecFechaAtencionOf() {
		return fecFechaAtencionOf;
	}
	public void setFecFechaAtencionOf(String fecFechaAtencionOf) {
		this.fecFechaAtencionOf = fecFechaAtencionOf;
	}
	public String getTxObservaciones() {
		return txObservaciones;
	}
	public void setTxObservaciones(String txObservaciones) {
		this.txObservaciones = txObservaciones;
	}
	public String getFecFechaReg() {
		return fecFechaReg;
	}
	public void setFecFechaReg(String fecFechaReg) {
		this.fecFechaReg = fecFechaReg;
	}
	public String getCveUsuario() {
		return cveUsuario;
	}
	public void setCveUsuario(String cveUsuario) {
		this.cveUsuario = cveUsuario;
	}
	public Long getCveSolCorr() {
		return cveSolCorr;
	}
	public void setCveSolCorr(Long cveSolCorr) {
		this.cveSolCorr = cveSolCorr;
	}
	/**
	 * @return the idTipoOficio
	 */
	public Long getIdTipoOficio() {
		return idTipoOficio;
	}
	/**
	 * @param idTipoOficio the idTipoOficio to set
	 */
	public void setIdTipoOficio(Long idTipoOficio) {
		this.idTipoOficio = idTipoOficio;
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
	 * @return the funcionarioRegistra
	 */
	public String getFuncionarioRegistra() {
		return funcionarioRegistra;
	}
	/**
	 * @param funcionarioRegistra the funcionarioRegistra to set
	 */
	public void setFuncionarioRegistra(String funcionarioRegistra) {
		this.funcionarioRegistra = funcionarioRegistra;
	}
	/**
	 * @return the funcionarioAutoriza
	 */
	public String getFuncionarioAutoriza() {
		return funcionarioAutoriza;
	}
	/**
	 * @param funcionarioAutoriza the funcionarioAutoriza to set
	 */
	public void setFuncionarioAutoriza(String funcionarioAutoriza) {
		this.funcionarioAutoriza = funcionarioAutoriza;
	}
	/**
	 * @return the motivosCancelacion
	 */
	public List<CgcCatMotivoCancelacion> getMotivosCancelacion() {
		return motivosCancelacion;
	}
	/**
	 * @param motivosCancelacion the motivosCancelacion to set
	 */
	public void setMotivosCancelacion(
			List<CgcCatMotivoCancelacion> motivosCancelacion) {
		this.motivosCancelacion = motivosCancelacion;
	}
	public Long getEjercicio() {
		return ejercicio;
	}
	public void setEjercicio(Long ejercicio) {
		this.ejercicio = ejercicio;
	}
	/**
	 * Retorna el valor fecElaboraPresentacion
	 * @return  fecElaboraPresentacion
	 */
	public String getFecElaboraPresentacion() {
		return fecElaboraPresentacion;
	}
	/**
	 * Asigna el valor del fecElaboraPresentacion al atributo fecElaboraPresentacion
	 * @param fecElaboraPresentacion 
	 */
	public void setFecElaboraPresentacion(String fecElaboraPresentacion) {
		this.fecElaboraPresentacion = fecElaboraPresentacion;
	}

	
	
}
