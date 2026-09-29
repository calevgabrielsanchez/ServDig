package mx.gob.imss.ctirss.delta.gestion.seguroVoluntario.web.controller.wizard;

import java.io.IOException;
import java.math.BigDecimal;
import java.security.InvalidKeyException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.ejb.EJBException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


import mx.gob.imss.consultamod40.Modalidad40VO;
import mx.gob.imss.consultamod40.RespuestaModalidad40;
import mx.gob.imss.consultamod40.WSConsultaMod40;
import mx.gob.imss.ctirss.delta.exception.domicilio.DomicilioNoLocalizadoException;
import mx.gob.imss.ctirss.delta.exception.domicilio.DomicilioNoValidoException;
import mx.gob.imss.ctirss.delta.exception.domicilio.MunicipioImssNoLocalizadoException;
import mx.gob.imss.ctirss.delta.exception.domicilio.SubDelegacionNoLocalizadaException;
import mx.gob.imss.ctirss.delta.framework.base.web.sso.UsuarioSSO;
import mx.gob.imss.ctirss.delta.framework.util.Base64Cipher;
import mx.gob.imss.ctirss.delta.gestion.domicilio.service.interfaces.domicilio.DomicilioServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.domicilio.service.interfaces.domicilio.DomicilioServiceBussinessExternosRemote;
import mx.gob.imss.ctirss.delta.gestion.seguroVoluntario.exception.IVROServiceException;
import mx.gob.imss.ctirss.delta.gestion.seguroVoluntario.service.interfaces.ValidaVigenciaRemote;
import mx.gob.imss.ctirss.delta.gestion.seguroVoluntario.service.interfaces.RetroactividadServiceRemote;
import mx.gob.imss.ctirss.delta.gestion.seguroVoluntario.service.interfaces.SeguroIvroServiceRemote;
import mx.gob.imss.ctirss.delta.gestion.seguroVoluntario.dto.GeneracionMultilineaConsultaResponse;
import mx.gob.imss.ctirss.delta.gestion.seguroVoluntario.dto.HistorialUltimoSeguroCotizadoDTO;
import mx.gob.imss.ctirss.delta.gestion.seguroVoluntario.dto.UltimoTrabajoModalidad40DTO;
import mx.gob.imss.ctirss.delta.gestion.seguroVoluntario.dto.ValidaRetroactividadDTO;
import mx.gob.imss.ctirss.delta.gestion.seguroVoluntario.dto.ValidaRetroactividadRequest;
import mx.gob.imss.ctirss.delta.gestion.seguroVoluntario.dto.ValidaRetroactividadResponse;
import mx.gob.imss.ctirss.delta.gestion.seguroVoluntario.util.CriptoUtilities;
import mx.gob.imss.ctirss.delta.gestion.seguroVoluntario.util.SeguroCvroUtil;
import mx.gob.imss.ctirss.delta.gestion.seguroVoluntario.util.SeguroIvroUtil;
import mx.gob.imss.ctirss.delta.gestion.seguroVoluntario.web.controller.WebServiceCallerController;
import mx.gob.imss.ctirss.delta.gestion.seguroVoluntario.web.servicios.SeguroIndividualServices;
import mx.gob.imss.ctirss.delta.gestion.solicitud.service.interfaces.SolicitudServiciosExpuestosRemote;
import mx.gob.imss.ctirss.delta.model.enums.*;
import mx.gob.imss.ctirss.delta.model.gestion.seguro.PatronPlataformasDigitales;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.OrigenSolicitudEnum;
import mx.gob.imss.ctirss.gestionpersonas.servicios.business.PersonaBusinessRemote;
import mx.gob.imss.digital.modelo.cobranza.Compra;
import mx.gob.imss.digital.modelo.cobranza.Cotizacion;
import mx.gob.imss.digital.modelo.cobranza.DatosCalculoCuota;
import mx.gob.imss.digital.modelo.cobranza.DatosEmpleado;
import mx.gob.imss.digital.modelo.cobranza.Pago;
import mx.gob.imss.digital.modelo.cobranza.PeriodoCuota;
import mx.gob.imss.digital.modelo.comun.Modalidad;
import mx.gob.imss.digital.modelo.domicilio.Domicilio;
import mx.gob.imss.digital.modelo.domicilio.Municipio;
import mx.gob.imss.digital.modelo.medio.contacto.CorreoElectronico;
import mx.gob.imss.digital.modelo.persona.Fisica;
import mx.gob.imss.digital.modelo.persona.Persona;
import mx.gob.imss.digital.modelo.persona.TipoPersona;
import mx.gob.imss.digital.modelo.seguros.AsignacionNssIvro;
import mx.gob.imss.digital.modelo.seguros.SeguroIvro;
import mx.gob.imss.digital.modelo.seguros.SegurosIvro;
import mx.gob.imss.digital.modelo.solicitud.EstadoSolicitud;
import mx.gob.imss.digital.modelo.solicitud.OrigenSolicitud;
import mx.gob.imss.digital.modelo.solicitud.Solicitud;
import mx.gob.imss.digital.modelo.solicitud.TipoSolicitud;
import mx.gob.imss.digital.modelo.tramite.EstadoTramite;
import mx.gob.imss.digital.modelo.tramite.TipoTramite;
import mx.gob.imss.digital.modelo.tramite.TramiteSeguroIvro;
import mx.gob.imss.digital.modelo.tramite.TramiteSeguroIvroMod40;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.ws.client.core.WebServiceTemplate;

import mx.gob.imss.ctirss.delta.gestion.motorCalculo.service.interfaces.CompraServiceRemote;
import mx.gob.imss.ctirss.delta.gestion.motorCalculo.service.exception.SUAException;

@Controller
@SessionAttributes(value={"tramiteSeguro","tramites","idPersonaSolicitante","solicitante","datosCalculo","domicilioSeguro", "domicilioOtraUbicacion","datosCotizacion","solicitud","correoCVRO", "dtoResponse"})
@RequestMapping(value="/wizard/continuacionVoluntaria")
public class WizardSeguroModalidad40Controller extends WebServiceCallerController {


	private static final String KEY_NSS_CIFRADO_FINAL = "_nssCifradoFinal";
	private static final String KEY_TERMINANDO_SOLICITUD = "_terminando_sol";
	private static final String KEY_NSS_MOD40 = "nss";
	private static final String KEY_NSS_CIFRADO_MOD40 = "nssCifrado";
	private static final String MSG_SIN_FECHA_BAJA = "Por este medio no es posible realizar la inscripci\u00F3n en la Continuaci\u00F3n Voluntaria, por favor acude a tu Subdelegaci\u00F3n a realizar tu solicitud.";
	private static final Integer NUM_SALARIOS = 25;
	private static final String MSG_CON_FECHA_BAJA_NSS ="El n\u00FAmero de seguridad social (NSS) no est\u00E1 vigente o no se localiza, favor de acudir a la Subdelegaci\u00F3n";
	private static final String MSG_VALIDACION_EXPIRADO = "El periodo para solicitar el reingreso en la Continuaci\u00F3n Voluntaria ha terminado.";
	private static final String SOLICITUD = "solicitud";
	private static final String TRAMITES = "tramites";
	private static final String SOLICITANTE = "solicitante";
	private static final String TRAMITE_SEGURO = "tramiteSeguro";
	private static final String DATOSCALCULO = "datosCalculo";
	private static final String DATOSCOTIZACION = "datosCotizacion";
	private static final String DOMICILIO_OTRA_UBICACION = "domicilioOtraUbicacion";
	private static final String ERROR = "error";
	private static final String ID_PERSONA_SOLICITANTE = "idPersonaSolicitante";
	private static final String MSG_ERROR = "msgError";
	private static final String SDIMIN = "sdiMin";
	private static final String CALLE = "calle";
	private static final String ID_PERSONA = "ID PERSONA";
	private static final String ERROR_1 = "Ocurri\u00F3 un error al intentar validar los datos del solicitante";
	private static final String WIZARD_ALTA = "wizardContinuacionVoluntariaAltaInit";
	private static final String WIZARD_CONFIRMAR_DATOS = "wizardSeguroCVROConfirmarDatos";
	private static final String BAJA = "baja";
 	private static final String HOY = "hoy";
 	private static final String COMUN_ERROR = "error";
 	private static final String KEY_MSGERROR = "msgError";
 	private static final String OPCION_RETROACTIVIDAD = "seleccionRetroactividad";
 	
 	private static final String USUARIO_MODALIDAD = "MODALIDAD40";
 	private static final String DERECHO_RETROACTIVIDAD = "derechoRetroactividad";
 	private static final String MSG_RETROACTIVIDAD_EN_PROCESO = "Su tr\u00F3mite se encuentra en proceso. Agradecemos su paciencia y le invitamos a consultar nuevamente m\u00E1s tarde.";
 	 

	@Autowired
	@Qualifier("wSConsultaMod40")
	private WSConsultaMod40 wSConsultaMod40;

	@Autowired
	@Qualifier("webServiceConsultaSeguroCvro")
	private WebServiceTemplate webServiceConsultaSeguroCvro;

        @Autowired
        @Qualifier("domicilioExternosServiceBusiness")
        private DomicilioServiceBussinessExternosRemote domicilioExternosServiceBusiness;

	@Autowired
	@Qualifier("webServiceValidaPersonaContVoluntaria")
	private WebServiceTemplate webServiceValidaPersonaContVoluntaria;

	@Autowired
	@Qualifier("webServiceValidaCompraPersonaContVoluntaria")
	private WebServiceTemplate webServiceValidaCompraPersonaContVoluntaria;


	@Autowired
	@Qualifier("webServiceCotizaCompraPersonaContVoluntaria")
	private WebServiceTemplate webServiceCotizaCompraPersonaContVoluntaria;

	@Autowired
	@Qualifier("webServiceSolicitudSeguroIvro")
	private WebServiceTemplate webServiceSolicitudSeguroIvro;

	@Autowired
	@Qualifier("webServiceObtenerSalarioMinimoDfPorFecha")
	private WebServiceTemplate webServiceObtenerSalarioMinimoDfPorFecha;
	
	@Autowired
	@Qualifier("webServiceValidaPersonaContVoluntariaRenova")
	private WebServiceTemplate webServiceValidaPersonaContVoluntariaRenova;

	@Autowired
	private SeguroIndividualServices seguroIndividualServices;

