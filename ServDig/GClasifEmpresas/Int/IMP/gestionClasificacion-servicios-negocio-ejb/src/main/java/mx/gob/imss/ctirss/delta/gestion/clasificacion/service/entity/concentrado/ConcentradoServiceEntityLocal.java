/**
 *  
 *  @Cliente: Instituto Mexicano del Seguro Social
 *  @Autor: Eduardo Gonzalez
 *  @Proyecto: delta
 *  @Archivo:BitacoraServiceEntityLocal.java
 *  @Paquete:mx.gob.imss.ctirss.delta.gestion.clasificacion.service.entity.bitacora
 *  @Fecha:04/06/2012
 */

package mx.gob.imss.ctirss.delta.gestion.clasificacion.service.entity.concentrado;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Local;

import mx.gob.imss.ctirss.delta.model.clasificacion.ElementoConcentrado;
import mx.gob.imss.ctirss.delta.model.clasificacion.FiltrosConcentrado;
import mx.gob.imss.ctirss.delta.model.clasificacion.SubdelegacionesConcentrado;

@Local
public interface ConcentradoServiceEntityLocal {

	List<ElementoConcentrado> obtieneConcentrado(FiltrosConcentrado model) throws Exception;
	
	ArrayList<SubdelegacionesConcentrado> obtieneSubdelegaciones(int del) throws Exception;
	
}
