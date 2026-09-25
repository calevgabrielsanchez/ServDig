package mx.gob.imss.ctirss.delta.riesgosTrabajo.web.controller;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import mx.gob.imss.ctirss.delta.exception.riesgosTrabajo.RiesgosTrabajoException;
import mx.gob.imss.ctirss.delta.framework.base.controller.AbstractController;
import mx.gob.imss.ctirss.delta.model.enums.TipoDescargaArchivo;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.OrigenSolicitudEnum;
import mx.gob.imss.ctirss.delta.model.riesgosTrabajo.PatronRiesgosTrabajo;
import mx.gob.imss.ctirss.delta.model.riesgosTrabajo.RiesgoTrabajo;
import mx.gob.imss.ctirss.delta.riesgosTrabajo.web.util.UtilRTT;
import mx.gob.imss.distss.delta.rtt.service.interfaces.ConsultalRiesgoTrabajoServiceRemote;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "/wizard/riesgosTrabajo/")
public class ReporteRiesgosTrabajoController extends AbstractController {

    private static final Logger LOGGER = LoggerFactory
            .getLogger(ReporteRiesgosTrabajoController.class);

    private static final String RIESGO_TRABAJO_CONSULTA = "consultaRiesgosTrabajo";
    private static final String RIESGO_TRABAJO_ERROR = "errorRiesgostrabajo";
    private static final String RIESGO_TRABAJO_TYC = "terminosCondiciones";

    @Autowired
    private ConsultalRiesgoTrabajoServiceRemote generaReporteService;
    
    /**
     * 
     * @param session
     * @param id
     * @param rp
     * @param razonSocial
     * @return
     */
    @RequestMapping(value = "/tyc/{id}/{rp}/{razonSocial}", method = {RequestMethod.GET, RequestMethod.POST})
    public String terminosCondiciones(HttpSession session, Model model, @PathVariable String id,
            @PathVariable String rp, @PathVariable String razonSocial) {
        LOGGER.debug("Buscamos riesgos de trabajo terminados");
        String view = RIESGO_TRABAJO_TYC;
        session.setAttribute("id", id);
        session.setAttribute("rp", rp);
        session.setAttribute("razonSocial", razonSocial);
        session.setAttribute("fechaCarta", UtilRTT.obtenerFecha(new Date()));
        //Busca si ya se aceptaron tyc
		try {
			PatronRiesgosTrabajo patron = generaReporteService.buscarPatron(rp,null,null,null, OrigenSolicitudEnum.INTERNET);
			if(generaReporteService.validarTerminosCondiciones(patron)){
				LOGGER.debug("TYC ya aceptados");
				return obtenerComprobanteFiscalPorPeriodo(session);
			}
		} catch (RiesgosTrabajoException e) {
			LOGGER.debug(e.getMessage());
            view = RIESGO_TRABAJO_ERROR;
		}
        return view;
    }
    
    /**
     * Aceptar Terminos y Condiciones
     *
     * @param session
     * @return
     */
    @RequestMapping(value = "/aceptar", method = {RequestMethod.GET, RequestMethod.POST})
    public String aceptarTerminosCondiciones(HttpSession session) {

        LOGGER.debug("Buscamos riesgos de trabajo terminados TYC");

        String view = RIESGO_TRABAJO_CONSULTA;

        //Obtenemos los datos del patron
        PatronRiesgosTrabajo patron;
        try {
        	String rp = (String) session.getAttribute("rp");
            patron = generaReporteService.buscarPatron(rp,null,null,null, OrigenSolicitudEnum.INTERNET);
            //Guardamos al patron en sesion
            session.setAttribute("patron", patron);
            LOGGER.debug("Los datos del patron son " + patron);
            generaReporteService.aceptarTerminosCondiciones(patron);
            return obtenerComprobanteFiscalPorPeriodo(session);
        } catch (RiesgosTrabajoException e) {
            LOGGER.debug(e.getMessage());
            view = RIESGO_TRABAJO_ERROR;
        }

        return view;

    }

    /**
     * Busca los riesgo de trabajo y los muestra en pantalla
     *
     * @param session
     * @param id
     * @param rp
     * @param razonSocial
     * @return
     */
    @RequestMapping(value = "/porPeriodo", method = {RequestMethod.GET, RequestMethod.POST})
    public String obtenerComprobanteFiscalPorPeriodo(HttpSession session) {

        LOGGER.debug("Buscamos riesgos de trabajo terminados");

        String view = RIESGO_TRABAJO_CONSULTA;

        //Obtenemos los datos del patron
        PatronRiesgosTrabajo patron;
        try {
        	String rp = (String) session.getAttribute("rp");
            patron = generaReporteService.buscarPatron(rp,null,null,null, OrigenSolicitudEnum.INTERNET);
            
            //Guardamos al patron en sesion
            session.setAttribute("patron", patron);
            session.setAttribute("periodoConsulta", this.obtenerPeriodoHistorial(patron.getInicioPeriodo(), patron.getFinPeriodo(), OrigenSolicitudEnum.INTERNET));
            session.setAttribute("fechaTramite", new Date());
            LOGGER.debug("Los datos del patron son " + patron);

            //Se buscan los riesgos de trabajo
            List<RiesgoTrabajo> riesgosTrabajo = generaReporteService.obtenerRiesgosTrabajoPatronalesPeriodo(patron,OrigenSolicitudEnum.INTERNET);

            //Mandamos los datos a la vista           
            session.setAttribute("riesgosTrabajo", riesgosTrabajo);

            LOGGER.debug("Se muestran riesgos de trabajo");
        } catch (RiesgosTrabajoException e) {
            LOGGER.debug(e.getMessage());
            view = RIESGO_TRABAJO_ERROR;
        }

        return view;

    }

