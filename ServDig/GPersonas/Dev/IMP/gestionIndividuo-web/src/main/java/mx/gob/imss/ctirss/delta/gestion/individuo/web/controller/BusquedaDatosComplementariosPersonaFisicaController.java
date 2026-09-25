package mx.gob.imss.ctirss.delta.gestion.individuo.web.controller;

import java.util.ArrayList;
import java.util.List;

import mx.gob.imss.ctirss.delta.framework.base.controller.AbstractController;
import mx.gob.imss.ctirss.delta.model.domicilio.Domicilio;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Fisica;
import mx.gob.imss.ctirss.delta.model.gestion.medio.contacto.CorreoElectronico;
import mx.gob.imss.ctirss.delta.model.gestion.medio.contacto.MedioContacto;
import mx.gob.imss.ctirss.delta.model.gestion.medio.contacto.TelefonoFijo;
import mx.gob.imss.ctirss.delta.model.gestion.medio.contacto.TelefonoMovil;
import mx.gob.imss.ctirss.gestionpersonas.servicios.business.PersonaBusinessRemote;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author Samuel Rodriguez Grajeda
 * @company IMSS (Instituto Mexicano del Seguro Social)
 * @date 08/05/2012
 */

@Controller
@RequestMapping(value = "/persona/fisica")
public class BusquedaDatosComplementariosPersonaFisicaController extends AbstractController {
    
    @Autowired  
    private PersonaBusinessRemote personaBusiness;
    
    @RequestMapping(value = "datos-complementarios/busqueda/{idPersona}", method = RequestMethod.GET)
    public String buscarDatosComplementarios(final @PathVariable Long idPersona, final Model modelo) {
        
        Fisica personaFisicaResultado = new Fisica();
        String mensaje;
        
        personaFisicaResultado = personaBusiness.getDatosComplementariosPersonaFisica(idPersona);
        
        List<MedioContacto> mediosContacto = personaFisicaResultado.getMediosContacto();
        
        if(mediosContacto == null || mediosContacto.isEmpty()){
        	mensaje = "La persona con id " + idPersona +" no tiene asociados Medios de Contacto";
        }else{
        	
        	List<MedioContacto> mList = new ArrayList<MedioContacto>();
        	mList.addAll(mediosContacto);
            mediosContacto.clear();
        	
        	 for(MedioContacto medioContacto : mList){
                 if(medioContacto instanceof TelefonoFijo){
                     personaFisicaResultado.setTelefonoFijo((TelefonoFijo) medioContacto);
                 }
                 if(medioContacto instanceof TelefonoMovil){
                     personaFisicaResultado.setTelefonoMovil((TelefonoMovil) medioContacto);
                 }
                 if(medioContacto instanceof CorreoElectronico){
                     personaFisicaResultado.setCorreoElectronico((CorreoElectronico) medioContacto);
                 }
             }
        }
        
        if(personaFisicaResultado.getDocumentosProbatorios() == null || personaFisicaResultado.getDocumentosProbatorios().isEmpty()){
        	this.log.debug("La persona no tiene documentos comprobatorios");
        	mensaje = "La persona con id " + idPersona +" no tiene asociados Documentos Probatorios";
        }
        
        mensaje = "Los datos complementarios de la persona con Identificador " + idPersona + " son los siguientes: ";
        
        if(personaFisicaResultado.getDomicilios() != null && personaFisicaResultado.getDomicilios().size() > 0){
        	log.debug("Persona sin domicilio");
        } else {
            mensaje = "La persona con id: " + idPersona +" no tiene asociados Domicilios";
            personaFisicaResultado.setDomicilios(new ArrayList<Domicilio>());
        }
  
        modelo.addAttribute("isBusquedaPersonaFisica", true);
        modelo.addAttribute("fisica", personaFisicaResultado); 
        modelo.addAttribute("mensaje", mensaje);
        
        return "busquedaDatosComplementariosPersonaFisica";
    }

}