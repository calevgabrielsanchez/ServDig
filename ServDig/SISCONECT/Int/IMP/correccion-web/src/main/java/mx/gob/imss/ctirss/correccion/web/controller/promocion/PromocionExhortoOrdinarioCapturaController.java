package mx.gob.imss.ctirss.correccion.web.controller.promocion;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mx.gob.imss.ctirss.correccion.base.paginador.model.DatosEntradaPaginador;
import mx.gob.imss.ctirss.correccion.base.paginador.model.DatosSalidaPaginador;
import mx.gob.imss.ctirss.correccion.bean.SelectBean;
import mx.gob.imss.ctirss.correccion.catalogos.model.CrcTipoCorr;
import mx.gob.imss.ctirss.correccion.framework.base.controller.AbstractController;
import mx.gob.imss.ctirss.correccion.framework.base.model.AbstractModel;
import mx.gob.imss.ctirss.correccion.framework.utils.ConstantesBusiness;
import mx.gob.imss.ctirss.correccion.model.CrtSelector;
import mx.gob.imss.ctirss.correccion.model.SatPatron;
import mx.gob.imss.ctirss.correccion.model.SatUbicacion;
import mx.gob.imss.ctirss.correccion.presentacion.service.interfaces.PresentacionCorreccionServiceController;
import mx.gob.imss.ctirss.correccion.promocion.base.model.DataTableCriterioSeleccion;
import mx.gob.imss.ctirss.correccion.promocion.model.CrtPromocion;
import mx.gob.imss.ctirss.correccion.promocion.service.interfaces.PromocionExhortoService;
import mx.gob.imss.ctirss.correccion.promocion.service.interfaces.PromocionService;
import mx.gob.imss.ctirss.correccion.service.interfaces.ICatalogoService;
import mx.gob.imss.ctirss.correccion.service.interfaces.IPatronesService;
import mx.gob.imss.ctirss.correccion.session.ConstantesSession;
import mx.gob.imss.ctirss.correccion.session.UserSession;
import mx.gob.imss.ctirss.domiciliosInegi.model.DgDomicilioGeografico;
import mx.gob.imss.ctirss.domiciliosInegi.service.interfaces.DomiciliosInegiService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value="/promocion/exhorto/captura")
public class PromocionExhortoOrdinarioCapturaController extends AbstractController {
	
	@Autowired private PresentacionCorreccionServiceController businessController;
	//@Autowired private PromocionExhortoService promocionController;
	@Autowired private DomiciliosInegiService<DgDomicilioGeografico> domiciliosInegiServiceBean;
	@Autowired private PromocionService<CrtPromocion> promocionServiceBean;
	@Autowired private ICatalogoService<AbstractModel> catalogoServiceBean;
	@Autowired private IPatronesService patronesService;
	@Autowired
	private PromocionService<CrtSelector> selectorServiceBean;
	@Autowired
	private ICatalogoService<CrcTipoCorr> catalogoCriteriosServiceBean;
	
