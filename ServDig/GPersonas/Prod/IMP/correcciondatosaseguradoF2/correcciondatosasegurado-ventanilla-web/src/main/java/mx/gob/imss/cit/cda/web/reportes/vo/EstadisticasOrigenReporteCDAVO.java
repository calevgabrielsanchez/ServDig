package mx.gob.imss.cit.cda.web.reportes.vo;

import mx.gob.imss.cit.cda.web.support.model.BaseModel;

public class EstadisticasOrigenReporteCDAVO extends BaseModel{

    private static final long serialVersionUID = 9056453546373333165L;
    
    private String numeroInternet;
    private String numeroVentanilla;
    private String total;
    private String descripcion;
    
    public String getNumeroInternet() {
        return numeroInternet;
    }
    public void setNumeroInternet(String numeroInternet) {
        this.numeroInternet = numeroInternet;
    }
    public String getNumeroVentanilla() {
        return numeroVentanilla;
    }
    public void setNumeroVentanilla(String numeroVentanilla) {
        this.numeroVentanilla = numeroVentanilla;
    }
    public String getTotal() {
        return total;
    }
    public void setTotal(String total) {
        this.total = total;
    }
    public String getDescripcion() {
        return descripcion;
    }
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}
