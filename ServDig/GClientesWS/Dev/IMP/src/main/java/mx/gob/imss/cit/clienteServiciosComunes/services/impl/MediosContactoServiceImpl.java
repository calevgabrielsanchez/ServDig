package mx.gob.imss.cit.clienteServiciosComunes.services.impl;

import java.util.ArrayList;
import java.util.List;

import mx.gob.imss.cit.clienteServiciosComunes.mediosContacto.model.MedContactoRepreLegalDTO;
import mx.gob.imss.cit.clienteServiciosComunes.mediosContacto.ws.ArrayOfMedContactoRepreLegalDTOLiteral;
import mx.gob.imss.cit.clienteServiciosComunes.mediosContacto.ws.IMediosContactoWSService;
import mx.gob.imss.cit.clienteServiciosComunes.model.MedioContacto;
import mx.gob.imss.cit.clienteServiciosComunes.services.MediosContactoService;

public class MediosContactoServiceImpl implements MediosContactoService {

	IMediosContactoWSService iMediosContactoWSService;

	public List<MedioContacto> recuperaMediosContacto(String rfc) {
		List<MedioContacto> lstMediosContacto = null;
		ArrayOfMedContactoRepreLegalDTOLiteral arrayOfMedContactoRepreLegalDTOLiteral = iMediosContactoWSService
				.recuperaMediosContacto(rfc);
		MedioContacto medioContacto = null;
		if (arrayOfMedContactoRepreLegalDTOLiteral != null
				&& arrayOfMedContactoRepreLegalDTOLiteral.getMedContactoRepreLegalDTO() != null
				&& !arrayOfMedContactoRepreLegalDTOLiteral.getMedContactoRepreLegalDTO().isEmpty()) {
			lstMediosContacto = new ArrayList<MedioContacto>();
			for (MedContactoRepreLegalDTO medContactoRepreLegalDTO : arrayOfMedContactoRepreLegalDTOLiteral
					.getMedContactoRepreLegalDTO()) {
				medioContacto = new MedioContacto(medContactoRepreLegalDTO.getTipoContacto(),
						medContactoRepreLegalDTO.getDesFormaContacto(), medContactoRepreLegalDTO.getRfc());
				lstMediosContacto.add(medioContacto);
			}
		}
		return lstMediosContacto;
	}

	public IMediosContactoWSService getiMediosContactoWSService() {
		return iMediosContactoWSService;
	}

	public void setiMediosContactoWSService(IMediosContactoWSService iMediosContactoWSService) {
		this.iMediosContactoWSService = iMediosContactoWSService;
	}

}
