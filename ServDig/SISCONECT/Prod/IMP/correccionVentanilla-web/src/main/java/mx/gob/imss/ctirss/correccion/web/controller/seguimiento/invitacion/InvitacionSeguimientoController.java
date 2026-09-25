/**
 * 
 */
package mx.gob.imss.ctirss.correccion.web.controller.seguimiento.invitacion;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mx.gob.imss.ctirss.correccion.base.paginador.model.DatosEntradaPaginador;
import mx.gob.imss.ctirss.correccion.base.paginador.model.DatosSalidaPaginador;
import mx.gob.imss.ctirss.correccion.catalogos.model.CrcTipoCorr;
import mx.gob.imss.ctirss.correccion.commons.paginador.seguimiento.invitacion.InvitacionesSegWrapperDataTable;
import mx.gob.imss.ctirss.correccion.commons.vo.seguimiento.invitacion.InvitacionSeguimientoVO;
import mx.gob.imss.ctirss.correccion.deteccion.model.CrtDeteccion;
import mx.gob.imss.ctirss.correccion.deteccion.service.interfaces.DeteccionService;
import mx.gob.imss.ctirss.correccion.framework.base.controller.AbstractController;
import mx.gob.imss.ctirss.correccion.framework.base.model.AbstractModel;
import mx.gob.imss.ctirss.correccion.framework.utils.ConstantesBusiness;
import mx.gob.imss.ctirss.correccion.framework.utils.enums.CatEstatus;
import mx.gob.imss.ctirss.correccion.invitacion.service.interfaces.InvitacionService;
import mx.gob.imss.ctirss.correccion.model.CrtCorrPromInvita;
import mx.gob.imss.ctirss.correccion.model.CrtInvitacion;
import mx.gob.imss.ctirss.correccion.model.CrtInvitacionRP;
import mx.gob.imss.ctirss.correccion.model.CrtSolicitudcorr;
import mx.gob.imss.ctirss.correccion.model.SatPatron;
import mx.gob.imss.ctirss.correccion.model.SatUbicacion;
import mx.gob.imss.ctirss.correccion.presentacion.service.interfaces.PresentacionCorreccionServiceController;
import mx.gob.imss.ctirss.correccion.promocion.model.CrtPromocion;
import mx.gob.imss.ctirss.correccion.promocion.service.interfaces.PromocionService;
import mx.gob.imss.ctirss.correccion.service.interfaces.ICatalogoService;
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
 *  
 * Clase controladora de los eventos ejecutados para el seguimiento de las invitaciones
 * @author CesarAgustin
 * @version 1.0.0
 * @since 30/06/2012
 *
 */
@Controller
@RequestMapping("/seguimiento/seginvitacion")
public class InvitacionSeguimientoController extends AbstractController {

	private static final Logger LOGGER = Logger.getLogger(InvitacionSeguimientoController.class);
	
//	@Autowired
//	private CatalogosService catalogosServiceBean;
	
	@Autowired
	private ICatalogoService<CrcTipoCorr> catalogosServiceBean;
	@Autowired
	private InvitacionService<CrtInvitacion> invService;
	@Autowired
	private DeteccionService<CrtDeteccion> deteccionService;
	@Autowired
	private ICatalogoService<CrtInvitacionRP> invitacionRPServiceBean;	
	@Autowired 
	IPatronesService<SatPatron> patronService;
	@Autowired
	private	IPatronesService patronesService;
	@Autowired 
	private DomiciliosInegiService<DgDomicilioGeografico> domiciliosInegiServiceBean;	
	@Autowired
	private ICatalogoService<AbstractModel> iCatalogoServiceBean;
	@Autowired
	private PresentacionCorreccionServiceController presentacionService;
	@Autowired
	private InvitacionService<SatUbicacion> invUbicacionService;
	
