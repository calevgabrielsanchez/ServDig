package mx.gob.imss.digital.modelo.tramite;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import mx.gob.imss.digital.modelo.documento.probatorio.Documento;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "tipoTramite", namespace = "http://mx.gob.imss.digital.modelo.tramite")
@XmlRootElement(name = "tipoTramite", namespace = "http://mx.gob.imss.digital.modelo.tramite")
public class TipoTramite implements Serializable {

    /**
     * Serial version UID
     */
    private static final long serialVersionUID = -4893724700006526563L;
    /**
     * Identificador del tipo de tramite
     */
    private Integer idTipoTramite;
    /**
     * descripcion
     */
    private String descripcion;
    /**
     * Guia rapida
     */
    private byte[] guiaRapida;
    /**
     * guia detallada
     */
    private byte[] guiaDetallada;
    /**
     * documentacion Basica
     */
    private String documentacionBasica;
    /**
     * Fundamento legal
     */
    private String fundamentoLegal;
    /**
     * indicador de tipo de inclusion
     */
    private Integer indTipoConclusion;
    /**
     * @author Hugo Martinez
     */
    private Documento[] documentos;
    
 
	public Integer getIdTipoTramite() {
        return idTipoTramite;
    }

    public void setIdTipoTramite(final Integer idTipoTramite) {
        this.idTipoTramite = idTipoTramite;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(final String descripcion) {
        this.descripcion = descripcion;
    }

    public byte[] getGuiaRapida() {
        return guiaRapida;
    }

    public void setGuiaRapida(final byte[] guiaRapida) {
        this.guiaRapida = guiaRapida != null ? guiaRapida.clone() : null;
    }

    public byte[] getGuiaDetallada() {
        return guiaDetallada;
    }

    public void setGuiaDetallada(final byte[] guiaDetallada) {
        this.guiaDetallada = guiaDetallada != null ? guiaDetallada.clone() : null;
    }

    public String getDocumentacionBasica() {
        return documentacionBasica;
    }

    public void setDocumentacionBasica(String documentacionBasica) {
        this.documentacionBasica = documentacionBasica;
    }

    public String getFundamentoLegal() {
        return fundamentoLegal;
    }

    public void setFundamentoLegal(String fundamentoLegal) {
        this.fundamentoLegal = fundamentoLegal;
    }
    
    /**
     * 
     * @author Hugo Martinez
     * @Date 30/07/2012
     * @return
     */
    public Documento[] getDocumentos() {
		return documentos;
	}
    
    /**
     * 
     * @author Hugo Martinez
     * @Date 30/07/2012
     * @param documentos
     */
	public void setDocumentos(Documento[] documentos) {
		this.documentos = documentos != null ? documentos.clone() : null;
	}
	
	/**
	 * 
	 * @author Hugo Martinez
	 * @Date 05/10/2012
	 * @return
	 */
	public Integer getIndTipoConclusion() {
		return indTipoConclusion;
	}
	
	/**
	 * 
	 * @author Hugo Martinez
	 * @Date 05/10/2012
	 * @param indTipoConclusion
	 */
	public void setIndTipoConclusion(Integer indTipoConclusion) {
		this.indTipoConclusion = indTipoConclusion;
	}
	
	
}
