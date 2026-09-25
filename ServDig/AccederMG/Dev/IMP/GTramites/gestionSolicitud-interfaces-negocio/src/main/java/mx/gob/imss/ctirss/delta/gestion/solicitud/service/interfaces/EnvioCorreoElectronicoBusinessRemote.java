package mx.gob.imss.ctirss.delta.gestion.solicitud.service.interfaces;

import javax.ejb.Remote;

import mx.gob.imss.ctirss.delta.model.dto.CorreoElectronicoDTO;

@Remote
public interface EnvioCorreoElectronicoBusinessRemote {
	
	void enviarCorreo(CorreoElectronicoDTO correoElectronicoDTO) throws Exception;
	
	void enviarCorreo(CorreoElectronicoDTO correoElectronicoDTO, String from) throws Exception;

}
