package mx.gob.imss.ctirss.delta.model.gestion.individuo;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import mx.gob.imss.ctirss.delta.model.gestion.patronal.EscrituraConstitutiva;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.RegistroSindicato;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.TipoSociedad;

public class Moral extends Persona {

    private static final long serialVersionUID = 4028913331113322982L;
    private Long cveMoral;
    private TipoSociedad tipoSociedad;
    private String razonSocial;

    /*****/
    private String nrp;
    private String rfcSat;
    private String actaConstitutiva;
    private String desSituacion;
    private Integer idSituacion;
    private String fechaCreacionFormateada;
    private Date fechaCreacion;
    private Date fechaRegistro;
    private Date fechaBaja;
    private Date fechaModificacion;
    private String busqAprox;
    private Long idActaConstitutiva;
    private String altaEnImss;
    private Integer numeroLineaArchivo;
    private String estadosFormateados;
    private String subEstadosFormateados;
	private BigDecimal indAcreditado;
     /*******************************************/
    
	private EscrituraConstitutiva escrituraConstitutiva;
	private RegistroSindicato registroSindicato;
	
	private List<SituacionSAT> situacionesSAT;
	private DatosPersonaSAT datosPersonaSAT;

	/**
	 * Constructor por omision
	 */
	public Moral(){
		super();
		this.tipoSociedad = new TipoSociedad();
	}
	
	public Moral(Long cveIdPersonaMoral, String rfc, String denominacionRazonSocial ){
		super();
		this.tipoSociedad = new TipoSociedad();
		this.setTipoPersona(new TipoPersona());
		this.getTipoPersona().setIdTipoPersona(TipoPersona.TIPO_PERSONA_MORAL);
		this.setIdPersona(cveIdPersonaMoral);
		this.setRfc(rfc);
		this.razonSocial=denominacionRazonSocial;
	}
	
	public String getNrp() {
        return nrp;
    }

    public void setNrp(String nrp) {
        this.nrp = nrp;
    }

    public String getRfcSat() {
        return rfcSat;
    }

    public void setRfcSat(String rfcSat) {
        this.rfcSat = rfcSat;
    }

    public String getActaConstitutiva() {
        return actaConstitutiva;
    }

    public void setActaConstitutiva(String actaConstitutiva) {
        this.actaConstitutiva = actaConstitutiva;
    }

    public String getDesSituacion() {
        return desSituacion;
    }

    public void setDesSituacion(String desSituacion) {
        this.desSituacion = desSituacion;
    }

    public Integer getIdSituacion() {
        return idSituacion;
    }

    public void setIdSituacion(Integer idSituacion) {
        this.idSituacion = idSituacion;
    }

    public String getFechaCreacionFormateada() {
        return fechaCreacionFormateada;
    }

    public void setFechaCreacionFormateada(String fechaCreacionFormateada) {
        this.fechaCreacionFormateada = fechaCreacionFormateada;
    }

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public Date getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(Date fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    public Date getFechaBaja() {
        return fechaBaja;
    }

    public void setFechaBaja(Date fechaBaja) {
        this.fechaBaja = fechaBaja;
    }

    public Date getFechaModificacion() {
        return fechaModificacion;
    }

    public void setFechaModificacion(Date fechaModificacion) {
        this.fechaModificacion = fechaModificacion;
    }

    public String getBusqAprox() {
        return busqAprox;
    }

    public void setBusqAprox(String busqAprox) {
        this.busqAprox = busqAprox;
    }

    public Long getIdActaConstitutiva() {
        return idActaConstitutiva;
    }

    public void setIdActaConstitutiva(Long idActaConstitutiva) {
        this.idActaConstitutiva = idActaConstitutiva;
    }

    public String getAltaEnImss() {
        return altaEnImss;
    }

    public void setAltaEnImss(String altaEnImss) {
        this.altaEnImss = altaEnImss;
    }

    public Integer getNumeroLineaArchivo() {
        return numeroLineaArchivo;
    }

    public void setNumeroLineaArchivo(Integer numeroLineaArchivo) {
        this.numeroLineaArchivo = numeroLineaArchivo;
    }

    public String getEstadosFormateados() {
        return estadosFormateados;
    }

    public void setEstadosFormateados(String estadosFormateados) {
        this.estadosFormateados = estadosFormateados;
    }

    public String getSubEstadosFormateados() {
        return subEstadosFormateados;
    }

    public void setSubEstadosFormateados(String subEstadosFormateados) {
        this.subEstadosFormateados = subEstadosFormateados;
    }
    
	public BigDecimal getIndAcreditado() {
		return indAcreditado;
	}

	public void setIndAcreditado(BigDecimal indAcreditado) {
		this.indAcreditado = indAcreditado;
	}

	public Long getCveMoral() {
		return cveMoral;
	}

	public void setCveMoral(Long cveMoral) {
		this.cveMoral = cveMoral;
	}

	public TipoSociedad getTipoSociedad() {
		return tipoSociedad;
	}

	public void setTipoSociedad(TipoSociedad tipoSociedad) {
		this.tipoSociedad = tipoSociedad;
	}

	public String getRazonSocial() {
		return razonSocial;
	}

	public void setRazonSocial(String razonSocial) {
		this.razonSocial = razonSocial;
	}
	

    public EscrituraConstitutiva getEscrituraConstitutiva() {
		return escrituraConstitutiva;
	}

	public void setEscrituraConstitutiva(EscrituraConstitutiva escrituraConstitutiva) {
		this.escrituraConstitutiva = escrituraConstitutiva;
	}

	public RegistroSindicato getRegistroSindicato() {
		return registroSindicato;
	}

	public void setRegistroSindicato(RegistroSindicato registroSindicato) {
		this.registroSindicato = registroSindicato;
	}

	public List<SituacionSAT> getSituacionesSAT() {
		return situacionesSAT;
	}

	public void setSituacionesSAT(List<SituacionSAT> situacionesSAT) {
		this.situacionesSAT = situacionesSAT;
	}

	/**
	 * @return the datosPersonaSAT
	 */
	public DatosPersonaSAT getDatosPersonaSAT() {
		return datosPersonaSAT;
	}

	/**
	 * @param datosPersonaSAT the datosPersonaSAT to set
	 */
	public void setDatosPersonaSAT(DatosPersonaSAT datosPersonaSAT) {
		this.datosPersonaSAT = datosPersonaSAT;
	}

}
