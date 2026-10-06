package mx.gob.imss.cit.clienteswebservices.modalidad40.rest.bean;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PeriodoDTO implements Serializable {

	private static final long serialVersionUID = 1L;

    @JsonProperty("idPagoPeriodo")
    private Long idPagoPeriodo;

    @JsonProperty("anio")
    private Integer anio;

    @JsonProperty("mes")
    private Integer mes;

    @JsonProperty("fechaInicio")
    private Date fechaInicio;

    @JsonProperty("fechaFin")
    private Date fechaFin;

    @JsonProperty("diasNaturales")
    private Integer diasNaturales;

    @JsonProperty("idMunicipioImss")
    private Long idMunicipioImss;

    @JsonProperty("idSalarioGeneral")
    private Long idSalarioGeneral;

    @JsonProperty("idAreaGeografica")
    private Long idAreaGeografica;

    @JsonProperty("salarioElegido")
    private BigDecimal salarioElegido;

    @JsonProperty("salarioMinimo")
    private BigDecimal salarioMinimo;

    @JsonProperty("salarioAplicado")
    private BigDecimal salarioAplicado;

    @JsonProperty("ajustadoPorSalarioMinimo")
    private Boolean ajustadoPorSalarioMinimo;

    @JsonProperty("idUma")
    private Long idUma;

    @JsonProperty("umaDiaria")
    private BigDecimal umaDiaria;

    @JsonProperty("importeBase")
    private BigDecimal importeBase;

    @JsonProperty("importeActualizacion")
    private BigDecimal importeActualizacion;

    @JsonProperty("importeRecargo")
    private BigDecimal importeRecargo;

    @JsonProperty("importePago")
    private BigDecimal importePago;

    @JsonProperty("factorActualizacion")
    private BigDecimal factorActualizacion;

    @JsonProperty("tasaRecargo")
    private BigDecimal tasaRecargo;

    @JsonProperty("inpcInicial")
    private BigDecimal inpcInicial;

    @JsonProperty("inpcFinal")
    private BigDecimal inpcFinal;

    @JsonProperty("periodoInpcInicial")
    private String periodoInpcInicial;

    @JsonProperty("periodoInpcFinal")
    private String periodoInpcFinal;

    @JsonProperty("parametroRecargoPendiente")
    private Boolean parametroRecargoPendiente;

//    @JsonProperty("detalleRamas")
//    private List<DetalleRamaDTO> detalleRamas;

    public PeriodoDTO() {
    }

    public Long getIdPagoPeriodo() {
        return idPagoPeriodo;
    }

    public void setIdPagoPeriodo(Long idPagoPeriodo) {
        this.idPagoPeriodo = idPagoPeriodo;
    }

    public Integer getAnio() {
        return anio;
    }

    public void setAnio(Integer anio) {
        this.anio = anio;
    }

    public Integer getMes() {
        return mes;
    }

    public void setMes(Integer mes) {
        this.mes = mes;
    }

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public Date getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(Date fechaFin) {
        this.fechaFin = fechaFin;
    }

    public Integer getDiasNaturales() {
        return diasNaturales;
    }

    public void setDiasNaturales(Integer diasNaturales) {
        this.diasNaturales = diasNaturales;
    }

    public Long getIdMunicipioImss() {
        return idMunicipioImss;
    }

    public void setIdMunicipioImss(Long idMunicipioImss) {
        this.idMunicipioImss = idMunicipioImss;
    }

    public Long getIdSalarioGeneral() {
        return idSalarioGeneral;
    }

    public void setIdSalarioGeneral(Long idSalarioGeneral) {
        this.idSalarioGeneral = idSalarioGeneral;
    }

    public Long getIdAreaGeografica() {
        return idAreaGeografica;
    }

    public void setIdAreaGeografica(Long idAreaGeografica) {
        this.idAreaGeografica = idAreaGeografica;
    }

    public BigDecimal getSalarioElegido() {
        return salarioElegido;
    }

    public void setSalarioElegido(BigDecimal salarioElegido) {
        this.salarioElegido = salarioElegido;
    }

    public BigDecimal getSalarioMinimo() {
        return salarioMinimo;
    }

    public void setSalarioMinimo(BigDecimal salarioMinimo) {
        this.salarioMinimo = salarioMinimo;
    }

    public BigDecimal getSalarioAplicado() {
        return salarioAplicado;
    }

    public void setSalarioAplicado(BigDecimal salarioAplicado) {
        this.salarioAplicado = salarioAplicado;
    }

    public Boolean getAjustadoPorSalarioMinimo() {
        return ajustadoPorSalarioMinimo;
    }

    public void setAjustadoPorSalarioMinimo(Boolean ajustadoPorSalarioMinimo) {
        this.ajustadoPorSalarioMinimo = ajustadoPorSalarioMinimo;
    }

    public Long getIdUma() {
        return idUma;
    }

    public void setIdUma(Long idUma) {
        this.idUma = idUma;
    }

    public BigDecimal getUmaDiaria() {
        return umaDiaria;
    }

    public void setUmaDiaria(BigDecimal umaDiaria) {
        this.umaDiaria = umaDiaria;
    }

    public BigDecimal getImporteBase() {
        return importeBase;
    }

    public void setImporteBase(BigDecimal importeBase) {
        this.importeBase = importeBase;
    }

    public BigDecimal getImporteActualizacion() {
        return importeActualizacion;
    }

    public void setImporteActualizacion(BigDecimal importeActualizacion) {
        this.importeActualizacion = importeActualizacion;
    }

    public BigDecimal getImporteRecargo() {
        return importeRecargo;
    }

    public void setImporteRecargo(BigDecimal importeRecargo) {
        this.importeRecargo = importeRecargo;
    }

    public BigDecimal getImportePago() {
        return importePago;
    }

    public void setImportePago(BigDecimal importePago) {
        this.importePago = importePago;
    }

    public BigDecimal getFactorActualizacion() {
        return factorActualizacion;
    }

    public void setFactorActualizacion(BigDecimal factorActualizacion) {
        this.factorActualizacion = factorActualizacion;
    }

    public BigDecimal getTasaRecargo() {
        return tasaRecargo;
    }

    public void setTasaRecargo(BigDecimal tasaRecargo) {
        this.tasaRecargo = tasaRecargo;
    }

    public BigDecimal getInpcInicial() {
        return inpcInicial;
    }

    public void setInpcInicial(BigDecimal inpcInicial) {
        this.inpcInicial = inpcInicial;
    }

    public BigDecimal getInpcFinal() {
        return inpcFinal;
    }

    public void setInpcFinal(BigDecimal inpcFinal) {
        this.inpcFinal = inpcFinal;
    }

    public String getPeriodoInpcInicial() {
        return periodoInpcInicial;
    }

    public void setPeriodoInpcInicial(String periodoInpcInicial) {
        this.periodoInpcInicial = periodoInpcInicial;
    }

    public String getPeriodoInpcFinal() {
        return periodoInpcFinal;
    }

    public void setPeriodoInpcFinal(String periodoInpcFinal) {
        this.periodoInpcFinal = periodoInpcFinal;
    }

    public Boolean getParametroRecargoPendiente() {
        return parametroRecargoPendiente;
    }

    public void setParametroRecargoPendiente(Boolean parametroRecargoPendiente) {
        this.parametroRecargoPendiente = parametroRecargoPendiente;
    }

//    public List<DetalleRamaDTO> getDetalleRamas() {
//        return detalleRamas;
//    }
//
//    public void setDetalleRamas(List<DetalleRamaDTO> detalleRamas) {
//        this.detalleRamas = detalleRamas;
//    }

	@Override
	public String toString() {
		return "PeriodoDTO [idPagoPeriodo=" + idPagoPeriodo + ", anio=" + anio + ", mes=" + mes + ", fechaInicio="
				+ fechaInicio + ", fechaFin=" + fechaFin + ", diasNaturales=" + diasNaturales + ", idMunicipioImss="
				+ idMunicipioImss + ", idSalarioGeneral=" + idSalarioGeneral + ", idAreaGeografica=" + idAreaGeografica
				+ ", salarioElegido=" + salarioElegido + ", salarioMinimo=" + salarioMinimo + ", salarioAplicado="
				+ salarioAplicado + ", ajustadoPorSalarioMinimo=" + ajustadoPorSalarioMinimo + ", idUma=" + idUma
				+ ", umaDiaria=" + umaDiaria + ", importeBase=" + importeBase + ", importeActualizacion="
				+ importeActualizacion + ", importeRecargo=" + importeRecargo + ", importePago=" + importePago
				+ ", factorActualizacion=" + factorActualizacion + ", tasaRecargo=" + tasaRecargo + ", inpcInicial="
				+ inpcInicial + ", inpcFinal=" + inpcFinal + ", periodoInpcInicial=" + periodoInpcInicial
				+ ", periodoInpcFinal=" + periodoInpcFinal + ", parametroRecargoPendiente=" + parametroRecargoPendiente
				+ /*", detalleRamas=" + detalleRamas +*/ "]";
	}
    
    
}