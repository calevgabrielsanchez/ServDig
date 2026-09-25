package mx.gob.imss.ctirss.sso.admonusuarios.services;

import java.util.List;

import javax.ejb.Local;

import mx.gob.imss.ctirss.admonusuarios.entities.AreaNormativa;
import mx.gob.imss.ctirss.admonusuarios.entities.Delegacion;
import mx.gob.imss.ctirss.admonusuarios.entities.Departamento;
import mx.gob.imss.ctirss.admonusuarios.entities.DeptoModulo;
import mx.gob.imss.ctirss.admonusuarios.entities.Modulo;
import mx.gob.imss.ctirss.admonusuarios.entities.Puesto;
import mx.gob.imss.ctirss.admonusuarios.entities.Solicitudes;
import mx.gob.imss.ctirss.admonusuarios.entities.Subdelegacion;
import mx.gob.imss.ctirss.admonusuarios.entities.UnidadMedicaFamiliar;
import mx.gob.imss.ctirss.admonusuarios.entities.UsuarioFuncionario;
import mx.gob.imss.ctirss.sso.admonusuarios.dto.AreaNormativaDTO;
import mx.gob.imss.ctirss.sso.admonusuarios.dto.DelegacionDTO;
import mx.gob.imss.ctirss.sso.admonusuarios.dto.DepartamentoDTO;
import mx.gob.imss.ctirss.sso.admonusuarios.dto.ModuloDTO;
import mx.gob.imss.ctirss.sso.admonusuarios.dto.PuestoDTO;
import mx.gob.imss.ctirss.sso.admonusuarios.dto.SolicitudDTO;
import mx.gob.imss.ctirss.sso.admonusuarios.dto.SubdelegacionDTO;
import mx.gob.imss.ctirss.sso.admonusuarios.dto.UnidadMedicaDTO;
import mx.gob.imss.ctirss.sso.admonusuarios.exceptions.AdmonUsuariosException;

/**
 * Interfaz local del servicio de administración de roles. En esta interfaz se exponen servicios que serán consumidos por otros EJBs.
 * @author Guillermo Vilchis Gonzalez
 * @version 1.0
 */
@Local
public interface CatalogosSessionLocal {

	     /**
	     * Método para obtener todos las delegaciones disponibles en la tabla DIC_DELEGACIONES
	     * @return lista Delegacion
	     * @throws AdmonUsuariosException en caso de errores al obtener las delegaciones
	     */
	 List<Delegacion> obtenerDelegacion() throws AdmonUsuariosException;
	     /**
		 * Método para obtener la delegacion de la tabla DIC_DELEGACIONES por medio del ID de la delegacion
		 * @return objeto Delegacion
		 * @throws AdmonUsuariosException en caso de errores al obtener la delegacion
		 */
	 Delegacion obtenerDelegacionByID(final String idDelegacion) throws AdmonUsuariosException;
	     /**
		 * Método para obtener todos las subdelegaciones disponibles en la tabla DIC_SUBDELEGACIONES
		 * @return lista Subdelegacion
		 * @throws AdmonUsuariosException en caso de errores al obtener las subdelegaciones
		 */
	 List<Subdelegacion> obtenerSubdelegacion(final String idDelegacion) throws AdmonUsuariosException;
	     /**
		 * Método para obtener la subdelegacion de la tabla DIC_SUBDELEGACIONES por medio del ID de la subdelegacion
		 * @return objeto Subdelegacion
		 * @throws AdmonUsuariosException en caso de errores al obtener la subdelegacion
		 */
	 Subdelegacion obtenerSubdelegacionByID(final String idSubdelegacion) throws AdmonUsuariosException;
	     /**
		 * Método para obtener todos las subdelegaciones disponibles en la tabla DIC_UMF
		 * @return lista UnidadMedicaFamiliar
		 * @throws AdmonUsuariosException en caso de errores al obtener las unidadMedica
		 */
	 List<UnidadMedicaFamiliar> obtenerUnidadMedica(final String idSubdelegacion) throws AdmonUsuariosException;
	     /**
		 * Método para obtener la unidadMedica de la tabla DIC_UMF por medio del ID de la unidadMedicaFamiliar
		 * @return objeto UnidadMedicaFamiliar
		 * @throws AdmonUsuariosException en caso de errores al obtener la unidadMedica
		 */
	 UnidadMedicaFamiliar obtenerUnidadMedicaByID(final String idUmf) throws AdmonUsuariosException;
     /**
	 * Método para obtener todos las subdelegaciones disponibles en la tabla DIC_UMF
	 * @return lista UnidadMedicaFamiliar
	 * @throws AdmonUsuariosException en caso de errores al obtener las unidadMedica
	 */
	 List<AreaNormativa> obtenerAreasNormativas() throws AdmonUsuariosException;
	 
