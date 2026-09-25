package mx.gob.imss.ctirss.correccion.web.controller.promocion;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mx.gob.imss.ctirss.correccion.base.paginador.model.DatosEntradaPaginador;
import mx.gob.imss.ctirss.correccion.base.paginador.model.DatosSalidaPaginador;
import mx.gob.imss.ctirss.correccion.catalogos.model.CgcCatcriterioseleccion;
import mx.gob.imss.ctirss.correccion.catalogos.model.CgtCatCriterioeleccion;
import mx.gob.imss.ctirss.correccion.catalogos.model.CrtNroFolio;
import mx.gob.imss.ctirss.correccion.catalogos.service.interfaces.CatalogosService;
import mx.gob.imss.ctirss.correccion.deteccion.base.paginador.model.CrtDeteccionWrapperDataTable;
import mx.gob.imss.ctirss.correccion.deteccion.model.CrtDeteccion;
import mx.gob.imss.ctirss.correccion.deteccion.service.interfaces.DeteccionService;
import mx.gob.imss.ctirss.correccion.framework.base.controller.AbstractController;
import mx.gob.imss.ctirss.correccion.framework.base.model.AbstractModel;
import mx.gob.imss.ctirss.correccion.framework.utils.ConstantesBusiness;
import mx.gob.imss.ctirss.correccion.model.SatPatron;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value="/catalogo/promocionDet")
public class PromocionDetController extends AbstractController{
	
	@Autowired
	private PromocionService<CrtPromocion> promocionServiceBean;
	
	@Autowired
	private PromocionService<CrtDeteccion> deteccionServiceBean;
	
	@Autowired
	private DeteccionService<CrtDeteccion> deteccionServicBean;
	
	@Autowired
	private CatalogosService catalogosServiceBean;
	
	@Autowired
	private IPatronesService patronesService;
	
	@Autowired
	private ICatalogoService<AbstractModel> catalogoServiceBean;
	
	@Autowired
	private DomiciliosInegiService<DgDomicilioGeografico> domiciliosInegiServiceBean;
	
	@Autowired
	private ICatalogoService<CgcCatcriterioseleccion> catalogoCriteriosServiceBean;
	/**
	 * Logger.
	 */
	private final static Logger logger = Logger.getLogger(PromocionDetController.class);
	private final static Long EnProcesodeNotificacion = 28L; 
	
	@RequestMapping(method=RequestMethod.GET)
	public String getCreateForm(Model model) {
		model.addAttribute("crtDeteccion",new CrtDeteccion());
		model.addAttribute("crtPromocion", new CrtPromocion());
		 return "promocion";
	}
	
	@RequestMapping(value="/paginar", method=RequestMethod.POST )
    public @ResponseBody DatosSalidaPaginador<CrtDeteccion> pagina(@RequestBody CrtDeteccionWrapperDataTable aoData, HttpServletResponse response,HttpServletRequest request ) {
		logger.debug(".-.-controller public @ResponseBody DatosSalidaPaginador<CrtDeteccion> pagina(@RequestBody CrtDeteccionWrapperDataTable aoData ) {");
		UserSession user = getUsuarioFirmado(request);
		DatosEntradaPaginador send = new DatosEntradaPaginador();
		
		aoData.getoForm().setSdelegOrig(new BigDecimal(user.getIdSubDelegacion()));
		
		if(aoData.getoForm().getFechaIncial()!=null && aoData.getoForm().getFechaFinal()!=null){
			aoData.getoForm().setFecFechainicioEst(Functions.stringToDate(aoData.getoForm().getFechaIncial()));					
			aoData.getoForm().setFecFechaterminoEst(Functions.stringToDate(aoData.getoForm().getFechaFinal()));
		}						
						
		DatosSalidaPaginador<CrtDeteccion> reply = new DatosSalidaPaginador<CrtDeteccion>();
		
	
		send.parserArray(aoData.getAoData());
		send.setModelo(aoData.getoForm());	
		reply = this.deteccionServiceBean.paginaDeteccion(send);
		reply.setsEcho(send.getsEcho());
		
		
		logger.debug(".-.-controller realizo consulta) {");        
        
        return reply;
    }
	
