/**
 *
 */
package mx.gob.imss.ctirss.delta.gestion.seguroVoluntario.web.controller.wizard;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import mx.gob.imss.ctirss.delta.exception.gestion.asegurado.PersonaConVariosNSSException;
import mx.gob.imss.ctirss.delta.exception.gestion.asegurado.PersonaSinNSSException;
import mx.gob.imss.ctirss.delta.gestion.beneficio.service.interfaces.BeneficioRissServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.model.beneficio.RespuestaRifSat;
import mx.gob.imss.ctirss.delta.model.domicilio.MunicipioIMSS;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Fisica;
import mx.gob.imss.ctirss.delta.model.legado.asegurado.RespuestaPagosVentanilla;
import mx.gob.imss.ctirss.gestionpersonas.servicios.business.PersonaBusinessRemote;
import mx.gob.imss.digital.modelo.medio.contacto.MedioContacto;
import mx.gob.imss.digital.modelo.seguros.AsignacionNssIvro;
import mx.gob.imss.ws.estatusvigencia.individual.cliente.ModalidadFecha;
import mx.gob.imss.ws.estatusvigencia.individual.cliente.RespuestaSituacionAseguramiento;
import mx.gob.imss.ws.estatusvigencia.individual.cliente.WSConsultaSituacionAseguramiento;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import mx.gob.imss.ctirss.delta.exception.domicilio.DomicilioNoLocalizadoException;
import mx.gob.imss.ctirss.delta.exception.domicilio.DomicilioNoValidoException;
import mx.gob.imss.ctirss.delta.exception.domicilio.MunicipioImssNoLocalizadoException;
import mx.gob.imss.ctirss.delta.framework.base.controller.AbstractController;
import mx.gob.imss.ctirss.delta.framework.base.web.sso.UsuarioSSO;
import mx.gob.imss.ctirss.delta.gestion.domicilio.service.interfaces.domicilio.DomicilioServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.domicilio.service.interfaces.domicilio.DomicilioServiceBussinessExternosRemote;
import mx.gob.imss.ctirss.delta.gestion.patronal.service.interfaces.afiliacion.baja.RegistroPatronalServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.seguroVoluntario.exception.IVROExceptionGenerico;
import mx.gob.imss.ctirss.delta.gestion.seguroVoluntario.exception.IVROServiceException;
import mx.gob.imss.ctirss.delta.gestion.seguroVoluntario.service.interfaces.SeguroIvroServiceRemote;
import mx.gob.imss.ctirss.delta.gestion.seguroVoluntario.service.interfaces.VigenciaIvroServiceRemote;
import mx.gob.imss.ctirss.delta.gestion.seguroVoluntario.util.ConvertObjectInfoPago;
import mx.gob.imss.ctirss.delta.gestion.seguroVoluntario.util.SeguroIvroUtil;
import mx.gob.imss.ctirss.delta.gestion.seguroVoluntario.util.UtilConvert;
import mx.gob.imss.ctirss.delta.gestion.seguroVoluntario.vo.DatosLinea;
import mx.gob.imss.ctirss.delta.gestion.seguroVoluntario.vo.InfoPagoObject;
import mx.gob.imss.ctirss.delta.gestion.seguroVoluntario.web.servicios.SeguroIndividualServices;
import mx.gob.imss.ctirss.delta.gestion.solicitud.service.interfaces.SolicitudServiciosExpuestosRemote;
import mx.gob.imss.ctirss.delta.model.enums.*;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.OrigenSolicitudEnum;
import mx.gob.imss.digital.modelo.cobranza.Compra;
import mx.gob.imss.digital.modelo.cobranza.Cotizacion;
import mx.gob.imss.digital.modelo.cobranza.DatosCalculoCuota;
import mx.gob.imss.digital.modelo.cobranza.Pago;
import mx.gob.imss.digital.modelo.cuestionario.PersonaCuestionario;
import mx.gob.imss.digital.modelo.domicilio.Domicilio;
import mx.gob.imss.digital.modelo.patron.RegistroPatronal;
import mx.gob.imss.digital.modelo.persona.Persona;
import mx.gob.imss.digital.modelo.persona.TipoPersona;
import mx.gob.imss.digital.modelo.satRiss.DatosRiss;
import mx.gob.imss.digital.modelo.seguros.SeguroIvro;
import mx.gob.imss.digital.modelo.seguros.SegurosIvro;
import mx.gob.imss.digital.modelo.solicitud.FirmaElectronica;
import mx.gob.imss.digital.modelo.solicitud.Solicitud;
import mx.gob.imss.digital.modelo.tramite.EstadoTramite;
import mx.gob.imss.digital.modelo.tramite.TramiteSeguroIvro;
import mx.gob.imss.distss.gestion.cuestionario.modelo.TramiteCuestionarioDummy;

import mx.gob.imss.ctirss.delta.gestion.motorCalculo.service.interfaces.CompraServiceRemote;
import mx.gob.imss.ctirss.delta.gestion.motorCalculo.service.exception.SUAException;


/**
 * @author NOVUTECK1
 *
 */
@Controller
@RequestMapping(value = "/wizard/individual/")
public class WizardIndividualController extends AbstractController {

    /**
     * MEnsaje al no aplica por contestar erroneamente el cuetionario
     */
    private static final String MENSAJE_CUESTIONARIO = "No es sujeto de aseguramiento de conformidad con el Art. 82 del Reglamento de la Ley del Seguro Social en Materia de Afiliaci\u00F3n, Clasificaci\u00F3n de Empresas, Recaudaci\u00F3n y Fiscalizaci\u00F3n.";

    private static final String MSG_ERROR_NSS_NO_VIGENTE  = "El n\u00FAmero de seguridad social (NSS) no est\u00E1 vigente o no se localiza, favor de acudir a la Subdelegaci\u00F3n.";

    private static final String MSG_ERROR_VIGENTE_RO_RENOVA  = "Usted se encuentra vigente en R\u00E9gimen Obligatorio.  La renovaci\u00F3n de su Incorporaci\u00F3n Voluntaria al R\u00E9gimen Obligatorio del Seguro Social no puede tramitarse";

    private static final String MSG_ERROR_VIGENTE_RO_COMPRA  = "Usted se encuentra vigente en R\u00E9gimen Obligatorio.  La solicitud de su Incorporaci\u00F3n Voluntaria al R\u00E9gimen Obligatorio del Seguro Social no puede tramitarse";

    private static final String MSG_ERROR_RISS_VIGENTE = "Ya cuentas con los beneficios del r\\u00e9gimen de \" +\n\t\t\t\t\"incorporaci\\u00f3n fiscal dentro del Instituto Mexicano del Seguro Social.";

    private static final String SIN_CORREO  = "SIN_CORREO";

    private static final String DCC_VALIDACION_35  = "DCC_VALIDACION_35";

    public static final String FORMAT_DATE_SINSEPARA_YYYYMMDD ="yyyyMMdd";

    // VISTAS
    /**
     * Vista de inicio de tramite
     */
    private static final String INICIO_TRAMITE = "wizardIndividualInit";
    /**
     * Vista con el combo de tipos de pago
     */
    private static final String PIDE_TIPO_PAGO = "wizardIndividualComunSolicitarTipoPago";
    /**
     * Vista con el combo de modaliades
     */
    private static final String PIDE_MODALIDAD = "wizardIndividualModalidad";
    /**
     * Vista para mostrar el resumen de la cotizacion
     */
    private static final String MUESTRA_COTIZACION = "wizardIndividualCotizacion";
    /**
     * Vista para mostrar el resumen de la cotizacion de la renovacion
     */
    private static final String MUESTRA_COTIZACION_RENOVACION = "wizardIndividualCotizacionRenovacion";
    /**
     * Vista para mostrar la ultim pantalla con el resumen y las instrucciones de finalizacion
     */
    private static final String RESUMEN = "wizardIndividualResumen";
    /**
     * Vista con el cuestionario a aplicar
     */
    private static final String CUESTIONARIO = "wizardIndividualCuestionario";
    /**
     * Vista para mostrar el error de Pagos Ventanilla
     */
    private static final String VALIDA_PAGOS_VENTANILLA = "validaPagosVentanilla";
    /**
     * Parametro con la vandea para indicar que es internet
     */
    private static final String ES_INTERNET = "internet";
    /**
     * Parametro para indicar si se trata de un seguro a renovar 44 con cambio a modalidad 35
     */
    private static final String CAMBIAR_A_RENOVACION = "CAMBIAR_A_RENOVACION";
    /**
     * Modalidades IVRO
     */
    private static final String[] MODALIDADES_IVRO = new String[] {"35", "43", "44"};

    //    private static final String CAMBIAR_A_43_44  = "CAMBIAR_A_43_44";
    ///////OBJETOS MODELO Y SESION
    private static final String PERSONA = "persona";
    private static final String COTIZACION = "cotizacion";
    private static final String DATOS_COTIZACION = "datosCotizacion";
    private static final String SOLICITUD = "solicitud";
    private static final String PERSONA_CUESTIONARIO = "personaCuestionario";
    private static final String KEY_SIN_RFC = "SIN_RFC";
    private static final String ES_CUESTIONARIO = "cuestionario";
    private static final String ES_RENOVACION = "esRenovacion";
    private static final String RECARGOS = "recargos";
    private static final String FORMA_PAGO = "formaPago";
    private static final String COMUN_ERROR = "error";
    private static final String KEY_MSGERROR = "msgError";

    @Autowired
    @Qualifier("beneficioRissServiceBusiness")
    BeneficioRissServiceBusinessRemote beneficioRissServiceBusinessRemote;
    /**
     * Servicio para las peticiones del tramite de seguro
     */
    @Autowired
    private SeguroIndividualServices seguroIndividualServices;
    @Autowired
    @Qualifier("domicilioExternosServiceBusiness")
    private DomicilioServiceBussinessExternosRemote domicilioExternosServiceBusiness;

    @Autowired
    @Qualifier("solicitudServiciosExpuestos")
    private SolicitudServiciosExpuestosRemote solicitudServiciosExpuestos;

    @Autowired
    @Qualifier("domicilioServiceBusiness")
    private DomicilioServiceBusinessRemote domicilioServiceBusiness;

    @Autowired
    @Qualifier("registroPatronalServiceBusiness")
    private RegistroPatronalServiceBusinessRemote registroPatronalServiceBusiness;

	@Autowired
	@Qualifier("compraServiceBusiness")
	CompraServiceRemote compraServiceRemote;

    @Autowired
    @Qualifier("vigenciaIvroServiceRemote")
    private VigenciaIvroServiceRemote vigenciaIvroServiceBusiness;

    @Autowired
    @Qualifier("wsConsultaSituacionAseguramiento")
    private WSConsultaSituacionAseguramiento wsConsultaSituacionAseguramiento;

    @Autowired
    @Qualifier("seguroIvroServiceBusiness")
    private SeguroIvroServiceRemote seguroIvroServiceRemote;

    @Autowired
    @Qualifier("personaBusiness")
    private PersonaBusinessRemote personaBusinessRemote;

