/**
 *  
 *  @Cliente: Instituto Mexicano del Seguro Social
 *  @Autor: Eduardo González
 *  @Proyecto: delta
 *  @Archivo:ReportesAnalisisController.java
 *  @Paquete:mx.gob.imss.ctirss.delta.gestion.clasificacion.web.controller
 *  @Fecha:15/06/2012
 */
package mx.gob.imss.ctirss.delta.gestion.clasificacion.web.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import mx.gob.imss.ctirss.delta.exception.clasificacion.ConsultaReporteException;
import mx.gob.imss.ctirss.delta.framework.base.controller.AbstractController;
import mx.gob.imss.ctirss.delta.gestion.clasificacion.model.dto.DictamenDTO;
import mx.gob.imss.ctirss.delta.gestion.clasificacion.service.interfaces.DictamenServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.clasificacion.service.interfaces.analisis.AnalisisServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.clasificacion.service.interfaces.analisis.ReportesAnalisisBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.clasificacion.service.interfaces.tramite.TramiteServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.clasificacion.web.utils.Constantes;
import mx.gob.imss.ctirss.delta.model.Usuario;
import mx.gob.imss.ctirss.delta.model.clasificacion.CodigoRolClasificacion;
import mx.gob.imss.ctirss.delta.model.clasificacion.EstatusAnalisisModel;
import mx.gob.imss.ctirss.delta.model.clasificacion.FiltrosReportes;
import mx.gob.imss.ctirss.delta.model.clasificacion.ReporteAnalisis;
import mx.gob.imss.ctirss.delta.model.clasificacion.TipoRegistroEnum;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.TipoTramite;
import mx.gob.imss.ctirss.delta.web.validator.ReportesAnalisisValidator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.support.SessionStatus;

@Controller
@RequestMapping(value = "/consulta/reportesAnalisis")
public class ReportesAnalisisController extends AbstractController {
	
	@Autowired
	ReportesAnalisisBusinessRemote reportesAnalisisBusiness;
	
	@Autowired
	TramiteServiceBusinessRemote tramiteBusiness;
	
	@Autowired
	AnalisisServiceBusinessRemote analisisBusiness;
	
	@Autowired
	DictamenServiceBusinessRemote dictamenService;
	
	@RequestMapping(method=RequestMethod.GET)
    public String consultarReportesAnalisis(HttpSession session, Model model) {
		this.log.debug("[ReportesController] - consultarReportes");
		
		List<TipoTramite> listaTipoTramite = null;
		List<EstatusAnalisisModel> listaEstatusAnalisis = null;
		Long cveIdGrupoAnalisis = 0L;
		try {
			cveIdGrupoAnalisis = new Long((String)session.getAttribute("grupoTramite")).longValue();
			if(!cveIdGrupoAnalisis.equals(3L)) {
				listaTipoTramite = new ArrayList<TipoTramite>();
				listaTipoTramite = tramiteBusiness.consultaTipoTramitePorGrupoAnalisis(cveIdGrupoAnalisis);
			}
			listaEstatusAnalisis = new ArrayList<EstatusAnalisisModel>();
			listaEstatusAnalisis = analisisBusiness.consultaEstatusAnalisisPorGrupoAnalisis(cveIdGrupoAnalisis);
		} catch (Exception e) {
			log.error("Ocurrio un error: " + e.getMessage());
			e.printStackTrace();
		}
		
		session.setAttribute("lstTipoTramite", listaTipoTramite);
		session.setAttribute("lstEstatusAnalisis", listaEstatusAnalisis);
		session.setAttribute("menuDecoration", "2");
		
		if(cveIdGrupoAnalisis.equals(3L)) {
			model.addAttribute("dictamen", new DictamenDTO());
			return "analisisConsultaDictamen";
		}

		return "consultaReportes";
    }
	
