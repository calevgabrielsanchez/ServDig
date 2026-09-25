package mx.gob.imss.ctirss.delta.model.derechohabientes.negocio.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Sav005DTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String curp;
	private String nss;
	private String sDelegacion;
	private String cActual;
	private String cAnterior;
	private String aPaterno;
	private String aMaterno;
	private String nombre;
	private String domicilio;
	private String registroPatronal;
	private String ultimoMov;
	private String fechaUM;
	private String lugar;
	private String empleado;
	private String fecha;
	private Boolean cambioParcial;

	private String cadenaOriginal;
	private String selloDigital;
	private String secuenciaNotarial;
	private String numeroSerie;
	
	
	// -------------------------------------------------------------------
	// Se creara el DTO Beneficiaros ya que el reporte tiene mas de uno
	// -------------------------------------------------------------------
	private String nombreBeneficiario;
	private String curpBeneficiario;
	private String agregado;
	private String dVerificador;
	private String mesNacimiento;
	
	
	
	private List<BeneficiarioSav005DTO> beneficiarios;
	
	
	public List<BeneficiarioSav005DTO> getBeneficiarios() {
		return beneficiarios;
	}

	public void setBeneficiarios(List<BeneficiarioSav005DTO> beneficiarios) {
		this.beneficiarios = beneficiarios;
	}

	public void addBeneficiario( BeneficiarioSav005DTO beneficiario ){
		
		if( this.beneficiarios == null )
			this.beneficiarios = new ArrayList<BeneficiarioSav005DTO>();
		
		this.beneficiarios.add(beneficiario);
	}
	
	/**
	 * @return the curp
	 */
	public String getCurp() {
		return curp;
	}

	/**
	 * @param curp
	 *            the curp to set
	 */
	public void setCurp(String curp) {
		this.curp = curp;
	}

	/**
	 * @return the nss
	 */
	public String getNss() {
		return nss;
	}

	/**
	 * @param nss
	 *            the nss to set
	 */
	public void setNss(String nss) {
		this.nss = nss;
	}

	/**
	 * @return the sDelegacion
	 */
	public String getsDelegacion() {
		return sDelegacion;
	}

	/**
	 * @param sDelegacion
	 *            the sDelegacion to set
	 */
	public void setsDelegacion(String sDelegacion) {
		this.sDelegacion = sDelegacion;
	}

	/**
	 * @return the cActual
	 */
	public String getcActual() {
		return cActual;
	}

	/**
	 * @param cActual
	 *            the cActual to set
	 */
	public void setcActual(String cActual) {
		this.cActual = cActual;
	}

	/**
	 * @return the cAnterior
	 */
	public String getcAnterior() {
		return cAnterior;
	}

	/**
	 * @param cAnterior
	 *            the cAnterior to set
	 */
	public void setcAnterior(String cAnterior) {
		this.cAnterior = cAnterior;
	}

	/**
	 * @return the aPaterno
	 */
	public String getaPaterno() {
		return aPaterno;
	}

	/**
	 * @param aPaterno
	 *            the aPaterno to set
	 */
	public void setaPaterno(String aPaterno) {
		this.aPaterno = aPaterno;
	}

	/**
	 * @return the aMaterno
	 */
	public String getaMaterno() {
		return aMaterno;
	}

	/**
	 * @param aMaterno
	 *            the aMaterno to set
	 */
	public void setaMaterno(String aMaterno) {
		this.aMaterno = aMaterno;
	}

	/**
	 * @return the nombre
	 */
	public String getNombre() {
		return nombre;
	}

	/**
	 * @param nombre
	 *            the nombre to set
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	/**
	 * @return the domicilio
	 */
	public String getDomicilio() {
		return domicilio;
	}

	/**
	 * @param domicilio
	 *            the domicilio to set
	 */
	public void setDomicilio(String domicilio) {
		this.domicilio = domicilio;
	}

	/**
	 * @return the nombreBeneficiario
	 */
	public String getNombreBeneficiario() {
		return nombreBeneficiario;
	}

	/**
	 * @param nombreBeneficiario
	 *            the nombreBeneficiario to set
	 */
	public void setNombreBeneficiario(String nombreBeneficiario) {
		this.nombreBeneficiario = nombreBeneficiario;
	}

	/**
	 * @return the curpBeneficiario
	 */
	public String getCurpBeneficiario() {
		return curpBeneficiario;
	}

	/**
	 * @param curpBeneficiario
	 *            the curpBeneficiario to set
	 */
	public void setCurpBeneficiario(String curpBeneficiario) {
		this.curpBeneficiario = curpBeneficiario;
	}

	/**
	 * @return the agregado
	 */
	public String getAgregado() {
		return agregado;
	}

	/**
	 * @param agregado
	 *            the agregado to set
	 */
	public void setAgregado(String agregado) {
		this.agregado = agregado;
	}

	/**
	 * @return the dVerificador
	 */
	public String getdVerificador() {
		return dVerificador;
	}

	/**
	 * @param dVerificador
	 *            the dVerificador to set
	 */
	public void setdVerificador(String dVerificador) {
		this.dVerificador = dVerificador;
	}

	/**
	 * @return the mesNacimiento
	 */
	public String getMesNacimiento() {
		return mesNacimiento;
	}

	/**
	 * @param mesNacimiento
	 *            the mesNacimiento to set
	 */
	public void setMesNacimiento(String mesNacimiento) {
		this.mesNacimiento = mesNacimiento;
	}

	/**
	 * @return the registroPatronal
	 */
	public String getRegistroPatronal() {
		return registroPatronal;
	}

	/**
	 * @param registroPatronal
	 *            the registroPatronal to set
	 */
	public void setRegistroPatronal(String registroPatronal) {
		this.registroPatronal = registroPatronal;
	}

	/**
	 * @return the ultimoMov
	 */
	public String getUltimoMov() {
		return ultimoMov;
	}

	/**
	 * @param ultimoMov
	 *            the ultimoMov to set
	 */
	public void setUltimoMov(String ultimoMov) {
		this.ultimoMov = ultimoMov;
	}

	/**
	 * @return the fechaUM
	 */
	public String getFechaUM() {
		return fechaUM;
	}

	/**
	 * @param fechaUM
	 *            the fechaUM to set
	 */
	public void setFechaUM(String fechaUM) {
		this.fechaUM = fechaUM;
	}

	/**
	 * @return the lugar
	 */
	public String getLugar() {
		return lugar;
	}

	/**
	 * @param lugar
	 *            the lugar to set
	 */
	public void setLugar(String lugar) {
		this.lugar = lugar;
	}

	/**
	 * @return the empleado
	 */
	public String getEmpleado() {
		return empleado;
	}

	/**
	 * @param empleado
	 *            the empleado to set
	 */
	public void setEmpleado(String empleado) {
		this.empleado = empleado;
	}

	public String getFecha() {
		return fecha;
	}

	public void setFecha(String fecha) {
		this.fecha = fecha;
	}

	public Boolean getCambioParcial() {
		return cambioParcial;
	}

	public void setCambioParcial(Boolean cambioParcial) {
		this.cambioParcial = cambioParcial;
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

	

}
