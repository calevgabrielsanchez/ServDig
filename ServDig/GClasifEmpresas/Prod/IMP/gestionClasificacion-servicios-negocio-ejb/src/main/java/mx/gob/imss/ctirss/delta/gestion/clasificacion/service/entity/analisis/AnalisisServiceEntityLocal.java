/**
 *  
 *  @Cliente: Instituto Mexicano del Seguro Social
 *  @Autor: Eduardo Gonzalez
 *  @Proyecto: delta
 *  @Archivo:AnalisisServiceEntityLocal.java
 *  @Paquete:mx.gob.imss.ctirss.delta.gestion.clasificacion.service.entity.analisis
 *  @Fecha:04/06/2012
 */

package mx.gob.imss.ctirss.delta.gestion.clasificacion.service.entity.analisis;

import java.math.BigDecimal;
import java.util.List;

import javax.ejb.Local;
import javax.persistence.PersistenceException;

import mx.gob.imss.ctirss.delta.exception.clasificacion.AnalisisNoEncontradoException;
import mx.gob.imss.ctirss.delta.model.clasificacion.AnalisisClasificacionEmpresas;
import mx.gob.imss.ctirss.delta.model.clasificacion.EstatusAnalisisModel;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.SujetoObligado;

@Local
public interface AnalisisServiceEntityLocal{
	
	/**
	 * Consulta del catalogo de estatus por id de grupo.
	 * @param cveIdGrupo
	 * @return
	 */
	List<EstatusAnalisisModel> consultaEstatusAnalisisPorGrupoAnalisis(Long cveIdGrupo) throws Exception;

	
	/**
	 * Metodo para agregar un elemento al catalogo.
	 * @param model
	 * @return
	 */
	AnalisisClasificacionEmpresas agregaAnalisis(AnalisisClasificacionEmpresas model) throws PersistenceException;
	
	/**
	 * Metodo para actualizar el estado del analisis
	 * @param model : Con el estado seteado adentro del objeto.
	 * @return
	 * @throws PersistenceException
	 */
	AnalisisClasificacionEmpresas actualizaEstado(AnalisisClasificacionEmpresas model) throws AnalisisNoEncontradoException;
	
	AnalisisClasificacionEmpresas consultaPorIdAnalisis(long idAnalisis) throws PersistenceException;	
	
	boolean validaEstatusMovimiento(long cveIdAnalisis, String cveIdEstatus) throws PersistenceException;

	/**
	 * Metodo para realizar consulta por clave
	 * @param model
	 * @return
	 */
	AnalisisClasificacionEmpresas consultaDetalleAnalisis(BigDecimal idSolicitud) throws Exception;

	SujetoObligado consultarSujetoObligado(AnalisisClasificacionEmpresas model, int tipoPersona) throws PersistenceException ;
	
	
	/**
	 * Metodo para agregar un Análisis por motivo de cancelación por Registro Patronal
	 * @param model
	 * @return
	 */
	Long generaAnalisisCancelacion(Long cveIdSolicitud, Long cveIdGrupoAnalisis);
	
	void actualizaTipoCausa(Long cveIdAnalisis, Long cveIdTipoCausa);
	
	void actualizaIndCausa(AnalisisClasificacionEmpresas model, boolean activo);
	
	/**
	 * Metodo para actualizar el analisis
	 * @param model : Con el estado seteado adentro del objeto.
	 * @param zero 
	 * @return
	 * @throws PersistenceException
	 */
	void actualizaAnalisisDesechar(AnalisisClasificacionEmpresas model, boolean indRegCausa) throws AnalisisNoEncontradoException;

}