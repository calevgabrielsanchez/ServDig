/**
 * 
 */
package mx.gob.imss.ctirss.delta.gestion.individuo.service.utility;

import java.util.Map;

import javax.ejb.Local;

import mx.gob.imss.ctirss.delta.exception.individuo.ErrorComparacionDatosSATException;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.ICADatosRespuesta;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Moral;

/**
 * 120912
 * @author Samuel Rodríguez Grajeda
 *
 */
@Local
public interface CompararPersonaMoralEntidadExternaUtilityLocal {

	
	/**
	 * Compara la informacion de una persona moral sugerida con lo que se trae del SAT
	 * @param candidato
	 * @param entidad
	 * @param entrada
	 * @return
	 * @exception ErrorComparacionDatosSATException
	 *                : En caso de que los datos de entrada no coincidan con los
	 *                datos de la entidad externa SAT.
	 */
	Moral compararPersonaMoralConSAT(Moral candidato, Moral entidad, Moral entrada)throws ErrorComparacionDatosSATException;
	
	/**
	 * Contiene la lógica del caso de uso DST - 10 Comparar Persona
	 * 
	 * @param moral
	 * @param entidad
	 * @param mensajes
	 * @return
	 */
	ICADatosRespuesta compararDosPersonasMorales(Moral moral,
			Moral entidad, Map<String, String> mensajes);

}
