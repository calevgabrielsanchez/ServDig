package mx.gob.imss.ctirss.delta.gestion.individuo.web.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import mx.gob.imss.ctirss.delta.framework.base.controller.AbstractController;
import mx.gob.imss.ctirss.delta.framework.base.exception.AbstractException;
import mx.gob.imss.ctirss.delta.framework.base.web.sso.UsuarioSSO;
import mx.gob.imss.ctirss.delta.framework.exceptions.FormularioVacioException;
import mx.gob.imss.ctirss.delta.gestion.individuo.utility.bean.DatosEntradaPaginador;
import mx.gob.imss.ctirss.delta.gestion.individuo.utility.bean.DatosSalidaPaginador;
import mx.gob.imss.ctirss.delta.gestion.individuo.utility.bean.PersonaDataTable;
import mx.gob.imss.ctirss.delta.gestion.individuo.web.validator.PersonaFisicaBusquedaValidator;
import mx.gob.imss.ctirss.delta.model.Usuario;
import mx.gob.imss.ctirss.delta.model.derechohabiente.UsuarioFuncionario;
import mx.gob.imss.ctirss.delta.model.derechohabiente.negocio.PerfilUsuario;
import mx.gob.imss.ctirss.delta.model.domicilio.Delegacion;
import mx.gob.imss.ctirss.delta.model.domicilio.Subdelegacion;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Fisica;
import mx.gob.imss.ctirss.gestionpersonas.servicios.business.PersonaBusinessRemote;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindException;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author Samuel Rodriguez Grajeda
 * @company IMSS (Instituto Mexicano del Seguro Social)
 * @date 28/08/2011
 */
@Controller
@Scope("request")
@RequestMapping(value = "/persona/fisica")
public class BusquedaPersonaFisicaController extends AbstractController {

    @Autowired
    private PersonaBusinessRemote personaBusiness;

	@RequestMapping(value = "/busqueda", method = RequestMethod.GET)
	public String buscarPersona(Model modelo, HttpSession session,
			HttpServletRequest request) {
		modelo.addAttribute("personaFisica", new Fisica());
		log.error("test it now again");

		UsuarioSSO sso = this.procesarUsuarioSSO(request);

		/*
		 * Procesamos la información del openSSO
		 */
		if (sso != null) {
			ServletContext context = session.getServletContext();
			String rolVentanilla = context.getInitParameter("rolVentanilla");
	        String rolInternet = context.getInitParameter("rolInternet");
	        String[] arrayRolVentanilla = {rolVentanilla};
	        String[] arrayRolInternet = {rolInternet};

			Usuario usuario = new Usuario();
			usuario.setUsuario(sso.getNombre());

			PerfilUsuario pu = new PerfilUsuario();
			if (this.checkGrantedAuthorities(arrayRolVentanilla)) {
				pu.setIdPerfilUsuario(100L);
			} else if (this.checkGrantedAuthorities(arrayRolInternet)) {
				pu.setIdPerfilUsuario(200L);
			}
			pu.setDescripcion(sso.getPerfil());
			usuario.setPerfilUsuario(pu);

			UsuarioFuncionario uf = new UsuarioFuncionario();
			if (sso.getDelegacion() != null) {
				uf.setDelegacion(new Delegacion());
				uf.getDelegacion().setId(sso.getDelegacion().longValue());
			}
			if (sso.getSubdelegacion() != null) {
				usuario.setCveIdSubdelegacion(sso.getSubdelegacion().longValue());
				uf.setSubdelegacion(new Subdelegacion());
				uf.getSubdelegacion().setId(sso.getSubdelegacion().longValue());
			}
			uf.setUsuario(usuario);
			usuario.setUsuarioFuncionario(uf);

			// Subimos a la sesion la informacion del usuario
			session.setAttribute(KEY_USUARIO, usuario);
			this.setFechaSistema(session);
		}

		return "busquedaPersonaFisica";
	}

