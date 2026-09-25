package mx.gob.imss.ctirss.delta.gestion.beneficio.service.interfaces;

import java.util.Date;
import java.util.List;

import javax.ejb.Remote;

import mx.gob.imss.ctirss.delta.exception.beneficio.BeneficioRissException;
import mx.gob.imss.ctirss.delta.exception.beneficio.PersonaNoValidaBeneficioRissException;
import mx.gob.imss.ctirss.delta.exception.beneficio.RifException;
import mx.gob.imss.ctirss.delta.exception.individuo.SolicitudException;
import mx.gob.imss.ctirss.delta.framework.exceptions.ClienteWebserviceImssRissException;
import mx.gob.imss.ctirss.delta.framework.exceptions.SolicitudNoEncontradaException;
import mx.gob.imss.ctirss.delta.framework.exceptions.SolicitudNoValidaException;
import mx.gob.imss.ctirss.delta.framework.exceptions.TramiteNoEncontradoException;
import mx.gob.imss.ctirss.delta.model.Usuario;
import mx.gob.imss.ctirss.delta.model.beneficio.Beneficio;
import mx.gob.imss.ctirss.delta.model.beneficio.RespuestaRifSat;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Fisica;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.FirmaElectronica;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.SujetoObligado;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.OrigenSolicitudEnum;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.Solicitud;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TramiteRiss;
import mx.gob.imss.digital.modelo.satRiss.DatosRiss;

@Remote
public interface BeneficioRissServiceBusinessRemote {
	
	/**
	 * Servicio para obtener el beneficio por medio del NRP
	 *  y descuento acorde al periodo proporcionado.
	 *  
	 * @param nrp
	 * @param fechaInicio
	 * @param fechaFin
	 * @return
	 * @throws BeneficioRissException
	 */
	Beneficio obtenerBeneficioPorNRP(String nrp, Date fechaInicio, Date fechaFin)
		throws BeneficioRissException;
	
	/**
	 * Servicio para obtener el beneficio por medio del NSS
	 *  y descuento acorde al periodo proporcionado.
	 * 
	 * @param nss
	 * @param fechaInicio
	 * @param fechaFin
	 * @return
	 * @throws BeneficioRissException
	 */
	Beneficio obtenerBeneficioPorNSS(String nss, Date fechaInicio, Date fechaFin)
		throws BeneficioRissException;
	
	/**
	 * Servicio para obtener beneficios por medio del NRP.
	 * Si soloBeneficiosActivos=TRUE obtendra beneficios activos, de lo contrario
	 * obtendra beneficios sin contemplar el estado.
	 * 
	 * @param nrp
	 * @param soloBeneficiosActivos
	 * @return
	 * @throws BeneficioRissException
	 */
	List<Beneficio> obtenerBeneficiosPorNRP(String nrp, boolean soloBeneficiosActivos) 
		throws BeneficioRissException;
	
	/**
	 * Servicio para obtener beneficios por medio del id persona.
	 * Si soloBeneficiosActivos=TRUE obtendra beneficios activos, de lo contrario
	 * obtendra beneficios sin contemplar el estado.
	 *  
	 * @param idPersona
	 * @return
	 */
	List<Beneficio> obtenerBeneficiosPorIdPersona(Long idPersona, 
		boolean soloBeneficiosActivos);
			
	/**
	 * Servicio que crea la solicitud del RISS y la asocia a la persona
	 * recibida como par&aacute;metro
	 * 
	 * @param beneficio
	 * @param usuario
	 * @param idOrigenSolicitud
	 * @return
	 * @throws SolicitudNoValidaException
	 */
	Solicitud crearSolicitudRiss(Beneficio beneficio, Usuario usuario,
		Long idOrigenSolicitud) throws SolicitudNoValidaException;	
		
