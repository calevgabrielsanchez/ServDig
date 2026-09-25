/**
 *  
 *  @Cliente: Instituto Mexicano del Seguro Social
 *  @Autor: Eduardo Gonzalez
 *  @Proyecto: delta
 *  @Archivo: ConcentradoServiceUtilityLocal.java
 *  @Paquete: mx.gob.imss.ctirss.delta.gestion.clasificacion.service.utility.concentrado
 *  @Fecha: 04/06/2012
 */

package mx.gob.imss.ctirss.delta.gestion.clasificacion.service.utility.concentrado;

import java.util.ArrayList;

import javax.ejb.Local;

import mx.gob.imss.ctirss.delta.model.clasificacion.ElementoConcentrado;
import mx.gob.imss.ctirss.delta.model.clasificacion.SubdelegacionesConcentrado;
import mx.gob.imss.ctirss.delta.model.clasificacion.SumarizadoConcentrado;

@Local
public interface ConcentradoServiceUtilityLocal {

	ArrayList<SumarizadoConcentrado> obtieneSumarizadoSubDelegacion(
			ArrayList<SubdelegacionesConcentrado> subDelegacionesVO,
			ArrayList<ElementoConcentrado> repDel) throws Exception;

}