	@RequestMapping(value = "/generarReporteDictamen", method = RequestMethod.POST)
	public String generarReporteAnalisis(@ModelAttribute DictamenDTO dictamen, Model model, HttpSession session, HttpServletResponse response) {
		log.debug("reporteDictamen -> filtros para el reporte:" + dictamen.toString());
		
		/*Obtenemos el objeto de usuario de la sesion*/
		Usuario usuario = (Usuario)session.getAttribute(KEY_USUARIO);

		int iRol = usuario.getPerfilUsuario().getIdPerfilUsuario().intValue();
		
		if (iRol == CodigoRolClasificacion.JEFE_DEPTO_SUBDEL.getCodigo().intValue()
				|| iRol == CodigoRolClasificacion.JEFE_OFICINA_SUBDEL.getCodigo().intValue()
				|| iRol == CodigoRolClasificacion.VENTANILLA_CLASIF_SUBDEL.getCodigo().intValue()
				|| iRol == CodigoRolClasificacion.NORMATIVO_SUBDEL.getCodigo().intValue()
		) {
			dictamen.setIdSubDelegacion(usuario.getCveIdSubdelegacion());			
		}
		
		List<ReporteAnalisis> listaRetVal = new ArrayList<ReporteAnalisis>();
		try {
			listaRetVal = dictamenService.consultarReporteAnalisisDictamen(dictamen, iRol);
		} catch (ConsultaReporteException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		log.debug("Se encontraron " + listaRetVal.size() + " registros");
		
		if(listaRetVal!=null && listaRetVal.size() > 0){
			try {
//				byte[] res = 
//					reportesAnalisisBusiness.crearReporteAnalisis(listaRetVal, new Integer(filtrosReportes.getCveIdGrupoAnalisisCe()).intValue());
				byte[] res = 
						reportesAnalisisBusiness.crearReporteAnalisisAlmacenes(listaRetVal, new Integer(2));
					
				
				response.setContentType("application/vnd.ms-excel");
				response.setHeader("Content-Disposition","attachment;filename = " + Constantes.REPORTE_NOMBRE + "." + Constantes.REPORTE_EXTENSION);
				response.getOutputStream().write(res);
				response.getOutputStream().flush();
				response.getOutputStream().close();
			}catch(Exception e) {
				log.error("Error en el metodo init  previo : " + e);
				e.printStackTrace();
			}
		}
					
		
		model.addAttribute("dictamen", dictamen);
		return "analisisConsultaDictamen";
	}
	
	@RequestMapping(value = "/generarReporte", method = RequestMethod.POST)
	public String generarReporteAnalisis(@ModelAttribute FiltrosReportes filtrosReportes,
			BindingResult result, SessionStatus status, HttpSession session, Model model, 
			HttpServletResponse sresponse, HttpServletRequest srequest) {
		
		try{		

			this.log.info("+++++++++++++++++++++++++++ Generando reporte de los analisis ++++++++++++++++++++++++++++++++++");
			log.debug("****************************" + filtrosReportes.toString());

			if(filtrosReportes.getTipoRegistro()!=null && filtrosReportes.getTipoRegistro().compareTo(TipoRegistroEnum.TODOS.getClave())==0){
				filtrosReportes.setTipoRegistro(null);
			}
			
			filtrosReportes.setEsConsulta(true);
			filtrosReportes.setCveIdGrupoAnalisisCe((String)session.getAttribute("grupoTramite"));
			
			new ReportesAnalisisValidator().validate(filtrosReportes, result);
			if (result.hasErrors()) {
				log.debug("**************La validacion de datos para la impresion de reportes no pasa");
				//model.addAttribute("filtrosReportes", filtrosReportes);
				return "consultaReportes";
			}
			
			/*Obtenemos el objeto de usuario de la sesion*/
			Usuario usuario = (Usuario)session.getAttribute(KEY_USUARIO);

			int iRol = usuario.getPerfilUsuario().getIdPerfilUsuario().intValue();
			
			if (iRol == CodigoRolClasificacion.JEFE_DEPTO_SUBDEL.getCodigo().intValue()
					|| iRol == CodigoRolClasificacion.JEFE_OFICINA_SUBDEL.getCodigo().intValue()
					|| iRol == CodigoRolClasificacion.VENTANILLA_CLASIF_SUBDEL.getCodigo().intValue()
					|| iRol == CodigoRolClasificacion.NORMATIVO_SUBDEL.getCodigo().intValue()
			) {
				filtrosReportes.setSubDelegacion(usuario.getCveIdSubdelegacion().toString());
			}
			
			//Obtiene datos 
//			List<ReporteAnalisis> listaRetVal = reportesAnalisisBusiness.consultarReporteAnalisis(filtrosReportes, iRol);
			List<ReporteAnalisis> listaRetVal = reportesAnalisisBusiness.consultarReporteAnalisisAlmacenes(filtrosReportes, iRol);
		
			
			
			log.debug("Se encontraron " + listaRetVal.size() + " registros");
						
			if(listaRetVal!=null && listaRetVal.size() > 0){
				try {
//					byte[] res = 
//						reportesAnalisisBusiness.crearReporteAnalisis(listaRetVal, new Integer(filtrosReportes.getCveIdGrupoAnalisisCe()).intValue());
					byte[] res = 
							reportesAnalisisBusiness.crearReporteAnalisisAlmacenes(listaRetVal, new Integer(filtrosReportes.getCveIdGrupoAnalisisCe()).intValue());
						
					
					sresponse.setContentType("application/vnd.ms-excel");
					sresponse.setHeader("Content-Disposition","attachment;filename = " + Constantes.REPORTE_NOMBRE + "." + Constantes.REPORTE_EXTENSION);
					sresponse.getOutputStream().write(res);
					sresponse.getOutputStream().flush();
					sresponse.getOutputStream().close();
				}catch(Exception e) {
					log.error("Error en el metodo init  previo : " + e);
					e.printStackTrace();
				}
			}else{
				result.rejectValue("registroPatronal", "field.reportes.busqueda.vacia");										
				log.debug("**************LA busqueda no obtuvo registros.");
				model.addAttribute("filtrosReportes", filtrosReportes);
				return "consultaReportes";
			}
						
		}catch(ConsultaReporteException e) {
			log.debug("**************Ocurrio una excepcion al generar el reporte: " + e.getMessage());			
			model.addAttribute("msjException", e.getMessage());
			model.addAttribute("filtrosReportes", filtrosReportes);
			e.printStackTrace();
		}
		
		return "consultaReportes";
	}
	   

}
