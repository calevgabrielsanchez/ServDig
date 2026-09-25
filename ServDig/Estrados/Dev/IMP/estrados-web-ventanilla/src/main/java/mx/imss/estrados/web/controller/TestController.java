package mx.imss.estrados.web.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import mx.imss.estrados.dto.DiasInhabilDTO;
import mx.imss.estrados.service.interfaces.RegistroNotificacionServiceRemote;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value="/estrados")
public class TestController {
	
	@Autowired
	RegistroNotificacionServiceRemote registroNotificacionServiceB;
	
	@RequestMapping(value="/nueva")
	public String nuevaDenuncia(HttpServletResponse response, HttpServletRequest request, HttpSession ses) {
//		List<DiasInhabilDTO> lisDiasInhabilDTOs;
//		lisDiasInhabilDTOs = registroNotificacionServiceB.consultaDiasInhabiles();
//		request.setAttribute("lisDiasInhabilDTOs", lisDiasInhabilDTOs);
		return "TestConsulta";
	}
	
	@RequestMapping(value="/recuperaDatos")
	public @ResponseBody List<DiasInhabilDTO> recuperaDatos(HttpServletResponse response, HttpServletRequest request, HttpSession ses) {
//		List<DiasInhabilDTO> lisDiasInhabilDTOs;
//		lisDiasInhabilDTOs = registroNotificacionServiceB.consultaDiasInhabiles();
//		request.setAttribute("lisDiasInhabilDTOs", lisDiasInhabilDTOs);
//		return lisDiasInhabilDTOs;
		return null;
	}
	
}
