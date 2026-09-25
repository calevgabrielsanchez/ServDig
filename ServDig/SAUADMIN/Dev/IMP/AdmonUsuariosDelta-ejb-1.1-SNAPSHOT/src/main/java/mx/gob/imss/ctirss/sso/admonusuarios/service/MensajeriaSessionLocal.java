package mx.gob.imss.ctirss.sso.admonusuarios.service;

import javax.ejb.Local;

import mx.gob.imss.ctirss.sso.admonusuarios.dto.SolicitudDTO;
import mx.gob.imss.ctirss.sso.admonusuarios.dto.UsuarioDTO;
import mx.gob.imss.ctirss.sso.admonusuarios.exceptions.AdmonUsuariosException;

@Local
public interface MensajeriaSessionLocal {
	
	

	/**
	 * 
	 * @param origen
	 * @param destino
	 * @param mensaje
	 * @param datosSolicitud TODO
	 * @return
	 * @throws AdmonUsuariosException
	 */
	public Boolean enviarCorreo(String origen, String destino, String mensaje, String titulo, SolicitudDTO datosSolicitud,String tipoAcuse) throws AdmonUsuariosException;

	public Boolean enviarCorreoContrasena(String origen, String destino,UsuarioDTO user, String contraseña) throws AdmonUsuariosException;

	public Boolean enviarCorreoConconfirmacion(String origen, String destino,String mensaje, String titulo, SolicitudDTO datosSolicitud,String tipoAcuse, String link) throws AdmonUsuariosException;

	public Boolean enviarCorreoAprobador(String origen, String destino,String mensaje, String titulo, SolicitudDTO datosSolicitud,String tipoAcuse) throws AdmonUsuariosException;

	public Boolean enviarCorreoConconfirmacionAprobador(String origen, String destino,String mensaje, String titulo, SolicitudDTO datosSolicitud,String tipoAcuse, String link) throws AdmonUsuariosException;

}
