package mx.gob.imss.ctirss.correccion.web.controller.correccion;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.servlet.http.HttpServletRequest;

import mx.gob.imss.ctirss.correccion.base.paginador.model.DatosEntradaPaginador;
import mx.gob.imss.ctirss.correccion.base.paginador.model.DatosSalidaPaginador;
import mx.gob.imss.ctirss.correccion.bean.DataTableSolCorreccion;
import mx.gob.imss.ctirss.correccion.bean.PresentacionCorreccionVO;
import mx.gob.imss.ctirss.correccion.catalogos.model.CrcTramiteMensajes;
import mx.gob.imss.ctirss.correccion.catalogos.model.CrtTramitePresentado;
import mx.gob.imss.ctirss.correccion.commons.paginador.PagosPatronWrapperDataTable;
import mx.gob.imss.ctirss.correccion.commons.vo.pagos.PagosPatrones;
import mx.gob.imss.ctirss.correccion.correccion.service.interfaces.SolicitudService;

import mx.gob.imss.ctirss.correccion.firma.service.interfaces.Archivo;
import mx.gob.imss.ctirss.correccion.firma.service.interfaces.FirmaElectronicaService;
import mx.gob.imss.ctirss.correccion.framework.base.controller.AbstractController;
import mx.gob.imss.ctirss.correccion.framework.base.model.AbstractDocumentoElectronicoModel;
import mx.gob.imss.ctirss.correccion.framework.base.model.AbstractModel;
import mx.gob.imss.ctirss.correccion.framework.utils.ConstantesBusiness;
import mx.gob.imss.ctirss.correccion.model.CrtAnexosolcorrpat;
import mx.gob.imss.ctirss.correccion.model.CrtPresentacorr;
import mx.gob.imss.ctirss.correccion.model.CrtSolicitudcorr;
import mx.gob.imss.ctirss.correccion.model.ErrorValidation;
import mx.gob.imss.ctirss.correccion.model.PresentacionCorreccionModel;
import mx.gob.imss.ctirss.correccion.model.SatPatron;
import mx.gob.imss.ctirss.correccion.presentacion.AbstractPresentacionQuerys;
import mx.gob.imss.ctirss.correccion.presentacion.service.interfaces.PresentacionCorreccionServiceController;

import mx.gob.imss.ctirss.correccion.service.interfaces.ICatalogoService;
import mx.gob.imss.ctirss.correccion.service.interfaces.IPatronesService;
import mx.gob.imss.ctirss.correccion.session.ConstantesSession;
import mx.gob.imss.ctirss.correccion.session.TipoCertificado;
import mx.gob.imss.ctirss.correccion.session.UserSession;
import mx.gob.imss.ctirss.correccion.web.utils.GeneraReporteUtil;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 
 *
 * Controlador que nos permitira ejecutar todas las interacciones / peticiones WEB utilizando SPring MVC.
 *
 */

@Controller
@RequestMapping(value="/correccion/presentacion")
public class PresentacionCorreccionController extends AbstractController{
	
	Logger log = Logger.getLogger(PresentacionCorreccionController.class);
	
	@Autowired
	private PresentacionCorreccionServiceController businessController;
	
	@Autowired
	private IPatronesService<?> patronesService;
	
	@Autowired
	private SolicitudService<?> solicitudService;
	

	
	/**
	 * Servicio genérico de consulta
	 */
	@Autowired
	private ICatalogoService<AbstractModel> consultaGenerica;
	
	
	@Autowired
	private FirmaElectronicaService firmaElectronicaService;
	
	/**Pagina de inicio de la presentacion de la corrreccion */
	private static final String PAGINA_INICIO_PRESENTACION_CORRECCION = "correccion/presentacionCorreccionMain";
	private static final String PAGINA_ANEXO_SOL_CORR_RESUMEN = "correccion/anexoSolCorrResumen";
	private static final String PRESENTACION_CORRECCION_PDF = "PresentacionCorrecion.jasper";

