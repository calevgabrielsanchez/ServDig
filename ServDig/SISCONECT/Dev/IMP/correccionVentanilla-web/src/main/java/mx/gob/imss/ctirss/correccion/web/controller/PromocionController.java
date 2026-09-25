/**
 * ClaseCatalogoController.java
 * @package mx.gob.imss.delta.web.controller
 * @project delta-web	
 */
package mx.gob.imss.ctirss.correccion.web.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolation;

import mx.gob.imss.ctirss.correccion.base.paginador.model.DatosEntradaPaginador;
import mx.gob.imss.ctirss.correccion.base.paginador.model.DatosSalidaPaginador;
import mx.gob.imss.ctirss.correccion.catalogos.model.CrcTipoCorr;
import mx.gob.imss.ctirss.correccion.catalogos.model.CrtNroFolio;
import mx.gob.imss.ctirss.correccion.catalogos.model.SacSubdelegacion;
import mx.gob.imss.ctirss.correccion.framework.base.controller.AbstractController;
import mx.gob.imss.ctirss.correccion.framework.base.model.AbstractModel;
import mx.gob.imss.ctirss.correccion.framework.utils.ConstantesBusiness;
import mx.gob.imss.ctirss.correccion.model.CgcCatOrigen;
import mx.gob.imss.ctirss.correccion.model.CgcCatTipo;
import mx.gob.imss.ctirss.correccion.model.CgcReglaNegocio;
import mx.gob.imss.ctirss.correccion.model.CgtAnexoPago;
import mx.gob.imss.ctirss.correccion.model.CgtAnexoRP;
import mx.gob.imss.ctirss.correccion.model.CgtCatCriterioSeleccion;
import mx.gob.imss.ctirss.correccion.model.CgtPromocion;
import mx.gob.imss.ctirss.correccion.model.Clase;
import mx.gob.imss.ctirss.correccion.model.SatPatron;
import mx.gob.imss.ctirss.correccion.model.SatUbicacion;
import mx.gob.imss.ctirss.correccion.promocionCG.base.paginador.model.AnexoPagosWrapperDataTable;
import mx.gob.imss.ctirss.correccion.promocionCG.base.paginador.model.PromocionWrapperDataTable;
import mx.gob.imss.ctirss.correccion.promocionCG.base.paginador.model.RegistrosPatronalesWrapperDataTable;
import mx.gob.imss.ctirss.correccion.service.interfaces.ICatalogoService;
import mx.gob.imss.ctirss.correccion.service.interfaces.IPatronesService;
import mx.gob.imss.ctirss.correccion.session.UserSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author Lucio Duran Silva
 * @company IMSS (Instituto Mexicano del Seguro Social)
 * @date 29/08/2011
 */
@Controller
@RequestMapping(value="/controlGestion/promocion")
public class PromocionController extends AbstractController {

	@Autowired
	private ICatalogoService<AbstractModel> catalogoServiceBean;
	@Autowired
	private IPatronesService patronesService;
	
	/**
	 * Logger.
	 */
	private final static Logger logger = Logger.getLogger(PromocionController.class); 
	
	
	@RequestMapping(method=RequestMethod.GET)
	public String getCreateForm(Model model) {
		 return "controlGestion/promocion/promocionCGMain";
	}
	
	@RequestMapping(value="/modificar" , method=RequestMethod.POST)
	public @ResponseBody Clase modify(@RequestBody Clase clase, HttpServletResponse response) {
		this.catalogoServiceBean.actualizar(clase);
		return clase;
	}	
	