    /**
     * Verifica si el tramite por nternet se puede iniciar
     * @param request
     * @param idPersona
     * @param rfc
     * @return
     */
    @RequestMapping(value = "/validarAccesoTramite/{idPersona}/{correo}/{rfc}", method = RequestMethod.GET)
    public @ResponseBody Map<String, ? extends Object> validarAccesotramite(HttpSession session,
                                                                            HttpServletRequest request, @PathVariable Long idPersona,@PathVariable String correo,
                                                                            @PathVariable String rfc) {
        
    	
        //Se invalida el acceso por solicitud de usuario 2024
    	Map<String, Object> result = new HashMap<String, Object>();
    	
    	result.put("error", true);
        result.put("msgError", "IVRO2024: Se reestringe el acceso total al tramite IVRO.");
        return result;
    	
    	////****se comenta todo el codigo para tener respaldo y se invalida el acceso
        
        
//        result.put("idPersona", idPersona);
//        result.put("error", false);
//
//        // Seteamos el id de la persona y el tipo
//        Persona persona = new Persona();
//        persona.setIdPersona(idPersona);
//        session.setAttribute("idPersona", idPersona);
//        TipoPersona tipoPersona = new TipoPersona();
//        tipoPersona.setIdTipoPersona(TipoPersona.TIPO_PERSONA_FISICA);
//        persona.setTipoPersona(tipoPersona);
//        persona.setRfc(rfc);
//        OrigenSolicitudEnum origen = OrigenSolicitudEnum.getById(SeguroIvroUtil.getAmbiente(request));
//
//        if(correo.contains("\u0040")){
//            session.setAttribute("correoIVRO", correo);
//            log.info("Tiene correo: "+correo);
//        }else if(SIN_CORREO.equalsIgnoreCase(correo)){
//            Persona personaMC = seguroIndividualServices.getMediosContactoPersona(persona);
//            List<String> correos = SeguroIvroUtil.obtenerArrayCorreos(personaMC);
//            log.info("Tiene varios correos: "+correos);
//            session.setAttribute("listaCorreosIVRO", correos);
//        }
//        // Validamos el correo de la persona
//        try{
//            seguroIndividualServices.getValidarCorreoPersona(persona);
//        } catch (IVROServiceException e) {
//            result.put("error", true);
//            result.put("msgError", e.getMessage());
//            return result;
//        }
//
//        SegurosIvro segurosIvro=null;
//
//        try {
//            segurosIvro = seguroIndividualServices.obtenSeguroIndividual(idPersona);
//            
//        } catch (IVROExceptionGenerico e) {
//            log.error("Se presento error en WS ivro: "+e.getMessage());
//        }
//
//        SeguroIvro[] seguros = segurosIvro.getSeguroIvro() != null ? segurosIvro
//                .getSeguroIvro() : new SeguroIvro[] {};
//
//        boolean comprar = SeguroIvroUtil.puedeComprarSeguro(seguros);
//        boolean renovar = SeguroIvroUtil.puedeRenovarSeguro(seguros);
//        session.setAttribute("esCompra", Boolean.valueOf(comprar));
//        session.setAttribute("esRenovacion", Boolean.valueOf(renovar));
//        log.info(" comprar " + comprar);
//        log.info(" renovar " + renovar);
//
//        //********** 2024 INHIBIR IVRO ******//
//        if (comprar== true) {
//        	
//        	log.error("Se inhibe el acceso a la compra IVRO por solicitud del Instituto 2024.");
//        	
//        	result.put("error", true);
//            result.put("msgError", "IVRO2024: Usted no es candidato para realizar la compra del aseguramiento, favor de realizarlo a través del aplicativo Personas Trabajadores Independientes.");
//            return result;
//        }
//        boolean mostrarDetalle = false;
//        if(seguros!=null && seguros.length>0){
//            mostrarDetalle = validaSeguroActivoYRenovATiempo(seguros[0]);
//            log.info("Se tiene un seguro activo, se mostrará el detalle.");
//        }
//        
//        if(mostrarDetalle== false && renovar==true) {
//	        //Se valida si el usuario tiene RISS
//	        UsuarioSSO sso = this.procesarUsuarioSSO(request);
//	        log.info("-RFC: " + persona.getRfc());
//	        DatosRiss datosRiss = validaBeneficioRissVigente(persona.getIdPersona(), persona.getRfc(), origen.getId(),
//	                (sso != null ? sso.getCurp() : null));
//	        log.info("Datos Riss:"+datosRiss);
//	
//	        if(datosRiss != null && datosRiss.getErrorFormGeneral() !=  null && datosRiss.getErrorFormGeneral().contains("Ya cuentas")){
//	            log.info("Se cuenta con beneficio RISS, puede continuar con la Renovación.");
//	        }else {
//	        	log.error("Se inhibe el acceso a la compra IVRO por solicitud del Instituto 2024.");
//	        	
//	        	result.put("error", true);
//	            result.put("msgError", "IVRO2024: Se inhibe el acceso a la renovación debido a que no cuenta con Beneficio RISS.");
//	            return result;
//	        }
//        }
//        
//		//************* Validar LC antes de detalle *********
//
//        boolean lineasGeneradas = true;
//        log.info("############################Iniciando validacion lineasGeneradas");
//
//        if (seguros.length != 0 ) {
//        	
//        	
//        	log.info("Se cuenta con "+seguros.length+ " seguros");
//        	
//	        for(SeguroIvro seguro: seguros) {
//	        	
//	        	log.info("seguro: "+seguro.getCveIdSeguroIvro());
//	        	
//	        	
//	        	Long idCompra = seguro.getCompra().getIdCompra();
//	        	log.info("############################Se obtiene seguro: " + idCompra);
//	        	try {
//	            	log.info("############################ compraServiceRemote: " + compraServiceRemote);
//	        		Compra compra = compraServiceRemote.findCompraById(idCompra);
//	        		log.info("############################Se obtiene compra: " + compra);
//	        		Pago[] pagos = compra.getPagos();
//
//	        		for(Pago pago: pagos) {
//	        			log.info("############################Se obtiene pago: " + pago.getLineaCaptura());
//	        			if(pago.getLineaCaptura() == null || pago.getLineaCaptura().isEmpty()) {
//	        				lineasGeneradas = false;
//	        				break;
//	        			}
//	        		}
//
//	        		if (!lineasGeneradas) {
//	        			log.info("############################Se encontraron LC en nulo");
//	        			result.put("lineasGeneradas", false);
//	        			result.put("msgError", "El detalle de sus seguros se est\u00e1 procesando. Se recomienda cerrar las ventanas e ingresar nuevamente para verificar sus seguros");
//	        			return result;
//	        		}
//
//	        	} catch (SUAException e) {
//	        		log.error("############################Ocurrio un error al validar las LC: " + e);
//	        		e.printStackTrace();
//	                result.put(COMUN_ERROR, true);
//	                result.put(KEY_MSGERROR, e.getMessage());
//	                return result;
//	        	}
//	        }
//        }else {
//        	log.info("No se cuenta con ningun seguro previo.");
//        	log.info(" comprar " + comprar);
//            log.info(" renovar " + renovar);
//        }
//        
//        
//        
//        //Valida si puede acceder al trmite
//        DatosCalculoCuota dcc = seguroIndividualServices.obtenDatosCotizacion(persona, origen, null);
//        /**Se agrega el dcc a sesion para validar en 'validaSegurComprado si se trata
//         * de un 44(23) en renovacion que se pasara a una compra de 35(17)'*/
//        session.setAttribute(DCC_VALIDACION_35, dcc);
//
//        boolean cambiarACompra35 = false;
//        log.info("\t***************** DCC_VALIDACION_35 *****************");
//        log.info("\t\tMODALIDAD A COMPRAR: "+dcc.getModalidad());
//        if(seguros.length > 0) {
//            log.info("\t\tMODALIDAD ACTUAL   : " + seguros[0].getModalidad().getNumModalidad());
//            log.info("\t\tID_MODALIDAD ACTUAL: " + seguros[0].getModalidad().getIdModalidad());
//        }
//        if(dcc!=null){
//            log.info("\t\tMODALIDAD POR ADQUIRIR :     "+dcc.getModalidad());
//        }
//        /**La validacion regresa TRUE si es una renovacion de la 44 con cambio a 35 por regla de negocio
//         * dcc modalidad debe ser 17
//         * renovar TRUE
//         * comprar FALSE*/
//        cambiarACompra35 = SeguroIvroUtil.cambiarRenovacionToCompra(dcc,renovar,comprar, seguros);
//        log.info("\t\tCAMBIAR A 35       : "+cambiarACompra35);
//        log.info("\t*****************************************************");
//        result.put("cambiarRenovacionACompra",Boolean.valueOf(cambiarACompra35));
//        if(renovar && !cambiarACompra35){
//            return result;
//        }
//
//        if (StringUtils.trimToNull(dcc.getErrorFormGeneral()) != null) {
//            result.put("error", true);
//            result.put("msgError", dcc.getErrorFormGeneral());
//        }
//        return result;
    }

    @RequestMapping(value = "/validarSeguroComprado/{idPersona}", method = RequestMethod.GET)
    public @ResponseBody Map<String, ? extends Object> validarSeguroComprado(HttpSession session,
                                                                             HttpServletRequest request, @PathVariable Long idPersona) {

        Map<String, Object> result = new HashMap<String, Object>();
        result.put("idPersona", idPersona);
        result.put("error", false);

        log.info("validarSeguroComprado" + idPersona);
        
        Long ambiente = SeguroIvroUtil.getAmbiente(request);

        if (OrigenSolicitudEnum.VENTANILLA.getId().equals(ambiente)
                || OrigenSolicitudEnum.PORTAL_CIUDADANO.getId().equals(ambiente)) {

            SegurosIvro segurosIvro;

            //Obetenemos los seguros de la persona
            try {
                segurosIvro = seguroIndividualServices.obtenSeguroIndividual(idPersona);

            } catch (IVROExceptionGenerico e) {
                result.put("error", true);
                result.put("msgError", e.getMessage());
                return result;
            }

            // Validamos si es una compra o una renovacion
            SeguroIvro[] seguros = segurosIvro.getSeguroIvro() != null ? segurosIvro
                    .getSeguroIvro() : new SeguroIvro[] {};

            boolean comprar = SeguroIvroUtil.puedeComprarSeguro(seguros);
            boolean renovar = SeguroIvroUtil.puedeRenovarSeguro(seguros);
            boolean extemporanea=SeguroIvroUtil.esRenovacionExtemporanea(seguros);
            session.setAttribute("esCompra", Boolean.valueOf(comprar));
            session.setAttribute("esRenovacion", Boolean.valueOf(renovar));
            session.setAttribute("renovar", Boolean.valueOf(renovar));

            if(seguros!=null&&seguros.length>=1){
                log.info("tiene seguro Anterior");
                session.setAttribute("tieneSeguroAnterior", true);
            }else{
                log.info("No tiene seguro anterior");
                session.setAttribute("tieneSeguroAnterior", false);
            }

            log.info("comprar:" + comprar);
            log.info("renovar:" + renovar);
                log.info("extemporanea:" + extemporanea);
            result.put("esExtemporanea", extemporanea);
            boolean esPosteriorExtemporanea = false;
            boolean mostrarDetalle = false;

            log.info("\t***************** DCC_VALIDACION_35 *****************");
            DatosCalculoCuota dcc = (DatosCalculoCuota)((session.getAttribute(DCC_VALIDACION_35) != null)?session.getAttribute(DCC_VALIDACION_35):null);
            /**La validacion regresa TRUE si es una renovacion de la 44 con cambio a 35 por regla de negocio
             * dcc modalidad debe ser 17
             * renovar TRUE
             * comprar FALSE*/
            boolean cambiarRenovacionACompra = SeguroIvroUtil.cambiarRenovacionToCompra(dcc, renovar, comprar, seguros);

            log.info("\t\tCAMBIAR RENOVACION A COMPRA    : "  +cambiarRenovacionACompra);
            log.info("\t\tMODALIDAD POR ADQUIRIR :     "+dcc.getModalidad());
            log.info("\t*****************************************************");
            session.setAttribute(CAMBIAR_A_RENOVACION, Boolean.valueOf(cambiarRenovacionACompra));
            result.put("cambiarRenovacionACompra",Boolean.valueOf(cambiarRenovacionACompra));
            if(seguros!= null && seguros.length>0 && !cambiarRenovacionACompra){
                SeguroIvro seguro = seguros[0];

                log.info("seguro[0] "+seguro.getCveIdSeguroIvro());
                log.debug("seguro.getFechaFinAseguramiento():" + seguro.getFechaFin());
                log.debug("seguro.getEnRenovacion():" + seguro.getEnRenovacion() );
                log.debug("seguro.getExtemporanea():" + seguro.getExtemporanea() );

                //Se obtiene la fecha de fin de la renovación
                Date fechaFinRenovacion = vigenciaIvroServiceBusiness.obtenerFechaFinRenovacionIvro(seguros[0]);
                log.debug("fechaFinRenovacion:" + fechaFinRenovacion);
                esPosteriorExtemporanea =  SeguroIvroUtil.esPosteriorExtemporanea(fechaFinRenovacion);
                log.info("Es posteriorExtemporanea: "+esPosteriorExtemporanea);
                session.setAttribute("esPosteriorExtemporanea",esPosteriorExtemporanea);
            }

            if(seguros!=null && seguros.length>0){
                mostrarDetalle = validaSeguroActivoYRenovATiempo(seguros[0]);
            }

            // Validamos si la persona tiene un seguro vigente
            if (!(comprar || renovar) && seguros.length > 0 && !esPosteriorExtemporanea && !cambiarRenovacionACompra) {
                log.info(" no es compra o renovacion");
                result.put("mostrarDetalleSeguro", true);
                result.put("esRenovacion", false);
            } else {

                log.info(" es compra o renovacion y tiene un seguro");

                if ((renovar && !esPosteriorExtemporanea) ||
                        (comprar && seguros !=null && seguros.length>=1 && esPosteriorExtemporanea)) {
                    log.info("Renovar: "+renovar+" extemp: "+extemporanea+" mostrarDet: "+mostrarDetalle);
                    // Validamos si es una compra o una renovacion
                    if ((renovar || extemporanea) && mostrarDetalle) {
                        log.info("mostrarDetalle ok");
                        result.put("esRenovacion", true);
                        result.put("mostrarDetalleSeguro", true);
                    } else {
                        log.info("NO mostrarDetalle");
                        result.put("esRenovacion", renovar);
                        result.put("mostrarDetalleSeguro", false);
                    }
                }else{
                    log.info("NO mostrarDetalle");
                    result.put("esRenovacion", renovar);
                    result.put("mostrarDetalleSeguro", false);
                }
            }

        }
        
        return result;
    }

