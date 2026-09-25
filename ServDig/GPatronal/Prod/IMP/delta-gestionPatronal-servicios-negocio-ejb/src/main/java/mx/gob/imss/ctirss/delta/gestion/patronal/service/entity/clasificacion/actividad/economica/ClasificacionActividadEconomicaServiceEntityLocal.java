package mx.gob.imss.ctirss.delta.gestion.patronal.service.entity.clasificacion.actividad.economica;

import java.math.BigDecimal;
import java.util.List;

import javax.ejb.Local;

import mx.gob.imss.ctirss.delta.framework.base.exception.GestionPatronalBusinessException;
import mx.gob.imss.ctirss.delta.model.clasificacion.AdjuntosClasificacion;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.Clasificacion;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.Fraccion;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.SujetoObligado;
import mx.gob.imss.ctirss.delta.persistence.DicFraccionClase;
import mx.gob.imss.ctirss.delta.persistence.DitClasificacion;

@Local
public interface ClasificacionActividadEconomicaServiceEntityLocal {
	
	/**
	 * 
	 * @author Hugo Armando Martínez Chamónica
	 * @param cveIdPatronSujetoObligado
	 * @return
	 * Clasificacion
	 */
	Clasificacion consultarPorPatronSujetoObligado(Long cveIdPatronSujetoObligado);
	/**
	 * 
	 * @author Hugo Armando Martínez Chamónica
	 * @param clasificacion
	 * void
	 */
	void actualizarClasificacion(Clasificacion clasificacion, Long cveCausa);
	
	/**
	 * 
	 * @author Hugo Armando Martínez Chamónica
	 * @param idFraccion
	 * @return
	 * Fraccion
	 */
	Fraccion consultarFraccionPorIdentificador(Long idFraccion);
	
	/**
	 * Almacena una nueva clasificación y todos los datos relacionados a su actividad economica.
	 * @author Hugo Martinez
	 * @Date 18/06/2012
	 * @param sujetoTramite
	 */
	void guardarNuevaClasificacionActividadEconomica(SujetoObligado sujetoTramite, Long cveCausa);
	
	/**
	 * Consulta la fraccion equivalente a la fraccion del clasificador en base a los atributos
	 * num_xxx de fraccion, grupo y clase.
	 * 
	 * @author Hugo Martinez
	 * @Date 10/07/2012
	 * @param fraccion
	 * @return
	 */
	Fraccion consultarFraccionEquivalente(Fraccion fraccion);
	
	/**
	 * Obtiene el valor de la causa asociada al tipo de tramite
	 * @author Hugo Martinez
	 * @Date 27/02/2013
	 * @param cidTipoTramite
	 * @param idTipoProceso
	 * @return Integer
	 */
	Long obtenerCausaPorTipoTramite(Long cidTipoTramite, Long idTipoProceso);
	
	/**
	 * 
	 * @author Hugo Martinez
	 * @Date 07/03/2013
	 * @param clasificacion
	 */
	void actualizarClasificacionPorIdentificador(Clasificacion clasificacion, Integer cveCausa, Integer tpoMovimiento);
	
	/**
	 * 
	 * @author Hugo Martinez
	 * @Date 07/03/2013
	 * @param clasificacion
	 */
	void actualizarClasificacionPorRegistroPatronal(Clasificacion clasificacion, Integer cveCausa, Integer tpoMovimiento);
    /**
     * 
     * @author Roberto Bernabe
     * @param cveIdPatronSujetoObligado
     * @return
     * Clasificacion
     */
    void bajaClasificacionPorRegistroPatronal(String registroPatronal, Integer claveCausa);
    
    Fraccion obtenerFraccionClaseActiva(Long cveIdFraccion);
    
    /**
     * Retorna la clasificacion actual con la información referente a clase, división, grupo y fraccion
     * @param idClasificacion Identificador delta de la clasificación
     * @return
     */
    Clasificacion obtenerClasificacionPorId(Long idClasificacion);

    BigDecimal evaluarPrima(Long cveCausa, DicFraccionClase nuevaFraccion, DitClasificacion clasificacionActual);
    
    /**
     * Obtiene la información d euna fracción con su clase correspondiente en base a la fracción completa
     * por ejemplo 8913.
     * @param fraccionCompleta Concatenación de numDivision, numGrupo y numFraccion (pueden ser 3 o 4 digitos)
     * @return Fraccion
     */
    Fraccion consultarFraccionPorFraccionCompleta(String fraccionCompleta) throws GestionPatronalBusinessException;
    
	/**
	 * Se crea un registro en la tabla DIT_ADJUNTOS_CLASIFICACION asociado a un tramite de clasificacion
	 * @param adjuntosClasificacion
	 */
    void guardarArchivoAdjunto(AdjuntosClasificacion adjuntosClasificacion);

	/**
	 * Marcamos como borrado el archivo en la tabla DIT_ADJUNTOS_CLASIFICACION
	 * @param adjuntosClasificacion
	 */
    void quitarArchivoAdjunto(String folio, String nombreArchivo);
    
	/**
	 * Consulta archivos por folio en la tabla DIT_ADJUNTOS_CLASIFICACION
	 * @param adjuntosClasificacion
	 */    
	List<AdjuntosClasificacion> consultarArchivoAdjunto(String folio);
}