	/**
	 * Modelo inicio
	 * 
	 * @param model - Bean que se manejara para los datos de la pantalla
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET)
	public String getCreateForm(Model model, HttpServletRequest request){
		PresentacionCorreccionModel modelo = new PresentacionCorreccionModel();
		modelo.setFechaFirma(ConstantesBusiness.dateToStringFormat(new Date(), ConstantesBusiness.DD_MM_YYYY));
		UserSession user = (UserSession) request.getSession().getAttribute(ConstantesSession.USR_SESSION);
		if(user != null){
			modelo.setDelegacion(user.getNombreDelegacion());
			modelo.setSubDelegacion(user.getNombreSubDelegacion());
		}
		model.addAttribute(modelo);
		
		return determinaURL(request, PAGINA_INICIO_PRESENTACION_CORRECCION, "correccion/presentacionCorreccionMainPatron");
	}
	
	@RequestMapping(value="/anexoSolCorrResumen")
	public String handleAnexoSolCorrResumen(@RequestParam("folioSolCorr") String folio, HttpServletRequest request){
		return PAGINA_ANEXO_SOL_CORR_RESUMEN;
	}
	
	/**
	 * 
	 * @param correccion
	 * @param resquest
	 * @return
	 */
	@RequestMapping(value="/buscarPorFolio" , method=RequestMethod.POST)
	public @ResponseBody PresentacionCorreccionModel 
								buscarSolicitudesCorreccionPorFolio(@RequestBody PresentacionCorreccionModel correccion, HttpServletRequest request){
		log.debug("+++++++++++++++++ POR FOLIO");

		PresentacionCorreccionModel modelo = new PresentacionCorreccionModel();
		try{
			Integer solCorrId = new Integer(correccion.getFolioSoicitudCorreccionFiltro());
			List<ErrorValidation> validaciones = businessController.validarSolicitudCorreccion(solCorrId);
			if(validaciones.size() <= 0){
				modelo = businessController.buscarFolioDeCorreccion(solCorrId);
				
				String SQL = AbstractPresentacionQuerys.PAGO_TOTAL_COPS_RPS_INSCRITOS.replace("{1}", solCorrId.toString());
				List<?> rpsInscritos = consultaGenerica.consultaSQL(SQL);
				
				modelo.setHtmlCopPagadas(getHtmlCopPagadas(rpsInscritos, request).replaceAll("null", "0.00"));
				modelo.setCveSolicitudCorreccion(solCorrId);
			}else{
				StringBuilder builder = new StringBuilder();
				builder.append("<ol>");
				for(ErrorValidation err : validaciones){
					builder.append("<li>");
					builder.append(err.getError());
					builder.append("</li>");
				}
				builder.append("</ol>");
				modelo.setMensajeError(builder.toString());
			}
		}catch (EJBException e) {
			e.printStackTrace();
			modelo.setMensajeError(ConstantesBusiness.fromStackTraceToString(e));
		}

		SimpleDateFormat sf = new SimpleDateFormat(AbstractDocumentoElectronicoModel.FORMATO_FECHA_CADENA_ORIGINAL);
		UserSession user = getUsuarioFirmado(request);	
		modelo.setCveDelegacion(user.getCveCodigoDelegacion());
		modelo.setIdSubDelegacion(user.getIdSubDelegacion());
		modelo.setCveSubDelegacion(user.getCveCodigoSubDelegacion());
		modelo.setFechaCadenaOriginal(sf.format(new Date()));
		modelo.setProcedencia(user.getProcedencia());
		return modelo;
	}
	
	

