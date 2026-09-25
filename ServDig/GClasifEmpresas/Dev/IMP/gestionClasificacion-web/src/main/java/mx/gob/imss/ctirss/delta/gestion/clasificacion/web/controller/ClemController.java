/**
 *  
 *  @Cliente: Instituto Mexicano del Seguro Social
 *  @Autor: H�ctor Lara Andr�s
 *  @Proyecto: delta
 *  @Archivo:ClemController.java
 *  @Paquete:mx.gob.imss.ctirss.delta.gestion.clasificacion.web.controller
 *  @Fecha:15/06/2012
 */
package mx.gob.imss.ctirss.delta.gestion.clasificacion.web.controller;

import static mx.gob.imss.ctirss.delta.gestion.clasificacion.web.utils.Utiles.getFechaActual;

import java.io.IOException;
import java.math.BigDecimal;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import mx.gob.imss.ctirss.delta.exception.clasificacion.ClemCaracterException;
import mx.gob.imss.ctirss.delta.exception.clasificacion.DatosClemException;
import mx.gob.imss.ctirss.delta.exception.clasificacion.EstatusMovimientoException;
import mx.gob.imss.ctirss.delta.exception.clasificacion.GCESujetoObligadoException;
import mx.gob.imss.ctirss.delta.framework.base.controller.AbstractController;
import mx.gob.imss.ctirss.delta.gestion.clasificacion.service.interfaces.clem.DatosClemServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.clasificacion.web.utils.Constantes;
import mx.gob.imss.ctirss.delta.gestion.clasificacion.web.utils.Utiles;
import mx.gob.imss.ctirss.delta.model.Usuario;
import mx.gob.imss.ctirss.delta.model.clasificacion.DatosClem;
import mx.gob.imss.ctirss.delta.model.clasificacion.ReporteClemBean;
import mx.gob.imss.ctirss.delta.web.validator.DatosClemValidator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.binding.message.DefaultMessageContext;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.support.SessionStatus;

@Controller
@RequestMapping(value = "/clem/{cveIdAnalisis}")
public class ClemController extends AbstractController {
	
	@Autowired
	private DatosClemServiceBusinessRemote datosClemBusiness;
	
	@Autowired
	private DetalleSolicitudController detalleSolicitudController;

		
	@RequestMapping(value = "/verClem", method = RequestMethod.GET)
	public @ResponseBody String verDocumentoClem(@PathVariable String cveIdAnalisis, HttpServletResponse sresponse) {
		byte[] response = null;
		DatosClem datosClem= new DatosClem();
		try {
			datosClem.setCveAnalisis(new BigDecimal(cveIdAnalisis));
			datosClem.setIndActivo(BigDecimal.ONE);
			datosClem = datosClemBusiness.consultaClem(datosClem);
			
			response  = datosClem.getRefDocumento();
			if(response!=null){
				log.debug("Se obtuvo el archivo PDF");
				sresponse.setContentType("application/pdf");
				sresponse.addHeader("content-disposition","attachment; filename=DatosClem04.pdf");
				sresponse.addHeader("X-Download-Options", "open");
				try {
					log.debug("bytes pdf:"+response.length);
					sresponse.setContentLength(response.length);
					sresponse.getOutputStream().write(response);
					sresponse.getOutputStream().flush();
					sresponse.getOutputStream().close();
				}catch(IOException e) {
					log.error("Error en el metodo init  previo : " + e);
					e.printStackTrace();
				}
			}else{
				log.debug("No se obtuvo el archivo PDF:DatosClem04.pdf");
			}
		} catch (Exception e) {
			log.error("Error al obtener el archivo PDF.");
			e.printStackTrace();
		}
		
		return "analisisConsulta";
	}
	
