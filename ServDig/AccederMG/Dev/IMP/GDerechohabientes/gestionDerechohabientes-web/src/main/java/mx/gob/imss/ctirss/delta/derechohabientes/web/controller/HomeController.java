
package mx.gob.imss.ctirss.delta.derechohabientes.web.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import mx.gob.imss.ctirss.delta.derechohabientes.service.utility.interfaces.GrupoFamiliarServiceRemote;
import mx.gob.imss.ctirss.delta.derechohabientes.service.utility.interfaces.UmfServiceRemote;
import mx.gob.imss.ctirss.delta.derechohabientes.web.bean.Busqueda;
import mx.gob.imss.ctirss.delta.derechohabientes.web.bean.solicitud.SolicitudesAtendidasVb;
import mx.gob.imss.ctirss.delta.derechohabientes.web.utils.DateUtils;
import mx.gob.imss.ctirss.delta.derechohabientes.web.utils.OpcionesProperties;
import mx.gob.imss.ctirss.delta.exception.derechohabiente.DerechohabientesBusinessException;
import mx.gob.imss.ctirss.delta.exception.derechohabiente.ExceptionMessages;
import mx.gob.imss.ctirss.delta.framework.base.controller.AbstractController;
import mx.gob.imss.ctirss.delta.framework.base.web.sso.UsuarioSSO;
import mx.gob.imss.ctirss.delta.model.Usuario;
import mx.gob.imss.ctirss.delta.model.asegurado.AsignacionNSS;
import mx.gob.imss.ctirss.delta.model.derechohabiente.CabezaGrupoFamiliar;
import mx.gob.imss.ctirss.delta.model.derechohabiente.Derechohabiente;
import mx.gob.imss.ctirss.delta.model.derechohabiente.GrupoFamiliar;
import mx.gob.imss.ctirss.delta.model.derechohabiente.UnidadMedicaFamiliar;
import mx.gob.imss.ctirss.delta.model.derechohabiente.UsuarioFuncionario;
import mx.gob.imss.ctirss.delta.model.derechohabiente.dto.SolicitudesAtendidasDto;
import mx.gob.imss.ctirss.delta.model.derechohabiente.dto.vigenciaderechos.ServiciosDTO;
import mx.gob.imss.ctirss.delta.model.derechohabiente.negocio.PerfilUsuario;
import mx.gob.imss.ctirss.delta.model.enums.EstadoDerechohabienteEnum;
import mx.gob.imss.ctirss.delta.model.enums.ParentescoEnum;
import mx.gob.imss.ctirss.delta.model.enums.PerfilesEnum;
import mx.gob.imss.ctirss.delta.model.enums.PerfilesEsquemaSeguridadEnum;
import mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio.FileUploadVB;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Fisica;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.RegistroPatronal;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.SujetoObligado;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.EstadoTramiteEnum;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.Tramite;
import mx.gob.imss.ctirss.delta.model.util.Constants;
import mx.gob.imss.ctirss.gestionpersonas.servicios.business.PersonaBusinessRemote;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *  @Cliente: Instituto Mexicano del Seguro Social
 *  @Autor: Mario Teran Blanco
 *  @Proyecto: gestionDerechohabientes
 *  @Archivo:HomeController.java
 *  @Paquete:mx.gob.imss.ctirss.delta.derechohabientes.web.controller
 *  @Fecha:04/04/2013
 */

@Controller
@RequestMapping(value="/*")
public class HomeController extends AbstractController {

	@Autowired
	private OpcionesProperties opcionesProperties;
	@Autowired 
	private UmfServiceRemote umfServiceRemote;
	@Autowired 
	private PersonaBusinessRemote personaBusinessRemote;
	@Autowired 
	private GrupoFamiliarServiceRemote grupoFamiliarServiceRemote;
	
	@Autowired 
	private SessionControler sessionControler;
	
	
	
	@RequestMapping(value = "/tramita")
    public String homeTramitador(HttpSession session,HttpServletRequest request,Model model) {
		
		String forward = null;
		
		try {
			UsuarioSSO usr = this.procesarUsuarioSSO(request);
			sessionControler.limpiarSession(session);
			Usuario usuario =sessionControler.validarSesionUsuario(session, usr);
			if(isUsuarioNormativoCE(usr)) {
				session.setAttribute("normativoCE", 1);
				//forward = Constants.HOME_NORMATIVO_CE;
				forward = Constants.HOME_NORMATIVO_JEFE;
			}else if ( isUsuarioJefeDeptoSuper(usr)){
				session.setAttribute("normativoCE", 0);
				session.setAttribute("usuarioreporte", usuario);
				forward = Constants.HOME_JEFE_DEPTO_SUPER;
			}else {
				session.setAttribute("normativoCE", 0);
				forward = Constants.BUSQUEDA_PRINCIPAL_FORDWARD;
			}
		} catch(Exception e) {
			e.printStackTrace();
			request.setAttribute("exception", "exception.general");
			request.setAttribute("error", e.getCause().getMessage());
			return "internalError";
		}
		//opciones de menus
		request.setAttribute("opciones", opcionesProperties.getOpciones());
		//Establecemos los datos de la busqueda en el model para enviarlos a pantalla
		model.addAttribute(Busqueda.REQ_NAME, session.getAttribute("usuario"));
		return forward;
    }
	
	
	
	
	
		
	
		
	@RequestMapping(value = "/limpiarSesion")
	public @ResponseBody Boolean limpiarSesion(Model model, HttpServletRequest request, HttpSession session) {
		session.invalidate();
		return null;
	}
	
	
	private boolean isUsuarioNormativoCE(UsuarioSSO usuarioSSO) {
		String [] perfiles = usuarioSSO.getPerfiles();
		if(perfiles != null){
			for(int indice = 0; indice < perfiles.length; indice++){
				if(perfiles[indice].equalsIgnoreCase(PerfilesEnum.TITULAR_DE_LA_SUBJEFATURA_DE_DIVISION_DE_PRESTACIONES_EN_ESPECIE.getDesc())){
					return true;
				}
			}
			
		}
		return false;
	}

	private boolean isUsuarioJefeDeptoSuper(UsuarioSSO usuarioSSO) {
		String [] perfiles = usuarioSSO.getPerfiles();
		if(perfiles != null){
			for(int indice = 0; indice < perfiles.length; indice++){
				if(perfiles[indice].equalsIgnoreCase(PerfilesEnum.JEFE_DE_DEPARTAMENTO_DE_SUPERVISION_DE_AFILIACION_Y_VIGENCIA.getDesc())){
					return true;
				}
			}
			
		}
		return false;
	}

}
