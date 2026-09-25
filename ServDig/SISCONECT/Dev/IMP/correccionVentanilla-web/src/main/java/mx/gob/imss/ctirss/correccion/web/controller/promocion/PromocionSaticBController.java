package mx.gob.imss.ctirss.correccion.web.controller.promocion;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mx.gob.imss.ctirss.correccion.base.paginador.model.DatosEntradaPaginador;
import mx.gob.imss.ctirss.correccion.base.paginador.model.DatosSalidaPaginador;
import mx.gob.imss.ctirss.correccion.catalogos.model.CgcCatcriterioseleccion;
import mx.gob.imss.ctirss.correccion.catalogos.model.CgtCatCriterioeleccion;
import mx.gob.imss.ctirss.correccion.catalogos.model.CrtNroFolio;
import mx.gob.imss.ctirss.correccion.deteccion.base.paginador.model.CrtDeteccionWrapperDataTable;
import mx.gob.imss.ctirss.correccion.deteccion.model.CrtDeteccion;
import mx.gob.imss.ctirss.correccion.deteccion.service.interfaces.DeteccionService;
import mx.gob.imss.ctirss.correccion.framework.base.controller.AbstractController;
import mx.gob.imss.ctirss.correccion.framework.base.model.AbstractModel;
import mx.gob.imss.ctirss.correccion.framework.utils.ConstantesBusiness;
import mx.gob.imss.ctirss.correccion.framework.utils.TipoCorreccion;
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
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
@RequestMapping(value="/catalogo/promocionSaticB")
public class PromocionSaticBController extends AbstractController{
	
	@Autowired
	private PromocionService<CrtPromocion> promocionServiceBean;
	
	
	@Autowired
	private PromocionService<CrtDeteccion> deteccionServiceBean;
	
	@Autowired
	private DeteccionService<CrtDeteccion> deteccionServicBean;
	
	@Autowired
	private IPatronesService<?> patronesService;
	
	@Autowired
	private ICatalogoService<AbstractModel> catalogoServiceBean;
	
	@Autowired
	private DomiciliosInegiService<DgDomicilioGeografico> domiciliosInegiServiceBean;
	
	@Autowired
	private ICatalogoService<CgcCatcriterioseleccion> catalogoCriteriosServiceBean;

	
	/**
	 * Logger.
	 */
	private final static Logger logger = Logger.getLogger(PromocionSaticBController.class);
	private final static Long EnProcesodeNotificacion = 28L; 
	
	@RequestMapping(method=RequestMethod.GET)
	public String getCreateForm(Model model) {
		model.addAttribute("crtDeteccion",new CrtDeteccion());
		model.addAttribute("crtPromocion", new CrtPromocion());
		 return "promocionSaticB";
	}
	
	@RequestMapping(value="/paginarDeteccion", method=RequestMethod.POST )
    public @ResponseBody DatosSalidaPaginador<CrtDeteccion> pagina(@RequestBody CrtDeteccionWrapperDataTable aoData ) {
		logger.debug(".-.-controller public @ResponseBody DatosSalidaPaginador<CrtDeteccion> pagina(@RequestBody CrtDeteccionWrapperDataTable aoData ) {");
		DatosEntradaPaginador<CrtDeteccion> send = new DatosEntradaPaginador<CrtDeteccion>();								
		
		send.parserArray(aoData.getAoData());
		send.setModelo(aoData.getoForm());
		
		DatosSalidaPaginador<CrtDeteccion> reply = new DatosSalidaPaginador<CrtDeteccion>();
							
		if(aoData.getoForm().getFecFechainicioEst()==null && aoData.getoForm().getFecFechaterminoEst()==null){
			reply = this.deteccionServiceBean.paginaDeteccion(send);
		}else
			reply.setAaData(new ArrayList<CrtDeteccion>());
			
		logger.debug(".-.-controller realizo consulta) {");
        reply.setsEcho(send.getsEcho());
        
        return reply;
    }
	
