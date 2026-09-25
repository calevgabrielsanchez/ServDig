package mx.gob.imss.ctirss.delta.gestion.individuo.web.controller;

import javax.servlet.http.HttpSession;

import mx.gob.imss.ctirss.delta.framework.base.controller.AbstractController;
import mx.gob.imss.ctirss.delta.gestion.individuo.service.business.IndividuoServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.model.domicilio.EntidadFederativa;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Fisica;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Moral;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Sexo;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.TipoPersona;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 
 * @author Hugo Martinez
 *
 */
@Controller
@RequestMapping(value = "/individuo")
public class IndividuoController extends AbstractController {
	
	@Autowired
	IndividuoServiceBusinessRemote individuoServiceBusiness;
	
	@RequestMapping(value="/detalleFisica", method=RequestMethod.GET)
    public String detalleFisica(Model model, HttpSession session) throws Exception {
		Fisica fisica = new Fisica();
		fisica.setSexo(new Sexo());
		fisica.setLugarNacimiento(new EntidadFederativa());
		fisica.setIdPersona(3L);
		fisica=individuoServiceBusiness.consultarDatosBasicosPersonaFisica(fisica);
		model.addAttribute(fisica);
		return "detalleIndividuo";
	}
	
	@RequestMapping(value="/detalleMoral", method=RequestMethod.GET)
    public String detalleMoral(Model model, HttpSession session) throws Exception {
		Moral moral = new Moral();
		moral.setIdPersona(72L);
		moral.setCveMoral(72L);
		moral=individuoServiceBusiness.consultarDatosBasicosPersonaMoral(moral);
		model.addAttribute(moral);
		return "detalleIndividuoMoral";
	}
	
	@RequestMapping(value="/individuoTest", method=RequestMethod.GET)
    public String individuoTest(Model model, HttpSession session) throws Exception {
		return "individuoTest";
	}
	
	@RequestMapping(value="/detalle", method=RequestMethod.GET)
    public String detalleIndividuo(Model model, HttpSession session, @RequestParam Long idPersona, 
    		@RequestParam Long idTipoPersona) throws Exception {
		System.err.println("Parametro idPersona: "+idPersona);
		boolean bFisica = idTipoPersona.equals(TipoPersona.TIPO_PERSONA_FISICA);
		model.addAttribute("bFisica", bFisica);
		model.addAttribute("idPersona", idPersona);
		if(idTipoPersona.equals(TipoPersona.TIPO_PERSONA_FISICA)){
			Fisica fisica = new Fisica();
			fisica.setSexo(new Sexo());
			fisica.setLugarNacimiento(new EntidadFederativa());
			fisica.setIdPersona(idPersona);
			fisica=individuoServiceBusiness.consultarDatosBasicosPersonaFisica(fisica);
			model.addAttribute(fisica);
			return "detalleIndividuo";
		}else if(idTipoPersona.equals(TipoPersona.TIPO_PERSONA_MORAL)){
			Moral moral = new Moral();
//			moral.setIdPersona(idPersona);
			moral.setCveMoral(idPersona);
			moral=individuoServiceBusiness.consultarDatosBasicosPersonaMoral(moral);
			model.addAttribute(moral);
			return "detalleIndividuoMoral";
		}
		
		return "individuoTest";
	}
}
