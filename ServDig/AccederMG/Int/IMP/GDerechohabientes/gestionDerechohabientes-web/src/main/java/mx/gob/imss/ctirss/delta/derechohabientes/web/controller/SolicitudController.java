package mx.gob.imss.ctirss.delta.derechohabientes.web.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import mx.gob.imss.ctirss.delta.derechohabientes.service.utility.interfaces.BitacoraTramiteServiceRemote;
import mx.gob.imss.ctirss.delta.derechohabientes.service.utility.interfaces.SolicitudServiceRemote;
import mx.gob.imss.ctirss.delta.derechohabientes.web.bean.RespuestaJSON;
import mx.gob.imss.ctirss.delta.derechohabientes.web.bean.solicitud.SolicitudesAtendidasDataTable;
import mx.gob.imss.ctirss.delta.derechohabientes.web.bean.solicitud.SolicitudesAtendidasVb;
import mx.gob.imss.ctirss.delta.derechohabientes.web.bean.solicitud.SolicitudesPenAutVb;
import mx.gob.imss.ctirss.delta.derechohabientes.web.bean.solicitud.SolicitudesPendAutDataTable;
import mx.gob.imss.ctirss.delta.exception.derechohabiente.DerechohabientesBusinessException;
import mx.gob.imss.ctirss.delta.framework.base.controller.AbstractController;
import mx.gob.imss.ctirss.delta.framework.paginador.model.DatosEntradaPaginador;
import mx.gob.imss.ctirss.delta.framework.paginador.model.DatosSalidaPaginador;
import mx.gob.imss.ctirss.delta.gestion.solicitud.service.interfaces.SolicitudBusinessRemote;
import mx.gob.imss.ctirss.delta.model.ModuloEnum;
import mx.gob.imss.ctirss.delta.model.Usuario;
import mx.gob.imss.ctirss.delta.model.asegurado.AsignacionNSS;
import mx.gob.imss.ctirss.delta.model.derechohabiente.dto.SolicitudNssDto;
import mx.gob.imss.ctirss.delta.model.derechohabientes.negocio.dto.PaginacionDto;
import mx.gob.imss.ctirss.delta.model.derechohabientes.negocio.dto.SolicitudDto;
import mx.gob.imss.ctirss.delta.model.derechohabientes.negocio.dto.SolicitudesPendientesAutorizacionDto;
import mx.gob.imss.ctirss.delta.model.enums.RazonResultadoEnum;
import mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio.dto.ImpresionReporteDto;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.EstadoSolicitudEnum;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.TipoTramiteEnum;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.OrigenSolicitudEnum;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.Solicitud;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.RazonResultado;
import mx.gob.imss.ctirss.delta.model.util.Constants;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

/**
 * @author Mario Teran Blanco
 * @company IMSS (Instituto Mexicano del Seguro Social)
 * @date 10/04/2012
 */
@Controller
@RequestMapping(value = "/solicitud/*")
//nesesario para tener en session el Bean
@SessionAttributes({"solicitudesPenAutVb"})
public class SolicitudController extends AbstractController{

	@Autowired
	private SolicitudServiceRemote solicitudServiceRemote;
	@Autowired
	private BitacoraTramiteServiceRemote bitacoraServiceRemote;
	@Autowired
	private SolicitudBusinessRemote solicitudBusiness;
	
	
	@RequestMapping(value = "/finalizada")
	public String muestraSolicitudFinalizada(HttpServletRequest request, HttpSession session) {
		
		Solicitud solicitud = (Solicitud) session.getAttribute("solicitud");
		ImpresionReporteDto reporte = (ImpresionReporteDto) session.getAttribute("reporte");
		
		request.setAttribute("solicitud", solicitud);
		request.setAttribute("reporte", reporte);	
		
		return "finalizacionTramite";
	}
	
