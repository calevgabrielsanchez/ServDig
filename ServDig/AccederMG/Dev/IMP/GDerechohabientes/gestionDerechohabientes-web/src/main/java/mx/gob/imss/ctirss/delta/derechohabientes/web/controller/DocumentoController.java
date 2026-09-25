package mx.gob.imss.ctirss.delta.derechohabientes.web.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import mx.gob.imss.ctirss.delta.derechohabientes.service.utility.interfaces.DocumentosServiceRemote;
import mx.gob.imss.ctirss.delta.derechohabientes.service.utility.interfaces.GrupoFamiliarServiceRemote;
import mx.gob.imss.ctirss.delta.derechohabientes.service.utility.interfaces.SolicitudServiceRemote;
import mx.gob.imss.ctirss.delta.derechohabientes.service.utility.interfaces.TramiteDocumentosServiceRemote;
import mx.gob.imss.ctirss.delta.exception.derechohabiente.DerechohabientesBusinessException;
import mx.gob.imss.ctirss.delta.exception.derechohabiente.DerechohabientesWebSserviceException;
import mx.gob.imss.ctirss.delta.exception.individuo.SolicitudException;
import mx.gob.imss.ctirss.delta.framework.base.controller.AbstractController;
import mx.gob.imss.ctirss.delta.framework.exceptions.DocumentoException;
import mx.gob.imss.ctirss.delta.framework.exceptions.SolicitudNoValidaException;
import mx.gob.imss.ctirss.delta.gestion.solicitud.service.interfaces.FirmaDigitalBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.solicitud.service.interfaces.SolicitudBusinessRemote;
import mx.gob.imss.ctirss.delta.model.Usuario;
import mx.gob.imss.ctirss.delta.model.asegurado.AsignacionNSS;
import mx.gob.imss.ctirss.delta.model.derechohabiente.CabezaGrupoFamiliar;
import mx.gob.imss.ctirss.delta.model.derechohabiente.GrupoFamiliar;
import mx.gob.imss.ctirss.delta.model.derechohabientes.PropiedadesDocumento;
import mx.gob.imss.ctirss.delta.model.enums.EstadoInconsistenciaVigenciaEnum;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.FirmaElectronica;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.TipoSolicitudEnum;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.TipoTramiteEnum;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.OrigenSolicitudEnum;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.Solicitud;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.EstadoTramiteEnum;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.Tramite;
import mx.gob.imss.ctirss.delta.model.util.Constants;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping(value="/documentos/*")
public class DocumentoController extends AbstractController{

	@Autowired
	private DocumentosServiceRemote documentosService; 
	@Autowired
	private TramiteDocumentosServiceRemote tramiteDocumentosService;
	@Autowired
	private SolicitudServiceRemote solicitudService;
	@Autowired
	private SolicitudBusinessRemote solicitudBusinessRemote;
	@Autowired
	private FirmaDigitalBusinessRemote firmaDigitalBusinessRemote;
	@Autowired
	private GrupoFamiliarServiceRemote grupoFamiliarServiceRemote;
	
	@RequestMapping(value="/cuestionario")
	public void getCuestionario(Long tramite,Integer tipoTramite){

	}

	@RequestMapping(value="/sav007/{idPersona}")
	public void getDocumentoSav007(@PathVariable("idPersona") Long idPersona,
			HttpServletResponse response,
			HttpSession session){
		AsignacionNSS nss = (AsignacionNSS) session.getAttribute("AsignacionNSS");
		try{
			FirmaElectronica firma = new FirmaElectronica();
			byte[] res  = (byte[])documentosService.getDocumentoSav007(nss, firma, idPersona);
			construirPdf(response , res);
		}catch (Exception e) {
			e.printStackTrace();
		}

	}

	
	@RequestMapping(value="/documentoRegistro", method = RequestMethod.GET)
	public void getDocumentoRegistroDerechohabiente(@RequestParam("idPersona") Long idPersona,
			@RequestParam("idTramite") Long idTramite,@RequestParam("titulo") String titulo,
			HttpServletResponse response,HttpSession session){

		AsignacionNSS nss = (AsignacionNSS) session.getAttribute("AsignacionNSS");
		Solicitud sol = null;

		Usuario usuario =  (Usuario) session.getAttribute(Usuario.SES_NAME);
		try{

			sol = this.obtenerSolicitud(idTramite);
			Tramite tramite = null;
			
			for(Tramite tram :  sol.getTramites()) {
				if(tram.getTramiteId().equals(idTramite)) {
					tramite = tram;
					break;
				}
			}
			

			if(nss==null) {
				nss = solicitudService.getAsignacionByIdSolicitud(sol.getSolicitudId());
			}

			int identificadorReporte = tramite.getTipoTramite().getIdTipoTramite();
			PropiedadesDocumento propiedades = new PropiedadesDocumento();
			propiedades.setIdDerechohabiente(idPersona);
			propiedades.setIdTramite(idTramite);
			propiedades.setTitulo(titulo);

			//La generacion del documento Sav002, se realiza a traves de TramiteDocumentosService
			byte[] res = (byte[]) tramiteDocumentosService.generaDocumentoConSelloDigital(nss, sol, usuario, identificadorReporte, propiedades, null);

			construirPdf(response , res);
		}catch (Exception e) {
			e.printStackTrace();
		}

	}

