package mx.gob.imss.ctirss.delta.gestion.patronal.web.controller.paginator;

import mx.gob.imss.ctirss.delta.framework.paginador.model.WrapperDataTable;
import mx.gob.imss.ctirss.delta.model.gestion.medio.contacto.MedioContacto;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.Socio;

public class MedioContactoDataTable extends WrapperDataTable<MedioContacto> {
	
	private Socio socio;

	public Socio getSocio() {
		return socio;
	}

	public void setSocio(Socio socio) {
		this.socio = socio;
	}

}
