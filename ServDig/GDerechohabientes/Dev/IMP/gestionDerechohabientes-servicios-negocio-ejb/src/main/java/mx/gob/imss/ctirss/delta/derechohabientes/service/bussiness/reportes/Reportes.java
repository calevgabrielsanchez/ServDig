package mx.gob.imss.ctirss.delta.derechohabientes.service.bussiness.reportes;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import mx.gob.imss.ctirss.delta.derechohabientes.service.dao.RegistroDerechohabientesDaoLocal;
import mx.gob.imss.ctirss.delta.derechohabientes.service.dao.asegurado.DitAseguradoDaoLocal;
import mx.gob.imss.ctirss.delta.derechohabientes.service.utility.ManejadorReportesLocal;
import mx.gob.imss.ctirss.delta.derechohabientes.service.utility.interfaces.reportes.ReportesRemote;
import mx.gob.imss.ctirss.delta.framework.paginador.model.DatosSalidaPaginador;
import mx.gob.imss.ctirss.delta.gestion.solicitud.service.interfaces.EnvioCorreoElectronicoBusinessRemote;
import mx.gob.imss.ctirss.delta.model.derechohabientes.ReporteSav011;
import mx.gob.imss.ctirss.delta.model.derechohabientes.reportes.InfoDepuracion;
import mx.gob.imss.ctirss.delta.model.derechohabientes.reportes.SpRespuestaCommon;
import mx.gob.imss.ctirss.delta.model.derechohabientes.reportes.SpRespuestaDepuracionReportes;
import mx.gob.imss.ctirss.delta.model.derechohabientes.reportes.SpRespuestaDescargaReporte;
import mx.gob.imss.ctirss.delta.model.derechohabientes.reportes.SpRespuestaReporte;
import mx.gob.imss.ctirss.delta.model.dto.CorreoElectronicoDTO;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.ReporteRegistro;
import mx.gob.imss.ctirss.delta.model.util.ReporteEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Stateless(name = "reportes", mappedName = "reportes")
public class Reportes implements ReportesRemote {

    private static final Logger log = LoggerFactory.getLogger(ReportesRemote.class);

    @EJB
    private DitAseguradoDaoLocal aseguradoDao;

    @EJB
    private ManejadorReportesLocal manejadorReportes;

    @EJB
    private RegistroDerechohabientesDaoLocal registroDerechohabienteDaoLocal;

    @EJB(name = "envioCorreoElectronicoBusiness", mappedName = "envioCorreoElectronicoBusiness")
    private EnvioCorreoElectronicoBusinessRemote envioCorreoElectronicoBusinessRemote;

    @Override
    public Object getReporteSav011(Integer idAsegurado) {

        ReporteSav011 dato = aseguradoDao.getAsegurado(1);
        List<ReporteSav011> lista = new ArrayList<ReporteSav011>();
        lista.add(dato);

        Map<String, Object> parametros = new HashMap<String, Object>();
        String reporte = "SAV011.jrxml";

        ByteArrayOutputStream repo = manejadorReportes.ejecutaReporte(parametros, lista, reporte);

        return repo.toByteArray();
    }

    @Override
    public SpRespuestaDescargaReporte descargaReporte(String folio, ReporteEnum reporte) throws Exception {

        return manejadorReportes.descargaReporte(folio, reporte);
    }

    @Override
    public String generaReporte(String curp, Long cveIdDelegacionUser, Integer userNacional, Long cveIdDelegacion, Long cveIdSubdelegacion,
            Date fechaInicial, Date fechaFinal, ReporteEnum reporte) {

        SpRespuestaReporte spRespuesta = manejadorReportes
                .generaReporte(curp, cveIdDelegacionUser, userNacional, cveIdDelegacion, cveIdSubdelegacion, fechaInicial, fechaFinal, reporte);

        return spRespuesta.getFolio();

    }

    @Override
    public String obtieneEstatusReporte(String folio, ReporteEnum reporte) {

        SpRespuestaReporte spRespuesta = manejadorReportes.obtieneEstatusReporte(folio, reporte);
        return spRespuesta.getEstatus();
    }

    @Override
    public SpRespuestaCommon eliminaReporteDescargado(String folio, String cveUsuario, ReporteEnum reporte) {

        return manejadorReportes.eliminaReporte(folio, cveUsuario, reporte);
    }

