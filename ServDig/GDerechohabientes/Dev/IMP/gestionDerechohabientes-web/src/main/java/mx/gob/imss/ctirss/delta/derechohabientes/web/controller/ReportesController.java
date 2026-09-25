package mx.gob.imss.ctirss.delta.derechohabientes.web.controller;

import java.net.HttpURLConnection;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.PrintWriter;

import mx.gob.imss.ctirss.delta.model.util.ReporteEnum;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import mx.gob.imss.ctirss.delta.derechohabientes.service.utility.interfaces.reportes.ReportesRemote;
import mx.gob.imss.ctirss.delta.derechohabientes.web.bean.solicitud.SolicitudesPendAutDataTable;
import mx.gob.imss.ctirss.delta.derechohabientes.web.utils.Constantes;
import mx.gob.imss.ctirss.delta.framework.base.controller.AbstractController;
import mx.gob.imss.ctirss.delta.framework.base.web.sso.UsuarioSSO;
import mx.gob.imss.ctirss.delta.framework.paginador.model.DatosEntradaPaginador;
import mx.gob.imss.ctirss.delta.framework.paginador.model.DatosSalidaPaginador;
import mx.gob.imss.ctirss.delta.model.Usuario;
import mx.gob.imss.ctirss.delta.model.asegurado.AsignacionNSS;
import mx.gob.imss.ctirss.delta.model.derechohabientes.reportes.SpRespuestaCommon;
import mx.gob.imss.ctirss.delta.model.derechohabientes.reportes.SpRespuestaDescargaReporte;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.ReporteRegistro;
import mx.gob.imss.ctirss.delta.model.util.Constants;

@Controller
@RequestMapping("/reportes")
public class ReportesController extends AbstractController {

    @Autowired
    private ReportesRemote reportes;

    @Autowired
    private SessionControler sessionController;

