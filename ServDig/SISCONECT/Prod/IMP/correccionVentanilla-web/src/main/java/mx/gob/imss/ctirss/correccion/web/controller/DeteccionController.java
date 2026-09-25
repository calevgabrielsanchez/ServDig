package mx.gob.imss.ctirss.correccion.web.controller;

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
import mx.gob.imss.ctirss.correccion.catalogos.model.CgtCatCriterioeleccion;
import mx.gob.imss.ctirss.correccion.catalogos.model.CrcEstatusDeteccion;
import mx.gob.imss.ctirss.correccion.catalogos.model.CrcTipoobra;
import mx.gob.imss.ctirss.correccion.catalogos.model.CrtNroFolio;
import mx.gob.imss.ctirss.correccion.catalogos.service.interfaces.CatalogosService;
import mx.gob.imss.ctirss.correccion.deteccion.base.paginador.model.CrtDeteccionWrapperDataTable;
import mx.gob.imss.ctirss.correccion.deteccion.model.CrtDeteccion;
import mx.gob.imss.ctirss.correccion.deteccion.service.interfaces.DeteccionService;
import mx.gob.imss.ctirss.correccion.framework.base.controller.AbstractController;
import mx.gob.imss.ctirss.correccion.framework.base.model.AbstractModel;
import mx.gob.imss.ctirss.correccion.model.SatPatron;
import mx.gob.imss.ctirss.correccion.model.SatUbicacion;
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
@RequestMapping(value="/catalogo/deteccion")
public class DeteccionController extends AbstractController {
	
	@Autowired
	private DeteccionService<CrtDeteccion> deteccionServiceBean;
	
	@Autowired
	private DomiciliosInegiService<DgDomicilioGeografico> domiciliosInegiServiceBean;
	
	@Autowired
	private ICatalogoService<AbstractModel> catalogoServiceBean;
	
	@Autowired
	private CatalogosService catalogosService;
		
	@Autowired
	private IPatronesService patronesService;
	
	@Autowired
	private ICatalogoService<CrcEstatusDeteccion> catalogoEstatusDetServiceBean;
	
	@Autowired
	private DeteccionService<SatUbicacion> ubicacionServiceBean;
	
	@Autowired
	private ICatalogoService<CrcTipoobra> catalogoTipoObraServiceBean;
	
	private final static Logger logger = Logger.getLogger(DeteccionController.class);
	
		
	
	@RequestMapping(method=RequestMethod.GET)
	public String getCreateForm(Model model) {
		CrtDeteccion ctr = new CrtDeteccion();

		model.addAttribute(ctr);
		
    	 return "catalogos/deteccion/deteccionMain";
	}
	
	
	
	@RequestMapping(value="/modificar" , method=RequestMethod.POST)
	public @ResponseBody CrtDeteccion modify(@RequestBody CrtDeteccion deteccion, HttpServletResponse response) {
		logger.debug(".--. EN MODIFICAR");
		this.deteccionServiceBean.modificar(deteccion);
		logger.debug(".--. MODIFICO");
		return deteccion;
	}
	
	@RequestMapping(value="/eliminar" , method=RequestMethod.POST)
	public @ResponseBody CrtDeteccion delete(@RequestBody CrtDeteccion deteccion, HttpServletResponse response) {
		logger.debug(".--. EN eliminar");
		this.deteccionServiceBean.eliminar(deteccion);
		logger.debug("ELIMINO");
		return deteccion;
	}
	
	@RequestMapping(value="/agregar" , method=RequestMethod.POST)
	public @ResponseBody CrtDeteccion create(@RequestBody CrtDeteccion deteccion, HttpServletResponse response,HttpServletRequest request) {
		UserSession user = getUsuarioFirmado(request);		
		Hashtable<String,Object> dom = (Hashtable)getDomicilioInegiSession(request);
		DgDomicilioGeografico dg = (DgDomicilioGeografico)dom.get("dom");
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
		dg = this.domiciliosInegiServiceBean.agregar(dg);
		deteccion.setNuFoliodeteccion(this.generaFolioDeteccion(new Long(user.getCveCodigoDelegacion()),new Long(user.getCveCodigoSubDelegacion()),Functions.stringToDate(deteccion.getFechaDeteccion())));
		deteccion.setFecFechareg(Functions.stringToDate(dateFormat.format(new Date())));		
		deteccion.setFecFechadeteccionFc(Functions.FormateaFecha(deteccion.getFechaDeteccion(), "-"));	
		deteccion.setDomicilioId(new BigDecimal(dg.getDomicilioId()).intValue());
		if(deteccion.getFechaEstimIncio2()!=null)
			deteccion.setFecFechainicioEst(Functions.FormateaFecha(deteccion.getFechaEstimIncio2(), "-"));
		if(deteccion.getFechaEstTerm2()!=null)
			deteccion.setFecFechaterminoEst(Functions.FormateaFecha(deteccion.getFechaEstTerm2(), "-"));
		if(deteccion.getTipClaseobra().equalsIgnoreCase("-1")) deteccion.setTipClaseobra(null);
		if(deteccion.getCvePkTipObra()!=null && deteccion.getCvePkTipObra().intValue()<1) deteccion.setCvePkTipObra(null);
		if(deteccion.getCvePkFaseConst()!=null && deteccion.getCvePkFaseConst().intValue()==-1) deteccion.setCvePkFaseConst(null);
		if(deteccion.getCveFkZona()!=null && deteccion.getCveFkZona().intValue()==-1) deteccion.setCveFkZona(null);
		deteccion.setSdelegOrig(new BigDecimal(user.getIdSubDelegacion()));
		deteccion.setCveUsuario(user.getCurpUsuario().toString());				
		deteccion.setIdPromovido(new BigDecimal(1));
		this.deteccionServiceBean.agregar(deteccion);
		removeDomicilioInegiSession(request);		
		return deteccion;
	}
	