	@RequestMapping(value="/mostrar")
	public @ResponseBody CrtDeteccion mostrar(@RequestBody CrtDeteccion deteccion){
		String numExt = "";
		String numInt = "";
		if(deteccion.getCveDeteccion()>0){
			deteccion = this.promocionServiceBean.obtieneDeteccionporClave(deteccion);
			deteccion.setFechaRegistro(Functions.dateToString(deteccion.getFecFechareg()));
			DgDomicilioGeografico dg = new DgDomicilioGeografico();
			dg.setDomicilioId(deteccion.getDomicilioId());
			dg = domiciliosInegiServiceBean.consultaPorClave(dg);
			if(dg!=null){
				deteccion.setRefColonia(dg.getDgAsentamiento().getNomAsen());
				if(dg.getDgCatLocalidad().getDgCatMunicipio().getDgCatEstado().getNomEnt()!=null && !dg.getDgCatLocalidad().getDgCatMunicipio().getDgCatEstado().getNomEnt().equalsIgnoreCase(""))
					deteccion.setEstado(dg.getDgCatLocalidad().getDgCatMunicipio().getDgCatEstado().getNomEnt());
				if(dg.getDgCatLocalidad().getDgCatMunicipio().getNomMun()!=null && !dg.getDgCatLocalidad().getDgCatMunicipio().getNomMun().equalsIgnoreCase(""))
					deteccion.setMunicipio(dg.getDgCatLocalidad().getDgCatMunicipio().getNomMun());
				if(dg.getNomvial()!=null){
					if(!dg.getNomvial().equalsIgnoreCase(""))
						deteccion.setDomCalle(dg.getNomvial());
				}else{
					deteccion.setDomCalle(dg.getDgVialidadByCveViaPrin().getNomVia());
				}
				if(dg.getNumextnum() != null){
					numExt = dg.getNumextnum().toString();
				}
				if(dg.getNumextalf() != null && !dg.getNumextalf().equals("")){
					numExt += " - " + dg.getNumextalf();
				}
				deteccion.setNumNroext(numExt);
				if(dg.getNumintnum() != null){
					numInt = dg.getNumintnum().toString();
				}
				if(dg.getNumintalf() != null && !dg.getNumintalf().equals("")){
					numInt += " - " + dg.getNumintalf();
				}
				deteccion.setNumNroint(numInt);
				deteccion.setNumCodigopostal(dg.getDgCodigosPostales().getId().getCodigo());
			}
			
			if(deteccion.getCveFkPatron()!=null){				
				SatPatron patron = this.patronesService.getById(new Long(deteccion.getCveFkPatron()));
				deteccion.setRegPatron(patron.getRegistroPatronal().substring(0,patron.getRegistroPatronal().length()-1));
				deteccion.setRazonSocial(patron.getRazonSocial());
			}if(deteccion.getCvePkTipObra()!=null){
				deteccion.setTipoObra(catalogosServiceBean.getTipoObraById(deteccion.getCvePkTipObra()));
			}if(deteccion.getCvePkFaseConst()!=null){
				deteccion.setFaseObra(catalogosServiceBean.getFaseObraById(deteccion.getCvePkFaseConst()));
			}
			deteccion.setFechaDeteccion(Functions.dateToString(deteccion.getFecFechadeteccionFc()));
			if(deteccion.getFecFechainicioEst() != null){
				deteccion.setFechaEstimIncio2(Functions.dateToString(deteccion.getFecFechainicioEst()));
			}
			if(deteccion.getFecFechaterminoEst() != null){
				deteccion.setFechaEstTerm2(Functions.dateToString(deteccion.getFecFechaterminoEst()));
			}
		}
		System.out.println("Dete "+deteccion.getRazonSocial());
		return deteccion;
	}
	
