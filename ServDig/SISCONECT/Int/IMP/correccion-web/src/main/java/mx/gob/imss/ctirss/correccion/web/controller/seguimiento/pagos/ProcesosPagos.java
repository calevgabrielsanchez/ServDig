package mx.gob.imss.ctirss.correccion.web.controller.seguimiento.pagos;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import mx.gob.imss.ctirss.correccion.framework.base.model.AbstractModel;
import mx.gob.imss.ctirss.correccion.model.CrtCoppagada;
import mx.gob.imss.ctirss.correccion.model.CrtSolicitudcorr;
import mx.gob.imss.ctirss.correccion.seguimiento.estudioCorreccion.model.CrtRevPagos;
import mx.gob.imss.ctirss.correccion.seguimiento.estudioCorreccion.model.PagosConsultasSQL;
import mx.gob.imss.ctirss.correccion.seguimiento.service.interfaces.pagos.PagosService;
import mx.gob.imss.ctirss.correccion.service.interfaces.ICatalogoService;
import mx.gob.imss.ctirss.correccion.session.UserSession;
import mx.gob.imss.ctirss.correccion.utils.Functions;
import mx.gob.imss.ctirss.correccion.web.controller.seguimiento.pagos.vo.PagosVO;
import mx.gob.imss.ctirss.correccion.web.controller.seguimiento.pagos.vo.RegistroPatronalVO;

/**
 * Nos permite controlar todos los procesos involucrados
 * en el flujo de pagos (Cédula de Revisión y Validación).
 * 
 * @see PagosSeguimientoController
 * @see PagosConsultasSQL
 * @see PagosVO
 * @see PagosService
 * @see ICatalogoService
 * 
 * @version 1.1.0
 * @author Marco Antonio Nieto Plett
 *
 */
@Component
public  class ProcesosPagos {
	
	/**
	 * Servicio que controla el ABC del Pojo {@link CrtRevPagos}
	 */
	@Autowired
	private PagosService<?> pagosService;
	
	/**
	 * Servicio genérico, se utiliza para consultas SQL.
	 * @see PagosConsultasSQL
	 */
	@Autowired
	private ICatalogoService<AbstractModel> servicioGenerico;
	
	/**Logger de la clase de ProcesoPagos*/
	private final static Logger logger = Logger.getLogger(ProcesosPagos.class);
	
