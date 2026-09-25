/**
 * 
 */
package mx.gob.imss.digital.modelo.tramite;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import mx.gob.imss.digital.modelo.documento.probatorio.DocumentoPorTipo;
import mx.gob.imss.digital.modelo.documento.probatorio.DocumentoProbatorio;
import mx.gob.imss.digital.modelo.persona.Fisica;

/**
 * MOdelo de tramites IMSS
 * @author NOVUTECK1
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "tramite", namespace = "http://mx.gob.imss.digital.modelo.tramite")
@XmlRootElement(name = "tramite", namespace = "http://mx.gob.imss.digital.modelo.tramite")
public class Tramite implements Serializable {

    private static final long serialVersionUID = 1691007826053578876L;
    private static final DateFormat df = new SimpleDateFormat("dd/MM/yyyy kk:mm:ss");
    
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
    private DocumentoPorTipo[] documentoPorTipos;
    private Fisica persona;
    private Fisica[] personas;
    private DocumentoProbatorio[] documentosProbatorios;

    
    
    private String tramiteIdHashed;
    
    public DocumentoPorTipo[] getDocumentoPorTipos() {
        return documentoPorTipos;
    }

    public void setDocumentoPorTipos(DocumentoPorTipo[] documentoPorTipos) {
        this.documentoPorTipos = documentoPorTipos != null ? documentoPorTipos.clone() : null;
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

    public Fisica[] getPersonas() {
        return personas;
    }

    public void setPersonas(Fisica[] personas) {
        this.personas = personas != null ? personas.clone() : null;
    }

    public Date getFechaRegistroActualizacion() {
        return fechaRegistroActualizacion;
    }

    public void setFechaRegistroActualizacion(Date fechaRegistroActualizacion) {
        this.fechaRegistroActualizacion = fechaRegistroActualizacion;
    }

    public DocumentoProbatorio[] getDocumentosProbatorios() {
        return documentosProbatorios;
    }

    public void setDocumentosProbatorios(
            DocumentoProbatorio[] documentosProbatorios) {
        this.documentosProbatorios = documentosProbatorios != null ? documentosProbatorios.clone() : null;
    }
}
