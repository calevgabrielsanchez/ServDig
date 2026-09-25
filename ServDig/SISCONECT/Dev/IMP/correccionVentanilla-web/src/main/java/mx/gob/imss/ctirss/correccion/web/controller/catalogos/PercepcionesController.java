package mx.gob.imss.ctirss.correccion.web.controller.catalogos;


import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mx.gob.imss.ctirss.correccion.base.paginador.model.DatosEntradaPaginador;
import mx.gob.imss.ctirss.correccion.base.paginador.model.DatosSalidaPaginador;
import mx.gob.imss.ctirss.correccion.catalogos.base.paginador.model.CrcPercepcionesWrapperDataTable;
import mx.gob.imss.ctirss.correccion.catalogos.model.CrcPercepciones;
import mx.gob.imss.ctirss.correccion.catalogos.service.interfaces.PercepcionesService;
import mx.gob.imss.ctirss.correccion.correccion.service.interfaces.SolicitudService;
import mx.gob.imss.ctirss.correccion.framework.base.controller.AbstractController;
import mx.gob.imss.ctirss.correccion.framework.utils.ConstantesBusiness;
import mx.gob.imss.ctirss.correccion.model.CrtSolicitudcorr;
import mx.gob.imss.ctirss.correccion.presentacion.service.exception.BusinessException;
import mx.gob.imss.ctirss.correccion.session.ConstantesSession;
import mx.gob.imss.ctirss.correccion.session.UserSession;
import mx.gob.imss.ctirss.correccion.utils.Functions;
import mx.gob.imss.ctirss.correccion.web.controller.utils.ValidaSolicitudCorreccion;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value="/catalogo/percepciones")
public class PercepcionesController extends AbstractController{
	
	@Autowired
	private PercepcionesService<CrcPercepciones> persepcionesServiceBean;
	@Autowired
	private SolicitudService solicitudService;
	
	@Autowired
	private ValidaSolicitudCorreccion validaSolicitudService;
	
	
		
	@RequestMapping(method=RequestMethod.GET)
	public String getCreateForm(Model model,HttpServletRequest request) {
		CrcPercepciones crcPercepciones = new CrcPercepciones();
		
		UserSession user = getUsuarioFirmado(request);
		crcPercepciones.setCveSubdelegcionOficial(Functions.getCveSubdelegacionOficial(user));
		
		model.addAttribute(crcPercepciones);
		
		 return determinaURL(request, "catalogos/percepciones/percepcionesMain", "catalogos/percepciones/percepcionesMain/patron");
	}
	
	
	
	@RequestMapping(value="/modificar" , method=RequestMethod.POST)
	public @ResponseBody CrcPercepciones modify(@RequestBody CrcPercepciones percepcion, HttpServletResponse response) {
		System.out.println(".--. EN MODIFICAR");
		
		String resultadoValidaFolio = validaSolicitudService.estadoActualSolicitud(percepcion.getFolioCorreccion());
		
		if(resultadoValidaFolio.equals("")){
			this.persepcionesServiceBean.modificar(percepcion);
			percepcion.setError("");
			System.out.println(".--. MODIFICO");
		}else{ percepcion.setError(resultadoValidaFolio);}
		
		
		return percepcion;
	}
	
	@RequestMapping(value="/eliminar" , method=RequestMethod.POST)
	public @ResponseBody CrcPercepciones delete(@RequestBody CrcPercepciones percepcion, HttpServletResponse response) {
		System.out.println(".--. EN eliminar");

		String resultadoValidaFolio = ConstantesBusiness.NO_ERRROR;
		
		if(resultadoValidaFolio.equals("")){
			percepcion = this.persepcionesServiceBean.eliminar(percepcion);
			System.out.println("ELIMINO");
		}else{ percepcion.setError(resultadoValidaFolio);}
		
		
		
		return percepcion;
	}
	
	@RequestMapping(value="/agregar" , method=RequestMethod.POST)
	public @ResponseBody CrcPercepciones create(@RequestBody CrcPercepciones percepcion, HttpServletResponse response, HttpServletRequest request) {
	UserSession user = (UserSession)request.getSession().getAttribute(ConstantesSession.USR_SESSION);
		CrtSolicitudcorr solicitudCorr = new CrtSolicitudcorr();
		if(percepcion != null && percepcion.getFolioCorreccion() != null){	
			solicitudCorr.setNuFolio(percepcion.getFolioCorreccion());
			if(user!=null && user.getCveIdPatron()!=null && user.getCveIdPatron()!=0){
				solicitudCorr.setCvePatron(user.getCveIdPatron());
				solicitudCorr = solicitudService.consultarFolioRegPat(solicitudCorr);
			}else{
				solicitudCorr = solicitudService.consultarFolio(solicitudCorr);
			}
			
			
		}
		
		if(solicitudCorr!= null && solicitudCorr.getCveSolicitudCorr() != null){
			
			percepcion.setCveSolicitudCorr(solicitudCorr.getCveSolicitudCorr());
		}else{
			
			return null;
		}
		
		String resultadoValidaFolio = validaSolicitudService.estadoActualSolicitud(percepcion.getFolioCorreccion());
		
		if(resultadoValidaFolio.equals("") || percepcion.isValidaPercepcion()){
			this.persepcionesServiceBean.agregar(percepcion);
			List<CrcPercepciones> rs = this.persepcionesServiceBean.consultar(percepcion);		
			if(!rs.isEmpty()){
				percepcion=rs.get(0);
			}
			percepcion.setError(ConstantesBusiness.NO_ERRROR);
			System.out.println(".--. Agrego Percepcion::"+percepcion.getTxRemuneracion()+" ID "+percepcion.getCvePercepcion());
			
		}else{ percepcion.setError(resultadoValidaFolio);}
			
		
		return percepcion;
	}
	
