package mx.gob.imss.ctirss.delta.gestion.individuo.web.controller;

import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;

import mx.gob.imss.ctirss.delta.framework.base.controller.AbstractController;
import mx.gob.imss.ctirss.delta.framework.exceptions.ClienteWebserviceRenapoCurpException;
import mx.gob.imss.ctirss.delta.framework.exceptions.ClienteWebserviceSatRfcException;
import mx.gob.imss.ctirss.delta.gestion.individuo.web.validator.PersonaFisicaRegistro2Validator;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Calificacion;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.CalificacionEnum;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Fisica;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.PersonaCalificacion;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.vo.CalificacionPersona;
import mx.gob.imss.ctirss.gestionpersonas.servicios.publicos.ServiciosPersonaBusinessRemote;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author Samuel Rodriguez Grajeda
 * @company IMSS (Instituto Mexicano del Seguro Social)
 * @date 01/06/2012
 */
@Controller
@RequestMapping(value = "/persona/fisica")
public class RegistroPersonaFisica2Controller extends AbstractController {

    @Autowired
    private transient ServiciosPersonaBusinessRemote serviciosPersonaBusiness;
    
    
    @RequestMapping(value = "/registro2Popup", method = RequestMethod.GET)
    public String getPersonaPopup(final Model model) {
        model.addAttribute("fisica", new Fisica());
        model.addAttribute("titulo", "Registro de Persona F\u00edsica - Paso 1 de 2");
        return "registroPersonaFisica";
    }

