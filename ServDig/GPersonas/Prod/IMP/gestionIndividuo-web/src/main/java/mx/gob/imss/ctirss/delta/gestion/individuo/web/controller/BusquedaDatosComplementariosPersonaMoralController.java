package mx.gob.imss.ctirss.delta.gestion.individuo.web.controller;

import java.util.ArrayList;
import java.util.List;

import mx.gob.imss.ctirss.delta.framework.base.controller.AbstractController;
import mx.gob.imss.ctirss.delta.model.domicilio.Domicilio;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Moral;
import mx.gob.imss.ctirss.delta.model.gestion.medio.contacto.CorreoElectronico;
import mx.gob.imss.ctirss.delta.model.gestion.medio.contacto.MedioContacto;
import mx.gob.imss.ctirss.delta.model.gestion.medio.contacto.TelefonoFijo;
import mx.gob.imss.ctirss.delta.model.gestion.medio.contacto.TelefonoMovil;
import mx.gob.imss.ctirss.gestionpersonas.servicios.business.PersonaMoralBusinessRemote;

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
@RequestMapping(value = "/persona/moral")
public class BusquedaDatosComplementariosPersonaMoralController extends AbstractController {
    
    @Autowired  
    private PersonaMoralBusinessRemote personaMoralBusiness;
    
    @RequestMapping(value = "datos-complementarios/busqueda/{idPersona}", method = RequestMethod.GET)
    public String buscarDatosComplementarios(final @PathVariable Long idPersona, final Model modelo) {
        
        Moral personaMoralResultado = new Moral();
        String mensaje;
        
        try{
            
            log.debug("Se mostrarán los datos complementarios: documento probatorio, domicilios y medios de contacto de la persona con ID: " + idPersona);
            
            personaMoralResultado = personaMoralBusiness.getDatosComplementariosPersonaMoral(idPersona);
            
            List<MedioContacto> mediosContacto;
            if(personaMoralResultado != null){
            	this.log.debug("Domicilios : " + personaMoralResultado.getDomicilios());
            	mediosContacto = personaMoralResultado.getMediosContacto();
            }else{
            	this.log.warn(" No tiene domicilios");
            	mediosContacto = null;
            }
            
            if(mediosContacto == null || mediosContacto.isEmpty()){
            	mensaje = "La persona con id " + idPersona +" no tiene asociados Medios de Contacto";
            }else{
            	
            	List<MedioContacto> mList = new ArrayList<MedioContacto>();
            	mList.addAll(mediosContacto);
                mediosContacto.clear();
            	
            	 for(MedioContacto medioContacto : mList){
                     if(medioContacto instanceof TelefonoFijo){
                         personaMoralResultado.setTelefonoFijo((TelefonoFijo) medioContacto);
                     }
                     if(medioContacto instanceof TelefonoMovil){
                         personaMoralResultado.setTelefonoMovil((TelefonoMovil) medioContacto);
                     }
                     if(medioContacto instanceof CorreoElectronico){
                         personaMoralResultado.setCorreoElectronico((CorreoElectronico) medioContacto);
                     }
                 }
            }
                       
            mensaje = "Los datos complementarios de la persona con Identificador " + idPersona + " son los siguientes: ";
            if(personaMoralResultado.getDomicilios() != null && personaMoralResultado.getDomicilios().size() > 0){
            	//no hacer nada por el momento
            	log.debug("Persona sin domicilio");
            }else{
                mensaje = "La persona con id: " + idPersona +" no tiene asociados Domicilios";
                personaMoralResultado.setDomicilios(new ArrayList<Domicilio>());
            }
            
        }catch(Exception e){
            mensaje = "Error: " + e.getMessage();
        }
        
        modelo.addAttribute("personaMoral", personaMoralResultado);
        
        this.log.debug("Persona Moral:" + personaMoralResultado);
        modelo.addAttribute("mensaje", mensaje);
        
        return "busquedaDatosComplementariosPersonaMoral";
        
    }

}