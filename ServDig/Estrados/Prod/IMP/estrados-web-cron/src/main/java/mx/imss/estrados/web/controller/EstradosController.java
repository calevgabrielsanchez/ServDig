package mx.imss.estrados.web.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import mx.imss.estrados.commons.Constantes;
import mx.imss.estrados.commons.FechasPublicacionDTO;
import mx.imss.estrados.dto.AreaRespNotifDTO;
import mx.imss.estrados.dto.DocumentosAdjuntosDTO;
import mx.imss.estrados.dto.NotificacionesDTO;
import mx.imss.estrados.dto.ProcesoDTO;
import mx.imss.estrados.dto.SsoVwUsuarioDTO;
import mx.imss.estrados.dto.StatusDTO;
import mx.imss.estrados.dto.TipoAdjuntoDTO;
import mx.imss.estrados.dto.TipodocumentoDTO;
import mx.imss.estrados.paginado.dto.FiltroColumna;
import mx.imss.estrados.paginado.dto.PaginadoRequest;
import mx.imss.estrados.paginado.dto.PaginadoResponse;
import mx.imss.estrados.service.interfaces.ConsultaExternaServiceRemote;
import mx.imss.estrados.service.interfaces.ConsultaInternaServiceRemote;
import mx.imss.estrados.service.interfaces.RegistroNotificacionServiceRemote;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import vo.InfoPatronSalida;

@Controller
@RequestMapping(value="/estrados")
public class EstradosController {
	
	/**
	 * Logger
	 */
	private final static Logger logger = Logger.getLogger(EstradosController.class);

	@Autowired
	RegistroNotificacionServiceRemote registroNotificacionServiceB;
	
	@Autowired
	ConsultaExternaServiceRemote consultaExternaServiceRemote;
	
	@Autowired
	ConsultaInternaServiceRemote consultaInternaServiceRemote;
	
	
	@RequestMapping(value="/nuevoRegistro")
	public String nuevaDenuncia(HttpServletResponse response, HttpServletRequest request, HttpSession ses) {
		System.out.println("Nuevo registrooo");
		return "registrarNotificacionEstradosPrincipal";
	}

	/**
	 * Metodo que define el tipo de 
	 * 
	 * **/
	@RequestMapping(value="/consultaAreaResponsableNotificacion")
	public @ResponseBody List<AreaRespNotifDTO> consultaAreaResponsableNotificacion(HttpServletResponse response, HttpServletRequest request, HttpSession ses) {
		List<AreaRespNotifDTO> lista=new ArrayList<AreaRespNotifDTO>();
		return registroNotificacionServiceB.recuperaAreasResponsables("AEMR800123HDFCRF06");
//		return registroNotificacionServiceB.recuperaAreasResponsables("BAVC750915HDFRZS13");
	}
	
	@RequestMapping(value="/consultHeader")
	public @ResponseBody SsoVwUsuarioDTO consultHeader(HttpServletResponse response, HttpServletRequest request, HttpSession ses) {
		SsoVwUsuarioDTO user=registroNotificacionServiceB.recuperaHeader("AEMR800123HDFCRF06");
//		SsoVwUsuarioDTO user=registroNotificacionServiceB.recuperaHeader("BAVC750915HDFRZS13");
		request.getSession().setAttribute(Constantes.USER_LOGIN, user);
		

		
		return user;		
	}
	
	
	@RequestMapping(value="/recuperaNotificacion")
	public @ResponseBody NotificacionesDTO recuperaNotificacion(@RequestBody NotificacionesDTO notificacion,HttpServletResponse response, HttpServletRequest request, HttpSession ses) {
		NotificacionesDTO obj=null;
		if(notificacion.getCveNotificaciones()==0){
			obj=new NotificacionesDTO();
		}
		return obj;		
	}
	
	@RequestMapping(value="/validaRegistroPatronal")
	public @ResponseBody InfoPatronSalida validaRegistroPatronal(@RequestBody NotificacionesDTO notificacion,HttpServletResponse response, HttpServletRequest request, HttpSession ses) {
		InfoPatronSalida vo=null;
		vo=registroNotificacionServiceB.validaRegistroPatronal(notificacion.getRegistroPatronal());
		return vo;		
	}
	
	@RequestMapping(value="/recuperaTiposDocumento")
	public @ResponseBody List<TipodocumentoDTO> recuperaTiposDocumento(@RequestBody ProcesoDTO proceso,HttpServletResponse response, HttpServletRequest request, HttpSession ses) {	
		
		String atributos=proceso.getDesProceso();
		
		String valores[]=atributos.split("-");
		List<TipodocumentoDTO>  lista=registroNotificacionServiceB.recuperaTiposDocumento(Integer.parseInt(valores[1]));
		return lista;		
	}
	