	@Autowired
	private SeguroCvroUtil seguroCvroUtil;

        @Autowired
        @Qualifier("solicitudServiciosExpuestos")
        private SolicitudServiciosExpuestosRemote solicitudServiciosExpuestos;

        @Autowired
        @Qualifier("domicilioServiceBusiness")
        private DomicilioServiceBusinessRemote domicilioServiceBusiness;
        
        @Autowired
        @Qualifier("validaVigenciaBusinesss")
        private ValidaVigenciaRemote validaVigenciaBusinesss;

        @Autowired
        @Qualifier("seguroIvroServiceBusiness")
        private SeguroIvroServiceRemote seguroIvroServiceRemote;
        
        @Autowired
        @Qualifier("personaBusiness")
        private PersonaBusinessRemote personaBusiness;

	@Autowired
	@Qualifier("compraServiceBusiness")
	CompraServiceRemote compraServiceRemote;
	
	@Autowired
    @Qualifier("retroActividadServiceBusiness")
	RetroactividadServiceRemote retroactividadServiceRemote;

	@ModelAttribute("tramiteSeguro")
	public TramiteSeguroIvroMod40 getTramiteSeguro(){
		return new TramiteSeguroIvroMod40();
	}

	@ModelAttribute("tramites")
	public List<TramiteSeguroIvroMod40> getTramites() {
		return new ArrayList<TramiteSeguroIvroMod40>();
	}

	@ModelAttribute("idPersonaSolicitante")
	public Long getIdPersonaSolicitante() {
		return 0L;
	}

	@ModelAttribute("solicitante")
	public Fisica getSolicitante() {
		return new Fisica();
	}

	@ModelAttribute("domicilioSeguro")
	public Domicilio getDomicilioSeguro() {
		return new Domicilio();
	}

	@ModelAttribute("domicilioOtraUbicacion")
	public Domicilio getDomicilioOtraUbicacion() {
		return new Domicilio();
	}

	@ModelAttribute("datosCalculo")
	public DatosCalculoCuota getDatosCalculo() {
		return new DatosCalculoCuota();
	}

	@ModelAttribute("datosCotizacion")
	public DatosCalculoCuota getDatosCotizacion() {
		return new DatosCalculoCuota();
	}

	@ModelAttribute("solicitud")
	public Solicitud getSolicitud() {
		return new Solicitud();
	}

	@ModelAttribute("correoCVRO")
	public String getCorreoCVRO() {
		return new String();
	}
	
	@RequestMapping(value = "/validarTramiteRecompraExpresa/{idPersona}/{correo}/", method = RequestMethod.GET)
	public @ResponseBody Map<String, ? extends Object>  validarRecompraSolExpresa(Model model, HttpSession session,
			@PathVariable Long idPersona,@PathVariable String correo){
		
		log.info("Entrando a recompra expresa");
		
		String solicitaInsc ="El periodo para solicitar la inscripci\u00F3n en la continuaci\u00F3n voluntaria ha terminado, \u00E9sta debi\u00F3 solicitarse dentro del plazo de cinco a\u00F1os a partir de la fecha de baja.";

		this.comunLimpiarDatos(model, session);
		
		this.comunLimpiarDatos(model, session);

		Map<String, Object> result = new HashMap<String, Object>();
		session.setAttribute(KEY_TERMINANDO_SOLICITUD, false);
		result.put("idPersona", idPersona);
		result.put(ERROR, false);

		this.log.info("idPersona: " + idPersona + "CorreoMod40:" + correo);
                if(correo.contains("\u0040")){
                    model.addAttribute("correoCVRO", correo);
                }

		Persona persona = new Persona();
		persona.setIdPersona(idPersona);
		AsignacionNssIvro asignacionNss;
		try{
			asignacionNss = seguroIndividualServices.obtenerAsignacionNss(idPersona);

			if (asignacionNss.getFecRegistroBaja()!=null) {
				this.log.info("El idPersona es: "+idPersona+" -- Contiene Fecha de Baja: FechaDeBaja es:<"
						+ asignacionNss.getFecRegistroBaja() + ">");
				result.put(ERROR, true);
				result.put(MSG_ERROR, MSG_CON_FECHA_BAJA_NSS);
				return result;
			}
		}
		catch(Exception ex){
			this.log.info("El idPersona es: "+idPersona+" -- Excepcion controlada: " + ex.getLocalizedMessage()
					+ ", no se tiene FECHA DE BAJA",ex);
			result.put(ERROR, true);
			result.put(MSG_ERROR, MSG_SIN_FECHA_BAJA);
			return result;
		}


		String xmlValidacion = seguroCvroUtil.generarXMLValidacionCorreoElectronico(idPersona);
		log.debug("********** XML PARA VALIDACION\n" + xmlValidacion);

		try {
			/*
			 * Se consume primero el WS que consulta los seguros modalidad 40 de
			 * la persona que solicita, para poder mostrar el detalle del seguro
			 * o, en su defecto, continuar con las validaciones de acceso
			 */
			SegurosIvro seguros = callWebService(webServiceConsultaSeguroCvro, persona, SegurosIvro.class);
			/**Se consume WSConsultaMod40 para saber si su ultimo movimiento
			 * proviende de un 02 del R.O.
			 * */
			SeguroIvro seguro = null;

			if(seguros != null && seguros.getSeguroIvro() != null && seguros.getSeguroIvro().length > 0){
				/*
				 * Se toma el primer elemento ya que para este caso solo se
				 * deberia tiene un seguro asociado a la persona
				 */
				seguro = seguros.getSeguroIvro()[0];
				

				//************* Validar LC antes de detalle *********

                boolean lineasGeneradas = true;
                log.info("############################Iniciando validacion lineasGeneradas");

                	Long idCompra = seguro.getCompra().getIdCompra();
                	log.info("############################Se obtiene seguro: " + idCompra);
                	
                	Date fechaActual = limpiarHora(new Date());
                	
                	try {
                    	log.info("############################ compraServiceRemote: " + compraServiceRemote);
                		Compra compra = compraServiceRemote.findCompraById(idCompra);
                		log.info("############################Se obtiene compra: " + compra);
                		Pago[] pagos = compra.getPagos();

                		for(Pago pago: pagos) {
                			
                			log.info("############################Se obtiene pago NuevaVer: " + pago.getLineaCaptura());
                			if(pago.getLineaCaptura() == null || pago.getLineaCaptura().isEmpty()) {
                				
                				Date fechaLimitePago = limpiarHora(pago.getFechaLimitePago());
                				
                				
                				SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                				
                				
                				log.info("fecha actual: "+dateFormat.format(fechaActual));
                				log.info("fechaLimitePago: "+dateFormat.format(fechaLimitePago));
                				
                				if(fechaActual.after(fechaLimitePago)) {
                					log.info("Fecha limite de pago es mayor a la fecha actual, no se genera");
                					continue;
                				}else {
                					lineasGeneradas = false;
                					break;
                				}
                			}
                		}
                		
                		

                		if (!lineasGeneradas) {
                			log.info("############################Se encontraron LC en nulo");
                			result.put("idSeguro", seguro.getCveIdSeguroIvro());
                			result.put("lineasGeneradas", false);
                			result.put("msgError", "El detalle de sus seguros se est\u00e1 procesando. Se recomienda cerrar las ventanas e ingresar nuevamente para verificar sus seguros");
                			return result;
                		}

                	} catch (SUAException e) {
                		log.error("############################Ocurrio un error al validar las LC: " + e);
                		e.printStackTrace();
                        result.put(COMUN_ERROR, true);
                        result.put(KEY_MSGERROR, e.getMessage());
                        return result;
                	}
			}
			log.info("Se consulta compra para cveAsignacionNSS: "+asignacionNss.getCveIdAsignacionNss());
			
			List<PatronPlataformasDigitales> patronesPD = validaVigenciaBusinesss.consultaPatronesPlataformasDigitales();
			log.info("seguro1:"+ seguro);
			/*boolean compraDirecta
					= seguroCvroUtil.esCompraDirectaPlataformasDigitales(wSConsultaMod40.getConsultaMod40(String.valueOf(asignacionNss.getCveIdAsignacionNss())), seguro, patronesPD);
			log.info("seguro1:"+ seguro);
			log.info("compraDirecta2:    "+compraDirecta);*/
			/*
			 * Para esta funcion el valor de compradirecta es true
			 */
			boolean compraDirecta= true;
			



			if (seguro != null && 
					!(compraDirecta && 
							(seguro.getEstadoSeguro().getIdEstadoSeguro().equals(EstadoSeguroIvroEnum.BAJA_POR_MORA.getId()) ||
							seguro.getEstadoSeguro().getIdEstadoSeguro().equals(EstadoSeguroIvroEnum.VENCIDO.getId()) ||
							seguro.getEstadoSeguro().getIdEstadoSeguro().equals(EstadoSeguroIvroEnum.BAJA_POR_REINGRESO_RO.getId()) ||
							seguro.getEstadoSeguro().getIdEstadoSeguro().equals(EstadoSeguroIvroEnum.BAJA_A_SOLICITUD_ASEGURADO.getId())
							)
					 )
				) {

				Long estadoSeguro = seguro.getEstadoSeguro().getIdEstadoSeguro();
				boolean esSeguroInactivoAMostrar = 
						estadoSeguro.equals(EstadoSeguroIvroEnum.BAJA_POR_MORA.getId()) ||
						estadoSeguro.equals(EstadoSeguroIvroEnum.VENCIDO.getId()) ||
						estadoSeguro.equals(EstadoSeguroIvroEnum.BAJA_POR_REINGRESO_RO.getId())
				;
				
				if (esSeguroInactivoAMostrar) {
					seguro = seguroIndividualServices.getDetalleSeguro(seguro
							.getCveIdSeguroIvro());
					log.debug("El idPersona" + idPersona + " con Seguro en baja: " + seguro.getCveIdSeguroIvro());
					if (seguroCvroUtil.isRenovacionATiempo(seguro)) {
						result.put("tieneSeguro", Boolean.TRUE);
						result.put("idSeguro", seguro.getCveIdSeguroIvro());
					} else {
						String msgError = MSG_VALIDACION_EXPIRADO;
						this.log.error(msgError);
						result.put(ERROR, Boolean.TRUE);
						result.put(MSG_ERROR, msgError);
					}
				} else {
					this.log.debug("La persona [id=" + idPersona
							+ "] ya cuenta con un seguro modalidad 40 asociado, se procede a mostrar el detalle");
					result.put("tieneSeguro", Boolean.TRUE);
					result.put("idSeguro", seguro.getCveIdSeguroIvro());
				}
				
				result.put("idSeguroCifrado",CriptoUtilities.cifrar(String.valueOf(seguro.getCveIdSeguroIvro())));
				
				
			} else {
				seguroIndividualServices.getValidarCorreoPersona(persona);
				DatosCalculoCuota dcc = callWebService(
						webServiceValidaPersonaContVoluntaria, persona,
						DatosCalculoCuota.class);
                                //log.debug se cambia por un info
				this.log.info("Resultado de la validacion para dar acceso al tr\u00E1mite " + dcc);
				if (StringUtils.isNotBlank(dcc.getErrorFormGeneral())) {
					throw new IVROServiceException(dcc.getErrorFormGeneral());
				}
				//Se realiza validacion para saber si se cuenta con fecha de baja
				String fechaDeBaja;
				try {
					fechaDeBaja = dcc.getEmpleados()[0].getMovimientos()[0]
							.getFecha().toString();
					if (!StringUtils.isNotBlank(fechaDeBaja)) {
						this.log.info(" --- No contiene Fecha de baja: fechaDeBaja es:<"
								+ fechaDeBaja + ">");
						result.put(ERROR, true);
						result.put(MSG_ERROR, MSG_SIN_FECHA_BAJA);
						return result;
					}
				} catch (NullPointerException npe) {
					this.log.info(" -- Excepcion controlada: " + npe.getLocalizedMessage()
							+ ", no se tiene FECHA DE BAJA");
					result.put(ERROR, true);
					result.put(MSG_ERROR, MSG_SIN_FECHA_BAJA);
					return result;
				}
			}
			
			
			
			
        } catch (IVROServiceException e) {
        	log.error(e);
        	result.put(ERROR, true);
        	if(e.getMessage() != null && e.getMessage().equals(solicitaInsc)){
        		result.put(MSG_ERROR, MSG_VALIDACION_EXPIRADO);
        	}else{
        		result.put(MSG_ERROR, e.getMessage());
        	}

			return result;
        } catch (Exception e) {
        	String msgError = "Error inesperado al validar el acceso al tr\u00e1mite";

        	this.log.error(msgError, e);

        	result.put(ERROR, true);
			result.put(MSG_ERROR, msgError);

			return result;
		}

		return result;
	}

