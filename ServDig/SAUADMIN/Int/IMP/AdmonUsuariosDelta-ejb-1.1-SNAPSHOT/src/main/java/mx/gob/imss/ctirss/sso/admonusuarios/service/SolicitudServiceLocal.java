package mx.gob.imss.ctirss.sso.admonusuarios.service;

import java.util.List;

import javax.ejb.Local;


import mx.gob.imss.ctirss.sso.admonusuarios.dto.ActivaCuentaDTO;
import mx.gob.imss.ctirss.sso.admonusuarios.dto.ModuloDTO;
import mx.gob.imss.ctirss.sso.admonusuarios.dto.PerfilDTO;
import mx.gob.imss.ctirss.sso.admonusuarios.dto.PuestoDTO;
import mx.gob.imss.ctirss.sso.admonusuarios.dto.SolicitudDTO;
import mx.gob.imss.ctirss.sso.admonusuarios.dto.UsuarioDTO;
import mx.gob.imss.ctirss.sso.admonusuarios.exceptions.AdmonUsuariosException;
import mx.gob.imss.ctirss.sso.admonusuarios.selloDigital.RespuestaFirmadoSimple;


@Local
public interface SolicitudServiceLocal {

	public List<SolicitudDTO> solicitudesByFiltro(SolicitudDTO filtro, Long idEstatus, boolean quitaNulo, String curpExcluye)  throws AdmonUsuariosException;
	
	public List<SolicitudDTO> solicitudesExternasByDepto(Long depto, Long idEstatus)  throws AdmonUsuariosException;
	
	public List<PerfilDTO> perfilesBySol(Long idSol)  throws AdmonUsuariosException;
	
	public List<ModuloDTO> modulosBySol(Long idSolicitud)  throws AdmonUsuariosException;
	
	public boolean actualizaStatusSol(SolicitudDTO solDTO, Long idStatus)  throws AdmonUsuariosException;
	
	public void borrarPerfil(PerfilDTO tabla)  throws AdmonUsuariosException;

	public void borrarModulo(ModuloDTO tabla)  throws AdmonUsuariosException;
	
	public boolean agregaPerfil(PerfilDTO p)  throws AdmonUsuariosException;
	
	public boolean agregaModulo(ModuloDTO m, Long idSol, Long idAprobador)  throws AdmonUsuariosException;
	
	public List<ModuloDTO> listarModulosByDepartamento(Long cveDepartamento)  throws AdmonUsuariosException;
	
	public List<PuestoDTO> puestosByPerfiles(List<PerfilDTO> perfiles)  throws AdmonUsuariosException;
	
	public void autorizaRechazaSolicitudBitacora(String operacion, SolicitudDTO solicitud, Long idAprobador, Long idEstatus,String correo)  throws AdmonUsuariosException;
	
	public void agregaBorraModuloPerfilBitacora(String operacion, SolicitudDTO solicitud, Long idAprobador, Long idEstatus,String correo)  throws AdmonUsuariosException;
	
	public boolean actualizaStatusModulo(ModuloDTO moduloDTO, Long idEstatus) throws AdmonUsuariosException;

	public List<SolicitudDTO> searchSolicitudes(SolicitudDTO sol,String excluyeCurp) throws AdmonUsuariosException;
	
	public List<SolicitudDTO> searchSolCurp(SolicitudDTO sol, long estatus) throws AdmonUsuariosException;
	
	public void reactivaSolicitud(SolicitudDTO sol) throws AdmonUsuariosException;

	public String getPerfilDesc(long id) throws AdmonUsuariosException;
	
	public RespuestaFirmadoSimple getSelloDigital(String cadenaOriginal,String secuenciaNotaria, String rfc);
	
	void guardaMovimientosUsuario(String idSolicitud, int estatus,String desMovimiento, List<PuestoDTO> listaRoles,List<ModuloDTO> listaModulos, Long idAprobador)throws AdmonUsuariosException;

	int registrarSolicitudCuentaNVer(UsuarioDTO usuarioSolicitud)throws AdmonUsuariosException;

	void aplicaCambiosSolicitud(SolicitudDTO sol) throws AdmonUsuariosException;

	String getModuloDesc(long id)throws AdmonUsuariosException;

	String getDeptoDesc(long id)throws AdmonUsuariosException;

	String getPuestoDesc(long id)throws AdmonUsuariosException;

	String getDelegacionDesc(long id)throws AdmonUsuariosException;

	String getSubdeleagcionDesc(long id)throws AdmonUsuariosException;

	void aplicaCambiosSolicitudLdap(SolicitudDTO sol)throws AdmonUsuariosException;

	void actualizaAreaAdsUser(SolicitudDTO sol) throws AdmonUsuariosException;



}
