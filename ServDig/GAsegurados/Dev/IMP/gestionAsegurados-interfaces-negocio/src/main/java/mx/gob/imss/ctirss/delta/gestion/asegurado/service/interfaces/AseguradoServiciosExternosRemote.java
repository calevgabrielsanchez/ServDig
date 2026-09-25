package mx.gob.imss.ctirss.delta.gestion.asegurado.service.interfaces;

import javax.ejb.Remote;

import mx.gob.imss.ctirss.delta.exception.domicilio.DomicilioNoValidoException;
import mx.gob.imss.ctirss.delta.exception.domicilio.VialidadesNoLocalizadasException;
import mx.gob.imss.ctirss.delta.exception.gestion.asegurado.SolicitudNssCorreoException;
import mx.gob.imss.ctirss.delta.exception.gestion.asegurado.WsAntecedentesAseguradoExcpetion;
import mx.gob.imss.ctirss.delta.exception.gestion.serie.ErrorAlActivarSerieException;
import mx.gob.imss.ctirss.delta.exception.gestion.serie.NivelDeAsignacionSerieIndefinidoException;
import mx.gob.imss.ctirss.delta.exception.gestion.serie.SeriesNoLocalizadasException;
import mx.gob.imss.ctirss.delta.exception.individuo.AfectacionDatosPersonaException;
import mx.gob.imss.ctirss.delta.exception.individuo.AsignacionNssPersonaException;
import mx.gob.imss.ctirss.delta.exception.individuo.CURPNoLocalizadoEnEntidadExternaException;
import mx.gob.imss.ctirss.delta.exception.individuo.ErrorComparacionDatosRENAPOException;
import mx.gob.imss.ctirss.delta.exception.individuo.ErrorValidacionIdentidadCertificacionException;
import mx.gob.imss.ctirss.delta.exception.individuo.RegistroPersonaFisicaException;
import mx.gob.imss.ctirss.delta.exception.individuo.SolicitudException;
import mx.gob.imss.ctirss.delta.exception.individuo.calificacion.PersonaSinCalificacionesException;
import mx.gob.imss.ctirss.delta.exception.individuo.validacion.ErrorValidacionDatosConsultaEnEntidaExternaException;
import mx.gob.imss.ctirss.delta.framework.exceptions.ClienteWebserviceRenapoCurpException;
import mx.gob.imss.ctirss.delta.framework.exceptions.PersonaNoEncontradaException;
import mx.gob.imss.ctirss.delta.framework.exceptions.SolicitudNoEncontradaException;
import mx.gob.imss.ctirss.delta.framework.exceptions.SolicitudNoValidaException;
import mx.gob.imss.ctirss.delta.framework.exceptions.TramiteNoEncontradoException;
import mx.gob.imss.ctirss.delta.global.model.UnidadMedicaFamiliarTO;
import mx.gob.imss.ctirss.delta.model.externo.asegurado.CambioCurpResponse;
import mx.gob.imss.ctirss.delta.model.externo.asegurado.TramiteCambioCurpDTO;
import mx.gob.imss.ctirss.delta.model.externo.asegurado.ValidaRequisitosCambioCurpResponse;
import mx.gob.imss.ctirss.delta.model.externo.asegurado.VerificarCambioCurpResponse;
import mx.gob.imss.digital.modelo.domicilio.Domicilio;

@Remote
public interface AseguradoServiciosExternosRemote {
	/**
	 * Servicio que valida si la consulta o registro del NSS es v�lida, se basa
	 * en el correo y curp recibidos y las consultas permitidas por periodo.
	 * Devuelve el n�mero de operaci�n a realizar en caso de �xito: <br>
	 * 1 = guardar registro nuevo <br>
	 * 2 = reinicio de contador <br>
	 * 3 = registro de consulta
	 * 
	 * @param nssCorreo
	 * @return codigo_operacion	
	 * @throws SolicitudNssCorreoException
	 */
	int validarDerechoAConsultaNSS(String curp, String correo) throws SolicitudNssCorreoException;
	
	
	String asignarNSS (String curp, String correoElectronico, UnidadMedicaFamiliarTO umfTO, Domicilio domicilio) throws AsignacionNssPersonaException, 
	SolicitudException, CURPNoLocalizadoEnEntidadExternaException, 
	ClienteWebserviceRenapoCurpException, ErrorValidacionDatosConsultaEnEntidaExternaException, 
	ErrorComparacionDatosRENAPOException, SolicitudNoValidaException, SolicitudNoEncontradaException, 
	AfectacionDatosPersonaException, PersonaNoEncontradaException, RegistroPersonaFisicaException, 
	NivelDeAsignacionSerieIndefinidoException, SeriesNoLocalizadasException, 
	ErrorAlActivarSerieException, TramiteNoEncontradoException, 
	DomicilioNoValidoException, PersonaSinCalificacionesException;
	
	/**
	 * 
	 * Metodo para validar la identidada del asegurado, verifica que la identidad del NSS (Asegurado)
	 * y el curp (Persona) correspondan.
	 * @param curp
	 * @param nss
	 * @throws ErrorValidacionIdentidadCertificacionException En caso de existir un error en el momento de validar la identidad
	 * del asegurado.
	 */
	public void consultaNssAseguradoIdentidad(String curp , String nss)throws ErrorValidacionIdentidadCertificacionException ;
	
	boolean validaAseguradoConAntecedentePasoCambioAl(String strNSS) throws WsAntecedentesAseguradoExcpetion;
	 
	void convertirDomicilioSolicitud(Domicilio domicilio,mx.gob.imss.ctirss.delta.model.domicilio.Domicilio domicilioSolicitud)
				throws VialidadesNoLocalizadasException,AsignacionNssPersonaException, DomicilioNoValidoException;
	
	ValidaRequisitosCambioCurpResponse validarCambioCurp(String curp, String nss, String correo);
	
	CambioCurpResponse finalizaActualizacionCurp(TramiteCambioCurpDTO tramite);
	
	VerificarCambioCurpResponse validaCambioDatosCurp(String curp, String nss, String correo);
	
	/**
	 * Metodo diseñado para asignar un NSS a una persona que viene de los servicios de IMSS Bienestar que solo
	 * cuenta con la CURP. los valores se setean por default para UMF y domicilio si no se localiza antecedente
	 * en BDTU.
	 * @param curp
	 * @return
	 * @throws AsignacionNssPersonaException
	 */
	String asignarNSSImssBienestar(String curp) throws AsignacionNssPersonaException;
}

