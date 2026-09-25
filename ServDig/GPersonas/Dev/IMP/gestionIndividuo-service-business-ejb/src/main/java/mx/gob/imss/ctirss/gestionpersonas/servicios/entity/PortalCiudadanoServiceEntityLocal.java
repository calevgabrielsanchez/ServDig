package mx.gob.imss.ctirss.gestionpersonas.servicios.entity;

import java.util.Date;
import java.util.List;

import javax.ejb.Local;

import mx.gob.imss.ctirss.delta.persistence.DitCiudadanoCurpCorreo;

@Local
public interface PortalCiudadanoServiceEntityLocal {

	List<DitCiudadanoCurpCorreo> obtenerCiudadanoPorCorreo(String correo);
	
	void actualizarCurpACorreo(String correo, String curp);
	
	DitCiudadanoCurpCorreo guardarCiudadano(DitCiudadanoCurpCorreo ditCiudadano);
	
	DitCiudadanoCurpCorreo getCiudadanoById(Long id);
	
	/**
	 * Obtiene las asociaciones de correo vs curp que tengan una fecha de registro anterior a la fecha limite (fechaLimite)
	 * @param correo
	 * @param fechaLimite
	 * @return
	 */
	public List<DitCiudadanoCurpCorreo> obtenerCiudadanoPorCorreoYFechaLimite(String correo, Date fechaLimite);
}
