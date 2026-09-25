package mx.gob.imss.ctirss.delta.model.gestion.individuo;

import mx.gob.imss.ctirss.delta.framework.base.model.AbstractModel;

public class AfectarDatosPersonaWrapper extends AbstractModel {

	private static final long serialVersionUID = -3964915378981862487L;

	
	public AfectarDatosPersonaWrapper(){
		
	}
	
	public AfectarDatosPersonaWrapper(Persona persona){
		
		if( persona instanceof Fisica )
			this.fisica = (Fisica)persona;
		
		if( persona instanceof Moral )
			this.moral = (Moral)persona;
	}
	
	private Fisica fisica;
	private Moral moral;

	/*
	 * Banderas para indicar qué datos se desean modificar dentro del grupo de
	 * DATOS RENAPO
	 */
	private boolean modificarDatosRENAPO;
	private boolean modificarNombre;
	private boolean modificarCURP;
	private boolean modificarSexo;
	private boolean modificarFechaNacimiento;
	private boolean modificarLugarNacimiento;
	private boolean modificarDocumentoProbatorio;

	/*
	 * Banderas para indicar qué datos se desean modificar dentro del grupo de
	 * DATOS SAT
	 */
	private boolean modificarDatosSAT;
	private boolean modificarRFC;
	private boolean modificarSituacion;
	private boolean modificarDomicilioFiscal;
	private boolean modificarMediosContactoFiscales;
	private boolean modificarRazonSocial;
	private boolean modificarFechaCreacion;
	private boolean modificarTipoSociedad;

	/*
	 * Banderas para indicar qué datos se desean modificar dentro del grupo de
	 * DATOS COMPLEMENTARIOS
	 */
	private boolean modificarDatosComplementarios;
	private boolean modificarDomicilioParticular;
	private boolean modificarMediosContactoParticular;
	private boolean modificarActaConstitutiva;
	private boolean modificarRegistroSindicato;

	// Especifica desde qué servicio se está haciendo la afectación manual
	private TipoServicioModificacionEnum tipoServicio;
	
	private boolean modificarFechaDefuncion;
	//bandera para modificar el anio y el mes que vienen de SINDO
	private boolean modificarMesYAnioNacimiento;
	//bandera para indicar que el mes y anio de nacimiento se van a sacar de la fecha de nacimiento y no de lo que traiga la persona
	private boolean tomarMesYAnioDeFechaNacimiento;
	
	
	/**
	 * @return the fisica
	 */
	public Fisica getFisica() {
		return fisica;
	}

	/**
	 * @param fisica
	 *            the fisica to set
	 */
	public void setFisica(Fisica fisica) {
		this.fisica = fisica;
	}

	/**
	 * @return the moral
	 */
	public Moral getMoral() {
		return moral;
	}

	/**
	 * @param moral
	 *            the moral to set
	 */
	public void setMoral(Moral moral) {
		this.moral = moral;
	}

	/**
	 * @return the modificarDatosRENAPO
	 */
	public boolean getModificarDatosRENAPO() {
		return modificarDatosRENAPO;
	}

	/**
	 * @param modificarDatosRENAPO
	 *            the modificarDatosRENAPO to set
	 */
	public void setModificarDatosRENAPO(boolean modificarDatosRENAPO) {
		this.modificarDatosRENAPO = modificarDatosRENAPO;
	}

	/**
	 * @return the modificarNombre
	 */
	public boolean getModificarNombre() {
		return modificarNombre;
	}

	/**
	 * @param modificarNombre
	 *            the modificarNombre to set
	 */
	public void setModificarNombre(boolean modificarNombre) {
		this.modificarNombre = modificarNombre;
	}

	/**
	 * @return the modificarCURP
	 */
	public boolean getModificarCURP() {
		return modificarCURP;
	}

	/**
	 * @param modificarCURP
	 *            the modificarCURP to set
	 */
	public void setModificarCURP(boolean modificarCURP) {
		this.modificarCURP = modificarCURP;
	}

	/**
	 * @return the modificarSexo
	 */
	public boolean getModificarSexo() {
		return modificarSexo;
	}

	/**
	 * @param modificarSexo
	 *            the modificarSexo to set
	 */
	public void setModificarSexo(boolean modificarSexo) {
		this.modificarSexo = modificarSexo;
	}