	@Autowired
	private PromocionService<CrtPromocion> promocionServie;
	
	
	@RequestMapping(method=RequestMethod.GET)
	public String getCreateForm(Model model, HttpServletRequest request) {
		
		InvitacionSeguimientoVO invSegVO = new InvitacionSeguimientoVO();
		
		model.addAttribute(invSegVO);
		model.addAttribute("crtInvitacion",new CrtInvitacion());
		
		return "invitacion/seguimiento/segInvitacionMain";
	}
	
	@RequestMapping(value="/selecTiposCorr", method=RequestMethod.POST)
	public @ResponseBody List<CrcTipoCorr> listatipoCorr(HttpServletRequest request,
			HttpServletResponse response) {
		LOGGER.info("/**** Cargar tipos corr de invitacion ****/");
		
//		List<CrcTipoCorr> tiposCorrInv = catalogosServiceBean.getTiposCorreccion(Long.valueOf(4)); 
		
		List<CrcTipoCorr> tiposCorrInv =  this.catalogosServiceBean.consultaSQL(" select t.CVE_TIPOCORR,t.ID_TIPOCORR,t.TX_DESCRIPCION from CRC_TIPOCORR t where t.ID_TIPOCORR in (4) order by t.TX_DESCRIPCION ");
		
		return tiposCorrInv;
	}
	
	@RequestMapping(value="/consultaInvicaciones", method=RequestMethod.POST)
	public @ResponseBody DatosSalidaPaginador<CrtInvitacion> invitacionesPorFiltros(@RequestBody InvitacionesSegWrapperDataTable aoData, HttpServletRequest request,
			HttpServletResponse response) {
		
		UserSession user = getUsuarioFirmado(request);
		aoData.getoForm().setIdSubDelegacion(user.getIdSubDelegacion());
		aoData.getoForm().setCveIdUsuario(user.getCveIdUsuario());
		aoData.getoForm().setCveRol(user.getCveRol());
		List<CrtInvitacion> invitaciones = new ArrayList<CrtInvitacion>();
	
		LOGGER.info("/*** Tipo correccion"+aoData.getoForm().getIdTipoCorr());
		
		if (!aoData.getoForm().getIdTipoCorr().equals("0")) {
			String tipoCorrTx = aoData.getoForm().getIdTipoCorr().substring(11,aoData.getoForm().getIdTipoCorr().length());
			aoData.getoForm().setIdTipoCorr(tipoCorrTx);
		}
		invitaciones =  invService.consultaInvitacionesPorFiltros(aoData.getoForm());
		
		DatosEntradaPaginador send = new DatosEntradaPaginador();
		
		send.parserArray(aoData.getAoData());
		send.setModelo(aoData.getoForm());
		
		DatosSalidaPaginador<CrtInvitacion> reply = new DatosSalidaPaginador<CrtInvitacion>();
		
		reply.setAaData(invitaciones);
		reply.setiTotalRecords(invitaciones.size());
		reply.setiTotalDisplayRecords(invitaciones.size());
		
		
		return reply;
	}
	
	@RequestMapping(value="/obtenerFechaServidor.do", method=RequestMethod.POST)
	public @ResponseBody String obtenerFechaServidor(HttpServletRequest request){
		return ConstantesBusiness.dateToStringFormat(new Date(), ConstantesBusiness.dd_mm_yyyy);
	}
	
	@RequestMapping(value="/obtenerFechaServidorMinima.do", method=RequestMethod.POST)
	public @ResponseBody String obtenerFechaServidorMinima(HttpServletRequest request){
		Calendar calendario = Calendar.getInstance();
		calendario.add(Calendar.DAY_OF_YEAR, -44);
		Date dia = calendario.getTime();
		return ConstantesBusiness.dateToStringFormat(dia, ConstantesBusiness.dd_mm_yyyy);
	}
	
