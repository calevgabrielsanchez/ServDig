package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "RTT_ENCABEZADO_RT")
public class RttEncabezado implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1359566413160329245L;

	@Id
	@Column(name = "RTT_CVE_DELEGACION")
	private String rttCveDelegacion;

	@Id
	@Column(name = "RTT_NUM_CICLO")
	private String rttNumCiclo;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "CVE_ID_TIPO_OPERACION")
	private RtcTipoOperacion tipoOperacion;

	@Column(name = "STP_INICIO_CARGA")
	private String stpInicioCarga;

	@Column(name = "STP_FIN_CARGA")
	private String stpFinCarga;

	@Column(name = "NUM_TOTAL_ARCH_PROCESADOS")
	private Integer numTotalArchProcesados;

	@Column(name = "NUM_TOTAL_REGS_PROCESADOS")
	private Integer numTotalRegsProcesados;

	@Column(name = "NUM_TOTAL_REGS_EXITOSOS")
	private Integer numTotalRegsExitosos;

	@Column(name = "NUM_TOTAL_REGS_ERROR")
	private Integer numTotalRegsError;

	@Column(name = "DES_NOTA_DE_CARGA")
	private String desNotaCarga;

	@OneToMany(mappedBy = "encabezado")
	private List<RttRegistro> registros;

	public String getRttCveDelegacion() {
		return rttCveDelegacion;
	}

	public void setRttCveDelegacion(String rttCveDelegacion) {
		this.rttCveDelegacion = rttCveDelegacion;
	}

	public String getRttNumCiclo() {
		return rttNumCiclo;
	}

	public void setRttNumCiclo(String rttNumCiclo) {
		this.rttNumCiclo = rttNumCiclo;
	}

	public RtcTipoOperacion getTipoOperacion() {
		return tipoOperacion;
	}

	public void setTipoOperacion(RtcTipoOperacion tipoOperacion) {
		this.tipoOperacion = tipoOperacion;
	}

	public String getStpInicioCarga() {
		return stpInicioCarga;
	}

	public void setStpInicioCarga(String stpInicioCarga) {
		this.stpInicioCarga = stpInicioCarga;
	}

	public String getStpFinCarga() {
		return stpFinCarga;
	}

	public void setStpFinCarga(String stpFinCarga) {
		this.stpFinCarga = stpFinCarga;
	}

	public Integer getNumTotalArchProcesados() {
		return numTotalArchProcesados;
	}

	public void setNumTotalArchProcesados(Integer numTotalArchProcesados) {
		this.numTotalArchProcesados = numTotalArchProcesados;
	}

	public Integer getNumTotalRegsProcesados() {
		return numTotalRegsProcesados;
	}

	public void setNumTotalRegsProcesados(Integer numTotalRegsProcesados) {
		this.numTotalRegsProcesados = numTotalRegsProcesados;
	}

	public Integer getNumTotalRegsExitosos() {
		return numTotalRegsExitosos;
	}

	public void setNumTotalRegsExitosos(Integer numTotalRegsExitosos) {
		this.numTotalRegsExitosos = numTotalRegsExitosos;
	}

	public Integer getNumTotalRegsError() {
		return numTotalRegsError;
	}

	public void setNumTotalRegsError(Integer numTotalRegsError) {
		this.numTotalRegsError = numTotalRegsError;
	}

	public String getDesNotaCarga() {
		return desNotaCarga;
	}

	public void setDesNotaCarga(String desNotaCarga) {
		this.desNotaCarga = desNotaCarga;
	}

	public List<RttRegistro> getRegistros() {
		return registros;
	}

	public void setRegistros(List<RttRegistro> registros) {
		this.registros = registros;
	}

}
