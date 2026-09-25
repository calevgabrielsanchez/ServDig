package mx.gob.imss.ctirss.delta.gestion.beneficio.service.utility;

import java.awt.image.BufferedImage;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.ejb.Local;

import mx.gob.imss.ctirss.delta.beneficio.model.MovimientoRissType;
import mx.gob.imss.ctirss.delta.exception.beneficio.BeneficioRissException;
import mx.gob.imss.ctirss.delta.exception.beneficio.PersonaNoValidaBeneficioRissException;
import mx.gob.imss.ctirss.delta.framework.exceptions.TransformacionException;
import mx.gob.imss.ctirss.delta.model.Usuario;
import mx.gob.imss.ctirss.delta.model.beneficio.Beneficio;
import mx.gob.imss.ctirss.delta.model.beneficio.CancelacionBeneficio;
import mx.gob.imss.ctirss.delta.model.beneficio.DescuentoBeneficio;
import mx.gob.imss.ctirss.delta.model.beneficio.RespuestaRifSat;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Fisica;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Persona;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.FirmaElectronica;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.SujetoObligado;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.Solicitud;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TramiteRiss;
import mx.gob.imss.ctirss.delta.persistence.DicDescuentoRiss;
import mx.gob.imss.ctirss.delta.persistence.DitBeneficio;
import mx.gob.imss.ctirss.delta.persistence.DitPersona;

@Local
public interface BeneficioServiceUtilityLocal {

	Beneficio convertirEntityToModel(DitBeneficio entity)
		throws TransformacionException;
	
	DitBeneficio prepararBeneficioRiss(Beneficio model, List<DicDescuentoRiss> listaDescuentosRiss, 
		Date fechaActual, Date fechaSatRif, Date FechaInicioRifImss) throws BeneficioRissException;
	
	List<DescuentoBeneficio> obtenerDescuentoBeneficio(List<DicDescuentoRiss> listaDescuentosRiss, 
		Date fechaActual, Date fechaSatRif, Date fechaMaximaRif);
	
	TramiteRiss obtenerTramitePersonaFisica(Solicitud solicitud);
	
	TramiteRiss obtenerTramitePatron(Solicitud solicitud);
	
	Solicitud crearSolicitudRiss(Beneficio beneficio, Usuario usuario,
		Long idOrigenSolicitud);
	
	Solicitud crearSolicitudRechazoRif(Fisica fisica, Usuario usuario,
			Long idOrigenSolicitud, String motivoRechazo);
	
	List<Integer> getBeneficioEstadoActivo();
	
	List<Integer> getTipoBeneficioRif();
	
	Date fechaFinRif10Anios(Date fechaSatRif);
	
	List<Long> getIdsBeneficios(List<Beneficio> listaBeneficios);
	
	List<Long> getIdsPersonasFisicas(List<Fisica> listaFisicas);
	
	List<Long> getIdsSujetosObligados(List<SujetoObligado> listaSujetosObligados);
	
	List<Long> getIdsModalidades10y13();
	
	void lanzarErrorBeneficioRiss(String mensaje) throws BeneficioRissException;
	
	void lanzarErrorPersonaNoValidaBeneficioRissException(String mensaje) 
		throws PersonaNoValidaBeneficioRissException;
	
	void validarBeneficiosExistentes(List<Beneficio> listaBeneficios) throws BeneficioRissException;
	
	Beneficio validarBeneficioPorPeriodo(List<Beneficio> listaBeneficios, 
		Date fechaInicio, Date fechaFin) throws BeneficioRissException;
	
	void validarRegimenRif(boolean esRegimenRif) throws BeneficioRissException;
	
	void validarFiltrosBeneficios(String valor, Date fechaInicio, Date fechaFin) 
		throws BeneficioRissException;
	
	void validarPersonaFisica(Fisica fisica) throws BeneficioRissException;
	
	void validarSujetoObligado(SujetoObligado sujetoObligado) throws BeneficioRissException;
	
	void validarRespuestaSat(boolean respuesta) throws BeneficioRissException;
	
	void validarRespuestaInfonavit(boolean respuesta) throws BeneficioRissException;
	
	void validarVigenciaBeneficioRiss(Date fechaAltaRif, Date FechaInicioRifImss) throws BeneficioRissException;
	
	void validarCalificacionSat(boolean tieneCalificacionSat) throws BeneficioRissException;
	
	CancelacionBeneficio obtenerTipoMotivoCancelacion(String rfc, String nrp, 
			String nss,	int indicadorInstitucion);

	List<MovimientoRissType> prepararLayoutMovimientoAltaRiss(List<DescuentoBeneficio> listaDescuentosBeneficio,
		List<SujetoObligado> listaSujetosObligados, boolean incluirMovimientoPF,Fisica fisicaMovimiento, 
		Date fechaActual, RespuestaRifSat respuestaRifSat, String patronGeneral);
	
	List<MovimientoRissType> movimientosAltaPatrones(List<DescuentoBeneficio> listaDescuentosBeneficio, 
		List<SujetoObligado> listaSujetosObligados, Fisica fisicaMovimiento, Date fechaActual, 
		Date fechaAltaRif, RespuestaRifSat respuestaRifSat);
	
	List<MovimientoRissType> prepararLayoutMovimientoBajaBeneficio(List<DitBeneficio> listaDitBeneficio,
		DitPersona personaMovimiento, String nss, int motivoBaja, Date fechaBaja, String patronGeneral) 
		throws BeneficioRissException;
	
	String generarCadenaOriginal(Solicitud solicitud, Persona persona);
	
	Map<String, Object> generarParametrosReporteRiss(Fisica persona, FirmaElectronica firmaElectronica, 
		RespuestaRifSat respuestaRifSat, boolean tramiteFisica, boolean tramitePatron, Date fechaAltaBeneficio, 
		List<SujetoObligado> listaSujetosObligados, Solicitud solicitud, BufferedImage imagenCodeQR);
	
	boolean esTramiteActivo(TramiteRiss tramiteRiss);
	
	String obtenerTipoApartadoBeneficio(TramiteRiss tramiteRiss);
	
	String obtenerTipoApartadoBeneficio(RespuestaRifSat respuestaRifSat, boolean esPatron);
		
	Beneficio prepararBeneficioPatronSinValidaciones(SujetoObligado so, RespuestaRifSat respuestaRifSat);
	
	/**
	 * Valida si habilita proceso RISS, acorde al parametro riss portal y origen solicitud:
	 * 
	 * 	TODO LOS PORTALES	
	 * 		: parametroRissPortal = 0,
	 *
	 * 	PORTALES INTERNET (Portales diferentes a VENTANILLA)	
	 * 		: parametroRissPortal = 99 y idOrigenSolicitud <> OrigenSolicitudEnum.VENTANILLA,
	 *
	 * 	PORTAL ESPECIFICO (Fiel, Ventanilla, Ciudadano, etc.)	
	 * 		: parametroRissPortal exista en OrigenSolicitudEnum y parametroRissPortal = idOrigenSolicitud.
	 *  
	 * @param 	idOrigenSolicitud
	 * @param 	parametroRissPortal
	 * @return  habilitarRissPortal
	 */
	boolean habilitarRissPortal(Long idOrigenSolicitud, Long parametroRissPortal);

	
}
