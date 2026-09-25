/**
 * 
 */
package mx.gob.imss.ctirss.sso.admonusuarios.services;

import java.util.List;

import javax.ejb.Local;

import mx.gob.imss.ctirss.sso.admonusuarios.dto.ModuloDTO;
import mx.gob.imss.ctirss.sso.admonusuarios.dto.PuestoDTO;
import mx.gob.imss.ctirss.sso.admonusuarios.dto.UsuarioDTO;
import mx.gob.imss.ctirss.sso.admonusuarios.exceptions.AdmonUsuariosException;

/**
 * @author cesarAgsutin
 *
 */
@Local
public interface SolicitudCuentaSessionLocal {

	
	/**
	 * 
	 * @param usr
	 * @throws AdmonUsuariosException
	 */
	public void registrarCambioUsuarioExterno(UsuarioDTO usr) throws AdmonUsuariosException;
	
	/**
	 * 
	 * @param usuarioSolicitud
	 * @throws AdmonUsuariosException
	 */
	public void registrarNotificacionCuentaNVer(UsuarioDTO usuarioSolicitud, Long sol) throws AdmonUsuariosException;

	/**
	 * 
	 * @param idAprobador
	 * @return
	 * @throws AdmonUsuariosException
	 */
	public List<UsuarioDTO> obtenerSolicitudesAbiertas(Long idAprobador) throws AdmonUsuariosException;
	
	/**
	 * 
	 * @param idAprobador
	 * @return
	 * @throws AdmonUsuariosException
	 */
	public List<UsuarioDTO> obtenerSolicitudesPendientesPorAprobador(Long idAprobador) throws AdmonUsuariosException;

	/**
	 * 
	 * @param idSolicitud
	 * @return
	 * @throws AdmonUsuariosException
	 */
	public UsuarioDTO obtenerDetalleSolicitud(Long idSolicitud) throws AdmonUsuariosException;
	
	/**
	 * 
	 * @param idSolicitud
	 * @return
	 * @throws AdmonUsuariosException
	 */
	public UsuarioDTO obtenerTodoDetalleSolicitud(Long idSolicitud) throws AdmonUsuariosException;

	/**
	 * 
	 * @param idSolicitud
	 * @throws AdmonUsuariosException
	 */
	public void actualizarSolicitud(Long idSolicitud) throws AdmonUsuariosException;
	
	
	/**
	 * 
	 * @param idSolicitud
	 * @throws AdmonUsuariosException
	 */
	public void actualizarSolicitudBD(Long idSolicitud, UsuarioDTO usuario) throws AdmonUsuariosException;
	
	/**
	 * 
	 * @param curp
	 * @throws AdmonUsuariosException
	 */
	public String buscaCurpExistente(String curp) throws AdmonUsuariosException;
	
	/**
	 * 
	 * @param curp
	 * @throws AdmonUsuariosException
	 */
	public String buscaEstatusSolicitud(String curp) throws AdmonUsuariosException;
	
	/**
	 * 
	 * @param curp
	 * @throws AdmonUsuariosException
	 */
	public Long obtenerIDSolicitud(String curp) throws AdmonUsuariosException;
	
	/**
	 * 
	 * @param curp
	 * @throws AdmonUsuariosException
	 */
	public Long buscaSolByCurp(String curp) throws AdmonUsuariosException;

	/**
	 * 
	 * @param curp
	 * @throws AdmonUsuariosException
	 */
	public void registrarPerfiles(long idSolicitud, int cvePuesto, String defaultRol) throws AdmonUsuariosException;
	
	/**
	 * 
	 * @param idSolicitud, cveDeptoMod, status, idAprobador
	 * @throws AdmonUsuariosException
	 */
	public void registrarModulos(Long idSolicitud, int cveDepto, int cveMod, int status) throws AdmonUsuariosException;
	
	
	/**
	 * 
	 * @param curp
	 * @throws AdmonUsuariosException
	 */
	public void eliminarPerfiles(Long idSolicitud, int cvePuesto) throws AdmonUsuariosException;
	
	/**
	 * 
	 * @param idSolicitud, cveDeptoMod, status, idAprobador
	 * @throws AdmonUsuariosException
	 */
	public void eliminarModulos(Long idSolicitud, int cveDepto, int cveMod) throws AdmonUsuariosException;
	
	/**
	 * 
	 * @param idSolicitud, cveDeptoMod, status, idAprobador
	 * @throws AdmonUsuariosException
	 */
	public void eliminarAprobador(Long idSolicitud, int cveDepto, int cveMod) throws AdmonUsuariosException;
	
	/**
	 * 
	 * @param curp
	 * @throws AdmonUsuariosException
	 */
	public String obtenerDatosUsuarioEnLDAP(String curp) throws AdmonUsuariosException;
	
	/**
	 * 
	 * @param idSolicitud, usuario
	 * @throws AdmonUsuariosException
	 */
	public void eliminaPerfilesModulosBD(Long idSolicitud, UsuarioDTO usuario) throws AdmonUsuariosException;
	
	/**
	 * 
	 * @param idSolicitud, usuario
	 * @throws AdmonUsuariosException
	 */
	public void actualizarSolicitudAprobadaBD(Long idSolicitud, UsuarioDTO usuario) throws AdmonUsuariosException;
	
	
	/**
	 * 
	 * @param idSolicitud, usuario
	 * @throws AdmonUsuariosException
	 */
	public void actualizarSolicitudRechazadaBD(Long idSolicitud, UsuarioDTO usuario) throws AdmonUsuariosException;
	
	/**
	 * 
	 * @param idSolicitud, usuario
	 * @throws AdmonUsuariosException
	 */
	public void actualizarSolicitudBDEliminacion(Long idSolicitud, UsuarioDTO usuario,int tipoBaja) throws AdmonUsuariosException;
	

	/**
	 * 
	 * @param idSolicitud, delegacion, subdelegacion, umf
	 * @throws AdmonUsuariosException
	 */
	public void actualizarSolicitudBDRecuperacion(Long idSolicitud, int delegacion, int subdelegacion, int umf) throws AdmonUsuariosException ;
	
	
	
	public void eliminarModulosId(int cveMod) throws AdmonUsuariosException;
	
}