	/**
	 * @return the modificarFechaNacimiento
	 */
	public boolean getModificarFechaNacimiento() {
		return modificarFechaNacimiento;
	}

	/**
	 * @param modificarFechaNacimiento
	 *            the modificarFechaNacimiento to set
	 */
	public void setModificarFechaNacimiento(boolean modificarFechaNacimiento) {
		this.modificarFechaNacimiento = modificarFechaNacimiento;
	}

	/**
	 * @return the modificarLugarNacimiento
	 */
	public boolean getModificarLugarNacimiento() {
		return modificarLugarNacimiento;
	}

	/**
	 * @param modificarLugarNacimiento
	 *            the modificarLugarNacimiento to set
	 */
	public void setModificarLugarNacimiento(boolean modificarLugarNacimiento) {
		this.modificarLugarNacimiento = modificarLugarNacimiento;
	}

	/**
	 * @return the modificarDocumentoProbatorio
	 */
	public boolean getModificarDocumentoProbatorio() {
		return modificarDocumentoProbatorio;
	}

	/**
	 * @param modificarDocumentoProbatorio
	 *            the modificarDocumentoProbatorio to set
	 */
	public void setModificarDocumentoProbatorio(
			boolean modificarDocumentoProbatorio) {
		this.modificarDocumentoProbatorio = modificarDocumentoProbatorio;
	}

	/**
	 * @return the modificarDatosSAT
	 */
	public boolean getModificarDatosSAT() {
		return modificarDatosSAT;
	}

	/**
	 * @param modificarDatosSAT
	 *            the modificarDatosSAT to set
	 */
	public void setModificarDatosSAT(boolean modificarDatosSAT) {
		this.modificarDatosSAT = modificarDatosSAT;
	}

	/**
	 * @return the modificarRFC
	 */
	public boolean getModificarRFC() {
		return modificarRFC;
	}

	/**
	 * @param modificarRFC
	 *            the modificarRFC to set
	 */
	public void setModificarRFC(boolean modificarRFC) {
		this.modificarRFC = modificarRFC;
	}

	/**
	 * @return the modificarSituacion
	 */
	public boolean getModificarSituacion() {
		return modificarSituacion;
	}

	/**
	 * @param modificarSituacion
	 *            the modificarSituacion to set
	 */
	public void setModificarSituacion(boolean modificarSituacion) {
		this.modificarSituacion = modificarSituacion;
	}

	/**
	 * @return the modificarDomicilioFiscal
	 */
	public boolean getModificarDomicilioFiscal() {
		return modificarDomicilioFiscal;
	}

	/**
	 * @param modificarDomicilioFiscal
	 *            the modificarDomicilioFiscal to set
	 */
	public void setModificarDomicilioFiscal(boolean modificarDomicilioFiscal) {
		this.modificarDomicilioFiscal = modificarDomicilioFiscal;
	}

	/**
	 * @return the modificarMediosContactoFiscales
	 */
	public boolean getModificarMediosContactoFiscales() {
		return modificarMediosContactoFiscales;
	}

	/**
	 * @param modificarMediosContactoFiscales
	 *            the modificarMediosContactoFiscales to set
	 */
	public void setModificarMediosContactoFiscales(
			boolean modificarMediosContactoFiscales) {
		this.modificarMediosContactoFiscales = modificarMediosContactoFiscales;
	}

	/**
	 * @return the modificarRazonSocial
	 */
	public boolean getModificarRazonSocial() {
		return modificarRazonSocial;
	}

	/**
	 * @param modificarRazonSocial
	 *            the modificarRazonSocial to set
	 */
	public void setModificarRazonSocial(boolean modificarRazonSocial) {
		this.modificarRazonSocial = modificarRazonSocial;
	}

	/**
	 * @return the modificarFechaCreacion
	 */
	public boolean getModificarFechaCreacion() {
		return modificarFechaCreacion;
	}

	/**
	 * @param modificarFechaCreacion
	 *            the modificarFechaCreacion to set
	 */
	public void setModificarFechaCreacion(boolean modificarFechaCreacion) {
		this.modificarFechaCreacion = modificarFechaCreacion;
	}

	/**
	 * @return the modificarTipoSociedad
	 */
	public boolean getModificarTipoSociedad() {
		return modificarTipoSociedad;
	}

