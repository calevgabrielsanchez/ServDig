package mx.gob.imss.ctirss.delta.gestion.seguroVoluntario.web.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import mx.gob.imss.ctirss.delta.framework.base.web.sso.UsuarioSSO;
import mx.gob.imss.ctirss.delta.gestion.seguroVoluntario.service.interfaces.SeguroIvroServiceRemote;

/**
 * Controller Spring MVC para Baja Expresa (REQ 14-20)
 *
 * Maneja:
 * - Solicitud de baja expresa (POST /baja-expresa/solicitar)
 * - Confirmación de baja expresa (GET/POST /baja-expresa/confirmar)
 *
 * @author Sistema Bajas IMSS
 * @version 1.0
 */
@Controller
@RequestMapping("/baja-expresa")
public class BajaExpresaController {

    private static final Logger LOGGER = LoggerFactory.getLogger(BajaExpresaController.class);

    @Autowired
    private SeguroIvroServiceRemote seguroIvroService;

    // ========================================================================
    // REQ 14-16: SOLICITAR BAJA EXPRESA
    // ========================================================================

    /**
     * POST /baja-expresa/solicitar
     *
     * Endpoint AJAX invocado desde detalle-seguro.jsp cuando el usuario
     * hace clic en "Solicitar Baja Expresa"
     *
     * @param idSeguro ID del seguro a dar de baja
     * @param motivo Motivo de baja (opcional, capturado de textarea)
     * @param request HttpServletRequest para obtener IP y sesión
     * @return JSON { "exito": true/false, "mensaje": "...", "idSolicitud": ... }
     */
    @RequestMapping(value = "/solicitar", method = RequestMethod.POST)
    @ResponseBody
    public RespuestaSolicitudDTO solicitarBaja(
            @RequestParam("idSeguro") Long idSeguro,
            @RequestParam(value = "motivo", required = false) String motivo,
            HttpServletRequest request) {

        RespuestaSolicitudDTO respuesta = new RespuestaSolicitudDTO();

        try {
            // Obtener IP del cliente
            String ipSolicitud = obtenerIpCliente(request);

            LOGGER.info("Solicitud de baja expresa - Seguro: " + idSeguro + ", IP: " +ipSolicitud);
                        

            // Invocar servicio EJB
            Long idSolicitud = seguroIvroService.solicitarBajaExpresa(
                idSeguro,
                motivo,
                "BAJA_EXPRESA",
                ipSolicitud
            );

            respuesta.setExito(true);
            respuesta.setMensaje("Solicitud de baja enviada correctamente. " +
                                "Revise su correo electr&oacute;nico para confirmar la baja.");
            respuesta.setIdSolicitud(idSolicitud);

            LOGGER.info("Solicitud de baja expresa exitosa. ID Solicitud: {}", idSolicitud);

        } catch (Exception ex) {
            LOGGER.error("Error al solicitar baja expresa", ex);
            respuesta.setExito(false);
            respuesta.setMensaje("Error al procesar solicitud: " + ex.getMessage());
        }

        return respuesta;
    }


    // ========================================================================
    // REQ 17-18: CONFIRMAR BAJA EXPRESA (Pantalla de confirmaci�n)
    // ========================================================================

    /**
     * GET /baja-expresa/confirmar?t=TOKEN
     *
     * Endpoint invocado desde el link del correo electr�nico
     * Muestra JSP con form de confirmación
     *
     * @param token Token UUID del link
     * @param model Spring Model para pasar datos al JSP
     * @return Vista JSP "baja-expresa/confirmacion-baja-expresa"
     */
    @RequestMapping(value = "/confirmar", method = RequestMethod.GET)
    public String mostrarConfirmacion(
            @RequestParam("t") String token,
            Model model) {

        LOGGER.info("GET /baja-expresa/confirmar - Token: {}", token);

        // Validar que el token no esté vac�o
        if (StringUtils.isBlank(token)) {
            model.addAttribute("error", "Token inv�lido");
            return "confirmacionErrorBajaexpresa";
        }

        // Pasar token al JSP para el form POST
        model.addAttribute("token", token);

        return "confirmacionBajaexpresa";
    }


