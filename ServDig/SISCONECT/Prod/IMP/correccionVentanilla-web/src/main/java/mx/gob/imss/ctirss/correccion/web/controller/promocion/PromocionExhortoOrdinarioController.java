/**
 * PromocionExhortoOrdinarioController.java
 * @package mx.gob.imss.ctirss.correccion.web.controller.promocion
 * @project Correccion-web	
 */
package mx.gob.imss.ctirss.correccion.web.controller.promocion;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import mx.gob.imss.ctirss.correccion.base.paginador.model.DatosEntradaPaginador;
import mx.gob.imss.ctirss.correccion.base.paginador.model.DatosSalidaPaginador;
import mx.gob.imss.ctirss.correccion.bean.SelectBean;
import mx.gob.imss.ctirss.correccion.framework.base.controller.AbstractController;
import mx.gob.imss.ctirss.correccion.framework.utils.ConstantesBusiness;
import mx.gob.imss.ctirss.correccion.model.CrtSelector;
import mx.gob.imss.ctirss.correccion.model.SatPatron;
import mx.gob.imss.ctirss.correccion.presentacion.service.interfaces.PresentacionCorreccionServiceController;
import mx.gob.imss.ctirss.correccion.promocion.model.CrtPromocion;
import mx.gob.imss.ctirss.correccion.promocion.service.interfaces.PromocionExhortoService;
import mx.gob.imss.ctirss.correccion.promocion.service.interfaces.PromocionService;
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

/**
 * @author IMSS
 * @company IMSS (Instituto Mexicano del Seguro Social)
 * @date 01/05/2012
 */

@Controller
@RequestMapping(value="/promocion/exhorto/ordinario")
public class PromocionExhortoOrdinarioController extends AbstractController {
	
	@Autowired private PresentacionCorreccionServiceController businessController;
	@Autowired private PromocionExhortoService promocionController;
	@Autowired private DomiciliosInegiService<DgDomicilioGeografico> domiciliosInegiServiceBean;
	@Autowired private PromocionService<CrtPromocion> promocionServiceBean;
	@Autowired private IPatronesService patronesService;
	@Autowired private PromocionService<CrtSelector> selectorServiceBean;
	
	private final static Long EnProcesodeNotificacion = 28L;
	
	
	@RequestMapping(method=RequestMethod.GET)
	public String getCreateForm(Model model){
		PromocionCargaModel modelo = new PromocionCargaModel();
		Map<String, List<SelectBean>> mapa = businessController.obtenerTipoPromocionYOrigen(ConstantesBusiness.FLUJO_PROMOCION, 5);
		
		List<SelectBean> listaOrigen = mapa.get(ConstantesBusiness.LISTA_ORIGENES);
		List<SelectBean> listaOrigenTmp= new ArrayList<SelectBean>();
		for(SelectBean origen : listaOrigen){
			if(ConstantesBusiness.ORIGEN_PROGRAMADO_NIVEL_CENTRAL.equals(origen.getId())){
				listaOrigenTmp.add(origen);
				break;
			}
		}
		modelo.setOrigenes(listaOrigenTmp);
		//modelo.setTiposPromocion(mapa.get(ConstantesBusiness.LISTA_TIPOS_PROMOCION));
		model.addAttribute(modelo);
		return "promocion/exhortoOrdinario";
	}
	
	/**
	 * Metodo llamdo por AJAX para generar la tabla de las promociones de acuerdo a un criterio de seleccion, el cual esta ya definido en el JSP. 
	 * @param HttpRequest
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/buscarCriteriosSeleccion", method=RequestMethod.POST)
	public @ResponseBody DatosSalidaPaginador<CrtSelector> buscarCriteriosSeleccion(@RequestBody FiltroTableCriterioSeleccion filtro, HttpServletRequest request){
		UserSession user = (UserSession) request.getSession().getAttribute(ConstantesSession.USR_SESSION);
		
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
	@RequestMapping(value="/promocionar", method=RequestMethod.POST)
	public @ResponseBody CrtPromocion guardarPromocionExhortoOrdinario(@RequestBody PromocionExhortoOrdinarioModel datos, HttpServletRequest request){
		CrtPromocion promocion = new CrtPromocion();
		
		@SuppressWarnings({ "unchecked", "rawtypes" })
		Hashtable<String,Object> dom = (Hashtable)getDomicilioInegiSession(request);
		
		UserSession user = (UserSession) request.getSession().getAttribute(ConstantesSession.USR_SESSION);
		
		DgDomicilioGeografico domicilio = (DgDomicilioGeografico)dom.get("dom");
		domicilio = this.domiciliosInegiServiceBean.agregar(domicilio);
		
		promocion.setFecFechaoficiopro(ConstantesBusiness.stringToDate(datos.getFechaPromocion()));
		promocion.setFecFechaemisionpro(ConstantesBusiness.stringToDate(datos.getFechaPromocion()));
		if(datos.getFechaNotificacion() != null &&
				!datos.getFechaNotificacion().equals("")){
			promocion.setFecFechanotif(ConstantesBusiness.stringToDate(datos.getFechaNotificacion()));
		}
		promocion.setUsuarioFirmado(user);
		promocion.setCveSelector(datos.getCveSelector());
		promocion.setRegPatron(datos.getRegPatronal());
		if(datos.getRegPatronal() != null){
			SatPatron patron = this.patronesService.getByRegistroPatronal(datos.getRegPatronal());
			if(patron != null){
				promocion.setCveFkPatron(patron.getCvePK());
			}
		}
		promocion.setTxObservaciones(datos.getObservaciones());
		promocion.setCveTipocorr(new Long(6));
		promocion.setFecFechareg(new Date());
		promocion.setCveDomGeoPatron(new BigDecimal(domicilio.getDomicilioId()));
		promocion.setCveUsuario(user.getCurpUsuario().toString());
		promocion.setIdCriterioSeleccion(Long.valueOf(datos.getIdCriterioSeleccion()));
		promocion.setDomicilio(concatenarDomicilion(domicilio));
		promocion.setNuOficiopro(datos.getNumeroOficio());
		promocion.setSdelegOrig(user.getIdSubDelegacion());
		
		// se agrega para el seguimiento  EDJ 10/04/2012
		promocion.setCveEstatus(EnProcesodeNotificacion);
		promocion.setCveAuditorAsignado("");
		promocion = this.promocionServiceBean.guardar(promocion);
		
		promocionServiceBean.actualizaSelector(datos.getCveSelector(), String.valueOf(user.getCurpUsuario()));
		// Se comenta replica a caratula  EDJ  12/09/2012
		//promocionController.replicaPromocionExhortoOrdinario(promocionController.obtenerReplicaPromocion(promocion));
		//this.promocionServiceBean.replicaPromocion(promocion);
		
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
	
	/**
	 * Con este metodo buscamos la promocion por el id. 
	 * @return
	 */
	@RequestMapping(value="/mostrarDatos", method=RequestMethod.POST)
	public @ResponseBody CrtSelector mostrarDatos(@RequestBody CrtSelector datos){
		CrtSelector crtSelector = new CrtSelector();
		crtSelector.setCveSelector(datos.getCveSelector());
		crtSelector = this.selectorServiceBean.consultaSelectorPorClave(crtSelector);
		if(crtSelector != null && crtSelector.getSatPatron() != null){
			crtSelector.setRegistroPatronal(crtSelector.getSatPatron().getRegistroPatronal());
			crtSelector.setRazonSocial(crtSelector.getSatPatron().getRazonSocial());
		}
		return crtSelector;
	}
	
}