	@RequestMapping(value = "/validarAccesoTramite/{idPersona}/{correo}/", method = RequestMethod.GET)
	public @ResponseBody Map<String, ? extends Object>  validarAccesotramite(Model model, HttpSession session,
			@PathVariable Long idPersona,@PathVariable String correo){

		String solicitaInsc ="El periodo para solicitar la inscripci\u00F3n en la continuaci\u00F3n voluntaria ha terminado, \u00E9sta debi\u00F3 solicitarse dentro del plazo de cinco a\u00F1os a partir de la fecha de baja.";

		this.comunLimpiarDatos(model, session);

		Map<String, Object> result = new HashMap<String, Object>();
		session.setAttribute(KEY_TERMINANDO_SOLICITUD, false);
		result.put("idPersona", idPersona);
		result.put(ERROR, false);

		this.log.info("idPersona: " + idPersona + "CorreoMod40:" + correo);
                if(correo.contains("\u0040")){
                    model.addAttribute("correoCVRO", correo);
                }

		Persona persona = new Persona();
		persona.setIdPersona(idPersona);
		AsignacionNssIvro asignacionNss;
		try{
			asignacionNss = seguroIndividualServices.obtenerAsignacionNss(idPersona);

			if (asignacionNss.getFecRegistroBaja()!=null) {
				this.log.info("El idPersona es: "+idPersona+" -- Contiene Fecha de Baja: FechaDeBaja es:<"
						+ asignacionNss.getFecRegistroBaja() + ">");
				result.put(ERROR, true);
				result.put(MSG_ERROR, MSG_CON_FECHA_BAJA_NSS);
				return result;
			}
		}
		catch(Exception ex){
			this.log.info("El idPersona es: "+idPersona+" -- Excepcion controlada: " + ex.getLocalizedMessage()
					+ ", no se tiene FECHA DE BAJA",ex);
			result.put(ERROR, true);
			result.put(MSG_ERROR, MSG_SIN_FECHA_BAJA);
			return result;
		}


		String xmlValidacion = seguroCvroUtil.generarXMLValidacionCorreoElectronico(idPersona);
		log.debug("********** XML PARA VALIDACION\n" + xmlValidacion);

		try {
			/*
			 * Se consume primero el WS que consulta los seguros modalidad 40 de
			 * la persona que solicita, para poder mostrar el detalle del seguro
			 * o, en su defecto, continuar con las validaciones de acceso
			 */
			SegurosIvro seguros = callWebService(webServiceConsultaSeguroCvro, persona, SegurosIvro.class);
			/**Se consume WSConsultaMod40 para saber si su ultimo movimiento
			 * proviende de un 02 del R.O.
			 * */
			SeguroIvro seguro = null;
			Fisica titular = seguro != null ? seguro.getTitular() : null;

			if(seguros != null && seguros.getSeguroIvro() != null && seguros.getSeguroIvro().length > 0){
				/*
				 * Se toma el primer elemento ya que para este caso solo se
				 * deberia tiene un seguro asociado a la persona
				 */
				seguro = seguros.getSeguroIvro()[0];

				//************* Validar LC antes de detalle *********

				boolean lineasGeneradas = true;
                log.info("############################Iniciando validacion lineasGeneradas");

                	Long idCompra = seguro.getCompra().getIdCompra();
                	log.info("############################Se obtiene seguro: " + idCompra);
                	
                	Date fechaActual = limpiarHora(new Date());
                	
                	try {
                    	log.info("############################ compraServiceRemote: " + compraServiceRemote);
                		Compra compra = compraServiceRemote.findCompraById(idCompra);
                		log.info("############################Se obtiene compra: " + compra);
                		Pago[] pagos = compra.getPagos();

                		for(Pago pago: pagos) {
                			
                			log.info("############################Se obtiene pago NuevaVer: " + pago.getLineaCaptura());
                			if(pago.getLineaCaptura() == null || pago.getLineaCaptura().isEmpty()) {
                				
                				Date fechaLimitePago = limpiarHora(pago.getFechaLimitePago());
                				
                				
                				SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                				
                				
                				log.info("fecha actual: "+dateFormat.format(fechaActual));
                				log.info("fechaLimitePago: "+dateFormat.format(fechaLimitePago));
                				
                				if(fechaActual.after(fechaLimitePago)) {
                					log.info("La fecha actual no puede ser mayor a la fecha limite de pago, no se genera");
                					continue;
                				}else {
                					lineasGeneradas = false;
                					break;
                				}
                			}
                		}
                		
                		

                		if (!lineasGeneradas) {
                			log.info("############################Se encontraron LC en nulo");
                			result.put("idSeguro", seguro.getCveIdSeguroIvro());
                			result.put("idSeguroCifrado", CriptoUtilities.cifrar(String.valueOf(seguro.getCveIdSeguroIvro())));
                			result.put("lineasGeneradas", false);
                			result.put("msgError", "El detalle de sus seguros se est\u00e1 procesando. Se recomienda cerrar las ventanas e ingresar nuevamente para verificar sus seguros");
                			return result;
                		}

                	} catch (SUAException e) {
                		log.error("############################Ocurrio un error al validar las LC: " + e);
                		e.printStackTrace();
                        result.put(COMUN_ERROR, true);
                        result.put(KEY_MSGERROR, e.getMessage());
                        return result;
                	}
			}
			
			if(titular==null) {
				log.info("Caso de ventanilla, no se tiene un seguro previo");
				mx.gob.imss.ctirss.delta.model.gestion.individuo.Fisica titutarSinSeguro = personaBusiness.getPersonaFisica(idPersona);
				titular =  seguroCvroUtil.convertirFisica(titutarSinSeguro);
				
				log.info("titular:"+titular==null?"error, titular nulo":titular.getCurp());
			}
			log.info("Se consulta compra para cveAsignacionNSS: "+asignacionNss.getCveIdAsignacionNss());
			
			List<PatronPlataformasDigitales> patronesPD = validaVigenciaBusinesss.consultaPatronesPlataformasDigitales();
			
			//boolean compraDirecta = seguroCvroUtil.esCompraDirectaPlataformasDigitales(wSConsultaMod40.getConsultaMod40(String.valueOf(asignacionNss.getCveIdAsignacionNss())), seguro, patronesPD);
			RespuestaModalidad40 responseMod40 = wSConsultaMod40.getConsultaMod40(String.valueOf(asignacionNss.getCveIdAsignacionNss()));
			
			boolean compraDirecta = seguroCvroUtil.esCompraDirectaPlataformasDigitales(responseMod40, seguro, patronesPD);
			
			log.info("compraDirecta:    "+compraDirecta);
			
			//Serviocio 1 
			Boolean aplicaRetro = true;
			
			// Guarda el ultimo trabajo consultado en BDTUT_ULTIMO_TRABAJO. 
			log.info("***************************************************************************************************************************************************************************************************************");
			log.info("*****titular.nss = "+titular.getNss());
			
			
			Modalidad40VO modalidad40 = responseMod40 != null ? responseMod40.getModalidad40() : null;
			
			session.setAttribute(DERECHO_RETROACTIVIDAD, true);
			
			//UltimoTrabajoModalidad40DTO bdtutUltimoTrabajo = seguroIvroServiceRemote
			//		.getUltimoTrabajoPorNss(titular.getNss());
			GeneracionMultilineaConsultaResponse generacionMultilinea = retroactividadServiceRemote.consultaMultilineaRetroactividad(titular.getNss());
 
			boolean muestraDetalle=false;
			
			if (generacionMultilinea.getVrDto() != null && generacionMultilinea.getVrDto().getIdSolicitud()!=null) {
				if (generacionMultilinea.getVrDto().getEstado().equals("EN_PROCESO")) {
					log.warn("La multilinea esta en proceso");
					result.put(ERROR, true);
					result.put(MSG_ERROR, MSG_RETROACTIVIDAD_EN_PROCESO);
					return result;
				} else {
					if (generacionMultilinea.getVrDto().getResultado() != null && generacionMultilinea.getVrDto().getMultilinea() != null) {
						log.warn("Se genero la multilinea, se mostrara detalle");
						muestraDetalle= true;
					}
				}
 
			}
			
			
				
				
				if (modalidad40 != null&&muestraDetalle==false) {
					
					log.info("compraDirecta:    "+compraDirecta);
					
					String nssConsulta = titular.getNss();
					if (StringUtils.isBlank(nssConsulta) && titular != null) {
						nssConsulta = titular.getNss();
					}
					if (StringUtils.isNotBlank(nssConsulta)
							&& modalidad40.getModUltimoObligatorio() != null
							&& StringUtils.isNotBlank(modalidad40.getModUltimoObligatorio().getValue())
							&& modalidad40.getFecMovObligatorio() != null
							&& modalidad40.getSalarioObligatorio() != null) {
						try {
							
							Integer modalidad = Integer.valueOf(modalidad40.getModUltimoMov());
							
							UltimoTrabajoModalidad40DTO ultimoTrabajo = new UltimoTrabajoModalidad40DTO();
							ultimoTrabajo.setCveCurp(titular != null ? titular.getCurp() : null);
							ultimoTrabajo.setCveModalidad(modalidad);
							ultimoTrabajo.setCveNss(nssConsulta);
							ultimoTrabajo.setCveRfcAsegurado(titular != null ? titular.getRfc() : null);
							ultimoTrabajo.setNomAsegurado(titular != null ? titular.getNombre() + " " + titular.getPrimerApellido() + " " + titular.getSegundoApellido() : null);
							ultimoTrabajo.setRefRegistroPatronal(null);
							ultimoTrabajo.setTipoMovObligatorio(modalidad40.getTipoMovObligatorio() != null
									? modalidad40.getTipoMovObligatorio().getValue() : null);
							ultimoTrabajo.setFechaUltimoTrabajo(modalidad40.getFecMovObligatorio().getValue());
							ultimoTrabajo.setSalarioUltimoTrabajo(modalidad40.getSalarioObligatorio().getValue());
							ultimoTrabajo.setSemanasCotizadas(modalidad40.getSemanasCotizadas());
							ultimoTrabajo.setIndPension(modalidad40.getIndPension());
							ultimoTrabajo.setIndTrabajadorImss(modalidad40.getIndTrabajadorIMSS());
							seguroIvroServiceRemote.guardarHistorialUltimoSeguroModalidad40(ultimoTrabajo);
							
							//
							seguroIvroServiceRemote.actualizarHistorialUltimoSeguroModalidad40(titular.getNss(), "09", "011");
							
							UltimoTrabajoModalidad40DTO ultimoTrabajoTemp = seguroIvroServiceRemote.getUltimoTrabajoPorNss(titular.getNss());
							
							log.info("ultimoTrabajoTemp: "+ultimoTrabajoTemp.toString());
							
							//session.setAttribute(KEY_NSS_HISTORIAL_MOD40, nssConsulta);
						} catch (Exception e) {
							log.error("No se pudo registrar la consulta del ultimo trabajo", e);
						}
					} else {
						log.warn("La consulta Mod40 no contiene NSS o datos del ultimo trabajo obligatorio");
					}
				
			}
//Termina el guardado

			if (seguro != null && 
					!(compraDirecta && 
							(seguro.getEstadoSeguro().getIdEstadoSeguro().equals(EstadoSeguroIvroEnum.BAJA_POR_MORA.getId()) ||
							seguro.getEstadoSeguro().getIdEstadoSeguro().equals(EstadoSeguroIvroEnum.VENCIDO.getId()) ||
							seguro.getEstadoSeguro().getIdEstadoSeguro().equals(EstadoSeguroIvroEnum.BAJA_POR_REINGRESO_RO.getId())
							)
					 )
				) {

				Long estadoSeguro = seguro.getEstadoSeguro().getIdEstadoSeguro();
				boolean esSeguroInactivoAMostrar = 
						estadoSeguro.equals(EstadoSeguroIvroEnum.BAJA_POR_MORA.getId()) ||
						estadoSeguro.equals(EstadoSeguroIvroEnum.VENCIDO.getId()) ||
						estadoSeguro.equals(EstadoSeguroIvroEnum.BAJA_POR_REINGRESO_RO.getId())
				;
				
				log.info("esSeguroInactivoAMostrar: "+esSeguroInactivoAMostrar);
				
				if((seguro.getEstadoSeguro().getIdEstadoSeguro().equals(EstadoSeguroIvroEnum.BAJA_POR_REINGRESO_RO.getId()))){

					Fisica personaRO = new Fisica();
					personaRO.setIdPersona(persona.getIdPersona());
					boolean validaRO = this.verificarVigenciaParaRO(personaRO);
					//validaRO == true => sigue en baja por RO y se muestra el detalle
					if(validaRO) {
						log.info("Se mostrara el detalle");
					}else {
						//validaRO == false => ya no est? en RO y se muestra compra inicial
						log.info("Se mostrara el detalle");
						result.put("tieneSeguro", Boolean.FALSE);
						return result;
					}
				}
				
				if (esSeguroInactivoAMostrar) {
					seguro = seguroIndividualServices.getDetalleSeguro(seguro
							.getCveIdSeguroIvro());
					log.debug("El idPersona" + idPersona + " con Seguro en baja: " + seguro.getCveIdSeguroIvro());
					if (seguroCvroUtil.isRenovacionATiempo(seguro)) {
						result.put("tieneSeguro", Boolean.TRUE);
						//result.put("idSeguro", seguro.getCveIdSeguroIvro());
						result.put("idSeguro",CriptoUtilities.cifrar(String.valueOf(seguro.getCveIdSeguroIvro())));
					} else {
						String msgError = MSG_VALIDACION_EXPIRADO;
						this.log.error(msgError);
						result.put(ERROR, Boolean.TRUE);
						result.put(MSG_ERROR, msgError);
					}
				} else {
					this.log.debug("La persona [id=" + idPersona
							+ "] ya cuenta con un seguro modalidad 40 asociado, se procede a mostrar el detalle");
					result.put("tieneSeguro", Boolean.TRUE);
					//result.put("idSeguro", seguro.getCveIdSeguroIvro());
					result.put("idSeguro",CriptoUtilities.cifrar(String.valueOf(seguro.getCveIdSeguroIvro())));
				}
				
				result.put("idSeguroCifrado",CriptoUtilities.cifrar(String.valueOf(seguro.getCveIdSeguroIvro())));
				
			} else {
				
				seguroIndividualServices.getValidarCorreoPersona(persona);
				DatosCalculoCuota dcc = callWebService(
						webServiceValidaPersonaContVoluntaria, persona,
						DatosCalculoCuota.class);
                                //log.debug se cambia por un info
				this.log.info("Resultado de la validacion para dar acceso al tr\u00E1mite " + dcc);
				
				
				if (StringUtils.isNotBlank(dcc.getErrorFormGeneral())) {
					throw new IVROServiceException(dcc.getErrorFormGeneral());
				}
				
				//Validar con NORMATIVO
				if(dcc!=null && dcc.getModalidad()==0L) {
					log.info("CASO VIENE DE VENTANILLA");
				}else {
					//Se realiza validacion para saber si se cuenta con fecha de baja
					String fechaDeBaja;
					try {
						fechaDeBaja = dcc.getEmpleados()[0].getMovimientos()[0]
								.getFecha().toString();
						if (!StringUtils.isNotBlank(fechaDeBaja)) {
							this.log.info(" --- No contiene Fecha de baja: fechaDeBaja es:<"
									+ fechaDeBaja + ">");
							result.put(ERROR, true);
							result.put(MSG_ERROR, MSG_SIN_FECHA_BAJA);
							return result;
						}
					} catch (NullPointerException npe) {
						this.log.info(" -- Excepcion controlada: " + npe.getLocalizedMessage()
								+ ", no se tiene FECHA DE BAJA");
						result.put(ERROR, true);
						result.put(MSG_ERROR, MSG_SIN_FECHA_BAJA);
						return result;
					}
				}
			}
			
			
			
        } catch (IVROServiceException e) {
        	log.error(e);
        	result.put(ERROR, true);
        	if(e.getMessage() != null && e.getMessage().equals(solicitaInsc)){
        		result.put(MSG_ERROR, MSG_VALIDACION_EXPIRADO);
        	}else{
        		result.put(MSG_ERROR, e.getMessage());
        	}

			return result;
        } catch (Exception e) {
        	String msgError = "Error inesperado al validar el acceso al tr\u00e1mite";

        	this.log.error(msgError, e);

        	result.put(ERROR, true);
			result.put(MSG_ERROR, msgError);

			return result;
		}

		return result;
	}

