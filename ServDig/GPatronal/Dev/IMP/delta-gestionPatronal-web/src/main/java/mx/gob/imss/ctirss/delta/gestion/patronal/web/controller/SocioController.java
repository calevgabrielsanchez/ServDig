/**
 *  
 *  @Cliente: Instituto Mexicano del Seguro Social
 *  @Autor: Miguel Alejandro Joaqun Rodriguez
 *  @Proyecto: smod-web
 *  @Archivo:SocioController.java
 *  @Paquete:mx.gob.imss.ctirss.smod.solicitud.controller
 *  @Fecha: 10/04/2012
 */
package mx.gob.imss.ctirss.delta.gestion.patronal.web.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import mx.gob.imss.ctirss.delta.framework.base.controller.AbstractController;
import mx.gob.imss.ctirss.delta.framework.base.exception.AbstractException;
import mx.gob.imss.ctirss.delta.framework.base.exception.GestionPatronalBusinessException;
import mx.gob.imss.ctirss.delta.framework.base.model.AbstractModel;
import mx.gob.imss.ctirss.delta.framework.paginador.model.DatosEntradaPaginador;
import mx.gob.imss.ctirss.delta.framework.paginador.model.DatosSalidaPaginador;
import mx.gob.imss.ctirss.delta.gestion.patronal.service.interfaces.SujetoObligadoServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.patronal.service.interfaces.socios.SocioServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.patronal.service.interfaces.solicitud.SolicitudServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.patronal.web.controller.paginator.MedioContactoDataTable;
import mx.gob.imss.ctirss.delta.gestion.patronal.web.controller.paginator.SociosDataTable;
import mx.gob.imss.ctirss.delta.gestion.patronal.web.utils.SocioValidator;
import mx.gob.imss.ctirss.delta.model.Usuario;
import mx.gob.imss.ctirss.delta.model.domicilio.EntidadFederativa;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Fisica;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Moral;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.TipoPersona;
import mx.gob.imss.ctirss.delta.model.gestion.medio.contacto.MedioContacto;
import mx.gob.imss.ctirss.delta.model.gestion.medio.contacto.TipoMedioContacto;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.EscrituraConstitutiva;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.ItemClasificacion;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.Socio;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.SujetoObligado;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.TipoAccionAfectacionEnum;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.TipoPersonaFiscal;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.TipoSocioEnum;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.TipoSolicitudEnum;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.TipoTramiteEnum;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.EstadoSolicitudEnum;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.Solicitud;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.EstadoTramiteEnum;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TramiteSujetoObligado;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindException;
import org.springframework.validation.Errors;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author Miguel Alejandro Joaqun Rodriguez
 *
 */
@Controller
@RequestMapping(value="/socios")
public class SocioController extends AbstractController {

	@Autowired
	SocioServiceBusinessRemote socioService;
	
	@Autowired
	private SujetoObligadoServiceBusinessRemote sujetoObligadoService;
	
	@Autowired
	SolicitudServiceBusinessRemote solicitudServiceBusiness;
		
	
	private String mensajeActualizacion="Su solicitud se ha enviado para ser procesada.<BR>" +
	"El número de folio de la solicitud es el siguiente[ varNumeroFolio ]" +
	"<BR> Por favor tome nota de su número de folio para su seguimiento";
	
	@RequestMapping(method=RequestMethod.GET)
	public String sociosInit(Model model, HttpServletRequest request) {
		
		Socio nuevoSocio = new Socio();
		
		model.addAttribute(nuevoSocio);
		model.addAttribute("socio", nuevoSocio);
		
		return "nuevoSocio";
	}
	
	
	@RequestMapping(method=RequestMethod.POST)
	//public String  inicio(@RequestParam String regPat, Model model, HttpSession session){
	public String inicio(@ModelAttribute SujetoObligado sujetoObligado, 
			@RequestParam("idSolicitud") String idSolicitud, Model model, HttpSession session){
		
		String l = (String) session.getAttribute("idSolicitud");
		
//		String idSolicitudRLTemp = null;
//		
//		if (l == null && !StringUtils.isBlank(idSolicitud)){
//			idSolicitudRLTemp = idSolicitud;
//		}
		
		if (sujetoObligado.getNumeroRegistroPatronal() == null){
			if (idSolicitud != null && !StringUtils.isBlank(idSolicitud)) {
				
				log.debug("OBTAINING SUJETOOBLIGADO DATA for " + this.getClass().getName() + "...");
				
				Solicitud sol = solicitudServiceBusiness
						.consultarSolicitudPorId(Long.valueOf(idSolicitud));
				TramiteSujetoObligado tso = (TramiteSujetoObligado) sol
						.getTramites().get(0);
				sujetoObligado = tso.getSujetoObligado();
				
				model.addAttribute("idSolicitud", idSolicitud);
				session.setAttribute("idSolicitud", idSolicitud);
				
				log.debug("OBTAINED SUJETOOBLIGADO DATA for " + this.getClass().getName() + ".");
			
			}
		} else {
			sujetoObligado = sujetoObligadoService
					.obtenerDetalleSujetoObligadoActividadEconomica(sujetoObligado);
			
			model.addAttribute("idSolicitud", l);
			session.setAttribute("idSolicitud", l);
		}
		
		
		
		Socio socio = new Socio(); 
		Usuario usuario = (Usuario) session.getAttribute("usuario");
		
		TipoTramiteEnum t=TipoTramiteEnum.DEFAULT;
		
		socio.setCveIdPatronSujetoObligado(sujetoObligado.getCveIdSujetoObligado());
		
		session.setAttribute("usuario", usuario); // necesario para la generaicon del tramite
		session.setAttribute("sujetoObligado", sujetoObligado);
		session.setAttribute("socio", socio); // TODO puede que no tenga que cargarse a la session.
		
//		model.addAttribute("sujetoObligado", sujetoObligado);
		model.addAttribute(socio);
		model.addAttribute("idTramite", t);
    	
		return "socios";
	}
    
    
    @SuppressWarnings({ "unchecked" })
	@RequestMapping(value="/paginarForSession", method=RequestMethod.POST)
    public @ResponseBody DatosSalidaPaginador<Socio> paginarForSession(@RequestBody SociosDataTable params, HttpSession session) {
		
    	boolean cargarSociosDesdeLaBase = true;
		List<Socio> socios = new ArrayList<Socio>();
		
		Object sujetoObj = session.getAttribute("sujetoObligado");
		SujetoObligado sujetoTramiteSocios = sujetoObj != null ? (SujetoObligado)sujetoObj : null;
    	
		
		Long idSolicitud = params.getoForm().getIdSocio();
		
		
		
		String attribute = (String) session.getAttribute("idSolicitud");
		if (attribute != null && !"".equals(attribute)) {
			idSolicitud = Long.parseLong(attribute);
		}

		
		//Se obtiene la información referente al trámite

		if (idSolicitud != null) {
			Solicitud sol = solicitudServiceBusiness.consultarSolicitudPorId(Long.valueOf(idSolicitud));
			TramiteSujetoObligado tso = (TramiteSujetoObligado) sol.getTramites().get(0);
			sujetoTramiteSocios = tso.getSujetoObligado();
			socios.addAll(sujetoTramiteSocios.getSocios());
			//session.setAttribute("idSolicitud", idSolicitud);
			System.out.println("SE SUBIO ID SOLICITUD");
			log.debug("SUJETO OBLIGADO: [" + sujetoTramiteSocios + "]");
			cargarSociosDesdeLaBase = false;
		}
    	    	
		DatosSalidaPaginador<Socio> output = new DatosSalidaPaginador<Socio>();
		DatosEntradaPaginador<Socio> input = new DatosEntradaPaginador<Socio>();
		input.parserArray(params.getAoData());
		input.setModelo(params.getoForm());
		try {
			output.setsEcho(input.getsEcho());
			if (cargarSociosDesdeLaBase){
				if (sujetoTramiteSocios.getSocios() != null){
					for (Socio socio : sujetoTramiteSocios.getSocios()) {
						if (socio.getFecRegistroBaja() == null){ // removemos de la lista los regsitros dados de baja
							socios.add(socio);
						}
					}
				}
			}
			
			sujetoTramiteSocios.setSocios(socios);
			output.setAaData(sujetoTramiteSocios.getSocios());
			
		} catch (Exception e) {
			output = new DatosSalidaPaginador<Socio>();
			output.setAaData(params.getAoData());
			log.error(input, e);
		}
		session.setAttribute("sujetoObligado", sujetoTramiteSocios);
		return output;
        
    }
	
	

    
    @RequestMapping(value="/paginar", method=RequestMethod.POST)
    public @ResponseBody DatosSalidaPaginador<Socio> pagina(@RequestBody SociosDataTable aoData ) {
    	
    	DatosEntradaPaginador<Socio> send = new DatosEntradaPaginador<Socio>();
        send.parserArray(aoData.getAoData());
        send.setModelo(aoData.getoForm());
        DatosSalidaPaginador<Socio> reply = null;
		try {
			reply = socioService.paginarSocios(send);
		} catch (Exception e) {
			e.printStackTrace();
		}
        reply.setsEcho(send.getsEcho());
        System.err.println("reply; " + reply);
        return reply;
        
    }
	
	/**
	 * Metodo para obtener los productos de la solicitud.
	 * @param idSolicitud
	 * @param model
	 * @return
	 */
    @RequestMapping(value="/consulta", method=RequestMethod.GET)
	public String getSociosConsulta (@PathVariable String cveIdPatronSujetoObligado , Model model , HttpSession session) {
    	Socio socio = new Socio();
    	socio.setCveIdPatronSujetoObligado(new Long(cveIdPatronSujetoObligado));
    	model.addAttribute("socio", socio);
    	return "sociosConsulta";
	}
    
