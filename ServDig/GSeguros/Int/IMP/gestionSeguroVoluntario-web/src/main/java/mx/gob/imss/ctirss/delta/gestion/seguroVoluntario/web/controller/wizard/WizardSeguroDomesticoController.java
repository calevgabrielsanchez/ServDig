package mx.gob.imss.ctirss.delta.gestion.seguroVoluntario.web.controller.wizard;

import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.security.InvalidKeyException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.ejb.EJBException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import mx.gob.imss.ctirss.delta.derechohabientes.service.utility.interfaces.GrupoFamiliarServiceRemote;
import mx.gob.imss.ctirss.delta.exception.derechohabiente.DerechohabientesBusinessException;
import mx.gob.imss.ctirss.delta.exception.domicilio.DomicilioNoLocalizadoException;
import mx.gob.imss.ctirss.delta.exception.domicilio.DomicilioNoValidoException;
import mx.gob.imss.ctirss.delta.exception.domicilio.MunicipioImssNoLocalizadoException;
import mx.gob.imss.ctirss.delta.framework.base.web.sso.UsuarioSSO;
import mx.gob.imss.ctirss.delta.framework.util.Base64Cipher;
import mx.gob.imss.ctirss.delta.gestion.domicilio.service.interfaces.domicilio.DomicilioServiceBussinessExternosRemote;
import mx.gob.imss.ctirss.delta.gestion.seguroVoluntario.exception.IVROServiceException;
import mx.gob.imss.ctirss.delta.gestion.seguroVoluntario.service.exception.IvroException;
import mx.gob.imss.ctirss.delta.gestion.seguroVoluntario.service.interfaces.ConsultaSeguroIvroServiceRemote;
import mx.gob.imss.ctirss.delta.gestion.seguroVoluntario.service.interfaces.VigenciaIvroServiceRemote;
import mx.gob.imss.ctirss.delta.gestion.seguroVoluntario.util.SeguroCvroUtil;
import mx.gob.imss.ctirss.delta.gestion.seguroVoluntario.util.SeguroIvroRenovacionUtil;
import mx.gob.imss.ctirss.delta.gestion.seguroVoluntario.util.SeguroIvroUtil;
import mx.gob.imss.ctirss.delta.gestion.seguroVoluntario.util.UtilConvert;
import mx.gob.imss.ctirss.delta.gestion.seguroVoluntario.web.controller.WebServiceCallerController;
import mx.gob.imss.ctirss.delta.gestion.seguroVoluntario.web.form.MDMDatosEntradaIvro;
import mx.gob.imss.ctirss.delta.gestion.seguroVoluntario.web.reporte.GeneradorComprobanteSeguro;
import mx.gob.imss.ctirss.delta.gestion.seguroVoluntario.web.servicios.SeguroIndividualServices;
import mx.gob.imss.ctirss.delta.model.asegurado.AsignacionNSS;
import mx.gob.imss.ctirss.delta.model.derechohabiente.GrupoFamiliar;
import mx.gob.imss.ctirss.delta.model.enums.EstadoSolicitudEnum;
import mx.gob.imss.ctirss.delta.model.enums.EstadoTramiteEnum;
import mx.gob.imss.ctirss.delta.model.enums.TipoContactoEnum;
import mx.gob.imss.ctirss.delta.model.enums.TipoPersonaEnum;
import mx.gob.imss.ctirss.delta.model.enums.TipoSolicitudEnum;
import mx.gob.imss.ctirss.delta.model.enums.TipoTramiteEnum;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.OrigenSolicitudEnum;
import mx.gob.imss.digital.modelo.cobranza.Cotizacion;
import mx.gob.imss.digital.modelo.cobranza.DatosCalculoCuota;
import mx.gob.imss.digital.modelo.cobranza.DatosEmpleado;
import mx.gob.imss.digital.modelo.cobranza.Pago;
import mx.gob.imss.digital.modelo.cobranza.PeriodoCuota;
import mx.gob.imss.digital.modelo.comun.Modalidad;
import mx.gob.imss.digital.modelo.cuestionario.PersonaCuestionario;
import mx.gob.imss.digital.modelo.domicilio.Domicilio;
import mx.gob.imss.digital.modelo.medio.contacto.MedioContacto;
import mx.gob.imss.digital.modelo.medio.contacto.TipoMedioContacto;
import mx.gob.imss.digital.modelo.patron.RegistroPatronal;
import mx.gob.imss.digital.modelo.persona.Fisica;
import mx.gob.imss.digital.modelo.persona.Persona;
import mx.gob.imss.digital.modelo.persona.TipoPersona;
import mx.gob.imss.digital.modelo.seguros.DocumentoSeguro;
import mx.gob.imss.digital.modelo.seguros.SeguroIvro;
import mx.gob.imss.digital.modelo.seguros.SegurosIvro;
import mx.gob.imss.digital.modelo.solicitud.EstadoSolicitud;
import mx.gob.imss.digital.modelo.solicitud.FirmaElectronica;
import mx.gob.imss.digital.modelo.solicitud.OrigenSolicitud;
import mx.gob.imss.digital.modelo.solicitud.Solicitud;
import mx.gob.imss.digital.modelo.solicitud.TipoSolicitud;
import mx.gob.imss.digital.modelo.tramite.EstadoTramite;
import mx.gob.imss.digital.modelo.tramite.TipoTramite;
import mx.gob.imss.digital.modelo.tramite.TramiteSeguroIvro;
import mx.gob.imss.distss.digital.jaxb.util.JaxbUtil;
import mx.gob.imss.distss.gestion.cuestionario.modelo.TramiteCuestionarioDummy;

import org.apache.commons.lang.StringUtils;
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
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.ws.client.core.WebServiceTemplate;

import mx.gob.imss.digital.modelo.seguros.AsignacionNssIvro;
import mx.gob.imss.ws.estatusvigencia.individual.cliente.WSConsultaSituacionAseguramiento;
import mx.gob.imss.ws.estatusvigencia.individual.cliente.RespuestaSituacionAseguramiento;
import mx.gob.imss.digital.modelo.sindo.VigenciaTrabajdor;
import mx.gob.imss.ctirss.delta.gestion.seguroVoluntario.util.UtilConvert;
import mx.gob.imss.digital.modelo.sindo.RespuestaValidacionTrabajador;

@Controller
@SessionAttributes(value={"empleador", "datosCalculo", "tramites", "solicitud", "empleado", "cotizacionEmpleado", "nssTrabajador", "sueldoDiarioTrabajador", "seguro", "tipoOperacion", "recargos", "seguros", "esVentanilla", "tieneSeguros", "registroPatronal", "idPersona", "patron","umaValor"})
@RequestMapping(value="/wizard/seguroDomestico")
public class WizardSeguroDomesticoController extends WebServiceCallerController {
	
	private static final String KEY_CADENA_ORIGINAL = "contenidoFirmar";
	private static final String KEY_FIRMA_ELECTRONICA = "datosFirmaElectronica";
	private static final String KEY_TIPO_SOLICITUD = "codigoTipoSolicitud";
	private static final String KEY_TIPO_TRAMITE = "codigoTipoTramite";
	private static final String DESC_TIPO_SOLICITUD = "descripcionTipoSolicitud";
	private static final String DESC_ALTA_TRAMITE_SOLICITUD = "ALTA DE SEGURO IVRO";
	private static final String DESC_RENOVACION_TRAMITE_SOLICITUD = "RENOVACION DE SEGURO IVRO";
	private static final String KEY_RENOVACION = "RENOVACION";
	private static final String KEY_NSS_CIFRADO_FINAL = "_nssCifradoFinal";
	private static final String KEY_SEGUROS_EMPLEADOS = "_listaSegurosEmpleados";
	private static final String KEY_NSS_DOMESTICO = "nss";
	private static final String KEY_NSS_CIFRADO_DOMESTICO = "nssCifrado";
	private static final String KEY_SIN_RFC = "SIN_RFC";
	private static final String MSG_EXCEPTION_EXTEMPORANEA = "El seguro de tu trabajador dom\u00E9stico venci\u00F3, puedes acudir a tu Subdelegaci\u00F3n para realizar el tr\u00E1mite de renovaci\u00F3n extempor\u00E1nea o continuar tu solicitud como una Incorporaci\u00F3n Inicial, en este caso elija la opci\u00F3n Siguiente. Ver mayor informaci\u00F3n";
	private static final String MSG_EXCEPTION_NSS_NO_VIGENTE = "El n\u00FAmero de seguridad social (NSS) no est\u00E1 vigente o no se localiza, favor de acudir a la Subdelegaci\u00F3n";
	
	@Autowired
	@Qualifier("webServiceValidaCompraPatronDomestico")
	private WebServiceTemplate webServiceValidaCompraPatronDomestico;
	
	@Autowired
	@Qualifier("webServiceValidaRenovacionPatronDomestico")
	private WebServiceTemplate webServiceValidaRenovacionPatronDomestico;
		
	@Autowired
	@Qualifier("webServiceCotizaSeguroTrabajadorServices")
	private WebServiceTemplate webServiceCotizaSeguroTrabajadorServices;

	@Autowired
	@Qualifier("webServiceCotizaSeguroServices")
	private WebServiceTemplate webServiceCotizaSeguroServices;
	 
	@Autowired
	@Qualifier("webServiceSolicitudSeguroIvro")
	private WebServiceTemplate webServiceSolicitudSeguroIvro;
		
	@Autowired
	@Qualifier("webServiceConsultaSeguroDomestico")
	private WebServiceTemplate webServiceConsultaSeguroDomestico;

	@Autowired
	@Qualifier("webServicePersonaDetalle")
	private WebServiceTemplate webServicePersonaDetalle;

	@Autowired
	@Qualifier("webServiceNRPDomesticoXDomicilio")
	private WebServiceTemplate webServiceNRPDomesticoXDomicilio;

	@Autowired
	@Qualifier("webServiceValidaCorreoPersona")
	private WebServiceTemplate webServiceValidaCorreoPersona;
	
	@Autowired
	  @Qualifier("wsConsultaSituacionAseguramiento")
	  private WSConsultaSituacionAseguramiento wsConsultaSituacionAseguramiento;

	@Autowired
	private GeneradorComprobanteSeguro generadorComprobanteSeguro;

    @Autowired
    private SeguroIndividualServices seguroIndividualServices;
    
    @Autowired
	private SeguroCvroUtil seguroCvroUtil;
    
    @Autowired
	private SeguroIvroRenovacionUtil seguroIvroRenovacionUtil;
    
    @Autowired
    @Qualifier("domicilioExternosServiceBusiness")
    private DomicilioServiceBussinessExternosRemote domicilioExternosServiceBusiness;
    
