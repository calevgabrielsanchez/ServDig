/**
 * 
 */
package mx.gob.imss.ctirss.delta.gestion.individuo.service.utility;

import java.util.Map;

import javax.ejb.Local;

import mx.gob.imss.ctirss.delta.exception.individuo.ErrorComparacionDatosRENAPOException;
import mx.gob.imss.ctirss.delta.exception.individuo.ErrorComparacionDatosSATException;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Fisica;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.ICADatosRespuesta;

/**
 * @author Lucio Duran Silva
 *
 */
@Local
public interface CompararPersonaFisicaEntidadExternaUtilityLocal {

	
	/**
	 * Compara la informacion de una persona fisica con las tres fuentes de
	 * origen, IMSS(candidato), RENAPO y Entrada.
	 * 
	 * @param candidato
	 * @param entidad
	 * @param entrada
	 * @return
	 * @exception ErrorComparacionDatosRENAPOException
	 *                : En caso de que los datos de entrada no coincidan con los
	 *                datos de la entidad externar RENAPO.
	 */
	Fisica compararPersonaFisicaConRENAPO(Fisica candidato, Fisica entidad, Fisica entrada)throws ErrorComparacionDatosRENAPOException;
	
	
	/**
	 * Compara la informacion de una persona fisica la de entrada, la del candidato y la de la entidad externa SAT.
	 * @param candidato
	 * @param entidad
	 * @param entrada
	 * @return
	 * @throws ErrorComparacionDatosSATException
	 */
	Fisica compararPersonaFisicaConSAT(Fisica candidato, Fisica entidad, Fisica entrada)throws ErrorComparacionDatosSATException;
	
	/**
	 * Compara la informacion entre una persona y la consultada en RENAPO
	 * @param personaRenapo
	 * @param parsonaSugerida
	 * @return
	 * @throws ErrorComparacionDatosSATException
	 */
	Boolean comparaDatosBasicosRENAPO(Fisica personaRenapo, Fisica parsonaSugerida) throws ErrorComparacionDatosRENAPOException;
	
	Integer comparaDiferenciaDatosBasicosRENAPO(Fisica personaRenapo, Fisica parsonaSugerida) throws ErrorComparacionDatosRENAPOException;

	Boolean comparaDatosBasicosSAT(Fisica personaSat, Fisica personaSugerida) throws ErrorComparacionDatosSATException;
	
	Boolean comparaNombreDePersonaFisica(Fisica entidad, Fisica entrada);
	
	ICADatosRespuesta compararDosPersonasFisicas(Fisica fisica, Fisica entidad,
			Map<String, String> mensajes, boolean indConsultaRENAPO,
			boolean indConsultaSAT) throws ErrorComparacionDatosRENAPOException;
	
	/**
	 * Metodo que compara los datos estadisticos entre 2 personas fisicas que recibe una con las reglas 
	 * de datos de renapo done la fisicaAsegurado puede tener o no fecha de nacimiento y se compara por mes y año
	 * @param personaRenapo
	 * @param personaAsegurado
	 * @return
	 * @throws ErrorComparacionDatosRENAPOException
	 */
	 Boolean comparaDatosBasicosAseguradoMesAnioNacRENAPO(Fisica personaRenapo,
			Fisica personaAsegurado) throws ErrorComparacionDatosRENAPOException;
}
