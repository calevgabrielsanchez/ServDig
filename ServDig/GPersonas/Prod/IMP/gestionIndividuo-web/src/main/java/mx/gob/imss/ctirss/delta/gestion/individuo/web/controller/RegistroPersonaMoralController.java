package mx.gob.imss.ctirss.delta.gestion.individuo.web.controller;

import java.text.SimpleDateFormat;
import java.util.List;

import javax.servlet.http.HttpSession;

import mx.gob.imss.ctirss.delta.framework.base.controller.AbstractController;
import mx.gob.imss.ctirss.delta.gestion.individuo.web.validator.PersonaMoralDatosBasicosIMSSValidator;
import mx.gob.imss.ctirss.delta.gestion.individuo.web.validator.PersonaMoralDatosBasicosSATValidator;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Calificacion;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Moral;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.PersonaCalificacion;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.vo.CalificacionPersona;
import mx.gob.imss.ctirss.gestionpersonas.servicios.business.PersonaBusinessRemote;
import mx.gob.imss.ctirss.gestionpersonas.servicios.business.PersonaMoralBusinessRemote;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.support.SessionStatus;

/**
 * @author Samuel Rodriguez Grajeda
 * @company IMSS (Instituto Mexicano del Seguro Social)
 * @date 28/08/2011
 */
@Controller
@RequestMapping(value = "/persona/moral")
public class RegistroPersonaMoralController extends AbstractController {

    @Autowired
    private transient PersonaMoralBusinessRemote personaMoralBusiness;

    @Autowired
    private transient PersonaBusinessRemote personaBusiness;

    @RequestMapping(value = "/registro", method = RequestMethod.GET)
    public String getPersona(final Model model) {
        model.addAttribute("moral", new Moral());
        return "registroPersonaMoral";
    }

    @RequestMapping(value = "/registroPopup", method = RequestMethod.GET)
    public String getPersonaPopup(final Model model) {
        model.addAttribute("moral", new Moral());
        model.addAttribute("titulo", "Registro de Persona Moral - Paso 1 de 2");
        return "registroPersonaMoral";
    }

    // /busqueda/sat
    @RequestMapping(value = "/busqueda/sat", method = RequestMethod.POST)
    public String buscarPersonaMoralEnSat(@ModelAttribute Moral oForm, BindingResult result, Model model, SessionStatus status, HttpSession session) throws Exception {

        String vista = "";
 
        /* Se valida que los campos basicos existan */
        new PersonaMoralDatosBasicosSATValidator().validate(oForm, result);

        if (result.hasErrors()) {
            log.debug("ERRORES DE VALIDACION:\n " + result);
            vista = "registroPersonaMoral";
            log.debug("FORMULARIO DE ENTRADA: " + oForm);
        } else {
            log.debug("SE REALIZARA LA BUSQUEDA DE PERSONA MORAL EN IMSS MEDIANTE EL RFC: " + oForm.getRfcSat());
            /* buscamos dentro del IMSS */
            final List<Moral> personaMoralLst = personaMoralBusiness.buscarPersonaMoralPorRfcEnImss(oForm.getRfcSat());
            log.debug("RESULTADO DE BUSQUEDA EN EL IMSS: " + personaMoralLst);

            if (personaMoralLst != null && personaMoralLst.size() > 0) {
                vista = "registroPersonaMoralIMSSResultado";
                model.addAttribute("personaMoralLst", personaMoralLst);
                model.addAttribute("mensaje", "La persona fue hallada en el IMSS. Se encontraron los siguientes registros: ");

            } else {
                log.debug("SE REALIZARA LA BUSQUEDA DE PERSONA MORAL EN SAT MEDIANTE EL RFC: " + oForm.getRfcSat());
                /* buscamos dentro del SAT */
                final Moral personaMoral = personaBusiness.buscarPersonaMoralPorRfcEnSat(oForm.getRfcSat());
                log.debug("RESULTADO DE BUSQUEDA EN EL SAT: " + personaMoral);
                if (personaMoral == null) {
                    log.debug("NO SE ENCONTRO EN NINGUN LADO: " + oForm);
                    vista = "mensajes";
                    model.addAttribute("mensaje", MENSAJE_ERROR_BUSQUEDAS.replaceAll("<PERSONA>", " con RFC '" + oForm.getRfcSat() + "' ").replace("<ENTIDAD_EXTERNA>", "SAT"));
                } else {
                    vista = "registroPersonaMoralCaptura";
                    model.addAttribute("titulo", "Registro de Persona Moral - Paso 2 de 2");

                    PersonaCalificacion personaCalificacion = new PersonaCalificacion();
                    personaCalificacion.setCalificacion(new Calificacion());
                    personaMoral.getPersonaCalificaciones().add(personaCalificacion);
                    
                    personaMoral.getPersonaCalificaciones().get(0).getCalificacion().setDescripcion(CalificacionPersona.CALIFICACION_2_VALIDADO_SAT);
                    personaMoral.getPersonaCalificaciones().get(0).getCalificacion().setIdCalificacion(2L);

                    model.addAttribute("moral", personaMoral);

                    model.addAttribute("mensaje", "La persona fue hallada en el SAT");
                    model.addAttribute("busquedaSat", true);
                }
            }
        }

        model.addAttribute("botonOprimido", "sat");
        return vista;
    }

