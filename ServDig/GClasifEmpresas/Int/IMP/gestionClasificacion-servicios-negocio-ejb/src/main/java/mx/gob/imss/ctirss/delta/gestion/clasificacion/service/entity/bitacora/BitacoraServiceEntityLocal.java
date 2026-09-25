/**
 *  
 *  @Cliente: Instituto Mexicano del Seguro Social
 *  @Autor: Eduardo Gonzalez
 *  @Proyecto: delta
 *  @Archivo:BitacoraServiceEntityLocal.java
 *  @Paquete:mx.gob.imss.ctirss.delta.gestion.clasificacion.service.entity.bitacora
 *  @Fecha:04/06/2012
 */

package mx.gob.imss.ctirss.delta.gestion.clasificacion.service.entity.bitacora;

import java.sql.SQLException;
import java.util.List;

import javax.ejb.Local;
import javax.persistence.PersistenceException;

import mx.gob.imss.ctirss.delta.exception.clasificacion.ClasificacionException;
import mx.gob.imss.ctirss.delta.gestion.clasificacion.model.dto.DictamenDTO;
import mx.gob.imss.ctirss.delta.model.clasificacion.ElementoBitacora;
import mx.gob.imss.ctirss.delta.model.clasificacion.EstatusAnalisisModel;
import mx.gob.imss.ctirss.delta.model.clasificacion.FiltrosBitacoras;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.Clasificacion;
import mx.gob.imss.ctirss.delta.persistence.DitClasificacion;
import mx.gob.imss.ctirss.delta.persistence.DitHistEstatusAnalisis;
import mx.gob.imss.ctirss.delta.model.clasificacion.ElementoBitacoraOmision;

@Local
public interface BitacoraServiceEntityLocal {

	List<ElementoBitacora> buscaRegistros(FiltrosBitacoras model) throws Exception;
	
	List<ElementoBitacora> buscaComentariosDetalle(Long cveIdAnalisis, String cveUsuario) throws Exception;
	
	List<ElementoBitacoraOmision> buscaComentariosOmisiones(Long cveIdAnalisis)throws Exception;

	List<ElementoBitacoraOmision> buscaOmisionActual(Long cveIdAnalisis) throws Exception;
	
	EstatusAnalisisModel guardaBitacora(EstatusAnalisisModel model) throws PersistenceException, ClasificacionException;
	
	void guardarBitacoraOmisiones(String omision, String idAnalisis, String justificacion, String pago, String fechaSurteEfecto)  throws PersistenceException;
	
	EstatusAnalisisModel buscaClasificacionInicial(Long cveIdAnalisis) throws PersistenceException;
	
	String fraccionMovAnt(Long idAnalisis) throws PersistenceException;
	
	Clasificacion consultaClasificacionActualPorHistorico(Long idAnalisis) throws PersistenceException;

	DitClasificacion findDitClasificacion(DitHistEstatusAnalisis entity);


}
