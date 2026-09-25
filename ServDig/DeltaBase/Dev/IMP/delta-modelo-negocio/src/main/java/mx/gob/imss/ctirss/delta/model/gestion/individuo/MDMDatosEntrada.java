package mx.gob.imss.ctirss.delta.model.gestion.individuo;

import java.util.Map;

import mx.gob.imss.ctirss.delta.framework.base.model.AbstractModel;
import mx.gob.imss.ctirss.delta.model.enums.CambioComparacionEnum;

public class MDMDatosEntrada extends AbstractModel {

	private static final long serialVersionUID = 904008540531844812L;

	private Fisica personaFisica;
	private Moral personaMoral;

	/*
	 * Banderas para indicar qué datos se desean modificar dentro del grupo de
	 * DATOS RENAPO
	 */
	private Boolean indCapturaDatosRENAPO;
	private Boolean indCapturaNombre;
	private Boolean indCapturaCURP;
	private Boolean indCapturaSexo;
	private Boolean indCapturaFechaNacimiento;
	private Boolean indCapturaLugarNacimiento;
	private Boolean indCapturaDocumentoProbatorio;

	/*
	 * Banderas para indicar qué datos se desean modificar dentro del grupo de
	 * DATOS SAT
	 */
	private Boolean indCapturaDatosSAT;
	private Boolean indCapturaRFC;
	private Boolean indCapturaDomicilioFiscal;
	private Boolean indCapturaMediosContactoFiscales;
	private Boolean indCapturaRazonSocial;
	private Boolean indCapturaFechaConstitucion;
	private Boolean indCapturaTipoSociedad;

	/*
	 * Banderas para indicar qué datos se desean modificar dentro del grupo de
	 * DATOS COMPLEMENTARIOS
	 */
	private Boolean indCapturaDatosComplementarios;
	private Boolean indCapturaDomicilioParticular;
	private Boolean indCapturaMediosContactoParticular;
	private Boolean indCapturaActaConstitutiva;
	private Boolean indCapturaRegistroSindicato;

	private Boolean indAutorizacion;

	private Map<String, String> traza;
	private Map<String, CambioComparacionEnum> cambios;

	/*
	 * Esta bandera indica si debe el tipo de tramite
	 * ASIGNACION_DE_DOMICILIO_PARTICULAR_DH(101) Debe ser usada
	 */
	private Boolean indAsignacionDomicilio;

	/*
	 * Esta bandera indica si se trata de una actualizacion de domicilio que
	 * tiene su origen en el portal del derechohabiente tipo de tramite =
	 * ACTUALIZACION_DOMICILIO_PARTICULAR(6)
	 */
	private Boolean indActualizacionDomicilioDerechohabiente;

	/*
	 * Esta bandera indica que se trata de un cambio de clinica el tipo de
	 * tramite es CAMBIO_CLINICA(36)
	 */
	private Boolean indCambioClinica;

	// Indicador para el origen de la modificación
	private Long origen;

	// Tipo de solicitud que se generará
	private Long idTipoSolicitud;
	
	// Usuario que realiza la modificacion
	private String usuarioSesion;
	
	/**
	 * @return the personaFisica
	 */
	public Fisica getPersonaFisica() {
		return personaFisica;
	}

	/**
	 * @param personaFisica
	 *            the personaFisica to set
	 */
	public void setPersonaFisica(Fisica personaFisica) {
		this.personaFisica = personaFisica;
	}

	/**
	 * @return the personaMoral
	 */
	public Moral getPersonaMoral() {
		return personaMoral;
	}

	/**
	 * @param personaMoral
	 *            the personaMoral to set
	 */
	public void setPersonaMoral(Moral personaMoral) {
		this.personaMoral = personaMoral;
	}

	/**
	 * @return the indCapturaDatosRENAPO
	 */
	public Boolean getIndCapturaDatosRENAPO() {
		return indCapturaDatosRENAPO;
	}

	/**
	 * @param indCapturaDatosRENAPO
	 *            the indCapturaDatosRENAPO to set
	 */
	public void setIndCapturaDatosRENAPO(Boolean indCapturaDatosRENAPO) {
		this.indCapturaDatosRENAPO = indCapturaDatosRENAPO;
	}

	/**
	 * @return the indCapturaNombre
	 */
	public Boolean getIndCapturaNombre() {
		return indCapturaNombre;
	}

	/**
	 * @param indCapturaNombre
	 *            the indCapturaNombre to set
	 */
	public void setIndCapturaNombre(Boolean indCapturaNombre) {
		this.indCapturaNombre = indCapturaNombre;
	}

	/**
	 * @return the indCapturaCURP
	 */
	public Boolean getIndCapturaCURP() {
		return indCapturaCURP;
	}

	/**
	 * @param indCapturaCURP
	 *            the indCapturaCURP to set
	 */
	public void setIndCapturaCURP(Boolean indCapturaCURP) {
		this.indCapturaCURP = indCapturaCURP;
	}

