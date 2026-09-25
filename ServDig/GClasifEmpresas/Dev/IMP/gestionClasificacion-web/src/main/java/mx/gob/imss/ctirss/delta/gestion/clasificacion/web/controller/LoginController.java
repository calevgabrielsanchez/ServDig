/**
 *  
 *  @Cliente: Instituto Mexicano del Seguro Social
 *  @Autor: Lucio Duran Silva
 *  @Proyecto: gestionClasificacion
 *  @Archivo:LoginController.java
 *  @Paquete:mx.gob.imss.ctirss.delta.gestion.clasificacion.web.controller
 *  @Fecha:07/02/2012
 */
package mx.gob.imss.ctirss.delta.gestion.clasificacion.web.controller;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.Properties;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import mx.gob.imss.ctirss.delta.framework.base.controller.AbstractController;
import mx.gob.imss.ctirss.delta.framework.base.web.sso.UsuarioSSO;
import mx.gob.imss.ctirss.delta.gestion.domicilio.service.interfaces.domicilio.DomicilioServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.model.Usuario;
import mx.gob.imss.ctirss.delta.model.clasificacion.CodigoRolClasificacion;
import mx.gob.imss.ctirss.delta.model.derechohabiente.UsuarioFuncionario;
import mx.gob.imss.ctirss.delta.model.derechohabiente.negocio.PerfilUsuario;
import mx.gob.imss.ctirss.delta.model.domicilio.Delegacion;
import mx.gob.imss.ctirss.delta.model.domicilio.Subdelegacion;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Fisica;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.support.SessionStatus;

/**
 * @author Lucio Duran Silva
 *
 */
@Controller
@RequestMapping(value="/login")
public class LoginController extends AbstractController{
	
	@Autowired
	DomicilioServiceBusinessRemote domicilioService;
	