	/**
	 * La pantalla de pagos es invocada a través de una ventana modal
	 * por lo que se requieren los siguientes parámetros en GET:<br><br>
	 * <b>periodoInicial:</b>DD/MM/YYYY<br>
	 * <b>periodoFinal:</b>DD/MM/YYYY<br>
	 * <b>folioCorreccion:</b>DDSS/XX/YYYY/####<br>
	 * <b>idPresentacion:</b>Númerico<br>
	 * <b>indTipoPago:</b> 1 para Cédula de Revisión y 2 Para Cédula de Validación<br>
	 * 
	 * @author Marco Antonio Nieto Plett
	 * @param pagosVO
	 * @param request
	 * 
	 * @see CrtRevPagos
	 * @return PagosVO inicializado
	 * @throws Exception en caso de que uno de los parámetros venga null.
	 */
	public PagosVO initModel(PagosVO pagosVO,HttpServletRequest request) throws Exception{
		
		logger.debug("Inicializando variables de request");
		
		String periodoInicial = request.getParameter("periodoInicial");
		String periodoFinal = request.getParameter("periodoFinal");
		String folioCorreccion = request.getParameter("folioCorreccion");
		String idPresentacion = request.getParameter("idPresentacion");
		String indTipoPago = request.getParameter("indTipoPago");
		String cveRegulaPagos = request.getParameter("cveRegulaPagos");
		String fechaMinDateCalendar = request.getParameter("fechaMinDateCalendar");
		
		
		if(validarParametrosEntrada(periodoInicial,periodoFinal,folioCorreccion,idPresentacion,indTipoPago,cveRegulaPagos,fechaMinDateCalendar)){
			
			logger.info("Asignando variable de request para el folio:"+folioCorreccion);
			
			pagosVO.setPeriodoInicial(Functions.stringToDate(periodoInicial));
			pagosVO.setPeriodoFinal(Functions.stringToDate(periodoFinal));
			pagosVO.setFolioCorreccion(folioCorreccion);
			pagosVO.getPagoModel().setCvePresentacorr(idPresentacion!=null ? Integer.valueOf(idPresentacion):null);
			pagosVO.getPagoModel().setIndTipopago(Integer.valueOf(indTipoPago));
			pagosVO.getPagoModel().setCveRegulaPagos(cveRegulaPagos!=null ? Integer.valueOf(cveRegulaPagos):null);
			pagosVO.setFechaMinDateCalendar(fechaMinDateCalendar.replaceAll("-", "/"));
		}
		
		pagosVO.setMsg("");
		
		return pagosVO;
		
	}
	
	
	/**
	 * Permite recuperar los registros patronales asociados a la solicitud
	 * de corrección a través del folio de corrección.
	 * 
	 * @author Marco Antonio Nieto Plett
	 * 
	 * @see CrtSolicitudcorr
	 * @see ICatalogoService
	 * @see PagosConsultasSQL
	 * @see RegistroPatronalVO
	 * @param folioCorreccion
	 * @return RegistroPatronalVO inicializado
	 */
	public RegistroPatronalVO obtenerRegistrosPatronales(PagosVO pagosVO){
		
		logger.info("Obteniendo registros patronales del folio:"+pagosVO.getFolioCorreccion());
		RegistroPatronalVO rpVO = null;
		
		if(pagosVO.getPagoModel().getCveRegulaPagos()==null){
			rpVO = new RegistroPatronalVO(
					servicioGenerico.consultaSQL(
							String.valueOf(PagosConsultasSQL.SQL_OBTENER_RPS).replace("{1}", pagosVO.getFolioCorreccion()))
					);
		}else{
			rpVO = new RegistroPatronalVO(
					servicioGenerico.consultaSQL(
							String.valueOf(PagosConsultasSQL.OBTENER_RP_ASOCIADO_PROMOCION).replace("{1}", pagosVO.getPagoModel().
																											getCveRegulaPagos().toString()))
					);			
		}
		
		
		return rpVO;
		
	}
	
	/**
	 * Permite obtener los datos de autodeterminación del patrón,
	 * estos son obtenidos de la tabla CRT_COPPAGADAS.
	 * <br><br>
	 * 
	 * Si existen datos en CRT_REVPAGOS este proceso no se ejecutará. 
	 * En caso contrario se ejecutará al momento de abrir 
	 * por primera vez la pantalla de pagos. 
	 * 
	 * @author Marco Antonio Nieto Plett
	 * 
	 * @see RegistroPatronalVO
	 * @see PagosVO
	 * @see CrtCoppagada
	 * @param regVO
	 * @param pagosVO
	 * @return Pagos realizados en la autodeterminación.
	 * @throws SQLException en caso de error con base de datos
	 * @throws Exception en caso de error personalizado
	 */
	private List<CrtCoppagada> getAutodetPatron(RegistroPatronalVO regVO, PagosVO pagosVO) throws SQLException, Exception{
		
		logger.info("Obteniendo Inf. de la autodeterminación del patrón");
		
		CrtCoppagada copPagadasModel = new CrtCoppagada();
		copPagadasModel.setIdsAnexoSolCorrPatConcat(regVO.getIdsConcatenados());
		
		return pagosService.obtenerDatosTablaCOPPagadas(copPagadasModel);
		
	}
	
