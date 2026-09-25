package mx.gob.imss.ctirss.delta.model.gestion.patronal;

import mx.gob.imss.ctirss.delta.framework.base.model.AbstractModel;

public class TipoContacto extends AbstractModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 9110298116501843169L;

	private Long cveIdTipoContacto;
	private String desTipoContacto;

	public Long getCveIdTipoContacto() {
		return cveIdTipoContacto;
	}

	public void setCveIdTipoContacto(Long cveIdTipoContacto) {
		this.cveIdTipoContacto = cveIdTipoContacto;
	}

	public String getDesTipoContacto() {
		return desTipoContacto;
	}

	public void setDesTipoContacto(String desTipoContacto) {
		this.desTipoContacto = desTipoContacto;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("TipoContacto [cveIdTipoContacto=");
		builder.append(cveIdTipoContacto);
		builder.append(", desTipoContacto=");
		builder.append(desTipoContacto);
		builder.append("]");
		return builder.toString();
	}

}
