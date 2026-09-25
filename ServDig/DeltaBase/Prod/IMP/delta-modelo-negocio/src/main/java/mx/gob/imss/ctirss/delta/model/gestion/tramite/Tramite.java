package mx.gob.imss.ctirss.delta.model.gestion.tramite;

import java.io.Serializable;
import java.security.InvalidKeyException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.xml.bind.annotation.XmlRootElement;

import mx.gob.imss.ctirss.delta.framework.base.model.AbstractModel;
import mx.gob.imss.ctirss.delta.framework.util.Base64Cipher;
import mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio.DocumentoPorTipo;
import mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio.DocumentoProbatorio;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Fisica;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.AcuseVentanilla;

@XmlRootElement
public class Tramite extends AbstractModel implements Serializable {

    private static final long serialVersionUID = 1691007826053578876L;
    private Boolean resultado;
    private RazonResultado razonResultado;
    private EstadoTramite estadoTramite;
    
    private Long tramiteId;
    private String observacion;
    private Date fechaTramite;
    private TipoTramite tipoTramite;
    private String detalleTramiteXml;
    private Boolean indRatificado; 
    private Date fechaPresentacion;
    private String fechaPresentacionParse;
    private Date fechaRegistroActualizacion;
    private Date fechaEfecto;
    private Date fechaConclusion;
    private String fechaConclusionParse;
    private Integer pasoTramite;
    private List<DocumentoPorTipo> documentoPorTipos;
    private Fisica persona;
    private List<Fisica> personas;
    private List<DocumentoProbatorio> documentosProbatorios;
	private DateFormat df = new SimpleDateFormat("dd/MM/yyyy kk:mm:ss");    
	private String tramiteIdHashed;
	private Long idPersonaRL;
	private AcuseVentanilla acuseVentanilla;
	private Boolean auditoria;
	

	public Boolean getAuditoria() {
		return auditoria;
	}

	public void setAuditoria(Boolean auditoria) {
		this.auditoria = auditoria;
	}

	public Tramite(){
		
	}
	
	public Tramite(Long tramiteId){
		this.tramiteId = tramiteId;
	}
	
    public List<DocumentoPorTipo> getDocumentoPorTipos() {
		return documentoPorTipos;
	}

	public void setDocumentoPorTipos(List<DocumentoPorTipo> documentoPorTipos) {
		this.documentoPorTipos = documentoPorTipos;
	}

	public Boolean getResultado() {
        return resultado;
    }

    public void setResultado(final Boolean resultado) {
        this.resultado = resultado;
    }

    public RazonResultado getRazonResultado() {
        if(razonResultado == null) {
            razonResultado = new RazonResultado();
        }
        return razonResultado;
    }

    public void setRazonResultado(final RazonResultado razonResultado) {
        this.razonResultado = razonResultado;
    }

    public EstadoTramite getEstadoTramite() {
        if(estadoTramite == null) {
            estadoTramite = new EstadoTramite();
        }
        return estadoTramite;
    }

    public void setEstadoTramite(final EstadoTramite estadoTramite) {
        this.estadoTramite = estadoTramite;
    }

    public Long getTramiteId() {
        return tramiteId;
    }

    public void setTramiteId(final Long tramiteId) {
        this.tramiteId = tramiteId;
        
        if (tramiteId != null) {
        	try {
				this.tramiteIdHashed = Base64Cipher.cifrar(tramiteId.toString());
			} catch (InvalidKeyException e) {
				e.printStackTrace();
			} catch (IllegalBlockSizeException e) {
				e.printStackTrace();
			} catch (BadPaddingException e) {
				e.printStackTrace();
			}
        }
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(final String observacion) {
        this.observacion = observacion;
    }

    public Date getFechaTramite() {
        return fechaTramite;
    }

    public void setFechaTramite(final Date fechaTramite) {
        this.fechaTramite = fechaTramite;
    }

    public TipoTramite getTipoTramite() {
        if(tipoTramite == null) {
            tipoTramite = new TipoTramite();
        }
        return tipoTramite;
    }

    public void setTipoTramite(final TipoTramite tipoTramite) {
        this.tipoTramite = tipoTramite;
    }

    public String getDetalleTramiteXml() {
        return detalleTramiteXml;
    }

    public void setDetalleTramiteXml(final String detalleTramiteXml) {
        this.detalleTramiteXml = detalleTramiteXml;
    }

	public Boolean getIndRatificado() {
		return indRatificado;
	}

	public void setIndRatificado(Boolean indRatificado) {
		this.indRatificado = indRatificado;
	}

	public Date getFechaPresentacion() {
		return fechaPresentacion;
	}

	public void setFechaPresentacion(Date fechaPresentacion) {
		this.fechaPresentacion = fechaPresentacion;
		
		if(this.fechaPresentacion != null) {
			this.fechaPresentacionParse = df.format(fechaPresentacion);
		}
	}

	public Date getFechaEfecto() {
		return fechaEfecto;
	}

	public void setFechaEfecto(Date fechaEfecto) {
		this.fechaEfecto = fechaEfecto;
	}

	public Date getFechaConclusion() {
		return fechaConclusion;
	}

	public void setFechaConclusion(Date fechaConclusion) {
		this.fechaConclusion = fechaConclusion;
	}

	public String getFechaPresentacionParse() {
		return fechaPresentacionParse;
	}

	public String getFechaConclusionParse() {
		return fechaConclusionParse;
	}

	public void setFechaPresentacionParse(String fechaPresentacionParse) {
		this.fechaPresentacionParse = fechaPresentacionParse;
	}

	public void setFechaConclusionParse(String fechaConclusionParse) {
		this.fechaConclusionParse = fechaConclusionParse;
	}

	public Integer getPasoTramite() {
		return pasoTramite;
	}

	public void setPasoTramite(Integer pasoTramite) {
		this.pasoTramite = pasoTramite;
	}

	public String getTramiteIdHashed() {
		return tramiteIdHashed;
	}

	public void setTramiteIdHashed(String tramiteIdHashed) {
		this.tramiteIdHashed = tramiteIdHashed;
	}

	public Fisica getPersona() {
		return persona;
	}

	public void setPersona(Fisica persona) {
		this.persona = persona;
	}

	public List<Fisica> getPersonas() {
		return personas;
	}

	public void setPersonas(List<Fisica> personas) {
		this.personas = personas;
	}

	public Date getFechaRegistroActualizacion() {
		return fechaRegistroActualizacion;
	}

	public void setFechaRegistroActualizacion(Date fechaRegistroActualizacion) {
		this.fechaRegistroActualizacion = fechaRegistroActualizacion;
	}

	public List<DocumentoProbatorio> getDocumentosProbatorios() {
		return documentosProbatorios;
	}

	public void setDocumentosProbatorios(
			List<DocumentoProbatorio> documentosProbatorios) {
		this.documentosProbatorios = documentosProbatorios;
	}
	
	public Long getIdPersonaRL() {
        return idPersonaRL;
    }
    public void setIdPersonaRL(Long idPersona) {
        this.idPersonaRL = idPersona;
    }

	public AcuseVentanilla getAcuseVentanilla() {
		return acuseVentanilla;
	}
	public void setAcuseVentanilla(AcuseVentanilla acuseVentanilla) {
		this.acuseVentanilla = acuseVentanilla;
	}
    
}