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
import mx.gob.imss.ctirss.correccion.login.model.SegUsuarioFuncionario;
import mx.gob.imss.ctirss.correccion.model.CgcCatConceptoOmitido;
import mx.gob.imss.ctirss.correccion.model.CgcCatSituacionCO;
import mx.gob.imss.ctirss.correccion.model.CgcReglaNegocio;
import mx.gob.imss.ctirss.correccion.model.CgtAnexoConceptoOmitido;
import mx.gob.imss.ctirss.correccion.model.CgtAnexoPago;
import mx.gob.imss.ctirss.correccion.model.CgtAnexoRP;
import mx.gob.imss.ctirss.correccion.model.CgtCatCriterioSeleccion;
import mx.gob.imss.ctirss.correccion.model.CgtCorreccion;
import mx.gob.imss.ctirss.correccion.model.CgtPromocion;
import mx.gob.imss.ctirss.correccion.model.Clase;
import mx.gob.imss.ctirss.correccion.model.SatPatron;
import mx.gob.imss.ctirss.correccion.model.SatUbicacion;
import mx.gob.imss.ctirss.correccion.promocionCG.base.paginador.model.AnexoConceptosOmitidosWrapperDataTable;
import mx.gob.imss.ctirss.correccion.promocionCG.base.paginador.model.AnexoPagosWrapperDataTable;
import mx.gob.imss.ctirss.correccion.promocionCG.base.paginador.model.CorreccionWrapperDataTable;
import mx.gob.imss.ctirss.correccion.promocionCG.base.paginador.model.PromocionWrapperDataTable;
import mx.gob.imss.ctirss.correccion.promocionCG.base.paginador.model.RegistrosPatronalesWrapperDataTable;
import mx.gob.imss.ctirss.correccion.promocionCG.base.paginador.model.TrabajadoresWrapperDataTable;
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
@RequestMapping(value="/controlGestion/correccion")
public class CorreccionController extends AbstractController {

	@Autowired
	private ICatalogoService<AbstractModel> catalogoServiceBean;
	@Autowired
	private IPatronesService patronesService;
	
	
	/**
	 * Logger.
	 */
	private final static Logger logger = Logger.getLogger(CorreccionController.class);
	