	@RequestMapping(value="/documentoRegistroDep")
	public void getDocumentoRegistroDerechohabienteDep(@RequestParam("idTramite") Long idTramite,@RequestParam("personas") String personas,
			@RequestParam("tipoTramite") Long tipoTramite,
			@RequestParam("titulo") String titulo,HttpServletResponse response,HttpSession session){

		List<Long> pl = new ArrayList<Long>();
		String[] ps = personas.split(",");

		for(String pss : ps){
			pl.add(new Long(pss));
		}

		Usuario usuario =  (Usuario) session.getAttribute(Usuario.SES_NAME);

		try{//487
			AsignacionNSS nss = (AsignacionNSS) session.getAttribute("AsignacionNSS");
			if(nss == null){
				//Tramite miTramite = solicitudService.getTramiteAutorizar(idTramite);
				Solicitud sol = this.obtenerSolicitud(idTramite);

				nss = solicitudService.getAsignacionByIdSolicitud(sol.getSolicitudId());
			}
			FirmaElectronica firma = new FirmaElectronica();
			byte[] res  = (byte[])documentosService.getDocumentoRegistroDerechohabientesDep(nss, firma,idTramite,pl,tipoTramite,titulo, usuario);
			construirPdf(response , res);
		}catch (Exception e) {
			e.printStackTrace();
		}

	}

	@RequestMapping(value="/comprobanteSolicitud", method = RequestMethod.GET)
	public void getDocumentoSav002(@RequestParam("idSolicitud") Long idSolicitud,
			@RequestParam("titulo") String titulo, HttpServletResponse response,HttpSession session){

		AsignacionNSS nss = (AsignacionNSS) session.getAttribute("AsignacionNSS");

		try{
			byte[] res  = (byte[])documentosService.getDocumentoSav002(nss,idSolicitud,titulo);
			construirPdf(response , res);
		}catch (Exception e) {
			e.printStackTrace();
		}
	}

	@RequestMapping(value="/rechazoSolicitud", method = RequestMethod.GET)
	public void getRechazoSolicitud(@RequestParam("titulo") String titulo, @RequestParam("tipoTramite") Long tipoTramite,@RequestParam("idTramite") Long idTramite, @RequestParam("idSolicitud") Long idSolicitud,HttpServletResponse response,HttpSession session){

		AsignacionNSS nss = (AsignacionNSS) session.getAttribute("AsignacionNSS");

		try{
			if(nss==null){
				nss = solicitudService.getAsignacionByIdSolicitud(idSolicitud);
			}
			byte[] res  = (byte[])documentosService.getRechazoSolicitud(nss,titulo,tipoTramite);
			construirPdf(response , res);
		}catch (Exception e) {
			e.printStackTrace();
		}

	}

	@RequestMapping(value="/comprobanteVD", method = RequestMethod.GET)
	public void getComprobanteVigenciaDerechos(HttpServletResponse response,HttpSession session){

		Usuario usuario =  (Usuario) session.getAttribute(Usuario.SES_NAME);
		AsignacionNSS nss = (AsignacionNSS) session.getAttribute("AsignacionNSS");
		CabezaGrupoFamiliar  cabezaGrupoFamiliar = (CabezaGrupoFamiliar)session.getAttribute(Constants.CABEZA_GRUPO_FAM_SESSION);
		
		try{
			
			Map<String, Object> resultado = null;
			
			
			//Identificadores para el tipo de tramite, solicitud
			Map<String, Integer> identificadoresMap = new HashMap<String, Integer>();
			identificadoresMap.put("solicitud", TipoSolicitudEnum.IMPRESION_DE_DOCUMENTO_DERECHOHABIENTE.getValor());
			identificadoresMap.put("tramite", TipoTramiteEnum.COMPROBANTE_DE_VIGENCIA_DE_DERECHOHAB.getCodigo());
			identificadoresMap.put("origenSolicitud", OrigenSolicitudEnum.VENTANILLA.getId().intValue());

			
			// -----------------------------------------------------------------------------------------
			// Para inconsistentes o estudiantes se obtiene la informaci�n exclusivamente del WS
			// -----------------------------------------------------------------------------------------
			if( nss.getEstadoInconsistencia().intValue() == EstadoInconsistenciaVigenciaEnum.ASEGURADO.getId() 
				|| nss.getEstadoInconsistencia().intValue() == EstadoInconsistenciaVigenciaEnum.BENEFICIARIOS.getId() ||
				cabezaGrupoFamiliar.getEsEstudiante()){
				resultado = this.generaTramiteDocumentoReporteConstanciaVigenciaWS(nss, usuario, identificadoresMap, false);
			}else{
				resultado = this.generaTramiteDocumentoReporteConstanciaVigencia(nss, usuario, identificadoresMap, false);
			}
			
			byte[] res = (byte[])resultado.get("documento");
			
			construirPdfVD(response , res, nss.getNss());
		}catch (DocumentoException e) {
			response.addHeader("Set-Cookie", "fileDownload=true;Path=/");
			response.addHeader("Set-Cookie", "fileDownloadError=true;Path=/");
			e.printStackTrace();
		}

	}
	