	@RequestMapping(value="/actualizar" , method=RequestMethod.POST)
	public @ResponseBody CrtDeteccion actualizar(@RequestBody CrtDeteccion deteccion, HttpServletResponse response,HttpServletRequest request) {
		UserSession user = getUsuarioFirmado(request);		
		Hashtable<String,Object> dom = (Hashtable)getDomicilioInegiSession(request);
		DgDomicilioGeografico dg = (DgDomicilioGeografico)dom.get("dom");
		if(deteccion.getCvePkFaseConst()!=null && (deteccion.getCvePkFaseConst()==-1 || deteccion.getCvePkFaseConst()==0)) deteccion.setCvePkFaseConst(null);
		if(deteccion.getCvePkTipObra()!=null && (deteccion.getCvePkTipObra()==-1 || deteccion.getCvePkTipObra()==0)) deteccion.setCvePkTipObra(null);
		if(deteccion.getCveFkZona()!=null && deteccion.getCveFkZona()==-1) deteccion.setCveFkZona(null);
		
		if(deteccion.getFechaEstimIncio()!=null && !deteccion.getFechaEstimIncio().equalsIgnoreCase("")){
			deteccion.setFecFechainicioEst(Functions.stringToDate(deteccion.getFechaEstimIncio()));			
		}if(deteccion.getFechaEstTerm()!=null && !deteccion.getFechaEstTerm().equalsIgnoreCase("")){
			deteccion.setFecFechaterminoEst(Functions.stringToDate(deteccion.getFechaEstTerm()));
		}
		
		deteccion.setFecFechadeteccionFc(Functions.FormateaFecha(deteccion.getFechaDeteccion(), "-"));
		deteccion.setFecFechareg(Functions.stringToDate(deteccion.getFechaRegistro()));
		dg = this.domiciliosInegiServiceBean.agregar(dg);
		deteccion.setDomicilioId(new BigDecimal(dg.getDomicilioId()).intValue());		
		this.deteccionServiceBean.modificar(deteccion);
		removeDomicilioInegiSession(request);		
		return deteccion;
	}
	
	
	
	@RequestMapping(value="/consultar" , method=RequestMethod.POST)
	public @ResponseBody List<CrtDeteccion> consultar(@RequestBody CrtDeteccion deteccion,HttpServletRequest request , HttpServletResponse response) {
		if(deteccion.getCvePkFaseConst()!=null && deteccion.getCvePkFaseConst().intValue()==-1) deteccion.setCvePkFaseConst(null);
		if(deteccion.getCvePkTipObra()!=null && deteccion.getCvePkTipObra().intValue()==-1) deteccion.setCvePkTipObra(null);
		List<CrtDeteccion> rsFinal = new ArrayList<CrtDeteccion>();
		if(deteccion.getFechaEstimIncio()!=null){
			deteccion.setFecFechainicioEst(Functions.stringToDate(deteccion.getFechaEstimIncio()));			
		}if(deteccion.getFechaEstTerm()!=null){
			deteccion.setFecFechaterminoEst(Functions.stringToDate(deteccion.getFechaEstTerm()));
		}
		List<CrtDeteccion> rs = this.deteccionServiceBean.consultar(deteccion);												
		
		if(rs.size()>0){
			Hashtable<String,Object> dom = (Hashtable)getDomicilioInegiSession(request);
			DgDomicilioGeografico dg = (DgDomicilioGeografico)dom.get("dom");
			
			List<DgDomicilioGeografico> domicilios = this.domiciliosInegiServiceBean.consultar(dg);
			if(domicilios.size()>0){
				Iterator<?> iterator = domicilios.iterator();
				while(iterator.hasNext()){
					DgDomicilioGeografico domicilio = (DgDomicilioGeografico)iterator.next();
					Iterator<?> iteraRs = rs.iterator();					
					while(iteraRs.hasNext()){
						CrtDeteccion det = (CrtDeteccion)iteraRs.next();
						if(det.getDomicilioId().intValue()==(new BigDecimal(domicilio.getDomicilioId())).intValue()){
							rsFinal.add(det);
						}
					}
				}
			}
		}
		
		if(rsFinal.size()>0){
			return rsFinal;
		}else
			return null;
	}
	
	@RequestMapping(value="/mostrar")
	public @ResponseBody CrtDeteccion mostrar(@RequestBody CrtDeteccion deteccion){
		String numExt = "";
		String numInt = "";
		if(deteccion.getCveDeteccion()>0){
			deteccion = this.deteccionServiceBean.consultaPorClave(deteccion);
			deteccion.setFechaRegistro(Functions.dateToString(deteccion.getFecFechareg()));
			if(deteccion.getDomicilioId()!=null){
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
			}
			if(deteccion.getCveFkPatron()!=null){
				SatPatron patron = this.patronesService.getById(new Long(deteccion.getCveFkPatron()));					
				deteccion.setRegPatron(patron.getRegistroPatronal().substring(0,patron.getRegistroPatronal().length()-1));				
			}if(deteccion.getCvePkTipObra()!=null){
				deteccion.setTipoObra(this.catalogosService.getTipoObraById(deteccion.getCvePkTipObra()));
			}if(deteccion.getCvePkFaseConst()!=null){
				deteccion.setFaseObra(this.catalogosService.getFaseObraById(deteccion.getCvePkFaseConst()));
			}
			deteccion.setFechaDeteccion(Functions.dateToString(deteccion.getFecFechadeteccionFc()));
			deteccion.setFechaEstimIncio2(Functions.dateToString(deteccion.getFecFechainicioEst()));
			deteccion.setFechaEstTerm2(Functions.dateToString(deteccion.getFecFechaterminoEst()));
		}		
		return deteccion;
	}
	
	@RequestMapping(value="/cancelar" , method=RequestMethod.POST)
	public @ResponseBody CrtDeteccion cancelar(@RequestBody CrtDeteccion deteccion, HttpServletResponse response) {
		Integer idCance = deteccion.getIdMotivocancelacion();
		deteccion = this.consultaPorClave(deteccion);
		deteccion.setIdMotivocancelacion(idCance);
		deteccion.setFecFechaCancela(Calendar.getInstance().getTime());
		this.deteccionServiceBean.modificar(deteccion);
		return deteccion;
	}
	
