package mx.gob.imss.ctirss.delta.derechohabientes.service.utility.interfaces;

import javax.ejb.Remote;

import mx.gob.imss.ctirss.delta.exception.derechohabiente.DerechohabientesBusinessException;
import mx.gob.imss.ctirss.delta.exception.domicilio.UmfNoLocalizadaException;
import mx.gob.imss.ctirss.delta.model.derechohabiente.dto.externo.AsentamientoExternoDto;
import mx.gob.imss.ctirss.delta.model.externo.response.derechohabientes.ConsultorioResponse;
import mx.gob.imss.ctirss.delta.model.externo.response.derechohabientes.TurnoConsultorioResponse;
import mx.gob.imss.ctirss.delta.model.externo.response.derechohabientes.TurnoResponse;
import mx.gob.imss.ctirss.delta.model.externo.response.derechohabientes.UmfResponse;

@Remote
public interface CatalogosDerechohabientesExternoRemote {

	/**
	 * 
	 * @param idAsignacion
	 * @param idUmf
	 * @param idParentesco
	 * @return
	 */
	TurnoConsultorioResponse findAntecedentesEnUmf(Long idAsignacion, Long idUmf, Long idParentesco);
	/**
	 * 
	 * @param idUmf
	 * @return
	 */
	TurnoResponse findTurnosByUmf(Long idUmf);
	
	/**
	 * Metodo para obtener el consultorio con menor poblacion en una umf
	 * paraun turno especificado
	 * @param idUmf
	 * @param idTurno
	 * @return
	 * @throws DerechohabientesBusinessException
	 */
	ConsultorioResponse getConsultorioMenorPoblacion(Long idUmf, Long idTurno);
	
	

	/**
	 * Metodo que recupera una lista de UMF asociadas a un codigo postal a 5 digitos
	 * @param String codigoPostal
	 * @return lista de UMF con los datos de la delegacion y subdelegacion a la que pertenece llenos
	 * @throws UmfNoLocalizadaException
	 */
	UmfResponse consultarUMFAcentamiento(AsentamientoExternoDto asentamiento);

}