	private final static Long EnProcesodeNotificacion = 28L;
	
	
	@RequestMapping(method=RequestMethod.GET)
	public String getCreateForm(Model model){
		PromocionCargaModel modelo = new PromocionCargaModel();
		Map<String, List<SelectBean>> mapa = businessController.obtenerTipoPromocionYOrigen(ConstantesBusiness.FLUJO_PROMOCION, 5);
		modelo.setOrigenes(mapa.get(ConstantesBusiness.LISTA_ORIGENES));
//		modelo.setTiposPromocion(mapa.get(ConstantesBusiness.LISTA_TIPOS_PROMOCION));
		model.addAttribute(modelo);
		return "promocion/exhortoOrdinarioIndividual";
	}
	
	
	@RequestMapping(value="/buscarCriteriosSeleccion", method=RequestMethod.POST)
	public @ResponseBody DatosSalidaPaginador<CrtSelector> buscarCriteriosSeleccion(@RequestBody FiltroTableCriterioSeleccion filtro, HttpServletRequest request){
		UserSession user = (UserSession) request.getSession().getAttribute(ConstantesSession.USR_SESSION);
		
		List<DataTableCriterioSeleccion> tablaCriterios = new ArrayList<DataTableCriterioSeleccion>();
		DatosSalidaPaginador<CrtSelector> salida = new DatosSalidaPaginador<CrtSelector>();
		CrtSelector selector = new CrtSelector();
		@SuppressWarnings("rawtypes")
		DatosEntradaPaginador entrada = new DatosEntradaPaginador();
		entrada.parserArray(filtro.getAoData());
		entrada.setModelo(selector); 
		
		salida = this.selectorServiceBean.obtenerCriterioSelector(entrada,user.getIdDelegacion(), user.getIdSubDelegacion(), filtro.getoForm().getIdCriterio());
		
		salida.setAaData(salida.getAaData());
		salida.setsEcho(entrada.getsEcho());
		return salida;
	}
	
	
	/**
	 * Con este metodo creamos un registro valido para una promocion del tipo Exhorto de lo Ordinario. 
	 * @param HttpRequest
	 * @return
	 */
	@RequestMapping(value="/validaNumeroOficioPromocion", method=RequestMethod.POST)
	public @ResponseBody CrtPromocion validarPromocionExhortoOrdinario(@RequestBody PromocionExhortoOrdinarioModel datos, HttpServletRequest request){
		CrtPromocion promocion = new CrtPromocion();
		List<CrtPromocion> listaPromociones=this.promocionServiceBean.consultaNumeroFolio(datos.getNumeroOficio());
		if(!listaPromociones.isEmpty()){
			promocion.setBandera("true");			
		}else{
			promocion.setBandera("false");
		}
		return promocion;
	}
	
	
	
	
	
	/**
	 * Con este metodo creamos un registro valido para una promocion del tipo Exhorto de lo Ordinario. 
	 * @param HttpRequest
	 * @return
	 */
	@RequestMapping(value="/promocionar", method=RequestMethod.POST)
	public @ResponseBody CrtPromocion guardarPromocionExhortoOrdinario(@RequestBody PromocionExhortoOrdinarioModel datos, HttpServletRequest request){
		CrtPromocion promocion = new CrtPromocion();
	
		@SuppressWarnings({ "unchecked", "rawtypes" })
		Hashtable<String,Object> dom = (Hashtable)getDomicilioInegiSession(request);
		
	
		
		
		
		UserSession user = (UserSession) request.getSession().getAttribute(ConstantesSession.USR_SESSION);
		
		DgDomicilioGeografico domicilio = (DgDomicilioGeografico)dom.get("dom");
		
		String cveDomGeo = domicilio.getDgCatLocalidad().getDgCatMunicipio().getDgCatEstado().getCveEnt();
		
		
	
		
		SatPatron patron =this.patronesService.validaRegistroPatronalWS(datos.getRegPatronal(), true);
		
		String cveDomPat = patron.getUbicacion().getMunicipio().getSacEntidadFederativa().getCveCodigo();
		
		
//		if(cveDomGeo.equals(cveDomPat)){
//			return null;
//		}
		
		
		domicilio = this.domiciliosInegiServiceBean.agregar(domicilio);
		patron.setDomicilioGeografico(domicilio);
		
				
		catalogoServiceBean.actualizar(patron);
		
		promocion.setFecFechaoficiopro(ConstantesBusiness.stringToDate(datos.getFechaPromocion()));
		promocion.setFecFechaemisionpro(ConstantesBusiness.stringToDate(datos.getFechaPromocion()));
		if(datos.getFechaNotificacion() != null &&
				!datos.getFechaNotificacion().equals("")){
			promocion.setFecFechanotif(ConstantesBusiness.stringToDate(datos.getFechaNotificacion()));
		}
		
		promocion.setUsuarioFirmado(user);
		promocion.setRegPatron(datos.getRegPatronal());
		promocion.setCveFkPatron(datos.getCveFkPatron());
		promocion.setTxObservaciones(datos.getObservaciones());
		promocion.setCveTipocorr(new Long(6));
		promocion.setFecFechareg(new Date());
		promocion.setCveDomGeoPatron(new BigDecimal(domicilio.getDomicilioId()));
		promocion.setCveUsuario(user.getCveIdUsuario().toString());
		
		promocion.setDomicilio(concatenarDomicilion(domicilio));
		// se agrega para el seguimiento  EDJ 10/04/2012
		promocion.setCveEstatus(EnProcesodeNotificacion);
		promocion.setCveAuditorAsignado("");
		promocion.setIdCriterioSeleccion(Long.valueOf(datos.getIdCriterioSeleccion()));
		promocion.setSdelegOrig(user.getIdSubDelegacion());
		promocion.setNuOficiopro(datos.getNumeroOficio());
		

			promocion = this.promocionServiceBean.guardar(promocion);			
	
		

	//	promocionServiceBean.actualizaSelector(datos.getCveSelector(), String.valueOf(user.getCveIdUsuario()));
		// Se comenta replica a caratula  EDJ  12/09/2012
		//promocionController.replicaPromocionExhortoOrdinario(promocionController.obtenerReplicaPromocionIndividual(promocion));
		
		return promocion;
	}
	
