package mx.gob.imss.ctirss.delta.riesgosTrabajo.web.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import mx.gob.imss.ctirss.delta.exception.riesgosTrabajo.RiesgosTrabajoException;
import mx.gob.imss.ctirss.delta.framework.base.controller.AbstractController;
import mx.gob.imss.ctirss.delta.model.enums.TipoDescargaArchivo;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.OrigenSolicitudEnum;
import mx.gob.imss.ctirss.delta.model.riesgosTrabajo.PatronRiesgosTrabajo;
import mx.gob.imss.distss.delta.rtt.service.interfaces.ConsultalRiesgoTrabajoServiceRemote;

import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "/wizard/riesgosTrabajoNPIE/")
public class NpieReporteRiesgosTrabajoController extends AbstractController {

    private static final Logger LOGGER = LoggerFactory
            .getLogger(NpieReporteRiesgosTrabajoController.class);

    @Autowired
    private ConsultalRiesgoTrabajoServiceRemote generaReporteNPIEService;

    /**
     * Genera el reporte
     *
     * @param response
     * @param codeInformation
     * @param session
     * @return
     */
    @RequestMapping(value = "/{codeInformation}", method = {RequestMethod.GET, RequestMethod.POST})
    public String obtenerRiesgosTrabajo(HttpServletResponse response, @PathVariable String codeInformation, HttpSession session) {

        LOGGER.debug("Buscar riesgos de trabajo");

        String view = null;

        //rfcytruggf|razonsocial.sa.cv|RP54657698787|PDF
        Base64 decoder = new Base64();
        String code="tebm|alsdj|A085149010|PDF";
        String codeXLS="tebm|alsdj|A085149010|XLS";
        String codeXML="tebm|alsdj|A085149010|XML";
        String codigo = new String(decoder.encode(code.getBytes()));
        String codigoXLS = new String(decoder.encode(codeXLS.getBytes()));
        String codigoXML = new String(decoder.encode(codeXML.getBytes()));
        LOGGER.debug("la llave generada es " + codigo + " la llave xls es " + codigoXLS + " la llave xml es " + codigoXML);
       
        byte[] reporte;
        PatronRiesgosTrabajo patron = new PatronRiesgosTrabajo();
        try {

            //Desencriptamos la informacion
            byte[] decodedByteArray = (byte[]) decoder.decode(codeInformation.getBytes());

            String datos = new String(decodedByteArray);
            String[] tokens = datos.split("\\|");

            // Obtenemos los datos de la cadena
            if (tokens.length > 3) {

                //Obtenemos los datos del patron
                patron = generaReporteNPIEService.buscarPatron(tokens[2],null,null,null, OrigenSolicitudEnum.INTERNET);
                LOGGER.debug("El patron es {}", patron);

                //obtenemos el tipo de decomento
                String tipoDocumento = tokens[3];

                response.reset();
                response.setHeader("Expires", "0");
                response.setHeader("Cache-Control", "must-revalidate, post-check=0, pre-check=0");
                response.setHeader("Pragma", "public");

                if (tipoDocumento.equalsIgnoreCase("PDF")) {
                    //Generamos el pdf
                    LOGGER.debug("generamos  PDF");
                    generaReporteNPIEService.validarSolicitud(patron);
                    reporte = generaReporteNPIEService.generarDocumentoRiesgosTrabajo(patron, TipoDescargaArchivo.PDF,OrigenSolicitudEnum.INTERNET);
                    response.setContentType("application/pdf");
                } else if (tipoDocumento.equalsIgnoreCase("XLS")) {
                    //Generamos Excel
                    LOGGER.debug("Generamos Excel");
                    reporte = generaReporteNPIEService.generarDocumentoRiesgosTrabajo(patron, TipoDescargaArchivo.XLS,OrigenSolicitudEnum.INTERNET);
                    response.setContentType("application/vnd.ms-excel");
                    response.setHeader("Content-Disposition", "attachment; filename=riesgos_trabajo_" + patron.getNrp() + ".xls");
                } else {
                    //Generamos XML
                    LOGGER.debug("Generamos XML");
                    reporte = generaReporteNPIEService.generarDocumentoRiesgosTrabajo(patron, TipoDescargaArchivo.XML,OrigenSolicitudEnum.INTERNET);
                    response.setContentType("application/xml");
                }

                response.setContentLength(reporte.length);
                response.getOutputStream().write(reporte, 0, reporte.length);
                response.getOutputStream().flush();
                response.getOutputStream().close();
                LOGGER.debug("Enviamos reporte");
            }

        } catch (IOException e) {
            log.error("Error al recuperar el documento: {}" + e.getMessage());
            response.reset();
        } catch (RiesgosTrabajoException e) {
            log.error("Error al recuperar el documento: {}" + e.getMessage());
            PrintWriter out;
            try {
                LOGGER.debug("Error al generar pdf {}", e);
                //Si el pdf no se genero mandamos un mensaje
                response.reset();
                response.setHeader("Expires", "0");
                response.setHeader("Cache-Control", "no-cache");
                response.setContentType("text/html; charset=UTF-8");
                out = response.getWriter();
                out.println("<link type=\"text/css\" href=\"/delta/resources/estilos/bootstrap/bootstrap.min.css\" rel=\"stylesheet\" />");
                out.println("<html><body><div style=\"text-align: center;\" class=\"alert alert-danger\"><h4>" + e.getMessage()
                        + "</h4></div></body></html>");
                response.setStatus(HttpServletResponse.SC_OK);
            } catch (IOException ex) {
                LOGGER.debug("Error al generar pdf {}", e);
            }
        }

        return view;
    }
    