	@RequestMapping(value="/consultaPorClave")
	public @ResponseBody CrtDeteccion consultaPorClave(@RequestBody CrtDeteccion deteccion){
		logger.debug("CVE ID A BUSCAR:"+deteccion.getCveDeteccion());
		if(deteccion.getCveDeteccion()>0){
			deteccion = deteccionServiceBean.consultaPorClave(deteccion);	
		}
		return deteccion;
	}
	
	@RequestMapping(value="/promocion")
	public @ResponseBody CrtDeteccion promocion(@RequestBody CrtDeteccion deteccion){
		logger.debug("ID A ACTUALIZAR:"+deteccion.getCveDeteccion());
		deteccion = this.consultaPorClave(deteccion);
		deteccion.setIdPromovido(new BigDecimal(1));
		this.deteccionServiceBean.modificar(deteccion);			
		return deteccion;
	}
	
	@RequestMapping(value="/paginar", method=RequestMethod.POST )
    public @ResponseBody DatosSalidaPaginador<CrtDeteccion> pagina(@RequestBody CrtDeteccionWrapperDataTable aoData, HttpServletResponse response,HttpServletRequest request) {
		logger.debug(".-.-controller public @ResponseBody DatosSalidaPaginador<CrtDeteccion> pagina(@RequestBody CrtDeteccionWrapperDataTable aoData ) {");
		UserSession user = getUsuarioFirmado(request);		
		DatosEntradaPaginador send = new DatosEntradaPaginador();
		DatosSalidaPaginador<CrtDeteccion> reply = new DatosSalidaPaginador<CrtDeteccion>();
		
		aoData.getoForm().setSdelegOrig(new BigDecimal(user.getIdSubDelegacion()));
		
		if(aoData.getoForm().getCvePkTipObra()!=null && aoData.getoForm().getCvePkTipObra().intValue()==-1) aoData.getoForm().setCvePkTipObra(null);
		if(aoData.getoForm().getCvePkFaseConst()!=null && aoData.getoForm().getCvePkFaseConst().intValue()==-1) aoData.getoForm().setCvePkFaseConst(null);

		if(aoData.getoForm().getFechaEstimIncio()!=null){
			aoData.getoForm().setFecFechainicioEst(Functions.stringToDate(aoData.getoForm().getFechaEstimIncio()));			
		}if(aoData.getoForm().getFechaEstTerm()!=null){
			aoData.getoForm().setFecFechaterminoEst(Functions.stringToDate(aoData.getoForm().getFechaEstTerm()));
		}											

		if( (aoData.getoForm().getFechaIncial()!=null && aoData.getoForm().getFechaFinal()!=null) || aoData.getoForm().getNuFoliodeteccion() != null){
			send.parserArray(aoData.getAoData());
			send.setModelo(aoData.getoForm());	
			reply = this.deteccionServiceBean.pagina(send);
			if(reply != null && reply.getAaData() != null && reply.getAaData().size() > 0){
				for (Iterator iterator = reply.getAaData().iterator(); iterator.hasNext();) {
					CrtDeteccion type = (CrtDeteccion) iterator.next();
					if(type.getIdPromovido() != null && type.getIdPromovido().intValue() == 1 && type.getIdMotivocancelacion() == null){
						type.setEstatus("PROMOVER A SATIC A");
					}else if(type.getIdPromovido() != null && type.getIdPromovido().intValue() == 0 && type.getIdMotivocancelacion() == null){
						type.setEstatus("PROMOVER A EX");
					}else if(type.getIdMotivocancelacion() != null){
						type.setEstatus("CANCELADO");
					}else if (type.getIdPromovido() == null && type.getIdMotivocancelacion() == null){
						type.setEstatus("SIN DOMICILIO");
					}
				}
			}
			reply.setsEcho(send.getsEcho());
		}		

		logger.debug(".-.-controller realizo consulta) {");		

        return reply;
    }
	
	@RequestMapping(value="/validar", method=RequestMethod.POST )
    public @ResponseBody DatosSalidaPaginador<CrtDeteccion> valida(@RequestBody CrtDeteccionWrapperDataTable aoData,  HttpServletResponse response,HttpServletRequest request ) {
		logger.debug(".-.-controller public @ResponseBody DatosSalidaPaginador<CrtDeteccion> pagina(@RequestBody CrtDeteccionWrapperDataTable aoData ) {");
		DatosEntradaPaginador send = new DatosEntradaPaginador();
		DatosSalidaPaginador<CrtDeteccion> reply = new DatosSalidaPaginador<CrtDeteccion>();
			
		if(aoData.getoForm().getRefColonia()!= null && aoData.getoForm().getDomCalle()!= null &&
				aoData.getoForm().getNumNroext()!= null &&	aoData.getoForm().getNumCodigopostal()!= null){
			if(!aoData.getoForm().getRefColonia().equalsIgnoreCase("") && !aoData.getoForm().getDomCalle().equalsIgnoreCase("") &&
					!aoData.getoForm().getNumNroext().equalsIgnoreCase("") && !aoData.getoForm().getNumCodigopostal().equalsIgnoreCase("")){
				
				send.parserArray(aoData.getAoData());
				send.setModelo(aoData.getoForm());
				
				Hashtable<String,Object> dom = (Hashtable)getDomicilioInegiSession(request);
				DgDomicilioGeografico dg = (DgDomicilioGeografico)dom.get("dom");
				
				List<DgDomicilioGeografico> domicilios = this.domiciliosInegiServiceBean.consultar(dg);
				
				reply = this.deteccionServiceBean.valida(send,domicilios);
				logger.debug(".-.-controller realizo consulta) {");
		        reply.setsEcho(send.getsEcho());
			}
		}
	        
        return reply;
    }
	