	@RequestMapping(value = "/errorFinalizado")
	public String errorAlFinalizar(HttpServletRequest request, HttpSession session) {
		
		Boolean mostrarBoton = (Boolean)session.getAttribute("mostrarBoton");
		String exception = (String) session.getAttribute("exception");
		String error = (String) session.getAttribute("error");
		
		request.setAttribute("mostrarBoton", mostrarBoton);
		request.setAttribute("exception", exception);
		request.setAttribute("error", error);
		
		return "internalError";
	}
	/**
	 * 
	 * @param idSolicitud
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/detalle", method = RequestMethod.POST )
	public String detalleSolicitud(@RequestParam(value = "idSolicitud") Long idSolicitud, HttpSession session, Model model, HttpServletRequest request) {
		AsignacionNSS miAsignacionNss = null;
		Solicitud encontrada = new Solicitud(idSolicitud);
		
		try {
			miAsignacionNss = (AsignacionNSS) session.getAttribute(Constants.ASIGNACION_NSS_SESSION_NAME);
			
			encontrada = solicitudBusiness.consultar(encontrada);
//			encontrada = solicitudServiceRemote.detalleSolicitud(idSolicitud);
			//Agregamos los datos de la solicitud y el asegurado
			model.addAttribute("solicitud", encontrada);
			model.addAttribute("asegurado", miAsignacionNss);
		} catch (Exception e) {			
			request.setAttribute("errores", e.getMessage());
		}		

		return "detalleSolicitud";
	}
	
	
	
	@RequestMapping(value = "/mostrarDetalleSolicitud" )
	public String mostrarDetalleSolicitud(
		HttpSession session, Model model, HttpServletRequest request) {
		
		
		
		return "mostrarDetalleSolicitud";
		
		
	}
	
	
	/**
	 * 
	 * @param idSolicitud
	 * @param model
	 * @param request
	 * @return mensaje a pantalla
	 */
	@RequestMapping( value = "/cancelar", method = RequestMethod.POST)
	public @ResponseBody RespuestaJSON<String> cancelarSolicitud(@RequestBody Solicitud solicitud, HttpServletRequest request, HttpSession session) {
		
		RespuestaJSON<String> respuesta = new RespuestaJSON<String>();
		Usuario usuario = (Usuario) session.getAttribute(Usuario.SES_NAME);
		try {
			//solicitudServiceRemote.cancelarSolicitud(solicitud.getSolicitudId(),miUsuario.getFisica());
			log.debug("Se cancelara la solicitud: "+solicitud.getSolicitudId()+"y la razon de cancelacion " + solicitud.getRazonCancelacion().getIdRazonCancelacion()
					+ "con las siguientes observaciiones " + solicitud.getObservacion());
			solicitudBusiness.cancelarSolicitud(solicitud.getSolicitudId(), solicitud.getRazonCancelacion().getIdRazonCancelacion(),1L,usuario.getCveIdUsuario(), solicitud.getObservacion());
			respuesta.setModelo("La solicitud fue cancelada correctamente");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			respuesta.setModelo("La solicitud no pudo ser cancelada");
		}
		
		return respuesta;
	}
	
	/**
	 * Muestra las solicitudes pendientes de Aut.
	 * @param idSubDelegacion
	 * @param numNSS
	 * @param refFolio
	 * @param request
	 * @return
	 */
	
	@RequestMapping(value = "/muestraSolicitudesPenAut", method = RequestMethod.POST)
	public @ResponseBody DatosSalidaPaginador<SolicitudNssDto> muestraSolicitudesPendAut(@RequestBody SolicitudesPendAutDataTable aoData, HttpSession session) {
		
//		AsignacionNSS asignacionNSS=null;
		DatosSalidaPaginador<SolicitudNssDto> datosSalidaPaginador=new DatosSalidaPaginador<SolicitudNssDto>();
		//obtener parametros de session session
		SolicitudesPenAutVb sPAVb= new SolicitudesPenAutVb();
		sPAVb.setSolicitudesPendientesAutorizacionDto(new SolicitudesPendientesAutorizacionDto());
		Usuario usuario=(Usuario) session.getAttribute(Usuario.SES_NAME);
		
		//paginacion
		@SuppressWarnings("rawtypes")
		DatosEntradaPaginador envio = new DatosEntradaPaginador();
        envio.parserArray(aoData.getAoData());
        sPAVb.getSolicitudesPendientesAutorizacionDto().setPagStar(new Long(envio.getiDisplayStart()));
        sPAVb.getSolicitudesPendientesAutorizacionDto().setPagEnd(new Long(envio.getiDisplayLength()));
		
		//llamado al servicio
        try{
//        	if(sPAVb.getSolicitudesPendientesAutorizacionDto().getNumNss() == null){
//        		log.debug("esta llegando el nss vacio voy por folio");
//        		asignacionNSS = solicitudServiceRemote.getSolicitudFolio(sPAVb.getSolicitudesPendientesAutorizacionDto().getFolio());
//        	}else{
//	        	//se obtiene el Asignacion nss que se utilizara para la consulta
//	        	asignacionNSS=this.solicitudServiceRemote.getAsignacionNssPorNss(sPAVb.getSolicitudesPendientesAutorizacionDto().getNumNss());
//        	}
        
        	//asignacion nss
//        	sPAVb.getSolicitudesPendientesAutorizacionDto().setAsignacionNSS(asignacionNSS);
        	//id umf
        	sPAVb.getSolicitudesPendientesAutorizacionDto().setIdUmf(usuario.getIdUmf());
        	datosSalidaPaginador=this.solicitudServiceRemote.listSolicitudesPendAut(sPAVb.getSolicitudesPendientesAutorizacionDto());
        	//se le pasa a mario oara el detalle
//    		session.setAttribute(Constants.ASIGNACION_NSS_SESSION_NAME, asignacionNSS);
		
        }catch(DerechohabientesBusinessException e){
        	datosSalidaPaginador.setAaData(new ArrayList<SolicitudNssDto>());       	
        } catch (Exception e) {
			log.error("Error inesperado",e);
		}finally{
        	datosSalidaPaginador.setsEcho(envio.getsEcho());
        }
		return datosSalidaPaginador;
		
	}
	
