package mx.gob.imss.ctirss.delta.model.gestion.solicitud;

import java.io.Serializable;
import java.security.InvalidKeyException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;

import org.apache.commons.lang.StringUtils;

import mx.gob.imss.ctirss.delta.framework.base.model.AbstractModel;
import mx.gob.imss.ctirss.delta.framework.util.Base64Cipher;
import mx.gob.imss.ctirss.delta.model.Usuario;
import mx.gob.imss.ctirss.delta.model.domicilio.Subdelegacion;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Fisica;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.FirmaElectronica;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.SujetoObligado;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.Tramite;

public class Solicitud extends AbstractModel implements Serializable {

    private static final long serialVersionUID = 1345068132530664771L;

    private CitaSolicitud citaSolicitud;
    private EstadoSolicitud estadoSolicitud;
    private Fisica personaInteresada;
    private PersonaInteresadaSolicitud personaInteresadaSolicitud;
    private RazonCancelacion razonCancelacion;
    private TipoSolicitud tipoSolicitud;
    private Usuario solicitante;

	private Long solicitudId;
    private Date fechaSolicitud;
    private String fechaSolicitudParse;
    private String noFolioSolicitud;
    private String observacion;
    
    private List<Tramite> tramites;
    
    private String cadenaOriginal;
	private String selloDigital;
	private String secuenciaDeNotaria;
	private String numeroSerieCertificado;
	private String urlAcuseFirma;
	
	private String cadenaOriginalRepresentado;
	private String selloDigitalRepresentado;
	private String secuenciaDeNotariaRepresentado;
	private String numeroSerieCertificadoRepresentado;
	private String urlAcuseFirmaRepresentado;
	
	private SujetoObligado sujetoObligado;
	
	private Date fechaActualizacion;
	
	private byte[] documentoAcuse;
	private byte[] documentoComprobante;
	
	private boolean indRpcInvalido;
	private boolean indReintentoRpc;
	private boolean publicable;
	
	private Date fechaBaja;
	private Date fechaCita;
	
	private boolean firmadaDigitalmente;
	private Date fechaConclusion;
	private Date fechaPresentacion;
	
	private String fechaPresentacionParse;
	private String fechaConclusionParse;
	private FirmaElectronica firmaElectronica;
	
	/**
	 * 
	 * @author Hugo Martinez
	 * @Date 31/10/2012
	 */
	private Subdelegacion subdelegacion;
	/**
	 * Se agrega pues los datos son requeridos para
	 * integrar el alta patronal con IDSE
	 */
	private Certificado certificado;
	
	private OrigenSolicitud origenSolicitud;
	
	SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
	
	// Id de la solicitud cifrado
	private String solicitudIdHashed;
	
	private String solicitudFolioHashed;
	
	public String getCadenaOriginal() {
		return cadenaOriginal;
	}

	public void setCadenaOriginal(String cadenaOriginal) {
		this.cadenaOriginal = cadenaOriginal;
	}

	public String getSelloDigital() {
		return selloDigital;
	}

	public void setSelloDigital(String selloDigital) {
		this.selloDigital = selloDigital;
	}

	public String getSecuenciaDeNotaria() {
		return secuenciaDeNotaria;
	}

	public void setSecuenciaDeNotaria(String secuenciaDeNotaria) {
		this.secuenciaDeNotaria = secuenciaDeNotaria;
	}

	public String getUrlAcuseFirma() {
		return urlAcuseFirma;
	}

	public void setUrlAcuseFirma(String urlAcuseFirma) {
		this.urlAcuseFirma = urlAcuseFirma;
	}
	
    public Solicitud() {
        // TO PMD
    }
    
    public Solicitud(final Long solicitudId) {
        this.solicitudId = solicitudId;
    }
    
    public Solicitud(Tramite tramite) {
        
    	this.setTramites(new ArrayList<Tramite>());
		this.getTramites().add(tramite);
		
		this.setFechaSolicitud(new Date());
    	
    }
    
