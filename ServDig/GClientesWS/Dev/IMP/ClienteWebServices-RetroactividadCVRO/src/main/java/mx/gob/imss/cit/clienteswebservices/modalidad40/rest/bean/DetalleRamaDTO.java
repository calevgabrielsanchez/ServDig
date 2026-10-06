package mx.gob.imss.cit.clienteswebservices.modalidad40.rest.bean;

import java.io.Serializable;
import java.math.BigDecimal;
import com.fasterxml.jackson.annotation.JsonProperty;
// Nota: Si utilizas Jackson 1.x, sustituye por:
// import org.codehaus.jackson.annotate.JsonProperty;

public class DetalleRamaDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @JsonProperty("idRama")
    private Long idRama;

    @JsonProperty("idTipoAportacion")
    private Long idTipoAportacion;

    @JsonProperty("salarioBase")
    private BigDecimal salarioBase;

    @JsonProperty("factor")
    private BigDecimal factor;

    @JsonProperty("diasCalculados")
    private Integer diasCalculados;

    @JsonProperty("aportacion")
    private BigDecimal aportacion;

    @JsonProperty("actualizacion")
    private BigDecimal actualizacion;

    @JsonProperty("recargo")
    private BigDecimal recargo;

    @JsonProperty("total")
    private BigDecimal total;

    @JsonProperty("origenFactor")
    private String origenFactor;

    @JsonProperty("idFactor")
    private Long idFactor;

    @JsonProperty("inicioVigenciaFactor")
    private String inicioVigenciaFactor;

    @JsonProperty("finVigenciaFactor")
    private String finVigenciaFactor;

    public DetalleRamaDTO() {
    }

    public Long getIdRama() {
        return idRama;
    }

    public void setIdRama(Long idRama) {
        this.idRama = idRama;
    }

    public Long getIdTipoAportacion() {
        return idTipoAportacion;
    }

    public void setIdTipoAportacion(Long idTipoAportacion) {
        this.idTipoAportacion = idTipoAportacion;
    }

    public BigDecimal getSalarioBase() {
        return salarioBase;
    }

    public void setSalarioBase(BigDecimal salarioBase) {
        this.salarioBase = salarioBase;
    }

    public BigDecimal getFactor() {
        return factor;
    }

    public void setFactor(BigDecimal factor) {
        this.factor = factor;
    }

    public Integer getDiasCalculados() {
        return diasCalculados;
    }

    public void setDiasCalculados(Integer diasCalculados) {
        this.diasCalculados = diasCalculados;
    }

    public BigDecimal getAportacion() {
        return aportacion;
    }

    public void setAportacion(BigDecimal aportacion) {
        this.aportacion = aportacion;
    }

    public BigDecimal getActualizacion() {
        return actualizacion;
    }

    public void setActualizacion(BigDecimal actualizacion) {
        this.actualizacion = actualizacion;
    }

    public BigDecimal getRecargo() {
        return recargo;
    }

    public void setRecargo(BigDecimal recargo) {
        this.recargo = recargo;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public String getOrigenFactor() {
        return origenFactor;
    }

    public void setOrigenFactor(String origenFactor) {
        this.origenFactor = origenFactor;
    }

    public Long getIdFactor() {
        return idFactor;
    }

    public void setIdFactor(Long idFactor) {
        this.idFactor = idFactor;
    }

    public String getInicioVigenciaFactor() {
        return inicioVigenciaFactor;
    }

    public void setInicioVigenciaFactor(String inicioVigenciaFactor) {
        this.inicioVigenciaFactor = inicioVigenciaFactor;
    }

    public String getFinVigenciaFactor() {
        return finVigenciaFactor;
    }

    public void setFinVigenciaFactor(String finVigenciaFactor) {
        this.finVigenciaFactor = finVigenciaFactor;
    }
}