	private String concatenarDomicilion(DgDomicilioGeografico domicilio) {
		StringBuffer buffer = new StringBuffer();
		buffer.append(domicilio.getNomvial());
		buffer.append(" ");
		buffer.append(domicilio.getDgAsentamiento().getNomAsen());
		buffer.append(" ");
		buffer.append(domicilio.getNumextnum());
		buffer.append(" ");
		buffer.append(domicilio.getDgCodigosPostales().getId().getCodigo());
		buffer.append(" ");
		
		return buffer.toString();
	}

	/**
	 * Metodo llamdo por AJAX para poder limpiar de la sesion el domicilio geografico. 
	 * @param HttpRequest
	 * @return
	 */
	@RequestMapping(value="/quitarDomicilioGeo.do", method=RequestMethod.POST)
	public @ResponseBody String limpiarSesionDomicilio(@RequestBody PromocionExhortoOrdinarioModel datos, HttpServletRequest request){
		removeDomicilioInegiSession(request);
		return "limpio";
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
		return ConstantesBusiness.dateToStringFormat(new Date(), ConstantesBusiness.DD_MM_YYYY);
	}
	
	@RequestMapping(value="/obtenerFechaServidorMinima.do", method=RequestMethod.POST)
	public @ResponseBody String obtenerFechaServidorMinima(HttpServletRequest request){
		Calendar calendario = Calendar.getInstance();
		calendario.add(Calendar.DAY_OF_YEAR, -44);
		Date dia = calendario.getTime();
		return ConstantesBusiness.dateToStringFormat(dia, ConstantesBusiness.DD_MM_YYYY);
	}
	
	
	@RequestMapping(value="/consultaComboCriterios", method=RequestMethod.POST)
	public @ResponseBody List<CrcTipoCorr> consultaCriterios(@RequestBody ArrayList list, HttpServletResponse response,HttpServletRequest request){
		//aqui borrar de la sesion el domicilio
		removeDomicilioInegiSession(request);
		List<CrcTipoCorr> lstResult = this.catalogoCriteriosServiceBean.consultaSQL("SELECT c.ID_CRITERIOSELECCION,c.ID_TIPO,c.ID_ORIGEN,c.DESC_CRITERIOSELECCION,c.FEC_FECHAREG,c.CVE_USUARIO FROM CGC_CATCRITERIOSELECCION c WHERE c.ID_ORIGEN = 5 AND c.ID_TIPO = 5 ORDER BY c.DESC_CRITERIOSELECCION");
		return lstResult;
	}	
	
	@RequestMapping(value="/consultaPatron", method=RequestMethod.POST)
	public @ResponseBody SatPatron consultaPatron(@RequestBody String regPat, HttpServletResponse response,HttpServletRequest request){
		UserSession user = getUsuarioFirmado(request);
		SatPatron patron = null;
		ArrayList listaOrigenes = (ArrayList) catalogoServiceBean.consultaLibrePorClave(0L, "from SatPatron p where p.registroPatronal = '"+regPat+"'");
		if(listaOrigenes!=null && listaOrigenes.size()>0){
			patron = (SatPatron) listaOrigenes.get(0);
		}
		return patron;
	}	
	
