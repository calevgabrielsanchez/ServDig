/**
 * SeguimientoPromocionGenericoController.java
 * @package mx.gob.imss.ctirss.correccion.web.controller.seguimiento.promocion.generico
 * @project correccion-web
 */
package mx.gob.imss.ctirss.correccion.web.controller.seguimiento.promocion.generico;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Hashtable;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mx.gob.imss.ctirss.correccion.base.paginador.model.DatosEntradaPaginador;
import mx.gob.imss.ctirss.correccion.base.paginador.model.DatosSalidaPaginador;
import mx.gob.imss.ctirss.correccion.commons.paginador.CrtIncidenciasWrapperDataTable;
import mx.gob.imss.ctirss.correccion.commons.paginador.CrtPeriodosPresentadosWrapperDataTable;
import mx.gob.imss.ctirss.correccion.commons.vo.seguimiento.promocion.generico.PromocionSeguimientoGenericoVO;
import mx.gob.imss.ctirss.correccion.commons.vo.seguimiento.promocion.generico.SegAnexoPagosVO;
import mx.gob.imss.ctirss.correccion.commons.vo.seguimiento.promocion.generico.SegAutAvisoDictGenericoTabVO;
import mx.gob.imss.ctirss.correccion.commons.vo.seguimiento.promocion.generico.SegCancelacionGenericoTabVO;
import mx.gob.imss.ctirss.correccion.commons.vo.seguimiento.promocion.generico.SegDerivacionSubdelGenericoTabVO;
import mx.gob.imss.ctirss.correccion.commons.vo.seguimiento.promocion.generico.SegEOIncidenciasObraGenericoVO;
import mx.gob.imss.ctirss.correccion.commons.vo.seguimiento.promocion.generico.SegEOIncidenciasObraVO;
import mx.gob.imss.ctirss.correccion.commons.vo.seguimiento.promocion.generico.SegEOPeriodosPresentadosObraVO;
import mx.gob.imss.ctirss.correccion.commons.vo.seguimiento.promocion.generico.SegRegularizarObraGenericoTabVO;
import mx.gob.imss.ctirss.correccion.framework.base.controller.AbstractController;
import mx.gob.imss.ctirss.correccion.framework.base.model.AbstractModel;
import mx.gob.imss.ctirss.correccion.framework.utils.ConstantesBusiness;
import mx.gob.imss.ctirss.correccion.framework.utils.enums.CatEstatus;
import mx.gob.imss.ctirss.correccion.model.CrtSolicitudcorr;
import mx.gob.imss.ctirss.correccion.model.SatPatron;
import mx.gob.imss.ctirss.correccion.promocion.model.CrtPromocion;
import mx.gob.imss.ctirss.correccion.promocion.regularizacion.model.CrtRegulapagos;
import mx.gob.imss.ctirss.correccion.promocion.regularizacion.model.CrtRegulapagosdet;
import mx.gob.imss.ctirss.correccion.promocion.regularizacion.paginador.model.CrtRegulapagosdetWrapperDataTable;
import mx.gob.imss.ctirss.correccion.promocion.regularizacion.service.interfaces.RegularizacionService;
import mx.gob.imss.ctirss.correccion.promocion.service.interfaces.PromocionService;
import mx.gob.imss.ctirss.correccion.seguimiento.estudioCorreccion.model.CrtRevPagos;
import mx.gob.imss.ctirss.correccion.seguimiento.service.interfaces.pagos.PagosService;
import mx.gob.imss.ctirss.correccion.service.interfaces.ICatalogoService;
import mx.gob.imss.ctirss.correccion.service.interfaces.IObraService;
import mx.gob.imss.ctirss.correccion.service.interfaces.IPatronesService;
import mx.gob.imss.ctirss.correccion.session.UserSession;
import mx.gob.imss.ctirss.correccion.utils.Functions;
import mx.gob.imss.ctirss.domiciliosInegi.model.DgDomicilioGeografico;
import mx.gob.imss.ctirss.domiciliosInegi.service.interfaces.DomiciliosInegiService;
import mx.gob.imss.ctirss.domiciliosInegi.web.controller.DomGeograficosController;

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
 * @date 04/06/2012
 */

@Controller						
@RequestMapping(value="/promocion/seguimiento/generico")
public class SeguimientoPromocionGenericoController extends AbstractController {
	
	/**
	 * Logger.
	 */
	private final static Logger logger = Logger.getLogger(SeguimientoPromocionGenericoController.class);
	
	@Autowired
	private PromocionService<CrtPromocion> promocionServiceBean;
	
	@Autowired
	private RegularizacionService<CrtRegulapagos> regularizacionService;
	
	@Autowired
	private RegularizacionService<CrtRegulapagosdet> regularizacionDetService;
	
	@Autowired
	private ICatalogoService<AbstractModel> iCatalogoServiceBean;

	@Autowired
	private	IPatronesService<?> patronesService;
	
	@Autowired 
	private DomiciliosInegiService<DgDomicilioGeografico> domiciliosInegiServiceBean;	
		
	@Autowired
	private IObraService<?> obraService;
	
	@Autowired
	private PagosService<?> pagosService;
	
	/**
	 * Metodo que realiza el flujo de la pestaña Generica de Derivar a fiscalizacion
	 * aplica para cualquier seguimiento de promocion
	 * 
	 * @param seguimientoGenericoVO
	 * @author Oscar Beltran Ortega
	 * @return CrtPromocion
	 * @version 1.0.0
	 */
	@RequestMapping(value="/fiscalizacion" , method=RequestMethod.POST)
	public @ResponseBody CrtPromocion derivarFiscalizacion(@RequestBody PromocionSeguimientoGenericoVO seguimientoGenericoVO, HttpServletResponse response, HttpServletRequest request) {

		UserSession user = getUsuarioFirmado(request);
		
		//consulta los datos de la promocion
		CrtPromocion promocion = new CrtPromocion();
		promocion.setCvePromocion(Long.valueOf(seguimientoGenericoVO.getCvePromocion()));
		promocion = promocionServiceBean.consultaPorClave(promocion);
		
		promocion.setFecFechapai(Functions.stringToDate(seguimientoGenericoVO.getSegDerivaFiscaTabVo().getFecDerivacionGenerica()));
		promocion.setTxRefDerivacion(seguimientoGenericoVO.getSegDerivaFiscaTabVo().getReferenciaDerFisca());
		promocion.setCveEstatus(CatEstatus.CORRECCION_DERIVADA_FISCALIZACION.getId());
		promocion.setFecFechareg(new Date());
		promocion.setCveUsuario(user.getCurpUsuario().toString());
		
		

		
		this.promocionServiceBean.modificar(promocion);
		return promocion;
	}

	/**
	 * Metodo que se encarga de actualizar los datos de la cancelacion generica
	 * @param seguimientoGenericoVO
	 * @param response
	 * @param request
	 * @return CrtPromocion
	 * @author Gerardo Salazar Vega
	 * @version 1.0.0
	 */	
	@RequestMapping(value="/cancelacionGenerica" , method=RequestMethod.POST)
	public @ResponseBody CrtPromocion cancelar(@RequestBody PromocionSeguimientoGenericoVO seguimientoGenericoVO, HttpServletResponse response, HttpServletRequest request) {
		CrtPromocion promocion = new CrtPromocion();
		SegCancelacionGenericoTabVO cancelacionGenerico= seguimientoGenericoVO.getCancelacionGenericoTabVO();
		
		promocion.setCvePromocion(Long.valueOf(seguimientoGenericoVO.getCvePromocion()));
		BigDecimal bIdMotivo = new BigDecimal(cancelacionGenerico.getCveMotivoCancelacion());

		logger.debug("cancelacionGenerica: "+cancelacionGenerico.toString());
		logger.debug("clave promocion: "+seguimientoGenericoVO.getCvePromocion());

		UserSession user = getUsuarioFirmado(request);	

		promocion = promocionServiceBean.consultaPorClave(promocion);
		promocion.setFecFechaCancela(Functions.stringToDate(cancelacionGenerico.getFechaCancelacion()));
		promocion.setIdMotivoCancelacion(bIdMotivo);
		promocion.setNuVolanteCancela(cancelacionGenerico.getReferenciaCancelacion());
		promocion.setCveFuncionarioAutoriza(cancelacionGenerico.getCveFuncionarioAutoriza());
		promocion.setCveEstatus(CatEstatus.FOLIO_CANCELADO.getId());
		promocion.setFecFechareg(new Date());
		promocion.setCveUsuario(user.getCurpUsuario().toString());
		this.promocionServiceBean.modificar(promocion);
		promocion.setExito("La promocion ha sido cancelada");

		return promocion;
	}

