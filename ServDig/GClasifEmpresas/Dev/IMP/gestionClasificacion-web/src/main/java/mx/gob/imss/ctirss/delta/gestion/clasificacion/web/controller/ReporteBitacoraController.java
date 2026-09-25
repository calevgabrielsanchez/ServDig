/**
 *  
 *  @Cliente: Instituto Mexicano del Seguro Social
 *  @Autor: Eduardo González
 *  @Proyecto: delta
 *  @Archivo:ReporteBitacoraController.java
 *  @Paquete:mx.gob.imss.ctirss.delta.gestion.clasificacion.web.controller
 *  @Fecha:30/06/2012
 */
package mx.gob.imss.ctirss.delta.gestion.clasificacion.web.controller;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import mx.gob.imss.ctirss.delta.exception.clasificacion.ExportarReporteException;
import mx.gob.imss.ctirss.delta.framework.base.controller.AbstractController;
import mx.gob.imss.ctirss.delta.gestion.clasificacion.service.interfaces.bitacora.ReporteBitacoraServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.clasificacion.web.utils.Constantes;
import mx.gob.imss.ctirss.delta.model.Usuario;
import mx.gob.imss.ctirss.delta.model.clasificacion.CodigoRolClasificacion;
import mx.gob.imss.ctirss.delta.model.clasificacion.ElementoBitacora;
import mx.gob.imss.ctirss.delta.model.clasificacion.FiltrosBitacoras;
import mx.gob.imss.ctirss.delta.model.clasificacion.GrupoAnalisisCeEnum;
import mx.gob.imss.ctirss.delta.web.validator.ReporteBitacoraValidator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.support.SessionStatus;

@Controller
@RequestMapping(value = "/consulta/reportesBitacoras")
public class ReporteBitacoraController extends AbstractController {
	
	@Autowired
	ReporteBitacoraServiceBusinessRemote bitacoraService;

	@RequestMapping(method=RequestMethod.GET)
    public String consultaInicioBitacoras(Model model, HttpSession session) {
		//Se recupera el usuario logeado
	 	Usuario usuario = (Usuario)session.getAttribute("usuario");
	 	int iRol = usuario.getPerfilUsuario().getIdPerfilUsuario().intValue();
	 	if (!perfilUsuarioValido(iRol)) {
			log.error("::: El usuario "+usuario.getUsuario()+" no tiene un perfil valido para ejecutar esta acción, rol: " + iRol);
			return "internalError";
		}
		this.log.debug("[ReporteBitacoraController] -consultaInicioBitacoras ");
		session.setAttribute("menuDecoration", "3");
		return "consultaBitacora";
    }
	
	@RequestMapping(value = "/generarReporte", method = RequestMethod.POST)
	public String generaReporte(@ModelAttribute FiltrosBitacoras filtrosBitacoras, 
			BindingResult result, SessionStatus status, HttpSession session, 
			Model model, HttpServletResponse httpResponse, HttpServletRequest httpRequest) {	
		
		try{
			//Se recupera el usuario logeado
		 	Usuario usuarios = (Usuario)session.getAttribute("usuario");
		 	int iRols = usuarios.getPerfilUsuario().getIdPerfilUsuario().intValue();
		 	if (!perfilUsuarioValido(iRols)) {
				log.error("::: El usuario "+usuarios.getUsuario()+" no tiene un perfil valido para ejecutar esta acción, rol: " + iRols);
				return "internalError";
			}
			this.log.info("++++++++ Generando reporte de bitacoras +++++++++");		
			log.debug("******************" + filtrosBitacoras.toString());
			
			ArrayList<ElementoBitacora> response= null;
			
			filtrosBitacoras.setEsConsulta(true);
			
			new ReporteBitacoraValidator().validate(filtrosBitacoras, result);
			if (result.hasErrors()) {
				log.debug("*****La validacion de datos para la impresion de bitacoras no pasa");
				return "consultaBitacora";
			}
			
			/*Obtenemos el objeto de usuario de la sesion*/
			Usuario usuario = (Usuario)session.getAttribute(KEY_USUARIO);
			filtrosBitacoras.setEsInscripcionInicial(session.getAttribute("grupoTramite").
					equals(""+ GrupoAnalisisCeEnum.INSCRIPCION_INICIAL.getClave()) ? true : false);
			   
			/*Debemos de obtener el rol del usuario*/
			int iRol = usuario.getPerfilUsuario().getIdPerfilUsuario().intValue();
			
			if (iRol == CodigoRolClasificacion.JEFE_DEPTO_SUBDEL.getCodigo().intValue()
					|| iRol == CodigoRolClasificacion.JEFE_OFICINA_SUBDEL.getCodigo().intValue()
					|| iRol == CodigoRolClasificacion.VENTANILLA_CLASIF_SUBDEL.getCodigo().intValue()
					|| iRol == CodigoRolClasificacion.NORMATIVO_SUBDEL.getCodigo().intValue()
			) {
				filtrosBitacoras.setSubDelegacion(usuario.getCveIdSubdelegacion());
			}
			
			response = (ArrayList<ElementoBitacora>) bitacoraService.obtieneElementosReporteExcel(filtrosBitacoras, iRol);
			
			if(response!=null && response.size() >0){
				log.debug("Se encontraron "+response.size()+" registros de la bitacora");			
				try {
					byte[] res = bitacoraService.obtieneReporteExcel(response, filtrosBitacoras);
					httpResponse.setContentType("application/vnd.ms-excel");
					httpResponse.setHeader("Content-Disposition","attachment;filename = " + 
						Constantes.BITACORA_NOMBRE + "." + Constantes.REPORTE_EXTENSION);
					httpResponse.getOutputStream().write(res);
					httpResponse.getOutputStream().flush();
					httpResponse.getOutputStream().close();
				}catch(Exception e) {
					log.error("Error en el metodo init  previo : " + e);
					e.printStackTrace();
				}
			}else{
				
				model.addAttribute("filtrosBitacoras", filtrosBitacoras);
				result.rejectValue("cveIdAnalisis", "field.reportes.busqueda.vacia");										
				log.debug("**************La busqueda no obtuvo registros.");
				return "consultaBitacora";
			}
		}catch(ExportarReporteException exc){
			log.error("Error al generarla bitacora: "+ exc.getMessage());
			exc.printStackTrace();
			model.addAttribute("msjException", exc.getMessage());
			return "consultaBitacora";
		}
			
		return "consultaBitacora";
	}
	private boolean perfilUsuarioValido(int iRol){
		if (iRol == CodigoRolClasificacion.JEFE_DEPTO_DEL.getCodigo().intValue()
				|| iRol == CodigoRolClasificacion.JEFE_DEPTO_SUBDEL.getCodigo().intValue()
				|| iRol == CodigoRolClasificacion.JEFE_OFICINA_SUBDEL.getCodigo().intValue()
				|| iRol == CodigoRolClasificacion.JEFE_OFICINA_DEL.getCodigo().intValue()
				|| iRol == CodigoRolClasificacion.VENTANILLA_CLASIF_DEL.getCodigo().intValue()
				|| iRol == CodigoRolClasificacion.VENTANILLA_CLASIF_SUBDEL.getCodigo().intValue()
				|| iRol == CodigoRolClasificacion.NORMATIVO_CENTRAL.getCodigo().intValue()
				|| iRol == CodigoRolClasificacion.NORMATIVO_DEL.getCodigo().intValue()
				|| iRol == CodigoRolClasificacion.NORMATIVO_SUBDEL.getCodigo().intValue()
		) {
			return true;
		}
		return false;
	}
}
 