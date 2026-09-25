package mx.gob.imss.ctirss.correccion.commons.vo.seguimiento.promocion.generico;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import mx.gob.imss.ctirss.correccion.framework.base.model.AbstractModel;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SegEOIncidenciasObraGenericoVO extends AbstractModel{
	/**
	 * 
	 */
	private static final long serialVersionUID = -7117351087074778677L;
	private String numeroDeRegistroDeObra;

	public String getNumeroDeRegistroDeObra() {
		return numeroDeRegistroDeObra;
	}

	public void setNumeroDeRegistroDeObra(String numeroDeRegistroDeObra) {
		this.numeroDeRegistroDeObra = numeroDeRegistroDeObra;
	}
}