	@RequestMapping(value="/detalleInvitacion", method=RequestMethod.POST)
	public @ResponseBody CrtInvitacion detalleInvitacion (@RequestBody CrtInvitacion invitacion, HttpServletRequest request,
			HttpServletResponse response) {
		
		LOGGER.info("/**** Controlador para obtener el detalle de una invitacion con ID :: ****/"+invitacion.getCveInvitacion());
		
		CrtInvitacion invitacionResult = invService.consultaPorClave(invitacion);
		
		UserSession user = getUsuarioFirmado(request);
		invitacionResult.setCveUsuario(user.getNombreCompleto());
		
//		String queryRol="select CVE_ROL from seg_perfil_usuario WHERE CVE_ID_USUARIO = "+ user.getCveIdUsuario().toString();			
//		ArrayList listaFuncionarios = (ArrayList) iCatalogoServiceBean.consultaSQL(queryRol);
//		String rol = listaFuncionarios.get(0).toString();
		

		String rol = String.valueOf(user.getCveRol());
		
		invitacionResult.setRolUsuario(rol);
		
		if (invitacionResult.getFecFechaemision()!=null) {
			invitacionResult.setFechaOfInvitacionTx(Functions.dateToString2(invitacionResult.getFecFechaemision()));		
		}
		if (invitacionResult.getFecPeriodoIni()!=null) {
			invitacionResult.setFechaIncial(Functions.dateToString2(invitacionResult.getFecPeriodoIni()));
		}
		if (invitacionResult.getFecPeriodoFin()!=null) {
			invitacionResult.setFechaFinal(Functions.dateToString2(invitacionResult.getFecPeriodoFin()));
		}
		if (invitacionResult.getFechaAisoDictamen()!=null) {
			invitacionResult.setFechaAisoDictamenTx(Functions.dateToString2(invitacionResult.getFechaAisoDictamen()));
		} 
		if (invitacionResult.getFechaDerSubdelegacion()!=null) {
			invitacionResult.setFechaDerSubdelegacionTx(Functions.dateToString2(invitacionResult.getFechaDerSubdelegacion()));
		}
		if (invitacionResult.getFecCancelacion()!=null) {
			invitacionResult.setFechaCancelacionTx(Functions.dateToString2(invitacionResult.getFecCancelacion()));
		}
		if (invitacionResult.getFecFechanotifi()!=null) {
			invitacionResult.setFechaNotOficioTx(Functions.dateToString2(invitacionResult.getFecFechanotifi()));
		}
		if (invitacionResult.getFechaPeriodoFInDic()!=null) {
			invitacionResult.setFechaPeriodoFinDicTx(Functions.dateToString2(invitacionResult.getFechaPeriodoFInDic()));
		}
		if (invitacionResult.getFechaPeriodoIniDic()!=null) {
			invitacionResult.setFechaPeriodoIniDicTx(Functions.dateToString2(invitacionResult.getFechaPeriodoIniDic()));
		}
		if (invitacionResult.getFecchaPai()!=null) {
			invitacionResult.setFechaPaiTx(Functions.dateToString2(invitacionResult.getFecchaPai()));
		}
		
		List<CrtSolicitudcorr> correccionesInvitacion = presentacionService.obtenerCorrInvitacionPorCveInvitacion(invitacionResult.getCveInvitacion());
		
		if (correccionesInvitacion!=null && !correccionesInvitacion.isEmpty()) {
			invitacionResult.setFechaCorreccionTx(Functions.dateToString2(correccionesInvitacion.get(0).getFecFechaElacoracionCorreccion()));
			invitacionResult.setFechaInicioCorreccionTx(Functions.dateToString2(correccionesInvitacion.get(0).getFecFechaPeriodoIni()));
			invitacionResult.setFechaFinCorreccionTx(Functions.dateToString2(correccionesInvitacion.get(0).getFecFechaPeriodoFin()));
		}
		//
		DgDomicilioGeografico domicilioGeografico = new DgDomicilioGeografico(); 
		if(invitacionResult.getCveDeteccion()!=null){
			CrtDeteccion crtDeteccion = new CrtDeteccion();
			crtDeteccion.setCveDeteccion(invitacionResult.getCveDeteccion().longValue());
			crtDeteccion = deteccionService.consultaPorClave(crtDeteccion);
			domicilioGeografico.setDomicilioId(crtDeteccion.getDomicilioId());
		}else if(invitacionResult.getCvePromocion()!=null){
			CrtPromocion crtPromocion = new CrtPromocion();
			crtPromocion.setCvePromocion(invitacionResult.getCvePromocion().longValue());
			crtPromocion = promocionServie.consultaPorClave(crtPromocion);
			if(crtPromocion.getCveDomGeoPatron() != null){
				domicilioGeografico.setDomicilioId(crtPromocion.getCveDomGeoPatron().longValue());	
			}
			
		}
		
		
		domicilioGeografico = domiciliosInegiServiceBean.consultaPorClave(domicilioGeografico);
		if(domicilioGeografico!=null && domicilioGeografico.getDomicilioBDTU()==null){
			domicilioGeografico=null;
		}
		
		
		String dom = "";
		
		if(domicilioGeografico!=null)
		{
			String numExtCompleto ="";
			String numIntCompleto ="";
			
			numExtCompleto = (domicilioGeografico.getNumextnum()!=null?domicilioGeografico.getNumextnum()+"":"");
			numExtCompleto+= (domicilioGeografico.getNumextalf()!=null?("-"+domicilioGeografico.getNumextalf())+"":"");
			
			numIntCompleto = (domicilioGeografico.getNumintnum()!=null?domicilioGeografico.getNumintnum()+"":"");
			numIntCompleto+= (domicilioGeografico.getNumintalf()!=null?("-"+domicilioGeografico.getNumintalf())+"":"");
			
			dom = dom + domicilioGeografico.getDgVialidadByCveViaPrin().getNomVia();
			dom = dom +" " + numExtCompleto;
			dom = dom +" "+ numIntCompleto;
			dom = dom +" "+domicilioGeografico.getDgAsentamiento().getNomAsen();
			dom = dom +" "+domicilioGeografico.getDgCatLocalidad().getNomLoc();
			dom = dom +" "+domicilioGeografico.getDgCatLocalidad().getDgCatMunicipio().getNomMun();
			dom = dom +" "+domicilioGeografico.getDgCatLocalidad().getDgCatMunicipio().getDgCatEstado().getNomEnt();
			dom = dom +" "+domicilioGeografico.getDgCodigosPostales().getId().getCodigo();
		}
		
		invitacionResult.setDomicilioObra(dom);
		
		//
		if (invitacionResult.getSatPatron()!=null) {
			LOGGER.info("/**** Se obtuvo la ubicacion con ID :: "+invitacionResult.getSatPatron().getFkUbicacion());								
			
//			SatUbicacion ubicacion = invService.obtenerUbicacionPorId(invitacionResult.getSatPatron().getFkUbicacion());
			SatUbicacion ubicacionEnvio = new SatUbicacion();
			ubicacionEnvio.setPatron(invitacionResult.getSatPatron());
			
			SatUbicacion ubicacion = invUbicacionService.obtieneUbicacionPatron(ubicacionEnvio);
			
			
			StringBuffer domicilio = new StringBuffer();
			domicilio.append(ubicacion.getCalle()!=null?ubicacion.getCalle():"")
					.append(" C.P ").append(ubicacion.getCodigoPostal());
			invitacionResult.setDomicilio(domicilio.toString());
		}
			
		return invitacionResult;
	}
	
