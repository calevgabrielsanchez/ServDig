package mx.gob.imss.ctirss.delta.riesgosTrabajo.web.controller;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.DataFormatException;
import java.util.zip.Inflater;
import java.util.Arrays;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import mx.gob.imss.ctirss.delta.exception.riesgosTrabajo.RiesgosTrabajoException;
import mx.gob.imss.ctirss.delta.framework.base.controller.AbstractController;
import mx.gob.imss.ctirss.delta.framework.base.web.sso.UsuarioSSO;
import mx.gob.imss.ctirss.delta.model.Usuario;
import mx.gob.imss.ctirss.delta.model.derechohabiente.UsuarioFuncionario;
import mx.gob.imss.ctirss.delta.model.derechohabiente.negocio.PerfilUsuario;
import mx.gob.imss.ctirss.delta.model.domicilio.Delegacion;
import mx.gob.imss.ctirss.delta.model.domicilio.Subdelegacion;
import mx.gob.imss.ctirss.delta.model.enums.TipoDescargaArchivo;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.OrigenSolicitudEnum;
import mx.gob.imss.ctirss.delta.model.riesgosTrabajo.PatronRiesgosTrabajo;
import mx.gob.imss.ctirss.delta.model.riesgosTrabajo.ReporteRiesgoTrabajo;
import mx.gob.imss.ctirss.delta.model.riesgosTrabajo.RiesgoTrabajo;
import mx.gob.imss.distss.delta.rtt.service.interfaces.ConsultalRiesgoTrabajoServiceRemote;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.core.io.ClassPathResource;

