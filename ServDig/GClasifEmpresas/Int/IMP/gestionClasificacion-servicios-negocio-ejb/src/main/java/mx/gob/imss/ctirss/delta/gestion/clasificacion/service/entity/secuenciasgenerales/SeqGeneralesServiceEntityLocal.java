/**
 *  
 *  @Cliente: Instituto Mexicano del Seguro Social
 *  @Autor: Eduardo Gonzalez
 *  @Proyecto: delta
 *  @Archivo: SeqGeneralesServiceEntityLocal.java
 *  @Paquete: mx.gob.imss.ctirss.delta.gestion.clasificacion.service.entity.secuenciasgenerales
 *  @Fecha: 04/06/2012
 */

package mx.gob.imss.ctirss.delta.gestion.clasificacion.service.entity.secuenciasgenerales;

import java.math.BigInteger;

import javax.ejb.Local;
import javax.persistence.PersistenceException;

@Local
public interface SeqGeneralesServiceEntityLocal {

	BigInteger obtieneClaveNueva(String parametro) throws PersistenceException;
	
	Boolean existeSecuencia(String var) throws PersistenceException ;
	
	Boolean creaSecuencia(String id, int value, String desc) throws PersistenceException ;
}