	@RequestMapping(value="/alta/init/{idPersona}/{nssCifrado}", method = RequestMethod.GET)
	public String altaInit(Model model, SessionStatus sessionStatus, HttpSession session,
			@PathVariable Long idPersona,
			@PathVariable String nssCifrado) {

		String nss = null;
		String numCifrado = "-1";
		String cifrado;
		String calculoError = "";

		if(!nssCifrado.equals(numCifrado)){
			Map<String, Object> result = descifrarNss(session,null, nssCifrado);
			nss = (String)result.get(KEY_NSS_MOD40);
			cifrado = (String)result.get(KEY_NSS_CIFRADO_MOD40);
		}else{
			cifrado=null;
		}

		Fisica persona = new Fisica();
		persona.setIdPersona(idPersona);

		TipoPersona tipoPersona = new TipoPersona();
		tipoPersona.setIdTipoPersona(TipoPersona.TIPO_PERSONA_FISICA);
		persona.setTipoPersona(tipoPersona);
		persona.setNssCifrado(cifrado);
		persona.setNss(nss);

		TramiteSeguroIvroMod40 tramiteSeguro = new TramiteSeguroIvroMod40();
		tramiteSeguro.setSolicitante(persona);
		List<TramiteSeguroIvroMod40> tramites  = new ArrayList<TramiteSeguroIvroMod40>();
		tramites.add(tramiteSeguro);

		model.addAttribute(TRAMITE_SEGURO, tramiteSeguro);
		model.addAttribute(TRAMITES, tramites);
		model.addAttribute(ID_PERSONA_SOLICITANTE, idPersona);
		model.addAttribute(SOLICITANTE, persona);

		this.log.info(ID_PERSONA + persona.getIdPersona() + " con el NSS: " + persona.getNss());


		try {
			DatosCalculoCuota calculo = callWebService(webServiceValidaPersonaContVoluntaria, persona, DatosCalculoCuota.class);
			model.addAttribute(DATOSCALCULO, calculo);

			if(!calculo.getErrorFormGeneral().equals(calculoError)){
				this.log.info(" -- MENSAJE DEL WEBSERVICE: "+calculo.getErrorFormGeneral());
				model.addAttribute(ERROR, calculo.getErrorFormGeneral());
			}
		} catch (Exception e) {
			model.addAttribute(ERROR, ERROR_1);
			log.error(ERROR_1, e);
		}

		//return WIZARD_ALTA;
		System.out.println("Paso por opcion_retroactividad en altaInit");
		System.out.println("permiso Retroactividad");
		ValidaRetroactividadRequest requestServicioRetro = new ValidaRetroactividadRequest();
		requestServicioRetro.setNss(nss);
		requestServicioRetro.setUsuario(USUARIO_MODALIDAD);
		ValidaRetroactividadResponse response = retroactividadServiceRemote.obtenerInfoInicialRetroactividad(requestServicioRetro);
		System.out.println(response);

		if(response!=null && !response.getCodigo().equals("200")) {
			model.addAttribute(ERROR, response.getDescripcion());
			log.error(ERROR_1+" "+response.getDescripcion() );
		}
		
		if(response.getVrDto().getAplicaRenovacion()) {
			
			//Datos requeridos para renovacion
			// ============================================================
						// RENOVACION
						// ============================================================
			 
						System.out.println("Paso por opcion_renovacion en altaInit");
			 
						return "redirect:/wizard/continuacionVoluntaria"
								+ "/renovacion/alta/initServicio/"
								+ "2026-08-01" + "/"
								+ idPersona + "/"
								+ nssCifrado;
			

		}

		//return WIZARD_ALTA;
		System.out.println("Paso por opcion_retroactividad en altaInit");
		System.out.println(response);
		
		if(response.getVrDto().getAplicaRetroactividad()) {
			model.addAttribute("dtoResponse",response.getVrDto());
			return OPCION_RETROACTIVIDAD;
		}else {
			return WIZARD_ALTA;
		}		
		
	}