    private boolean validaSeguroActivoYRenovATiempo(SeguroIvro seguro){

        if(seguro.getEstadoSeguro().getIdEstadoSeguro().equals(EstadoSeguroIvroEnum.ACTIVO.getId())
                ||seguro.getEstadoSeguro().getIdEstadoSeguro().equals(EstadoSeguroIvroEnum.NUEVO.getId())){
            Date hoy = new Date();
            Calendar fechaSeguro =  Calendar.getInstance();
            fechaSeguro.setTime(seguro.getFechaFin());
            Date fechaFinSeguro = fechaSeguro.getTime();
            log.info("Valor de fechaSeguro: "+ fechaFinSeguro);
            log.info("valor fecha hoy: "+hoy);
            if (hoy.before(fechaFinSeguro)||hoy.equals(fechaFinSeguro)){
                log.info("fecha de hoy antes de la fecha fin del seguro");
                return true;
            }else {
                log.info("fecha de hoy mayor a fecha fin");
            }
        }

        return false;
    }

    /**
     * Genera la vista inicial del tramite de compra o renovacion de un seguro individual
     * @param model
     * @param session
     * @param request
     * @param idPersona
     * @param rfc
     * @return
     */
    @RequestMapping(value = "iniciarRenovacion/{idPersona}/{correo}/{rfc}", method = RequestMethod.GET)
    public String initRenovacion(Model model, HttpSession session,
                                 HttpServletRequest request, @PathVariable Long idPersona, @PathVariable String correo, @PathVariable String rfc) {

        //El RFC en portal ciudadano es opcional. Se mande SIN_RFC
        if (rfc !=null && rfc.equals(KEY_SIN_RFC)) {
            rfc=null;
        }
        if(correo.contains("\u0040")){
            model.addAttribute("correoIVRO",correo);
            log.info("Agregando correo a model: "+correo);
        }

        /**Se vigila variable de cambio de modalidad*/
        Boolean cambioAModalidad35 = (session.getAttribute(CAMBIAR_A_RENOVACION)!= null)?((Boolean) session.getAttribute(CAMBIAR_A_RENOVACION)):Boolean.FALSE;
        String view = INICIO_TRAMITE;
        limpiaSesion(session);

        // Iniciamos renovacion

        Persona persona = asignaDatosPersona(idPersona, rfc);
        model.addAttribute(PERSONA, persona);
        if(cambioAModalidad35){
            model.addAttribute(CAMBIAR_A_RENOVACION,cambioAModalidad35);
            model.addAttribute("esRenovacion", false);
            session.setAttribute("esRenovacion", Boolean.FALSE);
        }else{
            model.addAttribute(CAMBIAR_A_RENOVACION,cambioAModalidad35);
            model.addAttribute("esRenovacion", true);
            session.setAttribute("esRenovacion", Boolean.TRUE);
        }

        SegurosIvro segurosIvro=null;
        //Obetenemos los seguros de la persona
        try {
            segurosIvro = seguroIndividualServices.obtenSeguroIndividual(idPersona);
        } catch (IVROExceptionGenerico e) {
            log.error(e.getMessage());
        }
        // Validamos si es una compra o una renovacion
        SeguroIvro[] seguros = segurosIvro.getSeguroIvro() != null ? segurosIvro
                .getSeguroIvro() : new SeguroIvro[] {};
        boolean comprar = SeguroIvroUtil.puedeComprarSeguro(seguros);
        boolean extemporanea=SeguroIvroUtil.esRenovacionExtemporanea(seguros);
        if(cambioAModalidad35){
            extemporanea=false;
            comprar= true;
        }
        model.addAttribute("esExtemporanea", extemporanea);
        session.setAttribute("esCompra", Boolean.valueOf(comprar));
        session.setAttribute("esExtemporanea", Boolean.valueOf(extemporanea));

        OrigenSolicitudEnum origen = OrigenSolicitudEnum.getById(SeguroIvroUtil.getAmbiente(request));
        UsuarioSSO sso = this.procesarUsuarioSSO(request);
        //Se valida si el usuario tiene RISS,
        log.info("-RFC: " + persona.getRfc());
        DatosRiss datosRiss = validaBeneficioRissVigente(persona.getIdPersona(), persona.getRfc(), origen.getId(),
                (sso != null ? sso.getCurp() : null));
        log.info("Datos Riss:"+datosRiss);

        
        return view;
    }

    /**
     * Genera la vista inicial del tramite de compra o renovacion de un seguro individual
     *
     * @param model
     * @param session
     * @param request
     * @param idPersona
     * @param rfc
     * @return
     */
    @RequestMapping(value = "/{idPersona}/{correo}/{rfc}", method = RequestMethod.GET)
    public String initWizard(Model model, HttpSession session,
                             HttpServletRequest request, @PathVariable Long idPersona, @PathVariable String correo, @PathVariable String rfc) {

        //El RFC en portal ciudadano es opcional. Se mande SIN_RFC
        if (rfc !=null && rfc.equals(KEY_SIN_RFC)) {
            rfc=null;
        }

        SeguroIvro seguroCancelar = null;

        Long ambiente = SeguroIvroUtil.getAmbiente(request);
        String view = INICIO_TRAMITE;
        limpiaSesion(session);
        SegurosIvro segurosIvro=null;
        boolean esRenovacion = false;
        boolean mostrarDetalle = false;
        boolean esPosteriorExtemporanea = false;

        Object temp = session.getAttribute("esPosteriorExtemporanea");
        if (temp!=null){
            esPosteriorExtemporanea =  (Boolean) temp;
        }

        //Se valida si cuenta con RISS o se genera si aplica
        OrigenSolicitudEnum origen = OrigenSolicitudEnum.getById(SeguroIvroUtil.getAmbiente(request));
        UsuarioSSO sso = this.procesarUsuarioSSO(request);
        log.info("-RFC: " + rfc);
        DatosRiss datosRiss = validaBeneficioRissVigente(idPersona, rfc, origen.getId(),
                (sso != null ? sso.getCurp() : null));
        log.info("Datos Riss:" + datosRiss);

        // Si ventanilla y no puede comprar o renovar mostramos el detalle
        if(OrigenSolicitudEnum.VENTANILLA.getId().equals(ambiente)
                || OrigenSolicitudEnum.PORTAL_CIUDADANO.getId().equals(ambiente)
                || OrigenSolicitudEnum.INTERNET.getId().equals(ambiente)) {


            try {
                segurosIvro = seguroIndividualServices.obtenSeguroIndividual(idPersona);
            } catch (IVROExceptionGenerico e) {
                log.error("Se presento error en WS ivro: "+e.getMessage());
            }

            SeguroIvro[] seguros = segurosIvro!=null && segurosIvro.getSeguroIvro() != null
                    ? segurosIvro.getSeguroIvro() : new SeguroIvro[]{};

            if(seguros!=null && seguros.length>0){
                seguroCancelar = seguros[0];
                mostrarDetalle = validaSeguroActivoYRenovATiempo(seguros[0]);
            }

            boolean comprar = SeguroIvroUtil.puedeComprarSeguro(seguros);
            boolean renovar = SeguroIvroUtil.puedeRenovarSeguro(seguros);
            boolean extemporanea=SeguroIvroUtil.esRenovacionExtemporanea(seguros);
            /**Se vigila variable de cambio de modalidad*/
            Boolean cambioAModalidad35 = (session.getAttribute(CAMBIAR_A_RENOVACION)!= null)?((Boolean) session.getAttribute(CAMBIAR_A_RENOVACION)):Boolean.FALSE;

            if (renovar && esPosteriorExtemporanea) {
                log.info("Si es renovacion y ya paso el mes despues de la fecha oportuna se manda a compra inicial");
                if(mostrarDetalle){
                    mostrarDetalle=false;
                }
            }else{
                log.info(" Se debe mostrar el detalle un mes despues de la fecha oportuna");
                if(renovar&& esPosteriorExtemporanea==false){
                    mostrarDetalle=true;
                }
            }

            log.info("Renovar: "+renovar+" extemp: "+extemporanea+" mostrarDet: "+mostrarDetalle);

            if((!comprar || extemporanea ) &&  seguros.length > 0 && mostrarDetalle) {
                SeguroIvro seguro = seguroIndividualServices.getDetalleSeguro(seguros[0].getCveIdSeguroIvro());

                /*
                 * Se ordena de forma ascendente la lista de pagos por fecha limite de
                 * pago
                 */
                SeguroIvroUtil.ordenarPagosPorFechaLimitePago(seguro);

                if(seguro.getCompra().getFormaPago() == FormaPagoEnum.BIMESTRAL.getId()) {
                    seguro = seguroIvroServiceRemote.confirmaPagosDeSeguro(seguro);
                }

                seguro.setFechaFin(SeguroIvroUtil.getFechaFinalSeguro(seguro));
                model.addAttribute("seguro", seguro);
                model.addAttribute("domestico", seguro.getModalidad().getIdModalidad() == ModalidadEnum.TREINTAYCUATRO.getId());
                String claseEstado = SeguroIvroUtil.getClaseEstado(seguro);
                model.addAttribute("claseEstado", claseEstado);
                model.addAttribute("ventanilla",true);
                model.addAttribute(CAMBIAR_A_RENOVACION,cambioAModalidad35);
                model.addAttribute("esRenovacion", renovar);
                model.addAttribute("esExtemporanea", extemporanea);
                session.setAttribute("esExtemporanea", Boolean.valueOf(extemporanea));

                /*
        		 * Se ordena de forma ascendente la lista de pagos
        		 * por fecha limite de pago
        		 */
                SeguroIvroUtil.ordenarPagosPorFechaLimitePago(seguro);

                //Sólo si su pago es bimestral aplicamos las reglas para confirmar su pago
                if(seguro.getCompra().getFormaPago() == FormaPagoEnum.BIMESTRAL.getId()){

                    seguro = seguroIvroServiceRemote.confirmaPagosDeSeguro(seguro);
                }

                //Si entra por fiel
                if(OrigenSolicitudEnum.INTERNET.getId().equals(ambiente)){

                    Persona persona = asignaDatosPersona(idPersona, rfc);
                    model.addAttribute(PERSONA, persona);

                    return view;

                }else{

                	// Inicia generacion JSON multipagos

             		InfoPagoObject infoPagoObject = new InfoPagoObject();

            		String claveAplicativo = null;
            		long tipoModalidad = seguro.getModalidad().getIdModalidad();

            		if (tipoModalidad == ModalidadEnum.TREINTAYCINCO.getId() ||
            					tipoModalidad == ModalidadEnum.CUARENTAYTRES.getId() ||
                                tipoModalidad == ModalidadEnum.CUARENTAYCUATRO.getId()){
                                	claveAplicativo = "IVRO";
                    } else if (tipoModalidad == ModalidadEnum.TREINTAYTRES.getId()) {
                                	claveAplicativo = "SSF";
                    } else if (tipoModalidad == ModalidadEnum.CUARENTA.getId()) {
                                	claveAplicativo = "CVRO";
                    }

             		infoPagoObject.setClaveAplicativo(claveAplicativo);
             		List<DatosLinea> datosLineas = new ArrayList<DatosLinea>();
             		Date today = new Date();



             		for (Pago pago : seguro.getCompra().getPagos()) {

             				log.info("tipoModalidad:" + tipoModalidad);
             				log.info("limite de pago:" + pago.getFechaLimitePago());
             				log.info("es imprimible:" + pago.getImprimible());
             				log.info("Estado pago:" + pago.getEstadoPago());
             				log.info("LC: " + pago.getLineaCaptura());


             			if (
             					(tipoModalidad == ModalidadEnum.CUARENTA.getId() || tipoModalidad == ModalidadEnum.TREINTAYTRES.getId()) &&
             					today.getTime() < (pago.getFechaLimitePago().getTime() + 86400000L)
             					&& (pago.getImprimible() || (pago.getEstadoPago().getIdEstadoPago() != EstadoPagoEnum.PAGADO.getId()
             					&& pago.getEstadoPago().getIdEstadoPago() != EstadoPagoEnum.VENCIDO.getId()))) {

             				if (pago.getLineaCaptura() != null && !pago.getLineaCaptura().isEmpty()) {

             					datosLineas.add(generaDatosLinea(pago));
             				}
             			} else if (
             					(tipoModalidad == ModalidadEnum.TREINTAYCINCO.getId() ||
             					tipoModalidad == ModalidadEnum.CUARENTAYTRES.getId() ||
                                 tipoModalidad == ModalidadEnum.CUARENTAYCUATRO.getId()) &&
             					today.getTime() < (pago.getFechaLimitePago().getTime() + 86400000L)
             					&& (pago.getImprimible() && (pago.getEstadoPago().getIdEstadoPago() != EstadoPagoEnum.PAGADO.getId()
             					&& pago.getEstadoPago().getIdEstadoPago() != EstadoPagoEnum.VENCIDO.getId()))) {

             				if (pago.getLineaCaptura() != null && !pago.getLineaCaptura().isEmpty()) {

             					datosLineas.add(generaDatosLinea(pago));
             				}
             			}
             			else {
             				log.info("La modalidad no aplica para hacer multipago");
             			}
             		}

             		 if(!datosLineas.isEmpty()) {
             	            infoPagoObject.setDatosLinea(datosLineas);
             	            ConvertObjectInfoPago convert = new ConvertObjectInfoPago();
             	            String jsonInfoPago = convert.getJsonInfoPago(infoPagoObject);
             	            model.addAttribute("jsonInfoPago", jsonInfoPago);

             	            log.info(jsonInfoPago);
             	        }

                	return SeguroIvroUtil.VIEW_DETALLE_SEGURO;
                }
            }
            if(renovar){
                esRenovacion=true;
            }

        }



        // En cualquier otro caso mandamos el inicio del tramite
        Persona persona = asignaDatosPersona(idPersona, rfc);
        model.addAttribute(PERSONA, persona);
        model.addAttribute("esRenovacion", false);
        Boolean cambioAModalidad35 = (session.getAttribute(CAMBIAR_A_RENOVACION)!= null)?((Boolean) session.getAttribute(CAMBIAR_A_RENOVACION)):Boolean.FALSE;
        model.addAttribute(CAMBIAR_A_RENOVACION,cambioAModalidad35);

        return view;

    }