    @Override
    public DatosSalidaPaginador<ReporteRegistro> getReporteRegistroUnionCivil(String fechaInicio, String fechaFin, Long cveDelegacion,
            Long cveSubdelegacion) throws Exception {

        DatosSalidaPaginador<ReporteRegistro> salidaPaginador = new DatosSalidaPaginador<ReporteRegistro>();
        List<ReporteRegistro> registros;

        registros = registroDerechohabienteDaoLocal.findRegistroUnionCivil(fechaInicio, fechaFin, cveDelegacion, cveSubdelegacion);
        salidaPaginador.setAaData(registros);
        salidaPaginador.setiTotalRecords(1);
        salidaPaginador.setiTotalDisplayRecords(1);
        return salidaPaginador;

    }

    @Override
    public void estatusDepuracionReportes(Date parFecha) {

        String CUERPO = "<html><body style='margin: 0; padding: 0; background: #F3F3F3;'> "
                + "<table cellpadding='0' cellspacing='0' border='0' align='center' width='560px' style='font-family: Helvetica, Arial; background: #ffffff;' bgcolor='#ffffff'>"
                + "<tr><td width='560px;' valign='top' align='left' bgcolor='#ffffff' style='font-family: Helvetica, Arial; font-size: 16px; color: #5A5A5A; background: #fff; padding: 38px 27px 76px;'><table cellpadding='0' cellspacing='0' border='0' style='color: #717171; font: normal 16px Helvetica, Arial; margin: 0px; padding: 0;' width='100%' class='content'>"
                + "<tr><td style='padding: 15px 0px;'><h4 style='color: #5A5A5A; margin: 0px; padding: 0px; line-height: 30px; font-size: 24px; font-family: Helvetica, Arial;'>Buen d&iacute;a,</h4><br /></td></tr></table>"
                + "<p style='color: #5A5A5A; font-weight: normal; margin: 0px; padding: 0px; line-height: 23px; font-size: 16px; font-family: Helvetica, Arial;'>_CONTENIDO_<br><br>"
                + "<ul>" + "<li><b>Fecha y hora de ejecución del proceso:</b> _FECHA_</li>" + "_DATA_" + "</ul>"
                + "<br><br>Saludos</p></td></tr></table></td></tr></table>" + "</body></html>";

        log.info("Iniciando consulta del SP del proceso de depuracion AU");
        SpRespuestaDepuracionReportes result = manejadorReportes.obtieneEstatusDepuracionReportes(parFecha);

        if (result.getIndEnvioCorreo() == 0) {
            if (result.getInfo().isEmpty()) {
                String contenido = "Hacemos de su conocimiento que el proceso ha finalizado de manera correcta, pero no se encontraron registros candidatos para el proceso de depuraci&oacute;n";
                CUERPO = CUERPO.replaceAll("_CONTENIDO_", contenido)
                        .replaceAll("_FECHA_", new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(parFecha)).replaceAll("_DATA_", "");
            } else {
                String contenido = "Hacemos de su conocimiento que el proceso ha finalizado de manera correcta, generando las siguientes cifras:";
                StringBuilder sb = new StringBuilder();
                sb.append("<ul>");
                int total = 0;
                for (InfoDepuracion r : result.getInfo()) {
                    if (r.getNomDelegacion().contains("NACIONAL")) {
                        sb.append("<li><b>Total de registros depurados ").append(r.getNomDelegacion()).append(":</b> ").append(r.getDepurados())
                                .append("</li>");
                    } else {
                        sb.append("<li><b>Total de registros depurados OOAD ").append(r.getDelegacion()).append(" ").append(r.getNomDelegacion())
                                .append(":</b> ").append(r.getDepurados()).append("</li>");
                    }
                    total = total + r.getDepurados();
                }
                sb.append("</ul>").append("<li><b>Total de registros depurados:</b> ").append(total).append("</li>");

                CUERPO = CUERPO.replaceAll("_CONTENIDO_", contenido)
                        .replaceAll("_FECHA_", new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(parFecha)).replaceAll("_DATA_", sb.toString())
                        .replaceAll("_TOTAL_DEPURADOS_", String.valueOf(total));
            }

            try {
                log.info("Enviando correo del proceso de depuracion AU");
                CorreoElectronicoDTO correo = new CorreoElectronicoDTO();
                correo.setAsunto("Cifras del proceso de depuraci\u00F3n de reportes");
                correo.setCorreoPara(new String[] { "fernando.castellanos@imss.gob.mx" });
                correo.setCorreoCopia(new String[] { "miguel.a.sanchez@syesoftware.com" });
                correo.setCuerpoCorreo(CUERPO);
                envioCorreoElectronicoBusinessRemote.enviarCorreo(correo);
            } catch (Exception e) {
                log.error("Ocurrio un error al enviar el correo con el estatus de la depuracion de archivos");
                e.printStackTrace();
            }
        }
    }
}
