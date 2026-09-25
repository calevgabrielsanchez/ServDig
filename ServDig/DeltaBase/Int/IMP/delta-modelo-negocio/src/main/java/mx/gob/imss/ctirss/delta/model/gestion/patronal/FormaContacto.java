package mx.gob.imss.ctirss.delta.model.gestion.patronal;

import mx.gob.imss.ctirss.delta.framework.base.model.AbstractModel;

public class FormaContacto extends AbstractModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1095370615671246528L;

	private Long cveIdFormaContacto;
	private String desFormaContacto;
	private TipoContacto tipoContacto;

	public Long getCveIdFormaContacto() {
		return cveIdFormaContacto;
	}

	public void setCveIdFormaContacto(Long cveIdFormaContacto) {
		this.cveIdFormaContacto = cveIdFormaContacto;
	}

	public String getDesFormaContacto() {
		return desFormaContacto;
	}

	public void setDesFormaContacto(String desFormaContacto) {
		this.desFormaContacto = desFormaContacto;
	}

	public TipoContacto getTipoContacto() {
		return tipoContacto;
	}

	public void setTipoContacto(TipoContacto tipoContacto) {
		this.tipoContacto = tipoContacto;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("FormaContacto [cveIdFormaContacto=");
		builder.append(cveIdFormaContacto);
		builder.append(", desFormaContacto=");
		builder.append(desFormaContacto);
		builder.append(", tipoContacto=");
		builder.append(tipoContacto);
		builder.append("]");
		return builder.toString();
	}

}
