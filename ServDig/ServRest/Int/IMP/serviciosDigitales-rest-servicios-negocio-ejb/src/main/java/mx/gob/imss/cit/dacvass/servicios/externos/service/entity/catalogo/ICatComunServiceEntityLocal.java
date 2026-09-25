package mx.gob.imss.cit.dacvass.servicios.externos.service.entity.catalogo;

import java.util.List;

import javax.ejb.Local;

import mx.gob.imss.cit.dacvass.servicios.externos.model.general.catalogos.catComun.CatComunDelegacion;
import mx.gob.imss.cit.dacvass.servicios.externos.model.general.catalogos.catComun.CatComunEntidadFederativa;
import mx.gob.imss.cit.dacvass.servicios.externos.model.general.catalogos.catComun.CatComunUmf;
import mx.gob.imss.cit.dacvass.servicios.externos.model.general.exception.ServiciosRestException;

/**
 * Clase que expondra los catalogos que no se encuentra en servicios digitales
 * @author juan.salinas
 *
 */
@Local
public interface ICatComunServiceEntityLocal {
	
	/**
	 * Servcicio de devuelve un listado de UMF con base los parametros que recibe como filtro
	 * @param cveNivelAtencion
	 * @param cveTipoUmf
	 * @return
	 */
	List<CatComunUmf> getCatComunUmfList(Long cveNivelAtencion, Long cveTipoUmf, String cveDelegacion)throws Exception;
	
	/**
	 * Metodo que consulta una umf por clave presupuestal
	 * @param cvePresupuesta
	 * @return
	 */
	CatComunUmf getCatComunUmf(String cvePresupuestal) throws Exception;
	
	/**
	 * Metodo que consulta una delegacion por la clave que recibe como parámetro
	 * @param cveDelegacion
	 * @return
	 * @throws ServiciosRestException
	 */
	CatComunDelegacion getCatComunDelegacion(String cveDelegacion) throws Exception;
	
	/**
	 * Consulta el catalgoo de delegaciones de cat comun
	 * @return
	 * @throws ServiciosRestException
	 */
	List<CatComunDelegacion> getCatComunDelegacionList() throws Exception;
	
	/**
	 * Metodo que consulta la entidad federativa que recibe como parametro
	 * @param cveEntidadFederativa
	 * @return
	 * @throws ServiciosRestException
	 */
	CatComunEntidadFederativa getCatComunEntidadFederativa (String cveEntidadFederativa) throws Exception;
	
	/**
	 * Consulta el catalogo de entidades federativas de  cat comun
	 * @return
	 * @throws ServiciosRestException
	 */
	List<CatComunEntidadFederativa> getCatComunEntidadFederativaList() throws Exception;
	
}
