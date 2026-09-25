/**
 *
 *
 **/
package mx.gob.imss.ctirss.delta.gestion.motorCalculo.service.utility;

import javax.ejb.Local;

import mx.gob.imss.ctirss.delta.model.gestion.tramite.TipoTramite;
import mx.gob.imss.ctirss.delta.persistence.DicTipoTramite;

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
public interface ExpedienteServiceUtilityLocal {

    TipoTramite convertirEntidadAModelo(DicTipoTramite entidad);

}
