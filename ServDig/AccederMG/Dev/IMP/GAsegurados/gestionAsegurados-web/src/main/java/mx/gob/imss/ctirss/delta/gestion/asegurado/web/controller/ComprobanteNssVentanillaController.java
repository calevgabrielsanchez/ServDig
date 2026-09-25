package mx.gob.imss.ctirss.delta.gestion.asegurado.web.controller;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.util.Map;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import mx.gob.imss.ctirss.delta.framework.base.controller.AbstractController;
import mx.gob.imss.ctirss.delta.framework.util.Base64Cipher;
import mx.gob.imss.ctirss.delta.gestion.solicitud.service.interfaces.SolicitudBusinessRemote;
import mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio.DocumentoPorTipoEnum;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author Joaquin Ponte
 * @author Cesar Garcia Mauricio
 * @author Samuel Rodriguez Grajeda
 * @company IMSS (Instituto Mexicano del Seguro Social)
 * @date Febrero/Marzo 2012
 */
@Controller
@RequestMapping(value = "/reporte")
public class ComprobanteNssVentanillaController extends AbstractController {
	
	@Autowired
	private transient SolicitudBusinessRemote solicitudBusinessRemote;

	/**
	 * Recupera el comprobante para una asignaci&oacute;n de NSS
	 * 
	 * @param folio
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/comprobante/interno", method = RequestMethod.GET)
	public void obtenerComprobanteAsignacion(Model model,
			final HttpServletRequest request, HttpServletResponse response,
			HttpSession session, @RequestParam("si") String solicitudId) {
		
		Long idSolicitud = null;
		byte[] comprobanteAsignacion = null;
		Map<String, Object> resultado = null;
		String fileName = null;
		
		if (StringUtils.isBlank(solicitudId)) {
			request.setAttribute("msgError","No se ha pasado el folio de la solicitud!");
		} else {
			try {
				idSolicitud = Long.valueOf(Base64Cipher.descrifrar(solicitudId));
				
				//Obtenemos el comprobante de asignacion
				resultado = solicitudBusinessRemote
						.obtenerDocumentoResultanteNss(idSolicitud, null,
								DocumentoPorTipoEnum.COMPROBANTE_ASIGNACION
										.getId().intValue());
				if (resultado != null) {
					comprobanteAsignacion = (byte[]) resultado.get("FILE");
					fileName = (String) resultado.get("FILE_NAME");
					
					try {
						if(comprobanteAsignacion != null){
							log.debug("El documento no es nulo");
							
							response.addHeader("Accept-Ranges","bytes");
							response.addHeader("Cache-Control","public");
							response.addHeader("Cache-Control","must-revalidate");
							response.addHeader("Pragma","public");
							response.setContentType("application/pdf");
							response.addHeader("expires","0");
							response.addHeader("Content-disposition", "attachment;filename=\"" + fileName + "\""); 
							response.setContentLength(comprobanteAsignacion.length);
							response.getOutputStream().write(comprobanteAsignacion);
							response.flushBuffer();
							
						} else {
							this.log.debug("No hay documento");
						}
					} catch (IOException e) {
						this.log.error("Error al generar el comprobante de asignacion de NSS -> ", e);
					}
					this.log.debug("Termina generación de documentos de solicitud");
				} else {
					this.log.error("No se genero el comprobante NSS de la solicitud "
							+ idSolicitud);
				}
			} catch (NumberFormatException e) {
				this.log.error("Error al generar el comprobante de asignacion de NSS -> ", e);
			} catch (InvalidKeyException e) {
				this.log.error("Error al generar el comprobante de asignacion de NSS -> ", e);
			} catch (IllegalBlockSizeException e) {
				this.log.error("Error al generar el comprobante de asignacion de NSS -> ", e);
			} catch (BadPaddingException e) {
				this.log.error("Error al generar el comprobante de asignacion de NSS -> ", e);
			} catch (IOException e) {
				this.log.error("Error al generar el comprobante de asignacion de NSS -> ", e);
			}
		}
	}
	
	/**
	 * Recupera el comprobante para una localizaci&oacute;n de NSS
	 * 
	 * @param folio
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/comprobante/recuperado", method = RequestMethod.GET)
	public void getReporteRecuperado(Model model,
			final HttpServletRequest request, HttpServletResponse response,
			HttpSession session, @RequestParam("si") String solicitudId) {
		
		Long idSolicitud = null;
		byte[] comprobanteAsignacion = null;
		Map<String, Object> resultado = null;
		String fileName = null;
		
		if (StringUtils.isBlank(solicitudId)) {
			request.setAttribute("msgError","No se ha pasado el folio de la solicitud!");
		} else {
			try {
				idSolicitud = Long.valueOf(Base64Cipher.descrifrar(solicitudId));
								
				//Obtenemos el comprobante de asignacion
				resultado = solicitudBusinessRemote
						.obtenerDocumentoResultanteNss(idSolicitud, null,
								DocumentoPorTipoEnum.COMPROBANTE_LOCALIZACION_NSS
										.getId().intValue());
				if (resultado != null) {
					comprobanteAsignacion = (byte[]) resultado.get("FILE");
					fileName = (String) resultado.get("FILE_NAME");
				
					try {
						if(comprobanteAsignacion != null){
							log.debug("El documento no es nulo");
												
							response.addHeader("Accept-Ranges","bytes");
							response.addHeader("Cache-Control","public");
							response.addHeader("Cache-Control","must-revalidate");
							response.addHeader("Pragma","public");
							response.setContentType("application/pdf");
							response.addHeader("expires","0");
							response.addHeader("Content-disposition", "attachment;filename=\"" + fileName + "\"");
							response.setContentLength(comprobanteAsignacion.length);
							response.getOutputStream().write(comprobanteAsignacion);
							response.flushBuffer();
							
						} else {
							this.log.debug("No hay documento");
						}
					} catch (IOException e) {
						this.log.error("Error al generar el comprobante de recuperacion de NSS -> ", e);
					}
					
					this.log.debug("Termina generación de documentos de solicitud");
				} else {
					this.log.error("No se genero el comprobante NSS de la solicitud "
							+ idSolicitud);
				}
			} catch (NumberFormatException e) {
				this.log.error("Error al generar el comprobante de recuperacion de NSS -> ", e);
			} catch (InvalidKeyException e) {
				this.log.error("Error al generar el comprobante de recuperacion de NSS -> ", e);
			} catch (IllegalBlockSizeException e) {
				this.log.error("Error al generar el comprobante de recuperacion de NSS -> ", e);
			} catch (BadPaddingException e) {
				this.log.error("Error al generar el comprobante de recuperacion de NSS -> ", e);
			} catch (IOException e) {
				this.log.error("Error al generar el comprobante de recuperacion de NSS -> ", e);
			}
		}
	}
}