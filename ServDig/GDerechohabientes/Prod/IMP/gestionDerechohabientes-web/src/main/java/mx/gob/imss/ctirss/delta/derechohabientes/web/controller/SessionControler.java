package mx.gob.imss.ctirss.delta.derechohabientes.web.controller;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import mx.gob.imss.ctirss.delta.derechohabientes.service.utility.interfaces.UmfServiceRemote;
import mx.gob.imss.ctirss.delta.derechohabientes.web.bean.Busqueda;
import mx.gob.imss.ctirss.delta.exception.derechohabiente.DerechohabientesBusinessException;
import mx.gob.imss.ctirss.delta.framework.base.controller.AbstractController;
import mx.gob.imss.ctirss.delta.framework.base.web.sso.UsuarioSSO;
import mx.gob.imss.ctirss.delta.gestion.solicitud.service.interfaces.SolicitudBusinessRemote;
import mx.gob.imss.ctirss.delta.model.Usuario;
import mx.gob.imss.ctirss.delta.model.derechohabiente.UnidadMedicaFamiliar;
import mx.gob.imss.ctirss.delta.model.derechohabiente.UsuarioFuncionario;
import mx.gob.imss.ctirss.delta.model.derechohabiente.negocio.PerfilUsuario;
import mx.gob.imss.ctirss.delta.model.domicilio.Delegacion;
import mx.gob.imss.ctirss.delta.model.domicilio.Subdelegacion;
import mx.gob.imss.ctirss.delta.model.enums.PerfilesEnum;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Fisica;
import mx.gob.imss.ctirss.delta.model.util.Constants;
import mx.gob.imss.ctirss.gestionpersonas.servicios.business.PersonaBusinessRemote;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindException;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "/session/*")
public class SessionControler extends AbstractController{
	
	
	private static final String KEY_PATRON_IMSS = "patronIMSS";
	private static final String KEY_GRUPO_FAMILIAR = "miGrupoFamiliar";
	private static final String KEY_PATRONES_ASEGURADO = "patrones";
	private static final String KEY_PATRON_ULTIMO_MOV = Constants.PATRON_SUJETO;
	
	@Autowired 
	private PersonaBusinessRemote personaBusinessRemote;
	@Autowired 
	private UmfServiceRemote umfServiceRemote;
	@Autowired
	private SolicitudBusinessRemote solicitudBusiness;
	
	
	
	@RequestMapping(value = "terminateSession",method=RequestMethod.GET)
	public String terminateSession(HttpSession ses){

		if(ses!=null){
			ses.invalidate();
		}
		return "inicio";
	}
		
	
	 /**
	  * Metodo para verificar si el usuario cuenta con los privilegios necesarios
	  * @param rolesToCheck
	  * @return Un booleano verdadero en caso de contar con el privilegio.
	  */
	 public Boolean checkRolesSSO(String[] rolesToCheck, String sso_perfil) {
		 Boolean hasGrantedAuthority = Boolean.FALSE;
		 if(rolesToCheck == null || rolesToCheck.length <=0){
			 this.log.warn("No se recibio la lista de roles a verificar");
			 return hasGrantedAuthority;
		 }
		 String[]  authorities = null;
		 if(!StringUtils.isEmpty(sso_perfil)){
		   authorities =   sso_perfil.split(",");
		 }
		 if(authorities != null && authorities.length > 0){
			 for (String authority : authorities) {
				 for( String roleToCheck : rolesToCheck){
					 this.log.debug("EL ROL DE OPEN AM ES [" +authority.trim()+"]");
					 if(roleToCheck.equals(authority.trim())){
						 this.log.debug("Se encontro el rol ( privilegio ) -- TRUE");
						 return hasGrantedAuthority.TRUE;
					 }
				 }
			}
			 
		 }
		 return hasGrantedAuthority;
	 }
	 
