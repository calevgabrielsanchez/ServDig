package mx.gob.imss.ctirss.delta.gestion.patronal.service.utility.clasificacion.actividad.economica;

import javax.ejb.Local;

import mx.gob.imss.ctirss.delta.model.gestion.patronal.Bien;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.Clase;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.Clasificacion;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.Division;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.EquipoTransporte;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.Fraccion;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.Grupo;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.MaquinariaEquipo;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.MateriaPrima;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.Personal;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.Proceso;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.Producto;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.TipoCombustible;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.TipoMaquinariaEquipo;
import mx.gob.imss.ctirss.delta.persistence.DicClase;
import mx.gob.imss.ctirss.delta.persistence.DicDivision;
import mx.gob.imss.ctirss.delta.persistence.DicFraccion;
import mx.gob.imss.ctirss.delta.persistence.DicGrupo;
import mx.gob.imss.ctirss.delta.persistence.DicTipoCombustible;
import mx.gob.imss.ctirss.delta.persistence.DicTipoMaquinariaEquipo;
import mx.gob.imss.ctirss.delta.persistence.DitBiene;
import mx.gob.imss.ctirss.delta.persistence.DitClasificacion;
import mx.gob.imss.ctirss.delta.persistence.DitEquipoTransporte;
import mx.gob.imss.ctirss.delta.persistence.DitMaquinariaEquipo;
import mx.gob.imss.ctirss.delta.persistence.DitMateriaPrimaMaterial;
import mx.gob.imss.ctirss.delta.persistence.DitPersonal;
import mx.gob.imss.ctirss.delta.persistence.DitProceso;
import mx.gob.imss.ctirss.delta.persistence.DitProducto;

@Local
public interface ClasificacionActividadEconomicaServiceUtilityLocal {
	
	/**
	 * Transforma la entidad clasificacion en la clase de negocio Clasificacion
	 * @author Hugo Armando Martínez Chamónica
	 * @param entity
	 * @return Clasificacion
	 */
	Clasificacion convertirEntityToModelClasificacion(DitClasificacion entity);
	
	/**
	 * 
	 * @author Hugo Armando Martínez Chamónica
	 * @param entity
	 * @return Producto
	 */
	Producto convertirEntityToModelProducto(DitProducto entity);
	
	/**
	 * 
	 * @author Hugo Armando Martínez Chamónica
	 * @param entity
	 * @return MateriaPrimaMaterial
	 */
	MateriaPrima convertirEntityToModelMateriaPrima(DitMateriaPrimaMaterial entity);
	
	/**
	 * 
	 * @author Hugo Armando Martínez Chamónica
	 * @param entity
	 * @return MaquinariaEquipo
	 */
	MaquinariaEquipo convertirEntityToModelMaquinariaEquipo(DitMaquinariaEquipo entity);
	
	/**
	 * 
	 * @author Hugo Armando Martínez Chamónica
	 * @param entity
	 * @return TipoMaquinariaEquipo
	 */
	TipoMaquinariaEquipo convertirEntityToModelTipoMaquinariaEquipo(DicTipoMaquinariaEquipo entity);
	
	/**
	 * 
	 * @author Hugo Armando Martínez Chamónica
	 * @param equipoTransporte
	 * @return EquipoTransporte
	 */
	EquipoTransporte convertirEntityToModelEquipoTransporte(DitEquipoTransporte equipoTransporte);
	
	/**
	 * 
	 * @author Hugo Armando Martínez Chamónica
	 * @param entity
	 * @return
	 * TipoCombustible
	 */
	TipoCombustible convertirEntityToModelTipoCombustible(DicTipoCombustible entity);
	
	/**
	 * 
	 * @author Hugo Armando Martínez Chamónica
	 * @param entity
	 * @return Proceso
	 */
	Proceso convertirEntityToModelProceso(DitProceso entity);
	
	/**
	 * 
	 * @author Hugo Armando Martínez Chamónica
	 * @param personal
	 * @return
	 * Personal
	 */
	Personal convertirEntityToModelPersonal(DitPersonal personal);
	
	/**
	 * 
	 * @author Hugo Armando Martínez Chamónica
	 * @param entity
	 * @return
	 * Fraccion
	 */
	Fraccion convertirEntityToModelFraccion(DicFraccion entity);
	
	/**
	 * 
	 * @author Hugo Armando Martínez Chamónica
	 * @param entity
	 * @return
	 * Grupo
	 */
	Grupo convertirEntityToModelGrupo(DicGrupo entity);
	
	/**
	 * 
	 * @author Hugo Armando Martínez Chamónica
	 * @param entity
	 * @return
	 * Division
	 */
	Division convertirEntityToModelDivision(DicDivision entity);
	
	/**
	 * 
	 * @author Hugo Armando Martínez Chamónica
	 * @param entity
	 * @return
	 * Clase
	 */
	Clase convertirEntityToModelClase(DicClase entity);
	
	/**
	 * 
	 * @author Hugo Armando Martínez Chamónica
	 * @param model
	 * @return DitClasificacion
	 */
	DitClasificacion convertirModelToEntity(Clasificacion model);
	
	/**
	 * 
	 * @author Hugo Armando Martínez Chamónica
	 * @param origen
	 * @param destino
	 * @return DitClasificacion
	 */
	DitClasificacion mergeEntities(DitClasificacion origen, DitClasificacion destino);
	
	/**
	 * 
	 * @author Hugo Martinez
	 * @Date 18/06/2012
	 * @param entity
	 * @return
	 */
	Bien convertirEntityToModelBien(DitBiene entity);
}