    @SuppressWarnings("unchecked")
    @RequestMapping(value = "/busqueda/bdu", method = RequestMethod.POST)
    public @ResponseBody DatosSalidaPaginador<Fisica> buscarPersonaFisicaEnBDU(@RequestBody PersonaDataTable aoData, HttpSession session, HttpServletResponse response) {
        log.debug("Busqueda de persona fisica: " + aoData.getoForm());

        /*
         * Codigo para el manejo de las validaciones de los campos requeridos o
         * de la aplicacion de las RN
         */
        final Map<String, Object> result = new HashMap<String, Object>();

        final DatosEntradaPaginador<Fisica> send = new DatosEntradaPaginador<Fisica>();

        send.parserArray(aoData.getAoData());
        send.setModelo(aoData.getoForm());

        DatosSalidaPaginador<Fisica> outData = null;

        PersonaFisicaBusquedaValidator pfbv = new PersonaFisicaBusquedaValidator();
        
        Errors errors = new BindException(aoData.getoForm(), "model");

        try {
            /* Primero se validara que el formulario tenga al menos 1 filtro de busqueda, de lo contrario se lanzara una excepcion */
            if(pfbv.validarFormularioVacio(aoData.getoForm())) {
                throw new FormularioVacioException();
            } else {
                pfbv.validate(aoData.getoForm(), errors);
                if(errors.hasErrors()) {
                    outData = new DatosSalidaPaginador<Fisica>();
                    procesaErroresDeCaptura(errors, result, response);
                    outData.setErroresCaptura((List)result.get(KEY_CODE_ERROR_FIELDS));
                } else {
                    outData = personaBusiness.getPersonaFisicaFiltro(send);
                }
            }
        } catch(AbstractException e) {
            log.debug("Error en la consulta [" + e.getMessage() + "]");
            outData = new DatosSalidaPaginador<Fisica>();
            procesarErrorDeNegocio(e, result, response);
            outData.setErroresNegocio(e.getMessage());
        }

        outData.setsEcho(send.getsEcho());
        log.debug("Lst size: " + outData.getAaData().size());
        log.trace("entity list: " + outData.getAaData());
        return outData;
    }
    
    /*************************************************************************/
    /**************** BUSQUEDA EMBEBIDA DE PERSONAS FISICAS ******************/
    /*************************************************************************/
    
    /**
     * En este metodo se lleva a cabo la llamada inicial a la pantalla de busqueda embebida de persona fisica
     * @param modelo
     * @return
     */
    @RequestMapping(value = "/busqueda-embebida", method = RequestMethod.GET)
    public String nuevaBuscarPersona(Model modelo) {
        modelo.addAttribute("fisica", new Fisica());
        return "busquedaEmbebidaPersonaFisica";
    }
    
	/**
	* En este metodo se hace la busqueda mediante un ID de persona fisica especifico
	* @param idPersonaFisica
	* @return
	*/
	@RequestMapping(value="/busqueda-embebida/{idPersonaFisica}" , method=RequestMethod.GET)
	public @ResponseBody Fisica getPersonaFisica(@PathVariable Long idPersonaFisica){
		Fisica fisica = personaBusiness.getPersonaFisica(idPersonaFisica);
		return fisica;
	}
	
    @SuppressWarnings("unchecked")
    @RequestMapping(value = "/busqueda/bdu/json/validar", method = RequestMethod.POST)
    public @ResponseBody DatosSalidaPaginador<Fisica> validarPersonaFisicaPrevioABusquedaEmbebidaBDUJSON(@RequestBody PersonaDataTable aoData, HttpSession session, HttpServletResponse response) {
        log.debug("Formulario a validar: " + aoData.getoForm());

        /*
         * Codigo para el manejo de las validaciones de los campos requeridos o
         * de la aplicacion de las RN
         */
        final Map<String, Object> result = new HashMap<String, Object>();

        final DatosEntradaPaginador<Fisica> send = new DatosEntradaPaginador<Fisica>();

        send.parserArray(aoData.getAoData());
        send.setModelo(aoData.getoForm());

        DatosSalidaPaginador<Fisica> outData = null;

        PersonaFisicaBusquedaValidator pfbv = new PersonaFisicaBusquedaValidator();
        
        Errors errors = new BindException(aoData.getoForm(), "model");

        try {
  
        	outData = new DatosSalidaPaginador<Fisica>();
        	
        	/* Primero se validara que el formulario tenga al menos 1 filtro de busqueda, de lo contrario se lanzara una excepcion */
            if(pfbv.validarFormularioVacio(aoData.getoForm())) {
                throw new FormularioVacioException();
            } else {
                pfbv.validate(aoData.getoForm(), errors);
                if(errors.hasErrors()) {
                    procesaErroresDeCaptura(errors, result, response);
                    outData.setErroresCaptura((List)result.get(KEY_CODE_ERROR_FIELDS));
                }
            }
        } catch(AbstractException e) {
            log.debug("Error en la validacion [" + e.getMessage() + "]");
            outData = new DatosSalidaPaginador<Fisica>();
            procesarErrorDeNegocio(e, result, response);
            outData.setErroresNegocio(e.getMessage());
        }

        outData.setsEcho(send.getsEcho());
        log.debug("Lst size: " + outData.getAaData().size());
        log.trace("entity list: " + outData.getAaData());
        return outData;
    }

