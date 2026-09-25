package mx.gob.imss.distss.portal.vigencia.grupo.controller;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import mx.gob.imss.ctirss.delta.derechohabientes.service.utility.interfaces.GrupoFamiliarServiceRemote;

import mx.gob.imss.ctirss.delta.exception.derechohabiente.DerechohabientesBusinessException;
import mx.gob.imss.ctirss.delta.framework.base.controller.AbstractController;
import mx.gob.imss.ctirss.delta.framework.base.web.sso.UsuarioSSO;
import mx.gob.imss.ctirss.delta.model.ModuloEnum;
import mx.gob.imss.ctirss.delta.model.Usuario;
import mx.gob.imss.ctirss.delta.model.asegurado.AsignacionNSS;
import mx.gob.imss.ctirss.delta.model.derechohabiente.GrupoFamiliarTE;
import mx.gob.imss.ctirss.delta.model.derechohabiente.UsuarioFuncionario;
import mx.gob.imss.ctirss.delta.model.derechohabiente.negocio.PerfilUsuario;
import mx.gob.imss.ctirss.delta.model.enums.PerfilesEnum;
import mx.gob.imss.ctirss.delta.model.domicilio.Delegacion;
import mx.gob.imss.ctirss.delta.model.domicilio.Subdelegacion;
import mx.gob.imss.distss.portal.vigencia.grupo.bean.InfoSession;
import mx.gob.imss.distss.portal.vigencia.grupo.controller.validator.BusquedaAseguradoValidator;
import mx.gob.imss.distss.portal.vigencia.grupo.portal.utilities.PropertiesOpciones;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "/busqueda")
public class BusquedaAseguradoController extends AbstractController {
	
	@Autowired
	private PropertiesOpciones propertiesOpciones;
	@Autowired
	private GrupoFamiliarServiceRemote grupoFamiliarServiceRemote;
	@Autowired
	private GuardaSolicitudConsultaAsinc guardaSolicitudConsultaAsinc;
	
	private static final String ERROR_MODEL = "error";
	private static final String VISTA_BUSQUEDA = "pantallaBusqueda";
	private static final String VISTA_CONSULTA_ASEGURADO = "portalVigenciaAsegurado";
	private static final String ACCESO_DENEGADO = "pantallaAccesoDenegado";

	@RequestMapping("")
	public String pantallaBusqueda(Model model, HttpServletRequest request, HttpSession session) {
		
		session.removeAttribute("infoSession");
		session.removeAttribute("usuario");
		
		//session.invalidate();
		UsuarioSSO usuariosso = this.procesarUsuarioSSO(request);
		log.debug("el usuarioSso es: " + usuariosso);
		Usuario usuario = getUsuarioSesion(usuariosso, session );
		
		try{
			validarSesion(usuario);
			validarUsuarioSso(usuariosso);
		}catch(IllegalArgumentException e){
			return ACCESO_DENEGADO;
		}
		
		AsignacionNSS asignacion = new AsignacionNSS();
		model.addAttribute("asignacion", asignacion);
		model.addAttribute("usuario", usuario);
		session.setAttribute("usuario", usuario);
		session.setAttribute("usuarioConsulta", usuario.getUsuario());
		
		
		return VISTA_BUSQUEDA;
	}
	
	
	
	@RequestMapping("/home")
	public String honmePantallaBusqueda(Model model, HttpServletRequest request, HttpSession session) {
		
		Usuario usuario = (Usuario)session.getAttribute("usuario");
		
		log.debug("llegue al nuevo home con usuario [" + usuario);
		try{
			validarSesion(usuario);
		}catch(IllegalArgumentException e){
			return ACCESO_DENEGADO;
		}
		
		AsignacionNSS asignacion = new AsignacionNSS();
		model.addAttribute("asignacion", asignacion);
		model.addAttribute("usuario", usuario);
		return VISTA_BUSQUEDA;
	}
	