	@RequestMapping(value="/consultaInvitacionesRP", method=RequestMethod.POST)
	public @ResponseBody  DatosSalidaPaginador<CrtInvitacion> consultaInvitacionesRP (@RequestBody InvitacionesSegWrapperDataTable aoData, HttpServletRequest request,
			HttpServletResponse response) {
		
		LOGGER.info("/**** Controlador las invitaciones RPS de la invitacion :: ****/"+aoData.getoForm().getFolioInvitacion());
		
		List<CrtInvitacionRP> invitacionesRP = invitacionRPServiceBean.consultaSQL("select CVE_FK_PATRON from CRT_INVITACION_RP where CVE_INVITACION = "+aoData.getoForm().getFolioInvitacion());
		
		List<CrtInvitacion> listaRetorno = new ArrayList<CrtInvitacion>();
		for (int i=0; i<invitacionesRP.size(); i++) {
			CrtInvitacion nuevoItem = new CrtInvitacion();
			LOGGER.info("/**** Patron con ID :: ****/"+invitacionesRP.get(i));
			
			Object objetoR = invitacionesRP.get(i);			
			SatPatron patron = patronService.getById(Long.valueOf(String.valueOf(objetoR)));
			
			nuevoItem.setRegPatronal(patron.getRegistroPatronal());
			
			SatUbicacion ubicacionEnvio = new SatUbicacion();
			ubicacionEnvio.setPatron(patron);
						
			SatUbicacion ubicacion = patron.getUbicacion();
									
			StringBuffer domicilio = new StringBuffer();
			domicilio.append(ubicacion.getCalle()!=null?ubicacion.getCalle():"")
					.append(" Numero Ext ").append(ubicacion.getNumeroExterior()!=null?ubicacion.getNumeroExterior():"")
					.append(" Numero Int ").append(ubicacion.getNumeroInterior()!=null?ubicacion.getNumeroInterior():"")
					.append(" Colonia ").append(ubicacion.getColonia()!=null?ubicacion.getColonia():"")
					.append(" C.P. ").append(ubicacion.getCodigoPostal()!=null?ubicacion.getCodigoPostal():"");
			nuevoItem.setDomicilio(domicilio.toString());
			
			listaRetorno.add(nuevoItem);			
		}	
		
		DatosEntradaPaginador send = new DatosEntradaPaginador();
		
		send.parserArray(aoData.getAoData());
		send.setModelo(aoData.getoForm());
		
		DatosSalidaPaginador<CrtInvitacion> reply = new DatosSalidaPaginador<CrtInvitacion>();
		
		reply.setAaData(listaRetorno);
		reply.setiTotalRecords(listaRetorno.size());
		reply.setiTotalDisplayRecords(listaRetorno.size());
		
		reply.setsEcho(send.getsEcho());
		
		return reply;
	}
	
