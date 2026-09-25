package mx.gob.imss.ctirss.delta.gestion.individuo.web.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import mx.gob.imss.ctirss.delta.framework.exceptions.PersonaYaExisteEnSolicitudException;
import mx.gob.imss.ctirss.delta.gestion.individuo.utility.bean.DatosEntradaPaginador;
import mx.gob.imss.ctirss.delta.gestion.individuo.utility.bean.DatosSalidaPaginador;
import mx.gob.imss.ctirss.delta.gestion.individuo.utility.bean.WrapperDataTable;
import mx.gob.imss.ctirss.delta.gestion.individuo.web.utils.WrapperSessionDatosPersonaMoralSalidaPaginador;
import mx.gob.imss.ctirss.delta.gestion.individuo.web.validator.PersonaMoralCapturaValidator;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Moral;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindException;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author Samuel Rodriguez Grajeda
 * @author Cesar Garcia Mauricio
 * @company IMSS (Instituto Mexicano del Seguro Social)
 * @date 28/08/2011
 */
@Controller
@Scope("request")
@RequestMapping(value = "/persona/moral")
public class RegistroPersonaMoralCapturaController extends RegistroPersonaController {

    @Autowired
    private transient WrapperSessionDatosPersonaMoralSalidaPaginador<Moral> wrapperDataPager;
    

    // registro-captura
    /**
     * Este servicio es para el guardado real de la solicitud en BDTU.
     * 
     * @param role
     * @param model
     * @return 'confirmacionRegistroPersona' view
     */
    @RequestMapping(value = "/registro-captura", method = RequestMethod.GET)
    public String registrarPersonaMoral(final HttpSession session, final Model model) throws Exception {
        final String role = (String) session.getAttribute("role");
        registrarPersona(role, wrapperDataPager.getDataPager().getAaData(), model);
        return "confirmacionRegistroPersona";
    }

    /* /registro-persona-solicitud */
    @RequestMapping(value = "/registro-persona-solicitud", method = RequestMethod.POST)
    public @ResponseBody
    Map<String, Object> registrarPersonaMoral(@RequestBody final Moral moral, final HttpServletResponse response) {
        log.debug("PERSONA Moral a incluir EN SOLICITUD:\n " + moral);
        final Map<String, Object> result = new HashMap<String, Object>();
        if (moral != null) {
            if (listContains(moral)) {
                procesarErrorDeNegocio(new PersonaYaExisteEnSolicitudException(), result, response);
                log.info(REG_REPETIDO_MSG);
                return result;
            } else {
                wrapperDataPager.getDataPager().getAaData().add(moral);
            }
        }
        result.put("oform", moral);
        log.debug("Personas actualmente en la solicitud: " + wrapperDataPager.getDataPager().getAaData());
        return result;
    }

    private Boolean listContains(final Moral moral) {
        Boolean listContains = Boolean.FALSE; // NOPMD
        final List<Moral> aaData = wrapperDataPager.getDataPager().getAaData();
        for (Moral moralEnSol : aaData) {
            if (areEqual(moral, moralEnSol)) {
                listContains = Boolean.TRUE;
                break;
            }
        }
        return listContains;
    }

    private Boolean areEqual(final Moral personaMoral, final Moral personaMoralEnSol) {
        Boolean areEqual;
        if (
                   personaMoral != null 
                && personaMoralEnSol != null 
//              && personaMoral.getActaConstitutiva().equals(personaMoralEnSol.getActaConstitutiva()) 
                && personaMoral.getFechaCreacion().equals(personaMoralEnSol.getFechaCreacion())
                && personaMoral.getTipoSociedad().getIdTipoSociedad().equals(personaMoralEnSol.getTipoSociedad().getIdTipoSociedad()) 
                && personaMoral.getRazonSocial().equals(personaMoralEnSol.getRazonSocial()) 
                && personaMoral.getRfc().equals(personaMoralEnSol.getRfc())
             ) {
            areEqual = true;
        } else {
            areEqual = false;
        }
        return areEqual;
    }

    /* /registro-persona-solicitud-lst */
    /**
     * Method called for updating of datatable in orchestrator (PM) view.
     * 
     * @param aoData
     * @return
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    @RequestMapping(value = "/registro-persona-solicitud-lst", method = RequestMethod.POST)
    @ResponseBody
    public DatosSalidaPaginador<Moral> getPersonaMoralList(final @RequestBody WrapperDataTable aoData) {
        final DatosEntradaPaginador<Moral> send = new DatosEntradaPaginador<Moral>();
        send.parserArray(aoData.getAoData());
        wrapperDataPager.getDataPager().setsEcho(send.getsEcho());
        wrapperDataPager.getDataPager().setiTotalRecords(wrapperDataPager.getDataPager().getAaData().size());
        wrapperDataPager.getDataPager().setiTotalDisplayRecords(wrapperDataPager.getDataPager().getAaData().size());
        return wrapperDataPager.getDataPager();
    }
    
    /**
     * Metodo que valida el formulario de captura de persona moral (combos y RFC unicamente)
     * @param oForm
     * @param response
     * @return
     */
    @RequestMapping(value = "/registro-captura/validaciones", method = RequestMethod.POST)
    public @ResponseBody Map<String, ? extends Object> validarFormulario(final @RequestBody Moral oForm, final HttpServletResponse response) {
        log.trace("entramos a RegistroPersonaMoralCapturaValidadorController para validar el objeto de formulario: " + oForm);
        final Map<String, Object> result = new HashMap<String, Object>();

        final Errors errors = new BindException(oForm, "model");
        new PersonaMoralCapturaValidator().validate(oForm, errors);
        if (errors.hasErrors()) {
            procesaErroresDeCaptura(errors, result, response);
            return result;
        }
        result.put("oForm", oForm);

        return result;

    }
    
}