	@RequestMapping(value="/eliminar" , method=RequestMethod.POST)
	public @ResponseBody Clase delete(@RequestBody Clase clase, HttpServletResponse response) {
		this.catalogoServiceBean.eliminar(clase);
		return clase;
	}		

	
	@RequestMapping(value="/agregar" , method=RequestMethod.POST)
//	public @ResponseBody Clase create(final Model model, @Valid final Clase clase, final BindingResult result) {
	public @ResponseBody Clase create(@RequestBody Clase clase) {
		logger.debug(".-. ingresa en agregar");
//		if(result.hasErrors()){
//			logger.debug(".-. hubo errores");
//			return clase;
//		}
//		else{
//		  logger.debug(".-. no hubo errores");
		  this.catalogoServiceBean.agregar(clase);
//		  logger.debug(".-. ingreso la informacion");
//		}
		return clase;
	}
	
	
	
	
	@RequestMapping(value="/consultaRegla", method=RequestMethod.POST)
	public @ResponseBody CgcReglaNegocio consultaPorClave(@RequestBody CgcReglaNegocio reglaNegocio){
		logger.debug("CVE ID A BUSCAR:"+reglaNegocio.getNombrecontrol());
		if(reglaNegocio.getNombrecontrol()!=null){
		ArrayList lista = (ArrayList) catalogoServiceBean.consultaLibrePorClave(0L, "from CgcReglaNegocio rn where rn.cgcCatTipo.cgcCatflujo.idFlujo=2 and rn.nombrecontrol='"+reglaNegocio.getNombrecontrol()+ "' and rn.cgcCatTipo.idTipo = " + reglaNegocio.getCgcCatTipo().getIdTipo());
		 logger.debug(reglaNegocio.getNivel());
		 if(lista!=null && lista.size()>0)
			 reglaNegocio = (CgcReglaNegocio) lista.get(0);
		}
		return reglaNegocio;
	}	
	
	@RequestMapping(value="/paginar", method=RequestMethod.POST )
    public @ResponseBody DatosSalidaPaginador<CgtAnexoPago> pagina(@RequestBody AnexoPagosWrapperDataTable aoData ) {
		logger.debug(".-.-controller public @ResponseBody DatosSalidaPaginador<CgtAnexoPago> pagina(@RequestBody ClaseWrapperDataTable aoData ) {");
		DatosEntradaPaginador send = new DatosEntradaPaginador();
		
		send.parserArray(aoData.getAoData());
		send.setModelo(new CgtAnexoPago());
		
		DatosSalidaPaginador reply = this.catalogoServiceBean.paginaAnexoPagos(send);
		logger.debug(".-.-controller realizo consulta) {");
        reply.setsEcho(send.getsEcho());
        
        return reply;
    }
	
	
	@RequestMapping(value="/paginarPatrones", method=RequestMethod.POST )
    public @ResponseBody DatosSalidaPaginador<CgtAnexoRP> paginaPatrones(@RequestBody RegistrosPatronalesWrapperDataTable aoData ) {
		logger.debug(".-.-controller public @ResponseBody DatosSalidaPaginador<CgtAnexoPago> pagina(@RequestBody ClaseWrapperDataTable aoData ) {");
		DatosEntradaPaginador send = new DatosEntradaPaginador();
		
		send.parserArray(aoData.getAoData());
		send.setModelo(new CgtAnexoRP());
		
		DatosSalidaPaginador reply = this.catalogoServiceBean.paginaAnexoPatrones(send);
		logger.debug(".-.-controller realizo consulta) {");
        reply.setsEcho(send.getsEcho());
        
        return reply;
    }
	
	
	 @RequestMapping(value="/consultaComboFindFuentePromocion", method=RequestMethod.POST)
		public @ResponseBody ArrayList consultaComboFindFuentePromocion(@RequestBody Long idTipo, HttpServletResponse response,HttpServletRequest request){
			UserSession user = getUsuarioFirmado(request);
			ArrayList listaOrigenes = (ArrayList) catalogoServiceBean.consultaLibrePorClave(0L, "select distinct to.cgcCatOrigen from CgcCatTipoOrigen to where to.cgcCatTipo.idTipo in (5,6,7,8)" );
			return listaOrigenes;
		}
	
