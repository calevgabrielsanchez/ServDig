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

import mx.gob.imss.ctirss.correccion.catalogos.model.CgcCatcriterioseleccion;
import mx.gob.imss.ctirss.correccion.catalogos.model.CrcTipoCorr;
import mx.gob.imss.ctirss.correccion.catalogos.model.CrtNroFolio;
import mx.gob.imss.ctirss.correccion.framework.base.controller.AbstractController;
import mx.gob.imss.ctirss.correccion.framework.base.model.AbstractModel;
import mx.gob.imss.ctirss.correccion.framework.utils.ConstantesBusiness;
import mx.gob.imss.ctirss.correccion.model.CgcCatOrigen;
import mx.gob.imss.ctirss.correccion.model.CrtSelector;
import mx.gob.imss.ctirss.correccion.model.SatPatron;
import mx.gob.imss.ctirss.correccion.presentacion.service.interfaces.PresentacionCorreccionServiceController;
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
@RequestMapping(value="/promocion/promocionSBCIndividual")
public class PromocionSBCIndividualController extends AbstractController{
	
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
		
		 return "promocionSBCIndividual";
	}
	
	
	
	@InitBinder
	@RequestMapping(value="/altaPromocionSBC")
	public @ResponseBody CrtPromocion altaPromocionSBC(@RequestBody CrtPromocion promocion, HttpServletResponse response,HttpServletRequest request) throws Exception{
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

		
		return promocion;		
	}
	
	
	
	@InitBinder
	@RequestMapping(value="/verificaExitencia")
	public @ResponseBody CrtPromocion verificaExistencia(@RequestBody CrtPromocion promocion, HttpServletResponse response,HttpServletRequest request) throws Exception{
		UserSession user = this.getUsuarioFirmado(request);
		int digVer = generaDigitoVerificador(promocion.getRegPatron());
		ArrayList listaPatron = (ArrayList)catalogoServiceBean.consultaLibrePorClave(0L, "from SatPatron p where p.registroPatronal= '"+ promocion.getRegPatron() + digVer+ "'");
		SatPatron patron = null;
		if(listaPatron!=null && listaPatron.size()>0){
			patron =(SatPatron) listaPatron.get(0);
		}
		CrtPromocion promo = null;
		if(patron!=null){
			ArrayList listaPromocion = (ArrayList)catalogoServiceBean.consultaLibrePorClave(0L, "from CrtPromocion p where p.nuOficiopro= '"+ promocion.getNuOficiopro()+"'");
			if(listaPromocion!=null && listaPromocion.size()>0){
				promo = (CrtPromocion)listaPromocion.get(0);
			}
		}
		

		
		return promo;		
	}
	
	
	public int generaDigitoVerificador(String nrp) {
        int factorDeConversion = 10;
        int digitoVerificador = 0;
        int paso3 = 0;
        boolean bandera = true;
        String alfabeto = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String clave = "";
        int primeraLetra = alfabeto.indexOf(nrp.toUpperCase().charAt(0));
        if (primeraLetra != -1) {
            clave = (primeraLetra + factorDeConversion) + nrp.substring(1, nrp.length());
        } else {
            clave = nrp;
        }
        int i = clave.length() - 1;
        while (i >= 0) {
            if (bandera) {
                int porDos = Integer.parseInt("" + clave.charAt(i)) * 2;
                if (porDos > 9)// si el resultado es un numero de dos cifras, es necesario tratar
                               // estas por separado.
                {
                    paso3 += (porDos % 10) + (porDos / 10);
                } else {
                    paso3 += porDos;
                }
                bandera = false;
            } else {
                paso3 += Integer.parseInt("" + clave.charAt(i));
                bandera = true;
            }
            i--;
        }
        digitoVerificador = 10 - (paso3 % 10);
        if (digitoVerificador > 9) {
            digitoVerificador = 0;
        }

        return digitoVerificador;
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
	
	
	
	@RequestMapping(value="/quitarDomicilioGeo.do", method=RequestMethod.POST)
	public @ResponseBody String limpiarSesionDomicilio(@RequestBody CgcCatcriterioseleccion datos, HttpServletRequest request){
		removeDomicilioInegiSession(request);
		return "limpio";
	}
	
	@RequestMapping(value="/validaMunicipio.do", method=RequestMethod.POST)
	public @ResponseBody boolean validarMunicipio(@RequestBody CrtPromocion datos, HttpServletRequest request){
		
		boolean resp = false;
		UserSession user = getUsuarioFirmado(request);	
		String MunicipioUser = user.getNombreDelegacion();
		if(datos != null && datos.getMunicipio() != null){
			if(MunicipioUser.equalsIgnoreCase(datos.getMunicipio())){
				resp = true;
			}
		}
			
		
		return resp;
	}
	
	@RequestMapping(value="/validaMunicipioPatron.do", method=RequestMethod.POST)
	public @ResponseBody boolean validaMunicipioPatron(@RequestBody CrtPromocion datos, HttpServletRequest request){
		
		boolean resp = false;
		UserSession user = getUsuarioFirmado(request);	
		String subDelegacionUser = user.getNombreSubDelegacion();
		String subDelegacionPatron = "";
		SatPatron patron = this.patronesService.getById(datos.getCveFkPatron());
		if(patron != null && patron.getUbicacion() != null){
			subDelegacionPatron = patron.getUbicacion().getMunicipio().getNombre();
		}
		if(subDelegacionUser.equalsIgnoreCase(subDelegacionPatron)){
			resp = true;
		}
		
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
	public @ResponseBody SatPatron validaPatron(@RequestBody String parametro ,HttpServletRequest request) {

		UserSession user = getUsuarioFirmado(request);	
		String subDelegacionUser = user.getNombreSubDelegacion();
		String valor = parametro.substring(1, parametro.length() -1);
		String subDelegacionPatron = "";
		SatPatron model = new SatPatron();
		model =  patronesService.validaRegistroPatronalWS(valor, true);
		
		if(model==null){
			model = new SatPatron(); 
			model.setError("El registro patronal inválido");
			return model;
		}
		
		if(!user.getCveCodigoDelegacion().equals(model.getUbicacion().getMunicipio().getSacSubdelegacion().getSacDelegacion().getCveCodigo())
				||	!user.getCveCodigoSubDelegacion().equals(model.getUbicacion().getMunicipio().getSacSubdelegacion().getCveCodigo())){
			model.setError("El registro patronal no pertenece a la subdelegaci\u00f3n");
			return model;
		}else{model.setError("");}
		
		if(model != null){
			subDelegacionPatron = model.getUbicacion().getMunicipio().getSacSubdelegacion().getNomNombre();
			model.setSubDelegacion(subDelegacionPatron);
		}
		
//		if(subDelegacionUser.equalsIgnoreCase(subDelegacionPatron)){
//			model.setBandera("true");
//		}else{
//			model.setBandera("false");
//		}
		
		return model;
	}
		
}
