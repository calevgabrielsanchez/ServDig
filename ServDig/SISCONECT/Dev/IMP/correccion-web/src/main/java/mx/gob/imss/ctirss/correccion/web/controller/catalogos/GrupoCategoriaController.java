package mx.gob.imss.ctirss.correccion.web.controller.catalogos;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mx.gob.imss.ctirss.correccion.base.paginador.model.DatosEntradaPaginador;
import mx.gob.imss.ctirss.correccion.base.paginador.model.DatosSalidaPaginador;
import mx.gob.imss.ctirss.correccion.catalogos.base.paginador.model.CrcGrupoCategoriaWrapperDataTable;
import mx.gob.imss.ctirss.correccion.catalogos.model.CrcGastos;
import mx.gob.imss.ctirss.correccion.catalogos.model.CrcGrupoCategoria;
import mx.gob.imss.ctirss.correccion.catalogos.service.interfaces.GrupoCategoriaService;
import mx.gob.imss.ctirss.correccion.correccion.service.interfaces.SolicitudService;
import mx.gob.imss.ctirss.correccion.framework.base.controller.AbstractController;
import mx.gob.imss.ctirss.correccion.model.CrtSolicitudcorr;
import mx.gob.imss.ctirss.correccion.session.UserSession;
import mx.gob.imss.ctirss.correccion.utils.Functions;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value="/catalogo/grupoCategoria")
public class GrupoCategoriaController extends AbstractController{

	@Autowired
	private GrupoCategoriaService<CrcGrupoCategoria> grupoCategoriaService;
	@Autowired
	private SolicitudService solicitudService;

	@RequestMapping(method=RequestMethod.GET)
	public String getCreateForm(Model model,HttpServletRequest request) {
		CrcGrupoCategoria crcGrupoCategoria = new CrcGrupoCategoria();
		UserSession user = getUsuarioFirmado(request);
		crcGrupoCategoria.setCveSubdelegcionOficial(Functions.getCveSubdelegacionOficial(user));
		
		model.addAttribute(crcGrupoCategoria);
		 return determinaURL(request, "catalogos/grupoCategoria/grupoCategoriaMain", "catalogos/grupoCategoria/grupoCategoriaMain/patron");
	}
	
	@RequestMapping(value="/modificar" , method=RequestMethod.POST)
	public @ResponseBody CrcGrupoCategoria modify(@RequestBody CrcGrupoCategoria grupoCategoria, HttpServletResponse response) {
		System.out.println(".--. EN MODIFICAR");
		this.grupoCategoriaService.modificar(grupoCategoria);
		System.out.println(".--. MODIFICO");
		return grupoCategoria;
	}
	
	@RequestMapping(value="/eliminar" , method=RequestMethod.POST)
	public @ResponseBody CrcGrupoCategoria delete(@RequestBody CrcGrupoCategoria grupoCategoria, HttpServletResponse response) {
		System.out.println(".--. EN eliminar");
		this.grupoCategoriaService.eliminar(grupoCategoria);
		System.out.println("ELIMINO");
		return grupoCategoria;
	}
	
	@RequestMapping(value="/agregar" , method=RequestMethod.POST)
	public @ResponseBody CrcGrupoCategoria create(@RequestBody CrcGrupoCategoria grupoCategoria, HttpServletResponse response) {

		CrtSolicitudcorr solicitud = new CrtSolicitudcorr();
		if(grupoCategoria != null && grupoCategoria.getFolioCorreccion() != null){
			solicitud.setNuFolio(grupoCategoria.getFolioCorreccion());
			solicitud = this.solicitudService.consultarFolio(solicitud);
		}
		if(solicitud != null && solicitud.getCveSolicitudCorr() != null){
			grupoCategoria.setCveSolicitudCorr(solicitud.getCveSolicitudCorr());
		}else{
			return null;
		}
			
		this.grupoCategoriaService.agregar(grupoCategoria);		
		return grupoCategoria;
	}
	
