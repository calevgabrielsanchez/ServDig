package mx.gob.imss.ctirss.delta.model.beneficio;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang.builder.ToStringBuilder;

public class BeneficioSchema implements java.io.Serializable {

    private static final long serialVersionUID = -7569566550537918937L;

    private Date inicioVigencia;
    private Date finVigencia;
    private Date fechaBaja;
    private EstadoBeneficio estadoBeneficio;
    private TipoBeneficio tipoBeneficio;
    private MotivoCancelacionBeneficio motivoCancelacion;
    private List<DescuentoBeneficio> listaDescuentosBeneficio;
    
    private String rfc;
    private String curp;    
    private String nombre;
    private String primerApellido;
    private String segundoApellido;
    private boolean indicadorApartadoC;
    private List<String> listaNRPsMod10y13;
    
    public Date getInicioVigencia() {
        return inicioVigencia;
    }

    public void setInicioVigencia(Date inicioVigencia) {
        this.inicioVigencia = inicioVigencia;
    }

    public Date getFinVigencia() {
        return finVigencia;
    }

    public void setFinVigencia(Date finVigencia) {
        this.finVigencia = finVigencia;
    }

    public Date getFechaBaja() {
		return fechaBaja;
	}

	public void setFechaBaja(Date fechaBaja) {
		this.fechaBaja = fechaBaja;
	}

	public EstadoBeneficio getEstadoBeneficio() {
        return estadoBeneficio;
    }

    public void setEstadoBeneficio(EstadoBeneficio estadoBeneficio) {
        this.estadoBeneficio = estadoBeneficio;
    }

    public TipoBeneficio getTipoBeneficio() {
        return tipoBeneficio;
    }

    public void setTipoBeneficio(TipoBeneficio tipoBeneficio) {
        this.tipoBeneficio = tipoBeneficio;
    }

    public MotivoCancelacionBeneficio getMotivoCancelacion() {
        return motivoCancelacion;
    }

    public void setMotivoCancelacion(MotivoCancelacionBeneficio motivoCancelacion) {
        this.motivoCancelacion = motivoCancelacion;
    }

    public List<DescuentoBeneficio> getListaDescuentosBeneficio() {
        return listaDescuentosBeneficio;
    }

    public void setListaDescuentosBeneficio(List<DescuentoBeneficio> listaDescuentosBeneficio) {
        this.listaDescuentosBeneficio = listaDescuentosBeneficio;
    }


	public String getRfc() {
		return rfc;
	}

	public void setRfc(String rfc) {
		this.rfc = rfc;
	}

	public String getCurp() {
		return curp;
	}

	public void setCurp(String curp) {
		this.curp = curp;
	}

	public boolean isIndicadorApartadoC() {
		return indicadorApartadoC;
	}

	public void setIndicadorApartadoC(boolean indicadorApartadoC) {
		this.indicadorApartadoC = indicadorApartadoC;
	}

	public List<String> getListaNRPsMod10y13() {
		return listaNRPsMod10y13;
	}

	public void setListaNRPsMod10y13(List<String> listaNRPsMod10y13) {
		this.listaNRPsMod10y13 = listaNRPsMod10y13;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getPrimerApellido() {
		return primerApellido;
	}

	public void setPrimerApellido(String primerApellido) {
		this.primerApellido = primerApellido;
	}

	public String getSegundoApellido() {
		return segundoApellido;
	}

	public void setSegundoApellido(String segundoApellido) {
		this.segundoApellido = segundoApellido;
	}


	@Override
	public String toString() {
        return new ToStringBuilder(this)
                .append("inicioVigencia", inicioVigencia)
                .append("finVigencia", finVigencia)
                .append("fechaBaja", fechaBaja)
                .append("estadoBeneficio", estadoBeneficio)
                .append("tipoBeneficio", tipoBeneficio)
                .append("motivoCancelacion", motivoCancelacion)
                .append("listaDescuentosBeneficio", listaDescuentosBeneficio)                
                .append("rfc", rfc)
                .append("curp", curp)
                .append("nombre", nombre)
                .append("primerApellido", primerApellido)
                .append("segundoApellido", segundoApellido)
                .append("indicadorApartadoC", indicadorApartadoC)
                .append("listaNRPsMod10y13", listaNRPsMod10y13)
                .toString();
    }

}
