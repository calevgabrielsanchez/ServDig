package mx.gob.imss.ctirss.delta.model.derechohabientes.negocio.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Sav002DTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3907765181638348462L;
	
	
	private String consultorio;
	private String curp;
	private String calleNumero;
	private String colonia;
	private String municipio;
	private String umf;
	private String nss;
	private String entidadFederativaNacimiento;
	private String edad;
	private String dia;
	private String mes;
	private String anio;
	private String clinica;
	private String modalidad;
	private String nombreAsegurado;
	private String documentos;
	private String domicilio;
	private Long idPersona;
	
	private String cadenaOriginal;
	private String selloDigital;
	private String secuenciaNotarial;
	private String numeroSerie;

	
	private String nombre;
	private String agregadoMedico;
	private String mesNacimiento;
	
	// --------------------------------------------------------------------------
	// Se agrega una coleccion de beneficiarios para que el reporte muestre
	// todos los afectados por el trámite
	// --------------------------------------------------------------------------
	private List<BeneficiarioSav002DTO> beneficiarios;
	
	public String getDocumentos() {
		return documentos;
	}
	public void setDocumentos(String documentos) {
		this.documentos = documentos;
	}
	public String getDomicilio() {
		return domicilio;
	}
	public void setDomicilio(String domicilio) {
		this.domicilio = domicilio;
	}
	/**
	 * @return the agregadoMedico
	 */
	public String getAgregadoMedico() {
		return agregadoMedico;
	}
	/**
	 * @param agregadoMedico the agregadoMedico to set
	 */
	public void setAgregadoMedico(String agregadoMedico) {
		this.agregadoMedico = agregadoMedico;
	}
	/**
	 * @return the consultorio
	 */
	public String getConsultorio() {
		return consultorio;
	}
	/**
	 * @param consultorio the consultorio to set
	 */
	public void setConsultorio(String consultorio) {
		this.consultorio = consultorio;
	}
	/**
	 * @return the curp
	 */
	public String getCurp() {
		return curp;
	}
	/**
	 * @param curp the curp to set
	 */
	public void setCurp(String curp) {
		this.curp = curp;
	}
	/**
	 * @return the calleNumero
	 */
	public String getCalleNumero() {
		return calleNumero;
	}
	/**
	 * @param calleNumero the calleNumero to set
	 */
	public void setCalleNumero(String calleNumero) {
		this.calleNumero = calleNumero;
	}
	/**
	 * @return the colonia
	 */
	public String getColonia() {
		return colonia;
	}
	/**
	 * @param colonia the colonia to set
	 */
	public void setColonia(String colonia) {
		this.colonia = colonia;
	}
	/**
	 * @return the municipio
	 */
	public String getMunicipio() {
		return municipio;
	}
	/**
	 * @param municipio the municipio to set
	 */
	public void setMunicipio(String municipio) {
		this.municipio = municipio;
	}
	/**
	 * @return the umf
	 */
	public String getUmf() {
		return umf;
	}
	/**
	 * @param umf the umf to set
	 */
	public void setUmf(String umf) {
		this.umf = umf;
	}
	/**
	 * @return the nombre
	 */
	public String getNombre() {
		return nombre;
	}
	/**
	 * @param nombre the nombre to set
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	/**
	 * @return the nss
	 */
	public String getNss() {
		return nss;
	}
	/**
	 * @param nss the nss to set
	 */
	public void setNss(String nss) {
		this.nss = nss;
	}
	/**
	 * @return the entidadFederativaNacimiento
	 */
	public String getEntidadFederativaNacimiento() {
		return entidadFederativaNacimiento;
	}
	/**
	 * @param entidadFederativaNacimiento the entidadFederativaNacimiento to set
	 */
	public void setEntidadFederativaNacimiento(String entidadFederativaNacimiento) {
		this.entidadFederativaNacimiento = entidadFederativaNacimiento;
	}
	/**
	 * @return the edad
	 */
	public String getEdad() {
		return edad;
	}
	/**
	 * @param edad the edad to set
	 */
	public void setEdad(String edad) {
		this.edad = edad;
	}
	/**
	 * @return the dia
	 */
	public String getDia() {
		return dia;
	}
	/**
	 * @param dia the dia to set
	 */
	public void setDia(String dia) {
		this.dia = dia;
	}
	/**
	 * @return the mes
	 */
	public String getMes() {
		return mes;
	}
	/**
	 * @param mes the mes to set
	 */
	public void setMes(String mes) {
		this.mes = mes;
	}
	/**
	 * @return the anio
	 */
	public String getAnio() {
		return anio;
	}
	/**
	 * @param anio the anio to set
	 */
	public void setAnio(String anio) {
		this.anio = anio;
	}
	/**
	 * @return the clinica
	 */
	public String getClinica() {
		return clinica;
	}
	/**
	 * @param clinica the clinica to set
	 */
	public void setClinica(String clinica) {
		this.clinica = clinica;
	}
	/**
	 * @return the mesNacimiento
	 */
	public String getMesNacimiento() {
		return mesNacimiento;
	}
	/**
	 * @param mesNacimiento the mesNacimiento to set
	 */
	public void setMesNacimiento(String mesNacimiento) {
		this.mesNacimiento = mesNacimiento;
	}
	/**
	 * @return the modalidad
	 */
	public String getModalidad() {
		return modalidad;
	}
	/**
	 * @param modalidad the modalidad to set
	 */
	public void setModalidad(String modalidad) {
		this.modalidad = modalidad;
	}
	/**
	 * @return the nombreAsegurado
	 */
	public String getNombreAsegurado() {
		return nombreAsegurado;
	}
	/**
	 * @param nombreAsegurado the nombreAsegurado to set
	 */
	public void setNombreAsegurado(String nombreAsegurado) {
		this.nombreAsegurado = nombreAsegurado;
	}
	public Long getIdPersona() {
		return idPersona;
	}
	public void setIdPersona(Long idPersona) {
		this.idPersona = idPersona;
	}
	public String getCadenaOriginal() {
		return cadenaOriginal;
	}
	public void setCadenaOriginal(String cadenaOriginal) {
		this.cadenaOriginal = cadenaOriginal;
	}
	public String getSelloDigital() {
		return selloDigital;
	}
	public void setSelloDigital(String selloDigital) {
		this.selloDigital = selloDigital;
	}
	public String getSecuenciaNotarial() {
		return secuenciaNotarial;
	}
	public void setSecuenciaNotarial(String secuenciaNotarial) {
		this.secuenciaNotarial = secuenciaNotarial;
	}
	public String getNumeroSerie() {
		return numeroSerie;
	}
	public void setNumeroSerie(String numeroSerie) {
		this.numeroSerie = numeroSerie;
	}
	
	public List<BeneficiarioSav002DTO> getBeneficiarios() {
		return beneficiarios;
	}
	public void setBeneficiarios(List<BeneficiarioSav002DTO> beneficiarios) {
		this.beneficiarios = beneficiarios;
	}
	
	public void addBeneficiario(BeneficiarioSav002DTO beneficiario){
		
		if( this.beneficiarios == null )
			this.beneficiarios = new ArrayList<BeneficiarioSav002DTO>();
		
		this.beneficiarios.add(beneficiario);
		
	}
	
	
	
}
