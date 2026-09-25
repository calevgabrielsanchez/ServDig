/**
 * 
 */
package mx.gob.imss.ctirss.delta.gestion.individuo.service.business;

import java.util.List;

import javax.ejb.Remote;

import mx.gob.imss.ctirss.delta.exception.individuo.ErrorComparacionDatosSATException;
import mx.gob.imss.ctirss.delta.exception.individuo.PersonasNoLocalizadasException;
import mx.gob.imss.ctirss.delta.exception.individuo.RFCNoLocalizadoEnEntidadExternaException;
import mx.gob.imss.ctirss.delta.framework.exceptions.ClienteWebserviceSatRfcException;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Candidato;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Moral;

/**
 * 130912
 * @author Samuel Rodr�guez Grajeda
 *
 */
@Remote
public interface ConsultaPersonaMoralServiceBusinessRemote {

	
	List<Candidato> consultarPersonaMoral(Moral moral);
	
	/**
	 * Busca la persona moral por rfc en IMSS si la encuentra m�s de una vez
	 * obtiene la mejor calificada y compara sus datos con sat y si hay errores manda la
	 * excepcion si no esta en IMSS la busca en el sat con rfc y la retorna.
	 * @param moral
	 * @return
	 * @throws ErrorComparacionDatosSATException
	 * @throws RFCNoLocalizadoEnEntidadExternaException
	 * @throws ClienteWebserviceSatRfcException
	 */
	Moral consultarPersonaMoralPorRFCEnIMSSySAT(Moral moral) 
			throws ClienteWebserviceSatRfcException, PersonasNoLocalizadasException;

	/**
	 * Busca la persona moral por rfc en IMSS si la encuentra m�s de una vez
	 * obtiene la mejor calificada y compara sus datos con sat y si hay errores manda la
	 * excepcion si no esta en IMSS la busca en el sat con rfc y la retorna.
	 * @param moral
	 * @return
	 * @throws ErrorComparacionDatosSATException
	 * @throws RFCNoLocalizadoEnEntidadExternaException
	 * @throws ClienteWebserviceSatRfcException
	 */
	Moral consultarPersonaMoralPorRFCEnIMSSySAT_AP(Moral moral)
			throws ClienteWebserviceSatRfcException, PersonasNoLocalizadasException;
}