	/**
	 * @return the indCapturaSexo
	 */
	public Boolean getIndCapturaSexo() {
		return indCapturaSexo;
	}

	/**
	 * @param indCapturaSexo
	 *            the indCapturaSexo to set
	 */
	public void setIndCapturaSexo(Boolean indCapturaSexo) {
		this.indCapturaSexo = indCapturaSexo;
	}

	/**
	 * @return the indCapturaFechaNacimiento
	 */
	public Boolean getIndCapturaFechaNacimiento() {
		return indCapturaFechaNacimiento;
	}

	/**
	 * @param indCapturaFechaNacimiento
	 *            the indCapturaFechaNacimiento to set
	 */
	public void setIndCapturaFechaNacimiento(Boolean indCapturaFechaNacimiento) {
		this.indCapturaFechaNacimiento = indCapturaFechaNacimiento;
	}

	/**
	 * @return the indCapturaLugarNacimiento
	 */
	public Boolean getIndCapturaLugarNacimiento() {
		return indCapturaLugarNacimiento;
	}

	/**
	 * @param indCapturaLugarNacimiento
	 *            the indCapturaLugarNacimiento to set
	 */
	public void setIndCapturaLugarNacimiento(Boolean indCapturaLugarNacimiento) {
		this.indCapturaLugarNacimiento = indCapturaLugarNacimiento;
	}

	/**
	 * @return the indCapturaDocumentoProbatorio
	 */
	public Boolean getIndCapturaDocumentoProbatorio() {
		return indCapturaDocumentoProbatorio;
	}

	/**
	 * @param indCapturaDocumentoProbatorio
	 *            the indCapturaDocumentoProbatorio to set
	 */
	public void setIndCapturaDocumentoProbatorio(
			Boolean indCapturaDocumentoProbatorio) {
		this.indCapturaDocumentoProbatorio = indCapturaDocumentoProbatorio;
	}

	/**
	 * @return the indCapturaDatosSAT
	 */
	public Boolean getIndCapturaDatosSAT() {
		return indCapturaDatosSAT;
	}

	/**
	 * @param indCapturaDatosSAT
	 *            the indCapturaDatosSAT to set
	 */
	public void setIndCapturaDatosSAT(Boolean indCapturaDatosSAT) {
		this.indCapturaDatosSAT = indCapturaDatosSAT;
	}

	/**
	 * @return the indCapturaRFC
	 */
	public Boolean getIndCapturaRFC() {
		return indCapturaRFC;
	}

	/**
	 * @param indCapturaRFC
	 *            the indCapturaRFC to set
	 */
	public void setIndCapturaRFC(Boolean indCapturaRFC) {
		this.indCapturaRFC = indCapturaRFC;
	}

	/**
	 * @return the indCapturaDomicilioFiscal
	 */
	public Boolean getIndCapturaDomicilioFiscal() {
		return indCapturaDomicilioFiscal;
	}

	/**
	 * @param indCapturaDomicilioFiscal
	 *            the indCapturaDomicilioFiscal to set
	 */
	public void setIndCapturaDomicilioFiscal(Boolean indCapturaDomicilioFiscal) {
		this.indCapturaDomicilioFiscal = indCapturaDomicilioFiscal;
	}

	/**
	 * @return the indCapturaMediosContactoFiscales
	 */
	public Boolean getIndCapturaMediosContactoFiscales() {
		return indCapturaMediosContactoFiscales;
	}

	/**
	 * @param indCapturaMediosContactoFiscales
	 *            the indCapturaMediosContactoFiscales to set
	 */
	public void setIndCapturaMediosContactoFiscales(
			Boolean indCapturaMediosContactoFiscales) {
		this.indCapturaMediosContactoFiscales = indCapturaMediosContactoFiscales;
	}

	/**
	 * @return the indCapturaRazonSocial
	 */
	public Boolean getIndCapturaRazonSocial() {
		return indCapturaRazonSocial;
	}

	/**
	 * @param indCapturaRazonSocial
	 *            the indCapturaRazonSocial to set
	 */
	public void setIndCapturaRazonSocial(Boolean indCapturaRazonSocial) {
		this.indCapturaRazonSocial = indCapturaRazonSocial;
	}

	/**
	 * @return the indCapturaFechaConstitucion
	 */
	public Boolean getIndCapturaFechaConstitucion() {
		return indCapturaFechaConstitucion;
	}

	/**
	 * @param indCapturaFechaConstitucion
	 *            the indCapturaFechaConstitucion to set
	 */
	public void setIndCapturaFechaConstitucion(
			Boolean indCapturaFechaConstitucion) {
		this.indCapturaFechaConstitucion = indCapturaFechaConstitucion;
	}

	/**
	 * @return the indCapturaTipoSociedad
	 */
	public Boolean getIndCapturaTipoSociedad() {
		return indCapturaTipoSociedad;
	}

