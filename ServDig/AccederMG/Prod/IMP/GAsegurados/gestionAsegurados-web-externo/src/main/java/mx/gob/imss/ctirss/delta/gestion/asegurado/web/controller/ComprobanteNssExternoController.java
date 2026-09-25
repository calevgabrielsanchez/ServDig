
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
public class ComprobanteNssExternoController extends AbstractController {
	
    @Autowired
	private transient SolicitudBusinessRemote solicitudBusinessRemote;
    
	/**
	 * Recupera el comprobante para una asignaci&oacute;n de NSS
	 * 
	 * @param folio
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/comprobante/interno", method = RequestMethod.GET)
	public void obtenerComprobanteAsignacion(Model model,
			final HttpServletRequest request, HttpServletResponse response,
			HttpSession session, @RequestParam("si") String solicitudId) {
		
		Map<String, Object> resultado = null;
		Long idSolicitud = null;
		byte[] comprobanteAsignacion = (byte[]) session.getAttribute("comprobanteNSS");
		Long idSolicitudSession = (Long) session.getAttribute("idSolicitudReporte");
		String fileName = null;
		
		if(comprobanteAsignacion != null) {
			fileName = "comprobanteNss";
		}
		
		if (StringUtils.isBlank(solicitudId)) {
			request.setAttribute("msgError","No se ha pasado el folio de la solicitud!");
		} else {
			try {
				idSolicitud = Long.valueOf(Base64Cipher.descrifrar(solicitudId));
				
				if(comprobanteAsignacion == null || (idSolicitudSession != null && !idSolicitudSession.equals(idSolicitud))) {
					log.debug("El comprobante de nss sin qr no se encontro en session");
					resultado = solicitudBusinessRemote.obtenerDocumentoResultanteNss(idSolicitud, null,
									DocumentoPorTipoEnum.COMPROBANTE_ASIGNACION.getId().intValue());
					
					if (resultado != null) {
						comprobanteAsignacion = (byte[]) resultado.get("FILE");
						fileName = (String) resultado.get("FILE_NAME");
					} else {
						this.log.error("No se genero el comprobante NSS de la solicitud "
								+ idSolicitud);
					}
				} else {
					log.debug("El comprobante de nss sin qr se encontro en session");
				}

				if(comprobanteAsignacion != null){
					log.debug("El documento sin qr no es nulo");

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
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/imprimeNSS", method=RequestMethod.POST)
	public void getComprobanteAsignacionQR(@RequestParam("si") String solicitudId,HttpSession session, HttpServletResponse response) {
		
		Map<String, Object> resultado = null;
		byte[] comprobanteAsignacion = (byte[]) session.getAttribute("tarjetaNSS");
		Long idSolicitudSession = (Long) session.getAttribute("idSolicitudReporte");
		String fileName = null;
		Long idSolicitud = null;
		
		if(comprobanteAsignacion != null) {
			fileName = "tarjetaNss";
		}
		
		try {
			idSolicitud = Long.valueOf(Base64Cipher.descrifrar(solicitudId));
		} catch (NumberFormatException e) {
			this.log.error("Error al generar el comprobante de asignacion de NSS -> ", e);
			return;
		} catch (InvalidKeyException e) {
			this.log.error("Error al generar el comprobante de asignacion de NSS -> ", e);
			return;
		} catch (IllegalBlockSizeException e) {
			this.log.error("Error al generar el comprobante de asignacion de NSS -> ", e);
			return;
		} catch (BadPaddingException e) {
			this.log.error("Error al generar el comprobante de asignacion de NSS -> ", e);
			return;
		} catch (IOException e) {
			this.log.error("Error al generar el comprobante de asignacion de NSS -> ", e);
			return;
		}
		
		log.debug("El id de la solicitud en session es: " + idSolicitudSession + " y el id de la solicitud en recibido es : " + idSolicitud);
		try {
			
			if(comprobanteAsignacion == null || (idSolicitudSession != null && !idSolicitudSession.equals(idSolicitud))) {
				log.debug("El comprobante no se encontro en session");
				resultado = solicitudBusinessRemote.obtenerDocumentoResultanteNss(idSolicitud, null,
								DocumentoPorTipoEnum.COMPROBANTE_ASIGNACION_SIMPLE_QR.getId().intValue());
				if (resultado != null) {
					comprobanteAsignacion = (byte[]) resultado.get("FILE");
					fileName = (String) resultado.get("FILE_NAME");
				}
			} else {
				log.debug("El comprobante se encontro en session");
			}

			if(comprobanteAsignacion != null){
				log.debug("El documento no es nulo");

				response.addHeader("Accept-Ranges", "bytes");
				response.addHeader("Cache-Control", "public");
				response.addHeader("Cache-Control", "must-revalidate");
				response.addHeader("Pragma", "public");
				response.setContentType("application/pdf");
				response.addHeader("expires", "0");
				response.addHeader("Content-disposition", "inline;filename=" + fileName);
				response.setContentLength(comprobanteAsignacion.length);
				response.getOutputStream().write(comprobanteAsignacion);
				response.getOutputStream().close();
			} else {
				log.debug("No hay documento");
			}
		} catch (IOException e) {
			log.error(e);
		}
	}
	
	/**
	 * Recupera el comprobante para una asignaci&oacute;n de NSS
	 * 
	 * @param folio
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/comprobante/simple", method = RequestMethod.GET)
	public void obtenerComprobanteAsignacionSimple(Model model,
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
}
