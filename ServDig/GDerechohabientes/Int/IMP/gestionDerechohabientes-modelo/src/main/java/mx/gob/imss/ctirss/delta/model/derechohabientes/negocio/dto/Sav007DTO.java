package mx.gob.imss.ctirss.delta.model.derechohabientes.negocio.dto;

import java.io.Serializable;

public class Sav007DTO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String nss;
	private String curp;
	private String aPaterno;
	private String aMaterno;
	private String nombre;
	private String aPaternoB;
	private String aMaternoB;
	private String nombreB;
	private String delegacion;
	private String subDelegacion;
	private String umf;
	private Boolean hombre;
	private String calidad;
	private Boolean incapacidad;
	private Boolean prorrogaAut;
	private String medicoResponsable;
	private String matricula;
	private String observaciones;
	private Boolean existeEnfermedad;
	private Integer enfermedad;
	private String  diagnostico;
	private String  lugar;
	private String mesNacimiento;
	private String anioNacimiento;
	private String fechaElaboracion;
	private String fechaProbInicio;
	private String fechaProbTermino;
	private String fechaConcepcion;
	private String fechaParto;
	private String fechaSolicitud;
	private String fechaInicioProrroga;
	private String fechaFinProrroga;
	private String fechaRevision;
	private String fechaInicioProrrogaLaudo;
	private String fechaInicioProrrogaAcuerdo;
	private String fechaFinProrrogaLaudo;
	private String fechaFinProrrogaAcuerdo;
	
	

	private String cadenaOriginal;
	private String selloDigital;
	private String secuenciaNotarial;
	private String numeroSerie;
	
	
	/**
	 * @return the fechaRevision
	 */
	public String getFechaRevision() {
		return fechaRevision;
	}
	/**
	 * @param fechaRevision the fechaRevision to set
	 */
	public void setFechaRevision(String fechaRevision) {
		this.fechaRevision = fechaRevision;
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
	 * @return the aPaterno
	 */
	public String getaPaterno() {
		return aPaterno;
	}
	/**
	 * @param aPaterno the aPaterno to set
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
	 * @param aMaterno the aMaterno to set
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
	 * @param nombre the nombre to set
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	/**
	 * @return the fechaSolicitud
	 */
	public String getFechaSolicitud() {
		return fechaSolicitud;
	}
	/**
	 * @param fechaSolicitud the fechaSolicitud to set
	 */
	public void setFechaSolicitud(String fechaSolicitud) {
		this.fechaSolicitud = fechaSolicitud;
	}
	/**
	 * @return the aPaternoB
	 */
	public String getaPaternoB() {
		return aPaternoB;
	}
	/**
	 * @param aPaternoB the aPaternoB to set
	 */
	public void setaPaternoB(String aPaternoB) {
		this.aPaternoB = aPaternoB;
	}
	/**
	 * @return the aMaternoB
	 */
	public String getaMaternoB() {
		return aMaternoB;
	}
	/**
	 * @param aMaternoB the aMaternoB to set
	 */
	public void setaMaternoB(String aMaternoB) {
		this.aMaternoB = aMaternoB;
	}
	/**
	 * @return the nombreB
	 */
	public String getNombreB() {
		return nombreB;
	}
	/**
	 * @param nombreB the nombreB to set
	 */
	public void setNombreB(String nombreB) {
		this.nombreB = nombreB;
	}
	/**
	 * @return the delegacion
	 */
	public String getDelegacion() {
		return delegacion;
	}
	/**
	 * @param delegacion the delegacion to set
	 */
	public void setDelegacion(String delegacion) {
		this.delegacion = delegacion;
	}
	/**
	 * @return the subDelegacion
	 */
	public String getSubDelegacion() {
		return subDelegacion;
	}
	/**
	 * @param subDelegacion the subDelegacion to set
	 */
	public void setSubDelegacion(String subDelegacion) {
		this.subDelegacion = subDelegacion;
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
	 * @return the hombre
	 */
	public Boolean getHombre() {
		return hombre;
	}
	/**
	 * @param hombre the hombre to set
	 */
	public void setHombre(Boolean hombre) {
		this.hombre = hombre;
	}
	/**
	 * @return the calidad
	 */
	public String getCalidad() {
		return calidad;
	}
	/**
	 * @param calidad the calidad to set
	 */
	public void setCalidad(String calidad) {
		this.calidad = calidad;
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
	 * @return the anioNacimiento
	 */
	public String getAnioNacimiento() {
		return anioNacimiento;
	}
	/**
	 * @param anioNacimiento the anioNacimiento to set
	 */
	public void setAnioNacimiento(String anioNacimiento) {
		this.anioNacimiento = anioNacimiento;
	}
	/**
	 * @return the fechaConcepcion
	 */
	public String getFechaConcepcion() {
		return fechaConcepcion;
	}
	/**
	 * @param fechaConcepcion the fechaConcepcion to set
	 */
	public void setFechaConcepcion(String fechaConcepcion) {
		this.fechaConcepcion = fechaConcepcion;
	}
	/**
	 * @return the fechaParto
	 */
	public String getFechaParto() {
		return fechaParto;
	}
	/**
	 * @param fechaParto the fechaParto to set
	 */
	public void setFechaParto(String fechaParto) {
		this.fechaParto = fechaParto;
	}
	/**
	 * @return the incapacidad
	 */
	public Boolean getIncapacidad() {
		return incapacidad;
	}
	/**
	 * @param incapacidad the incapacidad to set
	 */
	public void setIncapacidad(Boolean incapacidad) {
		this.incapacidad = incapacidad;
	}
	
	/**
	 * @return the prorrogaAut
	 */
	public Boolean getProrrogaAut() {
		return prorrogaAut;
	}
	/**
	 * @param prorrogaAut the prorrogaAut to set
	 */
	public void setProrrogaAut(Boolean prorrogaAut) {
		this.prorrogaAut = prorrogaAut;
	}
	/**
	 * @return the fechaInicioProrroga
	 */
	public String getFechaInicioProrroga() {
		return fechaInicioProrroga;
	}
	/**
	 * @param fechaInicioProrroga the fechaInicioProrroga to set
	 */
	public void setFechaInicioProrroga(String fechaInicioProrroga) {
		this.fechaInicioProrroga = fechaInicioProrroga;
	}
	/**
	 * @return the fechaFinProrroga
	 */
	public String getFechaFinProrroga() {
		return fechaFinProrroga;
	}
	/**
	 * @param fechaFinProrroga the fechaFinProrroga to set
	 */
	public void setFechaFinProrroga(String fechaFinProrroga) {
		this.fechaFinProrroga = fechaFinProrroga;
	}
	/**
	 * @return the medicoResponsable
	 */
	public String getMedicoResponsable() {
		return medicoResponsable;
	}
	/**
	 * @param medicoResponsable the medicoResponsable to set
	 */
	public void setMedicoResponsable(String medicoResponsable) {
		this.medicoResponsable = medicoResponsable;
	}
	/**
	 * @return the matricula
	 */
	public String getMatricula() {
		return matricula;
	}
	/**
	 * @param matricula the matricula to set
	 */
	public void setMatricula(String matricula) {
		this.matricula = matricula;
	}
	/**
	 * @return the observaciones
	 */
	public String getObservaciones() {
		return observaciones;
	}
	/**
	 * @param observaciones the observaciones to set
	 */
	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
	}
	/**
	 * @return the existeEnfermedad
	 */
	public Boolean getExisteEnfermedad() {
		return existeEnfermedad;
	}
	/**
	 * @param existeEnfermedad the existeEnfermedad to set
	 */
	public void setExisteEnfermedad(Boolean existeEnfermedad) {
		this.existeEnfermedad = existeEnfermedad;
	}
	/**
	 * @return the enfermedad
	 */
	public Integer getEnfermedad() {
		return enfermedad;
	}
	/**
	 * @param enfermedad the enfermedad to set
	 */
	public void setEnfermedad(Integer enfermedad) {
		this.enfermedad = enfermedad;
	}
	/**
	 * @return the diagnostico
	 */
	public String getDiagnostico() {
		return diagnostico;
	}
	/**
	 * @param diagnostico the diagnostico to set
	 */
	public void setDiagnostico(String diagnostico) {
		this.diagnostico = diagnostico;
	}
	/**
	 * @return the lugar
	 */
	public String getLugar() {
		return lugar;
	}
	/**
	 * @param lugar the lugar to set
	 */
	public void setLugar(String lugar) {
		this.lugar = lugar;
	}
	
	public String getFechaElaboracion() {
		return fechaElaboracion;
	}
	public void setFechaElaboracion(String fechaElaboracion) {
		this.fechaElaboracion = fechaElaboracion;
	}
	/**
	 * @return the fechaProbInicio
	 */
	public String getFechaProbInicio() {
		return fechaProbInicio;
	}
	/**
	 * @param fechaProbInicio the fechaProbInicio to set
	 */
	public void setFechaProbInicio(String fechaProbInicio) {
		this.fechaProbInicio = fechaProbInicio;
	}
	/**
	 * @return the fechaProbTermino
	 */
	public String getFechaProbTermino() {
		return fechaProbTermino;
	}
	/**
	 * @param fechaProbTermino the fechaProbTermino to set
	 */
	public void setFechaProbTermino(String fechaProbTermino) {
		this.fechaProbTermino = fechaProbTermino;
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
	
	public String getFechaInicioProrrogaLaudo() {
		return fechaInicioProrrogaLaudo;
	}
	public void setFechaInicioProrrogaLaudo(String fechaInicioProrrogaLaudo) {
		this.fechaInicioProrrogaLaudo = fechaInicioProrrogaLaudo;
	}
	public String getFechaInicioProrrogaAcuerdo() {
		return fechaInicioProrrogaAcuerdo;
	}
	public void setFechaInicioProrrogaAcuerdo(String fechaInicioProrrogaAcuerdo) {
		this.fechaInicioProrrogaAcuerdo = fechaInicioProrrogaAcuerdo;
	}
	public String getFechaFinProrrogaLaudo() {
		return fechaFinProrrogaLaudo;
	}
	public void setFechaFinProrrogaLaudo(String fechaFinProrrogaLaudo) {
		this.fechaFinProrrogaLaudo = fechaFinProrrogaLaudo;
	}
	public String getFechaFinProrrogaAcuerdo() {
		return fechaFinProrrogaAcuerdo;
	}
	public void setFechaFinProrrogaAcuerdo(String fechaFinProrrogaAcuerdo) {
		this.fechaFinProrrogaAcuerdo = fechaFinProrrogaAcuerdo;
	}
	
	
}
