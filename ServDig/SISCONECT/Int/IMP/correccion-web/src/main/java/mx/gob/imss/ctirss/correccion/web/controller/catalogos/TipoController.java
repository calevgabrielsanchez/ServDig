package mx.gob.imss.ctirss.correccion.web.controller.catalogos;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import mx.gob.imss.ctirss.correccion.base.paginador.model.DatosEntradaPaginador;
import mx.gob.imss.ctirss.correccion.base.paginador.model.DatosSalidaPaginador;
import mx.gob.imss.ctirss.correccion.catalogos.base.paginador.model.CgcTipoWrapperDataTable;
import mx.gob.imss.ctirss.correccion.catalogos.base.paginador.model.CrcPercepcionesWrapperDataTable;
import mx.gob.imss.ctirss.correccion.catalogos.model.CrcPercepciones;
import mx.gob.imss.ctirss.correccion.framework.base.controller.AbstractController;
import mx.gob.imss.ctirss.correccion.framework.base.model.AbstractModel;
import mx.gob.imss.ctirss.correccion.model.CgcCatFlujo;
import mx.gob.imss.ctirss.correccion.model.CgcCatTipo;
import mx.gob.imss.ctirss.correccion.model.CrtSolicitudcorr;
import mx.gob.imss.ctirss.correccion.service.interfaces.ICatalogoService;
import mx.gob.imss.ctirss.correccion.session.UserSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value="/catalogo/tipo")
public class TipoController extends AbstractController{

	@Autowired
	private ICatalogoService<CgcCatTipo> catalogoServiceBean;
	
	@RequestMapping(method=RequestMethod.GET)
	public String getCreateForm(Model model) {
		model.addAttribute("cgcCatFlujo",new CgcCatFlujo());
		 return "catalogos/tipo/tipoMain";
	}
	
	@RequestMapping(value="/paginar", method=RequestMethod.POST )
    public @ResponseBody DatosSalidaPaginador<CgcCatTipo> pagina(@RequestBody CgcTipoWrapperDataTable aoData ) {
		System.out.println(".-.-controller public @ResponseBody  DatosSalidaPaginador<CgcCatTipo> pagina(@RequestBody CgcTipoWrapperDataTable aoData ) {");
		DatosEntradaPaginador send = new DatosEntradaPaginador();
		CrtSolicitudcorr solicitud = new CrtSolicitudcorr();
		List<CgcCatTipo> lstSalida = new ArrayList<CgcCatTipo>();
				
		send.parserArray(aoData.getAoData());
		send.setModelo(aoData.getoForm());
		
		DatosSalidaPaginador<CgcCatTipo> reply = this.catalogoServiceBean.paginaTipo(send);
		
		
		System.out.println(".-.-controller realizo consulta) {");
        reply.setsEcho(send.getsEcho());
        
        return reply;
    }
	
	@RequestMapping(value="/guardar", method=RequestMethod.POST )
    public @ResponseBody CgcCatTipo guardar(@RequestBody CgcCatTipo aoData , HttpServletRequest request) {
		
		List lstResult = this.catalogoServiceBean.consultaSQL("select ct.ID_TIPO,ct.ID_FLUJO,ct.DESCRIPCION from CGC_CATTIPO ct order by ct.ID_TIPO desc");
		Iterator itera = lstResult.iterator();
		Long id = 0L;
		if(itera.hasNext()){
			Object[] obj = (Object[]) itera.next();
			BigDecimal valor = (BigDecimal) obj[0];
			id = valor.longValue();
			id = id + 1L;
		}
		
		UserSession session = this.getUsuarioFirmado(request);
		if(session != null && session.getCveIdUsuario() != null){
			aoData.setCveUsuario(session.getCveIdUsuario().toString());
		}
		
		aoData.setIdTipo(id);
		aoData.setFecFechaReg(new Date());
		aoData = this.catalogoServiceBean.agregar(aoData);
		
		return aoData;
	}
	
	@RequestMapping(value="/buscar", method=RequestMethod.POST )
    public @ResponseBody CgcCatTipo buscar(@RequestBody CgcCatTipo aoData ) {
		
		aoData = this.catalogoServiceBean.consultaPorClave(aoData);
		
		return aoData;
	}
	
	
}