	@RequestMapping(value="/guardaSeguimientoInvitacion", method=RequestMethod.POST)
	public @ResponseBody CrtInvitacion guardaSeguimientoInvitacion (@RequestBody CrtInvitacion invitacion, HttpServletRequest request,
			HttpServletResponse response) {
		
		UserSession user = getUsuarioFirmado(request);
		
		LOGGER.info("/**** Clave invitacion :: "+invitacion.getCveInvitacion());
		LOGGER.info("/**** Observaciones :: "+invitacion.getTxObservaciones());
		LOGGER.info("/**** Fecha notifi ofi :: "+invitacion.getFechaNotOficioTx());
		
		CrtInvitacion invitacionBD = invService.consultaPorClave(invitacion);
		
		invitacionBD.setCveUsuario(user.getCurpUsuario().toString());
		invitacionBD.setFecFechareg(Functions.stringToDate(ConstantesBusiness.dateToStringFormat(new Date(), ConstantesBusiness.dd_mm_yyyy)));
		invitacionBD.setTxObservaciones(invitacion.getTxObservaciones());
		invitacionBD.setFecFechanotifi(Functions.stringToDate(invitacion.getFechaNotOficioTx()));		
		
		Boolean actualizado = invService.actualizaSeguimientoInvitacion(invitacionBD);
		
		if (actualizado) {
			return invitacionBD;
		}
		return null;
	}
	
