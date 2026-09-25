/**
 * 
 */
package mx.gob.imss.ctirss.delta.gestion.patronal.service.interfaces.solicitud.firma;

import javax.ejb.Remote;

import mx.gob.imss.ctirss.delta.exception.firma.ErrorEnInvocacionRecursoRemotoException;
import mx.gob.imss.ctirss.delta.exception.firma.ModelAccessException;
import mx.gob.imss.ctirss.delta.exception.firma.RecursoRemotoNoDisponibleException;
import mx.gob.imss.ctirss.delta.exception.firma.RegistroPatronalInvalidoEnCertificadoException;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.FirmaElectronica;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.SujetoObligado;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.Solicitud;


@Remote
public interface FirmaElectronicaBusinessRemote  {

	/**
	 * Realiza la consulta de la solicitud y todas sus dependencias.
	 * @param idSolicitud Identificador de la solicitud.
	 * @return String separada por '|' que es la cadena a firmardigitalmente.
	 * @throws RecursoRemotoNoDisponibleException
	 * @throws ErrorEnInvocacionRecursoRemotoException
	 * @throws ModelAccessException
	 */
	String obtenerCadenaAFirmar(Solicitud solicitud,SujetoObligado sujetoObligado) throws RecursoRemotoNoDisponibleException, ErrorEnInvocacionRecursoRemotoException, ModelAccessException;
	
//	/**
//	 * Realiza la consulta a los datos de la solicitud.
//	 * @param idSolicitud
//	 * @return
//	 * @throws RecursoRemotoNoDisponibleException
//	 * @throws ErrorEnInvocacionRecursoRemotoException
//	 * @throws ModelAccessException
//	 */
//	Solicitud getSolicitud(BigDecimal idSolicitud) throws RecursoRemotoNoDisponibleException, ErrorEnInvocacionRecursoRemotoException, ModelAccessException;
	
	/**
	 * Método para firmar una solicitud de modificación.
	 * @param firma Objeto con el pkcs7 a firmar.
	 * @return 
	 * @throws RecursoRemotoNoDisponibleException
	 * @throws ErrorEnInvocacionRecursoRemotoException
	 * @throws ModelAccessException
	 */
	Solicitud firmarSolicitud(FirmaElectronica firma ) throws RecursoRemotoNoDisponibleException, ErrorEnInvocacionRecursoRemotoException, ModelAccessException, RegistroPatronalInvalidoEnCertificadoException;
	
	
}