	@RequestMapping(value="/paginarSatic", method=RequestMethod.POST )
    public @ResponseBody DatosSalidaPaginador<CrtDeteccion> paginaSatic(@RequestBody CrtDeteccionWrapperDataTable aoData, HttpServletResponse response,HttpServletRequest request ) {
		logger.debug(".-.-controller public @ResponseBody DatosSalidaPaginador<CrtDeteccion> pagina(@RequestBody CrtDeteccionWrapperDataTable aoData ) {");		
		DatosEntradaPaginador<CrtDeteccion> send = new DatosEntradaPaginador<CrtDeteccion>();
		
		UserSession user = getUsuarioFirmado(request);
		
		aoData.getoForm().setSdelegOrig(new BigDecimal(user.getIdSubDelegacion()));
		if(aoData.getoForm().getFechaEstimIncio()!=null && aoData.getoForm().getFechaEstTerm()!=null){
			aoData.getoForm().setFecFechainicioEst(Functions.FormateaFecha(aoData.getoForm().getFechaEstimIncio(), "-"));
			aoData.getoForm().setFecFechaterminoEst(Functions.FormateaFecha(aoData.getoForm().getFechaEstTerm(),"-"));
		}
		
		send.parserArray(aoData.getAoData());
		send.setModelo(aoData.getoForm());
		
		DatosSalidaPaginador<CrtDeteccion> reply = new DatosSalidaPaginador<CrtDeteccion>();
							
		if(aoData.getoForm().getFechaEstimIncio()!=null && aoData.getoForm().getFechaEstTerm()!=null){		
			
			reply = this.deteccionServiceBean.paginaSaticB(send);
			reply.setsEcho(send.getsEcho());
		}
		request.getSession().setAttribute("lista23", reply);
		
		logger.debug(".-.-controller realizo consulta) {");        
        
        return reply;
    }
	
	@RequestMapping(value="/paginarSaticSinRelaciones", method=RequestMethod.POST )
    public @ResponseBody DatosSalidaPaginador<CrtDeteccion> paginaSaticSinRelaciones(@RequestBody CrtDeteccionWrapperDataTable aoData ) {
		logger.debug(".-.-controller public @ResponseBody DatosSalidaPaginador<CrtDeteccion> pagina(@RequestBody CrtDeteccionWrapperDataTable aoData ) {");
		DatosEntradaPaginador<CrtDeteccion> send = new DatosEntradaPaginador<CrtDeteccion>();								
		
		aoData.getoForm().setSdelegOrig(new BigDecimal(55));
		if(aoData.getoForm().getFechaEstimIncio()!=null && aoData.getoForm().getFechaEstTerm()!=null){
			aoData.getoForm().setFecFechainicioEst(Functions.stringToDate(aoData.getoForm().getFechaEstimIncio()));
			aoData.getoForm().setFecFechaterminoEst(Functions.stringToDate(aoData.getoForm().getFechaEstTerm()));
		}
		
		send.parserArray(aoData.getAoData());
		send.setModelo(aoData.getoForm());
		
		DatosSalidaPaginador<CrtDeteccion> reply = new DatosSalidaPaginador<CrtDeteccion>();
							
		if(aoData.getoForm().getFechaEstimIncio()!=null && aoData.getoForm().getFechaEstTerm()!=null){			
			reply = this.deteccionServiceBean.paginaSaticBSinIncidencia(send);
			reply.setsEcho(send.getsEcho());
		}
			
		logger.debug(".-.-controller realizo consulta) {");        
        
        return reply;
    }
	