    @RequestMapping(value="/paginarConsulta", method=RequestMethod.POST)
    public @ResponseBody DatosSalidaPaginador<Socio> paginaConsulta(@RequestBody SociosDataTable aoData ) {
    	
    	DatosEntradaPaginador<Socio> send = new DatosEntradaPaginador<Socio>();
        send.parserArray(aoData.getAoData());
        send.setModelo(aoData.getoForm());
        DatosSalidaPaginador<Socio> reply = new DatosSalidaPaginador<Socio>();//actividadEconomicaService.paginarSocios(send);        
        reply.setsEcho(send.getsEcho());
        System.err.println("reply; " + reply);
        return reply;
        
    }
    

    
    /**
     * 
     * @param idProductoServicio
     * @return
     */
    @RequestMapping(value="/get" , method=RequestMethod.GET)
	public @ResponseBody Socio getSocio(@RequestParam String  idSocio, HttpSession session){
    	
    	Socio socio = null;
    	this.log.info("Obteniendo  el elemento [" + idSocio +" ]");
    	List<Socio> socios = ((SujetoObligado) session.getAttribute("sujetoObligado")).getSocios();
    	
    	for (Socio socio2 : socios) {
			if (idSocio.equals(socio2.getIdSocio().toString())){
				socio = socio2;
				break;
			}
		}
    	
    	return socio;
	}
    
	@RequestMapping(value = "/agregar", method = RequestMethod.POST)
	public @ResponseBody
	Map<String, ? extends Object> agregar(@RequestBody Socio oForm, HttpServletResponse response, HttpSession session) {

		/*log.info("Socio [" + oForm +" ]");
		Map result = new HashMap<String, Object>();
		if(oForm != null){
			
			log.info("Socio.rfc [" + oForm.getRfc() +" ]");
			log.info("Socio.rfc [" + oForm.getIdPersona() +" ]");
			Errors errors = new BindException(oForm, "model");
			 
	
			try {
				oForm = socioService.agregarSocio(oForm);
			} catch (Exception e) {
				e.printStackTrace();
				this.procesarErrorDeNegocio(new AbstractException(e.getMessage()), result, response);
				return result;
			}
			
			if (oForm.getIdSocio() == null) {
				result.put("errors", "No se inserto correctamente");
			}
		}
		result.put("oModel", oForm);
		return result;*/
		
		SujetoObligado sujetoObligado = (SujetoObligado) session.getAttribute("sujetoObligado");

		
		Map<String, Object> result = new HashMap <String, Object>();
    	Errors errors = new BindException(oForm, "model");
		
		
		
		/*
		 * this.procesarErrorDeNegocio(new AbstractException("No se ha seleccionado la persona a registrar como Representante Legal"), result, response);
			return result;
		 */
		

		// validamos datos requeridos
    	new SocioValidator().validate(oForm, errors);
		if (errors.hasErrors()) {
			this.procesaErroresDeCaptura(errors, result, response);
			return result;
		}
		
		
    	
    	try {
    		// validamos que no exista en lista (si hay elementos)
    		List<Socio> socios = sujetoObligado.getSocios();
    		Long cveIdPersona = null;
    		
    		if (socios != null){
    			for (Socio socio1 : socios) {
    				
        			cveIdPersona = socio1.getIdPersona();
        			
        			if (cveIdPersona != null){
        				if (cveIdPersona.toString().equals(oForm.getIdPersona().toString())){
        					this.procesarErrorDeNegocio(new AbstractException("Error al agregar el socio, ya se ha registrado anteriormente esta persona como socio."), result, response);
        					return result;
        				}
        			}
    			}
    		} else {
    			sujetoObligado.setSocios(new ArrayList<Socio>());
    		}
			
    		
			// agregamos este representatne legal al suejto obligado.

			try {
				sujetoObligado.getSocios().add(oForm);
			} catch (Exception e) {
				e.printStackTrace();
				this.procesarErrorDeNegocio(new AbstractException(e.getMessage()), result, response);
				return result;
			}
			
			result.put("oModel", oForm);
		} catch (Exception e) {
			this.procesarErrorDeNegocio(new AbstractException(e.getMessage()), result, response);
			return result;
		} 

    	return result;
	}
    
	
    
	/**
	 * Metodo para eliminar el registro
	 * 
	 * @param oForm
	 * @return
	 */
	@RequestMapping(value = "/eliminar", method = RequestMethod.GET)
	public @ResponseBody
	Map<String, ? extends Object> eliminar(
			@RequestParam String cveIdPersona,
			@RequestParam String cveIdPatronSujetoObligado, 
			HttpServletResponse response,
			HttpSession session) {
		
		/*Map result = new HashMap < String , Object>();
            this.log.info("Eliminando el elemento, socio[" + idSocio +"," +cveIdPatronSujetoObligado+ "]");
            Socio socio = new Socio();
            socio.setIdSocio(new Long(idSocio));
           // socio.setCveIdPatronSujetoObligado(new BigDecimal(cveIdPatronSujetoObligado));
            if (socio.getEsPersonaFisica()) {
            	socioService.eliminarSocioFisico(socio);
            	
            } else {
            	socioService.eliminarSocioMoral(socio);
            }
            result.put("oModel", socio);
        return result;*/
		
		
		Map<String, Object> result = new HashMap<String, Object>();
		
		SujetoObligado sujetoObligado = (SujetoObligado) session.getAttribute("sujetoObligado");
		
		try {
			
			List<Socio> socios = sujetoObligado.getSocios();
			if (socios.size() > 1){
				for (Socio iterable_element : socios) {
					if (cveIdPersona.equals(iterable_element.getIdPersona().toString())){
						socios.remove(iterable_element);
						break;
					}
				}
				//sujetoObligado.setRepresentantesLegales(representantesLegales);
			}else{
				this.procesarErrorDeNegocio(new AbstractException("Error al dar de baja al socio seleccionado, debe contar al menos con un registro."), result, response);
				return result;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			result.put("errors", "Error al eliminar el socio");
		}	
        return result;


	}
	

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value="/modificar" , method=RequestMethod.POST)
	public @ResponseBody Map<String, ? extends Object> modificar(@RequestBody Socio oForm,
			@RequestParam String cveIdPatronSujetoObligado,
			HttpServletResponse response) {

		Map result = new HashMap<String, Object>();
		Errors errors = new BindException(oForm, "model");
		new SocioValidator().validate(oForm, errors);
		if (errors.hasErrors()) {
			this.procesaErroresDeCaptura(errors, result, response);
			return result;
		}

		//oForm.setCveIdPatronSujetoObligado(new BigDecimal(cveIdPatronSujetoObligado));
			oForm = socioService.modificarSocio(oForm);
			result.put("oModel", oForm);
			if (oForm.getIdSocio() == null) {
				result.put("errors", "No se modifico correctamente");
			}

		return result;
	}
	
	@RequestMapping(value = "/finalizar", method = RequestMethod.POST)
	public void finalizar(
			//@RequestParam String idRepresentanteLegal,
			//@RequestParam String cveIdPatronSujetoObligado,
			//@RequestParam String tipoPersonaFiscal,
			HttpServletResponse response,
			HttpSession session) {
		
		Usuario usuario = (Usuario)session.getAttribute("usuario");
		Object obj = session.getAttribute("idSolicitud");
		String idSolicitud = obj!= null ? ((Long)obj).toString() : null;
		
		Map<String, Object> result = new HashMap<String, Object>();
		SujetoObligado sujetoObligado = (SujetoObligado) session.getAttribute("sujetoObligado");
		try {
			boolean afectar=administrarSolicitud(idSolicitud, usuario, sujetoObligado, TipoTramiteEnum.ACTUALIZACION_SOCIO, TipoSolicitudEnum.ACTUALIZACION_DE_SOCIO, result, session);
			if(afectar){
				
				List<Socio> socios = sujetoObligado.getSocios();
				socioService.actualizarSocios(socios);
			}
		} catch (GestionPatronalBusinessException e) {
			e.printStackTrace();
		}
		
	}
	
	private boolean administrarSolicitud(String idSolicitud, Usuario usuario, SujetoObligado sujetoObligado, TipoTramiteEnum tipoTramite, TipoSolicitudEnum tipoSolicitud, Map<String, Object> result, HttpSession session) throws GestionPatronalBusinessException{
		
		boolean afectar = false;
		if(idSolicitud==null || "vacio".equals(idSolicitud)){
			log.debug(" -- SE REGISTRA UNA NUEVA SOLICITUD [ " + sujetoObligado + " ]");
			Solicitud solicitud = solicitudServiceBusiness.generarSolicitud(tipoSolicitud, 
					EstadoSolicitudEnum.REGISTRADA, usuario, tipoTramite, 
					EstadoTramiteEnum.INICIADO, sujetoObligado, false, false);
			solicitudServiceBusiness.actualizarSolicitud(solicitud, EstadoTramiteEnum.ACTIVO, sujetoObligado);
			
			//Se agregan los datos a sesion para la impresion de reporte
			session.setAttribute("idSolicitud", solicitud.getSolicitudId());
			session.setAttribute("sujetoObligado", sujetoObligado);
			
			result.put("mensajeExito", agregarFolioAMensaje(solicitud.getNoFolioSolicitud()));
		}else{
			log.debug("ACTUALIZAR SOLICITUD -- SE ACTUALIZARA LA SOLICITUD ACTUAL [ " + sujetoObligado + " ]");
			Solicitud solicitud = new Solicitud();
			solicitud.setSolicitudId(Long.valueOf(idSolicitud));
			solicitudServiceBusiness.actualizarSolicitud(solicitud, EstadoTramiteEnum.CERRADO, sujetoObligado);
			
			session.removeAttribute("idSolicitud");
			session.removeAttribute("sujetoObligado");
					
			afectar = true;
		}
		return afectar;
	}
	