	@RequestMapping(method=RequestMethod.GET)
	public String getCreateForm(Model model) {
		 return "controlGestion/correccion/correccionCGMain";
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
		ArrayList lista = (ArrayList) catalogoServiceBean.consultaLibrePorClave(0L, "from CgcReglaNegocio rn where rn.cgcCatTipo.cgcCatflujo.idFlujo="+"1"+" and rn.nombrecontrol='"+reglaNegocio.getNombrecontrol()+ "' and rn.cgcCatTipo.idTipo = " + reglaNegocio.getCgcCatTipo().getIdTipo());
		 logger.debug(reglaNegocio.getNivel());
		 if(lista!=null && lista.size()>0)
			 reglaNegocio = (CgcReglaNegocio) lista.get(0);
		}
		return reglaNegocio;
	}	
	
	@RequestMapping(value="/consultaCriterios", method=RequestMethod.POST)
	public @ResponseBody ArrayList consultaPorClave(@RequestBody CgtCatCriterioSeleccion seleccion){
		
		ArrayList lista = (ArrayList) catalogoServiceBean.consultaLibrePorClave(0L, "from CgtCatCriterioSeleccion cs where cs.idCriterioseleccion = " + seleccion.getIdCriterioseleccion() );
		return lista;
	}	
	
	@RequestMapping(value="/obtenerFechaServidor.do", method=RequestMethod.POST)
	public @ResponseBody String obtenerFechaServidor(HttpServletRequest request){
		return ConstantesBusiness.dateToStringFormat(new Date(), ConstantesBusiness.DD_MM_YYYY);
	}

	@RequestMapping(value="/paginar", method=RequestMethod.POST )
    public @ResponseBody DatosSalidaPaginador<CgtPromocion> paginar(@RequestBody PromocionWrapperDataTable aoData, HttpServletResponse response,HttpServletRequest request ) {
		logger.debug(".-.-controller public @ResponseBody DatosSalidaPaginador<CgtAnexoPago> pagina(@RequestBody ClaseWrapperDataTable aoData ) {");
		UserSession user = getUsuarioFirmado(request);
		DatosEntradaPaginador send = new DatosEntradaPaginador();
		send.parserArray(aoData.getAoData());
		send.setModelo(new CgtPromocion());
		DatosSalidaPaginador reply = this.catalogoServiceBean.paginaPromociones(send, user.getIdSubDelegacion());
		logger.debug(".-.-controller realizo consulta) {");
        reply.setsEcho(send.getsEcho());
        return reply;
    }

	@RequestMapping(value="/paginarCorrecciones", method=RequestMethod.POST )
    public @ResponseBody DatosSalidaPaginador<CgtCorreccion> paginaCorrecciones(@RequestBody CorreccionWrapperDataTable aoData, HttpServletResponse response,HttpServletRequest request ) {
		logger.debug(".-.-controller public @ResponseBody DatosSalidaPaginador<CgtAnexoPago> pagina(@RequestBody ClaseWrapperDataTable aoData ) {");
		UserSession user = getUsuarioFirmado(request);
		DatosEntradaPaginador send = new DatosEntradaPaginador();
		send.parserArray(aoData.getAoData()); 
		send.setModelo(new CgtCorreccion());
		DatosSalidaPaginador reply = this.catalogoServiceBean.paginaCorrecciones(send, user.getIdSubDelegacion());
		logger.debug(".-.-controller realizo consulta) {");
        reply.setsEcho(send.getsEcho());
        return reply;
    }
	
	@RequestMapping(value="/obtenerDelegacion", method=RequestMethod.POST )
    public @ResponseBody UserSession obtenerDelegacion(@RequestBody String param,HttpServletResponse response,HttpServletRequest request) {
		UserSession user = getUsuarioFirmado(request);
		return user;
    }
	
	@RequestMapping(value="/paginarAnexoPagos", method=RequestMethod.POST )
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
	
	

	@RequestMapping(value="/paginarAnexoPagosA", method=RequestMethod.POST )
    public @ResponseBody DatosSalidaPaginador<CgtAnexoPago> paginaA(@RequestBody AnexoPagosWrapperDataTable aoData ) {
		logger.debug(".-.-controller public @ResponseBody DatosSalidaPaginador<CgtAnexoPago> pagina(@RequestBody ClaseWrapperDataTable aoData ) {");
		DatosEntradaPaginador send = new DatosEntradaPaginador();
		
		send.parserArray(aoData.getAoData());
		send.setModelo(new CgtAnexoPago());
		
		DatosSalidaPaginador reply = this.catalogoServiceBean.paginaAnexoPagosA(send);
		logger.debug(".-.-controller realizo consulta) {");
        reply.setsEcho(send.getsEcho());
        
        return reply;
    }
	
	

	@RequestMapping(value="/paginarAnexoPagosR", method=RequestMethod.POST )
    public @ResponseBody DatosSalidaPaginador<CgtAnexoPago> paginaR(@RequestBody AnexoPagosWrapperDataTable aoData ) {
		logger.debug(".-.-controller public @ResponseBody DatosSalidaPaginador<CgtAnexoPago> pagina(@RequestBody ClaseWrapperDataTable aoData ) {");
		DatosEntradaPaginador send = new DatosEntradaPaginador();
		
		send.parserArray(aoData.getAoData());
		send.setModelo(new CgtAnexoPago());
		
		DatosSalidaPaginador reply = this.catalogoServiceBean.paginaAnexoPagosR(send);
		logger.debug(".-.-controller realizo consulta) {");
        reply.setsEcho(send.getsEcho());
        
        return reply;
    }
	@RequestMapping(value="/paginarPatrones", method=RequestMethod.POST )
    public @ResponseBody DatosSalidaPaginador<CgtAnexoRP> pagina(@RequestBody RegistrosPatronalesWrapperDataTable aoData ) {
		logger.debug(".-.-controller public @ResponseBody DatosSalidaPaginador<CgtAnexoPago> pagina(@RequestBody ClaseWrapperDataTable aoData ) {");
		DatosEntradaPaginador send = new DatosEntradaPaginador();
		send.parserArray(aoData.getAoData());
		send.setModelo(new CgtAnexoRP());
		DatosSalidaPaginador reply = this.catalogoServiceBean.paginaAnexoPatrones(send);
		logger.debug(".-.-controller realizo consulta) {");
        reply.setsEcho(send.getsEcho());
        return reply;
    }
	
	@RequestMapping(value="/paginarTrabajadores", method=RequestMethod.POST )
    public @ResponseBody DatosSalidaPaginador<CgtAnexoRP> paginaTrabajadores(@RequestBody TrabajadoresWrapperDataTable aoData ) {
		logger.debug(".-.-controller public @ResponseBody DatosSalidaPaginador<CgtAnexoPago> pagina(@RequestBody ClaseWrapperDataTable aoData ) {");
		DatosEntradaPaginador send = new DatosEntradaPaginador();
		send.parserArray(aoData.getAoData());
		send.setModelo(new CgtAnexoRP());
		DatosSalidaPaginador reply = this.catalogoServiceBean.paginaAnexoPatrones(send);
		logger.debug(".-.-controller realizo consulta) {");
        reply.setsEcho(send.getsEcho());
        return reply;
    }
	
	@RequestMapping(value="/paginarConceptos", method=RequestMethod.POST )
    public @ResponseBody DatosSalidaPaginador<CgtAnexoRP> paginarConceptos(@RequestBody AnexoConceptosOmitidosWrapperDataTable aoData ) {
		logger.debug(".-.-controller public @ResponseBody DatosSalidaPaginador<CgtAnexoPago> pagina(@RequestBody ClaseWrapperDataTable aoData ) {");
		DatosEntradaPaginador send = new DatosEntradaPaginador();
		send.parserArray(aoData.getAoData());
		send.setModelo(new CgtAnexoConceptoOmitido());
		DatosSalidaPaginador reply = this.catalogoServiceBean.paginaAnexoConceptos(send);
		logger.debug(".-.-controller realizo consulta) {");
        reply.setsEcho(send.getsEcho());
        return reply;
    }
	
	@RequestMapping(value="/paginarConceptosC", method=RequestMethod.POST )
    public @ResponseBody DatosSalidaPaginador<CgtAnexoRP> paginarConceptosC(@RequestBody AnexoConceptosOmitidosWrapperDataTable aoData ) {
		logger.debug(".-.-controller public @ResponseBody DatosSalidaPaginador<CgtAnexoPago> pagina(@RequestBody ClaseWrapperDataTable aoData ) {");
		DatosEntradaPaginador send = new DatosEntradaPaginador();
		send.parserArray(aoData.getAoData());
		send.setModelo(new CgtAnexoConceptoOmitido());
		DatosSalidaPaginador reply = this.catalogoServiceBean.paginaAnexoConceptosC(send);
		logger.debug(".-.-controller realizo consulta) {");
        reply.setsEcho(send.getsEcho());
        return reply;
    }
	
	@RequestMapping(value="/paginarConceptosPAI", method=RequestMethod.POST )
    public @ResponseBody DatosSalidaPaginador<CgtAnexoRP> paginarConceptosPAI(@RequestBody AnexoConceptosOmitidosWrapperDataTable aoData ) {
		logger.debug(".-.-controller public @ResponseBody DatosSalidaPaginador<CgtAnexoPago> pagina(@RequestBody ClaseWrapperDataTable aoData ) {");
		DatosEntradaPaginador send = new DatosEntradaPaginador();
		send.parserArray(aoData.getAoData());
		send.setModelo(new CgtAnexoConceptoOmitido());
		DatosSalidaPaginador reply = this.catalogoServiceBean.paginaAnexoConceptosPAI(send);
		logger.debug(".-.-controller realizo consulta) {");
        reply.setsEcho(send.getsEcho());
        return reply;
    }
	
	
	@RequestMapping(value="/paginarConceptosAPAI", method=RequestMethod.POST )
    public @ResponseBody DatosSalidaPaginador<CgtAnexoRP> paginarConceptosAPAI(@RequestBody AnexoConceptosOmitidosWrapperDataTable aoData ) {
		logger.debug(".-.-controller public @ResponseBody DatosSalidaPaginador<CgtAnexoPago> pagina(@RequestBody ClaseWrapperDataTable aoData ) {");
		DatosEntradaPaginador send = new DatosEntradaPaginador();
		send.parserArray(aoData.getAoData());
		send.setModelo(new CgtAnexoConceptoOmitido());
		DatosSalidaPaginador reply = this.catalogoServiceBean.paginaAnexoConceptosAPAI(send);
		logger.debug(".-.-controller realizo consulta) {");
        reply.setsEcho(send.getsEcho());
        return reply;
    }
	
	@RequestMapping(value="/conteoPatrones", method=RequestMethod.POST)
	public @ResponseBody int conteoPatrones(@RequestBody CgtAnexoRP anexoRP){
		ArrayList lista = (ArrayList) catalogoServiceBean.consultaLibrePorClave(0L, "from CgtAnexoRP rp where rp.id.folio = '"+anexoRP.getId().getFolio()+"'");
		return lista.size();
	}
	
	@RequestMapping(value="/consultaAnexoPatrones", method=RequestMethod.POST)
	public @ResponseBody ArrayList consultaAnexoPatrones(@RequestBody CgtAnexoRP anexoRP){
		ArrayList lista = (ArrayList) catalogoServiceBean.consultaLibrePorClave(0L, "from CgtAnexoRP rp where rp.id.folio = '"+anexoRP.getId().getFolio()+"'");
		return lista;
	}
	
	@RequestMapping(value="/consultaConceptos", method=RequestMethod.POST)
	public @ResponseBody ArrayList consultaConceptos(@RequestBody String uno){
		ArrayList lista = (ArrayList) catalogoServiceBean.consultaLibrePorClave(0L, "from CgcCatConceptoOmitido");
		return lista;
	}
	
	@RequestMapping(value="/consultaConceptosFolio", method=RequestMethod.POST)
	public @ResponseBody ArrayList consultaConceptos(@RequestBody CgtAnexoConceptoOmitido concepto){
		@SuppressWarnings("rawtypes")
		ArrayList lista = null;
		if(concepto.getId().getCgcCatSituacionCO().getIdSituacionco()==3){
			lista = (ArrayList) catalogoServiceBean.consultaLibrePorClave(0L, "from CgtAnexoConceptoOmitido co where co.id.folio = '" + concepto.getId().getFolio() + "' and co.id.cgcCatConceptoOmitido.idConceptoomitido = " + 
					 concepto.getId().getCgcCatConceptoOmitido().idConceptoomitido + " and co.id.cgcCatSituacionCO.idSituacionco in (1,2,3)" + " and co.id.idProceso = "+ concepto.getId().getIdProceso());
		}else if(concepto.getId().getCgcCatSituacionCO().getIdSituacionco()==2){
			lista = (ArrayList) catalogoServiceBean.consultaLibrePorClave(0L, "from CgtAnexoConceptoOmitido co where co.id.folio = '" + concepto.getId().getFolio() + "' and co.id.cgcCatConceptoOmitido.idConceptoomitido = " + 
					 concepto.getId().getCgcCatConceptoOmitido().idConceptoomitido + " and co.id.cgcCatSituacionCO.idSituacionco in (1,2,3) and co.id.idProceso = " + concepto.getId().getIdProceso()   );
			
		}else{
			lista = (ArrayList) catalogoServiceBean.consultaLibrePorClave(0L, "from CgtAnexoConceptoOmitido co where co.id.folio = '" + concepto.getId().getFolio() + "' and co.id.cgcCatConceptoOmitido.idConceptoomitido = " + 
					 concepto.getId().getCgcCatConceptoOmitido().idConceptoomitido + " and co.id.cgcCatSituacionCO.idSituacionco in (1,2,3) and co.id.idProceso = " + concepto.getId().getIdProceso()   );
			
		}
		if (lista!=null && lista.size()==0)
			return null;
		return lista;
	}
	

	@RequestMapping(value="/consultaConceptosProceso", method=RequestMethod.POST)
	public @ResponseBody ArrayList consultaConceptosProceso(@RequestBody CgtAnexoConceptoOmitido concepto){
		ArrayList lista = (ArrayList) catalogoServiceBean.consultaLibrePorClave(0L, "from CgtAnexoConceptoOmitido co where co.id.folio = '" + concepto.getId().getFolio() + "' and co.id.idProceso = " + concepto.getId().getIdProceso());
		return lista;
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
	@RequestMapping(value="/consultaPromocion", method=RequestMethod.POST)
	public @ResponseBody ArrayList<CgtPromocion> consultaPromocion(@RequestBody CgtPromocion promocion){
		
		ArrayList lista = (ArrayList) catalogoServiceBean.consultaLibrePorClave(0L, "from CgtPromocion p where p.folio = '"+ promocion.getFolio()+"'");
		return lista;
	}	
	
	
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value="/finalizaPromocion", method=RequestMethod.POST)
	public @ResponseBody CgtCorreccion finalizaPromocion(@RequestBody ArrayList promocion,HttpServletResponse response,HttpServletRequest request){
		UserSession user = getUsuarioFirmado(request);
		CgtPromocion promo = (CgtPromocion) ((ArrayList) catalogoServiceBean.consultaLibrePorClave(0L, "from CgtPromocion p where p.folio = '"+ promocion.get(0)+"'")).get(0);
		promo.setCveUsuario(user.getNomUsuarioSistema());
		promo.setFecFechareg(new Date());
		
		if (promo!=null){
			promo.setFoliocorreccion(""+promocion.get(1));
			catalogoServiceBean.actualizar(promo);
			
		}
	 
		return null;
	}	
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value="/consultaPatronesAP", method=RequestMethod.POST)
	public @ResponseBody ArrayList<CgtAnexoRP> consultaPatronesAP(@RequestBody CgtAnexoPago anexoPago){
		ArrayList listaRP = (ArrayList) catalogoServiceBean.consultaLibrePorClave(0L, "from CgtAnexoRP a where a.id.folio = '"+ anexoPago.getFolio()+"'");
		return listaRP;
	}	
	@RequestMapping(value="/consultaAnexoPagos", method=RequestMethod.POST)
	public @ResponseBody ArrayList<CgtAnexoPago> consultaAnexoPagos(@RequestBody CgtAnexoPago anexoPago){
		
		ArrayList lista = (ArrayList) catalogoServiceBean.consultaLibrePorClave(0L, "from CgtAnexoPago ap where ap.folio = '"+ anexoPago.getFolio()+"'");
		return lista;
	}	
	
	@RequestMapping(value="/consultaAnexoPagosAR", method=RequestMethod.POST)
	public @ResponseBody ArrayList<CgtAnexoPago> consultaAnexoPagosR(@RequestBody CgtAnexoPago anexoPago){
		
		ArrayList lista = (ArrayList) catalogoServiceBean.consultaLibrePorClave(0L, "from CgtAnexoPago ap where ap.folio = '"+ anexoPago.getFolio()+"' and ap.idProceso = " + anexoPago.getIdProceso() );
		return lista;
	}	
	
	@RequestMapping(value="/consultaReglaNivel", method=RequestMethod.POST)
	public @ResponseBody ArrayList<CgcReglaNegocio> consultaPorNivel(@RequestBody CgcReglaNegocio reglaNegocio){
		logger.debug("CVE ID A BUSCAR:"+reglaNegocio.getNombrecontrol());
		ArrayList<CgcReglaNegocio> lista = new ArrayList<CgcReglaNegocio>();
		if(reglaNegocio.getNivel()!=null && reglaNegocio.getNivel().intValue()>0){
		 lista = (ArrayList) catalogoServiceBean.consultaLibrePorClave(0L, "from CgcReglaNegocio rn where rn.cgcCatTipo.cgcCatflujo.idFlujo=1 and rn.nivel="+reglaNegocio.getNivel().intValue() +" and rn.nombrecontrol<>'"+reglaNegocio.getNombrecontrol()+ "' and rn.cgcCatTipo.idTipo = " + reglaNegocio.getCgcCatTipo().getIdTipo());
		 logger.debug(reglaNegocio.getNivel());
		 //reglaNegocio = (CgcReglaNegocio) lista.get(0);
		}
		return lista;
	}	
	
	@RequestMapping(value="/obtenerFolio", method=RequestMethod.POST)
	public @ResponseBody ArrayList<CrtNroFolio> obtenerFolio(@RequestBody CrtNroFolio nroFolio, HttpServletResponse response,HttpServletRequest request ){
		
		ArrayList<CrtNroFolio> lista = new ArrayList<CrtNroFolio>();
		UserSession user = getUsuarioFirmado(request);
		Calendar calendar = Calendar.getInstance();
		int anio = calendar.get(Calendar.YEAR);
		 lista = (ArrayList) catalogoServiceBean.consultaLibrePorClave(0L, "from CrtNroFolio nf where nf.numAnio = " + anio + " and nf.cveDelegacion = " + user.getCveCodigoDelegacion() + " and nf.cveSubdelegacion = " + user.getCveCodigoSubDelegacion() + " and nf.crcTipoCorr.cveTipocorr =" + nroFolio.getCrcTipoCorr().getCveTipocorr()) ;
		 if(lista!=null && lista.size() > 0){
			 CrtNroFolio folioUpdate = (CrtNroFolio) lista.get(0);
			 folioUpdate.setNumNumero(new BigDecimal(folioUpdate.getNumNumero().longValue() + 1));
			 folioUpdate.setNumAnio(new BigDecimal(anio));
			 this.catalogoServiceBean.actualizar(folioUpdate);
		 }else{
			 CrtNroFolio folioNuevo = new CrtNroFolio();
			 folioNuevo.setCrcTipoCorr(nroFolio.getCrcTipoCorr());
			 folioNuevo.setCveDelegacion(new BigDecimal(user.getCveCodigoDelegacion()));
			 folioNuevo.setCveSubdelegacion(new BigDecimal(user.getCveCodigoSubDelegacion()));
			 folioNuevo.setNumAnio(new BigDecimal(anio));
			 folioNuevo.setNumNumero(new BigDecimal(1));
			 this.catalogoServiceBean.agregar(folioNuevo);
			 lista.add(folioNuevo);
		 }
		return lista;
	}	
	
	
	@RequestMapping(value="/validaRegPat", method=RequestMethod.POST)
	public @ResponseBody
	SatPatron validaRegistroPatronal(@RequestBody SatPatron patron) {
		SatPatron respuesta = this.patronesService.validaRegistroPatronalWS(
				patron.getRegistroPatronal().substring(0, 10).toUpperCase(), true);
		if (respuesta == null){
			respuesta = this.patronesService.validaRegistroPatronalWS(
					patron.getRegistroPatronal().substring(0, 10).toUpperCase(), true);
		}
			

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
	
	/**
	 * 
	 * @param correccion
	 * @param response
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/guardaCorreccion", method=RequestMethod.POST)
	public @ResponseBody
	CgtCorreccion guardaCorreccion(@RequestBody CgtCorreccion correccion,
			HttpServletResponse response, HttpServletRequest request) {
		logger.info("Guardando correccion");
		logger.debug("correccion.getIdMotivoRechazo() :: " + correccion.getIdMotivoRechazo());
		UserSession user = getUsuarioFirmado(request);
		correccion.setFecFechareg(new Date());
		SacSubdelegacion subdelegacion = (SacSubdelegacion) catalogoServiceBean.consultaLibrePorClave(0L,"from SacSubdelegacion s where s.cvePk = "	+ user.getIdSubDelegacion()).get(0);
		correccion.setSacSubdelegacion(subdelegacion);
		correccion.setCveUsuario(user.getNomUsuarioSistema());
		/*	CrtNroFolio folio = new CrtNroFolio();
			CrcTipoCorr tipoCor = new CrcTipoCorr();
			//PROMOCION EX-ORDINARIO
			if (correccion.getCgcCatTipo().idTipo == 5)
				tipoCor.setCveTipocorr(6);
			//PROMOCION EX-CONSTRUCCION
			else if (correccion.getCgcCatTipo().getIdTipo() == 6)
				tipoCor.setCveTipocorr(5);
			//PROMOCION SATIC A
			else if (correccion.getCgcCatTipo().getIdTipo() == 7)
				tipoCor.setCveTipocorr(3);
			//PROMOCION SATIC B
			else if (correccion.getCgcCatTipo().getIdTipo() == 8)
				tipoCor.setCveTipocorr(4);
			//CORRECCION CCE
			else if (correccion.getCgcCatTipo().getIdTipo() == 1)
				tipoCor.setCveTipocorr(12);
			//CORRECCION CCI
			else if (correccion.getCgcCatTipo().getIdTipo() == 2)
				tipoCor.setCveTipocorr(10);
			//CORRECCION CE
			else if (correccion.getCgcCatTipo().getIdTipo() == 3)
				tipoCor.setCveTipocorr(1);
			//CORRECCION CI
			else if (correccion.getCgcCatTipo().getIdTipo() == 4)
				tipoCor.setCveTipocorr(2);

			folio.setCrcTipoCorr(tipoCor);*/
		//	ArrayList folios = obtenerFolio(folio, response, request);
		//	folio = (CrtNroFolio) folios.get(0);
		//	correccion.setFolio(correccion.getFolio() + llenaCeros(folio.getNumNumero().toString()));
			int digVer = generaDigitoVerificador(correccion.getCvePatron().trim().toUpperCase()); 
			if(correccion.getCveModalidad()==null || correccion.getCveModalidad().intValue() == 0 ){
				String modalidad = correccion.getCvePatron().trim().substring(8, 10);
				if(modalidad!=null && modalidad.length()==2){
					correccion.setCveModalidad(new BigDecimal(modalidad));
				}
				
			}
			
			correccion.setCvePatron(correccion.getCvePatron().substring(0,8).toUpperCase().trim());
			correccion.setDv(new BigDecimal(digVer));
		
		if (correccion.getIdMotivoRechazo()!=null && correccion.getIdMotivoRechazo()<=0) {
			correccion.setIdMotivoRechazo(null);
			if (correccion!=null && correccion.getFolio()!=null && correccion.getFolio().length()>15){
				correccion = (CgtCorreccion) catalogoServiceBean.agregar(correccion);
			}
		}		
		return correccion;
	}	
	
	public String llenaCeros(String numFolio){
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
	
	/**
	 * 
	 * @param correccion
	 * @param response
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/guardaCorreccionFromPromo", method=RequestMethod.POST)
	public @ResponseBody CgtCorreccion guardaCorreccionPromo(@RequestBody CgtCorreccion correccion, HttpServletResponse response,HttpServletRequest request){
		UserSession user = getUsuarioFirmado(request);
		correccion.setFecFechareg(new Date());
		SacSubdelegacion subdelegacion = (SacSubdelegacion)catalogoServiceBean.consultaLibrePorClave(0L, "from SacSubdelegacion s where s.cvePk = " + user.getIdSubDelegacion()).get(0);
		correccion.setSacSubdelegacion(subdelegacion);
		correccion.setCveUsuario(user.getNomUsuarioSistema());
	/*	CrtNroFolio folio = new CrtNroFolio();
			CrcTipoCorr tipoCor = new CrcTipoCorr();
			if(correccion.getCgcCatTipo().idTipo == 5)
			tipoCor.setCveTipocorr(6);
		else if(correccion.getCgcCatTipo().getIdTipo() == 6)
			tipoCor.setCveTipocorr(5);
		else if(correccion.getCgcCatTipo().getIdTipo() == 7)
			tipoCor.setCveTipocorr(3);
		else if(correccion.getCgcCatTipo().getIdTipo() == 8)
			tipoCor.setCveTipocorr(4);
		else if(correccion.getCgcCatTipo().getIdTipo() == 1 )
			tipoCor.setCveTipocorr(1);
		else if(correccion.getCgcCatTipo().getIdTipo() == 2 )
			tipoCor.setCveTipocorr(10);
		else if(correccion.getCgcCatTipo().getIdTipo() == 3)
			tipoCor.setCveTipocorr(2);
		else if(correccion.getCgcCatTipo().getIdTipo() == 4)
			tipoCor.setCveTipocorr(11);
			*/
			
	//		folio.setCrcTipoCorr(tipoCor);
			//ArrayList folios = obtenerFolio(folio, response, request);
			//folio = (CrtNroFolio) folios.get(0);
			//correccion.setFolio(correccion.getFolio()+folio.getNumNumero());
		
			int digVer = generaDigitoVerificador(correccion.getCvePatron().trim().toUpperCase()); 
			if(correccion.getCveModalidad()==null || correccion.getCveModalidad().intValue() == 0 ){
				String modalidad = correccion.getCvePatron().trim().substring(8, 10);
				if(modalidad!=null && modalidad.length()==2){
					correccion.setCveModalidad(new BigDecimal(modalidad));
				}
				
			}
			
			correccion.setCvePatron(correccion.getCvePatron().trim().substring(0,8).toUpperCase());
			correccion.setDv(new BigDecimal(digVer));
			if (correccion!=null && correccion.getFolio()!=null && correccion.getFolio().length()>15){
				correccion =	 (CgtCorreccion) catalogoServiceBean.agregar(correccion);
			}
		return correccion;
	}	
	
	@RequestMapping(value="/guardaConceptoOmitido", method=RequestMethod.POST)
	public @ResponseBody CgtAnexoConceptoOmitido guardaConceptoOmitido(@RequestBody CgtAnexoConceptoOmitido conceptoOmitido, HttpServletResponse response,HttpServletRequest request){
		UserSession user = getUsuarioFirmado(request);
		CgcCatConceptoOmitido concepto = (CgcCatConceptoOmitido)catalogoServiceBean.consultaLibrePorClave(0L, "from CgcCatConceptoOmitido co where co.idConceptoomitido = "+ conceptoOmitido.getId().getCgcCatConceptoOmitido().getIdConceptoomitido()+"").get(0);
		CgcCatSituacionCO situacion = (CgcCatSituacionCO)catalogoServiceBean.consultaLibrePorClave(0L, "from CgcCatSituacionCO sc where sc.idSituacionco = "+ conceptoOmitido.getId().getCgcCatSituacionCO().getIdSituacionco()+"").get(0) ;
		conceptoOmitido.id.setCgcCatConceptoOmitido(concepto);
		conceptoOmitido.id.setCgcCatSituacionCO(situacion);
		conceptoOmitido.setFecFechareg(new Date());
		conceptoOmitido.setCveUsuario(user.getNomUsuarioSistema());
		conceptoOmitido =	 (CgtAnexoConceptoOmitido) catalogoServiceBean.agregar(conceptoOmitido);
		return conceptoOmitido;
	}
	
	/**
	 * 
	 * @param anexoRP
	 * @param response
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/guardaRP", method=RequestMethod.POST)
	public @ResponseBody
	CgtAnexoRP guardaRP(@RequestBody CgtAnexoRP anexoRP,
			HttpServletResponse response, HttpServletRequest request) {
		if (anexoRP.getId().getRp() != null
				&& !anexoRP.getId().getRp().isEmpty()) {
			//trim
			anexoRP.getId().setRp(anexoRP.getId().getRp().trim().toUpperCase());
			
			int digVer = generaDigitoVerificador(anexoRP.getId().getRp()); 
			anexoRP.setDv(new BigDecimal(digVer));
			
			//buscamos para no repetir
//			List datosFromBD = catalogoServiceBean.consultaLibrePorClave(0L,
//					"from CgtAnexoRP rp where rp.id.folio = '"
//							+ anexoRP.getId().getFolio() + "' and rp.id.rp = '"
//							+ anexoRP.getId().getRp() + "'");
//			if (datosFromBD == null || datosFromBD.isEmpty()) {
				//insert
				UserSession user = getUsuarioFirmado(request);
				anexoRP.setCveUsuario(user.getNomUsuarioSistema());
				anexoRP.setFecFechareg(new Date());
				anexoRP = (CgtAnexoRP) catalogoServiceBean.agregar(anexoRP);
//			}
		}
		return anexoRP;
	}	
	
	
	@RequestMapping(value="/guardaAnexoPagos", method=RequestMethod.POST)
	public @ResponseBody CgtAnexoPago guardaAnexoPagos(@RequestBody CgtAnexoPago anexoPago, HttpServletResponse response,HttpServletRequest request){
		UserSession user = getUsuarioFirmado(request);
		anexoPago.setCveUsuario(user.getNomUsuarioSistema());
		anexoPago.setFecFechareg(new Date());
		
		if (anexoPago.getCopperiodo()!=null && anexoPago.getCopperiodo().intValue()<=0){
			anexoPago.setCopperiodo(null);
		}
		
		if (anexoPago.getRcvperiodo()!=null && anexoPago.getRcvperiodo().intValue()<=0){
			anexoPago.setRcvperiodo(null);
		}	
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
	
	
	@RequestMapping(value="/actualizaCorreccion", method=RequestMethod.POST)
	public @ResponseBody CgtCorreccion actualizaPromocion(@RequestBody CgtCorreccion correccion){
		correccion =	 (CgtCorreccion) catalogoServiceBean.actualizar(correccion);
		return correccion;
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
	
	@RequestMapping(value="/armaBimestres", method=RequestMethod.POST)
	 private @ResponseBody ArrayList armaBimestres(@RequestBody ArrayList listaFechas) {
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
	        if(mesFinal %2 != 0){
        		mesFinal++;
        	}
	        while ((anioInicial < anioFinal) || ((anioInicial == anioFinal) && (mesInicial <= mesFinal))) {
	            resultado.add(new Integer(anioInicial * 100 + mesInicial));
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
	 private ArrayList armarBimestresPosibles(ArrayList lista) {
	        ArrayList resultado = new ArrayList();
	        for(int i=0;i<lista.size();i ++){
	        	Integer periodo = (Integer)lista.get(i);
	        	if(periodo %2 == 0){
	        		resultado.add(periodo);
	        	}
	        		
	        	}
	        
	        
	        return resultado;
	    }
	
	 
	 
	 public static void main(String[] args){
		 PromocionController c = new PromocionController();
		// ArrayList listaMeses = c.armarRelacionesPosibles(201001, 201012);
		// ArrayList listaBi = c.armarBimestresPosibles(listaMeses);
	 }
	 
	 @RequestMapping(value="/consultaOrigen", method=RequestMethod.POST)
		public @ResponseBody ArrayList consultaOrigen(@RequestBody Long idTipo, HttpServletResponse response,HttpServletRequest request){
			UserSession user = getUsuarioFirmado(request);
			ArrayList listaOrigenes = (ArrayList) catalogoServiceBean.consultaLibrePorClave(0L, "from CgcCatTipoOrigen to where to.cgcCatTipo.idTipo = " +idTipo);
			return listaOrigenes;
		}	
	 
	 @RequestMapping(value="/consultaComboFindFuente", method=RequestMethod.POST)
		public @ResponseBody ArrayList consultaComboFindFuente(@RequestBody Long idTipo, HttpServletResponse response,HttpServletRequest request){
			UserSession user = getUsuarioFirmado(request);
			ArrayList listaOrigenes = (ArrayList) catalogoServiceBean.consultaLibrePorClave(0L, "select distinct to.cgcCatOrigen from CgcCatTipoOrigen to where to.cgcCatTipo.idTipo in (1,2,3,4)" );
			
			return listaOrigenes;
		}	
	 
	 @RequestMapping(value="/consultaComboFindFuentePromocion", method=RequestMethod.POST)
		public @ResponseBody ArrayList consultaComboFindFuentePromocion(@RequestBody Long idTipo, HttpServletResponse response,HttpServletRequest request){
			UserSession user = getUsuarioFirmado(request);
			ArrayList listaOrigenes = (ArrayList) catalogoServiceBean.consultaLibrePorClave(0L, "select distinct to.cgcCatOrigen from CgcCatTipoOrigen to where to.cgcCatTipo.idTipo in (5,6,7,8)" );
			return listaOrigenes;
		}	
	 
	 @RequestMapping(value="/consultaOrigenPromocion", method=RequestMethod.POST)
		public @ResponseBody ArrayList consultaOrigenPromocion(@RequestBody String idTipo, HttpServletResponse response,HttpServletRequest request){
			UserSession user = getUsuarioFirmado(request);
			ArrayList listaOrigenes = (ArrayList) catalogoServiceBean.consultaLibrePorClave(0L, "from CgcCatTipoOrigen to where to.cgcCatTipo.cgcCatflujo.idFlujo = 2");
			return listaOrigenes;
		}	
		
		@RequestMapping(value="/consultaComboCriterios", method=RequestMethod.POST)
		public @ResponseBody ArrayList consultaCriterios(@RequestBody ArrayList list, HttpServletResponse response,HttpServletRequest request){
			UserSession user = getUsuarioFirmado(request);
			String idTipo = (String)list.get(0);
			String idOrigen = (String)list.get(1);
			ArrayList listaOrigenes = (ArrayList) catalogoServiceBean.consultaLibrePorClave(0L, "from CgtCatCriterioSeleccion cs where cs.cgcCatOrigen.idOrigen = " +idOrigen + " and cs.cgcCatTipo.idTipo = "  + idTipo );
			return listaOrigenes;
		}	 
		
		@RequestMapping(value="/consultaMotivoRechazo", method=RequestMethod.POST)
		public @ResponseBody ArrayList consultaMotivoRechazo(@RequestBody Long idTipo, HttpServletResponse response,HttpServletRequest request){
			UserSession user = getUsuarioFirmado(request);
			ArrayList listaMotivos = (ArrayList) catalogoServiceBean.consultaLibrePorClave(0L, "from CgcCatMotivoRechazo");
			return listaMotivos;
		}
		

		@RequestMapping(value="/consultaMotivoCancelacion", method=RequestMethod.POST)
		public @ResponseBody ArrayList consultaMotivosCancelacion(@RequestBody Long idTipo, HttpServletResponse response,HttpServletRequest request){
			UserSession user = getUsuarioFirmado(request);
			ArrayList listaMotivos = (ArrayList) catalogoServiceBean.consultaLibrePorClave(0L, "from CgcCatMotivoCancelacion");
			return listaMotivos;
		}
	
		@RequestMapping(value="/consultaAuditores", method=RequestMethod.POST)
		public @ResponseBody ArrayList consultaAuditores(@RequestBody  String id,HttpServletResponse response,HttpServletRequest request){
			ArrayList<SegUsuarioFuncionario> listaAuditores = new ArrayList();
			UserSession user = getUsuarioFirmado(request);
			listaAuditores = (ArrayList)catalogoServiceBean.consultaLibrePorClave(0L, "from SegUsuarioFuncionario u where u.sacDelegacion.cveCodigo = '" +user.getCveCodigoDelegacion()  + "' and u.sacSubdelegacion.cveCodigo = '"+user.getCveCodigoSubDelegacion()+"'" ) ;
			ArrayList auditores = new ArrayList();
			for(int i=0; i<listaAuditores.size(); i++){
				String[] auditor = new String[2];
				auditor[0] = listaAuditores.get(i).getSegUsuario().getCveIdUsuario() +"";
				auditor[1] = listaAuditores.get(i).getSegUsuario().getNombreCompleto();
				auditores.add(auditor);
			}
			return auditores;
		}
		
		
		@RequestMapping(value="/consultaCorreccion", method=RequestMethod.POST)
		public @ResponseBody CgtCorreccion consultaCorreccion(@RequestBody CgtCorreccion correccion){
			ArrayList<CgtCorreccion> listaCorreccion = new ArrayList();
				 listaCorreccion = (ArrayList)catalogoServiceBean.consultaLibrePorClave(0L, "from CgtCorreccion p where p.folio = '" + correccion.getFolio() + "'") ;
				 CgtCorreccion corre = null;
				 if(listaCorreccion!=null && listaCorreccion.size() > 0){
					 corre = listaCorreccion.get(0);
					 logger.debug("getAfil15 :: "+correccion.getAfil15());
					 logger.debug("getIdMotivoRechazo :: "+correccion.getIdMotivoRechazo());
				 }
			return corre;
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
		
}
