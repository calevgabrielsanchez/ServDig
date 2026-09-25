package mx.gob.imss.ctirss.delta.derechohabientes.service.dao;

import java.util.List;

import javax.ejb.Local;

import mx.gob.imss.ctirss.delta.exception.derechohabiente.DerechohabientesBusinessException;
import mx.gob.imss.ctirss.delta.model.derechohabiente.Consultorio;
import mx.gob.imss.ctirss.delta.model.derechohabiente.MedicoEnTurno;
import mx.gob.imss.ctirss.delta.model.derechohabiente.MedicoFamiliar;
import mx.gob.imss.ctirss.delta.model.derechohabiente.Turno;
import mx.gob.imss.ctirss.delta.model.derechohabiente.UnidadMedicaFamiliar;
import mx.gob.imss.ctirss.delta.model.domicilio.Asentamiento;
import mx.gob.imss.ctirss.delta.model.domicilio.CodigoPostal;
import mx.gob.imss.ctirss.delta.persistence.DicUmf;

@Local
public interface UmfDaoLocal {
	
	Consultorio getConsultorioConMenorPoblacion(Long idUmf, Long idTurno, Boolean mostrarVirtuales) throws DerechohabientesBusinessException;
	DicUmf findUmfbySubDelagacionDelegacion(Long idSubDelegacion) throws Exception;
	List<Turno> findTurnosDisponiblesPorUmf(Long idUmf) throws DerechohabientesBusinessException;
	List<MedicoEnTurno> getMedicosByUMF(Long idUmf) throws DerechohabientesBusinessException,Exception;
	MedicoFamiliar getMedico(Long idMedico) throws DerechohabientesBusinessException,Exception;
	List<UnidadMedicaFamiliar> findUnidadesBySubdelegacionSinUmf(Long idSubdelegacion, Long idUmf) throws DerechohabientesBusinessException,Exception;
	List<UnidadMedicaFamiliar> findUnidadesBySubdelegacion(Long idSubdelegacion) throws DerechohabientesBusinessException,Exception;
	List<CodigoPostal> findCodigosPostalesByUmf(Long idUmf) throws DerechohabientesBusinessException,Exception;
	List<Asentamiento> findAsentamientosByUmf(Long idUmf) throws DerechohabientesBusinessException,Exception;
	List<Consultorio> findConsultoriosByUmfTurno(Long idUmf, Long idTurno, Boolean mostrarVirtuales) throws DerechohabientesBusinessException,Exception;
	List<Consultorio> findConsultoriosByUmfTurnoMedicoEsp(Long idUmf, Long idTurno, Long idMedicoEspecialidad)  throws DerechohabientesBusinessException,Exception;
	List<MedicoEnTurno> findMedicosByUmfTurno(Long idUmf, Long idTurno) throws DerechohabientesBusinessException,Exception;
	List<MedicoEnTurno> findMedicosByUmfTurnoConsultorio(Long idUmf, Long idTurno, Long idConsultorio) throws DerechohabientesBusinessException,Exception;
	List<MedicoEnTurno> findMedicosByUmfTurnoConsultorioMedicoEsp(Long idUmf, Long idTurno, Long idConsultorio, Long idMedicoEspecialidad)  throws DerechohabientesBusinessException,Exception;
	List <DicUmf> findUmfbySubDelagacion(Long idSubDelegacion) throws Exception;
	
	/**metodo que consulta las UMF asociadas a una subdelegacion filtrando el nivel de atencion en caso de ser nulo no se concidera como filtro**
	 * 
	 * @param idSubdelegacion
	 * @param nivelAtencion
	 * @return
	 * @throws DerechohabientesBusinessException
	 * @throws Exception
	 */
	List<UnidadMedicaFamiliar> findUnidadesBySubdelegacionNivelAtencion(Long idSubdelegacion, Long nivelAtencion) throws DerechohabientesBusinessException,Exception;
}