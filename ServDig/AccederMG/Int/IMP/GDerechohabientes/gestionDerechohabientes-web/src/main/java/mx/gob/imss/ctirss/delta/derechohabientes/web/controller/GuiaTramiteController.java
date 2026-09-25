
package mx.gob.imss.ctirss.delta.derechohabientes.web.controller;


import javax.servlet.http.HttpServletResponse;

import mx.gob.imss.ctirss.delta.derechohabientes.service.utility.interfaces.GuiaTramiteServiceRemote;
import mx.gob.imss.ctirss.delta.model.derechohabiente.negocio.Rol;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TipoTramite;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;



@Controller
@RequestMapping("/guiaTramite")
public class GuiaTramiteController {
	

	@Autowired
	GuiaTramiteServiceRemote guiaTramiteService;
	
	@RequestMapping(value = "/muestraGuiaTramite", method = RequestMethod.GET)
	public @ResponseBody String muestraGuiaTramite(@RequestParam(value="idRol",required=true)String idRol,
												@RequestParam(value="idTipoTramite",required=true)String idTipoTramite,
												HttpServletResponse sresponse
												) {
		
		
		byte[] res=null;
		Rol rol=new Rol();
		TipoTramite tipoTramite= new TipoTramite();
		rol.setIdRol(new Long(idRol));
		tipoTramite.setIdTipoTramite(new Integer(idTipoTramite));

		try {
				//llamar el servicio que me dara la ruta de la guia
				tipoTramite=guiaTramiteService.getRutaTramite(tipoTramite, rol);

				if(tipoTramite.getGuiaDetallada()==null){
					res=tipoTramite.getGuiaRapida();
				}else{
					res=tipoTramite.getGuiaDetallada();
				}

				sresponse.setContentType("application/pdf");
				
				sresponse.setContentLength(res.length);

				sresponse.setHeader("Content-Disposition","filename = guiaTramite.pdf");
				sresponse.getOutputStream().write(res);
				sresponse.getOutputStream().flush();
				sresponse.getOutputStream().close();
				
				
				
			}catch(Exception e) {
					e.printStackTrace();
			}
				
		
		return "welcome";
	}	
	
}
