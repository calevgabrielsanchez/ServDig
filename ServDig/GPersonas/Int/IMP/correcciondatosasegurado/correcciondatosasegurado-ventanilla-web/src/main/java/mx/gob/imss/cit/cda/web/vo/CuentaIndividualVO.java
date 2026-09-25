package mx.gob.imss.cit.cda.web.vo;

import java.io.Serializable;

public class CuentaIndividualVO implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5731769559617355816L;
	public String getNss() {
		return nss;
	}
	
	public void setNss(String nss) {
		this.nss = nss;
	}
	
	public String getRegistroPatronal() {
		return registroPatronal;
	}
	
	public void setRegistroPatronal(String registroPatronal) {
		this.registroPatronal = registroPatronal;
	}
	
	public String getClaveModalidad() {
		return claveModalidad;
	}
	
	public void setClaveModalidad(String claveModalidad) {
		this.claveModalidad = claveModalidad;
	}
	
	public String getFechaInicioMovimiento() {
		return fechaInicioMovimiento;
	}
	
	public void setFechaInicioMovimiento(String fechaInicioMovimiento) {
		this.fechaInicioMovimiento = fechaInicioMovimiento;
	}
	
	public int getNumeroConsecutivoPeriodos() {
		return numeroConsecutivoPeriodos;
	}
	
	public void setNumeroConsecutivoPeriodos(int numeroConsecutivoPeriodos) {
		this.numeroConsecutivoPeriodos = numeroConsecutivoPeriodos;
	}
	
	public String getCurp() {
		return curp;
	}
	
	public void setCurp(String curp) {
		this.curp = curp;
	}
	
	public String getFechaFinalMovimiento() {
		return fechaFinalMovimiento;
	}
	
	public void setFechaFinalMovimiento(String fechaFinalMovimiento) {
		this.fechaFinalMovimiento = fechaFinalMovimiento;
	}
	
	public String getOrigenMovimientoInicial() {
		return origenMovimientoInicial;
	}
	
	public void setOrigenMovimientoInicial(String origenMovimientoInicial) {
		this.origenMovimientoInicial = origenMovimientoInicial;
	}
	
	public String getOrigenMovimientoFinal() {
		return origenMovimientoFinal;
	}
	
	public void setOrigenMovimientoFinal(String origenMovimientoFinal) {
		this.origenMovimientoFinal = origenMovimientoFinal;
	}
	
	public int getTipoMovimientoIniintcial() {
		return tipoMovimientoIniintcial;
	}
	
	public void setTipoMovimientoIniintcial(int tipoMovimientoIniintcial) {
		this.tipoMovimientoIniintcial = tipoMovimientoIniintcial;
	}
	
	public int getTipoMovimientoFinal() {
		return tipoMovimientoFinal;
	}
	
	public void setTipoMovimientoFinal(int tipoMovimientoFinal) {
		this.tipoMovimientoFinal = tipoMovimientoFinal;
	}
	
	public String getFechaRecepcionMovimiento() {
		return fechaRecepcionMovimiento;
	}
	
	public void setFechaRecepcionMovimiento(String fechaRecepcionMovimiento) {
		this.fechaRecepcionMovimiento = fechaRecepcionMovimiento;
	}
	
	public Double getSalarioBase() {
		return salarioBase;
	}
	
	public void setSalarioBase(Double salarioBase) {
		this.salarioBase = salarioBase;
	}
	
	public String getTipoSalario() {
		return tipoSalario;
	}
	
	public void setTipoSalario(String tipoSalario) {
		this.tipoSalario = tipoSalario;
	}
	
	public String getJornadaSemanal() {
		return jornadaSemanal;
	}
	
	public void setJornadaSemanal(String jornadaSemanal) {
		this.jornadaSemanal = jornadaSemanal;
	}
	
	public String getEventual() {
		return eventual;
	}
	
	public void setEventual(String eventual) {
		this.eventual = eventual;
	}
	
	public String getSubrogacionServicio() {
		return subrogacionServicio;
	}
	
	public void setSubrogacionServicio(String subrogacionServicio) {
		this.subrogacionServicio = subrogacionServicio;
	}
	
	public String getHuelga() {
		return huelga;
	}
	
	public void setHuelga(String huelga) {
		this.huelga = huelga;
	}
	
	public String getExtemporaneoConvenioSuspencion() {
		return extemporaneoConvenioSuspencion;
	}
	
	public void setExtemporaneoConvenioSuspencion(String extemporaneoConvenioSuspencion) {
		this.extemporaneoConvenioSuspencion = extemporaneoConvenioSuspencion;
	}
	
	public String getFechaActualizacion() {
		return fechaActualizacion;
	}
	
	public void setFechaActualizacion(String fechaActualizacion) {
		this.fechaActualizacion = fechaActualizacion;
	}
	
	public int getClaveDelegacionOrigen() {
		return claveDelegacionOrigen;
	}
	
	public void setClaveDelegacionOrigen(int claveDelegacionOrigen) {
		this.claveDelegacionOrigen = claveDelegacionOrigen;
	}
	
	public int getClaveCiz() {
		return claveCiz;
	}
	
	public void setClaveCiz(int claveCiz) {
		this.claveCiz = claveCiz;
	}
	
	public String getFechaCarga() {
		return fechaCarga;
	}
	
	public void setFechaCarga(String fechaCarga) {
		this.fechaCarga = fechaCarga;
	}
	
	private String nss;
	private String registroPatronal;
	private String claveModalidad;
	private String fechaInicioMovimiento;
	private int numeroConsecutivoPeriodos;
	private String curp;
	private String fechaFinalMovimiento;
	private String origenMovimientoInicial;
	private String origenMovimientoFinal;
	private int tipoMovimientoIniintcial;
	private int tipoMovimientoFinal;
	private String fechaRecepcionMovimiento;
	private Double salarioBase;
	private String tipoSalario;
	private String jornadaSemanal;
	private String eventual;
	private String subrogacionServicio;
	private String huelga;
	private String extemporaneoConvenioSuspencion;
	private String fechaActualizacion;
	private int claveDelegacionOrigen;
	private int claveCiz;
	private String fechaCarga;

}
