package mx.gob.imss.cit.cda.web.controller;


import javax.servlet.http.HttpSession;

import mx.gob.imss.cit.cda.web.app.common.controller.AbstractController;
import mx.gob.imss.cit.cda.web.app.constants.RequestMappingConstants;
import mx.gob.imss.ctirss.delta.model.enums.EstadoTramiteEnum;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;


@Controller
@SessionAttributes(value = {"estadosTramite"})
@RequestMapping(value = RequestMappingConstants.CONSULTA_REPORTES)
public class ConsultarReportesController extends AbstractController {
	private final String VIEW_CONSULTA_REPORTES="consultarReportes";
	
	private static final String KEY_ESTADO = "estadosTramite";
	
	
	@RequestMapping(value = "")
	public String inicioCorreccionCurp(Model model, SessionStatus sessionStatus,HttpSession session) {		
		 model.addAttribute(KEY_ESTADO, EstadoTramiteEnum.values());
			
//			try {
//				delegaciones = this.componentComboService
//						.getOptions("mx.gob.imss.ctirss.delta.persistence.DicDelegacion");
//				subdelegaciones = this.componentComboService
//						.getOptions(
//								"mx.gob.imss.ctirss.delta.persistence.DicSubdelegacion");
//			} catch (TechnicalPersistenceException e) {
//				System.out.println("error");
////				this.log.error(e);
//			}
//			model.addAttribute("delegacionesAux", delegaciones);
//			model.addAttribute("subdelegacionesAux", subdelegaciones);
			model.addAttribute(KEY_ESTADO, EstadoTramiteEnum.values());
		return VIEW_CONSULTA_REPORTES;
	}
	
	@RequestMapping(value = "/consultarReportes", method = RequestMethod.GET)
	public Object consultarReportes(Model model, HttpSession session) {
		
		
		return VIEW_CONSULTA_REPORTES;
	}
	
	 @ModelAttribute("estadosTramite")
	    public EstadoTramiteEnum[] getEstado() {
	        return EstadoTramiteEnum.values();
	    }
	 
//	 @ModelAttribute("delegacionesAux")
//	    public List<SelectBean> getDelegacionesAux() {
//	        return this.componentComboService
//			.getOptions("mx.gob.imss.ctirss.delta.persistence.DicDelegacion");
//	    }
//    @ModelAttribute("subdelegacionesAux")
//    public List<SelectBean> getSubDelegacionesAux() {
//        return this.componentComboService
//		.getOptions(
//				"mx.gob.imss.ctirss.delta.persistence.DicSubdelegacion");
//    }

}