    private DatosLinea generaDatosLinea(Pago pago) {
        DatosLinea datosLinea = new DatosLinea();
        datosLinea.setLineaCaptura(pago.getLineaCaptura());
        datosLinea.setNumFolioSua(String.valueOf(pago.getSuaPago().getPatron().getFolioSUA()));
        datosLinea.setMontoPagar(pago.getMonto().doubleValue());
        datosLinea.setRegistroPatronal(pago.getSuaPago().getPatron().getRegistroPatronalIMSS());
        datosLinea.setNombreRegistroPatronal(pago.getSuaPago().getPatron().getNombreORazonSocial());

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(pago.getFechaInicioPeriodo());

        int mes = 1 + calendar.get(Calendar.MONTH);
        int anio = calendar.get(Calendar.YEAR);

        Formatter fmt = new Formatter();

        datosLinea.setNumPeriodoAseguramiento(anio + fmt.format("%02d", mes).toString());
        datosLinea.setIdCotizacion(pago.getIdPago());

        return datosLinea;
    }

    private MotivoCancelacionBeneficioEnum validaErrorEnum(RespuestaRifSat respuestaRifSat){

        String motivoCancelacion = respuestaRifSat.getMotivoDeRechazo();
        if(motivoCancelacion==null){
            motivoCancelacion = respuestaRifSat.getDescripcion();
        }

        MotivoCancelacionBeneficioEnum motivoRespuesta = null;

        if(motivoCancelacion.contains("SAT")) {
            motivoRespuesta = MotivoCancelacionBeneficioEnum.POR_SAT;
        }else if(motivoCancelacion.contains("INFONAVIT")) {
            motivoRespuesta = MotivoCancelacionBeneficioEnum.POR_INFONAVIT;
        }else if(motivoCancelacion.contains("IMSS")) {
            motivoRespuesta = MotivoCancelacionBeneficioEnum.POR_ADEUDO_IMSS;
        }else {
            log.info("Baja sin motivo coincidente");
        }

        return motivoRespuesta;
    }

