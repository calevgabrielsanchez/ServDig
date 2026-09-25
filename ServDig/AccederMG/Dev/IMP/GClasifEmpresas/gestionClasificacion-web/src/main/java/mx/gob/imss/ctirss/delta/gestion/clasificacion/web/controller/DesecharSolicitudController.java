/**
 *  
 *  @Cliente: Instituto Mexicano del Seguro Socialg
 *  DesecharSolicitudController
 *  @Paquete:mx.gob.imss.ctirss.delta.gestion.clasificacion.web.controller
 *  @Fecha:07/12/2021
 */
package mx.gob.imss.ctirss.delta.gestion.clasificacion.web.controller;

import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;

import javax.activation.FileDataSource;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.binding.message.DefaultMessageContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import mx.gob.imss.ctirss.delta.exception.clasificacion.EstatusMovimientoException;
import mx.gob.imss.ctirss.delta.framework.base.controller.AbstractController;
import mx.gob.imss.ctirss.delta.gestion.clasificacion.model.dto.ClasificacionPropuestaDTO;
import mx.gob.imss.ctirss.delta.gestion.clasificacion.service.interfaces.desechar.DesecharBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.clasificacion.web.utils.Utiles;
import mx.gob.imss.ctirss.delta.gestion.patronal.service.interfaces.clasificacion.actividad.economica.ActividadEcServiceRemote;
import mx.gob.imss.ctirss.delta.model.Usuario;
import mx.gob.imss.ctirss.delta.model.clasificacion.AdjuntosClasificacion;
import mx.gob.imss.ctirss.delta.model.clasificacion.CodigoRolClasificacion;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.SujetoObligado;


@Controller
@RequestMapping(value = "/desechar/{cveIdAnalisis}")
public class DesecharSolicitudController extends AbstractController {

	@Autowired
	private DesecharBusinessRemote desecharBusiness;

	@Autowired
	private DetalleSolicitudController detalleSolicitudController;
	
	@Autowired
	private ActividadEcServiceRemote actividadEcService;	

		
	/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// METODOS PARA DESECHAR UNA SOLICITUD
	/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/****
	 * M�todo para iniciar el flujo para desechar solicitud
	 * 
	 * @throws Exception
	 ****/
	@RequestMapping(value = "/inicio", method = RequestMethod.POST)
	public ModelAndView inicio(
			@PathVariable String cveIdAnalisis,
			@RequestParam String regPatronal, 
			@RequestParam String cveIdSolicitud,
			@RequestParam String popUp, 
			@RequestParam String cveIdDelegacion,
			@RequestParam String cveIdSubdelegacion,
			@RequestParam String cveIdFraccionPro,
			@RequestParam String primaSRTPro,
			@RequestParam String idTipoTramite,
			@RequestParam String cveIdFraccionAct,
			@RequestParam String primaSRTAct,
			@RequestParam String primaSRTAnt,
			@RequestParam String cveIdFraccionAnt,
			HttpSession session) {
		log.debug("::::: DesecharSolicitudController.inicio");
		log.debug("::: cveIdAnalisis: " + cveIdAnalisis);
		log.debug("::: regPatronal: " + regPatronal);
		log.debug("::: cveIdSolicitud: " + cveIdSolicitud);
		log.debug("::: popUp: " + popUp);
		log.debug("::: cveIdDelegacion: " + cveIdDelegacion);
		log.debug("::: cveIdSubdelegacion: " + cveIdSubdelegacion);
		log.debug("::: cveIdFraccionPro: " + cveIdFraccionPro);
		log.debug("::: primaSRTPro: " + primaSRTPro);
		log.debug("::: idTipoTramite: " + idTipoTramite);
		log.debug("::: cveIdFraccionAct: " + cveIdFraccionAct);
		log.debug("::: primaSRTAct: " + primaSRTAct);
		log.debug("::: cveIdFraccionAnt: " + cveIdFraccionAnt);
		log.debug("::: primaSRTAnt: " + primaSRTAnt);
		
		ModelAndView model = new ModelAndView();
		model.addObject("cveIdAnalisis", cveIdAnalisis);
		session.setAttribute("regPatronal", regPatronal);
		session.setAttribute("cveIdSolicitud", cveIdSolicitud);
		session.setAttribute("popUp", 1);
		model.addObject("cveIdDelegacion", cveIdDelegacion);
		model.addObject("cveIdSubdelegacion", cveIdSubdelegacion);
		model.addObject("cveIdFraccionPro", cveIdFraccionPro);
		model.addObject("primaSRTPro", primaSRTPro);
		model.addObject("idTipoTramite", idTipoTramite);
		model.addObject("cveIdFraccionAct", cveIdFraccionAct);
		model.addObject("primaSRTAct", primaSRTAct);
		model.addObject("cveIdFraccionAnt", cveIdFraccionAnt);
		model.addObject("primaSRTAnt", primaSRTAnt);

		model.setViewName("desecharSolicitud");
		return model;		
	}


