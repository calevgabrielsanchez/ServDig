package mx.gob.imss.cit.dacvass.servicios.externos.service.interfaces;

import java.util.List;

import javax.ejb.Remote;

import mx.gob.imss.cit.dacvass.servicios.externos.model.general.catalogos.MunicipioImss;
import mx.gob.imss.cit.dacvass.servicios.externos.model.general.catalogos.MunicipioInegi;
import mx.gob.imss.cit.dacvass.servicios.externos.model.general.catalogos.catComun.CatComunDelegacion;
import mx.gob.imss.cit.dacvass.servicios.externos.model.general.catalogos.catComun.CatComunEntidadFederativa;
import mx.gob.imss.cit.dacvass.servicios.externos.model.general.catalogos.catComun.CatComunUmf;
import mx.gob.imss.cit.dacvass.servicios.externos.model.general.exception.ServiciosRestException;
import mx.gob.imss.cit.dacvass.servicios.externos.model.identidad.dto.EntidadFederativa;
import mx.gob.imss.cit.dacvass.servicios.externos.model.identidad.dto.EstadoCivil;
import mx.gob.imss.cit.dacvass.servicios.externos.model.identidad.dto.Pais;
import mx.gob.imss.cit.dacvass.servicios.externos.model.identidad.dto.Parentesco;
import mx.gob.imss.cit.dacvass.servicios.externos.model.identidad.dto.Sexo;
import mx.gob.imss.cit.dacvass.servicios.externos.model.identidad.dto.Turno;
import mx.gob.imss.ctirss.delta.model.derechohabiente.UnidadMedicaFamiliar;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.TipoPersona;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.Clase;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.Division;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.Fraccion;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.Grupo;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.Modalidad;
import mx.gob.imss.digital.modelo.domicilio.Delegacion;
import mx.gob.imss.digital.modelo.domicilio.Subdelegacion;



@Remote
public interface ICatalogosServiciosDigitalesServiceRemote {
	
	Sexo getCatalogoSexo(Long idCatalogo) throws ServiciosRestException;
	List<Sexo> getCatalogoSexo()  throws ServiciosRestException;
	
	Parentesco getCatalogoParentesco(Long idCatalogo)  throws ServiciosRestException;
	List<Parentesco> getCatalogoParentesco()  throws ServiciosRestException;
	
	EntidadFederativa getCatalogoEntidadFed(String idCatalogo)  throws ServiciosRestException;
	List<EntidadFederativa> getCatalogoEntidadFed() throws ServiciosRestException;
	
	EstadoCivil getCatalogoEstadoCivil(Long idCatalogo)  throws ServiciosRestException;
	List<EstadoCivil> getCatalogoEstadoCivil()  throws ServiciosRestException;
	
	Pais getCatalogoPais(Long idPais) throws ServiciosRestException;
	List<Pais> getCatalogoPais() throws ServiciosRestException;
	
	Turno getCatalogoTurnoByID(Long idTurno)throws ServiciosRestException;
	 List<Turno> getCatalogoTurno() throws ServiciosRestException;
	 
	 
	Delegacion getCatalogoDelegacionById(Long idDelegacion) throws ServiciosRestException;
	List<Delegacion> getCatalogoDelegacion() throws ServiciosRestException;
	 
	Subdelegacion getCatalogoSubDelegacionById(Long idDelegacion) throws ServiciosRestException;
	List<Subdelegacion> getCatalogoSubDelegacionByIdDelegacion(Long idSubDelegacion) throws ServiciosRestException;
	
	List<UnidadMedicaFamiliar> getCatalogoUMfBySubdelegacionNivelAtencion(Long idCatalogo, Long idNivelAtencion) throws ServiciosRestException;
	UnidadMedicaFamiliar getCatalogoUMfById(Long idUmf) throws ServiciosRestException;
	
	List<Modalidad> getCatalogoModalidad() throws ServiciosRestException;
	Modalidad getCatalogoModalidadByCveModalidad(String idCatalogo) throws ServiciosRestException;
	
	List<TipoPersona> getCatalogoTipoPersona() throws ServiciosRestException;
	TipoPersona getCatalogoTipoPersonaByIdTipoPersona(Long idCatalogo) throws ServiciosRestException;
	
