/**
 * SeguimientoCorreccionController.java
 * @package mx.gob.imss.ctirss.correccion.web.controller.seguimiento.estudioCorreccion
 * @project correccion-web
 */
package mx.gob.imss.ctirss.correccion.web.controller.seguimiento.estudioCorreccion;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import mx.gob.imss.ctirss.correccion.catalogos.model.CrcPercepciones;
import mx.gob.imss.ctirss.correccion.catalogos.model.SacDelegacion;
import mx.gob.imss.ctirss.correccion.catalogos.model.SacSubdelegacion;
import mx.gob.imss.ctirss.correccion.commons.vo.seguimiento.estudioCorreccion.CedulaRevisionAudVO;
import mx.gob.imss.ctirss.correccion.commons.vo.seguimiento.estudioCorreccion.CedulaValidacionConsolidadoVO;
import mx.gob.imss.ctirss.correccion.commons.vo.seguimiento.estudioCorreccion.CedulaValidacionVO;
import mx.gob.imss.ctirss.correccion.commons.vo.seguimiento.estudioCorreccion.CorreccionSeguimientoGenericoVO;
import mx.gob.imss.ctirss.correccion.commons.vo.seguimiento.estudioCorreccion.DerivacionFiscalSeguimientoCorrVO;
import mx.gob.imss.ctirss.correccion.commons.vo.seguimiento.estudioCorreccion.DevSubDelegacionSeguimientoCorreccionVO;
import mx.gob.imss.ctirss.correccion.commons.vo.seguimiento.estudioCorreccion.OperacionCorreccionVO;
import mx.gob.imss.ctirss.correccion.commons.vo.seguimiento.estudioCorreccion.RecepcionSeguimientoVO;
import mx.gob.imss.ctirss.correccion.commons.vo.seguimiento.estudioCorreccion.RevOficiosVO;
import mx.gob.imss.ctirss.correccion.commons.vo.seguimiento.estudioCorreccion.RubroVO;
import mx.gob.imss.ctirss.correccion.commons.vo.seguimiento.promocion.generico.SegRegularizarObraGenericoTabVO;
import mx.gob.imss.ctirss.correccion.framework.base.controller.AbstractController;
import mx.gob.imss.ctirss.correccion.framework.base.model.AbstractModel;
import mx.gob.imss.ctirss.correccion.framework.utils.ConstantesBusiness;
import mx.gob.imss.ctirss.correccion.framework.utils.enums.CatEstatus;
import mx.gob.imss.ctirss.correccion.model.CgcCatMotivoCancelacion;
import mx.gob.imss.ctirss.correccion.model.CrtPresentacorr;
import mx.gob.imss.ctirss.correccion.model.CrtRevCedRevision;
import mx.gob.imss.ctirss.correccion.model.CrtRevDerivAFisca;
import mx.gob.imss.ctirss.correccion.model.CrtRevDerivASubd;
import mx.gob.imss.ctirss.correccion.model.CrtRevOficios;
import mx.gob.imss.ctirss.correccion.model.CrtRevRecepcion;
import mx.gob.imss.ctirss.correccion.model.CrtSolicitudOficios;
import mx.gob.imss.ctirss.correccion.model.CrtSolicitudcorr;
import mx.gob.imss.ctirss.correccion.seguimiento.estudioCorreccion.model.CrtRevPagos;
import mx.gob.imss.ctirss.correccion.seguimiento.interfaces.SeguimientoCorreccionServiceCtr;
import mx.gob.imss.ctirss.correccion.seguimiento.service.interfaces.pagos.PagosService;
import mx.gob.imss.ctirss.correccion.service.interfaces.ICatalogoService;
import mx.gob.imss.ctirss.correccion.session.UserSession;
import mx.gob.imss.ctirss.correccion.utils.Functions;
import mx.gob.imss.ctirss.correccion.web.controller.consultaEstudioCorreccion.procesos.GeneraTablaCedulaG;
import mx.gob.imss.ctirss.correccion.web.controller.consultaEstudioCorreccion.vo.ConsultaEstudioCorreccionVO;
import mx.gob.imss.ctirss.correccion.web.controller.seguimiento.promocion.generico.SeguimientoPromocionGenericoController;
import mx.gob.imss.ctirss.delta.exception.domicilio.DomicilioNoLocalizadoException;
import mx.gob.imss.ctirss.delta.gestion.domicilio.service.interfaces.domicilio.DomicilioServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.model.domicilio.Domicilio;
import mx.gob.imss.ctirss.domiciliosInegi.model.DgDomicilioGeografico;
import mx.gob.imss.ctirss.domiciliosInegi.service.interfaces.DomiciliosInegiService;
import mx.gob.imss.ctirss.domiciliosInegi.web.controller.DomGeograficosController;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperRunManager;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;


/**
 * @author Oscar Beltran Ortega
 * @company IMSS (Instituto Mexicano del Seguro Social)
 * @date 31/07/2012
 */

@Controller						
@RequestMapping(value="/seguimiento/correccion")
public class SeguimientoCorreccionController extends AbstractController{
	

	/**
	 * Logger.
	 */
	private final static Logger logger = Logger.getLogger(SeguimientoPromocionGenericoController.class);
	
	
	
	@Autowired
	private SeguimientoCorreccionServiceCtr seguimientoCorreccionServiceCtr;
	@Autowired
	private ICatalogoService<CgcCatMotivoCancelacion> catalogoCriteriosServiceBean;
	
	@Autowired 
	private ICatalogoService<SacDelegacion> delegacionDAO;
	
	@Autowired 
	private ICatalogoService<CrtSolicitudcorr> crtSolicitudCorr;
	
	@Autowired 
	private ICatalogoService<SacSubdelegacion> subdelegacionDAO;
	
	@Autowired 
	private ICatalogoService<CgcCatMotivoCancelacion> motivoCancelacion;
	
	
	
	@Autowired 
	private ICatalogoService<?> catalogoDAO;
	
	
	@Autowired 
	private ICatalogoService<CrtSolicitudOficios> solicitudOficiosDAO;
	
	
	@Autowired
	private PagosService<?> pagosService;
	
	@Autowired
	protected DomicilioServiceBusinessRemote domiciliosServiceBean;	
	
	@Autowired
	private ICatalogoService<AbstractModel> iCatalogoServiceBean;
	private final static Long requerimientoDocumentacion = 1L;
	private final static Long oficioResultados = 2L;
	private final static Long conclusion = 3L;
	private final static Long cancelacion = 5L;
	private final static Long DERIVACION_DICTAMEN = 4L;
	private final static Long CORRECCION_RECHAZADA = 6L;
	
	@Autowired 
	private DomiciliosInegiService<DgDomicilioGeografico> domiciliosInegiServiceBean;	
	
	
	@Autowired 
	private GeneraTablaCedulaG cedulaG;
	/** 
	 * Metodo llamdo por AJAX para preguntar por la fecha del sistema esto con
	 * el objetivo de evitar de fallas en los calculos de fechas. Cabe la
	 * posibilidad de que la creacion y obtencion de las fechas actuales sean
	 * incorrectas si esta operacion se le delega a JAVASCRIPT ya que con
	 * cambiar la fecha en la maquina local los calendarios generados se
	 * reajustaran a esta fecha local, en cambio si la fecha del dia se pide al
	 * servidor no habra este tipo de errores, claro a menos de que la fecha del
	 * servidor tambien este mal.
	 * 
	 * @param HttpRequest
	 * @return
	 */
	@RequestMapping(value = "/obtenerFechaServidor.do", method = RequestMethod.POST)
	public @ResponseBody
	String obtenerFechaServidor(HttpServletRequest request) {
		return ConstantesBusiness.dateToStringFormat(new Date(), "dd-MM-yyyy");
	}

	/**
	 * Metodo llamdo por AJAX para preguntar por la fecha del sistema esto con
	 * el objetivo de evitar de fallas en los calculos de fechas. Cabe la
	 * posibilidad de que la creacion y obtencion de las fechas actuales sean
	 * incorrectas si esta operacion se le delega a JAVASCRIPT ya que con
	 * cambiar la fecha en la maquina local los calendarios generados se
	 * reajustaran a esta fecha local, en cambio si la fecha del dia se pide al
	 * servidor no habra este tipo de errores, claro a menos de que la fecha del
	 * servidor tambien este mal.
	 * 
	 * @param HttpRequest
	 * @return
	 */
	@RequestMapping(value = "/obtenerFechaServidorMinima.do", method = RequestMethod.POST)
	public @ResponseBody
	String obtenerFechaServidorMinima(HttpServletRequest request) {
		Calendar calendario = Calendar.getInstance();
		calendario.add(Calendar.DAY_OF_YEAR, -44);
		Date dia = calendario.getTime();
		return ConstantesBusiness.dateToStringFormat(dia,ConstantesBusiness.dd_mm_yyyy);
	}
	
	


	/**
	 * Metodo que recupera la informacion correspondiente al usuario y determinar el rol asignado
	 *
	 * @author Jorge Hernandez Almazan
	 * @version 1.0.1
	 */
	@RequestMapping(value = "/consultaRolUsuario.do", method = RequestMethod.POST)	
	public @ResponseBody String consultaRolUsuario(HttpServletResponse response , HttpServletRequest request) {
	
		UserSession user = getUsuarioFirmado(request);		
		return String.valueOf(user.getCveRol());
		
	}
	
	

	/**
	 * Metodo que recupera la informacion correspondiente al usuario y determinar el rol asignado
	 *
	 * @author Jorge Hernandez Almazan
	 * @version 1.0.1
	 */
	@RequestMapping(value = "/consultaRegistrosPatronales.do", method = RequestMethod.POST)	
	public @ResponseBody List<Object> consultaRegistrosPatronales(@RequestBody CorreccionSeguimientoGenericoVO clase,HttpServletResponse response , HttpServletRequest request) {
	
		return seguimientoCorreccionServiceCtr.getRegPatronales(clase);
		
	}
	

	/**
	 * Metodo que atuoriza la revision de cedula , actualiza estatus de la presentacion
	 *
	 * @author Jorge Hernandez Almazan
	 * @version 1.0.1
	 */
	@RequestMapping(value = "/autorizaCedulaRevision.do", method = RequestMethod.POST)	
	public @ResponseBody CorreccionSeguimientoGenericoVO autorizaCedulaRevision(@RequestBody CorreccionSeguimientoGenericoVO clase,HttpServletResponse response , HttpServletRequest request) {
		
		
		UserSession user = getUsuarioFirmado(request);	
		boolean res=false;		
		clase.setUsuarioFirmado(user);
		clase=seguimientoCorreccionServiceCtr.guardaCedulaRevision(clase, true);
		
		res=seguimientoCorreccionServiceCtr.autorizaCedulaRevision(clase);
		clase.getCedulaRevisionAudVO().setAutorizacionCompleta(res);
		if(res){
			clase.setExito("Autorizacion de cedulas completa");	
		}else{
			clase.setExito("Autorizacion efectuada,quedan cedulas por autorizar");	
		}
		return clase;
		
	}
	
	/**
	 * Metodo que rechaza la revision de la cedula, se actualiza el estatus de la presentacion
	 * 
	 *
	 * @author Jorge Hernandez Almazan
	 * @version 1.0.1
	 */
	
