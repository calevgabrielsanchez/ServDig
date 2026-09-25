package mx.gob.imss.ctirss.correccion.web.controller.promocion;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mx.gob.imss.ctirss.correccion.base.paginador.model.DatosEntradaPaginador;
import mx.gob.imss.ctirss.correccion.base.paginador.model.DatosSalidaPaginador;
import mx.gob.imss.ctirss.correccion.bean.SelectBean;
import mx.gob.imss.ctirss.correccion.catalogos.model.CgcCatcriterioseleccion;
import mx.gob.imss.ctirss.correccion.catalogos.model.CgtCatCriterioeleccion;
import mx.gob.imss.ctirss.correccion.catalogos.model.CrcTipoCorr;
import mx.gob.imss.ctirss.correccion.catalogos.model.CrtNroFolio;
import mx.gob.imss.ctirss.correccion.catalogos.model.SacDelegacion;
import mx.gob.imss.ctirss.correccion.catalogos.model.SacSubdelegacion;
import mx.gob.imss.ctirss.correccion.framework.base.controller.AbstractController;
import mx.gob.imss.ctirss.correccion.framework.base.model.AbstractModel;
import mx.gob.imss.ctirss.correccion.framework.utils.ConstantesBusiness;
import mx.gob.imss.ctirss.correccion.model.CgcCatOrigen;
import mx.gob.imss.ctirss.correccion.model.CgtCatCriterioSeleccion;
import mx.gob.imss.ctirss.correccion.model.CrtSelector;
import mx.gob.imss.ctirss.correccion.model.SatPatron;
import mx.gob.imss.ctirss.correccion.presentacion.service.interfaces.PresentacionCorreccionServiceController;
import mx.gob.imss.ctirss.correccion.promocion.base.paginador.model.CrtSelectorWrapperDataTable;
import mx.gob.imss.ctirss.correccion.promocion.model.CrtPromocion;
import mx.gob.imss.ctirss.correccion.promocion.service.interfaces.PromocionService;
import mx.gob.imss.ctirss.correccion.service.interfaces.ICatalogoService;
import mx.gob.imss.ctirss.correccion.service.interfaces.IPatronesService;
import mx.gob.imss.ctirss.correccion.session.UserSession;
import mx.gob.imss.ctirss.correccion.utils.Functions;
import mx.gob.imss.ctirss.domiciliosInegi.model.DgDomicilioGeografico;
import mx.gob.imss.ctirss.domiciliosInegi.service.interfaces.DomiciliosInegiService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value="/promocion/promocionSB")
public class PromocionSalarioBaseController extends AbstractController{
	
	@Autowired
	private PromocionService<CrtPromocion> promocionServiceBean;
	
	@Autowired
	private PromocionService<CgcCatcriterioseleccion> criterioSelServiceBean;
	
	@Autowired
	private PromocionService<CrtSelector> selectorServiceBean;
		
	@Autowired
	private ICatalogoService<AbstractModel> catalogoServiceBean;
	
	@Autowired
	private PromocionService<CrtNroFolio> numFolioServiceBean;
	
	@Autowired
	private ICatalogoService<CgcCatOrigen> catalogoOrigenServiceBean;
	
	@Autowired
	private ICatalogoService<CgcCatcriterioseleccion> catalogoCriteriosServiceBean;
	@Autowired private PresentacionCorreccionServiceController businessController;
	@Autowired private DomiciliosInegiService<DgDomicilioGeografico> domiciliosInegiServiceBean;
	
	@Autowired
	private IPatronesService patronesService;
	
	private final static Long EnProcesodeNotificacion = 28L; 
	

	@RequestMapping(method=RequestMethod.GET)
	public String getCreateForm(Model model) {
		model.addAttribute("crtSelector",new CrtSelector());
		model.addAttribute("crtPromocion",new CrtPromocion());
		PromocionCargaModel modelo = new PromocionCargaModel();
		Map<String, List<SelectBean>> mapa = businessController.obtenerTipoPromocionYOrigen(ConstantesBusiness.FLUJO_PROMOCION, 5);
		modelo.setOrigenes(mapa.get(ConstantesBusiness.LISTA_ORIGENES));
//		modelo.setTiposPromocion(mapa.get(ConstantesBusiness.LISTA_TIPOS_PROMOCION));
		model.addAttribute(modelo);
		 return "promocionSB";
	}
	
