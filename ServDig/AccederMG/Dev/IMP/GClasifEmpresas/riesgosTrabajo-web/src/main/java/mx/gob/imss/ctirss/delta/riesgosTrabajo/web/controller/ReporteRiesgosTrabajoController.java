package mx.gob.imss.ctirss.delta.riesgosTrabajo.web.controller;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.HashMap;
import java.util.Arrays;
import java.util.zip.DataFormatException;
import java.util.zip.Inflater;
import java.util.Calendar;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import mx.gob.imss.ctirss.delta.exception.riesgosTrabajo.RiesgosTrabajoException;
import mx.gob.imss.ctirss.delta.framework.base.controller.AbstractController;
import mx.gob.imss.ctirss.delta.model.enums.TipoDescargaArchivo;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.OrigenSolicitudEnum;
import mx.gob.imss.ctirss.delta.model.riesgosTrabajo.PatronRiesgosTrabajo;
import mx.gob.imss.ctirss.delta.model.riesgosTrabajo.ReporteRiesgoTrabajo;
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
import org.springframework.web.bind.support.SessionStatus;

@Controller
@RequestMapping(value = "/wizard/riesgosTrabajo/")
public class ReporteRiesgosTrabajoController extends AbstractController {

    private static final Logger LOGGER = LoggerFactory
            .getLogger(ReporteRiesgosTrabajoController.class);

