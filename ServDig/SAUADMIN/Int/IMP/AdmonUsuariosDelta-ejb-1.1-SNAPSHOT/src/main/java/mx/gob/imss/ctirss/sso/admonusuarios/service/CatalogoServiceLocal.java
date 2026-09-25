package mx.gob.imss.ctirss.sso.admonusuarios.service;

import java.util.List;

import mx.gob.imss.ctirss.admonusuarios.entidad.DicDelegacion;
import mx.gob.imss.ctirss.admonusuarios.entidad.DicSubdelegacion;
import mx.gob.imss.ctirss.admonusuarios.entidad.DicUmf;
import mx.gob.imss.ctirss.admonusuarios.entidad.SsoAccesomodulo;
import mx.gob.imss.ctirss.admonusuarios.entidad.SsoCatareanormativa;
import mx.gob.imss.ctirss.admonusuarios.entidad.SsoCatdepartamento;
import mx.gob.imss.ctirss.admonusuarios.entidad.SsoCatpuesto;
import mx.gob.imss.ctirss.sso.admonusuarios.dto.AprobadorDTO;
import mx.gob.imss.ctirss.sso.admonusuarios.dto.AreaNormativaDTO;
import mx.gob.imss.ctirss.sso.admonusuarios.dto.DelegacionDTO;
import mx.gob.imss.ctirss.sso.admonusuarios.dto.DepartamentoDTO;
import mx.gob.imss.ctirss.sso.admonusuarios.dto.ModuloDTO;
import mx.gob.imss.ctirss.sso.admonusuarios.dto.PuestoDTO;
import mx.gob.imss.ctirss.sso.admonusuarios.dto.SubdelegacionDTO;
import mx.gob.imss.ctirss.sso.admonusuarios.dto.UmfDTO;
import mx.gob.imss.ctirss.sso.admonusuarios.dto.UnidadMedicaDTO;
import mx.gob.imss.ctirss.sso.admonusuarios.dto.SolicitudDTO;
import mx.gob.imss.ctirss.admonusuarios.entities.Solicitudes;
import mx.gob.imss.ctirss.admonusuarios.entities.Puesto;
import mx.gob.imss.ctirss.admonusuarios.entities.Departamento;
import mx.gob.imss.ctirss.admonusuarios.entities.DeptoModulo;
import mx.gob.imss.ctirss.admonusuarios.entities.AreaNormativa;
import mx.gob.imss.ctirss.admonusuarios.entities.UnidadMedicaFamiliar;
import mx.gob.imss.ctirss.admonusuarios.entities.Subdelegacion;
import mx.gob.imss.ctirss.admonusuarios.entities.Delegacion;
import mx.gob.imss.ctirss.sso.admonusuarios.exceptions.AdmonUsuariosException;


public interface CatalogoServiceLocal {


	public List<DelegacionDTO> listarDelegacion() throws AdmonUsuariosException;

	public List<AreaNormativaDTO> listarAreaNormativa()throws AdmonUsuariosException;
	
	public List<AreaNormativaDTO> listarAreaNormativaPensiones()throws AdmonUsuariosException;

	public DelegacionDTO recuperarDelegacion(Long idDelegacion)throws AdmonUsuariosException;

	public List<SubdelegacionDTO> listarSubdelegacion(Long idDelegacion)throws AdmonUsuariosException ;

	public SubdelegacionDTO recuperarSubdelegacion(Long idSubdelegacion)throws AdmonUsuariosException;

	public List<UnidadMedicaDTO> listarUnidadMedica(Long idSubdelegacion)throws AdmonUsuariosException;

	public PuestoDTO consultaPerfilBD(Long perfil) throws AdmonUsuariosException;

	public List<UnidadMedicaDTO> listarUnidadMedicaBySubdelegacion(Long idSubdelegacion) throws AdmonUsuariosException;

	public UnidadMedicaDTO recuperarUnidadMedica(Long idUmf)throws AdmonUsuariosException;

	public List<ModuloDTO> listaModulo() throws AdmonUsuariosException;

	public List<AprobadorDTO> listaUsuarioFuncionario()throws AdmonUsuariosException;

	public List<DepartamentoDTO> listarDepartamentos()throws AdmonUsuariosException;

	public List<DepartamentoDTO> listarDepartamentosByAreaNormativa(Long cveAreaNormativa) throws AdmonUsuariosException;
	
	public List<DepartamentoDTO> listarDepartamentosByAreaNormativaPensiones(Long cveAreaNormativa) throws AdmonUsuariosException;

	public List<PuestoDTO> listarPuestos() throws AdmonUsuariosException;

	public List<PuestoDTO> listarPuestoByDepartamento(Long cveDepartamento)throws AdmonUsuariosException ;

	public PuestoDTO listarPuestoByClave(Long clavePuesto)throws AdmonUsuariosException ;

	public List<ModuloDTO> listarModulosByDepartamento(Long cveDepartamento)throws AdmonUsuariosException;

	public List<PuestoDTO> listarRolesRelacioados(Long idSolicitud)throws AdmonUsuariosException;

	public List<ModuloDTO> listarModulosRelacioados(Long idSolicitud) throws AdmonUsuariosException;
	
	public List<SsoCatareanormativa> obtenerAreasNormativas() throws AdmonUsuariosException;
	
	public List<SsoCatareanormativa> obtenerAreasNormativasPensiones() throws AdmonUsuariosException;
	
	public List<DicDelegacion> obtenerDelegacion() throws AdmonUsuariosException;
	
