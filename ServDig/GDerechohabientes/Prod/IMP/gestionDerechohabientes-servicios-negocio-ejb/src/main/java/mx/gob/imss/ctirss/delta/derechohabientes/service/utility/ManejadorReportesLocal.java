package mx.gob.imss.ctirss.delta.derechohabientes.service.utility;

import java.io.ByteArrayOutputStream;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.ejb.Local;

import mx.gob.imss.ctirss.delta.model.derechohabientes.reportes.SpRespuestaCommon;
import mx.gob.imss.ctirss.delta.model.derechohabientes.reportes.SpRespuestaDepuracionReportes;
import mx.gob.imss.ctirss.delta.model.derechohabientes.reportes.SpRespuestaDescargaReporte;
import mx.gob.imss.ctirss.delta.model.derechohabientes.reportes.SpRespuestaReporte;
import mx.gob.imss.ctirss.delta.model.util.ReporteEnum;
import net.sf.jasperreports.engine.JasperPrint;

@Local
public interface ManejadorReportesLocal {

    ByteArrayOutputStream ejecutaHolaMundo(String cveSolicitud);

    ByteArrayOutputStream ejecutaReporte(Map parametros, List<? extends Serializable> lista, String nombre);

    ByteArrayOutputStream ejecutaReportePlantillas(Map parametros, List<? extends Serializable> lista, List<String> reporte);

    JasperPrint imprimeReporte(Map parametros, List<? extends Serializable> lista, String reporte);

    ByteArrayOutputStream ejecutaReporteCompilado(Map parametros, List<? extends Serializable> lista, String reporte);

    ByteArrayOutputStream ejecutaReporteSubreporte(Map parametros, List<? extends Serializable> lista, String reporte,
            Map<String, String> plantillas);

    ByteArrayOutputStream concatPDF(List<ByteArrayOutputStream> byteArrayOutputStream, boolean paginate);

    JasperPrint getReporteCompilado(Map parametros, List<? extends Serializable> lista, String reporte);

    ByteArrayOutputStream mergeReporteCompilado(List<JasperPrint> jasperPrints);

    // Reportes con stored procedure
    SpRespuestaReporte generaReporte(String curpUsuario, Long cveIdDelegacionUser, Integer userNacional, Long cveIdDelegacion,
            Long cveIdSubdelegacion, Date fechaInicial, Date fechaFinal, ReporteEnum reporte);

    SpRespuestaReporte obtieneEstatusReporte(String folio, ReporteEnum reporte);

    SpRespuestaDescargaReporte descargaReporte(String folio, ReporteEnum reporte) throws SQLException;

    SpRespuestaCommon eliminaReporte(String folio, String curpUsuario, ReporteEnum reporte);

    SpRespuestaDepuracionReportes obtieneEstatusDepuracionReportes(Date parFecha);

}
