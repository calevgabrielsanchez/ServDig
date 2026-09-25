/**
 * 
 */
package mx.gob.imss.ctirss.delta.derechohabientes.service.utility.interfaces;

import java.util.List;

import javax.ejb.Remote;

import mx.gob.imss.ctirss.delta.exception.derechohabiente.CodigoSinUmfException;
import mx.gob.imss.ctirss.delta.exception.derechohabiente.DerechohabientesBusinessException;
import mx.gob.imss.ctirss.delta.model.derechohabiente.Consultorio;
import mx.gob.imss.ctirss.delta.model.derechohabiente.MedicoEnTurno;
import mx.gob.imss.ctirss.delta.model.derechohabiente.MedicoFamiliar;
import mx.gob.imss.ctirss.delta.model.derechohabiente.Turno;
import mx.gob.imss.ctirss.delta.model.derechohabiente.UnidadMedicaFamiliar;
import mx.gob.imss.ctirss.delta.model.domicilio.Asentamiento;
import mx.gob.imss.ctirss.delta.model.domicilio.CodigoPostal;
import mx.gob.imss.ctirss.delta.model.domicilio.EntidadFederativa;
import mx.gob.imss.ctirss.delta.model.domicilio.Municipio;


/**
 * @author ghdolores
 *
 */
@Remote
public interface UmfServiceRemote {

	/**
	 * Metodo para obtener el consultorio con menor poblacion en una umf
	 * paraun turno especificado
	 * @param idUmf
	 * @param idTurno
	 * @return
	 * @throws DerechohabientesBusinessException
	 */
	Consultorio getConsultorioMenorPoblacion(Long idUmf, Long idTurno, Boolean mostrarVirtuales) throws DerechohabientesBusinessException;
	/**
	 * MEtodo para obtener las umfs
	 * @param idUmf
	 * @return
	 * @throws DerechohabientesBusinessException
	 */
	List<MedicoEnTurno> getMedicosByUmf(Long idUmf)  throws DerechohabientesBusinessException;
	/**
	 * Metodo para obtener los turnos disponibles en uns UMF
	 * @param idUmf - La umf de donde se quieren obtener las umfs disponibles
	 * @return
	 * @throws DerechohabientesBusinessException
	 */
	List<Turno> getTurnosDisponiblesPorUmf(Long idUmf) throws DerechohabientesBusinessException;
	MedicoFamiliar getMedico(Long idMedico)  throws DerechohabientesBusinessException;
	List<UnidadMedicaFamiliar> findUmfbySubDelagacionDelegacion(Long idSubdelegacion) throws DerechohabientesBusinessException;
	List<CodigoPostal> findCodigosPostalByUmf(Long idUmf) throws DerechohabientesBusinessException;
	List<Asentamiento> findAsentamientosByUmg(Long idUmf) throws DerechohabientesBusinessException;
	List<Consultorio> findConsultorioByUmfTurno( Long idUmf, Long idTurno, Boolean mostrarVirtuales) throws DerechohabientesBusinessException;
	List<MedicoEnTurno> findMedicosByUmfTurno( Long idUmf, Long idTurno) throws DerechohabientesBusinessException;
	List<MedicoEnTurno> findMedicosPoblacionByUmfTurno( Long idUmf, Long idTurno) throws DerechohabientesBusinessException;
	List<Consultorio> findConsultorioByUmfTurnoMedicoEsp( Long idUmf, Long idTurno,Long idMedicoEspecialidad) throws DerechohabientesBusinessException, Exception;
	List<MedicoEnTurno> findMedicosByUmfTurnoConsultorio(Long idUmf, Long idTurno, Long idConsultorio) throws DerechohabientesBusinessException;
	List<MedicoEnTurno> findMedicosByUmfTurnoConsultorioMedicoEsp(Long idUmf, Long idTurno, Long idConsultorio,Long idMedicoEspecialidad) throws DerechohabientesBusinessException, Exception;
	List<EntidadFederativa> findEstadosByDelegacion(Long idDelegacion) throws DerechohabientesBusinessException, Exception; 
	List<Municipio> findMunicipiosByDelegacionEstado(Long idDelegacion, Long idEstado) throws DerechohabientesBusinessException, Exception;
	List<Asentamiento> finAsentamientosByDelegacionEstadoMunicipio(Long idDelegacion, Long idEstado, Long idMunicipio) throws DerechohabientesBusinessException, Exception;
	List<UnidadMedicaFamiliar> findUmfByCodigoPostal(String codigoPostal) throws DerechohabientesBusinessException,CodigoSinUmfException, Exception ;
	UnidadMedicaFamiliar getUnidadMedicaFamiliarById(Long idUmf) throws DerechohabientesBusinessException;
	Boolean mismaCircunscripcion(Long idUmfOrigen, Long idUmfDestino) throws DerechohabientesBusinessException;
	UnidadMedicaFamiliar findUmfCodPosByCodPos(String codigoPostal) throws DerechohabientesBusinessException;
	List<MedicoEnTurno> findMedicosByUmfTurnoConsultorioConPoblacion(Long idUmf,Long idTurno, Long idConsultorio)  throws DerechohabientesBusinessException;
	/**
	 * Metodo para buscar las umfs de acuerdo al codigo postal, en caso de que 
	 * notEqualIndUmfCfe sea 0 se traen las umfs de CFE
	 * en caso de que sea uno no se incluiran las umfs de cfe
	 * @param codigoPostal
	 * @param notEqualIndUmfCfe
	 * @return
	 * @throws DerechohabientesBusinessException
	 * @throws CodigoSinUmfException
	 * @throws Exception
	 */
	List<UnidadMedicaFamiliar> findUmfByCodigoPostal(String codigoPostal, Integer notEqualIndUmfCfe) throws DerechohabientesBusinessException,CodigoSinUmfException,Exception;
	List<UnidadMedicaFamiliar> findUmfsByAsentamiento(Asentamiento asentamiento, Boolean incrluirCFE) throws DerechohabientesBusinessException,CodigoSinUmfException,Exception;
	

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