	@RequestMapping(value="/agregaPromocion")
	public @ResponseBody CrtPromocion agregaPromocion(@RequestBody CrtPromocion promocion, HttpServletResponse response,HttpServletRequest request){
		UserSession user = getUsuarioFirmado(request);
		Hashtable<String,Object> dom = (Hashtable)getDomicilioInegiSession(request);
		DgDomicilioGeografico dg = (DgDomicilioGeografico)dom.get("dom");		
		dg = this.domiciliosInegiServiceBean.agregar(dg);
		promocion.setFecFechanotif(Functions.FormateaFecha(promocion.getFechaNotificacion(), "-"));
		promocion.setFecFechaoficiopro(Functions.FormateaFecha(promocion.getFechaOficio(),"-"));
		promocion.setFecFechaemisionpro(Functions.FormateaFecha(promocion.getFechaOficio(), "-"));
		promocion.setFecFechareg(Functions.FormateaFecha(new SimpleDateFormat("dd-MM-yyyy").format(new Date()),"-"));
		promocion.setUsuarioFirmado(user);
		promocion.setCveUsuario(user.getCurpUsuario().toString());
		promocion.setSdelegOrig(new Long(user.getIdSubDelegacion()));
		promocion.setCveDomGeoPatron(new BigDecimal(dg.getDomicilioId()));
		promocion.setCveTipocorr(new Long(TipoCorreccion.SATIC_B.getId()));
		promocion = this.promocionServiceBean.guardar(promocion);
		removeDomicilioInegiSession(request);
		// Se comenta replica a caratula  EDJ  12/09/2012
		//replicaPromocion(promocion);
		return promocion;		
	}
	
	@RequestMapping(value="/mostrar")
	public @ResponseBody CrtDeteccion mostrar(@RequestBody CrtDeteccion deteccion, HttpServletResponse response,HttpServletRequest request){
		UserSession user = getUsuarioFirmado(request);
		deteccion.setSdelegOrig(new BigDecimal(user.getIdSubDelegacion()));
		deteccion = this.promocionServiceBean.obtieneObraporNumeroRegistro(deteccion);						
		if(deteccion.getCveFkPatron()!=null){
			SatPatron patron = this.patronesService.getById(deteccion.getCveFkPatron());
			if(patron!=null){
				deteccion.setNomRazonsocial(patron.getRazonSocial());
				deteccion.setTxRfcpatron(patron.getRfc());
				deteccion.setTxCurppatron(patron.getCurp());
			}
			deteccion.setCveTipocorr(TipoCorreccion.SATIC_B.getId());
		}
		DatosSalidaPaginador<CrtDeteccion> reply = (DatosSalidaPaginador<CrtDeteccion>)request.getSession().getAttribute("lista23");
		
		for (int i = 0; i < reply.getAaData().size(); i++) {
			String srt1 = reply.getAaData().get(i).getNumRegObra();
			String srt2 = deteccion.getNumRegObra();
			if ( srt1.equals(srt2) ){
				deteccion.setIncidencia(reply.getAaData().get(i).getIncidencia());
				break;
			}
		}
		return deteccion;
	}
	
