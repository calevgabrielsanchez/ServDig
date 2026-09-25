package mx.gob.imss.ctirss.correccion.web.controller.catalogos;

import java.util.ArrayList;
import java.util.List;

import mx.gob.imss.ctirss.correccion.base.paginador.model.DatosEntradaPaginador;
import mx.gob.imss.ctirss.correccion.base.paginador.model.DatosSalidaPaginador;
import mx.gob.imss.ctirss.correccion.catalogos.base.paginador.model.CgcOrigenWrapperDataTable;
import mx.gob.imss.ctirss.correccion.catalogos.base.paginador.model.CgcTipoOrigenWrapperDataTable;
import mx.gob.imss.ctirss.correccion.framework.base.controller.AbstractController;
import mx.gob.imss.ctirss.correccion.model.CgcCatOrigen;
import mx.gob.imss.ctirss.correccion.model.CgcCatTipo;
import mx.gob.imss.ctirss.correccion.model.CgcCatTipoOrigen;
import mx.gob.imss.ctirss.correccion.model.CrtSolicitudcorr;
import mx.gob.imss.ctirss.correccion.service.interfaces.ICatalogoService;
import mx.gob.imss.ctirss.correccion.web.controller.vo.SelectorCatVO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value="/catalogo/tipoOrigen")
public class TipoOrigenController extends AbstractController{

	@Autowired
	private ICatalogoService<CgcCatTipoOrigen> catalogoServiceBean;
	@Autowired
	private ICatalogoService<CgcCatTipo> catalogoTipoServiceBean;
	@Autowired
	private ICatalogoService<CgcCatOrigen> catalogoOrigenServiceBean;
	
	@RequestMapping(method=RequestMethod.GET)
	public String getCreateForm(Model model) {
		model.addAttribute("selectorCatVO",new SelectorCatVO());
		
		
		 return "catalogos/tipoOrigen/tipoOrigenMain";
	}
	
	@RequestMapping(value="/paginar", method=RequestMethod.POST )
    public @ResponseBody DatosSalidaPaginador<CgcCatTipoOrigen> pagina(@RequestBody CgcTipoOrigenWrapperDataTable aoData ) {
		System.out.println(".-.-controller public @ResponseBody  DatosSalidaPaginador<CgcCatTipo> pagina(@RequestBody CgcTipoWrapperDataTable aoData ) {");
		
		DatosEntradaPaginador send = new DatosEntradaPaginador();
				
		send.parserArray(aoData.getAoData());
		send.setModelo(aoData.getoForm());
		
		DatosSalidaPaginador<CgcCatTipoOrigen> reply = this.catalogoServiceBean.paginaTipoOrigen(send);
		
		
		System.out.println(".-.-controller realizo consulta) {");
        reply.setsEcho(send.getsEcho());
        
        return reply;
    }
	
	@RequestMapping(value="/guardar", method=RequestMethod.POST )
    public @ResponseBody CgcCatTipoOrigen guardar(@RequestBody CgcCatTipoOrigen aoData ) {
		
		CgcCatTipo tipo = new CgcCatTipo();
		CgcCatOrigen origen =  new CgcCatOrigen();
		
		tipo.setIdTipo(aoData.getIdTipo());
		origen.setIdOrigen(aoData.getIdOrigen());
		
		aoData.setCgcCatTipo(tipo);
		aoData.setCgcCatOrigen(origen);
		
		aoData = this.catalogoServiceBean.agregar(aoData);
		
		return aoData;
	}
	
	@RequestMapping(value="/buscar", method=RequestMethod.POST )
    public @ResponseBody CgcCatTipoOrigen buscar(@RequestBody CgcCatTipoOrigen aoData ) {
		
		aoData = this.catalogoServiceBean.consultaPorClave(aoData);
		
		return aoData;
	}
	
	@RequestMapping(value="/eliminar", method=RequestMethod.POST )
    public @ResponseBody CgcCatTipoOrigen eliminar(@RequestBody CgcCatTipoOrigen aoData ) {
		
		this.catalogoServiceBean.eliminar(aoData);
		
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