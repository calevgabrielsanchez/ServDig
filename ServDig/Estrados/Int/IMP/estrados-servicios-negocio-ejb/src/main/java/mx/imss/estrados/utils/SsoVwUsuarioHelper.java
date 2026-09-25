package mx.imss.estrados.utils;

import mx.imss.estrados.dto.SsoVwUsuarioDTO;
import mx.imss.estrados.entity.SsoVwUsuario;

public class SsoVwUsuarioHelper {
	
	/**
	 * Metodo para settear la información de la entidad SsoVwUsuario a el objeto SsoVwUsuarioDTO
	 * 
	 * @param NeeCatTipoAdjunto
	 * @return SsoVwUsuarioDTO
	 */
	public SsoVwUsuarioDTO setterSsoVwUsuarioEntityToSsoVwUsuarioDTO(SsoVwUsuario ssoVwUsuario) {
		SsoVwUsuarioDTO ssoVwUsuarioDTO = new SsoVwUsuarioDTO();
		System.out.println("Entra 0.1 CURP:");
		ssoVwUsuarioDTO.setCveSsoAreaNorma(ssoVwUsuario.getCveSsoAreaNorma());
		ssoVwUsuarioDTO.setDesAreaNorma(ssoVwUsuario.getDesAreaNorma());
		ssoVwUsuarioDTO.setCveDelegacion(ssoVwUsuario.getCveDelegacion());
		ssoVwUsuarioDTO.setDesDelegacion(ssoVwUsuario.getDesDelegacion());
		ssoVwUsuarioDTO.setCveSubdelegacion(ssoVwUsuario.getCveSubdelegacion());
		ssoVwUsuarioDTO.setDesSubdelegacion(ssoVwUsuario.getDesSubdelegacion());
		ssoVwUsuarioDTO.setCveSSODepto(ssoVwUsuario.getCveSSODepto());
		ssoVwUsuarioDTO.setCveSSOPuesto(ssoVwUsuario.getCveSSOPuesto());
		ssoVwUsuarioDTO.setDesDepartamento(ssoVwUsuario.getDesDepartamento());
		ssoVwUsuarioDTO.setCveSSOPuesto(ssoVwUsuario.getCveSSOPuesto());
		ssoVwUsuarioDTO.setDesPuesto(ssoVwUsuario.getDesPuesto());
		ssoVwUsuarioDTO.setCveSSOEstatus(ssoVwUsuario.getCveSSOEstatus());
		ssoVwUsuarioDTO.setDesEstatus(ssoVwUsuario.getDesEstatus());
		ssoVwUsuarioDTO.setDesUsrCURP(ssoVwUsuario.getDesUsrCURP());
		ssoVwUsuarioDTO.setNomNombre(ssoVwUsuario.getNomNombre());
		ssoVwUsuarioDTO.setNomPaterno(ssoVwUsuario.getNomPaterno());
		ssoVwUsuarioDTO.setNomMaterno(ssoVwUsuario.getNomMaterno());
		ssoVwUsuarioDTO.setRefCorreoElectronico(ssoVwUsuario.getRefCorreoElectronico());
		ssoVwUsuarioDTO.setCveIdDelegacion(ssoVwUsuario.getCveIdDelegacion());
		ssoVwUsuarioDTO.setCveIdSubdelegacion(ssoVwUsuario.getCveIdSubdelegacion());
		ssoVwUsuarioDTO.setDesClavePresupuestal(ssoVwUsuario.getDesClavePresupuestal());
		
		return ssoVwUsuarioDTO;
	}

}