	@RequestMapping(value="/comunes/init/temporal/{idPersona}/{nssCifrado}", method = RequestMethod.GET)
	public String initTemporal(Model model, SessionStatus sessionStatus, HttpSession session,
			@PathVariable Long idPersona,
			@PathVariable String nssCifrado) {

		String nss = null;
		String numCifrado = "-1";
		String cifrado = "";
		String calculoError = "";

		if(!nssCifrado.equals(numCifrado)){
			Map<String, Object> result = descifrarNss(session,null, nssCifrado);
			nss = (String)result.get(KEY_NSS_MOD40);
			cifrado = (String)result.get(KEY_NSS_CIFRADO_MOD40);
		}else{
			cifrado=null;
		}

		Fisica persona = new Fisica();
		persona.setIdPersona(idPersona);

		TipoPersona tipoPersona = new TipoPersona();
		tipoPersona.setIdTipoPersona(TipoPersona.TIPO_PERSONA_FISICA);
		persona.setTipoPersona(tipoPersona);
		persona.setNssCifrado(cifrado);
		persona.setNss(nss);

		TramiteSeguroIvroMod40 tramiteSeguro = new TramiteSeguroIvroMod40();
		tramiteSeguro.setSolicitante(persona);
		List<TramiteSeguroIvroMod40> tramites  = new ArrayList<TramiteSeguroIvroMod40>();
		tramites.add(tramiteSeguro);

		model.addAttribute(TRAMITE_SEGURO, tramiteSeguro);
		model.addAttribute(TRAMITES, tramites);
		model.addAttribute(ID_PERSONA_SOLICITANTE, idPersona);
		model.addAttribute(SOLICITANTE, persona);

		this.log.info(ID_PERSONA+ persona.getIdPersona()+" con el NSS: " + persona.getNss());


		try {
			DatosCalculoCuota calculo = callWebService(webServiceValidaPersonaContVoluntaria, persona, DatosCalculoCuota.class);
			model.addAttribute(DATOSCALCULO, calculo);

			if(!calculo.getErrorFormGeneral().equals(calculoError)){
				this.log.error(" --- MENSAJE DEL WEBSERVICE: "+calculo.getErrorFormGeneral());
				model.addAttribute(ERROR, calculo.getErrorFormGeneral());
			}
		} catch (Exception e) {
			model.addAttribute(ERROR,ERROR_1);
			log.error(ERROR_1, e);
		}
		return WIZARD_ALTA;
	}

	@RequestMapping(value = "/comunes/otraUbicacion")
	public String comunOtraUbicacion(Model model) {

		model.addAttribute("domicilioAlterno", new mx.gob.imss.ctirss.delta.model.domicilio.Domicilio());

		return "wizardSeguroContinuacionVoluntariaComunAgregarDomicilio";
	}

	@RequestMapping(value = "/comunes/agregarDomicilioNueva", method = RequestMethod.POST)
	public String comunAgregarDomicilio(Model model,
			@ModelAttribute mx.gob.imss.ctirss.delta.model.domicilio.Domicilio domicilio) {

		domicilio.setCodigoPostal(domicilio.getAsentamiento().getCodigoPostal());
		domicilio.setLocalidad(domicilio.getAsentamiento().getLocalidad());
		domicilio.getVialidadPrimaria().setNombre(domicilio.getCalle());

		Domicilio domicilioOtraUbicacion = SeguroIvroUtil.convertirDomicilioAImssDigital(domicilio);

		domicilioOtraUbicacion.getAsentamiento().setMunicipio(
				domicilioOtraUbicacion.getAsentamiento().getLocalidad()
						.getMunicipio());
		domicilioOtraUbicacion.setColonia(domicilioOtraUbicacion.getAsentamiento()
				.getNombre());
		//Revisar que se pueda determinar de quien es el domicilo
		log.info("********* VALOR DEL DOMICILIO CON OTRA UBICACION en la Colonia: " + domicilioOtraUbicacion.getColonia()
                        + CALLE + domicilioOtraUbicacion.getCalle()
                        + " numero exterior: "+ domicilioOtraUbicacion.getNumExteriorAlf()
                        + " numero interior: "+domicilioOtraUbicacion.getNumInteriorAlf());

		model.addAttribute(DOMICILIO_OTRA_UBICACION, domicilioOtraUbicacion);

		return "wizardSeguroCVROAgregarDomicilio";
	}


	@RequestMapping(value="/comunes/agregarDomicilio")
	public String comunSolicitarDomicilio(Model model, HttpServletRequest request, HttpSession session,
			@ModelAttribute("solicitante") Fisica solicitante,
			@ModelAttribute("idPersonaSolicitante") Long idPersona,
			@ModelAttribute("datosCalculo") DatosCalculoCuota datosCalculo) {
		
		System.out.println("comunSolicitarDomicilio");

		Persona persona = new Persona();
		persona.setIdPersona(idPersona);

		String nombreSolicitante = request.getParameter("nombreSolicitante");
		if (nombreSolicitante!=null) {
			solicitante.setNombre(nombreSolicitante);
		}

		String mailSolicitante = request.getParameter("correoSolicitante");
		if(mailSolicitante!=null){
			CorreoElectronico correoElectronico = new CorreoElectronico();
			correoElectronico.setCorreo(mailSolicitante);
			solicitante.setCorreoElectronico(correoElectronico);
			this.log.info(" --- IdPersona: " + idPersona + " --- CORREO   : " + correoElectronico);
		}

		boolean isActualizado = false;
		
		try {
			Domicilio domicilio = domicilioExternosServiceBusiness.consultarUltimoDomicilioParticilar(idPersona);
			solicitante.setIdPersona(idPersona);
			solicitante.setDomicilioParticular(domicilio);

			//String nssHistorial = (String) session.getAttribute(KEY_NSS_HISTORIAL_MOD40);
			String nssHistorial = solicitante.getNss();
			if (StringUtils.isNotBlank(nssHistorial)) {
				try {
					AsignacionNssIvro asignacionNss = seguroIndividualServices.obtenerAsignacionNss(idPersona);
					Municipio municipio = domicilio != null && domicilio.getLocalidad() != null
							? domicilio.getLocalidad().getMunicipio() : null;
					if (asignacionNss != null && StringUtils.equals(nssHistorial, asignacionNss.getNumNss())
							&& municipio != null && municipio.getEntidadFederativa() != null
							&& StringUtils.isNotBlank(municipio.getClave())
							&& StringUtils.isNotBlank(municipio.getEntidadFederativa().getClave())) {
						isActualizado = seguroIvroServiceRemote.actualizarHistorialUltimoSeguroModalidad40(
								nssHistorial, municipio.getEntidadFederativa().getClave(), municipio.getClave());
					} else {
						log.warn("No se actualizo el ultimo trabajo: NSS o datos de municipio y entidad no disponibles");
					}
				} catch (Exception e) {
					log.error("No se pudo actualizar el ultimo trabajo con municipio y entidad", e);
				}
			}
			
			
		} catch (DomicilioNoLocalizadoException e) {
			model.addAttribute(ERROR, "Ocurri\u00F3 un error al intentar obtener el domicilio del solicitante.");
			log.error("********** Ocurrio un error al intentar obtener el domicilio del solicitante..", e);
		} catch (MunicipioImssNoLocalizadoException e) {
                    model.addAttribute(ERROR, "Ocurri\u00F3 un error al intentar obtener el domicilio del solicitante.");
                    log.error("********** Ocurrio un error al intentar obtener el domicilio del solicitante..", e);
            }
		
		model.addAttribute("historialUltimoTrabajoActualizado", isActualizado);
		model.addAttribute(DATOSCALCULO, datosCalculo);
		model.addAttribute(SOLICITANTE, solicitante);
		return "wizardSeguroCVROAgregarDomicilio";
	}