	@RequestMapping(value = "/rechazaCedulaRevision.do", method = RequestMethod.POST)	
	public @ResponseBody CorreccionSeguimientoGenericoVO rechazaCedulaRevision(@RequestBody CorreccionSeguimientoGenericoVO clase,HttpServletResponse response , HttpServletRequest request) {
		
		UserSession user = getUsuarioFirmado(request);	
		String res=null;
		
		clase.setUsuarioFirmado(user);
		seguimientoCorreccionServiceCtr.guardaCedulaRevision(clase, true);
		res=seguimientoCorreccionServiceCtr.rechazaCedulaRevision(clase);		
		clase.setExito(res);
		
		return clase;
		
	}
	
	/**
	 * Metodo que recupera la informacion correspondiente a la cedula de revision
	 *
	 * @author Jorge Hernandez Almazan
	 * @version 1.0.1
	 */
	@RequestMapping(value = "/cedulaRevisionAud.do", method = RequestMethod.POST)	
	public @ResponseBody CorreccionSeguimientoGenericoVO cedulaRevisionAud(@RequestBody CorreccionSeguimientoGenericoVO clase,HttpServletResponse response , HttpServletRequest request) {
	
		UserSession user = getUsuarioFirmado(request);			
		clase.setUsuarioFirmado(user);
		
		CorreccionSeguimientoGenericoVO vo=seguimientoCorreccionServiceCtr.generaCedulaRevision(clase);
		List<RubroVO> rubros=new ArrayList<RubroVO>();
		List<CrcPercepciones> per=new ArrayList<CrcPercepciones>();	
		vo.getCedulaRevisionAudVO().setRubros(rubros);
		vo.getCedulaRevisionAudVO().setPercepciones(per);
		return vo;
		
	}
	
