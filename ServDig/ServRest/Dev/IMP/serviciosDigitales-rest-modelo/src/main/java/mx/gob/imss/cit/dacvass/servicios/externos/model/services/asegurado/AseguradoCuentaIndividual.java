package mx.gob.imss.cit.dacvass.servicios.externos.model.services.asegurado;

import java.io.Serializable;
import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class AseguradoCuentaIndividual implements Serializable{
	
	
	
    /**
	 * 
	 */
	private static final long serialVersionUID = 4603654375189911961L;
	private String nss;
    private String registroPatronal;
    private String claveModalidad;
    private Date fechaInicioMovimiento;
    private int numeroConsecutivoPeriodos;
    private String curp;
    private Date fechaFinalMovimiento;
    private String origenMovimientoInicial;
    private String origenMovimientoFinal;
    private int tipoMovimientoIniintcial;
    private int tipoMovimientoFinal;
    private Date fechaRecepcionMovimiento;
    private Double salarioBase;
    private String tipoSalario;
    private String jornadaSemanal;
    private String eventual;
    private String subrogacionServicio;
    private String huelga;
    private String extemporaneoConvenioSuspencion;
    private Date fechaActualizacion;
    private int claveDelegacionOrigen;
    private int claveCiz;
    private Date fechaCarga;
    
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
	public Date getFechaInicioMovimiento() {
		return fechaInicioMovimiento;
	}
	public void setFechaInicioMovimiento(Date fechaInicioMovimiento) {
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
	public Date getFechaFinalMovimiento() {
		return fechaFinalMovimiento;
	}
	public void setFechaFinalMovimiento(Date fechaFinalMovimiento) {
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
	public Date getFechaRecepcionMovimiento() {
		return fechaRecepcionMovimiento;
	}
	public void setFechaRecepcionMovimiento(Date fechaRecepcionMovimiento) {
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
	public Date getFechaActualizacion() {
		return fechaActualizacion;
	}
	public void setFechaActualizacion(Date fechaActualizacion) {
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
	public Date getFechaCarga() {
		return fechaCarga;
	}
	public void setFechaCarga(Date fechaCarga) {
		this.fechaCarga = fechaCarga;
	}
	
	
	@Override
	public String toString() {
		return "AseguradoCuentaIndividual [nss=" + nss + ", registroPatronal=" + registroPatronal + ", claveModalidad="
				+ claveModalidad + ", fechaInicioMovimiento=" + fechaInicioMovimiento + ", numeroConsecutivoPeriodos="
				+ numeroConsecutivoPeriodos + ", curp=" + curp + ", fechaFinalMovimiento=" + fechaFinalMovimiento
				+ ", origenMovimientoInicial=" + origenMovimientoInicial + ", origenMovimientoFinal="
				+ origenMovimientoFinal + ", tipoMovimientoIniintcial=" + tipoMovimientoIniintcial
				+ ", tipoMovimientoFinal=" + tipoMovimientoFinal + ", fechaRecepcionMovimiento="
				+ fechaRecepcionMovimiento + ", salarioBase=" + salarioBase + ", tipoSalario=" + tipoSalario
				+ ", jornadaSemanal=" + jornadaSemanal + ", eventual=" + eventual + ", subrogacionServicio="
				+ subrogacionServicio + ", huelga=" + huelga + ", extemporaneoConvenioSuspencion="
				+ extemporaneoConvenioSuspencion + ", fechaActualizacion=" + fechaActualizacion
				+ ", claveDelegacionOrigen=" + claveDelegacionOrigen + ", claveCiz=" + claveCiz + ", fechaCarga="
				+ fechaCarga + "]";
	}
    
    
        
    

}