	@RequestMapping(value="/comunes/datosInscripcion", method = RequestMethod.POST)
	public String datosInscripcion(Model model, HttpSession session,
			@ModelAttribute("datosCalculo") DatosCalculoCuota datosCalculo,
			@ModelAttribute("tramiteSeguro") TramiteSeguroIvroMod40 tramiteSeguro,
			@ModelAttribute("domicilioOtraUbicacion") Domicilio domicilioOtraUbicacion,
			@ModelAttribute("domicilioSeguro") Domicilio domicilioSeguro,
			@ModelAttribute("solicitante") Fisica solicitante) {

		String valida ="";

		Domicilio domicilio;

                //Revisar que se pueda determinar de quien es el domicilo
		if (tramiteSeguro.getDomicilioSeguro().getIdDomicilio() != null) {
			domicilio = solicitante.getDomicilioParticular();
                        log.info("**** Se continua con domicilio original: "+" Colonia: "+domicilio.getColonia()
                                + CALLE + domicilio.getCalle()
                                + " Numero Exterior: " + domicilio.getNumExteriorAlf()
                                + " Numero Interior: " + domicilio.getNumInteriorAlf() + " ****");
		} else {
			domicilio = domicilioOtraUbicacion;
                        log.info("**** Se registro un domicilio nuevo en la Colonia: " + domicilio.getColonia()
                                + CALLE + domicilio.getCalle()
                                + " Numero exterior: " + domicilio.getNumExteriorAlf()
                                + " Numero interior: " + domicilio.getNumInteriorAlf());
		}

		solicitante.setDomicilioParticular(domicilio);
		BigDecimal sdiUltReg = BigDecimal.ZERO;
		String sdiUltRegFormat ="0.00";
		try {
			sdiUltReg = datosCalculo.getEmpleados()[0].getMovimientos()[0].getSalario();
		this.log.info(" --- Ultimo Salario Registrado:  " + sdiUltReg + " ---");
	    sdiUltRegFormat = seguroCvroUtil.formatCurrency(sdiUltReg);

		}catch(Exception ex){
			this.log.error(" --- Ultimo Salario registrado No Disponible: " + sdiUltReg + " ---",ex);
		}
		try {
			//WEB SERVICE QUE OBTIENE ELVALOR DE LA UMA VIGENTE DEL DF
			BigDecimal uma = this.seguroIndividualServices.obtenerUmaPorFecha(new Date());
			this.log.info(" --- UMA: " + uma + " ---");

			//WEB SERVICE QUE OBTIENE EL SALARIO MINIMO VIGENTE DEL DF
//			BigDecimal smv = this.seguroIndividualServices.obtenerSalarioMinimoVigenteDF("A");
//			this.log.info(" --- SMV: " + smv + " ---");

			DatosCalculoCuota validaCompra = callWebService(webServiceValidaCompraPersonaContVoluntaria, solicitante, DatosCalculoCuota.class);
			BigDecimal smv = validaCompra.getSalarioMinimo();
			this.log.info(" --- SMV: " + smv + " ---");
			BigDecimal salMin = smv;

			// DEBE SER EL SALARIO MINIMO ACORDE AL DF
			BigDecimal salMax = seguroCvroUtil.calcularSalaraioMaximo(uma);
			model.addAttribute("sdiMax", salMax);
			model.addAttribute(SDIMIN, salMin);

			this.log.info(" --- Ultimo Salario registrado antes de validar:  " + sdiUltReg + " ---");

			if(sdiUltReg!=null){
				model.addAttribute("sdiUltRegNum", sdiUltReg);
				int resultadoPrimeraCondicion;
				int resultadoSegundaCondicion;

				resultadoPrimeraCondicion = sdiUltReg.compareTo(salMin);
				resultadoSegundaCondicion = sdiUltReg.compareTo(salMax);

				if(resultadoPrimeraCondicion == 1 && resultadoSegundaCondicion == -1){
					//Si sdiUltReg > salMin y sdiUltReg < salMax
					model.addAttribute(SDIMIN, sdiUltReg);
				}else if(resultadoSegundaCondicion == 0){
					//Si sdiUltReg = salMax
					model.addAttribute(SDIMIN, salMax);
				}else if(resultadoSegundaCondicion == 1){
					//Si sdiUltReg > salMax
					model.addAttribute(SDIMIN, salMax);
				}else{
					model.addAttribute(SDIMIN, salMin);
				}
			} else {
				model.addAttribute("sdiUltRegNum", 0);
			}

			if(!validaCompra.getErrorFormGeneral().equals(valida)){
				this.log.info(" -- MENSAJE DEL WEBSERVICE: "+validaCompra.getErrorFormGeneral());
				model.addAttribute(ERROR, validaCompra.getErrorFormGeneral());
			}
			model.addAttribute(DATOSCOTIZACION, validaCompra);
		} catch (Exception e) {
			model.addAttribute(ERROR, ERROR_1);
			log.error(ERROR_1, e);
		}

		model.addAttribute("sdiUltReg", sdiUltRegFormat);
		model.addAttribute(SOLICITANTE, solicitante);
		model.addAttribute(DATOSCALCULO, datosCalculo);

		return "wizardSeguroCVROComunSolicitarDatosInscripcion";
	}

	@RequestMapping(value="/comunes/confirmarDatos", method = RequestMethod.POST)
	public String confirmarDatos(Model model, HttpServletRequest request, HttpSession session,
			@ModelAttribute("datosCalculo") DatosCalculoCuota datosCalculo,
			@ModelAttribute("datosCotizacion") DatosCalculoCuota datosCotizacion,
			@ModelAttribute("solicitante") Fisica solicitante,
			@ModelAttribute("tramiteSeguro") TramiteSeguroIvroMod40 tramiteSeguro) {

		String view=WIZARD_CONFIRMAR_DATOS;

		String tipoSolicitudAlta = request.getParameter(HOY);
		String tipoSolicitudBaja = request.getParameter(BAJA);
		String tipoSolicitutCVRO ="";

		boolean recargoPorFechaBaja = false;

		if(tipoSolicitudAlta!=null){
			if(tipoSolicitudAlta.equals(HOY)){
				this.log.info(" -- Tipo Solicitud es a la fecha de Solicitud: "+tipoSolicitudAlta);
				tipoSolicitutCVRO = tipoSolicitudAlta;
			}
		}else if(tipoSolicitudBaja!=null && tipoSolicitudBaja.equals(BAJA)){
			this.log.info(" -- Tipo Solicitud es a la fecha de Baja: "+tipoSolicitudBaja);
			tipoSolicitutCVRO = tipoSolicitudBaja;
		}

		String salarioCotizar = request.getParameter("salario");
		this.log.info(" -- Salario a cotizar: "+salarioCotizar);

		Date fechaBaja;
		BigDecimal ultSdi;
		String sdiUltRegFormat="";
		try{
			fechaBaja = datosCalculo.getEmpleados()[0].getMovimientos()[0].getFecha();
			ultSdi = datosCalculo.getEmpleados()[0].getMovimientos()[0].getSalario();
			sdiUltRegFormat = seguroCvroUtil.formatCurrency(ultSdi);
		}catch(Exception ex){
			log.info(ex);
			fechaBaja = null;
			ultSdi = null;
		}


		/*Solicitud del CVRO a la fecha de:*/
		Date fechaTramite = new Date();
		if(tipoSolicitutCVRO.equals(HOY)){
			this.log.info(" -- Tipo: "+tipoSolicitutCVRO);
			fechaTramite = new Date();
			this.log.info(" -- Solicitud es a la fecha de hoy: "+fechaTramite);
			recargoPorFechaBaja = false;
		}else{
			if(tipoSolicitutCVRO.equals(BAJA)){
				this.log.info(" -- Tipo: "+tipoSolicitutCVRO);
				this.log.info(" -- Solicitud es a la fecha de baja: "+fechaBaja);
				recargoPorFechaBaja = true;
				fechaTramite = fechaBaja;
			}
		}

		List<Fisica> integrantes = new ArrayList<Fisica>();
		integrantes.add(solicitante);

		try {
			Cotizacion cotizacionSolicitante = this.generarCotizacion(
					solicitante, integrantes, datosCotizacion, salarioCotizar, recargoPorFechaBaja, fechaTramite);

			this.log.info(" -- Cotizacion: "+cotizacionSolicitante.getIdCotizacion());
			this.log.info(" -- Concepto: "+cotizacionSolicitante.getConcepto());
			this.log.info(" -- Fecha Inicio: "+cotizacionSolicitante.getDetalle().getFechaInicioCalculo());
			this.log.info(" -- Fecha Fin: "+cotizacionSolicitante.getDetalle().getFechaFinCalculo());
			this.log.info(" -- Salario a cotizar: "+salarioCotizar);

			tramiteSeguro.setBeneficiarios(integrantes.toArray(new Fisica[0]));
			tramiteSeguro.setCotizacion(cotizacionSolicitante);

			EstadoTramite estadoTramite = new EstadoTramite();
			estadoTramite.setIdEstadoTramitePersona(EstadoTramiteEnum.INICIADO.getId());
			tramiteSeguro.setEstadoTramite(estadoTramite);

			Modalidad modalidad = new Modalidad();
			modalidad.setIdModalidad(datosCalculo.getModalidad());
			tramiteSeguro.setModalidad(modalidad);

			tramiteSeguro.setPersona(solicitante);

			log.info(ID_PERSONA + solicitante.getIdPersona());

			TipoTramite tipoTramite = new TipoTramite();
			tipoTramite.setIdTipoTramite(TipoTramiteEnum.COMPRA_CONTINUACION_VOLUNTARIA.getCodigo());
			tramiteSeguro.setTipoTramite(tipoTramite);

			Solicitud solicitud = new Solicitud();
			solicitud.setTramite(new TramiteSeguroIvroMod40[] {tramiteSeguro});
			solicitante.setIdPersona(solicitante.getIdPersona());

			OrigenSolicitud origenSolicitud = new OrigenSolicitud();
			origenSolicitud.setIdOrigenSolicitud(SeguroIvroUtil.getAmbiente(request));
			solicitud.setOrigenSolicitud(origenSolicitud);

			TipoSolicitud tipoSolicitud = new TipoSolicitud();
			tipoSolicitud.setIdTipoSolicitud(TipoSolicitudEnum.CONTINUACION_VOLUNTARIA_REGIMEN_OBLIGATORIO.getId());
			solicitud.setTipoSolicitud(tipoSolicitud);

			EstadoSolicitud estadoSolicitud = new EstadoSolicitud();
			estadoSolicitud.setIdEstadoSolicitud(EstadoSolicitudEnum.REGISTRADA.getId().intValue());
			solicitud.setEstadoSolicitud(estadoSolicitud);
			solicitud.setFechaRegistro(new Date());

			log.info(ID_PERSONA + ((TramiteSeguroIvroMod40)solicitud.getTramite()[0]).getPersona().getIdPersona());

			//Se agrega usuario
	        String strUsuario;
	        Long ambiente = SeguroIvroUtil.getAmbiente(request);

	        if(!OrigenSolicitudEnum.PORTAL_CIUDADANO.getId().equals(ambiente)) {
	        	UsuarioSSO sso = this.procesarUsuarioSSO(request);
	        	strUsuario = sso.getCurp();
	        } else {
	        	strUsuario = seguroIndividualServices.obtenerCurpPorIdPersona(solicitante.getIdPersona());
	        }
	        solicitud.setUsuario(strUsuario);

			Solicitud solicitudResultado = new Solicitud();
			try {
				solicitudResultado = callWebService(
						webServiceSolicitudSeguroIvro, solicitud,
						Solicitud.class, new Class[] { Solicitud.class,
								TramiteSeguroIvroMod40.class });
			} catch(Exception e) {
				model.addAttribute(ERROR, "Ocurri\u00F3 un error al intentar registrar la solicitud.");
				log.error("********** Ocurrio un error al intentar registrar la solicitud.", e);
				view = WIZARD_ALTA;
			}

			if(solicitudResultado.getErrorFormGeneral() != null && !solicitudResultado.getErrorFormGeneral().trim().isEmpty()) {
				model.addAttribute(ERROR, solicitudResultado.getErrorFormGeneral());
				view = WIZARD_ALTA;
			}

			solicitud.setIdSolicitud(solicitudResultado.getIdSolicitud());
			solicitud.setNumSolicitud(solicitudResultado.getNumSolicitud());
			solicitud.getTramite()[0].setTramiteId(solicitudResultado.getTramite()[0].getTramiteId());

			log.info("********** ID SOLICITUD: " + solicitud.getIdSolicitud() + " **********");

			seguroCvroUtil.generarCadenaOriginalyFirma(solicitud, solicitante, session);

			model.addAttribute("periodos", cotizacionSolicitante.getDetalle().getEmpleados()[0].getPeriodos());
			model.addAttribute(SOLICITUD, solicitud);

			model.addAttribute(SOLICITANTE, solicitante);
			model.addAttribute("fechaSolicitud", solicitud.getFechaRegistro());

			model.addAttribute("fechaBaja", fechaBaja);
			model.addAttribute("ultSdi",sdiUltRegFormat);

			model.addAttribute("sbc", salarioCotizar);
			session.setAttribute("mostrarMensajeExito", true);
			session.setAttribute(SOLICITUD, solicitud);
			return WIZARD_CONFIRMAR_DATOS;

		} catch (IVROServiceException e) {
			log.error("********** Ocurrio un error al intentar generar la cotizacion.", e);
			model.addAttribute(ERROR, e.getMessage());
			view = WIZARD_ALTA;
		}
		if(view.equals(WIZARD_CONFIRMAR_DATOS)){
			session.setAttribute("mostrarMensajeExito", true);
		}

		return view;
	}


