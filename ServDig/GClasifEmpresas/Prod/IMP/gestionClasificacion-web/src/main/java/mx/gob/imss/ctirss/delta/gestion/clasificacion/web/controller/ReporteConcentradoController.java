/**
 *  
 *  @Cliente: Instituto Mexicano del Seguro Social
 *  @Autor: Jonathan Sanchez Montiel
 *  @Proyecto: delta
 *  @Archivo:ReporteConcentradoController.java
 *  @Paquete:mx.gob.imss.ctirss.delta.gestion.clasificacion.web.controller
 *  @Fecha:20/10/2014
 */
package mx.gob.imss.ctirss.delta.gestion.clasificacion.web.controller;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import mx.gob.imss.ctirss.delta.exception.clasificacion.ExportarReporteException;
import mx.gob.imss.ctirss.delta.framework.base.controller.AbstractController;
import mx.gob.imss.ctirss.delta.gestion.clasificacion.service.interfaces.concentrado.ReporteConcentradoServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.clasificacion.web.utils.Constantes;
import mx.gob.imss.ctirss.delta.model.Usuario;
import mx.gob.imss.ctirss.delta.model.clasificacion.FiltrosConcentrado;
import mx.gob.imss.ctirss.delta.model.clasificacion.SumarizadoConcentrado;
import mx.gob.imss.ctirss.delta.web.validator.ReporteConcentradoValidator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.support.SessionStatus;

@Controller
@RequestMapping(value = "/consulta/concentradoNacional")
public class ReporteConcentradoController extends AbstractController {
	
	@Autowired
	ReporteConcentradoServiceBusinessRemote concentradoService;

	@RequestMapping(method=RequestMethod.GET)
    public String consultaInicioConcentrado(@ModelAttribute FiltrosConcentrado filtrosConcentrado, Model model, HttpSession session) {
		this.log.debug("[ReporteConcentradoController] -consultaInicioConcentrado ");
		model.addAttribute("filtrosConcentrado", filtrosConcentrado);
		session.setAttribute("menuDecoration", "4");
		return "consultaConcentrado";
    }
	
	@RequestMapping(value = "/generarConcentrado", method = RequestMethod.POST)
	public String generaReporte(@ModelAttribute FiltrosConcentrado filtrosConcentrado, 
			BindingResult result, SessionStatus status, HttpSession session, 
			Model model, HttpServletResponse httpResponse, HttpServletRequest httpRequest) {	
		
		try{
			this.log.info("++++++++ Generando reporte Concentrado Nacional +++++++++");		
			log.debug("******************" + filtrosConcentrado.toString());
			
			ArrayList<SumarizadoConcentrado> response= null;
			
			new ReporteConcentradoValidator().validate(filtrosConcentrado, result);
			
			if (result.hasErrors()) {
				log.debug("*****La validacion de datos para la impresion del concentrado nacional no pasa");
				return "consultaConcentrado";
			}
			
			/*Obtenemos el objeto de usuario de la sesion*/
			Usuario usuario = (Usuario)session.getAttribute(KEY_USUARIO);
			
			//generacion del reporte
			response = concentradoService.obtieneElementosReporteExcel(filtrosConcentrado, usuario.getPerfilUsuario().getIdPerfilUsuario().intValue(),
					usuario.getUsuario());
			
			if(response != null && response.size() > 0){
				log.debug("************ Se encontraron "+response.size()+" registros a nivel nacional");			
				try {
					
					String nombreArchivo = "";
					
					if(filtrosConcentrado.getCveIdGrupoAnalisisCe().equals("1"))
						nombreArchivo = Constantes.CONCENTRADO_NOMBRE_INSCRIPCION;
					else
						nombreArchivo = Constantes.CONCENTRADO_NOMBRE_MODIFICACIONES;					
					
					//creacion del archivo de excel
					byte[] res = concentradoService.obtieneReporteExcel(response, filtrosConcentrado.getStrPeriodoInicio(), filtrosConcentrado.getStrPeriodoFin());
					httpResponse.setContentType("application/vnd.ms-excel");
					httpResponse.setHeader("Content-Disposition","attachment;filename = " + 
							nombreArchivo + "." + Constantes.REPORTE_EXTENSION);
					httpResponse.getOutputStream().write(res);
					httpResponse.getOutputStream().flush();
					httpResponse.getOutputStream().close();
					
				}catch(Exception e) {
					log.error("Exception al generar Excel de reporte concentrado : " + e);
					e.printStackTrace();
				}
			}else{
				result.rejectValue("cveIdGrupoAnalisisCe", "field.reportes.busqueda.vacia");										
				log.debug("**************La busqueda no obtuvo registros.");
				model.addAttribute("filtrosConcentrado", filtrosConcentrado);
				return "consultaConcentrado";
			}
			
		}catch(ExportarReporteException exc){
			log.error("ExportarReporteException al generar el reporte concentrado: "+ exc.getMessage());
			model.addAttribute("filtrosConcentrado", filtrosConcentrado);
			exc.printStackTrace();
			model.addAttribute("msjException", exc.getMessage());
			return "consultaConcentrado";
		}catch(Exception e){
			log.error("Exception al generar el reporte concentrado: "+ e.getMessage());
			model.addAttribute("filtrosConcentrado", filtrosConcentrado);
			e.printStackTrace();
			model.addAttribute("msjException", e.getMessage());
			return "consultaConcentrado";
		}
			
		return "consultaConcentrado";
	}
	
}
 