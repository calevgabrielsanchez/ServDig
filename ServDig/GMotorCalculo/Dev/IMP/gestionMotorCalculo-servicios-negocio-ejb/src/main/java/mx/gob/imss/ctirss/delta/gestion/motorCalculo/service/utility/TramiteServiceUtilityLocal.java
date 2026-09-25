/**
 *
 *
 **/
package mx.gob.imss.ctirss.delta.gestion.motorCalculo.service.utility;

import java.util.List;

import javax.ejb.Local;

import mx.gob.imss.ctirss.delta.model.gestion.tramite.Tramite;
import mx.gob.imss.ctirss.delta.persistence.DitTramite;

/**
 * 
 * @Cliente: Instituto Mexicano del Seguro Social
 * @Autor: Lucio Duran Silva
 * @Proyecto: delta
 * @Archivo: TramiteServiceUtilityLocal.java
 * @Paquete: mx.gob.imss.ctirss.delta.tramite.service.utility
 * @Fecha: 09:41:14
 */
@Local
public interface TramiteServiceUtilityLocal {

    /**
     * Convierte una lista de entidades a una lista de modelo.
     * 
     * @param tramites
     * @return
     */
    List<Tramite> convertirListaEntityToModel(List<DitTramite> tramites);

    /**
     * Convierte un entity a modelo de Tramite.
     * 
     * @param ditTramite
     * @return
     */
    Tramite convertirEntidadAModelo(DitTramite ditTramite);

}