	@RequestMapping(value="/cancelaSeguimientoInvitacion", method=RequestMethod.POST)
	public @ResponseBody CrtInvitacion cancelaSeguimientoInvitacion (@RequestBody CrtInvitacion invitacion, HttpServletRequest request,
			HttpServletResponse response) {
				
		LOGGER.info("/**** Clave invitacion :: "+invitacion.getCveInvitacion());
		LOGGER.info("/**** Fecha de cancelacion :: "+invitacion.getFechaCancelacionTx());
		LOGGER.info("/**** Usuario :: "+invitacion.getCveUsuario());
		LOGGER.info("/**** Usuario Autoriza :: "+invitacion.getCveUsuarioAutoriza());
		LOGGER.info("/**** Motivo cancela :: "+invitacion.getIdMotivoCancelacion());
		LOGGER.info("/**** Referencia cancela"+invitacion.getNumOficioCancelacion());
		
		CrtInvitacion invitacionBD = invService.consultaPorClave(invitacion);
		UserSession user = getUsuarioFirmado(request);
		
		invitacionBD.setCveUsuario(user.getCurpUsuario().toString());	
		invitacionBD.setFecFechareg(Functions.stringToDate(ConstantesBusiness.dateToStringFormat(new Date(), ConstantesBusiness.dd_mm_yyyy)));
		invitacionBD.setFecCancelacion(Functions.stringToDate(invitacion.getFechaCancelacionTx()));		
		invitacionBD.setCveUsuarioAutoriza(invitacion.getCveUsuarioAutoriza());
		invitacionBD.setIdMotivoCancelacion(invitacion.getIdMotivoCancelacion());
		invitacionBD.setNumOficioCancelacion(invitacion.getNumOficioCancelacion());
		invitacionBD.setCveEstatus(CatEstatus.FOLIO_CANCELADO.getId());
		invitacionBD.setTxObservaciones(invitacion.getTxObservaciones());
		
		if(invitacion.getFechaNotOficioTx()!=null && !invitacion.getFechaNotOficioTx().equals(""))
			invitacionBD.setFecFechanotifi(Functions.stringToDate(invitacion.getFechaNotOficioTx()));
		
		Boolean actualizado = invService.actualizaSeguimientoInvitacion(invitacionBD);
		
		if (actualizado) {
			return invitacionBD;
		}
		return null;
	}
	
	@RequestMapping(value="/derivaFiscalSegInvitacion", method=RequestMethod.POST)
	public @ResponseBody CrtInvitacion derivaFiscalSegInvitacion (@RequestBody CrtInvitacion invitacion, HttpServletRequest request,
			HttpServletResponse response) {
			
		LOGGER.info("/**** Clave invitacion :: "+invitacion.getCveInvitacion());
		LOGGER.info("/**** Fecha derivacion sub :: "+invitacion.getFechaPaiTx());
		LOGGER.info("/**** Referecnia :: "+invitacion.getTxRfrpai());
		
		CrtInvitacion invitacionBD = invService.consultaPorClave(invitacion);
		UserSession user = getUsuarioFirmado(request);
		
		invitacionBD.setCveUsuario(user.getCurpUsuario().toString());	
		invitacionBD.setFecFechareg(Functions.stringToDate(ConstantesBusiness.dateToStringFormat(new Date(), ConstantesBusiness.dd_mm_yyyy)));
		invitacionBD.setFecchaPai(Functions.stringToDate(invitacion.getFechaPaiTx()));
		invitacionBD.setTxRfrpai(invitacion.getTxRfrpai());
		invitacionBD.setCveEstatus(CatEstatus.CORRECCION_DERIVADA_FISCALIZACION.getId());
		invitacionBD.setTxObservaciones(invitacion.getTxObservaciones());
		if(invitacion.getFechaNotOficioTx()!=null && !invitacion.getFechaNotOficioTx().equals(""))
			invitacionBD.setFecFechanotifi(Functions.stringToDate(invitacion.getFechaNotOficioTx()));
		
		Boolean actualizado = invService.actualizaSeguimientoInvitacion(invitacionBD);
		
		if (actualizado) {
			return invitacionBD;
		}
		return null;
	}
	
