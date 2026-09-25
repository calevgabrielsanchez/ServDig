package mx.gob.imss.cit.clienteServiciosComunes.services.impl;

import java.util.ArrayList;
import java.util.List;

import mx.gob.imss.cit.clienteServiciosComunes.model.MedioContacto;
import mx.gob.imss.cit.clienteServiciosComunes.services.MediosContactoService;

public class MediosContactoServiceMockImpl implements MediosContactoService {

	private String mailNotificar;

	public List<MedioContacto> recuperaMediosContacto(String rfc) {
		List<MedioContacto> lstMediosContacto = new ArrayList<MedioContacto>();
		lstMediosContacto.add(new MedioContacto("CORREO ELECTRONICO", mailNotificar, rfc));
		lstMediosContacto.add(new MedioContacto("CORREO ELECTRONICO", mailNotificar, rfc));
		return lstMediosContacto;
	}

	public String getMailNotificar() {
		return mailNotificar;
	}

	public void setMailNotificar(String mailNotificar) {
		this.mailNotificar = mailNotificar;
	}

}