	@RequestMapping(value="/consultar" , method=RequestMethod.POST)
	public @ResponseBody List<CrtSelector> consultar(@RequestBody CrtSelector selector, HttpServletRequest request) {
		
		UserSession usuario = getUsuarioFirmado(request);
		
		
		selector.setCgcCatcriterioseleccion(new CgtCatCriterioeleccion());
		selector.getCgcCatcriterioseleccion().setIdCriterioseleccion(selector.getCriterioSeleccion());
		selector.setSacDelegacion( new SacDelegacion());
		selector.getSacDelegacion().setCvePk(usuario.getIdDelegacion());
		selector.setSacSubdelegacion(new SacSubdelegacion());
		selector.getSacSubdelegacion().setCvePk(usuario.getIdSubDelegacion());		
		//System.out.println("REGISTRO PATRONAL A BUSCAR:"+ selector.getCriterioSeleccion());
		return  this.selectorServiceBean.consultarSelector(selector);		
	}
	
	
	@RequestMapping(value="/paginar", method=RequestMethod.POST )
    public @ResponseBody DatosSalidaPaginador<CrtSelector> pagina(@RequestBody CrtSelectorWrapperDataTable aoData, HttpServletRequest request ) {
		System.out.println(".-.-controller public @ResponseBody DatosSalidaPaginador<CrtSelector> pagina(@RequestBody CrtSelectorWrapperDataTable aoData ) {");
		DatosEntradaPaginador<CrtSelector> send = new DatosEntradaPaginador<CrtSelector>();
		
		UserSession usuario = getUsuarioFirmado(request);
		DatosSalidaPaginador<CrtSelector> reply = new DatosSalidaPaginador<CrtSelector>();
		
		if(aoData.getoForm() != null){
		
			send.parserArray(aoData.getAoData());
			send.setModelo(aoData.getoForm());			
			
			aoData.setoForm(new CrtSelector());
			CgtCatCriterioeleccion csel = new CgtCatCriterioeleccion();
			csel.setIdCriterioseleccion(Long.parseLong(send.getsSearch().trim()));
			aoData.getoForm().setCgcCatcriterioseleccion(csel);
			aoData.getoForm().setSacDelegacion( new SacDelegacion());
			if(usuario.getIdDelegacion() != null){
				aoData.getoForm().getSacDelegacion().setCvePk(usuario.getIdDelegacion());
			}
			aoData.getoForm().setSacSubdelegacion(new SacSubdelegacion());
			aoData.getoForm().getSacSubdelegacion().setCvePk(usuario.getIdSubDelegacion());
			
			send.parserArray(aoData.getAoData());
			send.setModelo(aoData.getoForm());
		
			reply = this.selectorServiceBean.obtenerCriterioSelector(send, usuario.getIdDelegacion(), usuario.getIdSubDelegacion(), csel.getIdCriterioseleccion());
		}
        reply.setsEcho(send.getsEcho());
        
        return reply;
    }
	