	/****
	 * M�todo que actualiza el estatus a pendiente de autorizar por rectificaci�n
	 * 
	 * @throws Exception
	 ****/
	@RequestMapping(value = "/confirmar", method = RequestMethod.POST)
	public String actualizarStatus(@PathVariable String cveIdAnalisis,
			@RequestParam String idTipoPersona, 
			@RequestParam String regPatron,
			@RequestParam String cveIdDivision,
			@RequestParam String cveIdGrupo,
			@RequestParam String cveIdFraccion, 
			@RequestParam String clase,
			@RequestParam String cveIdDelegacion,
			@RequestParam String cveIdSubdelegacion,
			@RequestParam String primaSRTPro,
			@RequestParam String fechaSurteEfecto,
			@RequestParam String idTipoTramite,
			@RequestParam String cveIdFraccionAct,
			@RequestParam String primaSRTAct, 			
			@RequestParam String cveIdFraccionAnt,
			@RequestParam String primaSRTAnt,
			@RequestParam MultipartFile oficio,
			HttpSession session, Model model,
			DefaultMessageContext messageContext) throws Exception {
		
		log.debug("::::: DesecharSolicitudController.actualizarStatus");
		
		String idSolicitud = session.getAttribute("idSolicitud").toString();
		String regPatronal = session.getAttribute("regPatronal").toString();
		String tipoPersona = session.getAttribute("tipoPersona").toString();
		
		SujetoObligado sujetoObligado = (SujetoObligado) session.getAttribute("sujetoObligado");
		
		try {		
			//Se recupera el usuario logeado
		 	Usuario usuario = (Usuario)session.getAttribute("usuario");
		 	int iRol = usuario.getPerfilUsuario().getIdPerfilUsuario().intValue();
		 	if (!perfilUsuarioValido(iRol)) {
				log.error("::: El usuario "+usuario.getUsuario()+" no tiene un perfil valido para ejecutar esta acci�n, rol: " + iRol);
				return "internalError";
			}

			messageContext.setMessageSource(messageSource);

			if(oficio != null) {
				log.debug("::: Llego documento con nombre: " + oficio.getOriginalFilename() + ", size: "
						+ oficio.getSize() + ", idSolicitud: " + idSolicitud);
			}else {
				log.debug(":::: El oficio llego NULL, idSolicitud: " + idSolicitud);			
			}
			
			log.debug("----------- Datos recibidos ---------");
			log.debug("::: : idTipoPersona: " + idTipoPersona);
			log.debug("::: : regPatron: " + regPatron);
			log.debug("::: : cveIdDivision: " + cveIdDivision);
			log.debug("::: : cveIdGrupo: " + cveIdGrupo);
			log.debug("::: : cveIdFraccion: " + cveIdFraccion);
			log.debug("::: : clase: " + clase);
			log.debug("::: : cveIdDelegacion: " + cveIdDelegacion);
			log.debug("::: : cveIdSubdelegacion: " + cveIdSubdelegacion);
			log.debug("::: : primaSRTPro: " + primaSRTPro);
			log.debug("::: : fechaSurteEfecto: " + fechaSurteEfecto );
			log.debug("::: : idTipoTramite: " + idTipoTramite );
			log.debug("::: : cveIdFraccionAct: " +cveIdFraccionAct );
			log.debug("::: : primaSRTAct: " + primaSRTAct);
			log.debug("::: : cveIdFraccionAnt: " + cveIdFraccionAnt);
			log.debug("::: : primaSRTAnt: " + primaSRTAnt);
						
			ClasificacionPropuestaDTO dto = new ClasificacionPropuestaDTO();
			dto.setCveIdSolicitud(idSolicitud);
			dto.setRegPatronal(regPatronal);
			dto.setTipoPersona(idTipoPersona);
			dto.setCveIdAnalisis(cveIdAnalisis);
			dto.setCveIdDivision(cveIdDivision);
			dto.setCveIdGrupo(cveIdGrupo);
			dto.setCveIdFraccionPro(cveIdFraccion);
			dto.setClase(clase);
			dto.setCveIdDelegacion(cveIdDelegacion);
			dto.setCveIdSubdelegacion(cveIdSubdelegacion);
			dto.setPrimaSRTPro(primaSRTPro);
			dto.setUsuario(usuario);
			dto.setCveUsuarioAsignado(usuario.getCveIdUsuario());
			dto.setRfc(session.getAttribute("rfc").toString());
			dto.setCveIdFraccionAct(cveIdFraccionAct);
			dto.setPrimaSRTAct(primaSRTAct);			
			dto.setCveIdFraccionAnt(cveIdFraccionAnt);
			dto.setPrimaSRTAnt(primaSRTAnt);

			if(desecharBusiness.desecharSolicitud(dto, idTipoTramite, fechaSurteEfecto) != 0){
				
				//Guardamos archivo en el servidor
				String pathServer = obtieneRutaServidor();
            	String path = pathServer + "/" + new SimpleDateFormat("yyyy-MM-dd").format(new Date()) + "/" + idSolicitud + "/";
            	log.debug("::: Se guarda el archivo "+oficio.getOriginalFilename()+" en la ruta: " + path);
            	guardarAdjuntoEnSistemaDeArchivos(oficio.getBytes(), path, oficio.getOriginalFilename());            	
            	log.debug("::: "+oficio.getOriginalFilename()+" se guardo con exito en file system");
               	//guardamos registro del archivo en la BD            	
            	AdjuntosClasificacion adjuntos = new AdjuntosClasificacion();
            	adjuntos.setCveIdTipoTramite(new Long(idTipoTramite));
            	adjuntos.setNombreArchivo(oficio.getOriginalFilename());
            	adjuntos.setRefFolio(idSolicitud);
            	adjuntos.setRutaArchivo(path);
            	actividadEcService.guardarArchivoAdjunto(adjuntos);
            	log.debug("::: "+oficio.getOriginalFilename()+" se guardo con exito la referencia en BD");	            	
            	
				model.addAttribute("regPatronal", regPatronal);
				model.addAttribute("tipoPersona", idTipoPersona);
				model.addAttribute("popUp", 1);
				messageContext.addMessage(Utiles.construirMensaje(Boolean.FALSE,
						"label.info.success"));
			}else{
				model.addAttribute("popUp", 0);
				model.addAttribute("cveIdAnalisis", cveIdAnalisis);
				model.addAttribute("idTipoPersona", idTipoPersona);
				model.addAttribute("regPatronal", regPatronal);
				model.addAttribute("cveIdDivision", cveIdDivision);
				model.addAttribute("cveIdGrupo", cveIdGrupo);
				model.addAttribute("cveIdFraccion", cveIdFraccion);
				model.addAttribute("clase", clase);
				model.addAttribute("cveIdDelegacion", cveIdDelegacion);
				model.addAttribute("cveIdSubdelegacion", cveIdSubdelegacion);
				model.addAttribute("primaSRTPro", primaSRTPro);
				model.addAttribute("cveIdFraccionAct", cveIdFraccionAct);
				model.addAttribute("primaSRTAct", primaSRTAct);
				model.addAttribute("primaSRTAnt", primaSRTAnt);
				model.addAttribute("cveIdFraccionAnt", cveIdFraccionAnt);

				messageContext.addMessage(Utiles.construirMensaje(Boolean.TRUE, "label.error.regpatronal"));
			}
			
		} catch (final EstatusMovimientoException e) {
			messageContext.addMessage(Utiles.construirMensaje(Boolean.TRUE, "label.error.estado.desechar"));
			log.error(e.getMessage(), e);
			
		} catch (final Exception e) {
			messageContext.addMessage(Utiles.construirMensaje(Boolean.TRUE, "label.error.estado.desechar"));
			log.error(e.getMessage(), e);
		}
		
		model.addAttribute("messageContext", messageContext);
		
		return detalleSolicitudController.detalle(idSolicitud, regPatronal, tipoPersona, session, model, null, sujetoObligado,null);
	}
	