	/**
     * Metodo que valida el formulario de captura de persona fisica (combos, CURP y RFC unicamente)
     * @param oForm
     * @param response
     * @return
     */
    @RequestMapping(value = "/validaciones", method = RequestMethod.POST)
    public @ResponseBody Map<String, ? extends Object> validarFormulario(final @RequestBody AsignacionNSS oForm, final HttpServletResponse response) {
        log.trace("entramos a busqueda de persona para validar el objeto de formulario --> " + ReflectionToStringBuilder.toString(oForm, ToStringStyle.MULTI_LINE_STYLE));
        final Map<String, Object> result = new HashMap<String, Object>();

        final Errors errors = new BindException(oForm, "model");
        new BusquedaAseguradoValidator().validate(oForm, errors);
        
        if (errors.hasErrors()) {
            procesaErroresDeCaptura(errors, result, response);
            return result;
        }
        result.put("oForm", oForm);

        return result;
    }
	
	@RequestMapping(value = "/asegurado",method = RequestMethod.POST)
	public String iniciarBusquedaAsegurado(@ModelAttribute AsignacionNSS asignacion, BindingResult result, Model model, HttpServletRequest request, HttpSession session){
		
		UsuarioSSO usuariosso = this.procesarUsuarioSSO(request);
		Usuario usuario = getUsuarioSesion(usuariosso, session);
		
		try{
			validarSesion(usuario);
		}catch(IllegalArgumentException e){
			return ACCESO_DENEGADO;
		}
		
		
		String nss = asignacion.getNss();
		//String curp = asignacion.getCurp();
		Long idPersona = asignacion.getIdPersona();
		Long idAsignacion = asignacion.getIdAsignacionNSS();
		
		GrupoFamiliarTE grupoFamTE = new GrupoFamiliarTE();
		
		model.addAttribute("usuario", usuario);
		
		// ---------------------------------------------------------------
		new BusquedaAseguradoValidator().validate(asignacion, result);

		if(result.hasErrors()) {
			model.addAttribute("nss",nss);
			log.warn("Se encontraron errores de captura");
			return VISTA_BUSQUEDA;
		}
		
		// ------------------------------------------------------------------
		if(!StringUtils.isBlank(nss)) {
			if(nss.length() == 10){										
				nss = nss+generaDigitoVerificador(nss);								
			}
			asignacion.setNss(nss);
			
			model.addAttribute("nss",nss);
			
			//Solo si el id de persona y el id de asignacion vienen nulos haremos la busqueda
			if(idPersona == null && idAsignacion == null) {
				try {
					grupoFamTE = grupoFamiliarServiceRemote.getGpoFamSinPersona(nss);
					log.debug("en el front despues de la consulta de los WS: " + grupoFamTE);
					asignacion = grupoFamTE.getAsignacionNSS();
					
					if(asignacion.getIdPersona() != null) {
						idPersona = asignacion.getIdPersona();
					}
					
					if(asignacion.getIdAsignacionNSS() != null) {
						idAsignacion = asignacion.getIdAsignacionNSS();
					}
				} catch (DerechohabientesBusinessException e) {
					model.addAttribute(ERROR_MODEL, e.getMessage());
				}  catch (Exception e) {
					// model.addAttribute(ERROR_MODEL, "Ocurri&oacute; un error al consultar el NSS");
					if (e.getMessage().toString().contains("con fecha de  baja")) {
						model.addAttribute(ERROR_MODEL, "El N&uacute;mero de Seguridad Social ingresado, cuenta con tr&aacute;mite de correcci&oacute;n de datos, acuda a una Subdelegaci&oacute;n a realizar la aclaraci&oacute;n pertinente.");
					} else {
						model.addAttribute(ERROR_MODEL, "Ocurri&oacute; un error al consultar el NSS");
					}
				}
			}
		}
		
		model.addAttribute("asignacion",asignacion);
		
		
		
		// -----------------------------------------------------------
		// Regresamos a la pantalla de b�squeda y se muestra el error
		// -----------------------------------------------------------
		if(model.containsAttribute(ERROR_MODEL)) {
			return VISTA_BUSQUEDA;
		}	
		
		//se guarda la solicitud de consulta asincronamente
		guardaSolicitudConsultaAsinc.guardaSolicitudConsulta(asignacion, usuario);
		log.debug("saliendo del llamado en teoria el otro se esta ejecutando");
		
		if(idPersona == null)
			idPersona = 0L;
		
		if( idAsignacion == null )
			idAsignacion = 0L;
		
		
		//bloque para recuperar los tags nuevos de los articulos
		if (grupoFamTE != null){
			if (grupoFamTE.isArticulo82()){
				log.debug("aplica art 82");
				model.addAttribute("articulo82",true);
			} 
			if (grupoFamTE.isArticulo83() && grupoFamTE.getTiemposEspera()!= null){
				log.debug("aplica art 83");
				log.debug("el tiempo de espera acumulado es de: " + grupoFamTE.getTiemposEspera() + " meses");
				model.addAttribute("tiempoMeses", Integer.parseInt(grupoFamTE.getTiemposEspera().replace("M", "")));
				model.addAttribute("articulo83",true);
			} 
			if (grupoFamTE.isArticulo84()){
				log.debug("aplica art 84");
				model.addAttribute("articulo84",true);
			}
		}
		
		model.addAttribute("idPersona",idPersona);
		model.addAttribute("idAsignacionNss", idAsignacion);
		
		return VISTA_CONSULTA_ASEGURADO;
	}
	
	
	