	/**
	 * Este metodo hace la busqueda de persona fisica en base a los filtros seleccionados desde la vista mediante una peticion
	 * asincrona, y se regresa una lista de personas fisicas
	 * @param aoData
	 * @param response
	 * @param model
	 * @return
	 */	
    @SuppressWarnings("unchecked")
    @RequestMapping(value = "/busqueda/bdu/json", method = RequestMethod.POST)
    public String buscarPersonaFisicaEnBDUJSON(@RequestBody PersonaDataTable aoData, HttpServletResponse response, Model model) {
        log.debug("Busqueda de persona fisica: " + aoData.getoForm());

        /*
         * Codigo para el manejo de las validaciones de los campos requeridos o
         * de la aplicacion de las RN
         */
        final Map<String, Object> result = new HashMap<String, Object>();

        final DatosEntradaPaginador<Fisica> send = new DatosEntradaPaginador<Fisica>();

        send.parserArray(aoData.getAoData());
        send.setModelo(aoData.getoForm());

        DatosSalidaPaginador<Fisica> outData = null;

        PersonaFisicaBusquedaValidator pfbv = new PersonaFisicaBusquedaValidator();
        
        Errors errors = new BindException(aoData.getoForm(), "model");

        try {
            /* Primero se validara que el formulario tenga al menos 1 filtro de busqueda, de lo contrario se lanzara una excepcion */
            if(pfbv.validarFormularioVacio(aoData.getoForm())) {
                throw new FormularioVacioException();
            } else {
                pfbv.validate(aoData.getoForm(), errors);
                if(errors.hasErrors()) {
                    outData = new DatosSalidaPaginador<Fisica>();
                    procesaErroresDeCaptura(errors, result, response);
                    outData.setErroresCaptura((List)result.get(KEY_CODE_ERROR_FIELDS));
                } else {
                	// 191807 211112 esta busqueda unicamente busca en el IMSS. Ahora se requiere que tambien busque en entidades externas tambien
                	// de modo que se utilizara el servicio de abajo, pero previamente hay que nullear los campos de persona fisica sino el servicio de
                	// localizarPersonaFisica hace pendejadas
//                  outData = personaBusiness.getPersonaFisicaFiltro(send);
                	outData = personaBusiness.buscarPersonaFisicaEnIMSSyEE(nullearCampos(send.getModelo())); // Aqui dentro de este metodo se manda a llamar a servicioPersonaBusiness.localizarPersonaFisica(...)
                }
            }
        } catch(AbstractException e) {
            log.debug("Error en la consulta [" + e.getMessage() + "]");
            outData = new DatosSalidaPaginador<Fisica>();
            procesarErrorDeNegocio(e, result, response);
            outData.setErroresNegocio(e.getMessage());
        }

        outData.setsEcho(send.getsEcho());
        log.debug("Lst size: " + outData.getAaData().size());
        log.trace("entity list: " + outData.getAaData());
        
        List<Fisica> personasFisicas = outData.getAaData();
        
        model.addAttribute("personasFisicas", personasFisicas);
        
        return "busquedaEmbebidaPersonaFisicaResultados";
    }
    
    /**
     * 191807 211112
     * Con este metodo evitamos que se pasen al backend propiedades que vengan oomo cadenas vacias. Para un funcionamiento correcto de las consultas, las 
     * propiedades deben ser NULAS o NO NULAS (pero NUNCA CADENAS VACIAS)
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

}
