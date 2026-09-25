package mx.gob.imss.ctirss.delta.model.derechohabientes.documentos;

import java.io.Serializable;

public class CartillaSalud implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3907765181638348462L;
	
	private String folio;
	private String nombre;
	private String nss;
	private String umf;
	private String horario;
	private String consultorio;
	private String curp;
	private String domicilio;
	private String entidadFederativaDomicilio;
	private String lugarNacimiento;
	private String dia;
	private String mes;
	private String anio;
	private String calleNumero;
	private String colonia;
	private String entidadFederativaNacimiento;
	private String municipio;
	private String coloniaNacimiento;
	private String municipioNacimiento;
	private String edad;
	private String agregadoMedico;
	private String mesNacimiento;
	private String clave;
	private String modalidad;
	private String clinica;
	private String nombreAsegurado;
	
	public String getNombreAsegurado() {
		return nombreAsegurado;
	}

	public void setNombreAsegurado(String nombreAsegurado) {
		this.nombreAsegurado = nombreAsegurado;
	}

	public String getAgregadoMedico() {
		return agregadoMedico;
	}

	public void setAgregadoMedico(String agregadoMedico) {
		this.agregadoMedico = agregadoMedico;
	}

	public String getEdad() {
		return edad;
	}

	public void setEdad(String edad) {
		this.edad = edad;
	}

	public String getColoniaNacimiento() {
		return coloniaNacimiento;
	}

	public void setColoniaNacimiento(String coloniaNacimiento) {
		this.coloniaNacimiento = coloniaNacimiento;
	}

	public String getMunicipioNacimiento() {
		return municipioNacimiento;
	}

	public void setMunicipioNacimiento(String municipioNacimiento) {
		this.municipioNacimiento = municipioNacimiento;
	}

	public String getCalleNumero() {
		return calleNumero;
	}

	public void setCalleNumero(String calleNumero) {
		this.calleNumero = calleNumero;
	}

	public String getColonia() {
		return colonia;
	}

	public void setColonia(String colonia) {
		this.colonia = colonia;
	}

	public String getMunicipio() {
		return municipio;
	}

	public void setMunicipio(String municipio) {
		this.municipio = municipio;
	}

	

	public String getFolio() {
		return folio;
	}

	public void setFolio(String folio) {
		this.folio = folio;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getNss() {
		return nss;
	}

	public void setNss(String nss) {
		this.nss = nss;
	}

	public String getUmf() {
		return umf;
	}

	public void setUmf(String umf) {
		this.umf = umf;
	}

	public String getHorario() {
		return horario;
	}

	public void setHorario(String horario) {
		this.horario = horario;
	}

	public String getConsultorio() {
		return consultorio;
	}

	public void setConsultorio(String consultorio) {
		this.consultorio = consultorio;
	}

	public String getCurp() {
		return curp;
	}

	public void setCurp(String curp) {
		this.curp = curp;
	}

	public String getDomicilio() {
		return domicilio;
	}

	public void setDomicilio(String domicilio) {
		this.domicilio = domicilio;
	}

	public String getLugarNacimiento() {
		return lugarNacimiento;
	}

	public void setLugarNacimiento(String lugarNacimiento) {
		this.lugarNacimiento = lugarNacimiento;
	}

	public String getDia() {
		return dia;
	}

	public void setDia(String dia) {
		this.dia = dia;
	}

	public String getMes() {
		return mes;
	}

	public void setMes(String mes) {
		this.mes = mes;
	}

	public String getAnio() {
		return anio;
	}

	public void setAnio(String anio) {
		this.anio = anio;
	}

	public String getEntidadFederativaDomicilio() {
		return entidadFederativaDomicilio;
	}

	public void setEntidadFederativaDomicilio(String entidadFederativaDomicilio) {
		this.entidadFederativaDomicilio = entidadFederativaDomicilio;
	}

	public String getEntidadFederativaNacimiento() {
		return entidadFederativaNacimiento;
	}

	public void setEntidadFederativaNacimiento(String entidadFederativaNacimiento) {
		this.entidadFederativaNacimiento = entidadFederativaNacimiento;
	}

	public String getMesNacimiento() {
		return mesNacimiento;
	}

	public void setMesNacimiento(String mesNacimiento) {
		this.mesNacimiento = mesNacimiento;
	}

	public String getClave() {
		return clave;
	}

	public void setClave(String clave) {
		this.clave = clave;
	}

	public String getModalidad() {
		return modalidad;
	}

	public void setModalidad(String modalidad) {
		this.modalidad = modalidad;
	}

	public String getClinica() {
		return clinica;
	}

	public void setClinica(String clinica) {
		this.clinica = clinica;
	}

}
