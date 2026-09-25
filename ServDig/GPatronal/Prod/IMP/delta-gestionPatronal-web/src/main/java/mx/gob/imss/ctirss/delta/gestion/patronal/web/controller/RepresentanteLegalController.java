/**
 * delta-gestionPatronal-web10/04/2012
 * mx.gob.imss.ctirss.delta.gestion.patronal.web.controller10/04/2012
 * RepresentanteLegalController.java
 * 10/04/2012
 * 
 */
package mx.gob.imss.ctirss.delta.gestion.patronal.web.controller;


import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import mx.gob.imss.ctirss.delta.framework.base.controller.AbstractController;
import mx.gob.imss.ctirss.delta.framework.base.exception.AbstractException;
import mx.gob.imss.ctirss.delta.framework.base.exception.GestionPatronalBusinessException;
import mx.gob.imss.ctirss.delta.framework.base.model.AbstractModel;
import mx.gob.imss.ctirss.delta.framework.paginador.model.DatosEntradaPaginador;
import mx.gob.imss.ctirss.delta.framework.paginador.model.DatosSalidaPaginador;
import mx.gob.imss.ctirss.delta.gestion.patronal.service.interfaces.SujetoObligadoServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.patronal.service.interfaces.rep.legal.RepresentanteLegalServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.patronal.service.interfaces.solicitud.SolicitudServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.patronal.web.controller.paginator.MedioContactoDataTable;
import mx.gob.imss.ctirss.delta.gestion.patronal.web.controller.paginator.RepresentanteLegalDataTable;
import mx.gob.imss.ctirss.delta.gestion.patronal.web.utils.RepresentanteLegalValidator;
import mx.gob.imss.ctirss.delta.model.Usuario;
import mx.gob.imss.ctirss.delta.model.enums.TipoPersonaEnum;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Fisica;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Moral;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.TipoPersona;
import mx.gob.imss.ctirss.delta.model.gestion.medio.contacto.MedioContacto;
import mx.gob.imss.ctirss.delta.model.gestion.medio.contacto.TipoMedioContacto;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.ItemClasificacion;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.RepresentanteLegal;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.SujetoObligado;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.TipoAccionAfectacionEnum;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.TipoPersonaFiscal;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.TipoSolicitudEnum;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.TipoTramiteEnum;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.EstadoSolicitudEnum;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.OrigenSolicitudEnum;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.Solicitud;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.EstadoTramiteEnum;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TramiteSujetoObligado;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindException;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author Luci Duran Silva
 * Instituto Mexicano del Seguro Social
 */

//@RequestMapping(value="{cveIdPatronSujetoObligado}/representanteLegal")
@Controller
@RequestMapping(value="representanteLegal")
public class RepresentanteLegalController extends AbstractController{
	
	@Autowired
	RepresentanteLegalServiceBusinessRemote representanteLegalServiceBusinessRemote;
	
	@Autowired
	private SujetoObligadoServiceBusinessRemote sujetoObligadoService;
	
	@Autowired
	SolicitudServiceBusinessRemote solicitudServiceBusiness;
		
//	@Autowired
//	private AfiliacionServiceBusinessRemote afiliacionService;

	private String mensajeActualizacion="Su solicitud se ha enviado para ser procesada.<BR>" +
			"El número de folio de la solicitud es el siguiente[ varNumeroFolio ]" +
			"<BR> Por favor tome nota de su número de folio para su seguimiento";
	
	@RequestMapping(method=RequestMethod.POST)
	//public String  inicio(@RequestParam String regPat, Model model, HttpSession session){
	public String inicio(@ModelAttribute SujetoObligado sujetoObligado, 
			@RequestParam("idSolicitud") String idSolicitudRL, Model model, HttpSession session){
		
		String idSolicitudRLTemp = null;

		String l = (String) session.getAttribute("idSolicitudRL");
		
		if (l == null && !StringUtils.isBlank(idSolicitudRL)){
			idSolicitudRLTemp = idSolicitudRL;
		}
		
		
		 
		if (sujetoObligado.getNumeroRegistroPatronal() == null){
			if (idSolicitudRLTemp != null && !StringUtils.isBlank(idSolicitudRLTemp)) {
				
				
				
				log.debug("OBTAINING SUJETOOBLIGADO DATA for " + this.getClass().getName() + "...");
				
				Solicitud sol = solicitudServiceBusiness
						.consultarSolicitudPorId(Long.valueOf(idSolicitudRLTemp));
				TramiteSujetoObligado tso = (TramiteSujetoObligado) sol
						.getTramites().get(0);
				sujetoObligado = tso.getSujetoObligado();
				
				model.addAttribute("idSolicitudRL", idSolicitudRL);
				session.setAttribute("idSolicitudRL", idSolicitudRL);
				
				
				
				log.debug("OBTAINED SUJETOOBLIGADO DATA for " + this.getClass().getSimpleName() + ".");
			
			}
		} else {
			sujetoObligado = sujetoObligadoService
					.obtenerDetalleSujetoObligadoActividadEconomica(sujetoObligado);
			
			model.addAttribute("idSolicitudRL", l);
			session.setAttribute("idSolicitudRL", l);
		}
		
		
		RepresentanteLegal representanteLegal = new RepresentanteLegal();
		Usuario usuario = (Usuario) session.getAttribute("usuario");
		
		TipoTramiteEnum t=TipoTramiteEnum.DEFAULT;
		
		representanteLegal.setCveIdPatronSujetoObligado(sujetoObligado.getCveIdSujetoObligado());
		
		session.setAttribute("usuario", usuario); // necesario para la generaicon del tramite		
		session.setAttribute("sujetoObligado", sujetoObligado);		
		session.setAttribute("representanteLegal", representanteLegal);
		
		model.addAttribute(representanteLegal);
		model.addAttribute("idTramite", t);
		
		sujetoObligado = (SujetoObligado) session.getAttribute("sujetoObligado");

		return this.consultarRepLegal(model, session);
		//return "fb.rep.legal";
	}
	
	@RequestMapping(value="/paginarForSession", method=RequestMethod.POST)
    public @ResponseBody DatosSalidaPaginador<RepresentanteLegal> paginarForSession(@RequestBody RepresentanteLegalDataTable params,    		
    		@RequestParam("idSolicitud") Long idSolicitud, HttpSession session) {
		log.debug("Cargando el grid de tramites representantes legales");
		for (Object obj:params.getAoData()){
			log.debug("<OTIKA>:"+obj);
		}
		Usuario usuario = (Usuario) session.getAttribute("usuario");	
		DatosEntradaPaginador<RepresentanteLegal> input = new DatosEntradaPaginador<RepresentanteLegal>();
		input.parserArray(params.getAoData());
		input.setModelo(params.getoForm());
		
		DatosSalidaPaginador<RepresentanteLegal> output=null;
		SujetoObligado sujetoTramite = (SujetoObligado) session.getAttribute("sujetoTramite");
		String tipoPersonaFiscal="MORAL";
		log.debug("<OTIKA>:TipoPersonafiscal:"+params.getoForm().getTipoPersonaRepresentada().getIdTipoPersona());
		if (params.getoForm().getTipoPersonaRepresentada().getIdTipoPersona()==1l)
			tipoPersonaFiscal="FISICA";										
		if (sujetoTramite==null){			
			log.debug("Se inicializa el sujeto tramite en sesion para RL");
			sujetoTramite=new SujetoObligado();			
			output = representanteLegalServiceBusinessRemote.obtenerMovimientosDeTramite(input, usuario, idSolicitud);
			//Se guarda en Session la lista:			
			for(RepresentanteLegal obj : output.getAaData()){
				log.debug("Dato: "+obj);
			}			
			output.setsEcho(input.getsEcho());
			if (output.getAaData()==null)
				output.setAaData(new ArrayList<RepresentanteLegal>());
			if (tipoPersonaFiscal.equals("FISICA")){
				sujetoTramite.setFisica(new Fisica());				
				sujetoTramite.getFisica().setRepresentantesLegales(output.getAaData());				
			}			
			if (tipoPersonaFiscal.equals("MORAL")){
				sujetoTramite.setMoral(new Moral());
				sujetoTramite.getMoral().setRepresentantesLegales(output.getAaData());
			}
			session.setAttribute("sujetoTramite",sujetoTramite);							
		}
		else {
			
			if (sujetoTramite.getFisica()!=null){
				
				if (sujetoTramite.getFisica().getRepresentantesLegales()!=null){
				
					output = new DatosSalidaPaginador<RepresentanteLegal>();
					output.setsEcho(input.getsEcho());					
					output.setAaData(sujetoTramite.getFisica().getRepresentantesLegales());
				}					
			}
			if (sujetoTramite.getMoral()!=null){
				
				if (sujetoTramite.getMoral().getRepresentantesLegales()!=null){					
					output = new DatosSalidaPaginador<RepresentanteLegal>();
					output.setsEcho(input.getsEcho());
					output.setAaData(sujetoTramite.getMoral().getRepresentantesLegales());
				}				
			}
		}
		Integer numeroRegistros = output.getAaData()!=null ? output.getAaData().size() : 0;
		output.setiTotalRecords(numeroRegistros);
		output.setiTotalDisplayRecords(numeroRegistros);
		return output;

        
    }
	
	@RequestMapping(value="/paginar", method=RequestMethod.POST)
    public @ResponseBody DatosSalidaPaginador<RepresentanteLegal> pagina(@RequestBody RepresentanteLegalDataTable aoData ) {
		
		DatosEntradaPaginador<RepresentanteLegal> send = new DatosEntradaPaginador<RepresentanteLegal>();
        send.parserArray(aoData.getAoData());
        send.setModelo(aoData.getoForm());
        DatosSalidaPaginador<RepresentanteLegal> reply = representanteLegalServiceBusinessRemote.paginarRepresentanteLegal(send);
        reply.setsEcho(send.getsEcho());
        
        
        return reply;
        
    }
	
    @RequestMapping(value="/consulta", method=RequestMethod.GET)
	public String getRepresentanteLegalConsulta (@PathVariable String cveIdPatronSujetoObligado , Model model , HttpSession session) {
    	
    	RepresentanteLegal representanteLegal = new RepresentanteLegal();
    	representanteLegal.setCveIdPatronSujetoObligado(Long.parseLong(cveIdPatronSujetoObligado));
    	
    	model.addAttribute("representanteLegal", representanteLegal);
    	
		return "representanteLegalConsulta";
	}
    