	@RequestMapping(value="/autAviDictamenSegInvitacion", method=RequestMethod.POST)
	public @ResponseBody CrtInvitacion autAviDictamenSegInvitacion (@RequestBody CrtInvitacion invitacion, HttpServletRequest request,
			HttpServletResponse response) {
			
		LOGGER.info("/**** Clave invitacion :: "+invitacion.getCveInvitacion());
		LOGGER.info("/**** Fecha aut aviso dict :: "+invitacion.getFechaAisoDictamenTx());
		LOGGER.info("/**** Fecha ini periodo :: "+invitacion.getFechaPeriodoIniDicTx());
		LOGGER.info("/**** Fecha fin periodo :: "+invitacion.getFechaPeriodoFinDicTx());
		LOGGER.info("/**** Numero aviso :: "+invitacion.getNumAvisoDictamen());
		
		CrtInvitacion invitacionBD = invService.consultaPorClave(invitacion);
		UserSession user = getUsuarioFirmado(request);
		
		invitacionBD.setCveUsuario(user.getCurpUsuario().toString());	
		invitacionBD.setFecFechareg(Functions.stringToDate(ConstantesBusiness.dateToStringFormat(new Date(), ConstantesBusiness.dd_mm_yyyy)));
		invitacionBD.setFechaAisoDictamen(Functions.stringToDate(invitacion.getFechaAisoDictamenTx()));
		invitacionBD.setFechaPeriodoIniDic(Functions.stringToDate(invitacion.getFechaPeriodoIniDicTx()));
		invitacionBD.setFechaPeriodoFInDic(Functions.stringToDate(invitacion.getFechaPeriodoFinDicTx()));
		invitacionBD.setNumAvisoDictamen(invitacion.getNumAvisoDictamen());
		invitacionBD.setCveEstatus(CatEstatus.CORRECCION_DERIVADA_DICTAMEN.getId());
		invitacionBD.setTxObservaciones(invitacion.getTxObservaciones());
		
		if(invitacion.getFechaNotOficioTx()!=null && !invitacion.getFechaNotOficioTx().equals(""))
			invitacionBD.setFecFechanotifi(Functions.stringToDate(invitacion.getFechaNotOficioTx()));
		
		Boolean actualizado = invService.actualizaSeguimientoInvitacion(invitacionBD);
		
		if (actualizado) {
			return invitacionBD;
		}
		return null;
	}
	
	@RequestMapping(value="/validaPatronSITAB" , method=RequestMethod.POST)
	public @ResponseBody SatPatron validaPatron(@RequestBody String parametro) {
		String valor = parametro.substring(1, parametro.length() -1);
		LOGGER.debug("longitud RP:"+parametro.length());
		SatPatron patron = new SatPatron();
		patron =  patronesService.validaRegistroPatronalWS(valor, true);
		return patron;
	}
	
	// Crea un HashTable de domicilios
	@RequestMapping(value="/solicitudDomGeograficoSITAB" , method=RequestMethod.GET)
	public String callDomGeograficosObra(HttpServletResponse response, HttpServletRequest request, Model model) {			
		DgDomicilioGeografico dg = new DgDomicilioGeografico();
			
		Hashtable doms = (Hashtable)getDomicilioInegiSession(request);
			
		if(doms!=null){
			DgDomicilioGeografico tmp = (DgDomicilioGeografico)doms.get("DOM_DERIVAR_SUBD_SITAB");
			if(tmp != null)
				dg=tmp;
		}
			
		dg.setHastableKeyDG("DOM_DERIVAR_SUBD_SITAB");
		dg.setBloquearEstado(false);
		request.getSession().removeAttribute("ObraSol");
		return new DomGeograficosController().getCreateGenericForm(model,dg,request);
	}
		
