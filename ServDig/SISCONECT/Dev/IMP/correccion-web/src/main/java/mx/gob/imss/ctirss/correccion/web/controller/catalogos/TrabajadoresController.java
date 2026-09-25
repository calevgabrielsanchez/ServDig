package mx.gob.imss.ctirss.correccion.web.controller.catalogos;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mx.gob.imss.ctirss.correccion.base.paginador.model.DatosEntradaPaginador;
import mx.gob.imss.ctirss.correccion.base.paginador.model.DatosSalidaPaginador;
import mx.gob.imss.ctirss.correccion.catalogos.base.paginador.model.CrcTrabajadoresWrapperDataTable;
import mx.gob.imss.ctirss.correccion.catalogos.model.CrcTrabajadores;
import mx.gob.imss.ctirss.correccion.catalogos.service.interfaces.TrabajadoresService;
import mx.gob.imss.ctirss.correccion.correccion.service.interfaces.SolicitudService;
import mx.gob.imss.ctirss.correccion.model.CrtSolicitudcorr;
import mx.gob.imss.ctirss.correccion.session.UserSession;
import mx.gob.imss.ctirss.correccion.utils.Functions;
import mx.gob.imss.ctirss.correccion.web.controller.utils.ValidaSolicitudCorreccion;
import mx.gob.imss.ctirss.correccion.framework.base.controller.AbstractController;
import mx.gob.imss.ctirss.correccion.framework.utils.ConstantesBusiness;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value="/catalogo/trabajadores")
public class TrabajadoresController extends AbstractController {

	@Autowired
	private TrabajadoresService<CrcTrabajadores> trabajadoresServiceBean;
	 

	@Autowired
	private SolicitudService<CrtSolicitudcorr> solicitudServiceBean;
	
	@Autowired
	private ValidaSolicitudCorreccion validaSolicitudService;

	
	@RequestMapping(method=RequestMethod.GET)
	public String getCreateForm(Model model,HttpServletRequest request){
		CrcTrabajadores  crcTrabajadores = new CrcTrabajadores();
		UserSession user = getUsuarioFirmado(request);
		crcTrabajadores.setCveSubdelegcionOficial(Functions.getCveSubdelegacionOficial(user));
		
		model.addAttribute("valCveSubdel",Functions.getCveSubdelegacionOficial(user));
		model.addAttribute(crcTrabajadores);
		return determinaURL(request, "catalogos/trabajadores/trabajadoresMain", "catalogos/trabajadores/trabajadoresMain/patron");
	}
	
	@RequestMapping(value="modificar", method=RequestMethod.POST)
	public @ResponseBody CrcTrabajadores modify(@RequestBody CrcTrabajadores trabajadores, HttpServletResponse response,  HttpServletRequest request){
		System.out.println(".--.EN MODIFICAR");
		
		UserSession user = getUsuarioFirmado(request);
		trabajadores.setFecFechareg(new Date());
		trabajadores.setCveUsuario(user.getCveIdUsuario().toString());
		
		String resultadoValidaFolio = validaSolicitudService.estadoActualSolicitud(trabajadores.getFolioCorreccion());
		
		if(resultadoValidaFolio.equals("")){
			this.trabajadoresServiceBean.modificar(trabajadores);
			trabajadores.setError(ConstantesBusiness.NO_ERRROR);
			System.out.println(".--. MODIFICO");
		}else{ trabajadores.setError(resultadoValidaFolio);}
		
		return trabajadores;
	}
	
	@RequestMapping(value="/eliminar" , method=RequestMethod.POST)
	public @ResponseBody CrcTrabajadores delete(@RequestBody CrcTrabajadores trabajadores, HttpServletResponse response) {
		System.out.println(".--. EN eliminar");
		
		String resultadoValidaFolio = ConstantesBusiness.NO_ERRROR;
		
		if(resultadoValidaFolio.equals("")){
			trabajadores = this.trabajadoresServiceBean.eliminar(trabajadores);
			System.out.println(".--. ELIMINO");
		}else{ trabajadores.setError(resultadoValidaFolio);}
		
		return trabajadores;
	}
	
	@RequestMapping(value="/agregar" , method=RequestMethod.POST)
	public @ResponseBody CrcTrabajadores create(@RequestBody CrcTrabajadores trabajadores, HttpServletResponse response, HttpServletRequest request) {

		UserSession user = getUsuarioFirmado(request);	
		
		CrtSolicitudcorr solicitud = new CrtSolicitudcorr();
		solicitud.setNuFolio(trabajadores.getFolioCorreccion());
		CrtSolicitudcorr resultado = solicitudServiceBean.consultarFolio(solicitud);
		
		if(resultado!=null){
			trabajadores.setCveSolicitudCorr(new Long(resultado.getCveSolicitudCorr()));
			trabajadores.setFecFechareg(new Date());
			if(user.getCveIdUsuario()!=null){
				trabajadores.setCveUsuario(user.getCveIdUsuario().toString());	
			}
			
			
			String resultadoValidaFolio = validaSolicitudService.estadoActualSolicitud(trabajadores.getFolioCorreccion());
			
			if(resultadoValidaFolio.equals("")){
				this.trabajadoresServiceBean.agregar(trabajadores);
				trabajadores.setError(ConstantesBusiness.NO_ERRROR);
				System.out.println(".--. ELIMINO");
			}else{ trabajadores.setError(resultadoValidaFolio);}
			
			
			return trabajadores;
		}else
			return null;
		
	}
	