    /**
     * POST /baja-expresa/confirmar
     *
     * Endpoint invocado desde el form del JSP cuando el usuario
     * hace clic en "Confirmar Baja Definitivamente"
     *
     * @param token Token UUID (hidden field del form)
     * @param request HttpServletRequest para obtener IP
     * @param model Spring Model
     * @return Vista JSP "baja-expresa/confirmacion-exitosa" o "confirmacion-error"
     */
    @RequestMapping(value = "/confirmar", method = RequestMethod.POST)
    public String confirmarBaja(
            @RequestParam("token") String token,
            HttpServletRequest request,
            Model model) {

        LOGGER.info("POST /baja-expresa/confirmar - Token: {}", token);

        try {
            // Obtener IP del cliente
            String ipConfirmacion = obtenerIpCliente(request);

            LOGGER.info("Confirmando baja expresa - Token: {}, IP: {}", token, ipConfirmacion);

            // Invocar servicio EJB
            boolean exitoso = seguroIvroService.confirmarBajaExpresa(token, ipConfirmacion);

            if (exitoso) {
                model.addAttribute("mensaje", "Su baja ha sido procesada exitosamente.");
                LOGGER.info("Baja expresa confirmada exitosamente - Token: {}", token);
                return "confirmacionExitosaBajaExpresa";
            } else {
                model.addAttribute("error", "No se pudo procesar la baja. Intente nuevamente.");
                return "confirmacionErrorBajaexpresa";
            }

        } catch (Exception ex) {
            LOGGER.error("Error al confirmar baja expresa", ex);

            // Mensajes específicos para el usuario
            String mensajeError = ex.getMessage();
            if (mensajeError.contains("ya fue procesada")) {
                mensajeError = "Esta solicitud ya fue procesada anteriormente.";
            } else if (mensajeError.contains("expirado")) {
                mensajeError = "El enlace ha expirado. Por favor, solicite una nueva baja desde el portal.";
            } else if (mensajeError.contains("inválido")) {
                mensajeError = "El enlace es incorrecto.";
            }
            //Se agrega en caso de que sea un error no controlado, no mostrar detalle
            else {
            	mensajeError = "Error. Favor de intentar m�s tarde";
            	LOGGER.info("Error no controlado al confirmar baja: "+mensajeError);
            }

            model.addAttribute("error", mensajeError);
            return "confirmacionErrorBajaexpresa";
        }
    }


    // ========================================================================
    // MÉTODOS UTILITARIOS
    // ========================================================================

    /**
     * Obtener IP real del cliente (considera proxies y balanceadores)
     *
     * @param request HttpServletRequest
     * @return IP del cliente
     */
    private String obtenerIpCliente(HttpServletRequest request) {
        String ip = request.getHeader("X-Forwarded-For");

        if (StringUtils.isBlank(ip) || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (StringUtils.isBlank(ip) || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (StringUtils.isBlank(ip) || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (StringUtils.isBlank(ip) || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (StringUtils.isBlank(ip) || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }

        // Si hay múltiples IPs (proxies), tomar la primera
        if (ip != null && ip.contains(",")) {
            ip = ip.split(",")[0].trim();
        }

        return ip;
    }


    // ========================================================================
    // DTOs INTERNOS
    // ========================================================================

    /**
     * DTO para respuesta JSON de solicitud de baja
     */
    public static class RespuestaSolicitudDTO {
        private boolean exito;
        private String mensaje;
        private Long idSolicitud;

        public boolean isExito() {
            return exito;
        }

        public void setExito(boolean exito) {
            this.exito = exito;
        }

        public String getMensaje() {
            return mensaje;
        }

        public void setMensaje(String mensaje) {
            this.mensaje = mensaje;
        }

        public Long getIdSolicitud() {
            return idSolicitud;
        }

        public void setIdSolicitud(Long idSolicitud) {
            this.idSolicitud = idSolicitud;
        }
    }
}