    @RequestMapping(value="/paginarConsulta", method=RequestMethod.POST)
    public @ResponseBody DatosSalidaPaginador<RepresentanteLegal> paginaConsulta(@RequestBody RepresentanteLegalDataTable aoData ) {
    	
    	DatosEntradaPaginador<RepresentanteLegal> send = new DatosEntradaPaginador<RepresentanteLegal>();
        send.parserArray(aoData.getAoData());
        send.setModelo(aoData.getoForm());
        DatosSalidaPaginador<RepresentanteLegal> reply = representanteLegalServiceBusinessRemote.paginarRepresentanteLegal(send);
        reply.setsEcho(send.getsEcho());
        System.err.println("reply; " + reply);
        return reply;
        
    }
    

    
    /**
     * 
     * @param idRepresentanteLegal
     * @return
     */
    @RequestMapping(value="/get" , method=RequestMethod.GET)
	public @ResponseBody RepresentanteLegal getRepresentanteLegal(@RequestParam String  cveIdPersona, HttpSession session ){
    	this.log.info("Obteniendo  el elemento [" + cveIdPersona +" ]");
    	RepresentanteLegal representanteLegal = null;
//    	representanteLegal.setCveIdRepresentanteLegal(Long.parseLong(idRepresentanteLegal));
//    	representanteLegal = representanteLegalServiceBusinessRemote.getRepresentanteLegal(representanteLegal);
    	
    	List<RepresentanteLegal> representantesLegales = ((SujetoObligado) session.getAttribute("sujetoObligado")).getRepresentantesLegales();
    	
    	for (RepresentanteLegal representanteLegal2 : representantesLegales) {
			if (cveIdPersona.equals(representanteLegal2.getCveIdPersona().toString())){
				representanteLegal = representanteLegal2;
				break;
			}
		}
    	
    	return representanteLegal;
	}
    