    /**
     * Inicia el tramite de seguro individual
     *
     * @param model
     * @param session
     * @param request
     * @param persona
     * @return
     */
    @RequestMapping(value = "/iniciarTramite")
    public String iniciarTramite( Model model, HttpSession session, HttpServletRequest request,
                                  @ModelAttribute(value=PERSONA) Persona persona) {

        session.setAttribute(PERSONA, persona);
        String view;
        OrigenSolicitudEnum origen = OrigenSolicitudEnum.getById(SeguroIvroUtil.getAmbiente(request));

        log.info("Se recibe persona del model| idPersona: " + persona.getIdPersona() + " Rfc: " + persona.getRfc());

        boolean personaTieneRFC = false;

        SeguroIvro[] seguros = obtenerSeguros(persona);


        boolean comprar = SeguroIvroUtil.puedeComprarSeguro(seguros);
        boolean renovar = SeguroIvroUtil.puedeRenovarSeguro(seguros);

        log.info("comprar:" + comprar);
        log.info("renovar:" + renovar);

        DatosCalculoCuota dcc = seguroIndividualServices.obtenDatosCotizacion(persona, origen, null);

        log.error("dcc: "+dcc);
        boolean aplicaCuestionario = dcc.getAplicaCuestionario() !=null? dcc.getAplicaCuestionario(): false;
        session.setAttribute(ES_CUESTIONARIO, Boolean.valueOf(aplicaCuestionario));

        log.error("aplicaCuestionario: "+aplicaCuestionario);

        Boolean esExtemporanea = false;
        /**Se vigila variable de cambio de modalidad*/
        Boolean cambioAModalidad35 = (session.getAttribute(CAMBIAR_A_RENOVACION)!= null)?((Boolean) session.getAttribute(CAMBIAR_A_RENOVACION)):Boolean.FALSE;

        Boolean cambiarVista = false;
        UsuarioSSO sso = this.procesarUsuarioSSO(request);
        DatosRiss datosRiss = validaBeneficioRissVigente(persona.getIdPersona(), persona.getRfc(), origen.getId(),
                (sso != null ? sso.getCurp() : null));
        log.info("Datos Riss:"+datosRiss);

        Boolean tieneRiss = (datosRiss.getErrorFormGeneral().contains("Ya cuentas"));

        if(origen != null ){

            if (tieneRiss) {

                Fisica personaFis = new Fisica();
                personaFis.setIdPersona(persona.getIdPersona());
                personaFis.setRfc(persona.getRfc());
                Boolean cancelaRissEsRenovacion;

                if(seguros!=null&& seguros.length>0){
                    log.info("Se valida si se debe cancelar RISS como renovacion");
                    cancelaRissEsRenovacion = true;
                }else{
                    log.info("Se valida si se debe cancelar RISS como compra");
                    cancelaRissEsRenovacion = false;
                }

                RespuestaRifSat respuestaRifSat = beneficioRissServiceBusinessRemote.validaEstadoBeneficio(personaFis,cancelaRissEsRenovacion);

                if (respuestaRifSat.getClaveError() == 1) {
                    //validar si el error es:  ha cotizado en los
                    log.info("### Motivo de Rechazo RISS: " + respuestaRifSat.getMotivoDeRechazo());
                    log.info("texto contiene ha cotizado: " +
                            respuestaRifSat.getMotivoDeRechazo().contains("ha cotizado en los") +
                            "\n Valida ultima modalidad: " + validaUltimaModalidadVigente(persona.getIdPersona() ));
                    if(respuestaRifSat.getMotivoDeRechazo().contains("ha cotizado en los") &&
                            validaUltimaModalidadVigente(persona.getIdPersona())){
                        log.info("Misma modalidad, no se debe cancelar RISS");
                        respuestaRifSat.setMotivoDeRechazo(MSG_ERROR_RISS_VIGENTE);
                    } else {

                        this.log.warn(" -- Perdio el beneficio, se cancela en BDTU::::::");

                        try {
                            String curp = personaBusinessRemote.obtenerCurpPersona(persona.getIdPersona());
                            String nss = personaBusinessRemote.obtenerNssPersona(persona.getIdPersona());

                            personaFis.setNss(nss);
                            personaFis.setCurp(curp);
                            if (personaFis != null && personaFis.getCurp() != null) {
                                MotivoCancelacionBeneficioEnum motivo = validaErrorEnum(respuestaRifSat);
                                seguroIndividualServices.cancelaBeneficioRiss(personaFis, motivo);
                                datosRiss.setErrorFormGeneral(respuestaRifSat.getMotivoDeRechazo());
                                tieneRiss = false;

                                log.info("Se cancelo el beneficio por el motivo: " + respuestaRifSat.getMotivoDeRechazo());
                            } else {
                                log.info("No se encontro la persona a la cual dar de baja el beneficio");
                            }

                        } catch (IVROServiceException e) {
                            log.error("Se presento error al cancelar el Beneficio: " + e.getMessage());
                        } catch (Exception e) {
                            log.error("Se presento error al cancelar el Beneficio: " + e.getMessage());
                        }
                    }
                }else if(respuestaRifSat.getClaveError() == -1){
                    datosRiss.setErrorFormGeneral(respuestaRifSat.getErrorFormGeneral());
                }
            }

            //session.setAttribute("datosRiss", datosRiss);
        }

        if(seguros != null && seguros.length>0 && !cambioAModalidad35){
            log.info("seguros "+seguros[0]);
            esExtemporanea=SeguroIvroUtil.esRenovacionExtemporanea(seguros);
            Date fechaFinRenovacion = vigenciaIvroServiceBusiness.obtenerFechaFinRenovacionIvro(seguros[0]);
            log.debug("fechaFinRenovacion:" + fechaFinRenovacion);
            boolean esPosteriorExtemporanea =  SeguroIvroUtil.esPosteriorExtemporanea(fechaFinRenovacion);

            Calendar hoy = Calendar.getInstance();
            hoy.setTime(new Date());
            Calendar calendarRenovacion = Calendar.getInstance();
            calendarRenovacion.setTime(fechaFinRenovacion);
            boolean esPosteriorFechaFinRenovacion = hoy.after(calendarRenovacion);
            log.debug("esPosteriorFechaFinRenovacion:" + esPosteriorFechaFinRenovacion);

            //Evalua si se cancela el beneficio Riss

            log.info("esExtemporanea:" + esExtemporanea);

            if (origen.equals(OrigenSolicitudEnum.INTERNET)
                    || origen.equals(OrigenSolicitudEnum.PORTAL_CIUDADANO)) {

                boolean debeValidarPagos = ( (renovar && esExtemporanea) || ( esPosteriorExtemporanea) ||
                        seguroIndividualServices.validaSeguroAnteriorVencidoCancelado(persona.getIdPersona()) ) &&
                        tieneRiss;
                log.info("Tiene RISS: " + tieneRiss);
                log.info("Debe validar Pagos:" + debeValidarPagos);

                try {
                    if (debeValidarPagos) {

                        RespuestaPagosVentanilla pagosVentanilla =
                                    seguroIndividualServices.validaPagosPorFechas(persona.getIdPersona(), seguros[0]);

                        log.info("### Responde del nuevo WS:  \n Codigo error: " + pagosVentanilla.getCodigoError()
                                + "\n Mensaje error: " +pagosVentanilla.getMensajeError()
                                + "\n Fecha Fin Aseguramiento: " + pagosVentanilla.getFechaFinAseguramiento());

                        log.info("El indicador de pagos completos es: " + pagosVentanilla.getIndPagosCompletos());

                        if(pagosVentanilla.getCodigoError() != null || pagosVentanilla.getCodigoError() != "-1"){

                            if (pagosVentanilla.getCodigoError().equals("02")) {
                                model.addAttribute("codigoError", pagosVentanilla.getMensajeError().contains("SERVICIO NO DISPONIBLE")?-1:2);
                                cambiarVista = true;
                            } else if (pagosVentanilla.getIndPagosCompletos() == null ||
                                    pagosVentanilla.getIndPagosCompletos().equals("0")) {
                                model.addAttribute("codigoError", 1);
                                model.addAttribute("indicadorPagos", 0);
                                model.addAttribute("idPersona", persona.getIdPersona());
                                model.addAttribute("rfc", persona.getRfc());
                                model.addAttribute("tipoPersona", persona.getTipoPersona());
                                cambiarVista = true;
                                log.info("Model enviado: " + model.toString());
                                log.info("### Se muestra mensaje de que no se encontraron pagos en ventanilla");

                            } else if (pagosVentanilla.getIndPagosCompletos().equals("1")) {
                                int codigoError = pagosVentanilla.getCodigoError().equals("4")?4:0;
                                cambiarVista = codigoError==4;

                                if (!validaPagosRenovacion(pagosVentanilla,false)) {
                                    codigoError = 3;
                                    log.info("### Se muestra mensaje de que no se encuentra en periodo de renovación");
                                    cambiarVista = true;
                                }else{
                                    if(!validaPagosRenovacion(pagosVentanilla,true)){
                                        log.info(" La fecha de renovacion es mayor a la fecha limite de pago, se procede a cancelar el RISS");
                                        seguroIndividualServices.cancelaBeneficioRiss(seguros[0], MotivoCancelacionBeneficioEnum.NO_CUMPLEN_SUPUESTOS_IMSS);
                                        datosRiss.setErrorFormGeneral("Debido a cancelaci");
                                    }
                                }
                                model.addAttribute("codigoError", codigoError);
                                log.info("Model enviado: " + model.toString());

                            } else {
                                // No debería entrar a este caso. Test only
                                log.info("### No se debe cancelar el beneficio porque tiene pagos completos");
                                //model.addAttribute("codigoError", "0");
                            }
                        } else {
                            model.addAttribute("codigoError", -1);
                            cambiarVista = true;
                        }
                    } else if (renovar && esPosteriorFechaFinRenovacion && tieneRiss) {  // Si es renovacion oportuna pero la fecha es posterior a la fecha fin renovacion se debe cancelar RISS
                        log.info(" La fecha de renovacion es mayor a la fecha limite de pago, se procede a cancelar el RISS");
                        seguroIndividualServices.cancelaBeneficioRiss(seguros[0], MotivoCancelacionBeneficioEnum.NO_CUMPLEN_SUPUESTOS_IMSS);
                    } else{
                        log.info(" NO se debe cancelar el beneficio");
                        model.addAttribute("codigoError", 0);
                    }
                }catch (IVROServiceException e) {
                    model.addAttribute("codigoError", -1);
                    cambiarVista = true;
                    log.info(e.getMessage());
                }
            }
        }
        session.setAttribute("datosRiss", datosRiss);
        dcc = seguroIndividualServices.obtenDatosCotizacion(persona, origen, null);

        if(comprar){
            comprar = SeguroIvroUtil.puedeComprarSeguro(seguros);
        }else if (renovar) {
            renovar = SeguroIvroUtil.enRenovacion(seguros);
        }
        if(cambioAModalidad35){
            comprar = cambioAModalidad35;
            log.info("Compra por cambio de modalidad   : " + comprar);
        }

        if(persona != null && StringUtils.isNotEmpty(persona.getRfc()) && StringUtils.isNotBlank(persona.getRfc())){

            personaTieneRFC = true;
            log.info("La persona "+persona.getIdPersona() +" tiene el RFC: "+persona.getRfc());

            //Solo en el caso de la compra se debe Evaluar su beneficio y si las condiciones
            //se cumplen, entonces se le creará el beneficio

        }else{
            log.info("La persona "+persona.getIdPersona() +"  NO tiene RFC");
            //  DatosRiss datosRiss = new DatosRiss();
            datosRiss.setIdOrigenSolicitud(-1L);
            datosRiss.setErrorFormGeneral("Debe ingresar su RFC para ser evaluado como sujeto al Régimen de Incorporación a la Seguridad Social.");
            session.setAttribute("datosRiss", datosRiss);
        }

        Long mod =  dcc.getModalidad();
        log.info(" dcc.getModalidad():" + mod);

        if(!mod.equals(0L)) {

            log.info("antes de obtener los datos de cotizacion nuevamente");
            log.info("Origen   : "+origen);
            //Obtenemos nuevamente los datos de la cotización pero ahora teniendo en cuenta la modalidad que trae por defecto
            if(renovar){
                log.info("obteniendo datos de la cotizacion en el caso de la renovacion");
                dcc = seguroIndividualServices.obtenDatosCotizacionRenovacion(persona, origen, ModalidadEnum.fromId(dcc.getModalidad()).getNumModalidad());
            }else{
                log.info("obteniendo datos de la cotizacion en el caso de compra");
                dcc = seguroIndividualServices.obtenDatosCotizacion(persona, origen, ModalidadEnum.fromId(dcc.getModalidad()).getNumModalidad());
            }

            if (StringUtils.trimToNull(dcc.getErrorFormGeneral()) != null) {
                //Si regreso algun error se usa la ventana de modalidad para mostrarlo al usuario
                model.addAttribute("datos", dcc);
                session.setAttribute(DATOS_COTIZACION, dcc);
                view = PIDE_MODALIDAD;
            }else{
                datosRiss = validaBeneficioRissVigente(persona.getIdPersona(), persona.getRfc(), origen.getId(),
                        (sso != null ? sso.getCurp() : null));
                session.setAttribute("datosRiss", datosRiss);

                //Si datosRiss no tiene valor es que es una renovacion o si tiene valor pero no tiene beneficio debe enviarlo a pedir el tipo de pago
                if(datosRiss == null
                        || (datosRiss != null && datosRiss.getErrorFormGeneral() !=  null && !datosRiss.getErrorFormGeneral().contains("Ya cuentas"))){
                    //Si traía modalidad y además no tiene beneficio RISS pedimos el tipo de pago
                    session.setAttribute(DATOS_COTIZACION, dcc);
                    model.addAttribute("Mod35",1);
                    if (cambiarVista) {
                        view = VALIDA_PAGOS_VENTANILLA;
                    } else {
                        view = PIDE_TIPO_PAGO;
                    }
                }else {

                    //Si tiene beneficio RISS le mostramos la cotización
                    session.setAttribute(FORMA_PAGO, FormaPagoEnum.BIMESTRAL.getId());
                    model.addAttribute(FORMA_PAGO,FormaPagoEnum.BIMESTRAL.getId());
                    model.addAttribute("EsRISS", 1);

                    //Si no tiene problemas de modalidad activa se muestra la cotizacion
                    if (cambiarVista) {
                        view = VALIDA_PAGOS_VENTANILLA;
                    } else {
                        view = MUESTRA_COTIZACION;
                    }

                    /**Cuando sea cambio de modalidad de 44 a 35*/
                    if(cambioAModalidad35){
                        dcc.setRenovacion(false);
                    }
                    Cotizacion cotizacion = seguroIndividualServices.generaCotizacion(dcc);
                    cotizacion.setAplicaCuestionario(dcc.getAplicaCuestionario());
                    session.setAttribute(COTIZACION, cotizacion);
                    agregaDatosVistaCotizacion(session);

                    //En caso de que sea renovacion oportuna
                    log.info(" dcc.getRenovacion():       " + dcc.getRenovacion());
                    log.info(" dcc.dcc.getModalidad():    " + dcc.getModalidad());
                    log.info(" cotizacion.getRenovacion():" + cotizacion.getRenovacion());
                    log.info("forma pago: "+session.getAttribute(FORMA_PAGO));

                    if(cambioAModalidad35){
                        log.info("AGREGA DATOS COTIZACION A SESION/ CAMBIO A MODALIDAD 35");
                        model.addAttribute("datos", dcc);
                        session.setAttribute(DATOS_COTIZACION, dcc);
                    }
                    else if(dcc.getRenovacion() && esExtemporanea!=null && !esExtemporanea){
                        log.info("GUARDAR TRAMITE RENOVACION");
                        //Guarda la solicitud
                        this.guardaTramite( model, session, request);
                        model.addAttribute("EsRISS", 1);
                        session.setAttribute(ES_INTERNET, model.asMap().get(ES_INTERNET));
                        //Enviar a la pantalla de Cotizacion de Renovacion
                        if (cambiarVista) {
                            view = VALIDA_PAGOS_VENTANILLA;
                        } else {
                            view = MUESTRA_COTIZACION_RENOVACION;
                        }
                    }
                }
            }

        } else {

            if(dcc.getErrorFormGeneral() != null){
                log.info(" dcc.getErrorFormGeneral():" + dcc.getErrorFormGeneral());

                if(dcc.getErrorFormGeneral().contains("No se localiza el registro de NSS asociado a su  CURP, si no cuenta con NSS ingrese")
                        && dcc.getErrorFormGeneral().contains("Si ya cuenta con NSS, acuda a su subdelegaci\u00F3n para actualizar los datos de su registro.")){
                    log.info(" se encontro error al validar la vigencia del nss");
                    dcc.setErrorFormGeneral(MSG_ERROR_NSS_NO_VIGENTE);

                }else if(dcc.getErrorFormGeneral().equals("Usted no puede realizar la solicitud de Incorporaci\u00F3n Voluntaria, ya que a la fecha se encuentra inscrito en el r\u00E9gimen obligatorio.")){
                    if(esCompra(persona)){
                        dcc.setErrorFormGeneral(MSG_ERROR_VIGENTE_RO_COMPRA);
                    }
                    if( esRenovacion(persona)){
                        dcc.setErrorFormGeneral(MSG_ERROR_VIGENTE_RO_RENOVA);
                    }

                }
            }

            model.addAttribute("datos", dcc);
            session.setAttribute(DATOS_COTIZACION, dcc);

            if (cambiarVista) {
                view = VALIDA_PAGOS_VENTANILLA;
            } else {
                view = PIDE_MODALIDAD;
            }
        }
        return view;
    }

    private boolean validaUltimaModalidadVigente(Long idPersona){

        AsignacionNssIvro asignacionNss = null;
        RespuestaSituacionAseguramiento response = new RespuestaSituacionAseguramiento();
        ModalidadFecha ultimaModalidad = new ModalidadFecha();
        ModalidadFecha ultimaModVigente = new ModalidadFecha();
        ModalidadFecha ultimaModBaja = new ModalidadFecha();

        try{
            asignacionNss = seguroIndividualServices.obtenerAsignacionNss(idPersona);
            log.info("asignacionNSS: "+asignacionNss.getCveIdAsignacionNss());
            response = wsConsultaSituacionAseguramiento.getSituacionAseguramientoXAsginacionNSS(""+asignacionNss.getCveIdAsignacionNss());
            log.info("Response: "+response.getResultado());
            if(response!=null){
                if(response.getResultado()!=null){
                    if(response.getResultado().getModalidadesFechaVigente()!=null){
                        log.info("la lista VIGENTE contiene "+ response.getResultado().getModalidadesFechaVigente().size() +" elementos");
                    }else{
                        log.info("++ lista VIGENTE vacia");
                    }
                    if(response.getResultado().getModalidadesFechaBaja()!=null){
                        log.info("la lista BAJA contiene "+ response.getResultado().getModalidadesFechaBaja().size() +" elementos");
                    }else{
                        log.info("++ lista BAJA vacia");
                    }

                }else{
                    log.info("resultado vacio");
                }
            }else{
                log.info("response vacio");
            }
            ultimaModVigente =this.recuperaUltimaModVigente(response.getResultado().getModalidadesFechaVigente());
            ultimaModBaja =this.recuperaUltimaModVigente(response.getResultado().getModalidadesFechaBaja());
            if(ultimaModVigente!=null&&ultimaModBaja==null){
                ultimaModalidad = ultimaModVigente;
            }else if(ultimaModVigente==null&&ultimaModBaja!=null){
                ultimaModalidad = ultimaModBaja;
            }else if(ultimaModVigente!=null&&ultimaModBaja!=null){
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", new Locale("es", "mx"));
                Date fechaVigente = sdf.parse(ultimaModVigente.getFecha());
                Date fechaBaja = sdf.parse(ultimaModBaja.getFecha());

                ultimaModalidad = fechaVigente.after(fechaBaja)? ultimaModVigente : ultimaModBaja;
            }else{
                ultimaModalidad = null;
            }
        } catch (Exception e) {

            this.log.error(e);
        }

        if(ultimaModalidad!=null){
            log.info("Ultima modalidad vigente: " + ultimaModalidad.getModalidad());
        }else{
            log.error("no existe ultima modalidad");
        }
        if(ultimaModalidad!=null && ultimaModalidad.getModalidad()!=null){
            if (ArrayUtils.contains(MODALIDADES_IVRO, ultimaModalidad.getModalidad())) {
                log.info("ultima Modalidad es ivro: true");
                return true;
            }
        }
        log.info("ultima Modalidad es ivro: false");
        return false;
    }