    private transient SimpleDateFormat formatoFechaCreacion = new SimpleDateFormat("dd/MM/yyyy");

    // /busqueda/imss
    @RequestMapping(value = "/busqueda/imss", method = RequestMethod.POST)
    public String buscarPersonaMoralEnImss(final @ModelAttribute Moral oForm, final BindingResult result, final Model model, final HttpSession session) {
        final String vista;
        log.debug("FORMULARIO DE ENTRADA: " + oForm);
        /* Se valida que los campos basicos existan */
        new PersonaMoralDatosBasicosIMSSValidator().validate(oForm, result);
        if (result.hasErrors()) {
            log.debug("ERRORES DE VALIDACION:\n " + result);
            vista = "registroPersonaMoral";
        } else {
            final String role = (String) session.getAttribute("role");
            log.trace("role: " + role);
            vista = localizarPersonaConDatosBasicos(oForm, model, role);
        }
        model.addAttribute("botonOprimido", "imss");
        log.info("vista a mostrar: " + vista);
        return vista;
    }

    private String localizarPersonaConDatosBasicos(final Moral oForm, final Model model, final String role) {
        log.debug("SE REALIZARA LA BUSQUEDA DE PERSONA MORAL EN IMSS MEDIANTE LOS DATOS BASICOS: " + oForm);
        final List<Moral> personas = personaMoralBusiness.getPersonaMoral(oForm);
        String vista = "registroPersonaMoralCaptura";
        final Boolean personaEnImss = !(personas == null || personas.isEmpty());
        if (personaEnImss) {
            log.debug("La persona fue hallada en el IMSS. personas:\n " + personas);
            procesarPersonaHalladaEnImss(model, personas);
            vista = "registroPersonaMoralIMSSResultado";
        } else {
            log.debug("NO SE ENCONTRO EN EL IMSS a la persona: " + oForm);
            PersonaCalificacion personaCalificacion = new PersonaCalificacion();
            personaCalificacion.setCalificacion(new Calificacion());
            oForm.getPersonaCalificaciones().add(personaCalificacion);
            vista = "registroPersonaMoralCaptura";
            model.addAttribute("titulo", "Registro de Persona Moral - Paso 2 de 2");
            if("ventanilla".equalsIgnoreCase(role)) {
                oForm.getPersonaCalificaciones().get(0).getCalificacion().setDescripcion(CalificacionPersona.CALIFICACION_3_VALIDADO_IMSS);
                oForm.getPersonaCalificaciones().get(0).getCalificacion().setIdCalificacion(CalificacionPersona.VALIDADO_IMSS.longValue());
            } else {
                oForm.getPersonaCalificaciones().get(0).getCalificacion().setDescripcion(CalificacionPersona.CALIFICACION_4_NO_VALIDADO);
                oForm.getPersonaCalificaciones().get(0).getCalificacion().setIdCalificacion(CalificacionPersona.NO_VALIDADO.longValue());
            }

            model.addAttribute("moral", oForm);
            model.addAttribute("mensaje", "La persona no fue hallada en el IMSS ni en el SAT");
        }
        return vista;
    }

    private void procesarPersonaHalladaEnImss(Model model, final List<Moral> personas) {
        for (Moral personaMoral : personas) {
            String fechaCreacionStr;
            if (personaMoral.getFechaCreacion() == null) {
                fechaCreacionStr = null;
            } else {
                fechaCreacionStr = formatoFechaCreacion.format(personaMoral.getFechaCreacion());
            }
            personaMoral.setFechaCreacionFormateada(fechaCreacionStr);
        }
        model.addAttribute("personaMoralLst", personas);
        model.addAttribute("mensaje", "La persona fue hallada en el IMSS. Se encontraron los siguientes registros: ");
    }

}
