package mx.gob.imss.ctirss.correccion.web.controller.catalogos;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import mx.gob.imss.ctirss.correccion.base.paginador.model.DatosEntradaPaginador;
import mx.gob.imss.ctirss.correccion.base.paginador.model.DatosSalidaPaginador;
import mx.gob.imss.ctirss.correccion.catalogos.base.paginador.model.CgcOrigenWrapperDataTable;
import mx.gob.imss.ctirss.correccion.catalogos.base.paginador.model.CgcTipoWrapperDataTable;
import mx.gob.imss.ctirss.correccion.framework.base.controller.AbstractController;
import mx.gob.imss.ctirss.correccion.model.CgcCatFlujo;
import mx.gob.imss.ctirss.correccion.model.CgcCatOrigen;
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
@RequestMapping(value="/catalogo/origen")
public class OrigenController extends AbstractController{

	@Autowired
	private ICatalogoService<CgcCatOrigen> catalogoServiceBean;
	
	@RequestMapping(method=RequestMethod.GET)
	public String getCreateForm(Model model) {
		model.addAttribute("cgcCatOrigen",new CgcCatOrigen());
		 return "catalogos/origen/origenMain";
	}
	
	@RequestMapping(value="/paginar", method=RequestMethod.POST )
    public @ResponseBody DatosSalidaPaginador<CgcCatOrigen> pagina(@RequestBody CgcOrigenWrapperDataTable aoData ) {
		System.out.println(".-.-controller public @ResponseBody  DatosSalidaPaginador<CgcCatTipo> pagina(@RequestBody CgcTipoWrapperDataTable aoData ) {");
		DatosEntradaPaginador send = new DatosEntradaPaginador();
		CrtSolicitudcorr solicitud = new CrtSolicitudcorr();
		List<CgcCatOrigen> lstSalida = new ArrayList<CgcCatOrigen>();
				
		send.parserArray(aoData.getAoData());
		send.setModelo(aoData.getoForm());
		
		DatosSalidaPaginador<CgcCatOrigen> reply = this.catalogoServiceBean.paginaOrigen(send);
		
		
		System.out.println(".-.-controller realizo consulta) {");
        reply.setsEcho(send.getsEcho());
        
        return reply;
    }
	
	@RequestMapping(value="/guardar", method=RequestMethod.POST )
    public @ResponseBody CgcCatOrigen guardar(@RequestBody CgcCatOrigen aoData , HttpServletRequest request ) {
		
		List lstResult = this.catalogoServiceBean.consultaSQL("select co.ID_ORIGEN,co.DESC_ORIGEN from CGC_CATORIGEN co order by co.ID_ORIGEN desc");
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
		
		aoData.setIdOrigen(id);
		aoData.setFecFechaReg(new Date());
		aoData = this.catalogoServiceBean.agregar(aoData);
		
		return aoData;
	}
	
	@RequestMapping(value="/buscar", method=RequestMethod.POST )
    public @ResponseBody CgcCatOrigen buscar(@RequestBody CgcCatOrigen aoData ) {
		
		aoData = this.catalogoServiceBean.consultaPorClave(aoData);
		
		return aoData;
	}
	
	
}