	@RequestMapping(value="/actualiza")
	public @ResponseBody CrtPromocion actualizaDet(@RequestBody CrtDeteccion deteccion){
		
		if(deteccion.getCvePkFaseConst()!=null && deteccion.getCvePkFaseConst()==-1) deteccion.setCvePkFaseConst(null);
		if(deteccion.getCvePkTipObra()!=null && deteccion.getCvePkTipObra()==-1) deteccion.setCvePkTipObra(null);
		if(deteccion.getCveFkZona()!=null && deteccion.getCveFkZona()==-1) deteccion.setCveFkZona(null);
		
		if(deteccion.getFechaEstimIncio()!=null && !deteccion.getFechaEstimIncio().equalsIgnoreCase("")){
			deteccion.setFecFechainicioEst(Functions.stringToDate(deteccion.getFechaEstimIncio()));			
		}if(deteccion.getFechaEstTerm()!=null && !deteccion.getFechaEstTerm().equalsIgnoreCase("")){
			deteccion.setFecFechaterminoEst(Functions.stringToDate(deteccion.getFechaEstTerm()));
		}
		
		deteccion.setFecFechadeteccionFc(Functions.FormateaFecha(deteccion.getFechaDeteccion(), "-"));
		deteccion.setFecFechareg(Functions.stringToDate(deteccion.getFechaRegistro()));
		
		CrtPromocion promocion = new CrtPromocion();
		
		promocion.setCveDeteccion(new BigDecimal(deteccion.getCveDeteccion().intValue()));
		promocion.setCveTipocorr(new Long(3));
		promocion.setIdTipo(7);
		if(deteccion.getCveFkPatron()!=null){
			promocion.setCveFkPatron(new Long(deteccion.getCveFkPatron().intValue()));
		}
		if(deteccion.getCveTipocorr().intValue()==9){
			promocion.setIdOrigen(6);
		}else
			promocion.setIdOrigen(deteccion.getCveTipocorr());
		
		this.deteccionServicBean.modificar(deteccion);
		
		return promocion;		
	}
	
	@RequestMapping(value="/agregaPromocion")
	public @ResponseBody CrtPromocion agregaPromocion(@RequestBody CrtPromocion promocion, HttpServletResponse response,HttpServletRequest request){
		logger.info("Inicia - agregaPromocion(...)");
		UserSession user = getUsuarioFirmado(request);
		Hashtable<String,Object> dom = (Hashtable)getDomicilioInegiSession(request);
		DgDomicilioGeografico dg = (DgDomicilioGeografico)dom.get("dom");		
		dg = this.domiciliosInegiServiceBean.agregar(dg);
		if(promocion.getFechaNotificacion()!=null){
			promocion.setFecFechanotif(Functions.FormateaFecha(promocion.getFechaNotificacion(), "-"));
		}
		promocion.setFecFechaoficiopro(Functions.FormateaFecha(promocion.getFechaOficio(),"-"));
		promocion.setFecFechaemisionpro(Functions.FormateaFecha(promocion.getFechaOficio(), "-"));
		promocion.setFecFechareg(Functions.FormateaFecha(new SimpleDateFormat("dd-MM-yyyy").format(new Date()),"-"));
		promocion.setNuFoliopromocion(generaFolioPromocion(new Long(user.getCveCodigoDelegacion()),new Long(user.getCveCodigoSubDelegacion()),"SATICA",promocion.getFechaOficio().substring(6,promocion.getFechaOficio().length())));
		promocion.setCveUsuario(user.getCurpUsuario().toString());
		promocion.setSdelegOrig(new Long(user.getIdSubDelegacion()));
		promocion.setCveDomGeoPatron(new BigDecimal(dg.getDomicilioId()));
		promocion.setCveTipocorr(new Long(3));
		promocion = this.promocionServiceBean.agregar(promocion);
		removeDomicilioInegiSession(request);				
		// Se comenta replica a caratula  EDJ  12/09/2012
		//replicaPromocion(promocion);		
		logger.info("Fin - agregaPromocion(...)");
		return promocion;		
	}
	
	@RequestMapping(value="/consultaMotivos", method=RequestMethod.POST)
	public @ResponseBody ArrayList<CgtCatCriterioeleccion> consultaAnexoPagos(@RequestBody CrtPromocion prom){
		
		ArrayList lista = (ArrayList) catalogoServiceBean.consultaLibrePorClave(0L, "from CgtCatCriterioeleccion cg where cg.idTipo = "+ prom.getIdTipo()+" and cg.idOrigen = "+ prom.getIdOrigen());
		return lista;
	}	
	
