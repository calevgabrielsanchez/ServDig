/**
 * 
 */
package mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio;

import java.io.Serializable;
import java.util.List;

import mx.gob.imss.ctirss.delta.framework.base.model.AbstractModel;

/**
 * @author Victor
 *
 */
public class TipoNivelEducativo extends AbstractModel implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -6142623468236215042L;
	private Long idNivelEducativo;
	private String desNivelEducativo;
	private List<NivelEducativo> nivelEducativos;
	public Long getIdNivelEducativo() {
		return idNivelEducativo;
	}
	public void setIdNivelEducativo(Long idNivelEducativo) {
		this.idNivelEducativo = idNivelEducativo;
	}
	public String getDesNivelEducativo() {
		return desNivelEducativo;
	}
	public void setDesNivelEducativo(String desNivelEducativo) {
		this.desNivelEducativo = desNivelEducativo;
	}
	public List<NivelEducativo> getNivelEducativos() {
		return nivelEducativos;
	}
	public void setNivelEducativos(List<NivelEducativo> nivelEducativos) {
		this.nivelEducativos = nivelEducativos;
	}
	
	
	
}