	private Map<String, Object> generaTramiteDocumentoReporteConstanciaVigencia(AsignacionNSS asignacionNSS, Usuario usuario,
			Map<String, Integer> identificadoresMap, boolean enviaMail)
			throws DocumentoException {
		
		return this.generaConstancia(asignacionNSS, usuario, identificadoresMap, enviaMail,false);

			
	}
	
	private Map<String, Object> generaTramiteDocumentoReporteConstanciaVigenciaWS(AsignacionNSS asignacionNSS, Usuario usuario, Map<String, Integer> identificadoresMap, boolean enviaMail) throws DocumentoException {

		if(asignacionNSS.getIdAsignacionNSS() == null || asignacionNSS.getIdAsignacionNSS().intValue() == 0){
			try {
				GrupoFamiliar grupoFamiliar =  grupoFamiliarServiceRemote.getInfoAsegurado(asignacionNSS.getNss());
				asignacionNSS = grupoFamiliar.getAsignacionNSS();
			} catch (DerechohabientesWebSserviceException e) {
				throw new DocumentoException(e.getMessage());
			}
		}
		
		return this.generaConstancia(asignacionNSS, usuario, identificadoresMap, enviaMail, true);
			
	}
	
	private Map<String, Object> generaConstancia(AsignacionNSS asignacionNSS, Usuario usuario,
			Map<String, Integer> identificadoresMap, boolean enviaMail, boolean infoWS) throws DocumentoException {
		
		byte[] documentByteArray = null;
		Map<String, Object> resultado = new HashMap<String, Object>();
		String nss = asignacionNSS.getNssStr();
		asignacionNSS.setNss(nss);
		
		try {

			// 1. Se debe crear un tramite y solicitud.
			Integer origenSolicitud = identificadoresMap.get("origenSolicitud");
			if(origenSolicitud==null) {
				origenSolicitud = OrigenSolicitudEnum.INTERNET.getId().intValue();
			}
	
			log.debug("voy a crear la solicitud");
			// asignacionNSS es de tipo AsignacionNSS que extiende de Fisica
			// por lo que puede pasarse como parametro para crear la solicitud
			Solicitud solicitud = tramiteDocumentosService.crearTramiteSolicitud(asignacionNSS,
					origenSolicitud.longValue(), usuario, identificadoresMap);
	
			log.debug("Termino de generar la solicitud");
			// 2. Obtener sello digital
			// Se obtiene el sello digital
			// Se genera cadena original
			asignacionNSS.setNss(nss);
			log.debug("voy a guardar en notaria");
			FirmaElectronica firmaElectronica = tramiteDocumentosService.generaFirmaElectronica(
					asignacionNSS, solicitud, "COMPROBANTE DE VIGENCIA DE DERECHOHABIENTES");
			log.debug("termino de guardar en notaria");
		
			log.debug("Voy a generar el pdf con datos de WS [" + infoWS +"]");
			
			if(infoWS) {
				// 3. Generacion del reporte con informacion del sello digital
				documentByteArray = (byte[]) documentosService.getConstanciaVigenciaWS(asignacionNSS, firmaElectronica, usuario);
			} else {
				// 3. Generacion del reporte con informacion del sello digital
				documentByteArray = (byte[]) documentosService.getConstanciaVigenciaInternetRecortado(asignacionNSS,firmaElectronica, usuario);
			}
			log.debug("Termino de generar el pdf");
			
			log.debug("Voy a giardar en notaria");
			// 4. Se guarda el reporte
			firmaDigitalBusinessRemote.guardarArchivoFirmado(
					firmaElectronica.getReciboNotarial(),
					"reporteVigencia"+nss+".pdf", documentByteArray);
			log.debug("Termino de guardar en notaria");
			
			Long tramiteId = solicitud.getTramites().get(0).getTramiteId();
			
			/*solicitudEntity.actualizarDocumentosTramite(tramiteId, 
					DocumentoPorTipoEnum.COMPROBANTE_VIGENCIA_DERECHOS.getId(), documentByteArray);*/
			
			resultado.put("documento", documentByteArray);
			resultado.put("tramiteId", tramiteId);
			resultado.put("folio", solicitud.getNoFolioSolicitud());
			resultado.put("idSolicitud", solicitud.getSolicitudId().toString());
			resultado.put("secuenciaNotarial",firmaElectronica.getSecuenciaNotaria());
	
	
		} catch (SolicitudNoValidaException e) {
			throw new DocumentoException(e.getMessage());
		} catch (SolicitudException e) {
			throw new DocumentoException(e.getMessage());
		} catch (DerechohabientesBusinessException e) {
			throw new DocumentoException(e.getMessage());
		} catch (Exception e) {
			throw new DocumentoException(e.getMessage());
		}
		return resultado;
	}