	@RequestMapping(value="/guardoParcialNotificacion")
	public @ResponseBody NotificacionesDTO guardoParcialNotificacion(@RequestBody NotificacionesDTO notificacion,HttpServletResponse response, HttpServletRequest request, HttpSession ses) {	
		
		SsoVwUsuarioDTO usuario=(SsoVwUsuarioDTO) (request.getSession().getAttribute(Constantes.USER_LOGIN));
		notificacion.setSsoVwUsuarioDTO(usuario);		
		notificacion=registroNotificacionServiceB.guardaParcialNotificacion(notificacion);
		return notificacion;		
	}
	
	@RequestMapping(value="/enviaArchivo", method=RequestMethod.POST)
	public @ResponseBody String enviaArchivo(HttpServletResponse response, 
            HttpServletRequest request, HttpSession ses) {	
	
	     String cveNotificacion = request.getParameter("cveNotificaAcuerdoFile");
	     String numeroOficio = request.getParameter("desNumOficioFile");
	    // String tipoDocumento = request.getParameter("tipoDocumentoFile");
	     String tipoAdjunto = request.getParameter("tipoDocumentoAdjunto");
	     System.out.println(cveNotificacion);
	     System.out.println(numeroOficio);
	     //System.out.println(tipoDocumento);
	     SsoVwUsuarioDTO usuario=(SsoVwUsuarioDTO) (request.getSession().getAttribute(Constantes.USER_LOGIN));
	     DocumentosAdjuntosDTO adjunto=new DocumentosAdjuntosDTO();
	     try {
	 		MultipartHttpServletRequest multipartRequest =(MultipartHttpServletRequest) request;
			MultipartFile adjuntoArchivo = multipartRequest.getFile("file");
			
	    	TipoAdjuntoDTO typeAdjunto=new TipoAdjuntoDTO();
	    	typeAdjunto.setCveTipoAdjunto(Integer.valueOf(tipoAdjunto));
	    	NotificacionesDTO not=new NotificacionesDTO();
	    	not.setCveNotificaciones(Long.parseLong(cveNotificacion));
	    	
	    	adjunto.setArchivo(adjuntoArchivo.getBytes());
	    	adjunto.setDesNombreArchivo(adjuntoArchivo.getOriginalFilename());
	    	adjunto.setDesNumOficio(numeroOficio);	    	
	    	adjunto.setNotificacionesDTO(not);
	    	adjunto.setTipoAdjuntoDTO(typeAdjunto);
	    	System.out.println("usuario "+usuario);
	    	adjunto.setDtSsoVwUsuarioDTO(usuario);
	    	adjunto=registroNotificacionServiceB.guardarArchivo(adjunto);
			System.out.println(adjuntoArchivo.getOriginalFilename());
	    	
	     }catch(Exception e){
	    	 e.printStackTrace();	    	 
	     }
	     
	     if(adjunto.getCveDoctoAdjunto()!=0){
	    	 return String.valueOf(adjunto.getCveDoctoAdjunto());	 
	     }else{
	    	 return String.valueOf("0");
	     }
				
	}	
	
//	@RequestMapping(value="/recuperaDocumentosOtros")
//	public @ResponseBody List<DocumentosAdjuntosDTO> recuperaDocumentosOtros(@RequestBody NotificacionesDTO notificacion,HttpServletResponse response, HttpServletRequest request, HttpSession ses) {	
//		System.out.println("Clave Notificacion "+notificacion.getCveNotificaciones());
//		return registroNotificacionServiceB.recuperaListaDocOtrosAdjuntos(notificacion);
//	}
	
