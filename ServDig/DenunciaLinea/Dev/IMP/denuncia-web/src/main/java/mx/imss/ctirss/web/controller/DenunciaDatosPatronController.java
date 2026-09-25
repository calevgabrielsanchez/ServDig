package mx.imss.ctirss.web.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import javax.ws.rs.Consumes;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;

import mx.imss.ctirss.framework.base.controller.AbstractController;
import mx.imss.ctirss.framework.base.model.AbstractModel;
import mx.imss.ctirss.login.service.interfaces.IDenunciaService;
import mx.imss.ctirss.model.DltDatospatron;
import mx.imss.ctirss.model.DltDenuncia;
import mx.imss.ctirss.web.bean.DenunciaDTO;

@Controller
@RequestMapping(value="/denunciaLinea/datosPatron")
@SessionAttributes("denunciaDTO")
public class DenunciaDatosPatronController extends AbstractController {
	/**
	 * Logger.
	 */
	private final static Logger logger = Logger.getLogger(DenunciaDatosPatronController.class); 
	
	private static final String SESSION_BEAN=DenunciaDTO.SES_NAME;
	
	@Autowired
	private IDenunciaService<AbstractModel> denunciaServiceBean;
			
	
	@RequestMapping(method=RequestMethod.GET)
	public ModelAndView getCreateForm(Model model, HttpServletRequest request, HttpSession ses) {
		DenunciaDTO denunciaDTO = null;
		DenunciaDTO denunciaDTOSes = (DenunciaDTO)request.getSession().getAttribute("denunciaDTO");
		 if(denunciaDTOSes != null){
			 denunciaDTO = denunciaDTOSes;
			 ses.setAttribute(SESSION_BEAN, denunciaDTOSes);			 			 
		 }else{
			 denunciaDTO = new DenunciaDTO();
			 ses.setAttribute(SESSION_BEAN, denunciaDTO);
		 }
		
		 ModelAndView mw = new ModelAndView( "denuncia/datosPatronMain");
		 mw.addObject("denunciaDTO", denunciaDTO);
		 return mw; 
	}
	
	
	@RequestMapping(value="/guardarDatosPatron", method=RequestMethod.POST)
	public @ResponseBody DenunciaDTO submit( @RequestBody List<Object> patrones, @Valid DenunciaDTO denunciaDTO, BindingResult denunciaDTOBr,SessionStatus status, HttpServletRequest request)
	{
		System.out.println("entra a guardar patrones");
		//DenunciaDTO denunciaDTO = (DenunciaDTO)request.getSession().getAttribute("denunciaDTO");
		DltDenuncia denuncia = denunciaDTO.getDltDenuncia();
		logger.debug("ingresa a guardar datos patron");		
		if(patrones != null && patrones.size() > 1){
			denunciaDTO.setPatrones(patrones);	
		}
		
		try{
			if(denunciaDTO.getPatrones() != null && denunciaDTO.getPatrones().size() > 1){
				
				//denuncia = denunciaServiceBean.generaDenunciaPatronComplemento(denunciaDTO.getDltDenuncia(), denunciaDTO.getPatrones());
			}
			if(denunciaDTO.getPatrones() != null && denunciaDTO.getPatrones().size() == 1){							
			  denunciaDTO.getDltPatron().setDltDenuncia(denunciaDTO.getDltDenuncia());
			  denunciaDTO.getDltPatron().setIdPatronprincipal(new BigDecimal(1));
			  denunciaDTO.getDltPatron().setFecFechareg(new Date());			  
			  denunciaDTO.getDltDenuncia().setDltDatospatrons(new HashSet<DltDatospatron>());
			  denunciaDTO.getDltDenuncia().getDltDatospatrons().add(denunciaDTO.getDltPatron());
			  denuncia = denunciaServiceBean.saveDenuncia(denunciaDTO.getDltDenuncia(), null);
			}
		}catch(Exception e){
			e.printStackTrace();
		}			
		
		denunciaDTO.setDltDenuncia(denuncia);

		return denunciaDTO;
	}
	
	@RequestMapping(value="/agregaPatrones", method=RequestMethod.POST)
	//public void agregaPatrones(@RequestBody List<DltDatospatron> patronesCmpl, @ModelAttribute("denunciaDTO") DenunciaDTO denunciaDTO, 
    //        BindingResult denunciaDTOBr,SessionStatus status, HttpServletRequest request){
	public @ResponseBody DenunciaDTO agregaPatrones(@RequestBody List<Object> patrones,@ModelAttribute("denunciaDTO") DenunciaDTO denunciaDTO,
			                                                        BindingResult denunciaDTOBr, HttpServletRequest request,HttpServletResponse response){		
	
		if(patrones != null && patrones.size() > 1){
			denunciaDTO.setPatrones(patrones);	
		}// si esto está ok, le agrego los patrones al dto, y al confirmar mando el submit..
		return denunciaDTO;		
	}

	@RequestMapping(value="/datosTrabajo", method=RequestMethod.POST)
	public ModelAndView sendDatosTrabajo(@ModelAttribute("denunciaDTO") DenunciaDTO denunciaDTO, 
		               BindingResult denunciaDTOBr,SessionStatus status, HttpServletRequest request){
		ModelAndView mw = new ModelAndView("denuncia/datosTrabajoMain");
		mw.addObject("denunciaDTO", denunciaDTO);
		return mw;
	}
	
}