	@RequestMapping(value="/reporte4305A", method = RequestMethod.GET)
	public void getDocumento4305A(HttpServletResponse response,HttpSession session){

		AsignacionNSS nss = (AsignacionNSS) session.getAttribute("AsignacionNSS");
		Usuario usuario =  (Usuario) session.getAttribute(Usuario.SES_NAME);

		try{
			PropiedadesDocumento propiedades = new PropiedadesDocumento();
			Solicitud solicitud = null;

			Map<String, Integer> identificadoresMap = new HashMap<String, Integer>();
			identificadoresMap.put("tramite", TipoTramiteEnum.TARJETA_DE_ADSCRIPCION_430A5
					.getCodigo());
			identificadoresMap.put("solicitud", TipoSolicitudEnum.IMPRESION_DE_DOCUMENTO_DERECHOHABIENTE
					.getValor()); 

			int identificadorReporte = TipoTramiteEnum.TARJETA_DE_ADSCRIPCION_430A5.getCodigo();
			byte[] res = (byte[])tramiteDocumentosService.generaDocumentoConSelloDigital(nss, solicitud, usuario, identificadorReporte, propiedades, identificadoresMap);

			construirPdf(response , res);
		}catch (Exception e) {
			response.addHeader("Set-Cookie", "fileDownload=true;Path=/");
			response.addHeader("Set-Cookie", "fileDownloadError=true;Path=/");
			e.printStackTrace();
		}

	}