	@RequestMapping(value="/comunes/cancelarSolicitud")
	public @ResponseBody Map<String, ? extends Object> comunCancelarSolicitud(Model model,
			@ModelAttribute("solicitud") Solicitud solicitud) {

		EstadoTramite estadoTramite = new EstadoTramite();
		estadoTramite.setIdEstadoTramitePersona(EstadoTramiteEnum.CERRADO.getId());
		solicitud.getTramite()[0].setEstadoTramite(estadoTramite);

		EstadoSolicitud estadoSolicitud = new EstadoSolicitud();
		estadoSolicitud.setIdEstadoSolicitud(EstadoSolicitudEnum.CANCELADA.getId().intValue());
		solicitud.setEstadoSolicitud(estadoSolicitud);

		Solicitud solicitudResultado = new Solicitud();

		try {
			solicitudResultado = callWebService(webServiceSolicitudSeguroIvro, solicitud, Solicitud.class);
		} catch(Exception e) {
			solicitud.setErrorFormGeneral("Ocurri\u00F3 un error al intentar cancelar la solicitud.");
			log.error("********** Ocurrio un error al intentar cancelar la solicitud.", e);
		}

		log.info("********** SOLICITUD: " + solicitudResultado.getIdSolicitud() + " CANCELADA **********");
		model.addAttribute(SOLICITUD, solicitud);

		return null;
	}

	@RequestMapping(value="/comunes/impresionDocumentos", method = RequestMethod.POST)
	public String impresionDocumentos(Model model, HttpSession session) {

		return "wizardSeguroCVROImpresionDocumentos";
	}

	@RequestMapping(value="/comunes/limpiarDatos")
	public @ResponseBody Map<String, ? extends Object> comunLimpiarDatos(Model model, HttpSession session) {

		model.addAttribute(TRAMITE_SEGURO, getTramiteSeguro());
		model.addAttribute(SOLICITANTE, getSolicitante());
		model.addAttribute(ID_PERSONA_SOLICITANTE, getIdPersonaSolicitante());
		model.addAttribute(TRAMITES, getTramites());
		model.addAttribute(DATOSCALCULO, getDatosCalculo());
		model.addAttribute("domicilioSeguro", getDomicilioSeguro());
		model.addAttribute(DOMICILIO_OTRA_UBICACION, getDomicilioSeguro());
		model.addAttribute(DATOSCOTIZACION, getDatosCotizacion());
		model.addAttribute(SOLICITUD, getSolicitud());


		session.removeAttribute(TRAMITE_SEGURO);
		session.removeAttribute(SOLICITANTE);
		session.removeAttribute(ID_PERSONA_SOLICITANTE);
		session.removeAttribute(TRAMITES);
		session.removeAttribute(DATOSCALCULO);
		session.removeAttribute("domicilioSeguro");
		session.removeAttribute(DOMICILIO_OTRA_UBICACION);
		session.removeAttribute(DATOSCOTIZACION);
		session.removeAttribute(SOLICITUD);
		session.removeAttribute(KEY_TERMINANDO_SOLICITUD);

		this.log.info(" --- SE LIMPIARON LOS DATOS DE LA SESION ---");
		return null;
	}


	private Map<String, Object> descifrarNss(HttpSession session,
			String valorRetornoErrorNssCifrado, String nssCifrado){
		Map<String, Object> result = new HashMap<String, Object>();
		String nss = null;
		String cifrado = null;
		String   cifradoFinal  ="-1";
		try {
			nss = Base64Cipher.descrifrar(nssCifrado);
		} catch (InvalidKeyException e1) {
			cifrado = valorRetornoErrorNssCifrado;
			this.log.error(e1);
		} catch (IllegalBlockSizeException e1) {
			cifrado = (String)session.getAttribute(KEY_NSS_CIFRADO_FINAL);
			if(StringUtils.isNotEmpty(nssCifrado) && StringUtils.isNotBlank(nssCifrado)){
				try {
					nss = Base64Cipher.descrifrar(nssCifrado);
				} catch (Exception e) {
					cifrado = valorRetornoErrorNssCifrado;
					this.log.error(e);
				}
			}else{
				cifrado = valorRetornoErrorNssCifrado;
				this.log.error(e1);
			}
		} catch (BadPaddingException e1) {
			cifrado = valorRetornoErrorNssCifrado;
			this.log.error(e1);
		} catch (IOException e1) {
			cifrado = valorRetornoErrorNssCifrado;
			this.log.error(e1);
		}

		if(StringUtils.isNotEmpty(cifrado)
			&& StringUtils.isNotBlank(cifrado) && !cifrado.equals(cifradoFinal)){
				session.setAttribute(KEY_NSS_CIFRADO_FINAL, cifrado);
		}else{
			session.removeAttribute(KEY_NSS_CIFRADO_FINAL);
		}

		result.put(KEY_NSS_MOD40, nss);
		result.put(KEY_NSS_CIFRADO_MOD40, cifrado);

		return result;
	}