	/**
	 * Permite recuperar todos los pagos a detalle realizados por el patron/auditor, 
	 * estos son los registros reflejados en la tabla CRT_REVPAGOS.<br><br>
	 * 
	 * Nota: Es importante indicar el indTipoPago (1,2) según sea 
	 * cédula de revisión o validación respectivamente.
	 * 
	 * @author Marco Antonio Nieto Plett
	 * 
	 * @see CrtRevPagos
	 * @see HashMap
	 * @param idPresentacion
	 * @param indTipoPago
	 * @param mapRps ( Se obtiene del objeto {@link RegistroPatronalVO}) 
	 * @return Lista de Pagos realizados ( Vacio en caso de no existir)
	 * @throws SQLException en caso de error con base de datos
	 * @throws Exception en caso de error personalizado
	 */
	public List<CrtRevPagos> obtenerListaPagos(Integer idPresentacion, Integer indTipoPago, 
									Integer cveRegulaPagos, Map<Integer,String> mapRps) throws SQLException, Exception{
		
		logger.info("Obteniendo Pagos Realizados ID Presentación:"+idPresentacion);
		
		CrtRevPagos model = new CrtRevPagos();
		model.setCvePresentacorr(idPresentacion);
		model.setIndTipopago(indTipoPago);
		model.setCveRegulaPagos(cveRegulaPagos);
		
		List<CrtRevPagos> lsPagosRealizados = pagosService.obtenerListaPagos(model);
		List<CrtRevPagos> lsPagosFinal = new ArrayList<CrtRevPagos>();
		
		
		if(mapRps!=null){
			for(CrtRevPagos currentPago : lsPagosRealizados){
				if(cveRegulaPagos==null){
					currentPago.setRegistroPatronal(String.valueOf(mapRps.get(currentPago.getCveAnexosolcorrpat())));
				}else{
					Iterator<String> iter = mapRps.values().iterator();
					iter.next();//eliminamos el seleccione del combo
					String registroPatronal = String.valueOf(iter.next());
					currentPago.setRegistroPatronal(registroPatronal);
				}
				
				lsPagosFinal.add(currentPago);
			}
		}else{
			lsPagosFinal = lsPagosRealizados;
		}
		
		
		return lsPagosFinal;
	}
	
	/**
	 * Permite recuperar todos los pagos a sumarizados por 
	 * registros patronale realizados por el patron/auditor, 
	 * estos son los registros reflejados en la tabla CRT_REVPAGOS.<br><br>
	 * 
	 * Nota: Es importante indicar el indTipoPago (1,2) según sea 
	 * cédula de revisión o validación respectivamente.
	 * 
	 * @author Marco Antonio Nieto Plett
	 * @see CrtRevPagos
	 * @param idPresentacion
	 * @param indTipoPago
	 * @return Lista de pagos agrupados por registro patronal
	 * @throws SQLException en caso de error con base de datos
	 * @throws Exception en caso de error personalizado
	 */
	public List<CrtRevPagos> obtenerListaPagosPorRP(Integer idPresentacion, Integer indTipoPago) throws SQLException, Exception{
		
		logger.info("Obteniendo Pagos Realizados por RP ID Presentación:"+idPresentacion);
		/*
		CrtRevPagos model = new CrtRevPagos();
		model.setCvePresentacorr(idPresentacion);
		model.setIndTipopago(indTipoPago);
		*/
		List<CrtRevPagos> lsPagosRealizados = pagosService.obtenerListaPagosPorRP(idPresentacion,indTipoPago);
				
		return lsPagosRealizados;
	}
	