	/**
	 * Metodo para eliminar el registro
	 * 
	 * @param oForm
	 * @return
	 */
	@RequestMapping(value = "/eliminar", method = RequestMethod.GET)
	public @ResponseBody Map<String, ? extends Object> eliminar(
			@RequestParam String cveIdPersona,
			@RequestParam String cveIdPatronSujetoObligado,
			@RequestParam String tipoPersonaFiscal,
			HttpServletResponse response,
			HttpSession session) {
		
		Map<String, Object> result = new HashMap<String, Object>();
				
		SujetoObligado sujetoObligado = (SujetoObligado) session.getAttribute("sujetoObligado");
		
        /*RepresentanteLegal representanteLegal = new RepresentanteLegal();
		representanteLegal.setCveIdRepresentanteLegal(Long.parseLong(idRepresentanteLegal));
		representanteLegal.setCveIdPatronSujetoObligado(Long.parseLong(cveIdPatronSujetoObligado));*/
		//representanteLegalServiceBusinessRemote.eliminarRepresentanteLegal(representanteLegal, tipoPersonaFiscal);
		
		//result.put("oModel", representanteLegal);
		
		
		try {
			
			List<RepresentanteLegal> representantesLegales = sujetoObligado.getRepresentantesLegales();
			if (representantesLegales.size() > 1){
				for (RepresentanteLegal iterable_element : representantesLegales) {
					if (cveIdPersona.equals(iterable_element.getCveIdPersona().toString())){
						representantesLegales.remove(iterable_element);
						break;
					}
				}
				sujetoObligado.setRepresentantesLegales(representantesLegales);
			}else{
				this.procesarErrorDeNegocio(new AbstractException("Error al dar de baja al representante legal, debe contar al menos con un registro."), result, response);
				return result;
				
				
			}

		} catch (Exception e) {
			e.printStackTrace();
			result.put("errors", "Error al eliminar el representante legal");
		}	
        return result;
	}
	

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value="/modificar" , method=RequestMethod.POST)
	public @ResponseBody Map<String, ? extends Object> modificar(@RequestBody RepresentanteLegal oForm, HttpServletResponse response, HttpSession session) {

		Map result = new HashMap<String, Object>();
		
		SujetoObligado sujetoTramite = (SujetoObligado) session
		.getAttribute("sujetoObligado");
		
		/*new RepresentanteLegalValidator().validate(oForm, errors);
		if (errors.hasErrors()) {
			this.procesaErroresDeCaptura(errors, result, response);
			return result;
		}

		try {
			representanteLegalServiceBusinessRemote.modificarRepresentanteLegal(oForm);
			result.put("oModel", oForm);
		} catch (RepresentanteLegalInvalidoException e) {
			this.procesarErrorDeNegocio(e, result, response);
			return result;
		} catch (RepresentanteLegalYaExisteException e) {
			this.procesarErrorDeNegocio(e, result, response);
			return result;
		}*/
		
		if (oForm != null) {
			try {
				if (esValido(oForm, result, response)) {
					// actualizamos fecha de modificacion
					oForm.setFecRegistroActualizado(new Date());
					
					// reemplazamos en lista
					modificarItemLista(sujetoTramite.getRepresentantesLegales(),
							oForm);
				}
			} catch (Exception e) {
				result.put("errors", "Error al actualizar el representante legal");
				log.error(oForm, e);
			}
		}

		return result;
	}
	
	
	@RequestMapping(value = "/finalizar", method = RequestMethod.POST)
	public void finalizar(
			//@RequestParam String idRepresentanteLegal,
			//@RequestParam String cveIdPatronSujetoObligado,
			//@RequestParam String tipoPersonaFiscal,
			Model model,
			HttpServletResponse response,
			HttpSession session) {

		Usuario usuario = (Usuario)session.getAttribute("usuario"); 
				
		Map<String, Object> result = new HashMap<String, Object>();
		SujetoObligado sujetoObligado = (SujetoObligado) session.getAttribute("sujetoObligado");
		try {
			boolean afectar = administrarSolicitud(
					(String) session.getAttribute("idSolicitudRL"), 
					usuario,
					sujetoObligado,
					TipoTramiteEnum.ACTUALIZACION_REPRESENTANTE_LEGAL,
					TipoSolicitudEnum.ACTUALIZACION_DE_REPRESENTANTE_LEGAL,
					result, 
					model, 
					session);
			
			if(afectar){
				List<RepresentanteLegal> representantes = sujetoObligado.getRepresentantesLegales();
				representanteLegalServiceBusinessRemote.actualizarRepresentantesLegales(null, representantes, OrigenSolicitudEnum.INTERNET);
			}
		} catch (GestionPatronalBusinessException e) {
			e.printStackTrace();
		}
		
		// interaccion del jugoso!!!!!!!!!! XD
		
	}
	
	/**
	 * *************************************************************************************
	 * Sección de métodos utilitarios para trabajar con los elementos del objeto de sesión
	 **************************************************************************************
	 */
	
	
	public void modificarItemLista(List<RepresentanteLegal> lista, RepresentanteLegal oForm) {
		
		for (RepresentanteLegal representanteLegal : lista) {
			if (representanteLegal.getCveIdPersona().toString().equals(oForm.getCveIdPersona().toString())){
				int indexOf = lista.indexOf(representanteLegal);
				lista.remove(indexOf);
				lista.add(indexOf, oForm);
				break;
			}
		}
		/* esto no me la jala
		 * if (lista.contains(oForm)) {
			lista.remove(oForm);
			lista.add(oForm);
		}*/
	}
	
	public boolean esValido(AbstractModel inputObject,
			Map<String, Object> result, HttpServletResponse response) {
		log.debug("representanteLegalController.esValido(" + inputObject + ")");
		Errors errors = new BindException(inputObject, "model");
		
		RepresentanteLegalValidator.getInstance().validate(inputObject, errors);

		if (errors.hasErrors()) {
			this.procesaErroresDeCaptura(errors, result, response);
		}

		return !errors.hasErrors();
	}
	
	
	private boolean administrarSolicitud(String idSolicitudRL, Usuario usuario, SujetoObligado sujetoObligado, TipoTramiteEnum tipoTramite, TipoSolicitudEnum tipoSolicitud, Map<String, Object> result, Model model, HttpSession session) throws GestionPatronalBusinessException{
		
		boolean afectar = false;
		if(idSolicitudRL==null || "vacio".equals(idSolicitudRL) || "".equals(idSolicitudRL)){
			log.debug(" -- SE REGISTRA UNA NUEVA SOLICITUD [ " + sujetoObligado + " ]");
			Solicitud solicitud = solicitudServiceBusiness.generarSolicitud(tipoSolicitud, 
					EstadoSolicitudEnum.REGISTRADA, usuario, tipoTramite, 
					EstadoTramiteEnum.INICIADO, sujetoObligado, false, false);
			solicitudServiceBusiness.actualizarSolicitud(solicitud, EstadoTramiteEnum.ACTIVO, sujetoObligado);
			
			//Se agregan los datos a sesion para la impresion de reporte
			session.setAttribute("idSolicitudRL", solicitud.getSolicitudId().toString());
			session.setAttribute("sujetoObligado", sujetoObligado);
			
			result.put("mensajeExito", agregarFolioAMensaje(solicitud.getNoFolioSolicitud()));
		}else{
			Solicitud solicitud = new Solicitud();
			solicitud.setSolicitudId(Long.valueOf(idSolicitudRL));
			log.debug("ACTUALIZAR SOLICITUD -- SE ACTUALIZARA LA SOLICITUD ACTUAL [ " + sujetoObligado + " ]");
			solicitudServiceBusiness.actualizarSolicitud(solicitud, EstadoTramiteEnum.CERRADO, sujetoObligado);

			session.removeAttribute("idSolicitudRL");
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
	 * SEGMENTO PARA EL NUEVO FLUJO BASE DE REPRESENTANTES LEGALES
	 *  
	 ********************************************************************
	 ********************************************************************/
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/fb/paginarRepLegales", method = RequestMethod.POST)
	public @ResponseBody
	DatosSalidaPaginador<RepresentanteLegal> paginarRepLegales(
			@RequestBody RepresentanteLegalDataTable params, HttpSession session) {
		
		SujetoObligado sujetoTramite = (SujetoObligado) session
				.getAttribute("sujetoTramite");
		
		DatosSalidaPaginador<RepresentanteLegal> output = new DatosSalidaPaginador<RepresentanteLegal>();
		DatosEntradaPaginador<RepresentanteLegal> input = new DatosEntradaPaginador<RepresentanteLegal>();
		
		input.parserArray(params.getAoData());
		input.setModelo(params.getoForm());
		
		try {
			output.setsEcho(input.getsEcho());
			output.setAaData(sujetoTramite.getRepresentantesLegales());
		} catch (Exception e) {
			output = new DatosSalidaPaginador<RepresentanteLegal>();
			output.setAaData(params.getAoData());
			log.error(input, e);
		}
		return output;
	}		
	
	@RequestMapping(value = "/fb/agregarRepLegal", method = RequestMethod.POST)
	public @ResponseBody 
	boolean agregarRepLegal(	
			@RequestBody RepresentanteLegalDataTable params,			
		HttpSession session) {
		RepresentanteLegal nuevoRepresentante = params.getoForm();
		
		System.err.println(">>> NUEVO REPRESENTANTE LEGAL A AGREGAR AL TRAMITE: " + nuevoRepresentante);
		
		nuevoRepresentante.setAccion(TipoAccionAfectacionEnum.AGREGAR);
			
		SujetoObligado sujetoTramite = (SujetoObligado) session.getAttribute("sujetoTramite");
		SujetoObligado sujetoTramiteForRepLegal = (SujetoObligado) session.getAttribute("sujetoTramiteForRepLegal");
		
		DatosEntradaPaginador<RepresentanteLegal> send = new DatosEntradaPaginador<RepresentanteLegal>();
		List <RepresentanteLegal> representantesLegales = new ArrayList<RepresentanteLegal>();
		RepresentanteLegal representanteLegal = new RepresentanteLegal(); 
		TipoPersona tipoPersona = null;
		boolean respuesta=true;	
					
		if (sujetoTramiteForRepLegal.getTipoPersonaFiscal().name().equals(TipoPersonaFiscal.FISICA.name())){
			tipoPersona = new TipoPersona();
			tipoPersona.setIdTipoPersona(TipoPersona.TIPO_PERSONA_FISICA);
			representanteLegal.setCveIdPersona(sujetoTramiteForRepLegal.getFisica().getIdPersona());
			representanteLegal.setTipoPersonaRepresentada(tipoPersona);
			
			representantesLegales=sujetoTramite.getFisica().getRepresentantesLegales();
			
		} else if (sujetoTramiteForRepLegal.getTipoPersonaFiscal().name().equals(TipoPersonaFiscal.MORAL.name())) {
			tipoPersona = new TipoPersona();
			tipoPersona.setIdTipoPersona(TipoPersona.TIPO_PERSONA_MORAL);
			representanteLegal.setCveIdPersona(sujetoTramiteForRepLegal.getMoral().getIdPersona());
			representanteLegal.setTipoPersonaRepresentada(tipoPersona);	
			
    		representantesLegales=sujetoTramite.getMoral().getRepresentantesLegales();
    		
		}
		
		send.setModelo(representanteLegal);
				
		                
        DatosSalidaPaginador<RepresentanteLegal> reply = representanteLegalServiceBusinessRemote.paginarRepresentanteLegalParaManejoDeTramite(send);
        
        boolean noExisteRenglon=true;                	        
        //Se busca si el representante no está actualmente en la lista de representantes legales
        for(RepresentanteLegal repLegalDB : reply.getAaData()){
        	if (repLegalDB.getPersonaFisica().getIdPersona().toString().equals(nuevoRepresentante.getPersonaFisica().getIdPersona().toString())){
        		System.err.println("Ya esta la persona en la base ");
        		noExisteRenglon=false;        		
        		respuesta=false;
        		break;
        	}        	
        } 
        
        //Se busca si el representante no está actualmente en la lista "en trámite"
        for(RepresentanteLegal repLegalSession : representantesLegales){
        	if (repLegalSession.getPersonaFisica().getIdPersona().toString().equals(nuevoRepresentante.getPersonaFisica().getIdPersona().toString())){
        		System.err.println("Ya esta la persona en el tramite");
        		noExisteRenglon=false;        		
        		respuesta=false;
        		break;
        	}        	
        }                	           	    	                           	        	       	   
        if(noExisteRenglon){
        	/*
        	 * Para solventar la relacion de los medios de contacto al agregar por primera vez este
        	 * rep legal, se envía el id de la persona fisica del rep legal como cveIdRepLegal
        	 */
        	nuevoRepresentante.setCveIdRepresentanteLegal(nuevoRepresentante.getPersonaFisica().getIdPersona());
        	System.err.println("Id rep al agregar: "+nuevoRepresentante.getPersonaFisica().getIdPersona());
		    representantesLegales.add(nuevoRepresentante);	    		    	           
		    if (sujetoTramiteForRepLegal.getTipoPersonaFiscal().name().equals(TipoPersonaFiscal.FISICA.name())){
		    	sujetoTramite.getFisica().setRepresentantesLegales(representantesLegales);
		    }
		    else{        		
		    	sujetoTramite.getMoral().setRepresentantesLegales(representantesLegales);
		    }		   
        }
        
	    session.setAttribute("sujetoTramite",sujetoTramite);        	                                                           		
	    return respuesta;
	}
	
	@RequestMapping(value = "/fb/eliminarRepLegal", method = {RequestMethod.POST, RequestMethod.GET})
	public @ResponseBody boolean eliminarRepLegal(
			@RequestParam String cveIdPersonaRepLegal,			
			@RequestParam String tipoPersonaFiscal,			
			HttpSession session) {
		
		log.debug("Se eliminará al replegal: [cveIdPersonaFisica]"+cveIdPersonaRepLegal+"[tipoPersonaFiscal]"+tipoPersonaFiscal);
		
		SujetoObligado sujetoTramite = (SujetoObligado) session.getAttribute("sujetoTramite");
		SujetoObligado sujetoTramiteForRepLegal = (SujetoObligado) session.getAttribute("sujetoTramiteForRepLegal");
				
		DatosEntradaPaginador<RepresentanteLegal> send = new DatosEntradaPaginador<RepresentanteLegal>();
		RepresentanteLegal representanteLegal = new RepresentanteLegal(); 
		TipoPersona tipoPersona = null;
		
		boolean respuesta=true;			
		List <RepresentanteLegal> representantesLegales = new ArrayList<RepresentanteLegal>();
		
		if (sujetoTramite==null){					
        	sujetoTramite=new SujetoObligado();        	
        	if (tipoPersonaFiscal.equals("FISICA")){
        		tipoPersona = new TipoPersona();
				tipoPersona.setIdTipoPersona(TipoPersona.TIPO_PERSONA_FISICA);
				representanteLegal.setCveIdPersona(sujetoTramiteForRepLegal.getFisica().getIdPersona());
				representanteLegal.setTipoPersonaRepresentada(tipoPersona);
        		sujetoTramite.setFisica(new Fisica());
        		sujetoTramite.setTipoPersonaFiscal(TipoPersonaFiscal.FISICA);
        	}else{
        		tipoPersona = new TipoPersona();
				tipoPersona.setIdTipoPersona(TipoPersona.TIPO_PERSONA_MORAL);
				representanteLegal.setCveIdPersona(sujetoTramiteForRepLegal.getMoral().getIdPersona());
				representanteLegal.setTipoPersonaRepresentada(tipoPersona);	
        		sujetoTramite.setMoral(new Moral());
        		sujetoTramite.setTipoPersonaFiscal(TipoPersonaFiscal.MORAL);
        	}
        	send.setModelo(representanteLegal);
        	
		}
		else{
			if (tipoPersonaFiscal.equals("FISICA") && sujetoTramite.getFisica().getRepresentantesLegales()!=null){
				tipoPersona = new TipoPersona();
				tipoPersona.setIdTipoPersona(TipoPersona.TIPO_PERSONA_FISICA);
				representanteLegal.setCveIdPersona(sujetoTramiteForRepLegal.getFisica().getIdPersona());
				representanteLegal.setTipoPersonaRepresentada(tipoPersona);
				representantesLegales=sujetoTramite.getFisica().getRepresentantesLegales();
			}else if (tipoPersonaFiscal.equals("MORAL") && sujetoTramite.getMoral().getRepresentantesLegales()!=null) {
				tipoPersona = new TipoPersona();
				tipoPersona.setIdTipoPersona(TipoPersona.TIPO_PERSONA_MORAL);
				representanteLegal.setCveIdPersona(sujetoTramiteForRepLegal.getMoral().getIdPersona());
				representanteLegal.setTipoPersonaRepresentada(tipoPersona);	
	    		representantesLegales=sujetoTramite.getMoral().getRepresentantesLegales();	    	
			}
			send.setModelo(representanteLegal);
		}		
		
		                
        DatosSalidaPaginador<RepresentanteLegal> reply = representanteLegalServiceBusinessRemote.paginarRepresentanteLegalParaManejoDeTramite(send);                                               	        	      
        List<RepresentanteLegal> representantesLegalesDB=reply.getAaData();
        
        log.debug("<OTIKA>En DataBase hay : [RepLegales]"+representantesLegalesDB.size());
        log.debug("<OTIKA>En Session hay : [RepLegales]"+representantesLegales.size());        
        
        boolean noExisteRenglon=true;            
        
        log.debug("<OTIKA>Buscando que la persona a eliminar no esté ya en la lista de personas en trámite");
        for(RepresentanteLegal repLegalSession : representantesLegales){   	
        	//if (repLegalSession.getCveIdPersona().toString().equals(cveIdPersona)){
        	log.debug("Comparando "+repLegalSession.getPersonaFisica().getIdPersona().toString() + " y " + cveIdPersonaRepLegal);
        	if (repLegalSession.getPersonaFisica().getIdPersona().toString().equals(cveIdPersonaRepLegal)){
        		log.debug("El renglon ya tiene un tramite previo");
        		noExisteRenglon=false;        		
        		respuesta=false;
        		break;
        	}        	
        }
        
        if(noExisteRenglon){
        	log.debug("Condiciones correctas para eliminar");
        	for(RepresentanteLegal repLegalDB : representantesLegalesDB){
        		if (repLegalDB.getPersonaFisica().getIdPersona().toString().equals((cveIdPersonaRepLegal))){
        			log.debug("Se agrega el registro a eliminar en la lista de operaciones del tramite");
        			repLegalDB.setAccion(TipoAccionAfectacionEnum.ELIMINAR);        			
        			representantesLegales.add(repLegalDB);
        			break;
        		}
        	}                
                         	
        	if (tipoPersonaFiscal.equals("FISICA")){        		
        		sujetoTramite.getFisica().setRepresentantesLegales(representantesLegales);
        	}
        	else{        		        		
        		sujetoTramite.getMoral().setRepresentantesLegales(representantesLegales);
        	}
        }               
        session.setAttribute("sujetoTramite",sujetoTramite);        	                                                           		
        return respuesta;
	}
	
	
	
	@RequestMapping(value = "/fb/eliminarRepLegalPowered", method = {RequestMethod.POST, RequestMethod.GET})
	public @ResponseBody boolean eliminarRepLegalPowered(
			@RequestBody RepresentanteLegal input,
			HttpSession session) {
		String cveIdPersonaRepLegal = input.getCveIdRepresentanteLegal().toString();
		String tipoPersonaFiscal = input.getTipoPersonaRepresentada().getIdTipoPersona().equals(TipoPersona.TIPO_PERSONA_FISICA) ? "FISICA" : "MORAL";
		
		log.debug("Se eliminará al replegal: [cveIdPersonaFisica]"+cveIdPersonaRepLegal+"[tipoPersonaFiscal]"+tipoPersonaFiscal);
		
		SujetoObligado sujetoTramite = (SujetoObligado) session.getAttribute("sujetoTramite");
		SujetoObligado sujetoTramiteForRepLegal = (SujetoObligado) session.getAttribute("sujetoTramiteForRepLegal");
				
		DatosEntradaPaginador<RepresentanteLegal> send = new DatosEntradaPaginador<RepresentanteLegal>();
		RepresentanteLegal representanteLegal = new RepresentanteLegal(); 
		TipoPersona tipoPersona = null;
		
		boolean respuesta=true;			
		List <RepresentanteLegal> representantesLegales = new ArrayList<RepresentanteLegal>();
		
		if (sujetoTramite==null){					
        	sujetoTramite=new SujetoObligado();        	
        	if (tipoPersonaFiscal.equals("FISICA")){
        		tipoPersona = new TipoPersona();
				tipoPersona.setIdTipoPersona(TipoPersona.TIPO_PERSONA_FISICA);
				representanteLegal.setCveIdPersona(sujetoTramiteForRepLegal.getFisica().getIdPersona());
				representanteLegal.setTipoPersonaRepresentada(tipoPersona);
        		sujetoTramite.setFisica(new Fisica());
        		sujetoTramite.setTipoPersonaFiscal(TipoPersonaFiscal.FISICA);
        	}else{
        		tipoPersona = new TipoPersona();
				tipoPersona.setIdTipoPersona(TipoPersona.TIPO_PERSONA_MORAL);
				representanteLegal.setCveIdPersona(sujetoTramiteForRepLegal.getMoral().getIdPersona());
				representanteLegal.setTipoPersonaRepresentada(tipoPersona);	
        		sujetoTramite.setMoral(new Moral());
        		sujetoTramite.setTipoPersonaFiscal(TipoPersonaFiscal.MORAL);
        	}
        	send.setModelo(representanteLegal);
        	
		}
		else{
			if (tipoPersonaFiscal.equals("FISICA") && sujetoTramite.getFisica().getRepresentantesLegales()!=null){
				tipoPersona = new TipoPersona();
				tipoPersona.setIdTipoPersona(TipoPersona.TIPO_PERSONA_FISICA);
				representanteLegal.setCveIdPersona(sujetoTramiteForRepLegal.getFisica().getIdPersona());
				representanteLegal.setTipoPersonaRepresentada(tipoPersona);
				representantesLegales=sujetoTramite.getFisica().getRepresentantesLegales();
			}else if (tipoPersonaFiscal.equals("MORAL") && sujetoTramite.getMoral().getRepresentantesLegales()!=null) {
				tipoPersona = new TipoPersona();
				tipoPersona.setIdTipoPersona(TipoPersona.TIPO_PERSONA_MORAL);
				representanteLegal.setCveIdPersona(sujetoTramiteForRepLegal.getMoral().getIdPersona());
				representanteLegal.setTipoPersonaRepresentada(tipoPersona);	
	    		representantesLegales=sujetoTramite.getMoral().getRepresentantesLegales();	    	
			}
			send.setModelo(representanteLegal);
		}		
		
		                
        DatosSalidaPaginador<RepresentanteLegal> reply = representanteLegalServiceBusinessRemote.paginarRepresentanteLegalParaManejoDeTramite(send);                                               	        	      
        List<RepresentanteLegal> representantesLegalesDB=reply.getAaData();
        
        log.debug("<OTIKA>En DataBase hay : [RepLegales]"+representantesLegalesDB.size());
        log.debug("<OTIKA>En Session hay : [RepLegales]"+representantesLegales.size());        
        
        boolean noExisteRenglon=true;            
        
        log.debug("<OTIKA>Buscando que la persona a eliminar no esté ya en la lista de personas en trámite");
        for(RepresentanteLegal repLegalSession : representantesLegales){   	
        	//if (repLegalSession.getCveIdPersona().toString().equals(cveIdPersona)){
        	log.debug("Comparando "+repLegalSession.getPersonaFisica().getIdPersona().toString() + " y " + cveIdPersonaRepLegal);
        	if (repLegalSession.getPersonaFisica().getIdPersona().toString().equals(cveIdPersonaRepLegal)){
        		log.debug("El renglon ya tiene un tramite previo");
        		noExisteRenglon=false;        		
        		respuesta=false;
        		break;
        	}        	
        }
        
        if(noExisteRenglon){
        	log.debug("Condiciones correctas para eliminar");
        	for(RepresentanteLegal repLegalDB : representantesLegalesDB){
        		if (repLegalDB.getPersonaFisica().getIdPersona().toString().equals((cveIdPersonaRepLegal))){
        			log.debug("Se agrega el registro a eliminar en la lista de operaciones del tramite");
        			repLegalDB.setAccion(TipoAccionAfectacionEnum.ELIMINAR);        			
        			log.debug("Se obtuvieron los medios de contacto para el representante legal a eliminar de la base: "+repLegalDB.getMediosContacto().size());
        			representantesLegales.add(repLegalDB);
        			break;
        		}
        	}                
                         	
        	if (tipoPersonaFiscal.equals("FISICA")){        		
        		sujetoTramite.getFisica().setRepresentantesLegales(representantesLegales);
        	}
        	else{        		        		
        		sujetoTramite.getMoral().setRepresentantesLegales(representantesLegales);
        	}
        }               
        session.setAttribute("sujetoTramite",sujetoTramite);        	                                                           		
        return respuesta;
	}
	
	@RequestMapping(value = "/fb/modificarRepLegal", method = RequestMethod.POST)
	public @ResponseBody boolean modificarRepLegal(
			@RequestBody RepresentanteLegal repLegal,
			HttpSession session) {
		
		System.err.println(">> REPRESENTANTE LEGAL A MODIFICAR: " + repLegal + ", \n\n>>> medios de contacto de esta persona: " + repLegal.getMediosContacto());
		
		DatosEntradaPaginador<RepresentanteLegal> send = new DatosEntradaPaginador<RepresentanteLegal>();	
		List <RepresentanteLegal> representantesLegales = new ArrayList<RepresentanteLegal>();
		RepresentanteLegal representanteLegal = new RepresentanteLegal(); 
		TipoPersona tipoPersona = null;
		
		boolean respuesta=true;
		
		SujetoObligado sujetoTramite = (SujetoObligado) session.getAttribute("sujetoTramite");
		SujetoObligado sujetoTramiteForRepLegal = (SujetoObligado) session.getAttribute("sujetoTramiteForRepLegal");
		
		if (sujetoTramite==null){			
			System.err.println("Se inicializara sujeto tramite para rep legal");
        	sujetoTramite=new SujetoObligado();
        	if (repLegal.getTipoPersonaRepresentada().getDescripcion().equals("FISICA")){
        		tipoPersona = new TipoPersona();
				tipoPersona.setIdTipoPersona(TipoPersona.TIPO_PERSONA_FISICA);
				representanteLegal.setCveIdPersona(sujetoTramiteForRepLegal.getFisica().getIdPersona());
				representanteLegal.setTipoPersonaRepresentada(tipoPersona);
				representanteLegal.setMediosContacto(repLegal.getMediosContacto());
				
        		sujetoTramite.setFisica(new Fisica());
        		sujetoTramite.setTipoPersonaFiscal(TipoPersonaFiscal.FISICA);
        	}else{
        		tipoPersona = new TipoPersona();
				tipoPersona.setIdTipoPersona(TipoPersona.TIPO_PERSONA_MORAL);
				representanteLegal.setCveIdPersona(sujetoTramiteForRepLegal.getFisica().getIdPersona());
				representanteLegal.setTipoPersonaRepresentada(tipoPersona);
				representanteLegal.setMediosContacto(repLegal.getMediosContacto());
				
        		sujetoTramite.setMoral(new Moral());
        		sujetoTramite.setTipoPersonaFiscal(TipoPersonaFiscal.MORAL);
        	}
        	send.setModelo(representanteLegal);

		}
		else{
			System.err.println("Ya existe sujeto Tramite");
			if (repLegal.getTipoPersonaRepresentada().getDescripcion().equals("FISICA")){
				System.err.println("Persona representada fisica");
				tipoPersona = new TipoPersona();
				tipoPersona.setIdTipoPersona(TipoPersona.TIPO_PERSONA_FISICA);
				representanteLegal.setCveIdPersona(sujetoTramiteForRepLegal.getFisica().getIdPersona());
				representanteLegal.setTipoPersonaRepresentada(tipoPersona);
				representanteLegal.setMediosContacto(repLegal.getMediosContacto());
				
				representantesLegales=sujetoTramite.getFisica().getRepresentantesLegales();
	    	}else{
	    		System.err.println("Persona representada moral");
	    		tipoPersona = new TipoPersona();
				tipoPersona.setIdTipoPersona(TipoPersona.TIPO_PERSONA_MORAL);
				representanteLegal.setCveIdPersona(sujetoTramiteForRepLegal.getMoral().getIdPersona());
				representanteLegal.setTipoPersonaRepresentada(tipoPersona);
				representanteLegal.setMediosContacto(repLegal.getMediosContacto());
				
	    		representantesLegales=sujetoTramite.getMoral().getRepresentantesLegales();
	    	}
			send.setModelo(representanteLegal);
		}
		                
        DatosSalidaPaginador<RepresentanteLegal> reply = representanteLegalServiceBusinessRemote.paginarRepresentanteLegalParaManejoDeTramite(send);        
        
                               	        	       
        List<RepresentanteLegal> representantesLegalesDB=reply.getAaData();
        boolean noExisteRenglon=true;                	        
        for(RepresentanteLegal repLegalSession : representantesLegales){
        	System.err.println("Representante actual en sesion: "+repLegalSession);
        	if (repLegalSession.getPersonaFisica().getIdPersona().toString().equals(repLegal.getPersonaFisica().getIdPersona().toString())){
        		System.err.println("Ya hay un renglon en tramite para esta persona: "+repLegalSession);
        		noExisteRenglon=false;        		
        		respuesta=false;
        		break;
        	}        	
        }
        if(noExisteRenglon){
        	for(RepresentanteLegal repLegalDB : representantesLegalesDB){        		
        		System.err.println("No existe ese renglon de rep legal");
        		if (repLegalDB.getPersonaFisica().getIdPersona().toString().equals((repLegal.getPersonaFisica().getIdPersona().toString()))){        		
        			System.err.println("Se asigna la accion modificar y se agrega a la lista de rep legales en tramite");
        			repLegalDB.setAccion(TipoAccionAfectacionEnum.MODIFICAR);
        			repLegalDB.setIndActAdmonDominio(repLegal.getIndActAdmonDominio());
        			repLegalDB.setMediosContacto(repLegal.getMediosContacto());
        			representantesLegales.add(repLegalDB);
        			break;
        		}
        	}                
                         	
        	if (repLegal.getTipoPersonaRepresentada().getDescripcion().equals("FISICA")){
        		System.err.println("Se agrega el rep legal a la persona fisica");
        		sujetoTramite.getFisica().setRepresentantesLegales(representantesLegales);
        	}
        	else{        		
        		sujetoTramite.getMoral().setRepresentantesLegales(representantesLegales);
        	}
        }        
        session.setAttribute("sujetoTramite",sujetoTramite);        	                                                           		
        return respuesta;
	}
	
	@RequestMapping(value = "/fb/deshacerEliminarRepLegal", method = RequestMethod.GET)
	public @ResponseBody boolean deshacerEliminarRepLegal(
			@RequestParam String cveIdPersonaRepLegal,						
			@RequestParam String tipoPersonaFiscal,
			HttpSession session) {
		
		
		SujetoObligado sujetoTramite = (SujetoObligado) session.getAttribute("sujetoTramite");		
		
		List <RepresentanteLegal> representantesLegales = new ArrayList<RepresentanteLegal>();
		if (sujetoTramite==null){
			log.debug("<OTIKA>Imposible eliminar la lista de representantes legales!");			
			return false;
		}
		else{
			if (tipoPersonaFiscal.equals("FISICA")){
				representantesLegales=sujetoTramite.getFisica().getRepresentantesLegales();				
	    	}
	    	else{        		
	    		
	    		representantesLegales=sujetoTramite.getMoral().getRepresentantesLegales();
	    	}	
		}
						
        
        int index=0;                   	        	                     
        for(RepresentanteLegal repLegalSession : representantesLegales){        	
        	if (repLegalSession.getPersonaFisica().getIdPersona().toString().equals((cveIdPersonaRepLegal))){        		
        		representantesLegales.remove(index);
        		break;
        	}
    		index++;
        }                
                         	
        if (tipoPersonaFiscal.equals("FISICA")){
        	sujetoTramite.getFisica().setRepresentantesLegales(representantesLegales);
        }
        else{        		
        	sujetoTramite.getMoral().setRepresentantesLegales(representantesLegales);
        }        
        session.setAttribute("sujetoTramite",sujetoTramite);        
        return true;
	}
	
	
	@RequestMapping(value = "/fb/deshacerEliminarRepLegalPowered", method = {RequestMethod.GET, RequestMethod.POST})
	public @ResponseBody boolean deshacerEliminarRepLegalPowered(
			@RequestBody RepresentanteLegal input,
			HttpSession session) {
		
		String cveIdPersonaRepLegal = input.getCveIdRepresentanteLegal().toString();
		String tipoPersonaFiscal = input.getTipoPersonaRepresentada().getIdTipoPersona().equals(TipoPersona.TIPO_PERSONA_FISICA) ? "FISICA" : "MORAL";
		
		SujetoObligado sujetoTramite = (SujetoObligado) session.getAttribute("sujetoTramite");		
		
		List <RepresentanteLegal> representantesLegales = new ArrayList<RepresentanteLegal>();
		if (sujetoTramite==null){
			log.debug("<OTIKA>Imposible eliminar la lista de representantes legales!");			
			return false;
		}
		else{
			if (tipoPersonaFiscal.equals("FISICA")){
				representantesLegales=sujetoTramite.getFisica().getRepresentantesLegales();				
	    	}
	    	else{        		
	    		
	    		representantesLegales=sujetoTramite.getMoral().getRepresentantesLegales();
	    	}	
		}
						
        
        int index=0;                   	        	                     
        for(RepresentanteLegal repLegalSession : representantesLegales){        	
        	if (repLegalSession.getPersonaFisica().getIdPersona().toString().equals((cveIdPersonaRepLegal))){        		
        		representantesLegales.remove(index);
        		break;
        	}
    		index++;
        }                
                         	
        if (tipoPersonaFiscal.equals("FISICA")){
        	sujetoTramite.getFisica().setRepresentantesLegales(representantesLegales);
        }
        else{        		
        	sujetoTramite.getMoral().setRepresentantesLegales(representantesLegales);
        }        
        session.setAttribute("sujetoTramite",sujetoTramite);        
        return true;
	}

	
	@RequestMapping(value = "/fb/finalizar", method = RequestMethod.POST)
	public void finalizar1(
			Model model,
			HttpServletResponse response,
			HttpSession session) {

		Usuario usuario = (Usuario)session.getAttribute("usuario"); 
				
		Map<String, Object> result = new HashMap<String, Object>();
		SujetoObligado sujetoObligado = (SujetoObligado) session.getAttribute("sujetoObligado");
		try {
			boolean afectar = administrarSolicitud(
					(String) session.getAttribute("idSolicitudRL"), 
					usuario,
					sujetoObligado,
					TipoTramiteEnum.ACTUALIZACION_REPRESENTANTE_LEGAL,
					TipoSolicitudEnum.ACTUALIZACION_DE_REPRESENTANTE_LEGAL,
					result, 
					model, 
					session);
			
			if(afectar){
				List<RepresentanteLegal> representantes = sujetoObligado.getRepresentantesLegales();
				representanteLegalServiceBusinessRemote.actualizarRepresentantesLegales(null, representantes, OrigenSolicitudEnum.INTERNET);
			}
		} catch (GestionPatronalBusinessException e) {
			e.printStackTrace();
		}
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
	
	@RequestMapping(value="/fb/paginar", method=RequestMethod.POST)
    public @ResponseBody DatosSalidaPaginador<RepresentanteLegal> paginaFb(@RequestBody RepresentanteLegalDataTable aoData ,HttpSession session) {
		
		DatosEntradaPaginador<RepresentanteLegal> send = new DatosEntradaPaginador<RepresentanteLegal>();
        
		send.parserArray(aoData.getAoData());
		
		RepresentanteLegal representanteLegal = aoData.getoForm();
		
		System.err.println(">> RepresentanteLegalController.paginarFb: representanteLegal que llega para paginacion: " + representanteLegal);
		
		SujetoObligado sujetoTramite = (SujetoObligado) session.getAttribute("sujetoTramiteForRepLegal");
 		TipoPersona tipoPersona = null;
        
		if (sujetoTramite != null){
			if (sujetoTramite.getTipoPersonaFiscal().equals(TipoPersonaFiscal.FISICA)){
				tipoPersona = new TipoPersona();
				tipoPersona.setIdTipoPersona(TipoPersonaEnum.FISICA.getId());
				representanteLegal.setCveIdPersona(sujetoTramite.getFisica().getIdPersona());
				representanteLegal.setTipoPersonaRepresentada(tipoPersona);
			}else{
				tipoPersona = new TipoPersona();
				tipoPersona.setIdTipoPersona(TipoPersonaEnum.MORAL.getId());
				representanteLegal.setCveIdPersona(sujetoTramite.getMoral().getIdPersona());
				representanteLegal.setTipoPersonaRepresentada(tipoPersona);				
			}
		}
        
		System.out.println("Parametros grid rep legal: "+representanteLegal);
		
		send.setModelo(representanteLegal);
        
        DatosSalidaPaginador<RepresentanteLegal> reply = representanteLegalServiceBusinessRemote.paginarRepresentanteLegal(send);        
        System.out.println("Representantes actuales en base: "+reply.getAaData().size());
        reply.setsEcho(send.getsEcho());
        
        // subimos a sesion esta lista de rep legales proveniente de la base para realizar validaciones y ratificaciones
        session.setAttribute("repLegalListFromDBForValidations", reply.getAaData());        
        
        return reply;        
    }
	
	@RequestMapping(value="/fb/consultarRepLegal", method=RequestMethod.GET)
	public String consultarRepLegal (Model model , HttpSession session) {
		SujetoObligado sujetoObligado = (SujetoObligado) session.getAttribute("sujetoObligado");	
		sujetoObligado.setRepresentantesLegales(poblarRepresentantesForTest()); // Se colocan representantes legales para pruebas
		session.setAttribute("sujetoObligado", sujetoObligado);
		session.setAttribute("sujetoTramite", sujetoObligado);

    	model.addAttribute(sujetoObligado);
		return "fb.rep.legal";
	}

	private List<RepresentanteLegal> poblarRepresentantesForTest() {
		
		List<RepresentanteLegal> representantesLegales = new ArrayList<RepresentanteLegal>();
		RepresentanteLegal representanteLegal = new RepresentanteLegal();
		representanteLegal.setCveIdPatronSujetoObligado(1L);
		representanteLegal.setCveIdPersona(10L);
		
		Fisica personaFisica = new Fisica();
		
		personaFisica.setCurp("SACL651210MDFNRT04");
		personaFisica.setRfc("SACL651210");
		personaFisica.setNombre("Yuu");
		personaFisica.setPrimerApellido("Darvish");
		personaFisica.setIdPersona(10L);
		TipoPersona tipoPersona = new TipoPersona();
		tipoPersona.setDescripcion("PERSONA FISICA");
		tipoPersona.setIdTipoPersona(300l);
		personaFisica.setTipoPersona(tipoPersona);
		representanteLegal.setPersonaFisica(personaFisica);
		representanteLegal.setIndActAdmonDominio(BigDecimal.ONE);
				
		representantesLegales.add(representanteLegal);
		
		representanteLegal = new RepresentanteLegal();
		representanteLegal.setCveIdPatronSujetoObligado(1L);
		representanteLegal.setCveIdPersona(10L);
		
		personaFisica = new Fisica();
		
		personaFisica.setCurp("AGGV651210MDFNRT04");
		personaFisica.setRfc("AGGV651210");
		personaFisica.setNombre("Ichiro");
		personaFisica.setPrimerApellido("Susuki");
		personaFisica.setIdPersona(11L);
		tipoPersona = new TipoPersona();
		tipoPersona.setDescripcion("PERSONA FISICA");
		tipoPersona.setIdTipoPersona(300l);
		personaFisica.setTipoPersona(tipoPersona);
		representanteLegal.setPersonaFisica(personaFisica);
		representanteLegal.setIndActAdmonDominio(BigDecimal.ONE);
		representanteLegal.setCveIdPersona(11L);
		
		representantesLegales.add(representanteLegal);
		
		return representantesLegales;
	}

	@RequestMapping(value="/fb/realizarValidaciones", method=RequestMethod.POST)
	public @ResponseBody String realizarValidaciones(@RequestParam("tipoPersonaFiscal") String tipoPersonaFiscal, 
			@RequestParam("operacion") Integer operacion, 
			HttpSession session){
		int contadorConActosAdmon = 0;
		
		// para persona moral
		final String MSG_1 = "Debe registrar al menos un representante legal con Poder para actos de Administraci\u00F3n y Dominio";
		final String MSG_2 = "Debe contar al menos con un representante legal y que \u00E9ste cuente  con Poder para actos de Administraci\u00F3n y Dominio";
		final String MSG_3 = "Para almacenar un tr\u00E1mite debe agregar al menos un movimiento: AGREGAR, MODIFICAR o ELIMINAR un representante legal";
		
		
		String msg = "OK";
		
        @SuppressWarnings("unchecked")
		List<RepresentanteLegal> repLegalListFromDB = (List<RepresentanteLegal>) session.getAttribute("repLegalListFromDBForValidations");
		List<RepresentanteLegal> repLegalListFromTramite = null;
        
        if (TipoPersonaFiscal.FISICA.name().equals(tipoPersonaFiscal)){
        	repLegalListFromTramite = ((SujetoObligado) session.getAttribute("sujetoTramite")).getFisica().getRepresentantesLegales();
        	return msg;
        } else if (TipoPersonaFiscal.MORAL.name().equals(tipoPersonaFiscal)){
        	repLegalListFromTramite = ((SujetoObligado) session.getAttribute("sujetoTramite")).getMoral().getRepresentantesLegales();
        }
        System.err.println("operacion: "+operacion);
        if(operacion == 21 &&  repLegalListFromTramite.isEmpty()){
        	msg = MSG_3;
        }else{
        
	        if (repLegalListFromDB.isEmpty() && repLegalListFromTramite.isEmpty()){
	        	msg = MSG_1;
	        } else if (repLegalListFromDB.isEmpty() && !repLegalListFromTramite.isEmpty()){
	        	System.err.println("evaluando lista de representantes en tramite");
	        	int contadorAgregar = 0;
	        	int contadorAgregarConActosAdmon = 0;
	        	
	        	for (RepresentanteLegal representanteLegal : repLegalListFromTramite) {
	        		if (representanteLegal.getAccion().name().equals(TipoAccionAfectacionEnum.AGREGAR.name())){
	            		contadorAgregar++;
	            		if (representanteLegal.getIndActAdmonDominio().intValue() == 1){
	            			contadorAgregarConActosAdmon++;
	            		}
	            	}
				}
	        	System.err.println("Representantes a agregar: "+contadorAgregar);
	        	System.err.println("Representantes a agregar con dominio: "+contadorAgregarConActosAdmon);
	        	if (contadorAgregar > 0){
	        		if (contadorAgregarConActosAdmon == 0){
	        			msg = MSG_1;
	        		}
	        	}
	        } else if(!repLegalListFromDB.isEmpty() && !repLegalListFromTramite.isEmpty()){
	        	int contadorEliminar = 0;
	        	
	        	System.err.println("Se tienen datos actuales y de tramite");
	        	
	        	for (RepresentanteLegal representanteLegal : repLegalListFromTramite) {
	        		if (representanteLegal.getAccion().name().equals(TipoAccionAfectacionEnum.ELIMINAR.name())){
	        			contadorEliminar++;
	            	}
				}
	        	
	        	if (contadorEliminar == repLegalListFromDB.size()){
	        		for (RepresentanteLegal representanteLegal : repLegalListFromTramite) {
	        			String accionARealizar = representanteLegal.getAccion().name();
	        			
	        			if (accionARealizar.equals(TipoAccionAfectacionEnum.AGREGAR.name()) 
	        				 && representanteLegal.getIndActAdmonDominio().intValue() == 1){
	        				contadorConActosAdmon++;
	        			}
	    
					}
	        		
	        		if(contadorConActosAdmon==0){
	        			msg = MSG_2;
	        		}
	        	} else {
	        		System.err.println("evaluando caso por defecto");
	        		int contadorEliminarModificar = 0;
	        		
	        		for (RepresentanteLegal representanteLegal : repLegalListFromDB) {
	        			if (representanteLegal.getIndActAdmonDominio().intValue() == 1){
	        				contadorConActosAdmon++;
	                	}
					}
	        		for (RepresentanteLegal representanteLegal : repLegalListFromTramite) {
	        			if ((representanteLegal.getAccion().name().equals(TipoAccionAfectacionEnum.ELIMINAR.name()) && representanteLegal.getIndActAdmonDominio().intValue() == 1) || (representanteLegal.getAccion().name().equals(TipoAccionAfectacionEnum.MODIFICAR.name()) && representanteLegal.getIndActAdmonDominio().intValue() == 0)){
	        				contadorEliminarModificar++;
	                	}
	        			if ((representanteLegal.getAccion().name().equals(TipoAccionAfectacionEnum.AGREGAR.name()) && representanteLegal.getIndActAdmonDominio().intValue() == 1) || (representanteLegal.getAccion().name().equals(TipoAccionAfectacionEnum.MODIFICAR.name()) && representanteLegal.getIndActAdmonDominio().intValue() == 1)){
	        				contadorEliminarModificar--;
	        			}
					}
	        		
	        		if(contadorConActosAdmon <= contadorEliminarModificar){
	        			msg = MSG_2;
	        		}
	        	}
	        }
        }
        
		return msg;
	}
	
	/**
	 * Obtiene el detalle de un rep legal que vaya a ser agregado, modificado o eliminado en el trámite
	 * @param personaFisicaIdPersona
	 * @param session
	 * @return
	 */
	@RequestMapping(value="/fb/obtenerDetalleEnTramiteRepresentanteLegal" , method={RequestMethod.GET, RequestMethod.POST})
	public @ResponseBody RepresentanteLegal obtenerDetalleEnTramiteRepresentanteLegal(@RequestBody  Fisica fisica, HttpSession session){
		Long personaFisicaIdPersona = fisica.getIdPersona();
		RepresentanteLegal rl = new RepresentanteLegal();
		List <RepresentanteLegal> representantesLegales = new ArrayList<RepresentanteLegal>();
		
		SujetoObligado sujetoTramite = (SujetoObligado) session.getAttribute("sujetoTramite");
		SujetoObligado sujetoTramiteForRepLegal = (SujetoObligado) session.getAttribute("sujetoTramiteForRepLegal");
					
		if (sujetoTramiteForRepLegal.getTipoPersonaFiscal().name().equals(TipoPersonaFiscal.FISICA.name())){			
			representantesLegales=sujetoTramite.getFisica().getRepresentantesLegales();
		} else if (sujetoTramiteForRepLegal.getTipoPersonaFiscal().name().equals(TipoPersonaFiscal.MORAL.name())) {
			representantesLegales=sujetoTramite.getMoral().getRepresentantesLegales();
		}
		
		for (RepresentanteLegal representanteLegal : representantesLegales) {
			if (representanteLegal.getPersonaFisica().getIdPersona().intValue() == personaFisicaIdPersona.intValue()){
				/*
				 *  datosContactoDetalleRLAux contendra los medios de contacto originales de este rep legal en caso que el usuario
				 *  presione 'cancelar' en la pantalla del detalle del RL en el apartado del tramite
				 */
				List<MedioContacto> mediosContactoDetalleRLAux = new ArrayList<MedioContacto>();
				
				mediosContactoDetalleRLAux.addAll(representanteLegal.getMediosContacto());
				session.setAttribute("mediosContactoDetalleRLAux", mediosContactoDetalleRLAux);
				rl = representanteLegal;
				break;
			}
		}
		
		return rl;
	}
	
	@RequestMapping(value="/fb/obtenerDetalleEnTramiteRepresentanteLegalDatosContacto" , method={RequestMethod.GET, RequestMethod.POST})
	public @ResponseBody DatosSalidaPaginador<MedioContacto> obtenerDetalleEnTramiteRepresentanteLegalDatosContacto(@RequestBody MedioContactoDataTable params, HttpSession session){
		
		DatosSalidaPaginador<MedioContacto> dspmc = new DatosSalidaPaginador<MedioContacto>();
		System.err.println("personaFisicaIdPersona que llega a \"obtenerDetalleEnTramiteRepresentanteLegalDatosContacto\": " + params.getoForm().getClave()); // se reutiliza clave como id de la persona del rep legal
		
		if (params.getoForm().getClave() != null){
			DatosEntradaPaginador<MedioContacto> send = new DatosEntradaPaginador<MedioContacto>();
	        send.parserArray(params.getAoData());
	        send.setModelo(params.getoForm());
//	        send.setiDisplayStart(0);
	        
			SujetoObligado sujetoTramite = (SujetoObligado) session.getAttribute("sujetoTramite");
			SujetoObligado sujetoTramiteForRepLegal = (SujetoObligado) session.getAttribute("sujetoTramiteForRepLegal");
			
			List<RepresentanteLegal> representantesLegales = new ArrayList<RepresentanteLegal>();
			
			
			if (sujetoTramiteForRepLegal.getTipoPersonaFiscal().name().equals(TipoPersonaFiscal.FISICA.name())){			
				representantesLegales=sujetoTramite.getFisica().getRepresentantesLegales();
			} else if (sujetoTramiteForRepLegal.getTipoPersonaFiscal().name().equals(TipoPersonaFiscal.MORAL.name())) {
				representantesLegales=sujetoTramite.getMoral().getRepresentantesLegales();
			}
			
			for (RepresentanteLegal representanteLegal : representantesLegales) {
				System.err.println("Comparando: "+representanteLegal.getPersonaFisica().getIdPersona().intValue() +" y "+ params.getoForm().getClave().intValue());
				if (representanteLegal.getPersonaFisica().getIdPersona().intValue() == params.getoForm().getClave().intValue()){
					
					System.err.println(">>> Medios de contacto encontrado para ser agragado en el dt del detalle: " + representanteLegal.getMediosContacto());
					
					for (int j = 0; j < representanteLegal.getMediosContacto().size(); j++) {
						representanteLegal.getMediosContacto().get(j).setIdVista(new Long(j + 1));
						representanteLegal.getMediosContacto().get(j).setErrorFormGeneral(representanteLegal.getPersonaFisica().getIdPersona().toString());
					}
					
					List<MedioContacto> mediosADesplegar = obtenerMediosContactoDeTramiteADesplegar(representanteLegal.getMediosContacto(), send);
					
					System.err.println("Medios de contacto encontrados en la página: "+mediosADesplegar);
					if(mediosADesplegar == null || mediosADesplegar.size() == 0){
						System.err.println("Medios de contacto encontrados en la página anterior: "+mediosADesplegar);
						mediosADesplegar = obtenerMediosADesplegarPaginaAnterior(representanteLegal.getMediosContacto(), send);
					}
					
					dspmc.setiTotalDisplayRecords(representanteLegal.getMediosContacto().size());
					dspmc.setiTotalRecords(representanteLegal.getMediosContacto().size());
					dspmc.setAaData(mediosADesplegar);
					dspmc.setsEcho(send.getsEcho());
					
					break;
				}
			}
		}
		return dspmc;
	}
	
	
	@RequestMapping(value="/fb/guardarCambioDetalleEnTramiteRepresentanteLegal" , method=RequestMethod.POST)
	public @ResponseBody String guardarCambioDetalleEnTramiteRepresentanteLegal(@RequestBody RepresentanteLegal repLegalAGuardar, HttpSession session){
		String result="";
		
		List <RepresentanteLegal> representantesLegales = new ArrayList<RepresentanteLegal>();
		
		SujetoObligado sujetoTramite = (SujetoObligado) session.getAttribute("sujetoTramite");
		SujetoObligado sujetoTramiteForRepLegal = (SujetoObligado) session.getAttribute("sujetoTramiteForRepLegal");
					
		if (sujetoTramiteForRepLegal.getTipoPersonaFiscal().name().equals(TipoPersonaFiscal.FISICA.name())){			
			representantesLegales=sujetoTramite.getFisica().getRepresentantesLegales();
			
			for (int i = 0; i < representantesLegales.size(); i++) {
				
				if (representantesLegales.get(i).getPersonaFisica().getIdPersona().intValue() == repLegalAGuardar.getPersonaFisica().getIdPersona().intValue()){
					
					representantesLegales.get(i).setIndActAdmonDominio(repLegalAGuardar.getIndActAdmonDominio());
					
					sujetoTramite.getFisica().setRepresentantesLegales(representantesLegales);
					session.setAttribute("sujetoTramite", sujetoTramite);
					result = "Representante Legal en Tr\u00E1mite actualizado correctamente.";
					break;
				}
			}
		} else if (sujetoTramiteForRepLegal.getTipoPersonaFiscal().name().equals(TipoPersonaFiscal.MORAL.name())) {
			representantesLegales=sujetoTramite.getMoral().getRepresentantesLegales();
			
			for (int i = 0; i < representantesLegales.size(); i++) {
				
				if (representantesLegales.get(i).getPersonaFisica().getIdPersona().intValue() == repLegalAGuardar.getPersonaFisica().getIdPersona().intValue()){
					
					representantesLegales.get(i).setIndActAdmonDominio(repLegalAGuardar.getIndActAdmonDominio());
					
					sujetoTramite.getMoral().setRepresentantesLegales(representantesLegales);
					session.setAttribute("sujetoTramite", sujetoTramite);
					result = "Representante Legal en Tr\u00E1mite actualizado correctamente.";
					break;
				}
			}
		}
		
		return result;
	}
	
	
	/**
	 * Gestion (agregar) de datos de contacto en el detalle del elemento solicitado en el trámite
	 * @param medioContacto
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/fb/agregarDatoContactoDetalleRL", method = RequestMethod.POST)
	public @ResponseBody boolean agregarDatoContactoDetalleRL(@RequestBody MedioContacto medioContacto, HttpSession session) {
		
		List <RepresentanteLegal> representantesLegales = new ArrayList<RepresentanteLegal>();
		boolean result = false; 
		
		SujetoObligado sujetoTramite = (SujetoObligado) session.getAttribute("sujetoTramite");
		SujetoObligado sujetoTramiteForRepLegal = (SujetoObligado) session.getAttribute("sujetoTramiteForRepLegal");
		
		
//		switch(medioContacto.getTipoMedioContacto().getIdTipoMedioContacto().intValue()){
//		
//		case 1:
//			System.err.println("1	Correo Electronico");
//			medioContacto.getTipoMedioContacto().setDescripcion("Correo Electrónico");
//		break;
//			
//		case 2:
//			System.err.println("2	Telefono Fijo");
//			medioContacto.getTipoMedioContacto().setDescripcion("Teléfono Fijo");
//		break;
//		
//		case 3:
//			System.err.println("3	Telefono Movil");
//			medioContacto.getTipoMedioContacto().setDescripcion("Teléfono Móvil");
//		break;
//		
//		case 4:
//			System.err.println("4	Facebook");
//			medioContacto.getTipoMedioContacto().setDescripcion("Facebook");
//		break;
//		
//		case 5:
//			System.err.println("5	Twitter");
//			medioContacto.getTipoMedioContacto().setDescripcion("Twitter");
//		break;
//		
//		}
//					
		if (sujetoTramiteForRepLegal.getTipoPersonaFiscal().name().equals(TipoPersonaFiscal.FISICA.name())){			
			representantesLegales=sujetoTramite.getFisica().getRepresentantesLegales();
			
			for (int i = 0; i < representantesLegales.size() ; i++) {
				/*
				 * para comparar usando el id de la persona fisica, se guardó previamente
				 * en error form general de medio de contacto de forma transitoria.
				 */
				if (representantesLegales.get(i).getPersonaFisica().getIdPersona().intValue() == medioContacto.getIdVista().intValue()){
					medioContacto.setIdVista(new Long(representantesLegales.get(i).getMediosContacto().size() + 1));
					representantesLegales.get(i).getMediosContacto().add(medioContacto);
					sujetoTramite.getFisica().setRepresentantesLegales(representantesLegales);
					session.setAttribute("sujetoTramite", sujetoTramite);
					result = true;
					break;
				}
			}
			
		} else if (sujetoTramiteForRepLegal.getTipoPersonaFiscal().name().equals(TipoPersonaFiscal.MORAL.name())) {
			representantesLegales=sujetoTramite.getMoral().getRepresentantesLegales();
			
			for (int i = 0; i < representantesLegales.size() ; i++) {
				/*
				 * para comparar usando el id de la persona fisica, se guardó previamente
				 * en id vista de medio de contacto de forma transitoria.
				 */
				if (representantesLegales.get(i).getPersonaFisica().getIdPersona().intValue() == medioContacto.getIdVista().intValue()){
					medioContacto.setIdVista(new Long(representantesLegales.get(i).getMediosContacto().size() + 1));
					representantesLegales.get(i).getMediosContacto().add(medioContacto);
					sujetoTramite.getMoral().setRepresentantesLegales(representantesLegales);
					session.setAttribute("sujetoTramite", sujetoTramite);
					result = true;
					break;
				}
			}
		}
		
		return result;
	}
	
	
	
	/**
	 * Gestion (modificar) de datos de contacto en el detalle del elemento solicitado en el trámite
	 * @param medioContacto
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/fb/modificarDatoContactoDetalleRL", method = RequestMethod.POST)
	public @ResponseBody boolean modificarDatoContactoDetalleRL(@RequestBody MedioContacto medioContacto, HttpSession session) {
		
		List <RepresentanteLegal> representantesLegales = new ArrayList<RepresentanteLegal>();
		boolean result = false; 
		
		SujetoObligado sujetoTramite = (SujetoObligado) session.getAttribute("sujetoTramite");
		SujetoObligado sujetoTramiteForRepLegal = (SujetoObligado) session.getAttribute("sujetoTramiteForRepLegal");
		
		System.err.println(">>>> medioContacto a modificar: " + medioContacto);
		
//		switch(medioContacto.getTipoMedioContacto().getIdTipoMedioContacto().intValue()){
//		
//		case 1:
//			System.err.println("1	Correo Electronico");
//			medioContacto.getTipoMedioContacto().setDescripcion("Correo Electronico");
//		break;
//			
//		case 2:
//			System.err.println("2	Telefono Fijo");
//			medioContacto.getTipoMedioContacto().setDescripcion("Telefono Fijo");
//		break;
//		
//		case 3:
//			System.err.println("3	Telefono Movil");
//			medioContacto.getTipoMedioContacto().setDescripcion("Telefono Movil");
//		break;
//		
//		case 4:
//			System.err.println("4	facebook");
//			medioContacto.getTipoMedioContacto().setDescripcion("facebook");
//		break;
//		
//		case 5:
//			System.err.println("5	twitter");
//			medioContacto.getTipoMedioContacto().setDescripcion("twitter");
//		break;
//		
//		}
					
		if (sujetoTramiteForRepLegal.getTipoPersonaFiscal().name().equals(TipoPersonaFiscal.FISICA.name())){			
			representantesLegales=sujetoTramite.getFisica().getRepresentantesLegales();
			
			for (int i = 0; i < representantesLegales.size() ; i++) {
				/*
				 * para comparar usando el id de la persona fisica, se guardó previamente
				 * en error form general de medio de contacto de forma transitoria.
				 */
				if (representantesLegales.get(i).getPersonaFisica().getIdPersona().intValue() == Integer.parseInt(medioContacto.getErrorFormGeneral())){
					
					for (int j = 0; j < representantesLegales.get(i).getMediosContacto().size(); j++) {
						if (representantesLegales.get(i).getMediosContacto().get(j).getIdVista().intValue() == medioContacto.getIdVista()){
							representantesLegales.get(i).getMediosContacto().get(j).setTipoMedioContacto(medioContacto.getTipoMedioContacto());
							representantesLegales.get(i).getMediosContacto().get(j).setDesFormaContacto(medioContacto.getDesFormaContacto());
						}
					}
					sujetoTramite.getFisica().setRepresentantesLegales(representantesLegales);
					session.setAttribute("sujetoTramite", sujetoTramite);
					result = true;
					break;
				}
			}
			
		} else if (sujetoTramiteForRepLegal.getTipoPersonaFiscal().name().equals(TipoPersonaFiscal.MORAL.name())) {
			representantesLegales=sujetoTramite.getMoral().getRepresentantesLegales();
			
			for (int i = 0; i < representantesLegales.size() ; i++) {
				/*
				 * para comparar usando el id de la persona fisica, se guardó previamente
				 * en error form general de medio de contacto de forma transitoria.
				 */
				if (representantesLegales.get(i).getPersonaFisica().getIdPersona().intValue() == Integer.parseInt(medioContacto.getErrorFormGeneral())){
					
					for (int j = 0; j < representantesLegales.get(i).getMediosContacto().size(); j++) {
						if (representantesLegales.get(i).getMediosContacto().get(j).getIdVista().intValue() == medioContacto.getIdVista()){
							representantesLegales.get(i).getMediosContacto().get(j).setTipoMedioContacto(medioContacto.getTipoMedioContacto());
							representantesLegales.get(i).getMediosContacto().get(j).setDesFormaContacto(medioContacto.getDesFormaContacto());
						}
					}
					sujetoTramite.getMoral().setRepresentantesLegales(representantesLegales);
					session.setAttribute("sujetoTramite", sujetoTramite);
					result = true;
					break;
				}
			}
		}
		
		return result;
	}
	
	/**
	 * Gestion (eliminar) de datos de contacto en el detalle del elemento solicitado en el trámite
	 * @param medioContacto
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/fb/eliminarDatoContactoDetalleRL", method = {RequestMethod.GET, RequestMethod.POST})
	public @ResponseBody boolean eliminarDatoContactoDetalleRL(
//			@RequestParam String personaFisicaIdPersona,			
//			@RequestParam Integer idVista, 
			@RequestBody RepresentanteLegal representante,
			HttpSession session) {
		
		boolean result = false;
		
		List <RepresentanteLegal> representantesLegales = new ArrayList<RepresentanteLegal>();
		
		
		SujetoObligado sujetoTramite = (SujetoObligado) session.getAttribute("sujetoTramite");
		SujetoObligado sujetoTramiteForRepLegal = (SujetoObligado) session.getAttribute("sujetoTramiteForRepLegal");
		Long personaFisicaIdPersona = representante.getPersonaFisica().getCveFisica();
		Long idVista = representante.getIdVista();
		System.err.println(">>>> personaFisicaIdPersona - medioContacto a eliminar: " + personaFisicaIdPersona);
		
					
		if (sujetoTramiteForRepLegal.getTipoPersonaFiscal().name().equals(TipoPersonaFiscal.FISICA.name())){			
			representantesLegales=sujetoTramite.getFisica().getRepresentantesLegales();
			
			for (int i = 0; i < representantesLegales.size() ; i++) {
				/*
				 * para comparar usando el id de la persona fisica, se guardó previamente
				 * en error form general de medio de contacto de forma transitoria.
				 */
				if (representantesLegales.get(i).getPersonaFisica().getIdPersona().intValue() == personaFisicaIdPersona.intValue()){
					
					for (int j = 0; j < representantesLegales.get(i).getMediosContacto().size(); j++) {
						if (representantesLegales.get(i).getMediosContacto().get(j).getIdVista().intValue() == idVista.intValue()){
							representantesLegales.get(i).getMediosContacto().remove(j);
							break;
						}
					}
					sujetoTramite.getFisica().setRepresentantesLegales(representantesLegales);
					session.setAttribute("sujetoTramite", sujetoTramite);
					result = true;
					break;
				}
			}
			
		} else if (sujetoTramiteForRepLegal.getTipoPersonaFiscal().name().equals(TipoPersonaFiscal.MORAL.name())) {
			representantesLegales=sujetoTramite.getMoral().getRepresentantesLegales();
			
			for (int i = 0; i < representantesLegales.size() ; i++) {
				/*
				 * para comparar usando el id de la persona fisica, se guardó previamente
				 * en error form general de medio de contacto de forma transitoria.
				 */
				if (representantesLegales.get(i).getPersonaFisica().getIdPersona().intValue() == personaFisicaIdPersona.intValue()){
					
					for (int j = 0; j < representantesLegales.get(i).getMediosContacto().size(); j++) {
						if (representantesLegales.get(i).getMediosContacto().get(j).getIdVista().intValue() == idVista.intValue()){
							representantesLegales.get(i).getMediosContacto().remove(j);
							break;
						}
					}
					sujetoTramite.getMoral().setRepresentantesLegales(representantesLegales);
					session.setAttribute("sujetoTramite", sujetoTramite);
					result = true;
					break;
				}
			}
		}
		return result;
	}
	
	@RequestMapping(value = "/fb/cancelarEdicionMediosContactoRepLegalEnDetalle", method = {RequestMethod.GET, RequestMethod.POST})
	public @ResponseBody boolean cancelarEdicionMediosContactoRepLegalEnDetalle(@RequestBody RepresentanteLegal repLegalEntrante, HttpSession session){
		boolean result = false;
		
		SujetoObligado sujetoTramite = (SujetoObligado) session.getAttribute("sujetoTramite");
		SujetoObligado sujetoTramiteForRepLegal = (SujetoObligado) session.getAttribute("sujetoTramiteForRepLegal");
		@SuppressWarnings("unchecked")
		List<MedioContacto> medioContactoListAux = (List<MedioContacto>) session.getAttribute("mediosContactoDetalleRLAux");
		System.err.println("medioContactoListAux: " + medioContactoListAux);
		
		if (sujetoTramiteForRepLegal.getTipoPersonaFiscal().name().equals(TipoPersonaFiscal.FISICA.name())){			
			List<RepresentanteLegal> representantesLegales = sujetoTramite.getFisica().getRepresentantesLegales();
			
			for (int i = 0; i < representantesLegales.size(); i++) {
				
				if (representantesLegales.get(i).getPersonaFisica().getIdPersona().intValue() == repLegalEntrante.getPersonaFisica().getIdPersona().intValue()){
					representantesLegales.get(i).setMediosContacto(medioContactoListAux);
					sujetoTramite.getFisica().setRepresentantesLegales(representantesLegales);
					session.setAttribute("sujetoTramite", sujetoTramite);
					result = true;
					break;
				}
			}
		} else if (sujetoTramiteForRepLegal.getTipoPersonaFiscal().name().equals(TipoPersonaFiscal.MORAL.name())) {
			List<RepresentanteLegal> representantesLegales = sujetoTramite.getMoral().getRepresentantesLegales();
			
			for (int i = 0; i < representantesLegales.size(); i++) {
				
				if (representantesLegales.get(i).getPersonaFisica().getIdPersona().intValue() == repLegalEntrante.getPersonaFisica().getIdPersona().intValue()){
					representantesLegales.get(i).setMediosContacto(medioContactoListAux);
					sujetoTramite.getMoral().setRepresentantesLegales(representantesLegales);
					session.setAttribute("sujetoTramite", sujetoTramite);
					result = true;
					break;
				}
			}
		}
		return result;
	}
	
	@RequestMapping(value = "/fb/validaMovimientoPrevio", method = {RequestMethod.GET, RequestMethod.POST})
	public @ResponseBody Map<String, Object> validarMovimientoPrevio(
			@RequestBody RepresentanteLegal repLegal,
			HttpSession session) {
		
		System.err.println("Verificando movimiento previo");
		System.err.println("Representante Legal a validar: "+repLegal.getCveIdRepresentanteLegal());
		Map<String, Object> result = new HashMap<String, Object>();
		SujetoObligado sujetoTramite = (SujetoObligado) session.getAttribute("sujetoTramite");
		List<RepresentanteLegal> representantesEnTramite = null;
		result.put("existeMovimientoPrevio", false);
		if(sujetoTramite.getFisica() != null){
			representantesEnTramite = sujetoTramite.getFisica().getRepresentantesLegales();
		}else if(sujetoTramite.getMoral() != null){
			representantesEnTramite = sujetoTramite.getMoral().getRepresentantesLegales();
		}
		
		if(representantesEnTramite!=null){
			for(RepresentanteLegal representanteEnTramite : representantesEnTramite){
				System.err.println("Representante actualmente en tramite: "+representanteEnTramite.getCveIdRepresentanteLegal());
				if(representanteEnTramite.getCveIdRepresentanteLegal().equals(repLegal.getCveIdRepresentanteLegal())){
					result.put("existeMovimientoPrevio", true);
					result.put("accionPreviamenteSolicitada", representanteEnTramite.getAccion().name());
				}
			}
		}
		
		return result;
	}
	
	
	@RequestMapping(value = "/validaMediosContacto", method = {RequestMethod.GET, RequestMethod.POST})
	public @ResponseBody Map<String, Object> validarMediosContactoRequeridos(
			@RequestBody RepresentanteLegal repLegal,
			HttpSession session) {
		Map<String, Object> result = new HashMap<String, Object>();
		
		SujetoObligado sujetoTramite = (SujetoObligado) session.getAttribute("sujetoTramite");
		List<RepresentanteLegal> representantesEnTramite = null;
		RepresentanteLegal representanteEnTramiteSeleccionado = null;
		if(sujetoTramite.getFisica() != null){
			representantesEnTramite = sujetoTramite.getFisica().getRepresentantesLegales();
		}else if(sujetoTramite.getMoral() != null){
			representantesEnTramite = sujetoTramite.getMoral().getRepresentantesLegales();
		}
		
		if(representantesEnTramite!=null){
			for(RepresentanteLegal representanteEnTramite : representantesEnTramite){
				System.err.println("Representante actualmente en tramite: "+representanteEnTramite.getCveIdRepresentanteLegal());
				
				if(repLegal.getCveIdRepresentanteLegal()!=null){
					if(representanteEnTramite.getCveIdRepresentanteLegal().equals(repLegal.getCveIdRepresentanteLegal())){
						representanteEnTramiteSeleccionado = representanteEnTramite;
						break;
					}
				}else {
					if(repLegal.getPersonaFisica().getIdPersona().equals(representanteEnTramite.getPersonaFisica().getIdPersona())){
						representanteEnTramiteSeleccionado = representanteEnTramite;
						break;
					}
				}
			}
		}
		boolean correoPresente = false;
		boolean telefonoFijoPresente = false;
		boolean telefonoMovilPresente = false;
		
		for(MedioContacto medio : representanteEnTramiteSeleccionado.getMediosContacto()){
			if(medio.getTipoMedioContacto().getIdTipoMedioContacto().equals(TipoMedioContacto.TIPO_CORREO_ELECTRONICO)){
				correoPresente = true;
			}else if(medio.getTipoMedioContacto().getIdTipoMedioContacto().equals(TipoMedioContacto.TIPO_TELEFONO_FIJO)){
				telefonoFijoPresente = true;
			} else if(medio.getTipoMedioContacto().getIdTipoMedioContacto().equals(TipoMedioContacto.TIPO_TELEFONO_MOVIL)){
				telefonoMovilPresente = true;
			}
		}
		
		boolean valido = (telefonoFijoPresente || telefonoMovilPresente) && correoPresente;
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
}