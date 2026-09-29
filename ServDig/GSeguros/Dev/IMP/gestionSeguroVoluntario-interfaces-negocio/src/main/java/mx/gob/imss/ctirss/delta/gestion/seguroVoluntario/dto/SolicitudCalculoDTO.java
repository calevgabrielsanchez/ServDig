package mx.gob.imss.ctirss.delta.gestion.seguroVoluntario.dto;

import java.io.Serializable;
import java.math.BigDecimal;

public class SolicitudCalculoDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private String idCalculo;
    private String nss;
    private String municipioImss;
    private BigDecimal salarioElegido;
    private String origenCalculo;
    private String usuario;

    public SolicitudCalculoDTO() {
    }


    public String getIdCalculo() {
		return idCalculo;
	}


	public void setIdCalculo(String idCalculo) {
		this.idCalculo = idCalculo;
	}


	public String getNss() {
        return nss;
    }

    public void setNss(String nss) {
        this.nss = nss;
    }

    public String getMunicipioImss() {
        return municipioImss;
    }

    public void setMunicipioImss(String municipioImss) {
        this.municipioImss = municipioImss;
    }

    public BigDecimal getSalarioElegido() {
        return salarioElegido;
    }

    public void setSalarioElegido(BigDecimal salarioElegido) {
        this.salarioElegido = salarioElegido;
    }

    public String getOrigenCalculo() {
        return origenCalculo;
    }

    public void setOrigenCalculo(String origenCalculo) {
        this.origenCalculo = origenCalculo;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }
}