    /*
     * Constructor de la solicitud que recibe el estado
     */
    public Solicitud(final Long solicitudId , Long  idEstadoSolicitud) {
    	
    	this.solicitudId = solicitudId;
    	
    	EstadoSolicitud estado = new EstadoSolicitud();
    	estado.setIdEstadoSolicitud(idEstadoSolicitud.intValue());
    	this.estadoSolicitud = estado;
    }
    
    /*
     * Constructor de la solicitud que recibe el id, el folio y el estado
     */
	public Solicitud(final String folio, final Long solicitudId,
			Long idEstadoSolicitud) {
    	
    	this.solicitudId = solicitudId;
    	
    	this.noFolioSolicitud = folio;
    	
    	EstadoSolicitud estado = new EstadoSolicitud();
    	estado.setIdEstadoSolicitud(idEstadoSolicitud.intValue());
    	this.estadoSolicitud = estado;
    }
    
    public CitaSolicitud getCitaSolicitud() {
        return citaSolicitud;
    }

    public void setCitaSolicitud(CitaSolicitud citaSolicitud) {
        this.citaSolicitud = citaSolicitud;
    }

    public EstadoSolicitud getEstadoSolicitud() {
        if(estadoSolicitud == null) {
            estadoSolicitud = new EstadoSolicitud();
        }
        return estadoSolicitud;
    }

    public void setEstadoSolicitud(EstadoSolicitud estadoSolicitud) {
        this.estadoSolicitud = estadoSolicitud;
    }

    public Fisica getPersonaInteresada() {
        return personaInteresada;
    }

    public void setPersonaInteresada(Fisica personaInteresada) {
        this.personaInteresada = personaInteresada;
    }

    public RazonCancelacion getRazonCancelacion() {
        if(razonCancelacion == null) {
            razonCancelacion = new RazonCancelacion();
        }
        return razonCancelacion;
    }

    public void setRazonCancelacion(RazonCancelacion razonCancelacion) {
        this.razonCancelacion = razonCancelacion;
    }

    public TipoSolicitud getTipoSolicitud() {
        if(tipoSolicitud == null) {
            tipoSolicitud = new TipoSolicitud();
        }
        return tipoSolicitud;
    }

    public void setTipoSolicitud(TipoSolicitud tipoSolicitud) {
        this.tipoSolicitud = tipoSolicitud;
    }

    public Usuario getSolicitante() {
        return solicitante;
    }

    public void setSolicitante(Usuario solicitante) {
        this.solicitante = solicitante;
    }

    public Long getSolicitudId() {
        return solicitudId;
    }

    public void setSolicitudId(Long solicitudId) {
        this.solicitudId = solicitudId;
        
        if (solicitudId != null) {
        	try {
				this.solicitudIdHashed = Base64Cipher.cifrar(solicitudId.toString());
			} catch (InvalidKeyException e) {
				e.printStackTrace();
			} catch (IllegalBlockSizeException e) {
				e.printStackTrace();
			} catch (BadPaddingException e) {
				e.printStackTrace();
			}
        }
    }

    public PersonaInteresadaSolicitud getPersonaInteresadaSolicitud() {
		return personaInteresadaSolicitud;
	}

	public void setPersonaInteresadaSolicitud(
			PersonaInteresadaSolicitud personaInteresadaSolicitud) {
		this.personaInteresadaSolicitud = personaInteresadaSolicitud;
	}

	public Date getFechaSolicitud() {
        return fechaSolicitud;
    }

    public void setFechaSolicitud(Date fechaSolicitud) {
        this.fechaSolicitud = fechaSolicitud;
        
		if (fechaSolicitud != null) {
        	this.fechaSolicitudParse = sdf.format(fechaSolicitud);
        }
    }

    public String getNoFolioSolicitud() {
        return noFolioSolicitud;
    }

