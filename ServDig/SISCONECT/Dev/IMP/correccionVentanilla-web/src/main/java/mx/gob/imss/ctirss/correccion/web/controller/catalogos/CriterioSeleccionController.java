package mx.gob.imss.ctirss.correccion.web.controller.catalogos;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import mx.gob.imss.ctirss.correccion.base.paginador.model.DatosEntradaPaginador;
import mx.gob.imss.ctirss.correccion.base.paginador.model.DatosSalidaPaginador;
import mx.gob.imss.ctirss.correccion.catalogos.base.paginador.model.CgcCRiterioSeleccionWrapperDataTable;
import mx.gob.imss.ctirss.correccion.catalogos.base.paginador.model.CgcTipoOrigenWrapperDataTable;
import mx.gob.imss.ctirss.correccion.catalogos.model.CgcCatcriterioseleccion;
import mx.gob.imss.ctirss.correccion.framework.base.controller.AbstractController;
import mx.gob.imss.ctirss.correccion.model.CgcCatOrigen;
import mx.gob.imss.ctirss.correccion.model.CgcCatTipo;
import mx.gob.imss.ctirss.correccion.model.CgcCatTipoOrigen;
import mx.gob.imss.ctirss.correccion.service.interfaces.ICatalogoService;
import mx.gob.imss.ctirss.correccion.session.UserSession;
import mx.gob.imss.ctirss.correccion.web.controller.vo.SelectorCatVO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value="/catalogo/criterioSeleccion")
public class CriterioSeleccionController extends AbstractController{

	@Autowired
	private ICatalogoService<CgcCatcriterioseleccion> catalogoServiceBean;
	@Autowired
	private ICatalogoService<CgcCatTipo> catalogoTipoServiceBean;
	@Autowired
	private ICatalogoService<CgcCatOrigen> catalogoOrigenServiceBean;
	@Autowired
	private ICatalogoService<CgcCatcriterioseleccion> catalogoCSServiceBean;
	
	@RequestMapping(method=RequestMethod.GET)
	public String getCreateForm(Model model) {
		model.addAttribute("selectorCatVO",new SelectorCatVO());
		
		
		 return "catalogos/criterioSeleccion/criterioSeleccionMain";
	}
	
	@RequestMapping(value="/paginar", method=RequestMethod.POST )
    public @ResponseBody DatosSalidaPaginador<CgcCatcriterioseleccion> pagina(@RequestBody CgcCRiterioSeleccionWrapperDataTable aoData ) {
		System.out.println(".-.-controller public @ResponseBody  DatosSalidaPaginador<CgcCatTipo> pagina(@RequestBody CgcTipoWrapperDataTable aoData ) {");
		
		DatosEntradaPaginador send = new DatosEntradaPaginador();
				
		send.parserArray(aoData.getAoData());
		send.setModelo(aoData.getoForm());
		
		DatosSalidaPaginador<CgcCatcriterioseleccion> reply = this.catalogoServiceBean.paginaCriterioSeleccion(send);
		if(reply.getAaData() != null && reply.getAaData().size() > 0){
			CgcCatTipo tipo = new CgcCatTipo();
			CgcCatOrigen origen = new CgcCatOrigen();
			for (Iterator iterator = reply.getAaData().iterator(); iterator.hasNext();) {
				CgcCatcriterioseleccion type = (CgcCatcriterioseleccion) iterator.next();
				
				tipo.setIdTipo(type.getIdTipo());
				tipo = this.catalogoTipoServiceBean.consultaPorClave(tipo);
				type.setDescTipo(tipo.getDescripcion());
				
				origen.setIdOrigen(type.getIdOrigen());
				origen = this.catalogoOrigenServiceBean.consultaPorClave(origen);
				type.setDescOrigen(origen.getDescOrigen());
			}
			
		}
		
		System.out.println(".-.-controller realizo consulta) {");
        reply.setsEcho(send.getsEcho());
        
        return reply;
    }
	
	@RequestMapping(value="/guardar", method=RequestMethod.POST )
    public @ResponseBody CgcCatcriterioseleccion guardar(@RequestBody CgcCatcriterioseleccion aoData , HttpServletRequest request) {
		
		List lstResult = this.catalogoCSServiceBean.consultaSQL("  select cs.ID_CRITERIOSELECCION,cs.ID_TIPO,cs.ID_ORIGEN,cs.DESC_CRITERIOSELECCION from CGC_CATCRITERIOSELECCION cs order by cs.ID_CRITERIOSELECCION desc ");
		Iterator itera = lstResult.iterator();
		Long id = 0L;
		if(itera.hasNext()){
			Object[] obj = (Object[]) itera.next();
			BigDecimal valor = (BigDecimal) obj[0];
			id = valor.longValue();
			id = id + 1L;
		}
		
		UserSession session = this.getUsuarioFirmado(request);
		if(session != null && session.getCurpUsuario() != null){
			aoData.setCveUsuario(session.getCurpUsuario().toString());
		}
		
		aoData.setIdCriterioseleccion(id);
		aoData.setFecFechaReg(new Date());
		aoData = this.catalogoServiceBean.agregar(aoData);
		
		return aoData;
	}
	
	@RequestMapping(value="/buscar", method=RequestMethod.POST )
    public @ResponseBody CgcCatcriterioseleccion buscar(@RequestBody CgcCatcriterioseleccion aoData ) {
		
		aoData = this.catalogoServiceBean.consultaPorClave(aoData);
		
		return aoData;
	}
	
	
	@RequestMapping(value="/cboTipo", method=RequestMethod.POST )
    public @ResponseBody List<CgcCatTipo> cboTipo(@RequestBody CgcCatTipoOrigen aoData) {
		
		List<CgcCatTipo> lstResult = this.catalogoTipoServiceBean.consultaSQL("select tipo.ID_TIPO, tipo.DESCRIPCION from CGC_CATTIPO tipo ");
				
		
		return lstResult;
	}
	
	@RequestMapping(value="/cboOrigen", method=RequestMethod.POST )
    public @ResponseBody List<CgcCatOrigen> cboOrigen(@RequestBody CgcCatTipoOrigen aoData) {
		
		
		List<CgcCatOrigen> lstResult = this.catalogoOrigenServiceBean.consultaSQL("select ori.ID_ORIGEN, ori.DESC_ORIGEN from CGC_CATORIGEN ori");
				
		
		return lstResult;
	}
	
}