    @ModelAttribute("empleador")
	public Fisica getEmpleador() {
		return new Fisica();
	}
	@ModelAttribute("datosCalculo")
	public DatosCalculoCuota getDatosCalculo() {
		return new DatosCalculoCuota();
	}
	@ModelAttribute("tramites")
	public List<TramiteSeguroIvro> getTramites() {
		return new ArrayList<TramiteSeguroIvro>();
	}
	@ModelAttribute("empleado")
	public Fisica getEmpleado() {
		return new Fisica();
	}
	@ModelAttribute("cotizacionEmpleado")
	public Cotizacion getCotizacionEmpleado() {
		return new Cotizacion();
	}
	@ModelAttribute("nssTrabajador")
	public String getNssTrabajador() {
		return new String();
	}
	@ModelAttribute("sueldoDiarioTrabajador")
	public BigDecimal getSueldoDiarioTrabajador() {
		return BigDecimal.ZERO;
	}
	@ModelAttribute("seguro")
	public SeguroIvro getSeguro() {
		return new SeguroIvro();
	}
	@ModelAttribute("tipoOperacion")
	public String getTipoOperacion() {
		return new String();
	}
	@ModelAttribute("solicitud")
	public Solicitud getSolicitud() {
		return new Solicitud();
	}
	@ModelAttribute("recargos")
	public Boolean getRecargos(){
		return Boolean.FALSE;
	}
	@ModelAttribute("seguros")
	public SegurosIvro getSeguros() {
		return new SegurosIvro();
	}

	// Ventanilla/Ciudadano
	@ModelAttribute("esVentanilla")
	public Boolean getEsVentanilla() {
		return Boolean.FALSE;
	}
	
	@ModelAttribute("tieneSeguros")
	public Boolean getTieneSeguros() {
		return Boolean.FALSE;
	}

	@ModelAttribute("registroPatronal")
	public RegistroPatronal getRegistroPatronal() {
		return new RegistroPatronal();
	}

	@ModelAttribute("idPersona")
	public Long getIdPersona() {
		return new Long(0);
	}
	
	@ModelAttribute("patron")
	public Persona getPatron() {
		return new Persona();
	}

	@RequestMapping(value = "/validarAccesoTramite/{idPersona}/{correo}/{rfc}", method = RequestMethod.GET)
	public @ResponseBody Map<String, ? extends Object>
	validarAccesotramite(HttpSession session,
						 HttpServletRequest request, Model model, @PathVariable Long idPersona,
						 @PathVariable String correo, @PathVariable String rfc){

		Map<String, Object> result = new HashMap<String, Object>();
		result.put("idPersona", idPersona);
		result.put("error", false);

		//Borramos el tipo operacion por si vienen de algun intento de renovacion
		model.addAttribute("tipoOperacion", getTipoOperacion());
		session.removeAttribute("tipoOperacion");

		MDMDatosEntradaIvro mdmDatosEntrada = new MDMDatosEntradaIvro();
		Persona persona = new Persona();

		if(correo.contains("\u0040")){
			session.setAttribute("correoDomestico", correo);
		}

		// Seteamos el id de la persona y el tipo
		persona.setIdPersona(idPersona);
		TipoPersona tipoPersona = new TipoPersona();
		tipoPersona.setIdTipoPersona(TipoPersona.TIPO_PERSONA_FISICA);
		persona.setTipoPersona(tipoPersona);
		persona.setRfc(rfc);
		mdmDatosEntrada.setPersona(persona);

		String xmlValidacion = generarXMLValidacionCorreoElectronico(idPersona);
		log.info("********** XML PARA VALIDACION\n" + xmlValidacion);
		try {
			TipoMedioContacto tipoMedioContacto = callWebServiceSimpleParameter(
					webServiceValidaCorreoPersona, xmlValidacion,
					TipoMedioContacto.class);
			log.info("Mensaje: " + tipoMedioContacto.getDescripcion());

			if (tipoMedioContacto.getIdTipoMedioContacto() == null
					|| tipoMedioContacto.getIdTipoMedioContacto() == 0) {
				validWSRespError(tipoMedioContacto.getDescripcion());
			}
		} catch (IVROServiceException e) {
			result.put("error", true);
			result.put("msgError", e.getMessage());
		} catch (Exception e) {
			result.put("error", true);
			result.put("msgError", "Se present\u00F3 un problema al evaluar su solicitud, int\u00E9ntelo m\u00E1s tarde");
			log.error("Ocurrio un error al intentar realizar la validacion del correo electronico.", e);
			e.printStackTrace();
		}

		return result;
	}
	
	@RequestMapping(value = "/validarAccesoFielTramiteRenovacion/{idPersona}/{rfc}", method = RequestMethod.GET)
	public @ResponseBody Map<String, ? extends Object>  
	validarAccesoFielTramiteRenovacion(HttpSession session, HttpServletRequest request,
			@PathVariable Long idPersona,
			@PathVariable String rfc){
		Map<String, Object> result = new HashMap<String, Object>();
		
		result.put("error", false);
		
		Long idSeguroRenovacion = 0L;
		Persona persona = new Persona();
		persona.setIdPersona(idPersona);
		TipoPersona tipoPersona = new TipoPersona();
		tipoPersona.setIdTipoPersona(TipoPersona.TIPO_PERSONA_FISICA);
		persona.setTipoPersona(tipoPersona);
		persona.setRfc(rfc);
		
		SegurosIvro seguros = new SegurosIvro();
		try {
			seguros = callWebService(webServiceConsultaSeguroDomestico,
					persona, SegurosIvro.class, new Class[] { Persona.class });
			session.setAttribute("listaSeguros", seguros);
		} catch (Exception e) {
			log.error("Error al intentar leer la lista de seguros de la persona.", e);
		}
		
		SeguroIvro[] seguro = seguros!=null && seguros.getSeguroIvro() != null 
				? seguros.getSeguroIvro() : new SeguroIvro[]{};
				
		String nns = seguro[0].getTitular().getNss();
		idSeguroRenovacion = seguro[0].getCveIdSeguroIvro();
//		String idSeguro = String.valueOf(idSerguroRenovacion);
		
		result.put("idSeguroRenovacion", idSeguroRenovacion);
		AsignacionNssIvro asignacionNss=null;
		try{
			asignacionNss = seguroIndividualServices.obtenerAsignacionNss(nns);
		} catch (Exception e) {
		     
		      this.log.debug(e);
		    }
		Map<Long, SeguroIvro> mapaSeguros = seguroIvroRenovacionUtil.mapaSeguros(seguros);
		SeguroIvro seguroSeleccionado = null;
		if(mapaSeguros != null && mapaSeguros.containsKey(idSeguroRenovacion)){
			seguroSeleccionado = mapaSeguros.get(idSeguroRenovacion);
		
		}
		seguroIvroRenovacionUtil.validaPeriodoRenovacion(result, seguros, idSeguroRenovacion);
		seguroIvroRenovacionUtil.validarBeneficiariosNss(result, OrigenSolicitudEnum.INTERNET.getId(), seguroSeleccionado);
		
		RespuestaSituacionAseguramiento response = wsConsultaSituacionAseguramiento.getSituacionAseguramientoXAsginacionNSS(""+asignacionNss.getCveIdAsignacionNss());
		VigenciaTrabajdor vigenciaTrabajdor = new VigenciaTrabajdor();
		UtilConvert.convertirVigenciaDomestico(response, vigenciaTrabajdor);
		RespuestaValidacionTrabajador respuestaValidacionTrabajador= seguroIndividualServices.validaVigenciaTrabajadorDomesticoRenovacion(vigenciaTrabajdor);

		return result;
	}
	
	@RequestMapping(value = "/validarAccesoTramiteRenovacion/{nssTrabajador}/{idSerguroRenovacion}", method = RequestMethod.GET)
	public @ResponseBody Map<String, ? extends Object>  
			validarAccesotramiteRenovacion(HttpSession session, HttpServletRequest request, 
					@PathVariable String nssTrabajador,
					@PathVariable Long idSerguroRenovacion){
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("idSerguroRenovacion", idSerguroRenovacion);
		result.put("error", false);
		SegurosIvro seguros = (SegurosIvro) session.getAttribute("listaSeguros");
		
		
		AsignacionNssIvro asignacionNss=null;
		try{
			asignacionNss = seguroIndividualServices.obtenerAsignacionNss(nssTrabajador);
		} catch (Exception e) {
		     
		      this.log.debug(e);
		    }
		@SuppressWarnings("unchecked")
		Map<Long, SeguroIvro> mapaSeguros = (Map<Long, SeguroIvro>) session.getAttribute(KEY_SEGUROS_EMPLEADOS);
		SeguroIvro seguroSeleccionado = null;
		if(mapaSeguros != null && mapaSeguros.containsKey(idSerguroRenovacion)){
			seguroSeleccionado = mapaSeguros.get(idSerguroRenovacion);
		}
		Long idAmbiente = SeguroIvroUtil.getAmbiente(request);
		seguroIvroRenovacionUtil.validaPeriodoRenovacion(result, seguros, idSerguroRenovacion);
		seguroIvroRenovacionUtil.validarBeneficiariosNss(result, idAmbiente, seguroSeleccionado);
		
		
		RespuestaSituacionAseguramiento response = wsConsultaSituacionAseguramiento.getSituacionAseguramientoXAsginacionNSS(""+asignacionNss.getCveIdAsignacionNss());
		VigenciaTrabajdor vigenciaTrabajdor = new VigenciaTrabajdor();
		UtilConvert.convertirVigenciaDomestico(response, vigenciaTrabajdor);
		RespuestaValidacionTrabajador respuestaValidacionTrabajador= seguroIndividualServices.validaVigenciaTrabajadorDomesticoRenovacion(vigenciaTrabajdor);
		
		
		return result;
	}

	@RequestMapping(value = "/validarSeguroComprado/{idPersona}/{rfc}/{nssCifrado}", method = RequestMethod.GET)
	public @ResponseBody Map<String, ? extends Object> validarSeguroComprado(
			HttpSession session, HttpServletRequest request,
			Model model,
			@PathVariable Long idPersona,
			@PathVariable String rfc,
			@PathVariable String nssCifrado) {
		Map<String, Object> result = new HashMap<String, Object>();

		result.put("idPersona", idPersona);
		result.put("error", false);

		if (rfc !=null && rfc.equals(KEY_SIN_RFC)) {
    		rfc=null;
		}

		String nss = null;
		if (!nssCifrado.equals("-1")) {
			Map<String, Object> resultNss = descifrarNss(session, "-1", nssCifrado);
			nss = (String) resultNss.get(KEY_NSS_DOMESTICO);
			nssCifrado = (String) resultNss.get(KEY_NSS_CIFRADO_DOMESTICO);
		} else {
			nssCifrado = "-1";
		}

		Persona persona = new Persona();
		persona.setIdPersona(idPersona);
		TipoPersona tipoPersona = new TipoPersona();
		tipoPersona.setIdTipoPersona(TipoPersona.TIPO_PERSONA_FISICA);
		persona.setTipoPersona(tipoPersona);
		persona.setRfc(rfc);
//		persona.setNssCifrado(nssCifrado);
//		persona.setNss(nss);
		
		Fisica empleador = new Fisica();
		empleador.setIdPersona(idPersona);
		TipoPersona tipoPersonaEmpleador = new TipoPersona();
		tipoPersonaEmpleador.setIdTipoPersona(TipoPersona.TIPO_PERSONA_FISICA);
		empleador.setTipoPersona(tipoPersona);
		empleador.setRfc(rfc);
		empleador.setNssCifrado(nssCifrado);
		empleador.setNss(nss);
		model.addAttribute("empleador", empleador);
		SegurosIvro seguros = new SegurosIvro();
		try {
			seguros = callWebService(webServiceConsultaSeguroDomestico,
					persona, SegurosIvro.class, new Class[] { Persona.class });
			session.setAttribute("listaSeguros", seguros);
		} catch (Exception e) {
			log.error("Error al intentar leer la lista de seguros de la persona.", e);
		}
//		if (seguros.getSeguroIvro() != null && seguros.getSeguroIvro().length > 0) {
//			result.put("mostrarDetalleSeguro", true);
//		} else {
			result.put("mostrarDetalleSeguro", false);
//		}

		return result;
	}