    public void setNoFolioSolicitud(String noFolioSolicitud) {
        this.noFolioSolicitud = noFolioSolicitud;
        
        if (noFolioSolicitud != null) {
        	try {
				this.solicitudFolioHashed = Base64Cipher.cifrar(this.noFolioSolicitud);
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

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    public List<Tramite> getTramites() {
        if(tramites == null) {
            tramites = new ArrayList<Tramite>();
        }
        return tramites;
    }

    public void setTramites(List<Tramite> tramites) {
        this.tramites = tramites;
    }

	public SujetoObligado getSujetoObligado() {
		return sujetoObligado;
	}

	public void setSujetoObligado(SujetoObligado sujetoObligado) {
		this.sujetoObligado = sujetoObligado;
	}

	public Date getFechaActualizacion() {
		return fechaActualizacion;
	}

	public void setFechaActualizacion(Date fechaActualizacion) {
		this.fechaActualizacion = fechaActualizacion;
	}

	public byte[] getDocumentoAcuse() {
		return documentoAcuse;
	}

	public void setDocumentoAcuse(byte[] documentoAcuse) {
		this.documentoAcuse = documentoAcuse != null ? documentoAcuse.clone() : null;
	}

	public byte[] getDocumentoComprobante() {
		return documentoComprobante;
	}

	public void setDocumentoComprobante(byte[] documentoComprobante) {
		this.documentoComprobante = documentoComprobante != null ? documentoComprobante.clone() : null;
	}

	public Subdelegacion getSubdelegacion() {
		return subdelegacion;
	}

	public void setSubdelegacion(Subdelegacion subdelegacion) {
		this.subdelegacion = subdelegacion;
	}

	public String getFechaSolicitudParse() {
		return fechaSolicitudParse;
	}

	public void setFechaSolicitudParse(String fechaSolicitudParse) {
		this.fechaSolicitudParse = fechaSolicitudParse;
	}

	public boolean isIndRpcInvalido() {
		return indRpcInvalido;
	}

	public void setIndRpcInvalido(boolean indRpcInvalido) {
		this.indRpcInvalido = indRpcInvalido;
	}

	public boolean isIndReintentoRpc() {
		return indReintentoRpc;
	}

	public void setIndReintentoRpc(boolean indReintentoRpc) {
		this.indReintentoRpc = indReintentoRpc;
	}

	public Date getFechaBaja() {
		return fechaBaja;
	}

	public void setFechaBaja(Date fechaBaja) {
		this.fechaBaja = fechaBaja;
	}

	public Date getFechaCita() {
		return fechaCita;
	}

	public void setFechaCita(Date fechaCita) {
		this.fechaCita = fechaCita;
	}

	public boolean isPublicable() {
		return publicable;
	}

	public void setPublicable(boolean publicable) {
		this.publicable = publicable;
	}

	public boolean isFirmadaDigitalmente() {
		return firmadaDigitalmente;
	}

	public void setFirmadaDigitalmente(boolean fueFirmadaDigitalmente) {
		this.firmadaDigitalmente = fueFirmadaDigitalmente;
	}

	public Date getFechaConclusion() {
		return fechaConclusion;
	}

	public void setFechaConclusion(Date fechaConclusion) {
		this.fechaConclusion = fechaConclusion;
	}

	public Date getFechaPresentacion() {
		return fechaPresentacion;
	}

	public void setFechaPresentacion(Date fechaPresentacion) {
		this.fechaPresentacion = fechaPresentacion;
		
		if (fechaPresentacion != null) {
        	this.fechaPresentacionParse = sdf.format(fechaPresentacion);
        }
	}

	public String getFechaPresentacionParse() {
		return fechaPresentacionParse;
	}

	public void setFechaPresentacionParse(String fechaPresentacionParse) {
		this.fechaPresentacionParse = fechaPresentacionParse;
	}

	public String getFechaConclusionParse() {
		return fechaConclusionParse;
	}

	public void setFechaConclusionParse(String fechaConclusionParse) {
		this.fechaConclusionParse = fechaConclusionParse;
	}

	public Certificado getCertificado() {
		return certificado;
	}

	public void setCertificado(Certificado certificado) {
		this.certificado = certificado;
	}

	public FirmaElectronica getFirmaElectronica() {
		return firmaElectronica;
	}

	public void setFirmaElectronica(FirmaElectronica firmaElectronica) {
		this.firmaElectronica = firmaElectronica;
	}

	public String getCadenaOriginalRepresentado() {
		return cadenaOriginalRepresentado;
	}

	public void setCadenaOriginalRepresentado(String cadenaOriginalRepresentado) {
		this.cadenaOriginalRepresentado = cadenaOriginalRepresentado;
	}

	public String getSelloDigitalRepresentado() {
		return selloDigitalRepresentado;
	}

	public void setSelloDigitalRepresentado(String selloDigitalRepresentado) {
		this.selloDigitalRepresentado = selloDigitalRepresentado;
	}

	public String getSecuenciaDeNotariaRepresentado() {
		return secuenciaDeNotariaRepresentado;
	}

	public void setSecuenciaDeNotariaRepresentado(
			String secuenciaDeNotariaRepresentado) {
		this.secuenciaDeNotariaRepresentado = secuenciaDeNotariaRepresentado;
	}

	public String getUrlAcuseFirmaRepresentado() {
		return urlAcuseFirmaRepresentado;
	}

	public void setUrlAcuseFirmaRepresentado(String urlAcuseFirmaRepresentado) {
		this.urlAcuseFirmaRepresentado = urlAcuseFirmaRepresentado;
	}

	public String getNumeroSerieCertificado() {
		return numeroSerieCertificado;
	}

	public void setNumeroSerieCertificado(String numeroSerieCertificado) {
		this.numeroSerieCertificado = numeroSerieCertificado;
	}

	public String getNumeroSerieCertificadoRepresentado() {
		return numeroSerieCertificadoRepresentado;
	}

	public void setNumeroSerieCertificadoRepresentado(
			String numeroSerieCertificadoRepresentado) {
		this.numeroSerieCertificadoRepresentado = numeroSerieCertificadoRepresentado;
	}
	
	public OrigenSolicitud getOrigenSolicitud() {
		return origenSolicitud;
	}

	public void setOrigenSolicitud(OrigenSolicitud origenSolicitud) {
		this.origenSolicitud = origenSolicitud;
	}

	public String getSolicitudIdHashed() {
		
		if (StringUtils.isBlank(this.solicitudIdHashed)
				&& this.solicitudId != null) {
			try {
				this.solicitudIdHashed = Base64Cipher.cifrar(solicitudId.toString());
			} catch (InvalidKeyException e) {
				e.printStackTrace();
			} catch (IllegalBlockSizeException e) {
				e.printStackTrace();
			} catch (BadPaddingException e) {
				e.printStackTrace();
			}
		}
		
		return solicitudIdHashed;
	}

	public void setSolicitudIdHashed(String solicitudIdHashed) {
		this.solicitudIdHashed = solicitudIdHashed;
	}

	public String getSolicitudFolioHashed() {
		if (StringUtils.isBlank(this.solicitudFolioHashed)
				&& this.noFolioSolicitud != null) {
			try {
				this.solicitudFolioHashed = Base64Cipher.cifrar(this.noFolioSolicitud);
			} catch (InvalidKeyException e) {
				e.printStackTrace();
			} catch (IllegalBlockSizeException e) {
				e.printStackTrace();
			} catch (BadPaddingException e) {
				e.printStackTrace();
			}
		}
		
		return this.solicitudFolioHashed;
	}

	public void setSolicitudFolioHashed(String solicitudFolioHashed) {
		this.solicitudFolioHashed = solicitudFolioHashed;
	}
	
	
	
}