@Controller
@RequestMapping(value = "/historialRiesgoTrabajo/")
public class ConsultarHistorialRiesgoTrabajoController extends AbstractController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ConsultarHistorialRiesgoTrabajoController.class);

    @Autowired
    private ConsultalRiesgoTrabajoServiceRemote consultaHistRTTService;

    private static final String HISTORIAL_RIESGO_TRABAJO_INICIO = "historialRiesgoTrabajo";
    private static final String HISTORIAL_RIESGO_TRABAJO_NOMBRES = "historialRTTNombres";
    private static final String HISTORIAL_RIESGO_TRABAJO_CONSULTA = "consultaHistorialRiesgosTrabajo";
    private static final String RIESGO_TRABAJO_ERROR = "errorHistorialRiesgos";
    private static final String REPORTE_RIESGO_X_RFC = "consultaReporteXrfc";
    private static final String CONEXION_DATABASE_ERROR = "errorConexionBase";

    /**
     * Verifica si usuario puede consultar los riegos trabajo
     *
     * @param response
     * @param request
     * @param sessionStatus
     * @param session
     * @return
     */
    @RequestMapping(value = "/buscarRiesgosTrabajo", method = {RequestMethod.GET, RequestMethod.POST})
    public String validarUsuario(HttpServletResponse response, HttpServletRequest request, SessionStatus sessionStatus, HttpSession session) {

        LOGGER.debug("Validando perfiles");

        //Se recupera los datos del funcionario
        UsuarioSSO usuario = procesarUsuarioSSO(request);
        LOGGER.debug("El usuario []", usuario);
        session.setAttribute("funcionario", usuario);
        session.setAttribute("usuario", convertUsuarioSSO(usuario));
        return HISTORIAL_RIESGO_TRABAJO_INICIO;
    }

    private Usuario convertUsuarioSSO(UsuarioSSO usuarioSSO) {
        Usuario usuario = null;
        Map<Long, String> perfilPantalla = new HashMap<Long, String>();
        perfilPantalla.put(1L, "CENTRAL");
        perfilPantalla.put(2L, "DELEGACI&Oacute;N");
        perfilPantalla.put(3L, "SUBDELEGACI&Oacute;N");


        if (usuarioSSO != null) {
            usuario = new Usuario();
            usuario.setUsuario(usuarioSSO.getNombre());
            usuario.setPerfilUsuario(new PerfilUsuario());
            Long idPerfilUsuario = null;
            Integer idDelegacion = usuarioSSO.getDelegacion();
            Integer idSubdelegacion = usuarioSSO.getSubdelegacion();

            //si el usuario no trae ni subdelegacion ni delegacion, es normativo
            if (idDelegacion == null && idSubdelegacion == null) {
                idPerfilUsuario = 1L;
            } else if (idDelegacion != null && idSubdelegacion == null) {
                idPerfilUsuario = 2L;
            } else {
                idPerfilUsuario = 3L;
            }

            usuario.getPerfilUsuario().setIdPerfilUsuario(idPerfilUsuario);
            usuario.getPerfilUsuario().setDescripcion(perfilPantalla.get(idPerfilUsuario));

            if (usuario.getPerfilUsuario().getIdPerfilUsuario() != null) {
                long idPerfil = usuario.getPerfilUsuario().getIdPerfilUsuario();
                UsuarioFuncionario usuarioF = new UsuarioFuncionario();
                if (idPerfil == 2) {
                    Delegacion delegacion = consultaHistRTTService.getDelegacionUsuario(usuarioSSO.getDelegacion().longValue());
                    usuarioF.setDelegacion(delegacion);
                } else if (idPerfil == 3) {
                    Subdelegacion subdelegacion = consultaHistRTTService.getSubdelegacionUsuario(usuarioSSO.getSubdelegacion().longValue());
                    usuarioF.setSubdelegacion(subdelegacion);
                    usuarioF.setDelegacion(subdelegacion.getDelegacion());
                }
                usuario.setUsuarioFuncionario(usuarioF);

            }
        }

        return usuario;
    }

    /**
     * Busca los riesgo de trabajo y los muestra en pantalla
     *
     * @param model
     * @param sessionStatus
     * @param session
     * @param rp
     * @return
     */
    @RequestMapping(value = "/consultar/{rp}/{periodo}", method = {RequestMethod.GET, RequestMethod.POST})
    public String obtenerRiesgosTrabajo(Model model, SessionStatus sessionStatus, HttpSession session, @PathVariable String rp,
                                        @PathVariable Integer periodo, @RequestParam(required = false) Long delegacion, @RequestParam(required = false) Long subdelegacion) {

        LOGGER.debug("Entrando a consultar historial");

        //Limpiamos la sesion
        limpiarDatos(model, session);

        String view = HISTORIAL_RIESGO_TRABAJO_CONSULTA;

        try {
            if (!consultaHistRTTService.validaConexDB()) {
                view = CONEXION_DATABASE_ERROR;
                return view;
            }

            //Obtenemos los datos del patron
            PatronRiesgosTrabajo patron = null;
            if (!rp.equals("sinNRP")) {
                patron = consultaHistRTTService.buscarPatron(rp, periodo, delegacion, subdelegacion, OrigenSolicitudEnum.VENTANILLA);
            }

            LOGGER.debug("El periodo de busqueda es de " + patron.getInicioPeriodo() + " a " + patron.getFinPeriodo());
            //obtenemos los riesgos de trabajo 
            List<RiesgoTrabajo> riesgosTrabajo = consultaHistRTTService.obtenerRiesgosTrabajoPatronalesPeriodo(patron, OrigenSolicitudEnum.VENTANILLA);

            //Guardamos los datos del patron en sesion
            session.setAttribute("patron", patron);
            LOGGER.debug("Los datos del patron son " + patron);

            session.setAttribute("riesgosTrabajo", riesgosTrabajo);

        } catch (RiesgosTrabajoException ex) {
            LOGGER.debug(ex.getMessage());
            //mandamos la pantalla de que no tiene riesgos de trabajo
            view = RIESGO_TRABAJO_ERROR;
        }

        return view;
    }

    @RequestMapping(value = "/obtenerRFCs/{rfc}", method = {RequestMethod.GET, RequestMethod.POST})
    public String obtenerRFCs(Model model, SessionStatus sessionStatus, HttpSession session, @PathVariable String rfc,
                              @RequestParam(required = false) Long delegacion, @RequestParam(required = false) Long subdelegacion, @RequestParam(required = false) Integer periodo) {

        LOGGER.debug("Entrando a obtenerRFCs");

        //Limpiamos la sesion
        limpiarDatos(model, session);

        String view = HISTORIAL_RIESGO_TRABAJO_NOMBRES;

        try {
            if (!consultaHistRTTService.validaConexDB()) {
                view = CONEXION_DATABASE_ERROR;
                return view;
            }

            //Obtenemos los datos del patron
            List<Object[]> mapPatrones = null;
            if (!rfc.equals("sinRFC")) {
                mapPatrones = consultaHistRTTService.buscarPatronPorRFC(rfc.toUpperCase(), delegacion, subdelegacion, periodo, OrigenSolicitudEnum.VENTANILLA);
            }
            model.addAttribute("patrones", mapPatrones);
        } catch (RiesgosTrabajoException ex) {
            LOGGER.debug(ex.getMessage());
            //mandamos la pantalla de que no tiene riesgos de trabajo
            view = RIESGO_TRABAJO_ERROR;
        }

        return view;
    }

    @RequestMapping(value = "/consultaRfc/{rfc}", method = {RequestMethod.GET, RequestMethod.POST})
    public String consultaRfc(Model model, HttpSession session, @PathVariable String rfc, @RequestParam(required = false) Long delegacion,
                              @RequestParam(required = false) Long subdelegacion, @RequestParam(required = false) Integer periodo) throws RiesgosTrabajoException, ParseException {
        String view = REPORTE_RIESGO_X_RFC;
        String rfcUp = rfc.toUpperCase();

        if (!consultaHistRTTService.validaConexDB()) {
            view = CONEXION_DATABASE_ERROR;
            return view;
        }

        int validacion = consultaHistRTTService.encuentraRFC(rfcUp);

        if (validacion == 1) {
            try {
                int tamanList = consultaHistRTTService.buscarTamanListaRtxRp(periodo, rfcUp, delegacion, subdelegacion, OrigenSolicitudEnum.VENTANILLA);
                if(tamanList == 0)
                    return RIESGO_TRABAJO_ERROR;

                //Buscando los reportes solicitados
                ReporteRiesgoTrabajo reportesRT = consultaHistRTTService.buscarReportesXrfcEstados(rfcUp, OrigenSolicitudEnum.VENTANILLA);

                if (reportesRT == null) {
                    //Se crea un nuevo reporte
                    reportesRT = consultaHistRTTService.crearReporteRfc(rfcUp, OrigenSolicitudEnum.VENTANILLA);
                } else {
                    if (calcularDate(reportesRT.getFechaAlta())) {
                        int baja = consultaHistRTTService.bajaReporteRfc(rfcUp, OrigenSolicitudEnum.VENTANILLA);
                        reportesRT = consultaHistRTTService.crearReporteRfc(rfcUp, OrigenSolicitudEnum.VENTANILLA);
                    } else if (reportesRT.getEstadoReporte() != 1 && reportesRT.getUrlReporte() != null) {
                        if (calGenerate(reportesRT.getFechaAlta())) {
                            //Estados                               Generado
                            //0 - Solicitado, 1 - Disponible        0 - Sin generar, 1 - Generado
                            reportesRT.setEstadoReporte(1L);
                            reportesRT = consultaHistRTTService.actualizaReportesRfc(reportesRT, OrigenSolicitudEnum.VENTANILLA);
                        }
                    }
                }

                Object[] reporteObj = new Object[4];
                reporteObj[0] = reportesRT.getRfc();
                reporteObj[1] = reportesRT.getFechaAlta();
                if (reportesRT.getEstadoReporte() == 0) {
                    reporteObj[2] = "Solicitado";
                } else if (reportesRT.getEstadoReporte() == 1) {
                    reporteObj[2] = "Disponible";
                }
                reporteObj[3] = reportesRT.getEstadoReporte();

                limpiarDatos(model, session);
                session.setAttribute("reportObtain", reporteObj);
            } catch (RiesgosTrabajoException ex) {
                LOGGER.debug(ex.getMessage());
                //mandamos la pantalla de que no tiene riesgos de trabajo
                view = RIESGO_TRABAJO_ERROR;
            }
            return view;
        } else return RIESGO_TRABAJO_ERROR;
    }

    @RequestMapping(value = "/generaXlsRfc/{rfc}", method = {RequestMethod.GET, RequestMethod.POST})
    public String generaXlsRfc(Model model, HttpSession session, @PathVariable String rfc,
                               @RequestParam(required = false) Long delegacion, @RequestParam(required = false) Long subdelegacion,
                               @RequestParam(required = false) Integer periodo, HttpServletResponse response) throws RiesgosTrabajoException, ParseException {
        //boolean resultExc = false;
        ReporteRiesgoTrabajo reportObtain = new ReporteRiesgoTrabajo();
        //int baja = 3;
        boolean generar = false;
        String view = REPORTE_RIESGO_X_RFC;
        String rfcUp = rfc.toUpperCase();

        int validacion = consultaHistRTTService.encuentraRFC(rfcUp);
        if (validacion == 1) {
            //Buscando los reportes solicitados
            ReporteRiesgoTrabajo reportesRT = consultaHistRTTService.buscarReportesXrfcEstados(rfcUp, OrigenSolicitudEnum.VENTANILLA);
            reportObtain = reportesRT;

            if (reportesRT != null)
                if (reportesRT.getGeneraReporte() == 0)
                    generar = true;

            //Obtenemos los datos de la persona
            PatronRiesgosTrabajo patron = consultaHistRTTService.buscarPersona(rfc, null, OrigenSolicitudEnum.VENTANILLA);

            if (generar) {
                //Limpiamos la sesion
                //limpiarDatos(model, session);

                List<RiesgoTrabajo> riesgosTrabajo = null;
                byte[] excelObtain = null;
                try {
                    //Obtenemos los Registros por RFC
                    if (!rfc.equals("sinRFC")) {
                        LOGGER.debug("Datos enviados, RFC: " + rfcUp + ", Periodo: " + periodo);
                        int tamanList = 0;
                        int liminMenor = 25000;
                        tamanList = consultaHistRTTService.buscarTamanListaRtxRp(periodo, rfcUp, delegacion, subdelegacion, OrigenSolicitudEnum.VENTANILLA);

                        if (tamanList <= liminMenor) {
                            riesgosTrabajo = consultaHistRTTService.buscarRtXLisRp(periodo, rfcUp, delegacion, subdelegacion, OrigenSolicitudEnum.VENTANILLA);
                            //Genera en excel en back
                            excelObtain = generarExcelXRfcSAveDB(response, session, patron, riesgosTrabajo, rfcUp);
                        } else {
                            int resp = consultaHistRTTService.guardarxlsGeneradoBack(periodo, rfcUp, delegacion, subdelegacion, OrigenSolicitudEnum.VENTANILLA, patron);
                            if (resp == 4) {
                                //Se actualiza el nuevo reporte con la fecha de creación del documento
                                reportesRT.setUrlReporte("Generado");
                                //reportesRT.setDocumento(excelObtain);
                                reportesRT.setGeneraReporte(1L);
                                reportObtain = consultaHistRTTService.actualizaReportesRfc(reportesRT, OrigenSolicitudEnum.VENTANILLA);
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
                            ac += consultaHistRTTService.actualizaDocumentOnlyRfc(docuEnv, OrigenSolicitudEnum.VENTANILLA);
                        }

                        if (ac == 3) {
                            byte[] aux2 = Arrays.copyOfRange(reportObtain.getDocumento(), i * valorPaquete, reportObtain.getDocumento().length);
                            //enviar a guardar a la DB
                            docuEnv.setDocumento(aux2);
                            ac += consultaHistRTTService.actualizaDocumentOnlyRfc(docuEnv, OrigenSolicitudEnum.VENTANILLA);

                            //Se actualiza el nuevo reporte con la fecha de creación del documento
                            reportesRT.setUrlReporte("Generado");
                            //reportesRT.setDocumento(excelObtain);
                            reportesRT.setGeneraReporte(1L);
                            reportObtain = consultaHistRTTService.actualizaReportesRfc(reportesRT, OrigenSolicitudEnum.VENTANILLA);
                        }
                    }
                    session.setAttribute("reportObtain", reportObtain);

                } catch (RiesgosTrabajoException ex) {
                    LOGGER.debug(ex.getMessage());
                    //mandamos la pantalla de que no tiene riesgos de trabajo
                    view = RIESGO_TRABAJO_ERROR;
                }
            } else {
                if (reportesRT.getEstadoReporte() != 1) {
                    if (calGenerate(reportesRT.getFechaAlta())) {
                        //Estados                               Generado
                        //0 - Solicitado, 1 - Disponible        0 - Sin generar, 1 - Generado
                        reportesRT.setEstadoReporte(1L);
                        reportObtain = consultaHistRTTService.actualizaReportesRfc(reportesRT, OrigenSolicitudEnum.VENTANILLA);
                    }
                }
                session.setAttribute("reportObtain", reportObtain);
            }
            return null;
        }else return null;
    }

    @RequestMapping(value = "/obtenerNombres/{nombre}/{tipo}", method = {RequestMethod.GET, RequestMethod.POST})
    public String obtenerNombres(Model model, SessionStatus sessionStatus, HttpSession session, @PathVariable String nombre, @PathVariable String tipo,
                                 @RequestParam(required = false) Long delegacion, @RequestParam(required = false) Long subdelegacion, @RequestParam(required = false) Integer periodo) {

        LOGGER.debug("Entrando a consultar historial");

        //Limpiamos la sesion
        limpiarDatos(model, session);

        String view = HISTORIAL_RIESGO_TRABAJO_NOMBRES;

        try {
            if (!consultaHistRTTService.validaConexDB()) {
                view = CONEXION_DATABASE_ERROR;
                return view;
            }

            //Obtenemos los datos del patron
            List<Object[]> mapPatrones = null;
            if (!nombre.equals("sinNombreORazonSocial")) {
                mapPatrones = consultaHistRTTService.buscarPatronPorNombre(nombre.toUpperCase(), OrigenSolicitudEnum.VENTANILLA, tipo, delegacion, subdelegacion, periodo);
            }
            LOGGER.debug("");
            model.addAttribute("patrones", mapPatrones);
        } catch (RiesgosTrabajoException ex) {
            LOGGER.debug(ex.getMessage());
            //mandamos la pantalla de que no tiene riesgos de trabajo
            view = RIESGO_TRABAJO_ERROR;
        }

        return view;
    }

    /**
     * Genera excel
     *
     * @param response
     * @param request
     * @param model
     * @param session
     * @return
     */
    @RequestMapping(value = "/generarExcel", method = {RequestMethod.POST, RequestMethod.GET})
    public String generarExcelRiesgosTrabajo(HttpServletResponse response, HttpServletRequest request, Model model, HttpSession session) throws RiesgosTrabajoException {

        //Obtenemos los datos del patron de la session
        PatronRiesgosTrabajo patron = (PatronRiesgosTrabajo) session.getAttribute("patron");

        try {
            //Generamos el excel
            byte[] reporteXLS = consultaHistRTTService.generarDocumentoRiesgosTrabajo(patron, TipoDescargaArchivo.XLS, OrigenSolicitudEnum.VENTANILLA);

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
            e.printStackTrace();
            LOGGER.debug("Error al generar xls {}", e);
        }
        return null;

    }

    public byte[] generarExcelXRfcSAveDB(HttpServletResponse response, HttpSession session, PatronRiesgosTrabajo patron, List<RiesgoTrabajo> riesgosTrabajo, String rfc) {
        byte[] reporteXLS = null;
        try {
            reporteXLS = consultaHistRTTService.generarDocumentoRiesgosTrabajoRfc(patron, riesgosTrabajo, rfc, TipoDescargaArchivo.XLS, OrigenSolicitudEnum.VENTANILLA);
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
    @RequestMapping(value = "/descargaExcelRfc/{rfc}/{opt}", method = {RequestMethod.POST, RequestMethod.GET})
    public String descargaExcelRfc(Model model, SessionStatus sessionStatus, HttpSession session, @PathVariable String rfc, @PathVariable String opt, HttpServletResponse response, HttpServletRequest request) throws RiesgosTrabajoException, IOException {
        String rfcUp = rfc.toUpperCase();
        //Obtenemos los datos del patron de la session
        PatronRiesgosTrabajo patron = (PatronRiesgosTrabajo) session.getAttribute("patron");
        ReporteRiesgoTrabajo traerDocu = null;

        //Buscando los reportes solicitados
        //ReporteRiesgoTrabajo reporteRT = consultaHistRTTService.buscarReportesXrfc(rfc.toUpperCase());

        byte[] reporteDco = null;

        try {
            byte[] unico = bytesToFile(rfc, OrigenSolicitudEnum.VENTANILLA);
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
                traerDocu = consultaHistRTTService.descargaDocumentOnlyRfc(rfc.toUpperCase(), i, origen);
                datos.add(traerDocu.getDocumento());
                LOGGER.debug("traerDocu: " + i + " - " + traerDocu.getDocumento().length);
            } catch (RiesgosTrabajoException e) {
                e.printStackTrace();
            }
        }

        for (int o = datos.size(); o > 0; o--) {
            tamanho = tamanho + datos.get(o - 1).length;  //vemos el tamanho de la lista para formar mi byte[]
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

    public boolean calcularDate(Date dateReporte) throws ParseException {
        boolean respuesta = false;
        SimpleDateFormat formato = new SimpleDateFormat("dd-MM-yyyy");

        Calendar cal = Calendar.getInstance();
        cal.setTime(dateReporte);
        cal.add(Calendar.DAY_OF_MONTH, 3);
        String strCal = new SimpleDateFormat("dd-MM-yyyy").format(cal.getTime());
        Date dateUno = formato.parse(strCal);

        Calendar calD = Calendar.getInstance();
        calD.setTime(new Date());
        String strCalD = new SimpleDateFormat("dd-MM-yyyy").format(calD.getTime());
        Date dateDos = formato.parse(strCalD);

        LOGGER.debug("Es el resultado hoy: " + dateDos);
        LOGGER.debug("Es el resultado fecha reporte: " + dateUno);
        respuesta = dateDos.after(dateUno);
        LOGGER.debug("Es el resultado de la comparativa: " + respuesta);
        return respuesta;
    }

    public boolean calGenerate(Date dateReporte) throws ParseException {
        boolean respuesta = false;
        SimpleDateFormat formato = new SimpleDateFormat("dd-MM-yyyy");
        Date hoy = new Date();

        Calendar c = Calendar.getInstance();
        c.setTime(dateReporte);
        c.add(Calendar.DATE, 1);
        String strCal = new SimpleDateFormat("dd-MM-yyyy").format(c.getTime());
        Date dateConver = formato.parse(strCal);

        c = Calendar.getInstance();
        c.setTime(dateConver);
        c.add(Calendar.HOUR, 4);
        Date report = c.getTime();

        LOGGER.debug("Es el resultado hoy, hora: " + hoy);
        LOGGER.debug("Es el resultado fecha reporte: " + report);
        respuesta = hoy.after(report);
        LOGGER.debug("Es el resultado de la comparativa: " + respuesta);
        return respuesta;
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
        session.removeAttribute("riesgosTrabajo");

        LOGGER.debug("Se han limpiado los datos de sesion");

        return null;

    }

    @RequestMapping(value = "/comunes/getPeriodos", method = RequestMethod.POST)
    public @ResponseBody
    Map<String, ? extends Object> getPeriodos() {
        Map<String, Object> periodos = new HashMap<String, Object>();
        Calendar fecha = Calendar.getInstance();
        fecha.setTime(new Date());
        int anioLiberacion = 2016;
        int actual = fecha.get(Calendar.YEAR);


        List<Integer> anios = new ArrayList<Integer>();
        anios.add(actual);

        for (int i = 1; i < 6; i++) {
            int periodoAnterior = actual - i;
            if (periodoAnterior >= anioLiberacion) {
                anios.add(periodoAnterior);
                continue;
            }
            break;
        }

        periodos.put("actual", actual);
        periodos.put("periodos", anios);

        return periodos;
    }

    @RequestMapping(value = "/comunes/getDelegaciones", method = RequestMethod.POST)
    public @ResponseBody
    Map<String, ? extends Object> getDelegaciones() {
        Map<String, Object> periodos = new HashMap<String, Object>();
        List<Delegacion> delegaciones = consultaHistRTTService.findDelegacionesActivas();
        periodos.put("delegaciones", delegaciones);

        return periodos;
    }

    @RequestMapping(value = "/comunes/getSubdelegaciones/{idDelegacion}", method = RequestMethod.POST)
    public @ResponseBody
    Map<String, ? extends Object> getSubDelegaciones(@PathVariable Long idDelegacion) {
        Map<String, Object> periodos = new HashMap<String, Object>();
        List<Subdelegacion> subdelegaciones = consultaHistRTTService.findSubDelegacionesActivas(idDelegacion.longValue());
        periodos.put("subdelegaciones", subdelegaciones);

        return periodos;
    }

    /**
     * Confirma conexión con el servidor
     *
     * @return
     */
    @RequestMapping(value = "/connection", method = RequestMethod.GET)
    public @ResponseBody Map<String, ? extends Object> connection() {
        Map<String, Boolean> conecta = new HashMap<String, Boolean>();
        conecta.put("conecta", true);

        return conecta;
    }
}