	@RequestMapping(value="/consultaReglaDependencia", method=RequestMethod.POST)
	public @ResponseBody CgcReglaNegocio consultaCampoDependencia(@RequestBody CgcReglaNegocio reglaNegocio){
		logger.debug("CVE ID A BUSCAR:"+reglaNegocio.getNombrecontrol());
		if(reglaNegocio.getNombrecontrol()!=null){
		ArrayList lista = (ArrayList) catalogoServiceBean.consultaLibrePorClave(0L, "from CgcReglaNegocio rn where rn.cgcCatTipo.cgcCatflujo.idFlujo=2 and rn.nombrecontrol='"+reglaNegocio.getNombrecontrol()+ "' and rn.cgcCatTipo.idTipo = " + reglaNegocio.getCgcCatTipo().getIdTipo() + " and rn.cveUsuario = '" + reglaNegocio.getCveUsuario()+ "'");
		 logger.debug(reglaNegocio.getNivel());
		 if(lista!=null && lista.size()>0)
			 reglaNegocio = (CgcReglaNegocio) lista.get(0);
		}
		return reglaNegocio;
	}	
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value="/consultaPatronesAP", method=RequestMethod.POST)
	public @ResponseBody ArrayList<CgtPromocion> consultaPatronesAP(@RequestBody CgtAnexoPago anexoPago){
		
		ArrayList lista = (ArrayList) catalogoServiceBean.consultaLibrePorClave(0L, "from CgtPromocion p where p.folio = '"+ anexoPago.getFolio()+"'");
		return lista;
	}	
	@RequestMapping(value="/consultaAnexoPagos", method=RequestMethod.POST)
	public @ResponseBody ArrayList<CgtAnexoPago> consultaAnexoPagos(@RequestBody CgtAnexoPago anexoPago){
		
		ArrayList lista = (ArrayList) catalogoServiceBean.consultaLibrePorClave(0L, "from CgtAnexoPago ap where ap.folio = '"+ anexoPago.getFolio()+"'");
		return lista;
	}	
	
	@RequestMapping(value="/consultaReglaNivel", method=RequestMethod.POST)
	public @ResponseBody ArrayList<CgcReglaNegocio> consultaPorNivel(@RequestBody CgcReglaNegocio reglaNegocio){
		logger.debug("CVE ID A BUSCAR:"+reglaNegocio.getNombrecontrol());
		ArrayList<CgcReglaNegocio> lista = new ArrayList<CgcReglaNegocio>();
		if(reglaNegocio.getNivel()!=null && reglaNegocio.getNivel().intValue()>0){
		 lista = (ArrayList) catalogoServiceBean.consultaLibrePorClave(0L, "from CgcReglaNegocio rn where rn.cgcCatTipo.cgcCatflujo.idFlujo=2 and rn.nivel="+reglaNegocio.getNivel().intValue() +" and rn.nombrecontrol<>'"+reglaNegocio.getNombrecontrol()+ "' and rn.cgcCatTipo.idTipo = " + reglaNegocio.getCgcCatTipo().getIdTipo());
		 logger.debug(reglaNegocio.getNivel());
		 //reglaNegocio = (CgcReglaNegocio) lista.get(0);
		}
		return lista;
	}	
	
	@RequestMapping(value="/obtenerFolio", method=RequestMethod.POST)
	public @ResponseBody ArrayList<CrtNroFolio> obtenerFolio(@RequestBody CrtNroFolio nroFolio, Date ope, HttpServletResponse response,HttpServletRequest request){
		UserSession user = getUsuarioFirmado(request);
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(ope);
		int anio = calendar.get(Calendar.YEAR);
		ArrayList<CrtNroFolio> lista = new ArrayList<CrtNroFolio>();
		 lista = (ArrayList) catalogoServiceBean.consultaLibrePorClave(0L, "from CrtNroFolio nf where nf.numAnio = " + anio + " and nf.cveDelegacion = " + user.getCveCodigoDelegacion() + " and nf.cveSubdelegacion = " + user.getCveCodigoSubDelegacion() + " and nf.crcTipoCorr.cveTipocorr =" + nroFolio.getCrcTipoCorr().getCveTipocorr()) ;
		 if(lista!=null && lista.size() > 0){
			 CrtNroFolio folioUpdate = (CrtNroFolio) lista.get(0);
			 folioUpdate.setNumNumero(new BigDecimal(folioUpdate.getNumNumero().longValue() + 1));
			 this.catalogoServiceBean.actualizar(folioUpdate);
		 }else{
			 CrtNroFolio folioNuevo = new CrtNroFolio();
			 folioNuevo.setCrcTipoCorr(nroFolio.getCrcTipoCorr());
			 folioNuevo.setCveDelegacion(new BigDecimal(user.getIdDelegacion()));
			 folioNuevo.setCveSubdelegacion(new BigDecimal(user.getIdSubDelegacion()));
			 folioNuevo.setNumAnio(new BigDecimal(anio));
			 folioNuevo.setNumNumero(new BigDecimal(1));
			 this.catalogoServiceBean.agregar(folioNuevo);
			 lista.add(folioNuevo);
		 }
		return lista;
	}	
	
	
	@RequestMapping(value="/validaRegPat", method=RequestMethod.POST)
	public @ResponseBody SatPatron validaRegistroPatronal(@RequestBody SatPatron patron){
		SatPatron respuesta =this.patronesService.validaRegistroPatronalWS(patron.getRegistroPatronal().substring(0,10).toUpperCase(), true);
		if (respuesta == null)
			respuesta =this.patronesService.validaRegistroPatronalWS(patron.getRegistroPatronal().substring(0,10).toUpperCase(), true);
		
			
		return respuesta;
		
		
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
	
	@RequestMapping(value="/guardaPromocion", method=RequestMethod.POST)
	public @ResponseBody CgtPromocion guardaPromocion(@RequestBody CgtPromocion promocion, HttpServletResponse response,HttpServletRequest request){
		UserSession user = getUsuarioFirmado(request);
		CgcCatOrigen cgcCatOrigen = (CgcCatOrigen)    catalogoServiceBean.consultaLibrePorClave(0L, "from CgcCatOrigen o where o.idOrigen = " + promocion.getCgcCatOrigen().getIdOrigen()).get(0);
		CgcCatTipo cgcCatTipo = (CgcCatTipo) catalogoServiceBean.consultaLibrePorClave(0L, "from CgcCatTipo t where t.idTipo = " + promocion.getCgcCatTipo().getIdTipo()).get(0);
		CgtCatCriterioSeleccion cgtCatCriterioSeleccion = (CgtCatCriterioSeleccion)catalogoServiceBean.consultaLibrePorClave(0L, "from CgtCatCriterioSeleccion c where c.idCriterioseleccion = " + promocion.getCgtCatCriterioSeleccion().getIdCriterioseleccion()).get(0);
		promocion.setFecFechareg(new Date());
		SacSubdelegacion subdelegacion = (SacSubdelegacion)catalogoServiceBean.consultaLibrePorClave(0L, "from SacSubdelegacion s where s.cvePk = " + user.getIdSubDelegacion()).get(0);
		/*	CrtNroFolio folio = new CrtNroFolio();
			CrcTipoCorr tipoCor = new CrcTipoCorr();
			//PROMOCION EX-ORDINARIO
			if(cgcCatTipo.getIdTipo() == 5)
				tipoCor.setCveTipocorr(6);
			//PROMOCION EX-CONSTRUCCION
			else if(cgcCatTipo.getIdTipo() == 6)
				tipoCor.setCveTipocorr(5);
			//PROMOCION SATIC A
			else if(cgcCatTipo.getIdTipo() == 7)
				tipoCor.setCveTipocorr(3);
			//PROMOCION SATIC B
			else if(cgcCatTipo.getIdTipo() == 8)
				tipoCor.setCveTipocorr(4);
			//CORRECCION CCE
			else if(cgcCatTipo.getIdTipo() == 1 )
				tipoCor.setCveTipocorr(12);
			//CORRECCION CCI
			else if(cgcCatTipo.getIdTipo() == 2 )
				tipoCor.setCveTipocorr(10);
			//CORRECCION CE
			else if(cgcCatTipo.getIdTipo() == 3)
				tipoCor.setCveTipocorr(1);
			//CORRECCION CI
			else if(cgcCatTipo.getIdTipo() == 4)
				tipoCor.setCveTipocorr(2);
			folio.setCrcTipoCorr(tipoCor);
			*/
			//ArrayList folios = obtenerFolio(folio,promocion.getOpe(), response, request);
			//folio = (CrtNroFolio) folios.get(0);
			//Calendar calendar = Calendar.getInstance();
			//calendar.setTime(promocion.getOpe());
			//int anio = calendar.get(Calendar.YEAR);
			//promocion.setFolio(promocion.getFolio()+anio+"/"+ llenaCeros(folio.getNumNumero().toString()));
			
			
		
		if(promocion.getIdCodigoobra() != null && promocion.getIdCodigoobra().intValue()==-1){
			promocion.setIdCodigoobra(null);
		}
		promocion.setSacSubdelegacion(subdelegacion);
		promocion.setCveUsuario(user.getNomUsuarioSistema());
		promocion.setCgcCatOrigen(cgcCatOrigen);
		promocion.setCgcCatTipo(cgcCatTipo);
		promocion.setCgtCatCriterioSeleccion(cgtCatCriterioSeleccion);
		// se agrega para el seguimiento  EDJ 10/04/2012
		int digVer = generaDigitoVerificador(promocion.getCvePatron()); 
		String modalidad = promocion.getCvePatron().substring(8, 10).trim();
		promocion.setCveModalidad(new BigDecimal(modalidad));
		promocion.setCvePatron(promocion.getCvePatron().substring(0,8).trim().toUpperCase());
		promocion.setDv(new BigDecimal(digVer));
		if(promocion!=null && promocion.getFolio()!=null && promocion.getFolio().length()>15)
			promocion =	 (CgtPromocion) catalogoServiceBean.agregar(promocion);
		return promocion;
	}	
	
	@RequestMapping(value="/llenaCeros", method=RequestMethod.POST)
	public @ResponseBody String llenaCeros(@RequestBody String numFolio){
		String numero = numFolio;
		while(numero.length()<4){
			numero = "0"+numero;
		}
		return numero;
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
	
	@RequestMapping(value="/consultaOrigen", method=RequestMethod.POST)
	public @ResponseBody ArrayList consultaOrigen(@RequestBody Long idTipo, HttpServletResponse response,HttpServletRequest request){
		UserSession user = getUsuarioFirmado(request);
		ArrayList listaOrigenes = (ArrayList) catalogoServiceBean.consultaLibrePorClave(0L, "from CgcCatTipoOrigen to where to.cgcCatTipo.idTipo = " +idTipo);
		return listaOrigenes;
	}	
	
	@RequestMapping(value="/consultaMotivoCancelacion", method=RequestMethod.POST)
	public @ResponseBody ArrayList consultaMotivoCancelacion(@RequestBody Long idTipo, HttpServletResponse response,HttpServletRequest request){
		UserSession user = getUsuarioFirmado(request);
		ArrayList listaMotivos = (ArrayList) catalogoServiceBean.consultaLibrePorClave(0L, "from CgcCatMotivoCancelacion");
		return listaMotivos;
	}
	
	
	@RequestMapping(value="/consultaTiposObras", method=RequestMethod.POST)
	public @ResponseBody ArrayList consultaTiposObras(@RequestBody Long idTipo, HttpServletResponse response,HttpServletRequest request){
		UserSession user = getUsuarioFirmado(request);
		int tipoObra = 0;
		if(idTipo == 61 ||idTipo == 63 || idTipo == 65 || idTipo == 84|| idTipo == 87||idTipo == 88 || idTipo == 90 ||idTipo == 92 ||idTipo == 94 ||idTipo == 95){
			tipoObra = 1;
		}else if(idTipo == 62 || idTipo == 64 || idTipo == 66 || idTipo == 89 || idTipo == 91 || idTipo == 93 ){
			tipoObra = 2;
		}else{
			tipoObra = 1;
		}
		ArrayList listaMotivos = (ArrayList) catalogoServiceBean.consultaLibrePorClave(0L, "from CgcCatTipoObra o where o.id.idTipoobra="+tipoObra);
		return listaMotivos;
	}
	
	
	
	@RequestMapping(value="/consultaCriterios", method=RequestMethod.POST)
	public @ResponseBody ArrayList consultaCriterios(@RequestBody ArrayList list, HttpServletResponse response,HttpServletRequest request){
		UserSession user = getUsuarioFirmado(request);
		String idTipo = (String)list.get(0);
		String idOrigen = (String)list.get(1);
		ArrayList listaOrigenes = (ArrayList) catalogoServiceBean.consultaLibrePorClave(0L, "from CgtCatCriterioSeleccion cs where cs.cgcCatOrigen.idOrigen = " +idOrigen + " and cs.cgcCatTipo.idTipo = "  + idTipo );
		return listaOrigenes;
	}	
	
	
	@RequestMapping(value="/guardaAnexoPagos", method=RequestMethod.POST)
	public @ResponseBody CgtAnexoPago guardaAnexoPagos(@RequestBody CgtAnexoPago anexoPago,  HttpServletResponse response,HttpServletRequest request){
		UserSession user = getUsuarioFirmado(request);
		if(anexoPago.getCopperiodo().intValue() == 0){
			anexoPago.setCopperiodo(null);
		}
		if(anexoPago.getRcvperiodo().intValue() == 0){
			anexoPago.setRcvperiodo(null);
		}
		anexoPago.setFecFechareg(new Date());
		anexoPago.setCveUsuario(user.getNomUsuarioSistema());
		int digVer = generaDigitoVerificador(anexoPago.getCvePatron().trim().toUpperCase());
		String modalidad = anexoPago.getCvePatron().trim().toUpperCase().substring(8, 10);
		anexoPago.setCveModalidad(new BigDecimal(modalidad));
		anexoPago.setCvePatron(anexoPago.getCvePatron().substring(0,8).trim().toUpperCase());
		anexoPago =	 (CgtAnexoPago) catalogoServiceBean.agregar(anexoPago);
		
		
		
		return anexoPago;
	}	
	
	@RequestMapping(value="/eliminaAnexoPagos", method=RequestMethod.POST)
	public @ResponseBody CgtAnexoPago eliminaAnexoPagos(@RequestBody CgtAnexoPago anexoPago){
		
		catalogoServiceBean.eliminar((CgtAnexoPago)catalogoServiceBean.consultaPorClave(anexoPago));
		return anexoPago;
	}
	
	
	
	@RequestMapping(value="/actualizaPromocion", method=RequestMethod.POST)
	public @ResponseBody CgtPromocion actualizaPromocion(@RequestBody CgtPromocion promocion){
		if(promocion!=null && promocion.getFolio()!=null && promocion.getFolio().length()>15)
			promocion =	 (CgtPromocion) catalogoServiceBean.actualizar(promocion);
		return promocion;
	}	
	
	
	
	private Map<String, String> validationMessages(Set<ConstraintViolation<Clase>> failures) {
		Map<String, String> failureMessages = new HashMap<String, String>();
		for (ConstraintViolation<Clase> failure : failures) {
			failureMessages.put(failure.getPropertyPath().toString(), failure.getMessage());
		}
		return failureMessages;
	}

	@RequestMapping(value="/armaPeriodos", method=RequestMethod.POST)
	 private @ResponseBody ArrayList armaPeriodos(@RequestBody ArrayList listaFechas) {
			String sPeriodoInicial = (String) listaFechas.get(0);
			StringTokenizer token = new StringTokenizer(sPeriodoInicial, "/");
			String sDiaI = token.nextToken();
			String sMesI =  token.nextToken();
			String sAnioI = token.nextToken();
			sPeriodoInicial = sAnioI + sMesI;
			String sPeriodoFinal = (String) listaFechas.get(1);
			token = new StringTokenizer(sPeriodoFinal, "/");
			String sDiaF = token.nextToken();
			String sMesF =  token.nextToken();
			String sAnioF = token.nextToken();
			sPeriodoFinal = sAnioF + sMesF;
			
			
			int periodoInicial = Integer.parseInt(sPeriodoInicial);
			int periodoFinal = Integer.parseInt(sPeriodoFinal);
	        ArrayList resultado = new ArrayList();
	        int anioInicial = periodoInicial /100;
	        int mesInicial = periodoInicial % 100;
	        int anioFinal = periodoFinal / 100;
	        int mesFinal = periodoFinal % 100;
	        while ((anioInicial < anioFinal) || ((anioInicial == anioFinal) && (mesInicial <= mesFinal))) {
	            resultado.add(new Integer(anioInicial * 100
	                    + mesInicial));
	            if (mesInicial == 12) {
	                anioInicial++;
	                mesInicial = 1;
	            } else {
	                mesInicial++;
	            }
	        }
	        return resultado;
	    }
	
	/**
	 * MŽtodo par armar los bimestres para los pagos RCV
	 * @param listaFechas
	 * @return
	 */
	@RequestMapping(value="/armaBimestres", method=RequestMethod.POST)
	 public @ResponseBody ArrayList armaBimestres(@RequestBody ArrayList listaFechas) {
			String sPeriodoInicial = (String) listaFechas.get(0);
			StringTokenizer token = new StringTokenizer(sPeriodoInicial, "/");
			String sDiaI = token.nextToken();
			String sMesI =  token.nextToken();
			String sAnioI = token.nextToken();
			sPeriodoInicial = sAnioI + sMesI;
			
			String sPeriodoFinal = (String) listaFechas.get(1);
			token = new StringTokenizer(sPeriodoFinal, "/");
			String sDiaF = token.nextToken();
			String sMesF =  token.nextToken();
			String sAnioF = token.nextToken();
			sPeriodoFinal = sAnioF + sMesF;
			
			
			int periodoInicial = Integer.parseInt(sPeriodoInicial);
			int periodoFinal = Integer.parseInt(sPeriodoFinal);
	        ArrayList resultado = new ArrayList();
	        int anioInicial = periodoInicial /100;
	        int mesInicial = periodoInicial % 100;
	        int anioFinal = periodoFinal / 100;
	        int mesFinal = periodoFinal % 100;
	        while ((anioInicial < anioFinal)
	                || ((anioInicial == anioFinal) && (mesInicial <= mesFinal))) {
	            resultado.add(new Integer(anioInicial * 100
	                    + mesInicial));
	            if (mesInicial == 12) {
	                anioInicial++;
	                mesInicial = 1;
	            } else {
	                mesInicial++;
	            }
	        }
	        ArrayList listaBimestres =armarBimestresPosibles(resultado); 
	        if(listaBimestres!=null && listaBimestres.size()==0){
	        	return armaPeriodos(listaFechas);
	        }else
	        	return listaBimestres; 
	    }

	/**
	 * 
	 * @param lista
	 * @return
	 */
	private ArrayList armarBimestresPosibles(ArrayList lista) {
		ArrayList resultado = new ArrayList();
		Integer periodo = 0;
		for (int i = 0; i < lista.size(); i++) {
			periodo = (Integer) lista.get(i);
			if (periodo % 2 == 0) {
				resultado.add(periodo);
			}
		} //for
		if (periodo%2==1) {
			resultado.add(++periodo);
		}

		return resultado;
	}
	 
	 
	 
		@RequestMapping(value="/paginaPromociones", method=RequestMethod.POST )
	    public @ResponseBody DatosSalidaPaginador<CgtPromocion> pagina(@RequestBody PromocionWrapperDataTable aoData ,HttpServletResponse response,HttpServletRequest request) {
			logger.debug(".-.-controller public @ResponseBody DatosSalidaPaginador<CgtAnexoPago> pagina(@RequestBody ClaseWrapperDataTable aoData ) {");
			UserSession user = getUsuarioFirmado(request);
			DatosEntradaPaginador send = new DatosEntradaPaginador();
			send.parserArray(aoData.getAoData());
			send.setModelo(new CgtPromocion());
			DatosSalidaPaginador reply = this.catalogoServiceBean.paginaAllPromociones(send, ""+user.getIdSubDelegacion(), ""+user.getIdSubDelegacion());
			logger.debug(".-.-controller realizo consulta) {");
	        reply.setsEcho(send.getsEcho());
	        return reply;
	    }
	
		
		@RequestMapping(value="/obtenerDelegacion", method=RequestMethod.POST )
	    public @ResponseBody UserSession obtenerDelegacion(@RequestBody String param,HttpServletResponse response,HttpServletRequest request) {
			UserSession user = getUsuarioFirmado(request);
			return user;
	    }
		
		@RequestMapping(value="/consultaPromocion", method=RequestMethod.POST)
		public @ResponseBody CgtPromocion consultaPromocion(@RequestBody CgtPromocion promocion){
			ArrayList<CgtPromocion> listaPromocion = new ArrayList();
			CgtPromocion promo = null;
				 listaPromocion = (ArrayList)catalogoServiceBean.consultaLibrePorClave(0L, "from CgtPromocion p where p.folio = '" + promocion.getFolio() + "'") ;
				 if(listaPromocion!=null && listaPromocion.size() > 0){
					 promo = listaPromocion.get(0);
					 if (promocion.getUbicacion()==null || promocion.getUbicacion().indexOf("null")>=0 || promocion.getUbicacion().indexOf("NULL")>=0) {
						 promocion.setUbicacion("");
					 }
					 logger.debug("getFolio :: " + promocion.getFolio());
					 logger.debug("getDv :: " + promocion.getDv());
					 logger.debug("getIdCodigoobra :: " + promocion.getIdCodigoobra());
					 logger.debug("getOpe :: " + promocion.getOpe());
					 logger.debug("getUbicacion :: "+promocion.getUbicacion());
				 }
				
			return promo;
		}	
		@RequestMapping(value="/obtenerFechaServidor.do", method=RequestMethod.POST)
		public @ResponseBody String obtenerFechaServidor(HttpServletRequest request){
			return ConstantesBusiness.dateToStringFormat(new Date(), ConstantesBusiness.DD_MM_YYYY);
		}
		

		@RequestMapping(value="/consultaPago", method=RequestMethod.POST )
		public @ResponseBody CgtAnexoPago obtenerPago(@RequestBody Long param,HttpServletResponse response,HttpServletRequest request) {
			ArrayList<CgtAnexoPago> listaPago = new ArrayList<CgtAnexoPago>();
			CgtAnexoPago resp = null;
			listaPago = (ArrayList)catalogoServiceBean.consultaLibrePorClave(0L, "from CgtAnexoPago p where p.idPago = " + param) ;
			if(listaPago.size()>0){
				resp = listaPago.get(0);
			}
			return resp;
		}
		
		@RequestMapping(value="/verificaFolioExistente", method=RequestMethod.POST )
		public @ResponseBody String verificaFolioExistente(@RequestBody String folio) {
			ArrayList<CgtPromocion> listaPromocion = new ArrayList();
			CgtPromocion promocion = null;
			 listaPromocion = (ArrayList)catalogoServiceBean.consultaLibrePorClave(0L, "from CgtPromocion p where p.folio = '" + folio + "'") ;
			 if(listaPromocion!=null && listaPromocion.size() > 0){
				 promocion = listaPromocion.get(0);
				 
			 }
			
			 if(promocion!=null)
				 return promocion.getFolio();
			 else
				 return "";
			
		}
		
}