	/**
	 * Agrega el número de folio al mensaje de confirmación de creación de solicitud
	 * @param noFolio
	 * @return String Mensaje de confirmación con el número de folio
	 */
	private String agregarFolioAMensaje(String noFolio){
		return mensajeActualizacion.replaceFirst("varNumeroFolio", noFolio);
	}
	
	
	/********************************************************************
	 ********************************************************************
	 *
	 * SEGMENTO PARA EL NUEVO FLUJO BASE DE Socios
	 *  
	 ********************************************************************
	 ********************************************************************/
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/fb/paginarSocios", method = RequestMethod.POST)
	public @ResponseBody
	DatosSalidaPaginador<Socio> paginarSocios(
			@RequestBody SociosDataTable params, HttpSession session) {				
				
		/*
		 * para no cambiarla firma del metodo obtenerSociosPorSujetoObligado, se manda el id de la persona del patron SO en lugar de la cve_id_patron_sujeto_obligado
		 */
		Long idPatronSO = (Long) session.getAttribute("cveIdPatronSO");

		
		DatosSalidaPaginador<Socio> output = new DatosSalidaPaginador<Socio>();
		DatosEntradaPaginador<Socio> input = new DatosEntradaPaginador<Socio>();
		
		input.parserArray(params.getAoData());
		input.setModelo(params.getoForm());
		
		
		log.info("/**** ID Patron ::"+ idPatronSO+" ****/");
		List<Socio> sociosList = this.socioService.obtenerSociosPorSujetoObligado(idPatronSO);
		
		for(Socio socio:sociosList)
			log.info("/**** Patron Domicilio ::"+ socio.getNombres()+" "+ socio.getDomicilioFiscal() +" ****/");
		
		try {
			output.setsEcho(input.getsEcho());
			output.setAaData(sociosList);
			output.setiTotalRecords(sociosList.size());
			/*
	         *  subimos a sesion esta lista de socios proveniente de la base para realizar validaciones y
	         *  para ratificar 
	         */
	        session.setAttribute("sociosListFromDBForValidations", sociosList);
		} catch (Exception e) {
			output = new DatosSalidaPaginador<Socio>();
			output.setAaData(params.getAoData());
			log.error(input, e);
		}
		return output; 
	}
	
	
	@RequestMapping(value="/fb/paginarForSession", method=RequestMethod.POST)
    public @ResponseBody DatosSalidaPaginador<Socio> paginarForSession1(@RequestBody SociosDataTable params, HttpSession session, 
    		@RequestParam("idSolicitud") Long idSolicitud,
    		HttpServletRequest request) {
		log.debug("Cargando el grid de tramites para socios");
		for (Object obj:params.getAoData()){
			log.debug(">>>>>:"+obj);
		}
		
		DatosEntradaPaginador<Socio> input = new DatosEntradaPaginador<Socio>();
		input.parserArray(params.getAoData());
		input.setModelo(params.getoForm());
		
		DatosSalidaPaginador<Socio> output=null;
		SujetoObligado sujetoTramiteSocios = (SujetoObligado) session.getAttribute(TipoTramiteEnum.ACTUALIZACION_SOCIO.name());
		
		if (sujetoTramiteSocios!=null){
			if (sujetoTramiteSocios.getMoral() != null){
				log.debug(">>> Hay datos de trámite en la session de socios para persona moral:"+sujetoTramiteSocios.getMoral().getSocios());
			}
			if (sujetoTramiteSocios.getFisica() != null){
				log.debug(">>> Hay datos de trámite en la session de socios para persona fisica:"+sujetoTramiteSocios.getFisica().getSocios());
			}
			 
			
			if (sujetoTramiteSocios.getFisica()!=null){
				if (sujetoTramiteSocios.getFisica().getSocios()!=null){
					output = new DatosSalidaPaginador<Socio>();
					output.setsEcho(input.getsEcho());
					output.setAaData(sujetoTramiteSocios.getFisica().getSocios());
				}					
			}
			if (sujetoTramiteSocios.getMoral()!=null){
				if (sujetoTramiteSocios.getMoral().getSocios()!=null){
					output = new DatosSalidaPaginador<Socio>();
					output.setsEcho(input.getsEcho());
					output.setAaData(sujetoTramiteSocios.getMoral().getSocios());
				}				
			}
		}
		if (sujetoTramiteSocios==null){
			log.debug(">>> No hay datos de trámite en la session, inicializando para Socios...");
			Usuario usuario = (Usuario) session.getAttribute("usuario");
			output = socioService.obtenerMovimientosDeTramite(input, usuario, idSolicitud);
			//Se guarda en Session la lista:			
			for(Socio obj : output.getAaData()){
				log.debug("Socio: "+obj);
			}
			output.setsEcho(input.getsEcho());
			
			log.debug(">>> Inicialización de obj. tramite para Socios completa.");
		}
		return output;
	}

	
	@RequestMapping(value="/fb/consultarSocio", method=RequestMethod.GET)
	public String consultarRepLegal (Model model , HttpSession session) {
		
		SujetoObligado sujetoObligado = new SujetoObligado();
		
		sujetoObligado.setCveIdSujetoObligado(5L);
		
		sujetoObligado = sujetoObligadoService.obtenerDetalleSujetoObligadoActividadEconomica(sujetoObligado);
//		sujetoTramiteSocios = solicitudServiceBusiness.con
		
		session.setAttribute("sujetoObligado", sujetoObligado);
		session.setAttribute(TipoTramiteEnum.ACTUALIZACION_SOCIO.name(), sujetoObligado);
    	    	
		return "alfredoDef";
	}
	
	@RequestMapping(value = "/fb/agregarSocio", method = RequestMethod.POST)
	public @ResponseBody 
	Map<String, ? extends Object> agregarSocio(
			@RequestBody Socio socio, HttpServletResponse response, 
			HttpSession session) {
		
		Map<String, Object> result = new HashMap<String, Object>();
		SujetoObligado sujetoTramiteSocios = (SujetoObligado) session.getAttribute(TipoTramiteEnum.ACTUALIZACION_SOCIO.name());
		
		if (socio != null) {
			try {
				if (esValido(socio, result, response) && !existeSocio(socio, sujetoTramiteSocios, result, response)) {
					agregarItemLista(sujetoTramiteSocios.getSocios(), socio);
					log.debug("SUJETO TRAMITE tras agregar el socio ["+sujetoTramiteSocios+"].");
					result.put("mensajeExito", "");
				}
			}catch(Exception e){
				String message = "";
				message="Ocurri\u00F3 un error al intentar registrar un socio.";
				result.put("mensajeError", message);
			}
		}
		return result;
	}
	
	private boolean existeSocio(Socio oForm, SujetoObligado sujetoObligado, Map<String, Object> result, HttpServletResponse response) {
		
		List<Socio> socios = sujetoObligado != null ? sujetoObligado.getSocios() : null;
		Long cveIdPersona = null;
		boolean existeSocio = false;
		
		if (socios != null){
			for (Socio socio1 : socios) {
				
    			cveIdPersona = socio1.getIdPersona();
    			
    			if (cveIdPersona != null){
    				if (cveIdPersona.toString().equals(oForm.getIdPersona().toString())){
    					this.procesarErrorDeNegocio(new AbstractException("Error al agregar el socio, ya se ha registrado anteriormente esta persona como socio."), result, response);
    					existeSocio = true;
    				}
    			}
			}
		}
		return existeSocio;
	}


