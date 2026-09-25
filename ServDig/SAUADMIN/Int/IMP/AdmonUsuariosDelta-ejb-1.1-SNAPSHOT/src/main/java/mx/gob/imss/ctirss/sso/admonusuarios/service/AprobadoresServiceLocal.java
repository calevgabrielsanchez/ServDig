package mx.gob.imss.ctirss.sso.admonusuarios.service;

import java.util.List;

import mx.gob.imss.ctirss.sso.admonusuarios.dto.AprobadorDTO;
import mx.gob.imss.ctirss.sso.admonusuarios.dto.SolicitudDTO;
import mx.gob.imss.ctirss.sso.admonusuarios.exceptions.AdmonUsuariosException;

public interface AprobadoresServiceLocal {

	List<AprobadorDTO> consultaAprobadoresBySolicitud(List<SolicitudDTO> solicitudes) throws AdmonUsuariosException;

	void guarda(List<AprobadorDTO> solicitudes, List<AprobadorDTO> aprobadores) throws AdmonUsuariosException;

	AprobadorDTO validaAprobadorByCurp(String curp) throws AdmonUsuariosException;

	void addAprobador(AprobadorDTO ap) throws AdmonUsuariosException;

	void addAprobadorDirDpes(AprobadorDTO ap, Long tipoAprobador) throws AdmonUsuariosException;

	void delAprobador(AprobadorDTO ap) throws AdmonUsuariosException;

	void bajaAprobador(String curp) throws AdmonUsuariosException;

}
