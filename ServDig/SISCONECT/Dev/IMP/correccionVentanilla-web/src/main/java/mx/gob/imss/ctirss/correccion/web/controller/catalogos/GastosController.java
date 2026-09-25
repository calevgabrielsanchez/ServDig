
package mx.gob.imss.ctirss.correccion.web.controller.catalogos;


import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;


import mx.gob.imss.ctirss.correccion.base.paginador.model.DatosEntradaPaginador;
import mx.gob.imss.ctirss.correccion.base.paginador.model.DatosSalidaPaginador;

import mx.gob.imss.ctirss.correccion.catalogos.base.paginador.model.CrcGastosWrapperDataTable;
import mx.gob.imss.ctirss.correccion.catalogos.model.CrcGastos;
import mx.gob.imss.ctirss.correccion.catalogos.service.interfaces.GastosService;
import mx.gob.imss.ctirss.correccion.correccion.service.interfaces.SolicitudService;
import mx.gob.imss.ctirss.correccion.framework.base.controller.AbstractController;
import mx.gob.imss.ctirss.correccion.framework.utils.ConstantesBusiness;
import mx.gob.imss.ctirss.correccion.model.CrtSolicitudcorr;
import mx.gob.imss.ctirss.correccion.session.UserSession;
import mx.gob.imss.ctirss.correccion.utils.Functions;
import mx.gob.imss.ctirss.correccion.web.controller.utils.ValidaSolicitudCorreccion;

@Controller
@RequestMapping(value="/catalogo/gastos")
public class GastosController extends AbstractController{
	
	@Autowired
	private GastosService<CrcGastos> gastosServiceBean;
	@Autowired
	private SolicitudService solicitudService;
	@Autowired
	private ValidaSolicitudCorreccion validaSolicitudService;
	
	@RequestMapping(method=RequestMethod.GET)
	public String getCreateForm(Model model,HttpServletRequest request) {
		CrcGastos crcGastos = new CrcGastos();
		UserSession user = getUsuarioFirmado(request);
		crcGastos.setCveSubdelegcionOficial(Functions.getCveSubdelegacionOficial(user));
		model.addAttribute(crcGastos);
		return determinaURL(request, "catalogos/gastos/gastosMain", "catalogos/gastos/gastosMain/patron");
	}
	
	@RequestMapping(value="/modificar" , method=RequestMethod.POST)
	public @ResponseBody CrcGastos modify(@RequestBody CrcGastos gasto, HttpServletResponse response) {
		System.out.println(".--. EN MODIFICAR");
		
		String resultadoValidaFolio = validaSolicitudService.estadoActualSolicitud(gasto.getFolioCorreccion());
		
		if(resultadoValidaFolio.equals("")){
			this.gastosServiceBean.modificar(gasto);
			System.out.println(".--. MODIFICO");
		}else{
			
			gasto.setError(resultadoValidaFolio);
		}
		
		return gasto;
	}
	
	@RequestMapping(value="/eliminar" , method=RequestMethod.POST)
	public @ResponseBody CrcGastos delete(@RequestBody CrcGastos gasto, HttpServletResponse response) {
		System.out.println(".--. EN eliminar");
		
		String resultadoValidaFolio = ConstantesBusiness.NO_ERRROR;
		
		if(resultadoValidaFolio.equals("")){
			gasto = this.gastosServiceBean.eliminar(gasto);
			if(gasto.getError().equalsIgnoreCase("")) System.out.println("ELIMINO");
		}else{
			gasto.setError(resultadoValidaFolio);
		}
		
		return gasto;
	}
	
