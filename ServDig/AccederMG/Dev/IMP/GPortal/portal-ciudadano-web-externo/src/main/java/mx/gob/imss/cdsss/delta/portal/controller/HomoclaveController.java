package mx.gob.imss.cdsss.delta.portal.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import mx.gob.imss.ctirss.delta.framework.base.controller.AbstractController;
import mx.gob.imss.ctirss.delta.gestion.solicitud.service.interfaces.SolicitudBusinessRemote;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.Solicitud;

@Controller
@RequestMapping("/getHomoclave")
public class HomoclaveController extends AbstractController {
	
	@Autowired
	private SolicitudBusinessRemote solicitudBusinessRemote;
	
	@RequestMapping("/byFolio")
	public @ResponseBody Map<String,Object> getHomoclaveByFolio(@RequestBody Solicitud solicitud, HttpServletRequest request){
		
		Map<String, Object> result = new HashMap<String, Object>();
		String homoclave = null;
		
		homoclave = solicitudBusinessRemote.getHomoclaveSolicitud(solicitud.getNoFolioSolicitud());
		
		result.put("homoclave", homoclave);
		return result;
	}
	
	@RequestMapping("/byId")
	public @ResponseBody Map<String,Object> getHomoclaveById(@RequestBody Solicitud solicitud, HttpServletRequest request){
		
		Map<String, Object> result = new HashMap<String, Object>();
		String homoclave = null;
		
		homoclave = solicitudBusinessRemote.getHomoclaveSolicitud(solicitud.getSolicitudId());
		
		result.put("homoclave", homoclave);
		return result;
	}
}
