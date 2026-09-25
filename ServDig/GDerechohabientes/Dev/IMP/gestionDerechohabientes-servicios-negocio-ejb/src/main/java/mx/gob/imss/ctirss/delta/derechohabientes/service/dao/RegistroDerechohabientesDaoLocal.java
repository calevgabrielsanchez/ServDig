package mx.gob.imss.ctirss.delta.derechohabientes.service.dao;

import java.util.List;

import javax.ejb.Local;

import mx.gob.imss.ctirss.delta.exception.derechohabiente.DerechohabientesBusinessException;
import mx.gob.imss.ctirss.delta.model.derechohabiente.Derechohabiente;
import mx.gob.imss.ctirss.delta.model.derechohabiente.GrupoFamiliar;
import mx.gob.imss.ctirss.delta.model.derechohabiente.RazonRegistro;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.PersonaDomicilio;
import mx.gob.imss.ctirss.delta.model.gestion.medio.contacto.MedioContacto;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.PersonaInteresadaSolicitud;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TramiteRegistroDerechohabiente;
import mx.gob.imss.ctirss.delta.framework.paginador.model.DatosSalidaPaginador;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.ReporteRegistro;

/**
 * @author Juan Manuel Marquez
 * @company IMSS (Instituto Mexicano del Seguro Social)
 * @date 13/05/2012
 */
@Local
public interface RegistroDerechohabientesDaoLocal {

	Boolean saveRegistroDerechohabiente(TramiteRegistroDerechohabiente miRegistroDerechohabiente) throws DerechohabientesBusinessException, Exception;
	Derechohabiente saveDerechohabiente(Derechohabiente miDerechohabiente) throws DerechohabientesBusinessException, Exception;
	PersonaDomicilio savePersonaDomicilio(PersonaDomicilio miPersonaDomicilio) throws DerechohabientesBusinessException, Exception;
	void savePersonaInteresada(PersonaInteresadaSolicitud miPersonaInteresada) throws DerechohabientesBusinessException, Exception;
	void savePersonaContacto(MedioContacto medioContacto, long idPersona) throws Exception;
	GrupoFamiliar saveGrupoFamiliar(GrupoFamiliar miGrupoFamiliar) throws DerechohabientesBusinessException, Exception;
	void actualizaGrupoFamiliar(GrupoFamiliar miGrupoFamiliar) throws DerechohabientesBusinessException, Exception;
	TramiteRegistroDerechohabiente getRegistroDerechohabiente(Long idTramite) throws DerechohabientesBusinessException, Exception;
	PersonaDomicilio getPersonaDom(Long idPersona, Long tipoDomicilio) throws DerechohabientesBusinessException, Exception;
	boolean findModalidadParentesco(long idModalidad, String modalidades) throws DerechohabientesBusinessException, Exception;	
	void actualizaRegistroDerechohabiente(TramiteRegistroDerechohabiente miRegistroDerechohabiente) throws DerechohabientesBusinessException, Exception;
	boolean existeRegistroDerechohabiente(Long idTramite) throws DerechohabientesBusinessException, Exception;
	List<RazonRegistro> findRazonRegistro();
	/**
	 * Metodo encargado de actualizar la fecha de baja del registro en grupo familiar a null
	 * @param cveIdAsignacionNSS
	 * @param cveIdPersonaIntegrante
	 * @throws DerechohabientesBusinessException
	 */
	void actualizaFechaBajaGrupoFamiliartoNull(long cveIdAsignacionNSS, long cveIdPersonaIntegrante) throws DerechohabientesBusinessException;
	void actualizaFechaBajaGrupoFamiliarAfecha(long cveIdAsignacionNSS, long cveIdPersonaIntegrante) throws DerechohabientesBusinessException;
	List<ReporteRegistro> findRegistroConyugeConcubinario(String fechaInicio, String fechaFin, Long cveDelegacion, Long cveSubdelegacion) throws Exception;
	List<ReporteRegistro> findRegistroUnionCivil(String fechaInicio, String fechaFin, Long cveDelegacion, Long cveSubdelegacion) throws Exception;
}