	@RequestMapping(value="/validarSatic", method=RequestMethod.POST )
    public @ResponseBody DatosSalidaPaginador<CrtDeteccion> validaSatic(@RequestBody CrtDeteccionWrapperDataTable aoData ) {
		logger.debug(".-.-controller public @ResponseBody DatosSalidaPaginador<CrtDeteccion> pagina(@RequestBody CrtDeteccionWrapperDataTable aoData ) {");
		DatosEntradaPaginador send = new DatosEntradaPaginador();
		DatosSalidaPaginador<CrtDeteccion> reply = new DatosSalidaPaginador<CrtDeteccion>();
		
		if(aoData.getoForm().getRefColonia()!= null && aoData.getoForm().getDomCalle()!= null &&
				aoData.getoForm().getNumNroext()!= null && aoData.getoForm().getNumCodigopostal()!= null){
			if(!aoData.getoForm().getRefColonia().equalsIgnoreCase("") && !aoData.getoForm().getDomCalle().equalsIgnoreCase("") &&
					!aoData.getoForm().getNumNroext().equalsIgnoreCase("") && !aoData.getoForm().getNumCodigopostal().equalsIgnoreCase("")){
									
				if(aoData.getoForm().getCvePkTipObra()!=null && aoData.getoForm().getCvePkTipObra().intValue()==-1) aoData.getoForm().setCvePkTipObra(null);
				if(aoData.getoForm().getCvePkFaseConst()!=null && aoData.getoForm().getCvePkFaseConst().intValue()==-1) aoData.getoForm().setCvePkFaseConst(null);		
				
				aoData.getoForm().setSdelegOrig(new BigDecimal(1));
				
				send.parserArray(aoData.getAoData());
				send.setModelo(aoData.getoForm());
				
				reply = this.deteccionServiceBean.validacionObraSatic(send);
				logger.debug(".-.-controller realizo consulta) {");
		        reply.setsEcho(send.getsEcho());
			}
		}
        return reply;
    }
	