     /**
	 * Método para obtener todos los departamentos disponibles en la tabla SSO_CATDEPARTAMENTO
	 * @return lista Departamento
	 * @throws AdmonUsuariosException en caso de errores al obtener las unidadMedica
	 */
	 List<Departamento> obtenerDepartamentos() throws AdmonUsuariosException;
     /**
	 * Método para obtener todos los departamentos disponibles en la tabla SSO_CATDEPARTAMENTO
	 * @return lista Departamento
	 * @throws AdmonUsuariosException en caso de errores al obtener las unidadMedica
	 */
	 List<Departamento> obtenerDepartamentoByAreaNormativa(String cveAreaNormativa) throws AdmonUsuariosException;
	 
	 /**
	 * Método para obtener todas las solicitudes pendientes
	 * @return lista Solicitudes
	 * @throws AdmonUsuariosException en caso de errores al obtener las solicitudes
	 */
	 public List<Solicitudes> obtenerSolicitudesPendientes()throws AdmonUsuariosException;
	 
	 /**
		 * Método para obtener todas las solicitudes pendientes en DTO
		 * @return lista Solicitudes
		 * @throws AdmonUsuariosException en caso de errores al obtener las solicitudes
		 */
	 public List<SolicitudDTO> listarSolicitudes () throws AdmonUsuariosException;
	 /**
	  * 
	  * @return
	  * @throws AdmonUsuariosException
	  */
	 List<Modulo> obtenerModulo() throws AdmonUsuariosException;
	 
	 /**
	  * 
	  * @return
	  * @throws AdmonUsuariosException
	  */
	 List<UsuarioFuncionario> obtenerUsuarioFuncionario() throws AdmonUsuariosException;
	 
     /**
	 * Método para obtener todos los departamentos disponibles en la tabla SSO_CATDEPARTAMENTO
	 * @return lista Departamento
	 * @throws AdmonUsuariosException en caso de errores al obtener los funcionarios
	 */
	 List<Puesto> obtenerPuestos() throws AdmonUsuariosException;
     /**
	 * Método para obtener todos los departamentos disponibles en la tabla SSO_CATDEPARTAMENTO
	 * @return lista Departamento
	 * @throws AdmonUsuariosException en caso de errores al obtener los puestos
	 */
	 List<Puesto> obtenerPuestoByDepartamento(String cveDepartamenti) throws AdmonUsuariosException;

     /**
	 * Método para obtener todos los departamentos disponibles en la tabla SSO_CATDEPTOMODULO
	 * @return lista Departamento
	 * @throws AdmonUsuariosException en caso de errores al obtener los modulos
	 */
	 List<DeptoModulo> obtenerModulosByDepartamento(String cveDepartamento) throws AdmonUsuariosException;

     /**
	 * Método para obtener todas las UMD pod departamentos disponibles en la tabla DICUMF
	 * @return lista UMF
	 * @throws AdmonUsuariosException en caso de errores al obtener los modulos
	 */
	 List<UnidadMedicaFamiliar> obtenerUnidadMedicaBySubdelegacion(String cveIdDepartamento) throws AdmonUsuariosException;
	 
	 List<DelegacionDTO> listarDelegacion() throws AdmonUsuariosException;
	List<AreaNormativaDTO> listarAreaNormativa() throws AdmonUsuariosException;
	List<SolicitudDTO> listarSolicitudesGeneradas()throws AdmonUsuariosException;
	List<DepartamentoDTO> listarDepartamentos() throws AdmonUsuariosException;
	List<DepartamentoDTO> listarDepartamentosByAreaNormativa(String cveAreaNormativa) throws AdmonUsuariosException;
	List<PuestoDTO> listarPuestos() throws AdmonUsuariosException;
	List<PuestoDTO> listarPuestoByDepartamento(String cvePuesto)throws AdmonUsuariosException;
	PuestoDTO listarPuestoByClave(int clavePuesto)throws AdmonUsuariosException;
	List<ModuloDTO> listarModulosByDepartamento(String cveDepartamento)throws AdmonUsuariosException;
	List<SubdelegacionDTO> listarSubdelegacion(String idDelegacion)throws AdmonUsuariosException;
	List<UnidadMedicaDTO> listarUnidadMedica(String idSubdelegacion)throws AdmonUsuariosException;
	PuestoDTO consultaPerfilBD(String perfil) throws AdmonUsuariosException;
	List<UnidadMedicaDTO> listarUnidadMedicaBySubdelegacion(String cveIdSubdelegacion) throws AdmonUsuariosException;
	DelegacionDTO recuperarDelegacion(String idDelegacion)throws AdmonUsuariosException;
	SubdelegacionDTO recuperarSubdelegacion(String idSubdelegacion)throws AdmonUsuariosException;
	UnidadMedicaDTO recuperarUnidadMedica(String idUmf)throws AdmonUsuariosException;
	List<ModuloDTO> listaModulo() throws AdmonUsuariosException;
	List<PuestoDTO> listarRolesRelacioados(Long idSolicitud)throws AdmonUsuariosException;
	List<ModuloDTO> listarModulosRelacioados(Long idSolicitud)throws AdmonUsuariosException;
}