    private ModalidadFecha recuperaUltimaModVigente(List<ModalidadFecha> modalidadFechaList){
        if(modalidadFechaList!=null && modalidadFechaList.size()>0) {
            ModalidadFecha modalidadFecha = modalidadFechaList.get(0);

            try {
                for (ModalidadFecha temp : modalidadFechaList) {

                    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", new Locale("es", "mx"));
                    Date fechaTemp = sdf.parse(temp.getFecha());
                    Date fechaMayor = sdf.parse(modalidadFecha.getFecha());

                    if (fechaMayor.compareTo(fechaTemp) < 0) {
                        modalidadFecha = temp;
                    }
                }
            } catch (ParseException p) {
                log.error("no se pudo parsear: ", p);
            }
            log.info("Ultima Modalidad Vigente: Fecha: "+modalidadFecha.getFecha()+ " Modalidad: "+modalidadFecha.getModalidad());
            return modalidadFecha;
        }else{
            return null;
        }

    }


    /**
     * Verifica si la compra de seguro necesita pedir la modalidad del seguro
     * @param request
     * @param model
     * @param session
     * @param datosCotizacion
     * @return
     */
    @RequestMapping(value = "/modalidad")
    public String agregarModalid(HttpServletRequest request, Model model, HttpSession session,
                                 @ModelAttribute(value="datos") DatosCalculoCuota datosCotizacion) {
        String view  = MUESTRA_COTIZACION;
        Persona persona = (Persona)session.getAttribute(PERSONA);
        Boolean renovar = (Boolean) session.getAttribute("renovar");
        Boolean tieneSeguroAnterior = (Boolean) session.getAttribute("tieneSeguroAnterior");

        log.info("renovar: "+renovar);
        log.info("tieneSeguroAnterior: "+tieneSeguroAnterior);

        OrigenSolicitudEnum origen = OrigenSolicitudEnum.getById(SeguroIvroUtil.getAmbiente(request));
        DatosCalculoCuota dccc = null;

        if(renovar||tieneSeguroAnterior){
            log.info("obten Datos Cotizacion como Renovacion");
            dccc = seguroIndividualServices.obtenDatosCotizacionRenovacion(persona, origen, ModalidadEnum.fromId(datosCotizacion.getModalidad()).getNumModalidad());
        }else{
            log.info("obten Datos Cotizacion como Compra");
            dccc = seguroIndividualServices.obtenDatosCotizacion(persona, origen, ModalidadEnum.fromId(datosCotizacion.getModalidad()).getNumModalidad());
        }

        log.info("dccc.getModalidad() : "+ dccc.getModalidad());
        log.info("dccc.getRenovacion(): "+ dccc.getRenovacion());
        log.info("dccc.getErrorFormGeneral(): "+ dccc.getErrorFormGeneral());
        if (StringUtils.trimToNull(dccc.getErrorFormGeneral()) != null) {
            log.info("PIDE MODALIDAD");
            model.addAttribute("datos", dccc);
            session.setAttribute(DATOS_COTIZACION, dccc);
            view = PIDE_MODALIDAD;
        }else{
            DatosRiss datosRiss = (DatosRiss) session.getAttribute("datosRiss");
            log.info("Agrega modalidad - datos riss:"+datosRiss);

            if(datosRiss != null && datosRiss.getIdOrigenSolicitud().equals(-1L)){
                DatosCalculoCuota dcc = (DatosCalculoCuota)session.getAttribute(DATOS_COTIZACION);
                dcc.getAplicaCuestionario();
                dcc.setModalidad(datosCotizacion.getModalidad());
                session.removeAttribute("datosRiss");
                session.removeAttribute(DATOS_COTIZACION);
                session.setAttribute(DATOS_COTIZACION,dcc);

                view = PIDE_TIPO_PAGO;

            }else{

                if(datosRiss == null || (datosRiss.getErrorFormGeneral() !=  null && !datosRiss.getErrorFormGeneral().contains("Ya cuentas"))){
                    //Ya que se selecciona la modalidad y además no tiene beneficio RISS pedimos el tipo de pago
                    DatosCalculoCuota dcc = (DatosCalculoCuota)session.getAttribute(DATOS_COTIZACION);
                    dcc.getAplicaCuestionario();
                    dcc.setModalidad(datosCotizacion.getModalidad());
                    session.removeAttribute(DATOS_COTIZACION);

                    session.setAttribute(DATOS_COTIZACION,dcc);

                    view = PIDE_TIPO_PAGO;
                }else {
                    //Si tiene beneficio RISS le mostramos la cotización
                    session.setAttribute(FORMA_PAGO, FormaPagoEnum.BIMESTRAL.getId());

                    DatosCalculoCuota dcc = (DatosCalculoCuota)session.getAttribute(DATOS_COTIZACION);
                    dcc.setModalidad(datosCotizacion.getModalidad());
                    session.removeAttribute(DATOS_COTIZACION);
                    session.setAttribute(DATOS_COTIZACION,dcc);

                    Cotizacion cotizacion = seguroIndividualServices.generaCotizacion(dcc);
                    cotizacion.setAplicaCuestionario(dcc.getAplicaCuestionario());

                    session.setAttribute(COTIZACION, cotizacion);

                    agregaDatosVistaCotizacion(session);
                }
            }
        }
        session.removeAttribute("renovar");
        session.removeAttribute("tieneSeguroAnterior");
        return view;
    }

    @RequestMapping(value = "/seleccionarTipoPago")
    public String seleccionarTipoPago(HttpServletRequest request, Model model, HttpSession session){
        String view  = MUESTRA_COTIZACION;
        Boolean esRenovacion = (Boolean) session.getAttribute("esRenovacion");

        String tipoPago = request.getParameter("tipoPago");

        session.setAttribute(FORMA_PAGO, Boolean.valueOf(tipoPago)?FormaPagoEnum.BIMESTRAL.getId():FormaPagoEnum.ANUAL.getId());

        Boolean recargos = Boolean.valueOf(tipoPago);
        session.setAttribute(RECARGOS, recargos);

        log.info("Recargos: "+recargos);

        DatosCalculoCuota dcc = (DatosCalculoCuota)session.getAttribute(DATOS_COTIZACION);
        session.removeAttribute(DATOS_COTIZACION);
        dcc.setRecargos(recargos);
        Cotizacion cotizacion = seguroIndividualServices.generaCotizacion(dcc);
        cotizacion.setAplicaCuestionario(dcc.getAplicaCuestionario());
        session.setAttribute(COTIZACION, cotizacion);
        agregaDatosVistaCotizacion(session);

        if(esRenovacion != null && esRenovacion){
            log.info("GUARDAR TRAMITE RENOVACION");
            //Guarda la solicitud
            this.guardaTramite( model, session, request);
            //Enviar a la pantalla de Cotizacion de Renovacion
            view = MUESTRA_COTIZACION_RENOVACION;
            session.setAttribute(ES_INTERNET, model.asMap().get(ES_INTERNET));
        }

        return view;
    }

    /**
     * agerag datos complementarios a mostrar en la pantalla de cotizacion
     * @param session
     */
    private void agregaDatosVistaCotizacion (HttpSession session) {
        try {

            String correo = (String) session.getAttribute("correoIVRO");
            Persona persona = (Persona)session.getAttribute(PERSONA);
            Domicilio domicilio = domicilioExternosServiceBusiness.consultarUltimoDomicilioParticilar(persona.getIdPersona());
            session.setAttribute("domicilio", domicilio);
            Persona personaMC = seguroIndividualServices.getMediosContactoPersona(persona);

            if(personaMC!= null && correo!=null && personaMC.getMediosContacto() !=null &&personaMC.getMediosContacto().length>1){

                ArrayList<MedioContacto> mediosContacto = new ArrayList<MedioContacto>();
                log.info("la persona tiene "+personaMC.getMediosContacto().length+"correos." );
                for(MedioContacto temp:personaMC.getMediosContacto()){
                    log.info("temp correo: "+temp.getDesFormaContacto()+" portal correo: "+correo);
                    if(!temp.getTipoMedioContacto().getIdTipoMedioContacto().equals(TipoContactoEnum.CORREO_ELECTRONICO.getId())){
                        if(mediosContacto.size()>1 && mediosContacto.contains(temp)){
                            log.info("Medio de contacto duplicado: "+temp.getDesFormaContacto());
                        }else{
                            log.info("Se agrega el medio de contacto: "+temp.getDesFormaContacto());
                            mediosContacto.add(temp);
                        }
                    }else{
                        if(temp.getDesFormaContacto().equals(correo)){
                            log.info("Se agrega correo electronico");
                            MedioContacto nuevoMC = new MedioContacto();
                            nuevoMC.setDesFormaContacto(temp.getDesFormaContacto());
                            nuevoMC.setClave(temp.getClave());
                            nuevoMC.setTipoMedioContacto(temp.getTipoMedioContacto());
                            mediosContacto.add(nuevoMC);
                        }
                    }
                }
                MedioContacto[] ultimo = new MedioContacto[mediosContacto.size()];
                ultimo = (MedioContacto[])mediosContacto.toArray(ultimo);
                log.info("Size ultimo: "+ultimo.length);
                personaMC.setMediosContacto(ultimo);
            }
            session.setAttribute("personaMC", personaMC);
        } catch (DomicilioNoLocalizadoException ex) {
            log.error(ex);
        } catch (MunicipioImssNoLocalizadoException ex) {
            log.error(ex);
        }
    }

    /**
     * VAlida si la compra del seguro aplica cuestionario
     * @param model
     * @param session
     * @param request
     * @return
     */
    @RequestMapping(value = "/aplicaCuestionario")
    public String aplicaCuestionario( Model model, HttpSession session, HttpServletRequest request) {
        String view;
        Cotizacion cotizacion = (Cotizacion)session.getAttribute(COTIZACION);
        if(cotizacion.getAplicaCuestionario() != null && cotizacion.getAplicaCuestionario()) {
            model.addAttribute("nss", cotizacion.getDetalle().getEmpleados()[0].getNumeroSeguridadSocial());
            view = CUESTIONARIO;
        } else {
            view  = guardaTramite(model, session, request);
        }
        return view;
    }

    /**
     * Permite cambiar el domicilio
     * @param model
     * @return
     */
    @RequestMapping(value = "/otraUbicacion")
    public String comunOtraUbicacion(Model model) {
        model.addAttribute("domicilioAlterno", new mx.gob.imss.ctirss.delta.model.domicilio.Domicilio());
        return "wizardSeguroIndividualAgregarDomicilio";
    }

    /**
     * Obtiene el nuevo domicilio
     * @param model
     * @param session
     * @param domicilio
     * @return
     */
    @RequestMapping(value = "/agregarDomicilioNueva", method = RequestMethod.POST)
    public String comunAgregarDomicilio(Model model,HttpSession session,
                                        @ModelAttribute mx.gob.imss.ctirss.delta.model.domicilio.Domicilio domicilio) {
        Boolean esRenovacion = (Boolean) session.getAttribute("esRenovacion");
        log.info("---esRenovacion: "+esRenovacion);
        if(esRenovacion==null){
            log.info("variable esRenovacion Null");
            esRenovacion = false;
        }

        session.removeAttribute("domicilio");

        domicilio.setCodigoPostal(domicilio.getAsentamiento().getCodigoPostal());
        domicilio.setLocalidad(domicilio.getAsentamiento().getLocalidad());
        domicilio.getVialidadPrimaria().setNombre(domicilio.getCalle());

        Domicilio domicilioOtraUbicacion = SeguroIvroUtil.convertirDomicilioAImssDigital(domicilio);
        domicilioOtraUbicacion.getAsentamiento().setMunicipio(
                domicilioOtraUbicacion.getAsentamiento().getLocalidad()
                        .getMunicipio());
        domicilioOtraUbicacion.setColonia(domicilioOtraUbicacion.getAsentamiento()
                .getNombre());

        log.info("********* VALOR DEL DOMICILIO OTRA UBICACION: " + domicilioOtraUbicacion.toString());

        model.addAttribute("domicilio", domicilioOtraUbicacion);
        session.setAttribute("domicilio", domicilioOtraUbicacion);
        Cotizacion cotizacion = (Cotizacion)session.getAttribute(COTIZACION);
        log.info("********* COTIZACION RENOVACION: " + cotizacion.getRenovacion());
        if(esRenovacion){
            model.addAttribute(ES_INTERNET, session.getAttribute(ES_INTERNET));
            log.info("********* SESSION: " + session.getAttribute(ES_INTERNET));

            //Genera datos de la firma
            Solicitud solicitud = (Solicitud)session.getAttribute(SOLICITUD);
            FirmaElectronica firma = SeguroIvroUtil.generarCadenaOriginalyDatosFirma(solicitud);
            model.addAttribute("firmaEntrada", firma);

            return MUESTRA_COTIZACION_RENOVACION;
        }


        return MUESTRA_COTIZACION;
    }

