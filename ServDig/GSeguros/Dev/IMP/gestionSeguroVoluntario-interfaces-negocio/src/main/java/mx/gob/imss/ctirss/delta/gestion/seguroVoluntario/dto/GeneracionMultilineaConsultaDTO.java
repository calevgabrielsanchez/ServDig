package mx.gob.imss.ctirss.delta.gestion.seguroVoluntario.dto;

import java.io.Serializable;


public class GeneracionMultilineaConsultaDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long idSolicitud;
    private Long idCalculo;
    private String correlacion;
    private String estado;
    private Boolean finalizada;
    private Integer numeroPeriodos;
    private Integer periodosProcesados;
    private Integer intento;
    private String siguienteIntento;
    private String codigoRespuesta;
    private String mensaje;
    private String resultado;
    private String pdfBase64;
    private String multilinea;
    private String fechaRespuesta;

    public GeneracionMultilineaConsultaDTO() {
    }

    public Long getIdSolicitud() {
        return idSolicitud;
    }

    public void setIdSolicitud(Long idSolicitud) {
        this.idSolicitud = idSolicitud;
    }

    public Long getIdCalculo() {
        return idCalculo;
    }

    public void setIdCalculo(Long idCalculo) {
        this.idCalculo = idCalculo;
    }

    public String getCorrelacion() {
        return correlacion;
    }

    public void setCorrelacion(String correlacion) {
        this.correlacion = correlacion;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Boolean getFinalizada() {
        return finalizada;
    }

    public void setFinalizada(Boolean finalizada) {
        this.finalizada = finalizada;
    }

    public Integer getNumeroPeriodos() {
        return numeroPeriodos;
    }

    public void setNumeroPeriodos(Integer numeroPeriodos) {
        this.numeroPeriodos = numeroPeriodos;
    }

    public Integer getPeriodosProcesados() {
        return periodosProcesados;
    }

    public void setPeriodosProcesados(Integer periodosProcesados) {
        this.periodosProcesados = periodosProcesados;
    }

    public Integer getIntento() {
        return intento;
    }

    public void setIntento(Integer intento) {
        this.intento = intento;
    }

    public String getSiguienteIntento() {
        return siguienteIntento;
    }

    public void setSiguienteIntento(String siguienteIntento) {
        this.siguienteIntento = siguienteIntento;
    }

    public String getCodigoRespuesta() {
        return codigoRespuesta;
    }

    public void setCodigoRespuesta(String codigoRespuesta) {
        this.codigoRespuesta = codigoRespuesta;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }
    
    public String getResultado() {
        return resultado;
    }

    public void setResultado(String resultado) {
        this.resultado = resultado;
    }
    public String getPdfBase64() {
        return pdfBase64;
    }

    public void setPdfBase64(String pdfBase64) {
        this.pdfBase64 = pdfBase64;
    }

    public String getMultilinea() {
        return multilinea;
    }

    public void setMultilinea(String multilinea) {
        this.multilinea = multilinea;
    }

    public String getFechaRespuesta() {
        return fechaRespuesta;
    }

    public void setFechaRespuesta(String fechaRespuesta) {
        this.fechaRespuesta = fechaRespuesta;
    }
}