	@RequestMapping(value="/validaRegPatron" , method=RequestMethod.POST)
	public @ResponseBody CrtDeteccion consultar(@RequestBody CrtDeteccion deteccion) {
		SatPatron pat = this.patronesService.validaRegistroPatronalWS(deteccion.getRegPatron(), false);
		if(pat!=null){
			deteccion.setCveFkPatron(pat.getCvePK());
			deteccion.setTxRfcpatron(pat.getRfc());
			deteccion.setTxCurppatron(pat.getCurp());
			deteccion.setNomRazonsocial(pat.getRazonSocial());
			deteccion.setActividad(pat.getActividad());
		}else{
			deteccion.setCveFkPatron(null);
			deteccion.setTxRfcpatron(null);
			deteccion.setTxCurppatron(null);
			deteccion.setNomRazonsocial(null);
			deteccion.setActividad(null);
		}
		return deteccion;
	}
	
	public synchronized String generaFolioPromocion(Long del,Long sDel, String origen, String fecha){
	
		String numFolio = "";
		CrtNroFolio consecutivo = this.deteccionServicBean.obtieneFolios(del, sDel, fecha, 3);
		
		if(consecutivo!=null){			
			consecutivo.setNumNumero(new BigDecimal(consecutivo.getNumNumero().intValue()+1));
			this.catalogoServiceBean.agregar(consecutivo);
			numFolio = Functions.llenaCeros(consecutivo.getNumNumero().toString(), 4);
			numFolio = (del<10?"0"+del.toString():del.toString())+(sDel<10?"0"+sDel.toString():sDel.toString())+"/SATICA/"+fecha+"/"+numFolio;
		}
		
		return numFolio;
	}
	
	public void replicaPromocion(CrtPromocion promocion){
		this.promocionServiceBean.replicaPromocion(promocion);
	}
	
	@RequestMapping(value="/sessionDomicilioGeografico", method=RequestMethod.POST )
	public @ResponseBody DgDomicilioGeografico almacenaSessionDomicilioInegi(@RequestBody DgDomicilioGeografico domicilioInegi,
			HttpServletRequest request) {
		removeDomicilioInegiSession(request);
		domicilioInegi.setHastableKeyDG("dom");
		
		return new DomGeograficosController().almacenaSessionDomicilioInegi(domicilioInegi, request);
	}
	
	@RequestMapping(value="/promocionDomGeografico" , method=RequestMethod.GET)
	public String callDomGeograficos(HttpServletResponse response, HttpServletRequest request, Model model) {	
		Hashtable<String,Object> dom = (Hashtable)getDomicilioInegiSession(request);		
		if(dom!=null){
			DgDomicilioGeografico dg = (DgDomicilioGeografico)dom.get("dom");
			return new DomGeograficosController().getCreateGenericForm(model,dg,request);
		}else								
			return new DomGeograficosController().getCreateGenericForm(model,new DgDomicilioGeografico(),request);
	}
	
	@RequestMapping(value="/obtenerDomicilioSession", method=RequestMethod.POST )
	public @ResponseBody CrtPromocion obtenerDomicilio(@RequestBody CrtPromocion promocion,HttpServletRequest request) {		
		Hashtable<String,Object> dom = (Hashtable)getDomicilioInegiSession(request);
		if(dom!=null){
			DgDomicilioGeografico dg = (DgDomicilioGeografico)dom.get("dom");		
			promocion.setRefColonia(dg.getDgAsentamiento().getNomAsen());
			if(dg.getDgCatLocalidad().getDgCatMunicipio().getDgCatEstado().getNomEnt()!=null && !dg.getDgCatLocalidad().getDgCatMunicipio().getDgCatEstado().getNomEnt().equalsIgnoreCase(""))
				promocion.setEstado(dg.getDgCatLocalidad().getDgCatMunicipio().getDgCatEstado().getNomEnt());
			if(dg.getDgCatLocalidad().getDgCatMunicipio().getNomMun()!=null && !dg.getDgCatLocalidad().getDgCatMunicipio().getNomMun().equalsIgnoreCase(""))
				promocion.setMunicipio(dg.getDgCatLocalidad().getDgCatMunicipio().getNomMun());
			if(dg.getNomvial()!=null){
				if(!dg.getNomvial().equalsIgnoreCase(""))
					promocion.setDomCalle(dg.getNomvial());
				else
					promocion.setDomCalle(dg.getDgVialidadByCveViaPrin().getNomVia());
			}else{
				promocion.setDomCalle(dg.getDgVialidadByCveViaPrin().getNomVia());
			}
			promocion.setNumNroext(dg.getNumextnum().toString());
			if(dg.getNumintalf()!=null && !dg.getNumintalf().toString().equalsIgnoreCase(""))
				promocion.setNumNroint(dg.getNumintalf().toString());
			promocion.setNumCodigopostal(dg.getDgCodigosPostales().getId().getCodigo());			
		}
			
		return promocion;
	}
	