	private String generarXMLValidacionCorreoElectronico(Long idPersona) {
		StringBuffer sbXmlValidacion = new StringBuffer();
		sbXmlValidacion.append("<mx:idPersona xmlns:mx=\"http://mx.gob.imss.digital.modelo.seguros\">")
				.append(idPersona).append("</mx:idPersona>");
		return sbXmlValidacion.toString();
	}
	
	@RequestMapping(value="/alta/init/{idPersona}/{rfc}/{nssCifrado}", method = RequestMethod.GET)
	public String altaInit(Model model, SessionStatus sessionStatus, HttpSession session,
			@PathVariable Long idPersona,
			@PathVariable String rfc,
			@PathVariable String nssCifrado) {
		
		String nss = null;		
		if(!nssCifrado.equals("-1")){
			Map<String, Object> result = descifrarNss(session,null, nssCifrado);
			nss = (String)result.get(KEY_NSS_DOMESTICO);
			nssCifrado = (String)result.get(KEY_NSS_CIFRADO_DOMESTICO);
		}else{
			nssCifrado=null;
		}
		
		Fisica persona = new Fisica();
		persona.setIdPersona(idPersona);
		TipoPersona tipoPersona = new TipoPersona();
		tipoPersona.setIdTipoPersona(TipoPersona.TIPO_PERSONA_FISICA);
		persona.setTipoPersona(tipoPersona);
		persona.setRfc(rfc);
		persona.setNssCifrado(nssCifrado);
		persona.setNss(nss);		
		model.addAttribute("empleador", persona);
		model.addAttribute("idPersona", idPersona);
		log.info("********** ID PERSONA: " + persona.getIdPersona());
		
		return "wizardSeguroDomesticoAltaInit";
	}

	@RequestMapping(value="/alta/listaTrabajadores")
	public String altaListaTrabajadores(Model model, HttpServletRequest request, HttpSession session,
			@ModelAttribute("tramites") List<TramiteSeguroIvro> tramites) {
		datosSesion(session, null);
		return "wizardSeguroDomesticoAltaListaTrabajadores";
	}

	@RequestMapping(value="/alta/agregarTrabajador")
	public String altaAgregarTrabajador(Model model, HttpServletRequest request) {
		BigDecimal uma = seguroIndividualServices.obtenerUmaPorFecha(new Date());
        model.addAttribute("umaValor",uma);
        BigDecimal salMax =  BigDecimal.ZERO;
        try{
            if(uma!=null&&!uma.equals(BigDecimal.ZERO)){
                log.info("se actualiza el Salario maximo con respecto a la UMA: "+uma);
                // DEBE SER EL SALARIO MINIMO ACORDE AL DF
                salMax = uma.multiply(new BigDecimal(30).multiply(new BigDecimal(25)));
            }else{
                log.warn("No se pudo recuperar la UMA, se queda el maximo por salario");
            }

        }catch(Exception e){
            log.error("No se pudo recuperar el salario Maximo de acuerdo a la UMA, se calcula de acuerdo al salario minimo");
        }
        model.addAttribute("salMax",salMax);

        return "wizardSeguroDomesticoAltaAgregarTrabajador";
	}

	@RequestMapping(value="/alta/quitarTrabajador")
	public String altaQuitarTrabajador(Model model, HttpServletRequest request,
			@ModelAttribute("tramites") List<TramiteSeguroIvro> tramites) {
		log.info("********** Tamanio de la lista de trabajadores: " + tramites.size());
		int index = Integer.parseInt(request.getParameter("inputIndex"));
		log.info("********** Index a eliminar: " + index);
		tramites.remove(index);
		log.info("********** Trabajadores que permanecen: " + tramites.size());
		model.addAttribute("trabajadores", tramites);
		return "wizardSeguroDomesticoAltaListaTrabajadores";
	}

	@RequestMapping(value="/alta/cuestionario")
	public String altaCuestionario(Model model, HttpServletRequest request) {
		return "wizardSeguroDomesticoAltaCuestionario";
	}

	@RequestMapping(value="/alta/validarCuestionarioyAgregarAlistaTrabajadores")
	public String altaValidarCuestionarioyAgregarAListaTrabajadores(Model model, HttpServletRequest request,
//			@RequestBody RespuestasCuestionario respuestasCuestionario,
			@ModelAttribute("empleador") Fisica empleador,
			@ModelAttribute("tramites") List<TramiteSeguroIvro> tramites,
			@ModelAttribute("empleado") Fisica empleado,
			@ModelAttribute("cotizacionEmpleado") Cotizacion cotizacionEmpleado,
			@ModelAttribute("respuestasCuestionario") TramiteCuestionarioDummy tramiteCuestionario,
			@ModelAttribute("registroPatronal") RegistroPatronal registroPatronal) {
		if(tramiteCuestionario.getRespuestas().getErrorFormGeneral() != null && !tramiteCuestionario.getRespuestas().getErrorFormGeneral().trim().isEmpty()) {
			model.addAttribute("error", tramiteCuestionario.getRespuestas().getErrorFormGeneral());
		}
		else {
			if(tramiteCuestionario.getRespuestas().getSumatoriaRespuestas() == 0) {
				PersonaCuestionario personaCuestionario = UtilConvert.parseToPersonaCuestionario(tramiteCuestionario);
				personaCuestionario.setNssPersona(empleado.getNss());
				personaCuestionario.setCuestionarioValido(true);
				personaCuestionario.setIdPersona(empleador.getIdPersona());
				TramiteSeguroIvro tramite = new TramiteSeguroIvro();
				tramite.setCotizacion(cotizacionEmpleado);
				tramite.setBeneficiarios(new Fisica[] {empleado});
				tramite.setCuetionarios(new PersonaCuestionario[] {personaCuestionario});
				tramite.setRegistroPatronal(registroPatronal);
				tramites.add(tramite);
			} else {
				//TODO Guarda rechazo
				log.error("Se agregara solicitud rechazo antes d emensaje");
				generaSolicitudRechazoTrabajador(request, empleador, tramiteCuestionario, empleado, cotizacionEmpleado, registroPatronal, tramites);
				model.addAttribute("error", "El trabajador con n\u00FAmero de seguro social <strong>" + empleado.getNss()  + "</strong> no es sujeto de aseguramiento de conformidad con el Art. 82 del Reglamento de la Ley del Seguro Social en Materia de Afiliaci\u00F3n, Clasificaci\u00F3n de Empresas, Recaudaci\u00F3n y Fiscalizaci\u00F3n.");
			}
		}
		model.addAttribute("tramites", tramites);
		return "wizardSeguroDomesticoAltaListaTrabajadores";
	}
	
	/**
	 * Genera una solicitud de rechazo asociada al trabajador para su posterior validacion en caso de que este patron o algun otro lo quiera
	 * asegurar ante el instituto.
	 * @param request
	 * @param empleador
	 * @param tramiteCuestionario
	 * @param empleado
	 * @param cotizacionEmpleado
	 * @param registroPatronal
	 * @param tramites
	 */
	private void generaSolicitudRechazoTrabajador(HttpServletRequest request,Fisica empleador, 
			TramiteCuestionarioDummy tramiteCuestionario, Fisica empleado,  
			Cotizacion cotizacionEmpleado, RegistroPatronal registroPatronal, List<TramiteSeguroIvro> tramites){
		Long ambiente = SeguroIvroUtil.getAmbiente(request);
		log.error("Se generara solicitud de rechazo para domestico: " +empleado.getNss());
		PersonaCuestionario personaCuestionario = UtilConvert.parseToPersonaCuestionario(tramiteCuestionario);
		personaCuestionario.setNssPersona(empleado.getNss());
		personaCuestionario.setCuestionarioValido(true);
		personaCuestionario.setIdPersona(empleador.getIdPersona());
		TramiteSeguroIvro tramite = new TramiteSeguroIvro();
		tramite.setCotizacion(cotizacionEmpleado);
		tramite.setBeneficiarios(new Fisica[] {empleado});
		tramite.setCuetionarios(new PersonaCuestionario[] {personaCuestionario});
		tramite.setRegistroPatronal(registroPatronal);
		TipoTramite tipoTramite = new TipoTramite();
		tipoTramite.setIdTipoTramite(TipoTramiteEnum.COMPRA_SEGURO_DOMESTICO.getCodigo());
		tramite.setEstadoTramite(new EstadoTramite());
		tramite.getEstadoTramite().setIdEstadoTramitePersona(EstadoTramiteEnum.RECHAZADO.getId());
		tramite.setTipoTramite(tipoTramite);
		tramite.setPersona(empleado);

		 // TODO
        String strUsuario;
        if(!OrigenSolicitudEnum.PORTAL_CIUDADANO.getId().equals(ambiente)) {
        	UsuarioSSO sso = this.procesarUsuarioSSO(request);  
        	strUsuario = sso.getCurp();
        } else {
        	strUsuario = seguroIndividualServices.obtenerCurpPorIdPersona(empleador.getIdPersona());
        }    
    
		Solicitud solicitud = SeguroIvroUtil.armaSolicitudInicialDomestico(ambiente, tramite, strUsuario);
		//Se inicializa como solicitud rechazada
		solicitud.getEstadoSolicitud().setIdEstadoSolicitud(EstadoSolicitudEnum.RECHAZADA.getId().intValue());
		solicitud = seguroIndividualServices.guardaSolicitud(solicitud);
		log.debug("Se genero el folio de rechazo: "+solicitud.getNumSolicitud()+" para el NSS "+empleado.getNss());
		
	}
	
	@RequestMapping(value="/comunes/solicitarTipoPago")
	public String comunSolicitarTipoPago() {
		return "wizardSeguroDomesticoComunSolicitarTipoPago";
	}
	