	@RequestMapping(value="/validaRegPat", method=RequestMethod.POST)
	public @ResponseBody SatPatron validaRegistroPatronal(@RequestBody SatPatron patron){
		SatPatron respuesta =this.patronesService.validaRegistroPatronalWS(patron.getRegistroPatronal(), true);
		if (respuesta == null)
			respuesta =this.patronesService.validaRegistroPatronalWS(patron.getRegistroPatronal().substring(0,10), true);
		
			
		return respuesta;
		
		
	}	
	
	
	@RequestMapping(value="/validaPatron" , method=RequestMethod.POST)
	public @ResponseBody SatPatron validaPatron(@RequestBody String parametro, HttpServletRequest request) {
		String valor = parametro.substring(1, parametro.length() -1);
		SatPatron model = new SatPatron();
		UserSession user = getUsuarioFirmado(request);
		
		model =  patronesService.validaRegistroPatronalWS(valor, user.getIdSubDelegacion());
		
		return model;
	}
	
	
	@RequestMapping(value="/obtenerUsuario", method=RequestMethod.POST)
	public @ResponseBody UserSession obtenerUsuario(@RequestBody SatPatron patron, HttpServletResponse response,HttpServletRequest request){
		UserSession user = (UserSession) request.getSession().getAttribute(ConstantesSession.USR_SESSION);
		
		return user;
	}	
	
	@RequestMapping(value="/consultaUbicacion", method=RequestMethod.POST)
	public @ResponseBody SatUbicacion consultaUbicacion(@RequestBody SatUbicacion ubicacion){
		ArrayList<SatUbicacion> lista = new ArrayList<SatUbicacion>();
		if(ubicacion!=null && ubicacion.getCvePK() != null){
			 lista = (ArrayList)catalogoServiceBean.consultaLibrePorClave(0L, "from SatUbicacion u where u.cvePK = " + ubicacion.getCvePK().longValue()) ;
			 if(lista!=null && lista.size() > 0){
				 ubicacion = (SatUbicacion)lista.get(0);
			 }
			}
		return ubicacion;
	}	
	
	
	
	@RequestMapping(value="/obtenerDomGeo", method=RequestMethod.POST)
	public @ResponseBody DgDomicilioGeografico obtenerDomGeo(@RequestBody SatPatron patron, HttpServletResponse response,HttpServletRequest request){
		@SuppressWarnings({ "unchecked", "rawtypes" })
		Hashtable<String,Object> dom = (Hashtable)getDomicilioInegiSession(request);
		
		
		
		UserSession user = (UserSession) request.getSession().getAttribute(ConstantesSession.USR_SESSION);
		DgDomicilioGeografico domicilio = null;
		if(dom != null){
			domicilio = (DgDomicilioGeografico)dom.get("dom");
		
			String entidad = domicilio.getDgCatLocalidad().getDgCatMunicipio().getDgCatEstado().getCveEnt();
		}
		return domicilio;
	}	
	
	@RequestMapping(value="/validaPromocionExistePatron",method = RequestMethod.POST)
	public @ResponseBody CrtPromocion validaPromocionExistentePorNumOficio(@RequestBody  CrtPromocion promocion ){
		//System.out.println("promocion regpat " + promocion.getRegPatron());
		String[] arrarYFecha = promocion.getFechaOficio().split("/");
		
		CrtPromocion crtPromoConsultar= null;
		
		String QUERY = "  select  count(p.*) from CRT_PROMOCION p , sat_patron sp" +
		 		"   where " +
		 		" p.cve_fk_subdelegacion = " + promocion.getCveSubdel() +
		 		" AND   p.nu_oficiopro = " + promocion.getNuOficiopro() +
		 		" and   p.cve_fk_patron = sp.cve_pk " +
		 		" AND sp.num_registropatronal like'"+promocion.getRegPatron() +"%'" +
		 		" AND  (TO_CHAR(p.fec_fechaoficiopro,'YYYY')='"+arrarYFecha[2]+"') ";
		
		
		String QUERYOficio = "  select  * from CRT_PROMOCION p  "+
		 		"   where " +
		 		" p.cve_fk_subdelegacion = " + promocion.getCveSubdel() +
		 		" AND   p.nu_oficiopro = " + promocion.getNuOficiopro();
		//System.err.println(QUERY);
		
		if(promocion != null){
		
			 List listaPromociones =  catalogoServiceBean.consultaSQL(QUERY);
			 List obj=catalogoCriteriosServiceBean.consultaSQL(QUERYOficio);
			 
			
			if((listaPromociones != null && !listaPromociones.isEmpty()) || (obj!=null && !obj.isEmpty())){
				crtPromoConsultar = new CrtPromocion();
			}
		}
		return crtPromoConsultar;
		
	}
	
	
}