	@RequestMapping(value="/documentosBaja", method = RequestMethod.GET)
	public void getDocumentosBjaDerechohabiente(@RequestParam("idTramite") Long idTramite,@RequestParam("titulo") String titulo,
			HttpServletResponse response,HttpSession session){
		AsignacionNSS nss = (AsignacionNSS) session.getAttribute("AsignacionNSS");
		Solicitud sol = null;
		Usuario usuario =  (Usuario) session.getAttribute(Usuario.SES_NAME);
		try {
			sol = this.obtenerSolicitud(idTramite);

			if(nss==null) {
				nss = solicitudService.getAsignacionByIdSolicitud(sol.getSolicitudId());
			}


			Tramite tramite = sol.getTramites().get(0);


			int identificadorReporte = tramite.getTipoTramite().getIdTipoTramite();
			PropiedadesDocumento propiedades = new PropiedadesDocumento();
			propiedades.setIdTramite(idTramite);

			//La generacion del documento Sav002, se realiza a traves de TramiteDocumentosService
			byte[] res = (byte[]) tramiteDocumentosService.generaDocumentoConSelloDigital(nss, sol, usuario, identificadorReporte, propiedades, null);

			//			byte[] res  =  (byte[])documentosService.getDocumentoBajaDerechohabientes(nss,idTramite,titulo);
			construirPdf(response , res);
		} catch (DerechohabientesBusinessException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}


	@RequestMapping(value="/documentosSuspension", method = RequestMethod.GET)
	public void getDocumentosSuspension(@RequestParam("idTramite") Long idTramite,@RequestParam("titulo") String titulo,
			HttpServletResponse response,HttpSession session){
		AsignacionNSS nss = (AsignacionNSS) session.getAttribute("AsignacionNSS");
		try {
			byte[] res  =  (byte[])documentosService.getDocumentoBajaDerechohabientes(nss,idTramite,titulo);
			construirPdf(response , res);
		} catch (DerechohabientesBusinessException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@RequestMapping(value = "/documentosAutorizacion", method = RequestMethod.GET)
	public void generaDocumentoSAV017(
			@RequestParam("idPersona") Long idPersona,
			@RequestParam("idTramite") Long idTramite,
			@RequestParam("ind") Long indicador,
			HttpSession session,
			HttpServletResponse response) {

		AsignacionNSS nss = (AsignacionNSS) session.getAttribute("AsignacionNSS");
		Usuario usuario=(Usuario) session.getAttribute(Usuario.SES_NAME);
		Solicitud sol = null;

		try {
			sol = this.obtenerSolicitud(idTramite);
			Tramite tramite = solicitudService.getTramiteAutorizar(idTramite);

			if(nss==null) {
				nss = solicitudService.getAsignacionByIdSolicitud(sol.getSolicitudId());
			}

			int identificadorReporte = tramite.getTipoTramite().getIdTipoTramite(); //AUTORIZACION_SERVICIOS_CIRCUNSCRIPCION_FORANEA
			PropiedadesDocumento propiedades = new PropiedadesDocumento();
			propiedades.setIdDerechohabiente(idPersona);
			propiedades.setIdTramite(idTramite);
			propiedades.setAutorizacion( indicador == 1 ? true : false );
			byte[] res = (byte[]) tramiteDocumentosService.generaDocumentoConSelloDigital(nss, sol, usuario, identificadorReporte, propiedades, null);

			//			byte[] res = (byte[]) documentosService.getDocumentoSav017(idPersona , asignacionNSS, null, indicador == 1 ? true : false, null);
			construirPdf(response , res);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@RequestMapping(value="/documentosProrroga", method = RequestMethod.GET)
	public void getDocumentosBjaDerechohabiente(@RequestParam("idTramite") Long idTramite,@RequestParam("idDerechohabiente") Long idDerechohabiente,
			HttpServletResponse response,HttpSession session){
		AsignacionNSS nss = (AsignacionNSS) session.getAttribute("AsignacionNSS");
		Solicitud sol = null;
		Usuario usuario =  (Usuario) session.getAttribute(Usuario.SES_NAME);
		try {
			sol = this.obtenerSolicitud(idTramite);
			Tramite tramite = sol.getTramites().get(0);
			
			nss = solicitudService.getAsignacionByIdSolicitud(sol.getSolicitudId());

			PropiedadesDocumento propiedades = new PropiedadesDocumento();
			propiedades.setIdDerechohabiente(idDerechohabiente);


			int identificadorReporte = tramite.getTipoTramite().getIdTipoTramite();

			byte[] res = (byte[])tramiteDocumentosService.generaDocumentoConSelloDigital(nss, sol, usuario, identificadorReporte, propiedades, null);

			//			byte[] res  =  (byte[])documentosService.getDocumentosProrroga(nss, null, idTramite, idDerechohabiente);
			construirPdf(response , res);
		} catch (DerechohabientesBusinessException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@RequestMapping(value="/sav011")
	public void getDocumentoSav011(HttpServletResponse response,HttpSession session){
		AsignacionNSS nss = (AsignacionNSS) session.getAttribute("AsignacionNSS");
		Usuario usuario =  (Usuario) session.getAttribute(Usuario.SES_NAME);
		try{
			FirmaElectronica firma = new FirmaElectronica();
			byte[] res  = (byte[])documentosService.getDocumentoSav011(nss, firma, usuario, null);
			construirPdf(response , res);
		}catch (Exception e) {
			e.printStackTrace();
		}

	}



	@RequestMapping(value="/sav011Persona/{idPersona}")
	public void getDocumentoSav011Persona(@PathVariable("idPersona") Long idPersona,HttpServletResponse response,HttpSession session){
		AsignacionNSS nss = (AsignacionNSS) session.getAttribute("AsignacionNSS");
		Usuario usuario =  (Usuario) session.getAttribute(Usuario.SES_NAME);

		try{
			System.out.println("****************** idPersona "+idPersona);			
			FirmaElectronica firma = new FirmaElectronica();
			byte[] res  = (byte[])documentosService.getDocumentoSav011(nss, firma, usuario, idPersona);
			construirPdf(response , res);
		}catch (Exception e) {
			e.printStackTrace();
		}

	}
	@RequestMapping(value="/cartilla")
	public void getDocumentoCartilla(@RequestParam("idPersona") Long idPersona,
			@RequestParam("idTramite") Long idTramite,
			HttpServletResponse response, HttpSession session){

		AsignacionNSS nss = (AsignacionNSS) session.getAttribute("AsignacionNSS");
		Usuario usuario=(Usuario) session.getAttribute(Usuario.SES_NAME);
		Solicitud sol = null;

		try{

			sol = this.obtenerSolicitud(idTramite);
			Tramite tramite = solicitudService.getTramiteAutorizar(idTramite);

			if(nss==null) {
				nss = solicitudService.getAsignacionByIdSolicitud(sol.getSolicitudId());
			}

			int identificadorReporte = tramite.getTipoTramite().getIdTipoTramite();
			PropiedadesDocumento propiedades = new PropiedadesDocumento();
			propiedades.setIdDerechohabiente(idPersona);
			propiedades.setIdTramite(idTramite);

			byte[] res = (byte[]) tramiteDocumentosService.generaDocumentoConSelloDigital(nss, sol, usuario, identificadorReporte, propiedades, null);

			//			FirmaElectronica firma = new FirmaElectronica();
			//			byte[] res  = (byte[])documentosService.getCartillaNacionalSalud(idPersona,nss, firma);
			construirPdf(response , res);
		}catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * Metodo que construye los documentos de cambio de datos del derechohabiente
	 * @param idPersona
	 * @param response
	 * @param session
	 */
	@RequestMapping(value="/cambioDatos")
	public void getDocumentoCambioDatos(@RequestParam("idPersona") Long idPersona, 
			@RequestParam("idTramite") Long idTramite,@RequestParam("titulo") String titulo,
			HttpServletResponse response,HttpSession session){

		AsignacionNSS nss = (AsignacionNSS) session.getAttribute("AsignacionNSS");
		Usuario usuario=(Usuario) session.getAttribute(Usuario.SES_NAME);
		Solicitud sol = null;

		try{
			sol = this.obtenerSolicitud(idTramite);
			Tramite tramite = solicitudService.getTramiteAutorizar(idTramite);

			if(nss==null) {
				nss = solicitudService.getAsignacionByIdSolicitud(sol.getSolicitudId());
			}

			int identificadorReporte = tramite.getTipoTramite().getIdTipoTramite();
			PropiedadesDocumento propiedades = new PropiedadesDocumento();
			propiedades.setIdDerechohabiente(idPersona);
			propiedades.setIdTramite(idTramite);
			propiedades.setTitulo(titulo);

			byte[] res = (byte[]) tramiteDocumentosService.generaDocumentoConSelloDigital(nss, sol, usuario, identificadorReporte, propiedades, null);

			//			FirmaElectronica firma = new FirmaElectronica();
			//			byte[] res  = (byte[])documentosService.getDocumentosCambioDatos(nss, firma, idTramite, titulo, this.usuario);//CORRECCION DE DATO
			construirPdf(response , res);
		}catch (Exception e) {
			e.printStackTrace();
		}

	}

	@RequestMapping(value="/cambioClinicaO")
	public void getDocumentoCambioClinicaOrigen(@RequestParam("idPersona") Long idPersona,
			@RequestParam("idTramite") Long idTramite,
			HttpServletResponse response,HttpSession session){

		AsignacionNSS nss = (AsignacionNSS) session.getAttribute("AsignacionNSS");
		Usuario usuario=(Usuario) session.getAttribute(Usuario.SES_NAME);
		Solicitud sol = null;

		try{
			sol = this.obtenerSolicitud(idTramite);
			Tramite tramite = solicitudService.getTramiteAutorizar(idTramite);

			if(nss==null) {
				nss = solicitudService.getAsignacionByIdSolicitud(sol.getSolicitudId());
			}
			
			int identificadorReporte = tramite.getTipoTramite().getIdTipoTramite();//CAMBIO_CLINICA
			PropiedadesDocumento propiedades = new PropiedadesDocumento();
			propiedades.setIdDerechohabiente(idPersona);
			propiedades.setIdTramite(idTramite);
			propiedades.setIdEstadoTramite( EstadoTramiteEnum.INICIADO.getCodigo().longValue() );
			byte[] res = (byte[]) tramiteDocumentosService.generaDocumentoConSelloDigital(nss, sol, usuario, identificadorReporte, propiedades, null);

			//			FirmaElectronica firma = new FirmaElectronica();
			//			byte[] res  = (byte[])documentosService.getDocumentoSav005(nss, firma, idPersona, EstadoTramiteEnum.INICIADO.getId());
			construirPdf(response , res);
		}catch (Exception e) {
			e.printStackTrace();
		}

	}

	@RequestMapping(value="/cambioClinica")
	public void getDocumentoCambioClinica(@RequestParam("idPersona") Long idPersona,
			@RequestParam("idTramite") Long idTramite,@RequestParam("titulo") String titulo,
			HttpServletResponse response,HttpSession session){

		AsignacionNSS nss = (AsignacionNSS) session.getAttribute("AsignacionNSS");
		Usuario usuario=(Usuario) session.getAttribute(Usuario.SES_NAME);
		Solicitud sol = null;

		try{

			sol = this.obtenerSolicitud(idTramite);
			Tramite tramite = solicitudService.getTramiteAutorizar(idTramite);

			if(nss==null) {
				nss = solicitudService.getAsignacionByIdSolicitud(sol.getSolicitudId());
			}

			int identificadorReporte = tramite.getTipoTramite().getIdTipoTramite();//CAMBIO_CLINICA
			PropiedadesDocumento propiedades = new PropiedadesDocumento();
			propiedades.setIdDerechohabiente(idPersona);
			propiedades.setIdTramite(idTramite);
			propiedades.setTitulo(titulo);
			propiedades.setIdEstadoTramite( EstadoTramiteEnum.CERRADO.getCodigo().longValue() );
			byte[] res = (byte[]) tramiteDocumentosService.generaDocumentoConSelloDigital(nss, sol, usuario, identificadorReporte, propiedades, null);

			//			FirmaElectronica firma = new FirmaElectronica();
			//			byte[] res  = (byte[])documentosService.getDocumentosCambioClinica(nss, firma, idTramite,usuario, titulo);//CAMBIO_CLINICA
			construirPdf(response , res);
		}catch (Exception e) {
			e.printStackTrace();
		}

	}

	@RequestMapping(value="/circunscripcionA")
	public void getDocumentoCircunscripcionA(@RequestParam("idPersona") Long idPersona,
			@RequestParam("idTramite") Long idTramite,@RequestParam("titulo") String titulo,
			HttpServletResponse response,HttpSession session){

		AsignacionNSS nss = (AsignacionNSS) session.getAttribute("AsignacionNSS");
		Usuario usuario=(Usuario) session.getAttribute(Usuario.SES_NAME);
		Solicitud sol = null;

		try{
			sol = this.obtenerSolicitud(idTramite);
			Tramite tramite = solicitudService.getTramiteAutorizar(idTramite);

			if(nss==null) {
				nss = solicitudService.getAsignacionByIdSolicitud(sol.getSolicitudId());
			}

			int identificadorReporte = tramite.getTipoTramite().getIdTipoTramite(); //AUTORIZACION_SERVICIOS_CIRCUNSCRIPCION_FORANEA
			PropiedadesDocumento propiedades = new PropiedadesDocumento();
			propiedades.setIdDerechohabiente(idPersona);
			propiedades.setIdTramite(idTramite);
			propiedades.setTitulo(titulo);
			propiedades.setAutorizacion(true);
			byte[] res = (byte[]) tramiteDocumentosService.generaDocumentoConSelloDigital(nss, sol, usuario, identificadorReporte, propiedades, null);

			//			FirmaElectronica firma = new FirmaElectronica();
			//			byte[] res  = (byte[])documentosService.getDocumentosCircunscripcion(nss, firma, idTramite,true, titulo, this.usuario);  //AUTORIZACION_SERVICIOS_CIRCUNSCRIPCION_FORANEA
			construirPdf(response , res);
		}catch (Exception e) {
			e.printStackTrace();
		}
	}

	@RequestMapping(value="/circunscripcionS")
	public void getDocumentoCircunscripcionS(@RequestParam("idTramite") Long idTramite,@RequestParam("idPersona") Long idPersona,
			@RequestParam("titulo") String titulo,
			HttpServletResponse response,HttpSession session){

		AsignacionNSS nss = (AsignacionNSS) session.getAttribute("AsignacionNSS");
		Usuario usuario=(Usuario) session.getAttribute(Usuario.SES_NAME);
		Solicitud sol = null;
		
		try{
			sol = this.obtenerSolicitud(idTramite);
			Tramite tramite = solicitudService.getTramiteAutorizar(idTramite);

			if(nss==null) {
				nss = solicitudService.getAsignacionByIdSolicitud(sol.getSolicitudId());
			}

			int identificadorReporte = tramite.getTipoTramite().getIdTipoTramite(); //TipoTramiteEnum.SUSPENSION_SERVICIOS_CIRCUNSCRIPCION_FORANEA.getCodigo();
			PropiedadesDocumento propiedades = new PropiedadesDocumento();
			propiedades.setIdTramite(idTramite);
			propiedades.setIdDerechohabiente(idPersona);
			propiedades.setAutorizacion(false);

			//La generacion del documento Sav002, se realiza a traves de TramiteDocumentosService
			byte[] res = (byte[]) tramiteDocumentosService.generaDocumentoConSelloDigital(nss, sol, usuario, identificadorReporte, propiedades, null);

			//			SUSPENSION_SERVICIOS_CIRCUNSCRIPCION_FORANEA
			//			byte[] res  = (byte[])documentosService.getDocumentosSuspencionCircunscripcion(nss, idTramite, idPersona, titulo, usuario);  
			construirPdf(response , res);
		}catch (Exception e) {
			e.printStackTrace();
		}
	}

	@RequestMapping(value="/cuestionarioCD")
	public void getDocumentoCuestioanrio(@RequestParam("idTramite") Long idTramite,
			HttpServletResponse response,HttpSession session){
		AsignacionNSS asignacionNSS = (AsignacionNSS) session.getAttribute("AsignacionNSS");

		try{
			byte[] res  = (byte[])documentosService.getDocumentosCuestionario(asignacionNSS, idTramite);
			construirPdf(response , res);
		}catch (Exception e) {
			e.printStackTrace();
		}

	}

	@RequestMapping(value="/cambioConsultorio")
	public void getDocumentoCambioConsultorio(@RequestParam("idPersona") Long idPersona,
			@RequestParam("idTramite") Long idTramite,@RequestParam("titulo") String titulo,
			HttpServletResponse response,HttpSession session){

		AsignacionNSS nss = (AsignacionNSS) session.getAttribute("AsignacionNSS");
		Usuario usuario=(Usuario) session.getAttribute(Usuario.SES_NAME);
		Solicitud sol = null;

		try{

			sol = this.obtenerSolicitud(idTramite);
			Tramite tramite = solicitudService.getTramiteAutorizar(idTramite);

			if(nss==null) {
				nss = solicitudService.getAsignacionByIdSolicitud(sol.getSolicitudId());
			}

			int identificadorReporte = tramite.getTipoTramite().getIdTipoTramite();//TipoTramiteEnum.CAMBIO_CONSULTORIO_TURNO.getCodigo();
			PropiedadesDocumento propiedades = new PropiedadesDocumento();
			propiedades.setIdDerechohabiente(idPersona);
			propiedades.setIdTramite(idTramite);
			propiedades.setTitulo(titulo);

			//La generacion del documento Sav002, se realiza a traves de TramiteDocumentosService
			byte[] res = (byte[]) tramiteDocumentosService.generaDocumentoConSelloDigital(nss, sol, usuario, identificadorReporte, propiedades, null);

			//			FirmaElectronica firma = new FirmaElectronica();
			//			byte[] res  = (byte[])documentosService.getDocumentosCambioConsultorio(nss, firma, idTramite,usuario, titulo);
			construirPdf(response , res);
		}catch (Exception e) {
			e.printStackTrace();
		}

	}

	private void construirPdf(HttpServletResponse response , byte[] res){
		
		
		response.addHeader("Set-Cookie", "fileDownload=true;Path=/");
		try {
			
			response.addHeader("Accept-Ranges","bytes");
			response.addHeader("Cache-Control","public");
			response.addHeader("Cache-Control","must-revalidate");
			response.addHeader("Pragma","public");
			response.setContentType("application/pdf");
			response.addHeader("expires","0");
			response.addHeader("Content-disposition", "attachment;filename=\"reporte.pdf\""); 
			response.setContentLength(res.length);
			response.getOutputStream().write(res);
			response.flushBuffer();
			response.getOutputStream().close();
			
		} catch (Exception e) {
			response.addHeader("Set-Cookie", "fileDownloadError=true;Path=/");
			e.printStackTrace();
		}

	}	

	private void construirPdfVD(HttpServletResponse response , byte[] res, String nss){
		
		
		response.addHeader("Set-Cookie", "fileDownload=true;Path=/");
		try {
			
			response.addHeader("Accept-Ranges","bytes");
			response.addHeader("Cache-Control","public");
			response.addHeader("Cache-Control","must-revalidate");
			response.addHeader("Pragma","public");
			response.setContentType("application/pdf");
			response.addHeader("expires","0");
			response.addHeader("Content-disposition", "attachment;filename=\"comprobanteVigenciaDerechos" + nss + ".pdf\""); 
			response.setContentLength(res.length);
			response.getOutputStream().write(res);
			response.flushBuffer();
			response.getOutputStream().close();
			
		} catch (Exception e) {
			response.addHeader("Set-Cookie", "fileDownloadError=true;Path=/");
			e.printStackTrace();
		}

	}	


	private Solicitud obtenerSolicitud(Long idTramite) {

		Solicitud encontrada = null;
		try {
			encontrada = solicitudBusinessRemote.consultarPorIdTramite(idTramite);
		}catch(Exception e) {
			log.error("No se pudo consutar la solicitud", e);
		}
		return encontrada;
	}


}