    /**
     * Obtiene el nuevo domicilio
     * @param model
     * @param session
     * @return
     */
    @RequestMapping(value = "/regresarCotizacion", method = RequestMethod.POST)
    public String regresarCotizacion(Model model,HttpSession session) {
        return MUESTRA_COTIZACION_RENOVACION;
    }
    /**
     * Valida que el cuestionario sea respondido correctamente
     * @param model
     * @param session
     * @param request
     * @param tramiteCuestionario
     * @return
     */
    @RequestMapping(value = "/validaCuestionario")
    public String validaCuestionario( Model model, HttpSession session, HttpServletRequest request,
                                      @ModelAttribute("respuestasCuestionario") TramiteCuestionarioDummy tramiteCuestionario) {

        Long ambiente = SeguroIvroUtil.getAmbiente(request);
        PersonaCuestionario personaCuestionario = UtilConvert.parseToPersonaCuestionario(tramiteCuestionario);
        Cotizacion cotizacion = (Cotizacion)session.getAttribute(COTIZACION);
        if(tramiteCuestionario.getRespuestas().getSumatoriaRespuestas() == 0) {
            log.info("GENERA TRAMITE -- renovacion: "+cotizacion.getRenovacion());
            session.setAttribute(PERSONA_CUESTIONARIO, personaCuestionario);
            return guardaTramite(model, session, request);
        } else {
            Persona persona = (Persona)session.getAttribute(PERSONA);
            log.info("GENERA TRAMITE RECHAZO-- renovacion: "+cotizacion.getRenovacion());
            TramiteSeguroIvro tramite = SeguroIvroUtil.generaTramiteIvroIndividual(cotizacion, persona, personaCuestionario, true);

            // TODO
            String strUsuario;
            if(!OrigenSolicitudEnum.PORTAL_CIUDADANO.getId().equals(ambiente)) {
                UsuarioSSO sso = this.procesarUsuarioSSO(request);
                strUsuario = sso.getCurp();
            } else {
                strUsuario = seguroIndividualServices.obtenerCurpPorIdPersona(persona.getIdPersona());
            }

            Solicitud solicitud = SeguroIvroUtil.armaSolicitudInicial(ambiente, tramite, strUsuario);
            tramite.setObservacion("RECHAZADO POR CONDICIONES MEDICAS");
            session.setAttribute(SOLICITUD, solicitud);
            rechazaSolicitud(session, tramite);
            model.addAttribute("error", MENSAJE_CUESTIONARIO);
            return CUESTIONARIO;
        }
    }

    /**
     * GUarda una solicitud como iniciada
     * @param model
     * @param session
     * @param request
     * @return
     */
    @RequestMapping(value = "/guardaTramite")
    public String guardaTramite( Model model, HttpSession session, HttpServletRequest request) {
        String view  = RESUMEN;
        Long ambiente = SeguroIvroUtil.getAmbiente(request);
        Cotizacion cotizacion = (Cotizacion)session.getAttribute(COTIZACION);
        Boolean esCuestionario = (Boolean)session.getAttribute(ES_CUESTIONARIO);
        Persona persona = (Persona)session.getAttribute(PERSONA);
        PersonaCuestionario cuestionario = (PersonaCuestionario)session.getAttribute(PERSONA_CUESTIONARIO);
        /**Se vigila variable de cambio de modalidad*/
        Boolean cambioAModalidad35 = (session.getAttribute(CAMBIAR_A_RENOVACION)!= null)?((Boolean) session.getAttribute(CAMBIAR_A_RENOVACION)):Boolean.FALSE;

        TramiteSeguroIvro tramite= null;

        log.info("cambioAModalidad35: "+cambioAModalidad35);
        log.info("cotizacion.getRenovacion() "+cotizacion.getRenovacion());
        if(cotizacion.getRenovacion() != null && cotizacion.getRenovacion() && cambioAModalidad35 ) {
            log.info("*****GUARDANDO SEGURO COMO COMPRA POR CAMBIO DE MODALIDAD *******");
            tramite = SeguroIvroUtil.generaTramiteIvroIndividual(cotizacion, persona, cuestionario, true);
        }else if(cotizacion.getRenovacion() != null && cotizacion.getRenovacion() && !cambioAModalidad35 ){
            log.info("*****GUARDANDO RENOVACION: "+cotizacion.getRenovacion()+"*******");
            tramite = SeguroIvroUtil.generaTramiteIvroIndividual(cotizacion, persona, cuestionario, false);
        }else{
            log.info("*****GUARDANDO SEGURO *******");
            boolean validaTemp= esCompra(persona);
            log.info("validaTemp: "+validaTemp);
            tramite = SeguroIvroUtil.generaTramiteIvroIndividual(cotizacion, persona, cuestionario, validaTemp);
        }
        log.info("+++++TRAMITE ES RENOVACION: "+tramite.getRenovacion());
        //guardar el domicilio, bajarlo de session y setearselo al trâ®©te
        Domicilio domicilio = (Domicilio)session.getAttribute("domicilio");
        log.info("  Id Domcilio:" +  domicilio.getIdDomicilio());
        log.info("  Domicilio:" +  domicilio.getCalle());
        tramite.setDomicilioSeguro(domicilio);
        log.info("  Tramite-Domicilio:" +  tramite.getDomicilioSeguro().getCalle());
        // TODO
        String strUsuario;
        if(!OrigenSolicitudEnum.PORTAL_CIUDADANO.getId().equals(ambiente)) {
            UsuarioSSO sso = this.procesarUsuarioSSO(request);
            strUsuario = sso.getCurp();
        } else {
            strUsuario = seguroIndividualServices.obtenerCurpPorIdPersona(persona.getIdPersona());
        }

        Solicitud solicitud = SeguroIvroUtil.armaSolicitudInicial(ambiente, tramite, strUsuario);
        Solicitud respuesta = seguroIndividualServices.guardaSolicitud(solicitud);
        if(StringUtils.trimToNull(respuesta.getErrorFormGeneral()) != null){
            solicitud.setErrorFormGeneral(respuesta.getErrorFormGeneral());
        } else {
            solicitud.setIdSolicitud(respuesta.getIdSolicitud());
            solicitud.setNumSolicitud(respuesta.getNumSolicitud());
            solicitud.getTramite()[0].setTramiteId(respuesta.getTramite()[0].getTramiteId());
            log.info("  respuesta.getNumSolicitud():" +  respuesta.getNumSolicitud());
        }
        if(OrigenSolicitudEnum.INTERNET.getId().equals(ambiente)) {
            FirmaElectronica firma = SeguroIvroUtil.generarCadenaOriginalyDatosFirma(solicitud);
            model.addAttribute("firmaEntrada", firma);
        }
        session.setAttribute(SOLICITUD, solicitud);
        model.addAttribute(ES_INTERNET, OrigenSolicitudEnum.INTERNET.getId().equals(ambiente));
        model.addAttribute(ES_CUESTIONARIO, esCuestionario);
        return view;
    }
    /**
     * MEtodo encargado de cancelar una solicitud
     * @param domicilioInicial
     * @param idPersona
     */
    private void guardarPersonaDomicilio(Domicilio domicilioInicial,Long idPersona) {
        try {
            log.info("IdPersona:" + idPersona);
            log.info("  Domicilio previo:" +  domicilioInicial.getCalle());
            seguroIndividualServices.guardarYAsociarDomiciliosPersona(domicilioInicial,idPersona);
        } catch (DomicilioNoValidoException ex) {
            log.error("Error al guardar domicilio ", ex);
        } catch (DomicilioNoLocalizadoException ex) {
            log.error("Error al guardar domicilio ", ex);
        }
    }
    /**
     * MEtodo encargado de cancelar una solicitud
     * @param session
     * @return
     */

    /**
     * MEtodo encargado de cancelar una solicitud
     * @param session
     * @param tramite
     * @return
     */
    private Solicitud rechazaSolicitud( HttpSession session, TramiteSeguroIvro tramite) {
        Solicitud solicitud = (Solicitud)session.getAttribute(SOLICITUD);

        solicitud.getEstadoSolicitud().setIdEstadoSolicitud(
                EstadoSolicitudEnum.RECHAZADA.getId().intValue());
        solicitud.getTramite()[0].getEstadoTramite().setIdEstadoTramitePersona(
                EstadoTramiteEnum.RECHAZADO.getId());
        solicitud.getTramite()[0].getEstadoTramite().setDescripcion(EstadoTramiteEnum.RECHAZADO.name());
        String detalleTramiteIvro = SeguroIvroUtil.getDetalleTramiteString(tramite);
        solicitud.getTramite()[0].setDetalleTramiteXml(detalleTramiteIvro);

        return seguroIndividualServices.guardaSolicitud(solicitud);
    }

    /**
     * MEtodo encargado de cancelar una solicitud
     * @param session
     * @return
     */
    @RequestMapping(value = "/cancelaTramite")
    public @ResponseBody Solicitud cancelaSolicitud( HttpSession session) {
        Solicitud solicitud = (Solicitud)session.getAttribute(SOLICITUD);
        solicitud.getEstadoSolicitud().setIdEstadoSolicitud(
                EstadoSolicitudEnum.CANCELADA.getId().intValue());
        solicitud.getTramite()[0].getEstadoTramite().setIdEstadoTramitePersona(
                EstadoTramiteEnum.CERRADO.getId());
        return seguroIndividualServices.guardaSolicitud(solicitud);
    }


    /**
     * Guarda el tramite y la solicitud en proceso agragndole la firma si es necesaria, para que sea concluida
     * @param session
     * @param request
     * @param idPersona
     * @return
     */
    @RequestMapping(value = "/obtenerClaveSeguro/{idPersona}", method = RequestMethod.POST)
    public @ResponseBody Long obtenerCveSeguroIvro(HttpSession session, HttpServletRequest request,
                                                   @PathVariable Long idPersona) {

        SegurosIvro segurosIvro;
        try {

            segurosIvro = seguroIndividualServices.obtenSeguroIndividual(idPersona);

        } catch (IVROExceptionGenerico e) {

            return null;
        }
        Long idSeguro=segurosIvro.getSeguroIvro()[0].getCveIdSeguroIvro();

        return idSeguro;
    }