	@RequestMapping(value="/periodos")
	public @ResponseBody ArrayList<String> periodos(@RequestBody CrtDeteccion deteccion, HttpServletResponse response,HttpServletRequest request){
		ArrayList<String> lista = new ArrayList<String>();		
		
		Date fechaInicial;
		Date fechaFinal;
		Integer anioInicial;
		Integer anios;
		Integer mes;
		Integer meses = null;
		String periodo;
		
		UserSession user = getUsuarioFirmado(request);
		deteccion.setSdelegOrig(new BigDecimal(user.getIdSubDelegacion()));
		deteccion = this.promocionServiceBean.obtieneObraporNumeroRegistro(deteccion);
		List<CrtDeteccion> lstDet = this.promocionServiceBean.obtieneObraporNumReg(deteccion);
		Iterator<?> iterator = lstDet.iterator();
		
		if(deteccion.getFechaEstimIncio()!=null && deteccion.getFechaEstTerm()!=null
				&& !deteccion.getFechaEstimIncio().equalsIgnoreCase("") && !deteccion.getFechaEstTerm().equalsIgnoreCase("")){
			fechaInicial = Functions.stringToDate(deteccion.getFechaEstimIncio());
			fechaFinal = Functions.stringToDate(deteccion.getFechaEstTerm());
			if(fechaInicial.before(fechaFinal)){
				anioInicial = new Integer(deteccion.getFechaEstimIncio().substring(6, 10));
				anios = ((new Integer(deteccion.getFechaEstTerm().substring(6,10))-anioInicial));
				for(int i=0;i<anios;i++){					
					if(i==0){
						mes = new Integer(deteccion.getFechaEstimIncio().substring(3,5));
						if(mes>6 && anios>0)
							meses = 13;
						else if(mes<6 && anios>0)
							meses = 13;
						else if(anios==0){
							meses = 0;
							for(int y=mes;y<=new Integer(deteccion.getFechaEstTerm().substring(3,5));y++){
								meses = meses + 1;
							}
						}
						for(int x=mes;x<meses;x++){
							periodo = (anioInicial+i)+""+((x)<10?"0"+(x):x);
							iterator = lstDet.iterator();
							while(iterator.hasNext()){
								CrtDeteccion det = (CrtDeteccion)iterator.next();
								if(det.getPeriodo().equals(periodo))
									periodo = periodo +"1";
							}
							if(periodo.length()==6){
								periodo = periodo +"0";
							}
							lista.add(periodo);
						}
					}else if(i==anios){
						meses = new Integer(deteccion.getFechaFinal().substring(3,5));
						for(int x=0;x<meses;x++){
							periodo = (anioInicial+i)+""+((x+1)<10?"0"+(x+1):x+1);
							iterator = lstDet.iterator();
							while(iterator.hasNext()){
								CrtDeteccion det = (CrtDeteccion)iterator.next();
								if(det.getPeriodo().equals(periodo))
									periodo = periodo +"1";
							}
							if(periodo.length()==6){
								periodo = periodo +"0";
							}
							lista.add(periodo);							
						}
					}else{
						meses = 13;
						for(int x=0;x<meses;x++){
							periodo = (anioInicial+i)+""+((x+1)<10?"0"+(x+1):x+1);
							iterator = lstDet.iterator();
							while(iterator.hasNext()){
								CrtDeteccion det = (CrtDeteccion)iterator.next();
								if(det.getPeriodo().equals(periodo))
									periodo = periodo +"1";
							}
							if(periodo.length()==6){
								periodo = periodo +"0";
							}
							lista.add(periodo);							
						}
					}
				}
			}
		}else{
			fechaInicial = Functions.stringToDate(deteccion.getFechaIncial());
			fechaFinal = Functions.stringToDate(deteccion.getFechaFinal());
			if(fechaInicial.before(fechaFinal)){
				anioInicial = new Integer(deteccion.getFechaIncial().substring(6, 10));
				anios = ((new Integer(deteccion.getFechaFinal().substring(6,10))-anioInicial));
				for(int i=0;i<=(anios);i++){					
					if(i==0){
						mes = new Integer(deteccion.getFechaIncial().substring(3,5));
						if(mes>6 && anios>0)
							meses = 13;
						else if(mes<6 && anios>0)
							meses = 13;
						else if(anios==0){
							meses = 13;
//							for(int y=mes;y<=new Integer(deteccion.getFechaFinal().substring(3,5));y++){
//								meses = meses + 1;
//							}
						}
						for(int x=mes;x<meses;x++){
							periodo = (anioInicial+i)+""+((x)<10?"0"+(x):x);
							iterator = lstDet.iterator();
							while(iterator.hasNext()){
								CrtDeteccion det = (CrtDeteccion)iterator.next();
								if(det.getPeriodo().equals(periodo))
									periodo = periodo +"1";
							}
							if(periodo.length()==6){
								periodo = periodo +"0";
							}
							lista.add(periodo);
						}
					}else if(i==anios){
						meses = new Integer(deteccion.getFechaFinal().substring(3,5));
						for(int x=0;x<meses;x++){
							periodo = (anioInicial+i)+""+((x+1)<10?"0"+(x+1):x+1);
							iterator = lstDet.iterator();
							while(iterator.hasNext()){
								CrtDeteccion det = (CrtDeteccion)iterator.next();
								if(det.getPeriodo().equals(periodo))
									periodo = periodo +"1";
							}
							if(periodo.length()==6){
								periodo = periodo +"0";
							}
							lista.add(periodo);							
						}
					}else{
						meses = 13;
						for(int x=0;x<meses;x++){
							periodo = (anioInicial+i)+""+((x+1)<10?"0"+(x+1):x+1);
							iterator = lstDet.iterator();
							while(iterator.hasNext()){
								CrtDeteccion det = (CrtDeteccion)iterator.next();
								if(det.getPeriodo().equals(periodo))
									periodo = periodo +"1";
							}
							if(periodo.length()==6){
								periodo = periodo +"0";
							}
							lista.add(periodo);							
						}
					}
				}
			}
		}
		return lista;
	}
	