	List<Clase> getCatalogoClase() throws ServiciosRestException;
	Clase getCatalogoClaseByCveClase(Long idCatalogo) throws ServiciosRestException;
	
	List<Fraccion> getCatalogoFraccionByIdClase(Long idCatalogo) throws ServiciosRestException;
	Fraccion getCatalogoFraccionByCveFraccion(Long idCatalogo) throws ServiciosRestException;
	 
	
	List <Division> getCatalogoDivision() throws ServiciosRestException;
	List <Grupo> getCatalogoGrupoByIdDivision(Long idCatalogo) throws ServiciosRestException;
	List <Fraccion> getCatalogoFraccionByIdGrupo(Long idCatalogo) throws ServiciosRestException;
	
	/**
	 * Metodo para recuperar las fracciones haciendo join con las clases que no tiene fecha fin
	 * @param idCatalogo
	 * @return
	 * @throws ServiciosRestException
	 */
	List<Fraccion> getCatalogoFraccionConClaseActivaByIdGrupo(Long idCatalogo) throws ServiciosRestException;
	
	List<Subdelegacion> getSubDelegacionesPorCodigoPostal(String cp) throws ServiciosRestException;
	
	/**
	 * Metodo que regresa el listado de municipos inegi de la bdtu
	 * @param cveEntidadFed con la cve de la entidad federativa a filtrar
	 * @return
	 * @throws Exception
	 */
	List<MunicipioInegi> getCatalogoMunicipioInegi(String cveEntidadFed) throws ServiciosRestException;
	
	/**
	 * Consulta el objeto MunicipioInegi para recuperar la descripci�n del registro
	 * @param cveEntidadFed
	 * @param cveMunicipioInegi
	 * @return MunicipioInegi
	 * @throws Exception
	 */
	MunicipioInegi getMunicipioInegi(String cveEntidadFed, String cveMunicipioInegi) throws ServiciosRestException;
	
	/**
	 * Servcicio de devuelve un listado de UMF con base los parametros que recibe como filtro
	 * @param cveNivelAtencion
	 * @param cveTipoUmf
	 * @return
	 */
	List<CatComunUmf> getCatComunUmfList(Long cveNivelAtencion, Long cveTipoUmf, String cveDelegacion)throws ServiciosRestException;
	
	/**
	 * Metodo que consulta una umf por clave presupuestal
	 * @param cvePresupuesta
	 * @return
	 */
	CatComunUmf getCatComunUmf(String cvePresupuestal) throws ServiciosRestException;
	
	/**
	 * Metodo que consulta una delegacion por la clave que recibe como par�metro
	 * @param cveDelegacion
	 * @return
	 * @throws ServiciosRestException
	 */
	CatComunDelegacion getCatComunDelegacion(String cveDelegacion) throws ServiciosRestException;
	
	/**
	 * Consulta el catalgoo de delegaciones de cat comun
	 * @return
	 * @throws ServiciosRestException
	 */
	List<CatComunDelegacion> getCatComunDelegacionList() throws ServiciosRestException;
	
	/**
	 * Metodo que consulta la entidad federativa que recibe como parametro
	 * @param cveEntidadFederativa
	 * @return
	 * @throws ServiciosRestException
	 */
	CatComunEntidadFederativa getCatComunEntidadFederativa (String cveEntidadFederativa) throws ServiciosRestException;
	
	/**
	 * Consulta el catalogo de entidades federativas de  cat comun
	 * @return
	 * @throws ServiciosRestException
	 */
	List<CatComunEntidadFederativa> getCatComunEntidadFederativaList() throws ServiciosRestException;
	
	/**
	 * Metodo que conulta la informacion general del municipio IMSS incluyendo la entidadd federtiva
	 * @param cveMunicipioImss
	 * @return
	 * @throws ServiciosRestException
	 */
	MunicipioImss getMunicipioImss(String cveMunicipioImss)throws ServiciosRestException;
	
	/**
	 * Metodo que consulta el catálogo de municiopos IMSS por clave de delegacion y subdelegacion
	 * @param cveDelegacionImss
	 * @param cveSubDelegacionImss
	 * @return
	 */
	List <MunicipioImss> getMunicipioImssByDelegacionSubdelegacion(Long cveDelegacionImss, Long cveSubDelegacionImss)
			throws ServiciosRestException;

}
