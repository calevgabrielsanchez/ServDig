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
import mx.gob.imss.ctirss.delta.gestion.individuo.web.utils.WrapperSessionDatosPersonaFisicaSalidaPaginador;
import mx.gob.imss.ctirss.delta.gestion.individuo.web.validator.PersonaFisicaCapturaValidator;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Fisica;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
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
@RequestMapping(value = "/persona/fisica")
public class RegistroPersonaFisicaCapturaController extends RegistroPersonaController {

    @Autowired
    private transient WrapperSessionDatosPersonaFisicaSalidaPaginador<Fisica> wrapperDataPager;


    /* /registro-captura GET */
    /**
     * Este servicio es para el guardado real de la solicitud en BDTU.
     * 
     * @param role
     * @param model
     * @return 'confirmacionRegistroPersona' view
     */
    @RequestMapping(value = "/registro-captura", method = RequestMethod.GET)
    public String registrarPersonaFisica(final HttpSession session, final Model model) throws Exception {
    	
    	this.log.debug("registrarPersonaFisica ...." + model );
        final String role = (String) session.getAttribute("role");
        
        /* Hay que setear este campo ya que en la tabla el campo NoJuzgado es NOT NULLABLE, o sea obligatorio. Esto es porque el WS de RENAPO no trae este campo */
        //wrapperDataPager.getDataPager().getAaData().get(0).getActaNacimiento().setNoJuzgado("0");
        
		// LUDS 19/02/2013 se modifico ya que se debe de recorrer todos los
		// elementos de la lista, y setearle el numero de juzgado = 0

        registrarPersona(role, wrapperDataPager.getDataPager().getAaData(), model);
        return "confirmacionRegistroPersona";
    }

    /* /registro-persona-solicitud */
    @RequestMapping(value = "/registro-persona-solicitud", method = RequestMethod.POST)
    public @ResponseBody Map<String, Object> registrarPersonaFisica(@RequestBody final Fisica fisica, final HttpServletResponse response) {
        log.debug("Objeto de Lucio que es de todos (PERSONA FISICA A INCLUIR EN LA SOLICITUD): " + ReflectionToStringBuilder.toString(fisica, ToStringStyle.MULTI_LINE_STYLE));
        
        final Map<String, Object> result = new HashMap<String, Object>();
        if (fisica != null) {
            if (listContains(fisica)) {
                procesarErrorDeNegocio(new PersonaYaExisteEnSolicitudException(), result, response);
                log.info(REG_REPETIDO_MSG);
                return result;
            } else {
                wrapperDataPager.getDataPager().getAaData().add(fisica);
            }
        }
        log.debug("Personas actualmente en la solicitud: " + wrapperDataPager.getDataPager().getAaData());
        return result;
    }

    private Boolean listContains(final Fisica fisica) {
        Boolean listContains = Boolean.FALSE; // NOPMD
        final List<Fisica> aaData = wrapperDataPager.getDataPager().getAaData();
        for (Fisica fisicaEnSol : aaData) {
            if (areEqual(fisica, fisicaEnSol)) {
                listContains = Boolean.TRUE;
                break;
            }
        }
        return listContains;
    }

    private Boolean areEqual(final Fisica fisica, final Fisica fisicaSol) {
        log.trace("\n" + fisica);
        log.trace("\n" + fisicaSol);
        Boolean areEqual;
        if (fisica != null && fisicaSol != null) {
            final Boolean nombresIguales = fisica.getNombre() != null && fisica.getNombre().equals(fisicaSol.getNombre());
            final Boolean primerApellidosIguales = fisica.getPrimerApellido() != null && fisica.getPrimerApellido().equals(fisicaSol.getPrimerApellido());
            final Boolean segundoApellidosIguales = fisica.getSegundoApellido() != null && fisica.getSegundoApellido().equals(fisicaSol.getSegundoApellido());
            final Boolean sexosIguales = fisica.getSexo().getIdSexo() != null && fisica.getSexo().getIdSexo().equals(fisicaSol.getSexo().getIdSexo());
            final Boolean entidadNacimientoIguales = fisica.getLugarNacimiento() != null && fisica.getLugarNacimiento().getClave().equals(fisicaSol.getLugarNacimiento().getClave());
            final Boolean fechaNacimientoIguales = fisica.getFechaNacimiento() != null && fisica.getFechaNacimiento().equals(fisicaSol.getFechaNacimiento());
            areEqual = nombresIguales && primerApellidosIguales && segundoApellidosIguales && sexosIguales && entidadNacimientoIguales && fechaNacimientoIguales;
        } else if (fisica == null && fisicaSol == null) {
            areEqual = true;
        } else if (fisica == fisicaSol) {
            areEqual = true;
        } else {
            areEqual = false;
        }
        log.trace("areEqual: " + areEqual);
        return areEqual;
    }

    /* /registro-persona-solicitud-lst */
    /**
     * Method called for updating of datatable in orchestrator (PF) view.
     * 
     * @param aoData
     * @return
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    @RequestMapping(value = "/registro-persona-solicitud-lst", method = RequestMethod.POST)
    @ResponseBody public DatosSalidaPaginador<Fisica> getPersonaFisicaList(final @RequestBody WrapperDataTable aoData) {
        log.info("Lista de personas en sesion: " + wrapperDataPager.getDataPager().getAaData());
        final DatosEntradaPaginador<Fisica> send = new DatosEntradaPaginador<Fisica>();
        send.parserArray(aoData.getAoData());
        wrapperDataPager.getDataPager().setsEcho(send.getsEcho());
        wrapperDataPager.getDataPager().setiTotalRecords(wrapperDataPager.getDataPager().getAaData().size());
        wrapperDataPager.getDataPager().setiTotalDisplayRecords(wrapperDataPager.getDataPager().getAaData().size());
        return wrapperDataPager.getDataPager();
    }

    /**
     * Metodo que valida el formulario de captura de persona fisica (combos, CURP y RFC unicamente)
     * @param oForm
     * @param response
     * @return
     */
    @RequestMapping(value = "/registro-captura/validaciones", method = RequestMethod.POST)
    public @ResponseBody Map<String, ? extends Object> validarFormulario(final @RequestBody Fisica oForm, final HttpServletResponse response) {
        log.trace("entramos a RegistroPersonaFisicaCapturaValidadorController para validar el objeto de formulario --> " + ReflectionToStringBuilder.toString(oForm, ToStringStyle.MULTI_LINE_STYLE));
        
        final Map<String, Object> result = new HashMap<String, Object>();

        final Errors errors = new BindException(oForm, "model");
        new PersonaFisicaCapturaValidator().validate(oForm, errors);
        if (errors.hasErrors()) {
            procesaErroresDeCaptura(errors, result, response);
            return result;
        }
        result.put("oForm", oForm);

        return result;

    }
    
}