	@RequestMapping(value="/consultaMotivos", method=RequestMethod.POST)
	public @ResponseBody ArrayList<CgtCatCriterioeleccion> consultaAnexoPagos(@RequestBody CrtPromocion prom){
		
		ArrayList<CgtCatCriterioeleccion> lista = (ArrayList) catalogoServiceBean.consultaLibrePorClave(0L, "from CgtCatCriterioeleccion cg where cg.idTipo = "+ prom.getIdTipo()+" and cg.idOrigen = "+ prom.getIdOrigen());
		return lista;
	}	
	
	public synchronized String generaFolioPromocion(Long del,Long sDel, String origen,String fecha){
		
		String numFolio = "";
		CrtNroFolio consecutivo = this.deteccionServicBean.obtieneFolios(del, sDel, fecha, 4);

		if(consecutivo!=null){			
			consecutivo.setNumNumero(new BigDecimal(consecutivo.getNumNumero().intValue()+1));
			this.catalogoServiceBean.agregar(consecutivo);
			numFolio = Functions.llenaCeros(consecutivo.getNumNumero().toString(), 4);
			numFolio = (del<10?"0"+del.toString():del.toString())+(sDel<10?"0"+sDel.toString():sDel.toString())+"/SATICB/"+fecha+"/"+numFolio;
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
	
	@RequestMapping(value="/promoverSATICB") 
	public @ResponseBody CrtPromocion promoverSATICB(@RequestBody CrtPromocion promocion, HttpServletRequest request){
		
		UserSession user = getUsuarioFirmado(request);
		CrtDeteccion deteccion = new CrtDeteccion();
		deteccion.setSdelegOrig(BigDecimal.valueOf(user.getIdSubDelegacion()));
		deteccion.setNumRegObra(promocion.getNumRegObra());
		deteccion = this.promocionServiceBean.obtieneObraporNumeroRegistro(deteccion);		
		promocion.setUsuarioFirmado(user);
		promocion.setCveTipocorr(4L);
		promocion.setSdelegOrig(new Long(user.getIdSubDelegacion()));
		//promocion.setNuFoliopromocion(generaFolioPromocion(new Long(user.getCveCodigoDelegacion()),new Long(user.getCveCodigoSubDelegacion()),"SATICB",promocion.getFechaOficio().substring(6, promocion.getFechaOficio().length())));
		promocion.setFecFechaemisionpro(new Date());
		promocion.setFecFechareg(new Date());
		promocion.setFecFechaoficiopro(Functions.stringToDate(promocion.getFechaOficio()));
		promocion.setCveUsuario(user.getCurpUsuario().toString());
		promocion.setCveNroregobraSatic(BigDecimal.valueOf(Long.valueOf(promocion.getNumRegObra())));
		promocion.setCveEstatus(EnProcesodeNotificacion);
		promocion.setCveAuditorAsignado("");
		
		//promocion = this.promocionServiceBean.agregar(promocion);
		
		promocion = this.promocionServiceBean.guardar(promocion);
		// Se comenta replica a caratula  EDJ  12/09/2012	
		//replicaPromocion(promocion);
		
		return promocion;		
	}
	
	@RequestMapping(value="/obtenerFechaServidor.do", method=RequestMethod.POST)
	public @ResponseBody String obtenerFechaServidor(HttpServletRequest request){
		return ConstantesBusiness.dateToStringFormat(new Date(), ConstantesBusiness.dd_mm_yyyy);
	}
	
	@RequestMapping(value="/cboCriteriosSeleccion", method=RequestMethod.POST )
    public @ResponseBody List<CgcCatcriterioseleccion> cboCriteriosSeleccion(@RequestBody CgcCatcriterioseleccion aoData) {
		
		
		List<CgcCatcriterioseleccion> lstResult = this.catalogoCriteriosServiceBean.consultaSQL(" select cs.ID_CRITERIOSELECCION,cs.ID_TIPO,cs.ID_ORIGEN,cs.DESC_CRITERIOSELECCION from CGC_CATCRITERIOSELECCION cs where cs.ID_TIPO = 6 order by cs.DESC_CRITERIOSELECCION ");
				
		
		return lstResult;
	}
	
	@RequestMapping(value="/obtenerFechaServidorMinima.do", method=RequestMethod.POST)
	public @ResponseBody String obtenerFechaServidorMinima(HttpServletRequest request){
		Calendar calendario = Calendar.getInstance();
		calendario.add(Calendar.DAY_OF_YEAR, -44);
		Date dia = calendario.getTime();
		return ConstantesBusiness.dateToStringFormat(dia, ConstantesBusiness.dd_mm_yyyy);
	}
	
	/**
	 * Metodo que busca los periodos presentados y los periodos faltantes y los regresa transformados
	 * @author Enrique Duran JImenez
	 * @since 19/07/2012
	 * @return ArrayList
	 */
	@RequestMapping(value="/periodosObra")
	public @ResponseBody CrtDeteccion periodosObra(@RequestBody CrtDeteccion deteccion, HttpServletResponse response,HttpServletRequest request){
		
		CrtDeteccion deteccionSalida = new CrtDeteccion();
		Date fechaInicioObra = null;
		Date fechaFinObra = null;
		String respuesta = "";
		List<String> periodosPresentados=new ArrayList<String>();
		List<String> periodosFaltantes=new ArrayList<String>();	
		String numeroObra=deteccion.getNumRegObra();

		if(numeroObra != null){
			fechaInicioObra = Functions.stringToDate(deteccion.getFechaIncial());
			fechaFinObra = Functions.stringToDate(deteccion.getFechaFinal());
			
			logger.debug("!! Request  fini="+deteccion.getFechaIncial()+", ffin="+deteccion.getFechaFinal()+"  ###########");
			
			List<AbstractModel> listaPeriodos=generaQueryPeriodos(fechaInicioObra, fechaFinObra, new Long(numeroObra));		
			
			if(listaPeriodos!=null && !listaPeriodos.isEmpty()){
				Iterator<?> iter = listaPeriodos.iterator();
				Object[] currentObj = null;		
				
				while(iter.hasNext()){				
					currentObj = (Object[])iter.next();
						if(currentObj[0]!=null){
							periodosPresentados.add(currentObj[0].toString());
						}else{
							periodosFaltantes.add(currentObj[1].toString());
						}				
					}
			}
			logger.info(" La lista de periodos mide "+listaPeriodos.size());
			logger.info(" presentados.size= "+periodosPresentados.size());
			logger.info(" faltantes.size "+periodosFaltantes.size());			
		}
		
		// periodos faltantes
		if(periodosFaltantes != null && periodosFaltantes.size() > 0){
			for(int y=0; y < periodosFaltantes.size(); y++){
				respuesta = respuesta + periodosFaltantes.get(y) + "-";
			}
		}
		// Periodos presentados
		if(periodosPresentados != null && periodosPresentados.size() > 0){
			respuesta = respuesta + "/";
			for(int x=0; x< periodosPresentados.size(); x++){
				respuesta = respuesta + periodosPresentados.get(x).toString() + "-";
			}
		}
		
		deteccionSalida.setBandera(respuesta);
		return deteccionSalida;
	}
	
	/**
	 * Metodo que transforma el rango de fechas en periodos
	 * @author Enrique Duran JImenez
	 * @since 19/07/2012
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private List periodosFechas(String ini, String fin) {
		List lst = new ArrayList();
		String tempIni = transformarFechaAPeriodo(ini);
		String tempFin = transformarFechaAPeriodo(fin);
		String temp = "";
		temp = tempIni;
		lst.add(temp);
		while (!temp.equals(tempFin)) {
			String anioTemp = temp.substring(0, 4);
			String mesTemp = temp.substring(4, 6);
			Integer anioInt = Integer.valueOf(anioTemp);
			Integer mesInt = Integer.valueOf(mesTemp);
			if (mesInt == 12) {
				anioInt = anioInt + 1;
				mesInt = 1;
			} else if (mesInt < 12) {
				mesInt = mesInt + 1;
			}
			String periodo = "";
			if (mesInt < 10) {
				periodo = anioInt.toString() + "0" + mesInt.toString();
			} else {
				periodo = anioInt.toString() + mesInt.toString();
			}

			temp = periodo;
			lst.add(periodo);
		}
		return lst;
	}
	
	/**
	 * Metodo que transforma una fecha en un periodo 
	 * @author Enrique Duran JImenez
	 * @since 19/07/2012
	 * @return String - 201207
	 */
	private String transformarFechaAPeriodo(String fec){
		String periodo = "";
		String mes     = "";
		String anio    = "";
		mes = fec.substring(3, 5);
		anio = fec.substring(6);
		periodo = anio + mes;
		return periodo;
	}
	
	/**
	 * Metodo que limpia de una lista los objetos repetidos 
	 * @author Enrique Duran JImenez
	 * @since 20/07/2012
	 * @return String - 201207
	 */
	@SuppressWarnings("rawtypes")
	private List limpiaRepetidos(List listaLlena) {
		List lstAux = new ArrayList();
		for (int i = 0; i < listaLlena.size(); i++) {
			if (!lstAux.contains(listaLlena.get(i))) {
				lstAux.add(listaLlena.get(i));
			}

		}
		return lstAux;
	}
	
	
	public List<AbstractModel>  generaQueryPeriodos(Date fechaInicio,Date fechaFin,Long  regObra){
		StringBuilder query=new StringBuilder();
		SimpleDateFormat formater = new SimpleDateFormat("dd/MM/yyyy");		

		String fecIni= formater.format(fechaInicio);
		String fecFin=formater.format(fechaFin);
		
		query.append("select PERIODOSPRESENTADOS,CASE"
					+" WHEN PERIODOSPRESENTADOS IS NULL THEN PERIODOS" 
					+" ELSE NULL END as faltantes,PERIODOS  "
					+" from ( SELECT * FROM    ("
					+" SELECT A.CVE_NROREGOBRA, A.FEC_FECHAINICIO_FC, A.FEC_FECHATERMINO_FC, A.NUM_PERIODO AS PERIODOSPRESENTADOS"
					+" FROM ("
					+" SELECT O.CVE_NROREGOBRA, O.FEC_FECHAINICIO_FC, O.FEC_FECHATERMINO_FC,"
					+" RT.NUM_PERIODO, RT.IND_RELACIONINICIAL, "
					+" RT.FEC_FECHAREGISTRO_FC, RT.REF_LUGARREGISTRO,"
					+" O.CVE_PK CVE_PK_OBRA, O.CVE_FK_OBRAPRINCIPAL, RT.CVE_PK CVE_PK_RELTRABAJADORES" 
					+" FROM SAT_OBRA O, SAT_RELTRABAJADORES RT"
					+" WHERE O.IND_HISTORICO BETWEEN 1 AND 2"
					+" AND O.CVE_PK = RT.CVE_FK_OBRA"
					+" AND O.CVE_NROREGOBRA = '"+regObra+"'"
					+" ) A"
					+" GROUP BY A.CVE_NROREGOBRA, A.FEC_FECHAINICIO_FC, A.FEC_FECHATERMINO_FC, A.NUM_PERIODO" 
					+" ) X, (SELECT TO_CHAR(ADD_MONTHS(TRUNC(TO_DATE('"+fecFin+"','DD/MM/YYYY'), 'mm'), 1 - ROWNUM),'yyyymm') AS PERIODOS"
					+" FROM DUAL"
					+" CONNECT BY LEVEL < (MONTHS_BETWEEN(TO_DATE('"+fecFin+"','DD/MM/YYYY'), TRUNC(TO_DATE('"+fecIni+"','DD/MM/YYYY'), 'mm')) + 1)) Y"
					+" WHERE X.PERIODOSPRESENTADOS(+) = Y.PERIODOS"
					+" ORDER BY PERIODOS) vistaPeriodo ");
		
		List<AbstractModel> lis=catalogoServiceBean.consultaSQL(query.toString());
		logger.debug("query periodos Oct="+query.toString());
		
		return lis;
	}

}