	@InitBinder
	@RequestMapping(value="/agregaPromocionSB")
	public @ResponseBody CrtPromocion agregaPromocion(@RequestBody CrtPromocion promocion, HttpServletResponse response,HttpServletRequest request) throws Exception{
		UserSession user = this.getUsuarioFirmado(request);
		@SuppressWarnings({ "unchecked", "rawtypes" })
		Hashtable<String,Object> dom = (Hashtable)getDomicilioInegiSession(request);
		DgDomicilioGeografico dg = (DgDomicilioGeografico)dom.get("dom");
		dg = this.domiciliosInegiServiceBean.agregar(dg);
		Long usuario = 0L;
		String domicilio = promocion.getDomicilio();
		promocion.setFecFechareg(Functions.FormateaFecha(new SimpleDateFormat("dd-MM-yyyy").format(new Date()),"-"));
		promocion.setSdelegOrig(user.getIdSubDelegacion());
		Date fecha = Functions.stringToDate(promocion.getFechaOficio());
		promocion.setFecFechaoficiopro(fecha);
		promocion.setUsuarioFirmado(user);
		promocion.setCveUsuario(user.getCurpUsuario().toString());
		promocion.setCveTipocorr(7L);
		promocion.setFecFechaemisionpro(new Date());
		promocion.setCveDomGeoPatron(new BigDecimal(dg.getDomicilioId()));
		// se agrega para el seguimiento  EDJ 10/04/2012
		promocion.setCveEstatus(EnProcesodeNotificacion);
		promocion.setCveAuditorAsignado("");
		promocion = this.promocionServiceBean.guardar(promocion);	

		// Replicamos en CgtPromocion
		promocion.setDomicilio(domicilio);
		// Se comenta replica a caratula  EDJ  12/09/2012
		//this.promocionServiceBean.replicaPromocion(promocion);
		
		if(user != null){
			//usuario = user.getCveIdUsuario();
		}
		this.promocionServiceBean.actualizaSelector(promocion.getCveSelector(),user.getCurpUsuario().toString());
		return promocion;		
	}
	
	private String generaFolioPromocion(Long del,Long sDel, String origen, String anioOficio){
		
		Long ultimoValor;
		String folio = "";
		List<CrtNroFolio> consecutivo = new ArrayList<CrtNroFolio>();
		CrtNroFolio nuevo = new CrtNroFolio();
		try{
		consecutivo = this.promocionServiceBean.obtieneFolioPromocion(del, sDel, "7", anioOficio);
		}
		catch (Exception e) {
			consecutivo = null;
		}
		if(consecutivo!=null && consecutivo.size() > 0){
			ultimoValor = consecutivo.get(0).getNumNumero().longValue() + 1;
			consecutivo.get(0).setNumNumero(BigDecimal.valueOf(ultimoValor));
			String numero = ultimoValor.toString();
			numero = Functions.llenaCeros(numero, 4);
			folio = (del<10?"0"+del.toString():del.toString())+(sDel<10?"0"+sDel.toString():sDel.toString())+"/"+origen+"/"+anioOficio+"/"+numero;
			nuevo = consecutivo.get(0);
			nuevo.setNumNumero(BigDecimal.valueOf(ultimoValor));
		}else{
			folio = (del<10?"0"+del.toString():del.toString())+(sDel<10?"0"+sDel.toString():sDel.toString())+"/"+origen+"/"+anioOficio+"/0001";
			
			nuevo.setNumAnio(BigDecimal.valueOf(Long.valueOf(anioOficio)));
			nuevo.setCveDelegacion(BigDecimal.valueOf(del));
			nuevo.setCveSubdelegacion(BigDecimal.valueOf(sDel));
			nuevo.setNumNumero(new BigDecimal(1));
			nuevo.setCrcTipoCorr(new CrcTipoCorr());
			nuevo.getCrcTipoCorr().setCveTipocorr(7L);
		}
		
		this.numFolioServiceBean.agregar(nuevo);
		
		return folio;
	}
	
	private Boolean verificaDuplicidad(Long subDelegacion, Long idCriterioseleccion, Long patron ){
		
		CrtPromocion promocion = this.promocionServiceBean.verificaDuplicidad(subDelegacion, idCriterioseleccion, patron);
		if(promocion == null){
			return false;
		}
		
		return true;
	}
	
	
	@RequestMapping(value="/filtraCriterios", method=RequestMethod.POST)
	public @ResponseBody CrtPromocion filtrarCriterios(@RequestBody CrtPromocion promocion){
		
		if(promocion != null && promocion.getCveFkPatron() != null){
			SatPatron patron = this.patronesService.getById( promocion.getCveFkPatron());
			if(patron != null){
				promocion.setRegPatron(patron.getRegistroPatronal());
				promocion.setRazonSocial(patron.getRazonSocial());
			}
		}
		
		return promocion;
	}	
	