	@RequestMapping(value="/comunes/seleccionarTipoPago")
	public String comunSeleccionarTipoPago(Model model, HttpServletRequest request,
			@ModelAttribute("datosCalculo") DatosCalculoCuota datosCalculo,
			@ModelAttribute("tipoOperacion") String tipoOperacion) {
		String tipoPago = request.getParameter("tipoPago");
		Boolean recargos = Boolean.valueOf(tipoPago);
		model.addAttribute("recargos", recargos);
		String nextAction = null;
		if(isRenovacion(tipoOperacion)) {
			DatosCalculoCuota dcc = new DatosCalculoCuota();
			dcc.setModalidad(datosCalculo.getModalidad());
			dcc.setFechaInicioCalculo(datosCalculo.getFechaInicioCalculo());
			dcc.setFechaFinCalculo(datosCalculo.getFechaFinCalculo());
			dcc.setZonaSalarial(datosCalculo.getZonaSalarial());
			dcc.setNumeroRegistroPatronal(datosCalculo.getNumeroRegistroPatronal());
			dcc.setRecargos(recargos);
			DatosEmpleado empleado = new DatosEmpleado();
			empleado.setNumeroSeguridadSocial(datosCalculo.getEmpleados()[0].getNumeroSeguridadSocial());
			empleado.setSalario(datosCalculo.getEmpleados()[0].getSalario());
			DatosEmpleado[] empleados = {empleado};
			dcc.setEmpleados(empleados); // datos del empleado domestico
			dcc.setIdEmpleador(datosCalculo.getIdEmpleador());
			dcc.setRenovacion(true);
			List<TramiteSeguroIvro> tramites = new ArrayList<TramiteSeguroIvro>();
			Cotizacion cotizacion = new Cotizacion();
			try {
				cotizacion = callWebService(webServiceCotizaSeguroTrabajadorServices, dcc, Cotizacion.class);
				if(cotizacion.getErrorFormGeneral() != null && !cotizacion.getErrorFormGeneral().trim().isEmpty()) {
					model.addAttribute("error", cotizacion.getErrorFormGeneral());
					return "wizardSeguroDomesticoRenovacionInit";
				}
				TramiteSeguroIvro tramite = new TramiteSeguroIvro();
				tramite.setCotizacion(cotizacion);
				Fisica beneficiario = new Fisica();
				beneficiario.setNss(cotizacion.getDetalle().getEmpleados()[0].getNumeroSeguridadSocial());
				beneficiario.setNombre(cotizacion.getDetalle().getEmpleados()[0].getNombreTrabajador());
				tramite.setBeneficiarios(new Fisica[] {beneficiario});
				tramites.add(tramite);

				model.addAttribute("tramites", tramites);
			} catch(Exception e) {
				model.addAttribute("error", "Ocurri\u00F3 un error al intentar cotizar la renovaci\u00F3n del seguro.");
				log.error("Ocurrio un error al intentar cotizar la renovacion del seguro.", e);
			}
			nextAction = "wizardSeguroDomesticoRenovacionListaTrabajadores";
		}
		else {
			nextAction = "wizardSeguroDomesticoAltaListaTrabajadores";
		}
		return nextAction;
	}
	
	@RequestMapping(value="/comunes/solicitarCentroTrabajo")
	public String comunSolicitarCentroTrabajo(Model model, HttpSession session,
			@ModelAttribute("empleador") Fisica empleador) {
		try {
			Persona patron = obtenerPatronByRfc(empleador.getRfc());
//			patron.getRegistrosPatronales().getRegistrosPatronal()[0].getCentrotrabajo().getIdDomicilio()
			if (patron.getRegistrosPatronales() != null && patron.getRegistrosPatronales().getRegistrosPatronal() != null) {
				log.info("********** El solicitante tiene " + patron.getRegistrosPatronales().getRegistrosPatronal().length + " registros patronales.");
			} else {
				log.info("********** El solicitante no tiene centros de trabajo asociados");
			}
			model.addAttribute("patron", patron);
			model.addAttribute("registroPatronal", new RegistroPatronal());
			session.removeAttribute(KEY_NSS_CIFRADO_FINAL);
		} catch(Exception e) {
			log.error("Ocurrio un error la intentar obtener la informacion del contratante.", e);
		}
		return "wizardSeguroDomesticoComunSolicitarCentroTrabajo";
	}
	
	private Persona obtenerPatronByRfc(String rfc) throws Exception{
		Persona patron = new Persona();
		patron.setRfc(rfc);
		return callWebServiceSimpleParameter(webServicePersonaDetalle, "<mx:rfc xmlns:mx=\"http://mx.gob.imss.digital.modelo.patrones\">" + patron.getRfc() + "</mx:rfc>", Persona.class);
	}
	
	@RequestMapping(value="/comunes/seleccionarCentroTrabajo")
	public String comunSeleccionarCentroTrabajo(Model model, HttpServletRequest request,
			@ModelAttribute("patron") Persona patron,
			@ModelAttribute("registroPatronal") RegistroPatronal registroPatronal,
			@ModelAttribute("empleador") Fisica empleador,
			@ModelAttribute("tipoOperacion") String tipoOperacion) {

		if(registroPatronal.getNumeroRegistroPatronal() != null) {
			for(RegistroPatronal currentRP : patron.getRegistrosPatronales().getRegistrosPatronal()) {
				String registroPatronalListado = new StringBuffer(currentRP.getNumeroRegistroPatronal()).append(
						currentRP.getModalidad().getNumModalidad()).append(
								currentRP.getDigitoVerificador()).toString();
				
				if(registroPatronalListado.equalsIgnoreCase(registroPatronal.getNumeroRegistroPatronal())) {
					registroPatronal = currentRP;
					break;
				}
			}
		}

		if(!isRenovacion(tipoOperacion)) {
			Long idEmpleador = (Long) model.asMap().get("idPersona");
			obtenerDatosCalculoCuota(model, registroPatronal, empleador, idEmpleador);
		}
		
		try {
			log.info("********* VALOR DEL DOMICILIO: " + JaxbUtil.marshaller(registroPatronal));
		} catch(Exception e) {
			log.error("********** NO SE PUEDE MOSTRAR LA INFORMACION DEL REGISTRO PATRONAL", e);
		}
		model.addAttribute("registroPatronal", registroPatronal);
		
		return "wizardSeguroDomesticoComunSolicitarTipoPago";
	}	
	
	@RequestMapping(value="/comunes/agregarCentroTrabajo")
	public String comunAgregarCentroTrabajo(Model model) {
		model.addAttribute("registroPatronal", new RegistroPatronal());
		return "wizardSeguroDomesticoComunAgregarCentroTrabajo";
	}
	
	@RequestMapping(value="/comunes/registrarNuevoCentroTrabajo")
	public @ResponseBody Map<String, Object> comunRegistrarNuevoCentroTrabajo(Model model,
			@RequestBody mx.gob.imss.ctirss.delta.model.domicilio.Domicilio centroTrabajo,
		  	@ModelAttribute("tipoOperacion") String tipoOperacion) {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("existeCentroTrabajo", false);
		Domicilio centroTrabajoNuevo = SeguroIvroUtil.convertirDomicilioAImssDigital(centroTrabajo);
		centroTrabajoNuevo.getAsentamiento().setMunicipio(centroTrabajoNuevo.getAsentamiento().getLocalidad().getMunicipio());
		log.info("********* VALOR DEL DOMICILIO: " + centroTrabajoNuevo.toString());
		RegistroPatronal registroPatronal = new RegistroPatronal();
		registroPatronal.setCentrotrabajo(centroTrabajoNuevo);
		Fisica empleador = (Fisica)model.asMap().get("empleador");

		if(!isRenovacion(tipoOperacion)) {
			Long idEmpleador = (Long)model.asMap().get("idPersona");
			obtenerDatosCalculoCuota(model, registroPatronal, empleador, idEmpleador);
		}
		
		String xmlValidacion = generarXMLValidacionCentroTrabajo(empleador, centroTrabajoNuevo);
		log.info("********** XML PARA VALIDACION\n" + xmlValidacion);
		try {
			RegistroPatronal registroPatronalValidacion = callWebServiceSimpleParameter(webServiceNRPDomesticoXDomicilio, xmlValidacion, RegistroPatronal.class);
			if(registroPatronalValidacion != null && registroPatronalValidacion.getCentrotrabajo() != null && registroPatronalValidacion.getCentrotrabajo().getIdDomicilio() != null) {
				result.put("registroPatronal", registroPatronalValidacion);
				result.put("existeCentroTrabajo", true);
				model.addAttribute("registroPatronal", registroPatronalValidacion);
//				result.put("idCentroTrabajo", registroPatronalValidacion.getCentrotrabajo().getIdDomicilio());
//				result.put("numeroRegistroPatronal", registroPatronalValidacion.getNumeroRegistroPatronal());
//				result.put("idModalidad", registroPatronalValidacion.getModalidad().getIdModalidad());
//				result.put("numModalidad", registroPatronalValidacion.getModalidad().getNumModalidad());
//				result.put("digitoVerificador", registroPatronalValidacion.getDigitoVerificador());
//				result.put("descripcionCentroTrabajo", registroPatronalValidacion.getCentrotrabajo().getDescripcion());
			} else {
				model.addAttribute("registroPatronal", registroPatronal);
			}
		} catch (Exception e) {
			log.error("Ocurrio un error al intentar realizar la validacion del centro de trabajo.", e);
		}
		return result;
	}
	
	private void obtenerDatosCalculoCuota(Model model, RegistroPatronal registroPatronal, 
			Fisica empleador, Long idPersona){
		DatosCalculoCuota dcc = new DatosCalculoCuota();
		try {
			empleador.setDomicilioParticular(registroPatronal.getCentrotrabajo());
			empleador.setIdPersona(idPersona);
			dcc = callWebService(webServiceValidaCompraPatronDomestico, empleador, DatosCalculoCuota.class);
			if (dcc!=null) {
				dcc.setNumeroRegistroPatronal(null);
			}
			
		} catch(Exception e) {
			dcc.setErrorFormGeneral("Ocurri\u00F3 un error al realizar la validaci\u00F3n del empleador.");
			log.error("********** Ocurrio un error al realizar la validacion del empleador. **********", e);
		}
		model.addAttribute("datosCalculo", dcc);
	}
	