	@RequestMapping(method=RequestMethod.GET)
    public String login(Model model){
        model.addAttribute("usuario", new Usuario());
        return "login";
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////
	//Produccion/Stage
	///////////////////////////////////////////////////////////////////////////////////////////
	
	@RequestMapping(value="/entrar",method=RequestMethod.GET)
	public String entrar(@ModelAttribute Usuario usuario, BindingResult result,
			Model model, SessionStatus status, HttpSession session,
			HttpServletRequest request) {

		this.log.debug("::: Entre a LoginController");
		UsuarioSSO sso = this.procesarUsuarioSSO(request);
		this.log.debug("::: Datos de usuario: " + sso.toString());
		
		Subdelegacion subdelegacion = new Subdelegacion();
		//CONFIGURACION INICIAL
		
		if (sso != null) {
		
			ServletContext context = session.getServletContext();

			String rolJefeDeptoAfilVigenciaCe = context.getInitParameter("jefeDepartamentoAfiliacionVigencia");
			String rolJefeOficina = context.getInitParameter("jefeOficina");
			String rolVentanillaCe = context.getInitParameter("ventanillaCE");
			String rolNormativoCe = context.getInitParameter("normativoCE");
			String rolDelegadoCe = context.getInitParameter("delegado");
			String rolSubDelegadoCe = context.getInitParameter("subdelegado");
			String rolJefeOficinaParaCobrosCe = context.getInitParameter("jefeOficinaParaCobros");			
									
			String[] arrayRolJefeDeptoAfilVigenciaCe = {rolJefeDeptoAfilVigenciaCe};
			String[] arrayRolJefeOficina = {rolJefeOficina};
			String[] arrayRolVentanillaCe = {rolVentanillaCe};
			String[] arrayRolNormativoCe = {rolNormativoCe};
			String[] arrayRolDelegado = {rolDelegadoCe};
			String[] arrayRolSubDelegado = {rolSubDelegadoCe};
			String[] arrayRolJefeOficinaParaCobros = {rolJefeOficinaParaCobrosCe};

			usuario.setUsuario(sso.getNombre().toUpperCase());
			usuario.setCveIdUsuario(sso.getNombre().toUpperCase());
			
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

			PerfilUsuario pu = new PerfilUsuario();

			//Modificacion para que el usuario tome el rol de mayor jerarquia al iniciar sesion
			//Se agrega funcionalidad para rol de DELEGADO, SUBDELEGADO y JEFE DE OFICINA PARA COBROS
			//los cuales solo pueden ser nivel delegacional para el delegado y subdelegacional para
			//el subdelegado y jefe de oficina para cobros
			// Mm 4487140/WO1677390

			if (sso.getDelegacion() != null && sso.getDelegacion().intValue() > 0 && 
					sso.getSubdelegacion() != null && sso.getSubdelegacion().intValue() > 0){ 		//si el usuario tiene delegacion y subdelegacion
				this.log.debug("::: Entre a validar para obtener rol Subdelegacional");
				
				if (this.checkGrantedAuthorities(arrayRolDelegado)) {
			        log.debug("Se obtuvo rol de DELEGADO pero viene con subdelegacion, se omite este valor para tomarlo con nivel Delegacional");
					pu.setIdPerfilUsuario(CodigoRolClasificacion.DELEGADO_DEL.getCodigo());
			        pu.setDescripcion(arrayRolDelegado[0].substring(5));
			        sso.setSubdelegacion(null); // si el delegado viene con subdelegacion se pone a null respetar el nivel delegacional
					usuario.getUsuarioFuncionario().setSubdelegacion(null);
		        } else if (this.checkGrantedAuthorities(arrayRolSubDelegado)) {
			        pu.setIdPerfilUsuario(CodigoRolClasificacion.SUBDELEGADO_SUBDEL.getCodigo());
			        pu.setDescripcion(arrayRolSubDelegado[0].substring(5));
		        } else if (this.checkGrantedAuthorities(arrayRolJefeOficinaParaCobros)) {
			        pu.setIdPerfilUsuario(CodigoRolClasificacion.JEFE_OFICINA_COBROS_SUBDEL.getCodigo());
			        pu.setDescripcion(arrayRolJefeOficinaParaCobros[0].substring(5));			          
		        }else if (this.checkGrantedAuthorities(arrayRolJefeDeptoAfilVigenciaCe)){	
					pu.setIdPerfilUsuario(CodigoRolClasificacion.JEFE_DEPTO_SUBDEL.getCodigo());
					pu.setDescripcion(arrayRolJefeDeptoAfilVigenciaCe[0].substring(5));
				}else if (this.checkGrantedAuthorities(arrayRolVentanillaCe)){ 
					pu.setIdPerfilUsuario(CodigoRolClasificacion.VENTANILLA_CLASIF_SUBDEL.getCodigo());
					pu.setDescripcion(arrayRolVentanillaCe[0].substring(5));
				}else if (this.checkGrantedAuthorities(arrayRolNormativoCe)){
					pu.setIdPerfilUsuario(CodigoRolClasificacion.NORMATIVO_SUBDEL.getCodigo());
					pu.setDescripcion(arrayRolNormativoCe[0].substring(5));
				}else if (this.checkGrantedAuthorities(arrayRolJefeOficina)){
					pu.setIdPerfilUsuario(CodigoRolClasificacion.JEFE_OFICINA_SUBDEL.getCodigo());
					pu.setDescripcion(arrayRolJefeOficina[0].substring(5));
		        }  
				
			}else if (sso.getDelegacion() != null && sso.getDelegacion().intValue() > 0 &&  
					(sso.getSubdelegacion() == null || sso.getSubdelegacion().intValue() <= 0) ){ 	//si el usuario tiene solo delegacion
				this.log.debug("::: Entre a validar para obtener rol de Delegacional");
				if (this.checkGrantedAuthorities(arrayRolDelegado)){
			        pu.setIdPerfilUsuario(CodigoRolClasificacion.DELEGADO_DEL.getCodigo());
			        pu.setDescripcion(arrayRolDelegado[0].substring(5));
				}else if (this.checkGrantedAuthorities(arrayRolSubDelegado)){
			        log.debug("Se encontro rol de Subdelegado, este debe tener valor en la subdelegación");
				}else if (this.checkGrantedAuthorities(arrayRolJefeOficinaParaCobros)){
			        log.debug("Se encontro rol de Jefe de Oficina para Cobros, este debe tener valor en la subdelegación");
				}else if (this.checkGrantedAuthorities(arrayRolJefeDeptoAfilVigenciaCe)){
					pu.setIdPerfilUsuario(CodigoRolClasificacion.JEFE_DEPTO_DEL.getCodigo());
					pu.setDescripcion(arrayRolJefeDeptoAfilVigenciaCe[0].substring(5));
				}else if (this.checkGrantedAuthorities(arrayRolVentanillaCe)){
					pu.setIdPerfilUsuario(CodigoRolClasificacion.VENTANILLA_CLASIF_DEL.getCodigo());
					pu.setDescripcion(arrayRolVentanillaCe[0].substring(5));
				}else if (this.checkGrantedAuthorities(arrayRolNormativoCe)){
					pu.setIdPerfilUsuario(CodigoRolClasificacion.NORMATIVO_DEL.getCodigo());
					pu.setDescripcion(arrayRolNormativoCe[0].substring(5));
				}else if (this.checkGrantedAuthorities(arrayRolJefeOficina)){
					pu.setIdPerfilUsuario(CodigoRolClasificacion.JEFE_OFICINA_DEL.getCodigo());
					pu.setDescripcion(arrayRolJefeOficina[0].substring(5));
		        }  
								
			} else if ( (sso.getDelegacion() == null || sso.getDelegacion().intValue() <= 0) && 
					(sso.getSubdelegacion() == null || sso.getSubdelegacion().intValue() <= 0) ){ 	//si el usuario es de nivel central
				this.log.debug("::: Entre a validar para obtener rol Nacional");
				if (this.checkGrantedAuthorities(arrayRolNormativoCe)){
					pu.setIdPerfilUsuario(CodigoRolClasificacion.NORMATIVO_CENTRAL.getCodigo());
					pu.setDescripcion(arrayRolNormativoCe[0].substring(5));
				}
				
			}			
								
			this.log.debug("*************************************************************************************");
			if (pu.getIdPerfilUsuario() == null || pu.getIdPerfilUsuario().intValue() == 0){				
				this.log.debug("****** El usuario "  + sso.getNombre().toUpperCase() + " no tiene un perfil valido");				
				model.addAttribute("errorLogin", "errorLogin");				
			}
			this.log.debug("Usuario: " + usuario.getUsuario() + ", id: " + usuario.getCveIdUsuario() + ", del: "
					+ sso.getDelegacion() + ", subDel: " + sso.getSubdelegacion() + ", perfil: " + pu.getIdPerfilUsuario()
					+ " - " + pu.getDescripcion());
			this.log.debug("*************************************************************************************");

			usuario.setPerfilUsuario(pu);
			
			if(sso.getSubdelegacion() != null && sso.getSubdelegacion().intValue() > 0){
				subdelegacion = domicilioService.obtenerSubdelegacionPorId(sso
						.getSubdelegacion().longValue());
			}
			
			String sistemasOAM = "";
			String perfilesOAM = "";
			if(sso.getSistemas() != null) {
				for (int i = 0; i < sso.getSistemas().length; i++) {
					sistemasOAM += sso.getSistemas()[i] + "|";
				}
			}
			if(sso.getPerfiles() != null) {
				for (int i = 0; i < sso.getPerfiles().length; i++) {
					perfilesOAM += sso.getPerfiles()[i] + "|";
				}
			}
			
			session.setAttribute("sistemasOAM", sistemasOAM);
			session.setAttribute("perfilesOAM", perfilesOAM);			
			
		}					
		
		session.setAttribute("subdelegacion", subdelegacion);
		session.setAttribute(KEY_USUARIO, usuario);
		session.setAttribute("cenefa", "");
		session.setAttribute("menuDecoration", "");
		this.setFechaSistema(session);

		return "bienvenida";
    }
	
	////////////////////////////////////////////////////////////////////////////////////////////
	//Para ambiente local
	///////////////////////////////////////////////////////////////////////////////////////////
	
//	@RequestMapping(value="/entrar",method=RequestMethod.GET)
//	public String entrarLocal(@ModelAttribute Usuario usuario, BindingResult result,
//			Model model, SessionStatus status, HttpSession session,
//			HttpServletRequest request) {
//
//		this.log.debug("::: Entre a LoginController");
//		UsuarioSSO sso = procesarUsuarioSSOLocal(request);
//		this.log.debug("::: Datos de usuario: " + sso.toString());
//		
//		Subdelegacion subdelegacion = new Subdelegacion();
//		//CONFIGURACION INICIAL
//		
//		if (sso != null) {
//		
//			ServletContext context = session.getServletContext();
//
//			String rolJefeDeptoAfilVigenciaCe = context.getInitParameter("jefeDepartamentoAfiliacionVigencia");
//			String rolJefeOficina = context.getInitParameter("jefeOficina");
//			String rolVentanillaCe = context.getInitParameter("ventanillaCE");
//			String rolNormativoCe = context.getInitParameter("normativoCE");
//			String rolDelegadoCe = context.getInitParameter("delegado");
//			String rolSubDelegadoCe = context.getInitParameter("subdelegado");
//			String rolJefeOficinaParaCobrosCe = context.getInitParameter("jefeOficinaParaCobros");
//												
//			String[] arrayRolJefeDeptoAfilVigenciaCe = {rolJefeDeptoAfilVigenciaCe};
//			String[] arrayRolJefeOficina = {rolJefeOficina};
//			String[] arrayRolVentanillaCe = {rolVentanillaCe};
//			String[] arrayRolNormativoCe = {rolNormativoCe};
//			String[] arrayRolDelegado = {rolDelegadoCe};
//			String[] arrayRolSubDelegado = {rolSubDelegadoCe};
//			String[] arrayRolJefeOficinaParaCobros = {rolJefeOficinaParaCobrosCe};			
//
//			usuario.setUsuario(sso.getNombre().toUpperCase());
//			usuario.setCveIdUsuario(sso.getNombre().toUpperCase());
//			
//			UsuarioFuncionario uf = new UsuarioFuncionario();
//			if (sso.getDelegacion() != null) {
//				uf.setDelegacion(new Delegacion());
//				uf.getDelegacion().setId(sso.getDelegacion().longValue());
//			}
//			if (sso.getSubdelegacion() != null) {
//				usuario.setCveIdSubdelegacion(sso.getSubdelegacion().longValue());
//				uf.setSubdelegacion(new Subdelegacion());
//				uf.getSubdelegacion().setId(sso.getSubdelegacion().longValue());
//			}
//					
//			uf.setUsuario(usuario);
//			usuario.setUsuarioFuncionario(uf);
//
//			PerfilUsuario pu = new PerfilUsuario();
//
//			//Modificacion para que el usuario tome el rol de mayor jerarquia al iniciar sesion
//			if (sso.getDelegacion() != null && sso.getDelegacion().intValue() > 0 && 
//					sso.getSubdelegacion() != null && sso.getSubdelegacion().intValue() > 0){ 		//si el usuario tiene delegacion y subdelegacion
//				this.log.debug("::: Entre a validar para obtener rol Subdelegacional");
//				
//				if (this.checkGrantedAuthoritiesLocal(arrayRolDelegado, sso.getPerfiles())) {
//			        log.debug("Se obtuvo rol de DELEGADO pero viene con subdelegacion, se omite este valor para tomarlo con nivel Delegacional");
//					pu.setIdPerfilUsuario(CodigoRolClasificacion.DELEGADO_DEL.getCodigo());
//			        pu.setDescripcion(arrayRolDelegado[0].substring(5));
//			        sso.setSubdelegacion(null); // si el delegado viene con subdelegacion se pone a null respetar el nivel delegacional
//					usuario.getUsuarioFuncionario().setSubdelegacion(null);
//		        } else if (this.checkGrantedAuthoritiesLocal(arrayRolSubDelegado, sso.getPerfiles())) {
//			        pu.setIdPerfilUsuario(CodigoRolClasificacion.SUBDELEGADO_SUBDEL.getCodigo());
//			        pu.setDescripcion(arrayRolSubDelegado[0].substring(5));
//		        } else if (this.checkGrantedAuthoritiesLocal(arrayRolJefeOficinaParaCobros, sso.getPerfiles())) {
//			        pu.setIdPerfilUsuario(CodigoRolClasificacion.JEFE_OFICINA_COBROS_SUBDEL.getCodigo());
//			        pu.setDescripcion(arrayRolJefeOficinaParaCobros[0].substring(5));			          
//		        }else if (this.checkGrantedAuthoritiesLocal(arrayRolJefeDeptoAfilVigenciaCe, sso.getPerfiles())) {	
//					pu.setIdPerfilUsuario(CodigoRolClasificacion.JEFE_DEPTO_SUBDEL.getCodigo());
//					pu.setDescripcion(arrayRolJefeDeptoAfilVigenciaCe[0].substring(5));
//				}else if (this.checkGrantedAuthoritiesLocal(arrayRolVentanillaCe, sso.getPerfiles())) { 
//					pu.setIdPerfilUsuario(CodigoRolClasificacion.VENTANILLA_CLASIF_SUBDEL.getCodigo());
//					pu.setDescripcion(arrayRolVentanillaCe[0].substring(5));
//				}else if (this.checkGrantedAuthoritiesLocal(arrayRolNormativoCe, sso.getPerfiles())) {
//					pu.setIdPerfilUsuario(CodigoRolClasificacion.NORMATIVO_SUBDEL.getCodigo());
//					pu.setDescripcion(arrayRolNormativoCe[0].substring(5));
//				}else if (this.checkGrantedAuthoritiesLocal(arrayRolJefeOficina, sso.getPerfiles())) {
//					pu.setIdPerfilUsuario(CodigoRolClasificacion.JEFE_OFICINA_SUBDEL.getCodigo());
//					pu.setDescripcion(arrayRolJefeOficina[0].substring(5));
//		        }  
//				
//			}else if (sso.getDelegacion() != null && sso.getDelegacion().intValue() > 0 &&  
//					(sso.getSubdelegacion() == null || sso.getSubdelegacion().intValue() <= 0) ){ 	//si el usuario tiene solo delegacion
//				this.log.debug("::: Entre a validar para obtener rol de Delegacional");
//				if (this.checkGrantedAuthoritiesLocal(arrayRolDelegado, sso.getPerfiles())) {
//			        pu.setIdPerfilUsuario(CodigoRolClasificacion.DELEGADO_DEL.getCodigo());
//			        pu.setDescripcion(arrayRolDelegado[0].substring(5));
//				}else if (this.checkGrantedAuthoritiesLocal(arrayRolSubDelegado, sso.getPerfiles())) {
//			        log.debug("Se encontro rol de Subdelegado, este debe tener valor en la subdelegación");			        
//				}else if (this.checkGrantedAuthoritiesLocal(arrayRolJefeOficinaParaCobros, sso.getPerfiles())) {
//			        log.debug("Se encontro rol de Jefe de Oficina para Cobros, este debe tener valor en la subdelegación");			        
//				}else if (this.checkGrantedAuthoritiesLocal(arrayRolJefeDeptoAfilVigenciaCe, sso.getPerfiles())) {
//					pu.setIdPerfilUsuario(CodigoRolClasificacion.JEFE_DEPTO_DEL.getCodigo());
//					pu.setDescripcion(arrayRolJefeDeptoAfilVigenciaCe[0].substring(5));
//				}else if (this.checkGrantedAuthoritiesLocal(arrayRolVentanillaCe, sso.getPerfiles())) {
//					pu.setIdPerfilUsuario(CodigoRolClasificacion.VENTANILLA_CLASIF_DEL.getCodigo());
//					pu.setDescripcion(arrayRolVentanillaCe[0].substring(5));
//				}else if (this.checkGrantedAuthoritiesLocal(arrayRolNormativoCe, sso.getPerfiles())) {
//					pu.setIdPerfilUsuario(CodigoRolClasificacion.NORMATIVO_DEL.getCodigo());
//					pu.setDescripcion(arrayRolNormativoCe[0].substring(5));
//				}else if (this.checkGrantedAuthoritiesLocal(arrayRolJefeOficina, sso.getPerfiles())) {
//					pu.setIdPerfilUsuario(CodigoRolClasificacion.JEFE_OFICINA_DEL.getCodigo());
//					pu.setDescripcion(arrayRolJefeOficina[0].substring(5));
//		        }  
//								
//			} else if ( (sso.getDelegacion() == null || sso.getDelegacion().intValue() <= 0) && 
//					(sso.getSubdelegacion() == null || sso.getSubdelegacion().intValue() <= 0) ){ 	//si el usuario es de nivel central
//				this.log.debug("::: Entre a validar para obtener rol Nacional");
//				if (this.checkGrantedAuthorities(arrayRolNormativoCe)){
//					pu.setIdPerfilUsuario(CodigoRolClasificacion.NORMATIVO_CENTRAL.getCodigo());
//					pu.setDescripcion(arrayRolNormativoCe[0].substring(5));
//				}
//				
//			}			
//			
//
////			if (sso.getDelegacion() != null && sso.getDelegacion().intValue() > 0 && 
////					sso.getSubdelegacion() != null && sso.getSubdelegacion().intValue() > 0){ 		//si el usuario tiene delegacion y subdelegacion
////				
////				this.log.debug("::: Entre a validar para obtener rol Subdelegacional");
////				
////				if (sso.getPerfil().equals(CodigoRolClasificacion.DELEGADO_DEL.getCodigo().toString())) {
////			        log.debug("Se obtuvo rol de DELEGADO pero viene con subdelegacion, se omite este valor para tomarlo con nivel Delegacional");
////					pu.setIdPerfilUsuario(CodigoRolClasificacion.DELEGADO_DEL.getCodigo());
////			        pu.setDescripcion(arrayRolDelegado[0].substring(5));
////			        sso.setSubdelegacion(null); // si el delegado viene con subdelegacion se pone a null respetar el nivel delegacional	
////			        usuario.getUsuarioFuncionario().setSubdelegacion(null);			        
////				}else if (sso.getPerfil().equals(CodigoRolClasificacion.JEFE_DEPTO_SUBDEL.getCodigo().toString())) {
////					pu.setIdPerfilUsuario(CodigoRolClasificacion.JEFE_DEPTO_SUBDEL.getCodigo());
////					pu.setDescripcion(arrayRolJefeDeptoAfilVigenciaCe[0].substring(5));
////				}else if (sso.getPerfil().equals(CodigoRolClasificacion.JEFE_OFICINA_SUBDEL.getCodigo().toString())) {
////					pu.setIdPerfilUsuario(CodigoRolClasificacion.JEFE_OFICINA_SUBDEL.getCodigo());
////					pu.setDescripcion(arrayRolJefeOficina[0].substring(5));					
////		        } else if (sso.getPerfil().equals(CodigoRolClasificacion.VENTANILLA_CLASIF_SUBDEL.getCodigo().toString())) {
////		            pu.setIdPerfilUsuario(CodigoRolClasificacion.VENTANILLA_CLASIF_SUBDEL.getCodigo());
////		            pu.setDescripcion(arrayRolVentanillaCe[0].substring(5));
////		        } else if (sso.getPerfil().equals(CodigoRolClasificacion.NORMATIVO_SUBDEL.getCodigo().toString())) {
////		            pu.setIdPerfilUsuario(CodigoRolClasificacion.NORMATIVO_SUBDEL.getCodigo());
////		            pu.setDescripcion(arrayRolNormativoCe[0].substring(5));
////		        } else if (sso.getPerfil().equals(CodigoRolClasificacion.SUBDELEGADO_SUBDEL.getCodigo().toString())) {
////			        pu.setIdPerfilUsuario(CodigoRolClasificacion.SUBDELEGADO_SUBDEL.getCodigo());
////			        pu.setDescripcion(arrayRolSubDelegado[0].substring(5));			          
////		        } else if (sso.getPerfil().equals(CodigoRolClasificacion.JEFE_OFICINA_COBROS_SUBDEL.getCodigo().toString())) {
////			        pu.setIdPerfilUsuario(CodigoRolClasificacion.JEFE_OFICINA_COBROS_SUBDEL.getCodigo());
////			        pu.setDescripcion(arrayRolJefeOficinaParaCobros[0].substring(5));			          
////		        }	
////		        		          
////			}else if (sso.getDelegacion() != null && sso.getDelegacion().intValue() > 0 &&  
////					(sso.getSubdelegacion() == null || sso.getSubdelegacion().intValue() <= 0) ){ 	//si el usuario tiene solo delegacion
////			
////				this.log.debug("::: Entre a validar para obtener rol Delegacional");
////				
////		        if (sso.getPerfil().equals(CodigoRolClasificacion.JEFE_DEPTO_DEL.getCodigo().toString())) {
////		            pu.setIdPerfilUsuario(CodigoRolClasificacion.JEFE_DEPTO_DEL.getCodigo());
////		            pu.setDescripcion(arrayRolJefeDeptoAfilVigenciaCe[0].substring(5));
////		        } else if (sso.getPerfil().equals(CodigoRolClasificacion.JEFE_OFICINA_DEL.getCodigo().toString())) {
////		            pu.setIdPerfilUsuario(CodigoRolClasificacion.JEFE_OFICINA_DEL.getCodigo());
////		            pu.setDescripcion(arrayRolJefeOficina[0].substring(5));
////		        } else if (sso.getPerfil().equals(CodigoRolClasificacion.VENTANILLA_CLASIF_DEL.getCodigo().toString())) {
////		            pu.setIdPerfilUsuario(CodigoRolClasificacion.VENTANILLA_CLASIF_DEL.getCodigo());
////		            pu.setDescripcion(arrayRolVentanillaCe[0].substring(5));
////		        } else if (sso.getPerfil().equals(CodigoRolClasificacion.NORMATIVO_DEL.getCodigo().toString())) {
////		            pu.setIdPerfilUsuario(CodigoRolClasificacion.NORMATIVO_DEL.getCodigo());
////		            pu.setDescripcion(arrayRolNormativoCe[0].substring(5));
////		        } else if (sso.getPerfil().equals(CodigoRolClasificacion.DELEGADO_DEL.getCodigo().toString())) {
////			        pu.setIdPerfilUsuario(CodigoRolClasificacion.DELEGADO_DEL.getCodigo());
////			        pu.setDescripcion(arrayRolDelegado[0].substring(5));
////		        }				
////								
////			} else if ( (sso.getDelegacion() == null || sso.getDelegacion().intValue() <= 0) && 
////					(sso.getSubdelegacion() == null || sso.getSubdelegacion().intValue() <= 0) ){ 	//si el usuario es de nivel central
////
////				if (sso.getPerfil().equals(CodigoRolClasificacion.NORMATIVO_CENTRAL.getCodigo().toString())) {
////			        pu.setIdPerfilUsuario(CodigoRolClasificacion.NORMATIVO_CENTRAL.getCodigo());
////			        pu.setDescripcion(arrayRolNormativoCe[0].substring(5));
////				}
////		        
////			}	
////			
//			
//								
//			this.log.debug("*************************************************************************************");
//			if (pu.getIdPerfilUsuario() == null || pu.getIdPerfilUsuario().intValue() == 0){				
//				this.log.debug("****** El usuario "  + sso.getNombre().toUpperCase() + " no tiene un perfil valido");				
//				model.addAttribute("errorLogin", "errorLogin");				
//			}
//			this.log.debug("Usuario: " + usuario.getUsuario() + ", id: " + usuario.getCveIdUsuario() + ", del: "
//					+ sso.getDelegacion() + ", subDel: " + sso.getSubdelegacion() + ", perfil: " + pu.getIdPerfilUsuario()
//					+ " - " + pu.getDescripcion());
//			this.log.debug("*************************************************************************************");
//
//			usuario.setPerfilUsuario(pu);
//			
//			if(sso.getSubdelegacion() != null && sso.getSubdelegacion().intValue() > 0){
//				subdelegacion = domicilioService.obtenerSubdelegacionPorId(sso
//						.getSubdelegacion().longValue());
//			}
//			
//		}					
//		
//		String sistemasOAM = "";
//		String perfilesOAM = "";
//		
//		
//		if(sso.getSistemas() != null) {
//			for (int i = 0; i < sso.getSistemas().length; i++) {
//				sistemasOAM += sso.getSistemas()[i] + "|";
//			}
//		}
//		if(sso.getPerfiles() != null) {
//			for (int i = 0; i < sso.getPerfiles().length; i++) {
//				perfilesOAM += sso.getPerfiles()[i] + "|";
//			}
//		}
//		
//		session.setAttribute("sistemasOAM", sistemasOAM);
//		session.setAttribute("perfilesOAM", perfilesOAM);			
//		
//		session.setAttribute("subdelegacion", subdelegacion);
//		session.setAttribute(KEY_USUARIO, usuario);
//		session.setAttribute("cenefa", "");
//		session.setAttribute("menuDecoration", "");
//		this.setFechaSistema(session);
//
//		return "bienvenida";
//    }
//	
//	
//	
//	private boolean checkGrantedAuthoritiesLocal(String[] arrayRolMAC, String[] perfilesOAM) {
//		log.debug("::: Verificando ROL de usuario - checkGrantedAuthoritiesLocal");
//		for (int i = 0; i < arrayRolMAC.length; i++) {
//			log.debug("::: Verificando ROL: " + arrayRolMAC[i]);
//			for (int j = 0; j < perfilesOAM.length; j++) {
//				if(arrayRolMAC[i].substring(5, arrayRolMAC[i].length()).trim().equals(perfilesOAM[j])) {
//					log.debug("::: Encontre el perfil");
//					return true;
//				}
//			}
//		}
//		log.debug(":: No se encontro el ROL");
//		return false;
//	}
	

	//metodo para recuperar datos del usuario de forma local
	private UsuarioSSO procesarUsuarioSSOLocal(HttpServletRequest request) {
		this.log.debug(":::Recuperando los datos del usuario de manera local");
		UsuarioSSO usuario = new UsuarioSSO();
		String[] listSistemas = null;
		String[] listPerfiles = null;
		Properties prop = new Properties();
		try {
			InputStream input = new FileInputStream("c:/temp/sso.properties");
			prop.load(input);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		String sso_user = prop.getProperty("sso_user");
		String sso_delegacion = prop.getProperty("sso_delegacion");
		String sso_subdelegacion = prop.getProperty("sso_subdelegacion");
		String sso_umf = prop.getProperty("sso_umf");
		String sso_cupr = prop.getProperty("sso_curp");
		String sso_perfil = prop.getProperty("sso_perfil");
		String sso_id_persona = prop.getProperty("sso_id_persona");
		String sso_list_sistemas = prop.getProperty("sso_list_sistemas");
		String sso_perfiles = prop.getProperty("sso_perfiles");

		Enumeration req = request.getAttributeNames();
		while (req.hasMoreElements()) {
			this.log.debug("los atributos del reques son:"
					+ (String) req.nextElement());
		}

		this.log.debug(" SSO - User [" + sso_user + "]");
		this.log.debug(" SSO - Delegacion [" + sso_delegacion + "]");
		this.log.debug(" SSO - Subdelegacion [" + sso_subdelegacion + "]");
		this.log.debug(" SSO - UMF [" + sso_umf + "]");
		this.log.debug(" SSO - CURP [" + sso_cupr + "]");
		this.log.debug(" SSO - Perfil [" + sso_perfil + "]");
		this.log.debug(" SSO - Id Persona [" + sso_id_persona + "]");
		this.log.debug(" SSO - SISTEMAS [" + sso_list_sistemas + "]");
		this.log.debug(" SSO - PERFILES [" + sso_perfiles + "]");

		if (StringUtils.isNotBlank(sso_list_sistemas)) {
			listSistemas = sso_list_sistemas.split(",");
			if (listSistemas.length > 0) {
				usuario.setSistemas(listSistemas);
			}
		}

		if (StringUtils.isNotBlank(sso_perfiles)) {
			listPerfiles = sso_perfiles.split(",");
			if (listPerfiles.length > 0) {
				usuario.setPerfiles(listPerfiles);
			}
		}

		if (StringUtils.isNotBlank(sso_user)) {
			usuario.setNombre(sso_user.trim());
		} else {
			usuario.setNombre(sso_user);
		}
		if (StringUtils.isNotBlank(sso_cupr)) {
			usuario.setCurp(sso_cupr.trim());
		} else {
			usuario.setCurp(sso_cupr);
		}
		if (StringUtils.isNotBlank(sso_perfil)) {
			usuario.setPerfil(sso_perfil.trim());
		} else {
			usuario.setPerfil(sso_perfil);
		}
		try {

			if (sso_delegacion != null && !sso_delegacion.isEmpty()) {
				usuario.setDelegacion(Integer.parseInt(sso_delegacion));
			}
			if (sso_subdelegacion != null && !sso_subdelegacion.isEmpty()) {
				usuario.setSubdelegacion(Integer.parseInt(sso_subdelegacion));
			}
			usuario.setIdPersona(new Integer(sso_id_persona));
		} catch (Exception e) {
			this.log.error(e);
		}

		/* Subimos a la sesion el usuario de sso */

		return usuario;
	}	

}