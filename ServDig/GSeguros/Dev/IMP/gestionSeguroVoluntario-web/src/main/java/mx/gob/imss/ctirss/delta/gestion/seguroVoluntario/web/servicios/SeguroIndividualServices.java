/**
 *
 */
package mx.gob.imss.ctirss.delta.gestion.seguroVoluntario.web.servicios;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.persistence.NoResultException;
import mx.gob.imss.ctirss.delta.exception.domicilio.DomicilioNoLocalizadoException;
import mx.gob.imss.ctirss.delta.exception.domicilio.DomicilioNoValidoException;

import mx.gob.imss.ctirss.delta.gestion.seguroVoluntario.exception.IVROExceptionGenerico;
import mx.gob.imss.ctirss.delta.gestion.seguroVoluntario.exception.IVROServiceException;
import mx.gob.imss.ctirss.delta.model.derechohabiente.GrupoFamiliar;
import mx.gob.imss.ctirss.delta.model.enums.MotivoCancelacionBeneficioEnum;
import mx.gob.imss.ctirss.delta.model.gestion.cobranza.PagoCVRO;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Beneficiario;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.OrigenSolicitudEnum;
import mx.gob.imss.ctirss.delta.model.legado.asegurado.RespuestaPagosVentanilla;
import mx.gob.imss.digital.modelo.cobranza.Cotizacion;
import mx.gob.imss.digital.modelo.cobranza.DatosCalculoCuota;
import mx.gob.imss.digital.modelo.domicilio.Domicilio;
import mx.gob.imss.digital.modelo.persona.Persona;
import mx.gob.imss.digital.modelo.satRiss.DatosRiss;
import mx.gob.imss.digital.modelo.seguros.AsignacionNssIvro;
import mx.gob.imss.digital.modelo.seguros.SeguroIvro;
import mx.gob.imss.digital.modelo.seguros.SegurosIvro;
import mx.gob.imss.digital.modelo.sindo.RespuestaValidacionTrabajador;
import mx.gob.imss.digital.modelo.sindo.VigenciaSeguroFamiliar;
import mx.gob.imss.digital.modelo.sindo.VigenciaTrabajdor;
import mx.gob.imss.digital.modelo.solicitud.Solicitud;

/**
 * @author NOVUTECK1
 *
 */
public interface SeguroIndividualServices {

    /**
     * Obtiene los datos de calculo de un a persona
     *
     * @param persona la persona a consultar sus datos de calculo
     * @param origen
     * @param modalidad
     * @return los datos de calculo de la persona
     */
    DatosCalculoCuota obtenDatosCotizacion(Persona persona, OrigenSolicitudEnum origen, String modalidad);
	
    
    /**
     * Genera la cotizacion de un seguro a partir de sus datos de calculo
     *
     * @param datosCalculo los datos para generar la cotizacion
     * @return la cotizacion generada
     */
    Cotizacion generaCotizacion(DatosCalculoCuota datosCalculo);

    /**
     * Obtiene el seguro individual para una persona
     *
     * @param idPersona
     * @return
     * @throws
     * mx.gob.imss.ctirss.delta.gestion.seguroVoluntario.exception.IVROExceptionGenerico
     */
    SegurosIvro obtenSeguroIndividual(Long idPersona) throws IVROExceptionGenerico;

    /**
     * Obtiene el detalle del seguro asociado
     *
     * @param idSeguro el identificador del seguro a buscar
     * @return el seguro encontrado
     */
    SeguroIvro getDetalleSeguro(Long idSeguro);

    /**
     * GUarda o actualiza una solicitud del tramite
     *
     * @param solicitud la solicitud a guardar
     * @return la solicitu actualizada
     */
    Solicitud guardaSolicitud(Solicitud solicitud);

    /**
     * Obiene el domicilio particular de una persona
     *
     * @param persona la persona para buscar su domicilio
     * @return el domicilio de la persona
     */
    Domicilio getDomicilioPersona(Persona persona);

    /**
     * Obiene los medios de contacto de una persona
     *
     * @param persona la persona para buscar sus medios de contacto
     * @return persona con sus medios de contacto
     */
    Persona getMediosContactoPersona(Persona persona);

    /**
     * Valida si la persona es apta para otorgarle el beneficio RISS (Nivel
     * T.Independiente y Patronal)
     *
     * @param riss datos para solicitar beneficio riss
     * @return datosRiss
     */
    DatosRiss validaIncorporacionBeneficioRiss(DatosRiss riss);

    /**
     * Obtiene el CURP de una persona dado su ID
     *
     * @param idPersona
     * @return
     */
    String obtenerCurpPorIdPersona(Long idPersona);

    /**
     * Obtiene el SMVGDF de una persona dado su zona salarial
     *
     * @param zonaSalarial
     * @return
     */
    BigDecimal obtenerSalarioMinimoVigenteDF(String zonaSalarial);