	@RequestMapping(value="/recuperaNombre")
	public @ResponseBody String recuperaNombre(@RequestBody DocumentosAdjuntosDTO documentoAdjunto,HttpServletResponse response, HttpServletRequest request, HttpSession ses) {	
		String nombre="";
		StringBuilder nom=new StringBuilder();
		SsoVwUsuarioDTO use=(SsoVwUsuarioDTO) request.getSession().getAttribute(Constantes.USER_LOGIN);
				nom.append(use.getCveDelegacion());//Delegacion
		nom.append(use.getCveSubdelegacion());//Subdelegacion
		String areaResponsable=documentoAdjunto.getDesNombreArchivo().split("-")[1];
		int valor=Integer.parseInt(areaResponsable);
		switch(valor){
		case 1://Afiliacion
			nom.append("AFI");
			break;
		case 2://Clasificacion
			nom.append("CLA");
			break;
		case 3://Cobranza
			nom.append("COB");
			break;
		case 4://Correccion y dictamen
			nom.append("CYD");
			break;
		case 5://Fiscalizacion
			nom.append("FIS");
			break;
		}
		
		if(documentoAdjunto.getTipoAdjuntoDTO().getCveTipoAdjunto().intValue()==1){			
			nom.append("ACUERDO");
			nom.append("");
			//nom.append(".pdf");
		}else{
			nom.append("DOCUMENTO");
			nom.append("");
			//nom.append(".pdf");
		}
		
		
		nombre=nom.toString();
		return nombre;
	}
	
	
	@RequestMapping(value="/eliminarAdjuntoOtro")
	public @ResponseBody String eliminarAdjuntoOtro(@RequestBody DocumentosAdjuntosDTO documentoAdjunto,HttpServletResponse response, HttpServletRequest request, HttpSession ses) {
		registroNotificacionServiceB.eliminarArchivAdjunto(documentoAdjunto);
		return "Documento eliminado exitosamente";
	}
	
	@RequestMapping(value="/recuperaFechaServidor")
	public @ResponseBody String recuperaFechaServidor(HttpServletResponse response, HttpServletRequest request, HttpSession ses) {	
		String fecha="";
		String fechaLim="";
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DAY_OF_MONTH, 1);
		Date fechaCalendar = cal.getTime();
		