    /**
     * Genera pdf
     *
     * @param response
     * @param request
     * @param session
     * @return
     */
    @RequestMapping(value = "/generarPDF", method = {RequestMethod.POST, RequestMethod.GET})
    public String generarPDFRiesgosTrabajo(HttpServletResponse response, HttpServletRequest request, HttpSession session) {

        //Obtenemos los datos del patron de la session
        PatronRiesgosTrabajo patron = (PatronRiesgosTrabajo) session.getAttribute("patron");

        try {
            //Generamos el excel
            byte[] reportePDF = generaReporteService.generarDocumentoRiesgosTrabajo(patron, TipoDescargaArchivo.PDF,OrigenSolicitudEnum.INTERNET);

            //Si el PDF se genero lo mandamos a la vista
            LOGGER.debug("Se genero el reporte");
            
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            baos.write(reportePDF);
            response.reset();
            response.setHeader("Expires", "0");
            response.setHeader("Cache-Control", "must-revalidate, post-check=0, pre-check=0");
            response.setHeader("Pragma", "public");
            response.setContentType("application/pdf");
            response.addHeader("Content-Disposition", "attachment; filename=Riesgos_Trabajo_" + patron.getNrp() + ".pdf");
            response.setContentLength(baos.toByteArray().length);
            response.getOutputStream().write(baos.toByteArray(), 0, baos.toByteArray().length);
            response.getOutputStream().flush();
            response.getOutputStream().close();

        } catch (RiesgosTrabajoException e) {
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
        } catch (IOException e) {
            LOGGER.debug("Error al generar pdf {}", e);
        }
        return null;

    }

    /**
     * Genera la solicitud y descarga el excel
     *
     * @param response
     * @param request
     * @param model
     * @param session
     * @return
     */
    @RequestMapping(value = "/generarExcel", method = {RequestMethod.POST, RequestMethod.GET})
    public String generarExcelRiesgosTrabajo(HttpServletResponse response, HttpServletRequest request, Model model, HttpSession session) {

        //Obtenemos los datos del patron de la session
        PatronRiesgosTrabajo patron = (PatronRiesgosTrabajo) session.getAttribute("patron");

        try {
            //Generamos el excel
            byte[] reporteXLS = generaReporteService.generarDocumentoRiesgosTrabajo(patron, TipoDescargaArchivo.XLS,OrigenSolicitudEnum.INTERNET);

            //Si el excel se genero lo mandamos a la vista
            LOGGER.debug("Se genero el reporte");
            response.setContentType("application/vnd.ms-excel");
            response.setHeader("Content-Disposition", "attachment; filename=riesgos_trabajo_" + patron.getNrp() + ".xls");
            response.setContentLength(reporteXLS.length);
            OutputStream ouputStream = response.getOutputStream();
            ouputStream.write(reporteXLS, 0, reporteXLS.length);
            ouputStream.flush();
            ouputStream.close();

        } catch (RiesgosTrabajoException e) {
            PrintWriter out;
            try {
                LOGGER.debug("Error al generar xls {}", e);
                //Si el excel no se genero mandamos un mensaje
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
                LOGGER.debug("Error al generar xls {}", e);
            }
        } catch (IOException e) {
            LOGGER.debug("Error al generar xls {}", e);
        }
        return null;

    }

    /**
     * Metodo que valida que no tenga solicitudes en el mes en curso
     *
     * @param model
     * @param session
     * @return
     */
    @RequestMapping(value = "/validarSolicitud")
    public @ResponseBody
    Map<String, ? extends Object> validaSolicitud(Model model, HttpSession session) {

        LOGGER.debug("Iniciamos la validacion");
        
        //Obtenemos los datos del patron de la session
        PatronRiesgosTrabajo patron = (PatronRiesgosTrabajo) session.getAttribute("patron");

        Map<String, Object> result = new HashMap<String, Object>();
        result.put("error", false);
        
        try {
            //Validamos que no tenga solicitudes este mes
            generaReporteService.validarSolicitud(patron);
        } catch (RiesgosTrabajoException e){ 
            LOGGER.debug(e.getMessage());
            result.put("error", true);
            result.put("msg", e.getMessage());
        }
        
        LOGGER.debug("No cuenta con solicitudes para este mes");
        return result;
    }

    /**
     * Metodo para limpiar session
     *
     * @param model
     * @param session
     * @return
     */
    @RequestMapping(value = "/comunes/limpiarDatos")
    public @ResponseBody
    Map<String, ? extends Object> limpiarDatos(Model model, HttpSession session) {

        //Removemos los datos de la seccion
        session.removeAttribute("patron");
        session.removeAttribute("periodoConsulta");
        session.removeAttribute("fechaTramite");
        session.removeAttribute("riesgosTrabajo");

        LOGGER.debug("Se han limpiado los datos de sesion");

        return null;

    }
    
    public String obtenerPeriodoHistorial(Date fechaInicio, Date fechaFinal, OrigenSolicitudEnum origen) {
        Locale locMx = new Locale("es", "MX");
        SimpleDateFormat formatInicio;
        if (origen.equals(OrigenSolicitudEnum.VENTANILLA)) {
            formatInicio = new SimpleDateFormat("dd' de 'MMMM' del 'yyyy", locMx);
        } else {
            formatInicio = new SimpleDateFormat("dd' de 'MMMM", locMx);
        }
        SimpleDateFormat formatFin = new SimpleDateFormat("' al 'dd' de 'MMMM' del 'yyyy", locMx);
        
        return formatInicio.format(fechaInicio) + formatFin.format(fechaFinal);
    }
}