	private String obtieneRutaServidor(){		
		ResourceBundle rb = ResourceBundle.getBundle("clasificacion");
		String ruta = rb.getString("ruta.archivos.adjuntos.tramites.clasificacion");
		return ruta;
	}		

    private void guardarAdjuntoEnSistemaDeArchivos(byte [] doc, String ruta, String nombreDocumento) throws IOException {
        try {
            FileDataSource ds = new FileDataSource(ruta);
            if (!ds.getFile().exists()) {
                ds.getFile().mkdirs();
            }
            ds = new FileDataSource(ruta + nombreDocumento);
            OutputStream os = ds.getOutputStream();
            os.write(doc);
            os.close();
        } catch (IOException io) {
        	io.printStackTrace();
            log.error("Error al escribir el documento: " + io, io);
            throw new IOException();
        }
    }	
	
	private boolean perfilUsuarioValido(int iRol){
		if (iRol == CodigoRolClasificacion.VENTANILLA_CLASIF_DEL.getCodigo().intValue()
				|| iRol == CodigoRolClasificacion.VENTANILLA_CLASIF_SUBDEL.getCodigo().intValue()
				|| iRol == CodigoRolClasificacion.JEFE_DEPTO_DEL.getCodigo().intValue()
				|| iRol == CodigoRolClasificacion.JEFE_DEPTO_SUBDEL.getCodigo().intValue()
				) {
			return true;
		}
		return false;
	}

}
