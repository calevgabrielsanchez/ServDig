package mx.gob.imss.cit.cda.web.reportes.vo;

import java.util.List;

import mx.gob.imss.cit.cda.web.support.model.BaseModel;
import mx.gob.imss.cit.cda.web.support.model.Page;

public class DetalleReporteCDA extends BaseModel{

    private static final long serialVersionUID = 945576227487333736L;
    
    private List<Page<VariablesReportes>> variablesReportes;
    private List<Page<EstadisticasOrigenReporteCDAVO>> estadisticasReporteCDAVO;
    private String delegacionReporte;
    private String subdelegacionReporte;
    private String variableSeleccionadaReporte;
    private String estadisticaReporte;
    
    public List<Page<VariablesReportes>> getVariablesReportes() {
        return variablesReportes;
    }
    public void setVariablesReportes(
            List<Page<VariablesReportes>> variablesReportes) {
        this.variablesReportes = variablesReportes;
    }
    public List<Page<EstadisticasOrigenReporteCDAVO>> getEstadisticasReporteCDAVO() {
        return estadisticasReporteCDAVO;
    }
    public void setEstadisticasReporteCDAVO(
            List<Page<EstadisticasOrigenReporteCDAVO>> estadisticasReporteCDAVO) {
        this.estadisticasReporteCDAVO = estadisticasReporteCDAVO;
    }
    public String getDelegacionReporte() {
        return delegacionReporte;
    }
    public void setDelegacionReporte(String delegacionReporte) {
        this.delegacionReporte = delegacionReporte;
    }
    public String getSubdelegacionReporte() {
        return subdelegacionReporte;
    }
    public void setSubdelegacionReporte(String subdelegacionReporte) {
        this.subdelegacionReporte = subdelegacionReporte;
    }
    public String getVariableSeleccionadaReporte() {
        return variableSeleccionadaReporte;
    }
    public void setVariableSeleccionadaReporte(String variableSeleccionadaReporte) {
        this.variableSeleccionadaReporte = variableSeleccionadaReporte;
    }
    public String getEstadisticaReporte() {
        return estadisticaReporte;
    }
    public void setEstadisticaReporte(String estadisticaReporte) {
        this.estadisticaReporte = estadisticaReporte;
    }
    
    
}