	private String generarXMLValidacionCentroTrabajo(Persona empleador, Domicilio centroTrabajo) {
		StringBuffer xmlValidacion = new StringBuffer("<mx4:personaDomicilio xmlns:mx=\"http://mx.gob.imss.digital.modelo.persona\" "+ 
			"xmlns:mx1=\"http://mx.gob.imss.digital.modelo.domicilio\" " + 
			"xmlns:mx2=\"http://mx.gob.imss.digital.modelo.medio.contacto\" " + 
			"xmlns:mx3=\"http://mx.gob.imss.digital.modelo.comun\" " +
			"xmlns:mx4=\"http://mx.gob.imss.digital.modelo.patrones\">");
			try {
				xmlValidacion.append("<persona>")
					.append("<rfc>")
					.append(empleador.getRfc())
					.append("</rfc>")
					.append("</persona>")
					.append("<domicilio>")
					.append("<codigoPostal>")
					.append(centroTrabajo.getCodigoPostal())
					.append("</codigoPostal>")
					.append("<mx1:asentamiento>")
					.append("<mx1:municipio>")
					.append("<clave>")
					.append(centroTrabajo.getAsentamiento().getMunicipio().getClave())
					.append("</clave>")
					.append("<mx1:entidadFederativa>")
					.append("<clave>")
					.append(centroTrabajo.getAsentamiento().getMunicipio().getEntidadFederativa().getClave())
					.append("</clave>")
					.append("</mx1:entidadFederativa>")
					.append("</mx1:municipio>")
					.append("</mx1:asentamiento>")
					.append("</domicilio>")
					.append("</mx4:personaDomicilio>");
			} catch (Exception e) {
				log.error("********** Ocurrio un error al intentar generar la cadena de valicion para el centro de trabajo seleccionado.", e);
				return null;
			}
		return xmlValidacion.toString(); 
	}

	@RequestMapping(value="/comunes/cotizacionTrabajador")
	public String comunCotizarTrabajador(Model model, HttpServletRequest request,HttpSession session,
			@ModelAttribute("datosCalculo") DatosCalculoCuota datosCalculo,
			@ModelAttribute("tramites") List<TramiteSeguroIvro> tramites,
			@ModelAttribute("nssTrabajador") String nssTrabajador,
			@ModelAttribute("sueldoDiarioTrabajador") BigDecimal sueldoDiarioTrabajador,
			@ModelAttribute("recargos") Boolean recargos,
			@ModelAttribute("tipoOperacion") String tipoOperacion,
			@ModelAttribute("empleador") Fisica empleador,
			@ModelAttribute("idPersona") Long idPersona) {
		nssTrabajador = request.getParameter("nssTrabajador");
		
		String nssEmpleador=empleador.getNss();

		BigDecimal salMax = BigDecimal.ZERO;

		try{
			//WEB SERVICE QUE OBTIENE ELVALOR DE LA UMA VIGENTE DEL DF
			BigDecimal uma = this.seguroIndividualServices.obtenerUmaPorFecha(new Date());

			// DEBE SER EL SALARIO MINIMO ACORDE AL DF
			salMax = uma.multiply(new BigDecimal(30).multiply(new BigDecimal(25)));

			if(salMax!=null&&!salMax.equals(BigDecimal.ZERO)){
				log.info("se actualiza el Salario maximo con respecto a la UMA: "+uma);
			}else{
				log.warn("No se pudo recuperar la UMA, se queda el maximo por salario");
                salMax= datosCalculo.getSalarioMinimo().multiply(new BigDecimal(30)).multiply(new BigDecimal(25));
			}

		}catch(Exception e){
			log.error("No se pudo recuperar el salario Maximo de acuerdo a la UMA, se calcula de acuerdo al salario minimo");
            salMax= datosCalculo.getSalarioMinimo().multiply(new BigDecimal(30)).multiply(new BigDecimal(25));
		}
        model.addAttribute("salMax",salMax);


		try {
			if (nssEmpleador!=null) {
				validaRestriccionAutoempleo(nssTrabajador, nssEmpleador);
			}
			
		} catch (IvroException e1) {
			model.addAttribute("error", e1.getMessage());
			model.addAttribute("nssTrabajador",nssTrabajador);
			model.addAttribute("sueldoDiarioTrabajador",sueldoDiarioTrabajador);
			return "wizardSeguroDomesticoAltaAgregarTrabajador";
		}
		
		if(isRenovacion(tipoOperacion)) {
			Integer inputIndex = Integer.parseInt(request.getParameter("inputIndex"));
			model.addAttribute("inputIndex", inputIndex);
		}
		else{
		if(nssTrabajador == null || nssTrabajador.trim().isEmpty()) {
			model.addAttribute("error", "Debes ingresar el n\u00FAmero de seguro social del empleado.");
			return "wizardSeguroDomesticoAltaAgregarTrabajador";
		}
		if(nssTrabajador.length() < 11) {
			model.addAttribute("error", "El n\u00FAmero de seguro social del empleado debe ser de 11 d\u00EDgitos.");
			return "wizardSeguroDomesticoAltaAgregarTrabajador";
		}
		}
		String sueldoS = request.getParameter("sueldoDiarioTrabajador");
			
		BigDecimal sueldoMensual = new BigDecimal(sueldoS == null || sueldoS.trim().isEmpty() ? "0" : sueldoS);
		BigDecimal minMensual = datosCalculo.getSalarioMinimo().multiply(new BigDecimal(30));

		// Se obtiene el salario diario del trabajador
		sueldoDiarioTrabajador = sueldoMensual.divide(new BigDecimal(30), 2, RoundingMode.HALF_UP);
				
		if (sueldoMensual == null
				|| sueldoMensual.compareTo(minMensual) == -1) {
			//model.addAttribute("error", "El salario diario del empleado no debe ser menor al salario m\u00EDnimo [$ " + nf.format(datosCalculo.getSalarioMinimo()) + "].");
			NumberFormat nf = new DecimalFormat("#,##0.00");	    
			model.addAttribute("error", "El salario mensual del empleado no debe ser menor al salario m\u00EDnimo mensual [$ " + nf.format(minMensual) + "].");

			
			if (isRenovacion(tipoOperacion)) {
				return "wizardSeguroDomesticoRenovacionActualizarSalario";
			} else {
				return "wizardSeguroDomesticoAltaAgregarTrabajador";
			}
		}

		if (sueldoMensual.compareTo(salMax)  == 1) {
			NumberFormat nf = new DecimalFormat("#,##0.00");
			model.addAttribute("error", "El salario mensual del empleado no debe ser mayor al salario m&aacute;ximo mensual [$ " + nf.format(salMax) + "].");

			if (isRenovacion(tipoOperacion)) {
				return "wizardSeguroDomesticoRenovacionActualizarSalario";
			} else {
				return "wizardSeguroDomesticoAltaAgregarTrabajador";
			}
		}

		DatosCalculoCuota dcc = new DatosCalculoCuota();
		dcc.setIdEmpleador(idPersona);
		dcc.setModalidad(datosCalculo.getModalidad());
		dcc.setFechaInicioCalculo(datosCalculo.getFechaInicioCalculo());
		dcc.setFechaFinCalculo(datosCalculo.getFechaFinCalculo());
		dcc.setZonaSalarial(datosCalculo.getZonaSalarial());
		dcc.setNumeroRegistroPatronal(datosCalculo.getNumeroRegistroPatronal());
		dcc.setRecargos(recargos);
		DatosEmpleado empleado = new DatosEmpleado();
		empleado.setNumeroSeguridadSocial(nssTrabajador);
		empleado.setSalario(sueldoDiarioTrabajador);
		DatosEmpleado[] empleados = {empleado};
		dcc.setEmpleados(empleados); // datos del empleado domestico
		//TODO Agregar el origen
		dcc.setErrorFormGeneral(getOrigenContext(request).toString());
		
		if(!isRenovacion(tipoOperacion)) {
			for(TramiteSeguroIvro t : tramites) {
				if (t.getBeneficiarios()[0].getNss().equalsIgnoreCase(nssTrabajador)) {
					model.addAttribute("error", "El NSS ya existe en la lista de trabajadores para esta solicitud.");
					return "wizardSeguroDomesticoAltaAgregarTrabajador";
				}
			}
		}
		
		Cotizacion cotizacionEmpleado = new Cotizacion();
		try {
			
			log.info("********** Enviando datos: Modalidad: " + dcc.getModalidad() +
					"\nFechaInicioCalculo: " + dcc.getFechaInicioCalculo() +
					"\nFechaFinCalculo: " + dcc.getFechaFinCalculo() +
					"\nZonaSalarial: " + dcc.getZonaSalarial() +
					"\nNSSEmpleado: " + dcc.getEmpleados()[0].getNumeroSeguridadSocial() +
					"\nSalarioDiarioEmpleado: " + dcc.getEmpleados()[0].getSalario());
			cotizacionEmpleado = callWebService(webServiceCotizaSeguroTrabajadorServices, dcc, Cotizacion.class);
			
		}
		catch(Exception e) {
			model.addAttribute("error", "Ocurri\u00F3 un error al intentar realizar la cotizaci\u00F3n del empleado. Intenta nuevamente.");
			log.error("********** Ocurrio un error al intentar realizar la cotizacion del empleado.", e);
			if (isRenovacion(tipoOperacion)) {
				return "wizardSeguroDomesticoRenovacionActualizarSalario";
			} else {
				return "wizardSeguroDomesticoAltaAgregarTrabajador";
			}
		}
		if(cotizacionEmpleado.getErrorFormGeneral() != null && !cotizacionEmpleado.getErrorFormGeneral().trim().isEmpty()) {
			model.addAttribute("error", cotizacionEmpleado.getErrorFormGeneral());
			if (isRenovacion(tipoOperacion)) {
				return "wizardSeguroDomesticoRenovacionActualizarSalario";
			} else {
				return "wizardSeguroDomesticoAltaAgregarTrabajador";
			}
		}
		if(cotizacionEmpleado.getDetalle() != null && cotizacionEmpleado.getDetalle().getEmpleados().length > 0) {
			Fisica beneficiario = new Fisica();
			beneficiario.setNss(nssTrabajador);
			beneficiario.setNombre(cotizacionEmpleado.getDetalle().getEmpleados()[0].getNombreTrabajador());
			model.addAttribute("empleado", beneficiario);
			Arrays.sort(cotizacionEmpleado.getDetalle().getEmpleados()[0].getPeriodos(), new Comparator<PeriodoCuota>() {
				@Override
				public int compare(PeriodoCuota o1, PeriodoCuota o2) {
					if (o1.getOrden() < o2.getOrden()) {
						return -1;
					} else if(o1.getOrden() > o2.getOrden()) {
						return 1;
					}
					return 0;
				}
			});
			
		} else {
			model.addAttribute("error", "Ocurri\u00F3 un error al intentar realizar la cotizaci\u00F3n del empleado. Intenta nuevamente.");
			if (isRenovacion(tipoOperacion)) {
				return "wizardSeguroDomesticoRenovacionActualizarSalario";
			} else {
				return "wizardSeguroDomesticoAltaAgregarTrabajador";
			}
		}

		if ((!isRenovacion(tipoOperacion) ) && cotizacionEmpleado.getAplicaCuestionario()) {
			model.addAttribute("nextAction", "alta/cuestionario");
		} else {
			model.addAttribute("nextAction", "comunes/agregarAlistaTrabajadores");
		}
		model.addAttribute("cotizacionEmpleado", cotizacionEmpleado);
		return "wizardSeguroDomesticoComunCotizacionTrabajador";
	}
	
