package mx.gob.imss.ctirss.delta.gestion.individuo.service.business;

import javax.ejb.Remote;

import mx.gob.imss.ctirss.delta.model.gestion.individuo.Fisica;

/**
 * 081012
 * @author 191807
 *
 */
@Remote
public interface ActualizarPersonaFisicaServiceBusinessRemote {

	/**
	 * 191807 081012
	 * Metodo encargado de actualizar una persona fisica en BD
	 * @param fisica
	 * @return
	 * @throws
	 */
    Fisica actualizarPersonaFisica(Fisica fisica);

}