	@RequestMapping(value="/consultar" , method=RequestMethod.POST)
	public @ResponseBody List<CrcGrupoCategoria> consultar(@RequestBody CrcGrupoCategoria grupoCategoria, HttpServletResponse response) {
				
		List<CrcGrupoCategoria> rs = this.grupoCategoriaService.consultar(grupoCategoria);		
		if(rs.size()>0){
			return rs;
		}
		return null;
	}
	
	@RequestMapping(value="/consultaPorClave")
	public @ResponseBody CrcGrupoCategoria consultaPorClave(@RequestBody CrcGrupoCategoria grupoCategoria){
		System.out.println("CVE ID A BUSCAR:"+grupoCategoria.getCveGrupoCategoria());
		List<CrtSolicitudcorr> solicitud = new ArrayList<CrtSolicitudcorr>();
		if(grupoCategoria.getCveGrupoCategoria() > 0){
			
			grupoCategoria = (CrcGrupoCategoria) grupoCategoriaService.consultaPorClave(grupoCategoria);
			
		}
		if(grupoCategoria != null && grupoCategoria.getCveSolicitudCorr() != null){
			solicitud = this.solicitudService.consultarSolicitudesById(grupoCategoria.getCveSolicitudCorr());
		}
		if(solicitud != null && solicitud.size() > 0){
			grupoCategoria.setFolioCorreccion(solicitud.get(0).getNuFolio());
		}
		
		return grupoCategoria;
	}
	
	@RequestMapping(value="/paginar", method=RequestMethod.POST )
    public @ResponseBody DatosSalidaPaginador<CrcGrupoCategoria> pagina(@RequestBody CrcGrupoCategoriaWrapperDataTable aoData ) {
		System.out.println(".-.-controller public @ResponseBody DatosSalidaPaginador<CrcGrupoCategoria> pagina(@RequestBody CrcGrupoCategoriaWrapperDataTable aoData ) {");
		DatosEntradaPaginador send = new DatosEntradaPaginador();
		CrtSolicitudcorr solicitud = new CrtSolicitudcorr();
		// Buscamos Solicitud por folio
		if(aoData.getoForm().getFolioCorreccion() != null){
			
			solicitud.setNuFolio(aoData.getoForm().getFolioCorreccion());
			solicitud = this.solicitudService.consultarFolio(solicitud);			
		}
		
		if(solicitud != null && solicitud.getCveSolicitudCorr() != null){
			aoData.getoForm().setCveSolicitudCorr(solicitud.getCveSolicitudCorr());
			aoData.getoForm().setFolioCorreccion("");
		}
		send.parserArray(aoData.getAoData());
		send.setModelo(aoData.getoForm());
		
		DatosSalidaPaginador<CrcGrupoCategoria> reply = new DatosSalidaPaginador<CrcGrupoCategoria>();
		if(aoData.getoForm().getCveSolicitudCorr() != null || aoData.getoForm().getTxGrupoCategoria() != null){
			
			reply = this.grupoCategoriaService.pagina(send);
		}else{
			List<CrcGrupoCategoria> lstGrupoCategoria = new ArrayList<CrcGrupoCategoria>();
			reply.setAaData(lstGrupoCategoria);
			reply.setiTotalDisplayRecords(0);
			reply.setiTotalRecords(0);
		}
				
		System.out.println(".-.-controller realizo consulta) {");
        reply.setsEcho(send.getsEcho());
        
        return reply;
    }
	
	@RequestMapping(value="/validar", method=RequestMethod.POST )
    public @ResponseBody DatosSalidaPaginador<CrcGrupoCategoria> valida(@RequestBody CrcGrupoCategoriaWrapperDataTable aoData ) {
		System.out.println(".-.-controller public @ResponseBody DatosSalidaPaginador<CrcGrupoCategoria> pagina(@RequestBody CrcGrupoCategoriaWrapperDataTable aoData ) {");
		DatosEntradaPaginador send = new DatosEntradaPaginador();
		
		
		
		send.parserArray(aoData.getAoData());
		send.setModelo(aoData.getoForm());
		
		DatosSalidaPaginador<CrcGrupoCategoria> reply = this.grupoCategoriaService.pagina(send);
		System.out.println(".-.-controller realizo consulta) {");
        reply.setsEcho(send.getsEcho());
        
        return reply;
    }
	
	
}