    @RequestMapping(value = "/reporteSav011/{idAsegurado}", method = RequestMethod.GET)
    public void getReporteSav011(@PathVariable("idAsegurado") Integer idAsegurado, HttpServletResponse response) {

        try {
            byte[] res = (byte[]) reportes.getReporteSav011(idAsegurado);

            response.setContentType("application/pdf");
            response.setHeader("Content-Disposition", "inline;filename = sav");
            response.getOutputStream().write(res);
            response.getOutputStream().flush();
            response.getOutputStream().close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @RequestMapping(value = "/generarReporte")
    public String generarReporte(@RequestParam("tramite") Long tipoTramite, @RequestParam("fechaInicio") String fechaInicio,
            @RequestParam("fechaFin") String fechaFin, @RequestParam("nivelreporte") String nivelreporte,
            @RequestParam("delegacion") String delegacion, @RequestParam("subdelegacion") String subdelegacion,
            @RequestParam("folioReporte") String folio, HttpSession session, HttpServletRequest request, HttpServletResponse response, Model model)
            throws Exception {

        long deleg = !delegacion.isEmpty() ? Long.parseLong(delegacion) : 0L;
        long subdeleg = !subdelegacion.isEmpty() ? Long.parseLong(subdelegacion) : 0L;

        UsuarioSSO usuario = this.procesarUsuarioSSO(request);

        log.info("usuario " + usuario);
        log.info("usuario CURP " + usuario.getCurp());
        log.info("usuario subdelegacion " + usuario.getSubdelegacion());
        log.info("folio " + folio);

        session.setAttribute("fechaInicio", fechaInicio);
        session.setAttribute("fechaFin", fechaFin);

        model.addAttribute("nivelreporte", nivelreporte);
        model.addAttribute("delegacion", deleg);
        model.addAttribute("subdelegacion", subdeleg);

        log.debug("//JAS nivelreporte: " + nivelreporte);
        log.debug("//JAS delegacion: " + deleg);
        log.debug("//JAS subdelegacion: " + subdeleg);

        Integer usuarioNacional;

        String detailMessage = Constantes.EMPTY_STR;

        if (Integer.parseInt(nivelreporte) == 1) {
            usuarioNacional = 1;
        } else {
            if (usuario.getSubdelegacion() != null && usuario.getDelegacion() != null) {
                subdeleg = usuario.getSubdelegacion().longValue();
                deleg = usuario.getDelegacion().longValue();
                usuarioNacional = 0;
            } else if (usuario.getDelegacion() != null) {
                deleg = usuario.getDelegacion().longValue();
                usuarioNacional = 0;
            } else {
                usuarioNacional = 1;
            }
        }

        try {
            ReporteEnum reporte = tipoTramite == Constantes.REPORTE_CONYUGE_CONCUBINARIO ? ReporteEnum.CONYUGES : ReporteEnum.UNION_CIVIL;
            String showError = "none";
            if (!folio.equals(Constantes.EMPTY_STR)) {
                String status = reportes.obtieneEstatusReporte(folio, reporte);

                if (status == null) {
                    detailMessage = "El folio de generaci&oacute;n de reporte no existe, favor de validarlo.";
                    showError = "block";
                    model.addAttribute("showError", showError);
                    model.addAttribute("errorDetail", detailMessage);
                    return Constants.HOME_JEFE_DEPTO_SUPER;
                }

                log.info("status folio obtenido de SP " + status);

                if (!status.equalsIgnoreCase(Constantes.ESTATUS_TERMINADO)) {
                    if (status.equalsIgnoreCase(Constantes.ESTATUS_SOLICITADO) || status.equalsIgnoreCase(Constantes.ESTATUS_EN_PROCESO)) {
                        detailMessage = "El reporte a&uacute;n se encuentra en proceso de generaci&oacute;n, favor de consultarlo "
                                + "m&aacute;s tarde, una vez que se encuentre disponible, tienes 3 d&iacute;as naturales para descargarlo.";
                        showError = "block";
                    } else if (status.equals(Constantes.ESTATUS_ELIMINADO)) {
                        detailMessage = "El tiempo de descarga del reporte ya expir&oacute;, favor de generarlo nuevamente.";
                        showError = "block";
                    }
                } else {
                    log.debug("Enviando el folio del reporte terminado " + folio);
                    // Se termin? de generar el reporte
                    session.setAttribute("folioReporte", folio);
                }

            } else {

                // Caso que no se haya enviado el folio, se solicita generacion

                log.debug("Solicitando generacion de reporte...");

                log.debug("usuarioNacional= " + usuarioNacional);
                log.debug("usuario.getCveIdSubdelegacion()= " + usuario.getSubdelegacion());
                log.debug("delegacionSql= " + deleg);
                log.debug("subdelegacionSql= " + subdeleg);

                SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
                //Validacion a un mes el rango de fecha
                long fechaInicialMs = format.parse(fechaInicio).getTime();
                long fechaFinalMs = format.parse(fechaFin).getTime();
                long diferencia = fechaFinalMs - fechaInicialMs;
                double dias = Math.floor(diferencia / (1000 * 60 * 60 * 24));
                if (dias > 30) {
                    detailMessage = "Solo se puede realizar la consulta del reporte hasta un mes como m&aacute;ximo";
                } else {
                    String folioGenerado = reportes
                            .generaReporte(usuario.getCurp(), usuario.getDelegacion() != null ? usuario.getDelegacion().longValue() : 0L,
                                    usuarioNacional, deleg, subdeleg, format.parse(fechaInicio), format.parse(fechaFin), reporte);
                    log.info("Folio generado " + folioGenerado);
                    detailMessage = "Se ha comenzado la generaci&oacute;n del reporte solicitado con el folio siguiente: " + folioGenerado
                            + ", una vez que se encuentre disponible, tienes 3 d&iacute;as naturales para descargarlo.";
                }
                showError = "block";
            }

            model.addAttribute("showError", showError);
            session.setAttribute("tipoTramiteReporte", tipoTramite);
            if (!showError.equals("none")) {
                model.addAttribute("errorDetail", detailMessage);
                return Constants.HOME_JEFE_DEPTO_SUPER;
            }

        } catch (Exception e) {
            response.addHeader("Set-Cookie", "fileDownloadError=true;Path=/");
            e.printStackTrace();
        }

        //if (tipoTramite == Constantes.REPORTE_CONYUGE_CONCUBINARIO) {
        return Constants.URL_DESCARGA_REPORTES;
        /*} else {
            return Constants.URL_REP_REG_UNIONCIVIL;
        }*/
    }

    @RequestMapping(value = "/descargaReporte", method = RequestMethod.POST)
    public void getReporteRegConyugeConcubinario(@RequestParam("idExcelTxt") String idExcelTxt, HttpSession session, HttpServletRequest request,
            HttpServletResponse response) throws Exception {

        //Tratamos de obtener el objeto usuario de la sesion
        Usuario usuario = (Usuario) session.getAttribute(Usuario.SES_NAME);
        Long tipoTramite = (Long) session.getAttribute("tipoTramiteReporte");
        ReporteEnum reporte = tipoTramite == Constantes.REPORTE_CONYUGE_CONCUBINARIO ? ReporteEnum.CONYUGES : ReporteEnum.UNION_CIVIL;
        //Si no esta el usuario en sesion buscamos en el request la informacion del usuarioSso y lo ponemos en sesion
        if (usuario == null) {
            UsuarioSSO usr = this.procesarUsuarioSSO(request);
            usuario = sessionController.setUsuarioFromSSO(usr);
        }
        sessionController.setVariablesSesionUsuario(session, usuario);

        log.info("usuario " + usuario);
        log.info("usuario CURP " + usuario.getCveIdUsuario());
        log.info("folio " + idExcelTxt);

        String curpUsuario = null;

        if (usuario != null && usuario.getCveIdUsuario() != null) {
            log.debug("CURP usuario " + usuario.getCveIdUsuario());
            curpUsuario = usuario.getCveIdUsuario();
        }

        SpRespuestaDescargaReporte respuestaReporte = reportes.descargaReporte(idExcelTxt, reporte);

        log.debug("nombreArchivo " + respuestaReporte.getNomArchivo() + " tamaño: " + respuestaReporte.getByteArrayReporte().length);
        response.setContentType("application/zip");
        response.setHeader("Content-Disposition", "attachment; filename=" + respuestaReporte.getNomArchivo());
        response.setHeader("Content-Length", String.valueOf(respuestaReporte.getByteArrayReporte().length));

        ServletOutputStream out = response.getOutputStream();

        try {
            byte[] content = respuestaReporte.getByteArrayReporte();
            out.write(content, 0, content.length);
            out.flush();

            if (response.isCommitted()) {
                if (curpUsuario != null) {
                    log.info("Se llama al SP para eliminar el reporte ya descargado");
                    SpRespuestaCommon resp = reportes.eliminaReporteDescargado(idExcelTxt, curpUsuario, reporte);
                    log.debug("codProceso eliminaReporteDescargado " + resp.getCodProceso());
                    log.debug("desProceso eliminaReporteDescargado " + resp.getDesProceso());
                }
            }

        } catch (final Exception e) {
            if (!response.isCommitted()) {
                response.setStatus(HttpURLConnection.HTTP_BAD_REQUEST);
            }
        } finally {
            if (out != null) {
                out.close();
            }
        }
    }

    @RequestMapping(value = "/muestraRegistroUnionCivil", method = RequestMethod.POST)
    public @ResponseBody
    DatosSalidaPaginador<ReporteRegistro> muestraRegistroUnionCivil(@RequestBody SolicitudesPendAutDataTable aoData, HttpSession session,
            HttpServletRequest request) {

        AsignacionNSS asignacionNSS = (AsignacionNSS) session.getAttribute("AsignacionNSS");
        Usuario usuario = (Usuario) session.getAttribute("usuarioreporte");
        String fechaInicio = (String) session.getAttribute("fechaInicio");
        String fechaFin = (String) session.getAttribute("fechaFin");
        DatosSalidaPaginador<ReporteRegistro> resultado = new DatosSalidaPaginador<ReporteRegistro>();
        List<ReporteRegistro> regs = null;
        @SuppressWarnings("rawtypes")
        DatosEntradaPaginador envio = new DatosEntradaPaginador();
        envio.parserArray(aoData.getAoData());

        try {
            resultado = reportes.getReporteRegistroUnionCivil(fechaInicio, fechaFin, usuario.getUsuarioFuncionario().getDelegacion().getId(),
                    usuario.getCveIdSubdelegacion());
            resultado.setsEcho(envio.getsEcho());
        } catch (Exception e) {
            e.printStackTrace();
        }
        resultado.setsEcho(envio.getsEcho());
        session.setAttribute("DatosRepRegCon", resultado);
        return resultado;
    }

    @RequestMapping(value = "/Reporte_Registro_PersonaUnionCivil", method = RequestMethod.POST)
    public void getReporteRegUnionCivil(@RequestParam("idExcelTxt") Integer idExcelTxt, HttpSession session, HttpServletRequest request,
            HttpServletResponse response) {

        log.debug("el valor de idExcelTxt es: " + idExcelTxt);
        try {
            DatosSalidaPaginador<ReporteRegistro> resultado = (DatosSalidaPaginador<ReporteRegistro>) session.getAttribute(("DatosRepRegCon"));
            String filename = "";

            if (idExcelTxt == 3) {
                // Generar archivo Excel
                filename = "Reporte_Registro_PersonaUnionCivil.xlsx";
                response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
                Workbook workbook = new XSSFWorkbook();
                Sheet sheet = workbook.createSheet("Hoja de datos");
                Row row = null;
                Cell cell = null;
                int numeroRenglon = 0;

                row = sheet.createRow(numeroRenglon++);
                cell = row.createCell(0);
                cell.setCellValue("NSS");
                cell = row.createCell(1);
                cell.setCellValue("NOMBRE ASEGURADO");
                cell = row.createCell(2);
                cell.setCellValue("APELLIDO PATERNO ASEGURADO");
                cell = row.createCell(3);
                cell.setCellValue("APELLIDO MATERNO ASEGURADO");
                cell = row.createCell(4);
                cell.setCellValue("CURP ASEGURADO");
                cell = row.createCell(5);
                cell.setCellValue("SEXO ASEGURADO");
                cell = row.createCell(6);
                cell.setCellValue("DOMICILIO ASEGURADO MOMENTO TRAMITE");
                cell = row.createCell(7);
                cell.setCellValue("DOMICILIO ASEGURADO ACTUAL");
                cell = row.createCell(8);
                cell.setCellValue("NOMBRE BENEFICIARIO");
                cell = row.createCell(9);
                cell.setCellValue("APELLIDO PATERNO BENEFICIARIO");
                cell = row.createCell(10);
                cell.setCellValue("APELLIDO MATERNO BENEFICIARIO");
                cell = row.createCell(11);
                cell.setCellValue("CURP BENEFICIARIO");
                cell = row.createCell(12);
                cell.setCellValue("SEXO BENEFICIARIO");
                cell = row.createCell(13);
                cell.setCellValue("DOMICILIO BENEFICIARIO MOMENTO TRAMITE");
                cell = row.createCell(14);
                cell.setCellValue("DOMICILIO BENEFICIARIO ACTUAL");
                cell = row.createCell(15);
                cell.setCellValue("FECHA TRAMITE");
                cell = row.createCell(16);
                cell.setCellValue("ID TIPO TRAMITE");
                cell = row.createCell(17);
                cell.setCellValue("DELEGACION MOMENTO TRAMITE");
                cell = row.createCell(18);
                cell.setCellValue("DELEGACION ACTUAL");
                cell = row.createCell(19);
                cell.setCellValue("SUBDELEGACION MOMENTO TRAMITE");
                cell = row.createCell(20);
                cell.setCellValue("SUBDELEGACION ACTUAL");
                cell = row.createCell(21);
                cell.setCellValue("UMF MOMENTO TRAMITE");
                cell = row.createCell(22);
                cell.setCellValue("UMF ACTUAL");
                cell = row.createCell(23);
                cell.setCellValue("TIPO TRAMITE");
                cell = row.createCell(24);
                cell.setCellValue("VIGENCIA ASEGURADO MOMENTO TRAMITE");
                cell = row.createCell(25);
                cell.setCellValue("VIGENCIA BENEFICIARIO MOMENTO TRAMITE");
                cell = row.createCell(26);
                cell.setCellValue("IND UNION CIVIL MISMO SEXO");
                cell = row.createCell(27);
                cell.setCellValue("CUENTA USUARIO");
                cell = row.createCell(28);
                cell.setCellValue("ORIGEN TRAMITE");
                cell = row.createCell(29);
                cell.setCellValue("DOCUMENTOS PROBATORIOS");

                for (ReporteRegistro reg : resultado.getAaData()) {
                    row = sheet.createRow(numeroRenglon++);
                    cell = row.createCell(0);
                    cell.setCellValue(reg.getNUM_NSS());
                    cell = row.createCell(1);
                    cell.setCellValue(reg.getNOMBRE_ASEGURADO());
                    cell = row.createCell(2);
                    cell.setCellValue(reg.getAPELLIDO_PATERNO_ASEGURADO());
                    cell = row.createCell(3);
                    cell.setCellValue(reg.getAPELLIDO_MATERNO_ASEGURADO());
                    cell = row.createCell(4);
                    cell.setCellValue(reg.getCURP_ASEGURADO());
                    cell = row.createCell(5);
                    cell.setCellValue(reg.getSEXO_ASEGURADO());
                    cell = row.createCell(6);
                    cell.setCellValue(reg.getDOMICILIO_ASEGURADO_MOMENTO());
                    cell = row.createCell(7);
                    cell.setCellValue(reg.getDOMICILIO_ASEGURADO_ACTUAL());
                    cell = row.createCell(8);
                    cell.setCellValue(reg.getNOMBRE_BENEFICIARIO());
                    cell = row.createCell(9);
                    cell.setCellValue(reg.getAPELLIDO_PATERNO_BENEFICIARIO());
                    cell = row.createCell(10);
                    cell.setCellValue(reg.getAPELLIDO_MATERNO_BENEFICIARIO());
                    cell = row.createCell(11);
                    cell.setCellValue(reg.getCURP_BENEFICIARIO());
                    cell = row.createCell(12);
                    cell.setCellValue(reg.getSEXO_BENEFICIARIO());
                    cell = row.createCell(13);
                    cell.setCellValue(reg.getDOMICILIO_BENEFICIARIO_MOMENTO());
                    cell = row.createCell(14);
                    cell.setCellValue(reg.getDOMICILIO_BENEFICIARIO_ACTUAL());
                    cell = row.createCell(15);
                    cell.setCellValue(reg.getFECHA_TRAMITE());
                    cell = row.createCell(16);
                    cell.setCellValue(reg.getID_TIPO_TRAMITE());
                    cell = row.createCell(17);
                    cell.setCellValue(reg.getDES_DEL_MOMENTO());
                    cell = row.createCell(18);
                    cell.setCellValue(reg.getDES_DEL_ACTUAL());
                    cell = row.createCell(19);
                    cell.setCellValue(reg.getDES_SUB_MOMENTO());
                    cell = row.createCell(20);
                    cell.setCellValue(reg.getDES_SUB_ACTUAL());
                    cell = row.createCell(21);
                    cell.setCellValue(reg.getDES_UMF_MOMENTO());
                    cell = row.createCell(22);
                    cell.setCellValue(reg.getDES_UMF_ACTUAL());
                    cell = row.createCell(23);
                    cell.setCellValue(reg.getTIPO_TRAMITE());
                    cell = row.createCell(24);
                    cell.setCellValue(reg.getVIGENCIA_ASEG_MOMENTO());
                    cell = row.createCell(25);
                    cell.setCellValue(reg.getVIGENCIA_BENEF_MOMENTO());
                    cell = row.createCell(26);
                    cell.setCellValue(reg.getIND_UNION_CIVIL_MISMO_SEXO());
                    cell = row.createCell(27);
                    cell.setCellValue(reg.getCUENTA_USUARIO());
                    cell = row.createCell(28);
                    cell.setCellValue(reg.getORIGEN_TRAMITE());
                    cell = row.createCell(29);
                    cell.setCellValue(reg.getDOCUMENTOS_PROBATORIOS());
                }
                workbook.write(response.getOutputStream());
            } else {
                // Generar archivo de texto
                response.setContentType("text/plain");
                filename = "Reporte_Registro_PersonaUnionCivil.txt";
                response.setHeader("Content-Disposition", "attachment;filename=\"" + filename + "\"");

                PrintWriter writer = response.getWriter();

                writer.write("NSS | NOMBRE ASEGURADO | APELLIDO PATERNO ASEGURADO | APELLIDO MATERNO ASEGURADO | CURP ASEGURADO | "
                        + "SEXO ASEGURADO | DOMICILIO ASEGURADO MOMENTO TRAMITE | DOMICILIO ASEGURADO ACTUAL | NOMBRE BENEFICIARIO | "
                        + "APELLIDO PATERNO BENEFICIARIO | APELLIDO MATERNO BENEFICIARIO | CURP BENEFICIARIO | SEXO BENEFICIARIO | "
                        + "DOMICILIO BENEFICIARIO MOMENTO TRAMITE | DOMICILIO BENEFICIARIO ACTUAL | FECHA TRAMITE | ID TIPO TRAMITE | "
                        + "CLAVE DELEGACION MOMENTO TRAMITE | CLAVE DELEGACION ACTUAL | DELEGACION MOMENTO TRAMITE | DELEGACION ACTUAL | "
                        + "CLAVE SUBDELEGACION MOMENTO TRAMITE | CLAVE SUBDELEGACION ACTUAL | SUBDELEGACION MOMENTO TRAMITE | "
                        + "SUBDELEGACION ACTUAL | CLAVE UMF MOMENTO TRAMITE | UMF MOMENTO TRAMITE | CLAVE UMF ACTUAL | UMF ACTUAL | "
                        + "TIPO TRAMITE | VIGENCIA ASEGURADO MOMENTO TRAMITE | VIGENCIA BENEFICIARIO MOMENTO TRAMITE | "
                        + "IND UNION CIVIL MISMO SEXO | CUENTA USUARIO | ORIGEN TRAMITE | DOCUMENTOS PROBATORIOS");
                writer.write("\r\n");

                for (ReporteRegistro reg : resultado.getAaData()) {
                    String registro = reg.getNUM_NSS() + "|" + reg.getNOMBRE_ASEGURADO() + "|" + reg.getAPELLIDO_PATERNO_ASEGURADO() + "|" + reg
                            .getAPELLIDO_MATERNO_ASEGURADO() + "|" + reg.getCURP_ASEGURADO() + "|" + reg.getSEXO_ASEGURADO() + "|" + reg
                            .getDOMICILIO_ASEGURADO_MOMENTO() + "|" + reg.getDOMICILIO_ASEGURADO_ACTUAL() + "|" + reg.getNOMBRE_BENEFICIARIO() + "|"
                            + reg.getAPELLIDO_PATERNO_BENEFICIARIO() + "|" + reg.getAPELLIDO_MATERNO_BENEFICIARIO() + "|" + reg.getCURP_BENEFICIARIO()
                            + "|" + reg.getSEXO_BENEFICIARIO() + "|" + reg.getDOMICILIO_BENEFICIARIO_MOMENTO() + "|" + reg
                            .getDOMICILIO_BENEFICIARIO_ACTUAL() + "|" + reg.getFECHA_TRAMITE() + "|" + reg.getID_TIPO_TRAMITE() + "|" + reg
                            .getCLAVE_DEL_MOMENTO() + "|" + reg.getCLAVE_DEL_ACTUAL() + "|" + reg.getDES_DEL_MOMENTO() + "|" + reg.getDES_DEL_ACTUAL()
                            + "|" + reg.getCLAVE_SUB_MOMENTO() + "|" + reg.getCLAVE_SUB_ACTUAL() + "|" + reg.getDES_SUB_MOMENTO() + "|" + reg
                            .getDES_SUB_ACTUAL() + "|" + reg.getCVE_UMF_MOMENTO() + "|" + reg.getDES_UMF_MOMENTO() + "|" + reg.getCVE_UMF_ACTUAL()
                            + "|" + reg.getDES_UMF_ACTUAL() + "|" + reg.getTIPO_TRAMITE() + "|" + reg.getVIGENCIA_ASEG_MOMENTO() + "|" + reg
                            .getVIGENCIA_BENEF_MOMENTO() + "|" + reg.getIND_UNION_CIVIL_MISMO_SEXO() + "|" + reg.getCUENTA_USUARIO() + "|" + reg
                            .getORIGEN_TRAMITE() + "|" + reg.getDOCUMENTOS_PROBATORIOS().replace('|', '-') + "\r\n";
                    writer.write(registro);
                }

                writer.flush();
                writer.close();
            }

        } catch (Exception e) {
            // log.error("Error en el metodo init previo : " + e);
            e.printStackTrace();
        }

    }

}