	@RequestMapping(value = "/fb/eliminarSocio", method = RequestMethod.POST)
	public @ResponseBody boolean eliminarSocio(
			@RequestBody Socio socio,
			HttpSession session) {
		System.err.println("socio a eliminar: " + socio);
		
		SujetoObligado sujetoTramiteSocios = (SujetoObligado) session.getAttribute(TipoTramiteEnum.ACTUALIZACION_SOCIO.name());
		boolean respuesta=true;
		
		
		List <Socio> socios = new ArrayList<Socio>();
		if (sujetoTramiteSocios==null){			
			
        	sujetoTramiteSocios=new SujetoObligado();
        	
        	if (socio.getTipoPersonaFiscalPatron().equals("FISICA")){
        		sujetoTramiteSocios.setFisica(new Fisica());
        		sujetoTramiteSocios.setTipoPersonaFiscal(TipoPersonaFiscal.FISICA);
        	}else{
        		sujetoTramiteSocios.setMoral(new Moral());
        		sujetoTramiteSocios.setTipoPersonaFiscal(TipoPersonaFiscal.MORAL);
        	}
		}
		else{
			if (socio.getTipoPersonaFiscalPatron().equals("FISICA")){
				socios=sujetoTramiteSocios.getFisica().getSocios();
				
	    	}
	    	else{        		
	    		
	    		socios=sujetoTramiteSocios.getMoral().getSocios();
	    	}	
		}
                               	        	       
        List<Socio> sociosDB = this.socioService.obtenerSociosPorSujetoObligado((Long) session.getAttribute("cveIdPatronSO"));
        boolean noExisteRenglon=true;     
        
        for(Socio socioSession : socios){
        	
        	if (!socio.getEsDomicilioNacional().booleanValue() && !socio.getEsNacional().booleanValue()){ // socio extranjero, comparar por nombres o razon social
        		if (TipoSocioEnum.FISICO.getValor().intValue() == socio.getTipoSocio().getIdTipoPersona().intValue() && socioSession.getTipoSocio().getIdTipoPersona().intValue() == 1){
        			if(socio.getNombres().equals(socioSession.getNombres()) && socio.getPrimerApellido().equals(socioSession.getPrimerApellido()) && socio.getSegundoApellido().equals(socioSession.getSegundoApellido())){
        				noExisteRenglon=false;        		
                		respuesta=false;
                		break;
        			}
        		} else if (TipoSocioEnum.MORAL.getValor().intValue() ==  socio.getTipoSocio().getIdTipoPersona().intValue() && socioSession.getTipoSocio().getIdTipoPersona().intValue() == 2){
        			if (socio.getNombreRazonSocial().equalsIgnoreCase(socioSession.getNombreRazonSocial())){
        				noExisteRenglon=false;        		
                		respuesta=false;
                		break;
        			}
        		} else if (TipoSocioEnum.FIDEICOMISO.getValor().intValue() ==  socio.getTipoSocio().getIdTipoPersona().intValue() && socioSession.getTipoSocio().getIdTipoPersona().intValue() == 3){
        			if (socio.getNombreRazonSocial().equalsIgnoreCase(socioSession.getNombreRazonSocial())){
        				noExisteRenglon=false;        		
                		respuesta=false;
                		break;
        			}
        		}
        	} else {
        		if (socioSession.getIdPersona().intValue() == socio.getIdPersona().intValue()){
            		noExisteRenglon=false;        		
            		respuesta=false;
            		break;
            	}
        	}        	
        }
        
        if(noExisteRenglon){
        	for(Socio socioDB : sociosDB){
        		if (!socio.getEsDomicilioNacional().booleanValue() && !socio.getEsNacional().booleanValue()){
        			if (TipoSocioEnum.FISICO.getValor().intValue() == socio.getTipoSocio().getIdTipoPersona().intValue() && socioDB.getTipoSocio().getIdTipoPersona().intValue() == 1){
            			if(socio.getNombres().equals(socioDB.getNombres()) && socio.getPrimerApellido().equals(socioDB.getPrimerApellido()) && socio.getSegundoApellido().equals(socioDB.getSegundoApellido())){
            				socioDB.setAccion(TipoAccionAfectacionEnum.ELIMINAR);  
            				socioDB.setMediosContacto(socio.getMediosContacto());
                			socios.add(socioDB);
                    		break;
            			}
            		} else if (TipoSocioEnum.MORAL.getValor().intValue() ==  socio.getTipoSocio().getIdTipoPersona().intValue() && socioDB.getTipoSocio().getIdTipoPersona().intValue() == 2){
            			if (socio.getNombreRazonSocial().equalsIgnoreCase(socioDB.getNombreRazonSocial())){
            				socioDB.setAccion(TipoAccionAfectacionEnum.ELIMINAR);  
            				socioDB.setMediosContacto(socio.getMediosContacto());
                			socios.add(socioDB);
                    		break;
            			}
            		} else if (TipoSocioEnum.FIDEICOMISO.getValor().intValue() ==  socio.getTipoSocio().getIdTipoPersona().intValue() && socioDB.getTipoSocio().getIdTipoPersona().intValue() == 3){
            			if (socio.getNombreRazonSocial().equalsIgnoreCase(socioDB.getNombreRazonSocial())){
            				socioDB.setAccion(TipoAccionAfectacionEnum.ELIMINAR);
            				socioDB.setMediosContacto(socio.getMediosContacto());
                			socios.add(socioDB);
                    		break;
            			}
            		}
        		} else if(socioDB.getIdPersona().intValue() == socio.getIdPersona().intValue()){        		
        			socioDB.setAccion(TipoAccionAfectacionEnum.ELIMINAR);
        			socioDB.setMediosContacto(socio.getMediosContacto());
        			socios.add(socioDB);
        			break;
        		}
        	}                
                         	
        	if (socio.getTipoPersonaFiscalPatron().equals("FISICA")){
        		sujetoTramiteSocios.getFisica().setSocios(socios);
        	}
        	else{        		
        		sujetoTramiteSocios.getMoral().setSocios(socios);
        	}
        }        
        session.setAttribute(TipoTramiteEnum.ACTUALIZACION_SOCIO.name(),sujetoTramiteSocios);        	                                                           		
        return respuesta;
	}
	
