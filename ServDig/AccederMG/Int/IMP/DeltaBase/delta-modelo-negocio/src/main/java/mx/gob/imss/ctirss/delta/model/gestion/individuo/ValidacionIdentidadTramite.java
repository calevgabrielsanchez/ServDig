package mx.gob.imss.ctirss.delta.model.gestion.individuo;

import java.util.List;

import mx.gob.imss.ctirss.delta.framework.base.model.AbstractModel;

public class ValidacionIdentidadTramite extends AbstractModel {

	private static final long serialVersionUID = 1L;

	private boolean isValida;
	private List<ErrorValidacionIdentidadTramite> errores;

	public boolean isValida() {
		return isValida;
	}

	public void setValida(boolean isValida) {
		this.isValida = isValida;
	}

	public List<ErrorValidacionIdentidadTramite> getErrores() {
		return errores;
	}

	public void setErrores(List<ErrorValidacionIdentidadTramite> errores) {
		this.errores = errores;
	}
}
