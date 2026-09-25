	
package mx.gob.imss.ctirss.delta.derechohabientes.web.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.io.StringReader;
import java.io.StringWriter;

import javax.xml.bind.JAXBException;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import mx.gob.imss.ctirss.delta.derechohabientes.service.utility.interfaces.GrupoFamiliarServiceRemote;
import mx.gob.imss.ctirss.delta.derechohabientes.service.utility.interfaces.UmfServiceRemote;
import mx.gob.imss.ctirss.delta.derechohabientes.web.bean.Busqueda;
import mx.gob.imss.ctirss.delta.derechohabientes.web.bean.solicitud.SolicitudesAtendidasVb;
import mx.gob.imss.ctirss.delta.derechohabientes.web.utils.DateUtils;
import mx.gob.imss.ctirss.delta.derechohabientes.web.utils.JaxbContextHelper;
import mx.gob.imss.ctirss.delta.derechohabientes.web.utils.OpcionesProperties;
import mx.gob.imss.ctirss.delta.exception.derechohabiente.DerechohabientesBusinessException;
import mx.gob.imss.ctirss.delta.exception.derechohabiente.ExceptionMessages;
import mx.gob.imss.ctirss.delta.framework.base.controller.AbstractController;
import mx.gob.imss.ctirss.delta.framework.base.web.sso.UsuarioSSO;
import mx.gob.imss.ctirss.delta.model.ModuloEnum;
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
import mx.gob.imss.distss.digital.jaxb.util.JaxbUtil;
import mx.gob.imss.webservice.renapo.curp.cliente.ConsultaDatosUsuario;
import mx.gob.imss.webservice.renapo.curp.cliente.ConsultaDatosUsuarioResponse;
import mx.gob.imss.webservice.renapo.curp.cliente.UsuarioInfoDTO;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.ws.client.core.WebServiceTemplate;


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
	
	/*@Autowired
	private UsuarioServiceRemote usuarioService;*/
	
	@Autowired
    @Qualifier("webServicePersonalSubdelegacion")
    private WebServiceTemplate webServicePersonalSubdelegacion;
	
	@RequestMapping(value = "/tramita")
    public String homeTramitador(HttpSession session,HttpServletRequest request,Model model) {
		
		String forward = null;
		
		try {
			UsuarioSSO usr = this.procesarUsuarioSSO(request);
			sessionControler.limpiarSession(session);
			Usuario usuario =sessionControler.validarSesionUsuario(session, usr);
			/*if (!usuarioService.validarEstadoUsuario(usr.getCurp())) {
            	return "UsuarioInvalido";
            }*/			
			UsuarioInfoDTO usuarioWS = null;
	        ConsultaDatosUsuario consultaDatosUsuario = new ConsultaDatosUsuario();
	        consultaDatosUsuario.setCurp(usr.getCurp());
	       
	        String peticionXml = JaxbUtil.marshaller(consultaDatosUsuario, JaxbContextHelper.getInstance());
	        ConsultaDatosUsuarioResponse respuesta = this.realizaConsulta(peticionXml, webServicePersonalSubdelegacion);
	               
	        log.debug("LOS DATOS DEL USR SON: " + usr);

	        if (respuesta != null) {
	        	usuarioWS = respuesta.getReturn();
            } else {
            	request.setAttribute("error", "No se pudo encontrar el usuario");
    			return "internalError";
            }
	   
	        log.debug("EL APELLIDO MATERNO DEL USUARIO ES: " + usuarioWS.getApellidoMaterno()); 
	        log.debug("EL APELLIDO PATERNO DEL USUARIO ES: " + usuarioWS.getApellidoPaterno());
	        log.debug("EL CORREO ELECTRONICO DEL USUARIO ES: " + usuarioWS.getCorreoElectronico());
	        log.debug("EL NOMBRE DEL USUARIO ES: " + usuarioWS.getNombre());
	        log.debug("EL ESTADO DEL USUARIO ES: " + usuarioWS.getEstado());

	        if (usuarioWS.getEstado() != 2 || tieneModulo(usr) == false ) {
        		return "UsuarioInvalido";
        	}	
	        
			if(isUsuarioNormativoCE(usr)) {
				session.setAttribute("normativoCE", 1);
				//forward = Constants.HOME_NORMATIVO_CE;
				forward = Constants.HOME_NORMATIVO_JEFE;
			}else if ( isUsuarioJefeDeptoSuper(usr)){
				model.addAttribute("nivelreporte", "0");
				session.setAttribute("normativoCE", 0);
				session.setAttribute("usuarioreporte", usuario);
				forward = Constants.HOME_JEFE_DEPTO_SUPER;
			}else if ( isVentanillaAdmon(usr)){
				session.setAttribute("usuarioreporte", usuario);
				return "opcionesAdmin";
			}else {
				session.setAttribute("normativoCE", 0);
				forward = Constants.BUSQUEDA_PRINCIPAL_FORDWARD;
			}
		} catch (JAXBException e) {
			e.printStackTrace();
			request.setAttribute("exception", "exception.general");
			request.setAttribute("error", e.getCause().getMessage());
			return "internalError";
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
	
    @RequestMapping("/homeVentanillaAdmin")
    public String homeVentanillaAdmin (HttpSession session, HttpServletRequest request) {

        return "opcionesAdmin";
    }
    
    @RequestMapping("/continuarSolicitudActualizacionCorreo")
    public String continuarSolicitudActualizacionCorreo (HttpSession session, HttpServletRequest request) {

        return "finalizarTramiteCorreo";
    }
	
	
	
	
		
	
		
	@RequestMapping(value = "/limpiarSesion")
	public @ResponseBody Boolean limpiarSesion(Model model, HttpServletRequest request, HttpSession session) {
		session.invalidate();
		return null;
	}
	
	private boolean tieneModulo(UsuarioSSO usuarioSSO) {
		String [] modulo = usuarioSSO.getSistemas();
		log.debug("el modulo es: " + Arrays.toString(modulo));
		if(modulo != null){
			for(int indice = 0; indice < modulo.length; indice++){
				if(modulo[indice].equalsIgnoreCase(ModuloEnum.DERECHOHABIENTES.getDescripcion())){
					return true;
				}
			}
			
		}
		return false;
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
	
	private boolean isVentanillaAdmon(UsuarioSSO usuarioSSO) {
		String [] perfiles = usuarioSSO.getPerfiles();
		if(perfiles != null){
			for(int indice = 0; indice < perfiles.length; indice++){
				if(perfiles[indice].equalsIgnoreCase(PerfilesEnum.JEFE_DEPTO_AFIL_VIGENCIA.getDesc())
						||perfiles[indice].equalsIgnoreCase(PerfilesEnum.JEFE_OFICINA_VIGENCIA.getDesc())){
					return true;
				}
			}
			
		}
		return false;
	}
	
	private <T> T realizaConsulta(String entrada, WebServiceTemplate template) throws JAXBException {

        StringWriter writer = new StringWriter();
        StreamResult result = new StreamResult(writer);

        template.sendSourceAndReceiveToResult(new StreamSource(new StringReader(entrada)), result);        
        String documentoXml = writer.toString();
        return JaxbUtil.unmarshaller(documentoXml, JaxbContextHelper.getInstance());
    }

}