	@RequestMapping(value = "/actualizaDomGeograficoSITAB", method = RequestMethod.POST)
	public @ResponseBody DgDomicilioGeografico actualizaDomObra(@RequestBody CrtSolicitudcorr patron,HttpServletResponse response, HttpServletRequest request) {
		Hashtable domGeografico = (Hashtable)getDomicilioInegiSession(request);
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
	
	@RequestMapping(value="/eliminaDomicilioSessionSITAB", method=RequestMethod.POST )
	public @ResponseBody CrtInvitacion removerDomicilio(@RequestBody CrtInvitacion invitacion,HttpServletRequest request) {		
		removeDomicilioInegiSession(request);			
		return invitacion;
	}
	
	@RequestMapping(value="/derivarSubdelegacionSegInvitacion" , method=RequestMethod.POST)
	public @ResponseBody CrtInvitacion derivarSubdelegacionSegInvitacion(@RequestBody CrtInvitacion invitacion, HttpServletResponse response, HttpServletRequest request) {

		LOGGER.info("/**** Clave invitacion :: "+invitacion.getCveInvitacion());
		LOGGER.info("/**** Patron fiscal :: "+invitacion.getCvePatronFiscal());
		LOGGER.info("/**** Fecha der subdelegacion :: "+invitacion.getFechaDerSubdelegacionTx());
		LOGGER.info("/**** Referencia der :: "+invitacion.getTxReferenciaSubdeleg());
		LOGGER.info("/**** Subdeleg destino :: "+invitacion.getCveSubdelegDest());
		
		CrtInvitacion invitacionBD = invService.consultaPorClave(invitacion);
		UserSession user = getUsuarioFirmado(request);		
		
		Hashtable<String,Object> hDomicilios = (Hashtable)getDomicilioInegiSession(request);
		DgDomicilioGeografico domicilioGeo = (DgDomicilioGeografico)hDomicilios.get("DOM_DERIVAR_SUBD_SITAB");
		domicilioGeo.toString();
		
		domicilioGeo = this.domiciliosInegiServiceBean.agregar(domicilioGeo);
		LOGGER.debug("/**** Id DomicilioGeo :: " + domicilioGeo.getDomicilioId());
		
		invitacionBD.setCveDomicilioFiscal(new Long(domicilioGeo.getDomicilioId()));
		
		invitacionBD.setCveUsuario(user.getCurpUsuario().toString());	
		invitacionBD.setFecFechareg(Functions.stringToDate(ConstantesBusiness.dateToStringFormat(new Date(), ConstantesBusiness.dd_mm_yyyy)));
		invitacionBD.setFechaDerSubdelegacion(Functions.stringToDate(invitacion.getFechaDerSubdelegacionTx()));
		invitacionBD.setCvePatronFiscal(invitacion.getCvePatronFiscal());
		invitacionBD.setTxReferenciaSubdeleg(invitacion.getTxReferenciaSubdeleg());
		invitacionBD.setCveSubdelegDest(invitacion.getCveSubdelegDest());
		invitacionBD.setCveEstatus(CatEstatus.CORRECCION_DERIVADA_OTRA_SUBDELEGACION.getId());
		invitacionBD.setTxObservaciones(invitacion.getTxObservaciones());
		
		if(invitacion.getFechaNotOficioTx()!=null && !invitacion.getFechaNotOficioTx().equals(""))
			invitacionBD.setFecFechanotifi(Functions.stringToDate(invitacion.getFechaNotOficioTx()));
		
		Boolean actualizado = invService.actualizaSeguimientoInvitacion(invitacionBD);
		
		if (actualizado) {
			return invitacionBD;
		}
		return null;
	}
	
}