	/**
	 * 
	 * @param correccion
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/buscarPorRegPatronal" , method=RequestMethod.POST)
	public @ResponseBody List<PresentacionCorreccionModel> 
							buscarSolicitudCorreccionPorRegPatronal(@RequestBody PresentacionCorreccionModel correccion, HttpServletRequest request){
		log.debug("+++++++++++++++++ POR REG. Patronal");
		return null;
	}
	
	/**
	 * cambiar String por algo adecuado para el JSON editable Table.
	 * @param filtro
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/buscarFoliosSolicitudCorreccion", method=RequestMethod.POST)
	public @ResponseBody DatosSalidaPaginador<DataTableSolCorreccion> buscarFoliosSolicitudCorreccion(@RequestBody FiltroBusquedaSolicitudCorreccion filtro, HttpServletRequest request){
		
		List<DataTableSolCorreccion> data = new ArrayList<DataTableSolCorreccion>();
		DatosSalidaPaginador<DataTableSolCorreccion> salida = new DatosSalidaPaginador<DataTableSolCorreccion>();
		
		@SuppressWarnings("rawtypes")
		DatosEntradaPaginador entrada = new DatosEntradaPaginador();
		entrada.parserArray(filtro.getAoData());
		UserSession user = getUsuarioFirmado(request);	
		if(user.getCveIdPatron()!=null && user.getCveIdPatron()!=0){
			System.out.println("Registro Patronal de Session "+user.getRegistroPatronal());			
			filtro.getoForm().setRegistroPatronalFiltro(user.getRegistroPatronal());
		}
	
		
		try{
			if(filtro.getoForm().getRegistroPatronalFiltro() != null && 
					!filtro.getoForm().getRegistroPatronalFiltro().equals("")){
				System.out.println("RP a filtrar  "+filtro.getoForm().getRegistroPatronalFiltro());
				data = businessController.buscarFoliosSolicitudCorreccionPorRegPatronal(filtro.getoForm().getRegistroPatronalFiltro(), filtro.getoForm().getFolioSoicitudCorreccionFiltro());
			}else if(filtro.getoForm().getFolioSoicitudCorreccionFiltro() != null &&
					!filtro.getoForm().getFolioSoicitudCorreccionFiltro().equals("")){
				data = businessController.buscarFolioSolicitudCorrecion(filtro.getoForm().getFolioSoicitudCorreccionFiltro());
			}else{
				//puede ser una inicilizacion de pagina asi que mandamos un objeto vacio o podemos mandar los de la sesion
				setDummyElements(data, 0);
			}
		
			salida.setAaData(data);
			salida.setiTotalDisplayRecords(data.size());
			salida.setiTotalRecords(data.size());
			salida.setsEcho(entrada.getsEcho());
		}catch (EJBException e) {
			System.out.println("------- VIEW buscar folios solicitud corrección Excepcion -------");
			e.printStackTrace();
		}
		
			return salida;
	}
	
	

	/**
	 * Este metodo solo sirve para cuestiones de debuggeo
	 * @param data
	 */
	private void setDummyElements(List<DataTableSolCorreccion> data, int num) {
		for(int i = 0; i < num ; i++){
			DataTableSolCorreccion sol = new DataTableSolCorreccion();
			sol.setEstatus("Aceptada Kaon" + i);
			sol.setFdLimiteSolicitud("12/12/2010");
			sol.setNuFolioSolCorreccion("1605/CE/2011/000" + i);
			sol.setTipoCorreccion("Me invitaron");
			data.add(sol);
		}
	}
	/**
	 * {0} - registro patronal
	 * {1} - Digito Verificador
	 * {2} - Num Trabajadores
	 * 
	 * {3} - Dom. Trabajo
	 * 
	 * {4} - Cuotas IMSS
	 * {5} - Cuotas IMSS Actualizacion
	 * {6} - Cuotas IMSS Recargos
	 * {7} - Cuotas IMSS Total
	 * 
	 * {8} - Cuotas RCV
	 * {9} - Cuotas RCV Actualizacion
	 * {10}- Cuotas RCV Recargos
	 * {11}- Cuotas RCV Total
	 * 
	 * {12}- Cuotas Total IMSS + RCV
	 * {13}- Cuotas Total Actualizacion IMSS + Actualizacion RCV
	 * {14}- Cuotas Total Recargos IMSS + Recargos RCV
	 * {15}- Cuotas TOtal IMSS Total + RCV Total
	 * 
	 * @param list
	 * @param request
	 * @return
	 */
	private String getHtmlCopPagadas(List<?> list, HttpServletRequest request){
		String fileName = request.getSession().getServletContext().getRealPath("/") + ConstantesBusiness.RUTA_ARCHIVO_PROPIEDADES_CORRECCION + ConstantesBusiness.ARCHIVO_PROPIEDADES;
		String completeHtml = null;
		StringBuilder mainBuilder = new StringBuilder();
		Object[] pagosArray = null;
		
		if(list.size()>0){
			pagosArray = (Object[]) list.get(0);
		}
		
		mainBuilder.append("<fieldset><table style=\"width: 900px\" align=\"center\"><tbody>");
		
		mainBuilder.append("<tr valign=\"top\" class=\"impar\">");
		mainBuilder.append("<td align=\"center\" colspan=\"8\"><b>Total de C.O.P pagadas en la corrección</b></td>");
		mainBuilder.append("</tr>");
	
	
		Properties props = new Properties();
		try {
			InputStream in = new FileInputStream(fileName);
			props.load(in);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		completeHtml = props.getProperty("text.correccion.presentacion1") + props.getProperty("text.correccion.presentacion2");
		MessageFormat formatter = new MessageFormat(completeHtml);
		
					
		mainBuilder.append(formatter.format(pagosArray));
		
		mainBuilder.append("</tbody></table></fieldset>");
		
		return mainBuilder.toString();
	}
	
	
	private Double parserDoubleImport(String importe){		
		if(importe==null || importe.equals("null")){
			return 0.0;
		}else{
			return Double.parseDouble(importe);	
		}		
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value="/presentarCorreccion", method=RequestMethod.POST)
	public @ResponseBody CrtPresentacorr presentarCorreccion(@RequestBody PresentacionCorreccionModel solicitudCorreccion, HttpServletRequest request){
		log.debug("Método que presenta la corrección ID: " + solicitudCorreccion);

		UserSession usuario = getUsuarioFirmado(request);
		
		CrtPresentacorr presentacion = new CrtPresentacorr();
		presentacion.setObservaciones(solicitudCorreccion.getObservaciones());
		presentacion.setFolioCorreccion(solicitudCorreccion.getFolioSoicitudCorreccionFiltro());
		presentacion.setRefLugar(solicitudCorreccion.getLugar());
		presentacion.setRepresentanteLegalElaboro(solicitudCorreccion.getNombreYFirmaPatron());
		presentacion.setObservaciones(solicitudCorreccion.getObservaciones());
		if(solicitudCorreccion.getChkCombtPago() != null &&
				solicitudCorreccion.getChkCombtPago().booleanValue()){
			presentacion.setNuComprobantepago(new BigDecimal("1"));
		}else{
			presentacion.setNuComprobantepago(new BigDecimal("0"));
		}
		
		if(solicitudCorreccion.getChkCombtAvisosAfil() != null &&
				solicitudCorreccion.getChkCombtAvisosAfil().booleanValue()){
			presentacion.setNuCompromovafil(new BigDecimal("1"));
		}else{
			presentacion.setNuCompromovafil(new BigDecimal("0"));
		}
		
		if(solicitudCorreccion.getChkDocumentacionCorreccion() != null &&
				solicitudCorreccion.getChkDocumentacionCorreccion().booleanValue()){
			presentacion.setNuDoctosustento(new BigDecimal("1"));
		}else{
			presentacion.setNuDoctosustento(new BigDecimal("0"));
		}
		
		//if(solicitudCorreccion.getTipoDeCorreccionHidden().intValue() == ConstantesBusiness.SOLICITUD_CORRECCION_ESPONTANEA){
			presentacion.setFecAutorizacion(ConstantesBusiness.stringToDate(solicitudCorreccion.getFechaAutorizacionCorreccionEspontanea()));
		//}
		
		String SQL = AbstractPresentacionQuerys.PAGO_TOTAL_COPS_RPS_INSCRITOS.replace("{1}", solicitudCorreccion.getCveSolicitudCorreccion().toString());
		List<?> pagosRPS = consultaGenerica.consultaSQL(SQL);
		
		PagosPatrones copsPagadasSumarizadas = new PagosPatrones(pagosRPS);
		
		
		
		presentacion.setImpCop(BigDecimal.valueOf(parserDoubleImport(copsPagadasSumarizadas.getCuotaIMSS())));
		presentacion.setImpCopact(BigDecimal.valueOf(parserDoubleImport(copsPagadasSumarizadas.getActualizacionIMSS())));
		presentacion.setImpCoprec(BigDecimal.valueOf(parserDoubleImport(copsPagadasSumarizadas.getRecargosIMSS())));
		presentacion.setImpCoptot(BigDecimal.valueOf(parserDoubleImport(copsPagadasSumarizadas.getTotalIMSS())));
		presentacion.setImpRcv(BigDecimal.valueOf(parserDoubleImport(copsPagadasSumarizadas.getCuotaRCV())));
		presentacion.setImpRcvact(BigDecimal.valueOf(parserDoubleImport(copsPagadasSumarizadas.getActualizacionRCV())));
		presentacion.setImpRcvrec(BigDecimal.valueOf(parserDoubleImport(copsPagadasSumarizadas.getRecargosRCV())));
		presentacion.setImpRcvtot(BigDecimal.valueOf(parserDoubleImport(copsPagadasSumarizadas.getTotalRCV())));

		presentacion.setCadenaOriginal(solicitudCorreccion.getCadenaOriginal());
		presentacion.setFirmaElectronica(solicitudCorreccion.getFirmaElectronica());
		
		presentacion.setTipoCertificado(TipoCertificado.SAT);		
		presentacion.setNuTrabreg(solicitudCorreccion.getNumeroTrabajadores());
		CrtPresentacorr presentacionCorreccion = businessController.presentarCorreccion(presentacion, usuario);	
		if(presentacionCorreccion.isFolioDesdeInternet()){
			return presentacionCorreccion;
		}
		
		PresentacionCorreccionVO vo = businessController.obtenerPresentacionCorreccion(presentacionCorreccion.getCveSolicitudcorr());
		
//		UserSession user = (UserSession) request.getSession().getAttribute(ConstantesSession.USR_SESSION);

		UserSession user = getUsuarioFirmado(request);
		
		System.out.println("Usuario recuperado desde la session "+user);
		if(user != null){
			System.out.println("Valor de subdelegacion reporte "+user.getNombreSubDelegacion());
			vo.setDelegacion(user.getNombreDelegacion());
			vo.setSubDelegacion(user.getNombreSubDelegacion());
		}
		
		List list = new ArrayList();
		
		
		Map parameters = new HashMap();
		if(user.isPatron()){
			parameters.put("internet","1");			
		}
		
		CrtSolicitudcorr solicitudcorr = null;
		solicitudcorr = solicitudService.consultarPorId(presentacionCorreccion.getCveSolicitudcorr());
		
		
		List<CrtAnexosolcorrpat> anexos=solicitudService.consultarAnexos(presentacionCorreccion.getCveSolicitudcorr(),CrtAnexosolcorrpat.TIPO_REGISTRO_RP_FISCAL);
		
		SatPatron patron = patronesService.getById(solicitudcorr.getCvePatron());
		
		String nrp = patron.getRegistroPatronal();
		String digVerificador = patron.getRegistroPatronal().substring(10);
		parameters.put("folioSolicitud",solicitudcorr.getNuFolio());
		parameters.put("nombrePatron",patron.getRazonSocial());
		parameters.put("registroPatron",nrp);
		parameters.put("digitoVerificador",digVerificador);
		parameters.put("curp",patron.getCurp());
		parameters.put("rfc",patron.getRfc());
	
		if(!anexos.isEmpty()){
			vo.setCorreoElectronico(anexos.get(0).getTxEmail());
		}
		
		parameters.put("firmaElectronica",solicitudCorreccion.getFirmaElectronica());
		System.out.println("Cadena origianl "+solicitudCorreccion.getCadenaOriginal());
		parameters.put("cadenaOriginal", solicitudCorreccion.getCadenaOriginal());
		parameters.put("selloIMSS", solicitudCorreccion.getSelloIMSS());
		parameters.put("telefonoDomicilio", solicitudCorreccion.getTelefono());
		parameters.put("rutaImagen", request.getSession().getServletContext().getRealPath("/resources/images/") + "/");
		parameters.put("SUBREPORT_DIR", request.getSession().getServletContext().getRealPath("/WEB-INF/views/formatos/") + "/");
		parameters.put("fechaPublicacionFormato", null);
		String rutaArchivoJasper = request.getSession().getServletContext().getRealPath("/WEB-INF/views/formatos/") + "/" + PRESENTACION_CORRECCION_PDF;
		
		list.add(vo);
		byte[] reporte = GeneraReporteUtil.getInstance().generaReporte(rutaArchivoJasper, parameters, list);
		
		final String acusePdf = org.apache.soap.encoding.soapenc.Base64.encode(reporte);
		request.getSession().setAttribute(ConstantesSession.DOCUMENTO_PDF, acusePdf);
		request.getSession().setAttribute(ConstantesSession.NOMBRE_ARCHIVO_PDF, "presentacionCorrreccion.pdf");
		request.getSession().setAttribute(ConstantesSession.TIPO_DESCARGA_PDF, ConstantesSession.MUESTRA_PDF);
		//valores de la firma
		CrcTramiteMensajes ms= solicitudCorreccion.getMensaje();
		CrtTramitePresentado pres=new CrtTramitePresentado();
		
		if(ms!=null){
			pres.setCveMensaje(ms.getCveMensaje());
		}
		pres.setCveSolcorr(Long.valueOf(presentacionCorreccion.getCveSolicitudcorr()));
		if(ms!=null)
		pres.setCveTramite(ms.getCveTramite());
		pres.setIdTramiteRefNotaria(solicitudCorreccion.getFirmaElectronica());
		pres.setUrlAcuseFirma(solicitudCorreccion.getUrlAcuseFirma());
		//pres.setCveUsuarioCurp("CURPSEssion");
		pres.setFecFechaReg(Calendar.getInstance().getTime());
		pres.setDesRegPatronal(patron.getRegistroPatronal());
		pres.setDesRazonSocial(patron.getRazonSocial());
		
		//Se envia el archivo para poder verse en el visor
		
			if(solicitudCorreccion.getFirmaElectronica()!=null && !solicitudCorreccion.getFirmaElectronica().equals("")){
				Archivo archivo=new Archivo();
				archivo.setNombre("presentacionCorrreccion.pdf");
				archivo.setBuffer(acusePdf);
				log.info("El id "+solicitudCorreccion.getFirmaElectronica());
				firmaElectronicaService.guardarArchivoFirmado(solicitudCorreccion.getFirmaElectronica(), archivo);
			}
			
		firmaElectronicaService.guardarTramitePresentado(pres);
		if(solicitudcorr.getCveIdSolicitudBDTU()!=null){
			firmaElectronicaService.agregaNuevoTramite(solicitudcorr.getCveIdSolicitudBDTU().longValue(), patron.getRegistroPatronal(), firmaElectronicaService.generaConfiguracionPresentacion());
			firmaElectronicaService.cerrarSolicitudBDTU(solicitudcorr.getCveIdSolicitudBDTU().longValue());
		}
				
		//Se cierra la solicitud de la BDTU
		
		return presentacionCorreccion;
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/paginar", method = RequestMethod.POST)
	public @ResponseBody
	DatosSalidaPaginador<PagosPatrones> pagina(@RequestBody PagosPatronWrapperDataTable aoData,HttpServletRequest request) {
		
		@SuppressWarnings("rawtypes")
		DatosEntradaPaginador send = new DatosEntradaPaginador();
		send.parserArray(aoData.getAoData());
		send.setModelo(aoData.getoForm());

		
		DatosSalidaPaginador<PagosPatrones> reply = new DatosSalidaPaginador<PagosPatrones>();
		String SQL = AbstractPresentacionQuerys.PAGO_COPS_RPS_INSCRITOS.replace("{1}", aoData.getoForm().getCveSolicitudCorreccion().toString());
		List<?> rpsInscritos = consultaGenerica.consultaSQL(SQL);
		Iterator<?> iter = rpsInscritos.iterator();
		
		List<PagosPatrones> patronesInscritos = new ArrayList<PagosPatrones>();
		
		while(iter.hasNext()){
			patronesInscritos.add(new PagosPatrones((Object[])iter.next()));
		}
		
		reply.setAaData(patronesInscritos);
		reply.setiTotalDisplayRecords(patronesInscritos.size());
		reply.setiTotalRecords(patronesInscritos.size());
		
		
		reply.setsEcho(send.getsEcho());

		return reply;
	}

}