	/**
	 * @param indCapturaTipoSociedad
	 *            the indCapturaTipoSociedad to set
	 */
	public void setIndCapturaTipoSociedad(Boolean indCapturaTipoSociedad) {
		this.indCapturaTipoSociedad = indCapturaTipoSociedad;
	}

	/**
	 * @return the indCapturaDatosComplementarios
	 */
	public Boolean getIndCapturaDatosComplementarios() {
		return indCapturaDatosComplementarios;
	}

	/**
	 * @param indCapturaDatosComplementarios
	 *            the indCapturaDatosComplementarios to set
	 */
	public void setIndCapturaDatosComplementarios(
			Boolean indCapturaDatosComplementarios) {
		this.indCapturaDatosComplementarios = indCapturaDatosComplementarios;
	}

	/**
	 * @return the indCapturaDomicilioParticular
	 */
	public Boolean getIndCapturaDomicilioParticular() {
		return indCapturaDomicilioParticular;
	}

	/**
	 * @param indCapturaDomicilioParticular
	 *            the indCapturaDomicilioParticular to set
	 */
	public void setIndCapturaDomicilioParticular(
			Boolean indCapturaDomicilioParticular) {
		this.indCapturaDomicilioParticular = indCapturaDomicilioParticular;
	}

	/**
	 * @return the indCapturaMediosContactoParticular
	 */
	public Boolean getIndCapturaMediosContactoParticular() {
		return indCapturaMediosContactoParticular;
	}

	/**
	 * @param indCapturaMediosContactoParticular
	 *            the indCapturaMediosContactoParticular to set
	 */
	public void setIndCapturaMediosContactoParticular(
			Boolean indCapturaMediosContactoParticular) {
		this.indCapturaMediosContactoParticular = indCapturaMediosContactoParticular;
	}

	/**
	 * @return the indCapturaActaConstitutiva
	 */
	public Boolean getIndCapturaActaConstitutiva() {
		return indCapturaActaConstitutiva;
	}

	/**
	 * @param indCapturaActaConstitutiva
	 *            the indCapturaActaConstitutiva to set
	 */
	public void setIndCapturaActaConstitutiva(Boolean indCapturaActaConstitutiva) {
		this.indCapturaActaConstitutiva = indCapturaActaConstitutiva;
	}

	/**
	 * @return the indCapturaRegistroSindicato
	 */
	public Boolean getIndCapturaRegistroSindicato() {
		return indCapturaRegistroSindicato;
	}

	/**
	 * @param indCapturaRegistroSindicato
	 *            the indCapturaRegistroSindicato to set
	 */
	public void setIndCapturaRegistroSindicato(
			Boolean indCapturaRegistroSindicato) {
		this.indCapturaRegistroSindicato = indCapturaRegistroSindicato;
	}

	/**
	 * @return the indAutorizacion
	 */
	public Boolean getIndAutorizacion() {
		return indAutorizacion;
	}

	/**
	 * @param indAutorizacion
	 *            the indAutorizacion to set
	 */
	public void setIndAutorizacion(Boolean indAutorizacion) {
		this.indAutorizacion = indAutorizacion;
	}

	/**
	 * @return the traza
	 */
	public Map<String, String> getTraza() {
		return traza;
	}

	/**
	 * @param traza
	 *            the traza to set
	 */
	public void setTraza(Map<String, String> traza) {
		this.traza = traza;
	}

	/**
	 * @return the cambios
	 */
	public Map<String, CambioComparacionEnum> getCambios() {
		return cambios;
	}

	/**
	 * @param cambios
	 *            the cambios to set
	 */
	public void setCambios(Map<String, CambioComparacionEnum> cambios) {
		this.cambios = cambios;
	}

	public Boolean getIndAsignacionDomicilio() {
		return indAsignacionDomicilio;
	}

	public void setIndAsignacionDomicilio(Boolean indAsignacionDomicilio) {
		this.indAsignacionDomicilio = indAsignacionDomicilio;
	}

	public Boolean getIndActualizacionDomicilioDerechohabiente() {
		return indActualizacionDomicilioDerechohabiente;
	}

	public void setIndActualizacionDomicilioDerechohabiente(
			Boolean indActualizacionDomicilioDerechohabiente) {
		this.indActualizacionDomicilioDerechohabiente = indActualizacionDomicilioDerechohabiente;
	}

	public Boolean getIndCambioClinica() {
		return indCambioClinica;
	}

	public void setIndCambioClinica(Boolean indCambioClinica) {
		this.indCambioClinica = indCambioClinica;
	}

	public Long getOrigen() {
		return origen;
	}

	public void setOrigen(Long origen) {
		this.origen = origen;
	}
	
	public Long getIdTipoSolicitud() {
		return idTipoSolicitud;
	}

	public void setIdTipoSolicitud(Long idTipoSolicitud) {
		this.idTipoSolicitud = idTipoSolicitud;
	}

	public String getUsuarioSesion() {
		return usuarioSesion;
	}

	public void setUsuarioSesion(String usuarioSesion) {
		this.usuarioSesion = usuarioSesion;
	}
}
