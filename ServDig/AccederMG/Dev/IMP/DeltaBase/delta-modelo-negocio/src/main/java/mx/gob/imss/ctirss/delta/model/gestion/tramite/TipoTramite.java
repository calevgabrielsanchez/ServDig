package mx.gob.imss.ctirss.delta.model.gestion.tramite;

import java.io.Serializable;
import java.util.List;

import mx.gob.imss.ctirss.delta.framework.base.model.AbstractModel;
import mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio.Documento;

public class TipoTramite extends AbstractModel implements Serializable {

    private static final long serialVersionUID = -4893724700006526563L;
    private Integer idTipoTramite;
    private String descripcion;
    private String homoclave;
    private byte[] guiaRapida;
    private byte[] guiaDetallada;
    private String documentacionBasica;
    private String fundamentoLegal;
    private Integer indTipoConclusion;
    /**
     * @author Hugo Martinez
     */
    private List<Documento> documentos;
    
    
    public TipoTramite(){
    	
    }
    
    public TipoTramite(Integer idTipoTramite){
    	this.idTipoTramite = idTipoTramite;
    }
 
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
    public List<Documento> getDocumentos() {
		return documentos;
	}
    
    /**
     * 
     * @author Hugo Martinez
     * @Date 30/07/2012
     * @param documentos
     */
	public void setDocumentos(List<Documento> documentos) {
		this.documentos = documentos;
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

	public String getHomoclave() {
		return homoclave;
	}

	public void setHomoclave(String homoclave) {
		this.homoclave = homoclave;
	}
		
}
