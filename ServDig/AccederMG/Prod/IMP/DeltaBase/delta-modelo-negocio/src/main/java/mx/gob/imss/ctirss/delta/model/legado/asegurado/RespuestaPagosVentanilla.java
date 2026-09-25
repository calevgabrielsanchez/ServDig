package mx.gob.imss.ctirss.delta.model.legado.asegurado;

public class RespuestaPagosVentanilla {

    private static final long serialVersionUID = 1L;

    private String nss;
    private String periodo;
    private String modalidad;
    private String fechaInicioAseguramiento;
    private String fechaFinAseguramiento;
    private String fechaPago;
    private String indPago;
    private String indPagosCompletos;

    private String codigoError;
    private String mensajeError;

    public String getNss() {
        return nss;
    }

    public void setNss(String nss) {
        this.nss = nss;
    }

    public String getPeriodo() {
        return periodo;
    }

    public void setPeriodo(String periodo) {
        this.periodo = periodo;
    }

    public String getModalidad() {
        return modalidad;
    }

    public void setModalidad(String modalidad) {
        this.modalidad = modalidad;
    }

    public String getFechaInicioAseguramiento() {
        return fechaInicioAseguramiento;
    }

    public void setFechaInicioAseguramiento(String fechaInicioAseguramiento) {
        this.fechaInicioAseguramiento = fechaInicioAseguramiento;
    }

    public String getFechaFinAseguramiento() {
        return fechaFinAseguramiento;
    }

    public void setFechaFinAseguramiento(String fechaFinAseguramiento) {
        this.fechaFinAseguramiento = fechaFinAseguramiento;
    }

    public String getFechaPago() {
        return fechaPago;
    }

    public void setFechaPago(String fechaPago) {
        this.fechaPago = fechaPago;
    }

    public String getIndPago() {
        return indPago;
    }

    public void setIndPago(String indPago) {
        this.indPago = indPago;
    }

    public String getCodigoError() {
        return codigoError;
    }

    public void setCodigoError(String codigoError) {
        this.codigoError = codigoError;
    }

    public String getMensajeError() {
        return mensajeError;
    }

    public void setMensajeError(String mensajeError) {
        this.mensajeError = mensajeError;
    }

    public String getIndPagosCompletos() {
        return indPagosCompletos;
    }

    public void setIndPagosCompletos(String indPagosCompletos) {
        this.indPagosCompletos = indPagosCompletos;
    }

    @Override
    public String toString() {
        return "RespuestaPagosVentanilla{" +
                "nss='" + nss + '\'' +
                ", periodo='" + periodo + '\'' +
                ", modalidad='" + modalidad + '\'' +
                ", fechaInicioAseguramiento='" + fechaInicioAseguramiento + '\'' +
                ", fechaPago='" + fechaPago + '\'' +
                ", fechaFinAseguramiento='" + fechaFinAseguramiento + '\'' +
                ", ind='" + indPago + '\'' +
                ", indPagosCompletos='" + indPagosCompletos + '\'' +
                ", codigoError='" + codigoError + '\'' +
                ", mensajeError='" + mensajeError + '\'' +
                '}';
    }
}