    /**
     * Guarda el tramite y la solicitud en proceso agragndole la firma si es necesaria, para que sea concluida
     * @param session
     * @param request
     * @param firmaElectronica
     * @return
     */
    @RequestMapping(value = "/terminaTramite", method = RequestMethod.POST)
    public @ResponseBody Solicitud terminatTramite(HttpSession session, HttpServletRequest request,
                                                   @RequestBody FirmaElectronica firmaElectronica, @ModelAttribute(value="datos") DatosCalculoCuota datosCotizacion) {

        Domicilio domicilio = (Domicilio)session.getAttribute("domicilio");
        log.info("  Id Domcilio terminatTramite:" +  domicilio.getIdDomicilio());
        log.info("  Domicilio terminatTramite:" +  domicilio.getCalle());
        Solicitud solicitud = (Solicitud)session.getAttribute(SOLICITUD);
        Long ambiente = SeguroIvroUtil.getAmbiente(request);
        solicitud.setFirmadaDigitalmente(false);
        if(OrigenSolicitudEnum.INTERNET.getId().equals(ambiente)) {
            solicitud.setFirmadaDigitalmente(true);
            solicitud.setCadenaOriginal(firmaElectronica.getCadenaOriginal());
            solicitud.setFirmaElectronica(firmaElectronica);
        }

        EstadoTramite estTram = new EstadoTramite();
        estTram.setIdEstadoTramitePersona(EstadoTramiteEnum.CERRADO.getId());
        estTram.setDescripcion(EstadoTramiteEnum.CERRADO.name());
        solicitud.getTramite()[0].setEstadoTramite(estTram);

        TramiteSeguroIvro tsi = (TramiteSeguroIvro)solicitud.getTramite()[0];

        tsi.setDomicilioSeguro(domicilio);

        if(domicilio.getIdDomicilio()==null){
            Long idPersona = tsi.getPersona().getIdPersona();
            log.info("Se agrega nuevo domicilio, detalle domicilio: "+domicilio.toString());
            guardarPersonaDomicilio(domicilio, idPersona);
        }else{
            log.info("Domicilio sin cambios, idDomicilio: "+domicilio.getIdDomicilio());
        }

        /**Se vigila variable de cambio de modalidad*/
        Boolean cambioAModalidad35 = (session.getAttribute(CAMBIAR_A_RENOVACION)!= null)?((Boolean) session.getAttribute(CAMBIAR_A_RENOVACION)):Boolean.FALSE;
        Boolean esRenovacion = this.esRenovacion(tsi.getPersona()) && !cambioAModalidad35;
        log.info(" esRenovacion en terminaTramite:" + esRenovacion);
        log.info("CambioAModalidad35: " + cambioAModalidad35);


        try{
            if(esRenovacion != null && esRenovacion){
                log.info(" datosCotizacion.getModalidad(): " + datosCotizacion.getModalidad());
                String modalidad = datosCotizacion.getModalidad() != 0? String.valueOf(datosCotizacion.getModalidad()) : ModalidadEnum.CUARENTAYCUATRO.getNumModalidad();

                String nrp =  registroPatronalServiceBusiness.obtenerNrpConvencionalPorDomicilioParticularYModalidad(
                        solicitud.getTramite()[0].getPersona().getIdPersona(), modalidad);
                log.info(" nrp: " + nrp);
                RegistroPatronal registroPatronal = new RegistroPatronal();
                registroPatronal.setNumeroRegistroPatronal(nrp);
                tsi.setRegistroPatronal(registroPatronal);
                if(cambioAModalidad35){
                    session.setAttribute("enviarCorreoRenovacionIVRO",Boolean.FALSE);
                    session.setAttribute("enviarCorreoCompraIVRO",Boolean.TRUE);
                }else{
                    /**Se agrega Variable a sesion para el envio de comprobante y linea de captura en la renovacion IVRO
                     * Esta variable se quitara de sesion en el detalle de tramite para qeu solo se ejecute al termino de una Renovacion*/
                    session.setAttribute("enviarCorreoRenovacionIVRO",Boolean.TRUE);
                }
            }else{
                session.setAttribute("enviarCorreoCompraIVRO",Boolean.TRUE);
            }
        }catch(Exception ex){
            log.error("Error al obtener el nrp", ex);
        }
        log.info("  Tramite-Domicilio terminatTramite:" +  tsi.getDomicilioSeguro().getCalle());
        String detalleTramiteIvro = SeguroIvroUtil.getDetalleTramiteString(tsi);
        solicitud.getTramite()[0].setDetalleTramiteXml(detalleTramiteIvro);
        log.info("  Detalle:\n" +  detalleTramiteIvro);
        //Agregar estado del tramite a cerrado

        solicitud.getEstadoSolicitud().setIdEstadoSolicitud(
                EstadoSolicitudEnum.PENDIENTE_AUTORIZACION.getId().intValue());
        seguroIndividualServices.guardaSolicitud(solicitud);

        try {
            MunicipioIMSS municipioIMSS = registroPatronalServiceBusiness.obtenerMunicipioImssDeDomicilioParticularPorIdPersona(solicitud.getTramite()[0].getPersona().getIdPersona());
            if (municipioIMSS != null && municipioIMSS.getSubdelegacion() != null) {
                solicitudServiciosExpuestos.asociarSolicitudSubDelegacion(solicitud.getIdSolicitud(), municipioIMSS.getSubdelegacion().getId());
            }
        } catch (Exception e){
            log.error("No se localizo Subdelegacion", e);
        }

        return solicitud;
    }

    /**
     * Limpia los datos de sesion
     * @param session
     * @return
     *
     */
    @RequestMapping(value = "/limpiaSesion")
    public @ResponseBody String limpiaSesion(HttpSession session) {
        session.removeAttribute(DATOS_COTIZACION);
        session.removeAttribute(COTIZACION);
        session.removeAttribute(PERSONA);
        session.removeAttribute(SOLICITUD);
        session.removeAttribute(PERSONA_CUESTIONARIO);
        session.removeAttribute("personaMC");
        session.removeAttribute("esRenovacion");
        session.removeAttribute("esExtemporanea");
        session.removeAttribute("esCompra");
        session.removeAttribute(ES_CUESTIONARIO);
        session.removeAttribute("datosRiss");
        session.removeAttribute(ES_INTERNET);
        session.removeAttribute("domicilio");
        session.removeAttribute(PERSONA_CUESTIONARIO);
        session.removeAttribute("esPosteriorExtemporanea");
        session.removeAttribute(FORMA_PAGO);

        return "ok";
    }

    private boolean esCompra(Persona persona){
        boolean comprar = SeguroIvroUtil.puedeComprarSeguro(obtenerSeguros(persona));
        return comprar;
    }

    private boolean esRenovacion(Persona persona){
        boolean renovar = SeguroIvroUtil.puedeRenovarSeguro(obtenerSeguros(persona));
        return renovar;
    }

    /**
     * Servicio para validar si existe domicilio particular en la renovacion
     * @param session
     * @param request
     * @return
     */
    @RequestMapping(value = "/validaDomicilio")
    public @ResponseBody Map<String, ? extends Object> validaDomicilio(HttpSession session, HttpServletRequest request) {

        log.info("Entra a validar Domicilio");
        Map<String, Object> result = new HashMap<String, Object>();
        DatosCalculoCuota dcc;
        try {

            Persona persona = new Persona();
            persona.setIdPersona((Long)session.getAttribute("idPersona"));
            result.put("idPersona", persona.getIdPersona());
            log.info("idPersona: "+persona.getIdPersona());
            dcc = seguroIndividualServices.obtenDatosCotizacionRenovacion(persona, null, null);
            log.info("valida domicilio dcc: "+dcc);
            if(dcc!=null && dcc.getErrorFormGeneral()!=null && dcc.getErrorFormGeneral().contains("696")){
                String errorFormGeneral = dcc.getErrorFormGeneral().substring(5, dcc.getErrorFormGeneral().length());
                result.put("ErrorFormGeneral", errorFormGeneral);
                result.put("sinDomicilio", true);
                log.info("Entra a error sin Domicilio");
            }else{
                log.info("Error general: "+dcc.getErrorFormGeneral());
                result.put("ErrorFormGeneral", null);
                result.put("sinDomicilio", false);
            }

        } catch (Exception e) {
            result.put("ErrorFormGeneral", null);
            result.put("sinDomicilio", false);
        }
        session.removeAttribute("idPersona");
        return result;
    }

    /**
     * Procesa la aceptación de proceder con audedo de pagos y cancela el beneficio RISS
     *
     * @param model
     * @param persona
     * @return
     */
    @RequestMapping(value = "/procesaFaltaPago")
    public String procesaFaltaPago(Model model, @ModelAttribute(value = PERSONA) Persona persona) {
        log.info("Entrando a Cancelar el beneficio por falta de pago");
        log.info("Persona recibida: idPErsona:" + persona.getIdPersona() + "RFC: " + persona.getRfc() +
                "Persona errorForm: " + persona.getErrorFormGeneral());

        try {
            Fisica personaFis = new Fisica();
            personaFis.setIdPersona(persona.getIdPersona());
            personaFis.setRfc(persona.getRfc());
            personaFis.setNss(personaBusinessRemote.obtenerNssPersona(persona.getIdPersona()));

            log.info("PersonaFis | \nidPersona: " + personaFis.getIdPersona() + "\nRFC: " +
                    persona.getRfc() + "\nNSS: " + personaFis.getNss());
            seguroIndividualServices.cancelaBeneficioRiss(personaFis, MotivoCancelacionBeneficioEnum.NO_CUMPLEN_SUPUESTOS_IMSS);
            persona.setErrorFormGeneral("");
            model.addAttribute("persona", persona);
        } catch (IVROServiceException e) {
            log.error("Se presento error al cancelar el Beneficio: " + e.getMessage());
        } catch (PersonaSinNSSException psn) {
            log.error("Error al obtener el Id Persona: ", psn);
        } catch (PersonaConVariosNSSException psn) {
            log.error("Error al obtener el Id Persona: ", psn);
        } catch (Exception e) {
            log.error("se presento error al cancelar el Beneficio: ", e);
        }
        return INICIO_TRAMITE;
    }

    private DatosRiss validaBeneficioRissVigente(Long idPersona, String rfc, Long origenId, String curp) {
        try {
            DatosRiss riss = new DatosRiss();
            riss.setIdPersona(idPersona);
            riss.setRfc(rfc);
            riss.setIdOrigenSolicitud(origenId);
            riss.setUsuario(curp);
            log.info("Datos para consultar si tiene Riss:" + riss);
            return seguroIndividualServices.validaIncorporacionBeneficioRiss(riss);

        } catch (Exception e) {
            log.error("Error al consultar RISS: ", e);
            DatosRiss respuesta = new DatosRiss();
            respuesta.setErrorFormGeneral("Error al consultar RISS");
            return respuesta;
        }
    }

    private Persona asignaDatosPersona(Long idPersona, String rfc) {
        Persona persona = new Persona();
        persona.setIdPersona(idPersona);
        TipoPersona tipoPersona = new TipoPersona();
        tipoPersona.setIdTipoPersona(TipoPersonaEnum.FISICA.getId());
        persona.setTipoPersona(tipoPersona);
        persona.setRfc(rfc);

        return persona;
    }

    private SeguroIvro[] obtenerSeguros(Persona persona) {
        SegurosIvro segurosIvro = null;
        try {
            segurosIvro = seguroIndividualServices.obtenSeguroIndividual(persona.getIdPersona());
        } catch (IVROExceptionGenerico e) {
            log.error("Se presento error en WS ivro: " + e.getMessage());
        }
        SeguroIvro[] seguros = segurosIvro != null && segurosIvro.getSeguroIvro() != null
                ? segurosIvro.getSeguroIvro() : new SeguroIvro[]{};

        return seguros;
    }

    private boolean validaPagosRenovacion(RespuestaPagosVentanilla pagosVentanilla, boolean validaFinRenovacionRISS) {

        boolean renovar;

        Calendar fecIniRenova = Calendar.getInstance();
        Calendar fecFinRenova = Calendar.getInstance();
        Calendar hoy = Calendar.getInstance();
        hoy.setTime(new Date());

        try {
            fecIniRenova.setTime(new SimpleDateFormat(FORMAT_DATE_SINSEPARA_YYYYMMDD).parse(pagosVentanilla.getFechaFinAseguramiento()));

            fecIniRenova.set(Calendar.DAY_OF_MONTH, 1);
            fecIniRenova = DateUtils
                    .truncate(fecIniRenova, Calendar.DATE);

            fecFinRenova.setTime(new SimpleDateFormat(FORMAT_DATE_SINSEPARA_YYYYMMDD).parse(pagosVentanilla.getFechaFinAseguramiento()));
            if(validaFinRenovacionRISS) {
                fecFinRenova.set(Calendar.DAY_OF_MONTH, 25);
            }
            fecFinRenova = DateUtils
                    .truncate(fecFinRenova, Calendar.DATE);

            renovar = (hoy.after(fecIniRenova) && hoy.before(fecFinRenova))
                    || DateUtils.isSameDay(hoy, fecIniRenova) || DateUtils.isSameDay(hoy, fecFinRenova);

            SimpleDateFormat format1 = new SimpleDateFormat(FORMAT_DATE_SINSEPARA_YYYYMMDD);
            String iniRenova = format1.format(fecIniRenova.getTime());
            String finRenova = format1.format(fecFinRenova.getTime());
            String hoyMero = format1.format(hoy.getTime());

            log.info("\n fechaFinPago: " + pagosVentanilla.getFechaFinAseguramiento() + "\n Fecha Ini Renova: " + iniRenova + "\n Fecha Fin Renova: " + finRenova + "\n hoyMero:" + hoyMero + "\n Renovar: " + renovar);

            return renovar;

        } catch (Exception e) {
            log.error("Error al validar si el pago en ventanilla está en periodo de renovacion: ", e);
            return false;
        }
    }
}