	@RequestMapping(value="/consultar" , method=RequestMethod.POST)
	public @ResponseBody List<CrcPercepciones> consultar(@RequestBody CrcPercepciones percepcion, HttpServletResponse response) {
				
		List<CrcPercepciones> rs = this.persepcionesServiceBean.consultar(percepcion);		
		if(rs.size()>0){
			return rs;
		}
		return null;
	}
	
	@RequestMapping(value="/consultaPorClave")
	public @ResponseBody CrcPercepciones consultaPorClave(@RequestBody CrcPercepciones percepcion){
		System.out.println("CVE ID A BUSCAR:"+percepcion.getCvePercepcion());
		List<CrtSolicitudcorr> solicitud = new ArrayList<CrtSolicitudcorr>();
		if(percepcion.getCvePercepcion()>0){
			
			percepcion = (CrcPercepciones) persepcionesServiceBean.consultaPorClave(percepcion);
			
		}
		if(percepcion != null && percepcion.getCveSolicitudCorr() != null){
			solicitud = this.solicitudService.consultarSolicitudesById(percepcion.getCveSolicitudCorr());
		}
		if(solicitud != null && solicitud.size() > 0){
			percepcion.setFolioCorreccion(solicitud.get(0).getNuFolio());
		}
		
		
		return percepcion;
	}
	
	@RequestMapping(value="/paginar", method=RequestMethod.POST )
    public @ResponseBody DatosSalidaPaginador<CrcPercepciones> pagina(@RequestBody CrcPercepcionesWrapperDataTable aoData,HttpServletRequest request ) {
		System.out.println(".-.-controller public @ResponseBody DatosSalidaPaginador<CrcPercepciones> pagina(@RequestBody CrcPercepcionesWrapperDataTable aoData ) {");
		DatosEntradaPaginador send = new DatosEntradaPaginador();
		CrtSolicitudcorr solicitud = null;
		
		String resultadoValidaFolio = null;
		
		// Buscamos Solicitud por folio
		if(aoData.getoForm().getFolioCorreccion() != null){
			
			resultadoValidaFolio = validaSolicitudService.estadoActualSolicitud(aoData.getoForm().getFolioCorreccion());
			
			if(resultadoValidaFolio.equals(ConstantesBusiness.NO_ERRROR)){
				solicitud = new CrtSolicitudcorr();
				solicitud.setNuFolio(aoData.getoForm().getFolioCorreccion());
				solicitud = this.solicitudService.consultarFolio(solicitud);
			}
			
		}
		
		if(solicitud != null && solicitud.getCveSolicitudCorr() != null){
			aoData.getoForm().setCveSolicitudCorr(solicitud.getCveSolicitudCorr());
			aoData.getoForm().setFolioCorreccion("");
		}
		
		send.parserArray(aoData.getAoData());
		send.setModelo(aoData.getoForm());
		
		DatosSalidaPaginador<CrcPercepciones> reply = new DatosSalidaPaginador<CrcPercepciones>();
		if(aoData.getoForm().getTxRemuneracion() != null || aoData.getoForm().getCveSolicitudCorr() != null){
			reply =	this.persepcionesServiceBean.pagina(send);
			if(reply!=null && reply.getAaData()!=null && !reply.getAaData().isEmpty()){
				reply.getAaData().get(0).setCveStatusCorreccion(solicitud.getCveStatus());
				
			}
		}else{
			
			List<CrcPercepciones> lstPecepciones = new ArrayList<CrcPercepciones>();
			
			if(resultadoValidaFolio!=null) reply.setError(resultadoValidaFolio);			
			
			reply.setAaData(lstPecepciones);
			reply.setiTotalDisplayRecords(0);
			reply.setiTotalRecords(0);
		}
		System.out.println(".-.-controller realizo consulta) {");
        reply.setsEcho(send.getsEcho());
        
        return reply;
    }
	
}