	public Usuario getUsuarioSesion(UsuarioSSO usuariosso, HttpSession session) {
		
		
		
		Usuario usuario = new Usuario();
		usuario.setUsuario(usuariosso.getNombre());

		PerfilUsuario pu = new PerfilUsuario();
		pu.setDescripcion(usuariosso.getNombre());
		usuario.setPerfilUsuario(pu);

		// Se crean los objetos necesarios para ligar el usuario con la
		// subdelegacion y delegacion.
		
		if(usuariosso.getDelegacion() != null  && usuariosso.getSubdelegacion() != null){
			// LUDS Se agrego esta validacion para que si es en caso de un usuario EXTERNO no le llega la delegacion.
			UsuarioFuncionario uf = new UsuarioFuncionario();
			uf.setDelegacion(new Delegacion());
			uf.getDelegacion().setId(usuariosso.getDelegacion().longValue());
			uf.setSubdelegacion(new Subdelegacion());
			uf.setUsuario(usuario);
			uf.getSubdelegacion().setId(usuariosso.getSubdelegacion().longValue());
			usuario.setUsuarioFuncionario(uf);
		}
		
		if(usuariosso.getIdPersona()!= null){
			usuario.setCveIdUsuario(usuariosso.getIdPersona().toString());
		}
		
		InfoSession sessionBean = null;
		sessionBean = (InfoSession) session.getAttribute("infoSession");
		
		if(sessionBean == null){
			sessionBean = new InfoSession();
			sessionBean.setFechaSistema(new Date());
			Calendar cal = Calendar.getInstance();
			cal.add(Calendar.MINUTE,30);
			sessionBean.setFechaAvisoSession(cal.getTime());
			cal.add(Calendar.MINUTE,10);
			sessionBean.setFechaFinSession(cal.getTime());
			sessionBean.setValidaAvisoSession(true);
			session.setAttribute("infoSession", sessionBean);
		}
		
		
		return usuario;
	}
	
	public void validarSesion(Usuario usuario) throws IllegalArgumentException{
		log.debug("entro a validar el usuario");
		log.debug("el usuario a validar es: " + usuario);
		if(usuario != null && usuario.getUsuarioFuncionario() != null && usuario.getUsuarioFuncionario().getDelegacion() != null ){
			Long idDelegacion = usuario.getUsuarioFuncionario().getDelegacion().getId();
			Map<String, Boolean> mapaOpciones = propertiesOpciones.getOpciones();
			Boolean valido = mapaOpciones.get(idDelegacion.toString());
			if(valido != null && !valido){
				throw new IllegalArgumentException();
			}
		}
	}
	
