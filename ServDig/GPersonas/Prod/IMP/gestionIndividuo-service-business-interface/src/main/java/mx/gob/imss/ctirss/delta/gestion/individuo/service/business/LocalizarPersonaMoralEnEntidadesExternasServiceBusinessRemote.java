package mx.gob.imss.ctirss.delta.gestion.individuo.service.business;

import javax.ejb.Remote;

import mx.gob.imss.ctirss.delta.exception.individuo.ErrorComparacionDatosSATException;
import mx.gob.imss.ctirss.delta.exception.individuo.RFCNoLocalizadoEnEntidadExternaException;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Moral;

/**
 * 120912
 * Esta interface corresponde al diagrama N2
 * @author Samuel Rodríguez Grajeda
 *
 */
@Remote
public interface LocalizarPersonaMoralEnEntidadesExternasServiceBusinessRemote {
	
	/**
	 * Este metodo se encargara de localizar a una persona moral en el SAT y regresara otro objeto persona siempre y cuando haya sido localizada en SAT
	 * @param personaMoralSugerida
	 * @return
	 */
	Moral localizarPersonaMoralEnEntidadesExternas(Moral personaMoralSugerida) throws ErrorComparacionDatosSATException, RFCNoLocalizadoEnEntidadExternaException;

}