    @RequestMapping(value = "/busqueda-unificada", method = RequestMethod.POST)
    public String localizarPersonaFisica(final @ModelAttribute Fisica oForm, final BindingResult result, final Model model, final HttpSession session) {
        String vista = "";
        List<Fisica> personasFisicas;
        log.debug("FORMULARIO DE ENTRADA: " + oForm);
        ServletContext context = session.getServletContext();
        String rolVentanilla = context.getInitParameter("rolVentanilla");
        String[] arrayRolVentanilla = {rolVentanilla};
        String rolInternet = context.getInitParameter("rolInternet");
        String[] arrayRolInternet = {rolInternet};
        
        
        // Se valida que los campos del formulario
        new PersonaFisicaRegistro2Validator().validate(oForm, result);
        
        if (result.hasErrors()) {
            log.debug("ERRORES DE VALIDACION:\n " + result);
            vista = "registroPersonaFisica";
        } else {
        	StringBuffer entidades = new StringBuffer("La persona no fue hallada en el IMSS");
            final String role = (String) session.getAttribute("role");
            log.trace("role: " + role);
            
            // No podemos tener propiedades con cadenas vacias porque sino el backend hace pndjds */
            Fisica pf = nullearCampos(oForm);
            
            try{
                personasFisicas = serviciosPersonaBusiness.localizarPersonaFisica(pf.getCurp(), pf.getRfc(), pf.getNombre(), pf.getPrimerApellido(), pf.getSegundoApellido(), pf.getFechaNacimiento(), pf.getLugarNacimiento(), pf.getSexo());
                
                if(personasFisicas != null){
                	if(personasFisicas.size() > 0){
                		Fisica personaFisica = personasFisicas.get(0);
                		if(personaFisica.getIdPersona() != null){
                	        model.addAttribute("personaFisicaLst", personasFisicas);
                	        model.addAttribute("mensaje", "Se hallaron " + personasFisicas.size() + " registros en el IMSS");
                			vista = "registroPersonaFisicaIMSSResultado";
                		}else{
                			
                			if(personaFisica.getPersonaCalificaciones().size() == 2){
//                				if(personaFisica.getPersonaCalificaciones().get(0).getCalificacion().getIdCalificacion().equals(CalificacionEnum.VALIDADO_SAT.getCodigo().longValue()) && personaFisica.getPersonaCalificaciones().get(1).getCalificacion().getIdCalificacion().equals(CalificacionEnum.VALIDADO_RENAPO.getCodigo().longValue())){
                					model.addAttribute("mensaje", "La persona fue localizada en el SAT y en RENAPO");
//                				}
                			}else if(personaFisica.getPersonaCalificaciones().size() == 1){
	                			if(personaFisica.getPersonaCalificaciones().get(0).getCalificacion().getIdCalificacion().equals(CalificacionEnum.VALIDADO_RENAPO.getCodigo().longValue()) ){
	                				model.addAttribute("mensaje", "La persona fue localizada en el RENAPO");
	                			}else if(personaFisica.getPersonaCalificaciones().get(0).getCalificacion().getIdCalificacion().equals(CalificacionEnum.VALIDADO_SAT.getCodigo().longValue()) ){
	                				model.addAttribute("mensaje", "La persona fue localizada en el SAT");
	                			}
                			}
                			
                			// Ahora se verificara si la persona tiene mas de 1 calificacion, por ejemplo cuando se busca por SAT y regresa una CURP entonces se busca
                		    // nuevamente en RENAPO con esa CURP. En caso de haber 2 objetos en la lista PersonaCalificacion, se juntaran las descripciones para meterlas
                			// en la descripcion del elemento 0 de la lista
//                			if(personaFisica.getPersonaCalificaciones().size() > 1){
//                				StringBuffer calificaciones = new StringBuffer("");
//                				for(PersonaCalificacion c : personaFisica.getPersonaCalificaciones()){
//                					calificaciones.append(c.getCalificacion().getDescripcion());
//                					calificaciones.append(", ");
//                				}
//                				
//                				String cadenaSinComaFinal = "";
//                				
//                		        try{
//                		        	int fin = calificaciones.lastIndexOf(",");
//                		        	cadenaSinComaFinal = calificaciones.substring(0, fin);
//                		        }catch(Exception e){
//                		        	cadenaSinComaFinal = "Sin calificaci\u00f3";
//                		        }
//                		        
//                		        personaFisica.getPersonaCalificaciones().get(0).getCalificacion().setDescripcion(cadenaSinComaFinal);
//                		        
//                			}

                			model.addAttribute("fisica", personaFisica);
                			model.addAttribute("isBusquedaPersonaFisica", false);
                			vista = "registroPersonaFisicaCaptura";
                		}
                	}else{
                		entidades.append(validarLugaresDeBusqueda(pf));
                		
                		log.info(entidades.toString() + " (lista 'personasFisicas' con tamaño igual a 0)");
                		vista = "registroPersonaFisicaCaptura";
                		model.addAttribute("mensaje", entidades.toString());
//                		model.addAttribute("mensajeError", MENSAJE_ERROR_BUSQUEDAS.replaceAll("<PERSONA>", " ").replace("<ENTIDAD_EXTERNA>", "el RENAPO o el SAT"));
                	}
                }else{
            		log.info(entidades.toString() + " (lista 'personasFisicas' es nula)");
            		vista = "registroPersonaFisicaCaptura";
            		
            		Fisica personaFisica = new Fisica();
            		
            		BeanUtils.copyProperties(oForm, personaFisica);
            		
                    PersonaCalificacion personaCalificacion = new PersonaCalificacion();
                    personaCalificacion.setCalificacion(new Calificacion());
                    personaFisica.getPersonaCalificaciones().add(personaCalificacion);
                    //TODO obtener variable del WEB.XML
                    
                    if(checkGrantedAuthorities(arrayRolVentanilla)){
                    	personaFisica.getPersonaCalificaciones().get(0).getCalificacion().setDescripcion(CalificacionPersona.CALIFICACION_3_VALIDADO_IMSS);
                    	personaFisica.getPersonaCalificaciones().get(0).getCalificacion().setIdCalificacion(CalificacionPersona.VALIDADO_IMSS.longValue());
                    }else if(checkGrantedAuthorities(arrayRolInternet)){
                    	personaFisica.getPersonaCalificaciones().get(0).getCalificacion().setDescripcion(CalificacionPersona.CALIFICACION_4_NO_VALIDADO);
                    	personaFisica.getPersonaCalificaciones().get(0).getCalificacion().setIdCalificacion(CalificacionPersona.NO_VALIDADO.longValue());                	
                    }

                    entidades.append(validarLugaresDeBusqueda(pf));
                    
            		model.addAttribute("fisica", personaFisica);
            		model.addAttribute("mensaje", entidades.toString());
//            		model.addAttribute("mensajeError", MENSAJE_ERROR_BUSQUEDAS.replaceAll("<PERSONA>", " ").replace("<ENTIDAD_EXTERNA>", "el RENAPO o el SAT"));
                }            	
            }
            catch(ClienteWebserviceSatRfcException wsrfc){
    			vista = "mensajes";
        		model.addAttribute("mensaje", "No se pudo recuperar la informaci\u00f3n del SAT debido a problemas en el webservice");
            }
            catch(ClienteWebserviceRenapoCurpException wscurp){
    			vista = "mensajes";
        		model.addAttribute("mensaje", "No se pudo recuperar la informaci\u00f3n del RENAPO debido a problemas en el webservice");
            }
        }
        model.addAttribute("botonOprimido", "unificada");
        log.info("vista a mostrar: " + vista);
        return vista;
    }
    
