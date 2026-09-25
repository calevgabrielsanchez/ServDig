package mx.gob.imss.cit.dacvass.servicios.externos.service.entity.catalogo;

import java.util.Date;
import java.util.List;

import javax.ejb.Local;

import mx.gob.imss.cit.dacvass.servicios.externos.model.general.catalogos.MunicipioImss;
import mx.gob.imss.cit.dacvass.servicios.externos.model.general.catalogos.MunicipioInegi;
import mx.gob.imss.cit.dacvass.servicios.externos.model.general.exception.ServiciosRestException;
import mx.gob.imss.cit.dacvass.servicios.externos.model.identidad.dto.EntidadFederativa;
import mx.gob.imss.cit.dacvass.servicios.externos.model.identidad.dto.EstadoCivil;
import mx.gob.imss.cit.dacvass.servicios.externos.model.identidad.dto.Pais;
import mx.gob.imss.cit.dacvass.servicios.externos.model.identidad.dto.Parentesco;
import mx.gob.imss.cit.dacvass.servicios.externos.model.identidad.dto.Sexo;
import mx.gob.imss.cit.dacvass.servicios.externos.model.identidad.dto.Turno;
import mx.gob.imss.ctirss.delta.model.derechohabiente.UnidadMedicaFamiliar;
import mx.gob.imss.ctirss.delta.model.domicilio.Asentamiento;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.TipoPersona;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.Clase;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.Division;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.Fraccion;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.Grupo;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.Modalidad;
import mx.gob.imss.digital.modelo.domicilio.Delegacion;
import mx.gob.imss.digital.modelo.domicilio.Subdelegacion;

/**
 * Clase que expondra los catalogos que no se encuentra en servicios digitales
 * @author juan.salinas
 *
 */
@Local
public interface ICatalogoServiceEntityLocal {
	
	/**
	 * Consulta el catalogo de sexo por id
	 * @param idCatalogo
	 * @return
	 * @throws Exception
	 */
	Sexo getCatalogoSexo(Long idCatalogo) throws Exception;
	/**
	 * Consulta total del catalogo de sexo
	 * @return
	 * @throws Exception
	 */
	List<Sexo> getCatalogoSexo()  throws Exception;
	
	Parentesco getCatalogoParentesco(Long idCatalogo)  throws Exception;
	List<Parentesco> getCatalogoParentesco()  throws Exception;
	
	EntidadFederativa getCatalogoEntidadFed(String idCatalogo)  throws Exception;
	List<EntidadFederativa> getCatalogoEntidadFed() throws Exception;
	
	EstadoCivil getCatalogoEstadoCivil(Long idCatalogo)  throws Exception;
	List<EstadoCivil> getCatalogoEstadoCivil()  throws Exception;
	
	Pais getCatalogoPais(Long idPais) throws Exception;
	List<Pais> getCatalogoPais() throws Exception;
	
	Turno getCatalogoTurnoByID(Long idTurno)throws Exception;
	 List<Turno> getCatalogoTurno() throws Exception;
	 
	 
	Delegacion getCatalogoDelegacionById(Long idDelegacion) throws Exception;
	List<Delegacion> getCatalogoDelegacion() throws Exception;
	 
	Subdelegacion getCatalogoSubDelegacionById(Long idDelegacion) throws Exception;
	List<Subdelegacion> getCatalogoSubDelegacionByIdDelegacion(Long idSubDelegacion) throws Exception;
	
	List<UnidadMedicaFamiliar> getCatalogoUMfBySubdelegacionNivelAtencion(Long idCatalogo, Long idNivelAtencion) throws Exception;
	UnidadMedicaFamiliar getCatalogoUMfById(Long idUmf) throws Exception;
	
	List<Modalidad> getCatalogoModalidad() throws Exception;
	Modalidad getCatalogoModalidadByCveModalidad(String idCatalogo) throws Exception;
	
	List<TipoPersona> getCatalogoTipoPersona() throws Exception;
	TipoPersona getCatalogoTipoPersonaByIdTipoPersona(Long idCatalogo) throws Exception;
	
	List<Clase> getCatalogoClase() throws Exception;
	Clase getCatalogoClaseByCveClase(Long idCatalogo) throws Exception;
	
	List<Fraccion> getCatalogoFraccionByIdClase(Long idCatalogo) throws Exception;
	Fraccion getCatalogoFraccionByCveFraccion(Long idCatalogo) throws Exception;
	 
	
	List <Division> getCatalogoDivision() throws Exception;
	List <Grupo> getCatalogoGrupoByIdDivision(Long idCatalogo) throws Exception;
	List <Fraccion> getCatalogoFraccionByIdGrupo(Long idCatalogo) throws Exception;
	
	/**
	 * Metodo para recuperar las fracciones haciendo join con las clases que no tiene fecha fin
	 * @param idCatalogo
	 * @return
	 * @throws Exception
	 */
	List<Fraccion> getCatalogoFraccionConClaseActivaByIdGrupo(Long idCatalogo) throws Exception;
	
	List<Subdelegacion> getSubDelegacionesPorCodigoPostal(String cp) throws Exception;
	
	/**
	 * Metodo que regresa el listado de municipos inegi de la bdtu
	 * @param cveEntidadFed con la cve de la entidad federativa a filtrar
	 * @return
	 * @throws Exception
	 */
	List<MunicipioInegi> getCatalogoMunicipioInegi(String cveEntidadFed) throws Exception;
	
	/**
	 * Consulta el objeto MunicipioInegi para recuperar la descripci�n del registro
	 * @param cveEntidadFed
	 * @param cveMunicipioInegi
	 * @return MunicipioInegi
	 * @throws Exception
	 */
	MunicipioInegi getMunicipioInegi(String cveEntidadFed, String cveMunicipioInegi) throws Exception;
	
	/**
	 * COnsulta los dias ihabiles que estan en un anio que recibe como parametro
	 * @param numAnio
	 * @return
	 * @throws Exception
	 */
	List<Date> getDiasinhabilesByAnio(Long numAnio) throws Exception;
	
	/**
	 * Metodo que consulta los asentamientos geograficos de un codigo postal
	 * @param codigo
	 * @return
	 * @throws Exception
	 */
	List<Asentamiento> getAsentamientoPorCodigoPosta(String codigo)throws Exception;
	
	/**
	 * 
	 * @param municipio
	 * @return
	 */
	List<mx.gob.imss.cit.dacvass.servicios.externos.model.general.catalogos.Localidad> getLocalidadesPorMunicipio(MunicipioInegi municipio)throws Exception;
	
	/**
	 * Metodo que conulta la informacion general del municipio IMSS incluyendo la entidadd federtiva
	 * @param cveMunicipioImss
	 * @return
	 * @throws ServiciosRestException
	 */
	MunicipioImss getMunicipioImss(String cveMunicipioImss)throws Exception;
	
	/**
	 * Metodo que consulta el catálogo de municiopos IMSS por clave de delegacion y subdelegacion
	 * @param cveDelegacionImss
	 * @param cveSubDelegacionImss
	 * @return
	 */
	List <MunicipioImss> getMunicipioImssByDelegacionSubdelegacion(Long cveDelegacionImss, Long cveSubDelegacionImss)
			throws Exception;
	

}
