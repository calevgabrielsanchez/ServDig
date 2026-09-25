package mx.gob.imss.ctirss.delta.gestion.individuo.service.entity;

import javax.ejb.Local;

import mx.gob.imss.ctirss.delta.model.Usuario;
import mx.gob.imss.ctirss.delta.persistence.DitPerPortalCiudadano;

@Local
public interface UsuarioPortalEntityLocal {

	void insertarDatosCuenta(Usuario usuario, Long idSolicitud);
	DitPerPortalCiudadano recuperaPersona(Long cveIdPersona);
}
