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
import mx.gob.imss.ctirss.delta.gestion.individuo.utility.bean.PersonaMoralDataTable;
import mx.gob.imss.ctirss.delta.gestion.individuo.web.validator.PersonaMoralBusquedaValidator;
import mx.gob.imss.ctirss.delta.model.Usuario;
import mx.gob.imss.ctirss.delta.model.derechohabiente.UsuarioFuncionario;
import mx.gob.imss.ctirss.delta.model.derechohabiente.negocio.PerfilUsuario;
import mx.gob.imss.ctirss.delta.model.domicilio.Delegacion;
import mx.gob.imss.ctirss.delta.model.domicilio.Subdelegacion;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Moral;
import mx.gob.imss.ctirss.gestionpersonas.servicios.business.PersonaMoralBusinessRemote;

import org.springframework.beans.factory.annotation.Autowired;
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
@RequestMapping(value = "/persona/moral")
public class BusquedaPersonaMoralController extends AbstractController {

    @Autowired
    private PersonaMoralBusinessRemote personaMoralBusiness;

	@RequestMapping(value = "/busqueda", method = RequestMethod.GET)
	public String buscarPersona(Model modelo, final HttpSession session,
			HttpServletRequest request) {
		modelo.addAttribute("moral", new Moral());

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

		return "busquedaPersonaMoral";
	}

    @SuppressWarnings("unchecked")
    @RequestMapping(value = "/busqueda/bdu", method = RequestMethod.POST)
    public @ResponseBody DatosSalidaPaginador<Moral> buscarPersonaMoralEnBDU(@RequestBody PersonaMoralDataTable aoData, HttpSession session, HttpServletResponse response) {
        log.debug("Busqueda de persona moral: " + aoData.getoForm());
        
        /*
         * Codigo para el manejo de las validaciones de los campos requeridos o
         * de la aplicacion de las RN
         */
        final Map<String, Object> result = new HashMap<String, Object>();

        final DatosEntradaPaginador<Moral> send = new DatosEntradaPaginador<Moral>();

        send.parserArray(aoData.getAoData());
        send.setModelo(aoData.getoForm());

        DatosSalidaPaginador<Moral> outData = null;

        PersonaMoralBusquedaValidator pmbv = new PersonaMoralBusquedaValidator();

        Errors errors = new BindException(aoData.getoForm(), "model");
        
        try {
            /* Primero se validara que el formulario tenga al menos 1 filtro de busqueda, de lo contrario se lanzara una excepcion */
            if(pmbv.ValidarFormularioVacio(aoData.getoForm())) {
                throw new FormularioVacioException();
            }else{
                pmbv.validate(aoData.getoForm(), errors);
                if(errors.hasErrors()) {
                    outData = new DatosSalidaPaginador<Moral>();
                    procesaErroresDeCaptura(errors, result, response);
                    outData.setErroresCaptura((List)result.get(KEY_CODE_ERROR_FIELDS));
                } else {
                    outData = personaMoralBusiness.getPersonaMoralFiltro(send);                    
                }
            }
        } catch(AbstractException e) {
            log.debug("Error en la consulta [" + e.getMessage() + "]");
            outData = new DatosSalidaPaginador<Moral>();
            procesarErrorDeNegocio(e, result, response);
            outData.setErroresNegocio(e.getMessage());
        }

        outData.setsEcho(send.getsEcho());
        log.debug("Lst size: " + outData.getAaData().size());
        log.trace("entity list: " + outData.getAaData());
        return outData;
    }
    
    /*************************************************************************/
    /***************** BUSQUEDA EMBEBIDA DE PERSONAS MORALES *****************/
    /*************************************************************************/
    
    /**
     * En este metodo se lleva a cabo la llamada inicial a la pantalla de busqueda embebida de persona moral
     * @param modelo
     * @return
     */
    @RequestMapping(value = "/busqueda-embebida", method = RequestMethod.GET)
    public String nuevaBuscarPersona(Model modelo) {
        modelo.addAttribute("moral", new Moral());
        return "busquedaEmbebidaPersonaMoral";
    }
    
	/**
	* En este metodo se hace la busqueda mediante un ID de persona moral especifico
	* @param idPersonaMoral
	* @return
	*/
	@RequestMapping(value="/busqueda-embebida/{idPersonaMoral}" , method=RequestMethod.GET)
	public @ResponseBody Moral getPersonaMoral(@PathVariable Long idPersonaMoral){
		Moral moral = personaMoralBusiness.getPersonaMoral(idPersonaMoral);
		return moral;
	} 
    
	/**
	 * Este metodo hace la busqueda de persona moral en base a los filtros seleccionados desde la vista mediante una peticion
	 * asincrona, y se regresa una lista de personas morales
	 * @param aoData
	 * @param response
	 * @param model
	 * @return
	 */
    @SuppressWarnings("unchecked")
    @RequestMapping(value = "/busqueda/bdu/json", method = RequestMethod.POST)
    public String buscarPersonaMoralEnBDUJSON(@RequestBody PersonaMoralDataTable aoData, HttpServletResponse response, Model model) {
        log.debug("Busqueda de persona moral: " + aoData.getoForm());
        
        /*
         * Codigo para el manejo de las validaciones de los campos requeridos o
         * de la aplicacion de las RN
         */
        final Map<String, Object> result = new HashMap<String, Object>();

        final DatosEntradaPaginador<Moral> send = new DatosEntradaPaginador<Moral>();

        send.parserArray(aoData.getAoData());
        send.setModelo(aoData.getoForm());

        DatosSalidaPaginador<Moral> outData = null;

        PersonaMoralBusquedaValidator pmbv = new PersonaMoralBusquedaValidator();

        Errors errors = new BindException(aoData.getoForm(), "model");
        
        try {
            /* Primero se validara que el formulario tenga al menos 1 filtro de busqueda, de lo contrario se lanzara una excepcion */
            if(pmbv.ValidarFormularioVacio(aoData.getoForm())) {
                throw new FormularioVacioException();
            }else{
                pmbv.validate(aoData.getoForm(), errors);
                if(errors.hasErrors()) {
                    outData = new DatosSalidaPaginador<Moral>();
                    procesaErroresDeCaptura(errors, result, response);
                    outData.setErroresCaptura((List)result.get(KEY_CODE_ERROR_FIELDS));
                } else {
                    outData = personaMoralBusiness.getPersonaMoralFiltro(send);                    
                }
            }
        } catch(AbstractException e) {
            log.debug("Error en la consulta [" + e.getMessage() + "]");
            outData = new DatosSalidaPaginador<Moral>();
            procesarErrorDeNegocio(e, result, response);
            outData.setErroresNegocio(e.getMessage());
        }

        outData.setsEcho(send.getsEcho());
        log.debug("Lst size: " + outData.getAaData().size());
        log.trace("entity list: " + outData.getAaData());
        
        List<Moral> personasMorales = outData.getAaData();
        
        model.addAttribute("personasMorales", personasMorales);
        
        return "busquedaEmbebidaPersonaMoralResultados";
    }

}