	@RequestMapping(value = "/muestraSolicitudesAtendidas", method = RequestMethod.POST)
	public @ResponseBody DatosSalidaPaginador<SolicitudNssDto> muestraSolicitudesAtendidas(@RequestBody SolicitudesAtendidasDataTable aoData, HttpSession session) {
		SolicitudesAtendidasVb solAtenVb= (SolicitudesAtendidasVb) session.getAttribute(SolicitudesAtendidasVb.SES_NAME);
		DatosSalidaPaginador<SolicitudNssDto> datosSalidaPaginador=new DatosSalidaPaginador<SolicitudNssDto>();		
		
		//paginacion
		@SuppressWarnings("rawtypes")
		DatosEntradaPaginador envio = new DatosEntradaPaginador();
        envio.parserArray(aoData.getAoData());
        solAtenVb.getSolicitudesAtendidasDto().setPagStar(new Long(envio.getiDisplayStart()));
        solAtenVb.getSolicitudesAtendidasDto().setPagEnd(new Long(envio.getiDisplayLength()));
		
		try {
			datosSalidaPaginador = bitacoraServiceRemote.listSolicitudesAtendidas(solAtenVb.getSolicitudesAtendidasDto());
		} catch (DerechohabientesBusinessException e) {
			datosSalidaPaginador.setAaData(new ArrayList<SolicitudNssDto>());       	
        } catch (Exception e) {
			log.error("Error inesperado");
		}finally{
        	datosSalidaPaginador.setsEcho(envio.getsEcho());
        }
		
		return datosSalidaPaginador;		
	}
	