    private static final String RIESGO_TRABAJO_CONSULTA = "consultaRiesgosTrabajo";
    private static final String RIESGO_TRABAJO_ERROR = "errorRiesgostrabajo";
    private static final String RIESGO_TRABAJO_TYC = "terminosCondiciones";
    private static final String RIESGO_TRABAJO_CONSULTA_RFC = "consultaRiesgosTrabajoRfc";
    private static final String PARAMETRO_NO_EXISTE = "paramNotExist";
    private static final String SIN_RIESGO_TRABAJO_CONSULTA = "consultaSinRegistros";


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
            @PathVariable String rp, @PathVariable String razonSocial) throws RiesgosTrabajoException {
        LOGGER.debug("Buscamos riesgos de trabajo terminados");
        String view = RIESGO_TRABAJO_TYC;
        session.setAttribute("id", id);
        session.setAttribute("rp", rp);
        session.setAttribute("rfc", null);
        session.setAttribute("razonSocial", razonSocial);
        session.setAttribute("fechaCarta", UtilRTT.obtenerFecha(new Date()));
        session.setAttribute("fechaTramite", new Date());
        session.setAttribute("tipoParam", "NRP " + rp);
        session.setAttribute("fecSiniestra", UtilRTT.obtenerFechaSiniestralidad());

        int validaNrp = generaReporteService.encuentraNRP(rp);
        if (validaNrp == 1) {
            //Busca si ya se aceptaron tyc
            try {
                PatronRiesgosTrabajo patron = generaReporteService.buscarPatron(rp, null, null, null, OrigenSolicitudEnum.INTERNET);
                if (generaReporteService.validarTerminosCondiciones(patron)) {
                    LOGGER.debug("TYC ya aceptados");
                    return obtenerComprobanteFiscalPorPeriodo(session);
                }
            } catch (RiesgosTrabajoException e) {
                LOGGER.debug(e.getMessage());
                //view = RIESGO_TRABAJO_ERROR;
            }
        } else {
            view = PARAMETRO_NO_EXISTE;
        }
        return view;
    }

    /**
     *
     * @param session
     * @param rfc
     * @param razonSocial
     * @return
     */
    @RequestMapping(value = "/tycrfc/{rfc}/{razonSocial}", method = {RequestMethod.GET, RequestMethod.POST})
    public String terminosCondicionesRfc(HttpSession session, Model model, @PathVariable String rfc, @PathVariable String razonSocial) throws RiesgosTrabajoException {
        LOGGER.debug("Se buscan los riesgos de trabajo terminados por RFC");
        String view = RIESGO_TRABAJO_TYC;
        String rfcUp = rfc.toUpperCase();
        session.setAttribute("rfc", rfcUp);
        session.setAttribute("razonSocial", razonSocial);
        session.setAttribute("fechaCarta", UtilRTT.obtenerFecha(new Date()));
        session.setAttribute("tipoParam", "RFC " + rfcUp);
        session.setAttribute("fechaTramite", new Date());
        session.setAttribute("fecSiniestra", UtilRTT.obtenerFechaSiniestralidad());

        //Se valida si el RFC es correcto
        int validacion = generaReporteService.encuentraRFC(rfcUp);
        if (validacion == 1) {
            //Obtenemos los datos de la persona
            PatronRiesgosTrabajo patron = generaReporteService.buscarPersona(rfc, null, OrigenSolicitudEnum.INTERNET);
            patron.setRazonSocial(razonSocial);

            try {
                session.setAttribute("patron", patron);
                //Busca si ya se aceptaron tyc
                if (generaReporteService.validarTerminosCondicionesRfc(rfcUp)) {
                    LOGGER.debug("TYC ya aceptados");
                    return obtenerComprobanteFiscalRFCPorPeriodo(session, rfcUp);
                }
            } catch (RiesgosTrabajoException e) {
                LOGGER.debug(e.getMessage());
                //view = RIESGO_TRABAJO_ERROR;
            }
            return view;
        } else
            return PARAMETRO_NO_EXISTE;
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
            patron = generaReporteService.buscarPatron(rp, null, null, null, OrigenSolicitudEnum.INTERNET);
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
     * Aceptar Terminos y Condiciones
     *
     * @param session
     * @return
     */
    @RequestMapping(value = "/aceptarRfc", method = {RequestMethod.GET, RequestMethod.POST})
    public String aceptarTerminosCondicionesRfc(HttpSession session) {
        LOGGER.debug("Buscamos riesgos de trabajo terminados TYC");
        String view = RIESGO_TRABAJO_CONSULTA_RFC;
        PatronRiesgosTrabajo patron = (PatronRiesgosTrabajo) session.getAttribute("patron");
        String rfcUp = (String) session.getAttribute("rfc");

        try {
            generaReporteService.aceptarTerminosCondicionesRfc(patron, rfcUp);
            return obtenerComprobanteFiscalRFCPorPeriodo(session, rfcUp);
        } catch (RiesgosTrabajoException e) {
            LOGGER.debug(e.getMessage());
            //view = RIESGO_TRABAJO_ERROR;
        }

        return view;
    }

    /**
     * Busca los riesgo de trabajo y los muestra en pantalla
     *
     * @param session
     * @return
     */
    @RequestMapping(value = "/porPeriodo", method = {RequestMethod.GET, RequestMethod.POST})
    public String obtenerComprobanteFiscalPorPeriodo(HttpSession session) throws RiesgosTrabajoException {
        LOGGER.debug("Buscamos riesgos de trabajo terminados");
        String view = RIESGO_TRABAJO_CONSULTA;
        String tipo = "NRP";

        //Obtenemos los datos del patron
        String rp = (String) session.getAttribute("rp");
        PatronRiesgosTrabajo patron = generaReporteService.buscarPatron(rp, null, null, null, OrigenSolicitudEnum.INTERNET);
        limpiarDatos(null, session);

        //Guardamos al patron en sesion
        session.setAttribute("patron", patron);
        session.setAttribute("periodoConsulta", this.obtenerPeriodoHistorial(patron.getInicioPeriodo(), patron.getFinPeriodo(), OrigenSolicitudEnum.INTERNET));
        session.setAttribute("fechaTramite", new Date());
        session.setAttribute("tipo", tipo);
        session.setAttribute("rp", rp);
        session.setAttribute("rfc", null);
        session.setAttribute("fecSiniestra", UtilRTT.obtenerFechaSiniestralidad());

        LOGGER.debug("Los datos del patron son " + patron);
        //Se buscan los riesgos de trabajo
        List<RiesgoTrabajo> riesgosTrabajo = generaReporteService.obtenerRiesgosTrabajoPatronalesPeriodo(patron, OrigenSolicitudEnum.INTERNET);

        if (riesgosTrabajo.size() != 0 && !riesgosTrabajo.isEmpty() && !riesgosTrabajo.equals(null)) {
            //Mandamos los datos a la vista
            session.setAttribute("riesgosTrabajo", riesgosTrabajo);
            LOGGER.debug("Se muestran riesgos de trabajo");
        } else {
            view = SIN_RIESGO_TRABAJO_CONSULTA;
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
            byte[] reportePDF = generaReporteService.generarDocumentoRiesgosTrabajo(patron, TipoDescargaArchivo.PDF, OrigenSolicitudEnum.INTERNET);

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
            byte[] reporteXLS = generaReporteService.generarDocumentoRiesgosTrabajo(patron, TipoDescargaArchivo.XLS, OrigenSolicitudEnum.INTERNET);

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
        } catch (RiesgosTrabajoException e) {
            LOGGER.debug(e.getMessage());
            result.put("error", true);
            result.put("msg", e.getMessage());
        }

        LOGGER.debug("No cuenta con solicitudes para este mes");
        return result;
    }

    /**
     * Metodo que valida que no tenga solicitudes en el mes en curso
     *
     * @param model
     * @param session
     * @return
     */
    @RequestMapping(value = "/validarSolicitudRfc")
    public @ResponseBody
    Map<String, ? extends Object> validaSolicitudRfc(Model model, HttpSession session) {
        LOGGER.debug("Iniciamos la validacion");

        //Obtenemos los datos del patron de la session
        PatronRiesgosTrabajo patron = (PatronRiesgosTrabajo) session.getAttribute("patron");
        String rfc = (String) session.getAttribute("rfc");

        Map<String, Object> result = new HashMap<String, Object>();
        result.put("error", false);

        try {
            //Validamos que no tenga solicitudes este mes
            generaReporteService.validarSolicitudRfc(rfc);
        } catch (RiesgosTrabajoException e) {
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
        session.removeAttribute("reportObtain");
        session.removeAttribute("fecSiniestra");
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

    /**
     * Genera pdf
     *
     * @param response
     * @param request
     * @param session
     * @return
     */
    @RequestMapping(value = "/descargaPdfRfc/{rfc}/{periodo}", method = {RequestMethod.POST, RequestMethod.GET})
    public String generarPDFRiesgosTrabajoRFC(HttpServletResponse response, HttpServletRequest request, HttpSession session, @PathVariable String rfc, @PathVariable int periodo) throws RiesgosTrabajoException {

        //Obtenemos los datos del patron de la session
        PatronRiesgosTrabajo patron = (PatronRiesgosTrabajo) session.getAttribute("patron");
        List<RiesgoTrabajo> listaRiesgosT = generaReporteService.buscarRtXLisRp(periodo, rfc.toUpperCase(), null, null, OrigenSolicitudEnum.INTERNET);

        try {
            //Generamos el PDF
            byte[] reportePDF = generaReporteService.generarDocumentoRiesgosTrabajoRfc(patron,listaRiesgosT,rfc,TipoDescargaArchivo.PDF,OrigenSolicitudEnum.INTERNET);

            byte[] reporteDco = null;
            try {
                reporteDco = decompress(reportePDF);
            }catch(DataFormatException e){
                e.getStackTrace();
            }

            //Si el PDF se genero lo mandamos a la vista
            LOGGER.debug("Se genero el reporte");

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            baos.write(reporteDco);
            response.reset();
            response.setHeader("Expires", "0");
            response.setHeader("Cache-Control", "must-revalidate, post-check=0, pre-check=0");
            response.setHeader("Pragma", "public");
            response.setContentType("application/pdf");
            response.addHeader("Content-Disposition", "attachment; filename=Riesgos_Trabajo_" + rfc + ".pdf");
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

    private byte[] decompress(byte[] data) throws IOException, DataFormatException {
        Inflater inflater = new Inflater();
        inflater.setInput(data);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
        byte[] buffer = new byte[1024];
        while (!inflater.finished()) {
            int count = inflater.inflate(buffer);
            outputStream.write(buffer, 0, count);
        }
        outputStream.close();
        byte[] output = outputStream.toByteArray();
        LOGGER.debug("Original: " + data.length / 1024 + " Kb");
        LOGGER.debug("Descomprimido: " + output.length / 1024 + " Kb");
        return output;
    }

    /**
     * Busca los riesgo de trabajo y los muestra en pantalla
     *
     * @param session
     * @param rfc
     * @return
     */
    @RequestMapping(value = "/rfcPorPeriodo", method = {RequestMethod.GET, RequestMethod.POST})
    public String obtenerComprobanteFiscalRFCPorPeriodo(HttpSession session, String rfc) throws RiesgosTrabajoException {
        LOGGER.debug("Se buscan riesgos de trabajo terminados");
        String view = RIESGO_TRABAJO_CONSULTA_RFC;
        String tipo = "RFC";
        Object[] reporteObj = new Object[4];

        //Se obtienen los datos del patron de la session
        PatronRiesgosTrabajo patron = (PatronRiesgosTrabajo) session.getAttribute("patron");
        limpiarDatos(null, session);
        session.setAttribute("fechaTramite", new Date());
        session.setAttribute("patron", patron);
        session.setAttribute("periodoConsulta", this.obtenerPeriodoHistorial(patron.getInicioPeriodo(), patron.getFinPeriodo(), OrigenSolicitudEnum.INTERNET));
        session.setAttribute("rp", null);
        session.setAttribute("rfc", rfc);
        session.setAttribute("razonSocial", patron.getRazonSocial());
        session.setAttribute("tipo", tipo);
        session.setAttribute("fecSiniestra", UtilRTT.obtenerFechaSiniestralidad());

        int tamanList = generaReporteService.buscarTamanListaRtxRp(null, rfc, null, null, OrigenSolicitudEnum.INTERNET);
        if (tamanList != 0) {
            try {
                //Se buscan los reportes solicitados
                ReporteRiesgoTrabajo reportesRT = generaReporteService.buscarReportesXrfcEstados(rfc, OrigenSolicitudEnum.INTERNET);

                if (reportesRT == null) {
                    //Se crea un nuevo reporte
                    reportesRT = generaReporteService.crearReporteRfc(rfc, OrigenSolicitudEnum.INTERNET);
                } else {
                    if (calcularDate(reportesRT.getFechaAlta())) {
                        int baja = generaReporteService.bajaReporteRfc(rfc, OrigenSolicitudEnum.INTERNET);
                        reportesRT = generaReporteService.crearReporteRfc(rfc, OrigenSolicitudEnum.INTERNET);
                    } else if (reportesRT.getEstadoReporte() != 1 && reportesRT.getUrlReporte() != null) {
                        if (calGenerate(reportesRT.getFechaAlta())) {
                            //Estados                               Generado
                            //0 - Solicitado, 1 - Disponible        0 - Sin generar, 1 - Generado
                            reportesRT.setEstadoReporte(1L);
                            reportesRT = generaReporteService.actualizaReportesRfc(reportesRT, OrigenSolicitudEnum.INTERNET);
                        }
                    }
                }

                reporteObj[0] = reportesRT.getRfc();
                reporteObj[1] = reportesRT.getFechaAlta();
                if (reportesRT.getEstadoReporte() == 0) {
                    reporteObj[2] = "Solicitado";
                } else if (reportesRT.getEstadoReporte() == 1) {
                    reporteObj[2] = "Disponible";
                }
                reporteObj[3] = reportesRT.getEstadoReporte();
                session.setAttribute("reportObtain", reporteObj);
                generaDocumentoXlsRfc(session, rfc, patron, OrigenSolicitudEnum.INTERNET);
            } catch (RiesgosTrabajoException ex) {
                LOGGER.debug(ex.getMessage());
            }
        } else {
            session.setAttribute("reportObtain", reporteObj);
            reporteObj[0] = rfc;
            reporteObj[1] = new Date();
            view = SIN_RIESGO_TRABAJO_CONSULTA;
        }

        return view;
    }

    public boolean calcularDate(Date dateReporte) {
        boolean respuesta = false;
        SimpleDateFormat formato = new SimpleDateFormat("dd-MM-yyyy");

        Calendar cal = Calendar.getInstance();
        cal.setTime(dateReporte);
        cal.add(Calendar.DAY_OF_MONTH, 3);
        String strCal = new SimpleDateFormat("dd-MM-yyyy").format(cal.getTime());
        try {
        Date dateUno = formato.parse(strCal);
        Calendar calD = Calendar.getInstance();
        calD.setTime(new Date());
        String strCalD = new SimpleDateFormat("dd-MM-yyyy").format(calD.getTime());
        Date dateDos = formato.parse(strCalD);

        LOGGER.debug("Es el resultado hoy: " + dateDos);
        LOGGER.debug("Es el resultado fecha reporte: " + dateUno);
        respuesta = dateDos.after(dateUno);
        LOGGER.debug("Es el resultado de la comparativa: " + respuesta);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return respuesta;
    }

    public boolean calGenerate(Date dateReporte) {
        boolean respuesta = false;
        SimpleDateFormat formato = new SimpleDateFormat("dd-MM-yyyy");
        Date hoy = new Date();

        Calendar c = Calendar.getInstance();
        c.setTime(dateReporte);
        c.add(Calendar.DATE, 1);
        String strCal = new SimpleDateFormat("dd-MM-yyyy").format(c.getTime());
        try{
            Date dateConver = formato.parse(strCal);

        c = Calendar.getInstance();
        c.setTime(dateConver);
        c.add(Calendar.HOUR, 4);
        Date report = c.getTime();

        LOGGER.debug("Es el resultado hoy, hora: " + hoy);
        LOGGER.debug("Es el resultado fecha reporte: " + report);
        respuesta = hoy.after(report);
        LOGGER.debug("Es el resultado de la comparativa: " + respuesta);
    } catch (ParseException e) {
        e.printStackTrace();
    }
        return respuesta;
    }

    public void generaDocumentoXlsRfc(HttpSession session, String rfc, PatronRiesgosTrabajo patron, OrigenSolicitudEnum origen) throws RiesgosTrabajoException {
        boolean generar = false;
        String rfcUp = rfc.toUpperCase();
        LOGGER.debug("Ya entro a asyncrono");

        //Buscando los reportes solicitados
        ReporteRiesgoTrabajo reportesRT = generaReporteService.buscarReportesXrfcEstados(rfcUp, origen);
        ReporteRiesgoTrabajo reportObtain = reportesRT;

        if (reportesRT != null) {
            if (reportesRT.getGeneraReporte() == 0) {
                generar = true;
            }
        }

        if (generar) {
            List<RiesgoTrabajo> riesgosTrabajo = null;
            byte[] excelObtain = null;
            try {
                //Obtenemos los Registros por RFC
                if (!rfc.equals("sinRFC")) {
                    LOGGER.debug("Datos enviados, RFC: " + rfcUp);
                    int tamanList = 0;
                    int liminMenor = 25000;
                    tamanList = generaReporteService.buscarTamanListaRtxRp(null, rfcUp, null, null, origen);

                    if (tamanList <= liminMenor) {
                        riesgosTrabajo = generaReporteService.buscarRtXLisRp(null, rfcUp, null, null, origen);
                        //Genera en excel en back
                        excelObtain = generarExcelXRfcSAveDB(patron, riesgosTrabajo, rfcUp, origen);
                    } else {
                        int resp = generaReporteService.guardarxlsGeneradoBack(null, rfcUp, null, null, origen, patron);
                        if (resp == 4) {
                            //Se actualiza el nuevo reporte con la fecha de creación del documento
                            reportesRT.setUrlReporte("Generado");
                            //reportesRT.setDocumento(excelObtain);
                            reportesRT.setGeneraReporte(1L);
                            reportObtain = generaReporteService.actualizaReportesRfc(reportesRT, origen);
                        }
                    }
                }

                if (excelObtain != null) {
                    reportObtain.setDocumento(excelObtain);
                    int paquetes = 4;
                    int valorPaquete = (int) Math.ceil((float) reportObtain.getDocumento().length / paquetes);
                    int i = 0;
                    int ac = 0;
                    ReporteRiesgoTrabajo docuEnv = new ReporteRiesgoTrabajo();
                    docuEnv.setRfc(reportesRT.getRfc());
                    for (i = 0; i < (paquetes - 1); i++) {
                        byte[] aux = Arrays.copyOfRange(reportObtain.getDocumento(), i * valorPaquete, (i + 1) * valorPaquete);
                        //enviar a guardar a la DB
                        docuEnv.setDocumento(aux);
                        ac += generaReporteService.actualizaDocumentOnlyRfc(docuEnv, origen);
                    }

                    if (ac == 3) {
                        byte[] aux2 = Arrays.copyOfRange(reportObtain.getDocumento(), i * valorPaquete, reportObtain.getDocumento().length);
                        //enviar a guardar a la DB
                        docuEnv.setDocumento(aux2);
                        ac += generaReporteService.actualizaDocumentOnlyRfc(docuEnv, origen);

                        //Se actualiza el nuevo reporte con la fecha de creación del documento
                        reportesRT.setUrlReporte("Generado");
                        //reportesRT.setDocumento(excelObtain);
                        reportesRT.setGeneraReporte(1L);
                        reportObtain = generaReporteService.actualizaReportesRfc(reportesRT, origen);
                    }
                }
            } catch (RiesgosTrabajoException ex) {
                LOGGER.debug(ex.getMessage());
            }
        } else {
            if (reportesRT.getEstadoReporte() != 1) {
                if (calGenerate(reportesRT.getFechaAlta())) {
                    //Estados                               Generado
                    //0 - Solicitado, 1 - Disponible        0 - Sin generar, 1 - Generado
                    reportesRT.setEstadoReporte(1L);
                    reportObtain = generaReporteService.actualizaReportesRfc(reportesRT, origen);
                }
            }
        }
    }

    public byte[] generarExcelXRfcSAveDB(PatronRiesgosTrabajo patron, List<RiesgoTrabajo> riesgosTrabajo, String rfc, OrigenSolicitudEnum origen) {
        byte[] reporteXLS = null;
        try {
            reporteXLS = generaReporteService.generarDocumentoRiesgosTrabajoRfc(patron, riesgosTrabajo, rfc, TipoDescargaArchivo.XLS, origen);
            LOGGER.debug("Se genero el reporte");
        } catch (RiesgosTrabajoException ex) {
            LOGGER.debug("Error al generar xls {}", ex);
        }
        return reporteXLS;
    }

    /**
     * Busca el archivo de excel para descargarlo
     *
     * @param model
     * @param sessionStatus
     * @param session
     * @param rfc
     * @param response
     * @param request
     * @return
     * @throws RiesgosTrabajoException
     */
    @RequestMapping(value = "/descargaExcelRttRfc/{rfc}/{opt}", method = {RequestMethod.POST, RequestMethod.GET})
    public String descargaExcelRttRfc(Model model, SessionStatus sessionStatus, HttpSession session, @PathVariable String rfc, @PathVariable String opt, HttpServletResponse response, HttpServletRequest request) throws RiesgosTrabajoException, IOException {
        String rfcUp = rfc.toUpperCase();
        //Obtenemos los datos del patron de la session
        PatronRiesgosTrabajo patron = (PatronRiesgosTrabajo) session.getAttribute("patron");
        ReporteRiesgoTrabajo traerDocu = null;
        byte[] reporteDco = null;

        try {

            byte[] unico = bytesToFile(rfc, OrigenSolicitudEnum.INTERNET);
            //reporteDco = decompress(reporteRT.getDocumento());
            byte[] rev = decompress(unico);
            reporteDco = new byte[rev.length];
            reporteDco = rev;
        } catch (DataFormatException e) {
            e.getStackTrace();
        }

        //Si el excel se genero lo mandamos a la vista
        response.setContentType("application/vnd.ms-excel");
        response.setHeader("Content-Disposition", "attachment; filename=riesgos_trabajo_" + rfcUp + ".xlsx");
        response.setContentLength(reporteDco.length);
        OutputStream ouputStream = response.getOutputStream();
        ouputStream.write(reporteDco, 0, reporteDco.length);
        ouputStream.flush();
        ouputStream.close();

        return null;
    }

    private byte[] bytesToFile(String rfc, OrigenSolicitudEnum origen) {
        List<byte[]> datos = new ArrayList<byte[]>();
        ReporteRiesgoTrabajo traerDocu = null;
        int tamanho = 0;

        for (int i = 0; i <= 3; i++) {
            try {
                traerDocu = generaReporteService.descargaDocumentOnlyRfc(rfc.toUpperCase(), i, origen);
                datos.add(traerDocu.getDocumento());
                LOGGER.debug("traerDocu: " + i + " - " + traerDocu.getDocumento().length);
            } catch (RiesgosTrabajoException e) {
                e.printStackTrace();
            }
        }

        for (int o = datos.size(); o > 0; o--) {
            tamanho = tamanho + datos.get(o - 1).length;
        }

        LOGGER.debug("tamanho: " + tamanho);
        LOGGER.debug("datos.size(): " + datos.size());
        byte[] bytes = new byte[tamanho];
        int inicia = 0;
        try {
            for (int e = 0; e < datos.size(); e++) {
                System.arraycopy(datos.get(e), 0, bytes, inicia, datos.get(e).length);  //Copiamos todos los datos de la lista a bytes en orden
                inicia += datos.get(e).length;
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            e.printStackTrace();
            LOGGER.debug("Imposible guardar el archivo");
        }

        return bytes;
    }
}