    /**
     * Valida si la persona cuenta con email
     *
     * @param persona
     * @throws
     * mx.gob.imss.ctirss.delta.gestion.seguroVoluntario.exception.IVROServiceException
     */
    void getValidarCorreoPersona(Persona persona) throws IVROServiceException;

    /**
     * Obtiene el UMA por fecha
     *
     * @param fecha
     * @return uma
     */
    BigDecimal obtenerUmaPorFecha(Date fecha);

    /**
     *
     * @param domicilio
     * @param idPersona
     * @throws DomicilioNoValidoException
     * @throws DomicilioNoLocalizadoException
     */
    void guardarYAsociarDomiciliosPersona(Domicilio domicilio, Long idPersona) throws DomicilioNoValidoException, DomicilioNoLocalizadoException;
	
	/**
     * Cancela beneficio Riss 
     * @param seguro
     * @throws mx.gob.imss.ctirss.delta.gestion.seguroVoluntario.exception.IVROServiceException
     */
    void cancelaBeneficioRiss(SeguroIvro seguro) throws IVROServiceException;


    /**
     * Cancela beneficio Riss
     * @param seguro, motivoCancelacionBeneficioEnum
     * @throws mx.gob.imss.ctirss.delta.gestion.seguroVoluntario.exception.IVROServiceException
     */
    void cancelaBeneficioRiss(SeguroIvro seguro, MotivoCancelacionBeneficioEnum motivoCancelacionBeneficioEnum) throws IVROServiceException;

    void cancelaBeneficioRiss(mx.gob.imss.ctirss.delta.model.gestion.individuo.Fisica persona, MotivoCancelacionBeneficioEnum motivoCancelacionBeneficioEnum) throws IVROServiceException;

    /**
     *
     * @param vigenciaSeguroFamiliar
     * @return
     */
    RespuestaValidacionTrabajador validaVigenciaSeguroFamiliarRenovacion(VigenciaSeguroFamiliar vigenciaSeguroFamiliar);

    /**
     *
     * @param persona
     * @param origen
     * @param modalidad
     * @return
     */
    DatosCalculoCuota obtenDatosCotizacionRenovacion(Persona persona, OrigenSolicitudEnum origen, String modalidad);

    /**
     *
     * @param idPersona
     * @return
     * @throws NoResultException
     * @throws Exception
     */
    AsignacionNssIvro obtenerAsignacionNss(Long idPersona) throws NoResultException, Exception;

    /**
     *
     * @param numNss
     * @return
     * @throws NoResultException
     * @throws Exception
     */
    AsignacionNssIvro obtenerAsignacionNss(String numNss) throws NoResultException, Exception;
    
    RespuestaValidacionTrabajador validaVigenciaTrabajadorDomesticoRenovacion(VigenciaTrabajdor vigenciaTrabajdor);
    
    void validarBeneficiariosNss(Map<String, Object> result, Long idAmbiente, SeguroIvro seguroSeleccionado);
    
    void validarBeneficiariosNss(Map<String, Object> result, Long idAmbiente, GrupoFamiliar grupoFam, DatosCalculoCuota dcc);
	
	/**
     *
     * @param seguro
     * @param correos
     * @param adjuntos
     * @param tipo
     */
    void  enviaCorreo(SeguroIvro seguro, List<String> correos, int tipo, Map<String, byte[]>adjuntos);

    /**
     * Regresa el valor en un caracter de la zona que le corresponde en base a la cveEnt y cveMun
     * @param cveEnt
     * @param cveMun
     * @return
     */
    String obtenZonaSalarialOriginal(String cveEnt,String cveMun);

    /**
     * Recupera el seguro anterior inmediato para validar si dicho seguro esta vencido o cancelado
     * @param idPersona
     * @return
     */
    boolean validaSeguroAnteriorVencidoCancelado(Long idPersona);

    boolean insertaCancelacionCuestionario(Beneficiario beneficiarioCancelar, String numSolicitud) throws IVROServiceException;

    Long recuperaIdAsignacionPorNSS(String nss) throws IVROServiceException;

    boolean verificaBeneficiarioConEnfermedad(Long idAsignacion) throws IVROServiceException;

    /**
     * Se valida un rango de tiempo para indicar si tiene pagos al corriente
     * por Ventanilla desde el ultimo pago de un seguro a la fecha
     * @param idPersona
    //     * @param nss
     * @param seguro
     * @return
     */
    RespuestaPagosVentanilla validaPagosPorFechas(Long idPersona, SeguroIvro seguro) throws IVROServiceException;

    /**
     * Se valida un rango de tiempo para indicar si a un periodo en especifico se le realizo su pago por ventanilla
     * @param idPersona
     * @param nss
     * @param periodo
     * @return
     */
    RespuestaPagosVentanilla validaPagoDePeriodo(Long idPersona, String nss, String periodo, String numModalidad) throws IVROServiceException;

    /**
     * Obtiene una lista de pagos de los seguros asociados a la persona
     *
     * @param idSeguro el identificador del seguro a buscar
     * @return el seguro encontrado
     */
    List<PagoCVRO> getListaPagosPersona(Long idSeguro);
}