	@RequestMapping(value="/agregar" , method=RequestMethod.POST)
	public @ResponseBody CrcGastos create(@RequestBody CrcGastos gasto, HttpServletResponse response) {

		CrtSolicitudcorr solicitud = new CrtSolicitudcorr();
		if(gasto != null && gasto.getFolioCorreccion() != null){
			solicitud.setNuFolio(gasto.getFolioCorreccion());
			solicitud = this.solicitudService.consultarFolio(solicitud);
		}
		if(solicitud != null && solicitud.getCveSolicitudCorr() != null){
			gasto.setCveSolicitudCorr(solicitud.getCveSolicitudCorr());
		}else{
			return null;
		}
			
		String resultadoValidaFolio = validaSolicitudService.estadoActualSolicitud(gasto.getFolioCorreccion());
		
		if(resultadoValidaFolio.equals("")){
			this.gastosServiceBean.agregar(gasto);
			System.out.println("Genero Gasto");
			gasto.setError(ConstantesBusiness.NO_ERRROR);
		}else{
			gasto.setError(resultadoValidaFolio);
		}
				
		return gasto;
	}
	
	@RequestMapping(value="/consultar" , method=RequestMethod.POST)
	public @ResponseBody List<CrcGastos> consultar(@RequestBody CrcGastos gasto, HttpServletResponse response) {
				
		List<CrcGastos> rs = this.gastosServiceBean.consultar(gasto);		
		if(rs.size()>0){
			return rs;
		}
		return null;
	}
	
	@RequestMapping(value="/consultaPorClave")
	public @ResponseBody CrcGastos consultaPorClave(@RequestBody CrcGastos gasto){
		System.out.println("CVE ID A BUSCAR:"+gasto.getCveGasto());
		List<CrtSolicitudcorr> solicitud = new ArrayList<CrtSolicitudcorr>();
		if(gasto.getCveGasto()>0){
			
			gasto = (CrcGastos) gastosServiceBean.consultaPorClave(gasto);
			
		}
		if(gasto != null && gasto.getCveSolicitudCorr() != null){
			solicitud = this.solicitudService.consultarSolicitudesById(gasto.getCveSolicitudCorr());
		}
		if(solicitud != null && solicitud.size() > 0){
			gasto.setFolioCorreccion(solicitud.get(0).getNuFolio());
		}
		
		return gasto;
	}
	
	@RequestMapping(value="/paginar", method=RequestMethod.POST )
    public @ResponseBody DatosSalidaPaginador<CrcGastos> pagina(@RequestBody CrcGastosWrapperDataTable aoData ) {
		System.out.println(".-.-controller public @ResponseBody DatosSalidaPaginador<CrcGastos> pagina(@RequestBody CrcGastosWrapperDataTable aoData ) {");
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
		
		DatosSalidaPaginador<CrcGastos> reply = new DatosSalidaPaginador<CrcGastos>();
		if(aoData.getoForm().getCveSolicitudCorr() != null || aoData.getoForm().getTxGasto() != null){
			
			reply = this.gastosServiceBean.pagina(send);
			
			if(reply!=null && reply.getAaData()!=null && !reply.getAaData().isEmpty()){
				reply.getAaData().get(0).setCveStatusCorreccion(solicitud.getCveStatus());
				
			}
			
		}else{
			List<CrcGastos> lstGastos = new ArrayList<CrcGastos>();
			
			if(resultadoValidaFolio!=null) reply.setError(resultadoValidaFolio);
			
			reply.setAaData(lstGastos);
			reply.setiTotalDisplayRecords(0);
			reply.setiTotalRecords(0);
		}
				
		
		System.out.println(".-.-controller realizo consulta) {");
        reply.setsEcho(send.getsEcho());
        
        return reply;
    }
	
	@RequestMapping(value="/validar", method=RequestMethod.POST )
    public @ResponseBody DatosSalidaPaginador<CrcGastos> valida(@RequestBody CrcGastosWrapperDataTable aoData ) {
		System.out.println(".-.-controller public @ResponseBody DatosSalidaPaginador<CrcGastos> pagina(@RequestBody CrcGastosWrapperDataTable aoData ) {");
		DatosEntradaPaginador send = new DatosEntradaPaginador();
		
		
		
		send.parserArray(aoData.getAoData());
		send.setModelo(aoData.getoForm());
		
		DatosSalidaPaginador<CrcGastos> reply = this.gastosServiceBean.pagina(send);
		System.out.println(".-.-controller realizo consulta) {");
        reply.setsEcho(send.getsEcho());
        
        return reply;
    }
	
	
}
