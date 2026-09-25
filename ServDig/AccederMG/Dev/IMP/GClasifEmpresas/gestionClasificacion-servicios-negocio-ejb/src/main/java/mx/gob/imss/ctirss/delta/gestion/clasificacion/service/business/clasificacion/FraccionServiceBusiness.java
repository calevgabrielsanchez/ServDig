/**
 *  
 *  @Cliente: Instituto Mexicano del Seguro Social
 *  @Autor: Eduardo Gonzalez
 *  @Proyecto: delta
 *  @Archivo: FraccionServiceBusiness.java
 *  @Paquete: mx.gob.imss.ctirss.delta.gestion.clasificacion.service.business.clasificacion
 *  @Fecha: 04/06/2012
 */

package mx.gob.imss.ctirss.delta.gestion.clasificacion.service.business.clasificacion;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.PersistenceException;

import mx.gob.imss.ctirss.delta.gestion.clasificacion.service.entity.clasificacion.FraccionEntityLocal;
import mx.gob.imss.ctirss.delta.gestion.clasificacion.service.interfaces.clasificacion.FraccionServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.Clase;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.Division;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.Fraccion;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.Grupo;

@Stateless(name = "fraccionServiceBusiness", mappedName = "fraccionServiceBusiness")
public class FraccionServiceBusiness implements FraccionServiceBusinessRemote{
	
	@EJB
	private FraccionEntityLocal fraccionEntity;
	
	@Override
	public Fraccion consultaPorId(Long cveIdFraccion) throws Exception{
		return fraccionEntity.consultaPorId(cveIdFraccion);
	}
	
	/**
	 * {@inheritDoc}
	 * @see FraccionServiceBusiness#obtenerFotoClasificacionActual(long)
	 */
	@Override
	public Fraccion obtenerFotoClasificacionActual(final long cveIdAnalisis) {
		return fraccionEntity.obtenerFotoClasificacionActual(cveIdAnalisis);
	}

	
	
	@Override
	public List<Clase> consultaCatalogoClase() throws Exception {
		return fraccionEntity.consultaCatalogoClase();
	}

	@Override
	public List<Fraccion> consultaCatalogoFraccionByIdClase(Long idClase) throws Exception {
		return fraccionEntity.consultaCatalogoFraccionByIdClase(idClase);
	}

	@Override
	public Clase consultaCatalogoClaseById(Long idClase) throws Exception {
		return fraccionEntity.consultaCatalogoClaseById(idClase);
	}

	
	@Override
	public List<Division> consultaCatalogoDivision() throws Exception {
		return fraccionEntity.consultaCatalogoDivision();
	}

	@Override
	public List<Grupo> consultaCatalogoGrupoByIdDivision(Long idDivision) throws Exception {
		return fraccionEntity.consultaCatalogoGrupoByIdDivision(idDivision);
	}

	@Override
	public List<Fraccion> consultaCatalogoFraccionByIdGrupo(Long idGrupo) throws Exception {
		return fraccionEntity.consultaCatalogoFraccionByIdGrupo(idGrupo);
	}
	
	@Override
	public List<Fraccion> consultaCatalogoFraccionConClaseActivasByIdGrupo(Long idGrupo) throws Exception {
		return fraccionEntity.consultaCatalogoFraccionConClaseActivasByIdGrupo(idGrupo);
	}
	
	
	

} 
