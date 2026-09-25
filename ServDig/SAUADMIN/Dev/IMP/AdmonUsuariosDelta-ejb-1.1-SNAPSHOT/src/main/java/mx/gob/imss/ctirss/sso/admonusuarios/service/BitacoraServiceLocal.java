package mx.gob.imss.ctirss.sso.admonusuarios.service;

import javax.ejb.Local;

import mx.gob.imss.ctirss.admonusuarios.entidad.SsoAprobador;
import mx.gob.imss.ctirss.admonusuarios.entidad.SsoSolicitud;
import mx.gob.imss.ctirss.sso.admonusuarios.exceptions.AdmonUsuariosException;
import mx.gob.imss.ctirss.sso.admonusuarios.siap.modelo.UsuarioNominaResponse;
import mx.gob.imss.ctirss.sso.admonusuarios.ttds.modelo.UsuarioTTD;

@Local
public interface BitacoraServiceLocal {

	public void guardaBitacora(String operacion, Long idSol, String descripcion,
			Long idAprobador, Long idEstatus);

	void guardaSolicitudBit(Long idSol, Long idAprobador, int tpMov)throws AdmonUsuariosException;

	void guardaPuestoBit(Long idSol, Long idAprobador, Long idPuesto,boolean add) throws AdmonUsuariosException;

	void guardaModulosBit(Long idSol, Long idAprobador, Long idModulo,boolean add) throws AdmonUsuariosException;

	void guardaAprobadorBit(Long idSol, Long idAprobador,boolean add) throws AdmonUsuariosException;

	void guardaModulosBitSolicitud(SsoSolicitud sol, Long idAprobador,Long idModulo, boolean add) throws AdmonUsuariosException;

	void guardaPuestoBitSolicitud(SsoSolicitud sol, Long idAprobador,Long idPuesto, boolean add) throws AdmonUsuariosException;

	void guardaSolicitudBitSolicitud(SsoSolicitud sol, Long idAprobador,int tpMov) throws AdmonUsuariosException;

	void guardaLoginAprobadorBit(Long idAprobador)throws AdmonUsuariosException;

	void guardaSolicitudBitActivacion(Long solId)throws AdmonUsuariosException;
	
	SsoAprobador obtenDatosSession(Long idAprobador);
	
	UsuarioNominaResponse datosSIAP(String curp, String matricula);
	
	UsuarioTTD datosTTD(String curp, String matricula);
	
}
