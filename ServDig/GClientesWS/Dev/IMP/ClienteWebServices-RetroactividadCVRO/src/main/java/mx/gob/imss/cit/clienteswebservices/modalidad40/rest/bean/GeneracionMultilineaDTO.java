package mx.gob.imss.cit.clienteswebservices.modalidad40.rest.bean;

import java.io.Serializable;


public class GeneracionMultilineaDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private String idSolicitud;
    private String idCalculo;
    private String correlacion;
    private String estado;
    private Integer numeroPeriodos;
    private Integer periodosProcesados;
    private String urlConsulta;

    public GeneracionMultilineaDTO() {
    }


    public String getIdSolicitud() {
		return idSolicitud;
	}


	public void setIdSolicitud(String idSolicitud) {
		this.idSolicitud = idSolicitud;
	}


	public String getIdCalculo() {
		return idCalculo;
	}


	public void setIdCalculo(String idCalculo) {
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

    public String getUrlConsulta() {
        return urlConsulta;
    }

    public void setUrlConsulta(String urlConsulta) {
        this.urlConsulta = urlConsulta;
    }
}