	/**
	 * Metodo que se encarga de actualizar los datos del aviso de dictamen generico
	 * @param seguimientoGenericoVO
	 * @param response
	 * @param request
	 * @return CrtPromocion
	 * @author Gerardo Salazar Vega
	 * @version 1.0.0
	 */		
	@RequestMapping(value="/guardaAvisoDictamen" , method=RequestMethod.POST)
	public @ResponseBody CrtPromocion guardarDictamenGenerico(@RequestBody PromocionSeguimientoGenericoVO seguimientoGenericoVO, HttpServletResponse response, HttpServletRequest request) {
		SegAutAvisoDictGenericoTabVO avDictamenGenerico=seguimientoGenericoVO.getAvisoDictamenGenericoTabVO();
		UserSession user = getUsuarioFirmado(request);
		
		logger.debug("guardaAvisoDictamen: "+avDictamenGenerico.toString());
		logger.debug("clave promocion: "+seguimientoGenericoVO.getCvePromocion());
		
		//consulta los datos de la promocion
		CrtPromocion promocion = new CrtPromocion();
		promocion.setCvePromocion(Long.valueOf(seguimientoGenericoVO.getCvePromocion()));
		
		promocion = promocionServiceBean.consultaPorClave(promocion);
		promocion.setNumAvisoDictamen(avDictamenGenerico.getNumAvisoDictamen());
		promocion.setFecAvisoDictamen(Functions.stringToDate(avDictamenGenerico.getFecAutAvisoDictamen()));		
		promocion.setFecInicialDictamen(Functions.stringToDate(avDictamenGenerico.getFecIniDictamen()));
		promocion.setFecFinalDictamen(Functions.stringToDate(avDictamenGenerico.getFecFinDictamen()));
		promocion.setCveEstatus(CatEstatus.CORRECCION_DERIVADA_DICTAMEN.getId());
		promocion.setFecFechareg(new Date());
		promocion.setCveUsuario(user.getCurpUsuario().toString());
		
		// Actualiza los datos del dictamen para la promocion
		this.promocionServiceBean.modificar(promocion);
		promocion.setExito("La informacion ha sido guardada");
		return promocion;
	}	
	
	/**
	 * Metodo que se encarga de consultar los datos del funcionario que autoriza
	 * de acuerdo a la delegacion del usuario firmado.
	 * 
	 * @param response
	 * @param request
	 * @return ArrayList
	 * @author Gerardo Salazar Vega
	 * @version 1.0.0
	 */		
	@RequestMapping(value="/consultaFuncionarioAutoriza", method=RequestMethod.POST)
	public @ResponseBody ArrayList<?> consultaFuncionarioAutoriza(@RequestBody HttpServletResponse response,HttpServletRequest request){
		
		UserSession user = getUsuarioFirmado(request);
		logger.debug("delegacion="+user.getIdDelegacion().toString());
		
//		String queryFuncionario="select  A.CVE_ROL, B.NOM_NOMBRE || ' ' || B.NOM_PATERNO || ' ' || B.NOM_MATERNO"+
//					" from seg_perfil_usuario A,"+
//					"      seg_usuario B,"+
//					"      seg_usuario_funcionario C"+
//					" WHERE A.CVE_ID_USUARIO = B.CVE_ID_USUARIO"+
//					" AND B.CVE_ID_USUARIO =  c.CVE_ID_USUARIO"+
//					" AND B.CVE_ID_USUARIO >= 10000" + // clave usuario mayor a 10,000 solicitado x CBV
//					" AND A.CVE_ROL = "+ ConstantesBusiness.ROL_SUPERVISOR_DELEG_AUDIT_PATRON +
//					" AND C.CVE_ID_DELEGACION = " + user.getIdDelegacion().toString() +
//					" AND C.IND_VIGENCIA = " + ConstantesBusiness.INDICADOR_FUNCIONARIOVIGENTE;
		
		
		String queryFuncionario="select usuario.DES_USR_CURP,usuario.NOM_NOMBRE || ' ' || usuario.NOM_PATERNO || ' ' || usuario.NOM_MATERNO from SSO_USUARIOS usuario "+
				" where usuario.CVE_ID_DELEGACION ="+user.getIdDelegacion().toString()+" and usuario.CVE_SSOPUESTO ="+ConstantesBusiness.ROL_CANCELA_PROMOCION;
		ArrayList<?> listaFuncionarios = (ArrayList<?>) iCatalogoServiceBean.consultaSQL(queryFuncionario);
		return listaFuncionarios;
	}

	@RequestMapping(value="/validaPatron" , method=RequestMethod.POST)
	public @ResponseBody SatPatron validaPatron(@RequestBody String parametro, HttpServletRequest request) {
		UserSession user = getUsuarioFirmado(request);
		String valor = parametro.substring(1, parametro.length() - 1);
		logger.debug("--Seg Generico longitud RP:" + parametro.length());
		SatPatron patron = new SatPatron();
		patron = patronesService.validaRegistroPatronalWS(valor, user.getIdSubDelegacion());
		if (patron == null) {
			patron = new SatPatron();
			patron.setCveRespuestaWS(-1);
			patron.setDescRespuestaWS("No se encontr\u00f3 el registro patronal");
		} else {
			logger.debug("respuestaWS:" + patron.getDescRespuestaWS()
					+ ", cveTipoMov:" + patron.getCveTipoMovWS()
					+ ", cveRespuesta:" + patron.getCveRespuestaWS());
		}
		return patron;
	}

	@RequestMapping(value="/validaPatronTmp" , method=RequestMethod.POST)
	public @ResponseBody SatPatron validaPatronTmp(@RequestBody String parametro) {
		
		
		String valor = parametro.substring(1, parametro.length() -1);
		SatPatron patron = new SatPatron();
		patron.setRegistroPatronal(valor);
		patron =  patronesService.getByRegistroPatronal(valor);
		//patron =  patronesService.consultaPorClave(patron);
		if (patron==null){
			patron=new SatPatron();
			patron.setCveRespuestaWS(-1);
			patron.setDescRespuestaWS("No se encontro el registro patronal");
		}
		logger.debug("validaPatronTmp:("+parametro+")"+patron.getDescRespuestaWS() +
					", cveTipoMov:"+patron.getCveTipoMovWS()+
					", cveRespuesta:"+patron.getCveRespuestaWS());
		return patron;
	}

	
	// Crea un HashTable de domicilios
	@RequestMapping(value="/solicitudDomGeograficoObra" , method=RequestMethod.GET)
	public String callDomGeograficosObra(HttpServletResponse response, HttpServletRequest request, Model model) {
		
		DgDomicilioGeografico dg = new DgDomicilioGeografico();
		dg.setBloquearEstado(false);
		Hashtable doms = (Hashtable)getDomicilioInegiSession(request);
		
		if(doms!=null){
			DgDomicilioGeografico tmp = (DgDomicilioGeografico)doms.get("DOM_DERIVAR_SUBD");
			if(tmp != null)
				dg=tmp;
		}
		
		dg.setHastableKeyDG("DOM_DERIVAR_SUBD");
		request.getSession().removeAttribute("ObraSol");
		return new DomGeograficosController().getCreateGenericForm(model,dg,request);
	}	
	
	// Metodo puente para recuperar los valores
	@RequestMapping(value = "/actualizaDomObra", method = RequestMethod.POST)
	public @ResponseBody DgDomicilioGeografico actualizaDomObra(@RequestBody CrtSolicitudcorr patron,HttpServletResponse response, HttpServletRequest request) {
		Hashtable<String, Object> domGeografico = (Hashtable)getDomicilioInegiSession(request);
		if(domGeografico!=null&&domGeografico.size()>0)
		{	 
			return (DgDomicilioGeografico)((Hashtable)getDomicilioInegiSession(request)).get(patron.getPatron());
		}
		return null;
	}	
	
