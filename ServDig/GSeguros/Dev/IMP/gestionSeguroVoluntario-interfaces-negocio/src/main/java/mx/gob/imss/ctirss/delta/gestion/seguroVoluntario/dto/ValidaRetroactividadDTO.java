package mx.gob.imss.ctirss.delta.gestion.seguroVoluntario.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;


public class ValidaRetroactividadDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private String idCalculo;

    private String idTramite;

    private String nss;

    private Integer modalidad;

    private Boolean aplicaRetroactividad;

    private Boolean aplicaRenovacion;

    private Date fechaInicio;

    private Date fechaFin;

    private Integer numeroMeses;

    private Date fechaCorteValidacion;

    private Date fechaLimiteElegibilidad;

    private BigDecimal salarioPiso;

    private BigDecimal salarioTope;

    private String mensaje;

    // Constructor vacío (requerido por Jackson y especificación JavaBean)
    public ValidaRetroactividadDTO() {
    }

    // Constructor completo
    public ValidaRetroactividadDTO(String idCalculo, String idTramite, String nss, Integer modalidad,
                                    Boolean aplicaRetroactividad, Boolean aplicaRenovacion, 
                                    Date fechaInicio, Date fechaFin, Integer numeroMeses, 
                                    Date fechaCorteValidacion, Date fechaLimiteElegibilidad, 
                                    BigDecimal salarioPiso, BigDecimal salarioTope, String mensaje) {
        this.idCalculo = idCalculo;
        this.idTramite = idTramite;
        this.nss = nss;
        this.modalidad = modalidad;
        this.aplicaRetroactividad = aplicaRetroactividad;
        this.aplicaRenovacion = aplicaRenovacion;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.numeroMeses = numeroMeses;
        this.fechaCorteValidacion = fechaCorteValidacion;
        this.fechaLimiteElegibilidad = fechaLimiteElegibilidad;
        this.salarioPiso = salarioPiso;
        this.salarioTope = salarioTope;
        this.mensaje = mensaje;
    }

    // Getters y Setters
   

    public String getIdTramite() {
        return idTramite;
    }

    public String getIdCalculo() {
		return idCalculo;
	}

	public void setIdCalculo(String idCalculo) {
		this.idCalculo = idCalculo;
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

    public Integer getModalidad() {
        return modalidad;
    }

    public void setModalidad(Integer modalidad) {
        this.modalidad = modalidad;
    }

    public Boolean getAplicaRetroactividad() {
        return aplicaRetroactividad;
    }

    public void setAplicaRetroactividad(Boolean aplicaRetroactividad) {
        this.aplicaRetroactividad = aplicaRetroactividad;
    }

    public Boolean getAplicaRenovacion() {
        return aplicaRenovacion;
    }

    public void setAplicaRenovacion(Boolean aplicaRenovacion) {
        this.aplicaRenovacion = aplicaRenovacion;
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

    public Integer getNumeroMeses() {
        return numeroMeses;
    }

    public void setNumeroMeses(Integer numeroMeses) {
        this.numeroMeses = numeroMeses;
    }

    public Date getFechaCorteValidacion() {
        return fechaCorteValidacion;
    }

    public void setFechaCorteValidacion(Date fechaCorteValidacion) {
        this.fechaCorteValidacion = fechaCorteValidacion;
    }

    public Date getFechaLimiteElegibilidad() {
        return fechaLimiteElegibilidad;
    }

    public void setFechaLimiteElegibilidad(Date fechaLimiteElegibilidad) {
        this.fechaLimiteElegibilidad = fechaLimiteElegibilidad;
    }

    public BigDecimal getSalarioPiso() {
        return salarioPiso;
    }

    public void setSalarioPiso(BigDecimal salarioPiso) {
        this.salarioPiso = salarioPiso;
    }

    public BigDecimal getSalarioTope() {
        return salarioTope;
    }

    public void setSalarioTope(BigDecimal salarioTope) {
        this.salarioTope = salarioTope;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }
}