	@RequestMapping(value = "/guardaRevision.do", method = RequestMethod.POST)	
	public @ResponseBody CorreccionSeguimientoGenericoVO guardaRevision(@RequestBody CorreccionSeguimientoGenericoVO clase,HttpServletResponse response , HttpServletRequest request) {
		UserSession user = getUsuarioFirmado(request);	
		clase.setUsuarioFirmado(user);
		return seguimientoCorreccionServiceCtr.guardaCedulaRevision(clase, false);		
	}
	
	
	@RequestMapping(value = "/generaReporte.do", method = RequestMethod.POST)	
	public @ResponseBody CorreccionSeguimientoGenericoVO generaReporte(HttpServletResponse response , HttpServletRequest request) throws IOException {
		System.out.println("generando reporte");
		byte[] reporte = null;		
		InputStream reportStream = null;
		HttpSession session = request.getSession();
		Map parameters = new HashMap();
		try {
			
			reportStream = new FileInputStream(session.getServletContext().getRealPath("/WEB-INF/views/formatos/cedulaRevisionReporte.jasper"));
			reporte = JasperRunManager.runReportToPdf(reportStream, parameters,new JREmptyDataSource());
			final String acusePdf = org.apache.soap.encoding.soapenc.Base64.encode(reporte);
					
			session.setAttribute("documento", acusePdf);
			session.setAttribute("nombreArchivo", "RevisionCedula.pdf");
			session.setAttribute("tipoDescarga", "muestra");

			System.out.println("termina metodoaaaa");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (JRException e) {
			e.printStackTrace();
		}
	return new CorreccionSeguimientoGenericoVO();
		
	}
	
	
//	@RequestMapping(value = "/guardaRevisionSupervisor.do", method = RequestMethod.POST)	
//	public @ResponseBody CorreccionSeguimientoGenericoVO guardaRevisionSupervisor(@RequestBody CorreccionSeguimientoGenericoVO clase,HttpServletResponse response , HttpServletRequest request) {
//		
//		
//		UserSession user = getUsuarioFirmado(request);	
//		clase.setUsuarioFirmado(user);
//		return seguimientoCorreccionServiceCtr.guardaCedulaRevision(clase, true);
//		
//	}
//	
	
	
	
	@RequestMapping(value = "/finalizaCedulaRevision.do", method = RequestMethod.POST)	
	public @ResponseBody CorreccionSeguimientoGenericoVO finalizaCedulaRevision(@RequestBody CorreccionSeguimientoGenericoVO clase,HttpServletResponse response , HttpServletRequest request) {
		
	
		String res=null;
		UserSession user = getUsuarioFirmado(request);	
		clase.setUsuarioFirmado(user);
		res=seguimientoCorreccionServiceCtr.finalizaCedulaRevision(clase);
	
		clase.setExito(res);
		return clase;		
	}
	
	
	@RequestMapping(value = "/getBaseCotPagadaImss.do", method = RequestMethod.POST)	
	public @ResponseBody CorreccionSeguimientoGenericoVO getBaseCotPagada(@RequestBody CorreccionSeguimientoGenericoVO clase,HttpServletResponse response , HttpServletRequest request) {
	
		
		ConsultaEstudioCorreccionVO vo=new ConsultaEstudioCorreccionVO();
		UserSession user = getUsuarioFirmado(request);		
//		String rol=consultaRolUsuario(user.getCveIdUsuario());
		String rol=String.valueOf(user.getCveRol());
		user.setCveRol(Long.parseLong(rol));
		//user.setCveRol(Long.parseLong("11"));
		clase.setUser(user);
		vo.setIdSubDelegacion(user.getIdSubDelegacion().toString());		
		
		vo.setFolioCorreccion(clase.getNuFolio());
		vo.setRegistroPatronal(clase.getRegPatronal().substring(0, clase.getRegPatronal().length()-1));
		vo.setPeriodo(String.valueOf(clase.getCveEjercicio()));
	
		CrtRevCedRevision crtRev=null;
		CedulaRevisionAudVO cedulaRevisionAudVO=new CedulaRevisionAudVO();
		CedulaRevisionAudVO cedulaRevisionAudVOAcceso=new CedulaRevisionAudVO();
		cedulaRevisionAudVOAcceso=seguimientoCorreccionServiceCtr.determinaEstadoRecepcion(cedulaRevisionAudVOAcceso, clase);
		logger.info("Indicador "+cedulaRevisionAudVOAcceso.getIndAutorizaRevision());
		//no se permite el acceso al detalle
		CrtRevRecepcion rece=seguimientoCorreccionServiceCtr.consultaRecepcion(clase.getCvePresentaCorr(), ConstantesBusiness.TIPO_PAGO_REVISION);
		//logger.info("Indicador "+rece.getCveRevRecepcion());
		Double val=0.0;
		crtRev=seguimientoCorreccionServiceCtr.consultaCedulaRevision(clase);
		cedulaRevisionAudVO=seguimientoCorreccionServiceCtr.getRubros(clase);	
		if(crtRev!=null){
			//metemos valores de la bd
			cedulaRevisionAudVO.setAccesoDetalle(cedulaRevisionAudVOAcceso.isAccesoDetalle());
			cedulaRevisionAudVO.setRazonAcceso(cedulaRevisionAudVOAcceso.getRazonAcceso());
			cedulaRevisionAudVO.setBaseCotPagaImsIMSS(String.valueOf(crtRev.getImporteBaseCotPagImss()));
			cedulaRevisionAudVO.setBaseCotPagaImsPatron(String.valueOf(crtRev.getImporteBaseCotPatron()));
			cedulaRevisionAudVO.setDifBaseCotiIMSS(String.valueOf(crtRev.getImporteDIfBaseCotPagImss()));
			cedulaRevisionAudVO.setDifBaseCotiPatron(String.valueOf(crtRev.getImporteDifBaseCotPatron()));
			cedulaRevisionAudVO.setAutorizaBaseCotPaga((crtRev.getIndAutorizaBaseCotPagImss()!=null && crtRev.getIndAutorizaBaseCotPagImss().intValue()==1)?true:false);
			cedulaRevisionAudVO.setAutorizaDifBaseCot((crtRev.getIndAutorizaDifCotPagImss()!=null && crtRev.getIndAutorizaDifCotPagImss().intValue()==1)?true:false);
			if(rece!=null && rece.getIndAutorizaRevision()!=null && rece.getIndAutorizaRevision()==2){
				cedulaRevisionAudVO.setIndAutorizaRevision(1);
			}
			
		}else{
			//Consultamos la cedula G
			cedulaG.generarVistaCedula(vo);
			val=cedulaG.getImCuotaGuarderiasTotalColumna();
			cedulaRevisionAudVO.setAccesoDetalle(cedulaRevisionAudVOAcceso.isAccesoDetalle());
			cedulaRevisionAudVO.setRazonAcceso(cedulaRevisionAudVOAcceso.getRazonAcceso());
			cedulaRevisionAudVO.setBaseCotPagaImsPatron(val!=null?val.toString():"0.0");			
			cedulaRevisionAudVO.setBaseCotPagaImsIMSS(String.valueOf(BigDecimal.ZERO));
			cedulaRevisionAudVO.setDifBaseCotiIMSS(String.valueOf(BigDecimal.ZERO));
			cedulaRevisionAudVO.setDifBaseCotiPatron(String.valueOf(BigDecimal.ZERO));
		}			
		logger.info("Indicador "+cedulaRevisionAudVOAcceso.getIndAutorizaRevision());
		clase.setCedulaRevisionAudVO(cedulaRevisionAudVO);		
		return clase;
		
	}
	
	/**
	 * Metodo que se encarga de actualizar los datos de Requerimiento Documentacion
	 * @param revOficiosVO
	 * @return revOficiosVO
	 * @author Enrique Duran Jimenez
	 * @since  08/08/2012
	 * @version 1.0.0
	 */	
	@RequestMapping(value="/guardarReqDocumentacion" , method=RequestMethod.POST)
	public @ResponseBody RevOficiosVO guardarReqDocumentacion(@RequestBody RevOficiosVO model, HttpServletRequest request) {
		CrtRevOficios revOficios = new CrtRevOficios();
		UserSession usuario = getUsuarioFirmado(request);
		
		if(model.getCveSolCorr() != null){			
			revOficios.setCveSolCorr(model.getCveSolCorr());
			revOficios.setId_TipoOficio(requerimientoDocumentacion);
			revOficios = this.seguimientoCorreccionServiceCtr.consultaRevOficiosPorCveSolCorr(revOficios);
		}
		if(revOficios == null){
			revOficios = new CrtRevOficios();
			revOficios.setCveSolCorr(model.getCveSolCorr());
			revOficios.setCvePresentaCorr(model.getCvePresentaCorr());
			seguimientoCorreccionServiceCtr.guardaEstatusRecepcion(model.getCvePresentaCorr(), CatEstatus.PROCESO_DE_REQUERIMIENTO_DE_DOCUMENTACION.getId().intValue());
		}
		if(model.getNumFolioOficio() != null){
			revOficios.setNumFolioOficio(model.getNumFolioOficio());
		}		
		if(model.getFecFechaNotOf() != null && !model.getFecFechaNotOf().equals("")){
			revOficios.setFecFechaNotOf(Functions.stringToDate(model.getFecFechaNotOf()));
		}
		if(model.getFecFechaEmiOf() != null && !model.getFecFechaEmiOf().equals("")){
			revOficios.setFecFechaEmiOf(Functions.stringToDate(model.getFecFechaEmiOf()));
		}
		if(model.getFecFechaAtencionOf() != null && !model.getFecFechaAtencionOf().equals("")){
			revOficios.setFecFechaAtencionOf(Functions.stringToDate(model.getFecFechaAtencionOf()));
		}
		revOficios.setTxObservaciones(model.getTxObservaciones());
		revOficios.setFecFechaReg(new Date());
		revOficios.setId_TipoOficio(requerimientoDocumentacion);
		revOficios.setCveUsuario(usuario.getCurpUsuario().toString());
		
		revOficios = this.seguimientoCorreccionServiceCtr.guardaReqDoc(revOficios);
		model.setCveRevOficios(revOficios.getCveRevOficios());
		
		return model;	
	}
	
	/**
	 * Metodo que se encarga de buscar los datos de Requerimiento Documentacion
	 * @param CrtRevOficios
	 * @return CrtRevOficios
	 * @author Enrique Duran Jimenez
	 * @since  09/08/2012
	 * @version 1.0.0
	 */	
	@RequestMapping(value="/buscaReqDocumentacion" , method=RequestMethod.POST)
	public @ResponseBody CrtRevOficios buscaReqDocumentacion(@RequestBody CrtRevOficios model, HttpServletRequest request) {
		
		Long cveSolCorr = model.getCveSolCorr();
		CrtPresentacorr presentaCorr = new CrtPresentacorr();
		if(cveSolCorr != null){
			model.setId_TipoOficio(requerimientoDocumentacion);
			model = this.seguimientoCorreccionServiceCtr.consultaRevOficiosPorCveSolCorr(model);
		}
		if(model != null){
			if(model.getFecFechaEmiOf() != null){
				model.setFechaEmision(Functions.dateToString(model.getFecFechaEmiOf()));
			}
			if(model.getFecFechaNotOf() != null){
				model.setFechaNotificacion(Functions.dateToString(model.getFecFechaNotOf()));
			}
			if(model.getFecFechaAtencionOf() != null){
				model.setFechaAtencion(Functions.dateToString(model.getFecFechaAtencionOf()));
			}
		}else{
			model = new CrtRevOficios();
			model.setCveSolCorr(cveSolCorr);
			presentaCorr.setCveSolicitudcorr(cveSolCorr.intValue());
			presentaCorr = this.seguimientoCorreccionServiceCtr.consultaCrtPresentacorrPorClave(presentaCorr);
			if(presentaCorr != null){
				model.setCvePresentaCorr(presentaCorr.getCvePresentacorr());
			}
		}
		
		
		return model;	
	}
	
	@RequestMapping(value="/guardarRecepcion", method=RequestMethod.POST)
	public @ResponseBody RecepcionSeguimientoVO guardaRecepcionSeguimiento (@RequestBody RecepcionSeguimientoVO voRecepcion, HttpServletResponse response,HttpServletRequest request) {
		
		logger.info("/**** Guardar Recepcion ::  ****");
		
		
		UserSession user = getUsuarioFirmado(request);
		CorreccionSeguimientoGenericoVO correccionSeguimientoGenericoVO = new CorreccionSeguimientoGenericoVO();
		
		
		voRecepcion.setCveUsuario(user.getCurpUsuario().toString());
		voRecepcion.setFechaRegistro(Functions.stringToDate(ConstantesBusiness.dateToStringFormat(new Date(), ConstantesBusiness.dd_mm_yyyy)));
		
		
		
		
		correccionSeguimientoGenericoVO.setRecepcionVO(voRecepcion);
		correccionSeguimientoGenericoVO.setUser(user);
		correccionSeguimientoGenericoVO.getRecepcionVO().setConsolidaImporteVo(reemplazaComas(voRecepcion.getConsolidaImporteVo()));
		
		correccionSeguimientoGenericoVO =  seguimientoCorreccionServiceCtr.generaRecepcion(correccionSeguimientoGenericoVO);
		
		return correccionSeguimientoGenericoVO.getRecepcionVO();
	}
	
	
	 /**
	 * Metodo que elimina las comas de los campos de cantidades de los pagos , de la pestaña de Regularizar obra, 
	 * antes de realizar los calculos, las comas llegan por el formato de cantidades (miles) manejados en pantalla
	 * 
	 * @param PromocionSeguimientoGenericoVO
	 * @author Oscar Beltran Ortega
	 * @return void
	 * @version 1.0.0
	 */
	private SegRegularizarObraGenericoTabVO reemplazaComas(SegRegularizarObraGenericoTabVO consolidaImpteVO){
		//SegRegularizarObraGenericoTabVO consolidaImpteVO = genericoVO.getRecepcionVO().getConsolidaImporteVo();
		
		if(consolidaImpteVO != null) {
			if(consolidaImpteVO. getTrabRevisados()!=null && !("").equals(consolidaImpteVO.getTrabRevisados() )){
				consolidaImpteVO.setTrabRevisados((consolidaImpteVO.getTrabRevisados()).replace(",","" ).trim());
			}
			if(consolidaImpteVO.getTrabOmisos()!=null && !("").equals(consolidaImpteVO.getTrabOmisos() )){
				consolidaImpteVO.setTrabOmisos((consolidaImpteVO.getTrabOmisos()).replace(",","" ).trim());
			}
			if(consolidaImpteVO.getTrabSubdeclarados()!=null && !("").equals(consolidaImpteVO.getTrabSubdeclarados() )){
				consolidaImpteVO.setTrabSubdeclarados((consolidaImpteVO.getTrabSubdeclarados()).replace(",","" ).trim());
			}	
			
			if(consolidaImpteVO.getTrabRegularizados()!=null && !("").equals(consolidaImpteVO.getTrabRegularizados() )){
				consolidaImpteVO.setTrabRegularizados((consolidaImpteVO.getTrabRegularizados()).replace(",","" ).trim());
				
			}
			
			
			if(consolidaImpteVO.getSuertePpalDetCOP()!=null && !("").equals(consolidaImpteVO.getSuertePpalDetCOP() )){
				consolidaImpteVO.setSuertePpalDetCOP((consolidaImpteVO.getSuertePpalDetCOP()).replace(",","" ).trim());
			}
			if(consolidaImpteVO.getSuertePpalDetRCV()!=null && !("").equals(consolidaImpteVO.getSuertePpalDetRCV() )){
				consolidaImpteVO.setSuertePpalDetRCV((consolidaImpteVO.getSuertePpalDetRCV()).replace(",","" ).trim());
			}
		}
//		genericoVO.getRecepcionVO().setConsolidaImporteVo(consolidaImpteVO);
		return consolidaImpteVO;
		
	}
	
	// Crea un HashTable de domicilios
	@RequestMapping(value="derivSubdel/obtenerDomGeografico" , method=RequestMethod.GET)
	public String altacallDomGeograficos(HttpServletResponse response, HttpServletRequest request, Model model) {
		DgDomicilioGeografico dg = new DgDomicilioGeografico();		
		dg.setBloquearEstado(false);
		Hashtable doms = (Hashtable)getDomicilioInegiSession(request);		
		if(doms!=null){
			DgDomicilioGeografico tmp = (DgDomicilioGeografico)doms.get("CORR_DOM_DERIVAR_SUBD");
			if(tmp != null){
				dg=tmp;
			}
		}
		dg.setHastableKeyDG("CORR_DOM_DERIVAR_SUBD");
		return new DomGeograficosController().getCreateGenericForm(model,dg,request);
	}	
	
	// Metodo puente para recuperar los valores
	@RequestMapping(value = "derivSubdel/actualizaDom", method = RequestMethod.POST)
	public @ResponseBody DgDomicilioGeografico actualizaDomObra(@RequestBody CrtSolicitudcorr contenedor,HttpServletResponse response, HttpServletRequest request) {
		Hashtable domGeografico = (Hashtable)getDomicilioInegiSession(request);
		if(domGeografico!=null&&domGeografico.size()>0)
		{	 
			return (DgDomicilioGeografico)((Hashtable)getDomicilioInegiSession(request)).get(contenedor.getPatron());
		}
		return null;
	}
	
	@RequestMapping(value="derivSubdel/sessionDomicilioGeografico", method=RequestMethod.POST )
	public @ResponseBody DgDomicilioGeografico almacenaSessionDomicilioInegi(@RequestBody DgDomicilioGeografico domicilioInegi,HttpServletRequest request) {
		return new DomGeograficosController().almacenaSessionDomicilioInegi(domicilioInegi, request);
	}
	
	@RequestMapping(value="derivSubdel/removerDomicilioSession", method=RequestMethod.POST )
	public @ResponseBody DgDomicilioGeografico removerDomicilio(@RequestBody DgDomicilioGeografico domicilio,HttpServletRequest request) {		
		removeDomicilioInegiSession(request);			
		return domicilio;
	}
	
	
	/**
	 * Metodo que se encarga de buscar los datos de Oficio de Resultados
	 * @param CrtRevOficios
	 * @return CrtRevOficios

	 * @since  13/08/2012
	 * @version 1.0.0
	 */	
	@RequestMapping(value="/buscaOfResultados" , method=RequestMethod.POST)
	public @ResponseBody CrtRevOficios buscaOfResultados(@RequestBody CrtRevOficios model, HttpServletRequest request) {
		
		Long cveSolCorr = model.getCveSolCorr();
		CrtPresentacorr presentaCorr = new CrtPresentacorr();
		if(cveSolCorr != null){
			model.setId_TipoOficio(oficioResultados);
			model = this.seguimientoCorreccionServiceCtr.consultaRevOficiosPorCveSolCorrOR(model);
		}
		if(model != null){
			if(model.getFecFechaEmiOf() != null){
				model.setFechaEmision(Functions.dateToString(model.getFecFechaEmiOf()));
			}
			if(model.getFecFechaNotOf() != null){
				model.setFechaNotificacion(Functions.dateToString(model.getFecFechaNotOf()));
			}
			if(model.getFecFechaAtencionOf() != null){
				model.setFechaAtencion(Functions.dateToString(model.getFecFechaAtencionOf()));
			}
		}else{
			model = new CrtRevOficios();
			model.setCveSolCorr(cveSolCorr);
			presentaCorr.setCveSolicitudcorr(cveSolCorr.intValue());
			presentaCorr = this.seguimientoCorreccionServiceCtr.consultaCrtPresentacorrPorClave(presentaCorr);
			if(presentaCorr != null){
				model.setCvePresentaCorr(presentaCorr.getCvePresentacorr());
			}
		}
		
		
		return model;	
	}
	
	
	/**
	 * Metodo que se encarga de actualizar los datos de Oficio de Resultados
	 * @param ReqDocumentacionSeguimientoCorreccionVO
	 * @return ReqDocumentacionSeguimientoCorreccionVO

	 * @since  13/08/2012
	 * @version 1.0.0
	 */	
	@RequestMapping(value="/guardarOfResultados" , method=RequestMethod.POST)
	public @ResponseBody RevOficiosVO guardarOfResultados(@RequestBody RevOficiosVO model, HttpServletRequest request) {
		CrtRevOficios revOficios = new CrtRevOficios();
		UserSession usuario = getUsuarioFirmado(request);
		
		if(model.getCveSolCorr() != null){			
			revOficios.setCveSolCorr(model.getCveSolCorr());
			revOficios.setId_TipoOficio(oficioResultados);
			revOficios = this.seguimientoCorreccionServiceCtr.consultaRevOficiosPorCveSolCorr(revOficios);
		}
		if(revOficios == null){
			revOficios = new CrtRevOficios();
			revOficios.setCveSolCorr(model.getCveSolCorr());
			revOficios.setCvePresentaCorr(model.getCvePresentaCorr());
		}
		if(model.getNumFolioOficio() != null && !model.getNumFolioOficio().equals("")){
			revOficios.setNumFolioOficio(model.getNumFolioOficio());
		}
		if(model.getFecFechaNotOf() != null && !model.getFecFechaNotOf().equals("")){
			revOficios.setFecFechaNotOf(Functions.stringToDate(model.getFecFechaNotOf()));
		}
		if(model.getFecFechaEmiOf() != null && !model.getFecFechaEmiOf().equals("")){
			revOficios.setFecFechaEmiOf(Functions.stringToDate(model.getFecFechaEmiOf()));
		}
		if(model.getFecFechaAtencionOf() != null && !model.getFecFechaAtencionOf().equals("")){
			revOficios.setFecFechaAtencionOf(Functions.stringToDate(model.getFecFechaAtencionOf()));
		}

		revOficios.setFecFechaReg(new Date());
		revOficios.setId_TipoOficio(oficioResultados);
		revOficios.setCveUsuario(usuario.getCurpUsuario().toString());
		
		revOficios = this.seguimientoCorreccionServiceCtr.guardaReqDoc(revOficios);
		seguimientoCorreccionServiceCtr.guardaEstatusRecepcion(model.getCvePresentaCorr(), CatEstatus.NOTIFICACION_OFICIO_RESULTADOS.getId().intValue());
		model.setCveRevOficios(revOficios.getCveRevOficios());
		
		return model;	
	}
	
	
	@RequestMapping(value="/getRecepcion" , method=RequestMethod.POST)
	public @ResponseBody CrtRevRecepcion obtenerRecepcion(@RequestBody RevOficiosVO model, HttpServletRequest request) {
		
		return seguimientoCorreccionServiceCtr.consultaRecepcion(model.getCvePresentaCorr(), ConstantesBusiness.TIPO_PAGO_RECEPCION_AUTODETERMINACION);
		}
	
	
	
	@RequestMapping(value="derivSubdel/registrarDerivacionSubdel", method=RequestMethod.POST )
	public @ResponseBody CrtRevDerivASubd registrarDerivacionSubdel(@RequestBody CorreccionSeguimientoGenericoVO corrSegGenVO,HttpServletRequest request) {
		
		DevSubDelegacionSeguimientoCorreccionVO derivSubDelTabVO = corrSegGenVO.getDerivSubDelTabVO();
		UserSession user = getUsuarioFirmado(request);
		
		CrtRevDerivASubd derivacion=null;
		if(user.getIdSubDelegacion().intValue()==derivSubDelTabVO.getIdNuevaSubdelegacion()){		
			derivacion=new CrtRevDerivASubd();
			derivacion.setError("No se puede derivar a la misma subdelegacion");
			return derivacion;
		}		
		
		Hashtable<String,Object> hDomicilios = (Hashtable)getDomicilioInegiSession(request);
		DgDomicilioGeografico domicilioGeo = (DgDomicilioGeografico)hDomicilios.get("CORR_DOM_DERIVAR_SUBD");
		domicilioGeo.toString();
		
		domicilioGeo = this.domiciliosInegiServiceBean.agregar(domicilioGeo);
		logger.debug("idDomicilioGeo: " + domicilioGeo.getDomicilioId());
		derivacion = seguimientoCorreccionServiceCtr.registraCrtRevDerivASubd(derivSubDelTabVO, domicilioGeo, user);
		derivacion.setSubdelegDestino(null);
		derivacion.setSubdelegOrigen(null);
		seguimientoCorreccionServiceCtr.guardaEstatusRecepcion(corrSegGenVO.getCvePresentaCorr(), CatEstatus.CORRECCION_DERIVADA_OTRA_SUBDELEGACION.getId().intValue());
		
		request.getSession().removeAttribute("CORR_DOM_DERIVAR_SUBD");
		derivacion.setExito("La informacion ha sido guardada");
		removeDomicilioInegiSession(request);
		return derivacion;
	}
	
	
	@RequestMapping(value="derivSubdel/buscarDerivacionSubdel", method=RequestMethod.POST )
	public @ResponseBody CrtRevDerivASubd buscarDerivacionSubdel(@RequestBody CorreccionSeguimientoGenericoVO corrSegGenVO,HttpServletRequest request) {
		System.out.println("La clave de sol Corr es "+corrSegGenVO.getCveSolCorr());
		
		CrtRevDerivASubd derivacion = seguimientoCorreccionServiceCtr.consultaCrtRevDerivASubdCveSolCorr(corrSegGenVO.getCveSolCorr());
		return derivacion;
	}
	
	@RequestMapping(value="derivdictamen/registrarDerivacionDictamen", method=RequestMethod.POST )
	public @ResponseBody RevOficiosVO registrarDerivacionDictamen(@RequestBody RevOficiosVO model,HttpServletRequest request) {
		
		UserSession user = getUsuarioFirmado(request);

		
		CrtRevOficios revDerivDictamen = new CrtRevOficios();
		
		if(model.getCveSolCorr() != null){			
			CrtPresentacorr presentaCorr = new CrtPresentacorr();
			presentaCorr.setCveSolicitudcorr(model.getCveSolCorr().intValue());
			presentaCorr = this.seguimientoCorreccionServiceCtr.consultaCrtPresentacorrPorClave(presentaCorr);
			if(presentaCorr != null){
				revDerivDictamen.setCvePresentaCorr(presentaCorr.getCvePresentacorr());
			}
		}
		revDerivDictamen.setCveSolCorr(model.getCveSolCorr());
		revDerivDictamen.setNumFolioOficio(model.getNumFolioOficio());
		if(model.getFecFechaNotOf() != null && !model.getFecFechaNotOf().equals("")){
			revDerivDictamen.setFecFechaNotOf(Functions.stringToDate(model.getFecFechaNotOf()));
		}
		if(model.getFecFechaEmiOf() != null && !model.getFecFechaEmiOf().equals("")){
			revDerivDictamen.setFecFechaEmiOf(Functions.stringToDate(model.getFecFechaEmiOf()));
		}
		if(model.getFecFechaAtencionOf() != null && !model.getFecFechaAtencionOf().equals("")){
			revDerivDictamen.setFecFechaAtencionOf(Functions.stringToDate(model.getFecFechaAtencionOf()));
		}
		if(model.getEjercicio() != null){
			revDerivDictamen.setEjercicio(model.getEjercicio());
		}
		revDerivDictamen.setTxObservaciones(model.getTxObservaciones());
		revDerivDictamen.setFecFechaReg(new Date());
		revDerivDictamen.setId_TipoOficio(DERIVACION_DICTAMEN);
		revDerivDictamen.setCveUsuario(user.getCurpUsuario().toString());
		
		revDerivDictamen = this.seguimientoCorreccionServiceCtr.guardaReqDoc(revDerivDictamen);
		seguimientoCorreccionServiceCtr.guardaEstatusRecepcion(model.getCvePresentaCorr(), CatEstatus.CORRECCION_DERIVADA_DICTAMEN.getId().intValue());
		
		model.setExito("La informacion ha sido guardada");
		return model;
	}
	
	@RequestMapping(value="derivdictamen/buscarDerivacionDictamen", method=RequestMethod.POST )
	public @ResponseBody CrtRevOficios buscarDerivacionDictamen(@RequestBody RevOficiosVO model,HttpServletRequest request) {
		System.out.println("dereivacion a dictamen busqeda dictamen "+model.getCvePresentaCorr());
		UserSession user = getUsuarioFirmado(request);

		
		CrtRevOficios revDerivDictamen = new CrtRevOficios();	
		revDerivDictamen.setId_TipoOficio(DERIVACION_DICTAMEN);
		revDerivDictamen.setCveUsuario(user.getCurpUsuario().toString());
		revDerivDictamen.setCvePresentaCorr(model.getCvePresentaCorr());
		revDerivDictamen = this.seguimientoCorreccionServiceCtr.consultaRevOficiosPorClave(revDerivDictamen);
		if(revDerivDictamen!= null && revDerivDictamen.getFecFechaEmiOf() != null){
			revDerivDictamen.setFechaEmision(Functions.dateToString2(revDerivDictamen.getFecFechaEmiOf()));
		}

		return revDerivDictamen;
	}
	
	
	@RequestMapping(value="derivfiscal/registrarDerivacionFiscalizacion", method=RequestMethod.POST )
	public @ResponseBody DerivacionFiscalSeguimientoCorrVO guardarDerivacionFiscalizacion(@RequestBody DerivacionFiscalSeguimientoCorrVO model,HttpServletRequest request) {
		
		UserSession user = getUsuarioFirmado(request);
		CrtRevDerivAFisca derivacion = new CrtRevDerivAFisca();
		derivacion.setCveSolicitudCorr(model.getCveSolCorr());
		derivacion.setCveUsuario(user.getCurpUsuario().toString());
		derivacion.setFecFechaReg(new Date());
		derivacion.setFechaDeriva(Functions.stringToDate(model.getFechaDeriva()));		
		derivacion.setNomUsuarioDeriva(user.getNombreCompleto());
		derivacion.setNumFolioOficio(model.getNumFolioOficio());
		derivacion = this.seguimientoCorreccionServiceCtr.guardaDerivAFisca(derivacion);
		seguimientoCorreccionServiceCtr.guardaEstatusRecepcion(model.getCvePresentaCorr(), CatEstatus.CORRECCION_DERIVADA_FISCALIZACION.getId().intValue());
		model.setExito("La informacion ha sido guardada");
		model.setCveRevDerivAFisca(derivacion.getCveRevDerivAFis());
		return model;
	}
	
	@RequestMapping(value="derivfiscal/registrarReactivacion", method=RequestMethod.POST )
	public @ResponseBody DerivacionFiscalSeguimientoCorrVO guardarReactivacion(@RequestBody DerivacionFiscalSeguimientoCorrVO model,HttpServletRequest request) {
		
		UserSession user = getUsuarioFirmado(request);
		CrtRevDerivAFisca derivacion = new CrtRevDerivAFisca();
		if(model.getCveSolCorr() == null){
			model.setError("Falta la clave de solicitud de corrección");
			return model;
		}
		if(model.getCveRevDerivAFisca() == null){		
			model.setError("Falta la clave de derivación");
			return model;
		}
		
		derivacion =  seguimientoCorreccionServiceCtr.consultaDerivAFisca(model.getCveRevDerivAFisca());
		if(derivacion!=null){
			if(!derivacion.getCveSolicitudCorr().equals(model.getCveSolCorr())){
				model.setError("El registro de derivación no coincide con la solicitud");
				return model;
			}
			derivacion.setNomUserReactiva(user.getNombreCompleto());
			derivacion = this.seguimientoCorreccionServiceCtr.guardaDerivAFisca(derivacion);
			model.setExito("La información ha sido guardada");
			return model;
		}else{
			model.setError("No se encuentra el registro de derivación a fiscalización");
			return model;
		}



	}
		
	@RequestMapping(value="derivfiscal/actualizaDerivacionFiscalizacion", method=RequestMethod.POST )
	public @ResponseBody DerivacionFiscalSeguimientoCorrVO actualizaDerivacionFiscalizacion(@RequestBody DerivacionFiscalSeguimientoCorrVO model,HttpServletRequest request) {
		
		CrtRevDerivAFisca derivacion = new CrtRevDerivAFisca();
		if(model.getCveRevDerivAFisca() != null){			
			derivacion =  seguimientoCorreccionServiceCtr.consultaDerivAFisca(model.getCveRevDerivAFisca());
			if(derivacion!=null){
				model.setCveRevDerivAFisca(derivacion.getCveRevDerivAFis());

				if(model.getFechaEnvioSol()!=null && !model.getFechaEnvioSol().isEmpty()){
				    derivacion.setFechaEnvioSol(Functions.stringToDate(model.getFechaEnvioSol()));
				}
				if(model.getFechaReactiva()!=null && !model.getFechaReactiva().isEmpty()){
				    derivacion.setFechaReactiva(Functions.stringToDate(model.getFechaReactiva()));
				}
				
				if(model.getFechaSolReactiva()!=null && !model.getFechaSolReactiva().isEmpty()){
				    derivacion.setFechaSolReactiva(Functions.stringToDate(model.getFechaSolReactiva()));
				}
				if(model.getNumOficioReactiva()!=null && !model.getNumOficioReactiva().isEmpty()){
					derivacion.setNumOficioReactiva(model.getNumOficioReactiva());					
				}
				if(model.getNumOficioEnvio()!=null && !model.getNumOficioEnvio().isEmpty()){
					derivacion.setNumOficioEnvio(model.getNumOficioEnvio());					
				}
				if(model.getTxObservaciones()!=null && !model.getTxObservaciones().isEmpty()){
					derivacion.setTxObservaciones(model.getTxObservaciones());					
				}
				
				derivacion = this.seguimientoCorreccionServiceCtr.guardaDerivAFisca(derivacion);
				model.setExito("La información ha sido guardada");
				return model;
			}else{
				model.setError("No se encuentra el registro de derivación a fiscalización");
				return model;
			}
		}else{
			model.setError("Falta la clave de derivación");
			return model;
		}
		
	}
	
	@RequestMapping(value="derivfiscal/buscaDerivacionFiscalizacion", method=RequestMethod.POST )
	public @ResponseBody DerivacionFiscalSeguimientoCorrVO buscaDerivacionFiscalizacion(@RequestBody DerivacionFiscalSeguimientoCorrVO model,HttpServletRequest request) {
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
		if(model.getCveSolCorr() != null){
			CrtRevDerivAFisca derivacion = seguimientoCorreccionServiceCtr.buscaDerivAFiscaPorSolicitud(model.getCveSolCorr());
			if(derivacion!=null){
				model.setCveRevDerivAFisca(derivacion.getCveRevDerivAFis());
				model.setFechaDeriva(sdf.format(derivacion.getFechaDeriva()));
				if(derivacion.getFechaEnvioSol()!=null){
				   model.setFechaEnvioSol(sdf.format(derivacion.getFechaEnvioSol()));
				}
				if(derivacion.getFechaReactiva()!=null){
				   model.setFechaReactiva(sdf.format(derivacion.getFechaReactiva()));
				}
				if(derivacion.getFechaSolReactiva()!=null){
				   model.setFechaSolReactiva(sdf.format(derivacion.getFechaSolReactiva()));
				}
				model.setTxObservaciones(derivacion.getTxObservaciones());
				model.setNumFolioOficio(derivacion.getNumFolioOficio());
				model.setNumOficioEnvio(derivacion.getNumOficioEnvio());
				model.setNumOficioReactiva(derivacion.getNumOficioReactiva());
			}
		}					
		return model;
	}
	
		/**
	 * Metodo que se encarga de actualizar los datos de Coclusion
	 * @param revOficiosVO
	 * @return revOficiosVO
	 * @author Enrique Duran Jimenez
	 * @since  14/08/2012
	 * @version 1.0.0
	 */	
	@RequestMapping(value="/guardarConclusion" , method=RequestMethod.POST)
	public @ResponseBody RevOficiosVO guardarConclusion(@RequestBody RevOficiosVO model, HttpServletRequest request) {
		CrtRevOficios revOficios = new CrtRevOficios();
		UserSession usuario = getUsuarioFirmado(request);
		
		if(model.getCveSolCorr() != null){			
			revOficios.setCveSolCorr(model.getCveSolCorr());
			revOficios.setId_TipoOficio(conclusion);
			revOficios = this.seguimientoCorreccionServiceCtr.consultaRevOficiosPorCveSolCorr(revOficios);
		}
		if(revOficios == null){
			revOficios = new CrtRevOficios();
			revOficios.setCveSolCorr(model.getCveSolCorr());
			revOficios.setCvePresentaCorr(model.getCvePresentaCorr());
		}
		if(model.getNumFolioOficio() != null && !model.getNumFolioOficio().equals("")){
			revOficios.setNumFolioOficio(model.getNumFolioOficio());
		}
		if(model.getFecFechaNotOf() != null && !model.getFecFechaNotOf().equals("")){
			revOficios.setFecFechaNotOf(Functions.stringToDate(model.getFecFechaNotOf()));
		}
		if(model.getFecFechaEmiOf() != null && !model.getFecFechaEmiOf().equals("")){
			revOficios.setFecFechaEmiOf(Functions.stringToDate(model.getFecFechaEmiOf()));
		}

		revOficios.setFecFechaReg(new Date());
		revOficios.setId_TipoOficio(conclusion);
		revOficios.setCveUsuario(usuario.getCurpUsuario().toString());
		
		revOficios = this.seguimientoCorreccionServiceCtr.guardaReqDoc(revOficios);
		model.setCveRevOficios(revOficios.getCveRevOficios());
		
		return model;	
	}
	
	/**
	 * Metodo que se encarga de buscar los datos de Conclusion
	 * @param CrtRevOficios
	 * @return CrtRevOficios
	 * @author Enrique Duran Jimenez
	 * @since  14/08/2012
	 * @version 1.0.0
	 */	
	@RequestMapping(value="/buscaConclusion" , method=RequestMethod.POST)
	public @ResponseBody CrtRevOficios buscaConclusion(@RequestBody CrtRevOficios model, HttpServletRequest request) {
		
		Long cveSolCorr = model.getCveSolCorr();
		CrtPresentacorr presentaCorr = new CrtPresentacorr();
		if(cveSolCorr != null){
			model.setId_TipoOficio(conclusion);
			model = this.seguimientoCorreccionServiceCtr.consultaRevOficiosPorCveSolCorr(model);
		}
		if(model != null){
			if(model.getFecFechaEmiOf() != null){
				model.setFechaEmision(Functions.dateToString(model.getFecFechaEmiOf()));
			}
			if(model.getFecFechaNotOf() != null){
				model.setFechaNotificacion(Functions.dateToString(model.getFecFechaNotOf()));
			}
		}else{
			model = new CrtRevOficios();
			model.setCveSolCorr(cveSolCorr);
			presentaCorr.setCveSolicitudcorr(cveSolCorr.intValue());
			presentaCorr = this.seguimientoCorreccionServiceCtr.consultaCrtPresentacorrPorClave(presentaCorr);
			if(presentaCorr != null){
				model.setCvePresentaCorr(presentaCorr.getCvePresentacorr());
			}
		}
		
		
		return model;	
	}

/**
	 * Metodo que se encarga de buscar los datos de Cancelacion
	 * @param CrtRevOficios
	 * @return CrtRevOficios
	 * @author Enrique Duran Jimenez
	 * @since  15/08/2012
	 * @version 1.0.0
	 */	
	@RequestMapping(value="/buscaCancelacion" , method=RequestMethod.POST)
	public @ResponseBody RevOficiosVO buscaCancelacion(@RequestBody RevOficiosVO model, HttpServletRequest request) {
		
		UserSession usuario = getUsuarioFirmado(request);
		CrtPresentacorr presentaCorr = new CrtPresentacorr();
		CrtRevOficios revOficios = new CrtRevOficios();
		Long cveSolCorr = model.getCveSolCorr();
		if(cveSolCorr != null){
			revOficios.setId_TipoOficio(cancelacion);
			revOficios.setCveSolCorr(cveSolCorr);
			revOficios = this.seguimientoCorreccionServiceCtr.consultaRevOficiosPorCveSolCorr(revOficios);
		}
		if(revOficios != null){
			model.setNumFolioOficio(revOficios.getNumFolioOficio());
			if(revOficios.getIdMotivoCancelacion() != null){
				model.setIdMotivoCancelacion(revOficios.getIdMotivoCancelacion());
			}
			if(revOficios.getFecFechaEmiOf() != null){
				model.setFecFechaEmiOf(Functions.dateToString(revOficios.getFecFechaEmiOf()));
			}
		}else{
			revOficios = new CrtRevOficios();
			model.setCveSolCorr(cveSolCorr);
			presentaCorr.setCveSolicitudcorr(cveSolCorr.intValue());
			presentaCorr = this.seguimientoCorreccionServiceCtr.consultaCrtPresentacorrPorClave(presentaCorr);
			if(presentaCorr != null){
				model.setCvePresentaCorr(presentaCorr.getCvePresentacorr());
			}
		}		
		
		model.setMotivosCancelacion(this.cboMotivosCancelacion());
		
		model.setFuncionarioRegistra(usuario.getNombreCompleto());
		model.setFuncionarioAutoriza(usuario.getNombreCompleto());
		
		return model;	
	}
	
	/**
	 * Metodo que se encarga de actualizar los datos de Coclusion
	 * @param revOficiosVO
	 * @return revOficiosVO
	 * @author Enrique Duran Jimenez
	 * @since  14/08/2012
	 * @version 1.0.0
	 */	
	@RequestMapping(value="/guardarCancelacion" , method=RequestMethod.POST)
	public @ResponseBody RevOficiosVO guardarCancelacion(@RequestBody RevOficiosVO model, HttpServletRequest request) {
		CrtRevOficios revOficios = new CrtRevOficios();
		UserSession usuario = getUsuarioFirmado(request);
		
		seguimientoCorreccionServiceCtr.guardaEstatusRecepcion(model.getCvePresentaCorr(), CatEstatus.FOLIO_CANCELADO.getId().intValue());
		if(model.getCveSolCorr() != null){			
			revOficios.setCveSolCorr(model.getCveSolCorr());
			revOficios.setId_TipoOficio(cancelacion);
			revOficios = this.seguimientoCorreccionServiceCtr.consultaRevOficiosPorCveSolCorr(revOficios);
			//seguimientoCorreccionServiceCtr.actualizaEstatusCorreccion(model.getCveSolCorr().intValue(), CatEstatus.FOLIO_CANCELADO.getId().intValue());
		}
		if(revOficios == null){
			revOficios = new CrtRevOficios();
			revOficios.setCveSolCorr(model.getCveSolCorr());
			revOficios.setCvePresentaCorr(model.getCvePresentaCorr());
		}
		revOficios.setNumFolioOficio(model.getNumFolioOficio());
		revOficios.setIdMotivoCancelacion(model.getIdMotivoCancelacion());
		if(model.getFecFechaEmiOf() != null && !model.getFecFechaEmiOf().equals("")){
			revOficios.setFecFechaEmiOf(Functions.stringToDate(model.getFecFechaEmiOf()));
		}

		revOficios.setFecFechaReg(new Date());
		revOficios.setId_TipoOficio(cancelacion);
		revOficios.setCveUsuario(usuario.getCurpUsuario().toString());
		
		revOficios = this.seguimientoCorreccionServiceCtr.guardaReqDoc(revOficios);
		model.setCveRevOficios(revOficios.getCveRevOficios());
		
		return model;	
	}
	
	@RequestMapping(value="/consultaRecepcion", method=RequestMethod.POST)
	public @ResponseBody CrtRevRecepcion consultaRecepcionSeguimiento (@RequestBody RecepcionSeguimientoVO voRecepcion, HttpServletResponse response,HttpServletRequest request) {
		//CorreccionSeguimientoGenericoVO correccionSeguimientoGenericoVO = new CorreccionSeguimientoGenericoVO();
		CrtRevRecepcion crtRevRecepcion = null;
		UserSession user = getUsuarioFirmado(request);		
		String val=String.valueOf(user.getCveRol());
		if(voRecepcion.getIdPresentaCorreccion() != null){			
			crtRevRecepcion =  seguimientoCorreccionServiceCtr.consultaRecepcion(voRecepcion.getIdPresentaCorreccion(),ConstantesBusiness.TIPO_PAGO_RECEPCION_AUTODETERMINACION);
			if(crtRevRecepcion!=null){
				crtRevRecepcion.setRolUsuario(val);
			}
		}
		
				
		return crtRevRecepcion;
	}


private List<CgcCatMotivoCancelacion> cboMotivosCancelacion() {		
		
		List<CgcCatMotivoCancelacion> lstResult = this.catalogoCriteriosServiceBean.consultaSQL("  select mc.ID_MOTIVOCANCELACION, mc.MOTIVOCANCELACION from CGC_CATMOTIVOCANCELACION mc order by mc.MOTIVOCANCELACION ");
				
		
		return lstResult;
	}


@RequestMapping(value="/consultaPagosSolCorr", method=RequestMethod.POST)
public @ResponseBody List<CrtRevPagos> consultaPagosSegSolCorr (@RequestBody CorreccionSeguimientoGenericoVO seguimientoGenericoVO, HttpServletResponse response,HttpServletRequest request) {
logger.debug("consultaPagosSolCorr");
	List<CrtRevPagos> listaPagos = null;

	if(seguimientoGenericoVO.getCvePresentaCorr() != null){

		CrtRevPagos crtRevPagos = new CrtRevPagos();
		crtRevPagos.setCvePresentacorr(seguimientoGenericoVO.getCvePresentaCorr());
		crtRevPagos.setIndTipopago(new  Integer(1));
		try {
			listaPagos =  pagosService.obtenerListaPagos(crtRevPagos);
			
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
			
	return listaPagos;
}


    /*Se agrega parámetro al getSumarizado NULL al final ya que no es promoción*/
	@RequestMapping(value="/obtenerTotalesPagosSolCorr", method=RequestMethod.POST)
	public @ResponseBody CrtRevPagos obtenerSumarizadoPagosSegSolCorr (@RequestBody CorreccionSeguimientoGenericoVO seguimientoGenericoVO, HttpServletResponse response,HttpServletRequest request) {
	
		CrtRevPagos pagoTotal = null;
	
		if(seguimientoGenericoVO.getNuFolio() != null){
			logger.info("bandera tipo pago" + seguimientoGenericoVO.getBanderaTipoPago());
			try {
				pagoTotal = pagosService.getSumarizado(seguimientoGenericoVO.getNuFolio(),seguimientoGenericoVO.getBanderaTipoPago(),null);	
			
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
				
		return pagoTotal;
	}


	public String consultaRolUsuario(Long idUsuario){
		String rol = null;
		String queryRol="select  CVE_ROL from seg_perfil_usuario WHERE CVE_ID_USUARIO = "+ idUsuario.toString();
		ArrayList listaFuncionarios = (ArrayList) iCatalogoServiceBean.consultaSQL(queryRol);	
		rol = listaFuncionarios.get(0).toString();
		
		return rol;
	}
	
	@RequestMapping(value="/guardarCedulaValidacionSeg", method=RequestMethod.POST)
	public @ResponseBody CedulaValidacionVO guardaCedulaValidacion (@RequestBody CedulaValidacionVO cedulaValidacionVO, HttpServletResponse response,HttpServletRequest request) {
		
		logger.info("/**** Guardar Cedula de validacion ::  ****");
		
		
		cedulaValidacionVO.setConsolidaImporteVo(reemplazaComas(cedulaValidacionVO.getConsolidaImporteVo()));
		cedulaValidacionVO =  seguimientoCorreccionServiceCtr.generaCedulaValidacion(cedulaValidacionVO);
		cedulaValidacionVO.setResultado("Actualizacion Completada");
		return cedulaValidacionVO;
	}
	
	
	@RequestMapping(value="/consultaEstatusCorreccion", method=RequestMethod.POST)
	public @ResponseBody int consultaEstatusCorreccion(@RequestBody String folio,HttpServletResponse response,HttpServletRequest request) {
		System.out.println("e lf  folio recibido es "+folio);
		return 0;
	}

	@RequestMapping(value="/consultaCedValidacion", method=RequestMethod.POST)
	public @ResponseBody CedulaValidacionVO consultaCedulaValidacion (@RequestBody CorreccionSeguimientoGenericoVO seguimientoGenericoVO, HttpServletResponse response,HttpServletRequest request) {
		logger.debug("consultaCedValConsolidado");
		CedulaValidacionVO cedulaValidacionVO = new CedulaValidacionVO();
		cedulaValidacionVO.setConsolidaImporteVo(new SegRegularizarObraGenericoTabVO());
		List<CedulaValidacionConsolidadoVO> listaConsolidados = null;
		List<CedulaValidacionConsolidadoVO> listPercepciones = new ArrayList<CedulaValidacionConsolidadoVO>();
		List<Long> ejercicios=null;
		logger.debug(seguimientoGenericoVO.getCvePresentaCorr());

		if (seguimientoGenericoVO != null
				&& seguimientoGenericoVO.getCvePresentaCorr() != null) {

			try {
				listaConsolidados = seguimientoCorreccionServiceCtr.consultaCedulaValidacionConsolidado(seguimientoGenericoVO);
				cedulaValidacionVO.setListaConsolidados(listaConsolidados);
				
				//consulta ejercicios
				ejercicios=seguimientoCorreccionServiceCtr.recuperaEjercicios(seguimientoGenericoVO.getCveSolCorr());
				cedulaValidacionVO.setEjercicios(ejercicios);
				cedulaValidacionVO.setListaPercepciones(listPercepciones);
				
				
				//consulta recepcion
				CrtRevRecepcion crtRevRecepcion = seguimientoCorreccionServiceCtr.consultaRecepcion(seguimientoGenericoVO.getCvePresentaCorr(), ConstantesBusiness.TIPO_PAGO_REVISION);
				cedulaValidacionVO = transformModelRecepcion(crtRevRecepcion, cedulaValidacionVO);
				 
			} catch (Exception e) {
				logger.error(e.getMessage());
				e.printStackTrace();
			}
		}
		return cedulaValidacionVO;
	}	
	
	
	
	@RequestMapping(value="/consultaDetalleCedValidacion", method=RequestMethod.POST)
	public @ResponseBody CedulaValidacionVO consultaDetalleCedulaValidacion (@RequestBody CorreccionSeguimientoGenericoVO seguimientoGenericoVO, HttpServletResponse response,HttpServletRequest request) {
		logger.debug("consultaCedValConsolidado");
		System.out.println("El ejerciocio es "+seguimientoGenericoVO.getCveEjercicio());
		System.out.println("Reg patro  "+seguimientoGenericoVO.getRegPatronal());
		CedulaValidacionVO cedulaValidacionVO = new CedulaValidacionVO();
		cedulaValidacionVO.setCveAnexoSolCorrPat(seguimientoGenericoVO.getCveAnexoSolCorr());
		cedulaValidacionVO.setCvePresentaCorr(seguimientoGenericoVO.getCvePresentaCorr());
		cedulaValidacionVO.setCveEjercicio(seguimientoGenericoVO.getCveEjercicio().intValue());
		
		cedulaValidacionVO = seguimientoCorreccionServiceCtr.recuperaDetalleCedulaValidacion(cedulaValidacionVO);

		return cedulaValidacionVO;
	}	
	
	
	
	@RequestMapping(value="/finalizaCedulaValidacion", method=RequestMethod.POST)
	public @ResponseBody CedulaValidacionVO finalizaCedulaValidacion (@RequestBody CedulaValidacionVO cedulaValidacionVO, HttpServletResponse response,HttpServletRequest request) {
		logger.debug("finalizaCedulaValidacion");
		logger.info("Guardando cedula validacion");
		cedulaValidacionVO.setConsolidaImporteVo(reemplazaComas(cedulaValidacionVO.getConsolidaImporteVo()));
		cedulaValidacionVO =  seguimientoCorreccionServiceCtr.generaCedulaValidacion(cedulaValidacionVO);
	
		CedulaValidacionVO vo=seguimientoCorreccionServiceCtr.finalizaCedulaValidacion(cedulaValidacionVO);
		return vo;
	}	
	
	
	@RequestMapping(value="/autorizaCedulaValidacion", method=RequestMethod.POST)
	public @ResponseBody CedulaValidacionVO autorizaCedulaValidacion (@RequestBody CedulaValidacionVO cedulaValidacionVO, HttpServletResponse response,HttpServletRequest request) {
		logger.debug("autorizaCedulaValidacion");
		
		CedulaValidacionVO vo=new CedulaValidacionVO();
		cedulaValidacionVO.setConsolidaImporteVo(reemplazaComas(cedulaValidacionVO.getConsolidaImporteVo()));
		cedulaValidacionVO =  seguimientoCorreccionServiceCtr.generaCedulaValidacion(cedulaValidacionVO);
		
		
		String res=null;
		res=seguimientoCorreccionServiceCtr.autorizaCedulaValidacion(cedulaValidacionVO);
		vo.setResultado(res);
		return vo;
	}	


	@RequestMapping(value="/rechazaCedulaValidacion", method=RequestMethod.POST)
	public @ResponseBody CedulaValidacionVO rechazaCedulaValidacion (@RequestBody CedulaValidacionVO cedulaValidacionVO, HttpServletResponse response,HttpServletRequest request) {
		logger.debug("autorizaCedulaValidacion");
		
		CorreccionSeguimientoGenericoVO correccionSeguimientoGenericoVO = new CorreccionSeguimientoGenericoVO();
		CedulaValidacionVO vo=new CedulaValidacionVO();
		cedulaValidacionVO.setConsolidaImporteVo(reemplazaComas(cedulaValidacionVO.getConsolidaImporteVo()));
		cedulaValidacionVO =  seguimientoCorreccionServiceCtr.generaCedulaValidacion(cedulaValidacionVO);
		
		String res=null;
		System.out.println("el nivel de ayutorizacion es "+cedulaValidacionVO.getSeccionAutoriza());
		res=seguimientoCorreccionServiceCtr.rechazaCedulaValidacion(cedulaValidacionVO);
		vo.setResultado(res);
		return vo;
	}	
	
	private CedulaValidacionVO transformModelRecepcion(CrtRevRecepcion crtRevRecepcion, CedulaValidacionVO cedulaValidacionVO){
		
		if(crtRevRecepcion==null){
			return cedulaValidacionVO;
		}
		
		SegRegularizarObraGenericoTabVO consolidacionVO = new SegRegularizarObraGenericoTabVO();
		consolidacionVO.setPorcAvance(crtRevRecepcion.getPorcentajeAvance()!=null ? crtRevRecepcion.getPorcentajeAvance().toString():"");
		consolidacionVO.setPorcRegularizado(crtRevRecepcion.getPorcentajeRegula()!=null ? crtRevRecepcion.getPorcentajeRegula().toString() :"");
		consolidacionVO.setNumParcialidades(String.valueOf(crtRevRecepcion.getNumParcialidades()));
		//consolidacionVO.setNumTrabRegularizados
		if(crtRevRecepcion.getComprobanteConvenio()==null || crtRevRecepcion.getComprobanteConvenio()== 0){
			cedulaValidacionVO.setComprobanteConvenio(false);
		}else{
			cedulaValidacionVO.setComprobanteConvenio(true);
		}
		
		
		consolidacionVO.setTrabRevisados(crtRevRecepcion.getNumTrabrevisados()!=null ?crtRevRecepcion.getNumTrabrevisados().toString():"");
		consolidacionVO.setTrabOmisos(crtRevRecepcion.getNumTrabomisos()!=null? crtRevRecepcion.getNumTrabomisos().toString():"");
		consolidacionVO.setTrabSubdeclarados(crtRevRecepcion.getNumTrabSubdeclarados()!=null ?crtRevRecepcion.getNumTrabSubdeclarados().toString():"");
		consolidacionVO.setTrabRegularizados(crtRevRecepcion.getNumRegularizados()!=null ? crtRevRecepcion.getNumRegularizados().toString():"");
		
		consolidacionVO.setSuertePpalDetCOP(crtRevRecepcion.getImpSPCopAutDet()!=null ?crtRevRecepcion.getImpSPCopAutDet().toString():"");
		consolidacionVO.setSuertePpalDetRCV(crtRevRecepcion.getImpSPRcvAutDet()!=null ?crtRevRecepcion.getImpSPRcvAutDet().toString():"");
		consolidacionVO.setFechaRegistroTxt(Functions.dateToString(crtRevRecepcion.getFecFechaReg()));
		
//		cedulaValidacionVO.setCveConsolidaImporte(Integer.valueOf(crtRevRecepcion.getCveRevRecepcion()+""));
		
		cedulaValidacionVO.setNumTrabRegularizados(crtRevRecepcion.getNumRegularizados()!=null ?crtRevRecepcion.getNumRegularizados().intValue():0);
		cedulaValidacionVO.setConsolidaImporteVo(consolidacionVO);
		cedulaValidacionVO.setIndValPrimera(String.valueOf(crtRevRecepcion.getIndAutorizaValPrimera()));
		cedulaValidacionVO.setIndValSegunda(String.valueOf(crtRevRecepcion.getIndAutorizaValSegunda()));
		cedulaValidacionVO.setCveRecepcion((int) crtRevRecepcion.getCveRevRecepcion());
		return cedulaValidacionVO;
			
	}
	@RequestMapping(value="/actualizaEstatusRecepcionCedVal", method=RequestMethod.POST)
	public @ResponseBody CrtRevRecepcion cambiaEstatuRevRecepcion (@RequestBody CrtRevRecepcion crtRevRecepcion, HttpServletResponse response,HttpServletRequest request) {
		
		logger.info("/**** actualiza estatus revRecepcion ::  ****");
		
		
		CrtRevRecepcion recepcionActualizar = seguimientoCorreccionServiceCtr.consultaRecepcion(crtRevRecepcion.getCvePresentacorr(), crtRevRecepcion.getIndTipoPago());
		seguimientoCorreccionServiceCtr.guardaEstatusRecepcion(crtRevRecepcion.getCvePresentacorr(),crtRevRecepcion.getCveStatus());
		
		return recepcionActualizar;
	}
	
	
	@RequestMapping(value = "/operacionCorreccion", method = RequestMethod.GET)	
	public String operacionCorreccion(HttpServletResponse response , HttpServletRequest request) {
	
			
		return "estudioCorreccion/operacionCorreccion";
		
	}
	
	
	@RequestMapping(value = "/getDelegaciones", method = RequestMethod.POST)	
	public @ResponseBody List<SacDelegacion> recuperaListaDelegaciones(HttpServletResponse response , HttpServletRequest request) {
	
		List<SacDelegacion> delegaciones=delegacionDAO.consultaLibrePorClave(0L, "FROM SacDelegacion");
			
		return delegaciones;
		
	}
	

	
	@RequestMapping(value = "/motivosCancelaccion", method = RequestMethod.POST)	
	public @ResponseBody List<CgcCatMotivoCancelacion> motivosCancelaccion(HttpServletResponse response , HttpServletRequest request) {
	
		List motivos = catalogoDAO.consultaLibrePorClave(0L, "FROM CgcCatMotivoCancelacion");
			
		return motivos;
		
	}
	
	
	@RequestMapping(value = "/getSubDelegaciones", method = RequestMethod.POST)	
	public @ResponseBody List<SacSubdelegacion> recuperaListaSubDelegaciones(@RequestBody SacDelegacion delegacion,HttpServletResponse response , HttpServletRequest request) {
		System.out.println("delegacion seleccionada  "+delegacion.getCvePk());
		List<SacSubdelegacion> subdelegaciones=subdelegacionDAO.consultaLibrePorClave(0L, "FROM SacSubdelegacion dele where dele.sacDelegacion.cvePk="+delegacion.getCvePk()+" order by dele.nomNombre ");
			
		return subdelegaciones;
		
	}
	
	
	@RequestMapping(value = "/recuperaEjerciciosDictaminar", method = RequestMethod.POST)	
	public @ResponseBody List<Long> recuperaEjerciciosDictaminar(@RequestBody CrtSolicitudcorr solicitud,HttpServletResponse response , HttpServletRequest request) {
		String query="select distinct ejer.cveEjercicio from CrcEjercicio ejer,CrtAnexosolcorrpat anexo,CrtSolicitudcorr sol where ejer.cveAcexoCorrPat=anexo.cveAnexoSolicitudCorrPat"
				+" and sol.cveSolicitudCorr=anexo.cveSolicitudCorr and sol.nuFolio='"+solicitud.getNuFolio()+"'";		
		 List lista=catalogoDAO.consultaLibrePorClave(0L, query);
		return lista;		
	}
	
	
	
	@RequestMapping(value = "/validaSubdelegacion", method = RequestMethod.POST)	
	public @ResponseBody boolean  validaSubdelegacion(@RequestBody SacSubdelegacion subdelegacion,HttpServletResponse response , HttpServletRequest request) {
	System.out.println("validandop subdelegacion");	
	    UserSession user = getUsuarioFirmado(request);
		user.getIdSubDelegacion();
		if(user.getIdSubDelegacion().intValue()!=subdelegacion.getCvePk().intValue()){
			return true;
		}else{
			return false;
		}
			
	}
	
	
	@RequestMapping(value = "/gestionOperacion", method = RequestMethod.POST)	
	public @ResponseBody OperacionCorreccionVO gestionOperacion(@RequestBody OperacionCorreccionVO operacionCorreccionVO,HttpServletResponse response , HttpServletRequest request) {
	
		OperacionCorreccionVO operacion=new OperacionCorreccionVO();
		UserSession user = getUsuarioFirmado(request);
		operacion.setUsuario(user.getNombreCompleto());
		List<CrtSolicitudcorr> solicitud=crtSolicitudCorr.consultaLibrePorClave(0L, "FROM CrtSolicitudcorr solo where solo.nuFolio='"+operacionCorreccionVO.getNumeroFolio()+"' and solo.cveSubdelegacion="+user.getIdSubDelegacion()+" and solo.cveStatus!="+CORRECCION_RECHAZADA);
		if(!solicitud.isEmpty()){
			operacion.setExiste(true);
			operacion.setSolicitud(solicitud.get(0));
			List presenta=catalogoDAO.consultaLibrePorClave(0L, "FROM CrtPresentacorr pre where pre.cveSolicitudcorr="+solicitud.get(0).getCveSolicitudCorr());
			if(!presenta.isEmpty()){
				operacion.setPresentada(true);
				operacion.setMensaje("El folio ya ha sido presentado");
				return operacion;
			}
			List derivaSubdele=catalogoDAO.consultaLibrePorClave(0L, "FROM CrtRevDerivASubd deri where deri.solicitudCorr.cveSolicitudCorr="+solicitud.get(0).getCveSolicitudCorr());
			List derivafisca=catalogoDAO.consultaLibrePorClave(0L, "FROM CrtRevDerivAFisca fisca where fisca.cveSolicitudCorr="+solicitud.get(0).getCveSolicitudCorr()+" order by fisca.cveRevDerivAFis");
			List derivaDictamen=catalogoDAO.consultaLibrePorClave(0L, "FROM CrtSolicitudOficios dicta where dicta.cveSolicitudCorr="+solicitud.get(0).getCveSolicitudCorr()+" and dicta.id_TipoOficio="+DERIVACION_DICTAMEN);
			List listacancelacion=catalogoDAO.consultaLibrePorClave(0L, "FROM CrtSolicitudOficios dicta where dicta.cveSolicitudCorr="+solicitud.get(0).getCveSolicitudCorr()+" and dicta.id_TipoOficio="+cancelacion);
			if(!derivaSubdele.isEmpty()){
				System.out.println("DerivaSubde "+derivaSubdele);
				operacion.setDerivaSubdelegacion((CrtRevDerivASubd) derivaSubdele.get(0));
			
				Domicilio domiBdtu=new Domicilio();
				CrtRevDerivASubd du=(CrtRevDerivASubd) derivaSubdele.get(0);
				domiBdtu.setClave(du.getDomicilioId().intValue());
				try {
					domiBdtu=domiciliosServiceBean.consultarDomicilio(domiBdtu);
				} catch (DomicilioNoLocalizadoException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			
				operacion.setDomicilioSubdelegacion(domiBdtu);
			}
			
			if(!derivafisca.isEmpty()){
				if(derivafisca.size()==1){
					//operacion
					CrtRevDerivAFisca derFisca1=(CrtRevDerivAFisca) derivafisca.get(0);
					if(derFisca1.getFechaReactiva()!=null){
						operacion.setPuedeReactivar(false);
					}else{
						operacion.setPuedeReactivar(true);
					}
					
				}else{
					operacion.setPuedeReactivar(false);
				}
				
				if(derivafisca.size()==2){
					operacion.setFinalizaProceso(true);
				}
				
				operacion.setDerivaFiscalizacion((CrtRevDerivAFisca) derivafisca.get(0));
			}
			
			
			if(!derivaDictamen.isEmpty()){
				operacion.setDerivacionDicatmen((CrtSolicitudOficios) derivaDictamen.get(0));
			}
			
			if(!listacancelacion.isEmpty()){
				operacion.setCancelacion((CrtSolicitudOficios) listacancelacion.get(0));
			}
		}else{
			operacion.setMensaje("No se encontro el folio, o no pertenece a la subdelegacion");
			operacion.setExiste(false);
		}
		
		
		
		return operacion;
		
	}
	
	@RequestMapping(value = "/procesaOperacion", method = RequestMethod.POST)	
	public @ResponseBody OperacionCorreccionVO procesaOperacion(@RequestBody OperacionCorreccionVO operacionCorreccionVO,HttpServletResponse response , HttpServletRequest request) {
	
		System.out.println("operacionCorreccionVO  "+operacionCorreccionVO.getTipoOperacion());
		DevSubDelegacionSeguimientoCorreccionVO derivSubDelTabVO=new DevSubDelegacionSeguimientoCorreccionVO();
		DgDomicilioGeografico dom=new DgDomicilioGeografico();
		UserSession user = getUsuarioFirmado(request);
		operacionCorreccionVO.setMensaje("");
		dom.setDomicilioId(Integer.parseInt(operacionCorreccionVO.getCveDomicilio()));
		List<CrtSolicitudcorr> solicitud=crtSolicitudCorr.consultaLibrePorClave(0L, "FROM CrtSolicitudcorr solo where solo.nuFolio='"+operacionCorreccionVO.getNumeroFolio()+"'");
		if(solicitud.isEmpty()){
			operacionCorreccionVO.setMensaje("El folio no existe");
			return operacionCorreccionVO;
		}
		
		derivSubDelTabVO.setCveSolicitud(solicitud.get(0).getCveSolicitudCorr());
		
		derivSubDelTabVO.setIdNuevaSubdelegacion(Long.parseLong(operacionCorreccionVO.getCveSubdelegacionDestino()));
		derivSubDelTabVO.setIdAnteriorSubdelegacion(user.getIdSubDelegacion().intValue());
		derivSubDelTabVO.setFolioDerivacion(operacionCorreccionVO.getFolioOficioDerivacion());
		derivSubDelTabVO.setFechaDerivacion(operacionCorreccionVO.getFechaDerivacionSubdelegacion());
		operacionCorreccionVO.setUsuario(user.getNombreCompleto());
		
		seguimientoCorreccionServiceCtr.registraCrtRevDerivASubd(derivSubDelTabVO, dom, user);
	//	seguimientoCorreccionServiceCtr.actualizaEstatusCorreccion(operacionCorreccionVO.getSolicitud().getCveSolicitudCorr(),  CatEstatus.CORRECCION_DERIVADA_DICTAMEN.getId().intValue());
		operacionCorreccionVO.setMensaje("Folio derivado a subdelegacion");
		return operacionCorreccionVO;
		
	}
	
	
	@RequestMapping(value = "/derivarDictamen", method = RequestMethod.POST)	
	public @ResponseBody OperacionCorreccionVO derivarDictamen(@RequestBody OperacionCorreccionVO operacionCorreccionVO,HttpServletResponse response , HttpServletRequest request) {
		System.out.println();
		UserSession user = getUsuarioFirmado(request);
		
		CrtSolicitudOficios revDerivDictamen=new CrtSolicitudOficios();
		revDerivDictamen.setCveSolicitudCorr(operacionCorreccionVO.getSolicitud().getCveSolicitudCorr());
		revDerivDictamen.setNumFolioOficio(operacionCorreccionVO.getNumerAviso());
		revDerivDictamen.setFecFechaEmiOf(Functions.stringToDate(operacionCorreccionVO.getFechaAutDeAvisoDictamen()));
		revDerivDictamen.setEjercicio(Long.parseLong(operacionCorreccionVO.getEjercicioDictaminar()));
		revDerivDictamen.setFecFechaReg(Calendar.getInstance().getTime());
		revDerivDictamen.setId_TipoOficio(DERIVACION_DICTAMEN);
		revDerivDictamen.setCveUsuario(user.getCurpUsuario().toString());
		
		solicitudOficiosDAO.actualizar(revDerivDictamen);		
		seguimientoCorreccionServiceCtr.actualizaEstatusCorreccion(operacionCorreccionVO.getSolicitud().getCveSolicitudCorr(),  CatEstatus.CORRECCION_DERIVADA_DICTAMEN.getId().intValue());
		operacionCorreccionVO.setMensaje("El folio se derivo a dictamen correctamente");
		return operacionCorreccionVO;
	}
	
	
	@RequestMapping(value = "/derivarFiscalizacion", method = RequestMethod.POST)	
	public @ResponseBody OperacionCorreccionVO derivarFiscalizacion(@RequestBody OperacionCorreccionVO operacionCorreccionVO,HttpServletResponse response , HttpServletRequest request) {
		System.out.println("Derivar Fiscalizacion");
		
		UserSession user = getUsuarioFirmado(request);
		List derivafisca=catalogoDAO.consultaLibrePorClave(0L, "FROM CrtRevDerivAFisca fisca where fisca.cveSolicitudCorr="+operacionCorreccionVO.getSolicitud().getCveSolicitudCorr());
				
		CrtRevDerivAFisca derivacion = new CrtRevDerivAFisca();
		if(derivafisca.size()==0){
			operacionCorreccionVO.setPuedeReactivar(true);
		}else{
			operacionCorreccionVO.setPuedeReactivar(false);
		}
		if(derivafisca.size()==1){
			operacionCorreccionVO.setFinalizaProceso(true);
		}
	    derivacion.setCveSolicitudCorr(operacionCorreccionVO.getSolicitud().getCveSolicitudCorr());
		derivacion.setCveUsuario(user.getCurpUsuario().toString());
		derivacion.setFecFechaReg(Calendar.getInstance().getTime());
		derivacion.setFechaDeriva(Functions.stringToDate(operacionCorreccionVO.getFechaDerivacionFiscaliozacion()));		
		if(user.getNombreCompleto().length()>50){
			derivacion.setNomUsuarioDeriva(user.getNombreCompleto().substring(0,50));
		}else{
			derivacion.setNomUsuarioDeriva(user.getNombreCompleto());
		}
		
		derivacion.setNumFolioOficio(operacionCorreccionVO.getFolioOficioFiscalizacion());
		derivacion = this.seguimientoCorreccionServiceCtr.guardaDerivAFisca(derivacion);
		operacionCorreccionVO.setDerivaFiscalizacion(derivacion);
		seguimientoCorreccionServiceCtr.actualizaEstatusCorreccion(operacionCorreccionVO.getSolicitud().getCveSolicitudCorr(), CatEstatus.CORRECCION_DERIVADA_FISCALIZACION.getId().intValue());
		operacionCorreccionVO.setMensaje("El folio se derivo a fiscalizacion ");
		return operacionCorreccionVO;
	}
	
	
	@RequestMapping(value = "/cancelacionFolio", method = RequestMethod.POST)	
	public @ResponseBody OperacionCorreccionVO cancelacionFolio(@RequestBody OperacionCorreccionVO operacionCorreccionVO,HttpServletResponse response , HttpServletRequest request) {
		System.out.println("Derivar Fiscalizacion");
		
		UserSession user = getUsuarioFirmado(request);
		
		CrtSolicitudOficios revDerivDictamen=new CrtSolicitudOficios();
		List listacancelacion=catalogoDAO.consultaLibrePorClave(0L, "FROM CrtSolicitudOficios dicta where dicta.cveSolicitudCorr="+operacionCorreccionVO.getSolicitud().getCveSolicitudCorr()+" and dicta.id_TipoOficio="+cancelacion);
		if(!listacancelacion.isEmpty()){
			revDerivDictamen=(CrtSolicitudOficios) listacancelacion.get(0);
		}
		revDerivDictamen.setCveSolicitudCorr(operacionCorreccionVO.getSolicitud().getCveSolicitudCorr());
		
		revDerivDictamen.setNumFolioOficio(operacionCorreccionVO.getReferenciaCancelacion());
		revDerivDictamen.setFecFechaEmiOf(Functions.stringToDate(operacionCorreccionVO.getFechaCancelacion()));
		revDerivDictamen.setIdMotivoCancelacion(Long.parseLong(operacionCorreccionVO.getMotivoCancelacion()));
		
		
		revDerivDictamen.setFecFechaReg(Calendar.getInstance().getTime());
		revDerivDictamen.setId_TipoOficio(cancelacion);		
		revDerivDictamen.setCveUsuario(user.getCurpUsuario().toString());
		
		solicitudOficiosDAO.actualizar(revDerivDictamen);		
		seguimientoCorreccionServiceCtr.actualizaEstatusCorreccion(operacionCorreccionVO.getSolicitud().getCveSolicitudCorr(),  CatEstatus.FOLIO_CANCELADO.getId().intValue());
		operacionCorreccionVO.setMensaje("El folio se cancelo correctamente");
		
		return operacionCorreccionVO;
	}
	
	
	@RequestMapping(value = "/reactivar", method = RequestMethod.POST)	
	public @ResponseBody OperacionCorreccionVO reactivar(@RequestBody OperacionCorreccionVO operacionCorreccionVO,HttpServletResponse response , HttpServletRequest request) {
		System.out.println("Derivar Fiscalizacion");
		UserSession user = getUsuarioFirmado(request);
		CrtRevDerivAFisca derivacion = null;
//		CrtRevDerivAFisca derivacion = this.seguimientoCorreccionServiceCtr.buscaDerivAFiscaPorSolicitud(operacionCorreccionVO.getSolicitud().getCveSolicitudCorr());
		
		List derivafisca=catalogoDAO.consultaLibrePorClave(0L, "FROM CrtRevDerivAFisca fisca where fisca.cveSolicitudCorr="+operacionCorreccionVO.getSolicitud().getCveSolicitudCorr()+" order by fisca.cveRevDerivAFis");
		if(derivafisca.size()==1){
			derivacion=(CrtRevDerivAFisca) derivafisca.get(0);
			operacionCorreccionVO.setDerivaFiscalizacion(derivacion);
//			if(derivacion.getFechaReactiva()!=null){
//				operacionCorreccionVO.setPuedeReactivar(true);
//			}
		}
		
		
		if(derivacion!=null){
			System.out.println("Se puede reactivar  ");
			if(user.getNombreCompleto().length()>50){
				derivacion.setNomUserReactiva(user.getNombreCompleto().substring(0, 50));	
			}else{
				derivacion.setNomUserReactiva(user.getNombreCompleto());	
			}
			
			derivacion.setFechaSolReactiva(Functions.stringToDate(operacionCorreccionVO.getFechaSolicitudReactivacion()));
			derivacion.setFechaEnvioSol(Functions.stringToDate(operacionCorreccionVO.getFechaEnvioSolicitudNormativo()));
			derivacion.setFechaReactiva(Functions.stringToDate(operacionCorreccionVO.getFechaReactivacion()));
			derivacion.setNumOficioEnvio(operacionCorreccionVO.getNumeroOficio());
			derivacion.setNumOficioReactiva(operacionCorreccionVO.getNumeroOficioReactivacion());
			derivacion.setTxObservaciones(operacionCorreccionVO.getObservaciones());
			this.seguimientoCorreccionServiceCtr.guardaDerivAFisca(derivacion);
			operacionCorreccionVO.setMensaje("El folio se reactivo correctamente");
		}else{
			operacionCorreccionVO.setMensaje("No se puede reactivar el folio");
			System.out.println("No se puede reactivar");
		}
		
		seguimientoCorreccionServiceCtr.actualizaEstatusCorreccion(operacionCorreccionVO.getSolicitud().getCveSolicitudCorr(),2);//Estado autorizado
		return operacionCorreccionVO;
	}
	
	
	public void actualizaEstatusCorreccion(Integer cveSolicitudCorr, Integer estatus){
		CrtSolicitudcorr crtSolicitudcorr = new CrtSolicitudcorr();
		crtSolicitudcorr.setCveSolicitudCorr(cveSolicitudCorr);
		CrtSolicitudcorr crtSolicitudcorrActualizar = crtSolicitudCorr.consultaPorClave(crtSolicitudcorr);		
		crtSolicitudcorrActualizar.setCveStatus(estatus);		
		crtSolicitudCorr.actualizar(crtSolicitudcorrActualizar);
		System.out.println("Se actualiza");
	}	
	
	
}