		SimpleDateFormat formatoDeFecha = new SimpleDateFormat(Constantes.MASCARA_FECHA);
		fecha=new String(formatoDeFecha.format(fechaCalendar));		
		cal.add(Calendar.MONTH, 1);
		fechaLim=formatoDeFecha.format(cal.getTime());		
		fecha+=","+fechaLim;
		
		
//		RespuestaFirmadoSimple res=registroNotificacionServiceB.getSelloDigital("||campo|valor||", null, null);
//		System.out.println(res.getSello());
		return fecha;
	}
	
	@RequestMapping(value="/calculaFechasPublicacion")
	public @ResponseBody FechasPublicacionDTO calculaFechasPublicacion(@RequestBody FechasPublicacionDTO fechasPublicacion,HttpServletResponse response, HttpServletRequest request, HttpSession ses) {	
		SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
		
		try {
			 System.out.println("Fecha "+fechasPublicacion.getFechaPublicacion());
		
		
		Date fechaInicioPublicacion=registroNotificacionServiceB.agregaDias(formatter.parse(fechasPublicacion.getFechaPublicacion()),1);
		Date fechaFinPublicacion=registroNotificacionServiceB.agregaDias(formatter.parse(fechasPublicacion.getFechaPublicacion()),10);
		Date fechaRetiroPublicacion=registroNotificacionServiceB.agregaDias(formatter.parse(fechasPublicacion.getFechaPublicacion()),11);
		
		fechasPublicacion.setFechaFinPublicacion(formatter.format(fechaFinPublicacion));
		fechasPublicacion.setFechaInicioPublicacion(formatter.format(fechaInicioPublicacion));
		fechasPublicacion.setFechaRetiroPublicacion(formatter.format(fechaRetiroPublicacion));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return fechasPublicacion;
	}
	
	/**
	 * Metodo para el redireccionamiento a la consulta interna
	 * 
	 * @return String
	 */
	@RequestMapping(value = "/consultaExterna")
	public String consultaExterna() {
		return "consultaExterna";
	}
	
	/**
	 * Metodo para obtener el listado paginado de las notificaciones de la consulta externa
	 */
	@RequestMapping(value = "/consultaExternaPaginada")
	public @ResponseBody PaginadoResponse consultaExternaPaginada(
        @RequestParam(value = "sEcho") int sEcho,
        @RequestParam(value = "iColumns") int iColumns,
        @RequestParam(value = "sColumns") String sColumns,
        @RequestParam(value = "iDisplayStart") int iDisplayStart,
        @RequestParam(value = "iDisplayLength") int iDisplayLength,
        @RequestParam(value = "mDataProp_0") String mDataProp_0,
        @RequestParam(value = "mDataProp_1") String mDataProp_1,
        @RequestParam(value = "mDataProp_2") String mDataProp_2,
        @RequestParam(value = "sSearch") String sSearch,
        @RequestParam(value = "bRegex") boolean bRegex,
        @RequestParam(value = "sSearch_0") String sSearch_0,
        @RequestParam(value = "bRegex_0") boolean bRegex_0,
        @RequestParam(value = "bSearchable_0") boolean bSearchable_0,
        @RequestParam(value = "sSearch_1") String sSearch_1,
        @RequestParam(value = "bRegex_1") boolean bRegex_1,
        @RequestParam(value = "bSearchable_1") boolean bSearchable_1,
        @RequestParam(value = "sSearch_2") String sSearch_2,
        @RequestParam(value = "bRegex_2") boolean bRegex_2,
        @RequestParam(value = "bSearchable_2") boolean bSearchable_2,
        @RequestParam(value = "iSortCol_0") int iSortCol_0,
        @RequestParam(value = "sSortDir_0") String sSortDir_0,
        @RequestParam(value = "iSortingCols") int iSortingCols,
        @RequestParam(value = "bSortable_0") boolean bSortable_0,
        @RequestParam(value = "bSortable_1") boolean bSortable_1,
        @RequestParam(value = "bSortable_2") boolean bSortable_2,
        HttpServletResponse response) {
		
		PaginadoRequest paginadoRequest = new PaginadoRequest();
		
		paginadoRequest.setEcho(sEcho);
		paginadoRequest.setSearch(sSearch);
		paginadoRequest.setDisplayStart(iDisplayStart);
		paginadoRequest.setDisplayLength(iDisplayLength);
		paginadoRequest.setFiltroColumna(new FiltroColumna(iSortCol_0, sSortDir_0));
		
		return consultaExternaServiceRemote.consultaExternaPaginada(paginadoRequest);
	}
	
	/**
	 * Metodo para el redireccionamiento a la consulta interna
	 * 
	 * @return String
	 */
	@RequestMapping(value = "/consultaInterna")
	public String consultaInterna() {
		return "consultaInterna";
	}
	
	/**
	 * Metodo para obtener el listado paginado de las notificaciones de la consulta interna
	 */
	@RequestMapping(value = "/consultaInternaPaginada")
	public @ResponseBody PaginadoResponse consultaInternaPaginada(
        @RequestParam(value = "sEcho") int sEcho,
        @RequestParam(value = "iColumns") int iColumns,
        @RequestParam(value = "sColumns") String sColumns,
        @RequestParam(value = "iDisplayStart") int iDisplayStart,
        @RequestParam(value = "iDisplayLength") int iDisplayLength,
        @RequestParam(value = "mDataProp_0") String mDataProp_0,
        @RequestParam(value = "mDataProp_1") String mDataProp_1,
        @RequestParam(value = "mDataProp_2") String mDataProp_2,
        @RequestParam(value = "sSearch") String sSearch,
        @RequestParam(value = "bRegex") boolean bRegex,
        @RequestParam(value = "sSearch_0") String sSearch_0,
        @RequestParam(value = "bRegex_0") boolean bRegex_0,
        @RequestParam(value = "bSearchable_0") boolean bSearchable_0,
        @RequestParam(value = "sSearch_1") String sSearch_1,
        @RequestParam(value = "bRegex_1") boolean bRegex_1,
        @RequestParam(value = "bSearchable_1") boolean bSearchable_1,
        @RequestParam(value = "sSearch_2") String sSearch_2,
        @RequestParam(value = "bRegex_2") boolean bRegex_2,
        @RequestParam(value = "bSearchable_2") boolean bSearchable_2,
        @RequestParam(value = "iSortCol_0") int iSortCol_0,
        @RequestParam(value = "sSortDir_0") String sSortDir_0,
        @RequestParam(value = "iSortingCols") int iSortingCols,
        @RequestParam(value = "bSortable_0") boolean bSortable_0,
        @RequestParam(value = "bSortable_1") boolean bSortable_1,
        @RequestParam(value = "bSortable_2") boolean bSortable_2,
        @RequestParam(value = "sSearch_3") String sSearch_3,
        @RequestParam(value = "sSearch_4") String sSearch_4,
        @RequestParam(value = "sSearch_5") String sSearch_5,
        HttpServletResponse response, HttpServletRequest request, HttpSession ses) {
		
		PaginadoRequest paginadoRequest = new PaginadoRequest();
		
		paginadoRequest.setEcho(sEcho);
		paginadoRequest.setSearch(sSearch);
		paginadoRequest.setDisplayStart(iDisplayStart);
		paginadoRequest.setDisplayLength(iDisplayLength);
		paginadoRequest.setFiltroColumna(new FiltroColumna(iSortCol_0, sSortDir_0));
		if (!sSearch_2.isEmpty()) {
			paginadoRequest.setSearchColumnaDocumento(sSearch_2);
		}
		if (!sSearch_3.isEmpty()) {
			paginadoRequest.setSearchColumnaDocumento(sSearch_3);
		}
		if (!sSearch_4.isEmpty()) {
			paginadoRequest.setSearchColumnaStatus(sSearch_4);
		}
		if (!sSearch_5.isEmpty()) {
			paginadoRequest.setSearchColumnaStatus(sSearch_5);
		}
		paginadoRequest.setFiltroUsuarioSession((SsoVwUsuarioDTO) request.getSession().getAttribute(Constantes.USER_LOGIN));
		
		return consultaInternaServiceRemote.consultaInternaPaginada(paginadoRequest);
	}

	/**
	 * Metodo para obtener el catalogo de Tipo de Documentos de una Notificacion
	 * 
	 * @param response
	 * @param request
	 * @param ses
	 * @return JSON List<TipodocumentoDTO>
	 */
	@RequestMapping(value = "/obtenerFiltroTipoDocumento")
	public @ResponseBody List<TipodocumentoDTO> obtenerFiltroTipoDocumento(HttpServletResponse response, HttpServletRequest request, HttpSession ses) {
		List<TipodocumentoDTO> listTipodocumentoDTOs = new ArrayList<TipodocumentoDTO>();
		SsoVwUsuarioDTO ssoVwUsuarioDTO = (SsoVwUsuarioDTO) request.getSession().getAttribute(Constantes.USER_LOGIN);
		listTipodocumentoDTOs = consultaInternaServiceRemote.obtenerFiltroTipoDocumento(ssoVwUsuarioDTO);
		return listTipodocumentoDTOs;
	}
	
	/**
	 * Metodo para obtener el catalogo de Status de una Notificacion
	 * 
	 * @param response
	 * @param request
	 * @param ses
	 * @return JSON List<StatusDTO>
	 */
	@RequestMapping(value = "/obtenerFiltroStatus")
	public @ResponseBody List<StatusDTO> obtenerFiltroStatus(HttpServletResponse response, HttpServletRequest request, HttpSession ses) {
		List<StatusDTO> liststStatusDTOs = new ArrayList<StatusDTO>();
		SsoVwUsuarioDTO ssoVwUsuarioDTO = (SsoVwUsuarioDTO) request.getSession().getAttribute(Constantes.USER_LOGIN);
		liststStatusDTOs = consultaInternaServiceRemote.obtenerFiltroStatus(ssoVwUsuarioDTO);
		return liststStatusDTOs;
	}
	
	/**
	 * Metodo que busca el documento adjunto para ser utilizado y desplegado en el visor de archivos pdf´s, 
	 * el objeto DocumentosAdjuntosDTO que es obtenido en la busqueda se sube a sesion para ser usado en el 
	 * servlet llamado MostrarArchivoServlet
	 * 
	 * @param jsonDocumentosAdjuntosDTO
	 * @param response
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/obtenerDocumentoAdjunto", method = RequestMethod.POST)
	public @ResponseBody String obtenerDocumentoAdjunto(@RequestBody DocumentosAdjuntosDTO jsonDocumentosAdjuntosDTO, HttpServletResponse response, HttpServletRequest request) {
		DocumentosAdjuntosDTO documentosAdjuntosDTO;
		documentosAdjuntosDTO = consultaInternaServiceRemote.obtenerDocumentoAdjunto(jsonDocumentosAdjuntosDTO);
		request.getSession().setAttribute("documentosAdjuntosDTO", documentosAdjuntosDTO);
		return "";
	}
	
	/**
	 * Metodo que elimina una notificación
	 * @param cveNotificaciones
	 * @param response
	 * @param request
	 * @param ses
	 * @return
	 */
	@RequestMapping(value="/eliminarNotificacion")
	public @ResponseBody String eliminarNotificacion(@RequestBody long cveNotificaciones, HttpServletResponse response, HttpServletRequest request, HttpSession ses) {
		SsoVwUsuarioDTO ssoVwUsuarioDTO = (SsoVwUsuarioDTO) request.getSession().getAttribute(Constantes.USER_LOGIN);
		registroNotificacionServiceB.eliminarNotificacion(cveNotificaciones, ssoVwUsuarioDTO);
		return "Notificación eliminada exitosamente";
	}
	
//	@RequestMapping(value="/recuperaInhabiles")
//	public @ResponseBody List<List> recuperaInhabiles(HttpServletResponse response, HttpServletRequest request, HttpSession ses) {
//		
////		List<List> listaListas=new ArrayList<List>();
////		List<Integer> aa=new ArrayList<Integer>();
////		aa.add(2);
////		aa.add(3);
////		
////		List<Integer> bb=new ArrayList<Integer>();
////		bb.add(2);
////		bb.add(3);
////		listaListas.add(aa);
////		listaListas.add(bb);
////		System.out.println("Valores");
////		registroNotificacionServiceB.metodo();
//		return listaListas;
//	}
//	
	 
}
