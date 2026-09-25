package mx.gob.imss.cit.dacvass.servicios.externos.model.services.siap;

import java.io.Serializable;

public class EmpleadoSiapRest implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 597153601663328136L;
	
	private int matricula;
	private String nombre;
	private String apellidoPaterno;
	private String apellidoMaterno;
	private String claveDepto;
	private String desDepto;
	private int clavePuesto;
	private String desPuesto;
	private int claveArea;
	private String desArea;
	private int cuantiaBasica;
	private String status;
	private int tc;
	private String desTc;
	private String rfc;
	private String curp;
	private double nss;
	private String tipo_empleado;
	private int delegacion;
	private String desDelegacion;
	private int localidad;
	private String desLocalidad;
	private int quincenaMes;
	private String fechaJubPen;
	private int tipoJubilacion;
	private int porcentajePension;
	private String fechaIngreso;
	private int antAnios;
	private int antQnas;
	private int antDias;
	private String fechaFaja;
	private int claveBaja;
	private String desBaja;
	private String fechaModificacion;
	private int fmodorden;
	public int getMatricula() {
		return matricula;
	}
	public void setMatricula(int matricula) {
		this.matricula = matricula;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getApellidoPaterno() {
		return apellidoPaterno;
	}
	public void setApellidoPaterno(String apellidoPaterno) {
		this.apellidoPaterno = apellidoPaterno;
	}
	public String getApellidoMaterno() {
		return apellidoMaterno;
	}
	public void setApellidoMaterno(String apellidoMaterno) {
		this.apellidoMaterno = apellidoMaterno;
	}
	public String getClaveDepto() {
		return claveDepto;
	}
	public void setClaveDepto(String claveDepto) {
		this.claveDepto = claveDepto;
	}
	public String getDesDepto() {
		return desDepto;
	}
	public void setDesDepto(String desDepto) {
		this.desDepto = desDepto;
	}
	public int getClavePuesto() {
		return clavePuesto;
	}
	public void setClavePuesto(int clavePuesto) {
		this.clavePuesto = clavePuesto;
	}
	public String getDesPuesto() {
		return desPuesto;
	}
	public void setDesPuesto(String desPuesto) {
		this.desPuesto = desPuesto;
	}
	public int getClaveArea() {
		return claveArea;
	}
	public void setClaveArea(int claveArea) {
		this.claveArea = claveArea;
	}
	public String getDesArea() {
		return desArea;
	}
	public void setDesArea(String desArea) {
		this.desArea = desArea;
	}
	public int getCuantiaBasica() {
		return cuantiaBasica;
	}
	public void setCuantiaBasica(int cuantiaBasica) {
		this.cuantiaBasica = cuantiaBasica;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public int getTc() {
		return tc;
	}
	public void setTc(int tc) {
		this.tc = tc;
	}
	public String getDesTc() {
		return desTc;
	}
	public void setDesTc(String desTc) {
		this.desTc = desTc;
	}
	public String getRfc() {
		return rfc;
	}
	public void setRfc(String rfc) {
		this.rfc = rfc;
	}
	public String getCurp() {
		return curp;
	}
	public void setCurp(String curp) {
		this.curp = curp;
	}
	public double getNss() {
		return nss;
	}
	public void setNss(double nss) {
		this.nss = nss;
	}
	public String getTipo_empleado() {
		return tipo_empleado;
	}
	public void setTipo_empleado(String tipo_empleado) {
		this.tipo_empleado = tipo_empleado;
	}
	public int getDelegacion() {
		return delegacion;
	}
	public void setDelegacion(int delegacion) {
		this.delegacion = delegacion;
	}
	public String getDesDelegacion() {
		return desDelegacion;
	}
	public void setDesDelegacion(String desDelegacion) {
		this.desDelegacion = desDelegacion;
	}
	public int getLocalidad() {
		return localidad;
	}
	public void setLocalidad(int localidad) {
		this.localidad = localidad;
	}
	public String getDesLocalidad() {
		return desLocalidad;
	}
	public void setDesLocalidad(String desLocalidad) {
		this.desLocalidad = desLocalidad;
	}
	public int getQuincenaMes() {
		return quincenaMes;
	}
	public void setQuincenaMes(int quincenaMes) {
		this.quincenaMes = quincenaMes;
	}
	public String getFechaJubPen() {
		return fechaJubPen;
	}
	public void setFechaJubPen(String fechaJubPen) {
		this.fechaJubPen = fechaJubPen;
	}
	public int getTipoJubilacion() {
		return tipoJubilacion;
	}
	public void setTipoJubilacion(int tipoJubilacion) {
		this.tipoJubilacion = tipoJubilacion;
	}
	public int getPorcentajePension() {
		return porcentajePension;
	}
	public void setPorcentajePension(int porcentajePension) {
		this.porcentajePension = porcentajePension;
	}
	public String getFechaIngreso() {
		return fechaIngreso;
	}
	public void setFechaIngreso(String fechaIngreso) {
		this.fechaIngreso = fechaIngreso;
	}
	public int getAntAnios() {
		return antAnios;
	}
	public void setAntAnios(int antAnios) {
		this.antAnios = antAnios;
	}
	public int getAntQnas() {
		return antQnas;
	}
	public void setAntQnas(int antQnas) {
		this.antQnas = antQnas;
	}
	public int getAntDias() {
		return antDias;
	}
	public void setAntDias(int antDias) {
		this.antDias = antDias;
	}
	public String getFechaFaja() {
		return fechaFaja;
	}
	public void setFechaFaja(String fechaFaja) {
		this.fechaFaja = fechaFaja;
	}
	public int getClaveBaja() {
		return claveBaja;
	}
	public void setClaveBaja(int claveBaja) {
		this.claveBaja = claveBaja;
	}
	public String getDesBaja() {
		return desBaja;
	}
	public void setDesBaja(String desBaja) {
		this.desBaja = desBaja;
	}
	public String getFechaModificacion() {
		return fechaModificacion;
	}
	public void setFechaModificacion(String fechaModificacion) {
		this.fechaModificacion = fechaModificacion;
	}
	public int getFmodorden() {
		return fmodorden;
	}
	public void setFmodorden(int fmodorden) {
		this.fmodorden = fmodorden;
	}
	
	
	
	

}