	@RequestMapping(value="/removerDomicilioSession", method=RequestMethod.POST )
	public @ResponseBody CrtPromocion removerDomicilio(@RequestBody CrtPromocion promocion,HttpServletRequest request) {		
		removeDomicilioInegiSession(request);			
		return promocion;
	}
	
	@RequestMapping(value="/verficaPatron", method=RequestMethod.POST )
	public @ResponseBody Long verificaPatron(@RequestBody CrtDeteccion deteccion,HttpServletRequest request) {							
		Long resultado = this.catalogosServiceBean.getIdPatByRegPat(deteccion.getRegPatron());
		return resultado;
	}
	
	@RequestMapping(value="/obtenerFechaServidor.do", method=RequestMethod.POST)
	public @ResponseBody String obtenerFechaServidor(HttpServletRequest request){
		return ConstantesBusiness.dateToStringFormat(new Date(), ConstantesBusiness.dd_mm_yyyy);
	}
	
	@RequestMapping(value="/cboCriteriosSeleccion", method=RequestMethod.POST )
    public @ResponseBody List<CgcCatcriterioseleccion> cboCriteriosSeleccion(@RequestBody CgcCatcriterioseleccion aoData) {
		
		
		List<CgcCatcriterioseleccion> lstResult = this.catalogoCriteriosServiceBean.consultaSQL(" select cs.ID_CRITERIOSELECCION,cs.ID_TIPO,cs.ID_ORIGEN,cs.DESC_CRITERIOSELECCION from CGC_CATCRITERIOSELECCION cs where cs.ID_TIPO = 7 order by cs.DESC_CRITERIOSELECCION ");
				
		
		return lstResult;
	}
	
	@RequestMapping(value="/obtenerFechaServidorMinima.do", method=RequestMethod.POST)
	public @ResponseBody String obtenerFechaServidorMinima(HttpServletRequest request){
		Calendar calendario = Calendar.getInstance();
		calendario.add(Calendar.DAY_OF_YEAR, -44);
		Date dia = calendario.getTime();
		return ConstantesBusiness.dateToStringFormat(dia, ConstantesBusiness.dd_mm_yyyy);
	}
	
	@RequestMapping(value="/promueve")
	public @ResponseBody CrtPromocion promueve(@RequestBody CrtDeteccion deteccion){
		
		CrtDeteccion detGuardar = this.deteccionServicBean.consultaPorClave(deteccion);		
		
		if(deteccion.getCvePkFaseConst()!=null ){
			if(deteccion.getCvePkFaseConst()==-1){
				detGuardar.setCvePkFaseConst(null);
			}else{
				detGuardar.setCvePkFaseConst(deteccion.getCvePkFaseConst());
			}			
		}
		if(deteccion.getCvePkTipObra()!=null){
			if (deteccion.getCvePkTipObra()==-1){
				detGuardar.setCvePkTipObra(null);
			}else{
				detGuardar.setCvePkTipObra(deteccion.getCvePkTipObra());
			}
		}
		
		if(deteccion.getCveFkPatron()!=null){
			detGuardar.setCveFkPatron(deteccion.getCveFkPatron());
		}else{
			detGuardar.setCveFkPatron(null);
		}
		if(deteccion.getCveFkZona()!=null){
			if(deteccion.getCveFkZona()==-1){
				detGuardar.setCveFkZona(null);
			}else{
				detGuardar.setCveFkZona(deteccion.getCveFkZona());
			}
		}
		
		if(deteccion.getFechaEstimIncio2()!=null && !deteccion.getFechaEstimIncio2().equalsIgnoreCase("")){
			detGuardar.setFecFechainicioEst(Functions.stringToDate(deteccion.getFechaEstimIncio2()));			
		}if(deteccion.getFechaEstTerm2()!=null && !deteccion.getFechaEstTerm2().equalsIgnoreCase("")){
			detGuardar.setFecFechaterminoEst(Functions.stringToDate(deteccion.getFechaEstTerm2()));
		}
		
		detGuardar.setFecFechareg(new Date());
		detGuardar.setTipClaseobra(deteccion.getTipClaseobra());
		detGuardar.setCanSuperficie(deteccion.getCanSuperficie());
		detGuardar.setImpCostoobra(deteccion.getImpCostoobra());
		detGuardar.setPorAvanceobraEst(deteccion.getPorAvanceobraEst());
		detGuardar.setNumTrabajdores(deteccion.getNumTrabajdores());
		detGuardar.setDesDepcontratante(deteccion.getDesDepcontratante());
		detGuardar.setDesDependenciapub(deteccion.getDesDependenciapub());

		CrtPromocion promocion = new CrtPromocion();
		
		promocion.setCveDeteccion(new BigDecimal(deteccion.getCveDeteccion().intValue()));
		promocion.setCveTipocorr(new Long(3));
		promocion.setIdTipo(7);
		if(deteccion.getCveFkPatron()!=null)
			promocion.setCveFkPatron(new Long(deteccion.getCveFkPatron().intValue()));		
		
		
		this.deteccionServicBean.agregar(detGuardar);
		
		return promocion;		
	}
	