	@RequestMapping(value="/obtenerDomicilioSession", method=RequestMethod.POST )
	public @ResponseBody CrtDeteccion obtenerDomicilio(@RequestBody CrtDeteccion deteccion,HttpServletRequest request) {		
		Hashtable<String,Object> dom = (Hashtable)getDomicilioInegiSession(request);
		String numExt = "";
		String numInt = "";
		if(dom!=null){
			DgDomicilioGeografico dg = (DgDomicilioGeografico)dom.get("dom");		
			deteccion.setRefColonia(dg.getDgAsentamiento().getNomAsen());
			if(dg.getDgCatLocalidad().getDgCatMunicipio().getDgCatEstado().getNomEnt()!=null && !dg.getDgCatLocalidad().getDgCatMunicipio().getDgCatEstado().getNomEnt().equalsIgnoreCase(""))
				deteccion.setEstado(dg.getDgCatLocalidad().getDgCatMunicipio().getDgCatEstado().getNomEnt());
			if(dg.getDgCatLocalidad().getDgCatMunicipio().getNomMun()!=null && !dg.getDgCatLocalidad().getDgCatMunicipio().getNomMun().equalsIgnoreCase(""))
				deteccion.setMunicipio(dg.getDgCatLocalidad().getDgCatMunicipio().getNomMun());
			if(dg.getNomvial()!=null){
				if(!dg.getNomvial().equalsIgnoreCase(""))
					deteccion.setDomCalle(dg.getNomvial());
				else
					deteccion.setDomCalle(dg.getDgVialidadByCveViaPrin().getNomVia());
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
			deteccion.setDomicilioInegi(dg);
		}
			
		return deteccion;
	}
	
	@RequestMapping(value="alta/obtenerDomicilioSession", method=RequestMethod.POST )
	public @ResponseBody CrtDeteccion altaobtenerDomicilio(@RequestBody CrtDeteccion deteccion,HttpServletRequest request) {		
		Hashtable<String,Object> dom = (Hashtable)getDomicilioInegiSession(request);
		String numExt = "";
		String numInt = "";
		if(dom!=null){
			DgDomicilioGeografico dg = (DgDomicilioGeografico)dom.get("dom");		
			deteccion.setRefColonia(dg.getDgAsentamiento().getNomAsen());
			if(dg.getDgCatLocalidad().getDgCatMunicipio().getDgCatEstado().getNomEnt()!=null && !dg.getDgCatLocalidad().getDgCatMunicipio().getDgCatEstado().getNomEnt().equalsIgnoreCase(""))
				deteccion.setEstado(dg.getDgCatLocalidad().getDgCatMunicipio().getDgCatEstado().getNomEnt());
			if(dg.getDgCatLocalidad().getDgCatMunicipio().getNomMun()!=null && !dg.getDgCatLocalidad().getDgCatMunicipio().getNomMun().equalsIgnoreCase(""))
				deteccion.setMunicipio(dg.getDgCatLocalidad().getDgCatMunicipio().getNomMun());
			if(dg.getNomvial()!=null){
				if(!dg.getNomvial().equalsIgnoreCase(""))
					deteccion.setDomCalle(dg.getNomvial());
				else
					deteccion.setDomCalle(dg.getDgVialidadByCveViaPrin().getNomVia());
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
			deteccion.setDomicilioInegi(dg);
		}
			
		return deteccion;
	}
	
	public synchronized String generaFolioDeteccion(Long del,Long sDel, Date fecha){
		
		String consecutivo = this.deteccionServiceBean.obtieneFolios(del, sDel, fecha);
		
		
		return consecutivo;
	}
	
	@RequestMapping(value="/sessionDomicilioGeografico", method=RequestMethod.POST )
	public @ResponseBody DgDomicilioGeografico almacenaSessionDomicilioInegi(@RequestBody DgDomicilioGeografico domicilioInegi,
			HttpServletRequest request) {
		removeDomicilioInegiSession(request);
		domicilioInegi.setHastableKeyDG("dom");
		
		return new DomGeograficosController().almacenaSessionDomicilioInegi(domicilioInegi, request);
	}
	
	@RequestMapping(value="/deteccionDomGeografico" , method=RequestMethod.GET)
	public String callDomGeograficos(HttpServletResponse response, HttpServletRequest request, Model model) {
		Hashtable<String,Object> dom = (Hashtable)getDomicilioInegiSession(request);
		DgDomicilioGeografico dg = null;
		if(dom!=null){
			dg = (DgDomicilioGeografico)dom.get("dom");
			return new DomGeograficosController().getCreateGenericForm(model,dg,request);
		}else								
			return new DomGeograficosController().getCreateGenericForm(model,dg,request);
	}		
	
	@RequestMapping(value="/removerDomicilioSession", method=RequestMethod.POST )
	public @ResponseBody CrtDeteccion removerDomicilio(@RequestBody CrtDeteccion deteccion,HttpServletRequest request) {		
		removeDomicilioInegiSession(request);			
		return deteccion;
	}
	
	@RequestMapping(value="/censor" , method=RequestMethod.POST)
	public @ResponseBody CrtDeteccion getCensor(@RequestBody CrtDeteccion deteccion,HttpServletRequest request) {
		UserSession user = getUsuarioFirmado(request);
		deteccion.setNombreCensor(user.getNombreCompleto());		
		return deteccion;
	}
	
	@RequestMapping(value="/verficaPatron", method=RequestMethod.POST)
	public @ResponseBody Long verificaPatron(@RequestBody CrtDeteccion deteccion,HttpServletRequest request) {							
		return this.catalogosService.getIdPatByRegPat(deteccion.getRegPatron());
	}
	
	@RequestMapping(value="/llenaEstatus", method=RequestMethod.POST )
	public @ResponseBody List<CrcEstatusDeteccion> llenaEstatus (@RequestBody CrtDeteccion deteccion) {	
		
		List<CrcEstatusDeteccion> lstResult = this.catalogoEstatusDetServiceBean.consultaSQL("select ed.CVE_ESTATUS,ed.DESC_ESTATUS from CRC_ESTATUS_DETECCION ed");
		
		return lstResult;
	}

	@RequestMapping(value="/sinDomicilio",method=RequestMethod.POST)
	public @ResponseBody CrtDeteccion sinDomicilio(@RequestBody CrtDeteccion model,HttpServletRequest request) {
		request.getSession().setAttribute("sinDomicilio", model.getCveDeteccion());
    	 return model;
	}
	
	@RequestMapping(value="/altaModal" , method=RequestMethod.GET)
	public String callaltaModal(HttpServletResponse response, HttpServletRequest request, Model model) {			
		
		Long idDeteccion = (Long)request.getSession().getAttribute("sinDomicilio");
		CrtDeteccion det = new CrtDeteccion();
		
		if(idDeteccion != null){
			det.setCveDeteccion(idDeteccion);
			det = this.deteccionServiceBean.consultaPorClave(det);
			
		}
		model.addAttribute("CrtDeteccion", det);
		
		return new DeteccionAltaController().getCreateGenericForm(model);
	}	
	
	@RequestMapping(value="alta/consultar" , method=RequestMethod.POST)
	public @ResponseBody List<CrtDeteccion> altaconsultar(@RequestBody CrtDeteccion deteccion,HttpServletRequest request , HttpServletResponse response) {
		
		List<CrtDeteccion> rsFinal = new ArrayList<CrtDeteccion>();
		String sessionDelegacion = "";
		String dgDelegacion = "";
		
		Hashtable<String,Object> dom = (Hashtable)getDomicilioInegiSession(request);
		DgDomicilioGeografico dg = (DgDomicilioGeografico)dom.get("dom");
		
		UserSession session = this.getUsuarioFirmado(request);
		if(session != null && session.getNombreDelegacion() != null){
			sessionDelegacion = session.getNombreDelegacion().toUpperCase();
		}
		if(dg != null && dg.getDgCatLocalidad() != null && dg.getDgCatLocalidad().getDgCatMunicipio() != null && 
				dg.getDgCatLocalidad().getDgCatMunicipio().getDgCatEstado() != null && dg.getDgCatLocalidad().getDgCatMunicipio().getDgCatEstado().getNomEnt() != null){
			dgDelegacion = dg.getDgCatLocalidad().getDgCatMunicipio().getDgCatEstado().getNomEnt().toUpperCase();
		}
		if(!sessionDelegacion.equals("") && !dgDelegacion.equals("")){
			if(sessionDelegacion.equals(dgDelegacion)){
				List<DgDomicilioGeografico> domicilios = this.domiciliosInegiServiceBean.consultar(dg);
				if(domicilios != null && domicilios.size()>0){
					for (Iterator iterator = domicilios.iterator(); iterator.hasNext();) {
						DgDomicilioGeografico dgDomicilioGeografico = (DgDomicilioGeografico) iterator.next();
						CrtDeteccion detFind = new CrtDeteccion();
						detFind.setDomicilioId((int) dgDomicilioGeografico.getDomicilioId());
						CrtDeteccion lstDet = this.deteccionServiceBean.consultarXIdDom(detFind);
						if(lstDet != null){
							rsFinal.add(lstDet);
						}
					}					
				}
			}else{
				CrtDeteccion detEdo = new CrtDeteccion();
				detEdo.setEstatus("diferente");
				rsFinal.add(detEdo);
			}	
		}
		
		if(rsFinal != null && rsFinal.size()>0){
			return rsFinal;
		}else
			return null;
	}
	
	@RequestMapping(value="alta/validar", method=RequestMethod.POST )
    public @ResponseBody DatosSalidaPaginador<CrtDeteccion> altavalida(@RequestBody CrtDeteccionWrapperDataTable aoData,  HttpServletResponse response,HttpServletRequest request ) {
		logger.debug(".-.-controller public @ResponseBody DatosSalidaPaginador<CrtDeteccion> pagina(@RequestBody CrtDeteccionWrapperDataTable aoData ) {");
		DatosEntradaPaginador send = new DatosEntradaPaginador();
		DatosSalidaPaginador<CrtDeteccion> reply = new DatosSalidaPaginador<CrtDeteccion>();
		List<CrtDeteccion> rsFinal = new ArrayList<CrtDeteccion>();
			
		if(aoData.getoForm().getRefColonia()!= null && aoData.getoForm().getDomCalle()!= null &&
				aoData.getoForm().getNumNroext()!= null &&	aoData.getoForm().getNumCodigopostal()!= null){
			if(!aoData.getoForm().getRefColonia().equalsIgnoreCase("") && !aoData.getoForm().getDomCalle().equalsIgnoreCase("") &&
					!aoData.getoForm().getNumNroext().equalsIgnoreCase("") && !aoData.getoForm().getNumCodigopostal().equalsIgnoreCase("")){
				
				send.parserArray(aoData.getAoData());
				send.setModelo(aoData.getoForm());
				
				Hashtable<String,Object> dom = (Hashtable)getDomicilioInegiSession(request);
				DgDomicilioGeografico dg = (DgDomicilioGeografico)dom.get("dom");
				
				List<DgDomicilioGeografico> domicilios = this.domiciliosInegiServiceBean.consultar(dg);
				
				if(domicilios != null && domicilios.size()>0){
					for (Iterator iterator = domicilios.iterator(); iterator.hasNext();) {
						DgDomicilioGeografico dgDomicilioGeografico = (DgDomicilioGeografico) iterator.next();
						CrtDeteccion detFind = new CrtDeteccion();
						detFind.setDomicilioId((int) dgDomicilioGeografico.getDomicilioId());
						CrtDeteccion lstDet = this.deteccionServiceBean.consultarXIdDom(detFind);
						if(lstDet != null){
							lstDet.setDomCalle(dgDomicilioGeografico.getNomvial());
							lstDet.setRefColonia(dgDomicilioGeografico.getDgAsentamiento().getNomAsen());
							lstDet.setNumNroint(dgDomicilioGeografico.getNumintnum() == null ? "" : dgDomicilioGeografico.getNumintnum().toString());
							lstDet.setNumNroext(dgDomicilioGeografico.getNumextnum() == null ? "" : dgDomicilioGeografico.getNumextnum().toString());
							rsFinal.add(lstDet);
						}
					}
					
				}
				
				int iTotalRecords = 0;
				/*Se debe de obtener el numero total de registros en la base de datos*/
				if(rsFinal!=null)
					iTotalRecords = rsFinal.size();
				
				/**
				 * Total records, after filtering (i.e. the total number of records
				 * after filtering has been applied - not just the number of records
				 * being returned in this result set)
				 */
				int iTotalDisplayRecords = 0;
				
				if(rsFinal!=null)
					iTotalDisplayRecords = rsFinal.size();
			     
				reply.setAaData(rsFinal);
				reply.setiTotalDisplayRecords(iTotalDisplayRecords);
				reply.setiTotalRecords(iTotalRecords);
		        reply.setsEcho(send.getsEcho());
			}
		}
	        
        return reply;
    }
	
	@RequestMapping(value="alta/validarSatic", method=RequestMethod.POST )
    public @ResponseBody DatosSalidaPaginador<CrtDeteccion> validaSatic(@RequestBody CrtDeteccionWrapperDataTable aoData ,HttpServletRequest request ) {
		logger.debug(".-.-controller public @ResponseBody DatosSalidaPaginador<CrtDeteccion> pagina(@RequestBody CrtDeteccionWrapperDataTable aoData ) {");
		DatosEntradaPaginador send = new DatosEntradaPaginador();
		DatosSalidaPaginador<CrtDeteccion> reply = new DatosSalidaPaginador<CrtDeteccion>();
		String numCP = "";
		if(aoData != null && !aoData.getoForm().getNumCodigopostal().equals("")){
			numCP = aoData.getoForm().getNumCodigopostal();
			UserSession session = this.getUsuarioFirmado(request);
			if(session != null && session.getIdSubDelegacion() != null){
				aoData.getoForm().setSdelegOrig(BigDecimal.valueOf(session.getIdSubDelegacion()));
			}
					
			send.parserArray(aoData.getAoData());
			send.setModelo(aoData.getoForm());
			
			reply = this.deteccionServiceBean.validacionObraSatic(send);
			if(reply.getAaData() != null && reply.getAaData().size() > 0){
				for (Iterator iterator = reply.getAaData().iterator(); iterator.hasNext();) {
					CrtDeteccion type = (CrtDeteccion) iterator.next();
					if(type != null && type.getCveFkPatron() != null){
						SatPatron patron = this.patronesService.getById(type.getCveFkPatron());
						if(patron != null && patron.getUbicacion() != null){
							SatUbicacion ubicacion = new SatUbicacion();
							ubicacion.setPatron(patron);
							ubicacion = this.ubicacionServiceBean.buscaUbicacion(ubicacion);
							if(ubicacion != null){
								type.setDomCalle(ubicacion.getCalle());
								type.setNumNroext(ubicacion.getNumeroExterior());
								type.setNumCodigopostal(ubicacion.getCodigoPostal());
							}
							
						}
					}
					
				}
				reply.setsEcho(send.getsEcho());
			}
			else if(reply.getAaData() != null && reply.getAaData().size() > 0 || !send.getsSearch().equals("")){	
				reply.setsEcho(send.getsEcho());
				}else{
				reply = null;
				}
			
		}
   
        return reply;
    }
	
	@RequestMapping(value="alta/removerDomicilioSession", method=RequestMethod.POST )
	public @ResponseBody CrtDeteccion altaremoverDomicilio(@RequestBody CrtDeteccion deteccion,HttpServletRequest request) {		
		removeDomicilioInegiSession(request);	
		request.getSession().removeAttribute("sinDomicilio");
		return deteccion;
	}
	
	@RequestMapping(value="alta/actualizar" , method=RequestMethod.POST)
	public @ResponseBody CrtDeteccion altaactualizar(@RequestBody CrtDeteccion deteccion, HttpServletResponse response,HttpServletRequest request) {
		UserSession user = getUsuarioFirmado(request);		
		Hashtable<String,Object> dom = (Hashtable)getDomicilioInegiSession(request);
		DgDomicilioGeografico dg = (DgDomicilioGeografico)dom.get("dom");
		if(deteccion.getCvePkFaseConst()!=null && (deteccion.getCvePkFaseConst()==-1 || deteccion.getCvePkFaseConst()==0)) deteccion.setCvePkFaseConst(null);
		if(deteccion.getCvePkTipObra()!=null && (deteccion.getCvePkTipObra()==-1 || deteccion.getCvePkTipObra()==0)) deteccion.setCvePkTipObra(null);
		if(deteccion.getCveFkZona()!=null && deteccion.getCveFkZona()==-1) deteccion.setCveFkZona(null);
		
		if(deteccion.getFechaEstimIncio()!=null && !deteccion.getFechaEstimIncio().equalsIgnoreCase("")){
			deteccion.setFecFechainicioEst(Functions.stringToDate(deteccion.getFechaEstimIncio()));			
		}if(deteccion.getFechaEstTerm()!=null && !deteccion.getFechaEstTerm().equalsIgnoreCase("")){
			deteccion.setFecFechaterminoEst(Functions.stringToDate(deteccion.getFechaEstTerm()));
		}
		
		deteccion.setFecFechadeteccionFc(Functions.FormateaFecha(deteccion.getFechaDeteccion(), "-"));
		deteccion.setFecFechareg(Functions.stringToDate(deteccion.getFechaRegistro()));
		dg = this.domiciliosInegiServiceBean.agregar(dg);
		deteccion.setDomicilioId(new BigDecimal(dg.getDomicilioId()).intValue());		
		this.deteccionServiceBean.modificar(deteccion);
		removeDomicilioInegiSession(request);		
		return deteccion;
	}
	
//	@RequestMapping(value="alta/censores", method=RequestMethod.POST)
//	public @ResponseBody ArrayList censores(@RequestBody CrtDeteccion det,HttpServletRequest request , HttpServletResponse response){
//		UserSession user = getUsuarioFirmado(request);
//		ArrayList lista = (ArrayList) catalogoServiceBean.consultaSQL(  "select u.CVE_ID_USUARIO, u.NOM_NOMBRE" +
//																		" from SEG_USUARIO u " +
//																		"inner join SEG_PERFIL_USUARIO pu on u.CVE_ID_USUARIO = pu.CVE_ID_USUARIO" +
//																		" inner join SEG_ROL r on pu.CVE_ROL = r.CVE_ROL" +
//																		"inner join SEG_USUARIO_FUNCIONARIO uf on u.CVE_ID_USUARIO = uf.CVE_ID_USUARIO" +
//																		" where pu.CVE_ROL = 3" +
//																		"and uf.CVE_ID_SUBDELEGACION =" + user.getIdSubDelegacion() +
//																		"order by u.NOM_PATERNO,u.NOM_PATERNO,u.NOM_NOMBRE");		
//		return lista;
//	}	
	
	@RequestMapping(value="alta/agregar" , method=RequestMethod.POST)
	public @ResponseBody CrtDeteccion altacreate(@RequestBody CrtDeteccion deteccion, HttpServletResponse response,HttpServletRequest request) {
		UserSession user = getUsuarioFirmado(request);		
		Hashtable<String,Object> dom = (Hashtable)getDomicilioInegiSession(request);
		DgDomicilioGeografico dg = (DgDomicilioGeografico)dom.get("dom");
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
		
		dg = this.domiciliosInegiServiceBean.agregar(dg);
		CrtDeteccion detTemp = new CrtDeteccion();
		detTemp = this.deteccionServiceBean.consultaPorClave(deteccion);
		//deteccion.setNuFoliodeteccion(this.generaFolioDeteccion(new Long(user.getCveCodigoDelegacion()),new Long(user.getCveCodigoSubDelegacion()),deteccion.getFechaDeteccion().substring(6, deteccion.getFechaDeteccion().length())));
		detTemp.setFecFechareg(Functions.stringToDate(dateFormat.format(new Date())));
		if(deteccion.getFechaDeteccion() != null){
			detTemp.setFecFechadeteccionFc(Functions.FormateaFecha(deteccion.getFechaDeteccion(), "-"));
		}
		if(deteccion.getDomicilioId() == null){
			detTemp.setDomicilioId(new BigDecimal(dg.getDomicilioId()).intValue());
		}
		
		if(deteccion.getFechaEstimIncio2()!=null)
			detTemp.setFecFechainicioEst(Functions.FormateaFecha(deteccion.getFechaEstimIncio2(), "-"));
		if(deteccion.getFechaEstTerm2()!=null)
			detTemp.setFecFechaterminoEst(Functions.FormateaFecha(deteccion.getFechaEstTerm2(), "-"));
		if(deteccion.getTipClaseobra().equals("-1")){ 
			detTemp.setTipClaseobra(null);
		}else{
			detTemp.setTipClaseobra(deteccion.getTipClaseobra());
		}
		if(deteccion.getCvePkTipObra()!=null){ 
			detTemp.setCvePkTipObra(deteccion.getCvePkTipObra());
		}
		if(deteccion.getCvePkFaseConst()!=null && deteccion.getCvePkFaseConst().intValue() < 1){ 
			detTemp.setCvePkFaseConst(null);
		}else{
			detTemp.setCvePkFaseConst(deteccion.getCvePkFaseConst());
			
		}
		if(deteccion.getCveFkZona()!=null && deteccion.getCveFkZona().intValue() < 1){
			detTemp.setCveFkZona(null);
		}else{
			detTemp.setCveFkZona(deteccion.getCveFkZona());
		}
		detTemp.setIdPromovido(deteccion.getIdPromovido());
		detTemp.setSdelegOrig(new BigDecimal(user.getIdSubDelegacion()));
		detTemp.setCveUsuario(user.getCurpUsuario().toString());				
		
		detTemp.setTxActividad(deteccion.getActividad());
		detTemp.setDesDepcontratante(deteccion.getDesDepcontratante());
		detTemp.setDesDependenciapub(deteccion.getDesDependenciapub());
		detTemp.setTxEmail(deteccion.getTxEmail());
		detTemp.setTxTelefono(deteccion.getTxTelefono());
		detTemp.setTxCurppatron(deteccion.getTxCurppatron());
		detTemp.setTxRfcpatron(deteccion.getTxRfcpatron());
		if(deteccion.getNumTrabajdores() != null){
			detTemp.setNumTrabajdores(deteccion.getNumTrabajdores());
		}
		detTemp.setCveTipocorr(deteccion.getCveTipocorr());
		
		
		this.deteccionServiceBean.agregar(detTemp);
		removeDomicilioInegiSession(request);	
		request.getSession().removeAttribute("sinDomicilio");
		return detTemp;
	}
	
	@RequestMapping(value="alta/validaRegPatron" , method=RequestMethod.POST)
	public @ResponseBody CrtDeteccion altaconsultar(@RequestBody CrtDeteccion deteccion) {
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
	
	@RequestMapping(value="alta/validaNuReporte", method=RequestMethod.GET)
	public @ResponseBody boolean altavalidaNuReporte(@RequestParam String nuReportectrlobra,HttpServletRequest request){
		
		return true;
	}	
	
	
	@RequestMapping(value="alta/validaIdDeteccion", method=RequestMethod.POST)
	public @ResponseBody CrtDeteccion validaIdDeteccion(@RequestBody CrtDeteccion det,HttpServletRequest request){
		
		Object obj = request.getSession().getAttribute("sinDomicilio");
		Long idDeteccion = (Long) obj;
		if(idDeteccion != null){
			det.setCveDeteccion(idDeteccion);
			det = this.deteccionServiceBean.consultaPorClave(det);
			if(det.getFecFechadeteccionFc() != null){
				SimpleDateFormat formato = new SimpleDateFormat("dd-MM-yyyy");
				det.setFechaDeteccion(formato.format(det.getFecFechadeteccionFc()));
			}
			if(det.getFecFechainicioEst() != null){
				SimpleDateFormat formato = new SimpleDateFormat("dd-MM-yyyy");
				det.setFechaEstimIncio2(formato.format(det.getFecFechainicioEst()));
			}
			if(det.getFecFechaterminoEst() != null){
				SimpleDateFormat formato = new SimpleDateFormat("dd-MM-yyyy");
				det.setFechaEstTerm2(formato.format(det.getFecFechaterminoEst()));
			}
			det.setBandera("consulta");
			
		}else{
			det = null;
		}

		return det;
	}	
	
	@RequestMapping(value="alta/deteccionDomGeografico" , method=RequestMethod.GET)
	public String altacallDomGeograficos(HttpServletResponse response, HttpServletRequest request, Model model) {
		Hashtable<String,Object> dom = (Hashtable)getDomicilioInegiSession(request);		
		if(dom!=null){
			DgDomicilioGeografico dg = (DgDomicilioGeografico)dom.get("dom");
			return new DomGeograficosController().getCreateGenericForm(model,dg,request);
		}else								
			return new DomGeograficosController().getCreateGenericForm(model,new DgDomicilioGeografico(),request);
	}	
	
	@RequestMapping(value="alta/sessionDomicilioGeografico", method=RequestMethod.POST )
	public @ResponseBody DgDomicilioGeografico altaalmacenaSessionDomicilioInegi(@RequestBody DgDomicilioGeografico domicilioInegi,
			HttpServletRequest request) {
		removeDomicilioInegiSession(request);
		domicilioInegi.setHastableKeyDG("dom");
		
		return new DomGeograficosController().almacenaSessionDomicilioInegi(domicilioInegi, request);
	}
	
	
	@RequestMapping(value="/cboTipoObra", method=RequestMethod.POST )
    public @ResponseBody List<CrcTipoobra> cboTipoObra(@RequestBody CrtDeteccion aoData) {
		
		
		List<CrcTipoobra> lstResult = this.catalogoTipoObraServiceBean.consultaSQL(" select t.CVE_PK,t.DES_TIPOOBRA from SAC_TIPOOBRA t  ");
				
		
		return lstResult;
	}
	
	@RequestMapping(value="alta/censores", method=RequestMethod.POST )
    public @ResponseBody ArrayList censores(@RequestBody CrtDeteccion det,HttpServletRequest request) {		
		UserSession user = getUsuarioFirmado(request);
//		ArrayList lista = (ArrayList) catalogoServiceBean.consultaSQL(  " select u.CVE_ID_USUARIO, u.NOM_NOMBRE " +
//																		" from SEG_USUARIO u " +
//																		" inner join SEG_PERFIL_USUARIO pu on u.CVE_ID_USUARIO = pu.CVE_ID_USUARIO " +
//																		" inner join SEG_ROL r on pu.CVE_ROL = r.CVE_ROL " +
//																		" inner join SEG_USUARIO_FUNCIONARIO uf on u.CVE_ID_USUARIO = uf.CVE_ID_USUARIO " +
//																		" where pu.CVE_ROL = 3 " +
//																		" and uf.CVE_ID_SUBDELEGACION =" + user.getIdSubDelegacion() +
//																		" order by u.NOM_PATERNO,u.NOM_PATERNO,u.NOM_NOMBRE");		
		
		System.out.println("Deteccion COntrolers");
		String query="select a.DES_USR_CURP,a.NOM_NOMBRE,a.DES_SIAP_NSS,a.CVE_MATRICULA,a.NOM_PATERNO, a.NOM_MATERNO"
		+" from SSO_USUARIOS a"
		+" where a.cve_ssodepto = 31 and a.cve_ssopuesto in (74,75)"
		+" and a.CVE_ID_SUBDELEGACION = "+ user.getIdSubDelegacion()
		+" and a.DES_USR_CURP='"+user.getCurpUsuario()+"'"
		+" order by"
        +" a.NOM_PATERNO,"
        +" a.NOM_PATERNO,"
        +" a.NOM_NOMBRE";
		
		ArrayList lista = (ArrayList) catalogoServiceBean.consultaSQL(query);
		
		
		return lista;
	}					
	
}