    /**
     * Validar si se acepto la carta
     * 
     * @param session
     * @param model
     * @param rp
     * @return
     */
    @RequestMapping(value = "/validarCarta/{rp}", method = {RequestMethod.GET, RequestMethod.POST})
    public @ResponseBody Map<String, ? extends Object> validarCarta(@PathVariable String rp) {
    	Map<String, Boolean> res = new HashMap<String, Boolean>();
	    res.put("respuesta", false);
        LOGGER.debug("Validar Carta NPIE");
		try {
			PatronRiesgosTrabajo patron = generaReporteNPIEService.buscarPatron(rp,null,null,null, OrigenSolicitudEnum.INTERNET);
			if(generaReporteNPIEService.validarTerminosCondiciones(patron)){
				LOGGER.debug("TYC ya aceptados");
				res.put("respuesta", true);
			}
		} catch (RiesgosTrabajoException e) {
			LOGGER.debug(e.getMessage());
		}
		return res;
    }
    
    /**
     * Aceptar carta de terminos y condiciones, el rp se espera en base64
     * @param rp
     * @return
     */
    @RequestMapping(value = "/aceptarCarta/{rp}", method = {RequestMethod.GET, RequestMethod.POST})
    public @ResponseBody Map<String, ? extends Object> aceptarCarta(@PathVariable String rp) {
    	Map<String, Boolean> res = new HashMap<String, Boolean>();
    	res.put("respuesta", false);
        LOGGER.debug("Aceptar Carta NPIE");
        try {
        	Base64 decoder = new Base64();
            byte[] decodedByteArray = (byte[]) decoder.decode(rp.getBytes());
            String rpDecoded = new String(decodedByteArray);
            LOGGER.debug("RP Decodificado: "+rpDecoded);
        	PatronRiesgosTrabajo patron = generaReporteNPIEService.buscarPatron(rpDecoded,null,null,null, OrigenSolicitudEnum.INTERNET);
        	if(generaReporteNPIEService.validarTerminosCondiciones(patron)){
				LOGGER.debug("TYC ya aceptados");
			}else{
				generaReporteNPIEService.aceptarTerminosCondiciones(patron);
				res.put("respuesta", true);
			}
        } catch (RiesgosTrabajoException e) {
            LOGGER.debug(e.getMessage());
        }
        return res;
    }
    
}
