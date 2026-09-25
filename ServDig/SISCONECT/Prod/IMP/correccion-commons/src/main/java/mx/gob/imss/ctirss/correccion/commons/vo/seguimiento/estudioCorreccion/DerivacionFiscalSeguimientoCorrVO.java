package mx.gob.imss.ctirss.correccion.commons.vo.seguimiento.estudioCorreccion;

import java.io.Serializable;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import mx.gob.imss.ctirss.correccion.framework.utils.ControlTabs;

@JsonIgnoreProperties(ignoreUnknown = true)
public class DerivacionFiscalSeguimientoCorrVO extends ControlTabs implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long cveRevDerivAFisca;
	private Integer cveSolCorr;
	private Integer cvePresentaCorr;
	private String numFolioOficio;
	private String fechaDeriva;
	private String fechaSolReactiva;
	private String fechaEnvioSol;
	private String numOficioEnvio;
	private String fechaReactiva;
	private String numOficioReactiva;
	private String txObservaciones;
	private String fechaReg;
	private String nombreFuncionario;
	
	public Long getCveRevDerivAFisca() {
		return cveRevDerivAFisca;
	}
	public void setCveRevDerivAFisca(Long cveRevDerivAFisca) {
		this.cveRevDerivAFisca = cveRevDerivAFisca;
	}
	public Integer getCveSolCorr() {
		return cveSolCorr;
	}
	public void setCveSolCorr(Integer cveSolCorr) {
		this.cveSolCorr = cveSolCorr;
	}
	public String getNumFolioOficio() {
		return numFolioOficio;
	}
	public void setNumFolioOficio(String numFolioOficio) {
		this.numFolioOficio = numFolioOficio;
	}
	public String getFechaDeriva() {
		return fechaDeriva;
	}
	public void setFechaDeriva(String fechaDeriva) {
		this.fechaDeriva = fechaDeriva;
	}
	public String getFechaSolReactiva() {
		return fechaSolReactiva;
	}
	public void setFechaSolReactiva(String fechaSolReactiva) {
		this.fechaSolReactiva = fechaSolReactiva;
	}
	public String getFechaEnvioSol() {
		return fechaEnvioSol;
	}
	public void setFechaEnvioSol(String fechaEnvioSol) {
		this.fechaEnvioSol = fechaEnvioSol;
	}
	public String getNumOficioEnvio() {
		return numOficioEnvio;
	}
	public void setNumOficioEnvio(String numOficioEnvio) {
		this.numOficioEnvio = numOficioEnvio;
	}
	public String getFechaReactiva() {
		return fechaReactiva;
	}
	public void setFechaReactiva(String fechaReactiva) {
		this.fechaReactiva = fechaReactiva;
	}
	public String getNumOficioReactiva() {
		return numOficioReactiva;
	}
	public void setNumOficioReactiva(String numOficioReactiva) {
		this.numOficioReactiva = numOficioReactiva;
	}
	public String getTxObservaciones() {
		return txObservaciones;
	}
	public void setTxObservaciones(String txObservaciones) {
		this.txObservaciones = txObservaciones;
	}
	public String getFechaReg() {
		return fechaReg;
	}
	public void setFechaReg(String fechaReg) {
		this.fechaReg = fechaReg;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public String getNombreFuncionario() {
		return nombreFuncionario;
	}
	public void setNombreFuncionario(String nombreFuncionario) {
		this.nombreFuncionario = nombreFuncionario;
	}
	/**
	 * @return cvePresentaCorr
	 */
	public Integer getCvePresentaCorr() {
		return cvePresentaCorr;
	}
	/**
	 * @param cvePresentaCorr clave de la presentacion de correccion
	 */
	public void setCvePresentaCorr(Integer cvePresentaCorr) {
		this.cvePresentaCorr = cvePresentaCorr;
	}

}