	 /**
		 * Metodo para establecer las variables de sesion del usuario
		 * @param session
		 * @param usuario
		 * @param perfil
		 */
		public void setVariablesSesionUsuario(HttpSession session, Usuario usuario) {
			//Creamos el objeto para el encabezado
			Busqueda busqueda = null;
			busqueda = (Busqueda)session.getAttribute("usuario");
			if(busqueda == null){
				busqueda = new Busqueda();
				//Establecemos los atributos en el objeto busqueda
				busqueda.setFechaSistema(new Date());
				busqueda.setUsuario(usuario.getUsuario());
				Calendar cal = Calendar.getInstance();
				cal.add(Calendar.MINUTE,30);
				busqueda.setFechaAvisoSession(cal.getTime());
				cal.add(Calendar.MINUTE,10);
				busqueda.setFechaFinSession(cal.getTime());
				busqueda.setValidaAvisoSession(true);
			}
				log.debug(" la fecha sistemas es [" +busqueda.getFechaSistema()+ "] fecha setFechaAvisoSession " + 
					busqueda.getFechaAvisoSession() +"] la fecha fin session es [" +busqueda.getFechaFinSession() +"]");
				
			//Hacemos la verificacion para saber si mostraremos ls campos de perfil, delegacion  y umf
				busqueda.setPerfil(usuario.getPerfilUsuario().getIdPerfilUsuario());
				if(usuario.getUsuarioFuncionario() != null) { 
					busqueda.setDelegacion(usuario.getUsuarioFuncionario().getDelegacion().getDescripcion());
					if(usuario.getUsuarioFuncionario().getUnidadMedicaFamiliar() != null){
						busqueda.setUmf(usuario.getUsuarioFuncionario().getUnidadMedicaFamiliar().getNombreCorto());
					}
				}
				
				//Establecemos los objetos en la sesion
			session.removeAttribute("usuario");	
			session.setAttribute("usuario", busqueda);
			session.setAttribute(Usuario.SES_NAME, usuario);
		}
		
		
		/**
		 * Metodo para llenar un objeto usuario a parter del request
		 * @param request
		 * @return Usuario
		 */
		public Usuario setUsuarioFromSSO(UsuarioSSO usuarioSSO ) throws Exception{
			Usuario usuario = new Usuario();
			PerfilUsuario perfilUsuario = new PerfilUsuario();
			UnidadMedicaFamiliar unidadMedicaFamiliar = null;
			
			boolean isTramiador = false;
			boolean isJefeDeptoOficina = false;
			boolean isNormativo = false;
			
			
			//Establecemos los datos necesarios del usuario a partir del usuarioSSO
			
			//Establecemos el perfil del usuario en el objeto Usuario
			usuario.setUsuario(usuarioSSO.getNombre());
			usuario.setCveIdUsuario(usuarioSSO.getCurp());
			
			//se iteran los prefiles del objeto como pricipal el roll de tramitador
			String [] perfiles = usuarioSSO.getPerfiles();
			
			String perfil = usuarioSSO.getPerfil();
			boolean asignoPerfil = false;
			if(perfiles != null){
				for(int indice = 0; indice < perfiles.length; indice++){
					if(perfiles[indice].equalsIgnoreCase(PerfilesEnum.TRAMITADOR.getDesc())){
						perfilUsuario.setIdPerfilUsuario(PerfilesEnum.TRAMITADOR.getId());
						perfilUsuario.setDescripcion(PerfilesEnum.TRAMITADOR.getDesc());
						asignoPerfil = true;
						isTramiador = true;
						break;
					}else if(perfiles[indice].equalsIgnoreCase(PerfilesEnum.JEFE_DEPTO_AFIL_VIGENCIA.getDesc())){
						perfilUsuario.setIdPerfilUsuario(PerfilesEnum.JEFE_DEPTO_AFIL_VIGENCIA.getId());
						perfilUsuario.setDescripcion(PerfilesEnum.JEFE_DEPTO_AFIL_VIGENCIA.getDesc());
						asignoPerfil = true;
						isJefeDeptoOficina=true;
					}else if(perfiles[indice].equalsIgnoreCase(PerfilesEnum.JEFE_OFICINA_VIGENCIA.getDesc())){
						perfilUsuario.setIdPerfilUsuario(PerfilesEnum.JEFE_OFICINA_VIGENCIA.getId());
						perfilUsuario.setDescripcion(PerfilesEnum.JEFE_OFICINA_VIGENCIA.getDesc());
						asignoPerfil = true;
						isJefeDeptoOficina=true;
					}else if(perfiles[indice].equalsIgnoreCase(PerfilesEnum.JEFE_DE_DEPARTAMENTO_VIGENCIA.getDesc())){
						perfilUsuario.setIdPerfilUsuario(PerfilesEnum.JEFE_DE_DEPARTAMENTO_VIGENCIA.getId());
						perfilUsuario.setDescripcion(PerfilesEnum.JEFE_DE_DEPARTAMENTO_VIGENCIA.getDesc());
						asignoPerfil = true;
						isJefeDeptoOficina=true;
					}else if(perfiles[indice].equalsIgnoreCase(PerfilesEnum.TITULAR_DE_LA_SUBJEFATURA_DE_DIVISION_DE_PRESTACIONES_EN_ESPECIE.getDesc())){
						perfilUsuario.setIdPerfilUsuario(PerfilesEnum.TITULAR_DE_LA_SUBJEFATURA_DE_DIVISION_DE_PRESTACIONES_EN_ESPECIE.getId());
						perfilUsuario.setDescripcion(PerfilesEnum.TITULAR_DE_LA_SUBJEFATURA_DE_DIVISION_DE_PRESTACIONES_EN_ESPECIE.getDesc());
						asignoPerfil = true;
						isNormativo = true;
					}else if(perfiles[indice].equalsIgnoreCase(PerfilesEnum.JEFE_DE_DEPARTAMENTO_DE_SUPERVISION_DE_AFILIACION_Y_VIGENCIA.getDesc())){
						perfilUsuario.setIdPerfilUsuario(PerfilesEnum.JEFE_DE_DEPARTAMENTO_DE_SUPERVISION_DE_AFILIACION_Y_VIGENCIA.getId());
						perfilUsuario.setDescripcion(PerfilesEnum.JEFE_DE_DEPARTAMENTO_DE_SUPERVISION_DE_AFILIACION_Y_VIGENCIA.getDesc());
						asignoPerfil = true;
						if(usuarioSSO.getUmf() != null){
							usuario.setIdUmf(usuarioSSO.getUmf().longValue());
						}else{
							usuario.setIdUmf(Long.valueOf(0));
						}
						usuario.setUsuarioFuncionario(new UsuarioFuncionario());
						usuario.getUsuarioFuncionario().setDelegacion(new Delegacion());
						if(usuarioSSO.getDelegacion() != null){
							usuario.getUsuarioFuncionario().getDelegacion().setId(usuarioSSO.getDelegacion().longValue());
						}else {
							usuario.getUsuarioFuncionario().getDelegacion().setId(Long.valueOf(0));
						}
						if(usuarioSSO.getSubdelegacion() != null) {
							usuario.setCveIdSubdelegacion(usuarioSSO.getSubdelegacion().longValue());
						} else{
							usuario.setCveIdSubdelegacion(Long.valueOf(0));
						}
					}
				}
				
			}
			if(!asignoPerfil){
				if(StringUtils.isNotEmpty(perfil)){
					if(perfil.equalsIgnoreCase(PerfilesEnum.TRAMITADOR.getDesc())){
						perfilUsuario.setIdPerfilUsuario(PerfilesEnum.TRAMITADOR.getId());
						perfilUsuario.setDescripcion(PerfilesEnum.TRAMITADOR.getDesc());
						isTramiador = true;
						log.debug("se seteo como tramitador");
					}else if(perfil.equalsIgnoreCase(PerfilesEnum.JEFE_DEPTO_AFIL_VIGENCIA.getDesc())){
						perfilUsuario.setIdPerfilUsuario(PerfilesEnum.JEFE_DEPTO_AFIL_VIGENCIA.getId());
						perfilUsuario.setDescripcion(PerfilesEnum.JEFE_DEPTO_AFIL_VIGENCIA.getDesc());
						isJefeDeptoOficina=true;
						log.debug("se seteo como jefe deptor");
					}else if(perfil.equalsIgnoreCase(PerfilesEnum.JEFE_OFICINA_VIGENCIA.getDesc())){
						perfilUsuario.setIdPerfilUsuario(PerfilesEnum.JEFE_OFICINA_VIGENCIA.getId());
						perfilUsuario.setDescripcion(PerfilesEnum.JEFE_OFICINA_VIGENCIA.getDesc());
						isJefeDeptoOficina=true;
					}else if(perfil.equalsIgnoreCase(PerfilesEnum.TITULAR_DE_LA_SUBJEFATURA_DE_DIVISION_DE_PRESTACIONES_EN_ESPECIE.getDesc())){
						perfilUsuario.setIdPerfilUsuario(PerfilesEnum.TITULAR_DE_LA_SUBJEFATURA_DE_DIVISION_DE_PRESTACIONES_EN_ESPECIE.getId());
						perfilUsuario.setDescripcion(PerfilesEnum.TITULAR_DE_LA_SUBJEFATURA_DE_DIVISION_DE_PRESTACIONES_EN_ESPECIE.getDesc());
						asignoPerfil = true;
						isNormativo = true;
					}else if(perfil.equalsIgnoreCase(PerfilesEnum.JEFE_DE_DEPARTAMENTO_VIGENCIA.getDesc())){
						perfilUsuario.setIdPerfilUsuario(PerfilesEnum.JEFE_DE_DEPARTAMENTO_VIGENCIA.getId());
						perfilUsuario.setDescripcion(PerfilesEnum.JEFE_DE_DEPARTAMENTO_VIGENCIA.getDesc());
						asignoPerfil = true;
						isJefeDeptoOficina=true;
					}else{
						throw new DerechohabientesBusinessException("El usuario no cuenta con un Perfil valido para el Sistema");
					}
					
				}else{
					throw new DerechohabientesBusinessException("El usuario no cuenta con un Perfil valido para el Sistema");
				}
				
			}
			usuario.setPerfilUsuario(perfilUsuario);	
			
			//se valida si es tramitador ya que es obligatoria la UMF
			if(isTramiador){
				if(usuarioSSO.getUmf() != null) {
					log.debug("si es tramitador y la umf no es nula");
					usuario.setIdUmf(usuarioSSO.getUmf().longValue());
					//usuario.setIdUmf(319L);
					//Consultamos la informacion de la unidad medica familiar del usuario
					unidadMedicaFamiliar = umfServiceRemote.getUnidadMedicaFamiliarById(usuario.getIdUmf());
					//Si encontramos la umf establecemos los datos de la misma en el objeto usuario
					if(unidadMedicaFamiliar != null) {
						usuario.setUsuarioFuncionario(new UsuarioFuncionario());
						usuario.getUsuarioFuncionario().setDelegacion(unidadMedicaFamiliar.getSubdelegacion().getDelegacion());
						usuario.getUsuarioFuncionario().setSubdelegacion(unidadMedicaFamiliar.getSubdelegacion());
						usuario.getUsuarioFuncionario().setUnidadMedicaFamiliar(unidadMedicaFamiliar);
						usuario.setCveIdSubdelegacion(unidadMedicaFamiliar.getSubdelegacion().getId());
					}else{
						throw new DerechohabientesBusinessException("No se encontro la UMF del usuario en BD");
					}
					
				}else{
					throw new DerechohabientesBusinessException("El usuario no cuenta con UMF en SSO");
				}
			}// en otro caso es algun jefe asi que se setea la subdelegacion 
			else if (isJefeDeptoOficina) {
				if (usuarioSSO.getSubdelegacion() != null) {
					log.debug("si es jefe  y la subdelegacion no es nula");
					Subdelegacion subdelegacion = solicitudBusiness.getDatosSubdelegacion(usuarioSSO.getSubdelegacion().longValue());
					if (subdelegacion != null) {
						usuario.setUsuarioFuncionario(new UsuarioFuncionario());
						usuario.getUsuarioFuncionario().setDelegacion(subdelegacion.getDelegacion());
						usuario.getUsuarioFuncionario().setSubdelegacion(subdelegacion);
						usuario.setCveIdSubdelegacion(subdelegacion.getId());
					} else {
						throw new DerechohabientesBusinessException(
								"El usuario no cuenta con Subdelegacion o esta no es valida");
					}
				} else {
					throw new DerechohabientesBusinessException("El usuario no cuenta con Subdelegacion");
				}
			}
			usuario.setNomNombre(usuarioSSO.getNombre());
		
			
			return usuario;
		}
		