    /**
     * Con este metodo evitamos que se pasen al backend propiedades que vengan oomo cadenas vacias. Para un funcionamiento correcto de las consultas, las propiedades deben
     * ser NULAS o NO NULAS (pero NUNCA CADENAS VACIAS)
     * @param oForm
     * @return
     */
    public Fisica nullearCampos(Fisica oForm){
    	
    	Fisica personaFisica = new Fisica();
    	
    	if(StringUtils.isWhitespace(oForm.getCurp())){personaFisica.setCurp(null);}else{personaFisica.setCurp(oForm.getCurp());}
    	if(StringUtils.isWhitespace(oForm.getRfc())){personaFisica.setRfc(null);}else{personaFisica.setRfc(oForm.getRfc());}
    	if(StringUtils.isWhitespace(oForm.getNombre())){personaFisica.setNombre(null);}else{personaFisica.setNombre(oForm.getNombre());}
    	if(StringUtils.isWhitespace(oForm.getPrimerApellido())){personaFisica.setPrimerApellido(null);}else{personaFisica.setPrimerApellido(oForm.getPrimerApellido());}
    	if(StringUtils.isWhitespace(oForm.getSegundoApellido())){personaFisica.setSegundoApellido(null);}else{personaFisica.setSegundoApellido(oForm.getSegundoApellido());}
    	if(oForm.getFechaNacimiento() == null){personaFisica.setFechaNacimiento(null);}else{personaFisica.setFechaNacimiento(oForm.getFechaNacimiento());}
    	if(oForm.getLugarNacimiento().getClave().equals("-1")){personaFisica.setLugarNacimiento(null);}else{personaFisica.setLugarNacimiento(oForm.getLugarNacimiento());}
    	if(oForm.getSexo().getIdSexo() == -1){personaFisica.setSexo(null);}else{personaFisica.setSexo(oForm.getSexo());}
    	
    	return personaFisica;
    }
    
    /**
     * Metodo que regresa una cadena que especifica donde se realizo la busqueda de entidades externas dependiendo de los campos proporionados en el formulario
     * @param pf
     * @return
     */
    public String validarLugaresDeBusqueda(Fisica pf){
    	String entidades = "";
    	
    	try{
	    
	    	if( StringUtils.isNotBlank(pf.getCurp()) || ( StringUtils.isNotBlank(pf.getNombre()) &&
													      StringUtils.isNotBlank(pf.getPrimerApellido()) &&
														  (pf.getSexo() != null && pf.getSexo().getIdSexo() != null && pf.getSexo().getIdSexo() != -1) && 
														  pf.getFechaNacimiento() != null &&
														  (pf.getLugarNacimiento() != null && pf.getLugarNacimiento().getClave() != null && !pf.getLugarNacimiento().getClave().equals("-1")) )
			){
	    		entidades += " ni en el RENAPO";
			}
	    	
    		if(StringUtils.isNotBlank(pf.getRfc())){
				entidades += " ni en el SAT";
			}
    		
    	}catch(Exception e){
    		log.debug(e.getMessage());
    	}
    	return entidades;
    }

}