	@RequestMapping(value="/consultar" , method=RequestMethod.POST)
	public @ResponseBody List<CrcTrabajadores> consultar(@RequestBody CrcTrabajadores trabajadores, HttpServletResponse response) {
		List<CrcTrabajadores> rs = this.trabajadoresServiceBean.consultar(trabajadores);		
		if(rs.size()>0){
			return rs;
		}
		return null;
	}
	
	@RequestMapping(value="/consultaPorClave")
	public @ResponseBody CrcTrabajadores consultaPorClave(@RequestBody CrcTrabajadores trabajadores){
		System.out.println("CVE ID A BUSCAR:"+trabajadores.getCveTrabajador());
		if(trabajadores.getCveTrabajador().longValue()>0){
			trabajadores = trabajadoresServiceBean.consultaPorClave(trabajadores);
		}
		return trabajadores;
	}
	
	@RequestMapping(value="/paginar", method=RequestMethod.POST )
    public @ResponseBody DatosSalidaPaginador<CrcTrabajadores> pagina(@RequestBody CrcTrabajadoresWrapperDataTable aoData ) {
		System.out.println(".-.-controller public @ResponseBody DatosSalidaPaginador<CrcGastos> pagina(@RequestBody CrcGastosWrapperDataTable aoData ) {");
		DatosEntradaPaginador send = new DatosEntradaPaginador();
		CrtSolicitudcorr solicitud = null;
		String resultadoValidaFolio = null;
		
		if(aoData.getoForm().getFolioCorreccion()!=null&&aoData.getoForm().getFolioCorreccion()!=""){
			
			resultadoValidaFolio = validaSolicitudService.estadoActualSolicitud(aoData.getoForm().getFolioCorreccion());
			
			if(resultadoValidaFolio.equals(ConstantesBusiness.NO_ERRROR)){
				resultadoValidaFolio = null;
				solicitud = new CrtSolicitudcorr();
				solicitud.setNuFolio(aoData.getoForm().getFolioCorreccion());
				solicitud = solicitudServiceBean.consultarFolio(solicitud);
			}
			
			if(solicitud!=null)
				aoData.getoForm().setCveSolicitudCorr(new Long(solicitud.getCveSolicitudCorr()));
			else
				aoData.getoForm().setCveSolicitudCorr(new Long(0));
			
			
		}else
			aoData.getoForm().setCveSolicitudCorr(new Long(0));
		
		send.parserArray(aoData.getAoData());
		send.setModelo(aoData.getoForm());
		
		DatosSalidaPaginador<CrcTrabajadores> reply = this.trabajadoresServiceBean.pagina(send);
		System.out.println(".-.-controller realizo consulta) {");
        reply.setsEcho(send.getsEcho());
        
        if(resultadoValidaFolio!=null) reply.setError(resultadoValidaFolio);
        
        if(reply!=null && reply.getAaData()!=null && !reply.getAaData().isEmpty()){
			reply.getAaData().get(0).setCveStatusCorreccion(solicitud.getCveStatus());
			
		}
        
        
        
        return reply;
    }
	
	@RequestMapping(value="/validar", method=RequestMethod.POST )
    public @ResponseBody DatosSalidaPaginador<CrcTrabajadores> valida(@RequestBody CrcTrabajadoresWrapperDataTable aoData ) {
		System.out.println(".-.-controller public @ResponseBody DatosSalidaPaginador<CrcTrabajadores> pagina(@RequestBody CrcTrabajadoresWrapperDataTable aoData ) {");
		DatosEntradaPaginador send = new DatosEntradaPaginador();

		send.parserArray(aoData.getAoData());
		send.setModelo(aoData.getoForm());
		
		
		DatosSalidaPaginador<CrcTrabajadores> reply = this.trabajadoresServiceBean.pagina(send);
		System.out.println(".-.-controller realizo consulta) {");
        reply.setsEcho(send.getsEcho());
        
        return reply;
    }
	
	
	@RequestMapping(value="/consultaPorClaveDatos")
	public @ResponseBody CrcTrabajadores consultaPorClaveEliminar(@RequestBody CrcTrabajadores trabajadores){
		System.out.println("CVE ID A BUSCAR:"+trabajadores.getCveTrabajador());
		if(trabajadores.getCveTrabajador().longValue()>0){
			trabajadores = trabajadoresServiceBean.consultaPorClaveDatos(trabajadores);
		}
		return trabajadores;
	}
}