		/**
		 * Metodo para verificar si ya esta iniciada una session y de ser asi devolver el objeto
		 * Si no esta iniciada una session llena el objeto usuario y lo pone en sesion
		 * @param session
		 * @param request
		 * @param perfil
		 * @return
		 */
		public Usuario validarSesionUsuario(HttpSession session, UsuarioSSO usaurioSSO) throws Exception {
			//Tratamos de obtener el objeto usuario de la sesion
			Usuario usuario = (Usuario) session.getAttribute(Usuario.SES_NAME);
			
			//Si no esta el usuario en sesion buscamos en el request la informacion del usuarioSso y lo ponemos en sesion
			if(usuario == null) {
				usuario = this.setUsuarioFromSSO(usaurioSSO);
			}
			this.setVariablesSesionUsuario(session, usuario);
			//retornamos el usuario encontrado
			return usuario;
		}
		
		
		/**
		 * Metodo que se encarga de setear los atributos de OPENAM aun usuario de session ya existente cuando el usuario en normativo 
		 * @param session
		 * @param usaurioSSO
		 * @return
		 * @throws Exception
		 */
		public Usuario validarSesionUsuarioNormativo(HttpSession session, UsuarioSSO usuarioSSO, Busqueda usuarioBusqueda) throws Exception {
			
			//Se manda a setear la infomracion del usuario de la pantalla
			log.debug("etopy en el seteo de la sesion del normativo");
			if(usuarioBusqueda.getPerfil() == PerfilesEnum.TRAMITADOR.getId().longValue()) {
				log.debug("estoy en el seteo tramitador umf [" +usuarioBusqueda.getUmf()+"]" );
				usuarioSSO.setPerfil(PerfilesEnum.TRAMITADOR.getDesc());
				usuarioSSO.setUmf(Integer.valueOf(usuarioBusqueda.getUmf()));
			}else if(usuarioBusqueda.getPerfil() == PerfilesEnum.JEFE_DEPTO_AFIL_VIGENCIA.getId().longValue()) {
				log.debug("estoy en el seteo JEFE");
				usuarioSSO.setPerfil(PerfilesEnum.JEFE_DEPTO_AFIL_VIGENCIA.getDesc());
				//usuarioSSO.setUmf(null);
			}else {
				throw new DerechohabientesBusinessException("El usario normativo no tiene un perfil valido");
			}
			usuarioSSO.setDelegacion(Integer.valueOf(usuarioBusqueda.getDelegacion()));
			usuarioSSO.setSubdelegacion(Integer.valueOf(usuarioBusqueda.getSubDelegacion()));
			usuarioSSO.setPerfiles(null);
			log.debug("la >UMF que tiene el usuario es[" +usuarioSSO.getUmf()+ "]");
			log.debug("la subdelegacion que tiene el usuario es[" +usuarioSSO.getSubdelegacion()+ "]");
			
			Usuario usuario = this.setUsuarioFromSSO(usuarioSSO);
			log.debug("el usuario quedo como [" +usuario.toString()+ "]");
			log.debug("el usuario quedo como [" +usuario+ "]");
			this.setVariablesSesionUsuario(session, usuario);
			//retornamos el usuario encontrado
			return usuario;
		}
		
		
		
		
		public void limpiarSession(HttpSession session) {
			session.removeAttribute("domicilioAsegurado");
			session.removeAttribute("integranteCambioMedicoSession");
			session.removeAttribute("solicitudActiva");
			session.removeAttribute(KEY_GRUPO_FAMILIAR);
			session.removeAttribute(KEY_PATRONES_ASEGURADO);
			session.removeAttribute(KEY_PATRON_IMSS);
			session.removeAttribute("datosSolicitudSession");
			session.removeAttribute("datosIntegranteCorreccionSession");
			session.removeAttribute(Constants.ASIGNACION_NSS_SESSION_NAME);
			session.removeAttribute(KEY_PATRON_ULTIMO_MOV);		
			session.removeAttribute("conAsegurado");
			session.removeAttribute("elemento");
			session.removeAttribute("conDetalleS");
			session.removeAttribute(Constants.CABEZA_GRUPO_FAM_SESSION);
			session.removeAttribute("afectadoAutorizacionCircunscripcion");
			session.removeAttribute("mostrarBoton");
			session.removeAttribute("exception");
			session.removeAttribute("error");
			session.removeAttribute("solicitud");
			session.removeAttribute("reporte");
			
		}
		
		
		@RequestMapping(value = "/validar/session", method = RequestMethod.POST)
		public @ResponseBody void validarDatosConsulta(Model model, HttpSession session, HttpServletRequest request,
				HttpServletResponse response) {
			this.log.debug("entre a validar la session");
			try{
			
				Busqueda busqueda = null;
				busqueda = (Busqueda)session.getAttribute("usuario");
				if(busqueda != null){
					busqueda.setValidaAvisoSession(false);
					session.removeAttribute("usuario");
					session.setAttribute("usuario", busqueda);
				}
			}catch(Exception e){
				this.log.error("ocurrio un error no cachado", e);
			}
			
			
		}
		
		
		@RequestMapping(value = "/revalidar/session", method = {RequestMethod.POST, RequestMethod.GET} )
		public @ResponseBody void revalidarDatosConsulta(
				Model model, HttpSession session, HttpServletRequest request,
				HttpServletResponse response) {
			this.log.debug("entre a revalidar la session");
			try{
			
				Busqueda busqueda = null;
				busqueda = (Busqueda)session.getAttribute("usuario");
				
				if(busqueda != null){
					busqueda.setValidaAvisoSession(true);
					Calendar cal = Calendar.getInstance();
					cal.add(Calendar.MINUTE,30);
					busqueda.setFechaAvisoSession(cal.getTime());
					cal.add(Calendar.MINUTE,10);
					busqueda.setFechaFinSession(cal.getTime());
					busqueda.setValidaAvisoSession(true);
					
					busqueda.setFechaSistema(new Date());
					session.removeAttribute("usuario");
					session.setAttribute("usuario", busqueda);
				}
			}catch(Exception e){
				this.log.error("ocurrio un error no cachado", e);
			}
			
			
		}


}