	/**
	 * En caso de que no existan registros en la tabla de CRT_REVPAGOS relacionados
	 * con la clave de presentación y el tipo de pago se ejecutará este proceso
	 * al momento de abrir por primera vez la pantalla de pagos.<br><br>
	 * 
	 * La funcion de este método es trasferir la información que ingresó el patrón
	 * en la autodeterminación (CRT_COPPAGADAS) a la tabla CRT_REVPAGOS donde
	 * estarán disponibles para que el auditor manipule la información.<br><br>
	 * 
	 * Nota: Revisión es igual a Recepción
	 * 
	 * @author Marco Antonio Nieto Plett
	 * @see RegistroPatronalVO
	 * @see PagosVO
	 * @see UserSession
	 * @param regVO Registros patronales (Objeto inicializado)
	 * @param pagosVO 
	 * @param user
	 * @throws SQLException en caso de error con base de datos
	 * @throws Exception en caso de error personalizado
	 */
	public void inicializarTablaPagosAuditor(RegistroPatronalVO regVO, PagosVO pagosVO, UserSession user) throws SQLException, Exception{
        
		if(pagosVO.getPagoModel().getIndTipopago().intValue()==CrtRevPagos.PAGO_CEDULA_REVISION.intValue()){
			
			List<CrtCoppagada> lsCOPPagadas = getAutodetPatron(regVO,pagosVO);
			
			logger.info("Evaluando la transferencia de información para Folio : "+pagosVO.getFolioCorreccion());
			
			List<?> lsPagosRealizados =  obtenerListaPagos(pagosVO.getPagoModel().getCvePresentacorr(),
														   pagosVO.getPagoModel().getIndTipopago(),null,null);
			
			if(lsCOPPagadas!=null && !lsCOPPagadas.isEmpty() && lsPagosRealizados.isEmpty()){
				logger.info("Transladando Inf. de autodeterminacion al Auditor [Folio"+pagosVO.getFolioCorreccion()+"]");
				for(CrtCoppagada currentCOP : lsCOPPagadas){
					CrtRevPagos currentClon = new CrtRevPagos();
					currentClon.setCvePresentacorr(pagosVO.getPagoModel().getCvePresentacorr());
					currentClon.setCveAnexosolcorrpat(currentCOP.getCveAnexoSolCorrPat());
					currentClon.setCveEjercicio(Integer.valueOf(currentCOP.getCrtRpEjercicio().getCveEjercicio().toString()));
					currentClon.setNumPeriodo(currentCOP.getNumPeriodo());
					currentClon.setNumPeriodoRCV(currentCOP.getNumPeriodoRCV());
					
					currentClon.setImpCopsp(currentCOP.getImpCOP());
					currentClon.setImpCopact(currentCOP.getImpCOPAct());
					currentClon.setImpCoprec(currentCOP.getImpCOPRec());
					currentClon.setImpCoptot(currentCOP.getImpCOPTot());
					currentClon.setImpCopmulta(BigDecimal.ZERO);
					currentClon.setImpRcvsp(currentCOP.getImpRCV());
					currentClon.setImpRcvact(currentCOP.getImpRCVAct());
					currentClon.setImpRcvrec(currentCOP.getImpRCVRec());
					currentClon.setImpRcvtot(currentCOP.getImpRCVTot());
					currentClon.setImpRcvmulta(BigDecimal.ZERO);
					currentClon.setNumTrabregula(currentCOP.getNuTrabRegu());
					currentClon.setNumAltas(currentCOP.getNumTrabAltas());
					currentClon.setNumBajas(currentCOP.getNumTrabBajas());
					currentClon.setNumModifsalario(currentCOP.getNumTrabModSalario());
					currentClon.setNumFoliosua(currentCOP.getNumFolioSua());
					currentClon.setNumOrdeningreso(currentCOP.getNumOrdenIngreso());
					currentClon.setNumCredito(currentCOP.getNumCredito());
					currentClon.setFecFechapago(currentCOP.getFechaPago());
					currentClon.setIdTipodocto(currentCOP.getIdTipoDocto());
					currentClon.setFecFechareg(currentCOP.getFechaRegistro());
					currentClon.setIndTipopago(CrtRevPagos.PAGO_CEDULA_REVISION);
					currentClon = completarDatosObligatorios(user, currentClon);
					pagosService.saveOrUpdate(currentClon);
				}
				
			}else{
				logger.info("Información previamente transferida idPresentación: "+pagosVO.getFolioCorreccion());
			}
								 
		}else{
			logger.info("Transferencia de información no requerida para Folio : "+pagosVO.getFolioCorreccion());
		}
					
	}
	