	/**
	 * M�todo para iniciar la modificacion de la CLEM
	 * @param reporteClemBean
	 * @param result
	 * @param status
	 * @param session
	 * @param model
	 * @param sresponse
	 * @return
	 */	
	@RequestMapping(value = "/modificarClem/capturaDatos", method = RequestMethod.POST)
	public String capturaDatosClem(@PathVariable String cveIdAnalisis,@ModelAttribute ReporteClemBean reporteClemBean,
			BindingResult result, SessionStatus status, HttpSession session, Model model, HttpServletResponse sresponse){
		try {
			reporteClemBean.setIdAnalisis(cveIdAnalisis);
			reporteClemBean=datosClemBusiness.consultaDatosClem(reporteClemBean);
			if(reporteClemBean.getLugarFechaExpedicion()==null){
				reporteClemBean.setLugarFechaExpedicion(getFechaActual());
			}
			if(reporteClemBean.getCveIdClem()!=null){
				reporteClemBean.setBotonClem(Constantes.CLEM_MODIFICAR);
			}else{
				reporteClemBean.setBotonClem(Constantes.CLEM_GENERAR);
			}
		} catch (DatosClemException e){
			log.error("Ocurri� un error al Consultar Propiedades del CLEM: " + e.getMessage());
		}
		model.addAttribute("reporteClemBean", reporteClemBean);
		return "modificaDatosClem";
	}	
	
	/**
	 * M�todo para modificar la clem
	 * @param reporteClemBean
	 * @param result
	 * @param status
	 * @param session
	 * @param model
	 * @param sresponse
	 * @param request
	 * @param messageContext
	 * @return
	 */	
	@RequestMapping(value = "/modificarClem/guardaModificacion", method = RequestMethod.POST)
	public String generacionClem(@ModelAttribute ReporteClemBean reporteClemBean,
			BindingResult result, SessionStatus status, HttpSession session, Model model, HttpServletResponse sresponse,
			HttpServletRequest request, DefaultMessageContext messageContext)throws Exception{
		
		try {
			messageContext.setMessageSource(messageSource);
			Usuario usuario = (Usuario) session.getAttribute("usuario");
			reporteClemBean.setCveSolicitud(session.getAttribute("idSolicitud").toString());
			reporteClemBean.setRegPatronal(session.getAttribute("regPatronal").toString());
			// se comenta ya que en la plantilla ahora se muestra el puesto que se captura
			//reporteClemBean.setTitular(reporteClemBean.getTitular().toUpperCase());
			reporteClemBean.setPuesto(reporteClemBean.getPuesto().toUpperCase());
			
			if(reporteClemBean.getSuplente() != null){
				reporteClemBean.setSuplente(reporteClemBean.getSuplente().toUpperCase());
			}
			
			new DatosClemValidator().validate(reporteClemBean, result);
			if (result.hasErrors()){
				log.debug("Error al validar la captura de datosclem");
				model.addAttribute("reporteClemBean", reporteClemBean);
				return "autorizarRectificacion";
			}
			
			datosClemBusiness.generacionClem(reporteClemBean, usuario, (new ClassPathResource("reportes/").getPath().toString()) + "asimss-clem.jpg",null);
			messageContext.addMessage(Utiles.construirMensaje(Boolean.FALSE, "label.info.success"));
		}catch(GCESujetoObligadoException e){
			messageContext.addMessage(Utiles.construirMensaje(Boolean.TRUE, "label.error.regpatronal"));
			log.error(e.getMessage(), e);
	 	} catch (final EstatusMovimientoException e) {
	 		messageContext.addMessage(Utiles.construirMensaje(Boolean.TRUE, "label.error.estado.analisis"));
			log.error(e.getMessage(), e);
	 	} catch (final ClemCaracterException e) {
	 		messageContext.addMessage(Utiles.construirMensaje(Boolean.TRUE, "label.error.caracter.clem"));
			log.error(e.getMessage(), e);
		} catch (final Exception e) {
			messageContext.addMessage(Utiles.construirMensaje(Boolean.TRUE, "label.error.desconocido.rectificar.autorizacion"));
			log.error(e.getMessage(), e);
		}
		
		model.addAttribute("messageContext", messageContext);
		
		return detalleSolicitudController.detalleSolicitud(
				session.getAttribute("idSolicitud").toString(),
				session.getAttribute("regPatronal").toString(),
				session.getAttribute("tipoPersona").toString(), session, model);
	}			
	
}