	/**
	 * Servicio que encola la solicitud para que sea procesada por el OSB
	 * 
	 * @param idSolicitud
	 * @param firma
	 * @return 
	 * @throws SolicitudNoEncontradaException
	 * @throws TramiteNoEncontradoException
	 */
	Solicitud encolarSolicitudRiss(Long idSolicitud, FirmaElectronica firma)
		throws SolicitudNoEncontradaException,
		TramiteNoEncontradoException, SolicitudException;

	/**
	 * Servicio para cancelar o rechaza una solicitud del RISS, dependiendo de
	 * la bandera recibida
	 * 
	 * @param idSolicitud
	 * @param causaCancelacion
	 * @param isRechazo
	 * @return
	 * @throws SolicitudNoEncontradaException
	 * @throws TramiteNoEncontradoException
	 */
	Solicitud cancelarRechazarSolicitudRiss(Long idSolicitud,
			String causaCancelacion, boolean isRechazo)
			throws SolicitudNoEncontradaException, TramiteNoEncontradoException;

	/**
	 * Servicio para cancelar o rechaza una solicitud del RISS, dependiendo de
	 * la bandera recibida
	 * 
	 * @param idSolicitud
	 * @param causaCancelacion
	 * @param isRechazo
	 * @return
	 * @throws SolicitudNoEncontradaException
	 * @throws TramiteNoEncontradoException
	 */
	Solicitud cancelarRechazarSolicitudRiss(Solicitud solicitud,
			String causaCancelacion, boolean isRechazo)
		throws SolicitudNoEncontradaException, TramiteNoEncontradoException;
	
	/**
	 * Servicio que procesa una solicitud del RISS
	 * 
	 * @param idSolicitud
	 * @throws BeneficioRissException
	 */
	void procesarSolicitudRiss(Long idSolicitud)
		throws BeneficioRissException;

	/**
	 * Servicio para guardar beneficio para una 
	 * persona f&iacute;sica o por sujeto obligado
	 * 
	 * @param beneficio
	 * @param fechaActual
	 * @param fechaSatRif
	 * @return
	 * @throws BeneficioRissException
	 */
	Beneficio guardarBeneficio(Beneficio beneficio, Date fechaActual, 
		Date fechaSatRif) throws BeneficioRissException;
	
	/**
	 * Servicio que obtiene los beneficios relacionados a sujetos obligados
	 * sin importar su estado.
	 * 
	 * @param listaSujetosObligados
	 * 
	 */
	List<Beneficio> obtenerBeneficiosPatronesPorIds(List<SujetoObligado> listaSujetosObligados);
	
	
	/**
	 * Servicio para obtener la persona f&iacute;sica y/o sujetos obligados
	 * a quiene se aplicara el beneficio RISS (por medio de idPersna y RFC).
	 * Si es patron, puede generar beneficios tanto como patron como persona f&iacute;sica.
	 * 
	 * 
	 * @param fisica
	 * @param origenSolicitud
	 * @param beneficioSoloPatron
	 * @return Beneficio
	 * @throws PersonaNoValidaBeneficioRissException
	 */
	Beneficio obtenerPersonaBeneficio(Fisica fisica, Long origenSolicitud, boolean beneficioSoloPatron) 
		throws PersonaNoValidaBeneficioRissException;
		
	Beneficio obtenerPersonaBeneficioVentanilla(Fisica fisica, List<SujetoObligado> sujetosObligados) 
		throws PersonaNoValidaBeneficioRissException;
	
	/**
	 * Servicio para obtener la persona f&iacute;sica a quiene se aplicara el beneficio RISS
	 * 
	 * @param nss
	 * @param idPersona
	 * @return
	 * @throws PersonaNoValidaBeneficioRissException
	 */
	Fisica obtenerPersonaFisicaParaBeneficio(String nss, Long idPersona) 
		throws PersonaNoValidaBeneficioRissException;
	
