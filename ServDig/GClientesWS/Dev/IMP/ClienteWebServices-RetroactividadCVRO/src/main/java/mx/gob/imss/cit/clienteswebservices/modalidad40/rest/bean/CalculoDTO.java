package mx.gob.imss.cit.clienteswebservices.modalidad40.rest.bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CalculoDTO implements Serializable {

    private static final long serialVersionUID = 1L;

	@JsonProperty("idCalculo")
    private String idCalculo;
	
	@JsonProperty("idCotizacion")
    private String idCotizacion;
	
	@JsonProperty("idTramite")
    private String idTramite;
	
	@JsonProperty("nss")
    private String nss;
	
	@JsonProperty("municipio")
    private String municipio;
	
	@JsonProperty("moneda")
    private String moneda;
	
	@JsonProperty("numeroPeriodos")
    private Integer numeroPeriodos;
	
	@JsonProperty("importeTotal")
    private BigDecimal importeTotal;
	
	@JsonProperty("calculoProvisional")
    private Boolean calculoProvisional;
	
	@JsonProperty("mensaje")
    private String mensaje;
	
	@JsonProperty("periodos")
    private List<PeriodoDTO> periodos;

    public CalculoDTO() {
    }


    public String getIdCalculo() {
		return idCalculo;
	}


	public void setIdCalculo(String idCalculo) {
		this.idCalculo = idCalculo;
	}


	public String getIdCotizacion() {
		return idCotizacion;
	}


	public void setIdCotizacion(String idCotizacion) {
		this.idCotizacion = idCotizacion;
	}


	public String getIdTramite() {
        return idTramite;
    }

    public void setIdTramite(String idTramite) {
        this.idTramite = idTramite;
    }

    public String getNss() {
        return nss;
    }

    public void setNss(String nss) {
        this.nss = nss;
    }

    public String getMunicipio() {
        return municipio;
    }

    public void setMunicipio(String municipio) {
        this.municipio = municipio;
    }

    public String getMoneda() {
        return moneda;
    }

    public void setMoneda(String moneda) {
        this.moneda = moneda;
    }

    public Integer getNumeroPeriodos() {
        return numeroPeriodos;
    }

    public void setNumeroPeriodos(Integer numeroPeriodos) {
        this.numeroPeriodos = numeroPeriodos;
    }

    public BigDecimal getImporteTotal() {
        return importeTotal;
    }

    public void setImporteTotal(BigDecimal importeTotal) {
        this.importeTotal = importeTotal;
    }

    public Boolean getCalculoProvisional() {
        return calculoProvisional;
    }

    public void setCalculoProvisional(Boolean calculoProvisional) {
        this.calculoProvisional = calculoProvisional;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public List<PeriodoDTO> getPeriodos() {
        return periodos;
    }

    public void setPeriodos(List<PeriodoDTO> periodos) {
        this.periodos = periodos;
    }
}