	/**
	 * Permite completar los datos de auditoria y ejercicio del POJO.
	 * 
	 * @author Marco Antonio Nieto Plett
	 * @see CrtRevPagos 
	 * @param user
	 * @param pagoModel (Inicializado)
	 * @return Modelo listo para ingresar a la base de datos
	 */
	public CrtRevPagos completarDatosObligatorios(UserSession user,CrtRevPagos pagoModel){
		
		logger.debug("Completando datos obligatorios de auditoria y adicionales del modelo");
		
		pagoModel.setCveUsuario(user.getCveIdUsuario().toString());
		
		Calendar currentDate = Calendar.getInstance();
		pagoModel.setFecFechareg(currentDate.getTime());
		
		if(pagoModel.getCveRevpagos()!=null &&pagoModel.getCveRevpagos().intValue()<=0){
			pagoModel.setCveRevpagos(null);
		}
		
		if(pagoModel!=null && pagoModel.getIndTipopago().intValue()!=CrtRevPagos.PAGO_PROMOCION){
			if(pagoModel.getNumPeriodo()!=null)
				pagoModel.setCveEjercicio(Integer.parseInt(pagoModel.getNumPeriodo().toString().substring(0,4)));
		}else{
				pagoModel.setCveEjercicio(null);
				pagoModel.setCveAnexosolcorrpat(null);
		}
		
		return pagoModel;
		
	}
	
