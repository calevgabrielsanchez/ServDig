package mx.gob.imss.ctirss.delta.derechohabientes.service.utility.interfaces;

import java.util.List;

import javax.ejb.Remote;


import mx.gob.imss.ctirss.delta.exception.derechohabiente.DerechohabientesBusinessException;
import mx.gob.imss.ctirss.delta.model.Usuario;
import mx.gob.imss.ctirss.delta.model.derechohabiente.MedicoEnTurno;
import mx.gob.imss.ctirss.delta.model.derechohabiente.UnidadMedicaFamiliar;
import mx.gob.imss.ctirss.delta.model.domicilio.Asentamiento;
import mx.gob.imss.ctirss.delta.model.domicilio.Domicilio;
import mx.gob.imss.ctirss.delta.model.domicilio.Municipio;

/**
 * @author Mario Teran Blanco
 * @company IMSS (Instituto Mexicano del Seguro Social)
 * @date 21/04/2012
 */
@Remote
public interface CambioMasivoClinicaServiceRemote {
	
	public List<UnidadMedicaFamiliar> getUmfBySubDelegacionSinUmf(Long idSubdelgacion, Long idUmf) throws DerechohabientesBusinessException;
	public List<UnidadMedicaFamiliar> getUmfbySubDelegacion(Long idDelegacion) throws DerechohabientesBusinessException, Exception;
	Domicilio getDomicilioByDelegacion(Long idDelegacion) throws Exception;
	List<Asentamiento> getAsentamientosByMunicipio(Municipio municipio);
	List<MedicoEnTurno> medicoEnTurnos(Long idUmf) throws DerechohabientesBusinessException, Exception;
	List<Asentamiento> getAsentamientosPorUmf(Long idUmfOrigen) throws DerechohabientesBusinessException, Exception;;
	Long[] guardarCambioClinicaMasivo(List<Asentamiento> asentamientos, List<MedicoEnTurno> matutino, List<MedicoEnTurno> vespertino, Usuario usuario, Long idUmfDestino) throws DerechohabientesBusinessException, Exception;
}