	public void validarUsuarioSso(UsuarioSSO usuarioSso) throws IllegalArgumentException {
		Boolean usuarioValido = false;
		log.debug("el usuario a validar es: " + usuarioSso);

		String[] perfiles = usuarioSso.getPerfiles();
		String perfil = usuarioSso.getPerfil();
		String[] modulo = usuarioSso.getSistemas();
		boolean asignoPerfil = false;
		boolean asignoModulo = false;

		if (perfiles != null) {
			for (int indice = 0; indice < perfiles.length; indice++) {
				if (perfiles[indice].equalsIgnoreCase(PerfilesEnum.CONSULTA_VIGENCIA.getDesc())) {
					asignoPerfil = true;
					usuarioValido = true;
				}
			}
		}

		if (!asignoPerfil) {
			if (StringUtils.isNotEmpty(perfil)) {
				if (perfil.equalsIgnoreCase(PerfilesEnum.CONSULTA_VIGENCIA.getDesc())) {
					usuarioValido = true;
				}
			}
		}

		log.debug("el modulo es: " + Arrays.toString(modulo));
		if (modulo != null) {
			for (int indice = 0; indice < modulo.length; indice++) {
				if (modulo[indice].equalsIgnoreCase(ModuloEnum.CONSULTA_VIGENCIA.getDescripcion())) {
					asignoModulo = true;
				}
			}
		}

		if (!usuarioValido || !asignoModulo) {
			throw new IllegalArgumentException();
		}

	}
	
	
	@RequestMapping(value = "/validar/session", method = RequestMethod.POST)
	public @ResponseBody void validarDatosConsulta(Model model, HttpSession session, HttpServletRequest request,
			HttpServletResponse response) {
		this.log.debug("entre a validar la session");
		try{
		
			InfoSession sessionBean = null;
			sessionBean = (InfoSession) session.getAttribute("infoSession");
			if(sessionBean != null){
				sessionBean.setValidaAvisoSession(false);
				session.removeAttribute("infoSession");
				session.setAttribute("infoSession", sessionBean);
			}
		}catch(Exception e){
			this.log.error("ocurrio un error no cachado", e);
		}
		
		
	}
	
	@RequestMapping(value = "/limpiarSesion")
	public @ResponseBody Boolean limpiarSesion(Model model, HttpServletRequest request, HttpSession session) {
		log.debug("entre a limpiar la sesion *********************");
		session.invalidate();
		return null;
	}
	
	private Integer generaDigitoVerificador(String nss){  
		int suma = 0;
		int resultado = 0;
		for(int i = 1 ; i <= nss.length() ; i++){
			if(i%2==0){
				int multiplicacion = (Integer.parseInt(nss.charAt(i-1)+"")) * 2;
				if(multiplicacion > 9 ){
					suma = suma + ((multiplicacion-10)+1);
				}else{
					suma = suma + multiplicacion;
				}
			}else{
				suma = suma + (Integer.parseInt(nss.charAt(i-1)+""));
			}	
		}
		int modulo = suma%10;
		if(modulo == 0 ){
			resultado = 0;
		}else if( modulo < 10){
			resultado = 10-modulo;
		}
		return resultado;
	}
	
	@RequestMapping(value = "/revalidar/session", method = {RequestMethod.POST, RequestMethod.GET} )
	public @ResponseBody void revalidarDatosConsulta(
			Model model, HttpSession session, HttpServletRequest request,
			HttpServletResponse response) {
		this.log.debug("entre a revalidar la session");
		try{
		
			InfoSession sessionBean = null;
			sessionBean = (InfoSession) session.getAttribute("infoSession");
			
			if(sessionBean != null){
				sessionBean.setValidaAvisoSession(true);
				Calendar cal = Calendar.getInstance();
				cal.add(Calendar.MINUTE,3);
				sessionBean.setFechaAvisoSession(cal.getTime());
				cal.add(Calendar.MINUTE,1);
				sessionBean.setFechaFinSession(cal.getTime());
				sessionBean.setValidaAvisoSession(true);
				
				sessionBean.setFechaSistema(new Date());
				session.removeAttribute("infoSession");
				session.setAttribute("infoSession", sessionBean);
			}
		}catch(Exception e){
			this.log.error("ocurrio un error no cachado", e);
		}
		
		
	}


}