	/**
	 * Permite validar los parámentros enviados por GET 
	 * al momento de abrir la pantalla de pagos.
	 * 
	 * @author Marco Antonio Nieto Plett
	 * @param periodoInicial
	 * @param periodoFinal
	 * @param folioCorreccion
	 * @param idPresentacion
	 * @param indTipoPago (1 o 2)
	 * @return true en caso de éxito, false en cualquier otro caso.
	 * @throws Exception en caso de que alguno de los valores sean null.
	 */
	private Boolean validarParametrosEntrada(String periodoInicial,String periodoFinal,
												String folioCorreccion,String idPresentacion,
												String indTipoPago,String cveRegulaPagos,String fechaMinDateCalendar) throws Exception{
		
		if(periodoInicial==null){
			logger.error("Periodo Incial Null, verificar los parámentros del GET (URL)");
			throw new Exception("El periodo inicial ingresado es inválido Error: NULL");
		}else if(periodoFinal==null ){
			logger.error("Periodo Final Null, verificar los parámentros del GET (URL)");
			throw new Exception("El periodo Final ingresado es inválido Error: NULL");
		}else if(folioCorreccion==null && cveRegulaPagos==null){
			logger.error("Folio de Corrección y clave de Regula Pagos Null se requiere 1"
					       +", verificar los parámentros del GET (URL)");
			throw new Exception("El Folio de Corrección ingresado es inválido Error: NULL");
		}else if(idPresentacion==null && cveRegulaPagos==null){
			logger.error("ID Presentación Null y clave de Regula Pagos Nullse requiere 1"
					     +", verificar los parámentros del GET (URL)");
			throw new Exception("El ID Presentación ingresado es inválido Error: NULL");
		}else if(indTipoPago==null){
			logger.error("ID tipo Pago Null, verificar los parámentros del GET (URL)");
			throw new Exception("El ID Tipo Pago ingresado es inválido Error: NULL");
		}else if(idPresentacion!=null && cveRegulaPagos!=null){
			logger.error("IdPresentacón y cveRegulaPagos son excluyetes solo se debe de usar 1");
			throw new Exception("El ID Tipo Pago ingresado es inválido Error: NULL");
		}else if(cveRegulaPagos!=null && !indTipoPago.equals(CrtRevPagos.PAGO_PROMOCION.toString())){
			logger.error("Si envía el parámentro de cveREgula pagos el indTipoPago debe de ser 3 [Promoción]");
			throw new Exception("El indTipoPago al usar cveRegulaPagos debe de ser 3 [Promoción]");
		}else if(cveRegulaPagos!=null && folioCorreccion!=null){
			logger.error("Los pagos por Promoción no contienen folio de corrección asignada, remover parámetro");
			throw new Exception("El ID Tipo Pago ingresado es inválido Error: NULL");
		}else if(cveRegulaPagos!=null && idPresentacion!=null){
			logger.error("Los pagos por Promoción no contienen presentación asignada, remover parámetro");
			throw new Exception("El ID Tipo Pago ingresado es inválido Error: NULL");
		}else if(cveRegulaPagos!=null && cveRegulaPagos.trim().equals("")){
			logger.error("El parametro cveRegulaPagos se encuentra vacio, favor de revisar envio GET en invocacion");
			throw new Exception("El parametro cveRegulaPagos se encuentra vacio, favor de revisar envio GET en invocacion");
		}else if(idPresentacion!=null && idPresentacion.trim().equals("")){
			logger.error("El parametro idPresentacion se encuentra vacio, favor de revisar envio GET en invocacion");
			throw new Exception("El parametro idPresentacion se encuentra vacio, favor de revisar envio GET en invocacion");
		}else if(folioCorreccion!=null && folioCorreccion.trim().equals("")){
			logger.error("El parametro folioCorreccion se encuentra vacio, favor de revisar envio GET en invocacion");
			throw new Exception("El parametro folioCorreccion se encuentra vacio, favor de revisar envio GET en invocacion");
		}else if(periodoInicial!=null && periodoInicial.trim().equals("")){
			logger.error("El parametro periodoInicial se encuentra vacio, favor de revisar envio GET en invocacion");
			throw new Exception("El parametro periodoInicial se encuentra vacio, favor de revisar envio GET en invocacion");
		}else if(periodoFinal!=null && periodoFinal.trim().equals("")){
			logger.error("El parametro periodoFinal se encuentra vacio, favor de revisar envio GET en invocacion");
			throw new Exception("El parametro periodoFinal se encuentra vacio, favor de revisar envio GET en invocacion");
		}else if(fechaMinDateCalendar==null){
			logger.error("El parametro fechaMinDateCalendar no se encuentra al momento de invocar la pantalla, favor de revisar envio GET en invocacion");
			throw new Exception("El parametro fechaMinDateCalendar no se encuentra al momento de invocar la pantalla, favor de revisar envio GET en invocacion");
		}else if(fechaMinDateCalendar!=null && periodoFinal.trim().equals("")){
			logger.error("El parametro fechaMinDateCalendar se encuentra vacio, favor de revisar envio GET en invocacion");
			throw new Exception("El parametro fechaMinDateCalendar se encuentra vacio, favor de revisar envio GET en invocacion");
		}
		
		
		
		
		logger.debug("Parámetros validados en la consulta del folio:"+folioCorreccion);
		return true;
	}
	
	/**
	 * Permite validar que los registros patronales asociados a
	 * la solicitud de corrección sean válidos y existan.
	 * 
	 * @author Marco Antonio Nieto Plett
	 * @param ls
	 * @return true en caso de que existan registros patronales asociados
	 *         false en cualquier otro caso.
	 */
	public Boolean validarContenidoRPs(List<RegistroPatronalVO> ls){
		
		logger.debug("Verificando Infromación de Registros Patronales");
		
		if(ls!=null && !ls.isEmpty()){
			logger.debug("Información válida");
			return true;
			
		}
		logger.debug("Información inválida");
		return false;
	}
	
	public RegistroPatronalVO getRegistroPatronalPromocion(PagosVO pagosVO){
		RegistroPatronalVO rpVO = new RegistroPatronalVO(
				servicioGenerico.consultaSQL(
						String.valueOf(PagosConsultasSQL.OBTENER_RP_ASOCIADO_PROMOCION).replace("{1}", pagosVO.getPagoModel().
																										getCveRegulaPagos().toString()))
				);
		if(rpVO.getListaRPS().isEmpty()){
			return null;
		}else{
			return rpVO.getListaRPS().get(0);
		}
			 
	}

}
