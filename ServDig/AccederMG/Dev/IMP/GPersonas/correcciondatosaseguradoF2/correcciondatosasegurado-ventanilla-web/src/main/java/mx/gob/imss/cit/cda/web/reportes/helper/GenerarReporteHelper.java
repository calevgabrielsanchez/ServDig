package mx.gob.imss.cit.cda.web.reportes.helper;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import mx.gob.imss.cit.cda.service.interfaces.ManejadorReportesRemote;
import mx.gob.imss.cit.cda.web.reportes.constans.ReporteConstantes;
import mx.gob.imss.cit.cda.web.reportes.controller.GenerarReportesController;
import mx.gob.imss.cit.cda.web.reportes.enums.FormatoReporteEnum;
import mx.gob.imss.cit.cda.web.reportes.vo.EstadisticasOrigenReporteCDAVO;
import mx.gob.imss.cit.cda.web.reportes.vo.ReporteDTO;
import mx.gob.imss.cit.cda.web.reportes.vo.VariablesReportes;
import mx.gob.imss.ctirss.delta.model.asegurado.cda.TramitesReportes;

/**
 * Clase que contiene diversos metodos para la generacion de reportes en formato
 * PDF y Excel
 */
@Component
public class GenerarReporteHelper extends BaseReporteHelper {

	private static final Logger log = LoggerFactory.getLogger(GenerarReporteHelper.class);
    private static final String EXCEL = "xls";
    private static final String PDF = "pdf";
    private static final String NOMBRE_REPORTE = "reporte";

    private static final long serialVersionUID = -6948731311776085036L;

    @Autowired
    @Qualifier("manejadorReportesBusiness")
    private ManejadorReportesRemote manejadorReportesBusiness;
    
    @Autowired
    protected HttpSession httpSession;

    /**
     * Metodo para crear el nombre de los reportes en formato PDF y Excel.
     * 
     * @param extension
     * @return
     */
    public String creaNombreReporte(String extension) {
        StringBuilder nombre = new StringBuilder();
        String fecha = formatoFecha(new Date());

        nombre.append(NOMBRE_REPORTE).append(fecha).append(".")
                .append(extension);

        return nombre.toString();
    }

    public String creaNombreReporteExcel() {
        return creaNombreReporte(EXCEL);
    }

    public String creaNombreReportePDF() {
        return creaNombreReporte(PDF);
    }

    private String formatoFecha(Date fecha) {
        if (fecha != null) {
            SimpleDateFormat formatter = new SimpleDateFormat("ddMMyyyy");
            return formatter.format(fecha);
        } else {
            return "";
        }
    }

    /**
     * Metodo para la generacion del reporte de CDA
     * 
     * @return ReporteDTO
     */
    @SuppressWarnings("unchecked")
    public ReporteDTO obtenerReporteVariablesPDF(String tipoVariable) {
        getLogger().debug("******Generando reporte [{}]",
                "obtenerReporteRemuneracionesPDF");

        ReporteDTO reporteDTO = new ReporteDTO();
        
        String delegacion = (String) httpSession.getAttribute(ReporteConstantes.DELEGACION_REPORTES);
        String subdelegacion = (String) httpSession.getAttribute(ReporteConstantes.SUBDELEGACION_REPORTES);
        
        delegacion = delegacion==null?" ":delegacion;
        subdelegacion = subdelegacion==null?" ":subdelegacion;        

        Map<String, Integer> variables = new HashMap<String, Integer>();
        Map<Integer, Integer> origenes = new HashMap<Integer, Integer>();

        List<VariablesReportes> listaReportes = (List<VariablesReportes>) httpSession.getAttribute(ReporteConstantes.LIST_VARIABLES_REPORTE);
        List<EstadisticasOrigenReporteCDAVO> listaOrigenes = (List<EstadisticasOrigenReporteCDAVO>) httpSession.getAttribute(ReporteConstantes.LIST_ORIGENES_REPORTES);
        
        for(VariablesReportes obj : listaReportes){
            variables.put(obj.getDescripcion(), Integer.parseInt(obj.getCantidad()));
        }
        
        for(EstadisticasOrigenReporteCDAVO obj: listaOrigenes){
            origenes.put(Integer.parseInt(obj.getNumeroInternet()), Integer.parseInt(obj.getNumeroVentanilla()));
        }
        
        String nombrePdf = creaNombreReportePDF();
        
        log.info("---CDA---- " +   nombrePdf +" " + variables +" "+ origenes+" "+ delegacion+" "+ subdelegacion+" "+ tipoVariable);

        byte[] archivoPdf = manejadorReportesBusiness.generarReportePDF(
                nombrePdf, variables, origenes, delegacion, subdelegacion, tipoVariable);
        
        reporteDTO.setContenido(archivoPdf);
        reporteDTO.setNombre(nombrePdf);
        reporteDTO.setContentType(FormatoReporteEnum.PDF.getContentType());
        return reporteDTO;
    }

    public ReporteDTO obtenerReporteVariablesXLS(List<TramitesReportes> datos) {
        ReporteDTO reporteDTO = new ReporteDTO();

        byte[] archivoExcel = manejadorReportesBusiness.generaReporteXLS(datos);
        reporteDTO.setContenido(archivoExcel);
        reporteDTO.setNombre(creaNombreReportePDF());
        reporteDTO.setContentType(FormatoReporteEnum.PDF.getContentType());
        return reporteDTO;
    }
}