	private Cotizacion generarCotizacion(Fisica solicitante, List<Fisica> integrantes,
			DatosCalculoCuota datosCalculo, String sdi, boolean recargoPorFechaBaja, Date fechaTramite) throws IVROServiceException {

		String sueldoS = sdi;
		BigDecimal sueldoDiarioTrabajador = new BigDecimal(StringUtils.isBlank(sueldoS) ? "0" : sueldoS);

		DatosCalculoCuota dcc = new DatosCalculoCuota();
		Calendar calendarTemporal = Calendar.getInstance();
		calendarTemporal.setTime(fechaTramite);
		calendarTemporal.add(Calendar.DATE, 1);
		dcc.setRecargos(datosCalculo.getRecargos());
		if(recargoPorFechaBaja){
			dcc.setFechaInicioCalculo(calendarTemporal);
			dcc.setAplicaRecargoPorFechaBaja(true);
			dcc.setRecargos(true);
			this.log.info(" -- Aplica recargos por Fecha de Baja: " + dcc.getAplicaRecargoPorFechaBaja());
			this.log.info(" -- Fecha de BAJA para el calculo: " + dcc.getFechaInicioCalculo());
		}else{
			dcc.setFechaInicioCalculo(datosCalculo.getFechaInicioCalculo());
			dcc.setAplicaRecargoPorFechaBaja(false);
			this.log.info(" -- Aplica recargos por fecha de Baja: " + dcc.getAplicaRecargoPorFechaBaja());
			this.log.info(" -- Fecha de SOLICITUD para el calculo: " + dcc.getFechaInicioCalculo());
		}
		dcc.setFechaFinCalculo(datosCalculo.getFechaFinCalculo());
		dcc.setNumeroRegistroPatronal(datosCalculo.getNumeroRegistroPatronal());
		dcc.setModalidad(datosCalculo.getModalidad());
		dcc.setZonaSalarial(datosCalculo.getZonaSalarial());
		dcc.setRenovacion(Boolean.FALSE);
		dcc.setAplicaCuestionario(datosCalculo.getAplicaCuestionario());

		dcc.setSalarioMinimo(new BigDecimal(1000));
		dcc.setIdEmpleador(solicitante.getIdPersona());

		// Datos del integrante
		List<DatosEmpleado> empleados = new ArrayList<DatosEmpleado>();

		for(Fisica integrante : integrantes) {
			DatosEmpleado empleado = new DatosEmpleado();
			empleado.setNumeroSeguridadSocial(integrante.getNss());
			empleado.setSalario(sueldoDiarioTrabajador);
			empleados.add(empleado);
			empleado.setEdad(0);
			empleado.setParentesco(-1L);
		}

		dcc.setEmpleados(empleados.toArray(new DatosEmpleado[0]));

		Cotizacion cotizacionIntegrante = new Cotizacion();
		try {
			log.info("********** Enviando datos: Modalidad: "
					+ dcc.getModalidad() + "\nFechaInicioCalculo: "
					+ dcc.getFechaInicioCalculo() + "\nFechaFinCalculo: "
					+ dcc.getFechaFinCalculo() + "\nZonaSalarial: "
					+ dcc.getZonaSalarial() + "\nNSSEmpleado: "
					+ dcc.getEmpleados()[0].getNumeroSeguridadSocial()
					+ "\nSalarioDiarioEmpleado: "
					+ dcc.getEmpleados()[0].getSalario()
					+ "\nAplicaRecargo:" + dcc.getRecargos()
					+ "\nAplicaRecargoPorFechaBaja:" + dcc.getAplicaRecargoPorFechaBaja());

			cotizacionIntegrante = callWebService(
					webServiceCotizaCompraPersonaContVoluntaria, dcc,
					Cotizacion.class);
		} catch (Exception e) {
			String error = "Ocurrio un error al intentar realizar la cotizacion del integrante con NSS. Intenta nuevamente.";

			log.error(error, e);
			throw new IVROServiceException(error);
		}

		if (cotizacionIntegrante.getErrorFormGeneral() != null
				&& !cotizacionIntegrante.getErrorFormGeneral().trim().isEmpty()) {
			log.error(cotizacionIntegrante.getErrorFormGeneral());
			throw new IVROServiceException(cotizacionIntegrante.getErrorFormGeneral());
		}

		if (cotizacionIntegrante.getDetalle() != null
				&& cotizacionIntegrante.getDetalle().getEmpleados().length > 0) {
			Arrays.sort(cotizacionIntegrante.getDetalle().getEmpleados()[0].getPeriodos(), new Comparator<PeriodoCuota>() {
				@Override
				public int compare(PeriodoCuota o1, PeriodoCuota o2) {
					if (o1.getOrden() < o2.getOrden()) {
						return -1;
					} else if (o1.getOrden() > o2.getOrden()) {
						return 1;
					}
					return 0;
				}
			});
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");

			for (PeriodoCuota periodoCuota : cotizacionIntegrante.getDetalle().getEmpleados()[0].getPeriodos()) {
				try {
					if (periodoCuota.getSalarioPeriodo() == null || periodoCuota.getSalarioPeriodo().equals(BigDecimal.ZERO)) {
						Map response = callWebServiceSimpleParameter(webServiceObtenerSalarioMinimoDfPorFecha,
								"<mx:fecha xmlns:mx=\"http://mx.gob.imss.digital.modelo.seguros\">"
										+ format.format(periodoCuota.getInicioPeriodo().getTime())
										+ "</mx:fecha>", Map.class);

						BigDecimal salarioMinimo = new BigDecimal(response.get("salarioMinimo").toString());
						BigDecimal salarioMaxPermit = salarioMinimo.multiply(BigDecimal.valueOf(NUM_SALARIOS));

						if (new BigDecimal(sueldoS).compareTo(salarioMaxPermit) > 0) {
							// Si el salario puesto es mayor al salario Max pemitido
							// Se le pone el maximo (NUM_SALARIOS veces el
							// salario minimo de ese tiempo)
							periodoCuota.setSalarioPeriodo(salarioMaxPermit);
						} else if (salarioMinimo.compareTo(new BigDecimal(sueldoS)) > 0) {
							periodoCuota.setSalarioPeriodo(salarioMinimo);
						}
						log.debug("No se obtuvo salario base de cotizacion, se realiza calculo manual: " + periodoCuota.getSalarioPeriodo());
					} else {
						log.debug("Se obtiene salario base de cotizacion: " + periodoCuota.getSalarioPeriodo());
					}
				} catch (Exception e) {
					String error = "Ocurrio un error al intentar consultar el salario minimo.";
					log.error(error, e);
					throw new IVROServiceException(error);
				}
			}
		} else {
			String error = "Ocurrio un error al intentar realizar la cotizacion. Intenta nuevamente.";

			log.error(error);
			throw new IVROServiceException(error);
		}

		return cotizacionIntegrante;
	}


    @RequestMapping(value = "/comunes/procesar-datos-firma")
    public @ResponseBody
    Map<String, Object> comunProcesarDatosFirma(Model model,
            HttpServletRequest request,
            HttpServletResponse response, HttpSession session,
            @ModelAttribute("solicitud") Solicitud solicitud) {

        EstadoTramite estadoTramite = new EstadoTramite();
        estadoTramite.setIdEstadoTramitePersona(EstadoTramiteEnum.CERRADO.getId());
        estadoTramite.setDescripcion(EstadoTramiteEnum.CERRADO.name());
        solicitud.getTramite()[0].setEstadoTramite(estadoTramite);
        TramiteSeguroIvro tsi = (TramiteSeguroIvro) solicitud.getTramite()[0];
		Domicilio domicilioParticular = null;

        if (tsi != null && tsi.getPersona() != null && tsi.getPersona().getDomicilioParticular() != null) {
        	if(tsi.getPersona().getDomicilioParticular().getIdDomicilio() == null){
				log.debug("**** Se asociara en la base el nuevo domicilio" + tsi.getPersona().getDomicilioParticular().getCalle() + " ***");
				guardarPersonaDomicilio(tsi.getPersona().getDomicilioParticular(), tsi.getPersona().getIdPersona());
			}
			domicilioParticular = tsi.getPersona().getDomicilioParticular();
        }

		solicitud.getTramite()[0].setDetalleTramiteXml(SeguroIvroUtil.getDetalleTramiteString(tsi));
        EstadoSolicitud estadoSolicitud = new EstadoSolicitud();
        estadoSolicitud.setIdEstadoSolicitud(EstadoSolicitudEnum.PENDIENTE_AUTORIZACION.getId().intValue());
        solicitud.setEstadoSolicitud(estadoSolicitud);
        UsuarioSSO usuarioSSO = procesarUsuarioSSO(request);
        solicitud.setUsuario(usuarioSSO.getCurp());
        solicitud.setUsuarioResponsable(usuarioSSO.getCurp());

        Map<String, Object> result = new HashMap<String, Object>();
        log.info(ID_PERSONA + tsi.getPersona().getIdPersona());

        session.setAttribute(KEY_NSS_CIFRADO_FINAL, tsi.getPersona().getNssCifrado());
        session.setAttribute(KEY_TERMINANDO_SOLICITUD, true);
        Solicitud solicitudResult = seguroIndividualServices.guardaSolicitud(solicitud);

        if (hasError(solicitudResult)) {
            result.put(ERROR, solicitudResult.getErrorFormGeneral());
        } else {
            result.put(SOLICITUD, solicitud);
            if(domicilioParticular!=null){
            mx.gob.imss.ctirss.delta.model.domicilio.Subdelegacion subdelegacion;
            try {
                subdelegacion = domicilioServiceBusiness.getSubDelegacionPorCodigoPostal(domicilioParticular.getCodigoPostal());
                if (subdelegacion != null && subdelegacion.getId() != null) {
                    solicitudServiciosExpuestos.asociarSolicitudSubDelegacion(solicitud.getIdSolicitud(), subdelegacion.getId());
                }
            } catch (SubDelegacionNoLocalizadaException ex) {
                log.error("No se localizo Subdelegacion", ex);
            } catch (Exception e){
                log.error("No se localizo Subdelegacion", e);
            }
        }

        }
        session.setAttribute("enviarCorreo", Boolean.TRUE);
        return result;
    }

    /**
     * MEtodo encargado de cancelar una solicitud
     *
     * @param domicilioInicial
     * @param idPersona
     */
    public void guardarPersonaDomicilio(Domicilio domicilioInicial, Long idPersona) {
        log.info("solicitudResult:" + domicilioInicial.toString()+ " y el IdPersona:" + idPersona);
        try {
            seguroIndividualServices.guardarYAsociarDomiciliosPersona(domicilioInicial, idPersona);
        } catch (DomicilioNoValidoException ex) {
            log.error("ERROR al guardar domicilio", ex);
        } catch (DomicilioNoLocalizadoException ex) {
            log.error("ERROR al guardar domicilio", ex);
        } catch (EJBException ejb) {
            log.error("Excepcion no controlada al consumir el EJB", ejb);//que significa que no se controlo al consumir el ejb
        }
    }

    public boolean esSolicitudInternet(Long origenSolicitud) {
        boolean esInternet = false;
        OrigenSolicitudEnum origen = OrigenSolicitudEnum.getById(origenSolicitud);
        if (origen.equals(OrigenSolicitudEnum.INTERNET)) {
            esInternet = true;
        }
        return esInternet;
    }
    
    /**
     * Funcion para validar si el asegurado continua en el RO o si ya no.
     * @param persona
     * @return
     */
    private boolean verificarVigenciaParaRO(Fisica persona) {
		try {
			this.log.info("Entrando en funcion verificarVigenciaParaRO");
			DatosCalculoCuota calculo = callWebService(webServiceValidaPersonaContVoluntariaRenova, persona, DatosCalculoCuota.class);
			if(calculo.getErrorFormGeneral().contains("debido a que te encuentras vigente")) {
				this.log.error(" --- MENSAJE DEL WEBSERVICE: "+calculo.getErrorFormGeneral());
				 log.info("El seguro esta cancelado previamente porque se detecto que esta inscrito en el regimen obligatorio.");
                 return true;
			}else {
				log.info("******* El asegurado ya no se encuentra dado de alta en el RO.");
				return false;
			}
		} catch (Exception e) {
			log.error("Ocurrio un errror al validar el regimen obligatorio: ", e);
			e.printStackTrace();
			return false;
		}
	}
    
    
    private static Date limpiarHora(Date fecha) {
    	
    	Calendar cal = Calendar.getInstance();
    	cal.setTime(fecha);
    	cal.set(Calendar.HOUR_OF_DAY, 0);
    	cal.set(Calendar.MINUTE, 0);
    	cal.set(Calendar.SECOND, 0);
    	cal.set(Calendar.MILLISECOND, 0);
    	
    	return cal.getTime();
    	
   	}

}
