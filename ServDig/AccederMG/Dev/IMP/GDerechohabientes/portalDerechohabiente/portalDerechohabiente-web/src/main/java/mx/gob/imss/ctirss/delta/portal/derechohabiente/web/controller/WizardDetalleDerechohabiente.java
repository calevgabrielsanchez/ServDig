package mx.gob.imss.ctirss.delta.portal.derechohabiente.web.controller;

import javax.servlet.http.HttpServletRequest;

import mx.gob.imss.ctirss.delta.derechohabientes.service.utility.interfaces.DerechohabienteServiceRemote;
import mx.gob.imss.ctirss.delta.derechohabientes.service.utility.interfaces.GrupoFamiliarServiceRemote;
import mx.gob.imss.ctirss.delta.exception.derechohabiente.DerechohabientesBusinessException;
import mx.gob.imss.ctirss.delta.exception.derechohabiente.ExceptionMessages;
import mx.gob.imss.ctirss.delta.framework.base.controller.AbstractController;
import mx.gob.imss.ctirss.delta.model.derechohabiente.CabezaGrupoFamiliar;
import mx.gob.imss.ctirss.delta.model.derechohabiente.GrupoFamiliar;

import org.bouncycastle.crypto.engines.ISAACEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/wizard/derechohabiente/")
public class WizardDetalleDerechohabiente extends AbstractController {

	@Autowired 
	private GrupoFamiliarServiceRemote grupoFamiliarServiceRemote;
	@Autowired
	private DerechohabienteServiceRemote derechohabienteServiceRemote;
	
	@RequestMapping(value = "/detalle/{nss}/{idIntegrante}/{idAsignacionNss}")
	public String wizardDetalleDerechohabiente(Model model, HttpServletRequest request, @PathVariable String nss, @PathVariable Long idIntegrante,
			@PathVariable Long idAsignacionNss) {
		CabezaGrupoFamiliar cabezaGrupoFamiliar = null;
		
		try {
			GrupoFamiliar derechohabiente = derechohabienteServiceRemote.detalleDerechohabienteGrupoFamiliar(idAsignacionNss, idIntegrante);
			cabezaGrupoFamiliar = grupoFamiliarServiceRemote.cabezaGrupoFamiliar(idAsignacionNss);
			//Agregamos al modelo los datos del derechohabiente y el domicilio particular
			model.addAttribute("derechohabiente", derechohabiente);
			model.addAttribute("patronImss", cabezaGrupoFamiliar.getPatronImss().equals(1));
			model.addAttribute("isAsegurado",false);
		}
		catch(DerechohabientesBusinessException e) {
			log.error("ocurrio un erro de derechohabientes", e);
			request.setAttribute("errores", "label.excepcion.vigencia.derechohabiente");
			
		}catch (Exception e){
			log.error("ocurrio un erro no cachado", e);
			request.setAttribute("errores", "label.excepcion.vigencia.derechohabiente");	
		}
		
		return "wizardDetalleDerechohabienteContenido";
	}
}
