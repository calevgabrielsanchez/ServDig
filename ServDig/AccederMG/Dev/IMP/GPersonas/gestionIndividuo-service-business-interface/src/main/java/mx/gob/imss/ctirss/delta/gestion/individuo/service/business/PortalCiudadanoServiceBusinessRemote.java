package mx.gob.imss.ctirss.delta.gestion.individuo.service.business;

import java.util.Date;

import javax.ejb.Remote;

import mx.gob.imss.ctirss.delta.exception.domicilio.DomicilioNoValidoException;
import mx.gob.imss.ctirss.delta.exception.individuo.AfectacionDatosPersonaException;
import mx.gob.imss.ctirss.delta.exception.individuo.CURPNoLocalizadoEnEntidadExternaException;
import mx.gob.imss.ctirss.delta.exception.individuo.DatosInsuficientesICAException;
import mx.gob.imss.ctirss.delta.exception.individuo.DiferenciasRENAPOContraSAT;
import mx.gob.imss.ctirss.delta.exception.individuo.ErrorComparacionDatosRENAPOException;
import mx.gob.imss.ctirss.delta.exception.individuo.PersonaFisicaNoEncontradaException;
import mx.gob.imss.ctirss.delta.exception.individuo.PortalCiudadanoException;
import mx.gob.imss.ctirss.delta.exception.individuo.RFCNoLocalizadoEnEntidadExternaException;
import mx.gob.imss.ctirss.delta.exception.individuo.calificacion.PersonaSinCalificacionesException;
import mx.gob.imss.ctirss.delta.exception.individuo.validacion.ErrorValidacionDatosConsultaEnEntidaExternaException;
import mx.gob.imss.ctirss.delta.framework.exceptions.ClienteWebserviceRenapoCurpException;
import mx.gob.imss.ctirss.delta.framework.exceptions.ClienteWebserviceSatRfcException;
import mx.gob.imss.ctirss.delta.framework.exceptions.PersonaNoEncontradaException;
import mx.gob.imss.ctirss.delta.framework.exceptions.SolicitudNoEncontradaException;
import mx.gob.imss.ctirss.delta.framework.exceptions.SolicitudNoValidaException;
import mx.gob.imss.ctirss.delta.framework.exceptions.TramiteNoEncontradoException;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.CiudadanoCurpCorreo;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Fisica;

@Remote
public interface PortalCiudadanoServiceBusinessRemote{

	CiudadanoCurpCorreo validarInicioCurpCorreo(String curp, String correo, Boolean aceptaTerminos) throws PortalCiudadanoException;
	
	CiudadanoCurpCorreo consultarCurpCorreo(String curp, String correo) throws PortalCiudadanoException;
	
	void actualizarCurpACorreo(String correo, String curp) throws PortalCiudadanoException;
	CiudadanoCurpCorreo actualizarTerminos(Long id, boolean terminos) throws PortalCiudadanoException;

	void actualizarDatosCiudadano(Fisica fisicaIMSS, boolean requiereActualizacionCurp, boolean requiereActualizacionFechaNac)
			throws SolicitudNoEncontradaException,
			TramiteNoEncontradoException, PersonaSinCalificacionesException,
			PersonaFisicaNoEncontradaException, PersonaNoEncontradaException,
			CURPNoLocalizadoEnEntidadExternaException,
			ClienteWebserviceRenapoCurpException,
			ErrorValidacionDatosConsultaEnEntidaExternaException,
			RFCNoLocalizadoEnEntidadExternaException,
			ClienteWebserviceSatRfcException,
			ErrorComparacionDatosRENAPOException,
			DatosInsuficientesICAException, DiferenciasRENAPOContraSAT,
			AfectacionDatosPersonaException, SolicitudNoValidaException;

	Fisica crearCiudadanoNuevo(Fisica fisica) throws DomicilioNoValidoException;
	
	/**
	 * Metodo encargado de consultar en base de datos la relacion correo con CURP, si se localiza, devuelve el registro encontrado.
	 * Si no existe ningun registro con ese correo devuelve el objetivo vacio, si se localiza correo con curp distinta arroja excepcion
	 * @param curp
	 * @param correo
	 * @return
	 * @throws PortalCiudadanoException
	 */
	CiudadanoCurpCorreo validaRegistroCurpCorreoCiudadano(String curp, String correo) throws PortalCiudadanoException;
	
	/**
	 * Método encargado de consultar en base de datos la relación correo con CURP y su fecha de alta, si es localizado una relación que cumpla
	 * con las condiciones siguientes: 
	 * 	- El correo no deberá de estar asignado a otra CURP 
	 * 	- La fecha de alta deberá de ser anterior a la fecha limite
	 * @param curp
	 * @param correo
	 * @param fechaLimite
	 * @return
	 * @throws PortalCiudadanoException
	 */
	boolean validaRegistroCurpCorreoCiudadano(String curp, String correo, Date fechaLimite);
}
