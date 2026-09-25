
package mx.gob.imss.ctirss.delta.gestion.individuo.web.controller;

import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import mx.gob.imss.ctirss.delta.framework.base.controller.AbstractController;
import mx.gob.imss.ctirss.delta.framework.base.web.sso.UsuarioSSO;
import mx.gob.imss.ctirss.delta.framework.exceptions.SolicitudNoEncontradaException;
import mx.gob.imss.ctirss.delta.model.Usuario;
import mx.gob.imss.ctirss.delta.model.derechohabiente.UsuarioFuncionario;
import mx.gob.imss.ctirss.delta.model.derechohabiente.negocio.PerfilUsuario;
import mx.gob.imss.ctirss.delta.model.domicilio.Delegacion;
import mx.gob.imss.ctirss.delta.model.domicilio.Subdelegacion;
import mx.gob.imss.ctirss.gestionpersonas.servicios.business.ReporteBusinessRemote;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author Joaquin Ponte
 * @author Cesar Garcia Mauricio
 * @author Samuel Rodriguez Grajeda
 * @company IMSS (Instituto Mexicano del Seguro Social)
 * @date Febrero/Marzo 2012
 */
@Controller
@RequestMapping(value = "/solicitud")
public class ReporteConfirmacionSolicitudController extends AbstractController {
	
    @Autowired	
	private transient ReporteBusinessRemote reporteBusiness;

	@RequestMapping(value = "/reporte-comprobante", method = RequestMethod.GET)
	public String buscarComprobanteSolicitud(final HttpSession session,
			HttpServletRequest request) {
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

		return "busquedaComprobanteSolicitud";
	}
    
    @RequestMapping(value = "/reporte-comprobante/{folio}", method = RequestMethod.GET)
    public String handleRequestInternal(@PathVariable String folio, final HttpServletRequest request, final HttpServletResponse response, HttpSession session) {        
        
    	this.log.debug("Imprimiendo el comprobante ....");
    	
    	try {
	    	response.setContentType("application/pdf");
	    	String folioSolicitud = folio; //<-- por GET
	    	if(StringUtils.isBlank(folioSolicitud)) {
	    	    request.setAttribute("msgError", "No se ha pasado el folio de la solicitud!");
	    	    return "mensajes";
	    	}
	    	
	    	
	    	/*
	    	 * BUG ID: 20 Presentar el nombre del solicitante correspondiente al usuario
	    	 * firmado en el SSO
	    	 */
	    	
	    	Usuario usuario = (Usuario) session.getAttribute(KEY_USUARIO);
			String nombreUsuario = null;
			if (usuario != null) {
				nombreUsuario = usuario.getUsuario();
			}
	    	this.log.debug("Nombre del solicitante en el reporte :"  + nombreUsuario);
	    	
	    	response.getOutputStream().write(reporteBusiness.getComprobanteOperacion(Long.valueOf(folioSolicitud), nombreUsuario));
	    	response.getOutputStream().flush();
	    	response.getOutputStream().close();
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        } catch (SolicitudNoEncontradaException e) {
        	this.log.error(e.getMessage(), e);
            request.setAttribute("mensaje", e.getMessage());
            return "mensajes";
        }
        return null;
    }
	
}