	/**
	 * @param modificarTipoSociedad
	 *            the modificarTipoSociedad to set
	 */
	public void setModificarTipoSociedad(boolean modificarTipoSociedad) {
		this.modificarTipoSociedad = modificarTipoSociedad;
	}

	/**
	 * @return the modificarDatosComplementarios
	 */
	public boolean getModificarDatosComplementarios() {
		return modificarDatosComplementarios;
	}

	/**
	 * @param modificarDatosComplementarios
	 *            the modificarDatosComplementarios to set
	 */
	public void setModificarDatosComplementarios(
			boolean modificarDatosComplementarios) {
		this.modificarDatosComplementarios = modificarDatosComplementarios;
	}

	/**
	 * @return the modificarDomicilioParticular
	 */
	public boolean getModificarDomicilioParticular() {
		return modificarDomicilioParticular;
	}

	/**
	 * @param modificarDomicilioParticular
	 *            the modificarDomicilioParticular to set
	 */
	public void setModificarDomicilioParticular(
			boolean modificarDomicilioParticular) {
		this.modificarDomicilioParticular = modificarDomicilioParticular;
	}

	/**
	 * @return the modificarMediosContactoParticular
	 */
	public boolean getModificarMediosContactoParticular() {
		return modificarMediosContactoParticular;
	}

	/**
	 * @param modificarMediosContactoParticular
	 *            the modificarMediosContactoParticular to set
	 */
	public void setModificarMediosContactoParticular(
			boolean modificarMediosContactoParticular) {
		this.modificarMediosContactoParticular = modificarMediosContactoParticular;
	}

	/**
	 * @return the modificarActaConstitutiva
	 */
	public boolean getModificarActaConstitutiva() {
		return modificarActaConstitutiva;
	}

	/**
	 * @param modificarActaConstitutiva
	 *            the modificarActaConstitutiva to set
	 */
	public void setModificarActaConstitutiva(boolean modificarActaConstitutiva) {
		this.modificarActaConstitutiva = modificarActaConstitutiva;
	}

	/**
	 * @return the modificarRegistroSindicato
	 */
	public boolean getModificarRegistroSindicato() {
		return modificarRegistroSindicato;
	}

	/**
	 * @param modificarRegistroSindicato
	 *            the modificarRegistroSindicato to set
	 */
	public void setModificarRegistroSindicato(boolean modificarRegistroSindicato) {
		this.modificarRegistroSindicato = modificarRegistroSindicato;
	}

	/**
	 * @return the tipoServicio
	 */
	public TipoServicioModificacionEnum getTipoServicio() {
		return tipoServicio;
	}

	/**
	 * @param tipoServicio
	 *            the tipoServicio to set
	 */
	public void setTipoServicio(TipoServicioModificacionEnum tipoServicio) {
		this.tipoServicio = tipoServicio;
	}

	
	/**
	 * @return boolean modificarFechaDefuncion
	 */
	public boolean getModificarFechaDefuncion() {
		return modificarFechaDefuncion;
	}

	/**
	 * 
	 * @param modificarFechaDefuncion Si el parámetro fec_defuncion será modificado
	 */
	public void setModificarFechaDefuncion(boolean modificarFechaDefuncion) {
		this.modificarFechaDefuncion = modificarFechaDefuncion;
	}

	public boolean getModificarMesYAnioNacimiento() {
		return modificarMesYAnioNacimiento;
	}

	public void setModificarMesYAnioNacimiento(boolean modificarMesYAnioNacimiento) {
		this.modificarMesYAnioNacimiento = modificarMesYAnioNacimiento;
	}

	public boolean getTomarMesYAnioDeFechaNacimiento() {
		return tomarMesYAnioDeFechaNacimiento;
	}

	public void setTomarMesYAnioDeFechaNacimiento(
			boolean tomarMesYAnioDeFechaNacimiento) {
		this.tomarMesYAnioDeFechaNacimiento = tomarMesYAnioDeFechaNacimiento;
	}

	public void actualizarAnioMesNacimiento(boolean modificarMesYAnio, boolean obtenerDeFechaNacimiento) {
		this.setModificarMesYAnioNacimiento(modificarMesYAnio);
		this.setTomarMesYAnioDeFechaNacimiento(obtenerDeFechaNacimiento);
	}
}