	/**
	 * Servicio para obtener los sujetos obligados a quienes se aplicara el beneficio RISS.
	 * 
	 * @param rfc
	 * @return
	 * @throws PersonaNoValidaBeneficioRissException
	 */
	List<SujetoObligado> obtenerSujetosObligadosParaBeneficio(String rfc) 
		throws PersonaNoValidaBeneficioRissException;

	/**
	 * Servicio que realiza las validaciones si es candidato al RISS
	 * 
	 * @param solicitud
	 * @param beneficio
	 * @param origenSolicitud
	 * @return
	 * @throws ClienteWebserviceImssRissException
	 * @throws BeneficioRissException
	 */
	Beneficio validarSolicitudRiss(Solicitud solicitud, Beneficio beneficio)
		throws BeneficioRissException, ClienteWebserviceImssRissException;

    /**
     * Servicio que realiza las validaciones si es candidato al RISS
     *
     * @param solicitud
     * @param beneficio
     * @param origenSolicitud
     * @return
     * @throws ClienteWebserviceImssRissException
     * @throws BeneficioRissException
     */
    Beneficio validarSolicitudRiss(Solicitud solicitud, Beneficio beneficio, boolean esRenovacion)
            throws BeneficioRissException, ClienteWebserviceImssRissException;

	/**
	 * Servicio para validar si la persona fisica cuenta con una solicitud riss 
	 * en proceso, incluyendo la relaci&oacute;n con sus sujetos obligados si aplica. 
	 * 
	 * @param fisica
	 * @param origenSolicitud
	 * @return
	 */
	Solicitud validarSolicitudRissEnProceso(Fisica fisica, OrigenSolicitudEnum origenSolicitud);

	/**
	 * Servicio para dar de alta el nuevo sujeto obliado al beneficio RISS, 
	 * siempre y cuando ya exista beneficio activo para Sujetos Obligados 
	 * que tienen en com&uacute;n la misma persona f&iacute;sica (Patron).
	 * 
	 * 
	 * @param SujetoObligado
	 * @param origenSolicitud
	 * @throws BeneficioRissException
	 */
	void heredarBeneficioAltaPatronal(SujetoObligado so, Long origenSolicitud) throws BeneficioRissException;
	
	void crearSolicitudRechazoRifAltaPatronal(Long idSolicitud, String mensaje);
	
	/**
	 * Servicio para validar si el tramite riss NO ha sido cancelado (este activo). 
	 * 
	 * @param tramiteRiss
	 * @return
	 */
	boolean esTramiteActivo(TramiteRiss tramiteRiss);
	
	String indicarRPsPendientes(Fisica fisica, OrigenSolicitudEnum origenSolicitud);
	
	/**
	 * 
	 * Valida posible incorporacion al beneficio RISS - Seguro Persona IVRO
	 * (Nivel T.Independiente y Patronal)
	 * 
     * @param riss datos para validara beneficio.
     * @return datosRiss
	 */
	DatosRiss validaIncorporacionBeneficioRissSeguroPersonal(DatosRiss riss);

	/**
	 * Valida si la persona es apta para otorgarle el beneficio RISS - Alta Patronal
	 *  
	 * @param fisica
	 * @param origenSolicitud
	 * @param usuario	 
	 * @throws BeneficioRissException, RifException
	 */
	void validaIncorporacionBeneficioRissAltaPatronal(Fisica fisica, 
			Long origenSolicitud, String usuario) throws BeneficioRissException, RifException;
	
	/**
	 * Servicio para validar si el RFC es del Regimen de Incorporacion Fiscal (SAT).
	 * 
	 * @param rfc
	 * @return RespuestaRifSat
	 * @throws RifException
	 */
	RespuestaRifSat getWebServicesValidaRifSat(String rfc) throws RifException;
	
	boolean habilitarRissPortal(Long idOrigenSolicitud);

	RespuestaRifSat validaEstadoBeneficio(Fisica persona);

	RespuestaRifSat validaEstadoBeneficio(Fisica persona,boolean esRenovacion);
}