	/**
	 * Metodo que muestra las solicitudes registradas mediante cualquier medio distinto a ventanilla
	 * @param aoData
	 * @param session
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/muestraSolicitudesOtrosOrigenes", method = RequestMethod.POST)
	public @ResponseBody DatosSalidaPaginador<Solicitud> muestraSolicitudesRegistradasMedianteOtroOrigen(@RequestBody SolicitudesPendAutDataTable aoData, 
			HttpSession session, HttpServletRequest request) {
		
		//invocamos el metodo que busca las solicitudes
		return this.listadoSolicitudesActivas(session, aoData, request,false);
		
	}
	
	/**
	 * Metodo que muestra las solicitudes registradas mediante ventanilla
	 * @param aoData
	 * @param session
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/muestraSolicitudesRegistradas", method = RequestMethod.POST)
	public @ResponseBody DatosSalidaPaginador<Solicitud> muestraSolicitudesRegistradasVentanilla(@RequestBody SolicitudesPendAutDataTable aoData, 
			HttpSession session, HttpServletRequest request) {
		
		//invocamos el metodo que busca las solicitudes
		return this.listadoSolicitudesActivas(session, aoData, request,true);
		
	}
	

	/**
	 * Metodo para consultar las solicitudes de un grupo familiar que aun no han sido finalizadas
	 * ya sea para mostrar las solicitudes generadas mediante ventanilla o las que no sean registradas
	 * desde ventanilla, para indicar si se muestrans las de ventanilla o no se usa el atributo origenVentanilla
	 * @param session
	 * @param aoData
	 * @param request
	 * @param origenVentanilla - Si se quieren las de ventanilla el atributo debe ser true
	 * @return
	 */
	private DatosSalidaPaginador<Solicitud> listadoSolicitudesActivas(HttpSession session,SolicitudesPendAutDataTable aoData,
			HttpServletRequest request, Boolean origenVentanilla) {
		//Obtnemos el nss de session
		AsignacionNSS asignacionNSS= (AsignacionNSS) session.getAttribute(Constants.ASIGNACION_NSS_SESSION_NAME);
		//Creamos el obneto que retornaremos
		DatosSalidaPaginador<Solicitud> datosSalidaPaginador=new DatosSalidaPaginador<Solicitud>();
		//lista de origenes de las solicitudes que buscaremos
		Long[] origenesSolicitud = {OrigenSolicitudEnum.VENTANILLA.getId()};
		//los estados de las solicitudes que buscaremos
		Long[] estadosSolicitud = {EstadoSolicitudEnum.REGISTRADA.getValor().longValue(),EstadoSolicitudEnum.PENDIENTE_AUTORIZACION.getValor().longValue()};
		//creamos el objeto de consulta
		SolicitudDto solicitudDto=new SolicitudDto();
		
		//parseamos los datos de la tabla
		DatosEntradaPaginador<Solicitud> envio = new DatosEntradaPaginador<Solicitud>();
        envio.parserArray(aoData.getAoData());
        
        //Establecemos los criterios de la consulta
        solicitudDto.setNumNss(asignacionNSS.getNssStr());
        //Se setea el id asignacion de NSS
        solicitudDto.setCveIdNSS(asignacionNSS.getIdAsignacionNSS());
        solicitudDto.setPaginacionDto(new PaginacionDto());
        //Establecemos el inicio u fin del paginador en la consulta
        solicitudDto.getPaginacionDto().setPagStar(new Long(envio.getiDisplayStart()));
        solicitudDto.getPaginacionDto().setPagEnd(new Long(envio.getiDisplayLength()));
        //Establecemos el modulo al que pertenecen las solicitudes
        solicitudDto.setCveModulo(ModuloEnum.DERECHOHABIENTES.getCodigo().longValue());
        //Establecemos el origen u origenes de las solicitudes a buscar
        solicitudDto.setCveOrigenesSol(Arrays.asList(origenesSolicitud));
        //establecemos los estados de la solicitud
        solicitudDto.setEstadosSolicitud(Arrays.asList(estadosSolicitud));
        //si se quieren mostrar las de ventanilla tenemos que poner la bandera en false para que solo se usen los origenes de la lista
        solicitudDto.setExluirOrigenes(!origenVentanilla);
 
        try{
        	//invocamos el sevicio de consulta
        	datosSalidaPaginador=solicitudServiceRemote.solicitudesDerechohabientes(solicitudDto);
        }catch(DerechohabientesBusinessException e){
        	//RNGD0074 Solicitudes pendientes de completar
			request.setAttribute("exception", "exception.RNGD0074");
        } catch (Exception e) {
        	request.setAttribute("error", e);
		}
        
        datosSalidaPaginador.setsEcho(envio.getsEcho());
        //retornamos los datos
		return datosSalidaPaginador;
		
	}
	/**
	 * Muestra solicitudesRegistradas
	 * @param idSolicitud
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/detalleReg", method = RequestMethod.POST )
	public String detalleSolicitudReg(@RequestParam(value = "idSolicitud") Long idSolicitud, Model model, HttpServletRequest request) {

		Solicitud encontrada=null;
		try {
			encontrada = solicitudServiceRemote.detalleSolicitud(idSolicitud);
		} catch (DerechohabientesBusinessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		model.addAttribute("solicitud", encontrada);

		return "detalleSolicitudReg";
	}
	
	
	/**
	 * Obteine las razones para el rechazo de un tr&aacute;mite
	 * 
	 * @param idTipoTramite
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/cargarRazonRechazo")
	public String cargarRazonRechazoSinTipo(Model model){
		return cargarRazonRechazo(0,model);
	}
	
	
	/**
	 * Obteine las razones para el rechazo de un tr&aacute;mite
	 * 
	 * @param idTipoTramite
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/cargarRazonRechazo/{idTipoTramite}")
	public String cargarRazonRechazo(@PathVariable("idTipoTramite") Integer idTipoTramite, Model model){
		
		//agregamos los tipos de razones
		List<Long> idRazones = new ArrayList<Long>();
		idRazones.add(RazonResultadoEnum.DOCUMENTOS_INCOMPLETOS.getId()); //Documentos probatorio incompletos
		idRazones.add(RazonResultadoEnum.IMPROCEDENCIA.getId()); //Improcedencia
		idRazones.add(RazonResultadoEnum.SOLICITUD_CANCELADA.getId());
		
		//si el tipo de tramite es registro de padres agre
		if(idTipoTramite != null && idTipoTramite.equals(TipoTramiteEnum.REGISTRO_PADRES.getCodigo())) {
			idRazones.add(RazonResultadoEnum.CONVIVENCI_DEPENDENCIA_NO_COMPROBADA.getId()); //Convivencia-dependencia no comprobadas
		}
		
		//obtenemos las razones de resultado y las seteamos en el model
		this.llenarRazonesResultado(model, idRazones);
		
		return "rechazoSolicitud";
	}
	
	@RequestMapping(value = "/cargarCancelarOtroOrigen")
	public String cargarRazon(Model model){
		
		Long[] idsRazones = {RazonResultadoEnum.SOLICITUD_CANCELADA.getId()};
		this.llenarRazonesResultado(model, Arrays.asList(idsRazones));
		return "razonRechazoOtrosMedios";
	}
	
	private void llenarRazonesResultado(Model model, List<Long> idsRazonResultado) {
		try{
			List<RazonResultado> razonesList = solicitudBusiness.obtenerRazonesResultado(idsRazonResultado);
			model.addAttribute("razones", razonesList);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	
	@RequestMapping(value = "/muestraSolicitudesConcluidas", method = RequestMethod.POST)
	public @ResponseBody DatosSalidaPaginador<Solicitud> muestraSolicitudesConcluidas(@RequestBody SolicitudesPendAutDataTable aoData, HttpSession session, HttpServletRequest request) {
		
	
		log.debug("------------------------- Se consultara las solicitudes concluidas");
		DatosSalidaPaginador<Solicitud> datosSalidaPaginador=new DatosSalidaPaginador<Solicitud>();
		SolicitudDto solicitudDto=new SolicitudDto();
		//obtener parametros de session session
		AsignacionNSS asignacionNSS= (AsignacionNSS) session.getAttribute(Constants.ASIGNACION_NSS_SESSION_NAME);
		solicitudDto.setNumNss(asignacionNSS.getNssStr());
		solicitudDto.setCveIdNSS(asignacionNSS.getIdAsignacionNSS());

		//paginacion

		@SuppressWarnings("rawtypes")
		DatosEntradaPaginador envio = new DatosEntradaPaginador();
        envio.parserArray(aoData.getAoData());
        solicitudDto.setPaginacionDto(new PaginacionDto());
        solicitudDto.getPaginacionDto().setPagStar(new Long(envio.getiDisplayStart()));
        solicitudDto.getPaginacionDto().setPagEnd(new Long(envio.getiDisplayLength()));
        
        solicitudDto.setCveModulo(ModuloEnum.DERECHOHABIENTES.getCodigo().longValue());
        //solicitudDto.setCveOrigenSol(OrigenSolicitudEnum.VENTANILLA.getId());
        
        List<Long> lstEstadoSolicitud = new ArrayList<Long>();
        lstEstadoSolicitud.add(EstadoSolicitudEnum.CANCELADA.getValor().longValue());
        lstEstadoSolicitud.add(EstadoSolicitudEnum.ATENDIDA.getValor().longValue());
        lstEstadoSolicitud.add(EstadoSolicitudEnum.RECHAZADA.getValor().longValue());
        solicitudDto.setEstadosSolicitud(lstEstadoSolicitud);
        //
		
		//llamado al servicio
        try{
        	//echo Nesesario
    		log.debug("Se llama el servicio para obtener las solicitudes concluidas");
        	datosSalidaPaginador=this.solicitudServiceRemote.solicitudesDerechohabientes(solicitudDto);
        	
		
        }catch(DerechohabientesBusinessException e){
        	e.printStackTrace();
        	//RNGD0074 Solicitudes pendientes de completar
			request.setAttribute("exception", "exception.RNGD0074");
        } catch (Exception e) {
        	e.printStackTrace();
        	request.setAttribute("error", e);
		}
        
        datosSalidaPaginador.setsEcho(envio.getsEcho());
		return datosSalidaPaginador;
		
	}

	

}