	@SuppressWarnings("unused")
	@RequestMapping(value = "/fb/modificarSocio", method = RequestMethod.POST)
	public @ResponseBody boolean modificarSocio(
			@RequestBody Socio socio,
			HttpSession session) {
		
		System.err.println(">>>>>>>> Socio a modificar: " + socio);
		
		SujetoObligado sujetoTramiteSocios = (SujetoObligado) session.getAttribute(TipoTramiteEnum.ACTUALIZACION_SOCIO.name());
		
		boolean respuesta		=	true;
		boolean noExisteRenglon	=	true;
		
		List <Socio> socios = new ArrayList<Socio>();
		if (sujetoTramiteSocios==null){
        	sujetoTramiteSocios=new SujetoObligado();
        	if (socio.getTipoPersonaFiscalPatron().equals("FISICA")){
        		sujetoTramiteSocios.setFisica(new Fisica());
        		sujetoTramiteSocios.setTipoPersonaFiscal(TipoPersonaFiscal.FISICA);
        	}else{
        		sujetoTramiteSocios.setMoral(new Moral());
        		sujetoTramiteSocios.setTipoPersonaFiscal(TipoPersonaFiscal.MORAL);
        	}
		}else{
			if (socio.getTipoPersonaFiscalPatron().equals("FISICA")){
				socios=sujetoTramiteSocios.getFisica().getSocios();
	    	}else{
	    		socios=sujetoTramiteSocios.getMoral().getSocios();
	    	}	
		}
        
        for(Socio socioSession : socios){
        	
        	if (!socio.getEsDomicilioNacional().booleanValue() && !socio.getEsNacional().booleanValue()){ // socio extranjero, comparar por nombres o razon social
        		if (TipoSocioEnum.FISICO.getValor().intValue() == socio.getTipoSocio().getIdTipoPersona().intValue() && socioSession.getTipoSocio().getIdTipoPersona().intValue() == 1){
        			if(socio.getNombres().equals(socioSession.getNombres()) && socio.getPrimerApellido().equals(socioSession.getPrimerApellido()) && socio.getSegundoApellido().equals(socioSession.getSegundoApellido())){
        				noExisteRenglon=false;        		
                		respuesta=false;
                		break;
        			}
        		} else if (TipoSocioEnum.MORAL.getValor().intValue() ==  socio.getTipoSocio().getIdTipoPersona().intValue() && socioSession.getTipoSocio().getIdTipoPersona().intValue() == 2){
        			if (socio.getNombreRazonSocial().equalsIgnoreCase(socioSession.getNombreRazonSocial())){
        				noExisteRenglon=false;        		
                		respuesta=false;
                		break;
        			}
        		} else if (TipoSocioEnum.FIDEICOMISO.getValor().intValue() ==  socio.getTipoSocio().getIdTipoPersona().intValue() && socioSession.getTipoSocio().getIdTipoPersona().intValue() == 3){
        			if (socio.getNombreRazonSocial().equalsIgnoreCase(socioSession.getNombreRazonSocial())){
        				noExisteRenglon=false;        		
                		respuesta=false;
                		break;
        			}
        		}
        	} else {
        		if (socioSession.getIdPersona().intValue() == socio.getIdPersona().intValue()){
            		noExisteRenglon=false;        		
            		respuesta=false;
            		break;
            	}
        	}        	
        }
        
        
        if(noExisteRenglon){
        	
        	if (!socio.getEsDomicilioNacional().booleanValue() && !socio.getEsNacional().booleanValue()){
    			if (TipoSocioEnum.FISICO.getValor().intValue() == socio.getTipoSocio().getIdTipoPersona().intValue()){
    				System.err.println(">>> a modificar socio FISICO, obteniendo de la BD para agregar a sujeto obligado en session...");
    				List<MedioContacto> medios = socio.getMediosContacto();
    				Socio socio2 = this.socioService.getSocio(socio);
    				socio2.setMediosContacto(medios);
    				System.err.println(">>> SOCIO FISICO obtenido: " +  socio2);
        			if (socio2 != null){
        				socio2.setPrimerApellido(socio.getPrimerApellido());
        				socio2.setSegundoApellido(socio.getSegundoApellido());
        				socio2.setNombres(socio.getNombres());
        				socio2.setAccion(TipoAccionAfectacionEnum.MODIFICAR);        			
            			socios.add(socio2);
        			}else {
        				System.err.println("Al modificar SOCIO FISICO no hay datos en la base, socio2 es null");
        			}
        		} else if (TipoSocioEnum.MORAL.getValor().intValue() ==  socio.getTipoSocio().getIdTipoPersona().intValue()){
        			System.err.println(">>> a modificar socio MORAL, obteniendo de la BD para agregar a sujeto obligado en session...");
        			List<MedioContacto> medios = socio.getMediosContacto();
        			Socio socio2 = this.socioService.getSocio(socio);
        			socio2.setMediosContacto(medios);
        			System.err.println(">>> SOCIO MORAL obtenido: " +  socio2);
        			if (socio2 != null){
        				socio2.setNombreRazonSocial(socio.getNombreRazonSocial()!=null ? socio.getNombreRazonSocial().toUpperCase() : ""); // <-- solo se actualiza un dato
        				socio2.setAccion(TipoAccionAfectacionEnum.MODIFICAR);        			
            			socios.add(socio2);
                		
        			}else {
        				System.err.println("Al modificar SOCIO MORAL no hay datos en la base, socio2 es null");
        				
        			}
        		} 
        		/**
        		 * NO HAY MODIFICACIONES EN EL DOC. DE CASO DE USO PARA FIDEICOMISO EXTRANJERO CON RESIDENCIA EXTRANJERA
        		 * 
        		 * else if (TipoSocioEnum.FIDEICOMISO.getValor().intValue() ==  socio.getTipoSocio().getIdTipoPersona().intValue()){
        			System.err.println(">>> a modificar socio FIDEICOMISO, obteniendo de la BD para agregar a sujeto obligado en session...");
        			Socio socio2 = socio2;
        			System.err.println(">>> SOCIO FIDEICOMISO obtenido: " +  socio2);
        			if (socio2 != null){
        				socio2.setNombreRazonSocial(socio.getNombreRazonSocial()); // <-- solo se actualiza un dato
        				socio2.setAccion(TipoAccionAfectacionEnum.MODIFICAR);        			
            			socios.add(socio2);
                		
        			}else {
        				System.err.println("Al modificar SOCIO FIDEICOMISO no hay datos en la base, socio2 es null");
        				
        			}
        		}*/
    		} else {
    			if (TipoSocioEnum.FISICO.getValor().intValue() == socio.getTipoSocio().getIdTipoPersona().intValue()){
    				List<MedioContacto> medios = socio.getMediosContacto();
    				Socio socio2 = this.socioService.getSocio(socio);
    				socio2.setMediosContacto(medios);
    				System.err.println("socio fisico nac. obtenido desde la base: " + socio2);
        			socio2.setAccion(TipoAccionAfectacionEnum.MODIFICAR);
        			
        			socios.add(socio2);
    			}else if (TipoSocioEnum.MORAL.getValor().intValue() ==  socio.getTipoSocio().getIdTipoPersona().intValue()){
    				List<MedioContacto> medios = socio.getMediosContacto();
    				Socio socio2 = this.socioService.getSocio(socio);
    				socio2.setMediosContacto(medios);
    				socio2.setNombreRazonSocial(socio2.getNombreRazonSocial()!=null ? socio2.getNombreRazonSocial().toUpperCase() : ""); // <-- solo se actualiza un dato
    				System.err.println("Socio MORAL nac. obtenido desde la base para sera ctualizado y metido a session: " + socio2);
    				System.err.println("Upper case de razon denomminacion social");
    				EscrituraConstitutiva escrituraConstitutiva = socio2.getEscrituraConstitutiva();
    				
    				if (escrituraConstitutiva != null){
    					escrituraConstitutiva.setFolioMercantil(socio.getEscrituraConstitutiva().getFolioMercantil());
        				escrituraConstitutiva.setSeccion(socio.getEscrituraConstitutiva().getSeccion());
        				escrituraConstitutiva.setPartida(socio.getEscrituraConstitutiva().getPartida());
        				escrituraConstitutiva.setVolumen(socio.getEscrituraConstitutiva().getVolumen());
        				escrituraConstitutiva.setFoja(socio.getEscrituraConstitutiva().getFoja());
    				} else {
    					escrituraConstitutiva = new EscrituraConstitutiva();
    				}
    				
    				socio2.setEscrituraConstitutiva(escrituraConstitutiva);
    				

    				/**
        			 * CESAREO, pendiete datos o medios de contact
        			 */
        			//socio2.setMediosContacto(null);
    				
    				socio2.setAccion(TipoAccionAfectacionEnum.MODIFICAR);
    				socios.add(socio2);
    					
    			} else if (TipoSocioEnum.FIDEICOMISO.getValor().intValue() ==  socio.getTipoSocio().getIdTipoPersona().intValue()){
    				List<MedioContacto> medios = socio.getMediosContacto();
    				Socio socio2 = this.socioService.getSocio(socio);
    				socio2.setMediosContacto(medios);
    				EntidadFederativa estado = socio2.getEstado();
    				System.err.println("Socio FIDEICOMISO nac. obtenido desde la base para sera ctualizado y metido a session: " + socio2);
    				
    				if (estado != null){
    					estado.setClave(socio.getEstado().getClave());
    				} else {
    					estado = new EntidadFederativa();
    				}
    				
    				socio2.setNumeroInstrumetoProtocolizacion(socio.getNumeroInstrumetoProtocolizacion());
    				socio2.setNotariaCorreduria(socio.getNotariaCorreduria());
    				socio2.setEstado(estado);
    				socio2.setFechaExpedicionContrato(socio.getFechaExpedicionContrato());
    				
    				/**
        			 * CESAREO, pendiete datos o medios de contact
        			 */
        			//socio2.setMediosContacto(null);
    				
    				socio2.setAccion(TipoAccionAfectacionEnum.MODIFICAR);
    				socios.add(socio2);
    			}
    		}
        }
        	               
                         	
    	if (socio.getTipoPersonaFiscalPatron().equals("FISICA")){
    		sujetoTramiteSocios.getFisica().setSocios(socios);
    	}
    	else{        		
    		sujetoTramiteSocios.getMoral().setSocios(socios);
    	}
                
        session.setAttribute(TipoTramiteEnum.ACTUALIZACION_SOCIO.name(),sujetoTramiteSocios);        	                                                           		
        return respuesta;
        
       
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void agregarItemLista(List lista, ItemClasificacion inputObject) {
		inputObject.setIdVista(new Long(lista.size() + 1));
		lista.add(inputObject);
	}
	
	@SuppressWarnings("rawtypes")
	public void eliminarItemLista(List lista, ItemClasificacion inputObject) {
		if (lista.contains(inputObject)) {
			lista.remove(inputObject);
		}
	}
	
	public boolean esValido(AbstractModel inputObject,
			Map<String, Object> result, HttpServletResponse response) {
		
		Errors errors = new BindException(inputObject, "model");
		
		SocioValidator.getInstance().validate(inputObject, errors);

		if (errors.hasErrors()) {
			this.procesaErroresDeCaptura(errors, result, response);
		}

		return !errors.hasErrors();
	}
	
	@RequestMapping(value = "/fb/deshacerAccionSobreSocio", method = RequestMethod.POST)
	public @ResponseBody boolean deshacerAccionSobreSocio(
			@RequestBody Socio socio,
			HttpSession session) {
		
		System.err.println("socio que llega para dehacer accion: " + socio);
		
		SujetoObligado sujetoTramiteSocios = (SujetoObligado) session.getAttribute(TipoTramiteEnum.ACTUALIZACION_SOCIO.name());		
		
		List <Socio> socios = new ArrayList<Socio>();
		if (sujetoTramiteSocios==null){
			return false;
		}
		else{
			if (socio.getTipoPersonaFiscalPatron().equals("FISICA")){
				socios=sujetoTramiteSocios.getFisica().getSocios();				
	    	}
	    	else{        		
	    		
	    		socios=sujetoTramiteSocios.getMoral().getSocios();
	    	}	
		}
						
        
        int index=0;                   	        	                     
        for(Socio socioSession : socios){
        	/*
        	 * Si idPersona es nulo, entonces se intenta remover
        	 * un socio extranjero con residencia extranjera
        	 */
        	if (!socio.getEsDomicilioNacional().booleanValue() && !socio.getEsNacional().booleanValue()){ // socio extranjero, comparar por nombres o razon social
        		if (TipoSocioEnum.FISICO.getValor().intValue() == socio.getTipoSocio().getIdTipoPersona().intValue() && socioSession.getTipoSocio().getIdTipoPersona().intValue() == 1){
        			if(socio.getNombres().equals(socioSession.getNombres()) && socio.getPrimerApellido().equals(socioSession.getPrimerApellido()) && socio.getSegundoApellido().equals(socioSession.getSegundoApellido())){
        				socios.remove(index);
                		break;
        			}
        		} else if (TipoSocioEnum.MORAL.getValor().intValue() ==  socio.getTipoSocio().getIdTipoPersona().intValue() && socioSession.getTipoSocio().getIdTipoPersona().intValue() == 2){
        			if (socio.getNombreRazonSocial().equalsIgnoreCase(socioSession.getNombreRazonSocial())){
        				socios.remove(index);
                		break;
        			}
        		} else if (TipoSocioEnum.FIDEICOMISO.getValor().intValue() ==  socio.getTipoSocio().getIdTipoPersona().intValue() && socioSession.getTipoSocio().getIdTipoPersona().intValue() == 3){
        			if (socio.getNombreRazonSocial().equalsIgnoreCase(socioSession.getNombreRazonSocial())){
        				socios.remove(index);
                		break;
        			}
        		}
        	} else if(socioSession.getIdPersona().intValue() == socio.getIdPersona().intValue()){
        		socios.remove(index);
        		break;
        	}
        	index++;
        }                
                         	
        if (socio.getTipoPersonaFiscalPatron().equals("FISICA")){
        	sujetoTramiteSocios.getFisica().setSocios(socios);
        }
        else{        		
        	sujetoTramiteSocios.getMoral().setSocios(socios);
        }        
        session.setAttribute(TipoTramiteEnum.ACTUALIZACION_SOCIO.name(),sujetoTramiteSocios);        
        return true;
	}
	
	@RequestMapping(value = "/fb/agregarSocioSesion", method = RequestMethod.POST)
	public @ResponseBody String agregarSocioSesion(@RequestBody Socio socio, HttpServletResponse response, HttpServletRequest reques,			
			HttpSession session) {
		
		final String SOCIO_DUPLICADO = "DUPLICATED";
		final String SOCIO_ASIGNADO_A_OTRO_CABRON_PATRON = "Esta persona ya se encuentra registrada como socio para \u00E9ste u otro patr\u00F3n, por favor realize otra selecci\u00F3n.";
		
		String msg = "OK";
		
		List <Socio> socios = null;
		TipoPersona tipoSocio = new TipoPersona();
		
		SujetoObligado sujetoTramiteSocios = (SujetoObligado) session.getAttribute(TipoTramiteEnum.ACTUALIZACION_SOCIO.name());
		socios = sujetoTramiteSocios.getMoral().getSocios() != null ? sujetoTramiteSocios.getMoral().getSocios() : new ArrayList<Socio>();
		
		Long idTipoPersona = socio.getTipoSocio().getIdTipoPersona();
		
		if (idTipoPersona.intValue() == 1){
			socio.setEsPersonaFisica(true);
			tipoSocio.setIdTipoPersona(TipoPersona.TIPO_PERSONA_FISICA);
			tipoSocio.setDescripcion("Persona Fisica");
			socio.setTipoSocio(tipoSocio);
			socio.setIdPersonaMoralPatron(sujetoTramiteSocios.getMoral().getIdPersona());
		
			System.err.println("socio fisico: " + socio);
		} else if(idTipoPersona.intValue() == 2) {
			socio.setEsPersonaFisica(false);
			tipoSocio.setIdTipoPersona(TipoPersona.TIPO_PERSONA_MORAL);
			tipoSocio.setDescripcion("Persona Moral");
			socio.setTipoSocio(tipoSocio);
			socio.setIdPersonaMoralPatron(sujetoTramiteSocios.getMoral().getIdPersona());
			
			System.err.println("socio moral: " + socio);
		} else {
			socio.setEsPersonaFisica(false);
			tipoSocio.setIdTipoPersona(TipoPersona.TIPO_PERSONA_FIDEICOMISO);
			tipoSocio.setDescripcion("Fideicomiso");
			socio.setTipoSocio(tipoSocio);
			socio.setIdPersonaMoralPatron(sujetoTramiteSocios.getMoral().getIdPersona());
			System.err.println("socio fideicomiso: " + socio);
		}
		
		// verificamos que este socio no esté asignado a otro patrón
		if (this.existenAsignacionesAnterioresSocioPatron(socio)){
			msg = SOCIO_ASIGNADO_A_OTRO_CABRON_PATRON;
		} else {
			boolean noExisteRenglon=true;     
	        
	        for(Socio socioSession : socios){
	        	/*
	        	 *  Al iterar pra buscar si ya esta registrado este socio hay que descartar a los 
	        	 *  extranjeros ya que estos no cuentan con id de Persona.
	        	 */
	        	if (socio.getIdPersona() != null){
	        		if (socioSession.getIdPersona() != null){
	        			if (socioSession.getIdPersona().toString().equals(socio.getIdPersona().toString())){
	                		noExisteRenglon=false;        		
	                		msg = SOCIO_DUPLICADO;
	                		break;
	                	}
	        		}
	        	} else {
	        		/*
	        		 * comparamos por nombres para socios extranjeros con residencia extranjera unicamente
	        		 */
	        		if (socioSession.getIdPersona() == null){
	        			if (idTipoPersona.intValue() == 1){	// <--  Socios fisicos
	    					if (socioSession.getNombres().equalsIgnoreCase(socio.getNombres()) && 
	    						socioSession.getPrimerApellido().equalsIgnoreCase(socio.getPrimerApellido()) && 
	    						socioSession.getSegundoApellido().equalsIgnoreCase(socio.getSegundoApellido())) {
	    						
	    						noExisteRenglon=false;        		
	    						msg = SOCIO_DUPLICADO;
	    	            		break;
	            				
	            			}
	            		}else if (idTipoPersona.intValue() == 2){	// <-- Socios morales
	            			if (socioSession.getDenominacionRazonSocial().equalsIgnoreCase(socio.getDenominacionRazonSocial())){
	            				noExisteRenglon=false;        		
	            				msg = SOCIO_DUPLICADO;
	                    		break;
	            			}
	            		}
	        		}
	        	}
	        }
	        if(noExisteRenglon){
	            socio.setAccion(TipoAccionAfectacionEnum.AGREGAR);
	            socio.setIdSocio(socio.getIdPersona()); // temporal
	            socio.setFecRegistroAlta(new Date());
	            socios.add(socio);
//	        	if (socio.getDenominacionRazonSocial().equals("FISICA")){
	            if (sujetoTramiteSocios.getFisica() != null){
	        		sujetoTramiteSocios.getFisica().setSocios(socios);
	        	}
	        	else{        		
	        		sujetoTramiteSocios.getMoral().setSocios(socios);
	        	}
	        }        
	        session.setAttribute(TipoTramiteEnum.ACTUALIZACION_SOCIO.name(),sujetoTramiteSocios);
		}
        
        return msg;
	}
	
	private boolean existenAsignacionesAnterioresSocioPatron(Socio socio) {
		return this.socioService.existenAsignacionesAnterioresSocioPatron(socio);
	}
	
	private Socio getSocioFromSessionList(List<Socio> socios, Socio socio) {

		for (Socio socioSession : socios) {
			
			if (!socio.getEsDomicilioNacional().booleanValue()
					&& !socio.getEsNacional().booleanValue()) { // socio
																// extranjero,
																// comparar por
																// nombres o
																// razon social
				if (TipoSocioEnum.FISICO.getValor().intValue() == socio
						.getTipoSocio().getIdTipoPersona().intValue()
						&& socioSession.getTipoSocio().getIdTipoPersona()
								.intValue() == 1) {
					if (socio.getNombres().equals(socioSession.getNombres())
							&& socio.getPrimerApellido().equals(
									socioSession.getPrimerApellido())
							&& socio.getSegundoApellido().equals(
									socioSession.getSegundoApellido())) {
						socio = socioSession;
						break;
					}
				} else if (TipoSocioEnum.MORAL.getValor().intValue() == socio
						.getTipoSocio().getIdTipoPersona().intValue()
						&& socioSession.getTipoSocio().getIdTipoPersona()
								.intValue() == 2) {
					if (socio.getNombreRazonSocial().equalsIgnoreCase(
							socioSession.getNombreRazonSocial())) {
						socio = socioSession;
						break;
					}
				} else if (TipoSocioEnum.FIDEICOMISO.getValor().intValue() == socio
						.getTipoSocio().getIdTipoPersona().intValue()
						&& socioSession.getTipoSocio().getIdTipoPersona()
								.intValue() == 3) {
					if (socio.getNombreRazonSocial().equalsIgnoreCase(
							socioSession.getNombreRazonSocial())) {
						socio = socioSession;
						break;
					}
				}
			} else {
				if (socioSession.getIdPersona().intValue() == socio
						.getIdPersona().intValue()) {
					System.err.println("Comparando socio: "+socio.getIdSocio() +" con socio sesion "+ socioSession.getIdSocio());
					System.err.println("Comparando socio persona: "+socio.getIdPersona() +" con socio sesion persona"+ socioSession.getIdPersona());
					socio = socioSession;
					break;
				}
			}
		}
		return socio;
	}
	
	private List<Socio> reemplazarSocioEnSessionList(List<Socio> socios, Socio socio) {

		for (int i = 0 ; i < socios.size() ; i++ ) {

			if (!socio.getEsDomicilioNacional().booleanValue() && !socio.getEsNacional().booleanValue()) {
				
				if (TipoSocioEnum.FISICO.getValor().intValue() == socio.getTipoSocio().getIdTipoPersona().intValue()
						&& socios.get(i).getTipoSocio().getIdTipoPersona().intValue() == 1) {
					
					if (socio.getNombres().equals(socios.get(i).getNombres())
							&& socio.getPrimerApellido().equals(socios.get(i).getPrimerApellido())
							&& socio.getSegundoApellido().equals(socios.get(i).getSegundoApellido())) {
						
						socios.remove(i);
						socios.add(i, socio);
						break;
					}
				} else if (TipoSocioEnum.MORAL.getValor().intValue() == socio.getTipoSocio().getIdTipoPersona().intValue()
						&& socios.get(i).getTipoSocio().getIdTipoPersona().intValue() == 2) {
					if (socio.getNombreRazonSocial().equalsIgnoreCase(socios.get(i).getNombreRazonSocial())) {
						socios.remove(i);
						socios.add(i, socio);
						break;
					}
				} else if (TipoSocioEnum.FIDEICOMISO.getValor().intValue() == socio.getTipoSocio().getIdTipoPersona().intValue()
						&& socios.get(i).getTipoSocio().getIdTipoPersona().intValue() == 3) {
					if (socio.getNombreRazonSocial().equalsIgnoreCase(socios.get(i).getNombreRazonSocial())) {
						socios.remove(i);
						socios.add(i, socio);
						break;
					}
				}
			} else {
				if (socios.get(i).getIdPersona().intValue() == socio.getIdPersona().intValue()) {
					socios.remove(i);
					socios.add(i, socio);
					break;
				}
			}
		}
		return socios;
	}


	@RequestMapping(value = "/fb/cargarDomicilioNuevoSocio", method = RequestMethod.POST)
	public @ResponseBody Socio obtenerDomicilioFiscal(@RequestBody Socio socio, HttpServletResponse response, HttpServletRequest reques) {
		log.info("/**** CARGAR DOMICILIO NUEVO SOCIO ID PERSONA :: "+socio.getIdPersona()+" TIPO PERSONA 1 :: "+socio.getDenominacionRazonSocial());
		
		Socio resulDomFiscal = new Socio();
		if (socio.getDenominacionRazonSocial()!=null) {
			log.info("PASO :: ");
			resulDomFiscal = sujetoObligadoService.obtenerDomicilioFiscal(socio.getIdPersona(), socio.getDenominacionRazonSocial());
		}
		
		return resulDomFiscal;
	}
	
	  /**
     * 
     * @param idProductoServicio
     * @return
     */
    @RequestMapping(value="/obtenerSocio" , method=RequestMethod.GET)
	public @ResponseBody Socio obtenerSocio(@RequestParam String  idSocio, @RequestParam String tipoSocioMod, HttpSession session){
    	
    	Socio socioBD = new Socio();
    	this.log.info("Obteniendo  el elemento [" + idSocio +" ]");
    	this.log.info("Tipo persona [" + tipoSocioMod +" ]");
    	
    	socioBD.setIdSocio(Long.valueOf(idSocio));
    	
    	if (tipoSocioMod.equals("1")) {
    		socioBD.setEsPersonaFisica(true);
    	} else {
    		socioBD.setEsPersonaFisica(false);
    	}
    	
    	Socio socioRetorno = socioService.getSocio(socioBD);
    	
    	
    	return socioRetorno;
	}
    
    @RequestMapping(value="/fb/realizarValidaciones", method=RequestMethod.POST)
	public @ResponseBody String realizarValidaciones(@RequestParam("tipoPersonaFiscal") String tipoPersonaFiscal,
			@RequestParam("operacion") Integer operacion,
			HttpSession session){
    	
    	final String MSG_1 = "Al menos son requeridos dos socios Actuales o en Modificaci\u00F3n o la combinaci\u00F3n de ambos.";
    	final String MSG_2 = "Para almacenar un tr\u00E1mite debe agregar al menos un movimiento: AGREGAR, MODIFICAR o ELIMINAR un socio.";
		
		String msg = "OK";
    	
    	@SuppressWarnings("unchecked")
		List<Socio> sociosListFromDBForValidations = (List<Socio>) session.getAttribute("sociosListFromDBForValidations");
    	SujetoObligado sujetoTramiteSocios = (SujetoObligado) session.getAttribute(TipoTramiteEnum.ACTUALIZACION_SOCIO.name());
    	List<Socio> socios = null;
    	
    	if (TipoPersonaFiscal.FISICA.name().equals(tipoPersonaFiscal)){
    		socios = sujetoTramiteSocios.getFisica().getSocios();
    	} else if(TipoPersonaFiscal.MORAL.name().equals(tipoPersonaFiscal)){
    		socios = sujetoTramiteSocios.getMoral().getSocios();
    	}
    	
		if(operacion == 21 &&  socios.isEmpty()){
			msg = MSG_2;
		} else {
			if ((sociosListFromDBForValidations.size() + socios.size()) < 2) {
        		msg = MSG_1;
        		
			} //else if (sociosListFromDBForValidations.size() > 2){
        	else {
				int contadorEliminar = 0;
				int contadorAgregar = 0;
				
				for (Socio socio : socios) {
					if (socio.getAccion().name().equals(TipoAccionAfectacionEnum.ELIMINAR.name())){
						contadorEliminar++;
					}
					if (socio.getAccion().name().equals(TipoAccionAfectacionEnum.AGREGAR.name())){
						contadorAgregar++;
					}
				}
				
				if ((sociosListFromDBForValidations.size() + contadorAgregar - contadorEliminar) < 2){
					msg = MSG_1;
				}
			}
		}
    	return msg;
    }
    
    
    @RequestMapping(value="/fb/obtenerDetalleEnTramiteSocios" , method={RequestMethod.GET, RequestMethod.POST})
	public @ResponseBody Socio obtenerDetalleEnTramiteSocios(@RequestBody  Socio socio, HttpSession session){
    	
		List<Socio> socios = (List<Socio>) ((SujetoObligado) session
								.getAttribute(TipoTramiteEnum.ACTUALIZACION_SOCIO.name()))
								.getMoral().getSocios();		
		
		Socio socioFromSessionList = this.getSocioFromSessionList(socios, socio);
		
		/*
		 *  datosContactoDetalleSocioAux contendrá los medios de contacto originales de este socio en caso que el usuario
		 *  presione 'cancelar' en la pantalla del detalle del socio en el apartado del tramite
		 */
		List<MedioContacto> mediosContactoDetalleSocioAux = new ArrayList<MedioContacto>();
		
		mediosContactoDetalleSocioAux.addAll(socioFromSessionList.getMediosContacto());
		session.setAttribute("mediosContactoDetalleSocioAux", mediosContactoDetalleSocioAux);
		
		return socioFromSessionList;
	}

	@RequestMapping(value="/fb/obtenerDetalleEnTramiteSociosDatosContacto" , method={RequestMethod.GET, RequestMethod.POST})
	public @ResponseBody DatosSalidaPaginador<MedioContacto> obtenerDetalleEnTramiteSociosDatosContacto(@RequestBody MedioContactoDataTable params, HttpSession session){
		
		DatosSalidaPaginador<MedioContacto> dspmc = new DatosSalidaPaginador<MedioContacto>();
		System.err.println(">>> ID persona del socio que llega a \"obtenerDetalleEnTramiteSociosDatosContacto\": " + params.getSocio().getIdPersona());
		
		DatosEntradaPaginador<MedioContacto> send = new DatosEntradaPaginador<MedioContacto>();
		
        send.parserArray(params.getAoData());
        send.setModelo(params.getoForm());
//        send.setiDisplayStart(0);
        
        
        System.err.println("socio sesion: "+(SujetoObligado)session
				.getAttribute(TipoTramiteEnum.ACTUALIZACION_SOCIO.name()));
        List<Socio> socios = (List<Socio>) ((SujetoObligado) session
				.getAttribute(TipoTramiteEnum.ACTUALIZACION_SOCIO.name()))
				.getMoral().getSocios();
        
        Socio socioFromSessionList = this.getSocioFromSessionList(socios, params.getSocio());
        System.err.println("El socio con el que se trabaja es el: "+socioFromSessionList);
        int sizeMediosContacto = socioFromSessionList.getMediosContacto().size();
        
		for (int i = 0; i < sizeMediosContacto; i++) {
        	socioFromSessionList.getMediosContacto().get(i).setIdVista(new Long(i + 1));
        	socioFromSessionList.getMediosContacto().get(i).setErrorFormGeneral(socioFromSessionList.getIdPersona().toString());
		}
        
		
		List<MedioContacto> mediosADesplegar = obtenerMediosContactoDeTramiteADesplegar(socioFromSessionList.getMediosContacto(), send);
		
		System.err.println("Medios de contacto de socio encontrados en la página: "+mediosADesplegar);
		if(mediosADesplegar == null || mediosADesplegar.size() == 0){
			System.err.println("Medios de contacto de socio encontrados en la página anterior: "+mediosADesplegar);
			mediosADesplegar = obtenerMediosADesplegarPaginaAnterior(socioFromSessionList.getMediosContacto(), send);
		}
		
		dspmc.setiTotalDisplayRecords(socioFromSessionList.getMediosContacto().size());
		dspmc.setiTotalRecords(socioFromSessionList.getMediosContacto().size());
		dspmc.setAaData(mediosADesplegar);
		dspmc.setsEcho(send.getsEcho());

//       dspmc.setiTotalDisplayRecords(send.getiDisplayStart());
//		dspmc.setiTotalRecords(sizeMediosContacto);
//		dspmc.setAaData(socioFromSessionList.getMediosContacto());
//		dspmc.setsEcho(send.getsEcho());
		
		return dspmc;
	}	
	
	@RequestMapping(value = "/fb/agregarDatoContactoDetalleSocio", method = RequestMethod.POST)
	public @ResponseBody boolean agregarDatoContactoDetalleSocio(@RequestBody MedioContactoDataTable params, HttpSession session) {
		
		boolean result = false;
		SujetoObligado sujetoTramiteSocio = (SujetoObligado) session
				.getAttribute(TipoTramiteEnum.ACTUALIZACION_SOCIO.name());
		
		List<Socio> socios = (List<Socio>) sujetoTramiteSocio
				.getMoral().getSocios();
		
		MedioContacto medioContacto = params.getoForm();
		
		System.err.println("medio contacto en fb/agregarDatoContactoDetalleSocio: " + medioContacto);
		
        try {
			Socio socioFromSessionList = this.getSocioFromSessionList(socios, params.getSocio());
			medioContacto.setIdVista(new Long(socioFromSessionList.getMediosContacto().size() + 1));
			socioFromSessionList.getMediosContacto().add(medioContacto);
			
			sujetoTramiteSocio.getMoral().setSocios(
					this.reemplazarSocioEnSessionList(
							sujetoTramiteSocio.getMoral().getSocios(), socioFromSessionList
							)
						);
			
			 session.setAttribute(TipoTramiteEnum.ACTUALIZACION_SOCIO.name(), sujetoTramiteSocio);
			
			result = true;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
		return result;
	}
	
	
	
	@RequestMapping(value = "/fb/modificarDatoContactoDetalleSocio", method = RequestMethod.POST)
	public @ResponseBody boolean modificarDatoContactoDetalleSocio(@RequestBody MedioContactoDataTable params, HttpSession session) {
		
		boolean result = false;
		SujetoObligado sujetoTramiteSocio = (SujetoObligado) session
				.getAttribute(TipoTramiteEnum.ACTUALIZACION_SOCIO.name());
		
		List<Socio> socios = (List<Socio>) sujetoTramiteSocio
				.getMoral().getSocios();
		
		MedioContacto medioContacto = params.getoForm();
		
		System.err.println("medioContacto en fb/modificarDatoContactoDetalleSocio:" + medioContacto);
		
        try {
			Socio socioFromSessionList = this.getSocioFromSessionList(socios, params.getSocio());
			
			for (int j = 0; j < socioFromSessionList.getMediosContacto().size(); j++) {
				if (socioFromSessionList.getMediosContacto().get(j).getIdVista().intValue() == medioContacto.getIdVista()){
					socioFromSessionList.getMediosContacto().get(j).setTipoMedioContacto(medioContacto.getTipoMedioContacto());
					socioFromSessionList.getMediosContacto().get(j).setDesFormaContacto(medioContacto.getDesFormaContacto());
					break;
				}
			}
			
			sujetoTramiteSocio.getMoral().setSocios(
					this.reemplazarSocioEnSessionList(
							sujetoTramiteSocio.getMoral().getSocios(), socioFromSessionList
							)
						);
			
			 session.setAttribute(TipoTramiteEnum.ACTUALIZACION_SOCIO.name(), sujetoTramiteSocio);
			
			result = true;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
		return result;
	}
	
	@RequestMapping(value = "/fb/eliminarDatoContactoDetalleSocio", method = RequestMethod.POST)
	public @ResponseBody boolean eliminarDatoContactoDetalleSocio(@RequestBody MedioContactoDataTable params, HttpSession session) {
		boolean result = false;
		
		SujetoObligado sujetoTramiteSocio = (SujetoObligado) session
				.getAttribute(TipoTramiteEnum.ACTUALIZACION_SOCIO.name());
		
		List<Socio> socios = (List<Socio>) sujetoTramiteSocio
				.getMoral().getSocios();
		
		MedioContacto medioContacto = params.getoForm();
		
        try {
			Socio socioFromSessionList = this.getSocioFromSessionList(socios, params.getSocio());
			
			for (int j = 0; j < socioFromSessionList.getMediosContacto().size(); j++) {
				if (socioFromSessionList.getMediosContacto().get(j).getIdVista().intValue() == medioContacto.getIdVista()){
					socioFromSessionList.getMediosContacto().remove(j);
				}
			}
			
			sujetoTramiteSocio.getMoral().setSocios(
					this.reemplazarSocioEnSessionList(
							sujetoTramiteSocio.getMoral().getSocios(), socioFromSessionList
							)
						);
			
			 session.setAttribute(TipoTramiteEnum.ACTUALIZACION_SOCIO.name(), sujetoTramiteSocio);
			
			result = true;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
		return result;
		
	}
    
	
	@RequestMapping(value = "/fb/validaMovimientoPrevio", method = {RequestMethod.GET, RequestMethod.POST})
	public @ResponseBody Map<String, Object> validarMovimientoPrevio(
			@RequestBody Socio socio,
			HttpSession session) {
	
		Map<String, Object> result = new HashMap<String, Object>();
		
		SujetoObligado sujetoTramiteSocio = (SujetoObligado)session.getAttribute(TipoTramiteEnum.ACTUALIZACION_SOCIO.name());
		List<Socio> socios = (List<Socio>) sujetoTramiteSocio
				.getMoral().getSocios();
		result.put("existeMovimientoPrevio", false);
		for(Socio socioItem : socios){
			if(socioItem.getIdSocio().equals(socio.getIdSocio())){
				result.put("existeMovimientoPrevio", true);
			}
		}
		
		return result;
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/fb/cancelarEdicionMediosContactoSocioEnDetalle", method = {RequestMethod.GET, RequestMethod.POST})
	public @ResponseBody boolean cancelarEdicionMediosContactoSocioEnDetalle(@RequestBody Socio socioEntrante, HttpSession session){
		
		boolean result = false;
		
		try {
			System.err.println(">>> Cancelando edicion de medios de contacto en el detalle del socio con tipo de socio [" + socioEntrante.getTipoSocio().getIdTipoPersona() + "] ..." );
			
			SujetoObligado so = (SujetoObligado) session.getAttribute(TipoTramiteEnum.ACTUALIZACION_SOCIO.name());
					
			so.getMoral().setSocios(this.actualizarMediosContactoOriginalesDeSocioEnSessionList(
					(List<Socio>) so.getMoral().getSocios(),
					socioEntrante,
					(List<MedioContacto>) session.getAttribute("mediosContactoDetalleSocioAux")));
			
			session.setAttribute(TipoTramiteEnum.ACTUALIZACION_SOCIO.name(), so);
			
			System.err.println(">>> Cancelando edicion de medios de contacto en el detalle del socio con tipo de socio [" + socioEntrante.getTipoSocio().getIdTipoPersona() + "], COMPLETADO." );
			
			result = true;
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}


	private List<Socio> actualizarMediosContactoOriginalesDeSocioEnSessionList(
			List<Socio> socios, Socio socio,
			List<MedioContacto> medioContactoListOrig) {
		for (int i = 0 ; i < socios.size() ; i++ ) {

			if (!socio.getEsDomicilioNacional().booleanValue() && !socio.getEsNacional().booleanValue()) {
				
				if (TipoSocioEnum.FISICO.getValor().intValue() == socio.getTipoSocio().getIdTipoPersona().intValue()
						&& socios.get(i).getTipoSocio().getIdTipoPersona().intValue() == 1) {
					
					if (socio.getNombres().equals(socios.get(i).getNombres())
							&& socio.getPrimerApellido().equals(socios.get(i).getPrimerApellido())
							&& socio.getSegundoApellido().equals(socios.get(i).getSegundoApellido())) {
						socios.get(i).setMediosContacto(medioContactoListOrig);
						break;
					}
				} else if (TipoSocioEnum.MORAL.getValor().intValue() == socio.getTipoSocio().getIdTipoPersona().intValue()
						&& socios.get(i).getTipoSocio().getIdTipoPersona().intValue() == 2) {
					if (socio.getNombreRazonSocial().equalsIgnoreCase(socios.get(i).getNombreRazonSocial())) {
						socios.get(i).setMediosContacto(medioContactoListOrig);
						break;
					}
				} else if (TipoSocioEnum.FIDEICOMISO.getValor().intValue() == socio.getTipoSocio().getIdTipoPersona().intValue()
						&& socios.get(i).getTipoSocio().getIdTipoPersona().intValue() == 3) {
					if (socio.getNombreRazonSocial().equalsIgnoreCase(socios.get(i).getNombreRazonSocial())) {
						socios.get(i).setMediosContacto(medioContactoListOrig);
						break;
					}
				}
			} else {
				if (socios.get(i).getIdPersona().intValue() == socio.getIdPersona().intValue()) {
					socios.get(i).setMediosContacto(medioContactoListOrig);
					break;
				}
			}
		}
		return socios;
	}

	@RequestMapping(value = "/validaMediosContacto", method = {RequestMethod.GET, RequestMethod.POST})
	public @ResponseBody Map<String, Object> validarMediosContactoRequeridos(
			@RequestBody Socio socio,
			HttpSession session) {
		Map<String, Object> result = new HashMap<String, Object>();
		
		
		
		
		SujetoObligado sujetoTramiteSocio = (SujetoObligado)session.getAttribute(TipoTramiteEnum.ACTUALIZACION_SOCIO.name());
		List<Socio> sociosEnTramite = (List<Socio>) sujetoTramiteSocio
				.getMoral().getSocios();
		
		Socio socioSeleccionado = null;
		if(sociosEnTramite!=null){
			for(Socio socioEnTramite : sociosEnTramite){
				System.err.println("Socio actualmente en tramite: "+socio.getIdSocio());
				
				if(socio.getIdSocio()!=null){
					if(socio.getIdSocio().equals(socioEnTramite.getIdSocio())){
						socioSeleccionado = socioEnTramite;
						break;
					}
				}else {
					if(socio.getIdPersona().equals(socioEnTramite.getIdPersona())){
						socioSeleccionado = socioEnTramite;
						break;
					}
				}
			}
		}
		boolean correoPresente = false;
		boolean telefonoPresente = false;
		
		if(!socio.getEsNacional() && !socioSeleccionado.getEsDomicilioNacional()){
			//ES SOCIO EXTRANJERO
			result.put("datosContactoValidos", true);
		}
		
		for(MedioContacto medio : socioSeleccionado.getMediosContacto()){
			if(medio.getTipoMedioContacto().getIdTipoMedioContacto().equals(TipoMedioContacto.TIPO_CORREO_ELECTRONICO)){
				correoPresente = true;
			}else{
				if(medio.getTipoMedioContacto().getIdTipoMedioContacto().equals(TipoMedioContacto.TIPO_TELEFONO_FIJO)
						|| medio.getTipoMedioContacto().getIdTipoMedioContacto().equals(TipoMedioContacto.TIPO_TELEFONO_MOVIL)){
					telefonoPresente = true;
				}
			}
		}
		
		boolean valido = telefonoPresente || correoPresente;
		result.put("datosContactoValidos", valido);
		
		return result;
	}
	
	private List<MedioContacto> obtenerMediosContactoDeTramiteADesplegar(List<MedioContacto> mediosTotales, DatosEntradaPaginador<MedioContacto> input){
		List<MedioContacto> mediosADesplegar = new ArrayList<MedioContacto>();
		Integer indiceInicio = input.getiDisplayStart();
		Integer mediosContactoTotales = mediosTotales != null ? mediosTotales.size()  : 0;
		Integer indiceFinal = mediosContactoTotales > input.getiDisplayStart()+input.getiDisplayLength() ? 
				input.getiDisplayStart()+input.getiDisplayLength() : mediosContactoTotales;
		
		System.err.println("indiceInicio uno: "+indiceInicio);
		System.err.println("indiceFinal uno: "+indiceFinal);
				
				
		if(indiceInicio > indiceFinal  ){
			indiceFinal = mediosContactoTotales;
			
			if( mediosContactoTotales < input.getiDisplayLength() ){
				indiceInicio = 0;
			}else{
				indiceInicio = calcularIndiceInicioDeUltimaPagina(mediosContactoTotales, input.getiDisplayLength());
			}
		}		
		
		System.err.println("mediosTotales: "+mediosTotales.size());
		System.err.println("indiceInicio: "+indiceInicio);
		System.err.println("indiceFinal: "+indiceFinal);
		
		mediosADesplegar.addAll(mediosTotales.subList(indiceInicio, indiceFinal));

		return mediosADesplegar;
	}
	
	
	private List<MedioContacto> obtenerMediosADesplegarPaginaAnterior(List<MedioContacto> mediosTotales, DatosEntradaPaginador<MedioContacto> input){
		List<MedioContacto> mediosADesplegar = new ArrayList<MedioContacto>();
		Integer indiceInicio = input.getiDisplayStart() - input.getiDisplayLength();
		indiceInicio = indiceInicio < 0 ? 0 : indiceInicio;
		Integer mediosContactoTotales = mediosTotales != null ? mediosTotales.size()  : 0;
		Integer indiceFinal = mediosContactoTotales > indiceInicio+input.getiDisplayLength() ? 
				indiceInicio+input.getiDisplayLength() : mediosContactoTotales;
		
		mediosADesplegar.addAll(mediosTotales.subList(indiceInicio, indiceFinal));
		
		return mediosADesplegar;
	}
	
	private Integer calcularIndiceInicioDeUltimaPagina(Integer elementosTotales, Integer elementosPorPagina){
		Double numPaginas = Math.floor(elementosTotales/elementosPorPagina);
		
		return numPaginas.intValue()*elementosPorPagina;
	}
	
	
	@InitBinder
	protected void initBinder(WebDataBinder binder) {
	    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
	    binder.registerCustomEditor(Date.class, new CustomDateEditor(
	            dateFormat, false));
	}
}