	@RequestMapping(value="/promoverSaticA") 
	public @ResponseBody CrtPromocion promoverSaticA(@RequestBody CrtPromocion promocion, HttpServletRequest request){
		
		UserSession user = getUsuarioFirmado(request);
		
		CrtDeteccion detGuardar = new CrtDeteccion(); 
		detGuardar.setCveDeteccion(promocion.getCveDeteccion().longValue());
		detGuardar = this.deteccionServicBean.consultaPorClave(detGuardar);	
		
		
		promocion.setCveTipocorr(3L);
		promocion.setSdelegOrig(new Long(user.getIdSubDelegacion()));
		promocion.setUsuarioFirmado(user);
		promocion.setFecFechaemisionpro(new Date());
		promocion.setFecFechareg(new Date());
		promocion.setFecFechaoficiopro(Functions.stringToDate(promocion.getFechaOficio()));
		promocion.setCveUsuario(user.getCurpUsuario().toString());
		if(promocion.getRegPatron()!=null && !promocion.getRegPatron().equals("")){
			promocion.setCveFkPatronObra(catalogosServiceBean.getIdPatByRegPat(promocion.getRegPatron())) ;
			promocion.setCveFkPatron(catalogosServiceBean.getIdPatByRegPat(promocion.getRegPatron())) ;
		}
		promocion.setRegPatron("");
		System.out.println("clvePatron "+promocion.getCveFkPatron());
		if(promocion.getCveFkPatron()==null && detGuardar.getCveFkPatron() != null){
			promocion.setCveFkPatron(detGuardar.getCveFkPatron());
		}
		if(detGuardar.getDomicilioId() != null){
			promocion.setCveDomGeoPatron(BigDecimal.valueOf(detGuardar.getDomicilioId()));
		}		
		// se agrega para el seguimiento  EDJ 10/04/2012
		promocion.setCveEstatus(EnProcesodeNotificacion);
		promocion.setCveAuditorAsignado("");
		
		promocion = this.promocionServiceBean.guardar(promocion);
		this.deteccionServiceBean.agregar(detGuardar);
		// Se comenta replica a caratula  EDJ  12/09/2012
		//replicaPromocion(promocion);
		
		return promocion;		
	}
	
	@RequestMapping(value="/validaFolio", method=RequestMethod.GET)
	public @ResponseBody boolean validaFolio(@RequestParam String nuOficio){
		
		boolean respuesta = false;
		List<CrtPromocion> lstPromocion = new ArrayList<CrtPromocion>();
		if(nuOficio != null){
			lstPromocion = this.promocionServiceBean.consultaNumeroFolio(nuOficio);
		}
		
		if(lstPromocion != null && lstPromocion.size() > 0){
			respuesta = true;
		}
		
		return respuesta;		
	}
	
}