	@RequestMapping(value="/sessionDomicilioGeografico", method=RequestMethod.POST )
	public @ResponseBody DgDomicilioGeografico almacenaSessionDomicilioInegi(@RequestBody DgDomicilioGeografico domicilioInegi,HttpServletRequest request) {
		return new DomGeograficosController().almacenaSessionDomicilioInegi(domicilioInegi, request);
	}

	@RequestMapping(value="/removerDomicilioSession", method=RequestMethod.POST )
	public @ResponseBody DgDomicilioGeografico removerDomicilio(@RequestBody DgDomicilioGeografico domicilio,HttpServletRequest request) {		
		removeDomicilioInegiSession(request);			
		return domicilio;
	}
	
	@RequestMapping(value="/derivarSubdelegacion" , method=RequestMethod.POST)
	public @ResponseBody CrtPromocion derivarSubdelegacion(@RequestBody PromocionSeguimientoGenericoVO seguimientoGenericoVO, HttpServletResponse response, HttpServletRequest request) {

		SegDerivacionSubdelGenericoTabVO derivaSubdelTabVo=seguimientoGenericoVO.getSegDerivaSubdelTabVo();
		UserSession user = getUsuarioFirmado(request);		
		//consulta los datos de la promocion
		CrtPromocion promocion = new CrtPromocion();
		promocion.setCvePromocion(Long.valueOf(seguimientoGenericoVO.getCvePromocion()));
		promocion = promocionServiceBean.consultaPorClave(promocion);

		Hashtable<String,Object> hDomicilios = (Hashtable)getDomicilioInegiSession(request);
		DgDomicilioGeografico domicilioGeo = (DgDomicilioGeografico)hDomicilios.get("DOM_DERIVAR_SUBD");
		domicilioGeo.toString();
		
		domicilioGeo = this.domiciliosInegiServiceBean.agregar(domicilioGeo);
		logger.debug("idDomicilioGeo: " + domicilioGeo.getDomicilioId());
		
		promocion.setCveDomGeoPatron(new BigDecimal(domicilioGeo.getDomicilioId()));
		
		logger.debug("derivarSubdelegacionGenerica: " + derivaSubdelTabVo.toString());
		promocion.setCveFkPatronFisica(derivaSubdelTabVo.getCveFkPatronFis());
		if (derivaSubdelTabVo.getCveFkPatronObra() != null) {
			promocion.setCveFkPatronObra(derivaSubdelTabVo.getCveFkPatronObra());
		}		
		promocion.setCveFkSubdelegacionDest(new Long (derivaSubdelTabVo.getCveSubdelegacionDestino()));
		promocion.setFecFechaDeriSubdelegacion(Functions.stringToDate(derivaSubdelTabVo.getFechaDerivacionSubdel()));
		promocion.setCveEstatus(CatEstatus.CORRECCION_DERIVADA_OTRA_SUBDELEGACION.getId());
		promocion.setFecFechareg(new Date());
		promocion.setCveUsuario(user.getCurpUsuario().toString());

		this.promocionServiceBean.modificar(promocion);
		request.getSession().removeAttribute("DOM_DERIVAR_SUBD");
		promocion.setExito("La informacion ha sido guardada");
		removeDomicilioInegiSession(request);
		return promocion;
	}
	
	/**
	 * Metodo llamdo por AJAX para preguntar por la fecha del sistema esto con el objetivo de evitar de fallas en los calculos de fechas. 
	 * Cabe la posibilidad de que la creacion y obtencion de las fechas actuales sean incorrectas si esta operacion se le delega a JAVASCRIPT
	 * ya que con cambiar la fecha en la maquina local los calendarios generados se reajustaran a esta fecha local, en cambio si la fecha del dia
	 * se pide al servidor no habra este tipo de errores, claro a menos de que la fecha del servidor tambien este mal. 
	 * @param HttpRequest
	 * @return
	 */
	@RequestMapping(value="/obtenerFechaServidor.do", method=RequestMethod.POST)
	public @ResponseBody String obtenerFechaServidor(HttpServletRequest request){
		return ConstantesBusiness.dateToStringFormat(new Date(), "dd-MM-yyyy");
	}

	
	/**
	 * Metodo llamdo por AJAX para preguntar por la fecha del sistema esto con el objetivo de evitar de fallas en los calculos de fechas. 
	 * Cabe la posibilidad de que la creacion y obtencion de las fechas actuales sean incorrectas si esta operacion se le delega a JAVASCRIPT
	 * ya que con cambiar la fecha en la maquina local los calendarios generados se reajustaran a esta fecha local, en cambio si la fecha del dia
	 * se pide al servidor no habra este tipo de errores, claro a menos de que la fecha del servidor tambien este mal. 
	 * @param HttpRequest
	 * @return
	 */
	@RequestMapping(value="/obtenerFechaServidorMinima.do", method=RequestMethod.POST)
	public @ResponseBody String obtenerFechaServidorMinima(HttpServletRequest request){
		Calendar calendario = Calendar.getInstance();
		calendario.add(Calendar.DAY_OF_YEAR, -44);
		Date dia = calendario.getTime();
		return ConstantesBusiness.dateToStringFormat(dia, ConstantesBusiness.dd_mm_yyyy);
	}
	
	/**
	 * Metodo que realiza la operacion de la pestaña Generica de Regulariza obra 
	 * la cual se utiliza en los seguimientos de promocion
	 * @param seguimientoGenericoVO
	 * @author Oscar Beltran Ortega
	 * @return PromocionSeguimientoGenericoVO
	 * @version 1.0.0
	 */
	@RequestMapping(value="/regulaObraGenerico" , method=RequestMethod.POST)
	public @ResponseBody PromocionSeguimientoGenericoVO regularObraFiscalizacion(@RequestBody PromocionSeguimientoGenericoVO seguimientoGenericoVO, HttpServletResponse response, HttpServletRequest request) {

		UserSession user = getUsuarioFirmado(request);
		
		//se eliminan las comas de los Strings 
		reemplazaComas(seguimientoGenericoVO);
		
		CrtRegulapagos pago = new CrtRegulapagos();
		if(seguimientoGenericoVO.getSegRegularizaObraVo().getCveRegulaPagos()!= null && !seguimientoGenericoVO.getSegRegularizaObraVo().getCveRegulaPagos().equals("")){
			pago.setCveRegulapagos(Long.valueOf(seguimientoGenericoVO.getSegRegularizaObraVo().getCveRegulaPagos()));
		}
		
		//pago.setCvePromocion(Long.valueOf(cvePromocion));
	    pago.setCvePromocion(Long.valueOf(seguimientoGenericoVO.getCvePromocion()));
		if(seguimientoGenericoVO.getSegRegularizaObraVo().getPeriodoRegDel()!= null){
			pago.setFecPeridodini(Functions.stringToDate(seguimientoGenericoVO.getSegRegularizaObraVo().getPeriodoRegDel()));
		}
		if(seguimientoGenericoVO.getSegRegularizaObraVo().getPeriodoRegAl()!=null){
			pago.setFecPeriodofin(Functions.stringToDate(seguimientoGenericoVO.getSegRegularizaObraVo().getPeriodoRegAl()));
		}
		if(seguimientoGenericoVO.getSegRegularizaObraVo().getPorcRegularizado() != null && !seguimientoGenericoVO.getSegRegularizaObraVo().getPorcRegularizado().equals("")){
			pago.setPorRegularizado(new BigDecimal(seguimientoGenericoVO.getSegRegularizaObraVo().getPorcRegularizado()));
		}
		if((seguimientoGenericoVO.getSegRegularizaObraVo().getPorcAvance() !=null && !seguimientoGenericoVO.getSegRegularizaObraVo().getPorcAvance().equals(""))){
			pago.setPorAvance(new BigDecimal(seguimientoGenericoVO.getSegRegularizaObraVo().getPorcAvance()));
		}
		
		pago.setNumTrabomisos(new BigDecimal(seguimientoGenericoVO.getSegRegularizaObraVo().getTrabOmisos()));
		pago.setNumTrabsubdclara(new BigDecimal(seguimientoGenericoVO.getSegRegularizaObraVo().getTrabSubdeclarados()));
		pago.setNumTrabrevisados(new BigDecimal(seguimientoGenericoVO.getSegRegularizaObraVo().getTrabRevisados()));
		pago.setFecFechaReg(new Date());
		pago.setCveUsuario(user.getCurpUsuario().toString());
		//pago.setNumConvenio(numConvenio);
		pago.setNumBaseDeterminada(new BigDecimal(seguimientoGenericoVO.getSegRegularizaObraVo().getBaseDeterminada()));
		
		if(seguimientoGenericoVO.getSegRegularizaObraVo().getNumParcialidades()!=null && !seguimientoGenericoVO.getSegRegularizaObraVo().getNumParcialidades().equals("")){
		pago.setNumParcialidades(Integer.valueOf(seguimientoGenericoVO.getSegRegularizaObraVo().getNumParcialidades()));
		}
		if(seguimientoGenericoVO.getSegRegularizaObraVo().getSuertePpalDetCOP() != null && !seguimientoGenericoVO.getSegRegularizaObraVo().getSuertePpalDetCOP().equals("")){
			pago.setSuertePpalDetCOP(new BigDecimal(seguimientoGenericoVO.getSegRegularizaObraVo().getSuertePpalDetCOP()));
		}
		if(seguimientoGenericoVO.getSegRegularizaObraVo().getSuertePpalDetRCV() != null && !seguimientoGenericoVO.getSegRegularizaObraVo().getSuertePpalDetRCV().equals("")){
			pago.setSuertePpalDetRCV(new BigDecimal(seguimientoGenericoVO.getSegRegularizaObraVo().getSuertePpalDetRCV()));
		}
		
		
		try{
			pago = this.regularizacionService.agregar(pago);
		}catch (Exception e) {
			logger.error("No se pudo almacenar los pagos");
			e.printStackTrace();
			return null;
		}
		seguimientoGenericoVO.getSegRegularizaObraVo().setCveRegulaPagos(""+pago.getCveRegulapagos());
		logger.info("Cve REGULA PAGOS Generada: " + pago.getCveRegulapagos());
		return seguimientoGenericoVO;

		
		
	}
	