	@RequestMapping(value="/validaPromocion", method=RequestMethod.POST)
	public @ResponseBody ArrayList<CgtCatCriterioSeleccion> validaPromocionSB(@RequestBody CrtPromocion promocion){
		
		ArrayList lista = (ArrayList) catalogoServiceBean.consultaLibrePorClave(0L, "from CrtPromocion p where p.idOrigen = 2 and cs.idTipo = 6");
		return lista;
	}	
	
	@RequestMapping(value="/cboOrigen", method=RequestMethod.POST )
    public @ResponseBody List<CgcCatOrigen> cboOrigen(@RequestBody CrtSelector aoData) {
		
		
		List<CgcCatOrigen> lstResult = this.catalogoOrigenServiceBean.consultaSQL("select ori.ID_ORIGEN, ori.DESC_ORIGEN from CGC_CATORIGEN ori WHERE ori.ID_ORIGEN=2");
				
		
		return lstResult;
	}
	
	@RequestMapping(value="/cboCriterios", method=RequestMethod.POST )
    public @ResponseBody List<CgcCatcriterioseleccion> cboCriterios(@RequestBody CgcCatcriterioseleccion aoData) {
		
		
		List<CgcCatcriterioseleccion> lstResult = this.catalogoCriteriosServiceBean.consultaSQL("select cs.ID_CRITERIOSELECCION,cs.ID_TIPO,cs.ID_ORIGEN,cs.DESC_CRITERIOSELECCION from CGC_CATCRITERIOSELECCION cs where cs.ID_ORIGEN = " + aoData.getIdOrigen() + " and cs.ID_TIPO = 9 order by cs.ID_CRITERIOSELECCION ");
				
		
		return lstResult;
	}
	
	@RequestMapping(value="/quitarDomicilioGeo.do", method=RequestMethod.POST)
	public @ResponseBody String limpiarSesionDomicilio(@RequestBody CgcCatcriterioseleccion datos, HttpServletRequest request){
		removeDomicilioInegiSession(request);
		return "limpio";
	}
	
	@RequestMapping(value="/validaMunicipio.do", method=RequestMethod.POST)
	public @ResponseBody boolean validarMunicipio(@RequestBody CrtPromocion datos, HttpServletRequest request){
		
		boolean resp = false;
		
		if(datos.getRegPatron()==null) return false;
		
		String rp = datos.getRegPatron();
		
		rp = rp.length()>10 ? rp.substring(0,10) : rp;
		
		SatPatron pat = this.patronesService.validaRegistroPatronalWS(rp, true);
		UserSession user = getUsuarioFirmado(request);
		
		if(pat==null)
			return false;
		
		if(!user.getCveCodigoSubDelegacion().equals(pat.getUbicacion().getMunicipio().getSacSubdelegacion().getCveCodigo())){
			return false;
		}else{resp=true;datos.setError("");}

			
		
		return resp;
	}
	
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
	
	@RequestMapping(value="/cboCriteriosSubdelegacion", method=RequestMethod.POST )
    public @ResponseBody List<CgcCatcriterioseleccion> cboCriteriosSubdelegacion(@RequestBody CgcCatcriterioseleccion aoData) {
		
		
		List<CgcCatcriterioseleccion> lstResult = this.catalogoCriteriosServiceBean.consultaSQL("select cs.ID_CRITERIOSELECCION,cs.ID_TIPO,cs.ID_ORIGEN,cs.DESC_CRITERIOSELECCION from CGC_CATCRITERIOSELECCION cs where cs.ID_ORIGEN = 5 and cs.ID_TIPO = 9 order by cs.ID_CRITERIOSELECCION ");
				
		
		return lstResult;
	}
	
	@RequestMapping(value="/validaPatron" , method=RequestMethod.POST)
	public @ResponseBody SatPatron validaPatron(@RequestBody String parametro) {
		String valor = parametro.substring(1, parametro.length() -1);
		SatPatron model = new SatPatron();
		model =  patronesService.validaRegistroPatronalWS(valor, true);
		
		return model;
	}
		
}