	public List<DicSubdelegacion> obtenerCatSubdelegacionByDelegacion(final Long idDelegacion)throws AdmonUsuariosException;
	
	public List<SsoCatdepartamento> obtenerCatDepartamentopByAreaNorma(final Long idAreaNorma)throws AdmonUsuariosException;
	
	public List<SsoCatdepartamento> obtenerCatDepartamentopByAreaNormaPensiones(final Long idAreaNorma)throws AdmonUsuariosException;
	
	public List<SsoCatpuesto> obtenerCatPuestoByDepartamento(final Long idDepto)throws AdmonUsuariosException;

	public List<DicUmf> obtenerCatUmfBySubdelegacion(final Long idSubdel)throws AdmonUsuariosException;
	
	public List<UmfDTO> listarUmf(Long idSubdel)throws AdmonUsuariosException;
	
	public List<SolicitudDTO> listarSolicitudes () throws AdmonUsuariosException;
	
	public List<Solicitudes> obtenerSolicitudesPendientes( )throws AdmonUsuariosException;
	
	public List<PuestoDTO> listarPuestoByDepartamentoPrincipal(String cvePuesto) throws AdmonUsuariosException;
	
	public AreaNormativaDTO listarAreaNormativaPrincipalPorClave(int clave) throws AdmonUsuariosException;
	
	public List<Puesto> obtenerPuestoByDepartamento(String cveDepartamento)throws AdmonUsuariosException;
	
	public AreaNormativa obtenerAreasNormativasPrincipalByClave(int clave)throws AdmonUsuariosException;
	
	public List<DepartamentoDTO> listarDepartamentosByAreaNormativa(String cveAreaNormativa) throws AdmonUsuariosException;
	
	public List<Departamento> obtenerDepartamentoByAreaNormativa(String cveAreaNormativa)throws AdmonUsuariosException;
	
	public List<ModuloDTO> listarModulosByDepartamento(String cveDepartamento) throws AdmonUsuariosException;
	
	public PuestoDTO listarPuestoByClave(int clavePuesto) throws AdmonUsuariosException;
	
	public List<AreaNormativaDTO> listarAreaNormativaPrincipal () throws AdmonUsuariosException;
	
	public List<AreaNormativa> obtenerAreasNormativasPrincipal()throws AdmonUsuariosException;
	
	public List<DeptoModulo> obtenerModulosByDepartamento(String cveDepartamento)throws AdmonUsuariosException;
	
	public List<SolicitudDTO> listarSolicitudesRecuperacion(int claveDelegacion, int claveSubDelegacion, int claveUMF) throws AdmonUsuariosException;
	
	public DelegacionDTO obtenerDelegacionByClave(int cveDeleg)throws AdmonUsuariosException;
	
	public SubdelegacionDTO obtenerSubDelegacionByClave(int cveSubDeleg)throws AdmonUsuariosException;
	
	public UmfDTO obtenerUmfByClave(int cveUmf)throws AdmonUsuariosException;
	
	public List<Solicitudes> obtenerSolicitudesBaja( )throws AdmonUsuariosException;
	
	public List<DepartamentoDTO> listarDepartamentoPrincipal( ) throws AdmonUsuariosException;
	
	public List<Departamento> obtenerDepartamentosPrincipal()throws AdmonUsuariosException;
	
	public List<DelegacionDTO> listarDelegacionPrincipal( ) throws AdmonUsuariosException;
	
	public List<SubdelegacionDTO> listarSubDelegacionPrincipal( ) throws AdmonUsuariosException;
	
	public List<UmfDTO> listarUMFPrincipal( ) throws AdmonUsuariosException;
	
	public List<PuestoDTO> listarPuestoPrincipal( ) throws AdmonUsuariosException;
	
	public List<Puesto> obtenerPuestosPrincipal()throws AdmonUsuariosException;
	
	public List<Delegacion> obtenerDelegacionesPrincipal()throws AdmonUsuariosException;
	
	public List<Subdelegacion> obtenerSubDelegacionesPrincipal()throws AdmonUsuariosException;
	
	public List<UnidadMedicaFamiliar> obtenerUMFsPrincipal()throws AdmonUsuariosException;
	
	public List<ModuloDTO> cargaModulosExistentes() throws AdmonUsuariosException;
	
	public List<ModuloDTO> cargaModulosRelacionados(int cveDep) throws AdmonUsuariosException;
	
	public void agregaModuloADep(int cveDep, int claveModuloAgregar) throws AdmonUsuariosException;
	
	public boolean validarDependencias(int cveDep, int claveModuloEliminar) throws AdmonUsuariosException;
	
	public void eliminaModuloADep(int cveDep, int claveModuloEliminar) throws AdmonUsuariosException;
	
	public List<SsoAccesomodulo> obtenerAccesoModulosBySolicitud(long cveSol)throws AdmonUsuariosException;
	
	public List<ModuloDTO> listarAccesoModulosByDepartamento(Long cveSol) throws AdmonUsuariosException;

	public List<DicUmf> obtenerCatHospitalesBySubdelegacion(Long idSubdel)throws AdmonUsuariosException;

	public List<UmfDTO> listarHospitales(Long idSubdel) throws AdmonUsuariosException;

	public List<DicUmf> obtenerUMFCatSinHospitalesBySubdelegacion(Long idSubdel)throws AdmonUsuariosException;

	public List<UmfDTO> listarUMFSinhospitales(Long idSubdel)throws AdmonUsuariosException;
	
}
