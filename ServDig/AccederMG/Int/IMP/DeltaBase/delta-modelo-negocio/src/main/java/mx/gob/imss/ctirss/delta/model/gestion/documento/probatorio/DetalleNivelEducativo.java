package mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

import mx.gob.imss.ctirss.delta.framework.base.model.AbstractModel;

@XmlRootElement(name = "DetalleNivelEducativo")
public class DetalleNivelEducativo extends AbstractModel implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6114148699008262331L;
	
	private Long idDetalleNivelEducativo;
	
	//Detalle nivel estudios
    private NivelEducativo nivelEducativo;
    //nivel estudios
    private TipoNivelEducativo tipoNivelEducativo;

	public Long getIdDetalleNivelEducativo() {
		return idDetalleNivelEducativo;
	}

	public void setIdDetalleNivelEducativo(Long idDetalleNivelEducativo) {
		this.idDetalleNivelEducativo = idDetalleNivelEducativo;
	}

	public NivelEducativo getNivelEducativo() {
		return nivelEducativo;
	}

	public void setNivelEducativo(NivelEducativo nivelEducativo) {
		this.nivelEducativo = nivelEducativo;
	}

	public TipoNivelEducativo getTipoNivelEducativo() {
		return tipoNivelEducativo;
	}

	public void setTipoNivelEducativo(TipoNivelEducativo tipoNivelEducativo) {
		this.tipoNivelEducativo = tipoNivelEducativo;
	}
    
    
	
}