	@RequestMapping(value="/comunes/agregarAlistaTrabajadores")
	public String comunAgregarAListaTrabajadores(Model model, HttpServletRequest request,
			@ModelAttribute("tramites") List<TramiteSeguroIvro> tramites,
			@ModelAttribute("empleado") Fisica empleado,
			@ModelAttribute("cotizacionEmpleado") Cotizacion cotizacionEmpleado,
			@ModelAttribute("tipoOperacion") String tipoOperacion) {
		String result = null;
		if(tipoOperacion == null || tipoOperacion.trim().equalsIgnoreCase("")) {
			TramiteSeguroIvro tramite = new TramiteSeguroIvro();
			tramite.setCotizacion(cotizacionEmpleado);
			tramite.setBeneficiarios(new Fisica[] {empleado});
			tramites.add(tramite);
			result = "wizardSeguroDomesticoAltaListaTrabajadores";
		} else {
			Integer inputIndex = Integer.parseInt(request.getParameter("inputIndex"));
			tramites.get(inputIndex).setCotizacion(cotizacionEmpleado);
			result = "wizardSeguroDomesticoRenovacionListaTrabajadores";
		}
		model.addAttribute("tramites", tramites);
		return result;
	}
	
	@RequestMapping(value="/comunes/resumen")
	public String comunResumen(Model model, HttpSession session, HttpServletRequest request,
			@ModelAttribute("empleador") Fisica empleador,
			@ModelAttribute("idPersona") Long idPersona,
			@ModelAttribute("tramites") List<TramiteSeguroIvro> tramites,
			@ModelAttribute("datosCalculo") DatosCalculoCuota datosCalculo,
			@ModelAttribute("recargos") Boolean recargos,
			@ModelAttribute("tipoOperacion") String tipoOperacion,
			@ModelAttribute("seguro") SeguroIvro seguro,
			@ModelAttribute("esVentanilla") Boolean esVentanilla,						// Ventanilla/Ciudadano
			@ModelAttribute("registroPatronal") RegistroPatronal registroPatronal) {
		datosSesion(session, tipoOperacion);
		DatosCalculoCuota dcc = new DatosCalculoCuota();
		dcc.setModalidad(datosCalculo.getModalidad());
		dcc.setFechaInicioCalculo(datosCalculo.getFechaInicioCalculo());
		dcc.setFechaFinCalculo(datosCalculo.getFechaFinCalculo());
		dcc.setZonaSalarial(datosCalculo.getZonaSalarial());
		dcc.setNumeroRegistroPatronal(datosCalculo.getNumeroRegistroPatronal());
		dcc.setRecargos(recargos);
		dcc.setRenovacion(datosCalculo.getRenovacion());
		List<DatosEmpleado> empleados = new ArrayList<DatosEmpleado>();
		List<Fisica> beneficiarios = new ArrayList<Fisica>();
		List<PersonaCuestionario> cuestionarios = new ArrayList<PersonaCuestionario>();
		for(TramiteSeguroIvro tramite : tramites) {
			DatosEmpleado empleado = new DatosEmpleado();
			empleado.setNumeroSeguridadSocial(tramite.getBeneficiarios()[0].getNss());
			empleado.setSalario(tramite.getCotizacion().getDetalle().getEmpleados()[0].getSalario());
			empleados.add(empleado);
			Fisica beneficiario = new Fisica();
			beneficiario.setNss(empleado.getNumeroSeguridadSocial());
			beneficiario.setNombre(tramite.getCotizacion().getDetalle().getEmpleados()[0].getNombreTrabajador());
			beneficiarios.add(beneficiario);
			if (tramite.getCuetionarios() != null && tramite.getCuetionarios().length > 0) {
				cuestionarios.add(tramite.getCuetionarios()[0]);
			}
		}
		dcc.setEmpleados(empleados.toArray(new DatosEmpleado[0]));
		Cotizacion cotizacionGeneral = new Cotizacion();
		try {
			cotizacionGeneral = callWebService(webServiceCotizaSeguroServices, dcc, Cotizacion.class);
		} catch(Exception e) {
			model.addAttribute("error", "Ocurri\u00F3 un error al intentar realizar la cotizaci\u00F3n general.");
			log.error("********** Ocurrio un error al intentar realizar la cotizacion general.", e);
			if (isRenovacion(tipoOperacion)) {
				return "wizardSeguroDomesticoRenovacionListaTrabajadores";
			} else {
				return "wizardSeguroDomesticoAltaListaTrabajadores";
			}
		}
		if(cotizacionGeneral.getErrorFormGeneral() != null && !cotizacionGeneral.getErrorFormGeneral().trim().isEmpty()) {
			model.addAttribute("error", cotizacionGeneral.getErrorFormGeneral());
			if(isRenovacion(tipoOperacion)) {
				return "wizardSeguroDomesticoRenovacionListaTrabajadores";
			} else {
				return "wizardSeguroDomesticoAltaListaTrabajadores";
			}
		}
		TramiteSeguroIvro tramiteGeneral = new TramiteSeguroIvro();
		if(isRenovacion(tipoOperacion)) {
			tramiteGeneral.setRenovacion(true);
			tramiteGeneral.setIdSeguroAnterior(seguro.getCveIdSeguroIvro());
		}
		tramiteGeneral.setBeneficiarios(beneficiarios.toArray(new Fisica[0]));
		tramiteGeneral.setCotizacion(cotizacionGeneral);
		tramiteGeneral.setCuetionarios(cuestionarios.toArray(new PersonaCuestionario[0]));
		if (cuestionarios.size() > 0) {
			tramiteGeneral.setAplicaCuestionario(true);
		}
		EstadoTramite estadoTramite = new EstadoTramite();
		estadoTramite.setIdEstadoTramitePersona(EstadoTramiteEnum.INICIADO.getId());
		tramiteGeneral.setEstadoTramite(estadoTramite);
		Modalidad modalidad = new Modalidad();
		modalidad.setIdModalidad(datosCalculo.getModalidad());
		tramiteGeneral.setModalidad(modalidad);
		
		if (!idPersona.equals(0L)) {
			empleador.setIdPersona(idPersona);
		}
		
		tramiteGeneral.setPersona(empleador);
		log.info("********** ID PERSONA: " + empleador.getIdPersona());
		TipoTramite tipoTramite = new TipoTramite();
		if (isRenovacion(tipoOperacion)) {
			tipoTramite.setIdTipoTramite(TipoTramiteEnum.RENOVACION_SEGURO_DOMESTICO.getCodigo());
		} else {
			tipoTramite.setIdTipoTramite(TipoTramiteEnum.COMPRA_SEGURO_DOMESTICO.getCodigo());
		}
		tramiteGeneral.setTipoTramite(tipoTramite);
		tramiteGeneral.setRegistroPatronal(registroPatronal);
		Solicitud solicitud = new Solicitud();
		solicitud.setTramite(new TramiteSeguroIvro[] {tramiteGeneral});
		OrigenSolicitud origenSolicitud = new OrigenSolicitud();
		origenSolicitud.setIdOrigenSolicitud(SeguroIvroUtil.getAmbiente(request));
		solicitud.setOrigenSolicitud(origenSolicitud);
		TipoSolicitud tipoSolicitud = new TipoSolicitud();
		tipoSolicitud.setIdTipoSolicitud(TipoSolicitudEnum.COMPRA_SEGURO.getId());
		solicitud.setTipoSolicitud(tipoSolicitud);
		EstadoSolicitud estadoSolicitud = new EstadoSolicitud();
		estadoSolicitud.setIdEstadoSolicitud(EstadoSolicitudEnum.REGISTRADA.getId().intValue());
		solicitud.setEstadoSolicitud(estadoSolicitud);
		solicitud.setFechaRegistro(new Date());

		log.info("********** ID PERSONA: " + ((TramiteSeguroIvro)solicitud.getTramite()[0]).getPersona().getIdPersona());

		//Se agrega usuario
		 // TODO
        String strUsuario;
        Long ambiente = SeguroIvroUtil.getAmbiente(request);
        if(!OrigenSolicitudEnum.PORTAL_CIUDADANO.getId().equals(ambiente)) {
        	UsuarioSSO sso = this.procesarUsuarioSSO(request);  
        	strUsuario = sso.getCurp();
        } else {
        	strUsuario = seguroIndividualServices.obtenerCurpPorIdPersona(empleador.getIdPersona());
        } 
        solicitud.setUsuario(strUsuario);

		Solicitud solicitudResultado = new Solicitud();
		try {
			solicitudResultado = callWebService(webServiceSolicitudSeguroIvro, solicitud, Solicitud.class, new Class[] {Solicitud.class, TramiteSeguroIvro.class});
		} catch(Exception e) {
			model.addAttribute("error", "Ocurri\u00F3 un error al intentar registrar la solicitud.");
			log.error("********** Ocurrio un error al intentar registrar la solicitud.", e);
			if (isRenovacion(tipoOperacion)) {
				return "wizardSeguroDomesticoRenovacionListaTrabajadores";
			} else {
				return "wizardSeguroDomesticoAltaListaTrabajadores";
			}
		}
		if(solicitudResultado.getErrorFormGeneral() != null && !solicitudResultado.getErrorFormGeneral().trim().isEmpty()) {
			model.addAttribute("error", solicitudResultado.getErrorFormGeneral());
			if (isRenovacion(tipoOperacion)) {
				return "wizardSeguroDomesticoRenovacionListaTrabajadores";
			} else {
				return "wizardSeguroDomesticoAltaListaTrabajadores";
			}
		}
		solicitud.setIdSolicitud(solicitudResultado.getIdSolicitud());
		solicitud.setNumSolicitud(solicitudResultado.getNumSolicitud());
		solicitud.getTramite()[0].setTramiteId(solicitudResultado.getTramite()[0].getTramiteId());
		log.info("********** ID SOLICITUD: " + solicitud.getIdSolicitud() + " **********");
		generarCadenaOriginalyFirma(solicitud, empleador, tipoOperacion, session);
		model.addAttribute("solicitud", solicitud);
		session.setAttribute("solicitud", solicitud);
		Persona persona = new Persona();
		persona.setIdPersona(empleador.getIdPersona());
		persona.setTipoPersona(empleador.getTipoPersona());
		persona.setRfc(empleador.getRfc());
//        Domicilio domicilio = seguroIndividualServices.getDomicilioPersona(persona);
//        model.addAttribute("domicilio", domicilio);
		
		Domicilio domicilio = registroPatronal.getCentrotrabajo();
		if(domicilio == null){
			try {
				domicilio = domicilioExternosServiceBusiness.consultarUltimoDomicilioParticilar(empleador.getIdPersona());
				if(domicilio == null){
					domicilio = obtenerDomicllioDeRegistroPatronal(empleador.getRfc());
					//registrar domicilio
					guardarPersonaDomicilio(empleador.getIdPersona(), domicilio);
				}
			} catch (DomicilioNoLocalizadoException e) {
				log.error("********** Ocurrio un error al consultar el domicilio.{}", e);
			} catch (MunicipioImssNoLocalizadoException e) {
				log.error("********** Ocurrio un error al consultar el domicilio{}", e);
			}
		}
		model.addAttribute("domicilio", domicilio);
        Persona personaMC = seguroIndividualServices.getMediosContactoPersona(persona);

		String correo = (String) session.getAttribute("correoDomestico");

		if(personaMC != null && correo!=null && personaMC.getMediosContacto() != null && personaMC.getMediosContacto().length>1){

			log.info("la persona tiene "+personaMC.getMediosContacto().length+"correos." );

			ArrayList<MedioContacto> mediosContacto = new ArrayList<MedioContacto>();

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


		model.addAttribute("personaMC", personaMC);

        return "wizardSeguroDomesticoComunResumen";
	}
	
	private Domicilio obtenerDomicllioDeRegistroPatronal(String rfc){
		try {
			Persona patron = obtenerPatronByRfc(rfc);
			if (patron.getRegistrosPatronales() != null && patron.getRegistrosPatronales().getRegistrosPatronal() != null) {
				log.info("********** El solicitante tiene " + patron.getRegistrosPatronales().getRegistrosPatronal().length + " registros patronales.");
				if(patron.getRegistrosPatronales().getRegistrosPatronal().length == 1){
					RegistroPatronal registroPatronal = patron.getRegistrosPatronales().getRegistrosPatronal()[0];
					if(registroPatronal.getCentrotrabajo() == null){
						log.info("********** El domicilio del registro patronal es nulo");
					}
					return registroPatronal.getCentrotrabajo();
				}
			} else {
				log.info("********** El solicitante no tiene centros de trabajo asociados");
			}
		} catch(Exception e) {
			log.error("Ocurrio un error la intentar obtener la informacion del contratante.", e);
		}
		return null;
	}
	
	private void guardarPersonaDomicilio(Long idPersona, Domicilio domicilioInicial) {
        log.info("solicitudResult:" + domicilioInicial.toString());
        log.info("IdPersona:" + idPersona);
        if(idPersona == null || domicilioInicial == null){
        	log.error("IdPersona is null?:" + (idPersona == null));
        	return;
        }
        try {
            seguroIndividualServices.guardarYAsociarDomiciliosPersona(domicilioInicial, idPersona);
        } catch (DomicilioNoValidoException ex) {
            log.error("Error al guardar el domicilio", ex);
        } catch (DomicilioNoLocalizadoException ex) {
            log.error("Error al guardar el domicilio", ex);
        } catch (EJBException ejb) {
            log.error("Excepcion no controlada al consumir el EJB", ejb);
        }
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
		model.addAttribute("solicitud", solicitud);
		return null;
	}
	
	@RequestMapping(value="/comunes/limpiarDatos")
	public @ResponseBody Map<String, ? extends Object> comunLimpiarDatos(Model model, HttpSession session) {
		model.addAttribute("empleador", getEmpleador());
		model.addAttribute("datosCalculo", getDatosCalculo());
		model.addAttribute("tramites", getTramites());
		model.addAttribute("solicitud", getSolicitud());
		model.addAttribute("empleado", getEmpleado());
		model.addAttribute("cotizacionEmpleado", getCotizacionEmpleado());
		model.addAttribute("nssTrabajador", getNssTrabajador());
		model.addAttribute("sueldoDiarioTrabajador", getSueldoDiarioTrabajador());
		model.addAttribute("seguro", getSeguro());
		model.addAttribute("tipoOperacion", getTipoOperacion());
		model.addAttribute("recargos", getRecargos());
		model.addAttribute("seguros", getSeguros());
		model.addAttribute("esVentanilla", getEsVentanilla());		// Ventanilla/Ciudadano
		model.addAttribute("tieneSeguros", getTieneSeguros());
		model.addAttribute("registroPatronal", getTieneSeguros());
		model.addAttribute("idPersona", getIdPersona());
		model.addAttribute("patron", getPatron());

		session.removeAttribute("empleador");
		session.removeAttribute("datosCalculo");
		session.removeAttribute("tramites");
		session.removeAttribute("solicitud");
		session.removeAttribute("empleado");
		session.removeAttribute("cotizacionEmpleado");
		session.removeAttribute("nssTrabajador");
		session.removeAttribute("sueldoDiarioTrabajador");
		session.removeAttribute("seguro");
		session.removeAttribute("tipoOperacion");
		session.removeAttribute("recargos");
		session.removeAttribute("seguros");
		session.removeAttribute("esVentanilla");
		session.removeAttribute("tieneSeguros");
		session.removeAttribute("registroPatronal");
		session.removeAttribute("idPersona");
		session.removeAttribute("patron");		
		
		return null;
	}

	@RequestMapping(value="/comunes/procesar-datos-firma", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> comunProcesarDatosFirma(Model model,
			@RequestBody FirmaElectronica firmaElectronica,
			HttpServletRequest request,
			HttpServletResponse response, HttpSession session,
			@ModelAttribute("solicitud") Solicitud solicitud,
			@ModelAttribute("esVentanilla") Boolean esVentanilla) {		// Ventanilla/Ciudadano
		
		Long idOrigen = SeguroIvroUtil.getAmbiente(request);
		log.info("********** EL ORIGEN DE LA SOLICITUD ES: " + idOrigen != null ? OrigenSolicitudEnum.getById(idOrigen).getDesc() : "" );
		if(esSolicitudInternet(idOrigen)) {
			log.info("********** ES PORTAL, SE RECIBEN LOS DATOS DE LA FIRMA ELECTRONICA **********");
			session.setAttribute(KEY_FIRMA_ELECTRONICA, firmaElectronica);
			solicitud.setCadenaOriginal(firmaElectronica.getCadenaOriginal());
			solicitud.setFirmadaDigitalmente(true);
			solicitud.setFirmaElectronica(firmaElectronica);
		}
		EstadoTramite estadoTramite = new EstadoTramite();
		estadoTramite.setIdEstadoTramitePersona(EstadoTramiteEnum.CERRADO.getId());
		estadoTramite.setDescripcion(EstadoTramiteEnum.CERRADO.name());
		solicitud.getTramite()[0].setEstadoTramite(estadoTramite);
		TramiteSeguroIvro tsi = (TramiteSeguroIvro)solicitud.getTramite()[0];
		solicitud.getTramite()[0].setDetalleTramiteXml(SeguroIvroUtil.getDetalleTramiteString(tsi));
		EstadoSolicitud estadoSolicitud = new EstadoSolicitud();
		estadoSolicitud.setIdEstadoSolicitud(EstadoSolicitudEnum.PENDIENTE_AUTORIZACION.getId().intValue());
		solicitud.setEstadoSolicitud(estadoSolicitud);
		UsuarioSSO usuarioSSO = procesarUsuarioSSO(request);
		solicitud.setUsuario(usuarioSSO.getCurp());
		solicitud.setUsuarioResponsable(usuarioSSO.getCurp());
		Map<String, Object> result = new HashMap<String, Object>();
		log.info("********** ID PERSONA: " + tsi.getPersona().getIdPersona());
		
		session.setAttribute(KEY_NSS_CIFRADO_FINAL, tsi.getPersona().getNssCifrado());
		Solicitud solicitudResult = seguroIndividualServices.guardaSolicitud(solicitud);
		if (hasError(solicitudResult)) { 
			result.put("error", solicitudResult.getErrorFormGeneral());
		} else {
			result.put("solicitud", solicitud);
		}
		return result;
	}
	
	@RequestMapping(value="/comunes/final")
	public String comunFinal(Model model,
			@ModelAttribute("solicitud") Solicitud solicitud) {
		if(((TramiteSeguroIvro)solicitud.getTramite()[0]).getCuetionarios() != null && ((TramiteSeguroIvro)solicitud.getTramite()[0]).getCuetionarios().length > 0) {
//			((TramiteSeguroIvro)solicitud.getTramite()[0]).setAplicaCuestionario(true);
			model.addAttribute("existenCuestionarios", Boolean.TRUE);
//			model.addAttribute("solicitud", solicitud);
		}
		return "wizardSeguroDomesticoComunFinal";
	}

	@RequestMapping(value="/comunes/generarComprobantes")
	@ResponseBody
	public void comunGenerarComprobantes(HttpServletResponse response,
			@ModelAttribute("solicitud") Solicitud solicitud,
			@ModelAttribute("seguros") SegurosIvro segurosIvro) {
		try {
			DocumentoSeguro documento = generadorComprobanteSeguro.generaComprobantes(segurosIvro, OrigenSolicitudEnum.INTERNET.getId());
			response.setContentType("application/pdf");
			response.addHeader("Content-Disposition", "attachment; filename=" + documento.getNombreArchivo());
			response.getOutputStream().write(documento.getArchivo());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@RequestMapping(value="/comunes/generarCuestionario")
	@ResponseBody
	public void comunGenerarCuestionarios(HttpServletResponse response,
			@ModelAttribute("solicitud") Solicitud solicitud,
			@ModelAttribute("seguros") SegurosIvro segurosIvro) {
		try {
			DocumentoSeguro cuestionarios = generadorComprobanteSeguro.generaCuestionarios(segurosIvro);
			response.setContentType("application/pdf");
			response.addHeader("Content-Disposition", "attachment; filename=" + cuestionarios.getNombreArchivo());
			OutputStream os = response.getOutputStream();
			os.write(cuestionarios.getArchivo());
			os.close();
		} catch(Exception e) {
			log.error("No se ha podido generar el documento de cuestionarios de salud para la solicitud " +  solicitud.getIdSolicitud(), e);
		}
	}
	
	@RequestMapping(value="/renovacion/init/{idPersona}/{rfc}/{idSeguro}", method = RequestMethod.GET)
	public String renovacionInit(Model model, SessionStatus sessionStatus, HttpSession session,
			@PathVariable Long idPersona,
			@PathVariable String rfc,
			@PathVariable Long idSeguro,
			@ModelAttribute("seguro") SeguroIvro seguro) {
		model.addAttribute("tipoOperacion", KEY_RENOVACION);
		Fisica persona = new Fisica();
		persona.setIdPersona(idPersona);
		TipoPersona tipoPersona = new TipoPersona();
		tipoPersona.setIdTipoPersona(TipoPersona.TIPO_PERSONA_FISICA);
		persona.setTipoPersona(tipoPersona);
		persona.setRfc(rfc);
		seguro = seguro == null ? new SeguroIvro() : seguro;
		seguro.setCveIdSeguroIvro(idSeguro);
		seguro.setEnRenovacion(true);
		seguro.setTitular(persona);
		DatosCalculoCuota dcc = new DatosCalculoCuota();
		try {
			dcc = callWebService(webServiceValidaRenovacionPatronDomestico, seguro, DatosCalculoCuota.class);
		} catch(Exception e) {
			model.addAttribute("error", "Ocurri\u00F3 un error al realizar la validaci\u00F3n del empleador.");
			log.error("********** Ocurrio un error al realizar la validacion del empleador. **********", e);
		}
		if(dcc.getErrorFormGeneral() != null && !dcc.getErrorFormGeneral().trim().isEmpty()) {
			model.addAttribute("error", dcc.getErrorFormGeneral());
			return "wizardSeguroDomesticoRenovacionInit";
		}	
		
		RegistroPatronal registroPatronal = new RegistroPatronal();
		registroPatronal.setNumeroRegistroPatronal(dcc.getNumeroRegistroPatronal());
		
		model.addAttribute("empleador", persona);
		model.addAttribute("idPersona", idPersona);
		model.addAttribute("datosCalculo", dcc);
		model.addAttribute("registroPatronal", registroPatronal);
		model.addAttribute("seguro", seguro);
		return "wizardSeguroDomesticoRenovacionInit";
	}
	
	@RequestMapping(value="/renovacion/listaTrabajadores")
	public String renovacionListaTrabajadores(Model model,
			HttpSession session,
			@ModelAttribute("tipoOperacion") String tipoOperacion) {
		datosSesion(session, tipoOperacion);
		return "wizardSeguroDomesticoRenovacionListaTrabajadores";
	}
	
	@RequestMapping(value="/renovacion/actualizarSalario")
	public String renovacionActualizarSalario(Model model, HttpServletRequest request) {
		Integer inputIndex = Integer.parseInt(request.getParameter("inputIndex"));
		model.addAttribute("inputIndex", inputIndex);
		return "wizardSeguroDomesticoRenovacionActualizarSalario";
	}

	private void generarCadenaOriginalyFirma(Solicitud solicitud, Persona persona, String tipoOperacion, HttpSession session) {
		Locale locMEX = new Locale("es", "MX");
		DateFormat dateFormat = new SimpleDateFormat("dd 'de' MMMM yyyy, HH:mm:ss", locMEX);
		Date fecha = Calendar.getInstance().getTime();
		FirmaElectronica datosEntradaFirma = new FirmaElectronica();
		StringBuffer contenidoAFirmar = new StringBuffer();
		// Inicio
		contenidoAFirmar.append("||");
		contenidoAFirmar.append("Invocante:portalimssdigital|");
		// Denominacion del Tramite o servicio
		contenidoAFirmar.append("Tramite:");
		if (isRenovacion(tipoOperacion)) {
			contenidoAFirmar.append(DESC_RENOVACION_TRAMITE_SOLICITUD).append("|");
		} else {
			contenidoAFirmar.append(DESC_ALTA_TRAMITE_SOLICITUD).append("|");
		}
		// Fecha Electronica
		String strFechaElectronica = dateFormat.format(fecha);
		contenidoAFirmar.append("Fecha:");
		contenidoAFirmar.append(strFechaElectronica).append("|");
		datosEntradaFirma.setFechaElectronicaFormateada(strFechaElectronica);
		datosEntradaFirma.setFechaElectronica(fecha);
		// Folio
		contenidoAFirmar.append("Folio:");
		contenidoAFirmar.append(solicitud.getNumSolicitud()).append("|");
		// RFC
		contenidoAFirmar.append("RFC:");
		contenidoAFirmar.append(persona.getRfc()).append("|");
		datosEntradaFirma.setRfc(persona.getRfc());
		session.setAttribute(KEY_CADENA_ORIGINAL, contenidoAFirmar.toString());
		session.setAttribute(KEY_FIRMA_ELECTRONICA, datosEntradaFirma);
	}

	private void datosSesion(HttpSession session, final String tipoOperacion) {
		List<Integer> listTipoTramite = new ArrayList<Integer>();
		if(isRenovacion(tipoOperacion)) {
			listTipoTramite.add(TipoTramiteEnum.RENOVACION_SEGURO_DOMESTICO.getCodigo());
			session.setAttribute(KEY_TIPO_SOLICITUD, TipoSolicitudEnum.COMPRA_SEGURO.getId());
			session.setAttribute(DESC_TIPO_SOLICITUD, TipoTramiteEnum.RENOVACION_SEGURO_DOMESTICO.name());
		} else {
			listTipoTramite.add(TipoTramiteEnum.COMPRA_SEGURO_DOMESTICO.getCodigo());
			session.setAttribute(KEY_TIPO_SOLICITUD, TipoSolicitudEnum.COMPRA_SEGURO.getId());
			session.setAttribute(DESC_TIPO_SOLICITUD, TipoTramiteEnum.COMPRA_SEGURO_DOMESTICO.name());
		}
		session.setAttribute(KEY_TIPO_TRAMITE, listTipoTramite);
	}

	private boolean isRenovacion(String tipoOperacion) {
		return tipoOperacion != null && tipoOperacion.trim().equalsIgnoreCase(KEY_RENOVACION);
	}
	
	void validaRestriccionAutoempleo(String nssEmpleado, String nssEmpleador)throws IvroException{
		if (nssEmpleado.equalsIgnoreCase(nssEmpleador)) {
			throw new IvroException("No es posible asegurarse como su propio empleado");
		}
	}
	
	/**
	    * Valid ws resp error.
	    *
	    * @param error the error
	    * @throws IVROServiceException the IVRO service exception
	    */
	private void validWSRespError(String error) throws IVROServiceException {
	   if (error!=null) {
		   if(error.length()>0  && !error.equals("")) {
			   throw new IVROServiceException(error);
		   }
	   }
			
	}

	/***
	 * Iniciar tramites para Ventanilla, Ciudadano
	 * 
	 * @param model
	 * @param sessionStatus
	 * @param session
	 * @param idPersona
	 * @param rfc
	 * @param nssCifrado
	 * @return
	 */
	@RequestMapping(value="/ventanilla/init/{idPersona}/{rfc}/{nssCifrado}", method = RequestMethod.GET)
	public String ventanillaInit(Model model, SessionStatus sessionStatus, HttpSession session,
			@PathVariable Long idPersona,
			@PathVariable String rfc,
			@PathVariable String nssCifrado) {
		
		String nss = null;
		if(!nssCifrado.equals("-1")){			
			Map<String, Object> result = descifrarNss(session,"-1", nssCifrado);
			nss = (String)result.get(KEY_NSS_DOMESTICO);
			nssCifrado = (String)result.get(KEY_NSS_CIFRADO_DOMESTICO);
		}else{
			nssCifrado = "-1";
		}
		
		Fisica persona = new Fisica();
		persona.setIdPersona(idPersona);
		TipoPersona tipoPersona = new TipoPersona();
		tipoPersona.setIdTipoPersona(TipoPersona.TIPO_PERSONA_FISICA);
		persona.setTipoPersona(tipoPersona);
		persona.setRfc(rfc);
		persona.setNssCifrado(nssCifrado);
		persona.setNss(nss);
		
		Persona personaTmpConsultaWS = new Persona();
		personaTmpConsultaWS.setIdPersona(idPersona);
		personaTmpConsultaWS.setTipoPersona(tipoPersona);
		personaTmpConsultaWS.setRfc(rfc);
		
		model.addAttribute("esVentanilla", Boolean.TRUE);
		model.addAttribute("empleador", persona);
		SegurosIvro seguros = new SegurosIvro();
		try {
			seguros = callWebService(webServiceConsultaSeguroDomestico, personaTmpConsultaWS, SegurosIvro.class, new Class[] {Persona.class});
		} catch(Exception e) {
			log.error("Error al intentar leer la lista de seguros de la persona.", e);
		}
		if(seguros.getSeguroIvro() != null && seguros.getSeguroIvro().length > 0) {
			try{
			 for(int x=0;seguros.getSeguroIvro().length>x;x++){
				 //SeguroIvro[] seguro = seguros.getSeguroIvro();
				 boolean renovacion = SeguroIvroUtil.puedeRenovarSeguro(seguros.getSeguroIvro()[x]);
				 seguros.getSeguroIvro()[x].setEnRenovacion(renovacion);
			 }
			 }catch (Exception e) {
				 log.error("********** Ocurrio un error al intentar setear si es renovacion", e);
			}
			Map<Long, SeguroIvro> mapaSeguros = seguroIvroRenovacionUtil.mapaSeguros(seguros);
			session.setAttribute(KEY_SEGUROS_EMPLEADOS, mapaSeguros);
			model.addAttribute("segurosDomesticos", seguros);
			model.addAttribute("tieneSeguros", Boolean.TRUE);
			
			Map<Long, String> mapaAseguamiento = new HashMap<Long, String>();
			Map<Long, String> mapaRenovacon = new HashMap<Long, String>();
			seguroIvroRenovacionUtil.generaHashMapsFechas(mapaSeguros.values(), mapaAseguamiento, mapaRenovacon);
			model.addAttribute("mapaFechaAseguamiento", mapaAseguamiento);
		    model.addAttribute("mapaFechaRenovacon", mapaRenovacon);
			
			return "wizardSeguroDomesticoVentanillaListaSeguros";
		} else {
			model.addAttribute("tieneSeguros", Boolean.FALSE);
			return altaInit(model, sessionStatus, session, idPersona, rfc, nssCifrado != null ? nssCifrado : "-1");
		}
	}
	
	public boolean esSolicitudInternet(Long origenSolicitud){
		boolean esInternet=false;
		OrigenSolicitudEnum origen = OrigenSolicitudEnum.getById(origenSolicitud);
		if(origen.equals(OrigenSolicitudEnum.INTERNET)){
			esInternet = true;
		}
		return esInternet;		
	}
	
	private Map<String, Object> descifrarNss(HttpSession session, 
			String valorRetornoErrorNssCifrado, String nssCifrado){		
		Map<String, Object> result = new HashMap<String, Object>();
		String nss = null;
		try {
			nss = Base64Cipher.descrifrar(nssCifrado);
		} catch (InvalidKeyException e1) {
			nssCifrado = valorRetornoErrorNssCifrado;
			e1.printStackTrace();
		} catch (IllegalBlockSizeException e1) {
			nssCifrado = (String)session.getAttribute(KEY_NSS_CIFRADO_FINAL);
			if(StringUtils.isNotEmpty(nssCifrado) && StringUtils.isNotBlank(nssCifrado)){
				try {
					nss = Base64Cipher.descrifrar(nssCifrado);					
				} catch (Exception e) {
					nssCifrado = valorRetornoErrorNssCifrado;
					e.printStackTrace();
				}
			}else{
				nssCifrado = valorRetornoErrorNssCifrado;
				e1.printStackTrace();
			}
		} catch (BadPaddingException e1) {
			nssCifrado = valorRetornoErrorNssCifrado;
			e1.printStackTrace();
		} catch (IOException e1) {
			nssCifrado = valorRetornoErrorNssCifrado;
			e1.printStackTrace();
		}
		
		if(StringUtils.isNotEmpty(nssCifrado) 
			&& StringUtils.isNotBlank(nssCifrado) && !nssCifrado.equals("-1")){
				session.setAttribute(KEY_NSS_CIFRADO_FINAL, nssCifrado);
		}else{
			session.removeAttribute(KEY_NSS_CIFRADO_FINAL);
		}
		
		result.put(KEY_NSS_DOMESTICO, nss);
		result.put(KEY_NSS_CIFRADO_DOMESTICO, nssCifrado);
		
		return result;
	}
	
	
}