	/**
	 * Metodo que realiza la operacion de la pestaña Generica de Anexar Pagos utilizada en los seguimientos
	 * de promocion, guarda pagos detalle con la informacion capturada por el usuario en pantalla
	 * 
	 * @param PromocionSeguimientoGenericoVO
	 * @author Oscar Beltran Ortega
	 * @return PromocionSeguimientoGenericoVO
	 * @version 1.0.0
	 */
	@RequestMapping(value="/guardarPagoDet" , method=RequestMethod.POST)
	public @ResponseBody PromocionSeguimientoGenericoVO guardarPagosDetalle(@RequestBody PromocionSeguimientoGenericoVO seguimientoGenericoVO, HttpServletResponse response, HttpServletRequest request) {

		UserSession user = getUsuarioFirmado(request);
		CrtRegulapagosdet pagoDetalle = new CrtRegulapagosdet();;
		
		logger.info(user.getCurpUsuario());
					
		reemplazaComasPagoDetalle(seguimientoGenericoVO);
		
		//actualizacion
		if(seguimientoGenericoVO.getAnexoPagosVO().getCveRegulaPago()!=null){
			
			pagoDetalle.setCveRegulapagosdet(Long.valueOf(seguimientoGenericoVO.getAnexoPagosVO().getCveRegulaPago()));
			pagoDetalle = regularizacionDetService.consultaPorClavePago(pagoDetalle);
			
		}else{ //nuevo
			CrtRegulapagos regulaPago = new CrtRegulapagos();
			regulaPago.setCveRegulapagos(Long.valueOf(seguimientoGenericoVO.getSegRegularizaObraVo().getCveRegulaPagos()));
			
			regulaPago = (CrtRegulapagos)this.regularizacionService.consultaPorClave(regulaPago);
			pagoDetalle.setCrtRegulapagos(regulaPago);
			
		}
		
		if(seguimientoGenericoVO.getAnexoPagosVO().getFolioSUA()!= null  ){
			pagoDetalle.setNumFoliosua(Integer.valueOf(seguimientoGenericoVO.getAnexoPagosVO().getFolioSUA()));
		}
		
		
		pagoDetalle.setNumOrdeningreso(seguimientoGenericoVO.getAnexoPagosVO().getOrdenIngreso());
		pagoDetalle.setNumCredito(seguimientoGenericoVO.getAnexoPagosVO().getNumCredito());
		pagoDetalle.setFecFechapago(Functions.FormateaFecha(seguimientoGenericoVO.getAnexoPagosVO().getFechaPagoGenerico(), "-"));
		
		//cop
		if(seguimientoGenericoVO.getAnexoPagosVO().isSeccionCOP()){
			if(seguimientoGenericoVO.getAnexoPagosVO().getMultasCOP() != null){
				pagoDetalle.setImpMultasCop(new BigDecimal(seguimientoGenericoVO.getAnexoPagosVO().getMultasCOP()));
			}
			if(seguimientoGenericoVO.getAnexoPagosVO().getSpCOP()!=null){
				pagoDetalle.setImpCopsp(new BigDecimal(seguimientoGenericoVO.getAnexoPagosVO().getSpCOP()));
			}
			if(seguimientoGenericoVO.getAnexoPagosVO().getActCOP() != null){
				pagoDetalle.setImpCopact(new BigDecimal(seguimientoGenericoVO.getAnexoPagosVO().getActCOP()));	
			}
			if(seguimientoGenericoVO.getAnexoPagosVO().getRecCOP() != null) {
				pagoDetalle.setImpCoprec(new BigDecimal(seguimientoGenericoVO.getAnexoPagosVO().getRecCOP()));
			}
			pagoDetalle.setIdConcepto(ConstantesBusiness.TIPO_PAGO_COP);
			pagoDetalle.setNumPeriodoCop(Integer.valueOf(seguimientoGenericoVO.getAnexoPagosVO().getPeriodoCOP()));
				
		}
			
		//RCV
		if(seguimientoGenericoVO.getAnexoPagosVO().isSeccionRCV()){
			pagoDetalle.setImpRcvsp(new BigDecimal(seguimientoGenericoVO.getAnexoPagosVO().getSpRCV()));
			if(seguimientoGenericoVO.getAnexoPagosVO().getActRCV() != null){
				pagoDetalle.setImpRcvact(new BigDecimal(seguimientoGenericoVO.getAnexoPagosVO().getActRCV()));
			}
			if(seguimientoGenericoVO.getAnexoPagosVO().getRecRCV() != null){
				pagoDetalle.setImpRcvrec(new BigDecimal(seguimientoGenericoVO.getAnexoPagosVO().getRecRCV()));
			}
			
			if(seguimientoGenericoVO.getAnexoPagosVO().getMultasRCV() != null){
				pagoDetalle.setImpMultasRcv(new BigDecimal(seguimientoGenericoVO.getAnexoPagosVO().getMultasRCV()));
			}
			pagoDetalle.setIdConcepto(ConstantesBusiness.TIPO_PAGO_RCV);
			pagoDetalle.setNumPeriodoRcv(Integer.valueOf(seguimientoGenericoVO.getAnexoPagosVO().getPeriodoRCV()));
				
		}
		//si ambos conceptos se incluyen cop y rcp
		if(seguimientoGenericoVO.getAnexoPagosVO().isSeccionRCV() && seguimientoGenericoVO.getAnexoPagosVO().isSeccionCOP()){
			pagoDetalle.setIdConcepto(ConstantesBusiness.TIPO_PAGO_RCV_Y_COP);
		}
			
		if(seguimientoGenericoVO.getAnexoPagosVO().getTipoDocto() !=null){
			pagoDetalle.setIdTipoDocto(Integer.valueOf(seguimientoGenericoVO.getAnexoPagosVO().getTipoDocto()));
		}
		if(seguimientoGenericoVO.getAnexoPagosVO().getTrabRegularizados() !=null){
			pagoDetalle.setNumTrabajadoresRegulariza(Integer.valueOf(seguimientoGenericoVO.getAnexoPagosVO().getTrabRegularizados()));
		}
		
		if(seguimientoGenericoVO.getAnexoPagosVO().getAltas() != null){
			pagoDetalle.setNumAltas(Integer.valueOf(seguimientoGenericoVO.getAnexoPagosVO().getAltas()));
		}
		
		if(seguimientoGenericoVO.getAnexoPagosVO().getBajas() != null){
			pagoDetalle.setNumBajas(Integer.valueOf(seguimientoGenericoVO.getAnexoPagosVO().getBajas()));
		}
		if(seguimientoGenericoVO.getAnexoPagosVO().getModifSalario() != null){
			pagoDetalle.setNumModifSalario(Integer.valueOf(seguimientoGenericoVO.getAnexoPagosVO().getModifSalario()));
		}
			
		pagoDetalle.setFecFechaReg(new Date());
		pagoDetalle.setCveUsuario(user.getCveIdUsuario());
		pagoDetalle = this.regularizacionDetService.agregar(pagoDetalle);
		
		CrtPromocion promocion = new CrtPromocion();
		promocion.setCvePromocion(Long.valueOf(seguimientoGenericoVO.getCvePromocion()));
		promocion = promocionServiceBean.consultaPorClave(promocion);
		
		promocion.setCveEstatus(CatEstatus.PROMOCION_REGULARIZADA.getId());
		promocion.setFecFechareg(new Date());
		promocion.setCveUsuario(String.valueOf(user.getCurpUsuario()));
		this.promocionServiceBean.modificar(promocion);
		return seguimientoGenericoVO;
	}
	
	
	/**
	 * Metodo que realiza la consulta , paginacion del Datatable incluido en la pestaña generica de Anexar pagos
	 * 
	 * @param 
	 * @author Oscar Beltran Ortega
	 * @return DatosSalidaPaginador<CrtRegulapagosdet>
	 * @version 1.0.0
	 */
	@RequestMapping(value="/paginar", method=RequestMethod.POST )
    public @ResponseBody DatosSalidaPaginador<CrtRegulapagosdet> pagina(@RequestBody CrtRegulapagosdetWrapperDataTable aoData ) {
		logger.info(".-.-controller public @ResponseBody DatosSalidaPaginador<SegAnexoPagosVO> pagina(@RequestBody CrcGastosWrapperDataTable aoData ) {");
		DatosEntradaPaginador<CrtRegulapagosdet> send = new DatosEntradaPaginador<CrtRegulapagosdet>();
		DatosSalidaPaginador<CrtRegulapagosdet> reply = new DatosSalidaPaginador<CrtRegulapagosdet>();
		
		send.parserArray(aoData.getAoData());
		send.setModelo(aoData.getoForm());
		
		
		if(aoData.getoForm().getCrtRegulapagos()!= null && aoData.getoForm().getCrtRegulapagos().getCveRegulapagos()!= 0){
			
			reply = this.regularizacionDetService.paginaPagos(send);
			
			if(reply.getAaData().size()>0){
				
			}else{
				reply.setAaData(new ArrayList<CrtRegulapagosdet>());
				reply.setiTotalDisplayRecords(0);
				reply.setiTotalRecords(0);
			}
			
		}
		
		logger.info(".-.-controller realizo consulta) {");
        reply.setsEcho(send.getsEcho());
        
        return reply;
    }

/**
 * Metodo que consulta la informacion de las pestañas genericas de Regularizar Obra y anexar Pagos
 * 
 * @param PromocionSeguimientoGenericoVO
 * @author Oscar Beltran Ortega
 * @return PromocionSeguimientoGenericoVO
 * @version 1.0.0
 */
@RequestMapping(value="/consultaPago" , method=RequestMethod.POST)
public @ResponseBody PromocionSeguimientoGenericoVO consultaRegulaPago(@RequestBody PromocionSeguimientoGenericoVO seguimientoGenericoVO, HttpServletResponse response, HttpServletRequest request) {
	logger.info("consultaRegulaPago");
	
	CrtRegulapagos regulaPago = new CrtRegulapagos();
	if(seguimientoGenericoVO.getCvePromocion() != null){
		
		regulaPago.setCvePromocion(Long.valueOf(seguimientoGenericoVO.getCvePromocion()));
		regulaPago = (CrtRegulapagos)this.regularizacionService.consultaPorClavePromocion(regulaPago);
		//si existe
		if(regulaPago.getCveRegulapagos() != 0){
			SegRegularizarObraGenericoTabVO regulaPagoVO = new SegRegularizarObraGenericoTabVO();
			regulaPagoVO.setCveRegulaPagos(String.valueOf(regulaPago.getCveRegulapagos()));
			logger.info("String.valueOf(regulaPago.getCveRegulapagos()) :" + String.valueOf(regulaPago.getCveRegulapagos()));
			regulaPagoVO.setPeriodoRegDel(Functions.dateToString(regulaPago.getFecPeridodini()));
			regulaPagoVO.setPeriodoRegAl(Functions.dateToString(regulaPago.getFecPeriodofin()));
			
			regulaPagoVO.setPorcAvance(regulaPago.getPorAvance()!=null?regulaPago.getPorAvance().toString():"");
			regulaPagoVO.setPorcRegularizado(regulaPago.getPorRegularizado() != null ?regulaPago.getPorRegularizado().toString():"");
			regulaPagoVO.setNumParcialidades(regulaPago.getNumParcialidades() != null ?regulaPago.getNumParcialidades().toString():"");
			regulaPagoVO.setTrabRevisados(regulaPago.getNumTrabrevisados() !=null ?regulaPago.getNumTrabrevisados().toString():"0");
			regulaPagoVO.setTrabOmisos(regulaPago.getNumTrabomisos() !=null ? regulaPago.getNumTrabomisos().toString():"0");
			regulaPagoVO.setTrabSubdeclarados(regulaPago.getNumTrabsubdclara()!= null ? regulaPago.getNumTrabsubdclara().toString():"0");
			
			regulaPagoVO.setBaseDeterminada(String.valueOf(regulaPago.getNumBaseDeterminada()));
		
			regulaPagoVO.setSuertePpalDetCOP(String.valueOf(regulaPago.getSuertePpalDetCOP()!=null? regulaPago.getSuertePpalDetCOP():0));
			regulaPagoVO.setSuertePpalDetRCV(String.valueOf(regulaPago.getSuertePpalDetRCV()!= null ? regulaPago.getSuertePpalDetRCV():0 ));
			
			seguimientoGenericoVO.setSegRegularizaObraVo(regulaPagoVO);
		}
	}
	
	
	return seguimientoGenericoVO;
	}

	/**
	 * Metodo que consulta la informacion de un Pago Detalle a travez de su ID
	 * 
	 * @param SegAnexoPagosVO
	 * @author Oscar Beltran Ortega
	 * @return CrtRegulapagosdet
	 * @version 1.0.0
	 */
	@RequestMapping(value="/consultaPagoDetalle" , method=RequestMethod.POST)
	public @ResponseBody CrtRegulapagosdet consultaPagoDetalle(@RequestBody SegAnexoPagosVO anexoPagoVO, HttpServletResponse response, HttpServletRequest request) {
		logger.info("consultaRegulaPago");
		
		CrtRegulapagosdet pagoDetalle = new CrtRegulapagosdet();
		pagoDetalle.setCveRegulapagosdet(Long.valueOf(anexoPagoVO.getCveRegulaPago()));
		pagoDetalle = regularizacionDetService.consultaPorClavePago(pagoDetalle);
		pagoDetalle.setFechaPago(Functions.dateToString(pagoDetalle.getFecFechapago()));
		
			
		return pagoDetalle;
	}
	
	
	/**
	 * Metodo que elimina un Pago Detalle a travez de su ID de la tabla
	 * 
	 * @param SegAnexoPagosVO
	 * @author Oscar Beltran Ortega
	 * @return CrtRegulapagosdet
	 * @version 1.0.0
	 */
	@RequestMapping(value="/eliminaPagoDetalle" , method=RequestMethod.POST)
	public @ResponseBody CrtRegulapagosdet eliminaPagoDetalle(@RequestBody SegAnexoPagosVO anexoPagoVO, HttpServletResponse response, HttpServletRequest request) {
		logger.info("consultaRegulaPago");
		
		CrtRegulapagosdet pagoDetalle = new CrtRegulapagosdet();
		pagoDetalle.setCveRegulapagosdet(Long.valueOf(anexoPagoVO.getCveRegulaPago()));
		regularizacionDetService.eliminar(pagoDetalle);
		
		
			
		return pagoDetalle;
	}
	



/**
 * Metodo que se encarga de actualizar los datos de Cierre por Cotizar Razonablemente
 * @param crtPromocion
 * @return CrtPromocion
 * @author Enrique Duran Jimenez
 * @version 1.0.0
 */	
@RequestMapping(value="/GuardarCCRG" , method=RequestMethod.POST)
public @ResponseBody CrtPromocion GuardarCCRG(@RequestBody CrtPromocion promocion) {
	
	CrtPromocion promocionGuardar = this.promocionServiceBean.consultaPorClave(promocion);
	if(promocionGuardar != null){
		promocionGuardar.setCveUsuario(promocion.getCveUsuario());
		promocionGuardar.setFecCotizRaz(Functions.FormateaFecha(promocion.getFechaRegularizacion(), "-"));
		promocionGuardar.setFecFechareg(new Date());
		promocionGuardar.setCveEstatus(CatEstatus.PROMOCION_CONCLUIDA_COTIZO_RAZONABLEMENTE.getId());	
	}
	promocion = this.promocionServiceBean.agregar(promocionGuardar);
	
	return promocion;
}


/**
 * Metodo que se encarga de actualizar los datos del seguimiento SBC
 * @param crtPromocion
 * @return CrtPromocion
 * @author Enrique Duran Jimenez
 * @version 1.0.0
 */	
@RequestMapping(value="/GuardarSeguimientoSBC" , method=RequestMethod.POST)
public @ResponseBody CrtPromocion GuardarSeguimientoSBC(@RequestBody CrtPromocion promocion, HttpServletRequest request) {
		
	UserSession user = getUsuarioFirmado(request);
	CrtPromocion promocionGuardar = this.promocionServiceBean.consultaPorClave(promocion);
	if(promocionGuardar != null){
		if(promocion.getFechaNotificacion() != null && !promocion.getFechaNotificacion().equals("")){
			promocionGuardar.setFecFechanotif(Functions.FormateaFecha(promocion.getFechaNotificacion(), "-"));
		}
		if(promocion.getFechaAtencion() != null && !promocion.getFechaAtencion().equals("")){
			promocionGuardar.setFecFechaAtencion(Functions.FormateaFecha(promocion.getFechaAtencion(), "-"));
		}
		if(promocion.getFechaInicio() != null && !promocion.getFechaInicio().equals("") && 
		   promocion.getFechaFin() != null && !promocion.getFechaFin().equals("")){
			promocionGuardar.setFecInicialDictamen(Functions.FormateaFecha(promocion.getFechaInicio(), "-"));
			promocionGuardar.setFecFinalDictamen(Functions.FormateaFecha(promocion.getFechaFin(), "-"));
		}
		promocionGuardar.setTxObservaciones(promocion.getTxObservaciones());		
		promocionGuardar.setFecFechareg(new Date());
		promocionGuardar.setCveUsuario(user.getCurpUsuario().toString());
	}
	promocion = this.promocionServiceBean.agregar(promocionGuardar);
	
	return promocion;
}



/**
 * Metodo que se encarga de actualizar los datos del seguimiento Construccion
 * @param crtPromocion
 * @return CrtPromocion
 * @author Enrique Duran Jimenez
 * @version 1.0.0
 */	
@RequestMapping(value="/GuardarSeguimientoEX" , method=RequestMethod.POST)
public @ResponseBody CrtPromocion GuardarSeguimientoEX(@RequestBody CrtPromocion promocion, HttpServletRequest request) {
		
	UserSession user = getUsuarioFirmado(request);
	CrtPromocion promocionGuardar = this.promocionServiceBean.consultaPorClave(promocion);
	if(promocionGuardar != null){
		if(promocion.getFechaNotificacion() != null && !promocion.getFechaNotificacion().equals("")){
			promocionGuardar.setFecFechanotif(Functions.FormateaFecha(promocion.getFechaNotificacion(), "-"));
		}
		if(promocion.getFechaAtencion() != null && !promocion.getFechaAtencion().equals("")){
			promocionGuardar.setFecFechaAtencion(Functions.FormateaFecha(promocion.getFechaAtencion(), "-"));
		}
		promocionGuardar.setTxObservaciones(promocion.getTxObservaciones());		
		promocionGuardar.setFecFechareg(new Date());
		promocionGuardar.setCveUsuario(user.getCurpUsuario().toString());
		if(promocion.getFechaInicio() != null && !promocion.getFechaInicio().equals("") && 
		   promocion.getFechaFin() != null && !promocion.getFechaFin().equals("")){
		   promocionGuardar.setFecInicialDictamen(Functions.FormateaFecha(promocion.getFechaInicio(), "-"));
		   promocionGuardar.setFecFinalDictamen(Functions.FormateaFecha(promocion.getFechaFin(), "-"));
		}
		// Se mete la clave del patron ya validado
		if(promocion.getRegPatron() != null && !promocion.getRegPatron().equalsIgnoreCase("")){
			promocionGuardar.setCveFkPatron(Long.valueOf(promocion.getRegPatron()));
		}
		if(promocion.getCveNroregobraSatic() != null){
			promocionGuardar.setCveNroregobraSatic(promocion.getCveNroregobraSatic());
		}
	}
	promocion = this.promocionServiceBean.agregar(promocionGuardar);
	
	return promocion;
}


/**
 * Metodo que se encarga de actualizar los datos del seguimiento SBC
 * @param crtPromocion
 * @return CrtPromocion
 * @author Enrique Duran Jimenez
 * @version 1.0.0
 */	
@RequestMapping(value="/GuardarSeguimientoSaticB" , method=RequestMethod.POST)
public @ResponseBody CrtPromocion GuardarSeguimientoSaticB(@RequestBody CrtPromocion promocion, HttpServletRequest request) {
		
	UserSession user = getUsuarioFirmado(request);
	CrtPromocion promocionGuardar = this.promocionServiceBean.consultaPorClave(promocion);
	if(promocionGuardar != null){
		if(promocion.getFechaNotificacion() != null){
			promocionGuardar.setFecFechanotif(Functions.FormateaFecha(promocion.getFechaNotificacion(), "-"));
		}else{
			promocionGuardar.setFecFechanotif(null);
		}
		
		promocionGuardar.setTxObservaciones(promocion.getTxObservaciones());		
		promocionGuardar.setFecFechareg(new Date());
		promocionGuardar.setCveUsuario(user.getCurpUsuario().toString());
		
		if(getDomicilioInegiSession(request) != null){
			Hashtable<String,Object> hDomicilios = (Hashtable)getDomicilioInegiSession(request);
			DgDomicilioGeografico domicilioGeo = (DgDomicilioGeografico)hDomicilios.get("DOM_SATICB_PATRON_OBRA");
			
			domicilioGeo = this.domiciliosInegiServiceBean.agregar(domicilioGeo);
			logger.debug("idDomicilioGeo   " + domicilioGeo.getDomicilioId());
			promocionGuardar.setCveDomGeoPatron(new BigDecimal(domicilioGeo.getDomicilioId()));
		}
		
	}
	promocion = this.promocionServiceBean.agregar(promocionGuardar);
	
	return promocion;
}


/**
 * Metodo que se encarga de actualizar los datos del seguimiento SBC
 * @param crtPromocion
 * @return CrtPromocion
 * @author Enrique Duran Jimenez
 * @version 1.0.0
 */	
@RequestMapping(value="/GuardarSeguimientoSBCRegulariza" , method=RequestMethod.POST)
public @ResponseBody CrtPromocion GuardarSeguimientoSBCRegulariza(@RequestBody CrtPromocion promocion, HttpServletRequest request) {
		
	UserSession user = getUsuarioFirmado(request);
	CrtPromocion promocionGuardar = this.promocionServiceBean.consultaPorClave(promocion);
	if(promocionGuardar != null){
		if(promocion.getFechaNotificacion() != null){
			promocionGuardar.setFecFechanotif(Functions.FormateaFecha(promocion.getFechaNotificacion(), "-"));
		}
		if(promocion.getFechaAtencion() != null){
			promocionGuardar.setFecFechaAtencion(Functions.FormateaFecha(promocion.getFechaAtencion(), "-"));
		}
		promocionGuardar.setTxObservaciones(promocion.getTxObservaciones());		
		promocionGuardar.setFecFechareg(new Date());
		promocionGuardar.setCveUsuario(user.getCurpUsuario().toString());
		promocionGuardar.setCveEstatus(CatEstatus.PROMOCION_REGULARIZADA.getId());
	}
	promocion = this.promocionServiceBean.agregar(promocionGuardar);
	
	return promocion;
}

// 	

/**
 * Metodo que se encarga de buscar la fecha de atencon del crtpromocion
 * @param crtPromocion
 * @return CrtPromocion
 * @author Enrique Duran Jimenez
 * @version 1.0.0
 */	
@RequestMapping(value="/buscaFechaAtencion" , method=RequestMethod.POST)
public @ResponseBody CrtPromocion buscaFechaAtencion(@RequestBody CrtPromocion promocion) {
	
	promocion = this.promocionServiceBean.consultaPorClave(promocion);
	if(promocion != null){
		promocion.setFechaAtencion(Functions.dateToString(promocion.getFecFechaAtencion()));
	}	
	
	return promocion;
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
private void reemplazaComas(PromocionSeguimientoGenericoVO genericoVO){
	
	if(genericoVO.getSegRegularizaObraVo().getTrabRevisados()!=null && !("").equals(genericoVO.getSegRegularizaObraVo().getTrabRevisados() )){
		genericoVO.getSegRegularizaObraVo().setTrabRevisados((genericoVO.getSegRegularizaObraVo().getTrabRevisados()).replace(",","" ).trim());
	}
	if(genericoVO.getSegRegularizaObraVo().getTrabOmisos()!=null && !("").equals(genericoVO.getSegRegularizaObraVo().getTrabOmisos() )){
		genericoVO.getSegRegularizaObraVo().setTrabOmisos((genericoVO.getSegRegularizaObraVo().getTrabOmisos()).replace(",","" ).trim());
	}
	if(genericoVO.getSegRegularizaObraVo().getTrabSubdeclarados()!=null && !("").equals(genericoVO.getSegRegularizaObraVo().getTrabSubdeclarados() )){
		genericoVO.getSegRegularizaObraVo().setTrabSubdeclarados((genericoVO.getSegRegularizaObraVo().getTrabSubdeclarados()).replace(",","" ).trim());
	}	
	
	if(genericoVO.getSegRegularizaObraVo().getTrabRegularizados()!=null && !("").equals(genericoVO.getSegRegularizaObraVo().getTrabRegularizados() )){
		genericoVO.getSegRegularizaObraVo().setTrabRegularizados((genericoVO.getSegRegularizaObraVo().getTrabRegularizados()).replace(",","" ).trim());
		
	}
	if(genericoVO.getSegRegularizaObraVo().getBaseDeterminada()!=null && !("").equals(genericoVO.getSegRegularizaObraVo().getBaseDeterminada() )){
		genericoVO.getSegRegularizaObraVo().setBaseDeterminada((genericoVO.getSegRegularizaObraVo().getBaseDeterminada()).replace(",","" ).trim());	
	}
	
	if(genericoVO.getSegRegularizaObraVo().getSuertePpalDetCOP()!=null && !("").equals(genericoVO.getSegRegularizaObraVo().getSuertePpalDetCOP() )){
		genericoVO.getSegRegularizaObraVo().setSuertePpalDetCOP((genericoVO.getSegRegularizaObraVo().getSuertePpalDetCOP()).replace(",","" ).trim());
	}
	if(genericoVO.getSegRegularizaObraVo().getSuertePpalDetRCV()!=null && !("").equals(genericoVO.getSegRegularizaObraVo().getSuertePpalDetRCV() )){
		genericoVO.getSegRegularizaObraVo().setSuertePpalDetRCV((genericoVO.getSegRegularizaObraVo().getSuertePpalDetRCV()).replace(",","" ).trim());
	}
	
}

/**
 * Metodo que elimina las comas de los campos de cantidades de los pagos , de la pestaña de anexar pagos, 
 * antes de realizar los calculos, las comas llegan por el formato de cantidades (miles) manejados en pantalla
 * 
 * @param PromocionSeguimientoGenericoVO
 * @author Oscar Beltran Ortega
 * @return void
 * @version 1.0.0
 */
	private void reemplazaComasPagoDetalle(PromocionSeguimientoGenericoVO genericoVO){
		if(genericoVO.getAnexoPagosVO().isSeccionCOP()){
			if(genericoVO.getAnexoPagosVO().getSpCOP()!=null && !("").equals(genericoVO.getAnexoPagosVO().getSpCOP() )){
				genericoVO.getAnexoPagosVO().setSpCOP((genericoVO.getAnexoPagosVO().getSpCOP()).replace(",","" ).trim());	
			}
			if(genericoVO.getAnexoPagosVO().getActCOP()!=null && !("").equals(genericoVO.getAnexoPagosVO().getActCOP() )){
				genericoVO.getAnexoPagosVO().setActCOP((genericoVO.getAnexoPagosVO().getActCOP()).replace(",","" ).trim());	
			}
			if(genericoVO.getAnexoPagosVO().getRecCOP()!=null && !("").equals(genericoVO.getAnexoPagosVO().getRecCOP() )){
				genericoVO.getAnexoPagosVO().setRecCOP((genericoVO.getAnexoPagosVO().getRecCOP()).replace(",","" ).trim());	
			}
			if(genericoVO.getAnexoPagosVO().getMultasCOP()!=null && !("").equals(genericoVO.getAnexoPagosVO().getMultasCOP() )){
				genericoVO.getAnexoPagosVO().setMultasCOP((genericoVO.getAnexoPagosVO().getMultasCOP()).replace(",","" ).trim());	
			}
		}
		if(genericoVO.getAnexoPagosVO().isSeccionRCV()){
			if(genericoVO.getAnexoPagosVO().getSpRCV()!=null && !("").equals(genericoVO.getAnexoPagosVO().getSpRCV() )){
				genericoVO.getAnexoPagosVO().setSpRCV((genericoVO.getAnexoPagosVO().getSpRCV()).replace(",","" ).trim());	
			}
			if(genericoVO.getAnexoPagosVO().getActRCV()!=null && !("").equals(genericoVO.getAnexoPagosVO().getActRCV() )){
				genericoVO.getAnexoPagosVO().setActRCV((genericoVO.getAnexoPagosVO().getActRCV()).replace(",","" ).trim());	
			}
			if(genericoVO.getAnexoPagosVO().getRecRCV()!=null && !("").equals(genericoVO.getAnexoPagosVO().getRecRCV() )){
				genericoVO.getAnexoPagosVO().setRecRCV((genericoVO.getAnexoPagosVO().getRecRCV()).replace(",","" ).trim());	
			}
			if(genericoVO.getAnexoPagosVO().getMultasRCV()!=null && !("").equals(genericoVO.getAnexoPagosVO().getMultasRCV() )){
				genericoVO.getAnexoPagosVO().setMultasRCV((genericoVO.getAnexoPagosVO().getMultasRCV()).replace(",","" ).trim());	
			}
			
			
		}
		
		if(genericoVO.getAnexoPagosVO().getTrabRegularizados()!=null && !("").equals(genericoVO.getAnexoPagosVO().getTrabRegularizados() )){
			genericoVO.getAnexoPagosVO().setTrabRegularizados((genericoVO.getAnexoPagosVO().getTrabRegularizados()).replace(",","" ).trim());	
		}
		if(genericoVO.getAnexoPagosVO().getAltas()!=null && !("").equals(genericoVO.getAnexoPagosVO().getAltas() )){
			genericoVO.getAnexoPagosVO().setAltas((genericoVO.getAnexoPagosVO().getAltas()).replace(",","" ).trim());	
		}
		if(genericoVO.getAnexoPagosVO().getBajas()!=null && !("").equals(genericoVO.getAnexoPagosVO().getBajas() )){
			genericoVO.getAnexoPagosVO().setBajas((genericoVO.getAnexoPagosVO().getBajas()).replace(",","" ).trim());	
		}
		if(genericoVO.getAnexoPagosVO().getModifSalario()!=null && !("").equals(genericoVO.getAnexoPagosVO().getModifSalario() )){
			genericoVO.getAnexoPagosVO().setModifSalario((genericoVO.getAnexoPagosVO().getModifSalario()).replace(",","" ).trim());	
		}

	}
	
	
	
	@RequestMapping(value="/paginarIncidencias", method=RequestMethod.POST )
    public @ResponseBody DatosSalidaPaginador<SegEOIncidenciasObraVO> paginarIncidencias(@RequestBody CrtIncidenciasWrapperDataTable wrapper ) {
		logger.info(".-.-controller public @ResponseBody DatosSalidaPaginador<SegEOIncidenciasObraVO> pagina(@RequestBody CrtIncidenciasWrapperDataTable wrapper ) {");
		DatosEntradaPaginador<SegEOIncidenciasObraGenericoVO> send = new DatosEntradaPaginador<SegEOIncidenciasObraGenericoVO>();
		DatosSalidaPaginador<SegEOIncidenciasObraVO> reply = new DatosSalidaPaginador<SegEOIncidenciasObraVO>();
		reply.setAaData(new ArrayList<SegEOIncidenciasObraVO>());
		reply.setiTotalDisplayRecords(0);
		reply.setiTotalRecords(0);	
		
		send.parserArray(wrapper.getAoData());
		send.setModelo(wrapper.getoForm());	


		if(wrapper.getoForm().getNumeroDeRegistroDeObra()!= null){
			logger.info(".-.-NumRegistroObra=" + wrapper.getoForm().getNumeroDeRegistroDeObra());
			Long lNumRegObra = Long.parseLong(wrapper.getoForm().getNumeroDeRegistroDeObra());
			reply = this.obraService.buscaIncidencias(send, lNumRegObra);
			
			if(reply.getAaData().size()==0){
				reply.setAaData(new ArrayList<SegEOIncidenciasObraVO>());
				reply.setiTotalDisplayRecords(0);
				reply.setiTotalRecords(0);				
			}
			
		}
		
		logger.info(".-.-controller realizo consulta pagina incidencias) {");
        reply.setsEcho(send.getsEcho());
        
        return reply;
    }
	
	
	@RequestMapping(value="/paginarPeriodosPresentados", method=RequestMethod.POST )
    public @ResponseBody DatosSalidaPaginador<SegEOPeriodosPresentadosObraVO> paginarPeriodosPresentados(@RequestBody CrtPeriodosPresentadosWrapperDataTable wrapper ) {
		logger.info(".-.-controller public @ResponseBody DatosSalidaPaginador<SegEOIncidenciasObraVO> pagina(@RequestBody CrtIncidenciasWrapperDataTable wrapper ) {");
		DatosEntradaPaginador send = new DatosEntradaPaginador();
		DatosSalidaPaginador<SegEOPeriodosPresentadosObraVO> reply = new DatosSalidaPaginador<SegEOPeriodosPresentadosObraVO>();
		reply.setAaData(new ArrayList<SegEOPeriodosPresentadosObraVO>());
		reply.setiTotalDisplayRecords(0);
		reply.setiTotalRecords(0);	
		
		send.parserArray(wrapper.getAoData());
		send.setModelo(wrapper.getoForm());	

		if(wrapper.getoForm().getNumeroDeRegistroDeObra()!= null){
			logger.info(".-.-NumRegistroObra=" + wrapper.getoForm().getNumeroDeRegistroDeObra());
			Long lNumRegObra = Long.parseLong(wrapper.getoForm().getNumeroDeRegistroDeObra());
			reply = this.obraService.buscaPeriodosPresentados(send, lNumRegObra);
			
			if(reply.getAaData().size()==0){
				reply.setAaData(new ArrayList<SegEOPeriodosPresentadosObraVO>());
				reply.setiTotalDisplayRecords(0);
				reply.setiTotalRecords(0);				
			}
			
		}
		
		logger.info(".-.-controller realizo consulta pagina periodos presentados) {");
        reply.setsEcho(send.getsEcho());
        
        return reply;
    }


@RequestMapping(value="/mandarARegularizar" , method=RequestMethod.POST)
public @ResponseBody PromocionSeguimientoGenericoVO mandarARegularizar(@RequestBody PromocionSeguimientoGenericoVO promocionSeguimiento, HttpServletResponse response, HttpServletRequest request) {

	UserSession user = getUsuarioFirmado(request);
	CrtPromocion promocionEjemplo = new CrtPromocion();
	Long clavePromocion = Long.parseLong(promocionSeguimiento.getCvePromocion());
	logger.debug("clave promocion=" +  clavePromocion);
	promocionEjemplo.setCvePromocion(clavePromocion);
	CrtPromocion promocionRegistrada =  this.promocionServiceBean.consultaPorClave(promocionEjemplo);
	if(promocionRegistrada !=null){
		promocionRegistrada.setFecFechaAtencion(Functions.FormateaFecha(promocionSeguimiento.getEstatusObraVO().getFechaAtencionOficio(), "-"));
		promocionRegistrada.setFecFechareg(new Date());
		promocionRegistrada.setCveUsuario(user.getCurpUsuario().toString());
		if (promocionSeguimiento.getEstatusObraVO().isRegularizarObra()) {
			promocionRegistrada.setIdRegulaObra(1L);
			promocionRegistrada.setCveEstatus(CatEstatus.PROMOCION_REGULARIZADA.getId());
		}
		try{
		  this.promocionServiceBean.modificar(promocionRegistrada);
		}catch(Exception e){
			e.printStackTrace();
			promocionSeguimiento.setError("Error al guardar la promoción:" + e);
		}
	}else{
		promocionSeguimiento.setError("No se encuentra promoción con clave " );
	}
	
	return promocionSeguimiento;

}

/**
 * Metodo ejecutado desde el checkbox de la pestaña generica de Estatus Obra , al seleccionar el chekbox
 * de Regularizar Obra, guarda la informacion de estatus Obra y la de los demas tabs activos y con datos 
 * hasta ese mmomento
 * 
 * @param 
 * @author Oscar Beltran Ortega
 * @return PromocionSeguimientoGenericoVO
 * @version 1.0.0
 */
@RequestMapping(value="/mandarARegularizarCheckBox" , method=RequestMethod.POST)
public @ResponseBody PromocionSeguimientoGenericoVO guardaARegularizarCheck(@RequestBody PromocionSeguimientoGenericoVO promocionSeguimiento, HttpServletResponse response, HttpServletRequest request) {

	UserSession user = getUsuarioFirmado(request);
	CrtPromocion promocionEjemplo = new CrtPromocion();
	Long clavePromocion = Long.parseLong(promocionSeguimiento.getCvePromocion());
	logger.debug("clave promocion=" +  clavePromocion);
	promocionEjemplo.setCvePromocion(clavePromocion);
	CrtPromocion promocionRegistrada =  this.promocionServiceBean.consultaPorClave(promocionEjemplo);
	if(promocionRegistrada !=null){
		promocionRegistrada.setFecFechaAtencion(Functions.FormateaFecha(promocionSeguimiento.getEstatusObraVO().getFechaAtencionOficio(), "-"));
		promocionRegistrada.setFecFechareg(new Date());
		promocionRegistrada.setCveUsuario(user.getCurpUsuario().toString());
		 
		if(promocionSeguimiento.getEstatusObraVO().isRegularizarObra()){
			promocionRegistrada.setIdRegulaObra(1L);
			promocionRegistrada.setCveEstatus(CatEstatus.PROMOCION_REGULARIZADA.getId());
		}else{
			promocionRegistrada.setIdRegulaObra(0L);
			promocionRegistrada.setCveEstatus(CatEstatus.EN_PROCESO_NOTIFICACION_OFICIO_PROMOCION.getId());
		}
		
		try{
		  this.promocionServiceBean.modificar(promocionRegistrada);
		}catch(Exception e){
			e.printStackTrace();
			promocionSeguimiento.setError("Error al guardar la promoción:" + e);
		}
	}else{
		promocionSeguimiento.setError("No se encuentra promoción con clave " );
	}
	
	return promocionSeguimiento;

}

/*Se agrega parámetro al getSumarizado NULL al final ya que no es promoción*/
	@RequestMapping(value="/obtenerTotalesPagosPromocion", method=RequestMethod.POST)
	public @ResponseBody CrtRevPagos obtenerSumarizadoPagosSegPromocion (@RequestBody SegRegularizarObraGenericoTabVO regularizaVO, HttpServletResponse response,HttpServletRequest request) {
	
		CrtRevPagos pagoTotal = null;
	
		if(regularizaVO.getCveRegulaPagos() != null && !regularizaVO.getCveRegulaPagos().equals("")){
			logger.info("bandera tipo pago" + regularizaVO.getBanderaTipoPago());
			try {
				pagoTotal = pagosService.getSumarizado(null,regularizaVO.getBanderaTipoPago(),Integer.valueOf(regularizaVO.getCveRegulaPagos()));	
			
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
				
		return pagoTotal;
	}
}